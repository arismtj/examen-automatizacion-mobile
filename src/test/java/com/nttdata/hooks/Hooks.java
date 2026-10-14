
package com.nttdata.hooks;

import com.nttdata.screens.AppConfigScreen;
import com.nttdata.support.ScreenshotAttacher;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void antesDelEscenario(Scenario scenario) {
        System.out.println(
                "INICIANDO: " + scenario.getName()
        );
    }

    @After
    public void despuesDelEscenario(Scenario scenario) {

        String estado = scenario.isFailed()
                ? "FALLIDO"
                : "EXITOSO";

        System.out.println(
                "RESULTADO: " + estado
        );

        try {
            ScreenshotAttacher.attach(
                    scenario,
                    "Evidencia final del escenario"
            );
        } catch (Exception e) {
            System.out.println(
                    "Captura no disponible: " + e.getMessage()
            );
        }

        if (scenario.getSourceTagNames()
                .contains("@MyDemoApp")) {

            try {
                new AppConfigScreen().cerrarAplicacion(
                        "com.saucelabs.mydemoapp.android"
                );
            } catch (Exception e) {
                System.out.println(
                        "Error al cerrar sesión: " + e.getMessage()
                );
            }
        }
    }
}
