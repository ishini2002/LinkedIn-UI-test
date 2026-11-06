package com.ishini.sqa.tests;

import com.ishini.sqa.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void loginPageOpens() {
        driver.get("https://www.linkedin.com/login");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("LinkedIn"), "Unexpected page title: " + title);
    }
}