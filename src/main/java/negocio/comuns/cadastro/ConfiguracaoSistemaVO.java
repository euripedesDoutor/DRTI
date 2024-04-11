package negocio.comuns.cadastro;

import annotations.arquitetura.ChavePrimaria;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import negocio.comuns.arquitetura.SuperVO;
import negocio.comuns.utilitarias.ConsistirException;

public class ConfiguracaoSistemaVO extends SuperVO implements Serializable {

    @ChavePrimaria
    private Integer codigo;
    private EmpresaVO empresa;
    private byte imagemBanner[];
    private byte imagemRelatorio[];
    private Integer segurancaTamanhoSenhaUsuario;
    private Integer segurancaPrazoExpiracaoSenha;
    private Integer segurancaTentativasAcessoAntesBloqueio;
    private Integer segurancaHorasParaZerarTentativasAcesso;
    private Boolean segurancaSenhaExigirLetraMaiuscula;
    private Boolean segurancaSenhaExigirNumero;
    private Boolean segurancaSenhaExigirCaractereEspecial;

    public ConfiguracaoSistemaVO() {
        super();
        inicializarDados();
    }

    public static void validarDados(ConfiguracaoSistemaVO obj) throws ConsistirException {

        if(!obj.getSegurancaPrazoExpiracaoSenhaValidacao().isEmpty()){
            throw new ConsistirException("Prazo mínimo de 30 dias para Expiração da senha (Aba Segurança)");
        }

        if(!obj.getSegurancaTentativasAcessoAntesBloqueioValidacao().isEmpty()){
            throw new ConsistirException("Mínimo de 2 tentativas para bloqueio do acesso (Aba Segurança)");
        }

        if(!obj.getSegurancaHorasParaZerarTentativasAcessoValidacao().isEmpty()){
            throw new ConsistirException("Mínimo de 1 hora para zerar as tentativas de acesso negado (Aba Segurança)");
        }

        if(!obj.getSegurancaTamanhoSenhaUsuarioValidacao().isEmpty()){
            throw new ConsistirException("Mínimo de 6 caracteres para o tamanho da senha (Aba Segurança)");
        }

    }

    public void inicializarDados() {
    }

    public InputStream getImagemPadraoRelatorio(Boolean NFe) throws Exception {
        if (getImagemRelatorio() == null) {
            return null;
        }
        return new ByteArrayInputStream(getImagemRelatorio());
    }


