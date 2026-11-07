package com.ishini.sqa.tests;

import com.ishini.sqa.base.BaseTest;
import com.ishini.sqa.pages.LoginPage;
import com.ishini.sqa.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void validLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(Config.username(), Config.password());
        loginPage.waitForManualVerification();

        Assert.assertTrue(loginPage.isLoggedIn(), "Feed page not reached. URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("/feed"), "URL does not contain /feed");
    }

    @Test(priority = 2)
    public void invalidPasswordShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(Config.username(), "WrongPassword@123");
        loginPage.waitForManualCheckpointToClear();

        Assert.assertTrue(loginPage.isErrorDisplayed(), "No error message was shown");
        Assert.assertFalse(driver.getCurrentUrl().contains("/feed"), "User should not reach the feed");
    }
}