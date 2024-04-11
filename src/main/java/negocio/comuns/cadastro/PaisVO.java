package negocio.comuns.cadastro;

import java.io.Serializable;
import negocio.comuns.arquitetura.SuperVO;

/**
 *
 * @author DOCTORCODE
 */
public class PaisVO extends SuperVO implements Serializable {

    private Integer codigo;
    private String nome;
    private String codigoSped;
    private String codigoSiscomex;

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

    public String getCodigoSped() {
        if (codigoSped == null) {
            codigoSped = "";
        }
        return codigoSped;
    }

    public void setCodigoSped(String codigoSped) {
        this.codigoSped = codigoSped;
    }

    public String getCodigoSiscomex() {
        if (codigoSiscomex == null) {
            codigoSiscomex = "";
        }
        return codigoSiscomex;
    }

    public void setCodigoSiscomex(String codigoSiscomex) {
        this.codigoSiscomex = codigoSiscomex;
    }
}
