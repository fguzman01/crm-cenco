package cencosud.pages.BackOffice;

import io.qameta.allure.Step;
import cencosud.models.DataBO;
import cencosud.models.DataExchageEasy;
import cencosud.models.General;
import org.openqa.selenium.*;

import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertTrue;


public class AsignarPage {
    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();
    public String so = "";
    Integer i = 1;
    private WebUtils web = null;
    DataBO dataBack = new DataBO().getInstance();


    public AsignarPage(WebDriver driver) throws FileNotFoundException {
        web = new WebUtils(driver);
    }


    /***
     * Seleccionar picker en sección
     */

    private By objComboPicker = By.xpath("//*[contains(@class,'filter-option-inner-inner') and text()='Selecciona un pickeador']");
    private By objTxtPicker = By.xpath("(//*[contains(@aria-label,'Search')])[2]");
    private By objPicker = By.xpath("//*[contains(@class,'active') and @role='option']");
    private By objTxtSecciones = By.xpath("//*[contains(text(),'Órdenes disponibles de sección')]");


    @Step("Seleccionar Picker")
    public boolean seleccionarPicker (List<General> general){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar Picker");


        web.WaitForElementVisible( objComboPicker );
        web.Click( objComboPicker );

        String str_picker = general.get(0).getNombre();
        web.SendKeys(objTxtPicker, str_picker);

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Nombre picker : " +  str_picker);
        WebElement picker = web.driver.findElement(By.xpath("(//*[contains(text(),'"+str_picker+"')])[2]"));

        web.WaitForElementVisible( objPicker );
        web.Click( objPicker );

        return web.WaitForElementVisible(By.xpath("//*[@class='label-picker' and text()='"+str_picker+"']"));


    }

    /***
     * Seleccionar seccion
     */

    private By objComboSeccion = By.xpath("//*[contains(@class,'filter-option-inner-inner') and text()='Selecciona una sección']");
    private By objAllSection = By.xpath("//*[@role='option']/span[@class='text']/span[text()='TODOS']");

    @Step("Seleccionar Sección")
    public void seleccionarSección (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Seleccionar Seccion");

        web.WaitForElementVisible( objComboSeccion);
        web.Click( objComboSeccion );

        web.Click( objAllSection );

        web.WaitForElementVisible( objTxtSecciones );

    }

    /***
     * Click Boton asignar todas
     */

    private By objBtnAsignarAll = By.xpath("//*[@id='asignarAll']");

    @Step("Boton asignar todas las ordenes")
    public void clickBotonAsinarAll(){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Click boton asignar todas");

        web.WaitForElementVisible( objBtnAsignarAll );
        web.Click( objBtnAsignarAll );

    }


    /***
     * Asignación de la orden
     */

    private By objListOrdenes = By.xpath("//*[@id='confirma-ordenes_wrapper']");

    @Step ("Asignar de la lista deja orden creada")
    public void asignacionLista (String so) {
        Integer i = 1;
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "De la lista dejar solo la orden creada");
        //String so = "31011953";

        web.WaitForElementVisible(objListOrdenes);

        List<WebElement> listProduct = web.driver.findElements(By.xpath("//*[contains(@class,'removeRow')]"));
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Tamaño Inicial lista ordenes : " + listProduct.size());
        Integer listOedenActualiza = listProduct.size();

        while (listOedenActualiza > 1) {
            By objOrder = By.xpath(("(//table[contains(@id,'confirma-ordenes')]/tbody/tr[@role='row']/td[contains(@class,'sorting_1')])[" + i + "]"));
            String str_so = web.GetText(objOrder);
            By bntIng = By.xpath(("(//*[contains(@class,'removeRow')])[" + i + "]"));
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"Orden a buscar : "+str_so);

            if (!str_so.equals(so)) {
                BaseUtils.println(BaseUtils.ANSI_YELLOW+"Son distinta a la creada se ignora " + str_so + " " + so);
                web.Click(bntIng);
            }
            else{
                System.out.println(" Es la misma orden no se ignora" + str_so + " " + so);
                i = 2;
            }

