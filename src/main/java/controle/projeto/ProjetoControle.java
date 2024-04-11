package controle.projeto;

import controle.arquitetura.SuperControle;
import negocio.comuns.arquitetura.UsuarioVO;
import negocio.comuns.projeto.ModuloVO;
import negocio.comuns.projeto.ProjetoVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.Usuario;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*
* @author DOCTORCODE
*/
@Controller("ProjetoControle")
@Scope("session")
@Lazy
public class ProjetoControle extends SuperControle {

    private ProjetoVO projetoVO;
    private ModuloVO moduloVO;

    public ProjetoControle() throws Exception {
        setControleConsulta(new ControleConsulta());
        getControleConsulta().setCampoConsulta("nomeApresentar");
        setMensagemID("msg_entre_prmconsulta");
    }

    public String novo() {
        try {
            setProjetoVO(null);
            inicializarListasSelectItemTodosComboBox();
            getProjetoVO().setUsuario(getUsuarioLogado());
            getProjetoVO().setData(new Date());
            setMensagemID("msg_entre_dados");
        return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String editar() {
        try {
            ProjetoVO obj = (ProjetoVO) context().getExternalContext().getRequestMap().get("projeto");
            obj.setNovoObj(false);
            obj.setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(obj.getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            obj.setListaModulo(getFacadeFactory().getModuloFacade().consultarPorProjeto(obj.getCodigo(), false, Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            for(ModuloVO item : obj.getListaModulo()){
                item.setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(item.getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            }
            setProjetoVO(obj);
            inicializarListasSelectItemTodosComboBox();
            getProjetoVO().setUsuario(getUsuarioLogado());
            getProjetoVO().setData(new Date());
            setMensagemID("msg_dados_editar");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String gravar() {
        try {
            getProjetoVO().setUsuario(getUsuarioLogado());
            getProjetoVO().setData(new Date());
            if (getProjetoVO().isNovoObj()) {
                getFacadeFactory().getProjetoFacade().incluir(getProjetoVO());
            } else {
                getFacadeFactory().getProjetoFacade().alterar(getProjetoVO());
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
                objs = getFacadeFactory().getProjetoFacade().consultarPorCodigo(Integer.parseInt(getControleConsulta().getValorConsulta()), true, Uteis.NIVELMONTARDADOS_TODOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("nomeApresentar")) {
                objs = getFacadeFactory().getProjetoFacade().consultarPorNomeApresentar(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
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
            getFacadeFactory().getProjetoFacade().excluir(getProjetoVO());
            setProjetoVO(null);
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void consultarUsuarioPorChavePrimaria() {
        try {
            getProjetoVO().setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(getProjetoVO().getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getProjetoVO().setUsuario(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public List<UsuarioVO> autocompleteUsuario(Object suggest) {
        String prefixo = (String) suggest;
        try {
            return Usuario.consultarParaAutoComplete(prefixo, 10);
        } catch (Exception ex) {
            setMensagemDetalhada("msg_erro", ex.getMessage());
        }
        return new ArrayList<UsuarioVO>();
    }

    public void consultarUsuarioModuloPorChavePrimaria() {
        try {
            getModuloVO().setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(getModuloVO().getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getModuloVO().setUsuario(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void adicionarModulo() {
        try {
            if (!getProjetoVO().getCodigo().equals(0)) {
                getModuloVO().setProjeto(getProjetoVO().getCodigo());
            }
            getModuloVO().setUsuario(getUsuarioLogado());
            getModuloVO().setData(new Date());
            int index = 0;
            for(ModuloVO item : getProjetoVO().getListaModulo()){
                if(getModuloVO().getNomeApresentar().equals(item.getNomeApresentar())) {
                    getProjetoVO().getListaModulo().set( index , getModuloVO() );
                    index = -1;
                    break;
                }
                index++;
            }
            if(index >= 0) {
                getProjetoVO().getListaModulo().add(getModuloVO());
            }
            this.setModuloVO(null);
            setMensagemID("msg_dados_adicionados");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void editarModulo() {
        try {
            ModuloVO obj = (ModuloVO) context().getExternalContext().getRequestMap().get("modulo");
            setModuloVO(obj);
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void removerModulo() {
        try {
            ModuloVO obj = (ModuloVO) context().getExternalContext().getRequestMap().get("modulo");
            getProjetoVO().getListaModulo().remove(obj);
            setMensagemID("msg_dados_excluidos");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }
    public void inicializarListasSelectItemTodosComboBox() throws Exception {
    }


    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("nomeApresentar", "Nome de Apresentação"));
        return itens;
    }

    public Boolean getTipoConsultaOutros() {
        if(getControleConsulta().getCampoConsulta().equals("codigo")) {
            return true;
        }
        if(getControleConsulta().getCampoConsulta().equals("nomeApresentar")) {
            return true;
        }
        return false;
    }

    public String inicializarConsultar() {
        setListaConsulta(null);
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public ProjetoVO getProjetoVO() {
        if (projetoVO == null){
            projetoVO = new ProjetoVO();
        }
        return projetoVO;
    }

    public void setProjetoVO(ProjetoVO projetoVO) {
        this.projetoVO = projetoVO;
    }

    public ModuloVO getModuloVO() {
        if (moduloVO == null){
            moduloVO = new ModuloVO();
        }
        return moduloVO;
    }

    public void setModuloVO(ModuloVO moduloVO) {
        this.moduloVO = moduloVO;
    }
}
