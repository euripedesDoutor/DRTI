package relatorio.negocio.jdbc.arquitetura;

import java.io.StringWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.arquitetura.ControleAcesso;
import negocio.facade.jdbc.arquitetura.SuperEntidade;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * SuperRelatorio é uma classe padrão para que encapsulam as operações básicas relativas a emissão de relatórios.
 * Verificar a permissão do USUÁRIO para realizar a emissão de um determinado relatório.
 */
public class SuperRelatorio extends SuperEntidade {

	protected SqlRowSet resultadoConsulta;
	protected String xmlRelatorio;
	protected Vector ordenacoesRelatorio;
	protected int ordenarPor;
	protected String descricaoFiltros;

	public SuperRelatorio() throws Exception {
		setXmlRelatorio("");
		setOrdenacoesRelatorio(new Vector());
		setOrdenarPor(0);
		setDescricaoFiltros("");
	}

	public void adicionarDescricaoFiltro(String filtro) {
		if (!descricaoFiltros.equals("")) {
			descricaoFiltros = descricaoFiltros + ";" + filtro;
		} else {
			descricaoFiltros = filtro;
		}
	}

//	/**
//	 * Operação padrão para realizar o EMITIR UM Relatório de dados de uma entidade no BD. Verifica e inicializa (se
//	 * necessário) a conexão com o BD. Verifica se o USUÁRIO logado possui permissão de acesso a operação
//	 * EMITIRRELATORIO.
//	 * 
//	 * @param idEntidade
//	 *            Nome da entidade para a qual se deseja realizar a operação.
//	 * @exception Exception
//	 *                Caso haja problemas de conexão ou restrição de acesso a esta operação.
//	 */
//	public void emitirRelatorio(String idEntidade, boolean verificarAcesso) throws Exception {
//		ControleAcesso.emitirRelatorio(idEntidade, verificarAcesso);
//	}

	public String adicionarCondicionalWhere(String whereStr, String filtro, boolean operadorAND) {
		if (!operadorAND) {
			return filtro;
		} else {
			return whereStr + " AND " + filtro;
		}
	}

	public String getStringFromDocument(Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			String xmlConvertido = writer.toString();
			xmlConvertido = xmlConvertido.replaceFirst("UTF-8", "UTF-8");
			return xmlConvertido;
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void converterResultadoConsultaParaXML(String nomeRelatorio, String nomeRegistro) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		// Criando o nº raiz do XML - com o nome padrão do relatório
		Element root = doc.createElement(nomeRelatorio);
		// Obtendo as informações de estrutura do ResultSet - METADados
		SqlRowSetMetaData metaDadosConsulta = resultadoConsulta.getMetaData();

		int cols = metaDadosConsulta.getColumnCount();
		while (resultadoConsulta.next()) {
			Element linhaXML = doc.createElement(nomeRegistro);

			for (int j = 1; j <= cols; j++) {
				String nomeTabela = metaDadosConsulta.getTableName(j);
				String nomeColuna = metaDadosConsulta.getColumnName(j);
				String nomeClasse = metaDadosConsulta.getColumnClassName(j);
				String valorColuna = " ";
				if (nomeClasse.equals("java.sql.Timestamp")) {
					valorColuna = Uteis.getData(resultadoConsulta.getDate(j));
					if ((valorColuna == null) || (valorColuna.equals("")) || (valorColuna.equals("null"))) {
						valorColuna = " ";
					}
				} else if (nomeClasse.equals("java.lang.Double")) {
					valorColuna = String.valueOf(resultadoConsulta.getDouble(j));
					if (valorColuna == null) {
						valorColuna = "0.0";
					}
				} else if (nomeClasse.equals("java.lang.Float")) {
					valorColuna = String.valueOf(resultadoConsulta.getFloat(j));
					if (valorColuna == null) {
						valorColuna = "0.0";
					}
				} else {
					valorColuna = resultadoConsulta.getString(j);
					// if (nomeColuna.equals("dia") || nomeColuna.equals("mes")) {
					// int posicao = valorColuna.lastIndexOf(".");
					// valorColuna.substring(0,posicao + 1);
					// valorColuna = " ";
					// }
					if ((valorColuna == null) || (valorColuna.equals("")) || (valorColuna.equals("null"))) {
						valorColuna = " ";
					}
				}

				Element e = doc.createElement(nomeColuna);
				e.appendChild(doc.createTextNode(valorColuna));
				linhaXML.appendChild(e);
			}
			root.appendChild(linhaXML);
		}
		doc.appendChild(root);
		this.setXmlRelatorio(getStringFromDocument(doc));

		/*
		 * //perform XSL transformation DOMSource source = new DOMSource(doc); //OutputStream outputStream = new
		 * OutputStream(); OutputStream buffer = new OutputStream(); OutputStreamWriter outSW = new
		 * OutputStreamWriter(buffer, "UTF-8"); StreamResult result = new StreamResult("customers.xml");
		 * TransformerFactory tmf = TransformerFactory.newInstance(); tmf.newTransformer().transform(source,result);
		 * //result.getWriter() FileOutputStream outFile = new FileOutputStream(this.getArquivoCodigo()); String
		 * conteudo = this.getText(); for (int i =0; i < conteudo.length(); i++) { outSW.append(conteudo.charAt(i)); }
		 * outSW.close();
		 */
	}

	public String getXmlRelatorio() {
		return xmlRelatorio;
	}

	public void setXmlRelatorio(String xmlRelatorio) {
		this.xmlRelatorio = xmlRelatorio;
	}

	public Vector getOrdenacoesRelatorio() {
		return ordenacoesRelatorio;
	}

	public void setOrdenacoesRelatorio(Vector ordenacoesRelatorio) {
		this.ordenacoesRelatorio = ordenacoesRelatorio;
	}

	public int getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(int ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public SqlRowSet getResultadoConsulta() {
		return resultadoConsulta;
	}

	public void setResultadoConsulta(SqlRowSet resultadoConsulta) {
		this.resultadoConsulta = resultadoConsulta;
	}

	public String getDescricaoFiltros() {
		return descricaoFiltros;
	}

	public void setDescricaoFiltros(String descricaoFiltros) {
		this.descricaoFiltros = descricaoFiltros;
	}
}
