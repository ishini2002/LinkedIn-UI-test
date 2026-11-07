package com.ishini.sqa.tests;

import com.ishini.sqa.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DebugTest extends BaseTest {

    @Test
    public void inspectLoginPage() throws Exception {
        driver.get("https://www.linkedin.com/login");
        Thread.sleep(8000); // debug only: let the page finish loading

        System.out.println("DEBUG url     = " + driver.getCurrentUrl());
        System.out.println("DEBUG title   = " + driver.getTitle());
        System.out.println("DEBUG inputs  = " + driver.findElements(By.tagName("input")).size());
        System.out.println("DEBUG iframes = " + driver.findElements(By.tagName("iframe")).size());

        List<WebElement> email = driver.findElements(By.cssSelector("input[type='email']"));
        System.out.println("DEBUG email found = " + email.size());
        if (!email.isEmpty()) {
            System.out.println("DEBUG email displayed = " + email.get(0).isDisplayed());
        }

        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Files.createDirectories(Path.of("screenshots"));
        Files.write(Path.of("screenshots", "debug-login.png"), png);
        Files.writeString(Path.of("screenshots", "debug-login.html"), driver.getPageSource());
    }
}