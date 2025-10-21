package cencosud.configuration;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.nio.file.Path;
import java.util.Optional;
import io.github.bonigarcia.wdm.WebDriverManager;
import cencosud.util.Conexion;
import cencosud.util.PropertyLoader;

import org.testng.Assert;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    

    public static WebDriver getDriver(String browser) throws Exception {
        WebDriver driver = null;
        Optional<Path> browserPath = null;
        switch(browser){
            case "Chrome":
                //Valido que exista Chrome en sistema
                browserPath = WebDriverManager.chromedriver().getBrowserPath();
                Assert.assertNotNull(browserPath);
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setAcceptInsecureCerts(true);
                options.addArguments("--disable-gpu", "--window-size=1920,1080", "--disable-web-security", "--incognito",
                        "--ignore-certificate-errors", "--allow-running-insecure-content", "--disable-extensions",
                        "--no-sandbox", "--disable-dev-shm-usage", "--aggressive-cache-discard", "--disable-cache",
                        "--disable-application-cache", "--silent", "--disable-browser-side-navigation", "--no-proxy-server",
                        "--log-level=3", "--disable-infobars", "--disable-offline-load-stale-cache", "--disable-notifications",
                        "--no-sandbox", "--proxy-server='direct://'", "--proxy-bypass-list=*", "--whitelisted-ips");

                driver = new ChromeDriver(options);
                break;
            case "Firefox":
                //Valido que existe Firefox en sistema
                browserPath = WebDriverManager.firefoxdriver().getBrowserPath();
                Assert.assertNotNull(browserPath);
                FirefoxOptions options2 = new FirefoxOptions();
                options2.setAcceptInsecureCerts(true);
                options2.addArguments("--disable-gpu", "--window-size=1920,1080", "--disable-web-security", "--incognito",
                        "--ignore-certificate-errors", "--allow-running-insecure-content", "--disable-extensions",
                        "--no-sandbox", "--disable-dev-shm-usage", "--aggressive-cache-discard", "--disable-cache",
                        "--disable-application-cache", "--silent", "--disable-browser-side-navigation", "--no-proxy-server",
                        "--log-level=3", "--disable-infobars", "--disable-offline-load-stale-cache", "--disable-notifications",
                        "--no-sandbox", "--proxy-server='direct://'", "--proxy-bypass-list=*", "--whitelisted-ips");
                driver = WebDriverManager.firefoxdriver().capabilities(options2).create();
                break;
        }
        driver.manage().timeouts().implicitlyWait(65, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(65, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().timeouts().setScriptTimeout(65, TimeUnit.SECONDS);
        return driver;

    }

    public static WebDriver getChromeDriver() throws Exception{

		System.out.println(System.getProperty("os.name").toLowerCase());
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			// windows
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/windows/chromedriver.exe");
		} else {
			File test = new File("src/test/resources/drivers/linux/chromedriver");
			test.setExecutable(true);
			test.setReadable(true);
			test.setWritable(true);
			System.out.println("Se puede ejecutar el chromedriver? " + test.canExecute());
			System.setProperty("webdriver.chrome.driver",
					"/builds/QC_TestingTecnico/ProyectosAutomatizacionFuncional/txd_cl_shipper/src/test/resources/drivers/linux/chromedriver");
		}

		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.notifications", 1);
		// 1-Allow, 2-Block, 0-default

		// options.setHeadless(true);
		options.setAcceptInsecureCerts(true);
		options.addArguments("--disable-gpu", "--window-size=1920,1080", "--disable-web-security", "--incognito",
				"--ignore-certificate-errors", "--allow-running-insecure-content", "--disable-extensions",
				"--no-sandbox", "--disable-dev-shm-usage", "--aggressive-cache-discard", "--disable-cache",
				"--disable-application-cache", "--silent", "--disable-browser-side-navigation", "--no-proxy-server",
				"--log-level=3", "--disable-infobars", "--disable-offline-load-stale-cache", "--disable-notifications",
				"--no-sandbox", "--proxy-server='direct://'", "--proxy-bypass-list=*", "--whitelisted-ips");

		// options.addArguments("--window-size=1920,1080");
		// options.addArguments("--headless", "--disable-gpu",
		// "--window-size=1920,1080", "--incognito");

		// options.setExperimentalOption("useAutomationExtension", false);
		// options.addArguments("--proxy-server='direct://'");
		// options.setExperimentalOption("excludeSwitches",
		// Collections.singletonList("enable-automation"));
		// options.addArguments("--proxy-bypass-list=*");

		// options.setExperimentalOption("prefs",prefs);
		// options.setExperimentalOption("useAutomationExtension", false);
		// options.setExperimentalOption("excludeSwitches",
		// Collections.singletonList("enable-automation"));
		// options.addArguments("--disable-notifications");
		// options.addArguments("disable-infobars");
		// options.addArguments("--ignore-ssl-errors=yes");
		// options.addArguments("--ignore-certificate-errors");//falta agreagar a driver
		// de aws
		// options.addArguments("incognito");
		// options.setHeadless(true);
		WebDriver driver=null;
		try {
			driver = new ChromeDriver(options);
		}
		catch (Exception e) {
			System.out.println("ERROR: Fallo en abrir ChromeDriver.   Stack:"+ e.getMessage());
			driver.quit();
			throw(e);
		}
        driver.manage().timeouts().implicitlyWait(65, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(65, TimeUnit.SECONDS);
		// driver.manage().window().setSize(new Dimension(1920,1080));
		driver.manage().window().maximize();
		// driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(65, TimeUnit.SECONDS);
		// driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

		return driver;
	}



    // public static WebDriver chromeDriveMobile() {
    //     Map<String, String> mobileEmulation = new HashMap<>();
    //     mobileEmulation.put("deviceName", "Nexus 5");

    //     System.out.println(System.getProperty("os.name").toLowerCase());

    //     if (System.getProperty("os.name").toLowerCase().contains("windows")) {
    //         // windows
    //         System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/windows/chromedriver.exe");
    //     } else {
    //         System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/linux/chrome/chromedriver");
    //     }

    //     ChromeOptions chromeOptions = new ChromeOptions();
    //     chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
    //     WebDriver driver = new ChromeDriver(chromeOptions);

    //     driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    //     driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
    //     driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

    //     return driver;

    // }


    //Driver Device Jumbo
    public static WebDriver deviceDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "FeGuzman");
        caps.setCapability("platformName", "Android");
        caps.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        caps.setCapability(CapabilityType.VERSION, "10");
        WebDriver driverDevice = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driverDevice.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driverDevice.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        driverDevice.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

        return driverDevice;

    }

    public static AppiumDriver getAppiumDriver() {
        AppiumDriver driver = null;
        PropertyLoader loadproperty = new PropertyLoader();
        String nombreDispositivo = loadproperty.loadProperties().getProperty("nombreDispositivo");
        String platformName = loadproperty.loadProperties().getProperty("platformName");
        String platformVersion = loadproperty.loadProperties().getProperty("platformVersion");
        String hostMaquina = loadproperty.loadProperties().getProperty("hostMaquina");
        String appPackage = loadproperty.loadProperties().getProperty("appPackage");
        String appActivity = loadproperty.loadProperties().getProperty("appActivity");

        driver = Conexion.creaConexion(driver, nombreDispositivo, platformVersion, platformName, hostMaquina, appPackage, appActivity);
        return driver;
    }

    public static AndroidDriver getDriverWebChromeForMobileDesktopView(String strDeviceName, String strVersion, AppiumDriver driverAppium) throws MalformedURLException {

        {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, strVersion);
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, strDeviceName);
            caps.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
            caps.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
            ChromeOptions options = new ChromeOptions();
            caps.setCapability(ChromeOptions.CAPABILITY, options);
            String url = "http://127.0.0.1:4723/wd/hub";
            AndroidDriver chromeDriver = new AndroidDriver<>(new URL(url), caps);
            //chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            chromeDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            chromeDriver.manage().deleteAllCookies();
            driverAppium.activateApp("com.android.chrome");
            /*try {

                InicioPageChrome inicioPageChrome = new InicioPageChrome(driverAppium);
                inicioPageChrome.validaPantalla();
                inicioPageChrome.setDesktopViewChrome();
                } catch (InterruptedException e) {
                  System.err.println(e);

            }*/
            return chromeDriver;
        }
    }
}