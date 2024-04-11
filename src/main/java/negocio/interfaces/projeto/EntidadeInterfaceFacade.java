package negocio.interfaces.projeto;

import negocio.comuns.projeto.EntidadeVO;
import negocio.comuns.projeto.ProjetoVO;
import negocio.comuns.projeto.ModuloVO;
import java.util.List;

/**
*
* @author DOCTORCODE
*/
public interface EntidadeInterfaceFacade {

    public EntidadeVO novo() throws Exception;
    public void incluir(EntidadeVO obj) throws Exception;
    public void alterar(EntidadeVO obj) throws Exception;
    public void excluir(EntidadeVO obj) throws Exception;
    public EntidadeVO consultarPorChavePrimaria(Integer codigo, int nivelMontarDados) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorProjeto(ProjetoVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorModulo(ModuloVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public void setIdEntidade(String idEntidade);
}
