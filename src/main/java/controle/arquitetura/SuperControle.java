package controle.arquitetura;

import negocio.comuns.arquitetura.*;
import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.PerfilAcesso;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller("SuperControle")
@Scope("session")
@Lazy
public class SuperControle extends SuperArquitetura {

    protected List listaConsulta;
    protected ControleConsulta controleConsulta;
    protected String mensagem = null;
    private String mensagemDetalhada = null;
    private String mensagemID = null;
    private UsuarioVO usuario = null;
    private EmpresaVO empresa = null;
    private PerfilAcessoVO perfilAcesso = null;
    private String versaoSistema = "";
    public static String identificadorEmpresa = "";
    private List<LogVO> listaLog;
    private String modal;
    private String foco;
    private String reRender;
    private static HashMap mapConfiguracaoSistema;
    private static HashMap mapEmpresa;
    private static HashMap mapPerfilAcesso;


    public SuperControle() {
        //obterUsuarioLogado();
        inicializarVersaoSistema();
    }

    public void liberarBackingBeanMemoria() {
        try {
            String nomeBackingBean = this.getClass().getSimpleName();

//            if (nomeBackingBean.equals("LoginControle")) {
//                ((LoginControle) getControlador("LoginControle")).logout();
//                System.out.println("=========AQUI========= LOGOUT REALIZADO");
//            } else {
            if (!nomeBackingBean.equals("LoginControle")) {
                removerManagedBean(nomeBackingBean);
                System.out.println("=========AQUI========= Backing da Memória " + this.getClass().getSimpleName() + " REMOVIDO DA MEMÓRIA.");
            } else {
                throw new Exception("=========TENTATIVA DE REMOÇÃO DO LOGINCONTROLE NÃO SUPORTADA========= Aplicação tentando remover o Login Controle." + this.getClass().getSimpleName());
            }
//            }

        } catch (Exception e) {
            System.out.println("Não conseguimos remover o Backing da Memória:" + this.getClass().getSimpleName());
        }
    }

    public String getCaminhoBase() throws Exception {
        String caminhoBaseAplicacao = Class.forName(SuperControle.class.getName()).getResource(File.separator).getPath();
        caminhoBaseAplicacao = caminhoBaseAplicacao.replaceAll("%20", " ");
        return caminhoBaseAplicacao;
    }

    protected void inicializarVersaoSistema() {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        //String versao = servletContext.getInitParameter("versaoSoftware");
        //setVersaoSistema("1." + SuperEntidade.obterCodigoSQL());
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        identificadorEmpresa = request.getContextPath().substring(1, request.getContextPath().length());

    }

    public UsuarioVO getUsuarioLogado() {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        UsuarioVO usuarioVO = loginControle.getUsuario();
        return usuarioVO;
    }

    public PerfilAcessoVO getPerfilAcessoLogado() {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        PerfilAcessoVO perfil = loginControle.getPerfilAcesso();
        return perfil;
    }

    public EmpresaVO getEmpresaLogado() {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        return loginControle.getEmpresa();
    }

    public Integer getCodigoEmpresaLogado() {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        return loginControle.getEmpresa().getCodigo();
    }

    public ConfiguracaoSistemaVO getConfiguracaoEmpresaLogado() {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        return loginControle.getConfiguracaoSistema();
    }

