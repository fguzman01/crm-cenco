package cencosud.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class VtexApiExample {

    private static final String APP_KEY = "vtexappkey-jumboprepro-KMSANI";
    private static final String APP_TOKEN = "YXXIAWNNSCBYLOUMRWUYQSAGGXBGHDTLFFKCPCJHKEPGIVNRRONATYEFXRCCUHFOJQSGERXONNOMDTYKKJPTPSSHAEFYAGMCYAXRCHMARZNGJSBDTRSLRMZQYLFALYOG";

    public static void main(String[] args) throws InterruptedException {
        RestAssured.baseURI = "https://jumboprepro.vtexcommercestable.com.br:443/api";
        String userId = "c6c1b400-8ef5-11ee-8452-0efa660bec3d";

        //int totalEliminados = 0;
        int totalNoEliminados = 0;
        boolean hayDireccionesParaEliminar;
        boolean esPrimerCiclo = true;
        int totalEliminados = 0;
        int totalProcesados = 0;
        List<String> addressIds = null;
        
        do {
            addressIds = obtenerAddressIds(userId);
            if (addressIds != null && !addressIds.isEmpty()) {
                // Añadir el total de addressIds en esta iteración a totalProcesados
                totalProcesados += addressIds.size();

                // Procesar y eliminar todos los addressIds excepto el último
                for (int i = 0; i < addressIds.size() - 1; i++) {
                    if (eliminarDireccion(addressIds.get(i))) {
                        totalEliminados++;
                    }
                }
            }

            Thread.sleep(5000);
        } while (addressIds != null && addressIds.size() > 1);

        System.out.println("Total de AddressId procesados: " + totalProcesados);
        //System.out.println("Total de AddressId eliminados: " + totalEliminados);
    }

    
    //Configuración de API
    private static RequestSpecification givenConfigured() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("X-VTEX-API-AppKey", APP_KEY)
                .header("X-VTEX-API-AppToken", APP_TOKEN);
    }

    
    //Consulta de addressIds de usuario
    public static List<String> obtenerAddressIds(String userId) {
        Response response = givenConfigured()
                .get("/dataentities/AD/search?_fields=id&_where=userId=" + userId + "&_pageSize=100");

        System.out.println("Respuesta completa de la API: " + response.asString());

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getList("id");
        } else {
            System.out.println("Error en la solicitud: " + response.getStatusCode());
            return null;
        }
    }

    
    //Eliminación de registros de addressIds
    public static boolean eliminarDireccion(String addressId) {
        Response response = givenConfigured()
                .delete("/dataentities/AD/documents/" + addressId);

        System.out.println("Respuesta para addressId " + addressId + ": " + response.getStatusCode());
        return response.getStatusCode() == 200;
    }
}






