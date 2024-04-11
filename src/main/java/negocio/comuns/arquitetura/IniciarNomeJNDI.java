/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.comuns.arquitetura;

import negocio.facade.jdbc.arquitetura.ContextoAplicacao;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Service;

/**
 *
 * @author Euripedes Doutor
 */
@Service

public class IniciarNomeJNDI extends JndiObjectFactoryBean {

    public IniciarNomeJNDI() {
        
    }

    public IniciarNomeJNDI(ContextoAplicacao contextoAplicacao) {
        setJndiName(contextoAplicacao.getNomeApp());
    }

}
