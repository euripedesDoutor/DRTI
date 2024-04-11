package controle.projeto;

import negocio.comuns.projeto.EntidadeVO;
import negocio.comuns.projeto.ProjetoVO;
import negocio.facade.jdbc.projeto.Projeto;
import negocio.comuns.projeto.ModuloVO;
import negocio.facade.jdbc.projeto.Modulo;
import negocio.comuns.arquitetura.UsuarioVO;
import negocio.facade.jdbc.arquitetura.Usuario;
import negocio.comuns.projeto.AtributosVO;
import negocio.comuns.projeto.ProjetoVO;
import negocio.facade.jdbc.projeto.Projeto;
import negocio.comuns.projeto.ModuloVO;
import negocio.facade.jdbc.projeto.Modulo;
import negocio.comuns.arquitetura.UsuarioVO;
import negocio.facade.jdbc.arquitetura.Usuario;

import java.util.ArrayList;
import java.util.Date;
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
@Controller("EntidadeControle")
@Scope("session")
@Lazy
public class EntidadeControle extends SuperControle {

    private EntidadeVO entidadeVO;
    private ProjetoVO projetoConsulta;
    private ModuloVO moduloConsulta;
    private AtributosVO atributosVO;
    private List listaSelectItemModulo;

    public EntidadeControle() throws Exception {
        setControleConsulta(new ControleConsulta());
        getControleConsulta().setCampoConsulta("nomeApresentar");
        setMensagemID("msg_entre_prmconsulta");
    }

