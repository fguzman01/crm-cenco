package cencosud.pages.BackOffice;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.io.FileNotFoundException;

public class MonitorPage   {

    private WebUtils web = null;
    public MonitorPage(WebDriver driver) throws FileNotFoundException {
        web = new WebUtils(driver);
    }


    /***
     * Ir a opción de asignación de ordenes
     */

    private By objLinkAsignar = By.xpath("//a[@href='/asignar' and @name='/orders']");
    private By objBtnAsignarAll = By.xpath("//*[@id='asignarAll']");

    @Step("Ir a opción asignar ordenes")
    public boolean opcionAsignar (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Selección ir asignar");

        web.Click( objLinkAsignar );
        if (!web.WaitForElementVisible( objBtnAsignarAll, 10 ))
            return false;

        return true;
    }

    /***
     * Ir a Sección ordenes
     */

    private By objLinkOrdenes = By.xpath("//li[contains(@role,'menuitem')]/a[@href='/orders']");
    private By objTxtPedidos= By.xpath("//span[contains(@class,'Headerdesktop') and text()='Pedidos']");

    public boolean pedidos(){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Ir a sección de pedidos");

        web.WaitForElementVisible( objLinkOrdenes );
        web.Click( objLinkOrdenes );
        web.Click( objLinkOrdenes );

        return web.WaitForElementVisible( objTxtPedidos );
    }

     /***
     * Ir a resumen 
     */

    private By objLinkResumen = By.xpath("//li[@role='menuitem']/a[@href='/resume']");
    private By objTxtResumenTienda = By.xpath("//*[text()='Resumen tiendas']");

    public boolean resumeOrders (){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Ir a sección Resumen");

        web.WaitForElementVisible( objLinkResumen );
        web.Click( objLinkResumen );

        return web.WaitForElementVisible( objTxtResumenTienda );
    }

}
