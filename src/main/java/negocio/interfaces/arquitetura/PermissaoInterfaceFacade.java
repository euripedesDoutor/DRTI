/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.interfaces.arquitetura;

import java.util.List;
import negocio.comuns.arquitetura.PermissaoVO;

/**
 *
 * @author Euripedes Doutor
 */
public interface PermissaoInterfaceFacade {

    /**
     * Operação responsável por alterar no BD os dados de um objeto da classe <code>PermissaoVO</code>.
     * Sempre utiliza a chave primária da classe como atributo para localização do registro a ser alterado.
     * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>alterar</code> da superclasse.
     * @param obj    Objeto da classe <code>PermissaoVO</code> que será alterada no banco de dados.
     * @exception Execption Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    void alterar(PermissaoVO obj) throws Exception;

    /**
     * Operação responsável por alterar todos os objetos da <code>PermissaoVO</code> contidos em um Hashtable no BD.
     * Faz uso da operação <code>excluirPermissaos</code> e <code>incluirPermissaos</code> disponíveis na classe <code>Permissao</code>.
     * @param objetos  List com os objetos a serem alterados ou incluídos no BD.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void alterarPermissaos(Integer codPerfilAcesso, List objetos) throws Exception;

    /**
     * Operação responsável por consultar todos os <code>PermissaoVO</code> relacionados a um objeto da classe <code>arquitetura.PerfilAcesso</code>.
     * @param codPerfilAcesso  Atributo de <code>arquitetura.PerfilAcesso</code> a ser utilizado para localizar os objetos da classe <code>PermissaoVO</code>.
     * @return List  Contendo todos os objetos da classe <code>PermissaoVO</code> resultantes da consulta.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    List consultarPermissaos(Integer codPerfilAcesso) throws Exception;

    /**
     * Operação responsável por localizar um objeto da classe <code>PermissaoVO</code>
     * através de sua chave primária.
     * @exception Exception Caso haja problemas de conexão ou localização do objeto procurado.
     */
    PermissaoVO consultarPorChavePrimaria(Integer codPerfilAcessoPrm, String nomeEntidadePrm) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>Permissao</code> através do valor do atributo
     * <code>String nomeEntidade</code>. Retorna os objetos, com iíicio do valor do atributo idêntico ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @param   controlarAcesso Indica se a aplicação deverá verificar se o usuário possui permissão para esta consulta ou não.
     * @return  List Contendo vários objetos da classe <code>PermissaoVO</code> resultantes da consulta.
     * @exception Exception Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorNomeEntidade(String valorConsulta, boolean controlarAcesso) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>Permissao</code> através do valor do atributo
     * <code>nome</code> da classe <code>PerfilAcesso</code>
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @return  List Contendo vários objetos da classe <code>PermissaoVO</code> resultantes da consulta.
     * @exception Execption Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorNomePerfilAcesso(String valorConsulta) throws Exception;

    
    /**
     * Operação responsável por excluir no BD um objeto da classe <code>PermissaoVO</code>.
     * Sempre localiza o registro a ser excluído através da chave primária da entidade.
     * Primeiramente verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>excluir</code> da superclasse.
     * @param obj    Objeto da classe <code>PermissaoVO</code> que será removido no banco de dados.
     * @exception Execption Caso haja problemas de conexão ou restrição de acesso.
     */
    void excluir(PermissaoVO obj) throws Exception;

    /**
     * Operação responsável por excluir todos os objetos da <code>PermissaoVO</code> no BD.
     * Faz uso da operação <code>excluir</code> disponível na classe <code>Permissao</code>.
     * @param <code>codPerfilAcesso</code> campo chave para exclusão dos objetos no BD.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void excluirPermissaos(Integer codPerfilAcesso) throws Exception;

    /**
     * Operação reponsável por retornar o identificador desta classe.
     * Este identificar é utilizado para verificar as permissões de acesso as operações desta classe.
     */
    String getIdEntidade();

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>PermissaoVO</code>.
     * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>incluir</code> da superclasse.
     * @param obj  Objeto da classe <code>PermissaoVO</code> que será gravado no banco de dados.
     * @exception Exception Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    void incluir(PermissaoVO obj) throws Exception;

    /**
     * Operação responsável por incluir objetos da <code>PermissaoVO</code> no BD.
     * Garantindo o relacionamento com a entidade principal <code>arquitetura.PerfilAcesso</code> através do atributo de vínculo.
     * @param objetos List contendo os objetos a serem gravados no BD da classe.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void incluirPermissaos(Integer codPerfilAcessoPrm, List objetos) throws Exception;

    /**
     * Operação responsável por retornar um novo objeto da classe <code>PermissaoVO</code>.
     */
    PermissaoVO novo() throws Exception;

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe.
     * Esta alteração deve ser possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos
     * distintos. Assim ao se verificar que Como o controle de acesso é realizado com base neste identificador,
     */
    void setIdEntidade(String idEntidade);

}
