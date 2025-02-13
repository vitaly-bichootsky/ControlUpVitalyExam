package com.automation.config;

import com.automation.utils.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class UITestBase {

    protected WebDriver driver;

    @BeforeClass
    @Step("Set up WebDriver")
    public void setUp() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    @Step("Tear down WebDriver")
    public void tearDown() {
        DriverFactory.closeDriver();
    }
}
