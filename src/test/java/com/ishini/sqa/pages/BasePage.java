package com.ishini.sqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    // Explicit wait that returns the first VISIBLE element matching the locator.
    // Elements that go stale while we check them are skipped and found again on the next poll.
    protected WebElement waitForVisible(By locator) {
        return wait.until(d -> {
            for (WebElement element : d.findElements(locator)) {
                try {
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (StaleElementReferenceException ignored) {
                    // LinkedIn re-rendered this element; the next poll finds the new one
                }
            }
            return null;
        });
    }
}