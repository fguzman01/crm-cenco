package cencosud.models;

public class General {


    private String user;
    private String pass;
    private String userApp;
    private String passApp;
    private String nombre;


    public General() {
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

    //

    public String getUserApp() {
        return userApp;
    }

    public void setUserApp(String userApp) {
        this.userApp = userApp;

    }

    public String getPassApp() {
        return passApp;
    }

    public void setPassApp(String passApp) {
        this.passApp = passApp;

    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;

    }



    @Override
    public String toString() {
        return "General [ user = "+ user +", pass = " + pass +
                ", nombre = "+nombre+"]";
    }





}
