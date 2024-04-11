package negocio.facade.jdbc.arquitetura;

import negocio.comuns.arquitetura.UsuarioVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.interfaces.arquitetura.UsuarioInterfaceFacade;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Usuario extends SuperEntidade implements UsuarioInterfaceFacade {

    protected static String idEntidade;

    public Usuario() throws Exception {
        super();
        setIdEntidade("Usuario");
    }

    public UsuarioVO novo() throws Exception {
        incluir(getIdEntidade());
        UsuarioVO obj = new UsuarioVO();
        return obj;
    }

   @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final UsuarioVO obj) throws Exception {
        try {
            UsuarioVO.validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Usuario( nome, username, senha, mostrarMensagemBloqueioSistema, bloquearAcesso, dataExpiracaoSenha, usuarioPeriodoAcesso ) " +
                    "VALUES ( ?, ?, ?, ?, ?, ?, ?) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont=1;
                    sqlInserir.setString(cont++, obj.getNome().trim());
                    sqlInserir.setString(cont++, obj.getUsername().trim());
                    try {
                        sqlInserir.setString(cont++, Uteis.encriptar(obj.getSenha()));
                    } catch (UnsupportedEncodingException e) {
                        throw new SQLException(e.getMessage());
                    }
                    sqlInserir.setBoolean(cont++, obj.getMostrarMensagemBloqueioSistema());
                    sqlInserir.setBoolean(cont++, obj.getBloquearAcesso());
                    sqlInserir.setDate(cont++, Uteis.getDataJDBC(obj.getDataExpiracaoSenha()));
                    sqlInserir.setString(cont++, obj.getUsuarioPeriodoAcesso());
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
            getFacadeFactory().getUsuarioPerfilAcessoFacade().incluirUsuarioPerfilAcesso(obj.getCodigo(), obj.getUsuarioPerfilAcessoVOs());
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final UsuarioVO obj) throws Exception {
        try {
            UsuarioVO.validarDados(obj);
            alterar(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "UPDATE Usuario set nome=?, username=?, mostrarMensagemBloqueioSistema=?, bloquearAcesso=?, dataExpiracaoSenha=?, usuarioPeriodoAcesso=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont=1;
                    sqlAlterar.setString(cont++, obj.getNome().trim());
                    sqlAlterar.setString(cont++, obj.getUsername().trim());
                    sqlAlterar.setBoolean(cont++, obj.getMostrarMensagemBloqueioSistema());
                    sqlAlterar.setBoolean(cont++, obj.getBloquearAcesso());
                    sqlAlterar.setString(cont++, obj.getUsuarioPeriodoAcesso());
                    sqlAlterar.setInt(cont++, obj.getCodigo().intValue());

                    return sqlAlterar;
                }
            });
            getFacadeFactory().getUsuarioPerfilAcessoFacade().alterarUsuarioPerfilAcesso(obj.getCodigo(), obj.getUsuarioPerfilAcessoVOs());
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterarSenha(final UsuarioVO obj, Boolean validarSenhaAnterior) throws Exception {
        try {
            if(validarSenhaAnterior) {
                if (validarSenhaJaUtilizadaAnteriormente(obj)) {
                    throw new Exception("Senha já utilizada anteriormente");
                }
            }
            alterarHistoricoSenha(obj);
            final String sql = "UPDATE Usuario set senha=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    try {
                        sqlAlterar.setString(1, Uteis.encriptar(obj.getSenha()));
                    } catch (UnsupportedEncodingException e) {
                        throw new SQLException(e.getMessage());
                    }
                    sqlAlterar.setInt(2, obj.getCodigo().intValue());
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    private Boolean validarSenhaJaUtilizadaAnteriormente(UsuarioVO obj) throws Exception {
        String sqlStr = "SELECT CASE WHEN COUNT(codigo) >= 1 THEN TRUE ELSE FALSE END AS utilizacao FROM USUARIO \n" +
                "WHERE codigo = ? \n" +
                "AND (senhaAnterior1 = ?\n" +
                "OR senhaAnterior2 = ?\n" +
                "OR senhaAnterior3 = ?\n" +
                "OR senhaAnterior4 = ?\n" +
                "OR senha = ?)";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{obj.getCodigo(), Uteis.encriptar(obj.getSenha()), Uteis.encriptar(obj.getSenha()), Uteis.encriptar(obj.getSenha()), Uteis.encriptar(obj.getSenha()), Uteis.encriptar(obj.getSenha())});
        if(tabelaResultado.next()){
            return tabelaResultado.getBoolean("utilizacao");
        }
        return false;
    }

    private void alterarHistoricoSenha(UsuarioVO obj) throws Exception {
        String sqlStr = "UPDATE USUARIO \n" +
                "SET senhaAnterior4 = senhaAnterior3, \n" +
                "senhaAnterior3 = senhaAnterior2, \n" +
                "senhaAnterior2 = senhaAnterior1, \n" +
                "senhaAnterior1 = senha \n" +
                "WHERE codigo = ? \n";

        getConexao().getJdbcTemplate().update(sqlStr, new Object[]{obj.getCodigo()});
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void bloquearUsuario(final String username) throws Exception {
        try {
            final String sql = "UPDATE Usuario set bloquearAcesso = TRUE WHERE ((username = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setString(1, username);
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static void alterarDataExpiracaoSenha(final Integer usuario, final Date dataExpiracaoSenha) throws Exception {
        try {
            final String sql = "UPDATE Usuario set dataExpiracaoSenha = ? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setDate(1, Uteis.getDataJDBC(dataExpiracaoSenha));
                    sqlAlterar.setInt(2, usuario);
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static void alterarUsuarioPeriodoacesso(final Integer usuario, final String usuarioPeriodoAcessoString) throws Exception {
        try {
            final String sql = "UPDATE Usuario set usuarioPeriodoAcesso = ? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setString(1, usuarioPeriodoAcessoString);
                    sqlAlterar.setInt(2, usuario);
                    return sqlAlterar;
                }
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public static String consultarUsuarioPeriodoAcesso(Integer usuario) throws Exception {
        String sqlStr = "SELECT usuarioPeriodoAcesso FROM Usuario WHERE codigo = ? ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{usuario});
        if(tabelaResultado.next()){
            return tabelaResultado.getString("usuarioPeriodoAcessoString");
        }
        return "";
    }

    public static Boolean consultarSeUsuarioBloqueado(String username) throws Exception {
        String sqlStr = "SELECT bloquearAcesso FROM Usuario WHERE username = ? ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr, new Object[]{username});
        if(tabelaResultado.next()){
            return tabelaResultado.getBoolean("bloquearAcesso");
        }
        throw new Exception("Usuário não encontrado");
    }


    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(UsuarioVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Usuario WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
            getFacadeFactory().getUsuarioPerfilAcessoFacade().excluirUsuarioPerfilAcesso(obj.getCodigo());
        } catch (Exception e) {
            throw e;
        }
    }

    public static void alterarDataUltimoAcesso(Integer usuario) throws Exception {
        try {
            String sql = "UPDATE usuario SET dataUltimoAcesso = now() WHERE codigo = ?;";
            getConexao().getJdbcTemplate().update(sql, new Object[]{usuario});
        } catch (Exception e) {
            throw e;
        }
    }

    public List consultarPorNomePerfilAcesso(String valorConsulta) throws Exception {
        consultar(getIdEntidade(), true);
//        String sqlStr = "SELECT Usuario.* FROM Usuario, PerfilAcesso WHERE Usuario.codPerfilAcesso = PerfilAcesso.codigo and Lower( PerfilAcesso.nome ) like('" + valorConsulta.toLowerCase()
//                + "%') ORDER BY PerfilAcesso.nome";
//
        StringBuilder sql = new StringBuilder();

        //sql.append("SELECT Usuario.codigo AS codigousuario, Usuario.nome AS nomeusuario, Usuario.username ");
        sql.append("SELECT Usuario.*");
        sql.append("FROM Usuario ");
        sql.append("INNER JOIN UsuarioPerfilAcesso ON usuario.codigo = UsuarioPerfilAcesso.usuario ");
        sql.append("INNER JOIN PerfilAcesso ON UsuarioPerfilAcesso.perfilAcesso = PerfilAcesso.codigo ");
        sql.append("WHERE Lower(perfilacesso.nome) like('").append(valorConsulta).append("') ORDER BY Usuario.codigo ");

        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado);
    }

    public List consultarPorCodigoPerfilAcesso(Integer valorConsulta, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), true);
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT Usuario.* ");
        sql.append("FROM Usuario ");
        sql.append("INNER JOIN UsuarioPerfilAcesso ON UsuarioPerfilAcesso.usuario = usuario.codigo ");
        sql.append("INNER JOIN PerfilAcesso ON PerfilAcesso.codigo = UsuarioPerfilAcesso.perfilAcesso ");
        sql.append("WHERE perfilAcesso.codigo = ").append(valorConsulta).append(" ");
        sql.append("GROUP BY usuario.codigo, usuario.username, usuario.senha, usuario.nome ");
        sql.append("ORDER BY Usuario.nome ");

        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarPorSituacaoAcesso(String situacaoAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), true);
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT Usuario.*");
        sql.append("FROM Usuario ");
        sql.append("WHERE bloquearAcesso = ").append(situacaoAcesso.equals("ATIVO") ? "false" : "true").append(" ORDER BY Usuario.nome ");

        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        return montarDadosConsulta(tabelaResultado, nivelMontarDados);
    }

    public List consultarPorNomePerfilAcessoRichModalPanel(String valorConsulta) throws Exception {
        consultar(getIdEntidade(), true);

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT DISTINCT(Usuario.codigo), Usuario.nome, Usuario.username, Usuario.dataUltimoAcesso ");
        sql.append(" FROM Usuario ");
        sql.append(" INNER JOIN UsuarioPerfilAcesso ON usuario.codigo = UsuarioPerfilAcesso.usuario ");
        sql.append(" INNER JOIN PerfilAcesso ON UsuarioPerfilAcesso.perfilAcesso = PerfilAcesso.codigo ");

        sql.append(" WHERE Lower(perfilacesso.nome) like('").append(valorConsulta.toLowerCase()).append("') AND NOT Usuario.bloquearAcesso ORDER BY Usuario.codigo ");

        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());

        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            UsuarioVO usuario = new UsuarioVO();
            usuario.setCodigo(tabelaResultado.getInt("codigo"));
            usuario.setNome(tabelaResultado.getString("nome"));
            usuario.setUsername(tabelaResultado.getString("username"));
            usuario.setDataUltimoAcesso(tabelaResultado.getDate("dataUltimoAcesso"));
            vetResultado.add(usuario);
        }

        return vetResultado;
    }

    public List consultarPorUsername(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Usuario WHERE Lower( username ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY username";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public static UsuarioVO consultarPorSenhaUsuarioAdministrador(String senha) throws Exception {
        String sqlStr = "SELECT * FROM Usuario "
                + "LEFT JOIN usuarioperfilacesso ON usuario.codigo = usuarioperfilacesso.usuario "
                + "LEFT JOIN perfilacesso ON usuarioperfilacesso.perfilacesso = perfilacesso.codigo "
                + " WHERE senha = '" + Uteis.encriptar(senha) + "' AND perfilacesso.administrador IS TRUE";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if (tabelaResultado.next()) {
            return (montarDados(tabelaResultado));
        }
        return null;
    }

    public UsuarioVO consultarUsuarioPorUsername(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Usuario WHERE Lower( username ) = '" + valorConsulta.toLowerCase() + "' LIMIT 1";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        tabelaResultado.next();
        return (montarDados(tabelaResultado));
    }

    public static Integer consultarCodigoUsuarioPorUsername(String username) throws Exception {
        String sqlStr = "SELECT codigo FROM Usuario WHERE Lower( username ) = '" + username.toLowerCase() + "' LIMIT 1";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if (tabelaResultado.next()) {
            return tabelaResultado.getInt("codigo");
        }else{
            return 0;
        }
    }

    public List consultarPorNome(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Usuario WHERE Lower( nome ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public static List consultarParaAutoComplete(String valorConsulta, Integer limite) throws Exception {

        String sqlStr = "SELECT Usuario.codigo, Usuario.nome FROM Usuario  ";
        sqlStr += " WHERE ";

        try {
            Integer codigo = Integer.parseInt(valorConsulta);
            sqlStr += " Usuario.codigo = " + codigo;
        } catch (NumberFormatException e) {
            sqlStr += " Lower(Usuario.nome) like('" + valorConsulta.toLowerCase() + "%') ";
        }
        sqlStr += " ORDER BY Usuario.nome LIMIT " + limite;
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        List usuariosVO = new ArrayList(0);
        while (tabelaResultado.next()) {
            UsuarioVO obj = new UsuarioVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            usuariosVO.add(obj);
        }
        return usuariosVO;
    }

    public List consultarUsuariosEmissaoNotaFiscal() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.codigo , u.nome FROM usuario u ");
        sql.append("INNER JOIN notafiscal nf ON nf.usuario = u.codigo ");
        sql.append("GROUP BY u.nome, u.codigo ");
        sql.append("ORDER BY u.nome");
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql.toString());
        List listaResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            UsuarioVO obj = new UsuarioVO();
            obj.setCodigo(tabelaResultado.getInt("codigo"));
            obj.setNome(tabelaResultado.getString("nome"));
            listaResultado.add(obj);
        }
        return listaResultado;
    }

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Usuario WHERE codigo = " + valorConsulta.intValue() + " ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado, nivelMontarDados));
    }

    public static List montarDadosConsulta(SqlRowSet tabelaResultado) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado));
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

    public static UsuarioVO montarDados(SqlRowSet dadosSQL) throws Exception {
        UsuarioVO obj = new UsuarioVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setUsername(dadosSQL.getString("username"));
        obj.setSenha(dadosSQL.getString("senha"));
        obj.setMostrarMensagemBloqueioSistema(dadosSQL.getBoolean("mostrarMensagemBloqueioSistema"));
        obj.setBloquearAcesso(dadosSQL.getBoolean("bloquearAcesso"));
        obj.setDataUltimoAcesso(dadosSQL.getTimestamp("dataUltimoAcesso"));
        obj.setDataExpiracaoSenha(dadosSQL.getDate("dataExpiracaoSenha"));
        obj.setUsuarioPeriodoAcesso(dadosSQL.getString("usuarioPeriodoAcesso"));
        obj.setNovoObj(false);
        obj.setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(obj.getCodigo()));
        return obj;
    }

    public static UsuarioVO montarDados(SqlRowSet dadosSQL, Integer nivelMontarDados) throws Exception {
        UsuarioVO obj = new UsuarioVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setUsername(dadosSQL.getString("username"));
        obj.setSenha(dadosSQL.getString("senha"));
        obj.setMostrarMensagemBloqueioSistema(dadosSQL.getBoolean("mostrarMensagemBloqueioSistema"));
        obj.setBloquearAcesso(dadosSQL.getBoolean("bloquearAcesso"));
        obj.setDataUltimoAcesso(dadosSQL.getTimestamp("dataUltimoAcesso"));
        obj.setDataExpiracaoSenha(dadosSQL.getDate("dataExpiracaoSenha"));
        obj.setUsuarioPeriodoAcesso(dadosSQL.getString("usuarioPeriodoAcesso"));
        obj.setNovoObj(false);
        if (nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS) {
            return obj;
        }
        obj.setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(obj.getCodigo()));
        return obj;
    }

    public static UsuarioVO montarDados2(SqlRowSet dadosSQL) throws Exception {
        UsuarioVO obj = new UsuarioVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setNome(dadosSQL.getString("nome"));
        obj.setUsername(dadosSQL.getString("username"));
        obj.setSenha(dadosSQL.getString("senha"));
        obj.setMostrarMensagemBloqueioSistema(dadosSQL.getBoolean("mostrarMensagemBloqueioSistema"));
        obj.setBloquearAcesso(dadosSQL.getBoolean("bloquearAcesso"));
        obj.setDataUltimoAcesso(dadosSQL.getTimestamp("dataUltimoAcesso"));
        obj.setDataExpiracaoSenha(dadosSQL.getDate("dataExpiracaoSenha"));
        obj.setUsuarioPeriodoAcesso(dadosSQL.getString("usuarioPeriodoAcesso"));
        obj.setNovoObj(false);
        //obj.setUsuarioPerfilAcessoVOs(getFacadeFactory().getUsuarioPerfilAcessoFacade().consultarUsuarioPerfilAcesso(obj.getCodigo()));
        obj.setFavoritos(getFacadeFactory().getUsuarioFavoritosFacade().consultarUsuarioFavoritos(obj.getCodigo()));
        return obj;
    }

    public UsuarioVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Usuario WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Usuario ).");
        }
        return (montarDados(tabelaResultado));
    }

    public UsuarioVO consultarPorChavePrimaria(Integer codigoPrm, Integer nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Usuario WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Usuario ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public static UsuarioVO consultarUsuarioSYSTEM() throws Exception {
        String sql = "SELECT * FROM Usuario WHERE username = 'SYSTEM'";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Usuario ).");
        }
        return (montarDados(tabelaResultado));
    }

    public static void criarUsuarioSYSTEM() throws Exception {
        String sql = "INSERT INTO Usuario (nome, username, senha) VALUES ('Sistema', 'SYSTEM', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');";
        getConexao().getJdbcTemplate().execute(sql);
    }

    public String getIdEntidade() {
        return Usuario.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        Usuario.idEntidade = idEntidade;
    }
}
