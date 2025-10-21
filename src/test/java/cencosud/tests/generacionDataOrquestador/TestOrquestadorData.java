package cencosud.tests.generacionDataOrquestador;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.TestListener;
import cencosud.dataproviders.DataProvidersJson;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import cencosud.models.CreacionOrdenData;
import cencosud.models.DataPedidos;
import cencosud.models.OrdenVtex;
import cencosud.tests.testAppPicking.PickingOrden;
import cencosud.tests.testBackOfficeWeb.AsignacionOrden;


@Listeners(TestListener.class)

public class TestOrquestadorData {
    CreacionOrdenData testData = new CreacionOrdenData();
    DataPedidos dataPedido = new DataPedidos();
    OrdenVtex testDataFinal = new OrdenVtex();


    @Epic("Generación de data orden normal")
    @Feature("Flujos generación Jumbo")
    @Story("0. Test Orquestador")
    @Test(  description = "TestOrquestadorCRM",
            dataProvider = "TestGeneraDataJson",
            dataProviderClass = DataProvidersJson.class)
    
    public void TD_GeneracionOrden_TestOsquestador (List<CreacionOrdenData> testDataList) throws Exception {

        BaseUtils.println(BaseUtils.ANSI_GREEN + "************** Test Orquestador  *************");
        testData = testDataList.get(0);
        BaseUtils.println(BaseUtils.ANSI_GREEN + "************** Input de datos");
        BaseUtils.println(BaseUtils.ANSI_GREEN + testDataList.toString());
        BaseUtils.println(BaseUtils.ANSI_GREEN + "Incio del test generación de Orden");

        /*
         1. Test Asignar orden en BackOffice
       */
        
          Test_BO_AsignarOrden();

        /*
         2. Test Procesar orden en BO
       */

          Test_app_PickingOrden();
        
        System.out.println("FIN TEST");

    }

    

    @Step ("TEST CASE: ASIGNACIÓN ORDEN EN BACKOFFICE")
    public void Test_BO_AsignarOrden()throws Exception
    
    {
        System.out.println("Inicio Flujo en BO");
        AsignacionOrden asignacionOrden = new AsignacionOrden();
        asignacionOrden.beforeClass();
        asignacionOrden.BO_AsignarOrden(testData);
        asignacionOrden.afterClass();

    }

    @Step ("TEST CASE: PROCESAR ORDEN EN APP")
    public void Test_app_PickingOrden()throws Exception
    {
        System.out.println("Inicio Flujo en app Shopper");
        PickingOrden pickingOrden = new PickingOrden();
        pickingOrden.beforeClass();
        pickingOrden.App_PickingOrden(testData, dataPedido);

    }

    

    @AfterClass(alwaysRun = true)
    public void afterClass() throws Exception{
        
    }

}