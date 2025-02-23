package com.example.tests;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TotalCalculationTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String MENU_BUTTON_SELECTOR = ".menu-btn";
    private static final String CHECKOUT_BUTTON_SELECTOR = ".pushy-link.btn.btn-primary";
    private static final String PRODUCT_QUANTITY_SELECTOR = "#product_quantity";
    private static final String CART_TOTAL_SELECTOR = "#total-cart-amount"; 

    @Test
    public void removeProductFromCart() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();

            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys("3");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();

            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MENU_BUTTON_SELECTOR)));
            menuButton.click();

            WebElement totalCartAmountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CART_TOTAL_SELECTOR)));
            String totalCartAmountText = totalCartAmountElement.getText().trim();

            if (!totalCartAmountText.equals("£1200.00")) {
                System.out.println("Error: el total no coincide. Se obtuvo: " + totalCartAmountText);
            } else {
                System.out.println("La cantidad ha sido verificada correctamente: " + totalCartAmountText);
            }

            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CHECKOUT_BUTTON_SELECTOR)));
            checkoutButton.click();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}


