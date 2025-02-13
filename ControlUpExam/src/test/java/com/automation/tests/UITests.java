package com.automation.tests;

import com.automation.config.UITestBase;
import com.automation.pages.CartPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import com.automation.utils.CSVReader;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j
public class UITests extends UITestBase {

    private static final String BASE_URL = "https://www.saucedemo.com";
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeClass
    public void setup() {
        // Initialize the Page Objects
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }

    // Test case to verify inventory items are displayed correctly after login
    @Test(priority = 10, dataProviderClass = CSVReader.class, dataProvider = "csvDataProvider", description = "Verify Inventory Items")
    public void verifyInventoryItems(String username, String password, int expectedItems) {
        log.info("Staring test \"Verify Inventory Items\"");
        try {
            navigateToBaseUrl();
            loginToApplication(username, password);

            int actualItemCount = inventoryPage.getInventoryItemsCount();
            Allure.step("Verifying inventory item count");
            Assert.assertEquals(actualItemCount, expectedItems, "Expected inventory item count is " + expectedItems + " but found " + actualItemCount);
        } catch (Exception e) {
            Allure.step("Exception encountered while verifying inventory items: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        log.info("Finishing test \"Verify Inventory Items\"");
    }

    // Test case to add an item to the cart and verify the cart badge count
    @Test(priority = 20, dependsOnMethods = "verifyInventoryItems", dataProviderClass = CSVReader.class, dataProvider = "csvDataProvider", description = "Add Item to Cart")
    public void addItemToCartAndVerifyBadge(int expectedItems) {
        log.info("Staring test \"Add Item to Cart\"");
        try {
            inventoryPage.addItemToCart(0);
            int actualBadgeCount = cartPage.getCartBadgeCount();
            Allure.step("Verifying cart badge count");
            Assert.assertEquals(actualBadgeCount, expectedItems, "Cart badge count should be " + expectedItems);
        } catch (Exception e) {
            Allure.step("Exception encountered while adding item to cart or verifying badge count: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        log.info("Finishing test \"Add Item to Cart\"");
    }

    // Helper method to navigate to the base URL
    private void navigateToBaseUrl() {
        log.info("Navigating to base url: " + BASE_URL);
        try {
            driver.get(BASE_URL);
        } catch (Exception e) {
            Allure.step("Failed to navigate to URL: " + BASE_URL + " Error: " + e.getMessage());
            Assert.fail("Failed to navigate to base URL: " + BASE_URL);
        }
    }

    // Helper method to perform login
    private void loginToApplication(String username, String password) {
        log.info("Login to Application");
        try {
            loginPage.login(username, password);
        } catch (Exception e) {
            Allure.step("Login failed for user: " + username + ". Error: " + e.getMessage());
            Assert.fail("Login failed for user: " + username + ". Error: " + e.getMessage());
        }
    }
}

