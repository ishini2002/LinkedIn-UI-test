package com.ishini.sqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.cssSelector("input[type='email'], input[autocomplete*='username']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By signInButton = By.xpath("//button[normalize-space(.)='Sign in']");
    private final By errorMessage = By.cssSelector("[role='alert'], [aria-live='assertive'], [id*='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // LinkedIn has hidden duplicates of some elements, so one locator can match
    // several elements. This explicit wait returns the first one that is VISIBLE.
    private WebElement waitForVisible(By locator) {
        return wait.until(d -> d.findElements(locator).stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));
    }

    public void open() {
        driver.get("https://www.linkedin.com/login");
        waitForVisible(usernameField);
    }

    public void login(String username, String password) {
        WebElement user = waitForVisible(usernameField);
        user.clear();
        user.sendKeys(username);

        WebElement pass = waitForVisible(passwordField);
        pass.clear();
        pass.sendKeys(password);

        WebElement signIn = waitForVisible(signInButton);
        wait.until(ExpectedConditions.elementToBeClickable(signIn)).click();
    }

    public boolean isErrorDisplayed() {
        try {
            waitForVisible(errorMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoggedIn() {
        try {
            wait.until(ExpectedConditions.urlContains("/feed"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // If LinkedIn shows a CAPTCHA or security check, we do NOT bypass it.
    // We wait up to 2 minutes for YOU to solve it manually in the browser.
    public void waitForManualVerification() {
        String url = driver.getCurrentUrl();
        if (url.contains("checkpoint") || url.contains("challenge")) {
            System.out.println(">>> LinkedIn security check detected. Complete it manually in Chrome.");
            new WebDriverWait(driver, Duration.ofSeconds(120))
                    .until(ExpectedConditions.urlContains("/feed"));
        }
    }
}