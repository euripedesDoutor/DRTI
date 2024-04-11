package negocio.facade.jdbc.cadastro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.SuperEntidade;
import negocio.interfaces.cadastro.ConfiguracaoSistemaInterfaceFacade;
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
 * <code>ConfiguracaoSistemaVO</code>. Responsável por implementar operações como incluir, alterar, excluir e consultar
 * pertinentes a classe <code>ConfiguracaoSistemaVO</code>. Encapsula toda a interação com o banco de dados.
 *
 * @see ConfiguracaoSistemaVO
 * @see SuperEntidade
 */
@Repository
@Lazy
public class ConfiguracaoSistema extends SuperEntidade implements ConfiguracaoSistemaInterfaceFacade {

    protected static String idEntidade;

    public ConfiguracaoSistema() throws Exception {
        super();
        setIdEntidade("ConfiguracaoSistema");
    }

    public ConfiguracaoSistemaVO novo() throws Exception {
        incluir(getIdEntidade());
        ConfiguracaoSistemaVO obj = new ConfiguracaoSistemaVO();
        return obj;
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final ConfiguracaoSistemaVO obj) throws Exception {
        try {
            ConfiguracaoSistemaVO.validarDados(obj);
            incluir(getIdEntidade());
            final String sql = "INSERT INTO ConfiguracaoSistema( empresa, ImagemBanner, ImagemRelatorio, segurancaTamanhoSenhaUsuario, segurancaPrazoExpiracaoSenha, segurancaTentativasAcessoAntesBloqueio, segurancaHorasParaZerarTentativasAcesso, segurancaSenhaExigirLetraMaiuscula, segurancaSenhaExigirNumero, segurancaSenhaExigirCaractereEspecial ) "
                    + "VALUES ("
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning codigo";

            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setInt(cont++, obj.getEmpresa().getCodigo().intValue());
                    sqlInserir.setBytes(cont++, obj.getImagemBanner());
                    sqlInserir.setBytes(cont++, obj.getImagemRelatorio());
                    sqlInserir.setInt(cont++, obj.getSegurancaTamanhoSenhaUsuario());
                    sqlInserir.setInt(cont++, obj.getSegurancaPrazoExpiracaoSenha());
                    sqlInserir.setInt(cont++, obj.getSegurancaTentativasAcessoAntesBloqueio());
                    sqlInserir.setInt(cont++, obj.getSegurancaHorasParaZerarTentativasAcesso());
                    sqlInserir.setBoolean(cont++, obj.getSegurancaSenhaExigirLetraMaiuscula());
                    sqlInserir.setBoolean(cont++, obj.getSegurancaSenhaExigirNumero());
                    sqlInserir.setBoolean(cont++, obj.getSegurancaSenhaExigirCaractereEspecial());
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
        } finally {
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterar(final ConfiguracaoSistemaVO obj) throws Exception {
        try {
            ConfiguracaoSistemaVO.validarDados(obj);
            alterar(getIdEntidade());
            final String sql = "UPDATE ConfiguracaoSistema set empresa=?, ImagemBanner=?, ImagemRelatorio=?, segurancaTamanhoSenhaUsuari=?, " +
                    "segurancaPrazoExpiracaoSenha=?, segurancaTentativasAcessoAntesBloqueio=?, segurancaHorasParaZerarTentativasAcesso=?, " +
                    "segurancaSenhaExigirLetraMaiuscula=?, segurancaSenhaExigirNumero=?, segurancaSenhaExigirCaractereEspecial=? WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlAlterar.setInt(cont++, obj.getEmpresa().getCodigo().intValue());
                    sqlAlterar.setBytes(cont++, obj.getImagemBanner());
                    sqlAlterar.setBytes(cont++, obj.getImagemRelatorio());
                    sqlAlterar.setInt(cont++, obj.getSegurancaTamanhoSenhaUsuario());
                    sqlAlterar.setInt(cont++, obj.getSegurancaPrazoExpiracaoSenha());
                    sqlAlterar.setInt(cont++, obj.getSegurancaTentativasAcessoAntesBloqueio());
                    sqlAlterar.setInt(cont++, obj.getSegurancaHorasParaZerarTentativasAcesso());
                    sqlAlterar.setBoolean(cont++, obj.getSegurancaSenhaExigirLetraMaiuscula());
                    sqlAlterar.setBoolean(cont++, obj.getSegurancaSenhaExigirNumero());
                    sqlAlterar.setBoolean(cont++, obj.getSegurancaSenhaExigirCaractereEspecial());
                    sqlAlterar.setInt(cont++, obj.getCodigo().intValue());
                    return sqlAlterar;
                }
            });
            registrarLogObjetoVO(obj);
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(ConfiguracaoSistemaVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM ConfiguracaoSistema WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    public ConfiguracaoSistemaVO consultarSeExisteConfiguracaoSistema(Integer empresa) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "";
        if (empresa == null) {
            sql = "SELECT * FROM ConfiguracaoSistema";
        } else {
            sql = "SELECT * FROM ConfiguracaoSistema WHERE ConfiguracaoSistema.empresa = " + empresa;
        }

        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
        ConfiguracaoSistemaVO obj = new ConfiguracaoSistemaVO();
        if (tabelaResultado.next()) {
            obj = montarDados(tabelaResultado);
        } else {
            return new ConfiguracaoSistemaVO();
        }
        return (obj);
    }

    public List consultarPorNomeFantasiaEmpresa(String valorConsulta, Integer empresa) throws Exception {
        consultar(getIdEntidade(), true);
        String sqlStr = "SELECT ConfiguracaoSistema.* FROM ConfiguracaoSistema, Empresa WHERE ConfiguracaoSistema.empresa = Empresa.codigo and Lower( Empresa.nomeFantasia ) like('"
                + valorConsulta.toLowerCase() + "%') AND ConfiguracaoSistema.empresa = " + empresa + " ORDER BY Empresa.nomeFantasia";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return montarDadosConsulta(tabelaResultado);
    }

    public List consultarPorCodigo(Integer valorConsulta, Integer empresa, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM ConfiguracaoSistema WHERE codigo = " + valorConsulta.intValue() + " AND ConfiguracaoSistema.empresa = " + empresa + " ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM ConfiguracaoSistema WHERE codigo = " + valorConsulta.intValue() + " ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public ConfiguracaoSistemaVO consultarConfiguracaoSistema(Integer valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM ConfiguracaoSistema WHERE empresa = " + valorConsulta.intValue() + "";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( ConfiguracaoSistema ).");
        }
        return (montarDados(tabelaResultado));
    }

