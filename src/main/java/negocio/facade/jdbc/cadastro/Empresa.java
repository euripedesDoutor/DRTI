package negocio.facade.jdbc.cadastro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.comuns.utilitarias.Uteis;
import negocio.comuns.utilitarias.VariaveisPadronizadas;
import negocio.facade.jdbc.arquitetura.SuperEntidade;
import negocio.interfaces.cadastro.EmpresaInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Lazy
public class Empresa extends SuperEntidade implements EmpresaInterfaceFacade {

    protected static String idEntidade;

    public Empresa() throws Exception {
        super();
        setIdEntidade("Empresa");
    }

    public EmpresaVO novo() throws Exception {
        incluir(getIdEntidade());
        EmpresaVO obj = new EmpresaVO();
        return obj;
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void incluir(final EmpresaVO obj) throws Exception {
        try {
            validarDados(obj);
            incluir(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "INSERT INTO Empresa( razaoSocial, nomeFantasia, endereco, numero, bairro, cidade, cep, telefone, celular, "
                    + "CNPJ, inscricao, dataBloqueio, alertarQuandoRestarDias, cnae, inscricaoMunicipal, "
                    + " situacao, limiteUsuariosSimultaneos, limiteUsuariosSimultaneosVendedorExterno ) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning codigo";
            obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                    int cont = 1;
                    sqlInserir.setString(cont++, obj.getRazaoSocial());
                    sqlInserir.setString(cont++, obj.getNomeFantasia());
                    sqlInserir.setString(cont++, obj.getEndereco());
                    sqlInserir.setString(cont++, obj.getNumero());
                    sqlInserir.setString(cont++, obj.getBairro());
                    sqlInserir.setInt(cont++, obj.getCidade().getCodigo().intValue());
                    sqlInserir.setString(cont++, obj.getCep());
                    sqlInserir.setString(cont++, obj.getTelefone());
                    sqlInserir.setString(cont++, obj.getCelular());
                    sqlInserir.setString(cont++, obj.getCNPJ());
                    sqlInserir.setString(cont++, obj.getInscricao());
                    sqlInserir.setDate(cont++, Uteis.getDataJDBC(obj.getDataBloqueio()));
                    sqlInserir.setInt(cont++, obj.getAlertarQuandoRestarDias());
                    sqlInserir.setString(cont++, obj.getCnae());
                    sqlInserir.setString(cont++, obj.getInscricaoMunicipal());
                    sqlInserir.setString(cont++, obj.getSituacao());
                    sqlInserir.setInt(cont++, obj.getLimiteUsuariosSimultaneos());
                    sqlInserir.setInt(cont++, obj.getLimiteUsuariosSimultaneosVendedorExterno());
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
    public void alterar(final EmpresaVO obj) throws Exception {
        try {
            validarDados(obj);
            alterar(getIdEntidade());
            obj.realizarUpperCase(obj);
            final String sql = "UPDATE Empresa set razaoSocial=?, nomeFantasia=?, endereco=?, numero=?, bairro=?, cidade=?, cep=?, telefone=?, celular=?, " +
                    "CNPJ=?, inscricao=?, dataBloqueio=?, alertarQuandoRestarDias=?, cnae=?, inscricaoMunicipal=?, " +
                    "situacao=?, limiteUsuariosSimultaneos=?, limiteUsuariosSimultaneosVendedorExterno=? "
                    + "WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    int cont = 1;
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
                    sqlAlterar.setString(cont++, obj.getRazaoSocial());
                    sqlAlterar.setString(cont++, obj.getNomeFantasia());
                    sqlAlterar.setString(cont++, obj.getEndereco());
                    sqlAlterar.setString(cont++, obj.getNumero());
                    sqlAlterar.setString(cont++, obj.getBairro());
                    sqlAlterar.setInt(cont++, obj.getCidade().getCodigo().intValue());
                    sqlAlterar.setString(cont++, obj.getCep());
                    sqlAlterar.setString(cont++, obj.getTelefone());
                    sqlAlterar.setString(cont++, obj.getCelular());
                    sqlAlterar.setString(cont++, obj.getCNPJ());
                    sqlAlterar.setString(cont++, obj.getInscricao());
                    sqlAlterar.setDate(cont++, Uteis.getDataJDBC(obj.getDataBloqueio()));
                    sqlAlterar.setInt(cont++, obj.getAlertarQuandoRestarDias());
                    sqlAlterar.setString(cont++, obj.getCnae());
                    sqlAlterar.setString(cont++, obj.getInscricaoMunicipal());
                    sqlAlterar.setString(cont++, obj.getSituacao());
                    sqlAlterar.setInt(cont++, obj.getLimiteUsuariosSimultaneos());
                    sqlAlterar.setInt(cont++, obj.getLimiteUsuariosSimultaneosVendedorExterno());
                    sqlAlterar.setInt(cont++, obj.getCodigo().intValue());

                    return sqlAlterar;
                }
            });

        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void alterarDataBloqueiosTodasEmpresasBancoDados(final EmpresaVO obj) throws Exception {
        {
            final String sqlDataBloqueioEmpresas = "UPDATE Empresa SET dataBloqueio=?, alertarquandorestardias=?";

            getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                    PreparedStatement sqlAlterar = arg0.prepareStatement(sqlDataBloqueioEmpresas);
                    sqlAlterar.setTimestamp(1, Uteis.getDataJDBCTimestamp(obj.getDataBloqueio()));
                    sqlAlterar.setInt(2, 5);
                    return sqlAlterar;
                }
            });

        }
    }

