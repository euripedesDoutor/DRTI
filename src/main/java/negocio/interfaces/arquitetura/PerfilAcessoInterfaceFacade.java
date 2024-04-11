package negocio.interfaces.arquitetura;
import negocio.comuns.arquitetura.PerfilAcessoVO;
import negocio.comuns.utilitarias.*;
import java.util.List;

/**
 * Interface reponsável por criar uma estrutura padrão de comunicação entre a camada de controle
 * e camada de negócio (em especial com a classe Facade). Com a utilização desta interface
 * é possível substituir tecnologias de uma camada da aplicação com mínimo de impacto nas demais.
 * Além de padronizar as funcionalidades que devem ser disponibilizadas pela camada de negócio, por intermédio
 * de sua classe Facade (responsável por persistir os dados das classes VO).
*/
public interface PerfilAcessoInterfaceFacade {
	

    public PerfilAcessoVO novo() throws Exception;
    public void incluir(PerfilAcessoVO obj) throws Exception;
    public void alterar(PerfilAcessoVO obj) throws Exception;
    public void excluir(PerfilAcessoVO obj) throws Exception;
    public PerfilAcessoVO consultarPorChavePrimaria(Integer codigo) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception;
    public PerfilAcessoVO consultarPorCodigoUsuarioEmpresa(Integer usuario, Integer empresa) throws Exception;
    public List consultarPorNome(String valorConsulta, boolean controlarAcesso) throws Exception;
    public void setIdEntidade(String aIdEntidade);
    public List consultarPorTipoPerfil(String valorConsulta) throws Exception;
}