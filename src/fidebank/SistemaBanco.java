
package fidebank;

import java.util.ArrayList;

public class SistemaBanco {

    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Cuenta> cuentas = new ArrayList<>();
    ArrayList<Transaccion> transacciones = new ArrayList<>();

    int siguienteTransaccion = 1;

    public boolean registrarCliente(String identificacion, String nombre, String pin) {

        if (identificacion == null || identificacion.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || pin == null || pin.trim().isEmpty()) {
            return false;
        }

        if (buscarCliente(identificacion) != null) {
            return false;
        }

        Cliente nuevo = new Cliente(identificacion, nombre, pin);
        clientes.add(nuevo);
        return true;
    }

    public Cliente validarAcceso(String identificacion, String pin) {

        for (Cliente cliente : clientes) {
            if (cliente.identificacion.equals(identificacion)
                    && cliente.validarPIN(pin)) {
                return cliente;
            }
        }

        return null;
    }

    public Cliente buscarCliente(String identificacion) {

        for (Cliente cliente : clientes) {
            if (cliente.identificacion.equals(identificacion)) {
                return cliente;
            }
        }

        return null;
    }

    public boolean crearCuenta(String identificacion, String numero,
            double saldo, String tipo) {

        if (buscarCliente(identificacion) == null
                || numero == null || numero.trim().isEmpty()
                || buscarCuenta(numero) != null
                || saldo < 0 || !Double.isFinite(saldo)
                || tipo == null) {
            return false;
        }

        Cuenta nueva;

        if (tipo.equalsIgnoreCase("Ahorros")) {
            nueva = new CuentaAhorro(numero, saldo);
        } else if (tipo.equalsIgnoreCase("Corriente")) {
            nueva = new CuentaCorriente(numero, saldo);
        } else {
            return false;
        }

        nueva.asignarCliente(identificacion);
        cuentas.add(nueva);
        return true;
    }

    public Cuenta buscarCuenta(String numero) {

        for (Cuenta cuenta : cuentas) {
            if (cuenta.numeroCuenta.equals(numero)) {
                return cuenta;
            }
        }

        return null;
    }

    public boolean verificarPropietario(String identificacion, String numero) {

        Cuenta cuenta = buscarCuenta(numero);

        if (cuenta != null) {
            return cuenta.identificacionCliente.equals(identificacion);
        }

        return false;
    }

    public double consultarSaldo(String identificacion, String numero) {

        if (!verificarPropietario(identificacion, numero)) {
            throw new IllegalArgumentException("Cuenta no autorizada");
        }

        return buscarCuenta(numero).consultarSaldo();
    }

    public boolean depositar(String identificacion, String numero, double monto) {

        if (!verificarPropietario(identificacion, numero)) {
            return false;
        }

        Cuenta cuenta = buscarCuenta(numero);

        if (cuenta.depositar(monto)) {
            registrarTransaccion("Deposito", monto, numero);
            return true;
        }

        return false;
    }

    public boolean retirar(String identificacion, String numero, double monto) {

        if (!verificarPropietario(identificacion, numero)) {
            return false;
        }

        Cuenta cuenta = buscarCuenta(numero);

        if (cuenta.retirar(monto)) {
            registrarTransaccion("Retiro", monto, numero);
            return true;
        }

        return false;
    }

    public boolean transferir(String identificacion, String origen,
            String destino, double monto) {

        if (!verificarPropietario(identificacion, origen)
                || destino == null || origen.equals(destino)
                || monto <= 0 || !Double.isFinite(monto)) {
            return false;
        }

        Cuenta cuentaOrigen = buscarCuenta(origen);
        Cuenta cuentaDestino = buscarCuenta(destino);

        if (cuentaDestino == null) {
            return false;
        }

        if (cuentaOrigen.retirar(monto)) {

            if (cuentaDestino.depositar(monto)) {
                registrarTransaccion("Transferencia enviada", monto, origen);
                registrarTransaccion("Transferencia recibida", monto, destino);
                return true;
            }

            cuentaOrigen.depositar(monto);
        }

        return false;
    }

    public void registrarTransaccion(String tipo, double monto, String numero) {

        Transaccion nueva = new Transaccion(
                siguienteTransaccion, tipo, monto, numero);

        transacciones.add(nueva);
        siguienteTransaccion++;
    }

    public String consultarMovimientos(String identificacion, String numero) {

        if (!verificarPropietario(identificacion, numero)) {
            return "Cuenta no autorizada";
        }

        String resultado = "Movimientos de la cuenta: " + numero + "\n\n";

        for (Transaccion transaccion : transacciones) {

            if (transaccion.numeroCuenta.equals(numero)) {
                resultado += transaccion.fecha + " - "
                        + transaccion.tipo + " - "
                        + transaccion.monto + "\n";
            }
        }

        return resultado;
    }

    public String obtenerComprobante(String identificacion, String numero) {

        if (!verificarPropietario(identificacion, numero)) {
            return "Cuenta no autorizada";
        }

        for (int i = transacciones.size() - 1; i >= 0; i--) {

            Transaccion transaccion = transacciones.get(i);

            if (transaccion.numeroCuenta.equals(numero)) {
                return transaccion.generarComprobante();
            }
        }

        return "No hay transacciones registradas";
    }
}
