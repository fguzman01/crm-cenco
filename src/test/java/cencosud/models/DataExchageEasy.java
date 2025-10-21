package cencosud.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataExchageEasy {

    public String so;
    public String seccion;
    public String servicio;
    public int cantidad;
    public String sku;
    public String ean;
    public int precio;
    public String nombre;;


    public String getSo() {
        return so;
    }
    public void setSo(String so) {
        this.so = so;
    }

    //

    public String getSeccion() {
        return seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    //

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    //
    public int getCantidad (){return cantidad;}
    public void setCantidad (int cantidad){this.cantidad = cantidad;}

    //
    public int getPrecio (){return precio;}
    public void setPrecio (int precio){this.precio = precio;}
    //

    public String getSku() { return sku; }
    public void setSku(String sku) {
        this.sku = sku;
    }
    //
    public String getEan() { return ean; }
    public void setEan(String ean) { this.ean = ean;}
    //
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre;}



    public List listDetalleProducto = new ArrayList<>();
    public final ThreadLocal<List<ConcurrentHashMap<String, String>>> listaEasy = new ThreadLocal<List<ConcurrentHashMap<String,String>>>(){
        @Override
        protected synchronized List<ConcurrentHashMap<String,String>> initialValue(){
            return new ArrayList<>();
        }
    };



    public ThreadLocal<HashMap<String,String>> listPrueba1 = new ThreadLocal<HashMap<String, String>>() {
        @Override
        protected synchronized HashMap<String, String> initialValue() {
            return new HashMap<>();
        }
    };



//Diccionario de datos (ID/Key, Valor)

    private static DataExchageEasy instance = null;


    public final synchronized DataExchageEasy getInstance() {
        if (instance == null) {
            instance = new DataExchageEasy();

        }
        return instance;
    }

    public synchronized void setInstance(DataExchageEasy instance) {
        this.instance = instance;
    }


}
