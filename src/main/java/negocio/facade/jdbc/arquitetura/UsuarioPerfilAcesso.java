package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import negocio.comuns.arquitetura.UsuarioPerfilAcessoVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.interfaces.arquitetura.UsuarioPerfilAcessoInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@SuppressWarnings("unchecked")
@Repository
@Lazy
public class UsuarioPerfilAcesso extends SuperEntidade implements UsuarioPerfilAcessoInterfaceFacade {

	protected static String idEntidade;

	public UsuarioPerfilAcesso() throws Exception {
		super();
		setIdEntidade("Cliente");
	}

        public static void validarDados(UsuarioPerfilAcessoVO obj) throws ConsistirException {

        if ((obj.getPerfilAcesso() == null) ||
            (obj.getPerfilAcesso().getCodigo().intValue() == 0)) {
            throw new ConsistirException("O campo PERFIL ACESSO (Perfil Acesso Usuário) deve ser informado.");
        }
    }

	/**
	 * Operação responsável por incluir no banco de dados um objeto da classe <code>UsuarioPerfilAcessoVO</code>.
	 * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a
	 * permissão do usuário para realizar esta operacão na entidade. Isto, através da operação <code>incluir</code> da
	 * superclasse.
	 * 
	 * @param obj
	 *            Objeto da classe <code>UsuarioPerfilAcessoVO</code> que será gravado no banco de dados.
	 * @exception Exception
	 *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void incluir(final UsuarioPerfilAcessoVO obj) throws Exception {
		UsuarioPerfilAcessoVO.validarDados(obj);
		incluir(getIdEntidade());
		final String sql = "INSERT INTO UsuarioPerfilAcesso( usuario, empresa, perfilAcesso) VALUES ( ?, ?, ?) returning codigo";
		obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement sqlInserir = arg0.prepareStatement(sql);
				sqlInserir.setInt(1, obj.getUsuario().intValue());
				sqlInserir.setInt(2, obj.getEmpresa().getCodigo().intValue());
				sqlInserir.setInt(3, obj.getPerfilAcesso().getCodigo().intValue());
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
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void alterar(final UsuarioPerfilAcessoVO obj) throws Exception {
		UsuarioPerfilAcessoVO.validarDados(obj);
		alterar(getIdEntidade());
		final String sql = "UPDATE UsuarioPerfilAcesso set usuario=?, empresa=?, perfilAcesso=? WHERE ((codigo = ?))";
		getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
				sqlAlterar.setInt(1, obj.getUsuario().intValue());
				sqlAlterar.setInt(2, obj.getEmpresa().getCodigo().intValue());
				sqlAlterar.setInt(3, obj.getPerfilAcesso().getCodigo().intValue());
				sqlAlterar.setInt(4, obj.getCodigo());
				return sqlAlterar;
			}
		});
	}

	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(UsuarioPerfilAcessoVO obj) throws Exception {
		excluir(getIdEntidade());
		String sql = "DELETE FROM UsuarioPerfilAcesso WHERE ((codigo = ?))";
		getConexao().getJdbcTemplate().update(sql, new Object[] { obj.getCodigo() });
	}

	public List consultarPorNomeEmpresa(String valorConsulta) throws Exception {
		consultar(getIdEntidade(), true);
		String sqlStr = "SELECT UsuarioPerfilAcesso.* FROM UsuarioPerfilAcesso, Empresa WHERE UsuarioPerfilAcesso.empresa = Empresa.codigo and Lower( Empresa.nomeFantasia ) like('"
				+ valorConsulta.toLowerCase() + "%') ORDER BY Empresa.nomeFantasia";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return montarDadosConsulta(tabelaResultado);
	}

	public List consultarPorEndereco(String valorConsulta, boolean controlarAcesso) throws Exception {
		consultar(getIdEntidade(), controlarAcesso);
		String sqlStr = "SELECT * FROM EnderecoEntrega WHERE Lower( endereco ) like('" + valorConsulta.toLowerCase() + "%') ORDER BY endereco";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return (montarDadosConsulta(tabelaResultado));
	}

	public List consultarPorNomeCliente(String valorConsulta) throws Exception {
		consultar(getIdEntidade(), true);
		String sqlStr = "SELECT UsuarioPerfilAcesso.* FROM UsuarioPerfilAcesso, Cliente WHERE UsuarioPerfilAcesso.cliente = Cliente.codigo and Lower( Cliente.nome ) like('"
				+ valorConsulta.toLowerCase() + "%') ORDER BY Cliente.nome";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return montarDadosConsulta(tabelaResultado);
	}

	/**
	 * Responsável por realizar uma consulta de <code>UsuarioPerfilAcesso</code> através do valor do atributo
	 * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido. Faz uso
	 * da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
	 * 
	 * @param controlarAcesso
	 *            Indica se a aplicação deverá verificar se o usuário possui permissão para esta consulta ou não.
	 * @return List Contendo vários objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
	 * @exception Exception
	 *                Caso haja problemas de conexão ou restrição de acesso.
	 */
	public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception {
		consultar(getIdEntidade(), controlarAcesso);
		String sqlStr = "SELECT * FROM UsuarioPerfilAcesso WHERE codigo = " + valorConsulta.intValue() + " ORDER BY codigo";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return (montarDadosConsulta(tabelaResultado));
	}

