package negocio.facade.jdbc.cadastro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.SuperEntidade;
import negocio.interfaces.cadastro.PaisInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import negocio.comuns.cadastro.PaisVO;

/**
 *
 * @author DOCTORCODE
 */
@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Pais extends SuperEntidade implements PaisInterfaceFacade {

    protected static String idEntidade;

    public Pais() throws Exception {
        super();
        setIdEntidade("Pais");
    }

    public PaisVO novo() throws Exception {
        incluir(getIdEntidade());
        PaisVO obj = new PaisVO();
        return obj;
    }

    public static void validarDados(PaisVO obj) throws ConsistirException {
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final PaisVO obj) throws Exception {
        try {
            validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Pais( nome, codigoSped, codigoSiscomex ) VALUES ( ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setString(cont++, obj.getNome());
                    sqlInserir.setString(cont++, obj.getCodigoSped());
                    sqlInserir.setString(cont++, obj.getCodigoSiscomex());
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

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final PaisVO obj) throws Exception {
        try {
            validarDados(obj);
            obj.realizarUpperCase(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE Pais set nome=?, codigoSped=?, codigoSiscomex=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlAlterar.setString(cont++, obj.getNome());
                    sqlAlterar.setString(cont++, obj.getCodigoSped());
                    sqlAlterar.setString(cont++, obj.getCodigoSiscomex());
                    sqlAlterar.setInt(cont++, obj.getCodigo());
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(PaisVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Pais WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        }
    }

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Pais.* ");
        sql.append("FROM Pais ");
        sql.append("WHERE Pais.codigo = ").append(valorConsulta).append(" ORDER BY Pais.codigo");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorNome(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Pais.* ");
        sql.append("FROM Pais ");
        sql.append("WHERE upper( Pais.nome ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Pais.nome");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorCodigoSped(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Pais.* ");
        sql.append("FROM Pais ");
        sql.append("WHERE upper( Pais.codigoSped ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Pais.codigoSped");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public List consultarPorCodigoSiscomex(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Pais.* ");
        sql.append("FROM Pais ");
        sql.append("WHERE upper( Pais.codigoSiscomex ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Pais.codigoSiscomex");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public static List consultarParaAutoComplete(String valorConsulta, Integer limite) throws Exception {
        String sqlStr = "SELECT * FROM Pais  ";
        sqlStr += " WHERE ";
        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " codigo = " + codigo;
        } catch (NumberFormatException e) {
            sqlStr += " Lower( nome ) like('" + valorConsulta.toLowerCase() + "%') ";
        }
        sqlStr += " ORDER BY nome LIMIT " + limite + " ";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            PaisVO obj = new PaisVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            vetResultado.add(obj);
        }
        return vetResultado;
    }

    public static List consultarParaAutoComplete(String valorConsulta) throws Exception {

        String sqlStr = "SELECT Pais.* FROM Pais  ";
        sqlStr += " WHERE ";

        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " pais.codigo = " + codigo;
        } catch (NumberFormatException e) {
            sqlStr += " Lower( pais.nome ) like('%" + valorConsulta.toLowerCase() + "%') ";
        }

        sqlStr += " ORDER BY pais.nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            PaisVO obj = new PaisVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            obj.setCodigoSped(tabelaResultado.getString("codigoSped"));
            obj.setCodigoSiscomex(tabelaResultado.getString("codigoSiscomex"));
            vetResultado.add(obj);
        }
        return vetResultado;
    }

    public static List montarDadosConsulta(SqlRowSet tabelaResultado, int nivelMontarDados) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado, nivelMontarDados));
        }
        return vetResultado;
    }

    public static PaisVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        PaisVO obj = new PaisVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setCodigoSped(dadosSQL.getString("codigoSped"));
        obj.setCodigoSiscomex(dadosSQL.getString("codigoSiscomex"));
        obj.setNovoObj(false);
        if (nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS) {
            return obj;
        }
        return obj;
    }

    public PaisVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Pais WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Pais ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public String getIdEntidade() {
        return Pais.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        Pais.idEntidade = idEntidade;
    }
}
