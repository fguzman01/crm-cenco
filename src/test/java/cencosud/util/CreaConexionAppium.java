package cencosud.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CreaConexionAppium {
    public static AppiumDriver<MobileElement> creaConexion(AppiumDriver<MobileElement> driver, String nombreDispositivo, String platformName, String platformVersion, String hostMaquina, String appPackage, String appActivity){
        try{
            System.out.println("nombre dispo " +nombreDispositivo);
            System.out.println("version "+platformVersion);
            System.out.println("nombre plat : "+platformName);
            System.out.println("host : "+hostMaquina);
            DesiredCapabilities cap = new DesiredCapabilities();
            //cap.setCapability("deviceName", "Nexus 5 API 27");
            cap.setCapability("deviceName", nombreDispositivo);
            //cap.setCapability("deviceName", "Nexus One");
            //cap.setCapability("udid", "emulator-5554");
            //cap.setCapability("app", "C:\\apk\\app-debug.apk");
            cap.setCapability("platformName", platformName);
            cap.setCapability("platformVersion", platformVersion);
            //cap.setCapability("platformVersion", "7.1.1");

            cap.setCapability("appPackage", appPackage);
            cap.setCapability("appActivity", appActivity);
            //cap.setCapability("app","C:\\apk\\app-debug.apk");

            //URL url = new URL("http://0.0.0.0:4723/wd/hub");
            URL url = new URL("http://"+hostMaquina+":4723/wd/hub");
            //driver = new RemoteWebDriver(url, cap);
            driver = new AppiumDriver<MobileElement>(url,cap);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


            String[] nombreApp = appPackage.split("\\.");
            int largo = nombreApp.length;
            largo = largo -1;
            System.out.println("Aplicacion : "+"'"+nombreApp[largo]+"'"+" iniciada");

           // Screen.screenShotOK(driver,"conexion.png");

            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


        }catch (Exception e){
            System.out.println(e);
        }

        return driver;
    }
}