            List<WebElement> listProductAct = web.driver.findElements(By.xpath("//*[contains(@class,'removeRow')]"));
            System.out.println("Tamaño actualizado : " + listProductAct.size());
            listOedenActualiza = listProductAct.size();
        }
    }



    /***
     * Confirmar asignación picker
     */

    private By objBtnConfAsignacion = By.xpath("//*[@id='confirmar-modal']");
    private By objMsjExito = By.xpath("//*[text()='Éxito']");
    private By objBtnExito = By.xpath("//*[@class='confirm' and text()='OK']");
    private By objBtnCerrar = By.xpath("//*[contains(@class,'btn-primary') and text()='Cerrar']");

    public boolean confirmarAsignacion (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Boton confirmar asignación");

        web.WaitForElementVisible( objBtnConfAsignacion );
        web.Click( objBtnConfAsignacion );

        if (!web.WaitForElementVisible( objMsjExito , 5)){
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Error al asignar orden");
            return false;}

        web.Click( objBtnExito );
        web.Click( objBtnCerrar );

        return true;
    }


    /***
     * Capturar data BackOffice
     */

    private By objNumerSo = By.xpath("//*[@id='ordenes-picker']/tbody/tr[@class='odd']/td[1]");
    private By objTipoServicio = By.xpath("//*[@id='ordenes-picker']/tbody/tr[@class='odd']/td[6]");
    private By objCantidad = By.xpath("//*[@id='ordenes-picker']/tbody/tr[@class='odd']/td[5]");
    private By objSeccion = By.xpath("//*[@id='ordenes-picker']/tbody/tr[@class='odd']/td[2]");

    @Step ("Obtener datos de BackOffice y almacena en un Map ")
    public ConcurrentHashMap obtenerDatosBo (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Obtener datos y almacenar");

        ThreadLocal<List<ConcurrentHashMap<String, String>>>   listaDatos = dataBack.listEasy;
        ConcurrentHashMap backDatos = new ConcurrentHashMap<>();

        String str_so = web.GetText( objNumerSo);
        backDatos.put("so", str_so);
        BaseUtils.println(BaseUtils.ANSI_YELLOW+ "Numero SO : " + str_so);

        String str_servicio = web.GetText( objTipoServicio ).substring(0,2);
        backDatos.put("servicio", str_servicio);
        BaseUtils.println(BaseUtils.ANSI_YELLOW+ "Tipo de Servicio : " + str_servicio);

        String str_cant = web.GetText( objCantidad );
        backDatos.put("cantidad", str_cant);
        BaseUtils.println(BaseUtils.ANSI_YELLOW+ "Cantidad : " + str_cant);

        String str_seccion = web.GetText( objSeccion );
        backDatos.put("seccion", str_seccion);
        BaseUtils.println(BaseUtils.ANSI_YELLOW+ "Seccion : " + str_seccion);

        System.out.println(backDatos.toString());
        listaDatos.get().add(0, backDatos);

        return backDatos;
    }


    /***
     * Desasignar ordenes
     */


    private By objDesAsignar = By.xpath("//*[@id='desasignarPicker']");
    private By objListaOrdenes = By.xpath("//*[@id='confirma-ordenes_wrapper']");
    private By objBtonConfirmar = By.xpath("//*[@id='confirmar-modal']");

    @Step ("Desasignar todas las ordes")
    public boolean desAsignarOrdenes (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Asignar orden con Picker y Sección");

        web.WaitForElementVisible( objDesAsignar );
        web.Click( objDesAsignar );

        web.WaitForElementVisible( objListaOrdenes );
        web.Click(objBtonConfirmar );

        if (!web.WaitForElementVisible( objMsjExito , 5)){
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Error al desasignar orden");
            return false;}

        web.Click( objBtnExito );
        web.Click( objBtnCerrar );

        return true;

    }


    /***
     * Metodo general que incluye todas las funciones para asignación de ordenes
     */

    @Step ("Asignar orden a Picker en sección correspodiente")
    public void asignarOrdenPicker (List<General> general, String so) throws InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Asignar orden con Picker y Sección");
        seleccionarPicker(general);
        seleccionarSección();
        clickBotonAsinarAll();
        asignacionLista( so );
        assertTrue( "No se pudo confirmar ordenes", confirmarAsignacion() );
        obtenerDatosBo();
        assertTrue( "No se pudo confirmar ordenes", desAsignarOrdenes() );

    }
}
