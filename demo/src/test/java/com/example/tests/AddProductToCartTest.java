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

public class AddProductToCartTest {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://localhost:1111";
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
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
            setProductQuantity("2");
            clickElement(ADD_TO_CART_SELECTOR, "Clicked final add-to-cart button.");

            logEndTime();
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
        logBuilder.append("\n")
                  .append("===========================================================\n")
                  .append("               AUTOMATED TEST EXECUTION REPORT             \n")
                  .append("===========================================================\n")
                  .append("Test Name        : AddProductToCartTest\n")
                  .append("Execution Start  : ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n")
                  .append("Base URL         : ").append(BASE_URL).append("\n")
                  .append("Browser          : Chrome\n")
                  .append("===========================================================\n\n");
    }

    private void clickElement(String cssSelector, String logMessage) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
        element.click();
        logBuilder.append("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + logMessage).append("\n");
        Thread.sleep(600);
    }

    private void setProductQuantity(String quantity) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
        logBuilder.append("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] Set product quantity to ").append(quantity).append(".\n");
        Thread.sleep(600);
    }

    private void logEndTime() {
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        logBuilder.append("\n-----------------------------------------------------------\n")
                  .append("Execution End    : ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n")
                  .append("Total Duration   : ").append(duration.toSeconds()).append(" seconds\n")
                  .append("Test Result      : SUCCESS ✅\n")
                  .append("-----------------------------------------------------------\n");
    }

    private void logError(String message, Exception e) {
        LocalDateTime errorTime = LocalDateTime.now();
        logBuilder.append("\n-----------------------------------------------------------\n")
                  .append("ERROR DETECTED ❌\n")
                  .append("Time             : ").append(errorTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n")
                  .append("Issue           : ").append(message).append("\n")
                  .append("Exception Trace  : ").append(e.toString()).append("\n")
                  .append("-----------------------------------------------------------\n");
        e.printStackTrace();
    }

    private void writeLogToFile(String filePath) throws IOException {
        Files.write(Paths.get(filePath), logBuilder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}


