package negocio.comuns.utilitarias;

public class ConexaoException extends Exception {
    
    public ConexaoException(String msg) {
        super(msg);
    }

    public ConexaoException(Exception e) {
        super(e);
    }

    
}
