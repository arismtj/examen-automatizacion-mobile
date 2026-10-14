
package com.nttdata.support;

import com.nttdata.steps.AppConfigSteps;
import io.cucumber.java.Scenario;

import java.nio.charset.StandardCharsets;

public final class ScreenshotAttacher {

    private static final AppConfigSteps appConfigSteps =
            new AppConfigSteps();

    private ScreenshotAttacher() {
    }

    public static void attach(Scenario scenario, String nombre) {

        try {

            byte[] screenshot = appConfigSteps.tomarScreenshot();

            if (screenshot != null && screenshot.length > 0) {

                scenario.attach(
                        screenshot,
                        "image/png",
                        nombre
                );

                System.out.println(
                        "Evidencia capturada: " + nombre
                );

            } else {

                registrarSinCaptura(scenario, nombre);
            }

        } catch (Exception e) {

            System.out.println(
                    "No se pudo capturar evidencia: " +
                            e.getMessage()
            );

            registrarSinCaptura(scenario, nombre);
        }
    }

    private static void registrarSinCaptura(
            Scenario scenario,
            String nombre) {

        scenario.attach(
                ("Captura no disponible: " + nombre)
                        .getBytes(StandardCharsets.UTF_8),
                "text/plain",
                "Incidencia de captura"
        );
    }
}
