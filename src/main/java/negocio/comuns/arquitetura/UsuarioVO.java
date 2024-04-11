package negocio.comuns.arquitetura;

import annotations.arquitetura.ChavePrimaria;
import annotations.arquitetura.DescricaoObjeto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import negocio.comuns.utilitarias.ConsistirException;
import annotations.arquitetura.NaoRealizarUpperCase;
import java.io.Serializable;
import java.util.Date;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.Usuario;
import net.sf.jasperreports.engine.JRDataSource;

public class UsuarioVO extends SuperVO implements Serializable {

    @ChavePrimaria
    private Integer codigo;
    @DescricaoObjeto
    private String nome;
    @NaoRealizarUpperCase
    private String username;
    @NaoRealizarUpperCase
    private String senha;
    private List usuarioPerfilAcessoVOs;
    private List favoritos;
    private Boolean mostrarMensagemBloqueioSistema;
    private Boolean bloquearAcesso;
    private Date dataUltimoAcesso;
    private Date dataExpiracaoSenha;
    private List<UsuarioPeriodoAcessoVO> usuarioPeriodoAcessoVOs;

    public UsuarioVO() {
        super();
        inicializarDados();
    }

    public static void validarDados(UsuarioVO obj) throws ConsistirException {
        if (obj.getNome().equals("")) {
            throw new ConsistirException("O campo NOME (Usuario) deve ser informado.");
        }
        if (obj.getUsername().equals("")) {
            throw new ConsistirException("O campo USERNAME (Usuario) deve ser informado.");
        }
        if (obj.getSenha().equals("")) {
            throw new ConsistirException("O campo SENHA (Usuario) deve ser informado.");
        }
        if (obj.getUsuarioPerfilAcessoVOs().size() == 0) {
            throw new ConsistirException("Informe um Perfil Acesso para o usuário.");
        }
    }

    public void inicializarDados() {
        setCodigo(0);
        setNome("");
        setUsername("");
        setSenha("");
        // setUsuarioPerfilAcessoVOs(new ArrayList(0));
        // setFuncionario(new FuncionarioVO());
    }

    public void adicionarObjUsuarioPerfilAcessoVOs(UsuarioPerfilAcessoVO obj) throws Exception {
        UsuarioPerfilAcessoVO.validarDados(obj);
        int index = 0;
        Iterator i = getUsuarioPerfilAcessoVOs().iterator();
        while (i.hasNext()) {
            UsuarioPerfilAcessoVO objExistente = (UsuarioPerfilAcessoVO) i.next();
            if (objExistente.getEmpresa().getCodigo().equals(obj.getEmpresa().getCodigo())) {
                // if (objExistente.getPerfilAcesso().getCodigo().equals(obj.getPerfilAcesso().getCodigo())) {
                getUsuarioPerfilAcessoVOs().set(index, obj);
                return;
                // }
            }
            index++;
        }
        getUsuarioPerfilAcessoVOs().add(obj);
    }

    public void excluirObjUsuarioPerfilAcessoVOs(Integer valor) throws Exception {
        int index = 0;
        Iterator i = getUsuarioPerfilAcessoVOs().iterator();
        while (i.hasNext()) {
            UsuarioPerfilAcessoVO objExistente = (UsuarioPerfilAcessoVO) i.next();
            if (objExistente.getPerfilAcesso().getCodigo().equals(valor)) {
                getUsuarioPerfilAcessoVOs().remove(index);
                return;
            }
            index++;
        }
    }

