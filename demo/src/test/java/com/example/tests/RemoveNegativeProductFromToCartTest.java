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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RemoveNegativeProductFromToCartTest {

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

        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT             \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : RemoveProductFromToCartTest\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : Chrome\n");
        logBuilder.append("===========================================================\n\n");

        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  
            
            Thread.sleep(600);
            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();
            logBuilder.append("[SUCCESS] Clicked initial add-to-cart button.\n");

            Thread.sleep(2000);
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();
            logBuilder.append("[SUCCESS] Clicked final add-to-cart button.\n");

            Thread.sleep(2000);
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(MENU_BUTTON_SELECTOR)));
            menuButton.click();
            logBuilder.append("[SUCCESS] Clicked menu button.\n");

            // Aquí se queda esperando el botón de eliminar sin hacer clic
            Thread.sleep(2000);
            logBuilder.append("[INFO] Waiting for delete-from-cart button...\n");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(DELETE_FROM_CART_SELECTOR)));
            logBuilder.append("[WARNING] The test is waiting indefinitely for the delete button!\n");

            // No se hace clic en el botón de eliminar producto

            Thread.sleep(2000);
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CHECKOUT_BUTTON_SELECTOR)));
            checkoutButton.click();
            logBuilder.append("[SUCCESS] Clicked checkout button.\n");

            WebElement cartTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CART_TOTAL_SELECTOR)));
            String cartTotalText = cartTotal.getText();
            assertEquals("$0.00", cartTotalText);
            logBuilder.append("[SUCCESS] Verified cart total is $0.00.\n");

            Thread.sleep(600);

        } catch (TimeoutException e) {
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("ERROR: Timeout occurred while waiting for an element.\n");
            logBuilder.append("Exception Details: ").append(e.getMessage()).append("\n");
            logBuilder.append("-----------------------------------------------------------\n");
        } catch (Exception e) {
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("ERROR: An unexpected error occurred.\n");
            logBuilder.append("Exception Details: ").append(e.getMessage()).append("\n");
            logBuilder.append("-----------------------------------------------------------\n");
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Execution End    : ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            logBuilder.append("Total Duration   : ").append(Duration.between(startTime, endTime).getSeconds()).append(" seconds\n");
            logBuilder.append("Test Result      : FAILED ❌\n");
            logBuilder.append("-----------------------------------------------------------\n");
            writeLogToFile(logBuilder.toString());
            driver.quit();
        }
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

