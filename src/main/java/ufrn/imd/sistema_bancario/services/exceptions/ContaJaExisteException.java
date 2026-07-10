package ufrn.imd.sistema_bancario.services.exceptions;

import ufrn.imd.sistema_bancario.SistemaBancarioBaseException;

public class ContaJaExisteException extends SistemaBancarioBaseException {
    private final String numeroConta;

    /*@ public normal_behavior
     @   assignable \nothing;
     @*/
    //@ pure
    public ContaJaExisteException(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    //@ pure
    @Override
    public String getFriendlyMessage() {
        return "Já existe uma conta com o número " + numeroConta + ".";
    }

    //@ pure
    @Override
    public String getLogMessage() {
        return "ContaJaExisteException: conta " + numeroConta + " já cadastrada.";
    }
}
