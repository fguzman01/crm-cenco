package cencosud.models;

public class CreacionOrdenData {

    public String user;
    public String pass;
    public String orden;
    public String estado;
    public String mailPicker;
    public String nombrePicker;
    private String monto;
    private String fecha;

   
    public String getMonto() {
        return monto;
    }
    public void setMonto(String monto) {
        this.monto = monto;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getMailPicker() {
        return mailPicker;
    }
    public void setMailPicker(String mailPicker) {
        this.mailPicker = mailPicker;
    }
    public String getNombrePicker() {
        return nombrePicker;
    }
    public void setNombrePicker(String nombrePicker) {
        this.nombrePicker = nombrePicker;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getOrden() {
        return orden;
    }
    public void setOrden(String orden) {
        this.orden = orden;
    }
    @Override
    public String toString() {
        return "CreacionOrdenData [user=" + user + ", pass=" + pass + ", orden=" + orden + ", estado=" + estado
                + ", mailPicker=" + mailPicker + ", nombrePicker=" + nombrePicker + ", monto=" + monto + ", fecha="
                + fecha + "]";
    }

    
    
    

     
}
