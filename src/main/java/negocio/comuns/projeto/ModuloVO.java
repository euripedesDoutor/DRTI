package negocio.comuns.projeto;

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
public class ModuloVO extends SuperVO implements Serializable{

    private Integer projeto;
    private Integer codigo;
    @NaoRealizarUpperCase
    private String nome;
    @NaoRealizarUpperCase
    private String nomeApresentar;
    private Date data;
    private UsuarioVO usuario;

    public Integer getProjeto() {
        if (projeto == null) {
            projeto = 0;
        }
        return projeto;
    }

    public void setProjeto(Integer projeto) {
        this.projeto = projeto;
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
