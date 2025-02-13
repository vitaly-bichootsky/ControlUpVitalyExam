package com.automation.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class InventoryPage {
    private WebDriver driver;

    // Locator for inventory items
    private By inventoryItems = By.className("inventory_item");

    // Constructor to initialize WebDriver
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // Retrieves all the inventory items present on the page.
    public List<WebElement> getInventoryItems() {
        try {
            return driver.findElements(inventoryItems);
        } catch (NoSuchElementException e) {
            log.error("Failed to retrieve inventory items: {}", e.getMessage());
            return null; // Return null if elements are not found
        }
    }

    // Retrieves the count of inventory items present on the page
    public int getInventoryItemsCount() {
        List<WebElement> items = getInventoryItems();
        if (items == null) {
            return 0; // Return 0 if items were not retrieved
        }
        return items.size();
    }

    // Adds an item to the cart based on the provided index
    public void addItemToCart(int index) {
        try {
            List<WebElement> items = getInventoryItems();
            if (items != null && index >= 0 && index < items.size()) {
                WebElement item = items.get(index);
                WebElement addToCartButton = item.findElement(By.className("btn_inventory"));
                addToCartButton.click();
            } else {
                log.error("Failed to add item to the cart, invalid index: {}", index);
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            }
        } catch (NoSuchElementException | IndexOutOfBoundsException e) {
            log.error("Failed to add item to cart at index: {}", index);
        }
    }
}
