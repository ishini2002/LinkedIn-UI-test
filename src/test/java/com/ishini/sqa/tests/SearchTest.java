package com.ishini.sqa.tests;

import com.ishini.sqa.base.BaseTest;
import com.ishini.sqa.pages.HomePage;
import com.ishini.sqa.pages.LoginPage;
import com.ishini.sqa.pages.SearchResultsPage;
import com.ishini.sqa.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void searchShowsResultsPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(Config.username(), Config.password());
        loginPage.waitForManualVerification();
        Assert.assertTrue(loginPage.isLoggedIn(), "Login failed, cannot search");

        HomePage home = new HomePage(driver);
        SearchResultsPage results = home.search("software testing");

        Assert.assertTrue(results.isLoaded(), "Search results page did not open");
        Assert.assertTrue(driver.getCurrentUrl().contains("/search/results"), "URL is not a results URL");
        Assert.assertTrue(results.hasVisibleResult(), "No visible result element");
    }
}