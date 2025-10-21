package cencosud.pages.Api.bo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class BeetrackApi {

    public String x_api_token = "t6KFdeZUUkj2Ari4sxd8OMapLXboZWSH";
    public String x_api_key = "1WtqWaIvgwJK5t2xUjPcAtiwQemhnsId";
    
    /*
     Cambiar estado a Entregado
    */

    public boolean cambiarEstado (String orden, String status){
        String url = "https://api.smdigital.cl/v0/cl/backoffice/qa/beetrack/webhook/dije";
        String requestBody = "{\n" +
                "    \"resource\": \"dispatch\",\n" +
                "    \"event\": \"update\",\n" +
                "    \"account_name\": \"SantaIsabel.cl\",\n" +
                "    \"account_id\": 2926,\n" +
                "    \"guide\": \""+ orden +"\",\n" +
                "    \"identifier\": \""+ orden +"\",\n" +
                "    \"route_id\": "+ orden +",\n" +
                "    \"dispatch_id\": 416507834,\n" +
                "    \"truck_identifier\": \"FOXER-DBDH60\",\n" +
                "    \"status\": "+status+",\n" +
                "    \"substatus\": null,\n" +
                "    \"substatus_code\": \"011\",\n" +
                "    \"estimated_at\": \"2022-06-09T16:38:48.000-04:00\",\n" +
                "    \"max_delivery_time\": \"2022-06-09T18:00:59.000-04:00\",\n" +
                "    \"min_delivery_time\": \"2022-06-09T16:00:00.000-04:00\",\n" +
                "    \"is_pickup\": false,\n" +
                "    \"is_trunk\": false,\n" +
                "    \"locked\": false,\n" +
                "    \"contact_name\": \"karina ponce flores\",\n" +
                "    \"contact_phone\": \"+56984180963\",\n" +
                "    \"contact_identifier\": \"15.079.481-1\",\n" +
                "    \"contact_email\": \"imperiodefrutasyverduras@gmail.com\",\n" +
                "    \"contact_address\": \"José Moreno 983 ,casa 983 ,(local comercial y casa habitaci n),  Valparaíso,Valparaiso\"\n" +
                "}";
        
        // Realizar la solicitud POST
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("x-api-token", x_api_token)
                .header("x-api-key", x_api_key)
                .body(requestBody)
                .post(url)
                .then()
                .log().all() // muestra el resultado en la consola
                .extract().response();
        
        // Obtener el valor de "internalCode" del cuerpo de la respuesta
        int internalCode = response.jsonPath().getInt("internalCode");

        // Realizar la aserción
        assertThat(internalCode, equalTo(400));
        assertEquals(200, response.getStatusCode());
        
        String payload = response.jsonPath().getString("payload");

    
        return payload.equals("identifier "+orden+" sent");
    }


    public String consultarBoleta(String boleta) {
        
        String url = "https://qa-api.cencosud.cl/sm/cl/v1/crm/credivouchers/reportes/DatosTrx/" + boleta;

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .header("apikey", "5FL8UmFLNMhCv7n3h8KClFqPagfqifHR")
            .log().all()
            .get(url)
            .then()
            .log().all()
            .extract().response();
    
        return response.jsonPath().getString("fecha");
    }

   

    public void esperarHastaQueLasFechasCoincidan(String boleta) throws InterruptedException {
        boolean fechasCoinciden = false;
        int intentosMaximos = 10; // Define un número máximo de intentos para evitar un bucle infinito
        int contadorIntentos = 0;
        String fechaApi = "";
        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());
    
        while (!fechasCoinciden && contadorIntentos < intentosMaximos) {
            fechaApi = consultarBoleta(boleta);
    
            if (fechaApi.equals(fechaActual)) {
                fechasCoinciden = true;
                System.out.println("Las fechas coinciden: " + fechaApi);
            } else {
                System.out.println("Las fechas no coinciden. Fecha API: " + fechaApi + ", Fecha actual: " + fechaActual);
                contadorIntentos++;
                Thread.sleep(5000); // Espera 5 segundos antes de intentar nuevamente
            }
        }
    
        if (!fechasCoinciden) {
            System.out.println("Se alcanzó el número máximo de intentos sin coincidencia de fechas.");
        }
    }



    private String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


}
