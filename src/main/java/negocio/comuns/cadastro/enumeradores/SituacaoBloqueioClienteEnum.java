/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.comuns.cadastro.enumeradores;

/**
 *
 * @author Euripedes Doutor
 */
public enum SituacaoBloqueioClienteEnum {

    BLOQUEADO,
    LIBERADO;
    
    public String getPrefix() {
        return ("enum_SituacaoBloqueioClienteEnum_");
    }

    public String getPropertyKey() {
        return getPrefix() + this.toString();
    }
}
