package negocio.facade.jdbc.arquitetura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import negocio.comuns.arquitetura.LogVO;
import negocio.comuns.arquitetura.SuperVO;
import negocio.comuns.cadastro.EmpresaVO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * SuperClasse padrão para classes de persistência que encapsulam todas as operações de manipulação dos dados.
 * Responsável por implementar características comuns a todas as operações das classes de persistência. Como por
 * exemplo: verificar a permissão do usuário para realizar uma determinada operação. Possui implementações parciais das
 * operações incluir, alterar, consultar e excluir.
 */
public abstract class SuperEntidade extends ControleAcesso {

    public SuperEntidade() throws Exception {
    }

//    public EmpresaVO getEmpresaLogado() throws Exception {
//        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
//        return loginControle.getEmpresa();
//    }
//
//    public UsuarioVO getUsuarioLogado() throws Exception {
//        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
//        return loginControle.getUsuario();
//    }
//
//     public ConfiguracaoSistemaVO getConfiguracaoEmpresaLogado(){
//        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
//        return loginControle.getConfiguracaoSistema();
//    }
    public Integer gerarChavePrimariaInt(String nomeEntidade, String chavePrimaria) throws Exception {
        int novoCodigo = 1;
        String sql = "select max(" + chavePrimaria + ") from " + nomeEntidade;
        SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
        if (resultado.next()) {
            int ultimoCodigo = resultado.getInt(1);
            novoCodigo = ultimoCodigo + 1;
        }
        return (new Integer(novoCodigo));
    }

    public Integer obterCodigoAuxiliar(String nomeEntidade, Integer empresa) throws Exception {
        int novoCodigo = 0;
        String sql = "select max(codigoAuxiliar) from " + nomeEntidade + " WHERE empresa = " + empresa;
        SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
        if (resultado.next()) {
            int ultimoCodigo = resultado.getInt(1);
            novoCodigo = ultimoCodigo + 1;
        }
        return (new Integer(novoCodigo));
    }

