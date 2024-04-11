package negocio.comuns.arquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service @Lazy
public abstract class FacadeManager {

    private static FacadeFactory facadeFactory;

    public static FacadeFactory getFacadeFactory() {
        return facadeFactory;
    }

    @Autowired
    public void setFacadeFactory(FacadeFactory facadeFactory) {
        FacadeManager.facadeFactory = facadeFactory;
    }
}
