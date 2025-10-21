package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;




public class LoginPage extends BaseConfig {

    private WebUtils app = null;


    public LoginPage(AppiumDriver driver) {

        //PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        app = new WebUtils(driverApp);
    }


    /***
     * Login en App
     */

    private By objTxtHome = By.xpath("//*[contains(@text,'INICIAR SESIÓN')]");
    //private By objTxtHome = By.xpath("//*[contains(@text,'Iniciar sesión')]"); --> Picker up
    private By objBtnEntrar = By.xpath("//*[contains(@text,'INICIAR')]");
    //private By objInputUser = By.xpath("//*[contains(@resource-id,'userText')]");  --> Picker up
    private By objInputUser = By.xpath("//*[contains(@class,'EditText') and following-sibling::android.widget.TextView[contains(@text,'Usuario')]]");
    //private By ObjInputPass = By.xpath("//*[contains(@resource-id,'passText')]"); --> Picker up
    private By ObjInputPass = By.xpath("(//*[contains(@class,'EditText')])[2]");
    private By objTxtLogin = By.xpath("//*[@text='Somos cuidadosos']");

    @Step("Login en App Shopper")
    public boolean Login(String strUser, String strPass) {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Login en App Shopper");

        app.WaitForElementVisible(objTxtHome);
        app.WaitForElementVisible(objBtnEntrar);

        app.SendKeys(objInputUser, strUser);
        app.SendKeys(ObjInputPass,  strPass);

        app.Click(objBtnEntrar);

        if (!app.WaitForElementVisible(objTxtLogin, 20)) {
            BaseUtils.println(BaseUtils.ANSI_YELLOW + "Login incorrecto no se muestra menu en App");
            return false;
        }
        return true;
    }

    /***
     * Swipe en Home
     */

    private By objBtnComenzar = By.xpath("//*[contains(@text,'COMENZAR')]");
    private By ObjTxtDisponible = By.xpath("//*[contains(@text,'NO DISPONIBLE')]");


    public boolean swipeProducto() throws InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + "Swipe en home");
        int intentos = 0;
        int maxIntentos = 4;

        //MobileElement objIniciSwipe = (MobileElement) driverApp.findElement(By.xpath("//*[contains(@class,'ImageView')]"));

        while (intentos < maxIntentos) {

            if (app.WaitForElementVisible(objBtnComenzar, 4)) {
                app.Click(objBtnComenzar);
                break;
            } else {
                MobileElement objIniciSwipe = (MobileElement) driverApp.findElement(By.xpath("//*[contains(@class,'ImageView')]"));
                app.swipeElementAndroid(objIniciSwipe, Direction.LEFT);
                intentos++;
            }
            if (intentos >= 4) {
                BaseUtils.println(BaseUtils.ANSI_YELLOW + "Maximo de intentos");
                Assert.fail("Maximo de intentos, no se encuentra orden");
                break;
            }
        }
        return app.WaitForElementVisible(ObjTxtDisponible);

    }



}
