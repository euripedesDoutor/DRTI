package negocio.interfaces.cadastro;

import negocio.comuns.cadastro.PaisVO;
import java.util.List;

/**
*
* @author DOCTORCODE
*/
public interface PaisInterfaceFacade {

    public PaisVO novo() throws Exception;
    public void incluir(PaisVO obj) throws Exception;
    public void alterar(PaisVO obj) throws Exception;
    public void excluir(PaisVO obj) throws Exception;
    public PaisVO consultarPorChavePrimaria(Integer codigo, int nivelMontarDados) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorNome(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorCodigoSped(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorCodigoSiscomex(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public void setIdEntidade(String idEntidade);
}
