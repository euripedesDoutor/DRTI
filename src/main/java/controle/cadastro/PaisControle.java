package controle.cadastro;

import negocio.comuns.cadastro.PaisVO;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.Uteis;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import controle.arquitetura.SuperControle;

/**
*
* @author DOCTORCODE
*/
@Controller("PaisControle")
@Scope("session")
@Lazy
public class PaisControle extends SuperControle {

    private PaisVO paisVO;

    public PaisControle() throws Exception {
        setControleConsulta(new ControleConsulta());
        getControleConsulta().setCampoConsulta("nome");
        setMensagemID("msg_entre_prmconsulta");
    }

    public String novo() {
        try {
            setPaisVO(null);
            inicializarListasSelectItemTodosComboBox();
            setMensagemID("msg_entre_dados");
        return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String editar() {
        try {
            PaisVO obj = (PaisVO) context().getExternalContext().getRequestMap().get("pais");
            obj.setNovoObj(false);
            setPaisVO(obj);
            inicializarListasSelectItemTodosComboBox();
            setMensagemID("msg_dados_editar");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String gravar() {
        try {
            if (getPaisVO().isNovoObj()) {
                getFacadeFactory().getPaisFacade().incluir(getPaisVO());
            } else {
                getFacadeFactory().getPaisFacade().alterar(getPaisVO());
            }
            setMensagemID("msg_dados_gravados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String consultar() {
        try {
            super.consultar();
            List objs = new ArrayList(0);
            if (getControleConsulta().getCampoConsulta().equals("codigo")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    getControleConsulta().setValorConsulta("0");
                }
                objs = getFacadeFactory().getPaisFacade().consultarPorCodigo(Integer.parseInt(getControleConsulta().getValorConsulta()), true, Uteis.NIVELMONTARDADOS_TODOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("nome")) {
                objs = getFacadeFactory().getPaisFacade().consultarPorNome(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("codigoSped")) {
                objs = getFacadeFactory().getPaisFacade().consultarPorCodigoSped(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("codigoSiscomex")) {
                objs = getFacadeFactory().getPaisFacade().consultarPorCodigoSiscomex(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            setListaConsulta(objs);
            setMensagemID("msg_dados_consultados");
            return "consultar";
        } catch (Exception e) {
            setListaConsulta(new ArrayList(0));
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "consultar";
        }
    }

    public String excluir() {
        try {
            getFacadeFactory().getPaisFacade().excluir(getPaisVO());
            setPaisVO(null);
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void inicializarListasSelectItemTodosComboBox() throws Exception {
    }


    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("nome", "Nome"));
        itens.add(new SelectItem("codigoSped", "Código Sped"));
        itens.add(new SelectItem("codigoSiscomex", "Código Siscomex"));
        return itens;
    }

    public Boolean getTipoConsultaOutros() {
        if(getControleConsulta().getCampoConsulta().equals("codigo")) {
            return true;
        }
        if(getControleConsulta().getCampoConsulta().equals("nome")) {
            return true;
        }
        if(getControleConsulta().getCampoConsulta().equals("codigoSped")) {
            return true;
        }
        if(getControleConsulta().getCampoConsulta().equals("codigoSiscomex")) {
            return true;
        }
        return false;
    }

    public String inicializarConsultar() {
        setListaConsulta(null);
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public PaisVO getPaisVO() {
        if (paisVO == null){
            paisVO = new PaisVO();
        }
        return paisVO;
    }

    public void setPaisVO(PaisVO paisVO) {
        this.paisVO = paisVO;
    }
}
