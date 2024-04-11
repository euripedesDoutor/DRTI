package negocio.interfaces.arquitetura;

import java.util.Date;
import negocio.comuns.arquitetura.LogVO;
import java.util.List;

/**
 * Interface reponsável por criar uma estrutura padrão de comunicação entre a camada de controle
 * e camada de negócio (em especial com a classe Facade). Com a utilização desta interface
 * é possível substituir tecnologias de uma camada da aplicação com mínimo de impacto nas demais.
 * Além de padronizar as funcionalidades que devem ser disponibilizadas pela camada de negócio, por intermédio
 * de sua classe Facade (responsável por persistir os dados das classes VO).
 */
public interface LogInterfaceFacade {

    public LogVO novo() throws Exception;

    public void incluir(LogVO obj) throws Exception;

    public void excluir(LogVO obj) throws Exception;

    public LogVO consultarPorChavePrimaria(Integer codigo, int nivelMontarDados) throws Exception;

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorNomeEntidade(String nomeEntidade, Date dataInicio, Date dataFim, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorNomeEntidade(String nomeEntidade, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorNomeEntidadeNomeCampoChavePrimaria(String nomeEntidade, String nomeCampo, String chavePrimaria, int nivelMontarDados) throws Exception;

    public List consultarPorCodigoEntidade(Integer codigoEntidade, Date dataInicio, Date dataFim, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public LogVO consultarPorNomeCodigoEntidade(String nomeEntidade, Integer codigoEntidade, Date dataInicio, Date dataFim, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorNomeCodigoEntidade(String nomeEntidade, Integer codigoEntidade, Date dataInicio, Date dataFim, int nivelMontarDados) throws Exception;

    public List consultarPorConteudoLog(String valorConsultar, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorResponsavel(String valorConsultar, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorOpercao(String valorConsultar, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public void setIdEntidade(String aIdEntidade);

    public List consultarAlteracoesPrecoCustoProduto(Integer codigoProduto, Integer nivelMontarDados, Integer empresa) throws Exception;

    public List consultarAlteracoesContasReceber(Integer contaReceber, Integer nivelMontarDados, Integer empresa) throws Exception;

    public List consultarAlteracoesTributacao(Integer codigoTributacao, Integer nivelMontarDados, Integer empresa) throws Exception;

    public List consultarAlteracoesPedidoVenda(Integer pedidoVenda, Integer nivelMontarDados, Integer empresa) throws Exception;

    public List consultarAlteracoesTabelaPreco(Integer codigoTabelaPreco, Integer nivelMontarDados, Integer empresa) throws Exception;

    public List consultarAlteracoesProduto(Integer codigoProduto, Integer nivelMontarDados, Integer empresa) throws Exception;
}
