/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package relatorio.negocio.jdbc.cadastro;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import relatorio.negocio.jdbc.arquitetura.SuperRelatorio;

/**
 * 
 * @author OTIMIZE
 */
@SuppressWarnings("unchecked")
@Repository
@Lazy
public class UsuarioRel extends SuperRelatorio {

	public UsuarioRel() throws Exception {
		// inicializarParametros();
		inicializarOrdenacoesRelatorio();
	}

	/**
	 * Método responsável por gerar os dados do relatório, retornando os mesmos no formato XML para apresentação por
	 * meio do iReport.
	 */
	public String emitirRelatorio() throws Exception {
		emitirRelatorio(getIdEntidade(), true);
		executarConsultaParametrizada();
		converterResultadoConsultaParaXML(this.getIdEntidade(), "registros");
		return getXmlRelatorio();
	}

	/**
	 * Método responsável por inicializar as opções de ordenação do relatório.
	 */
	public void inicializarOrdenacoesRelatorio() {
		Vector ordenacao = this.getOrdenacoesRelatorio();
		ordenacao.add("Tipo Pessoa, Razão Social");
	}

	/**
	 * Método responsável por inicializar as opções de ordenação do relatório.
	 */
	public void executarConsultaParametrizada() throws Exception {
		String selectStr = "SELECT US.nome AS usuario_s_nome, " + "PA.nome AS perfilacesso_nome, " + "EM.razaoSocial AS empresa_razaosocial " +

		"FROM usuarioPerfilAcesso UPA " + "INNER JOIN usuario US " + "ON UPA.usuario = US.codigo " + "INNER JOIN perfilAcesso PA " + "ON UPA.perfilAcesso = PA.codigo " + "INNER JOIN empresa EM "
				+ "ON UPA.empresa = EM.codigo " + "ORDER BY US.nome, PA.nome, EM.razaoSocial";

		selectStr = montarOrdenacaoRelatorio(selectStr);

		// inicializarParametrosRelatorio(sql);
		setResultadoConsulta(getConexao().getJdbcTemplate().queryForRowSet(selectStr));
	}

	/**
	 * Método responsável por definir a ordem dos dados envolvidos na consulta SQL.
	 * 
	 * @param selectStr
	 *            consulta inicialmente preparada, para a qual os vínculos seráo adicionados.
	 */
	protected String montarOrdenacaoRelatorio(String selectStr) {
		// String ordenacao = (String)getOrdenacoesRelatorio().get(getOrdenarPor());
		// if (ordenacao.equals("Tipo Pessoa, Razão Social")) {
		// selectStr = selectStr + " ORDER BY tipoPessoa, razaoSocial";
		// }
		return selectStr;
	}

	/**
	 * Operação reponsável por retornar o identificador desta classe. Este identificar é utilizado para verificar as
	 * permissões de acesso as operações desta classe.
	 */
	public String getIdEntidade() {
		return ("UsuarioRel");
	}

	/**
	 * Operação reponsável por retornar o arquivo (caminho e nome) correspondente ao design do relatório criado pelo
	 * IReport.
	 */
	public String getDesignIReportRelatorio() {
		return ("relatorio" + File.separator + "designRelatorio" + File.separator + "cadastro" + File.separator + getIdEntidade() + ".jrxml");
	}

	public String getCaminhoBaseRelatorio() {
		return ("relatorio" + File.separator + "designRelatorio" + File.separator + "cadastro" + File.separator);

	}


}
