package cencosud.pages.BackOffice;

import cencosud.models.DataExchageEasy;
import org.openqa.selenium.WebDriver;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;
import org.openqa.selenium.By;
import java.io.FileNotFoundException;



public class HomePage   {
    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();


    private WebUtils web = null;
    public HomePage(WebDriver driver) throws FileNotFoundException {
        web = new WebUtils(driver);
    }


    /***
     * Valida carga pagina inicio
     */


    private By objTextHome = By.xpath("//*[text()='Selecciona una opción para ingresar' or text()='Si tiene correo corporativo Cencosud, ingrese por ADFS']");
    private By objbtnBacloffice = By.xpath("//*[@type='button']/div[contains(text(),'BACKOFFICE')]");

    public boolean validaHome () throws InterruptedException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Valida carga de HomePage");
        web.WaitForElementVisible( objTextHome);

        return web.WaitForElementVisible(  objbtnBacloffice ) ;
    }

    /***
     * En HomePage Click en BackOfiice
     */

    private By objTextLogin = By.xpath("//*[contains(@class,'Text') and text()='Iniciar sesión']");

    public boolean seleccionarBacnOffice (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Click boton BackOffice");
        web.Click( objbtnBacloffice );

        return web.WaitForElementVisible( objTextLogin );

    }

     /***
     * Login Page
     */


    private By objInputUser = By.xpath("//*[@type='input' and @name='username']");
    private By objInputPass = By.xpath("//*[@type='password' and @name='password']");
    private By objButtonLogin = By.xpath("//*[@type='submit']/div[contains(text(),'Iniciar sesión')]");
    private By objTextWelcome = By.xpath("//*[text()='Resumen de Tiendas']");


    public boolean Login (String user, String pass) throws FileNotFoundException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW+" Login en Monitor de pedidos ");

        web.WaitForElementVisible( objInputUser );
        web.WaitForElementVisible( objInputPass );
        web.WaitForElementVisible( objButtonLogin );

        web.SendKeys(objInputUser, user);
        web.SendKeys(objInputPass, pass);

        web.Click( objButtonLogin );

        return web.WaitForElementVisible( objTextWelcome );

    }


}