    public static Integer obterCodigoSQL(String colunaBD) {
        try {
            String sql = "SELECT " + colunaBD + " FROM configuracaoSistema";
            SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
            if (resultado.next()) {
                return resultado.getInt(1);
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public static void executarSql(String sql) throws Exception {
        getConexao().getJdbcTemplate().execute(sql);
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public static void registrarLogObjetoVO(SuperVO ObjetoVO) {
        try {
            Log logFacade = new Log();
            List lista = ObjetoVO.gerarLogAlteracaoObjetoVO();
            Iterator i = lista.iterator();
            while (i.hasNext()) {
                LogVO log = (LogVO) i.next();
                logFacade.incluir(log);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Método responsável pelo registro do LOG de alteração do objeto selecionado, esse registro é feito de acordo com
     * as annotations que foram adicionadas no VO do objeto. No método é gerado uma lista com objetos do tipo LogVO,
     * onde cada objeto desta lista é incluida na tabela Log no BD (Banco de Dados) com a sua operação.
     *
     * @param ObjetoVO
     *            Objeto a ser validado para geração o LOG.
     * @author Kelvin Cantarelli dos Santos
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public static void registrarLogObjetoVO(SuperVO ObjetoVO, Integer codigoCliente, String nomeEntidade) {
        try {
            Log logFacade = new Log();
            List lista = ObjetoVO.gerarLogAlteracaoObjetoVO();
            Iterator i = lista.iterator();
            while (i.hasNext()) {
                LogVO log = (LogVO) i.next();
                log.setChavePrimaria(codigoCliente.toString());
                log.setNomeEntidade(nomeEntidade);
                logFacade.incluir(log);
            }
        } catch (Exception e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }

    /**
     * Método responsável pelo registro do LOG de alteração do objeto selecionado, esse registro é feito de acordo com
     * as annotations que foram adicionadas no VO do objeto. No método é gerado uma lista com objetos do tipo LogVO,
     * onde cada objeto desta lista é incluida na tabela Log no BD (Banco de Dados) com a sua operação.
     *
     * @param ObjetoVO
     *            Objeto a ser validado para geração o LOG.
     * @author Kelvin Cantarelli dos Santos
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public static void registrarLogObjetoVO(List lista, Boolean nova, Integer codigoPlanoAtual) {
        try {
            Log logFacade = new Log();
            Iterator i = lista.iterator();
            while (i.hasNext()) {
                LogVO log = (LogVO) i.next();
                if (nova) {
                    logFacade.incluir(log);
                } else {
                    log.setChavePrimaria(codigoPlanoAtual.toString());
                    logFacade.alterar(log);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Método responsável pelo registro do LOG de exclusão do objeto selecionado. No método é gerado uma lista com
     * objetos do tipo LogVO, onde cada objeto desta lista é incluida na tabela Log no BD (Banco de Dados) com a sua
     * operação.
     *
     * @param ObjetoVO
     *            Objeto a ser validado para geração o LOG.
     * @author Kelvin Cantarelli dos Santos
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public static void registrarLogExclusaoObjetoVO(SuperVO ObjetoVO) {
        try {
            Log logFacade = new Log();
            List lista = ObjetoVO.gerarLogExclusaoObjetoVO();
            Iterator i = lista.iterator();
            while (i.hasNext()) {
                LogVO log = (LogVO) i.next();
                logFacade.incluir(log);
            }
        } catch (Exception e) {
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public static void incluirPesoBalanca(final String id, final Double peso) throws Exception {
        final String sql = "INSERT INTO pesoBalanca( id, peso, lancado) VALUES ( ?, ?, ?) returning codigo";
        getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                sqlInserir.setString(1, id);
                sqlInserir.setDouble(2, peso);
                sqlInserir.setBoolean(3, false);
                return sqlInserir;
            }
        }, new ResultSetExtractor() {

            public Object extractData(ResultSet arg0) throws SQLException, DataAccessException {
                return null;
            }
        });
    }

    public static List consultarPesoBalanca(String id) throws Exception {
        List objetos = new ArrayList(0);
        String sql = "SELECT codigo, peso FROM pesoBalanca WHERE id = ? AND lancado = false";
        SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql, new Object[]{id});
        while (resultado.next()) {
            objetos.add(resultado.getDouble("peso"));
            sql = "DELETE FROM pesoBalanca WHERE ((codigo = ?))";
            getConexao().getJdbcTemplate().update(sql, new Object[]{true, resultado.getInt("codigo")});
        }
        return objetos;
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public static void incluirAcessoUsuario(final Integer usuario, final Integer empresa, final String navegador) throws Exception {
        final String sql = "INSERT INTO controleAcessoUsuario( usuario, empresa, navegador) VALUES ( ?, ?, ?) returning codigo";
        getConexao().getJdbcTemplate().query(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
                PreparedStatement sqlInserir = arg0.prepareStatement(sql);
                sqlInserir.setInt(1, usuario);
                sqlInserir.setInt(2, empresa);
                sqlInserir.setString(3, navegador);
                return sqlInserir;
            }
        }, new ResultSetExtractor() {

            public Object extractData(ResultSet arg0) throws SQLException, DataAccessException {
                return null;
            }
        });
    }

    public static void deletarAcessoUsuario(Integer usuario, Integer empresa, String navegador) throws Exception {
        String sql = "DELETE FROM controleAcessoUsuario WHERE usuario = " + usuario + " AND empresa = " + empresa + " AND navegador = '" + navegador + "'";
        getConexao().getJdbcTemplate().update(sql);
    }
    public static void deletarAcessoUsuarioSemNavegador(Integer usuario, Integer empresa) throws Exception {
        String sql = "DELETE FROM controleAcessoUsuario WHERE usuario = " + usuario + " AND empresa = " + empresa;
        getConexao().getJdbcTemplate().update(sql);
    }


    public static void executarCorrecaoQuantidadeCarregamento() throws Exception {
        String sql = "UPDATE itemcarregamento SET peso = d.peso, quantidade = d.volume FROM (\n"
                + "select carregamento, itemcarregamento, produto.codigo AS produto, detalhamentoItemCarregamento.volume, detalhamentoItemCarregamento.volume * produto.pesoVolume AS peso\n"
                + "	from detalhamentoItemCarregamento\n"
                + "	INNER JOIN produto ON produto.codigo = detalhamentoItemCarregamento.produto) AS d\n"
                + "WHERE itemcarregamento.carregamento = d.carregamento\n"
                + "AND itemcarregamento.codigo = d.itemCarregamento\n"
                + "AND itemcarregamento.produto = d.produto";
        getConexao().getJdbcTemplate().update(sql);
    }

    public static void consultarAcessoUsuario(Integer usuario, Integer empresa, String navegador) throws Exception {
        String sql = "SELECT empresa FROM controleAcessoUsuario WHERE usuario = " + usuario + " AND navegador = '" + navegador + "'";
        SqlRowSet resultado = getConexao().getJdbcTemplate().queryForRowSet(sql);
        int i = 0;
        while (resultado.next()) {
            if (!empresa.equals(resultado.getInt("empresa"))) {
                EmpresaVO obj = getFacadeFactory().getEmpresaFacade().consultarPorChavePrimaria(resultado.getInt("empresa"));
                i = 1;
                throw new Exception("<br>Você já entrou na empresa " + obj.getNomeFantasia() + " neste navegador.<br>Acesse utilizando outro navegador ou saia da empresa informada.");
            } else {
                i = 1;
            }
        }
        if (i == 0) {
            incluirAcessoUsuario(usuario, empresa, navegador);
        }
    }
}
