package cencosud.pages.BackOffice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;
import cencosud.models.DataPedidos;
import cencosud.models.OrdenVtex;
import cencosud.pages.Api.vtexCollection.VtexPage;
import io.qameta.allure.Step;
import cencosud.util.JsonFileUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import cencosud.pages.Api.bo.BeetrackApi;
import java.util.Date;

import java.text.SimpleDateFormat;



public class PedidoPage extends BaseConfig {

    private WebUtils web = null;
    DataPedidos dataPedidos = new DataPedidos();
    
    public String estado = "";
    public String rut = "";
    public String boleta = "";
    private OrdenVtex orden; 
    VtexPage vtexPage = new VtexPage();
    private BeetrackApi beetrackApi;

    public PedidoPage(WebDriver driver) throws FileNotFoundException {
        web = new WebUtils(driver);
        this.beetrackApi = new BeetrackApi();
    }



    // Método para asignar el valor al campo orden.
    public void setOrden(OrdenVtex orden) {
        this.orden = orden;
    }


    /***
     * Seleccionar
     * Opciones en pedido
     */

    private By objBtnOpciones = By.xpath("//button[@type='button']/div[text()='Opciones']");
    private By objWraperOpciones = By.xpath("//*[contains(@class,'ant-drawer-wrapper-body')]/div/div[contains(@class,'title')]/span[text()='Opciones']");

    public boolean seleccionarOpciones (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar Boton Opciones");

        web.WaitForElementVisible( objBtnOpciones );
        web.Click( objBtnOpciones );

        return web.WaitForElementVisible( objWraperOpciones );
    }

    /***
     * Seleccionar
     * Reniciar pedido
     */

    private By objBtnReniciar = By.xpath("//button[@type='button']/div[contains(text(),'Reiniciar Pedido')]");
    private By objTxtReniciar = By.xpath("//*[text()='¿Estas seguro de reiniciar el pedido? Los datos del picking seran eliminados.']");

    public boolean reniciarPedido (){

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar Boton reniciar pedido");

        web.WaitForElementVisible( objBtnReniciar );
        web.Click( objBtnReniciar );

        return web.WaitForElementVisible( objTxtReniciar );
    }

    /***
     * Confirmar
     * renicio pedido
     */

    private By objBtnConfirmar = By.xpath("//button[@type='button']/div[text()='Confirmar']");
    private By objSinAsignar = By.xpath("//*[text()='Sin Asignar']");

    public boolean confirmarRenicio ( ){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Confirmar renicio de pedido");

        web.WaitForElementVisible( objBtnConfirmar );
        web.Click( objBtnConfirmar );

        return web.WaitForElementVisible( objSinAsignar );

    }

    /***
     * Click boton asignar
     */


    private By ObjBtnAsignar = By.xpath("//*[contains(@class,'Truncate') and child::span[text()='Sin Asignar']]//following-sibling::div/button");

    @Step ("Seleccionar boton asignar")
    public boolean seleccionarAsignarEnPedido (String order){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar boton asignar");

        web.WaitForElementVisible( ObjBtnAsignar );
        web.Click( ObjBtnAsignar );

        return web.WaitForElementVisible(
                By.xpath("//*[contains(text(),'Asignar Shopper - Pedido "+order+"')]"));
    }

    /***
     * Input picker
     */

    private By objInputPicker = By.xpath("//input[contains(@placeholder,'Ingrese')]");

    @Step ("Ingresar mail picker")
    public boolean inputPickerPedido (String mailPicker){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Se ingresa ¨Picker");

        web.WaitForElementVisible( objInputPicker );
        web.SendKeys( objInputPicker, mailPicker );
        web.Click( objInputPicker );

        return web.WaitForElementVisible(
                By.xpath("//*[contains(@class,'Text__TextComponent') and contains(text(),'"+mailPicker+"')]"));
    }

    /***
     * Confirmar Asignación
     */

    private By objBtnConfirmarAsignacion = By.xpath("//button[@type='submit']/div[text()='Confirmar']");


    @Step ("Ingresar seleccionar Picker y confirmar")
    public boolean seleccionarPickerPedido(String mailPicker, String nombrePicker){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar Picker y confirmar");

        web.Click(By.xpath("//*[contains(@class,'Text__TextComponent') and contains(text(),'"+mailPicker+"')]"));

        web.WaitForElementVisible( objBtnConfirmarAsignacion );
        web.Click( objBtnConfirmarAsignacion );

        return web.WaitForElementVisible(By.xpath("//*[text()='" + nombrePicker + "']"));
    }

