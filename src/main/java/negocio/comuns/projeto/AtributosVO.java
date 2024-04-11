package negocio.comuns.projeto;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import annotations.arquitetura.NaoRealizarUpperCase;
import negocio.comuns.arquitetura.SuperVO;
import javax.faces.model.SelectItem;
import java.util.Date;
import negocio.comuns.utilitarias.Uteis;
import negocio.comuns.arquitetura.UsuarioVO;

/**
*
* @author DOCTORCODE
*/
public class AtributosVO extends SuperVO implements Serializable{

    private Integer entidade;
    private Integer codigo;
    @NaoRealizarUpperCase
    private String nome;
    private String nomeApresentar;
    private String tipoCampo;
    private String tipoComponente;
    private Boolean campoObrigatorio;
    private Boolean campoConsulta;
    private Boolean campoDescricaoObjeto;
    @NaoRealizarUpperCase
    private String mascara;
    private Integer tamanho;
    private Integer casasDecimais;
    private ProjetoVO projeto;
    private ModuloVO modulo;
    private Date data;
    private UsuarioVO usuario;

    public List getListaSelectItemTipoCampo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("STRING", "STRING"));
        itens.add(new SelectItem("INTEGER", "INTEGER"));
        itens.add(new SelectItem("DOUBLE", "DOUBLE"));
        itens.add(new SelectItem("DATE", "DATE"));
        itens.add(new SelectItem("LIST", "LIST"));
        itens.add(new SelectItem("CHAVEESTRANGEIRA", "CHAVEESTRANGEIRA"));
        itens.add(new SelectItem("CHAVEPRIMARIA", "CHAVEPRIMARIA"));
        itens.add(new SelectItem("DOMINIO", "DOMINIO"));
        itens.add(new SelectItem("BOOLEAN", "BOOLEAN"));
        itens.add(new SelectItem("UPLOAD", "UPLOAD"));
        return itens;
    }

    public List getListaSelectItemTipoComponente() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("INPUTTEXT", "INPUTTEXT"));
        itens.add(new SelectItem("OUTPUTTEXT", "OUTPUTTEXT"));
        itens.add(new SelectItem("COMBOBOX", "COMBOBOX"));
        itens.add(new SelectItem("AUTOCOMPLETE", "AUTOCOMPLETE"));
        itens.add(new SelectItem("CONSULTA_CHAVEPRIMARIA", "CONSULTA_CHAVEPRIMARIA"));
        itens.add(new SelectItem("CALENDAR", "CALENDAR"));
        itens.add(new SelectItem("TABELA", "TABELA"));
        itens.add(new SelectItem("CHECKBOX", "CHECKBOX"));
        itens.add(new SelectItem("UPLOAD", "UPLOAD"));
        return itens;
    }

    public Integer getEntidade() {
        if (entidade == null) {
            entidade = 0;
        }
        return entidade;
    }

    public void setEntidade(Integer entidade) {
        this.entidade = entidade;
    }

    public Integer getCodigo() {
        if (codigo == null) {
            codigo = 0;
        }
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        if (nome == null) {
            nome = "";
        }
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeApresentar() {
        if (nomeApresentar == null) {
            nomeApresentar = "";
        }
        return nomeApresentar;
    }

    public void setNomeApresentar(String nomeApresentar) {
        this.nomeApresentar = nomeApresentar;
    }

    public Boolean getTipoCampoString() {
        return getTipoCampo().equals("STRING");
    }

    public Boolean getTipoCampoInteger() {
        return getTipoCampo().equals("INTEGER");
    }

    public Boolean getTipoCampoDouble() {
        return getTipoCampo().equals("DOUBLE");
    }

    public Boolean getTipoCampoDate() {
        return getTipoCampo().equals("DATE");
    }

    public Boolean getTipoCampoList() {
        return getTipoCampo().equals("LIST");
    }

    public Boolean getTipoCampoChaveEstrangeira() {
        return getTipoCampo().equals("CHAVEESTRANGEIRA");
    }

    public Boolean getTipoCampoChavePrimaria() {
        return getTipoCampo().equals("CHAVEPRIMARIA");
    }

    public Boolean getTipoCampoChaveDominio() {
        return getTipoCampo().equals("DOMINIO");
    }

    public Boolean getTipoCampoBoolean() {
        return getTipoCampo().equals("BOOLEAN");
    }

    public Boolean getTipoCampoUpload() {
        return getTipoCampo().equals("UPLOAD");
    }

    public String getTipoCampo() {
        if (tipoCampo == null) {
            tipoCampo = "STRING";
        }
        return tipoCampo;
    }

    public void setTipoCampo(String tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public String getTipoCampo_Apresentar() {
        if (getTipoCampo().equals("STRING")) {
            return "STRING";
        }
        if (getTipoCampo().equals("INTEGER")) {
            return "INTEGER";
        }
        if (getTipoCampo().equals("DOUBLE")) {
            return "DOUBLE";
        }
        if (getTipoCampo().equals("DATE")) {
            return "DATE";
        }
        if (getTipoCampo().equals("LIST")) {
            return "LIST";
        }
        if (getTipoCampo().equals("CHAVEESTRANGEIRA")) {
            return "CHAVEESTRANGEIRA";
        }
        if (getTipoCampo().equals("CHAVEPRIMARIA")) {
            return "CHAVEPRIMARIA";
        }
        if (getTipoCampo().equals("DOMINIO")) {
            return "DOMINIO";
        }
        if (getTipoCampo().equals("BOOLEAN")) {
            return "BOOLEAN";
        }
        if (getTipoCampo().equals("UPLOAD")) {
            return "UPLOAD";
        }
        return "";
    }

    public String getTipoComponente() {
        if (tipoComponente == null) {
            tipoComponente = "INPUTTEXT";
        }
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getTipoComponente_Apresentar() {
        if (getTipoComponente().equals("INPUTTEXT")) {
            return "INPUTTEXT";
        }
        if (getTipoComponente().equals("OUTPUTTEXT")) {
            return "OUTPUTTEXT";
        }
        if (getTipoComponente().equals("COMBOBOX")) {
            return "COMBOBOX";
        }
        if (getTipoComponente().equals("AUTOCOMPLETE")) {
            return "AUTOCOMPLETE";
        }
        if (getTipoComponente().equals("CONSULTA_CHAVEPRIMARIA")) {
            return "CONSULTA_CHAVEPRIMARIA";
        }
        if (getTipoComponente().equals("CALENDAR")) {
            return "CALENDAR";
        }
        if (getTipoComponente().equals("TABELA")) {
            return "TABELA";
        }
        if (getTipoComponente().equals("CHECKBOX")) {
            return "CHECKBOX";
        }
        if (getTipoComponente().equals("UPLOAD")) {
            return "UPLOAD";
        }
        return "";
    }

    public Boolean getCampoObrigatorio() {
        if (campoObrigatorio == null) {
            campoObrigatorio = false;
        }
        return campoObrigatorio;
    }

    public void setCampoObrigatorio(Boolean campoObrigatorio) {
        this.campoObrigatorio = campoObrigatorio;
    }

    public String getCampoObrigatorio_Apresentar() {
        if (getCampoObrigatorio()) {
            return "SIM";
        }
        return "NÃO";
    }

    public Boolean getCampoConsulta() {
        if (campoConsulta == null) {
            campoConsulta = false;
        }
        return campoConsulta;
    }

    public void setCampoConsulta(Boolean campoConsulta) {
        this.campoConsulta = campoConsulta;
    }

    public String getCampoConsulta_Apresentar() {
        if (getCampoConsulta()) {
            return "SIM";
        }
        return "NÃO";
    }

    public Boolean getCampoDescricaoObjeto() {
        if (campoDescricaoObjeto == null) {
            campoDescricaoObjeto = false;
        }
        return campoDescricaoObjeto;
    }

    public void setCampoDescricaoObjeto(Boolean campoDescricaoObjeto) {
        this.campoDescricaoObjeto = campoDescricaoObjeto;
    }

    public String getCampoDescricaoObjeto_Apresentar() {
        if (getCampoDescricaoObjeto()) {
            return "SIM";
        }
        return "NÃO";
    }

    public String getMascara() {
        if (mascara == null) {
            mascara = "";
        }
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public Integer getTamanho() {
        if (tamanho == null) {
            tamanho = 0;
        }
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getCasasDecimais() {
        if (casasDecimais == null) {
            casasDecimais = 0;
        }
        return casasDecimais;
    }

    public void setCasasDecimais(Integer casasDecimais) {
        this.casasDecimais = casasDecimais;
    }

    public ProjetoVO getProjeto() {
        if (projeto == null) {
            projeto = new ProjetoVO();
        }
        return projeto;
    }

    public void setProjeto(ProjetoVO projeto) {
        this.projeto = projeto;
    }

    public ModuloVO getModulo() {
        if (modulo == null) {
            modulo = new ModuloVO();
        }
        return modulo;
    }

    public void setModulo(ModuloVO modulo) {
        this.modulo = modulo;
    }

    public Date getData() {
        if (data == null) {
            data = new Date();
        }
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getData_Apresentar() {
        if (getData() == null) {
            return "";
        }
        return (Uteis.getData(getData()) + " " + Uteis.gethoraHHMMSSAjustado(getData()));
    }

    public UsuarioVO getUsuario() {
        if (usuario == null) {
            usuario = new UsuarioVO();
        }
        return usuario;
    }

    public void setUsuario(UsuarioVO usuario) {
        this.usuario = usuario;
    }

}
