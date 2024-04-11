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
import negocio.interfaces.projeto.ModuloInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import negocio.comuns.projeto.ModuloVO;
import negocio.comuns.projeto.ProjetoVO;

/**
*
* @author DOCTORCODE
*/

@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Modulo extends SuperEntidade implements ModuloInterfaceFacade {

    protected static String idEntidade;

    public Modulo() throws Exception {
        super();
        setIdEntidade("Projeto");
    }

    public ModuloVO novo() throws Exception {
        incluir(getIdEntidade());
        ModuloVO obj = new ModuloVO();
        return obj;
    }

    public static void validarDados(ModuloVO obj) throws ConsistirException {
    }

@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final ModuloVO obj) throws Exception {
        try {
            validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Modulo( projeto, nome, nomeApresentar, data, usuario ) VALUES ( ?, ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setInt(cont++, obj.getProjeto());
                    sqlInserir.setString(cont++, obj.getNome());
                    sqlInserir.setString(cont++, obj.getNomeApresentar());
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
        } catch (Exception e) {
            obj.setNovoObj(true);
            throw e;
        }
    }
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final ModuloVO obj) throws Exception {
        try {
            validarDados(obj);
            obj.realizarUpperCase(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE Modulo set projeto=?, nome=?, nomeApresentar=?, data=?, usuario=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlAlterar.setInt(cont++, obj.getProjeto());
                    sqlAlterar.setString(cont++, obj.getNome());
                    sqlAlterar.setString(cont++, obj.getNomeApresentar());
                    sqlAlterar.setTimestamp(cont++, Uteis.getDataJDBCTimestamp(obj.getData()));
                    sqlAlterar.setInt(cont++, obj.getUsuario().getCodigo());
                    sqlAlterar.setInt(cont++, obj.getCodigo());
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(ModuloVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Modulo WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluirModuloPorProjeto(ProjetoVO projeto) throws Exception {
        excluir(getIdEntidade());
        String sql = "DELETE FROM Modulo WHERE (projeto = ?)";
        getConexao().getJdbcTemplate().update(sql, new Object[]{projeto.getCodigo()});
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterarModulo(ProjetoVO projeto) throws Exception {
        String sql = "DELETE FROM Modulo WHERE projeto = " + projeto.getCodigo() + " AND codigo NOT IN (0";
        for (ModuloVO modulo : projeto.getListaModulo()) {
            sql += "," + modulo.getCodigo() + " ";
            if (modulo.getNovoObj()) {
                modulo.setProjeto(projeto.getCodigo());
                incluir(modulo);
                sql += "," + modulo.getCodigo() + " ";
            } else {
                alterar(modulo);
            }
        }
        sql += ")";
        getConexao().getJdbcTemplate().update(sql, new Object[]{});

    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluirModulo(ProjetoVO projeto) throws Exception {
        for (ModuloVO modulo : projeto.getListaModulo()) {
            modulo.setProjeto(projeto.getCodigo());
            incluir(modulo);
        }
    }

     public List consultarPorProjeto(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Modulo.* ");
        sql.append("FROM Modulo ");
        sql.append("WHERE Modulo.projeto = ").append(valorConsulta).append(" ORDER BY Modulo.projeto");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Modulo.* ");
        sql.append("FROM Modulo ");
        sql.append("WHERE Modulo.codigo = ").append(valorConsulta).append(" ORDER BY Modulo.codigo");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Modulo.* ");
        sql.append("FROM Modulo ");
        sql.append("WHERE upper( Modulo.nomeApresentar ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Modulo.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorNomeApresentar(String valorConsulta, Integer projeto, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Modulo.* ");
        sql.append("FROM Modulo ");
        sql.append("WHERE upper( Modulo.nomeApresentar ) like('").append(valorConsulta.toUpperCase()).append("%') ");
        sql.append(" AND Modulo.projeto = ").append(projeto).append(" ");
        sql.append(" ORDER BY Modulo.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }


    public static List consultarParaAutoComplete(String valorConsulta, Integer limite) throws Exception {
        String sqlStr = "SELECT * FROM Modulo  ";
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
            ModuloVO obj = new ModuloVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            obj.setNomeApresentar(tabelaResultado.getString("nomeApresentar"));
            vetResultado.add(obj);
        }
        return vetResultado;
    }

    public static List consultarParaAutoComplete(String valorConsulta, Integer projeto, Integer limite) throws Exception {
        String sqlStr = "SELECT * FROM Modulo  ";
        sqlStr += " WHERE ";
        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " codigo = " + codigo;
        } catch (NumberFormatException e) {
            sqlStr += " Lower( nomeApresentar ) like('" + valorConsulta.toLowerCase() + "%') ";
        }
        sqlStr += " AND projeto = " + projeto;
        sqlStr += " ORDER BY nomeApresentar LIMIT " + limite + " ";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            ModuloVO obj = new ModuloVO();
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

    public static ModuloVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        ModuloVO obj = new ModuloVO();
        obj.setProjeto(dadosSQL.getInt("projeto"));
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setNomeApresentar(dadosSQL.getString("nomeApresentar"));
        obj.setData(dadosSQL.getTimestamp("data"));
        obj.getUsuario().setCodigo(dadosSQL.getInt("usuario"));
        obj.setNovoObj(false);
        if (nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS) {
            return obj;
        }
        return obj;
    }

    public ModuloVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Modulo WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Modulo ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public String getIdEntidade() {
        return Modulo.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        Modulo.idEntidade = idEntidade;
    }
}
