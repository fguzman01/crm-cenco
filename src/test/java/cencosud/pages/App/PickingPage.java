package cencosud.pages.App;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import cencosud.models.DataBO;
import cencosud.models.DataExchageEasy;
import org.openqa.selenium.By;

import cencosud.configuration.BaseConfig;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.WebUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PickingPage extends BaseConfig {
    DataExchageEasy dataEasy = new DataExchageEasy().getInstance();
    private WebUtils app = null;
    DataBO dataBack = new DataBO().getInstance();

    public PickingPage(AppiumDriver driver){
        app = new WebUtils(driverApp);
    }


    /***
     * Seleccionar orden para Picker
     */

    private By objTXTPicking = By.xpath("//*[contains(@resource-id,'txt_title_actionbar') and contains(@text,'Picking')]");

    @Step("Seleccionar orden para ir a Estado Picking")
    public boolean selecOrdenPicking (String so){
        BaseUtils.println(BaseUtils.ANSI_YELLOW+"Identificar orden para ir a estado de Picking");

        ThreadLocal<List<ConcurrentHashMap<String, String>>>  listProductos  = dataEasy.listaEasy;

        String str_so = "317121154";
        String str_seccion = listProductos.get().get(0).get("seccion");;

        app.WaitForElementVisible(  objTXTPicking );
        By objTxtSo = By.xpath("//*[contains(@text,'"+ so +"')]");
        By objTxtSeccion = By.xpath("//*[contains(@text,'"+ str_seccion +"')]");

        if (!app.WaitForElementVisible(objTxtSo, 10)){
            BaseUtils.println(BaseUtils.ANSI_YELLOW+"Orden con numero distinto");
            return false;
        }

        app.Click( objTxtSo );
        return true;
    }



}
