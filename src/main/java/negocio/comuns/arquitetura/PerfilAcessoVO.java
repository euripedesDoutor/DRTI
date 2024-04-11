package negocio.comuns.arquitetura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import negocio.comuns.utilitarias.ConsistirException;

/**
 * Reponsável por manter os dados da entidade PerfilAcesso. Classe do tipo VO - Value Object composta pelos atributos da
 * entidade com visibilidade protegida e os métodos de acesso a estes atributos. Classe utilizada para apresentar e
 * manter em memória os dados desta entidade.
 * 
 * @see SuperVO
 */
public class PerfilAcessoVO extends SuperVO implements Serializable {

    private Integer codigo;
    private String nome;
    private Boolean administrador;
    /** Atributo responsável por manter os objetos da classe <code>Permissao</code>. */
    private List permissaoVOs;
    private String tipoPerfil;
    /** Atributo não persistido no banco de dados */
    private Boolean perfilClonado;
    private String dispositivoAcessoExterno;

    /**
     * Construtor padrão da classe <code>PerfilAcesso</code>. Cria uma nova instância desta entidade, inicializando
     * automaticamente seus atributos (Classe VO).
     */
    public PerfilAcessoVO() {
        super();
        inicializarDados();
    }

    /**
     * Operação responsável por validar os dados de um objeto da classe <code>PerfilAcessoVO</code>. Todos os tipos de
     * consistência de dados são e devem ser implementadas neste método. São validações típicas: verificação de campos
     * obrigatérios, verificação de valores verificação para os atributos.
     *
     * @exception ConsistirExecption
     *                Se uma inconsistência for encontrada aumaticamente é gerada uma exceção descrevendo o atributo e o
     *                erro ocorrido.
     */
    public static void validarDados(PerfilAcessoVO obj) throws ConsistirException {
        if (obj.getNome().equals("")) {
            throw new ConsistirException("O campo NOME (Perfil de Acesso) deve ser informado.");
        }
    }

    /**
     * Operação reponsável por inicializar os atributos da classe.
     */
    public void inicializarDados() {
        setCodigo(0);
        setNome("");
        setAdministrador(false);
    }

    /**
     * Operação responsável por adicionar um novo objeto da classe <code>PermissaoVO</code> ao List
     * <code>permissaoVOs</code>. Utiliza o atributo padrão de consulta da classe <code>Permissao</code> -
     * getNomeEntidade() - como identificador (key) do objeto no List.
     *
     * @param obj
     *            Objeto da classe <code>PermissaoVO</code> que será adiocionado ao Hashtable correspondente.
     */
    public void adicionarObjPermissaoVOs(PermissaoVO obj) throws Exception {
        PermissaoVO.validarDados(obj);
        int index = 0;
        Iterator i = getPermissaoVOs().iterator();
        while (i.hasNext()) {
            PermissaoVO objExistente = (PermissaoVO) i.next();
            if (objExistente.getNomeEntidade().equals(obj.getNomeEntidade())) {
                getPermissaoVOs().set(index, obj);
                return;
            }
            index++;
        }
        getPermissaoVOs().add(obj);
    }

    /**
     * Operação responsável por excluir um objeto da classe <code>PermissaoVO</code> no List <code>permissaoVOs</code>.
     * Utiliza o atributo padrão de consulta da classe <code>Permissao</code> - getNomeEntidade() - como identificador
     * (key) do objeto no List.
     *
     * @param nomeEntidade
     *            Parâmetro para localizar e remover o objeto do List.
     */
    public void excluirObjPermissaoVOs(String nomeEntidade) throws Exception {
        int index = 0;
        Iterator i = getPermissaoVOs().iterator();
        while (i.hasNext()) {
            PermissaoVO objExistente = (PermissaoVO) i.next();
            if (objExistente.getNomeEntidade().equals(nomeEntidade)) {
                getPermissaoVOs().remove(index);
                return;
            }
            index++;
        }
    }

