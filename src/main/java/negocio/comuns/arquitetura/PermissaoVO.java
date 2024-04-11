package negocio.comuns.arquitetura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import negocio.comuns.utilitarias.*;
import negocio.facade.jdbc.arquitetura.PerfilAcesso;

/**
 * Reponsável por manter os dados da entidade Permissao. Classe do tipo VO - Value Object
 * composta pelos atributos da entidade com visibilidade protegida e os métodos de acesso a estes atributos.
 * Classe utilizada para apresentar e manter em memória os dados desta entidade.
 * @see SuperVO
 * @see PerfilAcesso
 */
public class PermissaoVO extends SuperVO implements Serializable {

    private Integer codPerfilAcesso;
    private String nomeEntidade;
    private String permissoes;
    private Integer tipoPermissao;
    private String valorEspecifico;
    private String valorInicial;
    private String valorFinal;
    private Boolean remover;
    private String temporaria;

    /**
     * Construtor padrão da classe <code>Permissao</code>.
     * Cria uma nova instância desta entidade, inicializando automaticamente seus atributos (Classe VO).
     */
    public PermissaoVO() {
        super();
        inicializarDados();
    }

    /**
     * Operação responsável por validar os dados de um objeto da classe <code>PermissaoVO</code>.
     * Todos os tipos de consistência de dados são e devem ser implementadas neste método.
     * São validações típicas: verificação de campos obrigatérios, verificação de valores verificação para os atributos.
     * @exception ConsistirExecption Se uma inconsistência for encontrada aumaticamente é gerada uma exceção descrevendo
     *                               o atributo e o erro ocorrido.
     */
    public static void validarDados(PermissaoVO obj) throws ConsistirException {
        if (obj.getNomeEntidade().equals("")) {
            throw new ConsistirException("O campo NOME DA ENTIDADE (Permissão) deve ser informado.");
        }
    }

    public void alterarPermissao() {
        try {
            setPermissoes("");
            setPermissoes(getTemporaria());
        } catch (Exception e) {
        }
    }

    public List getPermissaoAlterar() throws Exception {
        List objs = new ArrayList(0);
        objs.add(new SelectItem("(0)(9)(1)", "Incluir e Consultar"));
        objs.add(new SelectItem("(0)", "Consultar"));
        objs.add(new SelectItem("(12)", "Relatorio"));
        objs.add(new SelectItem("(2)", "Alterar"));
        objs.add(new SelectItem("(0)(1)(2)(9)(12)", "Total (Sem Excluir)"));
        objs.add(new SelectItem("(0)(1)(2)(3)(9)(12)", "Total"));
        objs.add(new SelectItem("(3)", "Excluir"));
        objs.add(new SelectItem("(1)(9)", "Incluir"));

        return objs;
    }

    /**
     * Operação reponsável por inicializar os atributos da classe.
     */
    public void inicializarDados() {
        setNomeEntidade("");
        setPermissoes("(0)(1)(2)(3)(9)(12)");
        setTipoPermissao(0);
        setValorEspecifico("");
        setValorInicial("");
        setValorFinal("");
    }

    public String getValorFinal() {
        if (valorFinal == null) {
            return "";
        }
        return (valorFinal);
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getValorInicial() {
        if (valorInicial == null) {
            return "";
        }
        return (valorInicial);
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getValorEspecifico() {
        if (valorEspecifico == null) {
            return "";
        }
        return (valorEspecifico);
    }

    public void setValorEspecifico(String valorEspecifico) {
        this.valorEspecifico = valorEspecifico;
    }

    public Integer getTipoPermissao() {
        return (tipoPermissao);
    }

    public void setTipoPermissao(Integer tipoPermissao) {
        this.tipoPermissao = tipoPermissao;
    }

    public String getTituloApresentacao() {
        return (OpcoesPerfilAcesso.obterTituloReferenteOpcaoPerfilAcesso(getNomeEntidade()));
    }

    public String getPermissoes() {
        if (permissoes == null) {
            return "";
        }
        return (permissoes);
    }

    /**
     * Operação responsável por retornar o valor de apresentação de um atributo com um domínio específico.
     * Com base no valor de armazenamento do atributo esta função é capaz de retornar o
     * de apresentação correspondente. Útil para campos como sexo, escolaridade, etc. 
     */
    public String getPermissoes_Apresentar() {
        if (permissoes == null) {
            return "";
        }
        if (permissoes.equals("(0)(9)(1)")) {
            return "Incluir e Consultar";
        }
        if (permissoes.equals("(0)")) {
            return "Consultar";
        }
        if (permissoes.equals("(12)")) {
            return "Relatorio";
        }
        if (permissoes.equals("(2)")) {
            return "Alterar";
        }
        if (permissoes.equals("(0)(1)(2)(9)(12)")) {
            return "Total (Sem Excluir)";
        }
        if (permissoes.equals("(0)(1)(2)(3)(9)(12)")) {
            return "Total";
        }
        if (permissoes.equals("(3)")) {
            return "Excluir";
        }
        if (permissoes.equals("(1)(9)")) {
            return "Incluir";
        }
        return (permissoes);
    }

    public void setPermissoes(String permissoes) {
        this.permissoes = permissoes;
    }

    public String getNomeEntidade() {
        if (nomeEntidade == null) {
            return "";
        }
        return (nomeEntidade);
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public Integer getCodPerfilAcesso() {
        return (codPerfilAcesso);
    }

    public void setCodPerfilAcesso(Integer codPerfilAcesso) {
        this.codPerfilAcesso = codPerfilAcesso;
    }

    public Boolean getRemover() {
        if (remover == null) {
            remover = false;
        }
        return remover;
    }

    public void setRemover(Boolean remover) {
        this.remover = remover;
    }

    public String getTemporaria() {
        if (temporaria == null) {
            temporaria = getPermissoes();
        }
        return temporaria;
    }

    public void setTemporaria(String temporaria) {
        this.temporaria = temporaria;
    }
}
