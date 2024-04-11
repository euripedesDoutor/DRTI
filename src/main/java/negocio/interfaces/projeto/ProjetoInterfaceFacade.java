package negocio.interfaces.projeto;

import negocio.comuns.projeto.ProjetoVO;
import java.util.List;

/**
*
* @author DOCTORCODE
*/
public interface ProjetoInterfaceFacade {

    public ProjetoVO novo() throws Exception;
    public void incluir(ProjetoVO obj) throws Exception;
    public void alterar(ProjetoVO obj) throws Exception;
    public void excluir(ProjetoVO obj) throws Exception;
    public ProjetoVO consultarPorChavePrimaria(Integer codigo, int nivelMontarDados) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public void setIdEntidade(String idEntidade);
}
