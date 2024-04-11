package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import negocio.comuns.arquitetura.PermissaoVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.interfaces.arquitetura.PermissaoInterfaceFacade;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de persistência que encapsula todas as operações de manipulação dos dados da classe <code>PermissaoVO</code>.
 * Responsável por implementar operações como incluir, alterar, excluir e consultar pertinentes a classe
 * <code>PermissaoVO</code>. Encapsula toda a interação com o banco de dados.
 * 
 * @see PermissaoVO
 * @see SuperEntidade
 * @see PerfilAcesso
 */
@SuppressWarnings("unchecked")
@Repository
@Lazy
public class Permissao extends SuperEntidade implements PermissaoInterfaceFacade {

	protected static String idEntidade;

	public Permissao() throws Exception {
		super();
		setIdEntidade("PerfilAcesso");
	}

	/**
	 * Operação responsável por retornar um novo objeto da classe <code>PermissaoVO</code>.
	 */
	public PermissaoVO novo() throws Exception {
		incluir(getIdEntidade());
		PermissaoVO obj = new PermissaoVO();
		return obj;
	}

	/**
	 * Operação responsável por incluir no banco de dados um objeto da classe <code>PermissaoVO</code>. Primeiramente
	 * valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do
	 * USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>incluir</code> da superclasse.
	 * 
	 * @param obj
	 *            Objeto da classe <code>PermissaoVO</code> que será gravado no banco de dados.
	 * @exception Exception
	 *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void incluir(final PermissaoVO obj) throws Exception {
		PermissaoVO.validarDados(obj);
		incluir(getIdEntidade());
		final String sql = "INSERT INTO Permissao( codPerfilAcesso, nomeEntidade, permissoes, tipoPermissao, valorEspecifico, valorInicial, valorFinal ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
		getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement sqlInserir = arg0.prepareStatement(sql);
				sqlInserir.setInt(1, obj.getCodPerfilAcesso().intValue());
				sqlInserir.setString(2, obj.getNomeEntidade());
				sqlInserir.setString(3, obj.getPermissoes());
				sqlInserir.setInt(4, obj.getTipoPermissao().intValue());
				sqlInserir.setString(5, obj.getValorEspecifico());
				sqlInserir.setString(6, obj.getValorInicial());
				sqlInserir.setString(7, obj.getValorFinal());
				return sqlInserir;
			}
		});
		obj.setNovoObj(false);
	}

	/**
	 * Operação responsável por alterar no BD os dados de um objeto da classe <code>PermissaoVO</code>. Sempre utiliza a
	 * chave primária da classe como atributo para localização do registro a ser alterado. Primeiramente valida os dados
	 * (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do USUÁRIO para
	 * realizar esta operacão na entidade. Isto, através da operação <code>alterar</code> da superclasse.
	 * 
	 * @param obj
	 *            Objeto da classe <code>PermissaoVO</code> que será alterada no banco de dados.
	 * @exception Execption
	 *                Caso haja problemas de conexão, restrição de acesso ou validação de dados.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void alterar(final PermissaoVO obj) throws Exception {
		PermissaoVO.validarDados(obj);
		alterar(getIdEntidade());
		final String sql = "UPDATE Permissao set permissoes=?, tipoPermissao=?, valorEspecifico=?, valorInicial=?, valorFinal=? WHERE ((codPerfilAcesso = ?) and (nomeEntidade = ?))";
		getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
				sqlAlterar.setString(1, obj.getPermissoes());
				sqlAlterar.setInt(2, obj.getTipoPermissao().intValue());
				sqlAlterar.setString(3, obj.getValorEspecifico());
				sqlAlterar.setString(4, obj.getValorInicial());
				sqlAlterar.setString(5, obj.getValorFinal());
				sqlAlterar.setInt(6, obj.getCodPerfilAcesso().intValue());
				sqlAlterar.setString(7, obj.getNomeEntidade());
				return sqlAlterar;
			}
		});
	}

	/**
	 * Operação responsável por excluir no BD um objeto da classe <code>PermissaoVO</code>. Sempre localiza o registro a
	 * ser excluído através da chave primária da entidade. Primeiramente verifica a conexão com o banco de dados e a
	 * permissão do USUÁRIO para realizar esta operacão na entidade. Isto, através da operação <code>excluir</code> da
	 * superclasse.
	 * 
	 * @param obj
	 *            Objeto da classe <code>PermissaoVO</code> que será removido no banco de dados.
	 * @exception Execption
	 *                Caso haja problemas de conexão ou restrição de acesso.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(PermissaoVO obj) throws Exception {
		excluir(getIdEntidade());
		String sql = "DELETE FROM Permissao WHERE ((codPerfilAcesso = ?) and (nomeEntidade = ?))";
		getConexao().getJdbcTemplate().update(sql, new Object[] { obj.getCodPerfilAcesso(), obj.getNomeEntidade() });
	}

	

	/**
	 * Responsável por realizar uma consulta de <code>Permissao</code> através do valor do atributo
	 * <code>String nomeEntidade</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro
	 * fornecido. Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List
	 * resultante.
	 * 
	 * @param controlarAcesso
	 *            Indica se a aplicação deverá verificar se o USUÁRIO possui permissão para esta consulta ou não.
	 * @return List Contendo vários objetos da classe <code>PermissaoVO</code> resultantes da consulta.
	 * @exception Exception
	 *                Caso haja problemas de conexão ou restrição de acesso.
	 */
	public List consultarPorNomeEntidade(String valorConsulta, boolean controlarAcesso) throws Exception {
		consultar(getIdEntidade(), controlarAcesso);
		String sqlStr = "SELECT * FROM Permissao WHERE nomeEntidade like('" + valorConsulta.toLowerCase() + "%') ORDER BY nomeEntidade";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return (montarDadosConsulta(tabelaResultado));
	}

