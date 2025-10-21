package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import cencosud.models.DataBO;
import cencosud.models.DataExchageEasy;
import org.openqa.selenium.By;

import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;

public class ClientePage {

    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();

    private WebUtils app = null;
    DataBO dataBack = new DataBO().getInstance();

    public ClientePage(AppiumDriver driverApp) throws FileNotFoundException {

        app = new WebUtils(driverApp);
    }

    /***
     * valida orden
     */

    public boolean validaOrden (/*String orden,*/ String tienda){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Valida orden en top");

        //By objOrden = By.xpath("//*[contains(@text,'"+ orden +"')]");
        By objTienda = By.xpath("//*[contains(@text,'"+ tienda +"')]");


        return /*app.WaitForElementVisible(objOrden, 10) && */ app.WaitForElementVisible(objTienda, 10);         
    }


    /***
     * Continuar datos
     */

    //By objBtnContinuar = By.xpath("//*[contains(@text,'Continuar')]");  --> Picker up
    By objBtnContinuar = By.xpath("//*[contains(@text,'CONTINUAR')]");
    By objBtnAdicional = By.xpath("//*[contains(@text,'PRODUCTO ADICIONAL')]");

    public boolean continuarOrden (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Boton continuar con la orden");

        app.WaitForElementVisible( objBtnContinuar );
        app.Click( objBtnContinuar );

        //return app.WaitForElementVisible( objBtnAdicional );
        return true;
    }


}
