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

public class TotalCalculationNegativeTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String MENU_BUTTON_SELECTOR = ".menu-btn";
    private static final String CHECKOUT_BUTTON_SELECTOR = ".pushy-link.btn.btn-primary";
    private static final String PRODUCT_QUANTITY_SELECTOR = "#product_quantity";
    private static final String CART_TOTAL_SELECTOR = "#total-cart-amount";
    private static final String RESULTS_FILE_PATH = "test-results.txt"; // Archivo de resultados

    @Test
    public void verifyTotalCalculationNegative() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        StringBuilder logBuilder = new StringBuilder();
        LocalDateTime startTime = LocalDateTime.now();
        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT             \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : TotalCalculationNegativeTest\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : Chrome\n");
        logBuilder.append("===========================================================\n\n");

        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();
            logBuilder.append("[").append(getTimestamp()).append("] Clicked initial add-to-cart button.\n");

            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys("3");
            logBuilder.append("[").append(getTimestamp()).append("] Set product quantity to 3.\n");

            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();
            logBuilder.append("[").append(getTimestamp()).append("] Clicked final add-to-cart button.\n");

            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MENU_BUTTON_SELECTOR)));
            menuButton.click();
            logBuilder.append("[").append(getTimestamp()).append("] Clicked menu button.\n");

            WebElement totalCartAmountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CART_TOTAL_SELECTOR)));
            String totalCartAmountText = totalCartAmountElement.getText().trim();
            logBuilder.append("[").append(getTimestamp()).append("] Total cart amount retrieved: ").append(totalCartAmountText).append("\n");

            String expectedTotal = "£1400.00"; // Valor incorrecto intencional
            if (!totalCartAmountText.equals(expectedTotal)) {
                logBuilder.append("Test Result      : FAILURE ❌ (Expected £1400.00 but got ").append(totalCartAmountText).append(")\n");
            } else {
                logBuilder.append("Test Result      : SUCCESS ✅ (Unexpected match - Check test logic)\n");
            }

            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CHECKOUT_BUTTON_SELECTOR)));
            checkoutButton.click();
            logBuilder.append("[").append(getTimestamp()).append("] Clicked checkout button.\n");

        } catch (NoSuchElementException e) {
            logError("Element not found", e, logBuilder);
        } catch (TimeoutException e) {
            logError("Operation timed out", e, logBuilder);
        } catch (Exception e) {
            logError("Unexpected error occurred", e, logBuilder);
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Execution End    : ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            logBuilder.append("Total Duration   : ").append(Duration.between(startTime, endTime).getSeconds()).append(" seconds\n");
            logBuilder.append("-----------------------------------------------------------\n");
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

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