    public Date consultarDataBloqueio(Integer codigoEmpresa) throws Exception {

        String sqlStr = "SELECT dataBloqueio FROM Empresa WHERE codigo = " + codigoEmpresa.intValue();
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        tabelaResultado.next();
        if (tabelaResultado != null) {
            return tabelaResultado.getDate("dataBloqueio");
        }
        return null;
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void excluir(EmpresaVO obj) throws Exception {
        try {
            excluir(getIdEntidade());
            String sql = "DELETE FROM Empresa WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{obj.getCodigo()});
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Responsável por realizar uma consulta de <code>Empresa</code> através do valor do atributo
     * <code>String CNPJ</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>EmpresaVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorCNPJ(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Empresa WHERE Lower( CNPJ ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY CNPJ";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarPorNomeCidade(String valorConsulta) throws Exception {
        consultar(getIdEntidade(), true);
        String sqlStr = "SELECT Empresa.* FROM Empresa, Cidade WHERE Empresa.cidade = Cidade.codigo and Lower( Cidade.nome ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY Cidade.nome";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return montarDadosConsulta(tabelaResultado);
    }

    /**
     * Responsável por realizar uma consulta de <code>Empresa</code> através do valor do atributo
     * <code>String endereco</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro
     * fornecido. Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List
     * resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>EmpresaVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorEndereco(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Empresa WHERE Lower( endereco ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY endereco";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    /**
     * Responsável por realizar uma consulta de <code>Empresa</code> através do valor do atributo
     * <code>String nomeFantasia</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro
     * fornecido. Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List
     * resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>EmpresaVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorNomeFantasia(String valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Empresa WHERE Lower( nomeFantasia ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY nomeFantasia";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarPorNomeFantasia(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Empresa WHERE Lower( nomeFantasia ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY nomeFantasia";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public List consultarPorNomeFantasiaSituacao(String valorConsulta, String situacao, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Empresa WHERE Lower( nomeFantasia ) like('" + valorConsulta.toLowerCase() + "%') AND UPPER(situacao) LIKE ('" + situacao.toUpperCase() + "') ORDER BY nomeFantasia";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    /**
     * Responsável por realizar uma consulta de <code>Empresa</code> através do valor do atributo
     * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido. Faz uso
     * da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     *
     * @param controlarAcesso
     *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
     * @return List Contendo vários objetos da classe <code>EmpresaVO</code> resultantes da consulta.
     * @exception Exception
     *                Caso haja problemas de conexão ou restrição de acesso.
     */
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception {
        consultar(getIdEntidade(), controlarAcesso);
        String sqlStr = "SELECT * FROM Empresa WHERE codigo = " + valorConsulta.intValue() + " ORDER BY codigo";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
        return (montarDadosConsulta(tabelaResultado));
    }

    public void setarCodigoContabilista(Integer codigoEmpresa, Integer codigoContabilista) throws Exception {
        String sqlStr = "UPDATE empresa SET contabilista = " + codigoContabilista + " WHERE codigo = " + codigoEmpresa;
        getConexao().getJdbcTemplate().update(sqlStr);
    }

    /**
     * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
     * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
     * vez.
     *
     * @return List Contendo vários objetos da classe <code>EmpresaVO</code> resultantes da consulta.
     */
    public static List montarDadosConsulta(SqlRowSet tabelaResultado) throws Exception {
        List vetResultado = new ArrayList(0);
        while (tabelaResultado.next()) {
            vetResultado.add(montarDados(tabelaResultado));
        }
        return vetResultado;
    }

    /**
     * Responsável por montar os dados resultantes de uma consulta ao banco de dados (<code>ResultSet</code>) em um
     * objeto da classe <code>EmpresaVO</code>.
     *
     * @return O objeto da classe <code>EmpresaVO</code> com os dados devidamente montados.
     */
    public static EmpresaVO montarDados(SqlRowSet dadosSQL) throws Exception {
        EmpresaVO obj = new EmpresaVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setRazaoSocial(dadosSQL.getString("razaoSocial"));
        obj.setNomeFantasia(dadosSQL.getString("nomeFantasia"));
        obj.setEndereco(dadosSQL.getString("endereco"));
        obj.setNumero(dadosSQL.getString("numero"));
        obj.setBairro(dadosSQL.getString("bairro"));
        obj.getCidade().setCodigo(dadosSQL.getInt("cidade"));
        obj.setCep(dadosSQL.getString("cep"));
        obj.setTelefone(dadosSQL.getString("telefone"));
        obj.setCelular(dadosSQL.getString("celular"));
        obj.setCNPJ(dadosSQL.getString("CNPJ"));
        obj.setInscricao(dadosSQL.getString("inscricao"));
        obj.setAlertarQuandoRestarDias(dadosSQL.getInt("alertarQuandoRestarDias"));
        obj.setDataBloqueio(dadosSQL.getDate("dataBloqueio"));
        obj.setCnae(dadosSQL.getString("cnae"));
        obj.setInscricaoMunicipal(dadosSQL.getString("inscricaoMunicipal"));
        obj.setSituacao(dadosSQL.getString("situacao"));
        obj.setLimiteUsuariosSimultaneos(dadosSQL.getInt("limiteUsuariosSimultaneos"));
        obj.setLimiteUsuariosSimultaneosVendedorExterno(dadosSQL.getInt("limiteUsuariosSimultaneosVendedorExterno"));
        obj.setNovoObj(false);

        montarDadosCidade(obj);
        return obj;
    }

    /**
     * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
     * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
     * vez.
     *
     * @return List Contendo vários objetos da classe <code>EmpresaVO</code> resultantes da consulta.
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
     * objeto da classe <code>EmpresaVO</code>.
     *
     * @return O objeto da classe <code>EmpresaVO</code> com os dados devidamente montados.
     */
    public static EmpresaVO montarDados(SqlRowSet dadosSQL, int nivelMontarDados) throws Exception {
        EmpresaVO obj = new EmpresaVO();
        obj.setCodigo(dadosSQL.getInt("codigo"));
        obj.setRazaoSocial(dadosSQL.getString("razaoSocial"));
        obj.setNomeFantasia(dadosSQL.getString("nomeFantasia"));
        obj.setEndereco(dadosSQL.getString("endereco"));
        obj.setNumero(dadosSQL.getString("numero"));
        obj.setBairro(dadosSQL.getString("bairro"));
        obj.getCidade().setCodigo(dadosSQL.getInt("cidade"));
        obj.setCep(dadosSQL.getString("cep"));
        obj.setTelefone(dadosSQL.getString("telefone"));
        obj.setCelular(dadosSQL.getString("celular"));
        obj.setCNPJ(dadosSQL.getString("CNPJ"));
        obj.setInscricao(dadosSQL.getString("inscricao"));
        obj.setAlertarQuandoRestarDias(dadosSQL.getInt("alertarQuandoRestarDias"));
        obj.setDataBloqueio(dadosSQL.getDate("dataBloqueio"));
        obj.setCnae(dadosSQL.getString("cnae"));
        obj.setInscricaoMunicipal(dadosSQL.getString("inscricaoMunicipal"));
        obj.setLimiteUsuariosSimultaneos(dadosSQL.getInt("limiteUsuariosSimultaneos"));
        obj.setLimiteUsuariosSimultaneosVendedorExterno(dadosSQL.getInt("limiteUsuariosSimultaneosVendedorExterno"));
        obj.setSituacao(dadosSQL.getString("situacao"));
        obj.setNovoObj(false);
        if (nivelMontarDados == Uteis.NIVELMONTARDADOS_DADOSBASICOS) {
            return obj;
        }
        montarDadosCidade(obj);
        return obj;
    }

    /**
     * Operação responsável por montar os dados de um objeto da classe <code>CidadeVO</code> relacionado ao objeto
     * <code>EmpresaVO</code>. Faz uso da chave primária da classe <code>CidadeVO</code> para realizar a consulta.
     *
     * @param obj
     *            Objeto no qual será montado os dados consultados.
     */
    public static void montarDadosCidade(EmpresaVO obj) throws Exception {
        if (obj.getCidade() == null || obj.getCidade().getCodigo().intValue() == 0) {
            // obj.setCidade(new CidadeVO());
            return;
        }
        obj.setCidade(getFacadeFactory().getCidadeFacade().consultarPorChavePrimaria(obj.getCidade().getCodigo()));
    }

    public EmpresaVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Empresa WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Empresa ).");
        }
        return (montarDados(tabelaResultado));
    }
    
    public EmpresaVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception {
        consultar(getIdEntidade(), false);
        String sql = "SELECT * FROM Empresa WHERE codigo = ?";
        SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{codigoPrm});
        if (!tabelaResultado.next()) {
            throw new ConsistirException("Dados Não Encontrados ( Empresa ).");
        }
        return (montarDados(tabelaResultado, nivelMontarDados));
    }

