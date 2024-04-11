package controle.arquitetura;

import java.util.logging.Level;
import java.util.logging.Logger;

import controle.cadastro.ConfiguracaoSistemaControle;
import controle.cadastro.EmpresaControle;
import controle.projeto.EntidadeControle;
import controle.projeto.ProjetoControle;
import negocio.comuns.arquitetura.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.Uteis;

import javax.servlet.http.HttpSession;

import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import negocio.comuns.utilitarias.Versao;
import negocio.facade.jdbc.arquitetura.ControleAcesso;
import negocio.facade.jdbc.arquitetura.SuperEntidade;
import negocio.facade.jdbc.arquitetura.Usuario;
import negocio.facade.jdbc.cadastro.ConfiguracaoSistema;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("LoginControle")
@Scope("session")
@Lazy
public class LoginControle extends SuperControle {

    private String username = "";
    private String senha = "";
    private String novaSenha = "";
    private String novaSenha2 = "";
    private String nome = "";
    private String nomeEmpresa = "";
    private List listaConsultaLog;
    private LogVO logVO;
    protected PermissaoAcessoMenuVO permissaoAcessoMenuVO;
    protected List ListaSelectItemEmpresa;
    protected List listaPendenciaSistema;
    private static Boolean threadDeletarArquivoDanfe = false;
    private static SqlAtualizador sqlAtualizador = new SqlAtualizador();
    private UsuarioFavoritosVO favorito;
    private Integer empresaLogin;
    private static Map<Integer, List<LoginUsuarioVO>> listaLogin;
    private Date dataTelaAberta;
    private HashMap<String, Boolean> listaFrame;
    private static Boolean executouSQL = false;

    public LoginControle() throws Exception {
        setUsuario(new UsuarioVO());
        setEmpresa(new EmpresaVO());
        setPermissaoAcessoMenuVO(new PermissaoAcessoMenuVO());
        setPerfilAcesso(new PerfilAcessoVO());
        setListaSelectItemEmpresa(new ArrayList(0));
        setMensagemID("msg_entre_prmlogin");
    }

    public void atualizarSQL() throws Exception {
        if (executouSQL) {
            return;
        }
        executouSQL = true;
        //executarSQL("AtualizacoesSQL.sql", "versaoSQL");//ARQUIVO ENCERRADO
        //executarSQL("SQL001.sql", "versaoSQL001");
    }

