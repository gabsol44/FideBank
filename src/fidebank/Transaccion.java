
package fidebank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {

    int idTransaccion;
    String tipo;
    double monto;
    String fecha;
    String numeroCuenta;

    public Transaccion(int idTransaccion, String tipo,
            double monto, String numeroCuenta) {

        this.idTransaccion = idTransaccion;
        this.tipo = tipo;
        this.monto = monto;
        this.numeroCuenta = numeroCuenta;

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        fecha = ahora.format(formato);
    }

    public String generarComprobante() {

        return "FideBank - Comprobante"
                + "\n---------------------------"
                + "\nTransacción: " + idTransaccion
                + "\nFecha: " + fecha
                + "\nTipo: " + tipo
                + "\nCuenta: " + numeroCuenta
                + "\nMonto: " + monto
                + "\nEstado: Completada";
    }
}