    public static List montarDadosConsulta(SqlRowSet tabelaResultado) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado));
        }
        return vetResultado;
    }

    public static ConfiguracaoSistemaVO montarDados(SqlRowSet dadosSQL) throws Exception {
        ConfiguracaoSistemaVO obj = new ConfiguracaoSistemaVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.getEmpresa().setCodigo(dadosSQL.getInt("empresa"));
        obj.setImagemBanner((byte[]) dadosSQL.getObject("imagemBanner"));
        obj.setImagemRelatorio((byte[]) dadosSQL.getObject("imagemRelatorio"));
        obj.setSegurancaTamanhoSenhaUsuario(dadosSQL.getInt("segurancaTamanhoSenhaUsuario"));
        obj.setSegurancaPrazoExpiracaoSenha(dadosSQL.getInt("segurancaPrazoExpiracaoSenha"));
        obj.setSegurancaTentativasAcessoAntesBloqueio(dadosSQL.getInt("segurancaTentativasAcessoAntesBloqueio"));
        obj.setSegurancaHorasParaZerarTentativasAcesso(dadosSQL.getInt("segurancaHorasParaZerarTentativasAcesso"));
        obj.setSegurancaSenhaExigirLetraMaiuscula(dadosSQL.getBoolean("segurancaSenhaExigirLetraMaiuscula"));
        obj.setSegurancaSenhaExigirNumero(dadosSQL.getBoolean("segurancaSenhaExigirNumero"));
        obj.setSegurancaSenhaExigirCaractereEspecial(dadosSQL.getBoolean("segurancaSenhaExigirCaractereEspecial"));
        obj.setNovoObj(false);

        return obj;
    }

    public static void montarDadosEmpresa(ConfiguracaoSistemaVO obj) throws Exception {
        if (obj.getEmpresa() == null || obj.getEmpresa().getCodigo().intValue() == 0) {
            // obj.setEmpresa(new EmpresaVO());
            return;
        }
        obj.setEmpresa(getFacadeFactory().getEmpresaFacade().consultarPorChavePrimaria(obj.getEmpresa().getCodigo()));
    }

    public ConfiguracaoSistemaVO consultarPorChavePrimaria(Integer codigoPrm, Integer empresa) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM ConfiguracaoSistema WHERE codigo = ? AND ConfiguracaoSistema.empresa = " + empresa;
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( ConfiguracaoSistema ).");
        }
        return (montarDados(tabelaResultado));
    }

    public ConfiguracaoSistemaVO consultarPorEmpresa(Integer empresa) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM ConfiguracaoSistema WHERE ConfiguracaoSistema.empresa = " + empresa;
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( ConfiguracaoSistema ).");
        }
        return (montarDados(tabelaResultado));
    }

    public static Integer consultarCampoInteger(String campo, Integer empresa) throws Exception {
        String sql = "";
        sql = "SELECT " + campo + " FROM ConfiguracaoSistema WHERE ConfiguracaoSistema.empresa = " + empresa;
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
        if (tabelaResultado.next()) {
            return tabelaResultado.getInt(campo);
        }
        return 0;
    }

    public String getIdEntidade() {
        return ConfiguracaoSistema.idEntidade;
    }

    public void setIdEntidade(String idEntidade) {
        ConfiguracaoSistema.idEntidade = idEntidade;
    }
}
