package cencosud.business;

import io.qameta.allure.Step;
import cencosud.pages.App.EstadoPickingPage;
import org.openqa.selenium.WebElement;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.DriverFactory;
import cencosud.pages.App.*;

import java.io.IOException;
import java.util.List;

public class appNegocio extends BaseConfig {

    public LoginPage loginPage;
    public PrincipalPage principalPage;
    public ClientePage clientePage;
    public PickingPage pickingPage;
    public EstadoPickingPage estadoPickingPage;
    public EstadoPickingPage estadodePicking;
    public SelectProductPage selectProductPage;
    public ValidaPage validaPage;
    public EmbolsarPage embolsarPage;
    public FinalizarPage finalizarPage;


    public void iniciarApp() throws Exception {

        printf( ANSI_WHITE+"Inicializando Aplicacion Movie App..");
        try {
            driverApp = DriverFactory.getAppiumDriver();

        }
        catch (Exception e) {
            printf(ANSI_RED + "ERROR al iniciar aplicacion " + e.getMessage());
            throw new Exception(e);
        }

        try {
            // Inicializa objetos POM.
            loginPage = new LoginPage(driverApp);
            principalPage = new PrincipalPage(driverApp);
            clientePage = new ClientePage(driverApp);
            pickingPage = new PickingPage(driverApp);
            estadoPickingPage = new EstadoPickingPage(driverApp);
            selectProductPage = new SelectProductPage(driverApp);
            validaPage = new ValidaPage(driverApp);
            embolsarPage = new EmbolsarPage(driverApp);
            finalizarPage = new FinalizarPage(driverApp);
            screenShotAllureMobile(" Inicio App");

        } catch (Exception e){
                println( ANSI_RED+"ERROR: Al iniciar App  "+e.getMessage());
                fail(ANSI_RED+"ERROR: Al iniciar App . "+e.getMessage());
            }
    }

    /***
     * Login en App
     */

