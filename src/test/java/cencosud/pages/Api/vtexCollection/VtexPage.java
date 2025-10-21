package cencosud.pages.Api.vtexCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import cencosud.configuration.BaseUtils;
import cencosud.exception.PatternNotFoundException;
import cencosud.models.InputData;
import cencosud.models.OrdenVtex;
import cencosud.util.DateFile;
import cencosud.util.JsonFileUtils;
import cencosud.util.PostmanRunner;
import cencosud.util.RegexUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class VtexPage extends BaseUtils{
    PostmanRunner postmanRunner = new PostmanRunner();
    List<OrdenVtex> ordenes = new ArrayList<>();
    DateFile dateFile = new DateFile();
    

    public boolean generacionOrden(String collectionFileName, String dataFileName) throws IOException, PatternNotFoundException {
        BaseUtils.println(BaseUtils.ANSI_YELLOW + " Collection crear orden ");
        List<InputData> inputDataList = JsonFileUtils.readDataFromJsonFile(dataFileName);
    
        // Variable para llevar registro de errores

        boolean hadError = false;
    
        for (InputData inputData : inputDataList) {
            List<InputData> singleInputDataList = new ArrayList<>();
            singleInputDataList.add(inputData);
    
            File tempDataFile = JsonFileUtils.writeDataToJsonTempFile(singleInputDataList);
            String output = postmanRunner.runCollection(collectionFileName, tempDataFile);
            System.out.println(output);
    
            System.out.println("eliminar espacios");
            String outputWithoutNewlinesAndSpaces = output.replaceAll("\\s*\n\\s*", " ");
    
            OrdenVtex orden = parseOrderData(outputWithoutNewlinesAndSpaces, inputData);
    
            System.out.println("ID de compra: " + orden.getCompraId() + ", Monto: " + orden.getMonto() + ", Fecha: " + orden.getFecha()
                    + " Metodo de Pago : " + orden.getPaymentMethod() + ", Status Transaccion: " + orden.getPurchaseStatus() 
                    + ", Tienda: " + orden.getTienda() + ", Orden status :" + orden.getOrdenStatus());
    
            System.out.println("Dicionario orden: " + orden);
    
            if (orden.getPurchaseStatus() != null && orden.getPurchaseStatus().contains("Authorized - Transaccion apro")) {
                ordenes.add(orden);
            } else {
                System.out.println("Error transacción status :" + orden.getPurchaseStatus());
                //hadError = true; // Se ha encontrado un error
            }
            tempDataFile.delete(); // Eliminar el archivo temporal después de usarlo
        }
    
        // Cambio de directorio y nombre del archivo
        JsonFileUtils.writeToJsonFile(ordenes, "output/outputFinal/", "OrdenesDebug.json");
    
        // Retorna si se encontró un error
        return true;
    }
    
    

    /*
     * Extración de valores
     */
    
    private OrdenVtex parseOrderData(String outputWithoutNewlinesAndSpaces, InputData inputData) throws PatternNotFoundException {
        
        System.out.println("Captura datos");
        
        

        String monto = RegexUtils.findPattern("Total Pagado: \\$(\\d+)", outputWithoutNewlinesAndSpaces);
        String fecha = RegexUtils.findPattern("Fecha: (\\d{4}-\\d{2}-\\d{2})", outputWithoutNewlinesAndSpaces);
        String compraId = RegexUtils.findPattern("Id de compra: \\D*(\\d+)\\D*", outputWithoutNewlinesAndSpaces);
        //String compraId = RegexUtils.findPattern("Id de compra: (.*?)\\\\n'", outputWithoutNewlinesAndSpaces);
        String paymentMethod = RegexUtils.findPattern("' Medio de pago: (.*?)\\\\n'", outputWithoutNewlinesAndSpaces);
        String purchaseStatus = RegexUtils.findPattern("Estado de la compra: ((.*?)(?=\\s*│|\\s*\\\\n))", outputWithoutNewlinesAndSpaces);
        purchaseStatus = purchaseStatus.replaceAll("\\s*\\|\\s*", "").replaceAll("\\\\n", "").trim();
        
        
    
        OrdenVtex orden = new OrdenVtex();
        
        orden.setCompraId(compraId);
        orden.setMonto(monto);
        orden.setFecha(fecha);
        orden.setPaymentMethod(paymentMethod);
        orden.setPurchaseStatus(purchaseStatus);
        orden.setEmail(inputData.getEmail());
        orden.setApp(inputData.getApp());
        orden.setDeliveryType(inputData.getDeliveryType());
        orden.setSalesChannel(String.valueOf(inputData.getSalesChannel()));
        orden.setOrdenStatus("Pendiente picking");
        
        // Asignar la tienda basada en app y salesChannel
        //try{
        String app = inputData.getApp();
        int salesChannel = inputData.getSalesChannel();
        String tienda = STORE_MAP.get(Pair.of(app, salesChannel));
        
        try{
        if (tienda == null) {
              throw new IllegalArgumentException("Invalid combination of app and salesChannel");
        }

        }catch (Exception e){
        System.out.println("Exception" + e);
        } 
        orden.setTienda(tienda);
        return orden;

        
    }


    

    public List<OrdenVtex> readOrdenesFromJson(String filePath) throws IOException {
        Type listType = new TypeToken<List<OrdenVtex>>(){}.getType();
        List<OrdenVtex> ordenes = JsonFileUtils.readFromJsonFile(filePath, listType);
        return ordenes;
    }
    



    private static final Map<Pair<String, Integer>, String> STORE_MAP = new HashMap<>();
    static {
    STORE_MAP.put(Pair.of("jumbodev", 1), "Jumbo Los Andes");
    STORE_MAP.put(Pair.of("jumbodev", 16), "Jumbo Viña del Mar");
    STORE_MAP.put(Pair.of("spiddev", 42), "SPID35 Lo Castillo");
    STORE_MAP.put(Pair.of("sisadev", 1), "SISA Pedro Fontova");
    STORE_MAP.put(Pair.of("spiddev", 1), "SPID35 Lo Castillo");
}

        
}

    

    
