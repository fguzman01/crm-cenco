package cencosud.business;

import java.io.IOException;
import cencosud.configuration.BaseConfig;
import cencosud.exception.PatternNotFoundException;
import cencosud.models.OrdenVtex;
import cencosud.pages.Api.bo.BeetrackApi;
import cencosud.pages.Api.vtexCollection.VtexPage;
import cencosud.util.OrdenVtexUtils;
import io.qameta.allure.Step;


public class apiNegocio extends BaseConfig{

    VtexPage vtexPage = new VtexPage();
    private BeetrackApi beetrackApi;
    private OrdenVtexUtils ordenVtexUtils = new OrdenVtexUtils();
    backOfficeNegocio backOfficeNegocio = new backOfficeNegocio();
    


    public apiNegocio() {
        this.beetrackApi = new BeetrackApi();
    }

     /***
     * Generación orden Jumbo
     */

    @Step("Crear orden Jumbo Vtex")
    public void crearOrdenJumbo (String collectionFileName, String dataFileName) throws IOException, PatternNotFoundException{
        vtexPage.generacionOrden(collectionFileName, dataFileName);
    }

     

@Step("Actualizar estado de orden en base a respuesta de Beetrack API")
public void actualizarEstadoOrden(OrdenVtex orden, String status) throws Exception {
    // Obtenemos el ID de la orden
    String idOrden = orden.getCompraId();
    // Utilizamos la API de Beetrack para intentar cambiar el estado de la orden
    boolean cambioExitoso = beetrackApi.cambiarEstado(idOrden, status);
    System.out.println("Sleep");
    Thread.sleep(5000);
    
    if (cambioExitoso) {
        System.out.println("Cambio estado");
        ordenVtexUtils.actualizarValorOrdenYGuardar(idOrden, "Entregado", "ordenStatus");
    } else {
        throw new Exception("La API de Beetrack no pudo cambiar el estado de la orden " + idOrden);
    }
}

 
    }