	/**
	 * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
	 * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
	 * vez.
	 * 
	 * @return List Contendo vários objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
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
	 * objeto da classe <code>UsuarioPerfilAcessoVO</code>.
	 * 
	 * @return O objeto da classe <code>UsuarioPerfilAcessoVO</code> com os dados devidamente montados.
	 */
	public static UsuarioPerfilAcessoVO montarDados(SqlRowSet dadosSQL) throws Exception {
		UsuarioPerfilAcessoVO obj = new UsuarioPerfilAcessoVO();
		obj.setCodigo(dadosSQL.getInt("codigo"));
		obj.setUsuario(dadosSQL.getInt("usuario"));
		obj.getEmpresa().setCodigo(dadosSQL.getInt("empresa"));
		obj.getPerfilAcesso().setCodigo(dadosSQL.getInt("perfilAcesso"));
		obj.setNovoObj(false);

		montarDadosEmpresa(obj);
		montarDadosPerfilAcesso(obj);
		return obj;
	}

	/**
	 * Operação responsável por montar os dados de um objeto da classe <code>CidadeVO</code> relacionado ao objeto
	 * <code>UsuarioPerfilAcessoVO</code>. Faz uso da chave primária da classe <code>CidadeVO</code> para realizar a
	 * consulta.
	 * 
	 * @param obj
	 *            Objeto no qual será montado os dados consultados.
	 */
	public static void montarDadosEmpresa(UsuarioPerfilAcessoVO obj) throws Exception {
		if (obj.getEmpresa() == null || obj.getEmpresa().getCodigo().intValue() == 0) {
			return;
		}
		obj.setEmpresa(getFacadeFactory().getEmpresaFacade().consultarPorChavePrimaria(obj.getEmpresa().getCodigo()));
	}

	/**
	 * Operação responsável por montar os dados de um objeto da classe <code>CidadeVO</code> relacionado ao objeto
	 * <code>UsuarioPerfilAcessoVO</code>. Faz uso da chave primária da classe <code>CidadeVO</code> para realizar a
	 * consulta.
	 * 
	 * @param obj
	 *            Objeto no qual será montado os dados consultados.
	 */
	public static void montarDadosPerfilAcesso(UsuarioPerfilAcessoVO obj) throws Exception {
		if (obj.getPerfilAcesso() == null || obj.getPerfilAcesso().getCodigo().intValue() == 0) {
			return;
		}
		obj.setPerfilAcesso(getFacadeFactory().getPerfilAcessoFacade().consultarPorChavePrimaria(obj.getPerfilAcesso().getCodigo()));
	}

