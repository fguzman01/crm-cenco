package cencosud.test_old.EndToEnd;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
//import cencosud.models.DataExchageEasy;
import cencosud.models.General;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import cencosud.business.appNegocio;
import cencosud.business.backOfficeNegocio;
import cencosud.configuration.BaseUtils;
import cencosud.dataproviders.DataProvidersJson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Date;

import java.util.List;

public class TestApp extends backOfficeNegocio {
   // DataExchageEasy dataEasy = new DataExchageEasy().getInstance();
    String numeroOrden = "1319140055902";
    String boleta = "1658642";
    public appNegocio app = new appNegocio();
    private static final String API_KEY = "5FL8UmFLNMhCv7n3h8KClFqPagfqifHR";
    private static final String BASE_URL = "https://qa-api.cencosud.cl/sm/cl/v1/crm/credivouchers/reportes/DatosTrx/";


    @Epic("Generación de data Jumbo")
    @Feature(" Flujo Crear data")
    @Story("2-Procesar Orden en App")
    @Test(description = "ProcesarOrdenApp", dataProvider = "dataAll", dataProviderClass = DataProvidersJson.class)
    public void Test_procesarOrdenApp (List<General> general) throws Exception {

        /***
         * Asignación por App
         */

        BaseUtils.println( ANSI_GREEN+"**************Test Procesar Orden en App Mobile*************");

        app.iniciarApp();
        //app.iniciarSesiónApp( general );
        app.inicioOrdenApp( numeroOrden );
        //app.recorridoPedido(numeroOrden);
        app.pickerProductosSala();
        app.pickerProductosEstantes();
        app.validarConCliente();
        app.sinEmbolsado();
        app.finalizarPedido();

    }

   

    @Test
    public void testConsultarBoleta() throws InterruptedException {
        String boleta = "1658640"; // Reemplaza con el número de boleta real
        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());
        
        int intentosMaximos = 10;
        int contadorIntentos = 0;
        String fechaApi = null;

        while (contadorIntentos < intentosMaximos) {
            fechaApi = consultarBoleta(boleta);

            if (fechaApi != null) {
                if (fechaActual.equals(fechaApi)) {
                    Assert.assertEquals(fechaActual, fechaApi, "Las fechas no coinciden");
                    break; // Las fechas coinciden, sal del bucle
                } else {
                    Assert.fail("Las fechas no coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                }
            }

            Thread.sleep(5000); // Espera 5 segundos antes de volver a consultar
            contadorIntentos++;
        }

        Assert.assertNotNull(fechaApi, "La fecha de la boleta es nula después de los intentos máximos");
    }

    private String consultarBoleta(String boleta) {
        String url = BASE_URL + boleta;
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("apikey", API_KEY)
                .log().all()
                .get(url)
                .then()
                .log().all()
                .extract().response();

        return response.jsonPath().getString("fecha");
    }
}

