package com.ishini.sqa.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // Runs after every test: saves a screenshot (PASS_ or FAIL_ in the name), then closes the browser.
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            try {
                byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String status = result.isSuccess() ? "PASS" : "FAIL";
                Path dir = Path.of("screenshots");
                Files.createDirectories(dir);
                Files.write(dir.resolve(status + "_" + result.getName() + "_" + System.currentTimeMillis() + ".png"), png);
            } catch (Exception e) {
                System.out.println("Screenshot failed: " + e.getMessage());
            }
            driver.quit();
        }
    }
}