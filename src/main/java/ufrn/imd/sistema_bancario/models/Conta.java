package ufrn.imd.sistema_bancario.models;

import lombok.Getter;
import lombok.Setter;
import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;

@Getter
@Setter
public class Conta {
    //@ spec_public
    private String numero;
    //@ spec_public
    private double saldo;

    //@ public invariant saldo >= 0
    //@ public invariant numero != null && !numero.trim().isEmpty()

    //@ requires numero != null && !numero.trim().isEmpty()
    //@ assignable numero, saldo
    //@ ensures this.numero.equals(numero)
    //@ ensures this.saldo == 0
    public Conta(String numero) {
        this.numero = numero;
        this.saldo = 0;
    }

    //@ requires valor > 0
    //@ assignable saldo
    //@ ensures saldo == \old(saldo) + valor
    //@ ensures saldo > \old(saldo)
    public void creditar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        this.saldo += valor;
    }

    //@ requires valor > 0 && saldo >= valor
    //@ assignable saldo
    //@ ensures saldo == \old(saldo) - valor
    //@ ensures saldo < \old(saldo)
    public void debitar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        this.saldo -= valor;
    }
}
