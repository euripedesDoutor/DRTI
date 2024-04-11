package negocio.comuns.utilitarias;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class UtilJSF {

	public static String getMensagemInternalizacao(String mensagemID) {
		String mensagem = "(" + mensagemID + ") Mensagem não localizada nas propriedades de internalização.";
		ResourceBundle bundle = null;
		Locale locale = null;
		String nomeBundle = context().getApplication().getMessageBundle();
		if (nomeBundle != null) {
			locale = context().getViewRoot().getLocale();
			bundle = ResourceBundle.getBundle(nomeBundle, locale, getCurrentLoader(nomeBundle));
			try {
				mensagem = bundle.getString(mensagemID);
				return mensagem;
			} catch (MissingResourceException e) {
				return mensagem;
			}
		}
		return mensagem;
	}

	public static FacesContext context() {
		return (FacesContext.getCurrentInstance());
	}

	public static ClassLoader getCurrentLoader(Object fallbackClass) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = fallbackClass.getClass().getClassLoader();
		}
		return loader;
	}
	
}
