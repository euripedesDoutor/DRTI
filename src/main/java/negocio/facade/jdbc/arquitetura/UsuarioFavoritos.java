package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import negocio.comuns.arquitetura.UsuarioFavoritosVO;
import negocio.comuns.utilitarias.ConsistirException;
import negocio.interfaces.arquitetura.UsuarioFavoritosInterfaceFacade;

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
public class UsuarioFavoritos extends SuperEntidade implements UsuarioFavoritosInterfaceFacade {

	protected static String idEntidade;

	public UsuarioFavoritos() throws Exception {
		super();
		setIdEntidade("Cliente");
	}

      

	
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void incluir(final UsuarioFavoritosVO obj) throws Exception {
		UsuarioFavoritosVO.validarDados(obj);
		final String sql = "INSERT INTO UsuarioFavoritos( usuario, empresa, titulo, endereco, icone) VALUES (?, ?, ?, ?, ?) returning codigo";
		obj.setCodigo((Integer) getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement sqlInserir = arg0.prepareStatement(sql);
				sqlInserir.setInt(1, obj.getUsuario().intValue());
				sqlInserir.setInt(2, obj.getEmpresa().getCodigo().intValue());
				sqlInserir.setString(3, obj.getTitulo());
				sqlInserir.setString(4, obj.getEndereco());
				sqlInserir.setString(5, obj.getIcone());
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
	public void alterar(final UsuarioFavoritosVO obj) throws Exception {
		UsuarioFavoritosVO.validarDados(obj);
		final String sql = "UPDATE UsuarioFavoritos set usuario=?, empresa=?, titulo=?, endereco=?, icone=? WHERE ((codigo = ?))";
		getConexao().getJdbcTemplate().update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement sqlAlterar = arg0.prepareStatement(sql);
				sqlAlterar.setInt(1, obj.getUsuario().intValue());
				sqlAlterar.setInt(2, obj.getEmpresa().getCodigo().intValue());
                                sqlAlterar.setString(3, obj.getTitulo());
				sqlAlterar.setString(4, obj.getEndereco());
				sqlAlterar.setString(5, obj.getIcone());
				sqlAlterar.setInt(6, obj.getCodigo());
				return sqlAlterar;
			}
		});
	}

	
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluir(UsuarioFavoritosVO obj) throws Exception {
		String sql = "DELETE FROM UsuarioFavoritos WHERE ((codigo = ?))";
		getConexao().getJdbcTemplate().update(sql, new Object[] { obj.getCodigo() });
	}

	
	public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception {
		String sqlStr = "SELECT * FROM UsuarioFavoritos WHERE codigo = " + valorConsulta.intValue() + " ORDER BY codigo";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sqlStr);
		return (montarDadosConsulta(tabelaResultado));
	}

	
	public static List montarDadosConsulta(SqlRowSet tabelaResultado) throws Exception {
		List vetResultado = new ArrayList(0);
		while (tabelaResultado.next()) {
			vetResultado.add(montarDados(tabelaResultado));
		}
		return vetResultado;
	}

	
	public static UsuarioFavoritosVO montarDados(SqlRowSet dadosSQL) throws Exception {
		UsuarioFavoritosVO obj = new UsuarioFavoritosVO();
		obj.setCodigo(dadosSQL.getInt("codigo"));
		obj.setUsuario(dadosSQL.getInt("usuario"));
		obj.getEmpresa().setCodigo(dadosSQL.getInt("empresa"));
		obj.setTitulo(dadosSQL.getString("titulo"));
		obj.setEndereco(dadosSQL.getString("endereco"));
		obj.setIcone(dadosSQL.getString("icone"));
		obj.setNovoObj(false);

		return obj;
	}


	
	
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void excluirUsuarioFavoritos(Integer usuario, String titulo, Integer empresa) throws Exception {
		String sql = "DELETE FROM UsuarioFavoritos WHERE (usuario = ? AND titulo = ? AND empresa = ?)";
		getConexao().getJdbcTemplate().update(sql, new Object[] { usuario, titulo, empresa });
	}


	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void incluirUsuarioFavoritos(Integer usuarioprm, List objetos) throws Exception {
		Iterator e = objetos.iterator();
		while (e.hasNext()) {
			UsuarioFavoritosVO obj = (UsuarioFavoritosVO) e.next();
			obj.setUsuario(usuarioprm);
			incluir(obj);
		}
	}

	public List consultarUsuarioFavoritos(Integer usuario) throws Exception {
		List objetos = new ArrayList(0);
		String sql = "SELECT * FROM UsuarioFavoritos WHERE usuario = ? ORDER BY codigo";
		SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[] { usuario });
		while (resultado.next()) {
			objetos.add(UsuarioFavoritos.montarDados(resultado));
		}
		return objetos;
	}

	public UsuarioFavoritosVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception {
		String sql = "SELECT * FROM UsuarioFavoritos WHERE codigo = ?";
		SqlRowSet tabelaResultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[] { codigoPrm });
		if (!tabelaResultado.next()) {
			throw new ConsistirException("Dados Não Encontrados ( UsuarioFavoritos ).");
		}
		return (montarDados(tabelaResultado));
	}

	/**
	 * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
	 * permissões de acesso as operações desta classe.
	 */
	public String getIdEntidade() {
		return UsuarioFavoritos.idEntidade;
	}

	/**
	 * Operação reponsável por definir um novo valor para o identificador desta classe. Esta alteração deve ser
	 * possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos distintos. Assim ao se verificar que
	 * Como o controle de acesso é realizado com base neste identificador,
	 */
	public void setIdEntidade(String idEntidade) {
		UsuarioFavoritos.idEntidade = idEntidade;
	}

    public UsuarioFavoritosVO novo() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}