package com.example.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
    private static final String RESULTS_FILE_PATH = "test-results.txt"; // Archivo de resultados

    @Test
    public void verifyMaxQuantityLimit() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        StringBuilder logBuilder = new StringBuilder();
        LocalDateTime startTime = LocalDateTime.now();
        logBuilder.append("\n\n");
        logBuilder.append("Test Name: MaxQuantityTest\n");
        logBuilder.append("Test Start Time: ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");

        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));  

            Thread.sleep(600);

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();
            logBuilder.append("Clicked initial add-to-cart button.\n");

            Thread.sleep(5000);

            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(MAX_QUANTITY));
            logBuilder.append("Set product quantity to " + MAX_QUANTITY + ".\n");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();
            logBuilder.append("Clicked final add-to-cart button.\n");

            Thread.sleep(2000);

            for (int attempt = 0; attempt < 5; attempt++) {  
                quantityInput.clear();
                quantityInput.sendKeys("15");
                logBuilder.append("Attempted to set product quantity to 15. Attempt: " + (attempt + 1) + ".\n");

                String currentQuantity = quantityInput.getAttribute("value");
                System.out.println("Cantidad actual después del intento " + (attempt + 1) + ": " + currentQuantity);
                if (currentQuantity.equals("15")) {
                    logBuilder.append("Successfully set product quantity to 15.\n");
                    break;  
                }
                
                Thread.sleep(1000); 
            }

            String finalQuantity = quantityInput.getAttribute("value");
            assertTrue(finalQuantity.equals("15"), "La cantidad debe ser 15. Valor actual: " + finalQuantity);
            logBuilder.append("Verified product quantity is 15.\n");

            Thread.sleep(2000);

        } catch (InterruptedException e) {
            logError("Test execution interrupted", e, logBuilder);
        } catch (NoSuchElementException e) {
            logError("Element not found", e, logBuilder);
        } catch (TimeoutException e) {
            logError("Operation timed out", e, logBuilder);
        } catch (Exception e) {
            logError("Unexpected error occurred", e, logBuilder);
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            logBuilder.append("Test End Time: ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            logBuilder.append("Test executed successfully.\n");
            writeLogToFile(logBuilder.toString());
            driver.quit();
        }
    }

    private void logError(String message, Exception e, StringBuilder logBuilder) {
        logBuilder.append(message).append(": ").append(e.getMessage()).append("\n");
        e.printStackTrace();
    }

    private void writeLogToFile(String log) {
        try {
            Files.write(Paths.get(RESULTS_FILE_PATH), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("File written successfully");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

