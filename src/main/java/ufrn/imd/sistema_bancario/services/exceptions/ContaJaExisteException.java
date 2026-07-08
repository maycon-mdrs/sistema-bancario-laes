package ufrn.imd.sistema_bancario.services.exceptions;

import ufrn.imd.sistema_bancario.SistemaBancarioBaseException;

public class ContaJaExisteException extends SistemaBancarioBaseException {
    private final String numeroConta;

    public ContaJaExisteException(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    @Override
    public String getFriendlyMessage() {
        return "Já existe uma conta com o número " + numeroConta + ".";
    }

    @Override
    public String getLogMessage() {
        return "ContaJaExisteException: conta " + numeroConta + " já cadastrada.";
    }
}
