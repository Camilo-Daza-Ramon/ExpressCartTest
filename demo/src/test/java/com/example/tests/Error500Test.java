package com.example.tests;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Error500Test {

    private static final String DRIVER_PATH = "C:\\Users\\CAMILO DAZA\\Desktop\\serverdrivers_selenium\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String BASE_URL = "http://127.0.0.1:1111";
    private static final String NAVBAR_BRAND_SELECTOR = ".navbar-brand"; 

    @Test
    public void handleServerError() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {

            driver.get(BASE_URL + "/cause-server-error");

            Thread.sleep(4000); 

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement navbarBrand = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(NAVBAR_BRAND_SELECTOR)));
            navbarBrand.click();

            Thread.sleep(4000); 

            driver.get(BASE_URL + "/cause-server-error");

            Thread.sleep(4000); 

        } catch (InterruptedException e) {
            System.out.println("La espera fue interrumpida: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}