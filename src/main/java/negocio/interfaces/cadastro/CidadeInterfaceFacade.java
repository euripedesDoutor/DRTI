package negocio.interfaces.cadastro;

import negocio.comuns.cadastro.CidadeVO;
import negocio.comuns.utilitarias.*;
import java.util.List;

/**
 * Interface reponsável por criar uma estrutura padrão de comunicação entre a camada de controle
 * e camada de negócio (em especial com a classe Facade). Com a utilização desta interface
 * é possível substituir tecnologias de uma camada da aplicação com mínimo de impacto nas demais.
 * Além de padronizar as funcionalidades que devem ser disponibilizadas pela camada de negócio, por intermédio
 * de sua classe Facade (responsável por persistir os dados das classes VO).
 */
public interface CidadeInterfaceFacade {

    public CidadeVO novo() throws Exception;

    public void incluir(CidadeVO obj) throws Exception;

    public void alterar(CidadeVO obj) throws Exception;

    public void excluir(CidadeVO obj) throws Exception;

    public CidadeVO consultarPorChavePrimaria(Integer codigo) throws Exception;

    public CidadeVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception;

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorNome(String valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorNome(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorEstado(String valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorCodigoIBGE(String valorConsulta, boolean controlarAcesso) throws Exception;

    public CidadeVO consultarPorCodigoExatoIBGE(String valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorEstado_OrdernarPorCidade(String valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarParaRelatorio(String valorConsulta, boolean controlarAcesso) throws Exception;

    public void setIdEntidade(String aIdEntidade);
}
