package ufrn.imd.sistema_bancario.models;

import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;

public class Conta {
    //@ spec_public
    private String numero;
    //@ spec_public
    private double saldo;

    //@ public invariant saldo >= 0;
    //@ public invariant numero != null && numero.length() > 0;

    /*@ public normal_behavior
      @   requires numero != null && numero.length() > 0;
      @   ensures this.numero.equals(numero);
      @   ensures this.saldo == 0;
      @*/
    //@ pure
    public Conta(String numero) {
        this.numero = numero;
        this.saldo = 0;
    }

    /*@ public normal_behavior
      @   requires valor > 0;
      @   assignable this.saldo;
      @   ensures this.saldo == \old(this.saldo) + valor;
      @   ensures this.saldo > \old(this.saldo);
      @ also
      @ public exceptional_behavior
      @   requires valor <= 0;
      @   assignable \nothing;
      @   signals_only ValorInvalidoException;
      @*/
    public void creditar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        this.saldo += valor;
    }

    /*@ public normal_behavior
      @   requires valor > 0 && this.saldo >= valor;
      @   assignable this.saldo;
      @   ensures this.saldo == \old(this.saldo) - valor;
      @   ensures this.saldo < \old(this.saldo);
      @ also
      @ public exceptional_behavior
      @   requires valor <= 0;
      @   assignable \nothing;
      @   signals_only ValorInvalidoException;
      @*/
    public void debitar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        this.saldo -= valor;
    }

    /*@ public normal_behavior
      @   assignable \nothing;
      @   ensures \result != null;
      @   ensures \result.length() > 0;
      @   ensures \result == numero;
      @*/
    //@ spec_pure
    public String getNumero() {
        return numero;
    }

    /*@ public normal_behavior
      @   requires numero != null && numero.length() > 0;
      @   assignable this.numero;
      @   ensures this.numero.equals(numero);
      @*/
    public void setNumero(String numero) {
        this.numero = numero;
    }


    /*@ public normal_behavior
      @   assignable \nothing;
      @   ensures \result >= 0;
      @   ensures \result == saldo;
      @*/
    //@ spec_pure
    public double getSaldo() {
        return saldo;
    }

    /*@ public normal_behavior
      @   requires saldo >= 0;
      @   assignable this.saldo;
      @   ensures this.saldo == saldo;
      @*/
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}