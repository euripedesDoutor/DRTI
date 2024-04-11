package negocio.interfaces.cadastro;
import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import java.util.List;

public interface ConfiguracaoSistemaInterfaceFacade {
	

    public ConfiguracaoSistemaVO novo() throws Exception;
    public void incluir(ConfiguracaoSistemaVO obj) throws Exception;
    public void alterar(ConfiguracaoSistemaVO obj) throws Exception;
    public void excluir(ConfiguracaoSistemaVO obj) throws Exception;
    public ConfiguracaoSistemaVO consultarPorChavePrimaria(Integer codigo, Integer empresa) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, Integer empresa, boolean controlarAcesso) throws Exception;
    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception ;
    public List consultarPorNomeFantasiaEmpresa(String valorConsulta, Integer empresa) throws Exception;
    public ConfiguracaoSistemaVO consultarSeExisteConfiguracaoSistema(Integer empresa) throws Exception;
    public ConfiguracaoSistemaVO consultarPorEmpresa(Integer empresa) throws Exception;
    public void setIdEntidade(String aIdEntidade);
    public ConfiguracaoSistemaVO consultarConfiguracaoSistema(Integer valorConsulta, boolean controlarAcesso) throws Exception;
}