package cencosud.models;

public class DataPedidos {

    public String rut;
    public String medioPago;
    public String monto;
    public String tienda;
    public String entrega;

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @Override
    public String toString() {
        return "DataPedidos [rut=" + rut + ", medioPago=" + medioPago + ", monto=" + monto + ", tienda=" + tienda
                + ", entrega=" + entrega + "]";
    }

    


}
