/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.arquitetura;

import java.util.Comparator;
import org.apache.commons.beanutils.BeanComparator;

/**
 *
 * @author Euripedes Doutor
 */
public class SelectItemOrdemInteger extends BeanComparator {
    
    public SelectItemOrdemInteger() {
        super("label", new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer)o1).compareTo((Integer)o2);
            }});
    }
    public SelectItemOrdemInteger(String campo) {
        super(campo, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Integer)o1).compareTo((Integer)o2);
            }});
    }
    
}