	/**
	 * Responsável por realizar uma consulta de <code>Permissao</code> através do valor do atributo <code>nome</code> da
	 * classe <code>PerfilAcesso</code> Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de
	 * prerarar o List resultante.
	 * 
	 * @return List Contendo vários objetos da classe <code>PermissaoVO</code> resultantes da consulta.
	 * @exception Execption
	 *                Caso haja problemas de conexão ou restrição de acesso.
	 */
	public List consultarPorNomePerfilAcesso(String valorConsulta) throws Exception {
		consultar(getIdEntidade(), true);
		String sqlStr = "SELECT Permissao.* FROM Permissao, PerfilAcesso WHERE Permissao.codPerfilAcesso = PerfilAcesso.codigo and PerfilAcesso.nome like('" + valorConsulta.toLowerCase()
				+ "%') ORDER BY PerfilAcesso.nome";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return montarDadosConsulta(tabelaResultado);
	}

	/**
	 * Responsável por montar os dados de vários objetos, resultantes de uma consulta ao banco de dados (
	 * <code>ResultSet</code>). Faz uso da operação <code>montarDados</code> que realiza o trabalho para um objeto por
	 * vez.
	 * 
	 * @return List Contendo vários objetos da classe <code>PermissaoVO</code> resultantes da consulta.
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
	 * objeto da classe <code>PermissaoVO</code>.
	 * 
	 * @return O objeto da classe <code>PermissaoVO</code> com os dados devidamente montados.
	 */
	public static PermissaoVO montarDados(SqlRowSet dadosSQL) throws Exception {
		PermissaoVO obj = new PermissaoVO();
		obj.setCodPerfilAcesso(dadosSQL.getInt("codPerfilAcesso"));
		obj.setNomeEntidade(dadosSQL.getString("nomeEntidade"));
		obj.setPermissoes(dadosSQL.getString("permissoes"));
		obj.setTipoPermissao(dadosSQL.getInt("tipoPermissao"));
		obj.setValorEspecifico(dadosSQL.getString("valorEspecifico"));
		obj.setValorInicial(dadosSQL.getString("valorInicial"));
		obj.setValorFinal(dadosSQL.getString("valorFinal"));
		obj.setNovoObj(false);
		return obj;
	}

