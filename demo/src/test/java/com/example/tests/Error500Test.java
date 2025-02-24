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

public class Error500Test {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String NAVBAR_BRAND_SELECTOR = ".navbar-brand"; 
    private static final String RESULTS_FILE_PATH = "test-results.txt"; // Archivo de resultados

    @Test
    public void handleServerError() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        StringBuilder logBuilder = new StringBuilder();
        LocalDateTime startTime = LocalDateTime.now();
        logBuilder.append("\n\n");
        logBuilder.append("Test Name: Error500Test\n");
        logBuilder.append("Test Start Time: ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");

        try {
            driver.get(BASE_URL + "/cause-server-error");
            logBuilder.append("Navigated to /cause-server-error\n");

            Thread.sleep(4000); 

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement navbarBrand = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(NAVBAR_BRAND_SELECTOR)));
            navbarBrand.click();
            logBuilder.append("Clicked on navbar brand.\n");

            Thread.sleep(4000); 

            driver.get(BASE_URL + "/cause-server-error");
            logBuilder.append("Navigated to /cause-server-error again\n");

            Thread.sleep(4000); 

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
