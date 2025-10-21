package cencosud.business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.*;

import io.qameta.allure.Step;
import org.testng.Assert;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.DriverFactory;
import cencosud.pages.BackOffice.*;


public class backOfficeNegocio extends BaseConfig {

    
    public String rut = "";
    public String boleat ="";

    public HomePage homePage;
    public MonitorPage monitorPage;
    public OrdenesPage ordenesPage;
    public AsignarPage asignarPage;
    public PedidoPage pedidoPage;


    /***
     * Inicializa selenium y objetos de BackOffice.
     * @throws Exception
     */

    public void inicioBackoffice () throws Exception{
        printf( ANSI_WHITE+"Inicializando BackOffice Jumbo..");
        try {

            driver = DriverFactory.getChromeDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, Object> vars = new HashMap<String, Object>();
            SetTimeouts(60);
            driver.get("https://cl-backoffice-pub-qa.smdigital.cl/");


        }
        catch (Exception e) {
            printf(ANSI_RED+"ERROR al acceder a https://cl-backoffice-pub-qa.smdigital.cl/ Stack: " + e.getMessage());
            CloseWebDriver();
            throw(e);
        }
        // Inicializa objetos POM.
        homePage = new HomePage(driver);
        monitorPage = new MonitorPage(driver);
        ordenesPage = new OrdenesPage(driver);
        asignarPage = new AsignarPage(driver);
        pedidoPage  = new PedidoPage(driver);
        println( ANSI_WHITE+"...correcto.");
    }

    /***
     * Login en monitor Jumbo
     */


    @Step("Inicia sesión en BackOffice Jumbo")
    public void inicioSesión(String strUser, String strPass) throws InterruptedException, FileNotFoundException {
        assertTrue(homePage.validaHome(), "Carga incorrecto Home");
        assertTrue(homePage.seleccionarBacnOffice(),"Incorrecto al seleccionar BackOffice");
        assertTrue(homePage.Login(strUser, strPass), "Login incorrecto en BackOffice");
    }

    @Step (" Ir a seccion ver ordenes ")
    public void irPedidos(){
        assertTrue(monitorPage.pedidos(),"Carga incorrecto de pedidos");
    }

    /***
     * Buscar con un máximo de intentos
     */

    @Step ("Buscar orden")
    public void buscarOrden (String strOrden) throws InterruptedException, IOException {
        int intentos = 0;
        int maxIntentos= 10;


        while (intentos < maxIntentos){
            if (ordenesPage.buscarOrden( strOrden )){
                screenShotAllure("Numero Orden encontrada");
             break;
            }else {
                intentos ++;
                Thread.sleep(15000);
                driver.navigate().refresh();
            }
            if(intentos>=20){
                BaseUtils.println(BaseUtils.ANSI_YELLOW+"Maximo de intentos");
                Assert.fail("Maximo de intentos, no se encuentra orden");
                break;
            }
        }
    }

    /***
     * Asignar orden a Picker
    ***/

    @Step("Asignar orden creada a Picker desde busqueda de orden")
    public void asignarOrdenDeBusqueda ( String order, String mailPicker, String nomPicker) throws IOException, InterruptedException {

        assertTrue(ordenesPage.seleccionarAsignar( order ),"No se ha podido seleccionar asignación");
        assertTrue(ordenesPage.inputPicker( mailPicker), "No encuentra picker");
        assertTrue(ordenesPage.seleccionarPicker(mailPicker, nomPicker), "No se puede asignar picker");

    }

    

    @Step ("Validación estado")
    public boolean validaciónEstadoOrden (String estado, String so) throws IOException, InterruptedException {
        int intentos = 0;
        int maxIntentos= 5;

        while (intentos < maxIntentos) {
            if (ordenesPage.validacionEstado(estado)) {
                screenShotAllure("Validación correcta estado");
                break;
            }else {
                System.out.println( "Numero de intentos : " + intentos);
                intentos ++;
                //driver.navigate().refresh();
                ordenesPage.buscarOrden( so );
                Thread.sleep(10000);
            }
            if(intentos>=10){
                BaseUtils.println(BaseUtils.ANSI_YELLOW+"Maximo de intentos");
                Assert.fail("Maximo de intentos, no cambia estado orden");
                break;
            }
        }
        return true;

    }

