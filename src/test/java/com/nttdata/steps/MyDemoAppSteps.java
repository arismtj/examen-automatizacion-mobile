package com.nttdata.steps;

import com.nttdata.screens.MyDemoAppScreen;

public class MyDemoAppSteps {

    private final MyDemoAppScreen screen =
            new MyDemoAppScreen();


    public boolean productosCargaronCorrectamente() {

        return screen.galeriaVisible();
    }


    public void agregarProducto(
            String producto,
            int unidades) {

        screen.seleccionarProducto(producto);

        screen.configurarCantidad(unidades);

        screen.agregarAlCarrito();
    }


    public int obtenerCantidadCarrito() {

        return screen.obtenerCantidadCarrito();
    }
}