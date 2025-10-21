package cencosud.dataproviders;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cencosud.models.General;
import cencosud.models.OrdenVtex;
import cencosud.models.CreacionOrdenData;
import org.testng.annotations.DataProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import static cencosud.util.JsonUtils.deserialize;

public class DataProvidersJson {

    //public static String pathFile = System.getProperty("user.dir") + "/inputs/pf1pf3Tests.json";
    
    //public static String pathFileFormat = System.getProperty("user.dir") + "/inputs/%s.json";

    /*
     Confuguración DataProvider 
     para genración de datos
    */


    @DataProvider(name = "TestGeneraDataJson", parallel = false)
    public static Object[][] datosJsonCRM() throws IOException {
        List<CreacionOrdenData> setPrueba = obtenerDatos();
        Object[][] objArray = new Object[setPrueba.size()][];
        for (int i = 0; i < setPrueba.size(); i++) {
            objArray[i] = new Object[1];
            objArray[i][0] = Arrays.asList(setPrueba.get(i));
        }
        return objArray;
    }

    public static List <CreacionOrdenData>obtenerDatos () throws FileNotFoundException {
        String pathFile = System.getProperty("user.dir") + "/inputs/pf1pf3Tests.json";
        String pathFileFormat = System.getProperty("user.dir") + "/inputs/%s.json";
        
        List<CreacionOrdenData> testDataList = new ArrayList<>();
        CreacionOrdenData creacionOrdenData;
        String nombreArchivoJson = "inputGeneraData";
        JsonObject dataInput = deserialize(String.format(pathFileFormat, nombreArchivoJson));
        JsonArray dataOrden = dataInput.get("tests").getAsJsonArray();  
        for (JsonElement jsonElement : dataOrden) {
            creacionOrdenData = new CreacionOrdenData();
            JsonObject dataCreacionOrdenTestObject = jsonElement.getAsJsonObject();
            creacionOrdenData.setOrden(dataCreacionOrdenTestObject.get("orden").toString().replace("\"", ""));
            creacionOrdenData.setUser(dataCreacionOrdenTestObject.get("user").toString().replace("\"", ""));
            creacionOrdenData.setPass(dataCreacionOrdenTestObject.get("pass").toString().replace("\"", ""));
            creacionOrdenData.setEstado(dataCreacionOrdenTestObject.get("estado").toString().replace("\"", ""));
            creacionOrdenData.setMailPicker(dataCreacionOrdenTestObject.get("mailPicker").toString().replace("\"", ""));
            creacionOrdenData.setNombrePicker(dataCreacionOrdenTestObject.get("nombrePicker").toString().replace("\"", ""));

            testDataList.add(creacionOrdenData); 
        }        
        return testDataList;
    }

    @DataProvider(name = "DataFinal", parallel = false)
    public static Object[][] ordenesJsonCRM() throws IOException {
        List<OrdenVtex> setPrueba = obtenerDatosFinal();
        Object[][] objArray = new Object[setPrueba.size()][];
        for (int i = 0; i < setPrueba.size(); i++) {
            objArray[i] = new Object[1];
            objArray[i][0] = Arrays.asList(setPrueba.get(i));
        }
        return objArray;
    }

    

    public static List <OrdenVtex>obtenerDatosFinal () throws FileNotFoundException {
        String pathFile = System.getProperty("user.dir") + "/output/outputFinal/pf1pf3Tests.json";
        String pathFileFormat = System.getProperty("user.dir") + "/output/outputFinal/%s.json";
        
        List<OrdenVtex> testDataList = new ArrayList<>();
        OrdenVtex ordenVtex;
        String nombreArchivoJson = "OrdenesDebug";
        JsonObject dataInput = deserialize(String.format(pathFileFormat, nombreArchivoJson));
        JsonArray dataOrden = dataInput.get("ordenesVtex").getAsJsonArray();
        for (JsonElement jsonElement : dataOrden) {
            ordenVtex = new OrdenVtex();
            JsonObject dataCreacionOrdenTestObject = jsonElement.getAsJsonObject();
            ordenVtex.setCompraId(dataCreacionOrdenTestObject.get("compraId").toString().replace("\"", ""));
            ordenVtex.setMonto(dataCreacionOrdenTestObject.get("monto").toString().replace("\"", ""));
            ordenVtex.setFecha(dataCreacionOrdenTestObject.get("fecha").toString().replace("\"", ""));
            ordenVtex.setPaymentMethod(dataCreacionOrdenTestObject.get("paymentMethod").toString().replace("\"", ""));
            ordenVtex.setPurchaseStatus(dataCreacionOrdenTestObject.get("purchaseStatus").toString().replace("\"", ""));
            ordenVtex.setEmail(dataCreacionOrdenTestObject.get("email").toString().replace("\"", ""));
            ordenVtex.setApp(dataCreacionOrdenTestObject.get("app").toString().replace("\"", ""));
            ordenVtex.setDeliveryType(dataCreacionOrdenTestObject.get("deliveryType").toString().replace("\"", ""));
            ordenVtex.setSalesChannel(dataCreacionOrdenTestObject.get("salesChannel").toString().replace("\"", ""));
            ordenVtex.setTienda(dataCreacionOrdenTestObject.get("tienda").toString().replace("\"", ""));
            ordenVtex.setOrdenStatus(dataCreacionOrdenTestObject.get("ordenStatus").toString().replace("\"", ""));
            ordenVtex.setBoleta(dataCreacionOrdenTestObject.get("boleta").toString().replace("\"", ""));
            ordenVtex.setBoleta(dataCreacionOrdenTestObject.get("fechaBoleta").toString().replace("\"", ""));
            ordenVtex.setBoleta(dataCreacionOrdenTestObject.get("validaBoleta").toString().replace("\"", ""));
            ordenVtex.setBoleta(dataCreacionOrdenTestObject.get("tipoPedido").toString().replace("\"", ""));
            testDataList.add(ordenVtex); 

        }  

        return testDataList;

    }


    
    @DataProvider ()
    public static Object[][] dataAll() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
        String path = "";
        path= checkAWS();
        System.out.println(path);
        Gson gson = new Gson();
        General general = gson.fromJson(new FileReader(path + "dataGeneral.json"), General.class);
        return new Object[][] {{ Arrays.asList(general) }};
    }


    private static String checkAWS()  {
        String appiumON = System.getProperty("appiumON", "N");
        String path = "";

        if (appiumON.equals("S")) {
            path = "data/";
        }else{
            path = "src/test/resources/data/";
        }
        return path;
    }

}