    /***
     * Ir a pedido desde la busqueda
     */

    @Step ("ir a pedido buscado en monitor")
    public void irApedidoBuscado (String strorden){
        assertTrue( ordenesPage.irApedido( strorden ), "No se pudo ir al pedido");
    }

    /***
     * Ir a pedido desde la busqueda
     */

    @Step ("Asignar pedido a picker en pagina del pedido")
    public void asignarOrdenEnPedido (String strOrder, String strMailPicker, String strNombrePicker) {
        assertTrue(pedidoPage.seleccionarAsignarEnPedido(strOrder), " No carga correctamente opción de asignación ");
        assertTrue(pedidoPage.inputPickerPedido( strMailPicker ), " No se encuentra picker buscado ");
        assertTrue(pedidoPage.seleccionarPickerPedido(strMailPicker,strNombrePicker), " No se asigno la orden");
    
    }

    /***
     * Reniciar pedido en pantalla de la orden
     */

    @Step("Reniciar orden en pedido")
    public void reniciarOrdenEnPedido (){

        pedidoPage.cerrarVentana();
        
        if(pedidoPage.validaAsignacionPedido()) {

          assertTrue(pedidoPage.seleccionarOpciones(), "Problemas al seleccionar opciones");
          assertTrue(pedidoPage.reniciarPedido(), "No esta habilitado boton reniciar");
          assertTrue(pedidoPage.confirmarRenicio(), "No se puede reniciar pedido");

      }
    }

    /***
     * Reniciar pedido en pantalla de la orden
     */
    @Step("Reniciar orden en pedido")
    public void renicioSiFallo (){

        String estado = pedidoPage.capturaEstadoOrden();
    }

    /***
     * Retorna estado orden BO
     */

    @Step("Retorna estado BO")
    public String estadoOrden (){
        String estado = pedidoPage.capturaEstadoOrden();
        return estado;
    }

    @Step("Boolean estado orden")
    public boolean existeEstado(){

        if (!pedidoPage.booleanEstado()){

            System.out.println("No estamos en pagina de ordenes");
            
            return false;
        }

        System.out.println("Existe estado - Continua flujo");
        return true;
    }

    /***
     * rescatar rut
     */

    @Step("Retorna estado BO")
    public String rutCliente(){
        String estado = pedidoPage.capturaEstadoOrden();
        return estado;
    }

    @Step(" Refresh pantalla")
    public void refreshPantalla (){
        System.out.println("F5");
        driver.navigate().refresh();
    }

    @Step("Retorna estado BO")
    public void actualizarDatosBo (String  compraId, String estado, String rut, String boleta) throws IOException{
        estado = pedidoPage.capturaEstadoOrden();
        System.out.println("Estado de la orden : " + estado);
        driver.navigate().refresh();
        pedidoPage.updateOrdenAndSaveToJson (compraId, estado, rut, boleta);

    }

    @Step(" Valida si esta facturada ")
    public boolean validarSiFacturado (){
        if (pedidoPage.validataFacturacion()){

            return true;
            
        }
        return false;
    }
    

    @Step("Validar boleta")
    public void validarBoleta (String  compraId) throws InterruptedException, IOException{
        pedidoPage.ConsultarBoleta(compraId); 
    }

    @Step("Captura final datos")
    public void actualizaciónFinal (String  compraId, String estado, String rut, String tipo, String boleta) throws IOException{
        driver.navigate().refresh();
        pedidoPage.updateOrdenAndSaveToJson (compraId, estado, rut, boleta);
    }
}
