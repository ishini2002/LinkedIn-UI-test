package com.ishini.sqa.tests;

import com.ishini.sqa.base.BaseTest;
import com.ishini.sqa.pages.HomePage;
import com.ishini.sqa.pages.LoginPage;
import com.ishini.sqa.pages.SearchResultsPage;
import com.ishini.sqa.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchResultTest extends BaseTest {

    @Test
    public void firstResultOpensCorrectPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(Config.username(), Config.password());
        loginPage.waitForManualVerification();
        Assert.assertTrue(loginPage.isLoggedIn(), "Login failed, cannot continue");

        HomePage home = new HomePage(driver);
        SearchResultsPage results = home.search("software testing");
        Assert.assertTrue(results.isLoaded(), "Search results page did not open");

        String clickedPath = results.clickFirstResult();
        String expectedPart = clickedPath.startsWith("/company/") ? "/company/" : "/in/";

        WebDriverWait pageWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Check 1: the URL shows the right type of page (profile or company).
        pageWait.until(d -> d.getCurrentUrl().contains(expectedPart));
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedPart),
                "Expected URL to contain " + expectedPart + " but was " + driver.getCurrentUrl());

        // Check 2: the page shows a heading with text. LinkedIn does not always use <h1>,
        // so any visible heading element is accepted.
        WebElement heading = pageWait.until(d -> d.findElements(By.cssSelector("h1, h2, [role='heading']"))
                .stream()
                .filter(e -> {
                    try {
                        return e.isDisplayed() && !e.getText().isBlank();
                    } catch (StaleElementReferenceException ex) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null));
        Assert.assertNotNull(heading, "No visible heading found on the opened page");
    }
}