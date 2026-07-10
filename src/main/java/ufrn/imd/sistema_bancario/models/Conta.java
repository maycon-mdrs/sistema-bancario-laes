package ufrn.imd.sistema_bancario.models;

import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;


public class Conta {
    //@ spec_public
    private String numero;
    //@ spec_public
    private double saldo;

    //@ public invariant saldo >= 0;
    //@ public invariant numero != null && numero.length > 0;

    //@ requires numero != null && !numero.length > 0;
    //@ assignable numero, saldo;
    //@ ensures this.numero.equals(numero);
    //@ ensures this.saldo == 0;
    public Conta(String numero) {
        this.numero = numero;
        this.saldo = 0;
    }

    //@ requires valor > 0;
    //@ assignable saldo;
    //@ ensures saldo == \old(saldo) + valor;
    //@ ensures saldo > \old(saldo);
    public void creditar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        this.saldo += valor;
    }

    //@ requires valor > 0 && saldo >= valor;
    //@ assignable saldo;
    //@ ensures saldo == \old(saldo) - valor;
    //@ ensures saldo < \old(saldo);
    public void debitar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        this.saldo -= valor;
    }

    //@ pure
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    //@ pure
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
