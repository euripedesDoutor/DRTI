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
import negocio.interfaces.projeto.EntidadeInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import negocio.comuns.projeto.EntidadeVO;
import negocio.comuns.projeto.ProjetoVO;
import negocio.comuns.projeto.ModuloVO;

/**
*
* @author DOCTORCODE
*/

@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Entidade extends SuperEntidade implements EntidadeInterfaceFacade {

    protected static String idEntidade;

    public Entidade() throws Exception {
        super();
        setIdEntidade("Entidade");
    }

    public EntidadeVO novo() throws Exception {
        incluir(getIdEntidade());
        EntidadeVO obj = new EntidadeVO();
        return obj;
    }

    public static void validarDados(EntidadeVO obj) throws ConsistirException {
    }

@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final EntidadeVO obj) throws Exception {
        try {
            validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Entidade( nome, nomeApresentar, projeto, modulo, data, usuario ) VALUES ( ?, ?, ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setString(cont++, obj.getNome());
                    sqlInserir.setString(cont++, obj.getNomeApresentar());
                    sqlInserir.setInt(cont++, obj.getProjeto().getCodigo());
                    sqlInserir.setInt(cont++, obj.getModulo().getCodigo());
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
            getFacadeFactory().getAtributosFacade().incluirAtributos(obj);
        } catch (Exception e) {
            obj.setNovoObj(true);
            throw e;
        }
    }
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final EntidadeVO obj) throws Exception {
        try {
            validarDados(obj);
            obj.realizarUpperCase(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE Entidade set nome=?, nomeApresentar=?, projeto=?, modulo=?, data=?, usuario=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlAlterar.setString(cont++, obj.getNome());
                    sqlAlterar.setString(cont++, obj.getNomeApresentar());
                    sqlAlterar.setInt(cont++, obj.getProjeto().getCodigo());
                    sqlAlterar.setInt(cont++, obj.getModulo().getCodigo());
                    sqlAlterar.setTimestamp(cont++, Uteis.getDataJDBCTimestamp(obj.getData()));
                    sqlAlterar.setInt(cont++, obj.getUsuario().getCodigo());
                    sqlAlterar.setInt(cont++, obj.getCodigo());
                    return sqlAlterar;
                }
            });
            getFacadeFactory().getAtributosFacade().alterarAtributos(obj);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(EntidadeVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Entidade WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
            getFacadeFactory().getAtributosFacade().excluirAtributosPorEntidade(obj);
        } catch (Exception e) {
            throw e;
        }
    }


     public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Entidade.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Entidade ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Entidade.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Entidade.modulo ");
        sql.append("WHERE Entidade.codigo = ").append(valorConsulta).append(" ORDER BY Entidade.codigo");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Entidade.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Entidade ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Entidade.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Entidade.modulo ");
        sql.append("WHERE upper( Entidade.nomeApresentar ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Entidade.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorProjeto(ProjetoVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Entidade.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Entidade ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Entidade.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Entidade.modulo ");
        sql.append("WHERE Entidade.projeto = ").append(valorConsulta.getCodigo()).append(" ORDER BY projeto.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorModulo(ModuloVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Entidade.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Entidade ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Entidade.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Entidade.modulo ");
        sql.append("WHERE Entidade.modulo = ").append(valorConsulta.getCodigo()).append(" ORDER BY modulo.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

    public static List consultarParaAutoComplete(String valorConsulta, Integer limite) throws Exception {
        String sqlStr = "SELECT * FROM Entidade  ";
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
            EntidadeVO obj = new EntidadeVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
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

    public static EntidadeVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        EntidadeVO obj = new EntidadeVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setNomeApresentar(dadosSQL.getString("nomeApresentar"));
        obj.getProjeto().setCodigo(dadosSQL.getInt("projeto"));
        obj.getModulo().setCodigo(dadosSQL.getInt("modulo"));
        obj.setData(dadosSQL.getTimestamp("data"));
        obj.getUsuario().setCodigo(dadosSQL.getInt("usuario"));
        try {
            obj.getProjeto().setNomeApresentar(dadosSQL.getString("descricaoObjetoProjeto"));
        } catch (Exception e) {
            obj.getProjeto().setNomeApresentar("");
        }
        try {
            obj.getModulo().setNomeApresentar(dadosSQL.getString("descricaoObjetoModulo"));
        } catch (Exception e) {
            obj.getModulo().setNomeApresentar("");
        }
        obj.setNovoObj(false);
        if (nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS) {
            return obj;
        }
        return obj;
    }

    public EntidadeVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Entidade WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Entidade ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public String getIdEntidade() {
        return Entidade.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        Entidade.idEntidade = idEntidade;
    }
}
