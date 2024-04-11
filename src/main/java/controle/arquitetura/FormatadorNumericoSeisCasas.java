
package controle.arquitetura;

import sun.util.locale.provider.DecimalFormatSymbolsProviderImpl;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Euripedes Doutor
 */
public class FormatadorNumericoSeisCasas implements Converter {

    public FormatadorNumericoSeisCasas() {

    }

    public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
        try {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "BR"));
            dfs.setDecimalSeparator(',');
            dfs.setGroupingSeparator('.');
            String pattern = "#,######0.000000";
            final DecimalFormat df = new DecimalFormat(pattern, dfs);
            df.setMinimumFractionDigits(6);
            df.setMaximumFractionDigits(6);
            return new Double(df.parse(value).doubleValue());
        } catch (final Exception e) {
            return new Double(0.0);
        }
    }

    public String getAsString(final FacesContext context,
            final UIComponent component, Object value) {
        if ((value == null) || (value.toString().trim().equals(""))) {
            value = new Double(0.0);
        }
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("pt", "BR"));
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        String pattern = "#,######0.000000";
        final DecimalFormat df = new DecimalFormat(pattern, dfs);
        df.setMinimumFractionDigits(6);
        df.setMaximumFractionDigits(6);
        return df.format(Double.valueOf(value.toString()));
    }
}

