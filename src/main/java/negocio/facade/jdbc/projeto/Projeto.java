package negocio.facade.jdbc.projeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.SuperEntidade;
import negocio.interfaces.projeto.ProjetoInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import negocio.comuns.projeto.ProjetoVO;

/**
*
* @author DOCTORCODE
*/

@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Projeto extends SuperEntidade implements ProjetoInterfaceFacade {

    protected static String idEntidade;

    public Projeto() throws Exception {
        super();
        setIdEntidade("Projeto");
    }

    public ProjetoVO novo() throws Exception {
        incluir(getIdEntidade());
        ProjetoVO obj = new ProjetoVO();
        return obj;
    }

    public static void validarDados(ProjetoVO obj) throws ConsistirException {
    }

@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final ProjetoVO obj) throws Exception {
        try {
            validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Projeto( nome, nomeApresentar, url, backend, frontend, data, usuario ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setString(cont++, obj.getNome());
                    sqlInserir.setString(cont++, obj.getNomeApresentar());
                    sqlInserir.setString(cont++, obj.getUrl());
                    sqlInserir.setString(cont++, obj.getBackend());
                    sqlInserir.setString(cont++, obj.getFrontend());
                    sqlInserir.setTimestamp(cont++, Uteis.getDataJDBCTimestamp(obj.getData()));
                    sqlInserir.setInt(cont++, obj.getUsuario().getCodigo());
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
            getFacadeFactory().getModuloFacade().incluirModulo(obj);
        } catch (Exception e) {
            obj.setNovoObj(true);
            throw e;
        }
    }
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final ProjetoVO obj) throws Exception {
        try {
            validarDados(obj);
            obj.realizarUpperCase(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE Projeto set nome=?, nomeApresentar=?, url=?, backend=?, frontend=?, data=?, usuario=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlAlterar.setString(cont++, obj.getNome());
                    sqlAlterar.setString(cont++, obj.getNomeApresentar());
                    sqlAlterar.setString(cont++, obj.getUrl());
                    sqlAlterar.setString(cont++, obj.getBackend());
                    sqlAlterar.setString(cont++, obj.getFrontend());
                    sqlAlterar.setTimestamp(cont++, Uteis.getDataJDBCTimestamp(obj.getData()));
                    sqlAlterar.setInt(cont++, obj.getUsuario().getCodigo());
                    sqlAlterar.setInt(cont++, obj.getCodigo());
                    return sqlAlterar;
                }
            });
            getFacadeFactory().getModuloFacade().alterarModulo(obj);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(ProjetoVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Projeto WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
            getFacadeFactory().getModuloFacade().excluirModuloPorProjeto(obj);
        } catch (Exception e) {
            throw e;
        }
    }


     public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Projeto.* ");
        sql.append("FROM Projeto ");
        sql.append("WHERE Projeto.codigo = ").append(valorConsulta).append(" ORDER BY Projeto.codigo");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Projeto.* ");
        sql.append("FROM Projeto ");
        sql.append("WHERE upper( Projeto.nomeApresentar ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Projeto.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

    public static List consultarParaAutoComplete(String valorConsulta, Integer limite) throws Exception {
        String sqlStr = "SELECT * FROM Projeto  ";
        sqlStr += " WHERE ";
        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " codigo = " + codigo;
        } catch (NumberFormatException e) {
            sqlStr += " Lower( nomeApresentar ) like('" + valorConsulta.toLowerCase() + "%') ";
        }
        sqlStr += " ORDER BY nomeApresentar LIMIT " + limite + " ";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            ProjetoVO obj = new ProjetoVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            obj.setNomeApresentar(tabelaResultado.getString("nomeApresentar"));
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

    public static ProjetoVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        ProjetoVO obj = new ProjetoVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setNomeApresentar(dadosSQL.getString("nomeApresentar"));
        obj.setUrl(dadosSQL.getString("url"));
        obj.setBackend(dadosSQL.getString("backend"));
        obj.setFrontend(dadosSQL.getString("frontend"));
        obj.setData(dadosSQL.getTimestamp("data"));
        obj.getUsuario().setCodigo(dadosSQL.getInt("usuario"));
        obj.setNovoObj(false);
        if (nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS) {
            return obj;
        }
        return obj;
    }

    public ProjetoVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Projeto WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Projeto ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public String getIdEntidade() {
        return Projeto.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        Projeto.idEntidade = idEntidade;
    }
}
