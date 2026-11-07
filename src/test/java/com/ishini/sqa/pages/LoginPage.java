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
    private final By errorMessage = By.xpath(
            "//*[@role='alert'] | //*[contains(@id,'error')] | //*[contains(normalize-space(text()),'Wrong email or password')]");

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

    // After a SUCCESSFUL login: wait for the feed or a security check to show up.
    // If a CAPTCHA appears we do NOT bypass it. We wait up to 2 minutes for YOU to solve it.
    public void waitForManualVerification() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(d -> d.getCurrentUrl().contains("/feed") || d.getCurrentUrl().contains("checkpoint"));
        } catch (Exception ignored) {
            // nothing appeared in 15s; the test assertions will report it
        }
        if (driver.getCurrentUrl().contains("checkpoint")) {
            System.out.println(">>> LinkedIn security check detected. Complete it MANUALLY in Chrome (2 minutes).");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(120))
                        .until(ExpectedConditions.urlContains("/feed"));
            } catch (Exception ignored) {
                // the test will fail; document this limitation in the report
            }
        }
    }

    // After a WRONG password: wait for either the error message or a security check.
    // If a CAPTCHA appears we do NOT bypass it. We wait up to 2 minutes for YOU to solve it.
    public void waitForManualCheckpointToClear() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(d -> d.getCurrentUrl().contains("checkpoint")
                            || d.findElements(errorMessage).stream().anyMatch(WebElement::isDisplayed));
        } catch (Exception ignored) {
            // neither appeared in 15s; the test assertions will report it
        }
        if (driver.getCurrentUrl().contains("checkpoint")) {
            System.out.println(">>> Security check shown. Complete it MANUALLY in Chrome (2 minutes).");
            try {
                new WebDriverWait(driver, Duration.ofSeconds(120))
                        .until(d -> !d.getCurrentUrl().contains("checkpoint"));
            } catch (Exception ignored) {
                // the test will fail below; document this limitation in the report
            }
        }
    }
}