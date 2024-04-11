/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.comuns.arquitetura;

import java.io.Serializable;
import java.util.Date;
import negocio.comuns.utilitarias.Uteis;

/**
 *
 * @author Kelvin
 */
public class LogVO extends SuperVO implements Serializable {

    protected Integer codigo;
    protected String nomeEntidade;
    protected String nomeEntidadeDescricao;
    protected String chavePrimaria;
    protected String chavePrimariaEntidadeSubordinada;
    protected String nomeCampo;
    protected String valorCampoAnterior;
    protected String valorCampoAlterado;
    protected Date dataAlteracao;
    protected String responsavelAlteracao;
    protected String operacao;

    public LogVO() {
        inicializarDados();
    }

    public void inicializarDados() {
        setNomeEntidade("");
        setNomeEntidadeDescricao("");
        setChavePrimaria("");
        setChavePrimariaEntidadeSubordinada("");
        setValorCampoAnterior("");
        setValorCampoAlterado("");
        setDataAlteracao(new Date());
        setNomeCampo("");
        setResponsavelAlteracao("");
        setOperacao("");
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nomeEntidade
     */
    public String getNomeEntidade() {
        if (nomeEntidade == null) {
            nomeEntidade = "";
        }
        return nomeEntidade;
    }

    public String getNomeEntidade_Apresentar() {
        if (!getChavePrimariaEntidadeSubordinada().equals("")) {
            return "(" + getNomeEntidadeDescricao() + ")";
        } else {
            return getNomeEntidade();
        }
    }

    /**
     * @param nomeEntidade the nomeEntidade to set
     */
    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    /**
     * @return the chavePrimaria
     */
    public String getChavePrimaria() {
        if (chavePrimaria == null) {
            chavePrimaria = "";
        }
        return chavePrimaria;
    }

    /**
     * @param chavePrimaria the chavePrimaria to set
     */
    public void setChavePrimaria(String chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }

    /**
     * @return the nomeCampo
     */
    public String getNomeCampo() {
        if (nomeCampo == null) {
            nomeCampo = "";
        }
        return nomeCampo;
    }

    /**
     * @param nomeCampo the nomeCampo to set
     */
    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    /**
     * @return the valorCampoAnterior
     */
    public String getValorCampoAnterior() {
        if (valorCampoAnterior == null) {
            valorCampoAnterior = "";
        }
        if (valorCampoAnterior.equals("true")) {
            valorCampoAnterior = "Sim";
        }
        if (valorCampoAnterior.equals("false")) {
            valorCampoAnterior = "Não";
        }
        return valorCampoAnterior;
    }

    /**
     * @param valorCampoAnterior the valorCampoAnterior to set
     */
    public void setValorCampoAnterior(String valorCampoAnterior) {
        this.valorCampoAnterior = valorCampoAnterior;
    }

    /**
     * @return the valorCampoAlterado
     */
    public String getValorCampoAlterado() {
        if (valorCampoAlterado == null) {
            valorCampoAlterado = "";
        }
        if (valorCampoAlterado.equals("true")) {
            valorCampoAlterado = "Sim";
        }
        if (valorCampoAlterado.equals("false")) {
            valorCampoAlterado = "Não";
        }
        return valorCampoAlterado;
    }

    /**
     * @param valorCampoAlterado the valorCampoAlterado to set
     */
    public void setValorCampoAlterado(String valorCampoAlterado) {
        this.valorCampoAlterado = valorCampoAlterado;
    }

    /**
     * @return the dataAlteracao
     */
    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public String getDataAlteracao_Apresentar() {
        return Uteis.getData(dataAlteracao);
    }

    public String getDataAlteracao_ApresentarHora() {
        return getDataAlteracao_Apresentar() + " " + Uteis.getObterHoraData(dataAlteracao);
    }

    public String getHoraAlteracao_Apresentar() {
        return Uteis.gethoraHHMMSSAjustado(dataAlteracao);
    }

    /**
     * @param dataAlteracao the dataAlteracao to set
     */
    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    /**
     * @return the responsavelAlteracao
     */
    public String getResponsavelAlteracao() {
        if (responsavelAlteracao == null) {
            responsavelAlteracao = "";
        }
        return responsavelAlteracao;
    }

    /**
     * @param responsavelAlteracao the responsavelAlteracao to set
     */
    public void setResponsavelAlteracao(String responsavelAlteracao) {
        this.responsavelAlteracao = responsavelAlteracao;
    }

    /**
     * @return the operacao
     */
    public String getOperacao() {
        if (operacao == null) {
            operacao = "";
        }
        return operacao;
    }

    /**
     * @param operacao the operacao to set
     */
    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    /**
     * @return the chavePrimariaEntidadeSubordinada
     */
    public String getChavePrimariaEntidadeSubordinada() {
        if (chavePrimariaEntidadeSubordinada == null) {
            chavePrimariaEntidadeSubordinada = "";
        }
        return chavePrimariaEntidadeSubordinada;
    }

    /**
     * @param chavePrimariaEntidadeSubordinada the chavePrimariaEntidadeSubordinada to set
     */
    public void setChavePrimariaEntidadeSubordinada(String chavePrimariaEntidadeSubordinada) {
        this.chavePrimariaEntidadeSubordinada = chavePrimariaEntidadeSubordinada;
    }

    /**
     * @return the nomeEntidadeDescricao
     */
    public String getNomeEntidadeDescricao() {
        return nomeEntidadeDescricao;
    }

    /**
     * @param nomeEntidadeDescricao the nomeEntidadeDescricao to set
     */
    public void setNomeEntidadeDescricao(String nomeEntidadeDescricao) {
        this.nomeEntidadeDescricao = nomeEntidadeDescricao;
    }
}
