package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import cencosud.models.DataBO;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;

public class AppGenerico {

    private WebUtils app = null;
    DataBO dataBack = new DataBO().getInstance();

    public AppGenerico(AppiumDriver driverApp) throws FileNotFoundException {

        app = new WebUtils(driverApp);
    }

    /***
     * Reniciar en app
     */

     public void reniciarApp (){

     }
    
}
