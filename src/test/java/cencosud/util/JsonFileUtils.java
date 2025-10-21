package cencosud.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.*;
import java.util.*;
import com.google.gson.Gson;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.lang.reflect.Type;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.io.FileReader;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;


import cencosud.models.InputData;
import cencosud.models.OrdenVtex;

public class JsonFileUtils {

    

    public static void writeToJsonFile(List<OrdenVtex> ordenes, String directoryPath, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
    
        // Crear el contenedor de órdenes
        Map<String, List<OrdenVtex>> ordenesVtex = new HashMap<>();
        ordenesVtex.put("ordenesVtex", ordenes);
    
        // Convertir a JSON con formato "pretty"
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ordenesVtex);
    
        // Crear directorio si no existe
        File directory = new File(directoryPath);
        if (!directory.exists()){
          directory.mkdirs();
        }
    
        File latestFile = new File(directoryPath + filename);
    
        // Simplemente sobreescribir el archivo "Ordeneslatest.json"
        FileUtils.writeStringToFile(latestFile, json, StandardCharsets.UTF_8);
    }

    public static void writeToJsonFileDebug(List<OrdenVtex> ordenes, String directoryPath, String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
    
        // Crear el contenedor de órdenes
        Map<String, List<OrdenVtex>> ordenesVtex = new HashMap<>();
        ordenesVtex.put("ordenesVtex", ordenes);
    
        // Convertir a JSON con formato "pretty"
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ordenesVtex);
    
        // Crear directorio si no existe
        File directory = new File(directoryPath);
        if (!directory.exists()){
          directory.mkdirs();
        }
    
        // Generar un archivo nuevo con un nombre basado en la fecha y hora actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String newFilename = "debug_" + LocalDateTime.now().format(formatter) + ".json";
        File newFile = new File(directoryPath + newFilename);
    
        // Escribir la información en el archivo nuevo
        FileUtils.writeStringToFile(newFile, json, StandardCharsets.UTF_8);
    }
    
    

    public static List<InputData> readDataFromJsonFile(String filename) {
     try{
      String dataPath = String.format(System.getProperty("user.dir") + "/inputs/dataCollections/%s.json", filename);
      
      ObjectMapper mapper = new ObjectMapper();
      //List<InputData> data;
      return Arrays.asList(mapper.readValue(new File(dataPath), InputData[].class));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read JSON file", e);
  }
    
  }

  public static File writeDataToJsonTempFile(Object data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    File tempFile = File.createTempFile("data-", ".json", new File(System.getProperty("user.dir") + "/inputs/dataCollections/"));
    mapper.writeValue(tempFile, data);
    return tempFile;
}



public static <T> T readFromJsonFile(String filePath, Type typeOfT) throws IOException {
    Gson gson = new Gson();

    // Lee el archivo y conviértelo en un objeto JsonObject
    JsonObject jsonObject = gson.fromJson(new FileReader(filePath), JsonObject.class);

    // Obten la lista de ordenes a partir de la clave "ordenesVtex" y conviértela al tipo de lista esperado
    JsonElement ordenesVtexElement = jsonObject.get("ordenesVtex");
    T ordenesVtex = gson.fromJson(ordenesVtexElement, typeOfT);

    return ordenesVtex;
}

public static File getLatestFilefromDir(String dirPath){
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
        return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
           lastModifiedFile = files[i];
       }
    }
    return lastModifiedFile;
}




//Respaldar archivo output final

public static void backupFileWithTimestamp(String sourceFilePath, String backupDirectoryPath) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = Paths.get(sourceFilePath).getFileName().toString();
            String backupPath = backupDirectoryPath + filename.replace(".json", "_") + timestamp + ".json";

            Files.copy(Paths.get(sourceFilePath), Paths.get(backupPath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup de " + filename + " guardado en: " + backupPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
}



public static void copyAndBackupLatestFile() {
  try {
      Path outputFolder = Paths.get("output/");
      Optional<File> mostRecentFile = Arrays.stream(outputFolder.toFile().listFiles())
          .max(Comparator.comparingLong(File::lastModified));
      
      if (mostRecentFile.isPresent()) {
          String sourceFilename = mostRecentFile.get().getName();
          Path source = Paths.get("output/" + sourceFilename);
          
          String targetFilename = "datos-ordenes-finales.json";
          Path target = Paths.get("inputs/inputOrdenesFinal/" + targetFilename);
          
          // Verifica si el archivo objetivo existe y realiza una copia de seguridad
          if (Files.exists(target)) {
              // Crea el nombre del archivo de respaldo con la fecha y hora
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
              String backupFilename = targetFilename.replace(".json", "_") + LocalDateTime.now().format(formatter) + ".json";
              
              // Crea el directorio de respaldo si no existe
              File backupDir = new File("inputs/inputOrdenesFinal/respaldo");
              if (!backupDir.exists()) {
                  backupDir.mkdirs();
              }

              Path backupPath = Paths.get("inputs/inputOrdenesFinal/respaldo/" + backupFilename);
              Files.move(target, backupPath, StandardCopyOption.REPLACE_EXISTING);
          }
          
          // Copia el archivo más reciente al directorio objetivo
          Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
          
      } else {
          System.out.println("No se encontraron archivos en la carpeta de salida.");
      }
  } catch (IOException e) {
      System.err.println("Se produjo un error al copiar y respaldar el archivo: " + e.getMessage());
  }
 }
}
