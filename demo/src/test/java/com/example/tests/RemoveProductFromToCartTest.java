package com.example.tests;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RemoveProductFromToCartTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String MENU_BUTTON_SELECTOR = ".menu-btn";
    private static final String DELETE_FROM_CART_SELECTOR = ".btn.btn-danger.btn-delete-from-cart";
    private static final String CHECKOUT_BUTTON_SELECTOR = ".pushy-link.btn.btn-primary";
    private static final String CART_TOTAL_SELECTOR = "#cart-total"; 

    @Test
    public void removeProductFromCart() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(BASE_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));  
            
            Thread.sleep(600);

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();

            Thread.sleep(2000);

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();

            Thread.sleep(2000);

            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MENU_BUTTON_SELECTOR)));
            menuButton.click();

            Thread.sleep(2000);

            WebElement deleteFromCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(DELETE_FROM_CART_SELECTOR)));
            deleteFromCartButton.click();

            Thread.sleep(2000);
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CHECKOUT_BUTTON_SELECTOR)));
            checkoutButton.click();

            WebElement cartTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CART_TOTAL_SELECTOR)));
            String cartTotalText = cartTotal.getText();
            assertEquals("$0.00", cartTotalText);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}


