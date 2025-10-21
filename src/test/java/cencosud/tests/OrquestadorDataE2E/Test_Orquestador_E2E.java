package cencosud.tests.OrquestadorDataE2E;
import java.io.IOException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import cencosud.business.apiNegocio;
import cencosud.business.appNegocio;
import cencosud.business.backOfficeNegocio;
import cencosud.configuration.BaseUtils;
import cencosud.configuration.TestListener;
import cencosud.dataproviders.DataProvidersJson;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import cencosud.models.OrdenVtex;
import cencosud.tests.testEndToEndo.GeneracionOrdenAll;
import cencosud.util.JsonFileUtils;

@Listeners(TestListener.class)

public class Test_Orquestador_E2E {
    
    public String compraId = "";
    public appNegocio app = new appNegocio();
    public backOfficeNegocio bo = new backOfficeNegocio();
    public apiNegocio api = new apiNegocio();
    public String estado =  "";
    public String rut =  "";
    public String boleta =  "";
    
   


    OrdenVtex testDataFinal = new OrdenVtex();

    @Epic("Generación de data orden completo")
    @Feature("Flujos generación Jumbo")
    @Story("0. Test Orquestador") 
    @Test(  description = "TestOrquestadorCRM",
            dataProvider = "DataFinal",
            dataProviderClass = DataProvidersJson.class)        
    public void TD_GeneracionOrdenFinal_TestOsquestador (List<OrdenVtex> testDataListFinal) throws Exception{

      BaseUtils.println(BaseUtils.ANSI_GREEN + "************** Test Orquestador  *************");
      testDataFinal = testDataListFinal.get(0);
      BaseUtils.println(BaseUtils.ANSI_GREEN + "************** Input de datos");
      BaseUtils.println(BaseUtils.ANSI_GREEN + testDataListFinal.toString());
      BaseUtils.println(BaseUtils.ANSI_GREEN + "Incio del test generación de Orden");

      /*
         1.- Asignación orden BO
     */

      Test_e2e_GenerarOrden();

      /*
         2.- Picking orden 
     */

      Test_e2e_PickingOrden();

    }

    @Step (" TEST CASE: GENERAR ORDEN E2E ")
    public void Test_e2e_GenerarOrden () throws Exception{
      System.out.println("Flujo E2E Generación Orden");
      GeneracionOrdenAll generacionOrdenAll = new GeneracionOrdenAll(app, bo);
      generacionOrdenAll.E2E_AsignarOrden_BO(testDataFinal);

    }

    @Step (" TEST CASE: PICKING ORDEN E2E ")
    public void Test_e2e_PickingOrden () throws Exception{
      System.out.println("Flujo E2E Picking Orden");
      GeneracionOrdenAll generacionOrdenAll = new GeneracionOrdenAll(app, bo);
      generacionOrdenAll.E2E_PickingOrden_App(testDataFinal);

    }

    boolean marcarComoFallido = false;

    @AfterMethod(alwaysRun = true)
    public void reiniciarFlujo( ITestResult result) throws Exception {
        
      if (bo.existeEstado()){
        estado = bo.estadoOrden();
        if(estado.equals("En picking") || estado.equals("En Embolsado") || estado.equals("Pendiente picking")) {
            BaseUtils.println(BaseUtils.ANSI_GREEN + "El estado es " + estado + ". Reniciar orden en BO");
            bo.reniciarOrdenEnPedido();
            app.reniciarOrdenApp();
            marcarComoFallido = true; 
        

        }
        
        else if (bo.validarSiFacturado ()){

          // Si es el estado es distinto al de los anteriores captura estatus orden
          BaseUtils.println(BaseUtils.ANSI_GREEN + " La orden esta en estado Facturado, se guarda datos");
          bo.actualizarDatosBo(testDataFinal.getCompraId(), estado, rut, boleta);
          
          /*
           * En ruta         --> 1
           * Entregado       --> 2
           * No entregado    --> 3
           * Entrega parcial --> 4
           * Facturado       --> dejar comentado la ejecución del servicio
           */
          
          //api.actualizarEstadoOrden(testDataFinal, "1");
          bo.actualizarDatosBo(testDataFinal.getCompraId(), estado, rut, boleta);
          bo.validarBoleta(testDataFinal.getCompraId());

        } else {
          estado = bo.estadoOrden();
          BaseUtils.println(BaseUtils.ANSI_GREEN + " La orden no esta Facturada, se actualiza estado final");
          bo.actualizarDatosBo(testDataFinal.getCompraId(), estado, rut, "null");
          marcarComoFallido = true; 

        }

      }

      // Marcar el resultado como fallido si se debe hacerlo
      if (marcarComoFallido) {
        result.setStatus(ITestResult.FAILURE);
        marcarComoFallido = false; // Restablecer la bandera
    }
  
    }

    
    @BeforeSuite
    public void beforeSuite() throws Exception {
        //Crear orden API Collections
        api.crearOrdenJumbo("JUMBO-SISA_2", "jumbo_test");

        // Iniciar Web BO e inicio sesión
        bo.inicioBackoffice();
        Thread.sleep(3000);
        bo.inicioSesión("felipe.arayag@cencosud.cl", "Jumbo0001");

        // Inicio Apium e inicio en app
        app.iniciarApp();
        app.iniciarSesiónApp("felipe.arayag@cencosud.cl", "Jumbo0001");
    }


    @AfterClass
    public void backupOrdenesDebug() {
    JsonFileUtils.backupFileWithTimestamp("output/outputFinal/OrdenesDebug.json", "output/backup/");
   }
    
}
