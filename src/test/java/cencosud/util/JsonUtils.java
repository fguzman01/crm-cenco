package cencosud.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtils  {

    

    public static JsonObject deserialize(String pathFile) throws FileNotFoundException {
        File inputData = new File(pathFile);

        JsonElement jsonElement = JsonParser.parseReader(new FileReader(inputData));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return jsonObject;
    }

    public static JsonObject StringToJsonObject(String string){
        JsonElement jsonElement = JsonParser.parseString(string);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return jsonObject;
    }
}

