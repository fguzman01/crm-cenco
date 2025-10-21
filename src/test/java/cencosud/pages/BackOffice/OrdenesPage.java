package cencosud.pages.BackOffice;

import io.qameta.allure.Step;
import cencosud.models.DataExchageEasy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;
import java.io.FileNotFoundException;
import java.util.List;

public class OrdenesPage  extends BaseConfig{
    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();

    private WebUtils web = null;
    public OrdenesPage(WebDriver driver) throws FileNotFoundException {
        web = new WebUtils(driver);
    }

    /***
     * Buscar SO en Monitor
     * BackOffice
     */

    private By objBtnBusqueda = By.xpath("//span[contains(@class,'Headerdesktop') and text()='Pedidos']\")");
    private By objBtnBuscar = By.xpath("//button[@type='button']/div[text()='Buscar']");
    private By objTxtInput = By.xpath("//*[@id='busqueda']");
    private By objTxtNoResult = By.xpath("//*[text()='No hay datos']");
    private By objBtnConfirm = By.xpath("//*[@class='confirm']");



    @Step("Buscar Orden")
    public boolean buscarOrden (String strOrden){

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Buscar pedido ");

        String orden = inputSoBuscar(strOrden);

        if(web.WaitForElementVisible(By.xpath("(//*[contains(@href,'" + strOrden +"')])[2]"), 10)){
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"Se encontro orden");
            WebElement ordenResult = web.driver.findElement(By.xpath("(//*[contains(@href,'/orders/')])[2]"));
            System.out.println("Orden " + ordenResult.getText());
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"Si esta SO numero :" + orden );
            return true;

        } else {
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "No se encuentra la orden");
            return false;
        }
    }
    

    /***
     * Input orden
     */


    private By objInputPedidos= By.xpath("//label[contains(@class,'Input') and contains(text(),'Pedido')]/following-sibling::div/input");

    public String inputSoBuscar (String strOrden){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Input SO en textBox");

        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Orden a utilizar : " + strOrden);

        web.WaitForElementVisible( objBtnBuscar );


        web.ClearText(objInputPedidos);
        web.SendKeys(objInputPedidos, strOrden);
        web.SendKeys(objInputPedidos, "\n");

        return strOrden;
    }

    /***
     * Validar estado orden
     */


    private By objTxtEstadoPick = By.xpath("//*[contains(@class,'Status')]");


    @Step ("Valida estado de orden en creación o termino")
    public boolean validacionEstado  (String estado) {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validación de estado de la orden");
        List<WebElement> objListResultados = web.driver.findElements(By.xpath("//*[@class='ant-table-body']/table/colgroup"));

        int list = objListResultados.size();

        if (list > 1) {
            Assert.fail("Entrega mas de un resultado para la orden");
        }

        if (web.WaitForElementVisible(By.xpath("//*[contains(@class,'StatusTag__OrderStatusTagContainer') and contains(text(),'" + estado +"')]"), 2)){
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Validación de estado de la orden correcta");
            return true;
        }

        else {
            return false;
        }

     }



    /***
     * Click boton asignar
     * BackOffice
     */


    private By ObjBtnAsignar = By.xpath("//*[contains(@class,'Truncate') and child::span[text()='Sin Asignar']]//following-sibling::div/button");

    @Step ("Seleccionar boton asignar")
    public boolean seleccionarAsignar (String order){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar boton asignar");

        web.WaitForElementVisible( ObjBtnAsignar );
        web.Click( ObjBtnAsignar );

        return web.WaitForElementVisible(
                By.xpath("//*[text()= 'Asignar Shopper - Pedido "+order+"']"));
    }

    /***
     * Input picker
     */

    private By objInputPicker = By.xpath("//input[contains(@placeholder,'Ingrese')]");

    @Step ("Ingresar mail picker")
    public boolean inputPicker (String mailPicker){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Se ingresa ¨Picker");

        web.WaitForElementVisible( objInputPicker );
        web.SendKeys( objInputPicker, mailPicker );
        web.Click( objInputPicker );

        return web.WaitForElementVisible(
                By.xpath("//*[contains(@class,'Text__TextComponent') and contains(text(),'"+mailPicker+"')]"));
    }


    /***
     * Input picker
    */

    private By objBtnConfirmar = By.xpath("//button[@type='submit']/div[text()='Confirmar']");


    @Step ("Ingresar seleccionar Picker y confirmar")
    public boolean seleccionarPicker(String mailPicker, String nomPicker){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar Picker y confirmar");

        web.Click(By.xpath("//*[contains(@class,'Text__TextComponent') and contains(text(),'"+mailPicker+"')]"));

        web.WaitForElementVisible( objBtnConfirmar );
        web.Click( objBtnConfirmar );

        return web.WaitForElementVisible(By.xpath("//*[text()='" + nomPicker + "']"));
    }


    /***
     * Ir al pedido
     */

     private By ObjTxtNoOrden = By.xpath("//*[contains(text(), ('El pedido que buscas no existe')]");

    @Step ("Ir a la pagina del pedido")
    public boolean irApedido (String order){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Ir al pedido");

        WebElement ObjLinkOrder = web.driver.findElement(By.xpath("(//*[contains(@href,'/orders/')])[2]"));
        ObjLinkOrder.click();

        
        return web.WaitForElementVisible(
                By.xpath("//*[contains(@class,'Headerdesktop__PageTitle') and text()='Pedido']" +
                        "//following-sibling::div/span[contains(text(),'" + order + "')]"));

    }


}

