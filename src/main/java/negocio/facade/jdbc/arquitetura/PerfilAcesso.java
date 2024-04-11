package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import negocio.comuns.arquitetura.PerfilAcessoVO;
import negocio.comuns.arquitetura.PermissaoVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.interfaces.arquitetura.PerfilAcessoInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de persistência que encapsula todas as operações de manipulação dos dados da classe
 * <code>PerfilAcessoVO</code>. Responsável por implementar operações como incluir, alterar, excluir e consultar
 * pertinentes a classe <code>PerfilAcessoVO</code>. Encapsula toda a interação com o banco de dados.
 * 
 * @see PerfilAcessoVO
 * @see SuperEntidade
 */
@SuppressWarnings("unchecked")
@Repository
@Lazy
public class PerfilAcesso extends SuperEntidade implements PerfilAcessoInterfaceFacade {

    protected static String idEntidade;
    private Hashtable permissaos;

    public PerfilAcesso() throws Exception {
        super();
        setIdEntidade("PerfilAcesso");
        setPermissaos(new Hashtable());
    }

    /**
     * Operação responsável por retornar um novo objeto da classe <code>PerfilAcessoVO</code>.
     */
    public PerfilAcessoVO novo() throws Exception {
        incluir(getIdEntidade());
        PerfilAcessoVO obj = new PerfilAcessoVO();
        return obj;
    }

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>PerfilAcessoVO</code>. Primeiramente
     * valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do
     * USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>incluir</code> da superclasse.
     *
     * @param obj
     *            Objeto da classe <code>PerfilAcessoVO</code> que será gravado no banco de dados.
     * @exception Exception
     *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final PerfilAcessoVO obj) throws Exception {
        try {
            PerfilAcessoVO.validarDados(obj);
            incluir(getIdEntidade());
            final String sql = "INSERT INTO PerfilAcesso( nome, administrador, tipoPerfil, dispositivoAcessoExterno) VALUES ( ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    sqlInserir.setString(1, obj.getNome());
                    sqlInserir.setBoolean(2, obj.getAdministrador());
                    sqlInserir.setString(3, obj.getTipoPerfil());
                    sqlInserir.setString(4, obj.getDispositivoAcessoExterno());
                    return sqlInserir;
                }
            }, new ResultSetExtractor() {

                public Object extractData(ResultSet arg0) throws SQLException, DataAccessException {
                    if (arg0.next()) {
                        obj.setNovoObj(false);
                        return arg0.getInt("codigo");
                    }
                    return null;
                }
            }));
            obj.setNovoObj(false);
            if (obj.getPerfilClonado()) {
                for (PermissaoVO permissao : (ArrayList<PermissaoVO>) obj.getPermissaoVOs()) {
                    permissao.setCodPerfilAcesso(obj.getCodigo());
                }
            }
            getFacadeFactory().getPermissaoFacade().incluirPermissaos(obj.getCodigo(), obj.getPermissaoVOs());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Operação responsável por alterar no BD os dados de um objeto da classe <code>PerfilAcessoVO</code>. Sempre
     * utiliza a chave primária da classe como atributo para localização do registro a ser alterado. Primeiramente
     * valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do
     * USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>alterar</code> da superclasse.
     *
     * @param obj
     *            Objeto da classe <code>PerfilAcessoVO</code> que será alterada no banco de dados.
     * @exception Execption
     *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final PerfilAcessoVO obj) throws Exception {
        try {
            PerfilAcessoVO.validarDados(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE PerfilAcesso set nome=? , administrador=?, tipoPerfil=?, dispositivoAcessoExterno=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setString(1, obj.getNome());
                    sqlAlterar.setBoolean(2, obj.getAdministrador());
                    sqlAlterar.setString(3, obj.getTipoPerfil());
                    sqlAlterar.setString(4, obj.getDispositivoAcessoExterno());
                    sqlAlterar.setInt(5, obj.getCodigo().intValue());
                    return sqlAlterar;
                }
            });
            getFacadeFactory().getPermissaoFacade().alterarPermissaos(obj.getCodigo(), obj.getPermissaoVOs());
            //regi
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Operação responsável por excluir no BD um objeto da classe <code>PerfilAcessoVO</code>. Sempre localiza o
     * registro a ser excluído através da chave primária da entidade. Primeiramente verifica a conexão com o banco de
     * dados e a permissão do USUÁRIO para realizar esta operacão na entidade. Isto, através da operação
     * <code>excluir</code> da superclasse.
     *
     * @param obj
     *            Objeto da classe <code>PerfilAcessoVO</code> que será removido no banco de dados.
     * @exception Execption
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(PerfilAcessoVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM PerfilAcesso WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
            getFacadeFactory().getPermissaoFacade().excluirPermissaos(obj.getCodigo());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Responsável por realizar uma consulta de <code>PerfilAcesso</code> através do valor do atributo
     * <code>String nome</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>PerfilAcessoVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorNome(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM PerfilAcesso WHERE Lower(nome) like('" + valorConsulta.toLowerCase() + "%') ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public boolean consultarPerfilAcessoServidorVisitanteSelecionado(Integer codigoPerfilAcesso, boolean valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "select * from perfilacesso where perfilacessoservidorvisitante = " + valorConsulta + " and codigo <> " + codigoPerfilAcesso.intValue();
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if (!tabelaResultado.next()) {
            return false;
        }
        return true;
    }

    public PerfilAcessoVO consultarPerfilAcessoServidorVisitante() throws Exception {
        String sqlStr = "select * from perfilacesso where perfilacessoservidorvisitante = true";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if (!tabelaResultado.next()) {
            return null;
        }
        return montarDados(tabelaResultado);
    }

    /**
     * Responsável por realizar uma consulta de <code>PerfilAcesso</code> através do valor do atributo
     * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido. Faz uso
     * da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>PerfilAcessoVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM PerfilAcesso WHERE codigo = " + valorConsulta.intValue() + " ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public static Integer consultarCodigoPerfilAcesso(Integer usuario, Integer empresa) throws Exception {
        String sqlStr = "SELECT PerfilAcesso.codigo FROM PerfilAcesso "
                + " INNER JOIN UsuarioPerfilAcesso ON usuarioPerfilAcesso.perfilAcesso = perfilAcesso.codigo "
                + " WHERE usuarioPerfilAcesso.usuario = " + usuario + " AND usuarioPerfilAcesso.empresa = " + empresa + "";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if(tabelaResultado.next()){
            return tabelaResultado.getInt("codigo");
        }
        return 0;
    }
    public PerfilAcessoVO consultarPorCodigoUsuarioEmpresa(Integer usuario, Integer empresa) throws Exception {
        String sqlStr = "SELECT PerfilAcesso.* FROM PerfilAcesso "
                + " INNER JOIN UsuarioPerfilAcesso ON usuarioPerfilAcesso.perfilAcesso = perfilAcesso.codigo "
                + " WHERE usuarioPerfilAcesso.usuario = " + usuario + " AND usuarioPerfilAcesso.empresa = " + empresa + "";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        tabelaResultado.next();
        return (montarDados(tabelaResultado));
    }

    public List consultarPorTipoPerfil(String valorConsulta) throws Exception {
        String sqlStr = "SELECT * FROM PerfilAcesso WHERE upper(tipoPerfil) like ('" + valorConsulta.toUpperCase() + "') ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    /**
     * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
     * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
     * vez.
     *
     * @return List Contendo vários objetos da classe <code>PerfilAcessoVO</code> resultantes da consulta.
     */
    public List montarDadosConsulta(SqlRowSet tabelaResultado) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado));
        }
        return vetResultado;
    }

    /**
     * Responsável por montar os dados resultantes de uma consulta ao banco de dados (<code>ResultSet</code>) em um
     * objeto da classe <code>PerfilAcessoVO</code>.
     *
     * @return O objeto da classe <code>PerfilAcessoVO</code> com os dados devidamente montados.
     */
    public PerfilAcessoVO montarDados(SqlRowSet dadosSQL) throws Exception {
        PerfilAcessoVO obj = new PerfilAcessoVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setAdministrador(dadosSQL.getBoolean("administrador"));
        obj.setTipoPerfil(dadosSQL.getString("tipoPerfil"));
        obj.setDispositivoAcessoExterno(dadosSQL.getString("dispositivoAcessoExterno"));
        obj.setDispositivoAcessoExterno(dadosSQL.getString("dispositivoAcessoExterno"));
        obj.setNovoObj(false);
        obj.setPermissaoVOs(getFacadeFactory().getPermissaoFacade().consultarPermissaos(obj.getCodigo()));
        return obj;
    }

    /**
     * Operação responsável por adicionar um objeto da <code>PermissaoVO</code> no Hashtable <code>Permissaos</code>.
     * Neste Hashtable são mantidos todos os objetos de Permissao de uma determinada PerfilAcesso.
     *
     * @param obj
     *            Objeto a ser adicionado no Hashtable.
     */
    public void adicionarObjPermissaos(PermissaoVO obj) throws Exception {
        getPermissaos().put(obj.getNomeEntidade() + "", obj);
        // adicionarObjSubordinadoOC
    }

    /**
     * Operação responsável por remover um objeto da classe <code>PermissaoVO</code> do Hashtable
     * <code>Permissaos</code>. Neste Hashtable são mantidos todos os objetos de Permissao de uma determinada
     * PerfilAcesso.
     *
     * @param NomeEntidade
     *            Atributo da classe <code>PermissaoVO</code> utilizado como apelido (key) no Hashtable.
     */
    public void excluirObjPermissaos(String NomeEntidade) throws Exception {
        getPermissaos().remove(NomeEntidade + "");
        // excluirObjSubordinadoOC
    }

    /**
     * Operação responsável por localizar um objeto da classe <code>PerfilAcessoVO</code> através de sua chave primária.
     *
     * @exception Exception
     *                Caso haja problemas de conexão ou localização do objeto procurado.
     */
    public PerfilAcessoVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM PerfilAcesso WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados.");
        }
        return (montarDados(tabelaResultado));
    }

    public Hashtable getPermissaos() {
        return (permissaos);
    }

    public void setPermissaos(Hashtable permissaos) {
        this.permissaos = permissaos;
    }

    /**
     * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
     * permissões de acesso as operações desta classe.
     */
    public String getIdEntidade() {
        return PerfilAcesso.idEntidade;
    }

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
     * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
     * Como o controle de acesso é realizado com base neste identificador,
     */
    public void setIdEntidade(String idEntidade) {
        PerfilAcesso.idEntidade = idEntidade;
    }
}
