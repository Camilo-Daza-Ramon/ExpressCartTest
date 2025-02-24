package com.example.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddProductNegativeTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://localhost:1111";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String NON_EXISTENT_ELEMENT_SELECTOR = ".btn.btn-nonexistent";
    private static final String PRODUCT_QUANTITY_SELECTOR = "#product_quantity";
    private static final String RESULTS_FILE_PATH = "test-results.txt";

    private WebDriver driver;
    private StringBuilder logBuilder;
    private LocalDateTime startTime;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        driver = new ChromeDriver();
        logBuilder = new StringBuilder();
    }

    @Test
    public void addProductToCart() {
        try {
            navigateToBaseUrl();
            logStartTime();

            clickElement(ADD_TO_CART_INITIAL_SELECTOR, "Clicked initial add-to-cart button.");
            Thread.sleep(3000);
            
            setProductQuantity("5");
            Thread.sleep(2000);
            
            clickElement(NON_EXISTENT_ELEMENT_SELECTOR, "Attempted to click nonexistent element.");
            Thread.sleep(4000);
            
            logEndTime("SUCCESS ✅");
            writeLogToFile(RESULTS_FILE_PATH);
        } catch (InterruptedException e) {
            logError("Test execution interrupted", e);
        } catch (NoSuchElementException e) {
            logError("Element not found", e);
        } catch (TimeoutException e) {
            logError("Operation timed out", e);
        } catch (IOException e) {
            logError("IO exception occurred", e);
        } catch (Exception e) {
            logError("Unexpected error occurred", e);
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void navigateToBaseUrl() {
        driver.get(BASE_URL);
    }

    private void logStartTime() {
        startTime = LocalDateTime.now();
        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT             \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : AddProductToCartTest\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : Chrome\n");
        logBuilder.append("===========================================================\n\n");
    }

    private void clickElement(String cssSelector, String logMessage) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
            element.click();
            logBuilder.append("[").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("] ").append(logMessage).append("\n");
        } catch (NoSuchElementException e) {
            logError("Failed to find element with selector: " + cssSelector, e);
        }
        Thread.sleep(600);
    }

    private void setProductQuantity(String quantity) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
        logBuilder.append("[").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).append("] Set product quantity to ").append(quantity).append(".\n");
        Thread.sleep(600);
    }

    private void logEndTime(String result) {
        LocalDateTime endTime = LocalDateTime.now();
        logBuilder.append("-----------------------------------------------------------\n");
        logBuilder.append("Execution End    : ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        logBuilder.append("Total Duration   : ").append(Duration.between(startTime, endTime).getSeconds()).append(" seconds\n");
        logBuilder.append("Test Result      : ").append(result).append("\n");
        logBuilder.append("-----------------------------------------------------------\n");
    }

    private void logError(String message, Exception e) {
        logBuilder.append("-----------------------------------------------------------\n");
        logBuilder.append("ERROR: ").append(message).append("\n");
        logBuilder.append("Exception Details: ").append(e.getMessage()).append("\n");
        logBuilder.append("-----------------------------------------------------------\n");
        e.printStackTrace();
        logEndTime("FAILED ❌");
        try {
            writeLogToFile(RESULTS_FILE_PATH);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void writeLogToFile(String filePath) throws IOException {
        Files.write(Paths.get(filePath), logBuilder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
