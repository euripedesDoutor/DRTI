package negocio.comuns.cadastro;

import annotations.arquitetura.ChavePrimaria;
import annotations.arquitetura.DescricaoObjeto;
import java.io.Serializable;
import negocio.comuns.cadastro.PaisVO;
import negocio.comuns.utilitarias.*;
import negocio.comuns.arquitetura.*;

/**
 * Reponsável por manter os dados da entidade Cidade. Classe do tipo VO - Value Object 
 * composta pelos atributos da entidade com visibilidade protegida e os métodos de acesso a estes atributos.
 * Classe utilizada para apresentar e manter em memória os dados desta entidade.
 * @see SuperVO
 */
public class CidadeVO extends SuperVO implements Serializable {

    @ChavePrimaria
    private Integer codigo;
    @DescricaoObjeto
    private String nome;
    private String estado;
    private String codigoIBGE;
    private PaisVO pais;
    private String codigoPais;

    /**
     * Construtor padrão da classe <code>Cidade</code>.
     * Cria uma nova instância desta entidade, inicializando automaticamente seus atributos (Classe VO).
     */
    public CidadeVO() {
        super();
        inicializarDados();
    }

    /**
     * Operação responsável por validar os dados de um objeto da classe <code>CidadeVO</code>.
     * Todos os tipos de consistência de dados são e devem ser implementadas neste método.
     * São validações típicas: verificação de campos obrigatérios, verificação de valores verificação para os atributos.
     * @exception ConsistirExecption Se uma inconsistência for encontrada aumaticamente é gerada uma exceção descrevendo
     *                               o atributo e o erro ocorrido.
     */
    public static void validarpar(CidadeVO obj) throws ConsistirException {
    }

    public static void validarDados(CidadeVO obj) throws ConsistirException {
        if (obj.getNome() == null || obj.getNome().equals("")) {
            throw new ConsistirException("O campo NOME (Cidade) deve ser informado.");
        }
        if (obj.getEstado() == null || obj.getEstado().equals("")) {
            throw new ConsistirException("O campo ESTADO (Cidade) deve ser informado.");
        }
        if (!obj.getEstadoEX()) {
            if (obj.getCodigoIBGE() == null || obj.getCodigoIBGE().equals("")) {
                throw new ConsistirException("O campo CÓDIGO IBGE (Cidade) deve ser informado.");
            }
        }
    }

    /**
     * Operação reponsável por inicializar os atributos da classe.
     */
    public void inicializarDados() {
        setCodigo(0);
        setNome("");
        setEstado("");
        setCodigoIBGE("");
    }

    public Boolean getEstadoEX() {
        if (getEstado().equals("EX")) {
            return true;
        }
        return false;
    }

    public String getEstado() {
        if (estado == null) {
            estado = "";
        }
        return (estado);
    }

    /**
     * Operação responsável por retornar o valor de apresentação de um atributo com um domínio específico.
     * Com base no valor de armazenamento do atributo esta função é capaz de retornar o
     * de apresentação correspondente. Útil para campos como sexo, escolaridade, etc. 
     */
    public String getEstado_Apresentar() {
        if (estado == null) {
            return "";
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        if (nome == null) {
            nome = "";
        }
        return (nome);
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCodigoIBGE() {
        if (codigoIBGE == null) {
            codigoIBGE = "";
        }
        if (getEstadoEX()) {
            return "9999999";
        }
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getCodigoPais() {
        if (codigoPais == null) {
            codigoPais = "";
        }
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public PaisVO getPais() {
        if(pais == null){
            pais = new PaisVO();
        }
        return pais;
    }

    public void setPais(PaisVO pais) {
        this.pais = pais;
    }
}
