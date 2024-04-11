package negocio.comuns.projeto;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import annotations.arquitetura.NaoRealizarUpperCase;
import negocio.comuns.arquitetura.SuperVO;
import java.util.Date;
import negocio.comuns.utilitarias.Uteis;
import negocio.comuns.arquitetura.UsuarioVO;

/**
*
* @author DOCTORCODE
*/
public class EntidadeVO extends SuperVO implements Serializable{

    private Integer codigo;
    @NaoRealizarUpperCase
    private String nome;
    private String nomeApresentar;
    private ProjetoVO projeto;
    private ModuloVO modulo;
    private Date data;
    private UsuarioVO usuario;
    private List<AtributosVO> listaAtributos;

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

    public List<AtributosVO> getListaAtributos() {
        if (listaAtributos == null) {
            listaAtributos = new ArrayList<AtributosVO>(0);
        }
        return listaAtributos;
    }

    public void setListaAtributos(List<AtributosVO> listaAtributos) {
        this.listaAtributos = listaAtributos;
    }

}
