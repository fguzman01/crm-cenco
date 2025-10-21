package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;

import java.io.FileNotFoundException;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

public class RecorridoPedidoPage extends BaseConfig {
    private WebUtils app = null;

    public RecorridoPedidoPage(AppiumDriver driverApp) throws FileNotFoundException {

        app = new WebUtils(driverApp);
    }

    /***
     * valida orden
     */

    public boolean validaOrden (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Valida orden en top");

        return true;
    }




}
