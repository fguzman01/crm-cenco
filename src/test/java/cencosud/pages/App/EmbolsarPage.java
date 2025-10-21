package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EmbolsarPage extends BaseConfig {

    private WebUtils app = null;

    public EmbolsarPage(AppiumDriver driverApp) throws FileNotFoundException {
        app = new WebUtils(driverApp);
    }

    /***
     * Seleccionar producto
     * suelto
     */

    public boolean selecionarSuelto (int pos, String estado){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar producto suelto");

        By objBtnSwitch = By.xpath("(//*[contains(@class,'Switch')])["+pos+"]");
        app.Click( objBtnSwitch );

        return app.WaitForElementVisible(By.xpath(
                ("(//*[contains(@class,'Switch') and @text='"+estado+"'])["+pos+"]")));
    }

    /***
     * Boton embolsado
     */

    private By objBtnEmbolsar = By.xpath("//*[contains(@text,'EMBOLSAR PRODUCTOS')]");
    private By objTxtBultos = By.xpath("//*[contains(@text,'Cantidad de bultos')]");

    public boolean clickBotonEmbolsar (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Click embolsar producto");

        app.WaitForElementVisible( objBtnEmbolsar );
        app.Click( objBtnEmbolsar );

        return app.WaitForElementVisible( objTxtBultos );
    }

    /***
     * Cantidad de Bultos
     */

    private By objBtnContinuar = By.xpath("//*[contains(@text,'CONTINUAR')]");
    private By objTxtResumenBultos = By.xpath("//*[contains(@text,'Resumen bultos')]");

    public boolean cantidadBultos () {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Seleccionar cantidad de bultos");

        app.WaitForElementVisible( objBtnContinuar );
        app.Click( objBtnContinuar );

        return app.WaitForElementVisible( objTxtResumenBultos );

    }

    /***
     * Cantidad de Bultos
     */

    private By objTxtResumenPedido = By.xpath("//*[contains(@text,'Resumen de pedido')]");

    public boolean resumenBultos () {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Resumen de bultos");

        app.WaitForElementVisible( objBtnContinuar );
        app.Click( objBtnContinuar );

        return app.WaitForElementVisible( objTxtResumenPedido );
    }


    /***
     * Lista de producto
     */

    private By objListProductos = By.xpath("//*[contains(@class,'Switch')]");

    public ArrayList<WebElement> obtenerListaProductos (){

        return (ArrayList<WebElement>)app.FindElements( objListProductos );

    }

}
