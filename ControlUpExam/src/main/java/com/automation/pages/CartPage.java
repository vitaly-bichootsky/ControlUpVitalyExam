package com.automation.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.NoSuchElementException;

@Slf4j
public class CartPage {
    private WebDriver driver;

    // Locator for the cart badge element that holds the item count
    private By cartBadge = By.className("shopping_cart_badge");

    // Constructor to initialize WebDriver
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    // This method retrieves the current count displayed in the shopping cart badge
    public int getCartBadgeCount() {
        try {
            WebElement badgeElement = driver.findElement(cartBadge);
            String badgeText = badgeElement.getText();

            // Parse the number from the badge's text and return it
            return Integer.parseInt(badgeText);
        } catch (NumberFormatException e) {
            log.error("Failed to parse the cart badge count: {}", e.getMessage());
            return 0; // Default return value in case of an error
        } catch (NoSuchElementException e) {
            log.error("Cart badge element is not present on the page: {}", e.getMessage());
            return 0; // Return 0 if the badge is not found
        }
    }
}
