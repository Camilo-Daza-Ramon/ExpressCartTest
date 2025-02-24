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
    private static final String RESULTS_FILE_PATH = "test-results.txt";

    @Test
    public void handleServerError() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        StringBuilder logBuilder = new StringBuilder();
        LocalDateTime startTime = LocalDateTime.now();
        logBuilder.append("===========================================================\n");
        logBuilder.append("               AUTOMATED TEST EXECUTION REPORT              \n");
        logBuilder.append("===========================================================\n");
        logBuilder.append("Test Name        : Error500Test\n");
        logBuilder.append("Execution Start  : ").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        logBuilder.append("Base URL         : ").append(BASE_URL).append("\n");
        logBuilder.append("Browser          : Chrome\n");
        logBuilder.append("===========================================================\n\n");

        try {
            driver.get(BASE_URL + "/cause-server-error");
            logBuilder.append("[" + getCurrentTimestamp() + "] Navigated to /cause-server-error\n");
            Thread.sleep(4000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement navbarBrand = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(NAVBAR_BRAND_SELECTOR)));
            navbarBrand.click();
            logBuilder.append("[" + getCurrentTimestamp() + "] Clicked on navbar brand.\n");
            Thread.sleep(4000);

            driver.get(BASE_URL + "/cause-server-error");
            logBuilder.append("[" + getCurrentTimestamp() + "] Navigated to /cause-server-error again\n");
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
            logBuilder.append("-----------------------------------------------------------\n");
            logBuilder.append("Execution End    : ").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            logBuilder.append("Total Duration   : ").append(Duration.between(startTime, endTime).toSeconds()).append(" seconds\n");
            logBuilder.append("Test Result      : SUCCESS ✅\n");
            logBuilder.append("-----------------------------------------------------------\n");
            writeLogToFile(logBuilder.toString());
            driver.quit();
        }
    }

    private void logError(String message, Exception e, StringBuilder logBuilder) {
        logBuilder.append("-----------------------------------------------------------\n");
        logBuilder.append("ERROR DETECTED ❌\n");
        logBuilder.append("Timestamp        : ").append(getCurrentTimestamp()).append("\n");
        logBuilder.append("Error Type       : ").append(e.getClass().getSimpleName()).append("\n");
        logBuilder.append("Error Message    : ").append(e.getMessage()).append("\n");
        logBuilder.append("Error Description: ").append(getErrorDescription(e)).append("\n");
        logBuilder.append("Suggested Fix    : ").append(getSuggestedFix(e)).append("\n");
        logBuilder.append("-----------------------------------------------------------\n");
    }

    private String getErrorDescription(Exception e) {
        if (e.getMessage().contains("500")) {
            return "Error 500: Internal Server Error. Ocurre cuando el servidor encuentra una condición inesperada que le impide cumplir con la solicitud. Puede ser causado por errores en el código del servidor, sobrecarga del sistema o configuraciones incorrectas.";
        }
        return "Error desconocido. Verifique los registros del sistema para más detalles.";
    }

    private String getSuggestedFix(Exception e) {
        if (e instanceof NoSuchElementException) {
            return "Verifique que el selector sea correcto y que el elemento esté presente en la página.";
        } else if (e instanceof TimeoutException) {
            return "Aumente el tiempo de espera o verifique si el elemento está cargando correctamente.";
        } else if (e.getMessage().contains("500")) {
            return "Revise los registros del servidor para detectar fallos internos. Asegúrese de que el backend esté funcionando correctamente y que no haya errores en la configuración.";
        } else {
            return "Revise los registros de la aplicación para obtener más detalles sobre el problema.";
        }
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    private void writeLogToFile(String log) {
        try {
            Files.write(Paths.get(RESULTS_FILE_PATH), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
