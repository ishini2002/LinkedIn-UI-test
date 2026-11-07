package com.ishini.sqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URI;

public class SearchResultsPage extends BasePage {

    private final By resultLinks = By.cssSelector(
            "main a[href*='/in/'], main a[href*='/company/']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("/search/results"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasVisibleResult() {
        try {
            waitForVisible(resultLinks);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Clicks the first visible result and returns the path of the link it clicked.
    // The link is found again and retried if the page re-renders while we click.
    public String clickFirstResult() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                WebElement first = waitForVisible(resultLinks);
                String path = URI.create(first.getAttribute("href")).getPath();
                wait.until(ExpectedConditions.elementToBeClickable(first)).click();
                return path;
            } catch (StaleElementReferenceException e) {
                System.out.println("Result link went stale, retrying (attempt " + attempt + ")");
            }
        }
        throw new IllegalStateException("Could not click the first result after 3 attempts");
    }
}