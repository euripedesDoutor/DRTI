/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.interfaces.arquitetura;

import java.util.List;
import negocio.comuns.arquitetura.UsuarioPerfilAcessoVO;

/**
 *
 * @author Euripedes Doutor
 */
public interface UsuarioPerfilAcessoInterfaceFacade {

    /**
     * Operação responsável por alterar no BD os dados de um objeto da classe <code>UsuarioPerfilAcessoVO</code>.
     * Sempre utiliza a chave primária da classe como atributo para localização do registro a ser alterado.
     * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>alterar</code> da superclasse.
     * @param obj    Objeto da classe <code>UsuarioPerfilAcessoVO</code> que será alterada no banco de dados.
     * @exception Execption Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    void alterar(UsuarioPerfilAcessoVO obj) throws Exception;

    /**
     * Operação responsável por alterar todos os objetos da <code>UsuarioPerfilAcessoVO</code> contidos em um Hashtable no BD.
     * Faz uso da operação <code>excluirUsuarioPerfilAcessos</code> e <code>incluirUsuarioPerfilAcessos</code> disponíveis na classe <code>UsuarioPerfilAcesso</code>.
     * @param objetos  List com os objetos a serem alterados ou incluídos no BD.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void alterarUsuarioPerfilAcesso(Integer usuario, List objetos) throws Exception;

    /**
     * Operação responsável por localizar um objeto da classe <code>UsuarioPerfilAcessoVO</code>
     * através de sua chave primária.
     * @exception Exception Caso haja problemas de conexão ou localização do objeto procurado.
     */
    UsuarioPerfilAcessoVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>UsuarioPerfilAcesso</code> através do valor do atributo
     * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @param   controlarAcesso Indica se a aplicação deverá verificar se o usuário possui permissão para esta consulta ou não.
     * @return  List Contendo vários objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
     * @exception Exception Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>UsuarioPerfilAcesso</code> através do valor do atributo
     * <code>String endereco</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @param   controlarAcesso Indica se a aplicação deverá verificar se o usuário possui permissão para esta consulta ou não.
     * @return  List Contendo vários objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
     * @exception Exception Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorEndereco(String valorConsulta, boolean controlarAcesso) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>UsuarioPerfilAcesso</code> através do valor do atributo
     * <code>nome</code> da classe <code>Cliente</code>
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @return  List Contendo vários objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
     * @exception Execption Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorNomeCliente(String valorConsulta) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>UsuarioPerfilAcesso</code> através do valor do atributo
     * <code>nome</code> da classe <code>Cidade</code>
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @return  List Contendo vários objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
     * @exception Execption Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorNomeEmpresa(String valorConsulta) throws Exception;

    /**
     * Operação responsável por consultar todos os <code>UsuarioPerfilAcessoVO</code> relacionados a um objeto da classe <code>cadastro.Cliente</code>.
     * @param cliente  Atributo de <code>cadastro.Cliente</code> a ser utilizado para localizar os objetos da classe <code>UsuarioPerfilAcessoVO</code>.
     * @return List  Contendo todos os objetos da classe <code>UsuarioPerfilAcessoVO</code> resultantes da consulta.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    List consultarUsuarioPerfilAcesso(Integer usuario) throws Exception;

    /**
     * Operação responsável por excluir no BD um objeto da classe <code>UsuarioPerfilAcessoVO</code>.
     * Sempre localiza o registro a ser excluído através da chave primária da entidade.
     * Primeiramente verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>excluir</code> da superclasse.
     * @param obj    Objeto da classe <code>UsuarioPerfilAcessoVO</code> que será removido no banco de dados.
     * @exception Execption Caso haja problemas de conexão ou restrição de acesso.
     */
    void excluir(UsuarioPerfilAcessoVO obj) throws Exception;

    /**
     * Operação responsável por excluir todos os objetos da <code>UsuarioPerfilAcessoVO</code> no BD.
     * Faz uso da operação <code>excluir</code> disponível na classe <code>UsuarioPerfilAcesso</code>.
     * @param <code>cliente</code> campo chave para exclusão dos objetos no BD.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void excluirUsuarioPerfilAcesso(Integer usuario) throws Exception;

    /**
     * Operação reponsável por retornar o identificador desta classe.
     * Este identificar é utilizado para verificar as permissões de acesso as operações desta classe.
     */
    String getIdEntidade();

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>UsuarioPerfilAcessoVO</code>.
     * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>incluir</code> da superclasse.
     * @param obj  Objeto da classe <code>UsuarioPerfilAcessoVO</code> que será gravado no banco de dados.
     * @exception Exception Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    void incluir(UsuarioPerfilAcessoVO obj) throws Exception;

    /**
     * Operação responsável por incluir objetos da <code>UsuarioPerfilAcessoVO</code> no BD.
     * Garantindo o relacionamento com a entidade principal <code>cadastro.Cliente</code> através do atributo de vínculo.
     * @param objetos List contendo os objetos a serem gravados no BD da classe.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void incluirUsuarioPerfilAcesso(Integer usuarioprm, List objetos) throws Exception;

    /**
     * Operação responsável por retornar um novo objeto da classe <code>UsuarioPerfilAcessoVO</code>.
     */
    UsuarioPerfilAcessoVO novo() throws Exception;

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe.
     * Esta alteração deve ser possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos
     * distintos. Assim ao se verificar que Como o controle de acesso é realizado com base neste identificador,
     */
    void setIdEntidade(String idEntidade);

}