    @Step (" Login en Aplicación ")
    public void iniciarSesiónApp(String strUser, String strPass) throws IOException, InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+" Login en App JumboShopper ");
        assertTrue(loginPage.Login(strUser, strPass), " Error al iniciar sesión ");
        assertTrue(loginPage.swipeProducto(), "Problemas al realizar scroll en App");
        //screenShotAllureMobile(" Inicio Sesión App ");
    }

    /***
     * Procesar orden en App
     * @throws InterruptedException
     */

    @Step (" Login y tomar orden de BO ")
    public void inicioOrdenApp (String strTienda) throws IOException, InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Procesar orden en App");
        assertTrue(principalPage.validarTienda(strTienda), "No se esta en la tienda correcta");
        assertTrue(principalPage.disponibleShopper(),"Error al disponibilizar shopper");
        assertTrue(principalPage.comenzarProceso(),"No se puede comenzar con el peido");
    }

    @Step (" Proceso de orden ")
    public void recorridoPedido (/*String strOrden*/ String strTienda){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Recorrido de pedido");
        assertTrue(clientePage.validaOrden(/*strOrden*/ strTienda), "Error en orden o tienda pedido");
        assertTrue(clientePage.continuarOrden(), "Error al continuar con la orden");

    }

     /***
     * Deshabilitar Shopper
     */
    
    @Step (" Deshabilitar Shopper")
    public void desHabilitarShopper (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Dejar el shopper deshabilitado");
        assertTrue(principalPage.noDisponibleShopper(), "Error al deshabilitar");
        assertTrue(principalPage.confirmarDeshabilitar(), "No se pudo deshabilitar shopper");
    }

    /***
     * Pickear Lista de productos
     * Jumbo
     */

    @Step (" Pickear productos Sin - Sala ")
    public void pickerProductosSala () throws IOException {

        /***
         * Seleccionar productos
         * en Sala sin Ubicación
         */

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Picker productos en SALA");

        if(selectProductPage.validaSala()) {
            pickearProductosLista();
            if (selectProductPage.validaSustituto()){
                selectProductPage.agregarSutituto();
            }else {
                BaseUtils.println(BaseUtils.ANSI_YELLOW+"Continua flujo");
                selectProductPage.ConfirmarPickeo();
            }
            //selectProductPage.ConfirmarPickeo();

        } else {
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"No hay/existe productos en SALA" +
                    " - Continua flujo.");
        }

    }

    
    @Step (" Sustitución producto ")
    public void sustitución (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Producto ingresado sustituto");

    }

    @Step (" Pickear productos - Estantes ")
    public void pickerProductosEstantes () throws IOException {
        /***
         * Seleccionar productos
         * en Sala Estantes
         */

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Picker productos en Estante");

        if ( ! selectProductPage.validaEstantes() ) {
            selectProductPage.SeccionEstantes();

        }
        if  ( selectProductPage.validaEstantes() ) {
            System.out.println("---Estantes---");
            pickearProductosLista();
        } else {
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"No hay/existe productos en Estantes" +
                    " - Continua flujo.");
            
            
        }
    }

    /***
     * Validación con cliente
     * Jumbo
     */

    @Step (" Validar con cliente ")
    public void validarConCliente (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Validación de pedido con cliente");
        assertTrue(selectProductPage.validarSinProductos(),"Error al validar no tener producto");
        //productosPorValidar();
        assertTrue(selectProductPage.validarCliente(), "Problemas al seleccionar validar cliente");

        if (selectProductPage.productosValidar()){
            selectProductPage.confirmarSustituto();
        }

        //productosPorValidar();
        assertTrue(validaPage.finalizarValidacion(), "Problemas al finalizar validación");
    }

    @Step (" Productos por validar")
    public void productosPorValidar (){
        if (selectProductPage.productosValida()){
            selectProductPage.selectProducto();
            selectProductPage.confirmarValidacion();
        }
    }

    /***
     * Finalizar pedido
     */

    @Step (" Finalizar pedido ")
    public void finalizarPedido () {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Validación de pedido con cliente");
        assertTrue(finalizarPage.finalizarPedido(), "Problemas al final el pedido");
    }

    /***
     * Seleccion productos
     * Lista
     */

    @Step (" Seleccionar productos de la lista - categoría ")
    public void pickearProductosLista () throws IOException {

        List<WebElement> listProductos = selectProductPage.obtenerListaProductos();
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Tamaño de la lista en Sala : " + listProductos.size());
        for (WebElement el : listProductos) {
            int numProd = listProductos.indexOf(el) + 1;
            int pos = 1;
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Posicición de Producto : " + numProd);
            assertTrue(selectProductPage.SelectProducto(pos), "No se puede selecionar producto en posicion:"
                    + numProd);
            selectProductPage.SeleccionarPickear();
            assertTrue(selectProductPage.InputEan(), "Problemas al ingresar EAN del producto");
            //assertTrue(selectProductPage.ConfirmarPickeo(), "Problemas al confirmar pedido");

        }
    }

    /***
     * Embolsado producto
     */
    
    @Step (" Productos sin Embolsar ")
    public void sinEmbolsado () {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Lista de productos sin Embolsado");
        List<WebElement> listProductos = embolsarPage.obtenerListaProductos();
        for (WebElement el : listProductos){
            int pos = listProductos.indexOf(el) + 1;
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Posicición de Producto : " + pos);
            assertTrue(embolsarPage.selecionarSuelto(pos, "ON"), "No se puede selecionar producto en posicion: "
                        + pos);
        }
        assertTrue(embolsarPage.clickBotonEmbolsar(), "Problemas al embolsar - boton");
        assertTrue(embolsarPage.cantidadBultos(), " Problemas en cantidad de bultos");
        assertTrue(embolsarPage.resumenBultos(), "Error en resumen de bultos");
    }


    /***
     * Pickear orden en App
     */

    @Step (" Pickear producto en App por SKU o EAN ")
    public void pickingProducto () throws InterruptedException, IOException {
        List<WebElement> listCantElementos = estadoPickingPage.obtenerListaProductos();
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Tamaño de la lista : " + listCantElementos.size());

        for (WebElement el : listCantElementos){
            int pos = listCantElementos.indexOf(el) + 1;
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"Posicición de Producto : " + pos);
            assertTrue(estadoPickingPage.seleccionarProductoSku(pos), "No se peude seleccionar producto");
            //screenShotAllureMobile(" Detalle producto ");
            estadoPickingPage.capturaDataProducto(pos);
            assertTrue(estadoPickingPage.inputPickeo("sku"), "No se pudo ingresar datos");
            assertTrue(estadoPickingPage.cantidadPickear(pos), "Error en validacion de cantidad");
        }

        assertTrue(estadoPickingPage.validacionEstado("Estado Jaula"),"Incorrecto estado subOrden");
       // screenShotAllureMobile(" Estado de Jaula");
    }

    @Step (" Pickear dos productos en App productos " +
            "1.- Ingreso por SKU " +
            "2.- Ingreso por EAN ")
    public void pickingProductosDistintos () throws InterruptedException, IOException {
        List<WebElement> listCantElementos = estadoPickingPage.obtenerListaProductos();
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Tamaño de la lista : " + listCantElementos.size());

        for (WebElement el : listCantElementos){
            int pos = listCantElementos.indexOf(el) + 1;
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"Posicición de Producto : " + pos);
            assertTrue(estadoPickingPage.seleccionarProductoSku(pos), "No se peude seleccionar producto");
            screenShotAllureMobile(" Detalle producto ");
            estadoPickingPage.capturaDataProducto(pos);

            if (pos == 2){
                assertTrue(estadoPickingPage.inputPickeo("ean"), "No se pudo ingresar datos");
                screenShotAllureMobile(" Input EAN");

            }else {
                assertTrue(estadoPickingPage.inputPickeo("sku"), "No se pudo ingresar datos");
                screenShotAllureMobile(" Input SKU");
            }
            assertTrue(estadoPickingPage.cantidadPickear(pos), "Error en validacion de cantidad");
        }
        assertTrue(estadoPickingPage.validacionEstado("Estado Jaula"),"Incorrecto estado subOrden");
        //screenShotAllureMobile(" Estado de Jaula");
    }


    /***
     * Para ordenes RT ingresa ubicación
     */

    @Step (" Ingresar datos ubicación de Suborden en RT ")
    public void ubicacionSubordenRT (){
        assertTrue(estadoPickingPage.ubicacionSubOrden(),"No carga opciones de ubicación");
        assertTrue(estadoPickingPage.inputDatosUbicacion(), "Problemas al ingresar ubicación");
    }

    /***
     * Asignar bultos al producto
     */

    @Step (" Asignar bultos a los productos ")
    public void asignarBultos (String cant) throws InterruptedException, IOException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Asignar bultos al producto");
        estadoPickingPage.swipeProducto();
        assertTrue(estadoPickingPage.swipeProducto(), "No se hace correctamente Swipe en Producto");
        assertTrue(estadoPickingPage.clickBultos(), "Problemas en click");
        assertTrue(estadoPickingPage.ingresaBultos(cant), "Problemas al ingresar cantidad");
        //screenShotAllureMobile(" Agregar Bultos ");

    }

    /***
     * Cerrar orden
     */

    @Step (" Cerrar orden ")
    public void cerrarOrdenApp () throws IOException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Cerrar orden");
        screenShotAllureMobile(" Estado finalizado ");
        assertTrue(estadoPickingPage.cerrarOrden(), "Problemas al cerrar orden");
        principalPage.sincronización();
        //screenShotAllureMobile(" Principal Page");
    }


    @Step ("")
    public void reniciarOrdenApp (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Reniciar orden en app");
        principalPage.reniciarApp();
    }


}
