
package com.nttdata.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyDemoAppScreen {

    private static final String PKG =
            "com.saucelabs.mydemoapp.android:id/";

    private final Duration timeout = Duration.ofSeconds(20);

    private final By galeria =
            AppiumBy.id(PKG + "productRV");

    private final By cantidad =
            AppiumBy.id(PKG + "noTV");

    private final By botonMas =
            AppiumBy.id(PKG + "plusIV");

    private final By botonAgregar =
            AppiumBy.id(PKG + "cartBt");

    private final By botonCarrito =
            AppiumBy.id(PKG + "cartRL");

    private String productoSeleccionado;

    private AndroidDriver driver() {
        return AppConfigScreen.getDriver();
    }

    private WebDriverWait waitDriver() {
        return new WebDriverWait(driver(), timeout);
    }

    public boolean galeriaVisible() {
        return waitDriver().until(
                ExpectedConditions.visibilityOfElementLocated(galeria)
        ).isDisplayed();
    }

    public void seleccionarProducto(String producto) {

        // Normalizamos el nombre del caso de prueba
        productoSeleccionado = producto.replace(
                "Bolt - T-Shirt", "Bolt T-Shirt"
        );

        String tituloXPath =
                "//*[@resource-id='" + PKG + "titleTV' " +
                        "and @text='" + productoSeleccionado + "']";

        By titulo = AppiumBy.xpath(tituloXPath);

        if (driver().findElements(titulo).isEmpty()) {

            driver().findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector()" +
                                    ".scrollable(true)).scrollIntoView(" +
                                    "new UiSelector().text(\"" +
                                    productoSeleccionado + "\"))"
                    )
            );
        }

        // La imagen, no el texto, abre el producto.
        By imagen = AppiumBy.xpath(
                tituloXPath +
                        "/../*[@resource-id='" + PKG + "productIV']"
        );

        waitDriver().until(
                ExpectedConditions.elementToBeClickable(imagen)
        ).click();

        try {
            waitDriver().until(
                    ExpectedConditions.visibilityOfElementLocated(cantidad)
            );
        } catch (TimeoutException e) {
            throw new AssertionError(
                    "No se abrió el detalle del producto: " +
                            productoSeleccionado +
                            ". Revisar posible fallo de la aplicación.",
                    e
            );
        }

        System.out.println(
                "Detalle abierto: " + productoSeleccionado
        );
    }

    public void configurarCantidad(int unidades) {

        if (unidades < 1) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }

        int actual = Integer.parseInt(
                waitDriver().until(
                        ExpectedConditions.visibilityOfElementLocated(cantidad)
                ).getText().trim()
        );

        while (actual < unidades) {

            waitDriver().until(
                    ExpectedConditions.elementToBeClickable(botonMas)
            ).click();

            actual++;

            waitDriver().until(
                    ExpectedConditions.textToBe(
                            cantidad, String.valueOf(actual)
                    )
            );
        }

        if (actual != unidades) {
            throw new AssertionError(
                    "Cantidad incorrecta. Esperada: " +
                            unidades + ", actual: " + actual
            );
        }

        System.out.println("Cantidad configurada: " + actual);
    }

    public void agregarAlCarrito() {

        waitDriver().until(
                ExpectedConditions.elementToBeClickable(botonAgregar)
        ).click();

        System.out.println(
                "Producto enviado al carrito: " + productoSeleccionado
        );
    }

    public int obtenerCantidadCarrito() {

        waitDriver().until(
                ExpectedConditions.elementToBeClickable(botonCarrito)
        ).click();

        By tituloProducto = AppiumBy.xpath(
                "//*[@resource-id='" + PKG + "titleTV' " +
                        "and @text='" + productoSeleccionado + "']"
        );

        waitDriver().until(
                ExpectedConditions.visibilityOfElementLocated(tituloProducto)
        );

        WebElement contador = waitDriver().until(
                ExpectedConditions.visibilityOfElementLocated(cantidad)
        );

        int cantidadActual = Integer.parseInt(
                contador.getText().trim()
        );

        System.out.println(
                "Producto en carrito: " + productoSeleccionado
        );

        System.out.println(
                "Cantidad en carrito: " + cantidadActual
        );

        return cantidadActual;
    }
}
