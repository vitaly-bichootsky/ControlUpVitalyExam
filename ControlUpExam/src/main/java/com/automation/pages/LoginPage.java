package com.automation.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

@Slf4j
public class LoginPage {

    private WebDriver driver;

    // Locators for the username, password fields, and the login button
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");

    // Constructor to initialize WebDriver
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Logs in the user by entering the username and password, and clicking the login button.
    public void login(String username, String password) {
        try {
            enterUsername(username);
            enterPassword(password);
            clickLoginButton();
        } catch (NoSuchElementException e) {
            log.error("One or more login elements (username, password, login button) are missing on the page: {}", e.getMessage());
        }
    }

    // Enters the username into the username field.
    private void enterUsername(String username) {
        WebElement usernameElement = findElement(usernameField);
        usernameElement.sendKeys(username);
    }

    // Enters the password into the password field.
    private void enterPassword(String password) {
        WebElement passwordElement = findElement(passwordField);
        passwordElement.sendKeys(password);
    }

    // Clicks the login button to submit the form.
    private void clickLoginButton() {
        WebElement loginButtonElement = findElement(loginButton);
        loginButtonElement.click();
    }

    // Helper method to find a web element, encapsulating the logic for finding elements.
    private WebElement findElement(By locator) {
        return driver.findElement(locator);
    }
}
