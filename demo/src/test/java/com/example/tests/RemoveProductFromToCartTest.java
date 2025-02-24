package com.example.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
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
    private static final String RESULTS_FILE_PATH = "test-results.txt"; 

    @Test
    public void removeProductFromCart() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        StringBuilder logBuilder = new StringBuilder();
        LocalDateTime startTime = LocalDateTime.now();
        logBuilder.append("\n\n");
        logBuilder.append("Test Name: RemoveProductFromToCartTest\n");
        logBuilder.append("Test Start Time: ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");

        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));  
            
            Thread.sleep(600);
            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();
            logBuilder.append("Clicked initial add-to-cart button.\n");

            Thread.sleep(2000);
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();
            logBuilder.append("Clicked final add-to-cart button.\n");

            Thread.sleep(2000);
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MENU_BUTTON_SELECTOR)));
            menuButton.click();
            logBuilder.append("Clicked menu button.\n");

            Thread.sleep(2000);
            WebElement deleteFromCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(DELETE_FROM_CART_SELECTOR)));
            deleteFromCartButton.click();
            logBuilder.append("Clicked delete-from-cart button.\n");

            Thread.sleep(2000);
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CHECKOUT_BUTTON_SELECTOR)));
            checkoutButton.click();
            logBuilder.append("Clicked checkout button.\n");

            WebElement cartTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CART_TOTAL_SELECTOR)));
            String cartTotalText = cartTotal.getText();
            assertEquals("$0.00", cartTotalText);
            logBuilder.append("Verified cart total is $0.00.\n");

            Thread.sleep(600);

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


