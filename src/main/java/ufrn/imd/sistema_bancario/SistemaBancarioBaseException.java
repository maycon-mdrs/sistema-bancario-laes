package ufrn.imd.sistema_bancario;



public abstract class SistemaBancarioBaseException extends RuntimeException {

    /*@ protected normal_behavior
      @   assignable \nothing;
      @*/
    //@ pure
    protected SistemaBancarioBaseException() {
        super();
    }

    //@ pure
    protected SistemaBancarioBaseException(Throwable cause) {
        super(cause);
    }

    //@ pure
    public abstract String getFriendlyMessage();

    //@ pure
    public abstract String getLogMessage();

}
