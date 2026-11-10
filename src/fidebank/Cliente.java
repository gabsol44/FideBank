package fidebank;

public class Cliente {

    String identificacion;
    String nombre;
    String pin;

    public Cliente(String identificacion, String nombre, String pin) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.pin = pin;
    }

    public boolean validarPIN(String pinIngresado) {
        return pin.equals(pinIngresado);
    }

    public String consultarDatos() {
        return "Identificación: " + identificacion
                + "\nNombre: " + nombre;
    }
}
