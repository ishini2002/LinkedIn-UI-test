package com.ishini.sqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private final By searchBox = By.cssSelector(
            "input[placeholder*='Search'], input[aria-label*='Search'], input[role='combobox']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // LinkedIn re-renders the search box when it is clicked, so the element is
    // found again after the click, and the whole step is retried if it goes stale.
    public SearchResultsPage search(String term) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                waitForVisible(searchBox).click();

                WebElement box = waitForVisible(searchBox);   // fresh element after the click
                box.sendKeys(Keys.chord(Keys.CONTROL, "a"));   // replace any text from an earlier attempt
                box.sendKeys(term);
                box.sendKeys(Keys.ENTER);
                return new SearchResultsPage(driver);
            } catch (StaleElementReferenceException e) {
                System.out.println("Search box went stale, retrying (attempt " + attempt + ")");
            }
        }
        throw new IllegalStateException("Could not use the search box after 3 attempts");
    }
}