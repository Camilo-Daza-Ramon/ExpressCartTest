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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TotalCalculationTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String RESULTS_FILE_PATH = "test-results.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void verifyTotalCalculation() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        LocalDateTime startTime = LocalDateTime.now();
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT             \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : TotalCalculationTest\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(FORMATTER)).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : Chrome\n");
        logBuilder.append("===========================================================\n\n");

        try {
            driver.get(BASE_URL);
            logAction(logBuilder, "Navigated to base URL");

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-primary.add-to-cart")));
            initialAddToCartButton.click();
            logAction(logBuilder, "Clicked initial add-to-cart button");

            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#product_quantity")));
            quantityInput.clear();
            quantityInput.sendKeys("3");
            logAction(logBuilder, "Set product quantity to 3");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-primary.btn-block.product-add-to-cart")));
            addToCartButton.click();
            logAction(logBuilder, "Clicked final add-to-cart button");

            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".menu-btn")));
            menuButton.click();
            logAction(logBuilder, "Clicked menu button");

            WebElement totalCartAmountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#total-cart-amount")));
            String totalCartAmountText = totalCartAmountElement.getText().trim();
            logAction(logBuilder, "Total cart amount retrieved: " + totalCartAmountText);

            boolean isTestSuccessful = totalCartAmountText.equals("£1200.00");
            if (!isTestSuccessful) {
                logAction(logBuilder, "Error: Total does not match expected value!");
            }

            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pushy-link.btn.btn-primary")));
            checkoutButton.click();
            logAction(logBuilder, "Clicked checkout button");

            LocalDateTime endTime = LocalDateTime.now();
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Execution End    : ").append(endTime.format(FORMATTER)).append("\n");
            logBuilder.append("Total Duration   : ").append(Duration.between(startTime, endTime).getSeconds()).append(" seconds\n");
            logBuilder.append("Test Result      : ").append(isTestSuccessful ? "SUCCESS ✅" : "FAILURE ❌").append("\n");
            logBuilder.append("-----------------------------------------------------------\n");

        } catch (NoSuchElementException | TimeoutException e) {
            logAction(logBuilder, "Test failed due to missing or non-clickable element: " + e.getMessage());
        } catch (Exception e) {
            logAction(logBuilder, "Unexpected error: " + e.getMessage());
        } finally {
            writeLogToFile(logBuilder.toString());
            driver.quit();
        }
    }

    private void logAction(StringBuilder logBuilder, String message) {
        logBuilder.append("[").append(LocalDateTime.now().format(FORMATTER)).append("] ").append(message).append("\n");
    }

    private void writeLogToFile(String log) {
        try {
            Files.write(Paths.get(RESULTS_FILE_PATH), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Test execution report saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing test report: " + e.getMessage());
        }
    }
}
