package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;

public class FinalizarPage extends BaseConfig {
    private WebUtils app = null;


    public FinalizarPage(AppiumDriver driverApp) throws FileNotFoundException {

        app = new WebUtils(driverApp);
    }

    /***
     * Finalizar y Seguir en Linea
     */

    private By objbtnFinalizar = By.xpath("//*[contains(@text,'FINALIZAR')]");
    private By objShoppDiponible = By.xpath("//*[contains(@text,'DISPONIBLE') or contains(@text,'COMENZAR')]");


    public boolean finalizarPedido () {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Finalizar y seguir en Linea");

        app.WaitForElementVisible( objbtnFinalizar );
        app.Click( objbtnFinalizar );

        return app.WaitForElementVisible( objShoppDiponible );
    }
}
