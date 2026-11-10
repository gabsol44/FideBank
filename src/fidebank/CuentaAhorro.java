
package fidebank;

public class CuentaAhorro extends Cuenta {

    public CuentaAhorro(String numeroCuenta, double saldo) {
        super(numeroCuenta, saldo, "Ahorros");
    }

    @Override
    public boolean retirar(double monto) {
        if (monto > 0 && Double.isFinite(monto) && monto <= saldo) {
            saldo = saldo - monto;
            return true;
        }
        return false;
    }
}
