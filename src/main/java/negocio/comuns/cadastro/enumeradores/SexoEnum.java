/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.comuns.cadastro.enumeradores;

/**
 *
 * @author OTIMIZE09-11
 */
public enum SexoEnum {
    NENHUM,
    MASCULINO,
    FEMININO;

    public String getPrefix(){
        return ("enum_SexoEnum_");
    }
    
    public String getPropertyKey(){
        return getPrefix() + this.toString();
    }

}
