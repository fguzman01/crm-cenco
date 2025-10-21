package cencosud.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;  // Para manejar excepciones de entrada/salida
import java.util.List;  // Para utilizar la clase List
import java.time.LocalDateTime;  // Para trabajar con la fecha y la hora
import java.time.format.DateTimeFormatter;  // Para formatear la fecha y la hora

import cencosud.business.apiNegocio;
import cencosud.models.OrdenVtex;
import cencosud.pages.Api.vtexCollection.VtexPage;

public class OrdenVtexTest {
    VtexPage vtexPage = new VtexPage();
    public apiNegocio api = new apiNegocio();
    

    @Test
    public void printAndModifyOrdenes() throws IOException {

        System.out.println("Comienza el test");
        // // Leer las ordenes del archivo JSON
        // List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/Ordeneslatest.json");

        // // Imprimir las ordenes y modificar los datos
        // for (OrdenVtex orden : ordenes) {
        //     System.out.println("Orden original: " + orden);

        //     // Modificar los datos
        //     orden.setRut("11.111.111-1");
        //     orden.setOrdenStatus("Facturado");

        //     System.out.println("Orden modificada: " + orden);
        // }

        // // Escribir las ordenes modificadas en un nuevo archivo JSON
        // JsonFileUtils.writeToJsonFile(ordenes, "output/outputFinal/Ordenes-Finales_" + getCurrentDateTime() + ".json");
    }

    private String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @AfterMethod
    public void afterTest () throws IOException{
        List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/Ordeneslatest.json");

        // Imprimir las ordenes y modificar los datos
        for (OrdenVtex orden : ordenes) {
            System.out.println("Orden original: " + orden);

            // Modificar los datos
            orden.setRut("11.111.111-1");
            orden.setOrdenStatus("Facturado");

            System.out.println("Orden modificada: " + orden);
        }

        // Escribir las ordenes modificadas en un nuevo archivo JSON
        //JsonFileUtils.writeToJsonFile(ordenes, "output/outputFinal/Ordenes-Finales_" + getCurrentDateTime() + ".json");
        //JsonFileUtils.writeToJsonFile(ordenes, directoryPath, filename);
    }

    

    @BeforeSuite
    public void beforeSuite() throws Exception {
        //Crear orden API Collections
        api.crearOrdenJumbo("Jumbo", "jumbo_dad_prime_menor");


    }
}