	/**
	 * Operação responsável por excluir todos os objetos da <code>UsuarioPerfilAcessoVO</code> no BD. Faz uso da
	 * operação <code>excluir</code> disponível na classe <code>UsuarioPerfilAcesso</code>.
	 * 
	 * @param <code>cliente</code> campo chave para exclusão dos objetos no BD.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluirUsuarioPerfilAcesso(Integer usuario) throws Exception {
		excluir(getIdEntidade());
		String sql = "DELETE FROM UsuarioPerfilAcesso WHERE (usuario = ?)";
		getConexao().getJdbcTemplate().update(sql, new Object[] { usuario });
	}

	/**
	 * Operação responsável por alterar todos os objetos da <code>UsuarioPerfilAcessoVO</code> contidos em um Hashtable
	 * no BD. Faz uso da operação <code>excluirUsuarioPerfilAcessos</code> e <code>incluirUsuarioPerfilAcessos</code>
	 * disponíveis na classe <code>UsuarioPerfilAcesso</code>.
	 * 
	 * @param objetos
	 *            List com os objetos a serem alterados ou incluídos no BD.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void alterarUsuarioPerfilAcesso(Integer usuario, List objetos) throws Exception {
		excluirUsuarioPerfilAcesso(usuario);
		incluirUsuarioPerfilAcesso(usuario, objetos);
	}

	/**
	 * Operação responsável por incluir objetos da <code>UsuarioPerfilAcessoVO</code> no BD. Garantindo o relacionamento
	 * com a entidade principal <code>cadastro.Cliente</code> através do atributo de vínculo.
	 * 
	 * @param objetos
	 *            List contendo os objetos a serem gravados no BD da classe.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void incluirUsuarioPerfilAcesso(Integer usuarioprm, List objetos) throws Exception {
		Iterator e = objetos.iterator();
		while (e.hasNext()) {
			UsuarioPerfilAcessoVO obj = (UsuarioPerfilAcessoVO) e.next();
			obj.setUsuario(usuarioprm);
			incluir(obj);
		}
	}

	/**
	 * Operação responsável por consultar todos os <code>UsuarioPerfilAcessoVO</code> relacionados a um objeto da classe
	 * <code>cadastro.Cliente</code>.
	 * 
	 * @param cliente
	 *            Atributo de <code>cadastro.Cliente</code> a ser utilizado para localizar os objetos da classe
	 *            <code>UsuarioPerfilAcessoVO</code>.
	 * @return List Contendo todos os objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	public List consultarUsuarioPerfilAcesso(Integer usuario) throws Exception {
		consultar(getIdEntidade());
		List objetos = new ArrayList(0);
		String sql = "SELECT * FROM UsuarioPerfilAcesso WHERE usuario = ?";
		SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[] { usuario });
		while (resultado.next()) {
			objetos.add(UsuarioPerfilAcesso.montarDados(resultado));
		}
		return objetos;
	}

	/**
	 * Operação responsável por localizar um objeto da classe <code>UsuarioPerfilAcessoVO</code> através de sua chave
	 * primária.
	 * 
	 * @exception Exception
	 *                Caso haja problemas de conexão ou localização do objeto procurado.
	 */
	public UsuarioPerfilAcessoVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception {
		consultar(getIdEntidade(), false);
		String sql = "SELECT * FROM UsuarioPerfilAcesso WHERE codigo = ?";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[] { codigoPrm });
		if (!tabelaResultado.next()) {
			throw new ConsistirException("Dados Não Encontrados ( UsuarioPerfilAcesso ).");
		}
		return (montarDados(tabelaResultado));
	}

	/**
	 * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
	 * permissões de acesso as operações desta classe.
	 */
	public String getIdEntidade() {
		return UsuarioPerfilAcesso.idEntidade;
	}

	/**
	 * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
	 * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
	 * Como o controle de acesso é realizado com base neste identificador,
	 */
	public void setIdEntidade(String idEntidade) {
		UsuarioPerfilAcesso.idEntidade = idEntidade;
	}

    public UsuarioPerfilAcessoVO novo() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}