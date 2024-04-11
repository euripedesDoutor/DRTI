/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.interfaces.arquitetura;

import java.util.List;
import negocio.comuns.arquitetura.UsuarioFavoritosVO;

/**
 *
 * @author Euripedes Doutor
 */
public interface UsuarioFavoritosInterfaceFacade {

    /**
     * Operação responsável por alterar no BD os dados de um objeto da classe <code>UsuarioFavoritosVO</code>.
     * Sempre utiliza a chave primária da classe como atributo para localização do registro a ser alterado.
     * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>alterar</code> da superclasse.
     * @param obj    Objeto da classe <code>UsuarioFavoritosVO</code> que será alterada no banco de dados.
     * @exception Execption Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    void alterar(UsuarioFavoritosVO obj) throws Exception;

    /**
     * Operação responsável por localizar um objeto da classe <code>UsuarioFavoritosVO</code>
     * através de sua chave primária.
     * @exception Exception Caso haja problemas de conexão ou localização do objeto procurado.
     */
    UsuarioFavoritosVO consultarPorChavePrimaria(Integer codigoPrm) throws Exception;

    /**
     * Responsável por realizar uma consulta de <code>UsuarioFavoritos</code> através do valor do atributo
     * <code>Integer codigo</code>. Retorna os objetos com valores iguais ou superiores ao parâmetro fornecido.
     * Faz uso da operação <code>montarDadosConsulta</code> que realiza o trabalho de prerarar o List resultante.
     * @param   controlarAcesso Indica se a aplicação deverá verificar se o usuário possui permissão para esta consulta ou não.
     * @return  List Contendo vários objetos da classe <code>UsuarioFavoritosVO</code> resultantes da consulta.
     * @exception Exception Caso haja problemas de conexão ou restrição de acesso.
     */
    List consultarPorCodigo(Integer valorConsulta, boolean controlarAcesso) throws Exception;

    
   
    /**
     * Operação responsável por consultar todos os <code>UsuarioFavoritosVO</code> relacionados a um objeto da classe <code>cadastro.Cliente</code>.
     * @param cliente  Atributo de <code>cadastro.Cliente</code> a ser utilizado para localizar os objetos da classe <code>UsuarioFavoritosVO</code>.
     * @return List  Contendo todos os objetos da classe <code>UsuarioFavoritosVO</code> resultantes da consulta.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    List consultarUsuarioFavoritos(Integer usuario) throws Exception;

    /**
     * Operação responsável por excluir no BD um objeto da classe <code>UsuarioFavoritosVO</code>.
     * Sempre localiza o registro a ser excluído através da chave primária da entidade.
     * Primeiramente verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>excluir</code> da superclasse.
     * @param obj    Objeto da classe <code>UsuarioFavoritosVO</code> que será removido no banco de dados.
     * @exception Execption Caso haja problemas de conexão ou restrição de acesso.
     */
    void excluir(UsuarioFavoritosVO obj) throws Exception;

    /**
     * Operação responsável por excluir todos os objetos da <code>UsuarioFavoritosVO</code> no BD.
     * Faz uso da operação <code>excluir</code> disponível na classe <code>UsuarioFavoritos</code>.
     * @param <code>cliente</code> campo chave para exclusão dos objetos no BD.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void excluirUsuarioFavoritos(Integer usuario, String titulo, Integer empresa) throws Exception;

    /**
     * Operação reponsável por retornar o identificador desta classe.
     * Este identificar é utilizado para verificar as permissões de acesso as operações desta classe.
     */
    String getIdEntidade();

    /**
     * Operação responsável por incluir no banco de dados um objeto da classe <code>UsuarioFavoritosVO</code>.
     * Primeiramente valida os dados (<code>validarDados</code>) do objeto. Verifica a conexão com o banco de dados e a permissão do usuário
     * para realizar esta operacão na entidade.
     * Isto, através da operação <code>incluir</code> da superclasse.
     * @param obj  Objeto da classe <code>UsuarioFavoritosVO</code> que será gravado no banco de dados.
     * @exception Exception Caso haja problemas de conexão, restrição de acesso ou validação de dados.
     */
    void incluir(UsuarioFavoritosVO obj) throws Exception;

    /**
     * Operação responsável por incluir objetos da <code>UsuarioFavoritosVO</code> no BD.
     * Garantindo o relacionamento com a entidade principal <code>cadastro.Cliente</code> através do atributo de vínculo.
     * @param objetos List contendo os objetos a serem gravados no BD da classe.
     * @exception Exception  Erro de conexão com o BD ou restrição de acesso a esta operação.
     */
    void incluirUsuarioFavoritos(Integer usuarioprm, List objetos) throws Exception;

    /**
     * Operação responsável por retornar um novo objeto da classe <code>UsuarioFavoritosVO</code>.
     */
    UsuarioFavoritosVO novo() throws Exception;

    /**
     * Operação reponsável por definir um novo valor para o identificador desta classe.
     * Esta alteração deve ser possível, pois, uma mesma classe de negócio pode ser utilizada com objetivos
     * distintos. Assim ao se verificar que Como o controle de acesso é realizado com base neste identificador,
     */
    void setIdEntidade(String idEntidade);

}