    private void executarSQL(String arquivo, String colunaBD) throws Exception {
        Integer ultimoCodigoExecutado = SuperEntidade.obterCodigoSQL(colunaBD);
        Boolean atualizarVersao = false;
        this.sqlAtualizador.setCaminhoDiretorioWeb(obterCaminhoDiretorioWeb());
        sqlAtualizador.getListaSql().clear();
        sqlAtualizador.setCont(1);
        sqlAtualizador.setArquivo(arquivo);
        sqlAtualizador.carregarArquivoSql();
        for (SqlVO sql : sqlAtualizador.getListaSql()) {
            if (ultimoCodigoExecutado < sql.getCodigo()) {
                if (sql.getSql().trim().isEmpty()) {
                    continue;
                }
                if (sql.getSql().contains("E N C E R R A D A")) {
                    System.out.println("ARQUIVO " + arquivo + " ENCERRADO");
                    continue;
                }
                try {
                    System.out.println("====================");
                    System.out.println("SQL " + sql.getCodigo());
                    System.out.println(sql.getSql());
                    SuperEntidade.executarSql(sql.getSql());
                    System.out.println("SQL EXECUTADO COM SUCESSO");
                    ultimoCodigoExecutado = sql.getCodigo();
                    atualizarVersao = true;
                } catch (Exception ex) {
                    System.out.println("ERRO: " + ex.getMessage());
                    Logger.getLogger(LoginControle.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("====================");
            }
        }
        if (atualizarVersao) {
            SuperEntidade.executarSql("UPDATE  configuracaoSistema SET " + colunaBD + " = " + ultimoCodigoExecutado);
            System.out.println("VERSÃO SQL ATUALIZADA PARA: " + colunaBD + " - " + ultimoCodigoExecutado);
        }
    }

    @PostConstruct
    public void inicializarListasSelectItemTodosComboBox() {
        try {
            //atualizarSQL();
            setVersaoSistema(Versao.NUMERO_VERSAO);

            montarListaSelectItemEmpresa();
//            HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
//            String view = request.getParameter("view");
//            if (view != null) {
//                if (view.equals("catalogo")) {
//                    UsuarioVO usuario = ControleAcesso.verificarLoginUsuario("catalogo", "123456");
//                    setNome(usuario.getNome());
//                    setUsuario(usuario);
//                    setEmpresa(obterEmpresaLogada(1));setNomeEmpresa(getEmpresa().getNomeFantasia());
//                    PerfilAcessoVO perfil = obterPerfilAcesso(usuario.getCodigo(), getEmpresa().getCodigo());
//                    setPerfilAcesso(perfil);
//                    montarPermissoesMenu(getPerfilAcesso());
//                    HttpServletResponse response = (HttpServletResponse) context().getExternalContext().getResponse();
//                    response.sendRedirect(getURLAplicacao() + "/faces/inicialCatalogo.jsp");
//                }
//            }
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro_conectarBD", e.getMessage());
        }
    }

    public String login() {
        try {
            if (Usuario.consultarSeUsuarioBloqueado(getUsername())) {
                throw new Exception("Acesso Bloqueado!");
            }
            Integer segurancaTentativasAcessoAntesBloqueio = ConfiguracaoSistema.consultarCampoInteger("segurancaTentativasAcessoAntesBloqueio", getEmpresaLogin());
            Integer segurancaHorasParaZerarTentativasAcesso = ConfiguracaoSistema.consultarCampoInteger("segurancaHorasParaZerarTentativasAcesso", getEmpresaLogin());
            UsuarioVO usuario = ControleAcesso.verificarLoginUsuario(getUsername(), getSenha(), getIpUsuarioRemoto(), segurancaTentativasAcessoAntesBloqueio, segurancaHorasParaZerarTentativasAcesso);
            PerfilAcessoVO perfil = obterPerfilAcesso(usuario.getCodigo(), getEmpresaLogin());
            if (perfil.getCodigo().equals(0)) {
                throw new Exception("Acesso Negado! Empresa, Usuário e Senha não Conferem!");
            }
            if (usuario.getBloquearAcesso()) {
                throw new Exception("Acesso Bloqueado!");
            }
            setEmpresa(obterEmpresaLogada(getEmpresaLogin()));
            setNomeEmpresa(getEmpresa().getNomeFantasia());
            //caso seja o sistema seja desbloqueado ou bloqueado, por isso é feito sempre uma nova consulta da dataBloqueio.
            getEmpresa().setDataBloqueio(getFacadeFactory().getEmpresaFacade().consultarDataBloqueio(getEmpresa().getCodigo()));

            Calendar calendarDataBloqueio = Calendar.getInstance();
            if (getEmpresa().getDataBloqueio() != null) {
                calendarDataBloqueio.setTime(getEmpresa().getDataBloqueio());
                if (getEmpresa().getDataBloqueio() != null && !getUsername().equals("admin") && Uteis.obterNrDiasEntreDatas(new Date(), getEmpresa().getDataBloqueio()) <= 0) {
//                if (getEmpresa().getDataBloqueio() != null && !perfil.getAdministrador() && Uteis.obterNrDiasEntreDatas(new Date(), getEmpresa().getDataBloqueio()) <= 0) {
                    if (Uteis.obterNrDiasEntreDatas(new Date(), getEmpresa().getDataBloqueio()) < 0) {
                        // sempre que a diferença entre a data atual e a data de bloqueio for menor que 0, não irá verificar a hora
                        throw new Exception("Licença de uso Expirada!");
                    } else if (Uteis.obterNrDiasEntreDatas(new Date(), getEmpresa().getDataBloqueio()) == 0) {
                        if (new Date().getTime() >= Uteis.getDateTimeReturnLong(getEmpresa().getDataBloqueio(), calendarDataBloqueio.get(Calendar.HOUR_OF_DAY), calendarDataBloqueio.get(Calendar.MINUTE), calendarDataBloqueio.get(Calendar.SECOND))) {
                            throw new Exception("Licença de uso Expirada!");
                        }
                    }
                }
            }
            setNome(usuario.getNome());
            setUsuario(usuario);
            setPerfilAcesso(perfil);

            validarPeriodoAcessoUsuario(usuario);
            validarUsuarioLogadoOutraEmpresa(usuario);
            validarLimiteUsuariosSimultaneos();

            montarPermissoesMenu(getPerfilAcesso());

            //setConfiguracaoSistema(getFacadeFactory().getConfiguracaoSistemaFacade().consultarSeExisteConfiguracaoSistema(getEmpresa().getCodigo()));
            verificarImagemBannerSistema();
            if (!threadDeletarArquivoDanfe) {
                iniciarThreadDeletarArquivosDanfe();
            }
            Usuario.alterarDataUltimoAcesso(getUsuarioLogado().getCodigo());
            setMensagemID("msg_entre_login");
            String index = definirPaginaAlteracaoSenha();
            if (!index.isEmpty()) {
                return index;
            }
            return definirPaginaPosLogin();
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "erroLogin";
        }
    }

    public void validarPeriodoAcessoUsuario(UsuarioVO usuario) throws Exception {
        for (UsuarioPeriodoAcessoVO x : usuario.getUsuarioPeriodoAcessoVOs()) {
            if (x.getDiaSemana().equals(Uteis.getDiaSemana(new Date()))) {
                if (!x.getAcessoPermitido()) {
                    if (!validarSeAcessoDiaAnteriorPermiteProximoDia(usuario.getUsuarioPeriodoAcessoVOs())) {
                        throw new Exception("Seu acesso não está configurado para este dia.");
                    } else {
                        return;
                    }
                }
                Integer hora = x.getHorasFim();
                Integer minuto = x.getMinutosFim();
                if (Uteis.getHoraAtualNumerico() > hora) {
                    throw new Exception("Acesso negado! Fora do horário configurado.");
                }
                if (Uteis.getHoraAtualNumerico() == hora) {
                    if (minuto.equals(0)) {
                        throw new Exception("Acesso negado! Fora do horário configurado.");
                    } else {
                        if (Uteis.getMinutosAtualNumerico() > minuto) {
                            throw new Exception("Acesso negado! Fora do horário configurado.");
                        }
                    }
                }

                hora = x.getHorasInicio();
                minuto = x.getMinutosInicio();
                if (Uteis.getHoraAtualNumerico() < hora) {
                    if (!validarSeAcessoDiaAnteriorPermiteProximoDia(usuario.getUsuarioPeriodoAcessoVOs())) {
                        throw new Exception("Acesso negado! Fora do horário configurado.");
                    } else {
                        return;
                    }
                }
                if (Uteis.getHoraAtualNumerico() == hora) {
                    if (minuto > Uteis.getMinutosAtualNumerico()) {
                        if (!validarSeAcessoDiaAnteriorPermiteProximoDia(usuario.getUsuarioPeriodoAcessoVOs())) {
                            throw new Exception("Acesso negado! Fora do horário configurado.");
                        } else {
                            return;
                        }
                    }
                }
                return;
            }
        }
    }

    private Boolean validarSeAcessoDiaAnteriorPermiteProximoDia(List<UsuarioPeriodoAcessoVO> lista) throws Exception {
        int ontem = Uteis.getDiaSemana(new Date()) - 1;
        if (ontem == 0) {
            ontem = 7;
        }
        for (UsuarioPeriodoAcessoVO x : lista) {
            if (x.getDiaSemana().equals(ontem)) {
                if (!x.getAcessoPermitido()) {
                    return false;
                }
                if (x.getAcessoExtendeProximoDia()) {
                    Integer hora = x.getHorasFim();
                    Integer minuto = x.getMinutosFim();
                    if (Uteis.getHoraAtualNumerico() > hora) {
                        return false;
                    }
                    if (Uteis.getHoraAtualNumerico() == hora) {
                        if (minuto.equals(0)) {
                            return false;
                        } else {
                            if (Uteis.getMinutosAtualNumerico() > minuto) {
                                return false;
                            }
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public String definirPaginaAlteracaoSenha() throws Exception {
        if (getUserNameIsAdmin()) {
            return "";
        }
        if (getUsuarioLogado().getDataExpiracaoSenha() == null) {
            getUsuarioLogado().setDataExpiracaoSenha(Uteis.incrementarData(new Date(), -6));
            Usuario.alterarDataExpiracaoSenha(getUsuarioLogado().getCodigo(), getUsuarioLogado().getDataExpiracaoSenha());
        }
        if (Uteis.obterNrDiasEntreDatas(new Date(), getUsuarioLogado().getDataExpiracaoSenha()) <= 7) {
            if (definirIndex().equals("android")) {
                return "inicialMudarSenhaMobile";
            } else {
                return "inicialMudarSenha";
            }
        }
        return "";
    }

    public String definirPaginaPosLogin() throws Exception {
        if (getPerfilAcesso().getTipoPerfilAbate()) {
            boolean localizado = false;
            for (PermissaoVO objPermissao : (ArrayList<PermissaoVO>) getPerfilAcessoLogado().getPermissaoVOs()) {
                if (objPermissao.getNomeEntidade().equals("LoteAbate")) {
                    localizado = true;
                }
            }
            if (!localizado) {
                throw new Exception("O usuário não tem permissão para a operação LOTE DE ABATE");
            }
            return "inicialAbate";
        }

        if (getPerfilAcesso().getTipoPerfilBalancao()) {
            return "inicialBalancao";
        }
        if (getPerfilAcesso().getTipoPerfilCarregamentoMobile()) {
            return "inicialCarregamento";
        }
        if (getPerfilAcesso().getTipoPerfilVendaBalcao()) {
            return "inicialVendaBalcao";
        }
        if (getPerfilAcesso().getTipoPerfilPDV()) {
            return "inicialPdv";
        }
        if (getPerfilAcesso().getTipoPerfilGestorGraxaria()) {
            return "inicialGestorGraxaria";
        }
        if (getPerfilAcesso().getTipoPerfilProducaoGraxaria()) {
            return "inicialProducaoGraxaria";
        }
        if (getPerfilAcesso().getTipoPerfilVendedorExterno()) {
            if (getPerfilAcesso().getDispositivoAcessoExterno().equals("SMARTPHONE")) {
                return "inicialVendedorSmartphone";
            } else {
                return "inicialVendedor";
            }
        }
        if (getPerfilAcesso().getTipoPerfilRepresentante()) {
            return "inicialRepresentante";
        }
        if (getPerfilAcesso().getTipoPerfilContador()) {
            return "inicialContador";
        }
        if (getPerfilAcesso().getTipoPerfilCaixa()) {
            return "homeCaixa";
        }
        if (getPerfilAcesso().getTipoPerfilProducao()) {
            return "inicialProducao";
        }
        if (getPerfilAcesso().getTipoPerfilEstoque()) {
            return "inicialEstoque";
        }
        if (getPerfilAcesso().getTipoPerfilColeta()) {
            return "inicialColeta";
        }
        if (getPerfilAcesso().getTipoPerfilVendaBalcaoMobile()) {
            return "inicialVendaBalcaoMobile";
        }
        return "login";
    }


    public String definirIndex() {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("Android")) {
            return "android";
        } else if (userAgent.contains("iPhone")) {
            return "android";
        } else {
            return "";
        }
    }

    public void validarLimiteUsuariosSimultaneos() throws Exception {
        removerUsuariosTelaFechada();
        if (!getUsername().equals("admin")) {
            if (getListaLoginContemUsername(getUsername())) {
                throw new Exception("Este usuário já está logado no sistema.");
            }
            if (getPerfilAcessoLogado().getTipoPerfilVendedorExterno()) {
                if (getQuantidadeUsuariosLoginEmpresaVendedorExterno() >= getEmpresaLogado().getLimiteUsuariosSimultaneosVendedorExterno()) {
                    throw new Exception("Limite de vendedores simultâneos atingido.<br>Tente novamente em alguns instantes.");
                }
            } else {
                if (getQuantidadeUsuariosLoginEmpresaGerencial() >= getEmpresaLogado().getLimiteUsuariosSimultaneos()) {
                    throw new Exception("Limite de usuários simultâneos atingido.<br>Tente novamente em alguns instantes.");
                }
            }
            registrarUsuarioLogin();
        }
    }

    public void registrarUsuarioLogin() throws Exception {
        removerUsuariosTelaFechada();
        if (!getListaLoginContemId(getIdSessao())) {
            LoginUsuarioVO obj = new LoginUsuarioVO();
            obj.setDataLogin(new Date());
            obj.setDataUltimaUtilizacao(new Date());
            obj.setEmpresa(getCodigoEmpresaLogado());
            obj.setNome(getNome());
            obj.setUsername(getUsername());
            obj.setPerfilAcesso(getPerfilAcessoLogado().getNome());
            obj.setTipoPerfilAcesso(getPerfilAcessoLogado().getTipoPerfilVendedorExterno() ? "V" : "G");
            HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
            obj.setIp(request.getRemoteAddr());
            obj.setSessao((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
            obj.setIdSessao(getIdSessao());
            getListaUsuariosLoginEmpresa().add(obj);
            System.out.println("SESSÃO CRIADA: " + getUsername() + " - " + getNome());
        }
    }

    public String getIdSessao() {
        return ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getId();
    }

    public void registrarUsuarioLogout() throws Exception {
        if (getListaLoginContemId(getIdSessao())) {
            getListaLoginRemove(getListaLoginGet(getIdSessao()));
            System.out.println("SESSÃO REMOVIDA: " + getUsername() + " - " + getNome());
        }
    }

    public void removerAcessoUsuario() throws Exception {
        LoginUsuarioVO obj = (LoginUsuarioVO) context().getExternalContext().getRequestMap().get("loginUsuario");
        getListaLoginRemove(getListaLoginGet(obj.getIdSessao()));
        System.out.println("SESSÃO REMOVIDA: " + obj.getUsername() + " - " + obj.getNome());
    }

    public void validarUsuarioLogadoOutraEmpresa(UsuarioVO usuario) throws Exception {
        String navegador = "";
        /**
         * IE=Internet Explorer ED=Microsoft Edge CR=Chrome FF=Firefox OU=Outros
         */
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("Trident") && userAgent.contains("like Gecko")) {
            navegador = "IE";
        } else if (userAgent.contains("Chrome")) {
            if (userAgent.contains("Edge")) {
                navegador = "ED";
            } else {
                navegador = "CR";
            }
        } else if (userAgent.contains("Firefox")) {
            navegador = "FF";
        } else {
            navegador = "OU";
        }
        SuperEntidade.consultarAcessoUsuario(getUsuarioLogado().getCodigo(), getCodigoEmpresaLogado(), navegador);
    }

    public void excluirAcessoUsuarioLogadoOutraEmpresa(UsuarioVO usuario) throws Exception {
        String navegador = "";
        /**
         * IE=Internet Explorer ED=Microsoft Edge CR=Chrome FF=Firefox OU=Outros
         */
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("Trident") && userAgent.contains("like Gecko")) {
            navegador = "IE";
        } else if (userAgent.contains("Chrome")) {
            if (userAgent.contains("Edge")) {
                navegador = "ED";
            } else {
                navegador = "CR";
            }
        } else if (userAgent.contains("Firefox")) {
            navegador = "FF";
        } else {
            navegador = "OU";
        }

        SuperEntidade.deletarAcessoUsuario(getUsuarioLogado().getCodigo(), getCodigoEmpresaLogado(), navegador);
    }

    public void iniciarThreadDeletarArquivosDanfe() throws Exception {
        threadDeletarArquivoDanfe = true;
        DeletarArquivoPDFDanfe deletarArquivoDanfe = new DeletarArquivoPDFDanfe();
        deletarArquivoDanfe.setMinutosParaProximaExecucao(1440);
        deletarArquivoDanfe.setExtensao("pdf");
        deletarArquivoDanfe.setPastaArquivosASeremDeletados(obterCaminhoDiretorioRelatorio());
        new Thread(deletarArquivoDanfe).start();
        /**
         * Deletar Boletos
         */
        DeletarArquivoPDFDanfe deletarArquivoBoleto = new DeletarArquivoPDFDanfe();
        deletarArquivoBoleto.setMinutosParaProximaExecucao(1440);
        deletarArquivoBoleto.setExtensao("pdf");
        deletarArquivoBoleto.setPastaArquivosASeremDeletados(obterCaminhoDiretorioWeb() + File.separator + "NFe");
        new Thread(deletarArquivoBoleto).start();

        DeletarArquivoPDFDanfe deletarArquivoXls = new DeletarArquivoPDFDanfe();
        deletarArquivoXls.setMinutosParaProximaExecucao(1440);
        deletarArquivoXls.setExtensao("xls");
        deletarArquivoXls.setPastaArquivosASeremDeletados(obterCaminhoDiretorioWeb() + File.separator + "relatorio");
        new Thread(deletarArquivoXls).start();
    }

    /*
     * Método que verifica se a imagem da página inicial do sistema está na pasta, caso não esteja, quando for feito o primeiro login será criada a partir
     * da que está no banco de dados.
     */
    public void verificarImagemBannerSistema() throws Exception {

        String caminho = obterCaminhoDiretorioWeb() + File.separator + "imagens" + File.separator + "imagemBanner" + Uteis.removerMascara(getEmpresaLogado().getCNPJ()) + ".jpg";

        File verificarArquivo = new File(caminho);
        if (!verificarArquivo.exists()) {
            FileOutputStream outPut = new FileOutputStream(caminho);
            outPut.write(getConfiguracaoEmpresaLogado().getImagemBanner());
            outPut.flush();
            outPut.close();
        }
    }

    public void montarPermissoesMenu(PerfilAcessoVO perfilAcesso) {
        setPermissaoAcessoMenuVO(getPermissaoAcessoMenuVO().montarPermissoesMenu(perfilAcesso.getPermissaoVOs()));
    }

    public void mudarSenha() {
        try {
            mudarSenhaSemTratarErro();
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public String mudarSenhaExpirada() {
        try {
            mudarSenhaSemTratarErro();
            return definirPaginaPosLogin();
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "";
        }
    }

    public void mudarSenhaSemTratarErro() throws Exception {
        if (!validarRegrasSenha()) {
            limparMensagemAlteracaoSenha();
            throw new Exception("Senha não condiz com as políticas de segurança");
        }
        UsuarioVO usuario = ControleAcesso.verificarLoginUsuario(getUsuarioLogado().getUsername(), getSenha(), getIpUsuarioRemoto(), getConfiguracaoEmpresaLogado().getSegurancaTentativasAcessoAntesBloqueio(), getConfiguracaoEmpresaLogado().getSegurancaHorasParaZerarTentativasAcesso());
        if (usuario.getCodigo() != 0) {
            if (getNovaSenha().equals(getNovaSenha2())) {
                usuario.setSenha(novaSenha);
                getFacadeFactory().getUsuarioFacade().alterarSenha(usuario, true);
                getUsuarioLogado().setDataExpiracaoSenha(Uteis.incrementarData(new Date(), getConfiguracaoEmpresaLogado().getSegurancaPrazoExpiracaoSenha()));
                Usuario.alterarDataExpiracaoSenha(getUsuarioLogado().getCodigo(), getUsuarioLogado().getDataExpiracaoSenha());
            } else {
                setMensagemDetalhada("msg_erro", "Nova Senha não Confere");
                return;
            }
        } else {
            setMensagemDetalhada("msg_erro", "Senha Atual não Confere");
            return;
        }
        setSenha("");
        setNovaSenha("");
        setNovaSenha2("");
        setMensagemDetalhada("Senha Alterada com Sucesso");
    }

    public String logout() {
        try {
            excluirAcessoUsuarioLogadoOutraEmpresa(getUsuarioLogado());
            setUsuario(new UsuarioVO());
            setPerfilAcesso(new PerfilAcessoVO());
            registrarUsuarioLogout();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.invalidate();
            removerTodosManagedBean();
            setMensagemID("msg_entre_prmlogout");
            return "erroLogin";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "login";
        }
    }

    /**
     * Método responsável por gerar uma lista de objetos do tipo
     * <code>SelectItem</code> para preencher o comboBox relativo ao atributo
     * <code>CodPerfilAcesso</code>.
     */
    public void montarListaSelectItemEmpresa(String prm) throws Exception {
        List resultadoConsulta = consultarEmpresaPorNome(prm);
        Iterator i = resultadoConsulta.iterator();
        List objs = new ArrayList(0);
        //objs.add(new SelectItem(0, ""));
        while (i.hasNext()) {
            EmpresaVO obj = (EmpresaVO) i.next();
            objs.add(new SelectItem(obj.getCodigo(), obj.getNomeFantasia().toString()));
        }
        setListaSelectItemEmpresa(objs);
    }

    /**
     * Método responsável por atualizar o ComboBox relativo ao atributo
     * <code>CodPerfilAcesso</code>. Buscando todos os objetos correspondentes a
     * entidade <code>PerfilAcesso</code>. Esta rotina não recebe parâmetros
     * para filtragem de dados, isto é importante para a inicialização dos
     * dados da tela para o acionamento por meio requisições Ajax.
     */
    public void montarListaSelectItemEmpresa() {
        try {
            montarListaSelectItemEmpresa("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método responsável por consultar dados da entidade <code><code> e montar o atributo
     * <code>nome</code> Este atributo é uma lista (<code>List</code>)
     * utilizada para definir os valores a serem apresentados no ComboBox
     * correspondente
     */
    public List consultarEmpresaPorNome(String nomePrm) throws Exception {
        List lista = getFacadeFactory().getEmpresaFacade().consultarPorNomeFantasiaSituacao(nomePrm, "ATIVA", false);
        return lista;
    }

    public void consultarLogObjetoSelecionado(String nomeEntidade, Integer codigoEntidade) {
        List lista = new ArrayList(0);
        setListaConsultaLog(new ArrayList(0));
        try {
            lista = getFacadeFactory().getLogFacade().consultarPorNomeCodigoEntidade(nomeEntidade, codigoEntidade, null, null, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            setListaConsultaLog(lista);
        } catch (Exception e) {
            setListaConsultaLog(new ArrayList(0));
            getListaConsultaLog().clear();
        }
    }

    public void selecionarDadosLog() throws Exception {
        LogVO obj = (LogVO) context().getExternalContext().getRequestMap().get("log");
        setLogVO(obj);
    }

    public String getNomeEntidadeCamposLog(String identificador) {
        String nomes = identificador;
        ResourceBundle bundle = null;
        Locale locale = null;
        String nomeBundle = "propriedades.aplicacao";
        if (nomeBundle != null) {
            locale = context().getViewRoot().getLocale();
            bundle = ResourceBundle.getBundle(nomeBundle, locale);
            try {
                nomes = bundle.getString(identificador);
                return nomes;
            } catch (MissingResourceException e) {
                return nomes;
            }
        }
        return nomes;
    }

    public String getNomeMenuLog(String identificador) {
        String nomes = identificador;
        ResourceBundle bundle = null;
        Locale locale = null;
        String nomeBundle = "propriedades.menu";
        if (nomeBundle != null) {
            locale = context().getViewRoot().getLocale();
            bundle = ResourceBundle.getBundle(nomeBundle, locale);
            try {
                nomes = bundle.getString(identificador);
                return nomes;
            } catch (MissingResourceException e) {
                return nomes;
            }
        }
        return nomes;
    }

    public String getAlinhamentoMenu() {
        return "10px";
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List getListaSelectItemEmpresa() {
        return ListaSelectItemEmpresa;
    }

    public void setListaSelectItemEmpresa(List ListaSelectItemEmpresa) {
        this.ListaSelectItemEmpresa = ListaSelectItemEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public List getListaConsultaLog() {
        return listaConsultaLog;
    }

    public void setListaConsultaLog(List listaConsultaLog) {
        this.listaConsultaLog = listaConsultaLog;
    }

    public LogVO getLogVO() {
        return logVO;
    }

    public void setLogVO(LogVO logVO) {
        this.logVO = logVO;
    }

    public PermissaoAcessoMenuVO getPermissaoAcessoMenuVO() {
        return permissaoAcessoMenuVO;
    }

    public void setPermissaoAcessoMenuVO(PermissaoAcessoMenuVO permissaoAcessoMenuVO) {
        this.permissaoAcessoMenuVO = permissaoAcessoMenuVO;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getNovaSenha2() {
        return novaSenha2;
    }

    public void setNovaSenha2(String novaSenha2) {
        this.novaSenha2 = novaSenha2;
    }

    public List getListaPendenciaSistema() {
        return listaPendenciaSistema;
    }

    public void setListaPendenciaSistema(List listaPendenciaSistema) {
        this.listaPendenciaSistema = listaPendenciaSistema;
    }

    public UsuarioFavoritosVO getFavorito() {
        if (favorito == null) {
            favorito = new UsuarioFavoritosVO();
        }
        return favorito;
    }

    public void setFavorito(UsuarioFavoritosVO favorito) {
        this.favorito = favorito;
    }

    public Integer getEmpresaLogin() {
        if (empresaLogin == null) {
            empresaLogin = 0;
        }
        return empresaLogin;
    }

    public void setEmpresaLogin(Integer empresaLogin) {
        this.empresaLogin = empresaLogin;
    }

    public Boolean getListaLoginContemUsername(String username) {
        for (LoginUsuarioVO obj : getListaUsuariosLoginEmpresa()) {
            if (obj.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Boolean getListaLoginContemId(String id) {
        for (LoginUsuarioVO obj : getListaUsuariosLoginEmpresa()) {
            if (obj.getIdSessao().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void getListaLoginRemove(LoginUsuarioVO obj) {
        getListaUsuariosLoginEmpresa().remove(obj);
    }

    public LoginUsuarioVO getListaLoginGet(String id) {
        for (LoginUsuarioVO obj : getListaUsuariosLoginEmpresa()) {
            if (obj.getIdSessao().equals(id)) {
                return obj;
            }
        }
        return null;
    }

    private Map<Integer, List<LoginUsuarioVO>> getListaLogin() {
        if (listaLogin == null) {
            listaLogin = new HashMap<Integer, List<LoginUsuarioVO>>();
        }
        return listaLogin;
    }

    public Integer getListaUsuariosLoginEmpresaSize() {
        return getListaUsuariosLoginEmpresa().size();
    }

    public List<LoginUsuarioVO> getListaUsuariosLoginEmpresa() {
        List<LoginUsuarioVO> lista = getListaLogin().get(getCodigoEmpresaLogado());
        if (lista == null) {
            lista = new ArrayList<LoginUsuarioVO>();
            listaLogin.put(getCodigoEmpresaLogado(), lista);
        }
        return lista;
    }

    public Integer getQuantidadeUsuariosLoginEmpresaGerencial() {
        Integer total = 0;
        for (LoginUsuarioVO u : getListaUsuariosLoginEmpresa()) {
            if (u.getTipoPerfilAcessoGerencial()) {
                total++;
            }
        }
        return total;
    }

    public Integer getQuantidadeUsuariosLoginEmpresaVendedorExterno() {
        Integer total = 0;
        for (LoginUsuarioVO u : getListaUsuariosLoginEmpresa()) {
            if (u.getTipoPerfilAcessoVendedorExterno()) {
                total++;
            }
        }
        return total;
    }

    public void removerUsuariosTelaFechada() {
        boolean chegouFinalLista = false;
        while (!chegouFinalLista) {
            chegouFinalLista = true;
            List<LoginUsuarioVO> lista = null;
            for (Map.Entry<Integer, List<LoginUsuarioVO>> e : getListaLogin().entrySet()) {
                lista = e.getValue();
                if (lista == null) {
                    lista = new ArrayList<LoginUsuarioVO>(0);
                }
                for (int i = 0; i < lista.size(); i++) {
                    LoginUsuarioVO obj = lista.get(i);
                    if (!obj.getTelaEstaAberta()) {
                        Integer codigoUsuario = null;
                        try {
                            codigoUsuario = Usuario.consultarCodigoUsuarioPorUsername(obj.getUsername());
                            SuperEntidade.deletarAcessoUsuarioSemNavegador(codigoUsuario, obj.getEmpresa());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        lista.remove(i);
                        chegouFinalLista = false;
                        break;
                    }
                }
            }
        }
    }

    public String atualizarTelaAberta() {
        removerUsuariosTelaFechada();
        if (!getListaLoginContemId(getIdSessao())) {
            if (!getUsername().equals("admin")) {
                return logout();
            }
        }
        setDataTelaAberta(new Date());
        return "";
    }

    public Date getDataTelaAberta() {
        return dataTelaAberta;
    }

    public void setDataTelaAberta(Date dataTelaAberta) {
        this.dataTelaAberta = dataTelaAberta;
    }

    public String getLabelUsuariosLogadosDisponiveis() {
        return (getEmpresaLogado().getLimiteUsuariosSimultaneos() - getListaUsuariosLoginEmpresaSize()) == 1 ? "Disponível" : "Disponíveis";
    }

    public Boolean getTamanhoSenha() {
        if (getNovaSenha().length() >= getConfiguracaoEmpresaLogado().getSegurancaTamanhoSenhaUsuario()) {
            return true;
        }
        return false;
    }

    public String getLabelTamanhoSenha() {
        if (getTamanhoSenha()) {
            return "<p style=\"color: #000000; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #008800;\"></i>&nbsp;Mínimo " + getConfiguracaoEmpresaLogado().getSegurancaTamanhoSenhaUsuario() + " caracteres</p>";
        }
        return "<p style=\"color: #AAAAAA; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #880000;\"></i>&nbsp;Mínimo " + getConfiguracaoEmpresaLogado().getSegurancaTamanhoSenhaUsuario() + " caracteres</p>";
    }

    public Boolean getSenhaLetraMaiuscula() {
        for (char c : getNovaSenha().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                return true;
            }
        }
        return false;
    }

    public String getLabelSenhaLetraMaiuscula() {
        if (getSenhaLetraMaiuscula()) {
            return "<p style=\"color: #000000; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #008800;\"></i>&nbsp;Uma letra maiúscula</p>";
        }
        return "<p style=\"color: #AAAAAA; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #880000;\"></i>&nbsp;Uma letra maiúscula</p>";
    }

    public Boolean getSenhaNumero() {
        for (char c : getNovaSenha().toCharArray()) {
            if (c >= '0' && c <= '9') {
                return true;
            }
        }
        return false;
    }

    public String getLabelSenhaNumero() {
        if (getSenhaNumero()) {
            return "<p style=\"color: #000000; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #008800;\"></i>&nbsp;Um número</p>";
        }
        return "<p style=\"color: #AAAAAA; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #880000;\"></i>&nbsp;Um número</p>";
    }

    public Boolean getSenhaIdenticos() {
        if (!getNovaSenha().isEmpty() && getNovaSenha().equals(getNovaSenha2())) {
            return true;
        }
        return false;
    }

    public String getLabelSenhaIdenticos() {
        if (getSenhaIdenticos()) {
            return "<p style=\"color: #000000; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #008800;\"></i>&nbsp;As senhas devem ser idênticas</p>";
        }
        return "<p style=\"color: #AAAAAA; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #880000;\"></i>&nbsp;As senhas devem ser idênticas</p>";
    }

    public Boolean getSenhaCaractereEspecial() {
        if (getNovaSenha().matches(".*[^\\w].*")) {
            return true;
        }
        return false;
    }

    public String getLabelSenhaCaractereEspecial() {
        if (getSenhaCaractereEspecial()) {
            return "<p style=\"color: #000000; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #008800;\"></i>&nbsp;Um Caractere Especial</p>";
        }
        return "<p style=\"color: #AAAAAA; font-size: 8pt;\"><i class=\"fa fa-square-check\" style=\"color: #880000;\"></i>&nbsp;Um Caractere Especial</p>";
    }


    public Boolean getApresentarBotaoMudarSenha() {
        return validarRegrasSenha();
    }

    public Boolean validarRegrasSenha() {
        if (!getTamanhoSenha()) {
            return false;
        }
        if (getConfiguracaoEmpresaLogado().getSegurancaSenhaExigirNumero()) {
            if (!getSenhaNumero()) {
                return false;
            }
        }
        if (getConfiguracaoEmpresaLogado().getSegurancaSenhaExigirLetraMaiuscula()) {
            if (!getSenhaLetraMaiuscula()) {
                return false;
            }
        }
        if (getConfiguracaoEmpresaLogado().getSegurancaSenhaExigirCaractereEspecial()) {
            if (!getSenhaCaractereEspecial()) {
                return false;
            }
        }
        if (!getSenhaIdenticos()) {
            return false;
        }
        return true;
    }

    public String getCssMensagemMudarSenha() {
        if (getMensagemDetalhada().equals("Senha Alterada com Sucesso")) {
            return "color: #008800";
        }
        return "color: #880000";
    }

    public String getCssBotaoMudarSenhaMobile() {
        if (validarRegrasSenha()) {
            return "btn";
        }
        return "btnDesabilitado";
    }

    public String getCssBotaoMudarSenha() {
        if (validarRegrasSenha()) {
            return "botoes";
        }
        return "botaoDesabilitado";
    }

    public void limparMensagemAlteracaoSenha() {
        setMensagemDetalhada("");
        setNovaSenha("");
        setNovaSenha2("");
    }

    public Boolean getSenhaExpirada() {
        if (Uteis.obterNrDiasEntreDatas(new Date(), getUsuarioLogado().getDataExpiracaoSenha()) <= 0) {
            return true;
        }
        return false;
    }

    public String getMensagemTelaSenhaExpirada() {
        if (Uteis.obterNrDiasEntreDatas(new Date(), getUsuarioLogado().getDataExpiracaoSenha()) <= 0) {
            return "Sua senha expirou. Altere-a para acessar o sistema";
        }
        if (Uteis.obterNrDiasEntreDatas(new Date(), getUsuarioLogado().getDataExpiracaoSenha()) <= 7) {
            return "Sua senha expira em " + Uteis.obterNrDiasEntreDatas(new Date(), getUsuarioLogado().getDataExpiracaoSenha()) + " dias";
        }
        return "&nbsp;";
    }

    public HashMap<String, Boolean> getListaFrame() {
        if (listaFrame == null) {
            listaFrame = new HashMap<>(0);
            listaFrame.put("dashboard", true);
            listaFrame.put("projeto", false);
            listaFrame.put("entidade", false);
            listaFrame.put("empresa", false);
            listaFrame.put("perfilAcesso", false);
            listaFrame.put("usuario", false);
            listaFrame.put("configuracao", false);
        }
        return listaFrame;
    }

    public void setListaFrame(HashMap<String, Boolean> listaFrame) {
        this.listaFrame = listaFrame;
    }

    private void selecionarTodosFramesFalse() {
        for (Map.Entry<String, Boolean> frame : getListaFrame().entrySet()) {
            frame.setValue(false);
        }
    }

    public Boolean getApresentarFrameDashboard() {
        return getListaFrame().get("dashboard");
    }

    public Boolean getApresentarFrameProjeto() {
        return getListaFrame().get("projeto");
    }

    public Boolean getApresentarFrameEntidade() {
        return getListaFrame().get("entidade");
    }

    public Boolean getApresentarFrameEmpresa() {
        return getListaFrame().get("empresa");
    }

    public Boolean getApresentarFramePerfilAcesso() {
        return getListaFrame().get("perfilAcesso");
    }

    public Boolean getApresentarFrameUsuario() {
        return getListaFrame().get("usuario");
    }

    public Boolean getApresentarFrameConfiguracao() {
        return getListaFrame().get("configuracao");
    }

    public void selecionarFrameDashboard() {
        selecionarTodosFramesFalse();
        getListaFrame().put("dashboard", true);
    }

    public void selecionarFrameEmpresa() {
        try {
            selecionarTodosFramesFalse();
            EmpresaControle controle = (EmpresaControle) getControlador("EmpresaControle");
            if (controle == null) {
                controle = new EmpresaControle();
            }
            setControlador("EmpresaControle", controle);
            getListaFrame().put("empresa", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selecionarFrameProjeto() {
        try {
            selecionarTodosFramesFalse();
            ProjetoControle controle = (ProjetoControle) getControlador("ProjetoControle");
            if (controle == null) {
                controle = new ProjetoControle();
            }
            setControlador("ProjetoControle", controle);
            getListaFrame().put("projeto", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selecionarFrameEntidade() {
        try {
            selecionarTodosFramesFalse();
            EntidadeControle controle = (EntidadeControle) getControlador("EntidadeControle");
            if (controle == null) {
                controle = new EntidadeControle();
            }
            setControlador("EntidadeControle", controle);
            getListaFrame().put("entidade", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selecionarFramePerfilAcesso() {
        try {
            selecionarTodosFramesFalse();
            PerfilAcessoControle controle = (PerfilAcessoControle) getControlador("PerfilAcessoControle");
            if (controle == null) {
                controle = new PerfilAcessoControle();
            }
            setControlador("PerfilAcessoControle", controle);
            getListaFrame().put("perfilAcesso", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selecionarFrameUsuario() {
        try {
            selecionarTodosFramesFalse();
            PerfilAcessoControle controle = (PerfilAcessoControle) getControlador("PerfilAcessoControle");
            if (controle == null) {
                controle = new PerfilAcessoControle();
            }
            setControlador("PerfilAcessoControle", controle);
            getListaFrame().put("usuario", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void selecionarFrameConfiguracao() {
        try {
            selecionarTodosFramesFalse();
            ConfiguracaoSistemaControle controle = (ConfiguracaoSistemaControle) getControlador("ConfiguracaoSistemaControle");
            if (controle == null) {
                controle = new ConfiguracaoSistemaControle();
            }
            setControlador("ConfiguracaoSistemaControle", controle);
            getListaFrame().put("configuracao", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
