package cencosud.tests.testEndToEndo;

import java.io.IOException;
import org.testng.annotations.Test;

import cencosud.business.appNegocio;
import cencosud.business.backOfficeNegocio;
import cencosud.configuration.BaseUtils;
import cencosud.dataproviders.DataProvidersJson;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import cencosud.models.OrdenVtex;

public class GeneracionOrdenAll  {
    public appNegocio app = new appNegocio();
    public backOfficeNegocio bo = new backOfficeNegocio();

    public GeneracionOrdenAll(appNegocio app, backOfficeNegocio bo) {
        this.app = app;
        this.bo = bo;
    }

    /*
     Test de Generación orden End to End 
     1.- Vtex
     2.- BO
     3.- Picking
    */

    @Epic(" Flujo de Generación de datos para CRM ")
    @Feature(" Flujos End To End ")
    @Story("0. Test Orquestador generación orden")
    @Test(description = "Test Asignar Orden", 
          dataProvider = "DataFinal",
          dataProviderClass = DataProvidersJson.class)
    public void E2E_AsignarOrden_BO(OrdenVtex testData) throws InterruptedException, IOException{

        /*
        Asignar orden
       */
        
        BaseUtils.println( BaseUtils.ANSI_GREEN+"**************Test Asignar Orden en BackOffice*************");
        BaseUtils.println(BaseUtils.ANSI_GREEN + "Ir a pedidos en Login");

        bo.irPedidos();

        BaseUtils.println(BaseUtils.ANSI_GREEN + " Buscar orden en BO ");
        bo.buscarOrden(testData.getCompraId());
        bo.validaciónEstadoOrden(testData.getOrdenStatus(), testData.getCompraId());

        BaseUtils.println(BaseUtils.ANSI_GREEN + " Ir a la pagina del pedido ");
        bo.irApedidoBuscado(testData.getCompraId());
        //replace("-CDE", "");

        BaseUtils.println(BaseUtils.ANSI_GREEN + " Asignar la orden seleccionada al Picker y posterior capturar datos");
        bo.asignarOrdenEnPedido(testData.getCompraId(), "felipe.arayag@cencosud.cl", "Felipe Guzman");
        //bo.capturarDatosDelPedido();

        
       
    }


    @Epic(" Flujo de Generación de datos para CRM ")
    @Feature(" Flujos End To End - Picking orden")
    @Story("0. Test Orquestador generación orden")
    @Test(description = "Test Picking Orden", 
          dataProvider = "DataFinal",
          dataProviderClass = DataProvidersJson.class)
    public void E2E_PickingOrden_App(OrdenVtex testData) throws InterruptedException, IOException{

        /*
        Picking  orden
       */
        
      BaseUtils.println(BaseUtils.ANSI_GREEN + "Validar orden y comenzar proceso de la Orden");
      String tienda = testData.getTienda();
      app.inicioOrdenApp( testData.getTienda());
      app.recorridoPedido(/*testData.getCompraId(),*/ testData.getTienda());
      
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
}
    


    

