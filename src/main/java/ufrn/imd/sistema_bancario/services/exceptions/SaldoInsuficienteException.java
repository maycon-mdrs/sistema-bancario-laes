package ufrn.imd.sistema_bancario.services.exceptions;

import ufrn.imd.sistema_bancario.SistemaBancarioBaseException;

public class SaldoInsuficienteException extends SistemaBancarioBaseException {
    private final String numeroConta;


    /*@ public normal_behavior
     @   assignable \nothing;
     @*/
    //@ pure
    public SaldoInsuficienteException(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    //@ pure
    @Override
    public String getFriendlyMessage() {
        return "Saldo insuficiente na conta de número " + numeroConta + ".";
    }

    //@ pure
    @Override
    public String getLogMessage() {
        return "SaldoInsuficienteException: saldo insuficiente na conta " + numeroConta + ".";
    }
}
