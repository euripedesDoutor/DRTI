package controle.arquitetura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.faces.model.SelectItem;

import negocio.comuns.arquitetura.PerfilAcessoVO;
import negocio.comuns.arquitetura.PermissaoVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.Dominios;
import negocio.comuns.utilitarias.OpcaoPerfilAcesso;
import negocio.comuns.utilitarias.OpcoesPerfilAcesso;
import negocio.facade.jdbc.arquitetura.PerfilAcesso;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("PerfilAcessoControle")
@Scope("session")
@Lazy
public class PerfilAcessoControle extends SuperControle {

    private String modulo;
    private String permissoesModulo;
    private String acao;
    private PerfilAcessoVO perfilAcessoVO;
    private PerfilAcessoVO perfilAcessoConsulta;
    private PermissaoVO permissaoVO;
    private List listaSelectItemTodasFuncionalidades;
    private List listaSelectItemModulos;
    private List listaConsultaUsuario;

    public PerfilAcessoControle() throws Exception {
        obterUsuarioLogado();
        inicializar();
        setControleConsulta(new ControleConsulta());
        setMensagemID("msg_entre_prmconsulta");
    }

    private void inicializar() {
        setModulo("");
        setAcao("");
        setPermissoesModulo("(0)(1)(2)(3)(9)(12)");
        inicializarListaSelectItemModulos();
        inicializarListaSelectItemTodasFuncionalidades();
    }

