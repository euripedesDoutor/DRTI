package negocio.comuns.utilitarias;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author Euripedes Doutor
 */
public class UteisJSF {

    public static String internacionalizar(String mensagem) {
        String propriedade = obterArquivoPropriedades(mensagem);
        ResourceBundle bundle = ResourceBundle.getBundle(propriedade, getLocale(), getCurrentLoader(propriedade));
        try {
            return bundle.getString(mensagem);
        } catch (MissingResourceException e) {
            return mensagem;
        }
    }

    public static String obterArquivoPropriedades(String mensagem) {
        if (mensagem.startsWith("msg")) {
            return "propriedades.mensagens";
        } else if (mensagem.startsWith("enum")) {
            return "propriedades.enum";
        } else if (mensagem.startsWith("prt")) {
            return "propriedades.aplicacao";
        } else if (mensagem.startsWith("menu")) {
            return "propriedades.menu";
        } else if (mensagem.startsWith("btn")) {
            return "propriedades.botoes";
        } else {
            return "propriedades.mensagens";
        }
    }

    public static Locale getLocale() {
        return context().getViewRoot().getLocale();
    }

//    public static String getMensagemInternalizacao(String mensagemID) {
//        String mensagem = "(" + mensagemID + ") Mensagem não localizada nas propriedades de internalização.";
//        ResourceBundle bundle = null;
//        Locale locale = null;
//        String nomeBundle = context().getApplication().getMessageBundle();
//        if (nomeBundle != null) {
//            locale = context().getViewRoot().getLocale();
//            bundle = ResourceBundle.getBundle(nomeBundle, locale, getCurrentLoader(nomeBundle));
//            try {
//                mensagem = bundle.getString(mensagemID);
//                return mensagem;
//            } catch (MissingResourceException e) {
//                return mensagem;
//            }
//        }
//        return mensagem;
//    }

    public static FacesContext context() {
        return FacesContext.getCurrentInstance();
    }

    // metodo nao esta sendo utilizado...
    public static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader;
    }
}
