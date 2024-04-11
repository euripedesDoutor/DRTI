package negocio.comuns.arquitetura;

import javax.faces.context.FacesContext;

import controle.arquitetura.LoginControle;
import java.util.Locale;

import negocio.comuns.utilitarias.UtilJSF;

public class SuperArquitetura extends FacadeManager {

    protected static String getMensagemInternalizacao(String mensagemID) {
        return UtilJSF.getMensagemInternalizacao(mensagemID);
    }

    protected FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }

    public static Object getControlador(String nomeControlador) throws Exception {
        FacesContext contexto = FacesContext.getCurrentInstance();
        return contexto.getExternalContext().getSessionMap().get(nomeControlador);
    }

    public static void setControlador(String nomeControlador, Object controle){
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.getExternalContext().getSessionMap().put(nomeControlador, controle);
    }

    /**
     * Método responsável por obter o usuário logado no sistema.
     */
    public UsuarioVO getUsuarioLogado() throws Exception {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        UsuarioVO usuario = loginControle.getUsuario();
        return usuario;
    }

    public Locale getLocale() {
        return context().getViewRoot().getLocale();
    }
}
