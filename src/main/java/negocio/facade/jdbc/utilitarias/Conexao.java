package negocio.facade.jdbc.utilitarias;

import java.sql.*;
import javax.faces.context.FacesContext;
import javax.naming.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Conexao {

    private String nomeJNDI = getNomeAplicacao();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Conexao() throws Exception {
    }

    public Connection getConexaoJNDI() throws Exception {

        Context ctx = new InitialContext();
        Connection conn = null;
        if (ctx == null) {
            throw new Exception("Servidor não disponível - Contexto JNDI não localizado.");
        }
        try {
            DataSource ds = (DataSource) ctx.lookup(nomeJNDI);
            conn = ds.getConnection();
        } catch (Exception e) {
            throw new Exception("JNDI Name inexistente. Não foi possível conectar o BD: "
                    + e.getMessage());
        }
        return conn;

    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplates) {

        this.jdbcTemplate = jdbcTemplates;

    }

    public Connection getConexao() throws Exception {
        return getConexaoJNDI();
    }

    public String getNomeAplicacao() {
        ServletContext servletContext = (ServletContext) this.context().getExternalContext().getContext();
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();

        return request.getContextPath().substring(1, request.getContextPath().length());
    }

    protected FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }
}
