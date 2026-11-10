package fidebank;

public class Cuenta {

    String numeroCuenta;
    double saldo;
    String tipoCuenta;
    String identificacionCliente;

    public Cuenta(String numeroCuenta, double saldo, String tipoCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
    }

    public void asignarCliente(String identificacion) {
        identificacionCliente = identificacion;
    }

    public double consultarSaldo() {
        return saldo;
    }

    public boolean depositar(double monto) {
        if (monto > 0 && Double.isFinite(monto)) {
            saldo = saldo + monto;
            return true;
        }
        return false;
    }

    public boolean retirar(double monto) {
        if (monto > 0 && Double.isFinite(monto) && monto <= saldo) {
            saldo = saldo - monto;
            return true;
        }
        return false;
    }
}
