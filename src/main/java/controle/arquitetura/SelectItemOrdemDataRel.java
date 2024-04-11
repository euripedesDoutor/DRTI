/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.arquitetura;

import java.util.Comparator;
import java.util.Date;
import org.apache.commons.beanutils.BeanComparator;

/**
 *
 * @author Euripedes Doutor
 */
public class SelectItemOrdemDataRel extends BeanComparator {
    
    public SelectItemOrdemDataRel() {
        super("data", new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Date)o1).compareTo((Date)o2);
            }});
    }
    
}