    public static void validarDados(EmpresaVO obj) throws ConsistirException {
        if (obj.getRazaoSocial() == null || obj.getRazaoSocial().equals("")) {
            throw new ConsistirException("O campo RAZÃO SOCIAL (Empresa) deve ser informado.");
        }
        if (obj.getNomeFantasia() == null || obj.getNomeFantasia().equals("")) {
            throw new ConsistirException("O campo NOME FANTASIA (Empresa) deve ser informado.");
        }
        if (obj.getEndereco() == null || obj.getEndereco().equals("")) {
            throw new ConsistirException("O campo ENDEREÇO (Empresa) deve ser informado.");
        }
        if (obj.getNumero() == null || obj.getNumero().equals("")) {
            throw new ConsistirException("O campo NUMERO (Empresa) deve ser informado.");
        }
        if (obj.getBairro() == null || obj.getBairro().equals("")) {
            throw new ConsistirException("O campo BAIRRO (Empresa) deve ser informado.");
        }
        if ((obj.getCidade() == null)
                || (obj.getCidade().getCodigo().intValue() == 0)) {
            throw new ConsistirException("O campo CIDADE (Empresa) deve ser informado.");
        }
        if (obj.getCep() == null || obj.getCep().equals("")) {
            throw new ConsistirException("O campo CEP (Empresa) deve ser informado.");
        }
        if (obj.getTelefone() == null || obj.getTelefone().equals("")) {
            throw new ConsistirException("O campo TELEFONE (Empresa) deve ser informado.");
        }
        if (obj.getCNPJ() == null || obj.getCNPJ().equals("")) {
            throw new ConsistirException("O campo CNPJ (Empresa) deve ser informado.");
        }
        if (obj.getInscricao() == null || obj.getInscricao().equals("")) {
            obj.setInscricao(VariaveisPadronizadas.ISENTO);
        } else if (obj.getInscricao() == null || obj.getInscricao().equals("")) {
            throw new ConsistirException("O campo INSCRIÇÃO (Empresa) deve ser informado.");
        }
    }

    /**
     * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
     * permissões de acesso as operações desta classe.
     */
    public String getIdEntidade() {
        return Empresa.idEntidade;
    }

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
     * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
     * Como o controle de acesso é realizado com base neste identificador,
     */
    public void setIdEntidade(String idEntidade) {
        Empresa.idEntidade = idEntidade;
    }
}