    public String novo() {
        try {
            setEntidadeVO(null);
            inicializarListasSelectItemTodosComboBox();
            getEntidadeVO().setData(new Date());
            getEntidadeVO().setUsuario(getUsuarioLogado());
            setMensagemID("msg_entre_dados");
        return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String editar() {
        try {
            EntidadeVO obj = (EntidadeVO) context().getExternalContext().getRequestMap().get("entidade");
            obj.setNovoObj(false);
            obj.setProjeto(getFacadeFactory().getProjetoFacade().consultarPorChavePrimaria(obj.getProjeto().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            obj.setModulo(getFacadeFactory().getModuloFacade().consultarPorChavePrimaria(obj.getModulo().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            obj.setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(obj.getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            obj.setListaAtributos(getFacadeFactory().getAtributosFacade().consultarPorEntidade(obj.getCodigo(), false, Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            for(AtributosVO item : obj.getListaAtributos()){
                item.setProjeto(getFacadeFactory().getProjetoFacade().consultarPorChavePrimaria(item.getProjeto().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
                item.setModulo(getFacadeFactory().getModuloFacade().consultarPorChavePrimaria(item.getModulo().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
                item.setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(item.getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            }
            setEntidadeVO(obj);
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
            getEntidadeVO().setData(new Date());
            getEntidadeVO().setUsuario(getUsuarioLogado());
            if (getEntidadeVO().isNovoObj()) {
                getFacadeFactory().getEntidadeFacade().incluir(getEntidadeVO());
            } else {
                getFacadeFactory().getEntidadeFacade().alterar(getEntidadeVO());
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
                objs = getFacadeFactory().getEntidadeFacade().consultarPorCodigo(Integer.parseInt(getControleConsulta().getValorConsulta()), true, Uteis.NIVELMONTARDADOS_TODOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("nomeApresentar")) {
                objs = getFacadeFactory().getEntidadeFacade().consultarPorNomeApresentar(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("projeto")) {
                objs = getFacadeFactory().getEntidadeFacade().consultarPorProjeto(getEntidadeVO().getProjeto(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("modulo")) {
                objs = getFacadeFactory().getEntidadeFacade().consultarPorModulo(getEntidadeVO().getModulo(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
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
            getFacadeFactory().getEntidadeFacade().excluir(getEntidadeVO());
            setEntidadeVO(null);
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void consultarProjetoPorChavePrimaria() {
        try {
            getEntidadeVO().setProjeto(getFacadeFactory().getProjetoFacade().consultarPorChavePrimaria(getEntidadeVO().getProjeto().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            montarListaSelectItemModulo();
        } catch (Exception e) {
            getEntidadeVO().setProjeto(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void consultarProjetoPorChavePrimariaTelaConsulta() {
        try {
            setProjetoConsulta(getFacadeFactory().getProjetoFacade().consultarPorChavePrimaria(getProjetoConsulta().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            setProjetoConsulta(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public List<ModuloVO> autocompleteModulo(Object suggest) {
        String prefixo = (String) suggest;
        try {
            if(getEntidadeVO().getProjeto().getCodigo().equals(0)){
                throw new Exception("Informe o Projeto antes de consultar o módulo");
            }
            return Modulo.consultarParaAutoComplete(prefixo, getEntidadeVO().getProjeto().getCodigo(), 10);
        } catch (Exception ex) {
            setMensagemDetalhada("msg_erro", ex.getMessage());
        }
        return new ArrayList<ModuloVO>();
    }

    public void consultarModuloPorChavePrimaria() {
        try {
            getEntidadeVO().setModulo(getFacadeFactory().getModuloFacade().consultarPorChavePrimaria(getEntidadeVO().getModulo().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getEntidadeVO().setModulo(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void consultarModuloPorChavePrimariaTelaConsulta() {
        try {
            setModuloConsulta(getFacadeFactory().getModuloFacade().consultarPorChavePrimaria(getModuloConsulta().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            setModuloConsulta(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void consultarUsuarioPorChavePrimaria() {
        try {
            getEntidadeVO().setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(getEntidadeVO().getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getEntidadeVO().setUsuario(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public List<ProjetoVO> autocompleteProjeto(Object suggest) {
        String prefixo = (String) suggest;
        try {
            return Projeto.consultarParaAutoComplete(prefixo, 10);
        } catch (Exception ex) {
            setMensagemDetalhada("msg_erro", ex.getMessage());
        }
        return new ArrayList<ProjetoVO>();
    }

    public void consultarProjetoAtributoPorChavePrimaria() {
        try {
            getAtributosVO().setProjeto(getFacadeFactory().getProjetoFacade().consultarPorChavePrimaria(getAtributosVO().getProjeto().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getAtributosVO().setProjeto(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void consultarModuloAtributoPorChavePrimaria() {
        try {
            getAtributosVO().setModulo(getFacadeFactory().getModuloFacade().consultarPorChavePrimaria(getAtributosVO().getModulo().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getAtributosVO().setModulo(null);
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

    public void consultarUsuarioAtributoPorChavePrimaria() {
        try {
            getAtributosVO().setUsuario(getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(getAtributosVO().getUsuario().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            getAtributosVO().setUsuario(null);
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void adicionarAtributos() {
        try {
            if (!getEntidadeVO().getCodigo().equals(0)) {
                getAtributosVO().setEntidade(getEntidadeVO().getCodigo());
            }
            getAtributosVO().setData(new Date());
            getAtributosVO().setUsuario(getUsuarioLogado());
            int index = 0;
            for(AtributosVO item : getEntidadeVO().getListaAtributos()){
                if(getAtributosVO().getNomeApresentar().equals(item.getNomeApresentar())) {
                    getEntidadeVO().getListaAtributos().set( index , getAtributosVO() );
                    index = -1;
                    break;
                }
                index++;
            }
            if(index >= 0) {
                getEntidadeVO().getListaAtributos().add(getAtributosVO());
            }
            this.setAtributosVO(null);
            setMensagemID("msg_dados_adicionados");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void editarAtributos() {
        try {
            AtributosVO obj = (AtributosVO) context().getExternalContext().getRequestMap().get("atributos");
            setAtributosVO(obj);
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void removerAtributos() {
        try {
            AtributosVO obj = (AtributosVO) context().getExternalContext().getRequestMap().get("atributos");
            getEntidadeVO().getListaAtributos().remove(obj);
            setMensagemID("msg_dados_excluidos");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }
    public void inicializarListasSelectItemTodosComboBox() throws Exception {
        //montarListaSelectItemModulo();
    }


    public void montarListaSelectItemModulo() {
        try {
            montarListaSelectItemModulo("");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void montarListaSelectItemModulo(String prm) throws Exception {
        List<ModuloVO> resultadoConsulta = consultarModuloPorNomeApresentar(prm);
        List objs = new ArrayList(0);
        objs.add(new SelectItem(0, ""));
        for (ModuloVO obj : resultadoConsulta) {
            objs.add(new SelectItem(obj.getCodigo(), obj.getNomeApresentar()));
        }
        setListaSelectItemModulo(objs);
    }

    public List consultarModuloPorNomeApresentar(String nomeApresentarPrm) throws Exception {
        List lista = getFacadeFactory().getModuloFacade().consultarPorNomeApresentar(nomeApresentarPrm, getEntidadeVO().getProjeto().getCodigo(), false, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
        return lista;
    }

    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("nomeApresentar", "Nome de Apresentação"));
        itens.add(new SelectItem("projeto", "Projeto"));
        itens.add(new SelectItem("modulo", "Módulo"));
        return itens;
    }

    public Boolean getTipoConsultaProjeto() {
        if(getControleConsulta().getCampoConsulta().equals("projeto")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoConsultaModulo() {
        if(getControleConsulta().getCampoConsulta().equals("modulo")) {
            return true;
        }
        return false;
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

    public EntidadeVO getEntidadeVO() {
        if (entidadeVO == null){
            entidadeVO = new EntidadeVO();
        }
        return entidadeVO;
    }

    public void setEntidadeVO(EntidadeVO entidadeVO) {
        this.entidadeVO = entidadeVO;
    }
    public ProjetoVO getProjetoConsulta() {
        if (projetoConsulta == null){
            projetoConsulta = new ProjetoVO();
        }
        return projetoConsulta;
    }

    public void setProjetoConsulta(ProjetoVO projetoConsulta) {
        this.projetoConsulta = projetoConsulta;
    }
    public ModuloVO getModuloConsulta() {
        if (moduloConsulta == null){
            moduloConsulta = new ModuloVO();
        }
        return moduloConsulta;
    }

    public void setModuloConsulta(ModuloVO moduloConsulta) {
        this.moduloConsulta = moduloConsulta;
    }

    public AtributosVO getAtributosVO() {
        if (atributosVO == null){
            atributosVO = new AtributosVO();
        }
        return atributosVO;
    }

    public void setAtributosVO(AtributosVO atributosVO) {
        this.atributosVO = atributosVO;
    }

    public List<ModuloVO> getListaSelectItemModulo() {
        if (listaSelectItemModulo == null){
            listaSelectItemModulo = new ArrayList<ModuloVO>(0);
        }
        return listaSelectItemModulo;
    }

    public void setListaSelectItemModulo(List<ModuloVO> listaSelectItemModulo) {
        this.listaSelectItemModulo = listaSelectItemModulo;
    }

}
