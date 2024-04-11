/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.comuns.arquitetura;

import controle.arquitetura.LoginControle;

import java.util.Date;
import javax.servlet.http.HttpSession;

import negocio.comuns.utilitarias.Uteis;

/**
 * @author Euripedes Doutor
 */
public class LoginUsuarioVO {

    private String username;
    private String nome;
    private String perfilAcesso;
    private String tipoPerfilAcesso; //G: Gerencial / V:Vendedor Externo
    private String ip;
    private String idSessao;
    private Date dataLogin;
    private Date dataUltimaUtilizacao;
    private Integer empresa;
    private HttpSession sessao;

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

    public String getDataLogin_Apresentar() {
        return Uteis.getData(getDataLogin()) + "-" + Uteis.gethoraHHMM(getDataLogin());
    }

    public String getTempoLogado() {
        return Uteis.getDiferencaEntreDatasFormatado(getDataLogin(), new Date());
    }

    public Date getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(Date dataLogin) {
        this.dataLogin = dataLogin;
    }

    public String getDataUltimaUtilizacao_Apresentar() {
        return Uteis.getData(getDataUltimaUtilizacao()) + "-" + Uteis.gethoraHHMM(getDataUltimaUtilizacao());
    }

    public Date getDataUltimaUtilizacao() {
        return dataUltimaUtilizacao;
    }

    public void setDataUltimaUtilizacao(Date dataUltimaUtilizacao) {
        this.dataUltimaUtilizacao = dataUltimaUtilizacao;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    public HttpSession getSessao() {
        return sessao;
    }

    public void setSessao(HttpSession sessao) {
        this.sessao = sessao;
    }

    public String getTelaEstaAberta_Apresentar() {
        return getTelaEstaAberta() ? "SIM" : "NÃO";
    }

    public Boolean getTelaEstaAberta() {
        try {
            if (((LoginControle) getSessao().getAttribute("LoginControle")).getDataTelaAberta() == null) {
                ((LoginControle) getSessao().getAttribute("LoginControle")).setDataTelaAberta(new Date());
            }
            if (new Date().getTime() - ((LoginControle) getSessao().getAttribute("LoginControle")).getDataTelaAberta().getTime() <= 120000) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public String getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(String perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
    }

    public String getIp() {
        if (ip == null) {
            ip = "";
        }
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(String idSessao) {
        this.idSessao = idSessao;
    }

    public Boolean getTipoPerfilAcessoGerencial() {
        if (getTipoPerfilAcesso().equals("G")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilAcessoVendedorExterno() {
        if (getTipoPerfilAcesso().equals("V")) {
            return true;
        }
        return false;
    }

    public String getTipoPerfilAcesso_Apresentar() {
        if(getTipoPerfilAcessoGerencial()){
            return "GERENCIAL";
        }
        return "VEND. EXTERNO";
    }

    public String getTipoPerfilAcesso() {
        if (tipoPerfilAcesso == null) {
            tipoPerfilAcesso = "G";
        }
        return tipoPerfilAcesso;
    }

    public void setTipoPerfilAcesso(String tipoPerfilAcesso) {
        this.tipoPerfilAcesso = tipoPerfilAcesso;
    }
}
