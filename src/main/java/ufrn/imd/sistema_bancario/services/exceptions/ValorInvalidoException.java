package ufrn.imd.sistema_bancario.services.exceptions;

import ufrn.imd.sistema_bancario.SistemaBancarioBaseException;

public class ValorInvalidoException extends SistemaBancarioBaseException {

    @Override
    public String getFriendlyMessage() {
        return "O valor da operação deve ser maior que zero.";
    }

    @Override
    public String getLogMessage() {
        return "Tentativa de operação com valor inválido.";
    }
}
