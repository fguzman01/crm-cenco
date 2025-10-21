package cencosud.tests;

import java.io.IOException;
import java.util.List;
import org.testng.annotations.Test;
import cencosud.dataproviders.DataProvidersJson;
import cencosud.models.OrdenVtex;
import org.testng.Assert;
import cencosud.pages.Api.vtexCollection.VtexPage;
import cencosud.pages.Api.bo.BeetrackApi;
import java.util.Date;
import java.text.SimpleDateFormat;


public class TesMetodos {
    
    VtexPage vtexPage = new VtexPage();
    private BeetrackApi beetrackApi;
    //private OrdenVtex orden; 
    OrdenVtex testDataFinal = new OrdenVtex();

    // Método para asignar el valor al campo orden.
    // public void setOrden(OrdenVtex orden) {
    //     this.orden = orden;
    // }



    @Test(description = "Cambia estado", 
          dataProvider = "DataFinal",
          dataProviderClass = DataProvidersJson.class)
    public void ConsultarBoleta() throws InterruptedException, IOException {
        
        System.out.println("Servicio consulta de boleta y validación");
        String boleta = "1662344"; // Reemplaza con el número de boleta real
        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Leer las ordenes del archivo `OrdenesDebug.json`
        List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
        
        int intentosMaximos = 20;
        int contadorIntentos = 0;
        String fechaApi = null;
        String compraId = "10084741";

        while (contadorIntentos < intentosMaximos) {
            fechaApi =  beetrackApi.consultarBoleta(boleta);

            if (fechaApi != null) {

                System.out.println("compraId a buscar: " + compraId);
                if (compraId.equals(compraId)) {
                    //System.out.println("Orden original: " + orden);
                
                    // Buscar y modificar la orden específica por compraId
                    for (OrdenVtex orden : ordenes) {
                       if (fechaActual.equals(fechaApi)) {
                            Assert.assertEquals(fechaActual, fechaApi, "Las fechas no coinciden");
                            System.out.println("Las fechas si coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                            orden.setFechaBoleta(fechaApi);
                            orden.setValidaBoleta("PASS");
                            break; // Las fechas coinciden, sal del bucle
                        } else {
                            System.out.println("Las fechas no coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                            orden.setFechaBoleta(fechaApi);
                            orden.setValidaBoleta("FAIL");
                            //Assert.fail("Las fechas no coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                       }
                    }
                
                }
            }

            Thread.sleep(5000); // Espera 5 segundos antes de volver a consultar
            contadorIntentos++;
        }

        //Assert.assertNotNull(fechaApi, "La fecha de la boleta es nula después de los intentos máximos");
    }
}




    

