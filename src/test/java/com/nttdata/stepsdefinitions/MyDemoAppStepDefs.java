
package com.nttdata.stepsdefinitions;

import com.nttdata.steps.MyDemoAppSteps;
import com.nttdata.support.ScreenshotAttacher;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyDemoAppStepDefs {

    private final MyDemoAppSteps steps =
            new MyDemoAppSteps();

    private Scenario scenario;
    private int unidadesEsperadas;
    private String productoActual;

    @Before
    public void configurarScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("estoy en la aplicación de SauceLabs")
    public void iniciarValidacion() {

        scenario.log("Aplicación Sauce Labs iniciada");
    }

    @And("valido que carguen correctamente los productos en la galeria")
    public void validarGaleria() {

        boolean galeriaDisponible =
                steps.productosCargaronCorrectamente();

        ScreenshotAttacher.attach(
                scenario,
                "CP - Galeria de productos"
        );

        assertTrue(
                "La galeria no cargo correctamente",
                galeriaDisponible
        );
    }

    @When("agrego {int} del siguiente producto {string}")
    public void agregarProducto(
            int unidades,
            String producto) {

        unidadesEsperadas = unidades;
        productoActual = producto;

        scenario.log(
                "Producto: " + producto +
                        " | Cantidad solicitada: " + unidades
        );

        // Evidencia antes de seleccionar el producto.
        ScreenshotAttacher.attach(
                scenario,
                "Galeria antes de seleccionar " + producto
        );

        try {

            steps.agregarProducto(producto, unidades);

            ScreenshotAttacher.attach(
                    scenario,
                    "Producto agregado - " + producto
            );

        } catch (RuntimeException | AssertionError e) {

            scenario.log(
                    "No se completo la seleccion o agregado: " +
                            producto
            );

            scenario.log(
                    "Detalle del error: " + e.getMessage()
            );

            ScreenshotAttacher.attach(
                    scenario,
                    "FALLO - " + producto
            );

            throw e;
        }
    }

    @Then("valido el carrito de compra actualice correctamente")
    public void validarCarrito() {

        int cantidadActual;

        try {

            cantidadActual = steps.obtenerCantidadCarrito();

        } catch (RuntimeException | AssertionError e) {

            ScreenshotAttacher.attach(
                    scenario,
                    "FALLO - No se pudo consultar carrito"
            );

            scenario.log(
                    "Error consultando el carrito: " +
                            e.getMessage()
            );

            throw e;
        }

        scenario.log(
                "Producto: " + productoActual
        );

        scenario.log(
                "Cantidad esperada: " + unidadesEsperadas
        );

        scenario.log(
                "Cantidad obtenida: " + cantidadActual
        );

        // CAPTURAMOS ANTES DE LA ASERCION.
        ScreenshotAttacher.attach(
                scenario,
                "Validacion carrito - " + productoActual +
                        " - Esperado " + unidadesEsperadas +
                        " - Actual " + cantidadActual
        );

        assertEquals(
                "El carrito no contiene la cantidad esperada",
                unidadesEsperadas,
                cantidadActual
        );

        scenario.log(
                "RESULTADO: CARRITO VALIDADO CORRECTAMENTE"
        );
    }
}
