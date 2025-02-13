package com.automation.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
public class DriverFactory {

    @Getter
    private static WebDriver driver;

    // Static block to initialize the WebDriver with specific configurations.
    // Chrome options are configured here, and the driver is instantiated.
    static {
        try {
            initializeDriver();
        } catch (Exception e) {
            log.error("Failed to initialize WebDriver: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    // Initializes the WebDriver with specified ChromeOptions.
    // These options can be expanded as needed.
    private static void initializeDriver() {
        // Set up ChromeOptions with necessary arguments
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Initialize the WebDriver instance with the options
        log.info("Initializing ChromeDriver with specified options...");
        driver = new ChromeDriver(options);
        log.info("WebDriver initialized successfully.");
    }

    // Closes the WebDriver and performs necessary cleanup.
    // This can be called at the end of the test session to ensure proper cleanup.
    public static void closeDriver() {
        if (driver != null) {
            log.info("Closing the WebDriver instance...");
            driver.quit();
            driver = null;
            log.info("WebDriver instance closed successfully.");
        } else {
            log.warn("WebDriver instance is already null. No action needed.");
        }
    }

}



