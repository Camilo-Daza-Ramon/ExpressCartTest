package com.example.tests;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private static final String ADD_TO_CART_INITIAL_SELECTOR = ".btn.btn-primary.add-to-cart";
    private static final String ADD_TO_CART_SELECTOR = ".btn.btn-primary.btn-block.product-add-to-cart";
    private static final String PRODUCT_QUANTITY_SELECTOR = "#product_quantity";
    private static final int MAX_QUANTITY = 10;  // Ajusta este valor según lo configurado en settings.json

    @Test
    public void verifyMaxQuantityLimit() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(BASE_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Incrementa el tiempo de espera

            // 1. Esperar 7 segundos para que cargue la página
            Thread.sleep(600);

            // 2. Hacer clic en el botón "Add to cart" inicial
            WebElement initialAddToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_INITIAL_SELECTOR)));
            initialAddToCartButton.click();

            // 3. Esperar 5 segundos para que cargue la página del producto
            Thread.sleep(5000);

            // 4. Subir la cantidad de productos de uno a diez
            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_QUANTITY_SELECTOR)));
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(MAX_QUANTITY));
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADD_TO_CART_SELECTOR)));
            addToCartButton.click();

            // Esperar un momento antes de continuar
            Thread.sleep(2000);

            // Intentar ajustar la cantidad a 15 hasta que se logre
            for (int attempt = 0; attempt < 5; attempt++) {  // Intentar hasta 5 veces
                quantityInput.clear();
                quantityInput.sendKeys("15");
                
                // Confirmar que la cantidad se ha ajustado a 15
                String currentQuantity = quantityInput.getAttribute("value");
                System.out.println("Cantidad actual después del intento " + (attempt + 1) + ": " + currentQuantity);
                if (currentQuantity.equals("15")) {
                    break;  // Salir del bucle si se ha ajustado correctamente
                }
                
                Thread.sleep(1000); // Esperar un segundo antes de reintentar
            }

            // Confirmar finalmente que la cantidad es 15
            String finalQuantity = quantityInput.getAttribute("value");
            assertTrue(finalQuantity.equals("15"), "La cantidad debe ser 15. Valor actual: " + finalQuantity);

            // Esperar un momento antes de intentar añadir más productos
            Thread.sleep(2000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}











