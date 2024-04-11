package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import negocio.comuns.arquitetura.LogVO;
import negocio.comuns.arquitetura.UsuarioVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.interfaces.arquitetura.LogInterfaceFacade;

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
 * Classe de persistência que encapsula todas as operações de manipulação dos dados da classe <code>UsuarioVO</code>.
 * Responsável por implementar operações como incluir, alterar, excluir e consultar pertinentes a classe
 * <code>UsuarioVO</code>. Encapsula toda a interação com o banco de dados.
 * 
 * @see UsuarioVO
 * @see SuperEntidade
 */
@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Log extends SuperEntidade implements LogInterfaceFacade {

    protected static String idEntidade;

    public Log() throws Exception {
        super();
        setIdEntidade("Log");
    }

    /**
     * Operação responsável por retornar um novo objeto da classe <code>UsuarioVO</code>.
     */
    public LogVO novo() throws Exception {
        incluir(getIdEntidade());
        LogVO obj = new LogVO();
        return obj;
    }

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>UsuarioVO</code>. Primeiramente
     * valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do
     * USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>incluir</code> da superclasse.
     *
     * @param obj
     *            Objeto da classe <code>UsuarioVO</code> que será gravado no banco de dados.
     * @exception Exception
     *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final LogVO obj) throws Exception {
        try {
            final String sql = "INSERT INTO Log( nomeEntidade, nomeEntidadeDescricao, chavePrimaria, chavePrimariaEntidadeSubordinada, nomeCampo, valorCampoAnterior, valorCampoAlterado, dataAlteracao, "
                    + "responsavelAlteracao, operacao ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    sqlInserir.setString(1, obj.getNomeEntidade().toUpperCase());
                    sqlInserir.setString(2, obj.getNomeEntidadeDescricao());
                    sqlInserir.setString(3, obj.getChavePrimaria());
                    sqlInserir.setString(4, obj.getChavePrimariaEntidadeSubordinada());
                    sqlInserir.setString(5, obj.getNomeCampo());
                    sqlInserir.setString(6, obj.getValorCampoAnterior());
                    sqlInserir.setString(7, obj.getValorCampoAlterado());
                    sqlInserir.setTimestamp(8, Uteis.getDataJDBCTimestamp(obj.getDataAlteracao()));
                    sqlInserir.setString(9, obj.getResponsavelAlteracao());
                    sqlInserir.setString(10, obj.getOperacao());
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
        } catch (Exception e) {
            obj.setNovoObj(true);
            throw e;
        }
    }

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>UsuarioVO</code>. Primeiramente
     * valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do
     * USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>incluir</code> da superclasse.
     *
     * @param obj
     *            Objeto da classe <code>UsuarioVO</code> que será gravado no banco de dados.
     * @exception Exception
     *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final LogVO obj) throws Exception {
        try {
            final String sql = "UPDATE Log set nomeEntidade=?, nomeEntidadeDescricao=?, chavePrimaria=?, chavePrimariaEntidadeSubordinada=?, nomeCampo=?, valorCampoAnterior=?, valorCampoAlterado=?, "
                    + "dataAlteracao=?, responsavelAlteracao=?, operacao=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setString(1, obj.getNomeEntidade().toUpperCase());
                    sqlAlterar.setString(2, obj.getNomeEntidadeDescricao());
                    sqlAlterar.setString(3, obj.getChavePrimaria());
                    sqlAlterar.setString(4, obj.getChavePrimariaEntidadeSubordinada());
                    sqlAlterar.setString(5, obj.getNomeCampo());
                    sqlAlterar.setString(6, obj.getValorCampoAnterior());
                    sqlAlterar.setString(7, obj.getValorCampoAlterado());
                    sqlAlterar.setTimestamp(8, Uteis.getDataJDBCTimestamp(obj.getDataAlteracao()));
                    sqlAlterar.setString(9, obj.getResponsavelAlteracao());
                    sqlAlterar.setString(10, obj.getOperacao());
                    sqlAlterar.setInt(11, obj.getCodigo());
                    return sqlAlterar;
                }
            });
            obj.setNovoObj(false);
        } catch (Exception e) {
            obj.setNovoObj(true);
            throw e;
        }
    }

    /**
     * Operação responsável por excluir no BD um objeto da classe <code>UsuarioVO</code>. Sempre localiza o registro a
     * ser excluído através da chave primária da entidade. Primeiramente verifica a conexão com o banco de dados e a
     * permissão do USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>excluir</code> da
     * superclasse.
     *
     * @param obj
     *            Objeto da classe <code>UsuarioVO</code> que será removido no banco de dados.
     * @exception Execption
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(LogVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Log WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Responsável por realizar uma consulta de <code>Usuario</code> através do valor do atributo
     * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido. Faz uso
     * da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>UsuarioVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM LOG WHERE codigo >= " + valorConsulta.intValue() + " ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorNomeEntidade(String nomeEntidade, Date dataInicio, Date dataFim, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE nomeEntidade like ('%" + nomeEntidade.toUpperCase() + "%') ";
        if (dataInicio != null) {
            sqlStr += "AND dataAlteracao >=  '" + Uteis.getDataJDBC(dataInicio) + "' ";
        }
        if (dataFim != null) {
            sqlStr += "AND dataAlteracao <=  '" + Uteis.getDataJDBC(dataFim) + "' ";
        }
        sqlStr += "ORDER BY dataAlteracao";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorNomeEntidade(String nomeEntidade, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE nomeEntidade like ('%" + nomeEntidade.toUpperCase() + "%') ORDER BY dataAlteracao";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorNomeEntidadeNomeCampoChavePrimaria(String nomeEntidade, String nomeCampo, String chavePrimaria, int nivelMontarDados) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM Log WHERE Upper(nomeEntidade) Like ('%").append(nomeEntidade.toUpperCase()).append("%')");
        sql.append("AND Upper(nomeCampo) Like ('%").append(nomeCampo.toUpperCase()).append("%') ");
        sql.append("AND chavePrimaria = '").append(chavePrimaria).append("' ");
        sql.append("ORDER BY dataAlteracao DESC");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorCodigoEntidade(Integer codigoEntidade, Date dataInicio, Date dataFim, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE chavePrimaria = (" + codigoEntidade.intValue() + ") ";
        if (dataInicio != null) {
            sqlStr += "AND dataAlteracao >=  '" + Uteis.getDataJDBC(dataInicio) + "' ";
        }
        if (dataFim != null) {
            sqlStr += "AND dataAlteracao <=  '" + Uteis.getDataJDBC(dataFim) + "' ";
        }
        sqlStr += "ORDER BY dataAlteracao";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public LogVO consultarPorNomeCodigoEntidade(String nomeEntidade, Integer codigoEntidade, Date dataInicio, Date dataFim, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE nomeEntidade like ('%" + nomeEntidade + "%') and chavePrimaria = " + codigoEntidade.intValue();
        if (dataInicio != null) {
            sqlStr += "AND dataAlteracao >=  '" + Uteis.getDataJDBC(dataInicio) + "' ";
        }
        if (dataFim != null) {
            sqlStr += "AND dataAlteracao <=  '" + Uteis.getDataJDBC(dataFim) + "' ";
        }
        sqlStr += "ORDER BY dataAlteracao";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if (!tabelaResultado.next()) {
            return null;
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorNomeCodigoEntidade(String nomeEntidade, Integer codigoEntidade, Date dataInicio, Date dataFim, int nivelMontarDados) throws Exception {
        String sqlStr = "SELECT * FROM Log WHERE nomeEntidade like ('%" + nomeEntidade + "%') and chavePrimaria = '" + codigoEntidade.intValue() + "'";
        if (dataInicio != null) {
            sqlStr += " AND dataAlteracao >=  '" + Uteis.getDataJDBC(dataInicio) + "'";
        }
        if (dataFim != null) {
            sqlStr += " AND dataAlteracao <=  '" + Uteis.getDataJDBC(dataFim) + "'";
        }
        sqlStr += " ORDER BY dataAlteracao DESC";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorOpercao(String valorConsultar, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE operacao like ('" + valorConsultar.toUpperCase() + "%') ORDER BY dataAlteracao";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorResponsavel(String valorConsultar, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE responsavelAlteracao like ('" + valorConsultar.toUpperCase() + "%') ORDER BY responsavelAlteracao";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorConteudoLog(String valorConsultar, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Log WHERE valorCampoAnterior like ('" + valorConsultar + "%') and valorCampoAlterado  like ('" + valorConsultar + "%') ORDER BY valorCampoAnterior";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarAlteracoesPrecoCustoProduto(Integer codigoProduto, Integer nivelMontarDados, Integer empresa) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from log where chaveprimaria = '").append(codigoProduto).append("' and nomeentidade = 'PRODUTO' and nomecampo = 'Custo' ");
        sql.append(" order by dataalteracao desc ");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarAlteracoesProduto(Integer codigoProduto, Integer nivelMontarDados, Integer empresa) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from log where chaveprimaria = '").append(codigoProduto).append("' and nomeentidade = 'PRODUTO'");
        sql.append(" order by dataalteracao desc ");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarAlteracoesContasReceber(Integer contaReceber, Integer nivelMontarDados, Integer empresa) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from log where chaveprimaria = '").append(contaReceber).append("' and nomeentidade = 'CONTASRECEBER' ");
        sql.append(" order by dataalteracao desc ");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarAlteracoesTributacao(Integer codigoTributacao, Integer nivelMontarDados, Integer empresa) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from log where chaveprimaria = '").append(codigoTributacao).append("' and nomeentidade = 'TRIBUTACAO' ");
        sql.append(" order by dataalteracao desc ");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarAlteracoesTabelaPreco(Integer codigoTabelaPreco, Integer nivelMontarDados, Integer empresa) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from log where chaveprimaria = '").append(codigoTabelaPreco).append("' and (nomeentidade = 'TABELAPRECO' or nomeentidade = 'TABELAPRECO - ITEMTABELAPRECO')");
        sql.append(" order by dataalteracao desc ");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarAlteracoesPedidoVenda(Integer pedidoVenda, Integer nivelMontarDados, Integer empresa) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from log where chaveprimaria = '").append(pedidoVenda).append("' and (nomeentidade = 'PEDIDOVENDA - ITEMPEDIDO' OR nomeentidade = 'PEDIDOVENDA')");
        sql.append(" order by dataalteracao desc ");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    /**
     * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
     * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
     * vez.
     *
     * @return List Contendo vários objetos da classe <code>UsuarioVO</code> resultantes da consulta.
     */
    public static List montarDadosConsulta(SqlRowSet tabelaResultado, int nivelMontarDados) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado, nivelMontarDados));
        }
        return vetResultado;
    }

    /**
     * Responsável por montar os dados resultantes de uma consulta ao banco de dados (<code>ResultSet</code>) em um
     * objeto da classe <code>UsuarioVO</code>.
     *
     * @return O objeto da classe <code>UsuarioVO</code> com os dados devidamente montados.
     */
    public static LogVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        LogVO obj = new LogVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNomeEntidade(dadosSQL.getString("nomeEntidade"));
        obj.setNomeEntidadeDescricao(dadosSQL.getString("nomeEntidadeDescricao"));
        obj.setChavePrimaria(dadosSQL.getString("chavePrimaria"));
        obj.setChavePrimariaEntidadeSubordinada(dadosSQL.getString("chavePrimariaEntidadeSubordinada"));
        obj.setNomeCampo(dadosSQL.getString("nomeCampo"));
        obj.setValorCampoAnterior(dadosSQL.getString("valorCampoAnterior"));
        obj.setValorCampoAlterado(dadosSQL.getString("valorCampoAlterado"));
        obj.setDataAlteracao(dadosSQL.getTimestamp("dataAlteracao"));
        obj.setResponsavelAlteracao(dadosSQL.getString("responsavelAlteracao"));
        obj.setOperacao(dadosSQL.getString("operacao"));
        obj.setNovoObj(false);

        return obj;
    }

    /**
     * Operação responsável por localizar um objeto da classe <code>UsuarioVO</code> através de sua chave primária.
     *
     * @exception Exception
     *                Caso haja problemas de conexão ou localização do objeto procurado.
     */
    public LogVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Cidade WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Usuario ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    /**
     * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
     * permissões de acesso as operações desta classe.
     */
    public String getIdEntidade() {
        return Log.idEntidade;
    }

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
     * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
     * Como o controle de acesso é realizado com base neste identificador,
     */
    public void setIdEntidade(String idEntidade) {
        Log.idEntidade = idEntidade;
    }
}
