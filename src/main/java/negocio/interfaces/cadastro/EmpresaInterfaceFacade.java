package negocio.interfaces.cadastro;

import java.util.Date;
import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.*;
import java.util.List;

/**
 * Interface reponsável por criar uma estrutura padrão de comunicação entre a camada de controle
 * e camada de negócio (em especial com a classe Facade). Com a utilização desta interface
 * é possível substituir tecnologias de uma camada da aplicação com mínimo de impacto nas demais.
 * Além de padronizar as funcionalidades que devem ser disponibilizadas pela camada de negócio, por intermédio
 * de sua classe Facade (responsável por persistir os dados das classes VO).
 */
public interface EmpresaInterfaceFacade {

    public EmpresaVO novo() throws Exception;

    public void incluir(EmpresaVO obj) throws Exception;

    public void alterar(EmpresaVO obj) throws Exception;

    public void alterarDataBloqueiosTodasEmpresasBancoDados(final EmpresaVO obj) throws Exception;

    public Date consultarDataBloqueio(Integer codigoEmpresa) throws Exception;

    public void excluir(EmpresaVO obj) throws Exception;

    public EmpresaVO consultarPorChavePrimaria(Integer codigo) throws Exception;
    
    public EmpresaVO consultarPorChavePrimaria(Integer codigoPrm, int nivelMontarDados) throws Exception;

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorNomeFantasia(String valorConsulta, boolean controlarAcesso) throws Exception;
    public List consultarPorNomeFantasia(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorNomeFantasiaSituacao(String valorConsulta, String situacao, boolean controlarAcesso) throws Exception;

    public List consultarPorEndereco(String valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorNomeCidade(String valorConsulta) throws Exception;

    public List consultarPorCNPJ(String valorConsulta, boolean controlarAcesso) throws Exception;

    public void setIdEntidade(String aIdEntidade);

    public void setarCodigoContabilista(Integer codigoEmpresa, Integer codigoContabilista) throws Exception;
}