    public void clonarPerfilAcesso() {
        try {
            PerfilAcessoVO novoPerfil = (PerfilAcessoVO) getPerfilAcessoVO().getClone();
            novo();
            setPerfilAcessoVO(novoPerfil);
            getPerfilAcessoVO().setCodigo(0);
            getPerfilAcessoVO().setNovoObj(true);
            getPerfilAcessoVO().setPerfilClonado(true);
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    private void inicializarListaSelectItemModulos() {
        List objs = new ArrayList(0);
        objs.add(new SelectItem("", ""));
        for (OpcaoPerfilAcesso modulo : OpcoesPerfilAcesso.getModulos()) {
            objs.add(new SelectItem(modulo.getNome(), modulo.getTitulo()));

        }
        setListaSelectItemModulos(objs);
    }

    public void inicializarListaSelectItemTodasFuncionalidades() {
        List objs = new ArrayList(0);
        objs.add(new SelectItem("", ""));
        if (!getModulo().equals("")) {
            objs.add(new SelectItem("TODOS", "TODOS DESTE MÓDULO"));
        }
        List<OpcaoPerfilAcesso> listaEntidades = new ArrayList(0);
        if (getModulo().equals("Cadastro")) {
            listaEntidades = OpcoesPerfilAcesso.getEntidadesCadastro();
        } else if (getModulo().equals("Arquitetura")) {
            listaEntidades = OpcoesPerfilAcesso.getEntidadesArquitetura();
        }else if (getModulo().equals("Projeto")) {
            listaEntidades = OpcoesPerfilAcesso.getEntidadesProjeto();
        }

        for (OpcaoPerfilAcesso opcao : listaEntidades) {
            objs.add(new SelectItem(opcao.getNome(), opcao.getTitulo()));
        }

        setListaSelectItemTodasFuncionalidades(objs);
    }

    public String novo() {
        setPerfilAcessoVO(new PerfilAcessoVO());
        setPermissaoVO(new PermissaoVO());
        setMensagemID("msg_entre_dados");
        return "editar";
    }

    public String editar() {
        PerfilAcessoVO obj = (PerfilAcessoVO) context().getExternalContext().getRequestMap().get("perfilAcesso");
        obj.setNovoObj(false);
        setPerfilAcessoVO(obj);
        setPermissaoVO(new PermissaoVO());
        setMensagemID("msg_dados_editar");
        return "editar";
    }

    public String gravar() {
        try {
            if (getPerfilAcessoVO().isNovoObj()) {
                getFacadeFactory().getPerfilAcessoFacade().incluir(getPerfilAcessoVO());
            } else {
                getFacadeFactory().getPerfilAcessoFacade().alterar(getPerfilAcessoVO());
                setarAlteracaoPerfilAcesso(getPerfilAcessoVO());
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
                objs = getFacadeFactory().getPerfilAcessoFacade().consultarPorCodigo(Integer.parseInt(getControleConsulta().getValorConsulta()), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("nome")) {
                objs = getFacadeFactory().getPerfilAcessoFacade().consultarPorNome(getControleConsulta().getValorConsulta(), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("tipoPerfil")) {
                objs = getFacadeFactory().getPerfilAcessoFacade().consultarPorTipoPerfil(getControleConsulta().getValorConsulta());
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
            getFacadeFactory().getPerfilAcessoFacade().excluir(perfilAcessoVO);
            setPerfilAcessoVO(new PerfilAcessoVO());

            setPermissaoVO(new PermissaoVO());
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String adicionarPermissaoAcao() throws Exception {
        try {
            if (!getPerfilAcessoVO().getCodigo().equals(0)) {
                permissaoVO.setCodPerfilAcesso(getPerfilAcessoVO().getCodigo());
            }
            permissaoVO.setNomeEntidade(acao);
            getPerfilAcessoVO().adicionarObjPermissaoVOs(getPermissaoVO());
            this.setAcao("");
            this.setPermissaoVO(new PermissaoVO());
            setMensagemID("msg_dados_adicionados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String adicionarPermissao() throws Exception {
        try {
            if (getPermissaoVO().getNomeEntidade().equals("TODOS")) {
                for (SelectItem entidade : (List<SelectItem>) getListaSelectItemTodasFuncionalidades()) {
                    if (!entidade.getValue().equals("TODOS") && !entidade.getValue().equals("")) {
                        if (!getPerfilAcessoVO().getCodigo().equals(0)) {
                            getPermissaoVO().setCodPerfilAcesso(getPerfilAcessoVO().getCodigo());
                        }
                        getPermissaoVO().setNomeEntidade(entidade.getValue().toString());
                        getPerfilAcessoVO().adicionarObjPermissaoVOs(getPermissaoVO());
                        this.setPermissaoVO(new PermissaoVO());
                    }
                }
            } else {
                if (!getPerfilAcessoVO().getCodigo().equals(0)) {
                    getPermissaoVO().setCodPerfilAcesso(getPerfilAcessoVO().getCodigo());
                }
                getPerfilAcessoVO().adicionarObjPermissaoVOs(getPermissaoVO());
                this.setPermissaoVO(new PermissaoVO());
            }
            setMensagemID("msg_dados_adicionados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String editarPermissao() throws Exception {
        PermissaoVO obj = (PermissaoVO) context().getExternalContext().getRequestMap().get("permissao");
        setPermissaoVO(obj);
        return "editar";
    }

    public String removerPermissao() throws Exception {
        PermissaoVO obj = (PermissaoVO) context().getExternalContext().getRequestMap().get("permissao");
        getPerfilAcessoVO().excluirObjPermissaoVOs(obj.getNomeEntidade());
        setMensagemID("msg_dados_excluidos");
        return "editar";
    }

    public String removerPermissaoTodosMarcados() throws Exception {
        List<PermissaoVO> listaExclusao = new ArrayList<PermissaoVO>(0);

        for (int i = 0; i < getPerfilAcessoVO().getPermissaoVOs().size(); i++) {
            PermissaoVO obj = (PermissaoVO) getPerfilAcessoVO().getPermissaoVOs().get(i);
            if (obj.getRemover()) {
                listaExclusao.add(obj);
            }
        }
        for (PermissaoVO obj : listaExclusao) {
            getPerfilAcessoVO().getPermissaoVOs().remove(obj);
        }

        setMensagemID("msg_dados_excluidos");
        return "editar";
    }

    public void irPaginaInicial() throws Exception {
        this.consultar();
    }

    public void consultarUsuario() {
        try {
            this.setListaConsultaUsuario(new ArrayList());
            List objs = new ArrayList(0);
            PerfilAcessoVO perfilAcessoConsultaVO = (PerfilAcessoVO) context().getExternalContext().getRequestMap().get("perfilAcesso");
            setPerfilAcessoConsulta(perfilAcessoConsultaVO);
            objs = getFacadeFactory().getUsuarioFacade().consultarPorNomePerfilAcessoRichModalPanel(perfilAcessoConsultaVO.getNome());
            setListaConsultaUsuario(objs);
            setMensagemID("msg_dados_consultados");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public List getListaSelectItemPermissoesPermissao() throws Exception {
        List objs = new ArrayList(0);
        objs.add(new SelectItem("", ""));
        Hashtable nivelAcessos = (Hashtable) Dominios.getNivelAcesso();
        Enumeration keys = nivelAcessos.keys();
        while (keys.hasMoreElements()) {
            String value = (String) keys.nextElement();
            String label = (String) nivelAcessos.get(value);
            objs.add(new SelectItem(value, label));
        }
        return objs;
    }

    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("nome", "Nome"));
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("tipoPerfil", "Tipo de perfil"));
        return itens;
    }

    public List getListaTipoPerfil() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("NORMAL", "Normal"));
		itens.add(new SelectItem("CAIXA", "Caixa"));
        itens.add(new SelectItem("VENDABALCAO", "Venda Balcão"));
        itens.add(new SelectItem("VENDABALCAOMOBILE", "Venda Balcão Mobile"));
        itens.add(new SelectItem("PDV", "PDV"));											
        itens.add(new SelectItem("VENDEDOREXTERNO", "Vendedor Externo"));
        itens.add(new SelectItem("REPRESENTANTE", "Representante"));
        itens.add(new SelectItem("CONTADOR", "Contador"));
		itens.add(new SelectItem("ABATE", "Abate"));
        itens.add(new SelectItem("PRODUCAO", "Produção"));
        itens.add(new SelectItem("ESTOQUE", "Estoque"));
        itens.add(new SelectItem("COLETA", "Coleta"));
        itens.add(new SelectItem("CARREGAMENTOMOBILE", "Carregamento Mobile"));
        itens.add(new SelectItem("BALANCAO", "Balanção"));
        itens.add(new SelectItem("GESTORGRAXARIA", "Gestor Graxaria"));
        itens.add(new SelectItem("PRODUCAOGRAXARIA", "Produção Graxaria"));
        return itens;
    }

    public String inicializarConsultar() {
        // setPaginaAtualDeTodas("0/0");
        setListaConsulta(new ArrayList(0));
        // definirVisibilidadeLinksNavegacao(0, 0);
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public PermissaoVO getPermissaoVO() {
        if (permissaoVO == null) {
            permissaoVO = new PermissaoVO();
        }
        return permissaoVO;
    }

    public void setPermissaoVO(PermissaoVO permissaoVO) {
        this.permissaoVO = permissaoVO;
    }

    public PerfilAcessoVO getPerfilAcessoVO() {
        if (perfilAcessoVO == null) {
            perfilAcessoVO = new PerfilAcessoVO();
        }
        return perfilAcessoVO;
    }

    public void setPerfilAcessoVO(PerfilAcessoVO perfilAcessoVO) {
        this.perfilAcessoVO = perfilAcessoVO;
    }

    public List getListaSelectItemTodasFuncionalidades() {
        if (listaSelectItemTodasFuncionalidades == null) {
            listaSelectItemTodasFuncionalidades = new ArrayList(0);
        }
        return listaSelectItemTodasFuncionalidades;
    }

    public void setListaSelectItemTodasFuncionalidades(List listaSelectItemTodasFuncionalidades) {
        this.listaSelectItemTodasFuncionalidades = listaSelectItemTodasFuncionalidades;
    }

    public List getListaSelectItemModulos() {
        if (listaSelectItemModulos == null) {
            listaSelectItemModulos = new ArrayList(0);
        }
        return listaSelectItemModulos;
    }

    public void setListaSelectItemModulos(List listaSelectItemModulos) {
        this.listaSelectItemModulos = listaSelectItemModulos;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getPermissoesModulo() {
        return permissoesModulo;
    }

    public void setPermissoesModulo(String permissoesModulo) {
        this.permissoesModulo = permissoesModulo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public void processarAdicionarPermissoesModulo(Hashtable modulo) throws Exception {
        Enumeration entidades = modulo.elements();
        while (entidades.hasMoreElements()) {
            OpcaoPerfilAcesso entidade = (OpcaoPerfilAcesso) entidades.nextElement();
            PermissaoVO permissao = new PermissaoVO();
            permissao.setCodPerfilAcesso(getPerfilAcessoVO().getCodigo());
            permissao.setNomeEntidade(entidade.getNome());
            permissao.setPermissoes(permissoesModulo);
            getPerfilAcessoVO().adicionarObjPermissaoVOs(permissao);
        }
    }

    public String adicionarPermissaoModulo() throws Exception {
        try {
            if (!getPerfilAcessoVO().getCodigo().equals(0)) {
                permissaoVO.setCodPerfilAcesso(getPerfilAcessoVO().getCodigo());
            }
            getPerfilAcessoVO().adicionarObjPermissaoVOs(getPermissaoVO());
            this.setPermissaoVO(new PermissaoVO());
            setMensagemID("msg_dados_adicionados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public List getListaConsultaUsuario() {
        return listaConsultaUsuario;
    }

    public void setListaConsultaUsuario(List listaConsultaUsuario) {
        this.listaConsultaUsuario = listaConsultaUsuario;
    }

    public Boolean getTipoConsultaTipoPerfil() {
        return getControleConsulta().getCampoConsulta().equals("tipoPerfil");
    }

    public PerfilAcessoVO getPerfilAcessoConsulta() {
        if (perfilAcessoConsulta == null){
            perfilAcessoConsulta = new PerfilAcessoVO();
        }
        return perfilAcessoConsulta;
    }

    public void setPerfilAcessoConsulta(PerfilAcessoVO perfilAcessoConsulta) {
        this.perfilAcessoConsulta = perfilAcessoConsulta;
    }
}
