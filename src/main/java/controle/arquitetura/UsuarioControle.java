package controle.arquitetura;

import java.util.*;

import javax.faces.model.SelectItem;

import negocio.comuns.arquitetura.*;

import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.Dominios;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.Usuario;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import relatorio.controle.cadastro.UsuarioControleRel;

@Controller("UsuarioControle")
@Scope("session")
@Lazy
public class UsuarioControle extends SuperControle {

    private UsuarioVO usuarioVO;
    private UsuarioVO usuarioConsulta;
    private UsuarioPerfilAcessoVO usuarioPerfilAcessoVO;
    private List listaSelectItemCodPerfilAcesso;
    private List listaSelectItemEmpresa;

    public UsuarioControle() throws Exception {
        obterUsuarioLogado();
        setControleConsulta(new ControleConsulta());
        setMensagemID("msg_entre_prmconsulta");
        inicializarListasSelectItemTodosComboBox();
    }

    public String novo() throws Exception {
        setUsuarioVO(new UsuarioVO());
        setUsuarioPerfilAcessoVO(new UsuarioPerfilAcessoVO());
        obterSenhaPadraoSistema();
        inicializarListasSelectItemTodosComboBox();
        preencherTabelaPeriodoAcesso();
        setMensagemID("msg_entre_dados");
        return "editar";
    }

    public void obterSenhaPadraoSistema() throws Exception {
        getUsuarioVO().setSenha("123456");
    }

