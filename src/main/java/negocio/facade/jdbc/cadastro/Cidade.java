package negocio.facade.jdbc.cadastro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.comuns.cadastro.CidadeVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.SuperEntidade;
import negocio.interfaces.cadastro.CidadeInterfaceFacade;

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
 * Classe de persistência que encapsula todas as operações de manipulação dos dados da classe <code>CidadeVO</code>.
 * Responsável por implementar operações como incluir, alterar, excluir e consultar pertinentes a classe
 * <code>CidadeVO</code>. Encapsula toda a interação com o banco de dados.
 * 
 * @see CidadeVO
 * @see SuperEntidade
 */
@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Cidade extends SuperEntidade implements CidadeInterfaceFacade {

    protected static String idEntidade;

    public Cidade() throws Exception {
        super();
        setIdEntidade("Cidade");
    }

    /**
     * Operação responsável por retornar um novo objeto da classe <code>CidadeVO</code>.
     */
    public CidadeVO novo() throws Exception {
        incluir(getIdEntidade());
        CidadeVO obj = new CidadeVO();
        return obj;
    }

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>CidadeVO</code>. Primeiramente
     * valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do
     * USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>incluir</code> da superclasse.
     *
     * @param obj
     *            Objeto da classe <code>CidadeVO</code> que será gravado no banco de dados.
     * @exception Exception
     *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final CidadeVO obj) throws Exception {
        try {
            CidadeVO.validarDados(obj);
            obj.realizarUpperCase(obj);
            incluir(getIdEntidade());
            final String sql = "INSERT INTO Cidade( nome, estado, codigoIBGE, pais, codigoPais ) VALUES ( ?, ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    sqlInserir.setString(1, obj.getNome());
                    sqlInserir.setString(2, obj.getEstado());
                    sqlInserir.setString(3, obj.getCodigoIBGE());
                    sqlInserir.setInt(4, obj.getPais().getCodigo());
                    sqlInserir.setString(5, obj.getCodigoPais());
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
            obj.setCodigo(0);
            obj.setNovoObj(true);
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final CidadeVO obj) throws Exception {
        try {
            CidadeVO.validarDados(obj);
            obj.realizarUpperCase(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE Cidade set nome=?, estado=?, codigoIBGE=?, pais=?, codigoPais=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setString(1, obj.getNome());
                    sqlAlterar.setString(2, obj.getEstado());
                    sqlAlterar.setString(3, obj.getCodigoIBGE());
                    sqlAlterar.setInt(4, obj.getPais().getCodigo());
                    sqlAlterar.setString(5, obj.getCodigoPais());
                    sqlAlterar.setInt(6, obj.getCodigo().intValue());
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }


    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(CidadeVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Cidade WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Responsável por realizar uma consulta de <code>Cidade</code> através do valor do atributo
     * <code>String estado</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>CidadeVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorEstado(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE lower( estado ) like(?) ORDER BY estado";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta.toLowerCase() + "%"});
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarPorEstado_OrdernarPorCidade(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE lower( estado ) like(?) ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta.toLowerCase() + "%"});
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarParaRelatorio(String valorConsulta, boolean controlarAcesso) throws Exception {
        emitirRelatorio("CidadeRel");
        String sqlStr = "SELECT * FROM Cidade WHERE lower( estado ) like(?) ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta.toLowerCase() + "%"});
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarPorCodigoIBGE(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE lower( codigoIBGE ) like(?) ORDER BY codigoIBGE";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta.toLowerCase() + "%"});
        return (montarDadosConsulta(tabelaResultado));
    }

    public CidadeVO consultarPorCodigoExatoIBGE(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE  codigoIBGE = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta});
        if (tabelaResultado.next()) {
            return (montarDados(tabelaResultado));
        } else {
            throw new Exception();
        }
//        return new CidadeVO();
    }

    /**
     * Responsável por realizar uma consulta de <code>Cidade</code> através do valor do atributo
     * <code>String nome</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>CidadeVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorNome(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE lower( nome ) like(?) ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta.toLowerCase() + "%"});
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }
    public List consultarPorNome(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE lower( nome ) like(?) ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta.toLowerCase() + "%"});
        return (montarDadosConsulta(tabelaResultado));
    }

    /**
     * Responsável por realizar uma consulta de <code>Cidade</code> através do valor do atributo
     * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido. Faz uso
     * da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>CidadeVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Cidade WHERE codigo = ? ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{valorConsulta});
        return (montarDadosConsulta(tabelaResultado));
    }

    /**
     * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
     * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
     * vez.
     *
     * @return List Contendo vários objetos da classe <code>CidadeVO</code> resultantes da consulta.
     */
    public static List montarDadosConsulta(SqlRowSet tabelaResultado, int nivelMontarDados) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado, nivelMontarDados));
        }
        return vetResultado;
    }

    public static List montarDadosConsulta(SqlRowSet tabelaResultado) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado));
        }
        return vetResultado;
    }

    /**
     * Responsável por montar os dados resultantes de uma consulta ao banco de dados (<code>ResultSet</code>) em um
     * objeto da classe <code>CidadeVO</code>.
     *
     * @return O objeto da classe <code>CidadeVO</code> com os dados devidamente montados.
     */
    public static CidadeVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        CidadeVO obj = new CidadeVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setEstado(dadosSQL.getString("estado"));
        obj.setCodigoIBGE(dadosSQL.getString("codigoIBGE"));
        obj.setCodigoPais(dadosSQL.getString("codigoPais"));
        obj.getPais().setCodigo(dadosSQL.getInt("pais"));
        obj.setNovoObj(false);
        if(nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS){
            return obj;
        }
        montarDadosPais(obj);
        return obj;
    }

    public static CidadeVO montarDados(SqlRowSet dadosSQL) throws Exception {
        CidadeVO obj = new CidadeVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setEstado(dadosSQL.getString("estado"));
        obj.setCodigoIBGE(dadosSQL.getString("codigoIBGE"));
        obj.setCodigoPais(dadosSQL.getString("codigoPais"));
        obj.getPais().setCodigo(dadosSQL.getInt("pais"));
        obj.setNovoObj(false);
        montarDadosPais(obj);
        return obj;
    }

    public static void montarDadosPais(CidadeVO obj) throws Exception {
        if (obj.getPais().getCodigo() > 0) {
            obj.setPais(getFacadeFactory().getPaisFacade().consultarPorChavePrimaria(obj.getPais().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        }
    }

    /**
     * Operação responsável por localizar um objeto da classe <code>CidadeVO</code> através de sua chave primária.
     *
     * @exception Exception
     *                Caso haja problemas de conexão ou localização do objeto procurado.
     */
    public CidadeVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Cidade WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Cidade ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public CidadeVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Cidade WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Cidade ).");
        }
        return (montarDados(tabelaResultado));
    }

    public static List consultarParaAutoComplete(String valorConsulta, Integer limite, Integer empresa) throws Exception {

        List filtros = new ArrayList(0);
        String sqlStr = "SELECT * FROM Cidade WHERE ";

        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " codigo = ?";
            filtros.add(codigo);
        } catch (NumberFormatException e) {
            sqlStr += " Lower( nome ) like(?) ";
            filtros.add("%" + valorConsulta.toLowerCase() + "%");
        }
        sqlStr += " ORDER BY nome ";
        if (limite != 0) {
            sqlStr += "LIMIT ?";
            filtros.add(limite);
        }

        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, filtros.toArray());
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            CidadeVO obj = new CidadeVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            obj.setEstado(tabelaResultado.getString("estado"));
            vetResultado.add(obj);
        }
        return vetResultado;
    }

    public static List consultarParaAutoCompleteFiltroEstado(String valorConsulta, String estado) throws Exception {

        List<Object> filtros = new ArrayList(0);
        String sqlStr = "SELECT * FROM Cidade WHERE ";

        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " codigo = ?" + codigo;
            filtros.add(codigo);
        } catch (NumberFormatException e) {
            sqlStr += " Lower( nome ) like(?) ";
            filtros.add("%" + valorConsulta.toLowerCase() + "%");
        }
        if (!estado.equals("")) {
            sqlStr += " AND estado = ? ";
            filtros.add(estado);
        }else{
        }
        sqlStr += " ORDER BY nome ";


        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, filtros.toArray());
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            CidadeVO obj = new CidadeVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            obj.setEstado(tabelaResultado.getString("estado"));
            vetResultado.add(obj);
        }
        return vetResultado;
    }

    /**
     * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
     * permissões de acesso as operações desta classe.
     */
    public String getIdEntidade() {
        return Cidade.idEntidade;
    }

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
     * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
     * Como o controle de acesso é realizado com base neste identificador,
     */
    public void setIdEntidade(String idEntidade) {
        Cidade.idEntidade = idEntidade;
    }
}
