package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;

public class ValidaPage extends BaseConfig {
    private WebUtils app = null;

    public ValidaPage(AppiumDriver driverApp) throws FileNotFoundException {
        app = new WebUtils(driverApp);
    }

    /***
     * Finalizar validación
     */

    private By objBtnFinalizar = By.xpath("//*[contains(@text,'FINALIZAR VALIDACION')]");
    private By objTxtPickFinalizar = By.xpath("//*[contains(@text,'Picking finalizado')]");

    public boolean finalizarValidacion (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Finalizar validación");

        app.WaitForElementVisible( objBtnFinalizar );
        app.Click( objBtnFinalizar );

        return app.WaitForElementVisible( objTxtPickFinalizar );
    }



}