    public Boolean getUsuarioIsAdministrador() throws Exception {
        if (getUsuarioLogado().getUsuarioPerfilAcessoVOs().isEmpty()) {
            getUsuarioLogado().setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(getUsuarioLogado().getCodigo()));
        }
        Iterator i = getUsuarioLogado().getUsuarioPerfilAcessoVOs().iterator();
        while (i.hasNext()) {
            UsuarioPerfilAcessoVO obj = (UsuarioPerfilAcessoVO) i.next();
            if (getCodigoEmpresaLogado().equals(obj.getEmpresa().getCodigo())) {
                if (obj.getPerfilAcesso().getAdministrador()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean getUserNameIsAdmin() {
        if (getUsuarioLogado().getUsername().equals("admin")) {
            return true;
        }
        return false;
    }

    public String getValidarUsuarioLogado() {
        obterUsuarioLogado();
        return "";
    }

    public void obterUsuarioLogado() {
        try {
            LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
            if (loginControle != null) {
                if (loginControle.getUsuario().getCodigo().equals(0)) {
                    throw new Exception("Não há Usuário Logado!");
                }
                if (!loginControle.getUsername().equals("admin")) {
                    if (!loginControle.getListaLoginContemId(loginControle.getIdSessao())) {
                        throw new Exception("Seu acesso expirou. Entre novamente no sistema");
                    }
                }
            }
        } catch (Exception e) {
            HttpServletResponse response = (HttpServletResponse) context().getExternalContext().getResponse();
            try {
                response.sendRedirect(getURLAplicacao() + "/faces/index.jsp");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Novo Modelo para pegar caminho da aplicação
    //Sempre que encontrar outro local utilizando outro formato, mudar para este
    public String getCaminhoWebArquivos() {
        try {
            return Uteis.getCaminhoWebArquivos();
        } catch (Exception e) {
            return "";
        }
    }

    //Novo Modelo para pegar caminho da aplicação
    //Sempre que encontrar outro local utilizando outro formato, mudar para este
    public String getCaminhoWeb() {
        try {
            return Uteis.getCaminhoWeb();
        } catch (Exception e) {
            return "";
        }
    }

    public String obterCaminhoWebAplicacao() throws Exception {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        return servletContext.getRealPath("WEB-INF" + File.separator + "classes");
    }

    public String obterCaminhoDiretorioArquivo() throws Exception {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        return servletContext.getRealPath("arquivos");
    }

    public String obterCaminhoDiretorioRelatorio() throws Exception {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        return servletContext.getRealPath("relatorio");
    }

    public String obterCaminhoDiretorioWeb() throws Exception {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        return servletContext.getRealPath("");
    }

    public String obterCaminhoDiretorioImagens() throws Exception {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        return servletContext.getRealPath("imagens");
    }

    public String getURLAplicacao() throws Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        StringBuffer urlAplicacao = request.getRequestURL();
        String url = urlAplicacao.toString();
        url = url.substring(0, urlAplicacao.lastIndexOf("/"));
        url = url.replace("localhost", getLocalhostIP());
        return url.substring(0, url.indexOf(getIdentificadorEmpresa()) + getIdentificadorEmpresa().length());
    }

    public String getLocalhostIP() {
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
            // localHost.getHostName();
            return localHost.getHostAddress();
        } catch (UnknownHostException ex) {
            return "localhost";
        }
    }

    protected void removerManagedBean(String nomeManagedBean) {
        context().getExternalContext().getSessionMap().remove(nomeManagedBean);
    }

    public String alterarLingua_En() {
        Locale local = new Locale("en", "US");
        context().getApplication().setDefaultLocale(local);
        context().getViewRoot().setLocale(local);
        return "alterarLingua";
    }

    public String alterarLingua_Pt() {
        Locale local = new Locale("pt", "BR");
        context().getApplication().setDefaultLocale(local);
        context().getViewRoot().setLocale(local);
        return "alterarLingua";
    }

    protected static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader;
    }

    protected String consultar() throws Exception {
        if (getControleConsulta() == null) {
            setControleConsulta(new ControleConsulta());
        }
        return "consultar";
    }

    public String getMensagem() {
        if ((getMensagemID() != null) && (!getMensagemID().equals(""))) {
            mensagem = getMensagemInternalizacao(getMensagemID());
        }
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer contarQuantidadeDePontos(String frase, String sub) {
        int cont = 0;
        for (int i = 0; i < (frase.length() - sub.length() + 1); i++) {
            String res = frase.substring(i, (i + sub.length()));
            if (res.equals(sub)) {
                cont++;
            }
        }
        return cont;
    }

    public String tratarMensagemErroDetalhada(String mensagemDetalhada) {
        if(mensagemDetalhada == null){
            mensagemDetalhada = "";
        }
        String novaMensagem = mensagemDetalhada;
        if ((novaMensagem.indexOf("duplicar chave viola") != -1)
                || (novaMensagem.indexOf("duplicate key") != -1)) {
            if (novaMensagem.lastIndexOf("_") != -1) {
                String campo = novaMensagem.substring(novaMensagem.lastIndexOf("_") + 1, novaMensagem.length() - 1);
                novaMensagem = "Já existe um registro com este valor para o campo " + campo.toUpperCase() + ".";
            } else {
                novaMensagem = "Já existe um registro gravado com estes valores (" + mensagemDetalhada + ")";
            }
        }
        if ((novaMensagem.indexOf("violates foreign key constraint") != -1)
                || (novaMensagem.indexOf("de chave estrangeira") != -1)) {
            novaMensagem = "Este registro é referenciado por outro cadastro, por isto não pode ser excluído e/ou modificado.";
        }
        return novaMensagem;
    }

    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

    public void setMensagemDetalhada(String mensagemID, String mensagemDetalhada) {
        this.mensagemID = mensagemID;
        this.mensagemDetalhada = tratarMensagemErroDetalhada(mensagemDetalhada);
    }

    public String getMensagemDetalhada() {
        if (mensagemDetalhada == null) {
            mensagemDetalhada = "";
        }
        return mensagemDetalhada;
    }

    public void setMensagemDetalhada(String mensagemDetalhada) {
        this.mensagemDetalhada = mensagemDetalhada;
    }

    public String getMensagemID() {
        return mensagemID;
    }

    public void setMensagemID(String mensagemID) {
        this.mensagemID = mensagemID;
        this.mensagemDetalhada = "";
    }

    public List getListaConsulta() {
        if (listaConsulta == null) {
            listaConsulta = new ArrayList(0);
        }
        return listaConsulta;
    }

    public void setListaConsulta(List listaConsulta) {
        this.listaConsulta = listaConsulta;
    }

    public ControleConsulta getControleConsulta() {
        return controleConsulta;
    }

    public void setControleConsulta(ControleConsulta controleConsulta) {
        this.controleConsulta = controleConsulta;
    }

    public UsuarioVO getUsuario() {
        if (usuario == null) {
            usuario = getUsuarioLogado();
        }
        return usuario;
    }

    protected void setUsuario(UsuarioVO aUsuario) {
        usuario = aUsuario;
    }

    public PerfilAcessoVO getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(PerfilAcessoVO aPerfilAcesso) {
        perfilAcesso = aPerfilAcesso;
    }

    public String getVersaoSistema() {
        return versaoSistema;
    }

    public void setVersaoSistema(String versaoSistema) {
        this.versaoSistema = versaoSistema;
    }

    public EmpresaVO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaVO empresa) {
        this.empresa = empresa;
    }

    protected void limparRecursosMemoria() {
        try {
            if (this.getListaConsulta() != null) {
                this.getListaConsulta().clear();
            }
            this.mensagem = null;
            this.mensagemDetalhada = null;
            this.mensagemID = null;
            this.usuario = null;
            this.perfilAcesso = null;
            System.out.println("BACKING....: " + this.getClass().getSimpleName() + " RECURSOS LIBERADOS DA MEMÓRIA.");
        } catch (Exception e) {
        }
    }

    protected void removerTodosManagedBean() {
        Set<String> identificadores = context().getExternalContext().getSessionMap().keySet();
        Iterator i = identificadores.iterator();
        while (i.hasNext()) {
            String identificadorMB = (String) i.next();
            if ((!identificadorMB.equals("LoginControle"))
                    && (identificadorMB.endsWith("Controle"))) {
                SuperControle controle = (SuperControle) context().getExternalContext().getSessionMap().get(identificadorMB);
                controle.limparRecursosMemoria();
                context().getExternalContext().getSessionMap().remove(identificadorMB);
            }
        }
    }

    public List getListaSelectItemImpressorasNoSO() {
        List objs = new ArrayList(0);
        objs.add(new SelectItem("", ""));
        String nome = "";
        Iterator i = Uteis.consultarImpressorasNoSO().iterator();
        while (i.hasNext()) {
            nome = i.next().toString();
            objs.add(new SelectItem(nome, nome));
        }
        return objs;
    }

    public List getListaImpressorasNoSO() {
        List objs = new ArrayList(0);
        String nome = "";
        Iterator i = Uteis.consultarImpressorasNoSO().iterator();
        while (i.hasNext()) {
            nome = i.next().toString();
            objs.add(nome);
        }
        return objs;
    }

    public String getIpUsuarioRemoto() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        return request.getRemoteAddr();
    }

    public String getIdentificadorEmpresa() {
        if (identificadorEmpresa == null) {
            return "";
        }
        return identificadorEmpresa;
    }

    public void setIdentificadorEmpresa(String identificadorEmpresa) {
        this.identificadorEmpresa = identificadorEmpresa;
    }

    public ConfiguracaoSistemaVO getConfiguracaoSistema() {
        if (mapConfiguracaoSistema == null) {
            mapConfiguracaoSistema = new HashMap();
        }
        if (!getCodigoEmpresaLogado().equals(0)) {
            ConfiguracaoSistemaVO conf = (ConfiguracaoSistemaVO) mapConfiguracaoSistema.get(getCodigoEmpresaLogado());
            if (conf == null) {
                try {
                    conf = getFacadeFactory().getConfiguracaoSistemaFacade().consultarSeExisteConfiguracaoSistema(getCodigoEmpresaLogado());
                    mapConfiguracaoSistema.put(getCodigoEmpresaLogado(), conf);
                } catch (Exception ex) {
                    Logger.getLogger(SuperControle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return conf;
        } else {
            return null;
        }
    }

    public void setConfiguracaoSistema(ConfiguracaoSistemaVO configuracaoSistema) {
        if (mapConfiguracaoSistema == null) {
            mapConfiguracaoSistema = new HashMap();
        }
        mapConfiguracaoSistema.remove(getCodigoEmpresaLogado());
        mapConfiguracaoSistema.put(getCodigoEmpresaLogado(), configuracaoSistema);
    }

    public EmpresaVO obterEmpresaLogada(Integer empresa) {
        if (mapEmpresa == null) {
            mapEmpresa = new HashMap();
        }
        if (!empresa.equals(0)) {
            EmpresaVO emp = (EmpresaVO) mapEmpresa.get(empresa);
            if (emp == null) {
                try {
                    emp = getFacadeFactory().getEmpresaFacade().consultarPorChavePrimaria(empresa);
                    mapEmpresa.put(empresa, emp);
                } catch (Exception ex) {
                    Logger.getLogger(SuperControle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return emp;
        } else {
            return null;
        }
    }

    public void setarAlteracaoEmpresa(EmpresaVO empresa) {
        mapEmpresa.remove(empresa.getCodigo());
        mapEmpresa.put(empresa.getCodigo(), empresa);
    }

    public PerfilAcessoVO obterPerfilAcesso(Integer usuario, Integer empresa) {
        try {
            if (mapPerfilAcesso == null) {
                mapPerfilAcesso = new HashMap();
            }
            if (!empresa.equals(0) && !usuario.equals(0)) {
                Integer codigo = PerfilAcesso.consultarCodigoPerfilAcesso(usuario, empresa);
                PerfilAcessoVO perfil = (PerfilAcessoVO) mapPerfilAcesso.get(codigo);

                if (perfil == null) {
                    try {
                        perfil = getFacadeFactory().getPerfilAcessoFacade().consultarPorChavePrimaria(codigo);
                        mapPerfilAcesso.put(codigo, perfil);
                    } catch (Exception ex) {
                        perfil = new PerfilAcessoVO();
                    }
                }

                return perfil;
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(SuperControle.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setarAlteracaoPerfilAcesso(PerfilAcessoVO perfil) {
        mapPerfilAcesso.remove(perfil.getCodigo());
        mapPerfilAcesso.put(perfil.getCodigo(), perfil);
    }

    public List<LogVO> getListaLog() {
        if (listaLog == null) {
            listaLog = new ArrayList<LogVO>();
        }
        return listaLog;
    }

    public void setListaLog(List<LogVO> listaLog) {
        this.listaLog = listaLog;
    }

    public String getFoco() {
        if (foco == null) {
            foco = "";
        }
        return foco;
    }

    public void setFoco(String foco) {
        this.foco = foco;
    }

    public String getModal() {
        if (modal == null) {
            modal = "";
        }
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getReRender() {
        if (reRender == null) {
            reRender = "";
        }
        return reRender;
    }

    public void setReRender(String reRender) {
        this.reRender = reRender;
    }

    public String obterCaminhoBaseAplicacao() throws Exception {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        return servletContext.getRealPath("WEB-INF" + File.separator + "classes");
    }

}