    /**
     * Operação responsável por consultar um objeto da classe <code>PermissaoVO</code> no List <code>permissaoVOs</code>
     * . Utiliza o atributo padrão de consulta da classe <code>Permissao</code> - getNomeEntidade() - como identificador
     * (key) do objeto no List.
     *
     * @param nomeEntidade
     *            Parâmetro para localizar o objeto do List.
     */
    public PermissaoVO consultarObjPermissaoVO(String nomeEntidade) throws Exception {
        Iterator i = getPermissaoVOs().iterator();
        while (i.hasNext()) {
            PermissaoVO objExistente = (PermissaoVO) i.next();
            if (objExistente.getNomeEntidade().equals(nomeEntidade)) {
                return objExistente;
            }
        }
        return null;
    }

    /** Retorna Atributo responsável por manter os objetos da classe <code>Permissao</code>. */
    public List getPermissaoVOs() {
        if (permissaoVOs == null) {
            permissaoVOs = new ArrayList(0);
        }
        return (permissaoVOs);
    }

    /** Define Atributo responsável por manter os objetos da classe <code>Permissao</code>. */
    public void setPermissaoVOs(List permissaoVOs) {
        this.permissaoVOs = permissaoVOs;
    }

    public String getNome() {
        if (nome == null) {
            return "";
        }
        return (nome);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigo() {
        return (codigo);
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getAdministrador_Apresentar() {
        if(getAdministrador()){
            return "SIM";
        }
        return "NÃO";
    }
    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean getTipoPerfilProducao() {
        if (getTipoPerfil().equals("PRODUCAO")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilEstoque() {
        if (getTipoPerfil().equals("ESTOQUE")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilAbate() {
        if (getTipoPerfil().equals("ABATE")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilPDV() {
        if (getTipoPerfil().equals("PDV")) {
            return true;
        }
        return false;
    }
    public Boolean getTipoPerfilGestorGraxaria() {
        if (getTipoPerfil().equals("GESTORGRAXARIA")) {
            return true;
        }
        return false;
    }
    public Boolean getTipoPerfilProducaoGraxaria() {
        if (getTipoPerfil().equals("PRODUCAOGRAXARIA")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilCaixa() {
        if (getTipoPerfil().equals("CAIXA")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilColeta() {
        if (getTipoPerfil().equals("COLETA")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilVendaBalcao() {
        if (getTipoPerfil().equals("VENDABALCAO")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilVendaBalcaoMobile() {
        if (getTipoPerfil().equals("VENDABALCAOMOBILE")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilNormal() {
        if (getTipoPerfil().equals("NORMAL")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilVendedorExterno() {
        if (getTipoPerfil().equals("VENDEDOREXTERNO")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilRepresentante() {
        if (getTipoPerfil().equals("REPRESENTANTE")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilContador() {
        if (getTipoPerfil().equals("CONTADOR")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilBalancao() {
        if (getTipoPerfil().equals("BALANCAO")) {
            return true;
        }
        return false;
    }

    public Boolean getTipoPerfilCarregamentoMobile() {
        if (getTipoPerfil().equals("CARREGAMENTOMOBILE")) {
            return true;
        }
        return false;
    }

    public String getTipoPerfil() {
        if (tipoPerfil == null) {
            tipoPerfil = "NORMAL";
        }
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Boolean getPerfilClonado() {
        if (perfilClonado == null) {
            perfilClonado = false;
        }
        return perfilClonado;
    }

    public void setPerfilClonado(Boolean perfilClonado) {
        this.perfilClonado = perfilClonado;
    }

    public Boolean getDispositivoAcessoExternoSmartPhone() {
        if (getDispositivoAcessoExterno().equals("SMARTPHONE")) {
            return true;
        }
        return false;
    }

    public Boolean getDispositivoAcessoExternoTablet() {
        if (getDispositivoAcessoExterno().equals("TABLET")) {
            return true;
        }
        return false;
    }

    public String getDispositivoAcessoExterno() {
        return "SMARTPHONE";
//        if (dispositivoAcessoExterno == null) {
//            dispositivoAcessoExterno = "SMARTPHONE";
//        }
//        return dispositivoAcessoExterno;
    }

    public void setDispositivoAcessoExterno(String dispositivoAcessoExterno) {
        this.dispositivoAcessoExterno = dispositivoAcessoExterno;
    }
}
