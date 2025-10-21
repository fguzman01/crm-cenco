package cencosud.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class PostmanRunner {

    

    public String runCollection(String collectionFileName, File dataFile) {
        StringBuilder output = new StringBuilder();
        try {
            String collectionPath  = String.format(System.getProperty("user.dir") + "/inputs/collections/%s.postman_collection.json", collectionFileName);
            String dataPath = dataFile.getAbsolutePath();
    
            ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-Command", "newman run " + collectionPath + " -d " + dataPath);
    
            pb.redirectErrorStream(true);  // redirige la salida de error a la salida estándar
            Process process = pb.start();
    
            // Muestra la salida estándar y la salida de error
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append(System.lineSeparator());
                System.out.println(line);
            }
    
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return output.toString();
    }
    
    
}


