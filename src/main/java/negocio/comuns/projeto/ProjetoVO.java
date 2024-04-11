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
public class ProjetoVO extends SuperVO implements Serializable{

    private Integer codigo;
    @NaoRealizarUpperCase
    private String nome;
    private String nomeApresentar;
    @NaoRealizarUpperCase
    private String url;
    private String backend;
    private String frontend;
    private Date data;
    private UsuarioVO usuario;
    private List<ModuloVO> listaModulo;

    public List getListaSelectItemBackend() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("XX", "Não Gerar"));
        itens.add(new SelectItem("JV6", "Java 6"));
        itens.add(new SelectItem("JSB", "Java Spring Boot"));
        return itens;
    }

    public List getListaSelectItemFrontend() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("XX", "Não Gerar"));
        if(getBackendJava6()) {
            itens.add(new SelectItem("JSF", "JSF"));
        }
        if(getBackendSpringBoot()) {
            itens.add(new SelectItem("ANG", "Angular"));
        }
        return itens;
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

    public String getUrl() {
        if (url == null) {
            url = "";
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getBackendJava6() {
        if(getBackend().equals("JV6")){
            return true;
        }
        return false;
    }
    public Boolean getBackendSpringBoot() {
        if(getBackend().equals("JSB")){
            return true;
        }
        return false;
    }

    public String getBackend() {
        if (backend == null) {
            backend = "JV6";
        }
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

    public String getBackend_Apresentar() {
        if (getBackend().equals("XX")) {
            return "Não Gerar";
        }
        if (getBackend().equals("JV6")) {
            return "Java 6";
        }
        if (getBackend().equals("JSB")) {
            return "Java Spring Boot";
        }
        return "";
    }

    public String getFrontend() {
        if (frontend == null) {
            frontend = "JSF";
        }
        if(getBackendJava6()){
            frontend = "JSF";
        }
        return frontend;
    }

    public void setFrontend(String frontend) {
        this.frontend = frontend;
    }

    public String getFrontend_Apresentar() {
        if (getFrontend().equals("XX")) {
            return "Não Gerar";
        }
        if (getFrontend().equals("JSF")) {
            return "JSF";
        }
        if (getFrontend().equals("ANG")) {
            return "Angular";
        }
        return "";
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

    public List<ModuloVO> getListaModulo() {
        if (listaModulo == null) {
            listaModulo = new ArrayList<ModuloVO>(0);
        }
        return listaModulo;
    }

    public void setListaModulo(List<ModuloVO> listaModulo) {
        this.listaModulo = listaModulo;
    }

}
