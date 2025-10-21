package cencosud.tests.testUnitarioBeeTrack;

import java.io.IOException;
import org.testng.annotations.Test;

import cencosud.business.apiNegocio;
import cencosud.dataproviders.DataProvidersJson;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import cencosud.models.OrdenVtex;

public class CambiaEstadoBeetrack {

    public apiNegocio api = new apiNegocio();

    @Epic(" Flujo API Beetrack")
    @Feature(" API - BO ")
    @Test(description = "Cambia estado", 
          dataProvider = "DataFinal",
          dataProviderClass = DataProvidersJson.class)
    public void BO_Cambia_Estado(OrdenVtex testData) throws InterruptedException, IOException{

        //api.actualizarEstadoOrden(testData.getCompraId());
    
   }
}
