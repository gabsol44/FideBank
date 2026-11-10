
package fidebank;

public class CuentaCorriente extends Cuenta {

    double sobregiro = 50000;

    public CuentaCorriente(String numeroCuenta, double saldo) {
        super(numeroCuenta, saldo, "Corriente");
    }

    @Override
    public boolean retirar(double monto) {
        if (monto > 0 && Double.isFinite(monto) && monto <= saldo + sobregiro) {
            saldo = saldo - monto;
            return true;
        }
        return false;
    }
}