	/**
	 * Operação responsável por excluir todos os objetos da <code>PermissaoVO</code> no BD. Faz uso da operação
	 * <code>excluir</code> disponível na classe <code>Permissao</code>.
	 * 
	 * @param <code>codPerfilAcesso</code> campo chave para exclusão dos objetos no BD.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluirPermissaos(Integer codPerfilAcesso) throws Exception {
		excluir(getIdEntidade());
		String sql = "DELETE FROM Permissao WHERE (codPerfilAcesso = ?)";
		getConexao().getJdbcTemplate().update(sql, new Object[] { codPerfilAcesso });
	}

	/**
	 * Operação responsável por alterar todos os objetos da <code>PermissaoVO</code> contidos em um Hashtable no BD. Faz
	 * uso da operação <code>excluirPermissaos</code> e <code>incluirPermissaos</code> disponíveis na classe
	 * <code>Permissao</code>.
	 * 
	 * @param objetos
	 *            List com os objetos a serem alterados ou incluídos no BD.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void alterarPermissaos(Integer codPerfilAcesso, List objetos) throws Exception {
		excluirPermissaos(codPerfilAcesso);
		incluirPermissaos(codPerfilAcesso, objetos);
	}

	/**
	 * Operação responsável por incluir objetos da <code>PermissaoVO</code> no BD. Garantindo o relacionamento com a
	 * entidade principal <code>arquitetura.PerfilAcesso</code> através do atributo de vínculo.
	 * 
	 * @param objetos
	 *            List contendo os objetos a serem gravados no BD da classe.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void incluirPermissaos(Integer codPerfilAcessoPrm, List objetos) throws Exception {
		Iterator e = objetos.iterator();
		while (e.hasNext()) {
			PermissaoVO obj = (PermissaoVO) e.next();
			obj.setCodPerfilAcesso(codPerfilAcessoPrm);
			incluir(obj);
		}
	}

	/**
	 * Operação responsável por consultar todos os <code>PermissaoVO</code> relacionados a um objeto da classe
	 * <code>arquitetura.PerfilAcesso</code>.
	 * 
	 * @param codPerfilAcesso
	 *            Atributo de <code>arquitetura.PerfilAcesso</code> a ser utilizado para localizar os objetos da classe
	 *            <code>PermissaoVO</code>.
	 * @return List Contendo todos os objetos da classe <code>PermissaoVO</code> resultantes da consulta.
	 * @exception Exception
	 *                Erro de conexão com o BD ou restrição de acesso a esta operação.
	 */
	public List consultarPermissaos(Integer codPerfilAcesso) throws Exception {
		consultar(getIdEntidade());
		List objetos = new ArrayList(0);
		String sql = "SELECT * FROM Permissao WHERE codPerfilAcesso = ?";
		SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[] { codPerfilAcesso });
		while (resultado.next()) {
			objetos.add(Permissao.montarDados(resultado));
		}
		return objetos;
	}

	/**
	 * Operação responsável por localizar um objeto da classe <code>PermissaoVO</code> através de sua chave primária.
	 * 
	 * @exception Exception
	 *                Caso haja problemas de conexão ou localização do objeto procurado.
	 */
	public PermissaoVO consultarPorChavePrimaria(Integer codPerfilAcessoPrm, String nomeEntidadePrm) throws Exception {
		consultar(getIdEntidade(), false);
		String sql = "SELECT * FROM Permissao WHERE codPerfilAcesso = ? and nomeEntidade = ?";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[] { codPerfilAcessoPrm, nomeEntidadePrm });
		if (!tabelaResultado.next()) {
			throw new ConsistirException("Dados Não Encontrados.");
		}
		return (montarDados(tabelaResultado));
	}

	/**
	 * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
	 * permissões de acesso as operações desta classe.
	 */
	public String getIdEntidade() {
		return Permissao.idEntidade;
	}

	/**
	 * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
	 * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
	 * Como o controle de acesso é realizado com base neste identificador,
	 */
	public void setIdEntidade(String idEntidade) {
		Permissao.idEntidade = idEntidade;
	}
}