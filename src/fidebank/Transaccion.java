
package fidebank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

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

        NumberFormat formatoMonto =
                NumberFormat.getNumberInstance(
                        new Locale("es", "CR"));

        formatoMonto.setMinimumFractionDigits(2);
        formatoMonto.setMaximumFractionDigits(2);

        return "FideBank - Comprobante"
                + "\n---------------------------"
                + "\nTransacción: " + idTransaccion
                + "\nFecha: " + fecha
                + "\nTipo: " + tipo
                + "\nCuenta: " + numeroCuenta
                + "\nMonto: ₡" + formatoMonto.format(monto)
                + "\nEstado: Completada";
    }
}
