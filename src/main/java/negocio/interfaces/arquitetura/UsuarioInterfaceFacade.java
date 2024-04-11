package negocio.interfaces.arquitetura;

import negocio.comuns.arquitetura.UsuarioVO;
import negocio.comuns.utilitarias.*;
import java.util.List;

public interface UsuarioInterfaceFacade {

    public UsuarioVO novo() throws Exception;

    public void incluir(UsuarioVO obj) throws Exception;

    public void alterar(UsuarioVO obj) throws Exception;

    public void excluir(UsuarioVO obj) throws Exception;

    public void bloquearUsuario(final String username) throws Exception;

    public UsuarioVO consultarPorChavePrimaria(Integer codigo) throws Exception;

    public UsuarioVO consultarPorChavePrimaria(Integer codigoPrm, Integer nivelMontarDados) throws Exception;

    public void alterarSenha(UsuarioVO obj, Boolean validarSenhaAnterior) throws Exception;

    public List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorNome(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public List consultarPorUsername(String valorConsulta, boolean controlarAcesso, int nivelMontarDados) throws Exception;

    public UsuarioVO consultarUsuarioPorUsername(String valorConsulta, boolean controlarAcesso) throws Exception;

    public List consultarPorNomePerfilAcesso(String valorConsulta) throws Exception;

    public List consultarPorNomePerfilAcessoRichModalPanel(String valorConsulta) throws Exception;

    public List consultarUsuariosEmissaoNotaFiscal() throws Exception;

    public void setIdEntidade(String aIdEntidade);

    public List consultarPorCodigoPerfilAcesso(Integer valorConsulta, int nivelMontarDados) throws Exception;

    public List consultarPorSituacaoAcesso(String situacaoAcesso, int nivelMontarDados) throws Exception;
}
