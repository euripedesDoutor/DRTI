package negocio.interfaces.projeto;

import negocio.comuns.projeto.AtributosVO;
import negocio.comuns.projeto.ProjetoVO;
import negocio.comuns.projeto.ModuloVO;
import negocio.comuns.projeto.EntidadeVO;
import java.util.List;

/**
*
* @author DOCTORCODE
*/
public interface AtributosInterfaceFacade {

    public AtributosVO novo() throws Exception;
    public void incluir(AtributosVO obj) throws Exception;
    public void alterar(AtributosVO obj) throws Exception;
    public void excluir(AtributosVO obj) throws Exception;
    public void incluirAtributos(EntidadeVO obj) throws Exception;
    public void alterarAtributos(EntidadeVO obj) throws Exception;
    public void excluirAtributosPorEntidade(EntidadeVO obj) throws Exception;
    public AtributosVO consultarPorChavePrimaria(Integer codigo, int nivelMontarDados) throws Exception;
    public List consultarPorEntidade(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorNomeApresentar(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorTipoCampo(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorTipoComponente(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorProjeto(ProjetoVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public List consultarPorModulo(ModuloVO valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;
    public void setIdEntidade(String idEntidade);
}