    public String getSenha() {
        if (senha == null) {
            return "";
        }
        return (senha);
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        if (username == null) {
            return "";
        }
        return (username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        if (nome == null) {
            return "";
        }
        return (nome);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return (codigo);
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List getUsuarioPerfilAcessoVOs() {
        if (usuarioPerfilAcessoVOs == null) {
            usuarioPerfilAcessoVOs = new ArrayList(0);
        }
        return usuarioPerfilAcessoVOs;
    }

    public void setUsuarioPerfilAcessoVOs(List usuarioPerfilAcessoVOs) {
        this.usuarioPerfilAcessoVOs = usuarioPerfilAcessoVOs;
    }

    public List getFavoritos() {
        if (favoritos == null) {
            favoritos = new ArrayList(0);
        }
        return favoritos;
    }

    public void setFavoritos(List favoritos) {
        this.favoritos = favoritos;
    }

    public Boolean getMostrarMensagemBloqueioSistema() {
        if (mostrarMensagemBloqueioSistema == null) {
            mostrarMensagemBloqueioSistema = false;
        }
        return mostrarMensagemBloqueioSistema;
    }

    public void setMostrarMensagemBloqueioSistema(Boolean mostrarMensagemBloqueioSistema) {
        this.mostrarMensagemBloqueioSistema = mostrarMensagemBloqueioSistema;
    }

    public String getBloquearAcesso_Apresentar() {
        if (getBloquearAcesso()) {
            return "INATIVO";
        }
        return "ATIVO";
    }

    public Boolean getBloquearAcesso() {
        if (bloquearAcesso == null) {
            bloquearAcesso = false;
        }
        return bloquearAcesso;
    }

    public void setBloquearAcesso(Boolean bloquearAcesso) {
        this.bloquearAcesso = bloquearAcesso;
    }

    public String getDataUltimoAcesso_Apresentar() {
        if (getDataUltimoAcesso() == null){
            return "É o seu primeiro acesso. Seja bem vindo!";
        }
        return Uteis.getDataDiaMesAnoConcatenadoPorExtenso(getDataUltimoAcesso()) + "-" + Uteis.gethoraHHMM(getDataUltimoAcesso());
    }

    public String getDataUltimoAcesso_ApresentarDataNumero() {
        if (getDataUltimoAcesso() == null) {
            return "--";
        }
        return Uteis.getData(getDataUltimoAcesso()) + "-" + Uteis.gethoraHHMM(getDataUltimoAcesso());
    }

    public Date getDataUltimoAcesso() {
        return dataUltimoAcesso;
    }

    public void setDataUltimoAcesso(Date dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    public String getDataExpiracaoSenha_Apresentar(){
        if(getDataExpiracaoSenha() == null){
            return null;
        }
        return Uteis.getData(getDataExpiracaoSenha());
    }

    public Date getDataExpiracaoSenha() {
        return dataExpiracaoSenha;
    }

    public void setDataExpiracaoSenha(Date dataExpiracaoSenha) {
        this.dataExpiracaoSenha = dataExpiracaoSenha;
    }

    public void setUsuarioPeriodoAcesso(String usuarioPeriodoAcessoString){
        if(usuarioPeriodoAcessoString != null && !usuarioPeriodoAcessoString.isEmpty()) {
            setUsuarioPeriodoAcessoVOs(null);
            Gson g = new Gson();
            UsuarioPeriodoAcessoVO[] lista = g.fromJson(usuarioPeriodoAcessoString, UsuarioPeriodoAcessoVO[].class);
            for(UsuarioPeriodoAcessoVO x : lista){
                getUsuarioPeriodoAcessoVOs().add(x);
            }
        }
    }

    public String getUsuarioPeriodoAcesso(){
        if(!getUsuarioPeriodoAcessoVOs().isEmpty()) {
            Gson g = new Gson();
            return g.toJson(getUsuarioPeriodoAcessoVOs());
        }
        return "";
    }

    public List<UsuarioPeriodoAcessoVO> getUsuarioPeriodoAcessoVOs() {
        if(usuarioPeriodoAcessoVOs == null){
            usuarioPeriodoAcessoVOs = new ArrayList<UsuarioPeriodoAcessoVO>(0);
        }
        return usuarioPeriodoAcessoVOs;
    }

    public void setUsuarioPeriodoAcessoVOs(List<UsuarioPeriodoAcessoVO> usuarioPeriodoAcessoVOs) {
        this.usuarioPeriodoAcessoVOs = usuarioPeriodoAcessoVOs;
    }

    public JRDataSource getUsuarioPerfilAcessoVOsJRDataSource() {
        return Uteis.getJrDataSource(getUsuarioPerfilAcessoVOs());
    }
}
