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
import negocio.interfaces.projeto.AtributosInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import negocio.comuns.projeto.AtributosVO;
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
public class Atributos extends SuperEntidade implements AtributosInterfaceFacade {

    protected static String idEntidade;

    public Atributos() throws Exception {
        super();
        setIdEntidade("Entidade");
    }

    public AtributosVO novo() throws Exception {
        incluir(getIdEntidade());
        AtributosVO obj = new AtributosVO();
        return obj;
    }

    public static void validarDados(AtributosVO obj) throws ConsistirException {
    }

@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final AtributosVO obj) throws Exception {
        try {
            validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Atributos( entidade, nome, nomeApresentar, tipoCampo, tipoComponente, campoObrigatorio, campoConsulta, campoDescricaoObjeto, mascara, tamanho, casasDecimais, projeto, modulo, data, usuario ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setInt(cont++, obj.getEntidade());
                    sqlInserir.setString(cont++, obj.getNome());
                    sqlInserir.setString(cont++, obj.getNomeApresentar());
                    sqlInserir.setString(cont++, obj.getTipoCampo());
                    sqlInserir.setString(cont++, obj.getTipoComponente());
                    sqlInserir.setBoolean(cont++, obj.getCampoObrigatorio());
                    sqlInserir.setBoolean(cont++, obj.getCampoConsulta());
                    sqlInserir.setBoolean(cont++, obj.getCampoDescricaoObjeto());
                    sqlInserir.setString(cont++, obj.getMascara());
                    sqlInserir.setInt(cont++, obj.getTamanho());
                    sqlInserir.setInt(cont++, obj.getCasasDecimais());
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
        } catch (Exception e) {
            obj.setNovoObj(true);
            throw e;
        }
    }
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final AtributosVO obj) throws Exception {
        try {
            validarDados(obj);
            obj.realizarUpperCase(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE Atributos set entidade=?, nome=?, nomeApresentar=?, tipoCampo=?, tipoComponente=?, campoObrigatorio=?, campoConsulta=?, campoDescricaoObjeto=?, mascara=?, tamanho=?, casasDecimais=?, projeto=?, modulo=?, data=?, usuario=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlAlterar.setInt(cont++, obj.getEntidade());
                    sqlAlterar.setString(cont++, obj.getNome());
                    sqlAlterar.setString(cont++, obj.getNomeApresentar());
                    sqlAlterar.setString(cont++, obj.getTipoCampo());
                    sqlAlterar.setString(cont++, obj.getTipoComponente());
                    sqlAlterar.setBoolean(cont++, obj.getCampoObrigatorio());
                    sqlAlterar.setBoolean(cont++, obj.getCampoConsulta());
                    sqlAlterar.setBoolean(cont++, obj.getCampoDescricaoObjeto());
                    sqlAlterar.setString(cont++, obj.getMascara());
                    sqlAlterar.setInt(cont++, obj.getTamanho());
                    sqlAlterar.setInt(cont++, obj.getCasasDecimais());
                    sqlAlterar.setInt(cont++, obj.getProjeto().getCodigo());
                    sqlAlterar.setInt(cont++, obj.getModulo().getCodigo());
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
    public void excluir(AtributosVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Atributos WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluirAtributosPorEntidade(EntidadeVO entidade) throws Exception {
        excluir(getIdEntidade());
        String sql = "DELETE FROM Atributos WHERE (entidade = ?)";
        getConexao().getJdbcTemplate().update(sql, new Object[]{entidade.getCodigo()});
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterarAtributos(EntidadeVO entidade) throws Exception {
        String sql = "DELETE FROM Atributos WHERE entidade = " + entidade.getCodigo() + " AND codigo NOT IN (0";
        for (AtributosVO atributos : entidade.getListaAtributos()) {
            sql += "," + atributos.getCodigo() + " ";
            if (atributos.getNovoObj()) {
                atributos.setEntidade(entidade.getCodigo());
                incluir(atributos);
                sql += "," + atributos.getCodigo() + " ";
            } else {
                alterar(atributos);
            }
        }
        sql += ")";
        getConexao().getJdbcTemplate().update(sql, new Object[]{});

    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluirAtributos(EntidadeVO entidade) throws Exception {
        for (AtributosVO atributos : entidade.getListaAtributos()) {
            atributos.setEntidade(entidade.getCodigo());
            incluir(atributos);
        }
    }

     public List consultarPorEntidade(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE Atributos.entidade = ").append(valorConsulta).append(" ORDER BY Atributos.entidade");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE Atributos.codigo = ").append(valorConsulta).append(" ORDER BY Atributos.codigo");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE upper( Atributos.nomeApresentar ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Atributos.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorTipoCampo(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE upper( Atributos.tipoCampo ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Atributos.tipoCampo");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorTipoComponente(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE upper( Atributos.tipoComponente ) like('").append(valorConsulta.toUpperCase()).append("%') ORDER BY Atributos.tipoComponente");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorProjeto(ProjetoVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE Atributos.projeto = ").append(valorConsulta.getCodigo()).append(" ORDER BY projeto.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

     public List consultarPorModulo(ModuloVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT Atributos.* ");
        sql.append(", projeto.nomeApresentar AS descricaoObjetoProjeto  ");
        sql.append(", modulo.nomeApresentar AS descricaoObjetoModulo  ");
        sql.append("FROM Atributos ");
        sql.append("INNER JOIN projeto projeto ON projeto.codigo = Atributos.projeto ");
        sql.append("INNER JOIN modulo modulo ON modulo.codigo = Atributos.modulo ");
        sql.append("WHERE Atributos.modulo = ").append(valorConsulta.getCodigo()).append(" ORDER BY modulo.nomeApresentar");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
     }

    public static List montarDadosConsulta(SqlRowSet tabelaResultado, int nivelMontarDados) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado, nivelMontarDados));
        }
        return vetResultado;
    }

    public static AtributosVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        AtributosVO obj = new AtributosVO();
        obj.setEntidade(dadosSQL.getInt("entidade"));
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setNomeApresentar(dadosSQL.getString("nomeApresentar"));
        obj.setTipoCampo(dadosSQL.getString("tipoCampo"));
        obj.setTipoComponente(dadosSQL.getString("tipoComponente"));
        obj.setCampoObrigatorio(dadosSQL.getBoolean("campoObrigatorio"));
        obj.setCampoConsulta(dadosSQL.getBoolean("campoConsulta"));
        obj.setCampoDescricaoObjeto(dadosSQL.getBoolean("campoDescricaoObjeto"));
        obj.setMascara(dadosSQL.getString("mascara"));
        obj.setTamanho(dadosSQL.getInt("tamanho"));
        obj.setCasasDecimais(dadosSQL.getInt("casasDecimais"));
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

    public AtributosVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Atributos WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Atributos ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public String getIdEntidade() {
        return Atributos.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        Atributos.idEntidade = idEntidade;
    }
}
