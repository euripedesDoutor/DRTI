/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.facade.jdbc.arquitetura;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoaderListener;

/**
 *
 * @author Euripedes Doutor
 */
@Service
public class ContextoAplicacao extends ContextLoaderListener{
    public String getNomeApp(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //return getCurrentWebApplicationContext().getServletContext().getContextPath().substring(1);
        return request.getContextPath().substring(1);
    }

}
