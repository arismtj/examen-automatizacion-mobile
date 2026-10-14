# language: es

Característica: Validación del carrito de compras

  Como usuario de la tienda
  Quiero agregar productos al carrito
  Para validar que la cantidad seleccionada se actualice correctamente

  @MyDemoApp @Smoke
  Esquema del escenario: Validar actualización del carrito de compras

    Dado ingreso al aplicativo "mydemoapp"
    Y estoy en la aplicación de SauceLabs
    Y valido que carguen correctamente los productos en la galeria
    Cuando agrego <UNIDADES> del siguiente producto "<PRODUCTO>"
    Entonces valido el carrito de compra actualice correctamente
    Y cierro el aplicativo "com.saucelabs.mydemoapp.android"

    Ejemplos:
      | PRODUCTO                   | UNIDADES |
      | Sauce Labs Backpack        | 1        |
      | Sauce Labs Bolt - T-Shirt  | 1        |
      | Sauce Labs Bike Light      | 2        |