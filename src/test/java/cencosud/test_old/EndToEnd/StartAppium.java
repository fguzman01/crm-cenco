package cencosud.test_old.EndToEnd;

import cencosud.models.General;
import org.testng.annotations.Test;
import cencosud.dataproviders.DataProvidersJson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class StartAppium {

    @Test(priority = 1, description = "AsigarOrden", dataProvider = "dataAll", dataProviderClass = DataProvidersJson.class)
    public void asignarOrden(List<General> general) throws FileNotFoundException, InterruptedException {

        startServer();
    }

    public void startServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("cmd.exe /c start cmd.exe /k \"C:\\Users\\fguzmana\\AppData\\Local\\Programs\\Appium\\Appium.exe -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
            Thread.sleep(10000);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
