package negocio.interfaces.projeto;

import negocio.comuns.projeto.ModuloVO;
import negocio.comuns.projeto.ProjetoVO;
import java.util.List;

/**
*
* @author DOCTORCODE
*/
public interface ModuloInterfaceFacade {

    public ModuloVO novo() throws Exception;
    public void incluir(ModuloVO obj) throws Exception;
    public void alterar(ModuloVO obj) throws Exception;
    public void excluir(ModuloVO obj) throws Exception;
    public void incluirModulo(ProjetoVO obj) throws Exception;
    public void alterarModulo(ProjetoVO obj) throws Exception;
    public void excluirModuloPorProjeto(ProjetoVO obj) throws Exception;
    public ModuloVO consultarPorChavePrimaria(Integer codigo, int nivelMontarDados) throws Exception;
    public List consultarPorProjeto(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorNomeApresentar(String valorConsulta, Integer projeto, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public void setIdEntidade(String idEntidade);
}
