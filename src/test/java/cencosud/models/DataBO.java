package cencosud.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DataBO {

    public String so;
    public String seccion;
    public String servicio;
    public String cantidad;
    public String codSap;

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
    public String getCantidad (){return cantidad;}
    public void setCantidad (String cantidad){this.cantidad = cantidad;}
    //

    public String getCodSap (){return codSap;}
    public void setCodSap (String codSap){this.codSap = codSap;}




    public List listDetalleProducto = new ArrayList<>();
    public ThreadLocal<List<ConcurrentHashMap<String, String>>> listEasy = new ThreadLocal<List<ConcurrentHashMap<String,String>>>() {
        @Override
        protected synchronized List<ConcurrentHashMap<String,String>> initialValue(){
            return new ArrayList<>();
        }
    };


    //Diccionario de datos (ID/Key, Valor)

    private static DataBO instance = null;


    public final synchronized DataBO getInstance() {
        if (instance == null) {
            instance = new DataBO();

        }
        return instance;
    }

    public synchronized void setInstance(DataBO instance) {
        this.instance = instance;
    }

}
