package negocio.facade.jdbc.arquitetura;

import negocio.comuns.arquitetura.SuperArquitetura;
import negocio.facade.jdbc.utilitarias.Conexao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Responsável por implementar características comuns relativas a conexão com o banco de dados.
 */
public abstract class SuperFacadeJDBC extends SuperArquitetura {

    /** con Conexão com o BD. Única para todas as classes filhas desta classe. */
    private static Conexao conexao;

    public static Conexao getConexao() throws Exception {
        if(conexao == null){
            DataSource dataSource = null;
            JndiTemplate jndiTemplate = new JndiTemplate();
            try {
                dataSource = (DataSource) jndiTemplate.lookup("DISCOVERY");
            } catch (NamingException e) {
                throw new NamingException(e.getMessage());
            }
            conexao = new Conexao();
            conexao.setJdbcTemplate(new JdbcTemplate());
            conexao.getJdbcTemplate().setDataSource(dataSource);
        }
        return conexao;
    }

    @Autowired
    public void setConexao(Conexao conexao) {
        SuperFacadeJDBC.conexao = conexao;
    }

    public SuperFacadeJDBC() throws Exception {
    }

    public String getIdEntidade() {
        return "Entidade";
    }

    public static Enum montarEnumerador(Enum[] lista, String valor) throws Exception {
        for (Enum e : lista) {
            if (e.toString().equals(valor)) {
                return e;
            }
        }
        return null;
    }
}
