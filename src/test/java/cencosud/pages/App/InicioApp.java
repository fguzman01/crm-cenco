package cencosud.pages.App;

import cencosud.configuration.DriverFactory;
import io.appium.java_client.AppiumDriver;

public class InicioApp{

    protected static AppiumDriver driver = null;

    public void setUp(){
        driver = DriverFactory.getAppiumDriver();
    }
}
