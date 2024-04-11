package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import negocio.comuns.arquitetura.PerfilAcessoVO;
import negocio.comuns.arquitetura.PermissaoVO;
import negocio.comuns.arquitetura.UsuarioVO;
import negocio.comuns.utilitarias.AcessoException;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import controle.arquitetura.LoginControle;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * SuperClasse padrão para a classe <code>SuperEntidade</code>. Responsável por implementar características comuns
 * relativas ao controle de acesso e a conexão com o banco de dados.
 */
public class ControleAcesso extends SuperFacadeJDBC {

    /** Usuario logado no sistema, para qual deve ser verificado as permissões de acesso. */
    protected UsuarioVO usuario = null;
    /** PerfilAcesso objeto que contém as regras de permissão de acesso do USUÁRIO, para funcionalidade da aplicação. */
    private PerfilAcessoVO perfilAcesso = null;

    public ControleAcesso() throws Exception {
    }

    /** Creates a new instance of Funcionalidade */
    /**
     * Operação padrão para realizar o INCLUIR de dados de uma entidade no BD. Verifica e inicializa (se necessário) a
     * conexão com o BD. Verifica se o USUÁRIO logado possui permissão de acesso a operação INCLUIR.
     *
     * @param idEntidade
     *            Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    public static void incluir(String idEntidade) throws Exception {
        if (!verificarPermissaoUsuario(idEntidade, Uteis.INCLUIR)) {
            String nomeUsuario = "";
            if (obterUsuarioLogado() != null) {
                nomeUsuario = " " + obterUsuarioLogado().getNome().toLowerCase();
            }
            String msgErro = "USUÁRIO" + nomeUsuario + " não possui permissão para INCLUIR em " + idEntidade.toLowerCase();
            throw (new AcessoException(msgErro));
        }
    }

    /**
     * Operação padrão para realizar o ALTERAR de dados de uma entidade no BD. Verifica e inicializa (se necessário) a
     * conexão com o BD. Verifica se o USUÁRIO logado possui permissão de acesso a operação ALTERAR.
     *
     * @param idEntidade
     *            Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    public static void alterar(String idEntidade) throws Exception {
        if (!verificarPermissaoUsuario(idEntidade, Uteis.ALTERAR)) {
            String nomeUsuario = "";
            if (obterUsuarioLogado() != null) {
                nomeUsuario = " " + obterUsuarioLogado().getNome().toLowerCase();
            }
            String msgErro = "USUÁRIO" + nomeUsuario + " não possui permissão para ALTERAR em " + idEntidade.toLowerCase();
            throw (new AcessoException(msgErro));
        }
    }

    /**
     * Operação padrão para realizar o EXCLUIR de dados de uma entidade no BD. Verifica e inicializa (se necessário) a
     * conexão com o BD. Verifica se o USUÁRIO logado possui permissão de acesso a operação EXCLUIR.
     *
     * @param idEntidade
     *            Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    public static void excluir(String idEntidade) throws Exception {
        if (!verificarPermissaoUsuario(idEntidade, Uteis.EXCLUIR)) {
            String nomeUsuario = "";
            if (obterUsuarioLogado() != null) {
                nomeUsuario = " " + obterUsuarioLogado().getNome().toLowerCase();
            }
            String msgErro = "USUÁRIO" + nomeUsuario + " não possui permissão para EXCLUIR em " + idEntidade.toLowerCase();
            throw (new AcessoException(msgErro));
        }
    }

    /**
     * Operação padrão para realizar o CONSULTAR de dados de uma entidade no BD. Verifica e inicializa (se necessário) a
     * conexão com o BD. Verifica se o USUÁRIO logado possui permissão de acesso a operação CONSULTAR.
     *
     * @param idEntidade
     *            Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    public static void consultar(String idEntidade, boolean verificarAcesso) throws Exception {
        if (!verificarAcesso) {
            return;
        }
        if (!verificarPermissaoUsuario(idEntidade, Uteis.CONSULTAR)) {
            String nomeUsuario = "";
            if (obterUsuarioLogado() != null) {
                nomeUsuario = " " + obterUsuarioLogado().getNome().toLowerCase();
            }
            String msgErro = "USUÁRIO" + nomeUsuario + " não possui permissão para CONSULTAR em " + idEntidade.toLowerCase();
            throw (new AcessoException(msgErro));
        }
    }

    public static void consultar(String idEntidade) throws Exception {
        consultar(idEntidade, false);
    }

    /**
     * Operação padrão para realizar o EMITIR UM Relatório de dados de uma entidade no BD. Verifica e inicializa (se
     * necessário) a conexão com o BD. Verifica se o USUÁRIO logado possui permissão de acesso a operação
     * EMITIRRELATORIO.
     *
     * @param idEntidade
     *            Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    public static void emitirRelatorio(String idEntidade, boolean verificarAcesso) throws Exception {
        if (!verificarAcesso) {
            return;
        }
        if (!verificarPermissaoUsuario(idEntidade, Uteis.EMITIRRELATORIO)) {
            String nomeUsuario = "";
            if (obterUsuarioLogado() != null) {
                nomeUsuario = " " + obterUsuarioLogado().getNome().toLowerCase();
            }
            String msgErro = "USUÁRIO" + nomeUsuario + " não possui permissão para EMITIR RELATÓRIO em " + idEntidade.toLowerCase();
            throw (new AcessoException(msgErro));
        }
    }

    public static void emitirRelatorio(String idEntidade) throws Exception {
        emitirRelatorio(idEntidade, true);
    }

    /**
     * Operação padrão para verificar acesso do USUÁRIO a determinada funcionalidade registrada no Perfil de Acesso do
     * USUÁRIO. Pode ser, por exemplo, alterar um valor de um campo. Neste caso deverá existir no PerfilAcesso do
     * USUÁRIO o key (apelido) identificando esta funcionalidade. Esta funcinalidade deverá estar gravada com a opção
     * TOTAL ou ALTERAR para garantir que o USUÁRIO tenha acesso é mesma.
     *
     * @param funcionalidade
     *            Nome da funcionalidade para a qual se deseja realizar a verificação de permissão do USUÁRIO.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    public static void verificarPermissaoUsuarioFuncionalidade(String funcionalidade) throws Exception {
        if (!verificarPermissaoUsuario(funcionalidade, Uteis.ALTERAR)) {
            String nomeUsuario = "";
            if (obterUsuarioLogado() != null) {
                nomeUsuario = " " + obterUsuarioLogado().getNome().toLowerCase();
            }
            String msgErro = "USUÁRIO" + nomeUsuario + " não possui permissão para a funcionalidade " + funcionalidade.toLowerCase();
            throw (new AcessoException(msgErro));
        }
    }

    /**
     * Operação de responsável por verificar se uma operação está definida dentro de um objeto de
     * <code>PermissaoVO</code>. Caso o codigo da operação (parâmetro <code>int operacao</code>) exista no atributo
     * <code>permissoes</code> de <code>PermissaoVO</code> significa que existe acesso liberado para esta operação. O
     * atributo permissoes assume valores do tipo "(1)(2)(4)", ou seja, os códigos da operações pertinentes cercados por
     * parênteses.
     *
     * @param permissaoVO
     *            objeto contendo as permissões para uma determinada entidade, de um perfil de acesso específico.
     * @param operacao
     *            código de uma operação para qual se deseja realizar esta verificação.
     * @return boolean true caso exista liberação para uso da operação neste perfil de acesso.
     */
    private static boolean verificarPermissaoOperacao(PermissaoVO permissaoVO, int operacao) throws Exception {
        String operStr = "(" + operacao + ")";
        if (permissaoVO == null) {
            return false;
        }
        int resultado = permissaoVO.getPermissoes().indexOf(operStr);
        if (resultado == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Operação responsável por verificar se uma operação está definida dentro de um conjunto de
     * <code>PermissaoVO</code>, mantidos em um List. Isto para uma determinada entidade. Com base no nome da entidade
     * (key do List) é possível obter o objeto da classe <code>PermissaoVO</code> pertinente a esta entidade. Para que
     * posteriormente seja verificado o acesso a operação através da operação
     * </code>verificarPermissaoOperacao(PermissaoVO permissao, int operacao)</code>.
     *
     * @param permissoes
     *            Todos os objetos PermissaoVO do perfil de acesso em questão.
     * @param nomeEntidade
     *            Código de uma operação para qual se deseja realizar esta verificação.
     * @param operacao
     *            Código de uma operação para qual se deseja realizar esta verificação.
     * @return boolean true caso exista liberação para uso da operação neste perfil de acesso.
     */
    private static boolean verificarPermissaoOperacao(List permissoes, String nomeEntidade, int operacao) throws Exception {
        String operStr = getOperacaoStr(operacao);
        Iterator i = permissoes.iterator();
        while (i.hasNext()) {
            PermissaoVO obj = (PermissaoVO) i.next();
            if (obj.getNomeEntidade().equalsIgnoreCase(nomeEntidade)) {
                return (verificarPermissaoOperacao(obj, operacao));
            }
        }
        return false;
    }

    private static String getOperacaoStr(int codOper) {
        return "(" + codOper + ")";
    }


    public static UsuarioVO verificarLoginUsuario(String username, String senha, String ip, Integer segurancaTentativasAcessoAntesBloqueio, Integer segurancaHorasParaZerarTentativasAcesso) throws Exception {
        String sqlStr = "SELECT * FROM Usuario WHERE ((username = ?) AND (senha = ?))";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{username, Uteis.encriptar(senha)});
        if (!tabelaResultado.next()) {
            registrarAcessoNegado(username, senha, ip);
            if(consultarQuantidadeAcessosNegados(username, segurancaHorasParaZerarTentativasAcesso) >= segurancaTentativasAcessoAntesBloqueio){
                getFacadeFactory().getUsuarioFacade().bloquearUsuario(username);
                throw new ConsistirException("Usuário bloqueado por limite de tentativas excedido.");
            }
            throw new ConsistirException("Usuário e senha não conferem.");
        }
        return (Usuario.montarDados2(tabelaResultado));
    }

    public static void registrarAcessoNegado(final String username, final String senha, final String ip) throws Exception {
        try {
            final String sql = "INSERT INTO historicoAcessoNegado( username, ip, data ) VALUES ( ?, ?, now() ) returning codigo";
            getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    sqlInserir.setString(1, username);
                    sqlInserir.setString(2, ip);
                    return sqlInserir;
                }
            }, new ResultSetExtractor() {
                public Object extractData(ResultSet arg0) throws SQLException, DataAccessException {
                    return null;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static Integer consultarQuantidadeAcessosNegados(String username, Integer segurancaHorasParaZerarTentativasAcesso) throws Exception {
        String sql = "SELECT COUNT(codigo) AS qtd FROM historicoAcessoNegado WHERE username = ? AND data >= (now() - INTERVAL '1 hour')";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{username});
        if (tabelaResultado.next()) {
            return tabelaResultado.getInt("qtd");
        }
        return 0;
    }

    private static UsuarioVO obterUsuarioLogado() throws Exception {
        LoginControle loginControle = (LoginControle) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginControle");
        UsuarioVO usuario = loginControle.getUsuario();
        return usuario;
    }

    private static PerfilAcessoVO obterPerfilAcessoUsuarioLogado() throws Exception {
        LoginControle loginControle = (LoginControle) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginControle");
        PerfilAcessoVO perfilAcesso = loginControle.getPerfilAcesso();
        return perfilAcesso;
    }

    /**
     * Operação responsável por verificar se um USUÁRIO possui acesso a uma determinada operação, de uma entidade
     * específica. Faz uso da operação </code>verificarPermissaoOperacao(getPerfilAcesso().getPermissaoVOs(),
     * nomeEntidade, operacao)</code> para realizar a verificação para um perfil de acesso específico. Libera a consulta
     * para as entidades PerfilAcesso e Permissoes, para que seja possível consultar se o USUÁRIO possui ou não acesso a
     * uma determinada funcionalidade.
     *
     * @param nomeEntidade
     *            Código de uma operação para qual se deseja realizar esta verificação.
     * @param operacao
     *            Código de uma operação para qual se deseja realizar esta verificação.
     * @return boolean true caso exista liberação para uso da operação neste perfil de acesso.
     */
    private static boolean verificarPermissaoUsuario(String nomeEntidade, int operacao) throws Exception {
        PerfilAcessoVO perfilAcesso = obterPerfilAcessoUsuarioLogado();
        UsuarioVO usuario = obterUsuarioLogado();
        if (nomeEntidade.equals("")) {
            return false;
        }
        if ((usuario == null) || (usuario.getUsername().equalsIgnoreCase(""))) {
            return false;
        }
        if (perfilAcesso == null) {
            return false;
        }
        return verificarPermissaoOperacao(perfilAcesso.getPermissaoVOs(), nomeEntidade, operacao);
    }

    public UsuarioVO getUsuario() {
        return usuario;
    }

    public void setSomenteUsuario(UsuarioVO aUsuario) throws Exception {
        usuario = aUsuario;
    }

    public void setUsuario(UsuarioVO aUsuario) throws Exception {
        usuario = aUsuario;
    }

    public String getIdEntidade() {
        return "Entidade";
    }

    public PerfilAcessoVO getPerfilAcesso() {
        return perfilAcesso;
    }

    public void setPerfilAcesso(PerfilAcessoVO aPerfilAcesso) {
        perfilAcesso = aPerfilAcesso;
    }
}