    public String editar() throws Exception {
        try {
            UsuarioVO obj = (UsuarioVO) context().getExternalContext().getRequestMap().get("usuario2");
            obj = getFacadeFactory().getUsuarioFacade().consultarPorChavePrimaria(obj.getCodigo(), Uteis.NIVELMONTARDADOS_TODOS);
            obj.setNovoObj(false);
            setUsuarioVO(obj);
            inicializarListasSelectItemTodosComboBox();
            setUsuarioPerfilAcessoVO(new UsuarioPerfilAcessoVO());
            preencherTabelaPeriodoAcesso();
            setMensagemID("msg_dados_editar");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void exportarListaConsultaParaPdf() throws Exception {
        try {
            UsuarioControleRel controle = (UsuarioControleRel)getControlador("UsuarioControleRel");
            if(controle == null){
                controle = new UsuarioControleRel();
            }
            for(UsuarioVO obj : (List<UsuarioVO>)getListaConsulta()){
                obj.setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(obj.getCodigo()));
            }
            controle.exportarListaConsultaParaPdf(getListaConsulta());
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    private void preencherTabelaPeriodoAcesso(){
        if(getUsuarioVO().getUsuarioPeriodoAcessoVOs().isEmpty()) {
            UsuarioPeriodoAcessoVO u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(1);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
            u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(2);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
            u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(3);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
            u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(4);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
            u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(5);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
            u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(6);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
            u = new UsuarioPeriodoAcessoVO();
            u.setAcessoPermitido(true);
            u.setDiaSemana(7);
            u.setHorasInicio(0);
            u.setMinutosInicio(0);
            u.setHorasFim(23);
            u.setMinutosFim(59);
            getUsuarioVO().getUsuarioPeriodoAcessoVOs().add(u);
        }
    }

    public String gravar() {
        try {
            if (getUsuarioVO().isNovoObj().booleanValue()) {
                getUsuarioVO().setDataExpiracaoSenha(Uteis.incrementarData(new Date(), -1));
                getFacadeFactory().getUsuarioFacade().incluir(this.getUsuarioVO());
            } else {
                getFacadeFactory().getUsuarioFacade().alterar(this.getUsuarioVO());
            }
            setMensagemID("msg_dados_gravados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void consultarPerfilAcesso() {
        try {
            UsuarioVO obj = (UsuarioVO) context().getExternalContext().getRequestMap().get("usuario2");
            setUsuarioConsulta(obj);
            getUsuarioConsulta().setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(obj.getCodigo()));
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public String consultar() {
        try {
            super.consultar();
            List<UsuarioVO> objs = new ArrayList(0);
            if (getControleConsulta().getCampoConsulta().equals("codigo")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    getControleConsulta().setValorConsulta("0");
                }
                int valorInt = Integer.parseInt(getControleConsulta().getValorConsulta());
                objs = getFacadeFactory().getUsuarioFacade().consultarPorCodigo(new Integer(valorInt), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("nome")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    throw new Exception("Informe ao menos 2 caracteres.");
                }
                objs = getFacadeFactory().getUsuarioFacade().consultarPorNome(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("username")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    throw new Exception("Informe ao menos 2 caracteres.");
                }
                objs = getFacadeFactory().getUsuarioFacade().consultarPorUsername(getControleConsulta().getValorConsulta(), true, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("perfilAcesso")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    getControleConsulta().setValorConsulta("0");
                }
                Integer codigoPerfilAcesso = Integer.parseInt(getControleConsulta().getValorConsulta());
                objs = getFacadeFactory().getUsuarioFacade().consultarPorCodigoPerfilAcesso(codigoPerfilAcesso, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("situacaoAcesso")) {
                objs = getFacadeFactory().getUsuarioFacade().consultarPorSituacaoAcesso(getControleConsulta().getValorConsulta(), Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            }
            getListaConsulta().clear();
            if(!getUserNameIsAdmin()) {
                for (UsuarioVO user : objs) {
                     if(!user.getUsername().equals("admin") &&
                             !user.getUsername().equals("SYSTEM")){
                        getListaConsulta().add(user);
                    }
                }
            }else{
                setListaConsulta(objs);
            }
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
            getFacadeFactory().getUsuarioFacade().excluir(usuarioVO);
            setUsuarioVO(new UsuarioVO());
            setUsuarioPerfilAcessoVO(new UsuarioPerfilAcessoVO());
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void resetarSenha() {
        try {
            getUsuarioVO().setSenha("123456");
            getFacadeFactory().getUsuarioFacade().alterarSenha(getUsuarioVO(), false);
            getUsuarioVO().setDataExpiracaoSenha(Uteis.incrementarData(new Date(), -1));
            Usuario.alterarDataExpiracaoSenha(getUsuarioVO().getCodigo(), getUsuarioVO().getDataExpiracaoSenha());
            setMensagemID("msg_resetarSenhaUsuario");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void irPaginaInicial() throws Exception {
        // controleConsulta.setPaginaAtual(1);
        this.consultar();
    }

    public void irPaginaAnterior() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getPaginaAtual() - 1);
        this.consultar();
    }

    public void irPaginaPosterior() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getPaginaAtual() + 1);
        this.consultar();
    }

    public void irPaginaFinal() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getNrTotalPaginas());
        this.consultar();
    }

    public List getTipoConsultaComboFuncionario() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("nome", "Nome"));

        return itens;
    }

    public List getHoraPeriodoAcesso() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem(0, "00"));
        itens.add(new SelectItem(1, "01"));
        itens.add(new SelectItem(2, "02"));
        itens.add(new SelectItem(3, "03"));
        itens.add(new SelectItem(4, "04"));
        itens.add(new SelectItem(5, "05"));
        itens.add(new SelectItem(6, "06"));
        itens.add(new SelectItem(7, "07"));
        itens.add(new SelectItem(8, "08"));
        itens.add(new SelectItem(9, "09"));
        itens.add(new SelectItem(10, "10"));
        itens.add(new SelectItem(11, "11"));
        itens.add(new SelectItem(12, "12"));
        itens.add(new SelectItem(13, "13"));
        itens.add(new SelectItem(14, "14"));
        itens.add(new SelectItem(15, "15"));
        itens.add(new SelectItem(16, "16"));
        itens.add(new SelectItem(17, "17"));
        itens.add(new SelectItem(18, "18"));
        itens.add(new SelectItem(19, "19"));
        itens.add(new SelectItem(20, "20"));
        itens.add(new SelectItem(21, "21"));
        itens.add(new SelectItem(22, "22"));
        itens.add(new SelectItem(23, "23"));
        return itens;
    }

    public List getMinutosPeriodoAcesso() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem(0, "00"));
        itens.add(new SelectItem(5, "05"));
        itens.add(new SelectItem(10, "10"));
        itens.add(new SelectItem(15, "15"));
        itens.add(new SelectItem(20, "20"));
        itens.add(new SelectItem(25, "25"));
        itens.add(new SelectItem(30, "30"));
        itens.add(new SelectItem(35, "35"));
        itens.add(new SelectItem(40, "40"));
        itens.add(new SelectItem(45, "45"));
        itens.add(new SelectItem(50, "50"));
        itens.add(new SelectItem(55, "55"));
        itens.add(new SelectItem(59, "59"));
        return itens;
    }

    public String adicionarUsuarioPerfilAcesso() throws Exception {
        try {
            if (!getUsuarioVO().getCodigo().equals(0)) {
                usuarioPerfilAcessoVO.setUsuario(getUsuarioVO().getCodigo());
            }
            if (getUsuarioPerfilAcessoVO().getEmpresa().getCodigo().intValue() != 0) {
                Integer campoEmpresa = getUsuarioPerfilAcessoVO().getEmpresa().getCodigo();
                EmpresaVO empresa = getFacadeFactory().getEmpresaFacade().consultarPorChavePrimaria(campoEmpresa);
                getUsuarioPerfilAcessoVO().setEmpresa(empresa);
            }
            if (getUsuarioPerfilAcessoVO().getPerfilAcesso().getCodigo().intValue() != 0) {
                Integer campoPerfilAcesso = getUsuarioPerfilAcessoVO().getPerfilAcesso().getCodigo();
                PerfilAcessoVO perfilAcesso = getFacadeFactory().getPerfilAcessoFacade().consultarPorChavePrimaria(campoPerfilAcesso);
                getUsuarioPerfilAcessoVO().setPerfilAcesso(perfilAcesso);
            }

            getUsuarioVO().adicionarObjUsuarioPerfilAcessoVOs(getUsuarioPerfilAcessoVO());
            this.setUsuarioPerfilAcessoVO(new UsuarioPerfilAcessoVO());
            setMensagemID("msg_dados_adicionados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public String editarUsuarioPerfilAcesso() throws Exception {
        UsuarioPerfilAcessoVO obj = (UsuarioPerfilAcessoVO) context().getExternalContext().getRequestMap().get("usuarioPerfilAcesso");
        setUsuarioPerfilAcessoVO(obj);
        return "editar";
    }

    public String removerUsuarioPerfilAcesso() throws Exception {
        UsuarioPerfilAcessoVO obj = (UsuarioPerfilAcessoVO) context().getExternalContext().getRequestMap().get("usuarioPerfilAcesso");
        getUsuarioVO().excluirObjUsuarioPerfilAcessoVOs(obj.getPerfilAcesso().getCodigo());
        setMensagemID("msg_dados_excluidos");
        return "editar";
    }

    public void montarListaSelectItemCodPerfilAcesso(String prm) throws Exception {
        List resultadoConsulta = consultarPerfilAcessoPorNome(prm);
        Iterator i = resultadoConsulta.iterator();
        List objs = new ArrayList(0);
        objs.add(new SelectItem(0, ""));
        while (i.hasNext()) {
            PerfilAcessoVO obj = (PerfilAcessoVO) i.next();
            objs.add(new SelectItem(obj.getCodigo(), obj.getNome().toString()));
        }
        setListaSelectItemCodPerfilAcesso(objs);
    }

    public void montarListaSelectItemCodPerfilAcesso() {
        try {
            montarListaSelectItemCodPerfilAcesso("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List consultarPerfilAcessoPorNome(String nomePrm) throws Exception {
        List lista = getFacadeFactory().getPerfilAcessoFacade().consultarPorNome(nomePrm, false);
        return lista;
    }

    public void inicializarListasSelectItemTodosComboBox() {
        montarListaSelectItemCodPerfilAcesso();
        montarListaSelectItemEmpresa();
    }

    public void montarListaSelectItemEmpresa(String prm) throws Exception {
        List resultadoConsulta = consultarEmpresaPorNome(prm);
        Iterator i = resultadoConsulta.iterator();
        List objs = new ArrayList(0);
        objs.add(new SelectItem(0, ""));
        while (i.hasNext()) {
            EmpresaVO obj = (EmpresaVO) i.next();
            objs.add(new SelectItem(obj.getCodigo(), obj.getNomeFantasia().toString()));
        }
        setListaSelectItemEmpresa(objs);
    }

    public void montarListaSelectItemEmpresa() {
        try {
            montarListaSelectItemEmpresa("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List consultarEmpresaPorNome(String nomePrm) throws Exception {
        List lista = getFacadeFactory().getEmpresaFacade().consultarPorNomeFantasia(nomePrm, false);
        return lista;
    }

    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("nome", "Nome"));
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("username", "Username"));
        itens.add(new SelectItem("perfilAcesso", "Perfil de Acesso"));
        itens.add(new SelectItem("situacaoAcesso", "Situação Acesso"));
        return itens;
    }

    public List getListaQuantidadeVias() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("1", "1"));
        itens.add(new SelectItem("2", "2"));
        itens.add(new SelectItem("3", "3"));
        return itens;
    }

    public List getListaOpcoesConsultaSituacaoAcesso() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("ATIVO", "ATIVO"));
        itens.add(new SelectItem("INATIVO", "INATIVO"));
        return itens;
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

    public String inicializarConsultar() {
        setListaConsulta(new ArrayList(0));
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public List getListaSelectItemCodPerfilAcesso() {
        return (listaSelectItemCodPerfilAcesso);
    }

    public void setListaSelectItemCodPerfilAcesso(List listaSelectItemCodPerfilAcesso) {
        this.listaSelectItemCodPerfilAcesso = listaSelectItemCodPerfilAcesso;
    }

    public UsuarioVO getUsuarioVO() {
        return usuarioVO;
    }

    public void setUsuarioVO(UsuarioVO usuarioVO) {
        this.usuarioVO = usuarioVO;
    }

    public List getListaSelectItemEmpresa() {
        return listaSelectItemEmpresa;
    }

    public void setListaSelectItemEmpresa(List listaSelectItemEmpresa) {
        this.listaSelectItemEmpresa = listaSelectItemEmpresa;
    }

    public UsuarioPerfilAcessoVO getUsuarioPerfilAcessoVO() {
        return usuarioPerfilAcessoVO;
    }

    public void setUsuarioPerfilAcessoVO(UsuarioPerfilAcessoVO usuarioPerfilAcessoVO) {
        this.usuarioPerfilAcessoVO = usuarioPerfilAcessoVO;
    }
  public Boolean getMostrarBotaoResetarSenha() throws Exception {
        if (getUsuarioLogado().getUsuarioPerfilAcessoVOs().isEmpty()) {
            getUsuarioLogado().setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(getUsuarioLogado().getCodigo()));
        }
        for (UsuarioPerfilAcessoVO usuarioPerfil : (ArrayList<UsuarioPerfilAcessoVO>) getUsuarioLogado().getUsuarioPerfilAcessoVOs()) {
            if (usuarioPerfil.getPerfilAcesso().getAdministrador()) {
                return true;
            }
        }
        return false;
    }

    public Boolean getTipoConsultaPerfilAcesso() {
        if (getControleConsulta().getCampoConsulta().equals("perfilAcesso")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoConsultaSituacaoAcesso() {
        if (getControleConsulta().getCampoConsulta().equals("situacaoAcesso")) {
            return true;
        }
        return false;
    }

    public UsuarioVO getUsuarioConsulta() {
        if (usuarioConsulta == null) {
            usuarioConsulta = new UsuarioVO();
        }
        return usuarioConsulta;
    }

    public void setUsuarioConsulta(UsuarioVO usuarioConsulta) {
        this.usuarioConsulta = usuarioConsulta;
    }
}
