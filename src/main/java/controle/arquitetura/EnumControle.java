package controle.arquitetura;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.faces.model.SelectItem;
import negocio.comuns.arquitetura.enumeradores.ObrigatorioEnum;
import negocio.comuns.cadastro.enumeradores.SituacaoBloqueioClienteEnum;
import negocio.comuns.utilitarias.UteisJSF;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Euripedes Doutor
 */

@Controller("EnumControle")
@Scope("singleton")
@Lazy
public class EnumControle extends SuperControle {

    private List<SelectItem> comboboxSituacaoBloqueioClienteEnum;
    private Locale localeCombobox;

    /**
     * Método responsável por montar um combobox a partir de um enum,
     * A ordenação dos itens permanece como foi implementado no enum.
     * @param enumeradores, array contendo valores de um enum.
     * @param obrigatorio, para validar a presença da opção em branco(NENHUM).
     * @return lista de SelectItem.
     * @throws Exception
     */
    public List<SelectItem> montarCombobox(Enum[] enumeradores, ObrigatorioEnum obrigatorio) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        for (Enum enumerador : enumeradores) {
            if (enumerador.toString().equals("NENHUM")) {
                if (obrigatorio == ObrigatorioEnum.NAO) {
                    lista.add(new SelectItem(enumerador.toString(), ""));
                }
                continue;
            }
            lista.add(new SelectItem(enumerador.toString(), internacionalizarEnum(enumerador)));
        }
        return lista;
    }

    
    public String internacionalizarEnum(Enum enumerador) {
        return UteisJSF.internacionalizar("enum_" + enumerador.getClass().getSimpleName() + "_" + enumerador.toString());
    }
    

    /**
     * Método responsável por montar combobox de SituacaoBloqueioClienteEnum
     * @return the comboboxSituacaoBloqueioClienteEnum
     */
    public List<SelectItem> getComboboxSituacaoBloqueioClienteEnum() {
        if (comboboxSituacaoBloqueioClienteEnum == null || !getLocale().equals(localeCombobox)) {
            localeCombobox = getLocale();
            comboboxSituacaoBloqueioClienteEnum = montarCombobox(SituacaoBloqueioClienteEnum.values(), ObrigatorioEnum.SIM);
        }
        return comboboxSituacaoBloqueioClienteEnum;
    }
    

    
}
