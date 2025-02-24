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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MaxQuantityNegativeTest {

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        StringBuilder logBuilder = new StringBuilder();
        LocalDateTime startTime = LocalDateTime.now();
        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT             \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : MaxQuantityNegativeTest\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : Chrome\n");
        logBuilder.append("===========================================================\n\n");

        try {
            driver.get(BASE_URL);
            Thread.sleep(600);

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();
            logBuilder.append("[" + getCurrentTime() + "] Clicked initial add-to-cart button.\n");

            Thread.sleep(5000);
            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(MAX_QUANTITY));
            logBuilder.append("[" + getCurrentTime() + "] Set product quantity to " + MAX_QUANTITY + ".\n");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();
            logBuilder.append("[" + getCurrentTime() + "] Clicked final add-to-cart button.\n");

            Thread.sleep(2000);
            for (int attempt = 0; attempt < 5; attempt++) {
                quantityInput.clear();
                quantityInput.sendKeys("15");
                logBuilder.append("[" + getCurrentTime() + "] Attempt " + (attempt + 1) + " to exceed max quantity.\n");

                String currentQuantity = quantityInput.getAttribute("value");
                if (currentQuantity.equals("15")) {
                    logBuilder.append("[" + getCurrentTime() + "] Successfully set quantity to 15.\n");
                    break;
                }
                Thread.sleep(1000);
            }

            String finalQuantity = quantityInput.getAttribute("value");
            assertTrue(finalQuantity.equals("15"), "Expected quantity to be 15, but got: " + finalQuantity);
            logBuilder.append("[" + getCurrentTime() + "] Verified quantity is 15.\n");
        
        } catch (Exception e) {
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("ERROR: " + e.getMessage() + "\n");
            logBuilder.append("Exception Details: ").append(e.toString()).append("\n");
            logBuilder.append("-----------------------------------------------------------\n");
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Execution End    : ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            logBuilder.append("Total Duration   : ").append(Duration.between(startTime, endTime).toSeconds()).append(" seconds\n");
            logBuilder.append("Test Result      : FAILED ❌\n");
            logBuilder.append("-----------------------------------------------------------\n");
            writeLogToFile(logBuilder.toString());
            driver.quit();
        }
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private void writeLogToFile(String log) {
        String filePath = "test-results.txt";
        try {
            Files.write(Paths.get(filePath), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("File written successfully");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
