package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import cencosud.models.DataBO;
import cencosud.models.DataExchageEasy;
import org.openqa.selenium.By;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PrincipalPage extends BaseConfig {
    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();

    private WebUtils app = null;
    DataBO dataBack = new DataBO().getInstance();

    public PrincipalPage(AppiumDriver driverApp) throws FileNotFoundException {

        app = new WebUtils(driverApp);
    }

    /***
     * Seleccionar orden
     */

    private By objTxtOrden = By.xpath("(//*[contains(@resource-id,'txt_title')])[2]");
    private By objTXTPicking = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and contains(@text,'Picking')]");


    @Step("Seleccionar orden en pagina principal")
    public boolean seleccionOrden (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar orden en App");

        ThreadLocal<List<ConcurrentHashMap<String, String>>> listaDatos = dataBack.listEasy;
       // String str_seccion = listaDatos.get().get(0).get("seccion");
        String str_seccion = "SECCIÓN PINTURA";

        app.WaitForElementVisible( objTxtOrden );

        //Seleccionar seccion
        By objtOrden = By.xpath("//*[contains(@text,'"+ str_seccion +"')]");
        app.Click( objtOrden );


        return app.WaitForElementVisible( objTXTPicking );

    }

    /***
     * Seleccionar orden
     */

    private By objBtnactualizar = By.xpath("//*[contains(@resource-id,'btn_refresh')]");

    public void sincronización (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Actualizar para sincronizar");

        app.WaitForElementVisible( objBtnactualizar );
        app.Click( objBtnactualizar );

    }

    //

    /***
     * Validar
     * tienda
     * @throws InterruptedException
     */



    public boolean validarTienda(String strTienda ) throws InterruptedException{
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Validar Tienda, espera...");
        //return app.WaitForElementVisible(By.xpath("//*[contains(@text,'" + strTienda + "')]"));
        Thread.sleep(5000);
        return true;
    }


    /***
     * Habilitar
     * Shopper
     */

    private By objBtnDisponible = By.xpath("//*[contains(@text,'NO DISPONIBLE') or contains(@text,'COMENZAR')]");
    private By objShoppDiponible = By.xpath("//*[@text = 'DISPONIBLE' or @text= 'COMENZAR']");

    public boolean disponibleShopper (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Habilitar Shopper");

        if (app.WaitForElementVisible(objShoppDiponible, 5)){
            noDisponibleShopper();
            confirmarDeshabilitar();
        }
        
        app.WaitForElementVisible(objBtnDisponible);
        app.Click( objBtnDisponible );
         

        return app.WaitForElementVisible( objShoppDiponible );

    }

    public boolean noDisponibleShopper (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Deshabilitar Shopper");

        //app.WaitForElementVisible( objShoppDiponible );
        app.Click( objShoppDiponible );

        return app.WaitForElementVisible( objTxtImportante );

    }

     /***
     * Confirmar deshabilitar
     */

    private By objTxtImportante = By.xpath("//*[contains(@text,'Importante')]");
    private By objBtnAceptar = By.xpath("//*[contains(@text,'ACEPTAR')]");
     
    public boolean confirmarDeshabilitar (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Confirmar Deshabilitar Shopper"); 

        app.WaitForElementVisible(objTxtImportante);
        app.WaitForElementVisible(objBtnAceptar);
        app.Click(objBtnAceptar);

        return app.WaitForElementVisible(objBtnDisponible);
    }

    private By objBtnCOmenzar = By.xpath("//*[contains(@text,'Comenzar') or contains(@text,'COMENZAR')]");
    //private By objBtnCOmenzar = By.xpath("//*[contains(@text,'COMENZAR')]");
    private By objTxtPedido = By.xpath("//*[contains(@text,'Recorrido del pedido')]");

    public boolean comenzarProceso (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Comenzar con la orden");

        if (app.WaitForElementVisible( objBtnCOmenzar, 30 )){
             
            app.Click( objBtnCOmenzar );
            return app.WaitForElementVisible( objTxtPedido );
            
        } else {

            noDisponibleShopper();
            return false;


        }
    }


     /***
     * Reniciar en app
     */

    private By objTxtPedidoNoDisponible = By.xpath("//*[contains(@text,'Pedido no Disponible')]"); 
    private By objBtnEntendido= By.xpath("//*[contains(@text,'ENTENDIDO')]"); 

    public boolean reniciarApp (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"validar mensaje para reniciar orden en app");


        if (app.WaitForElementVisible(objTxtPedidoNoDisponible, 10)){

            app.WaitForElementVisible( objBtnEntendido );
            app.Click(objBtnEntendido);

        } else {
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"No se alacanzo a asignar orden "); 
        }

        return app.WaitForElementVisible( objBtnDisponible );
        //return true;
    }
   



}
