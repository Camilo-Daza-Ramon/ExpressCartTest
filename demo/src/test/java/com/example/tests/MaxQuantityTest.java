package com.example.tests;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MaxQuantityTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String PRODUCT_QUANTITY_SELECTOR = "#product_quantity";
    private static final int MAX_QUANTITY = 10; 

    @Test
    public void verifyMaxQuantityLimit() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(BASE_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));  

            Thread.sleep(600);

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();

            Thread.sleep(5000);

            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(MAX_QUANTITY));
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();

            Thread.sleep(2000);

            for (int attempt = 0; attempt < 5; attempt++) {  
                quantityInput.clear();
                quantityInput.sendKeys("15");
                
                String currentQuantity = quantityInput.getAttribute("value");
                System.out.println("Cantidad actual después del intento " + (attempt + 1) + ": " + currentQuantity);
                if (currentQuantity.equals("15")) {
                    break;  
                }
                
                Thread.sleep(1000); 
            }

            String finalQuantity = quantityInput.getAttribute("value");
            assertTrue(finalQuantity.equals("15"), "La cantidad debe ser 15. Valor actual: " + finalQuantity);

            Thread.sleep(2000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}











