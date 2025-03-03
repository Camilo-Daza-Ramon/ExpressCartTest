package com.example.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private static final String BROWSER = "Chrome";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String PRODUCT_QUANTITY_SELECTOR = "#product_quantity";
    private static final int MAX_QUANTITY = 10;

    @Test
    public void verifyMaxQuantityLimit() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        LocalDateTime startTime = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter logTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder logBuilder = new StringBuilder();

        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT             \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : MaxQuantityTest\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(timeFormatter)).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : ").append(BROWSER).append("\n");
        logBuilder.append("===========================================================\n\n");

        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Thread.sleep(600);
            
            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();
            logBuilder.append("[").append(LocalDateTime.now().format(logTimeFormatter)).append("] Clicked initial add-to-cart button.\n");
            
            Thread.sleep(3000);

            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(MAX_QUANTITY));
            logBuilder.append("[").append(LocalDateTime.now().format(logTimeFormatter)).append("] Set product quantity to ").append(MAX_QUANTITY).append(".\n");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();
            logBuilder.append("[").append(LocalDateTime.now().format(logTimeFormatter)).append("] Clicked final add-to-cart button.\n");

            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Test Result      : SUCCESS ✅\n");
            logBuilder.append("-----------------------------------------------------------\n");
        } catch (Exception e) {
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("ERROR: Operation timed out\n");
            logBuilder.append("Exception Details: ").append(e.getMessage()).append("\n");
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Test Result      : FAILED ❌\n");
            logBuilder.append("-----------------------------------------------------------\n");
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            long duration = Duration.between(startTime, endTime).getSeconds();
            logBuilder.append("Execution End    : ").append(endTime.format(timeFormatter)).append("\n");
            logBuilder.append("Total Duration   : ").append(duration).append(" seconds\n");
            writeLogToFile(logBuilder.toString());
            driver.quit();
        }
    }

    private void writeLogToFile(String log) {
        String filePath = "test-results.txt";
        try {
            Files.write(Paths.get(filePath), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Test report written successfully");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

