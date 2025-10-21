package cencosud.util;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import cencosud.configuration.BaseUtils;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Conexion {


    public static AppiumDriver<MobileElement> creaConexion(AppiumDriver<MobileElement> driver, String nombreDispositivo, String platformName, String platformVersion, String hostMaquina, String appPackage, String appActivity) {
        /***
         * Fichero de propiedades
         */
        BaseUtils.println(BaseUtils.ANSI_GREEN+"Fichero : ");
        PropertyLoader loadproperty = new PropertyLoader();

        /***
         *  Recuperación del fichero de propiedades de la ruta
         *  y nombre de la aplicación móvil
         */

        String appiumON = System.getProperty("appiumON", "N");
        String device = System.getProperty("device", "JumboAhoraApp");
        BaseUtils.println(BaseUtils.ANSI_GREEN+"Valor por defecto appiumON : " + appiumON);

        /***
         *  Generación de capabilities a nivel del servicio Appium
         */

        BaseUtils.println(BaseUtils.ANSI_GREEN+"Generación de capabilities.....");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3600);

        /***
         *  Generación de capabilities a nivel del Driver
         */
        BaseUtils.println(BaseUtils.ANSI_GREEN+"Generación de driver.....");
        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        clientCapabilities.setCapability(MobileCapabilityType.UDID, device);
        clientCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, nombreDispositivo);


        try {

            /***
             * Crear conexión Appium
             * Local o remeta en AWS
             */

            BaseUtils.println(BaseUtils.ANSI_GREEN+"Validando si ambiende ejecución es local o AWS ....");
            if (appiumON.equals("S")) {
                System.out.println("Si es Amazon");
                URL url_appium = new URL("http://" + loadproperty.loadProperties().getProperty("AppiumServerIP") + ":" +
                        loadproperty.loadProperties().getProperty("AppiumServerPort") + "/wd/hub");
                driver = new AppiumDriver(url_appium, clientCapabilities);
            } else {
                BaseUtils.println(BaseUtils.ANSI_GREEN+"Es ambiente local");
                BaseUtils.println(BaseUtils.ANSI_GREEN+"El nombre del dispositivo es : " + nombreDispositivo);
                BaseUtils.println(BaseUtils.ANSI_GREEN+"Version Sistema operativo del dispositivo:  " + platformVersion);
                BaseUtils.println(BaseUtils.ANSI_GREEN+"Sistema Operativo  : " + platformName);
                BaseUtils.println(BaseUtils.ANSI_GREEN+"Host de Appium : " + hostMaquina);

                /***
                 * Creación de capabilities
                 */

                DesiredCapabilities cap = new DesiredCapabilities();
                cap.setCapability("deviceName", nombreDispositivo);
                cap.setCapability("platformName", platformName);
                cap.setCapability("platformVersion", platformVersion);
                cap.setCapability("appPackage", appPackage);
                cap.setCapability("appActivity", appActivity);
                cap.setCapability("autoGrantPermissions",true);
                System.out.println(System.getProperty("user.dir"));
                System.out.println(System.getProperty("apkDir"));
                cap.setCapability("app", System.getProperty("user.dir")+"/"+loadproperty.loadProperties().getProperty("apkDir")+"/"+loadproperty.loadProperties().getProperty("apkName"));

                cap.setCapability("newCommandTimeout", 3600);
                URL url = new URL("http://" + hostMaquina + ":4723/wd/hub");
                driver = new AppiumDriver<io.appium.java_client.MobileElement>(url, cap);

                String[] nombreApp = appPackage.split("\\.");
                int largo = nombreApp.length;
                largo = largo - 1;
                BaseUtils.println(BaseUtils.ANSI_GREEN+" Aplicacion : " + "'" + nombreApp[largo] + "'" + " iniciada");

            }

            } catch(Exception e){
                System.out.println(e);
                BaseUtils.println(BaseUtils.ANSI_RED+"Error al iniciar App excepción :" + e);
            }

            return driver;
        }

    public static AppiumDriver<MobileElement> creaConexionVpn(AppiumDriver<MobileElement> driverVpn, String nombreDispositivo, String platformName, String platformVersion, String hostMaquina, String appPackageVpn, String appActivityVpn) {
        // Carga del fichero de propiedades
        System.out.println("Fichero");
        PropertyLoader loadproperty = new PropertyLoader();
        // Recuperación del fichero de propiedades de la ruta y nombre de la aplicación móvil
        String appiumON = System.getProperty("appiumON", "N");
        String device = System.getProperty("device", "JumboAhoraApp");
        System.out.println(appiumON);
        System.out.println(device);

        // Generación de las capabilites a nivel del servicio de Appium
        System.out.println("generacion cap");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

        // Generación de las capabilites a nivel de driver
        System.out.println("Generación driver");
        DesiredCapabilities clientCapabilities = new DesiredCapabilities();
        clientCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        clientCapabilities.setCapability(MobileCapabilityType.UDID, device);
        clientCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "JumboAhoraApp");
        //driver.activateApp("com.android.chrome");

        try {

            System.out.println("Pregunta si es Amazon");
            if (appiumON.equals("S")) {
                System.out.println("Si es Amazon");
                URL url_appium = new URL("http://" + loadproperty.loadProperties().getProperty("AppiumServerIP") + ":" +
                        loadproperty.loadProperties().getProperty("AppiumServerPort") + "/wd/hub");
                driverVpn = new AppiumDriver(url_appium, clientCapabilities);
            } else {
                System.out.println("No es Amazon");
                System.out.println("nombre dispo " + nombreDispositivo);
                System.out.println("version " + platformVersion);
                System.out.println("nombre plat : " + platformName);
                System.out.println("host : " + hostMaquina);
                DesiredCapabilities cap = new DesiredCapabilities();
                cap.setCapability("deviceName", nombreDispositivo);
                cap.setCapability("platformName", platformName);
                cap.setCapability("platformVersion", platformVersion);
                cap.setCapability("appPackage", appPackageVpn);
                cap.setCapability("appActivity", appActivityVpn);
                //cap.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
                URL url = new URL("http://" + hostMaquina + ":4723/wd/hub");
                driverVpn = new AppiumDriver<io.appium.java_client.MobileElement>(url, cap);
                driverVpn.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


                String[] nombreApp = appActivityVpn.split("\\.");
                int largo = nombreApp.length;
                largo = largo - 1;
                System.out.println("Aplicacion : " + "'" + nombreApp[largo] + "'" + " iniciada");
                //driver.activateApp("com.android.chrome");


               // driverVpn.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            }

        } catch(Exception e){
            System.out.println(e);
        }

        return driverVpn;
    }
    }


