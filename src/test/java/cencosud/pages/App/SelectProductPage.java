package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.HashMap;
import java.util.Map;
import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import org.openqa.selenium.Keys;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SelectProductPage extends BaseConfig {
    private WebUtils app = null;
    String ean = "";

    public SelectProductPage(AppiumDriver driverApp) throws FileNotFoundException {

        app = new WebUtils(driverApp);
    }

    /***
     * Validar productos en sala
     */

    private By objTxtSala = By.xpath("//*[contains(@text,'SALA - SIN UBICACIÓN')]");

    public boolean validaSala (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Valida si hay producto en : SALA - Sin ubicación");

        return app.WaitForElementVisible(objTxtSala, 15);
    }

    /***
     * Seleccionar productos
     * SALA sin ubicación
     */

    private By objProductoSala = By.xpath("//android.view.ViewGroup[@content-desc='product']");
    private By objBtnPickear = By.xpath("//*[@content-desc='btnPickear']");

    public boolean SelectProducto (int pos){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar producto en la lsita");

        By objProductos = By.xpath("(//*[@content-desc='product'])["+pos+"]");

        app.WaitForElementVisible( objProductos );
        app.Click( objProductos );

        return app.WaitForElementVisible( objBtnPickear );
    }

    /***
     * Click Boton Pickear
     */

    private By objTxtEan = By.xpath("(//*[starts-with(@text, '78') or starts-with(@text,'77') or starts-with(@text,'24') or starts-with(@text,'26')])[1]");

    public boolean SeleccionarPickear (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Click Boton PICKEAR");

        app.WaitForElementVisible(objTxtEan);
        ean = app.GetText( objTxtEan );
        BaseUtils.println(BaseUtils.ANSI_GREEN + "El EAN del producto es :" + ean);


        cambiEanPesable();

        app.Click( objBtnPickear );

        return app.WaitForElementVisible( objTxtIngresaEan);
    }

    /***
     * Ingresar EAN de producto
     */

    private By objTxtIngresaEan = By.xpath("//*[contains(@text,'Ingresar manualmente')]");
    private By objInputEan = By.xpath("//*[contains(@class,'EditText')]");
    private By objTxtPickeado = By.xpath("//*[contains(@text,'Pickeado')]");

    public boolean InputEan (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Ingresar EAN de producto de la lista");
        
        app.WaitForElementVisible(objTxtIngresaEan );
        app.Click( objTxtIngresaEan );

        String newEan = ean.replace("-CDE", "");
        //newEan = "7808709500073";

        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Nuevo EAN :" + newEan);
       
        app.SendKeys(objInputEan, newEan);
        

        Actions action = new Actions(driverApp);
        action.sendKeys(Keys.ENTER).perform();

        if (!validaSustituto()){
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "No es sustituto, valida si existe confirmación");
            ConfirmarPickeo();
        } else {
            BaseUtils.println(BaseUtils.ANSI_YELLOW + " Es sustitución ");
        }
        //ConfirmarPickeo();

       // return (app.WaitForElementVisible( objTxtPickeado ) || app.WaitForElementVisible(txtSustitucion)) ;
       return true;
        
    }

    /***
     * Ingresar EAN de producto
     */

    private By objBtnConfirmar = By.xpath("//*[@resource-id='btnConfirmar']");

    public boolean ConfirmarPickeo (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Confirmar Pickeo");
        
        if (app.WaitForElementVisible(objBtnConfirmar, 5)){

            app.Click(btnCnnfirmaSel);
            
        } else {
            System.out.println("No existe confirmar");
        }
        
       return true;
    }

    /***
     * Ir a sección
     * Estantes
     */


    private By objSeccionEstantes = By.xpath("//*[contains(@class,'HorizontalScrollView')]/android.view.ViewGroup/android.view.View[2]");
    private By objTxtEstantes = By.xpath("//*[contains(@text,'Estantes')]");

    public boolean SeccionEstantes (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Sección estantes");

        //if (!app.WaitForElementVisible(objTxtEstantes, 5)){
            app.WaitForElementVisible(objSeccionEstantes);
            app.Click( objSeccionEstantes );
        //}

        return app.WaitForElementVisible(objTxtEstantes, 5);

    }

    /***
     * validar estantes
     */

    public boolean validaEstantes () {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validación si existe seccion estantes");

        return app.WaitForElementVisible(objTxtEstantes, 5);

    }

    /***
     * validar sin productos
     */

    private By objTxtsinProductos = By.xpath("//*[contains(@text,'Ya no quedan productos en la lista')]");

    public boolean validarSinProductos (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validar no hay productos");

        return app.WaitForElementVisible(objtBtnValidar);

    }


    /***
     * Validar cliente
     */

    private By objtBtnValidar = By.xpath("//*[contains(@text,'VALIDAR CON CLIENTE') or contains(@text,'VALIDACIÓN INTERNA')]");
    private By objTxtValidaCliente = By.xpath("//*[contains(@text,'Validación del cliente')]");
    
    public boolean validarCliente (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validar con cliente");

        app.WaitForElementVisible( objtBtnValidar );
        app.Click( objtBtnValidar );

        return app.WaitForElementVisible( objTxtValidaCliente ) || app.WaitForElementVisible(txtSustvalidar);

    }

    /***
     * Validar cliente
     */

    private By txtProdXvalidar = By.xpath("//*[contains(@text,'Validación del cliente')]");

    public boolean productosValida (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validar si hay producto por validar");

        return app.WaitForElementVisible(txtProdXvalidar, 5);

    }

    /***
     * Seleccion producto
     */

    private By btnProductValidar = By.xpath("//*[contains(@text,'Queso de Cabra')]");
    private By txtPickeadoXvalidar = By.xpath("//*[contains(@text,'Pickeado por validar')]");

    public boolean selectProducto (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar producto a validar");

        app.WaitForElementVisible(btnProductValidar);
        app.Click(btnProductValidar);

        return app.WaitForElementVisible(txtPickeadoXvalidar);

    }

    /***
     * Confirmar validación
     */

     private By btnConfirmar = By.xpath("//*[contains(@text,'CONFIRMAR')]");
     private By objBtnFinalizar = By.xpath("//*[contains(@text,'FINALIZAR VALIDACION')]");

     public boolean confirmarValidacion (){

        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Confirmar validación");

        app.WaitForElementVisible(btnConfirmar);
        app.Click(btnConfirmar);

        return app.WaitForElementVisible(objBtnFinalizar);

     }

     /*
      * Valida si es sustituto
      */

      public boolean validaSustituto (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validado si producto e sustituto");

        return app.WaitForElementVisible(txtSustitucion, 5);


      }


     /*
      * Producto sustituto
      */

      private By txtSustitucion = By.xpath("//*[@text = 'Sustitución']");
      private By txtSust = By.xpath("//*[@text = '¿Es una sustitución?']");
      private By btnConfirmarSust = By.xpath("//*[@content-desc= 'btnConfirmar']");
      private By btnValidaInterna = By.xpath("//*[@text= 'VALIDACIÓN INTERNA']");

      public boolean agregarSutituto (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Confirmar sustituto");

        app.WaitForElementVisible(txtSust);

        if (app.WaitForElementVisible(btnConfirmarSust, 15)){

            if (app.WaitForElementVisible(btnConfirmarSust, 5)){

                app.Click(btnConfirmarSust);

            } else {
                BaseUtils.println(BaseUtils.ANSI_YELLOW + "Ya confirmo sustituto");
            }

        }

        //app.Click(btnConfirmar);

        return app.WaitForElementVisible(btnValidaInterna);

      }

      /*
       * Confirmación sustituto
       */

    private By txtSustvalidar = By.xpath("//*[@text= 'Productos por validar']");
    private By btnProduct = By.xpath("//*[@content-desc=\"product\"]");
    private By txtSustProducto = By.xpath("//*[@text= 'Sustitución de Producto']");
    private By btnCnnfirmaSel = By.xpath("//*[@text= 'CONFIRMAR']");
    private By btnFinalizarValida = By.xpath("//*[@text= 'FINALIZAR VALIDACION']");

    public boolean productosValidar (){

        return app.WaitForElementVisible(txtSustvalidar, 5);
    }


    public boolean confirmarSustituto (){

        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Confirmar sustituto - Lista");

        //app.Click(btnValidaInterna);

        //app.WaitForElementVisible(txtSustvalidar);
        app.WaitForElementVisible(btnProduct);
        app.Click(btnProduct);

        app.WaitForElementVisible(txtSustProducto);
        app.WaitForElementVisible(btnCnnfirmaSel);
        app.Click(btnCnnfirmaSel);

        return app.WaitForElementVisible(btnFinalizarValida);
    }

    public void cambiEanPesable() {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Si es pesable cambio el valor del ean");
    
        // Crear un mapa de ean y sus reemplazos
        Map<String, String> eanReplacements = new HashMap<>();
        eanReplacements.put("2485363000009", "2485363068779");
        eanReplacements.put("2405330000009", "2405330020809");
        eanReplacements.put("7808709500073", "7802900001346");
    
        // Verificar si ean existe en el mapa y hacer el reemplazo si es necesario
        if (eanReplacements.containsKey(ean)) {
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Paso por el if  pesable " + eanReplacements.get(ean));
            ean = eanReplacements.get(ean);
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Ean es " + ean);
        } else {
            System.out.println("mantener ean");
        }
    }



    public ArrayList<WebElement> obtenerListaProductos (){

        return (ArrayList<WebElement>)app.FindElements( objProductoSala );

    }

}


