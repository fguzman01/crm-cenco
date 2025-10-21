package cencosud.tests.testBackOfficeWeb;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import cencosud.business.backOfficeNegocio;
import cencosud.configuration.BaseUtils;
import cencosud.dataproviders.DataProvidersJson;
import java.io.IOException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import cencosud.models.CreacionOrdenData;


public class AsignacionOrden extends backOfficeNegocio {
     

    /*
     Asignación de caso en BackOffice
    */

    @Epic("Flujo de Generación de datos para CRM")
    @Feature("Flujos de backoffice")
    @Story("0. Test Orquestador asignación orden BO")
    @Test(description = "Test Asignar Orden", 
          dataProvider = "TestGeneraDataJson",
          dataProviderClass = DataProvidersJson.class)

    public void BO_AsignarOrden (CreacionOrdenData testData) throws InterruptedException, IOException {
        BaseUtils.println( ANSI_GREEN+"**************Test Asignar Orden en BackOffice*************");
        BaseUtils.println(BaseUtils.ANSI_GREEN + "Inicio Ejecucion Login");

        inicioSesión(testData.getUser(), testData.getPass());
        irPedidos();

        BaseUtils.println(BaseUtils.ANSI_GREEN + " Buscar orden en BO ");
        buscarOrden(testData.getOrden());
        validaciónEstadoOrden(testData.getEstado(),  testData.getOrden());

        BaseUtils.println(BaseUtils.ANSI_GREEN + " Ir a la pagina del pedido ");
        irApedidoBuscado(testData.getOrden());

        BaseUtils.println(BaseUtils.ANSI_GREEN + " Asignar la orden seleccionada al Picker y posterior capturar datos");
        asignarOrdenEnPedido(testData.getOrden(), testData.getMailPicker(), testData.getNombrePicker());
       // capturarDatosDelPedido();

        // BaseUtils.println(BaseUtils.ANSI_GREEN + " Reiniciar Pedido ");
        // reniciarOrdenEnPedido();

        
    }


    @BeforeClass
    public void beforeClass() throws Exception {
        inicioBackoffice();
    }

    @AfterClass
    public void afterClass() throws Exception {
        //CloseWebDriver();
    }


    
}
