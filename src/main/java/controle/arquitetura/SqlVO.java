/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controle.arquitetura;

/**
 *
 * @author Euripedes Doutor
 */
public class SqlVO {
    private String sql;
    private Integer codigo;

    public SqlVO(Integer codigo, String sql) {
        this.sql = sql;
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        if (codigo == null){
            codigo = 0;
        }
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getSql() {
        if (sql == null){
            sql = "";
        }
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }



}