     /***
     * Cerrar ventanta asiganción
     */
    
    
    private By objBtnCerrar = By.xpath("//*[@name='cross']");

     public void cerrarVentana (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Cerrar venta si es que esta ejecutandose"); 

        if(web.WaitForElementVisible(objBtnCerrar, 5)){
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Esta la ventana de asignación abierta, cerrar ...."); 
            web.Click(objBtnCerrar);
        }else {
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "No esta la ventana de asignación abierta, continuar ....");  
        }
            
    }


    /***
     * Validar si orden tiene asignación
     */


    public boolean validaAsignacionPedido (){
       BaseUtils.println(BaseUtils.ANSI_YELLOW+"Valida si el pedido esta asigano");

        //return web.WaitForElementVisible(By.xpath("//*[text()='" + nomPicker + "']"), 10);
       if(web.WaitForElementVisible(ObjBtnAsignar, 10))
       {
         
        return false;

       }

       return true;
    }

    /***
     * Capturar datos de la orden
    */

    
    private By objTxtEstadoOrden = By.xpath("//span[text()='Pedido']/following-sibling::div[1]/span[2]");

    public String capturaEstadoOrden (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Rescata estado de la orden");

        web.WaitForElementVisible(objTxtEstadoOrden);
        estado = web.GetText(objTxtEstadoOrden);

        return estado;
    }

    public boolean booleanEstado (){

        return web.WaitForElementVisible(objTxtEstadoOrden, 10);
    }

    
    public String capturaRut (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"rescata rut");

        web.WaitForElementVisible(objTextRut);
        rut = web.GetText(objTxtEstadoOrden);

        return rut;
    }


    public boolean validataFacturacion (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+" Espera que el estado es en Facturado");

        

        return web.WaitForElementVisible(By.xpath("//span[text()='Pedido']/following-sibling::div[1]/span[2][text()='Facturado']"), 120);
    }



    /***
     * Capturar datos de la orden
    */

    private By objTextRut = By.xpath("(//span[text()='RUT']/following::span[1])[1]");
    private By obtTextMedioPago = By.xpath("(//span[text()='Medio de pago']/following::span[1])[1]");
    private By objTextMonto = By.xpath("(//span[text()='Monto del pedido']/following::span[1])[1]/span");
    private By objTextTienda = By.xpath("(//div[contains(@class,'CardHeaderContainer')])[4]//div[contains(@class,'BaseTitle')]");
    private By objTextEntrega = By.xpath("((//div[contains(@class,'CardHeaderContainer')])[4]//span[contains(@class,'Subtitle')])[1]");
    private By objTxtNumBoleta = By.xpath("//*[text()='Total Compras']/following-sibling::span[contains(@class,'Subtitle')]");
    private By txtSustituido = By.xpath("//*[contains(text(),'• Sustituido')]");

    public void updateOrdenAndSaveToJson(String compraId, String estado, String rut, String boleta) throws IOException {
        try {
            // Leer las ordenes del archivo `OrdenesDebug.json`
            List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
        
            // Buscar y modificar la orden específica por compraId
            for (OrdenVtex orden : ordenes) {
                System.out.println("compraId a buscar: " + compraId);
                if (orden.getCompraId().equals(compraId)) {
                    System.out.println("Orden original: " + orden);
            
                    // Modificar los datos
                    estado = web.GetText(objTxtEstadoOrden);
                    orden.setRut(web.GetText(objTextRut));
                    orden.setOrdenStatus(estado);
                    
                    if(estado.equals("Facturado")){
                        
                        try {

                            orden.setBoleta(web.GetText(objTxtNumBoleta).split("\\|")[1].trim());
                            
                            if(web.WaitForElementVisible(txtSustituido, 5)){
                                System.out.println("Es sustituto");
                                orden.setTipoPedido("Sustituto");

                            }else if (web.WaitForElementVisible(By.xpath("//*[text()='2405330020809' or text='2485363068779']"), 5)){
                                System.out.println("Es pesable");
                                orden.setTipoPedido("Pesable");
                        
                            }else{
                                System.out.println("Es normal");
                                orden.setTipoPedido("Normal");
                            }
                        
                         
                        } catch(Exception e){
                            System.out.println("e");
                        }

                    } else {
                        System.out.println("No se actualiza boleta - Estado de la orden :" + estado);
                    }
    
                    System.out.println("Orden modificada: " + orden);
                    break; // Salir del bucle una vez que se ha encontrado y modificado la orden
                }
            }
        
            // Escribir las ordenes modificadas de regreso en `OrdenesDebug.json`
            JsonFileUtils.writeToJsonFile(ordenes, "output/outputFinal/", "OrdenesDebug.json");
                
            // Imprimir el contenido del archivo para asegurarnos de que fue actualizado correctamente
            List<OrdenVtex> updatedOrdenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
            System.out.println("Contenido de OrdenesDebug.json después de escribir: " + updatedOrdenes);
                
        } catch (Exception e) {
            System.out.println("Exception en update " + e);
        }
    }



    public void ConsultarBoleta(String compraId) throws InterruptedException, IOException {
        
        System.out.println("Servicio consulta de boleta y validación");
        boleta = web.GetText(objTxtNumBoleta).split("\\|")[1].trim(); // Reemplaza con el número de boleta real
        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Leer las ordenes del archivo `OrdenesDebug.json`
        List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
        
        int intentosMaximos = 20;
        int contadorIntentos = 0;
        String fechaApi = null;

        while (contadorIntentos < intentosMaximos) {
            fechaApi =  beetrackApi.consultarBoleta(boleta);

            if (fechaApi != null) {
                if (fechaActual.equals(fechaApi)) {
                    Assert.assertEquals(fechaActual, fechaApi, "Las fechas no coinciden");
                        System.out.println("Las fechas si coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                        
                        updateBoletaData(compraId, fechaApi,  "PASS");
                        // orden.setFechaBoleta(fechaApi);
                        // orden.setValidaBoleta("PASS");
                        break; // Las fechas coinciden, sal del bucle
                        
                } else {
                        System.out.println("Las fechas no coinciden o es null. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                        updateBoletaData(compraId, fechaApi,"FAIL");
                        // orden.setFechaBoleta(fechaApi);
                        // orden.setValidaBoleta("FAIL");
                        //Assert.fail("Las fechas no coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                    }
                
            }

            Thread.sleep(5000); // Espera 5 segundos antes de volver a consultar
            contadorIntentos++;
        }

        //Assert.assertNotNull(fechaApi, "La fecha de la boleta es nula después de los intentos máximos");
    }


public void updateBoletaData(String compraId, String fechaBoleta, String validaBoleta) throws IOException {
    try {
        // Leer las ordenes del archivo `OrdenesDebug.json`
        List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
    
        // Buscar y modificar la orden específica por compraId
        for (OrdenVtex orden : ordenes) {
            System.out.println("compraId a buscar: " + compraId);
            if (orden.getCompraId().equals(compraId)) {
                System.out.println("Orden original: " + orden);
        
                // Modificar los datos
                orden.setFechaBoleta(fechaBoleta);
                orden.setValidaBoleta(validaBoleta);

                // // Agregar tipo pedido
                // if(web.WaitForElementVisible(txtSustituido, 3)){
                //     System.out.println("Es sustituto");
                //     orden.setTipoPedido("Sustituto");
                // }
            
                // if (web.WaitForElementVisible(By.xpath("//*[text()='2405330020809' or text='2485363068779']"), 3)){
                //     System.out.println("Es pesable");
                //     orden.setTipoPedido("Pesable");
            
                // }else{
                //     System.out.println("Es normal");
                //     orden.setTipoPedido("Normal");
                // }

                System.out.println("Orden modificada: " + orden);
                break; // Salir del bucle una vez que se ha encontrado y modificado la orden
            }
        }
    
        // Escribir las ordenes modificadas de regreso en `OrdenesDebug.json`
        JsonFileUtils.writeToJsonFile(ordenes, "output/outputFinal/", "OrdenesDebug.json");
            
        // Imprimir el contenido del archivo para asegurarnos de que fue actualizado correctamente
        List<OrdenVtex> updatedOrdenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
        System.out.println("Contenido de OrdenesDebug.json después de escribir: " + updatedOrdenes);
            
    } catch (Exception e) {
        System.out.println("Exception en update " + e);
    }
 }





 }




    