    public Integer getCodigo() {
        if (codigo == null) {
            codigo = 0;
        }
        return (codigo);
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public byte[] getImagemBanner() {
        return imagemBanner;
    }

    public void setImagemBanner(byte[] imagemBanner) {
        this.imagemBanner = imagemBanner;
    }

    public byte[] getImagemRelatorio() {
        return imagemRelatorio;
    }

    public void setImagemRelatorio(byte[] imagemRelatorio) {
        this.imagemRelatorio = imagemRelatorio;
    }

    public String getSegurancaTamanhoSenhaUsuarioFoco() {
        if(getSegurancaTamanhoSenhaUsuario() < 6){
            return "segurancaTamanhoSenhaUsuario";
        }
        return "segurancaSenhaExigirLetraMaiuscula";
    }

    public String getSegurancaTamanhoSenhaUsuarioValidacao() {
        if(getSegurancaTamanhoSenhaUsuario() < 6){
            return "Tamanho mínimo 6 dígitos";
        }
        return "";
    }

    public Integer getSegurancaTamanhoSenhaUsuario() {
        if (segurancaTamanhoSenhaUsuario == null) {
            segurancaTamanhoSenhaUsuario = 8;
        }
        return segurancaTamanhoSenhaUsuario;
    }

    public void setSegurancaTamanhoSenhaUsuario(Integer segurancaTamanhoSenhaUsuario) {
        this.segurancaTamanhoSenhaUsuario = segurancaTamanhoSenhaUsuario;
    }

    public String getSegurancaPrazoExpiracaoSenhaFoco() {
        if(getSegurancaPrazoExpiracaoSenha() < 30){
            return "segurancaPrazoExpiracaoSenha";
        }
            return "segurancaTentativasAcessoAntesBloqueio";
    }
    public String getSegurancaPrazoExpiracaoSenhaValidacao() {
        if(getSegurancaPrazoExpiracaoSenha() < 30){
            return "Prazo mínimo de 30 dias";
        }
            return "";
    }

    public Integer getSegurancaPrazoExpiracaoSenha() {
        if (segurancaPrazoExpiracaoSenha == null) {
            segurancaPrazoExpiracaoSenha = 180;
        }
        return segurancaPrazoExpiracaoSenha;
    }

    public void setSegurancaPrazoExpiracaoSenha(Integer segurancaPrazoExpiracaoSenha) {
        this.segurancaPrazoExpiracaoSenha = segurancaPrazoExpiracaoSenha;
    }

    public String getSegurancaTentativasAcessoAntesBloqueioFoco() {
        if(getSegurancaTentativasAcessoAntesBloqueio() < 2){
            return "segurancaTentativasAcessoAntesBloqueio";
        }
        return "segurancaHorasParaZerarTentativasAcesso";
    }
    public String getSegurancaTentativasAcessoAntesBloqueioValidacao() {
        if(getSegurancaTentativasAcessoAntesBloqueio() < 2){
            return "Mínimo de 2 tentativas";
        }
        return "";
    }

    public Integer getSegurancaTentativasAcessoAntesBloqueio() {
        if (segurancaTentativasAcessoAntesBloqueio == null) {
            segurancaTentativasAcessoAntesBloqueio = 10;
        }
        return segurancaTentativasAcessoAntesBloqueio;
    }

    public void setSegurancaTentativasAcessoAntesBloqueio(Integer segurancaTentativasAcessoAntesBloqueio) {
        this.segurancaTentativasAcessoAntesBloqueio = segurancaTentativasAcessoAntesBloqueio;
    }

    public String getSegurancaHorasParaZerarTentativasAcessoFoco() {
        if(getSegurancaHorasParaZerarTentativasAcesso() < 1){
            return "segurancaHorasParaZerarTentativasAcesso";
        }
        return "segurancaTamanhoSenhaUsuario";
    }
    public String getSegurancaHorasParaZerarTentativasAcessoValidacao() {
        if(getSegurancaHorasParaZerarTentativasAcesso() < 1){
            return "Mínimo de 1 hora";
        }
        return "";
    }

    public Integer getSegurancaHorasParaZerarTentativasAcesso() {
        if (segurancaHorasParaZerarTentativasAcesso == null) {
            segurancaHorasParaZerarTentativasAcesso = 24;
        }
        return segurancaHorasParaZerarTentativasAcesso;
    }

    public void setSegurancaHorasParaZerarTentativasAcesso(Integer segurancaHorasParaZerarTentativasAcesso) {
        this.segurancaHorasParaZerarTentativasAcesso = segurancaHorasParaZerarTentativasAcesso;
    }

    public Boolean getSegurancaSenhaExigirLetraMaiuscula() {
        if (segurancaSenhaExigirLetraMaiuscula == null) {
            segurancaSenhaExigirLetraMaiuscula = true;
        }
        return segurancaSenhaExigirLetraMaiuscula;
    }

    public void setSegurancaSenhaExigirLetraMaiuscula(Boolean segurancaSenhaExigirLetraMaiuscula) {
        this.segurancaSenhaExigirLetraMaiuscula = segurancaSenhaExigirLetraMaiuscula;
    }

    public Boolean getSegurancaSenhaExigirNumero() {
        if (segurancaSenhaExigirNumero == null) {
            segurancaSenhaExigirNumero = true;
        }
        return segurancaSenhaExigirNumero;
    }

    public void setSegurancaSenhaExigirNumero(Boolean segurancaSenhaExigirNumero) {
        this.segurancaSenhaExigirNumero = segurancaSenhaExigirNumero;
    }

    public Boolean getSegurancaSenhaExigirCaractereEspecial() {
        if (segurancaSenhaExigirCaractereEspecial == null) {
            segurancaSenhaExigirCaractereEspecial = true;
        }
        return segurancaSenhaExigirCaractereEspecial;
    }

    public void setSegurancaSenhaExigirCaractereEspecial(Boolean segurancaSenhaExigirCaractereEspecial) {
        this.segurancaSenhaExigirCaractereEspecial = segurancaSenhaExigirCaractereEspecial;
    }

    public EmpresaVO getEmpresa() {
        if (empresa == null) {
            empresa = new EmpresaVO();
        }
        return empresa;
    }

    public void setEmpresa(EmpresaVO empresa) {
        this.empresa = empresa;
    }

    public Integer getQuantidadeDigitosAutoComplete(){
        return 3;
    }
}
