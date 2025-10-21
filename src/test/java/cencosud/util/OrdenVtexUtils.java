package cencosud.util;

import java.util.List;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;

import cencosud.models.OrdenVtex;
import cencosud.pages.Api.vtexCollection.VtexPage;

public class OrdenVtexUtils {
    VtexPage vtexPage = new VtexPage();

   

    public void actualizarValorOrdenYGuardar(String idOrden, String nuevoValor, String campo) throws Exception {
        // Leer las ordenes del archivo `OrdenesDebug.json`
        List<OrdenVtex> ordenes = vtexPage.readOrdenesFromJson("output/outputFinal/OrdenesDebug.json");
        
        for (OrdenVtex orden : ordenes) {
            if (orden.getCompraId().equals(idOrden)) {
                switch (campo) {
                    case "ordenStatus":
                        orden.setOrdenStatus(nuevoValor);
                        break;
                    case "rut":
                        orden.setRut(nuevoValor);
                        break;
                    case "boleta":
                        orden.setBoleta(nuevoValor);
                        break;
                    default:
                        throw new Exception("Campo desconocido: " + campo);
                }
                break;
            }
        }
        
        // Escribir las ordenes modificadas en `OrdenesDebug.json`
        JsonFileUtils.writeToJsonFile(ordenes, "output/outputFinal/", "OrdenesDebug.json");
    }
    



    private static String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
