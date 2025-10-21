package cencosud.tests.testAppPicking;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import cencosud.business.appNegocio;
import cencosud.configuration.BaseUtils;
import cencosud.models.CreacionOrdenData;
import cencosud.models.DataPedidos;
import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PickingOrden extends appNegocio{

    public appNegocio app = new appNegocio();

    @Epic("Generación de data Jumbo")
    @Feature(" Flujo Crear data")
    @Story("2-Procesar Orden en App")
    @Test()
    public void App_PickingOrden (CreacionOrdenData testData, DataPedidos dataPedido) throws IOException, InterruptedException{
        BaseUtils.println( ANSI_GREEN+"**************Test Procesar Orden en APP Shopper*************");
        BaseUtils.println(BaseUtils.ANSI_GREEN + "Inicio Ejecucion Login");
        app.iniciarSesiónApp(testData.getUser(), testData.getPass());

        BaseUtils.println(BaseUtils.ANSI_GREEN + "Validar orden y comenzar proceso de la Orden");
        app.inicioOrdenApp( "Jumbo" );
        //app.recorridoPedido(testData.getOrden(), "Jumbo");
        app.recorridoPedido("Jumbo");
        
        BaseUtils.println(BaseUtils.ANSI_GREEN + "Picker productos de la lista de la orden");
        app.pickerProductosSala();
        //app.pickerProductosEstantes();
        app.validarConCliente();
        
        BaseUtils.println(BaseUtils.ANSI_GREEN + "Finalizar Orden ");
        app.sinEmbolsado();
        app.finalizarPedido();

        BaseUtils.println(BaseUtils.ANSI_GREEN + "Dejar shopper deshabilitado");
        app.desHabilitarShopper();
    }

    @BeforeMethod
    public void beforeClass() throws Exception {
        app.iniciarApp();  
    }
    
}
