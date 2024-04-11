package negocio.comuns.cadastro;

import annotations.arquitetura.ChavePrimaria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import negocio.comuns.arquitetura.*;

public class EmpresaVO extends SuperVO implements Serializable {

    @ChavePrimaria
    private Integer codigo;
    private String razaoSocial;
    private String nomeFantasia;
    private String endereco;
    private String numero;
    private String bairro;
    private String cep;
    private String telefone;
    private String celular;
    private String CNPJ;
    private String cnae;
    private String inscricao;
    private String inscricaoMunicipal;
    private CidadeVO cidade;
    private Date dataBloqueio;
    private Integer alertarQuandoRestarDias;
    private String situacao;
    private Integer limiteUsuariosSimultaneos;
    private Integer limiteUsuariosSimultaneosVendedorExterno;

    public EmpresaVO() {
        super();
        inicializarDados();
    }

    public void inicializarDados() {
        setCodigo(0);
        setRazaoSocial("");
        setNomeFantasia("");
        setEndereco("");
        setNumero("");
        setBairro("");
        setCep("");
        setTelefone("");
        setCelular("");
        setCNPJ("");
        setInscricao("");
    }

    public CidadeVO getCidade() {
        if (cidade == null) {
            cidade = new CidadeVO();
        }
        return (cidade);
    }

    public void setCidade(CidadeVO obj) {
        this.cidade = obj;
    }

    public String getInscricao() {
        if (inscricao == null) {
            inscricao = "";
        }
        return (inscricao);
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getCNPJ() {
        if (CNPJ == null) {
            CNPJ = "";
        }
        return (CNPJ);
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getCelular() {
        if (celular == null) {
            celular = "";
        }
        return (celular);
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


    public String getTelefone() {
        if (telefone == null) {
            telefone = "";
        }
        return (telefone);
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        if (cep == null) {
            cep = "";
        }
        return (cep);
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        if (bairro == null) {
            bairro = "";
        }
        return (bairro);
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        if (numero == null) {
            numero = "";
        }
        return (numero);
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        if (endereco == null) {
            endereco = "";
        }
        return (endereco);
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNomeFantasia() {
        if (nomeFantasia == null) {
            nomeFantasia = "";
        }
        return (nomeFantasia);
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        if (razaoSocial == null) {
            razaoSocial = "";
        }
        return (razaoSocial);
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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

    public Date getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(Date dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    public String getCnae() {
        if (cnae == null) {
            cnae = "";
        }
        return cnae;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public String getInscricaoMunicipal() {
        if (inscricaoMunicipal == null) {
            inscricaoMunicipal = "";
        }
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getSituacao() {
        if (situacao == null) {
            situacao = "";
        }
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Boolean getSituacaoAtiva() {
        return getSituacao().equals("ATIVA");
    }

    public String getSituacaoInativaCss() {
        if(getSituacaoAtiva()){
            return "";
        }
        return "color:red; font-weight: bold;";
    }

    public Integer getLimiteUsuariosSimultaneos() {
        if (limiteUsuariosSimultaneos == null){
            limiteUsuariosSimultaneos = 5;
        }
        return limiteUsuariosSimultaneos;
    }

    public void setLimiteUsuariosSimultaneos(Integer limiteUsuariosSimultaneos) {
        this.limiteUsuariosSimultaneos = limiteUsuariosSimultaneos;
    }

    public Integer getLimiteUsuariosSimultaneosVendedorExterno() {
        if (limiteUsuariosSimultaneosVendedorExterno == null) {
            limiteUsuariosSimultaneosVendedorExterno = 0;
        }
        return limiteUsuariosSimultaneosVendedorExterno;
    }

    public void setLimiteUsuariosSimultaneosVendedorExterno(Integer limiteUsuariosSimultaneosVendedorExterno) {
        this.limiteUsuariosSimultaneosVendedorExterno = limiteUsuariosSimultaneosVendedorExterno;
    }

    public Integer getAlertarQuandoRestarDias() {
        if (alertarQuandoRestarDias == null) {
            alertarQuandoRestarDias = 0;
        }
        return alertarQuandoRestarDias;
    }

    public void setAlertarQuandoRestarDias(Integer alertarQuandoRestarDias) {
        this.alertarQuandoRestarDias = alertarQuandoRestarDias;
    }
}
