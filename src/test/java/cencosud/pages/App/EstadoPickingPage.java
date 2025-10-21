package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import cencosud.models.DataBO;
import cencosud.models.DataExchageEasy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EstadoPickingPage extends BaseConfig {
    public String ean = "";
    public String sku = "";
    public String codeSap;
    private WebUtils app = null;
    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();
    DataBO dataBack = new DataBO().getInstance();
    ThreadLocal<List<ConcurrentHashMap<String, String>>>  listProductos  = dataEasy.listaEasy;
    ConcurrentHashMap datosSku = new ConcurrentHashMap();

    public EstadoPickingPage(AppiumDriver driver){

        app = new WebUtils(driverApp);
    }


    /***
     * Seleccionar picker en sección
     */

    private By objCodProducto = By.xpath("//*[contains(@resource-id,'txt_product_code')]");


   @Step ("Cantida de productos en estado de picking")
    public Integer cantidadProductos(){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Cantidad de filas de productos");

       List ListProducto = driverApp.findElements(By.xpath("//*[contains(@resource-id,'txt_product_code')]"));
       BaseUtils.println(BaseUtils.ANSI_YELLOW+"Cantidad de filas de productos es : " + ListProducto.size());
       int int_cant = ListProducto.size();

       return int_cant ;
    }

    public ArrayList<WebElement> obtenerListaProductos (){

       return (ArrayList<WebElement>)app.FindElements( objCodProducto );

    }

    @Step ("Seleccionar producto con EAN")
    public void seleccionProductoEan (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar Producto");

    }

    /***
     * Seleccionar producto - Ir a detalle
     */

    private By ObjTitleDetail = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and @text = 'Producto']");

    @Step(" Seleccionar producto por Sku ")
    public boolean seleccionarProductoSku (int pos) {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar Producto");


        objCodProducto = By.xpath("(//*[contains(@resource-id,'txt_product_code')])["+pos+"]");
        app.WaitForElementVisible( objCodProducto);
        //sku = app.GetText( objCodProducto );

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Sku en Estado de Picking" + sku);

        app.Click( objCodProducto );

        return (app.WaitForElementVisible( ObjTitleDetail ) | app.WaitForElementVisible( objCodigoSap ));

    }


    /***
     * Ficha de detalle producto
     */

    private By objNombreProducto = By.xpath("//*[contains(@resource-id,'txt_name')]");
    private By objCodigoSap = By.xpath("//*[contains(@resource-id,'txt_sap_code')]");
    private By objNomProducto = By.xpath("//*[contains(@resource-id,'txt_name')]");

    @Step("Detalle de producto captura EAN y SAP")
    public String capturaDataProducto (int pos){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"En detalle capturar EAN y validar SAP con SKU");
        String str_ean = "";

        app.WaitForElementVisible( objNombreProducto);

        String str_sap = app.GetText(objCodigoSap).replaceAll("SKU: ", "");
        System.out.println(str_sap);
        sku = app.GetText(objCodigoSap);
        String str_nom = app.GetText( objNomProducto);

        String[] arrayNombre = str_nom.split("-");

        //Captura solo Ean
        int i;
        for ( i = 0; i < arrayNombre.length; i++) {
            System.out.println(arrayNombre[i]);
            str_ean = arrayNombre[i];
        }

        ean = str_ean.replaceAll("\\s","");

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Numero de EAN : " + ean);

        volverAtras();

        return ean;

    }

    /***
     * Volver atras
     */

    private By objBtnBack = By.xpath("//*[@content-desc='Navegar hacia arriba' or @content-desc='Navigate up']");

    @Step ("Volver atras a estado de picking")
    public void volverAtras (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Volver a estado de picking");

        app.WaitForElementVisible( objBtnBack );
        app.Click( objBtnBack );

    }

    /***
     * Input para pickear EAN o Codigo SAP
     */

    private By objBtnSearch = By.xpath("//*[contains(@resource-id,'btn_search')]");
    private By ObjInputText = By.xpath("//*[contains(@resource-id,'edit_text_search')]");
    private By obtTxtTitlePicking = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and @text = 'Estado Picking']");


    @Step("Ingresar EAN o SKU para pikear")
    public boolean inputPickeo (String input) throws InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Ingresar numero EAN o codigo SAP");
        String str_input = "";

        //ean = capturaDataProducto();
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Numero Ean es : " + ean);
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Numero Sku es : " + sku);


        if (input == "ean"){
            str_input = ean;
        } else {
            str_input = sku;
        }

        app.WaitForElementVisible( ObjInputText );
        app.Click( ObjInputText );

        app.SendKeys( ObjInputText, str_input );


        return app.WaitForElementVisible( By.xpath("//*[contains(@resource-id,'edit_text_search') " +
                "and @text="+str_input+"]") );
    }

    /***
     * Ingresar cantidad de productos
     */

    private By objCantidad = By.xpath("//*[contains(@resource-id,'title_text') and @text='Cantidad Pickeada']");
    private By objConfirmar = By.xpath("//*[contains(@resource-id,'confirm_button')]");
    private By ObjInputCantPick = By.xpath("//*[contains(@class, 'EditText')]");

    @Step (" Cantidad de productos pickear ")
    public boolean cantidadPickear ( int pos ){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Ingresar cantidad de productos a pickear");

        String cantidad = cantidaProducto(pos);
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Cantidad de " + cantidad + " en posición producto : "
                + pos  );

        app.WaitForElementVisible( objBtnSearch );
        app.Click( objBtnSearch );


        app.WaitForElementVisible( ObjInputCantPick );
        app.SendKeys( ObjInputCantPick, cantidad );

        app.WaitForElementVisible( objConfirmar );
        app.Click( objConfirmar );

        String cantPick = capturePickeado( pos );
        assertEquals (cantidad, cantPick, " Cantidades distintas ");


        return (Objects.equals(cantPick, cantidad) |
                app.WaitForElementVisible(
                        By.xpath("(//*[contains(@resource-id,'txt_percent') and @text='100%'])["+pos+"]") ));
    }


    /***
     * Captura cantidad pickeada
     */


    public String capturePickeado (int pos){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Captura cantidad de productos pickeados");
        By objTxtPickeado = By.xpath("(//*[contains(@resource-id, 'txt_picked')])["+pos+"]");

        String cantPick = "";
        String s = app.GetText( objTxtPickeado );

        Matcher m = Pattern.compile("Pickeado: (.+?)").matcher(s);

        if (m.find())
        {
            cantPick = m.group(1);
            System.out.println("Cantidad pickeada : " + cantPick);
        }

        return cantPick;
    }

    public String cantidaProducto (int pos){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+" Cantidad por productos ");

        By objTxtCant = By.xpath("(//*[contains(@resource-id, 'txt_picked')])["+pos+"]");

        String cantProducto = "";
        String s = app.GetText( objTxtCant );

        Matcher m = Pattern.compile("Cantidad: (.+?) /").matcher(s);

        if (m.find())
        {
            cantProducto = m.group(1);
            System.out.println("Cantidad pickeada : " + cantProducto);
        }

        return cantProducto;
    }



    /***
     * Cerrar orden en App
     */

    private By objEstadoJaula = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and @text='Estado Jaula']");
    private By objPorcentaje = By.xpath("//*[contains(@resource-id,'txt_percent') and @text='100%']");
    private By objConfOrden = By.xpath("//*[contains(@text,'Confirma cerrar orden')]");
    private By objCerrarOrden = By.xpath("//*[contains(@resource-id,'confirm_button') and @text='SI']");
    private By objBtnCloseOrder = By.xpath("//*[contains(@resource-id,'btn_close')]");
    private By objOk = By.xpath("//*[contains(@resource-id,'confirm_button') and @text='OK']");
    private By objSinOrdenes = By.xpath("//*[contains(@resource-id,'txt_not_registers') and @text='No hay registros para mostrar']");


    @Step ("Cerrar orden")
    public boolean cerrarOrdenDad(){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Valida estado de jaula y continuar");

        app.WaitForElementVisible( objEstadoJaula );
        app.WaitForElementVisible( objPorcentaje );

        app.WaitForElementVisible( objBtnCloseOrder );
        app.Click( objBtnCloseOrder );

        app.WaitForElementVisible( objConfOrden );
        app.Click( objCerrarOrden );

        app.WaitForElementVisible(objOk);
        app.Click( objOk );

        if (!app.WaitForElementVisible( objSinOrdenes , 10)){
            BaseUtils.println(BaseUtils.ANSI_RED+"No se pudo cerrar la orden de manera correcta");
            return false;
        }
        return true;
    }

    /***
     * Boton Ubicación subOrden
     */

    private By objBtnMove = By.xpath("//*[contains(@resource-id,'btn_move')]");
    private By objBannerLoad = By.xpath("//*[contains(@resource-id,'loading')]");

    @Step ("Boton ubicación SubOrden")
    public Boolean ubicacionSubOrden (){

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Boton Mover SubOrden - Ubicación");

        app.WaitForElementVisible( objBtnMove );
        app.Click( objBtnMove );

        return app.WaitForElementVisible( objBannerLoad );
    }

    /***
     * Ingresar datos
     * Ubicación SubOrden
     */


    private By objInputBodega = By.xpath("//*[contains(@class,'EditText') and @text ='Bodega']");
    private By objInputRack = By.xpath("//*[contains(@class,'EditText') and @text ='Rack']");
    private By objInputPos = By.xpath("//*[contains(@class,'EditText') and @text ='Posición']");
    private By objBtnConfirm = By.xpath("//*[contains(@resource-id,'confirm_button')]");
    private By objTxtEstadoTienda = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and @text = 'Estado Tienda']");

    @Step ("Datos ubicación subOrden")
    public boolean inputDatosUbicacion (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Datos Ubicación  de la SubOrden");

        app.WaitForElementVisible( objInputBodega );
        app.SendKeys( objInputBodega ,"1");

        app.WaitForElementVisible( objInputRack );
        app.SendKeys( objInputRack ,"1");

        app.WaitForElementVisible( objInputPos );
        app.SendKeys( objInputPos ,"1");

        app.WaitForElementVisible( objBtnConfirm );
        app.Click( objBtnConfirm );

        return app.WaitForElementVisible( objTxtEstadoTienda );

    }


    /***
     * Swipe en producto
     */

    private By objImageBultos = By.xpath("//*[contains(@class,'LinearLayout')]/android.widget.ImageView");

    public boolean swipeProducto () throws InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Swipe en elemento");

        MobileElement objSwipe = (MobileElement) driverApp.findElement(By.xpath("//*[contains(@resource-id,'txt_product_code')]"));
        app.WaitForElementVisible( objSwipe );
        app.swipeElementAndroid(objSwipe , Direction.LEFT);

        return app.WaitForElementVisible(objImageBultos , 5);

    }

    /***
     * Click Imagen bultos
     */

    private By objTxtBultos = By.xpath("//*[contains(@text,'Cantidad Bultos') and contains(@resource-id,'title_text')]");

    public boolean clickBultos (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Click en bultos");

        app.WaitForElementVisible( objImageBultos );
        app.Click( objImageBultos );

        return app.WaitForElementVisible( objTxtBultos );

    }

    /***
     * Ingresar bultos
     */

    private By objInputCant = By.xpath("//*[contains(@class,'EditText')]");
    private By obtBtnConfirm = By.xpath("//*[contains(@resource-id,'confirm_button')]");
    private By obtBtnCancel = By.xpath("//*[contains(@resource-id,'cancel_button')]");

    public boolean ingresaBultos (String cant){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Ingresa cantidad de bultos");

        app.WaitForElementVisible( objInputCant );
        app.SendKeys(objInputCant, cant);

        app.WaitForElementVisible( obtBtnConfirm );
        app.Click( obtBtnConfirm );

        By objValidaBulto = By.xpath("//*[contains(@resource-id,'txt_package') and @text = 'Bultos: "+cant+"']");

        return app.WaitForElementVisible( objValidaBulto );
    }

    /***
     * Validación estado
     */

    public boolean validacionEstado (String estado){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Validación por estado : '" + estado + "'");

        By objTxtEstado = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and @text='"+estado+"']");

        return app.WaitForElementVisible( objTxtEstado );

    }

    /***
     * Cerrar orden
     */

    private By objBtnCerrarOrden = By.xpath("//*[contains(@resource-id,'confirm_button') and @text='SI']");
    private By objBtnOk = By.xpath("//*[contains(@resource-id,'confirm_button') and @text='OK']");

    public boolean cerrarOrden (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Boton cerrar orden");

        app.WaitForElementVisible( objBtnCloseOrder );
        app.Click( objBtnCloseOrder );

        app.WaitForElementVisible(objBtnCerrarOrden);
        app.Click( objBtnCerrarOrden );

        app.WaitForElementVisible(objBtnOk);
        app.Click( objBtnOk );

        return app.WaitForElementVisible( objSinOrdenes);

    }


    }



