/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.interfaces.arquitetura;

import negocio.comuns.arquitetura.PerfilAcessoVO;
import negocio.comuns.arquitetura.UsuarioVO;

/**
 *
 * @author Euripedes Doutor
 */
public interface ControleAcessoInterfaceFacade {

    /**
     * Operação padrão para realizar o ALTERAR de dados de uma entidade no BD.
     * Verifica e inicializa (se necessário) a conexão com o BD.
     * Verifica se o USUÁRIO logado possui permissão de acesso a operação ALTERAR.
     * @param idEntidade  Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception  Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    void alterar(String idEntidade) throws Exception;

    /**
     * Operação padrão para realizar o CONSULTAR de dados de uma entidade no BD.
     * Verifica e inicializa (se necessário) a conexão com o BD.
     * Verifica se o USUÁRIO logado possui permissão de acesso a operação CONSULTAR.
     * @param idEntidade  Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception  Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    void consultar(String idEntidade, boolean verificarAcesso) throws Exception;

    /**
     * Operação padrão para realizar o EMITIR UM Relatório de dados de uma entidade no BD.
     * Verifica e inicializa (se necessário) a conexão com o BD.
     * Verifica se o USUÁRIO logado possui permissão de acesso a operação EMITIRRELATORIO.
     * @param idEntidade  Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception  Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    void emitirRelatorio(String idEntidade, boolean verificarAcesso) throws Exception;

    /**
     * Operação padrão para realizar o EXCLUIR de dados de uma entidade no BD.
     * Verifica e inicializa (se necessário) a conexão com o BD.
     * Verifica se o USUÁRIO logado possui permissão de acesso a operação EXCLUIR.
     * @param idEntidade  Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception  Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    void excluir(String idEntidade) throws Exception;

    String getIdEntidade();

    PerfilAcessoVO getPerfilAcesso();

    UsuarioVO getUsuario();

    /**
     * Operação padrão para realizar o INCLUIR de dados de uma entidade no BD.
     * Verifica e inicializa (se necessário) a conexão com o BD.
     * Verifica se o USUÁRIO logado possui permissão de acesso a operação INCLUIR.
     * @param idEntidade  Nome da entidade para a qual se deseja realizar a operação.
     * @exception Exception  Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    void incluir(String idEntidade) throws Exception;

    void setPerfilAcesso(PerfilAcessoVO aPerfilAcesso);

    void setSomenteUsuario(UsuarioVO aUsuario) throws Exception;

    void setUsuario(UsuarioVO aUsuario) throws Exception;

    /**
     * Operação padrão para verificar acesso do USUÁRIO a determinada funcionalidade registrada
     * no Perfil de Acesso do USUÁRIO. Pode ser, por exemplo, alterar um valor de um campo.
     * Neste caso deverá existir no PerfilAcesso do USUÁRIO o key (apelido) identificando esta
     * funcionalidade. Esta funcinalidade deverá estar gravada com a opção TOTAL ou ALTERAR
     * para garantir que o USUÁRIO tenha acesso é mesma.
     * @param funcionalidade Nome da funcionalidade para a qual se deseja realizar a verificação de permissão do USUÁRIO.
     * @exception Exception  Caso haja problemas de conexão ou restrição de acesso a esta operação.
     */
    void verificarPermissaoUsuarioFuncionalidade(String funcionalidade) throws Exception;

    public void emitirRelatorio(String idEntidade) throws Exception;
}
