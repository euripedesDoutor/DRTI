package relatorio.controle.cadastro;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import relatorio.controle.arquitetura.SuperControleRelatorio;
import relatorio.negocio.jdbc.cadastro.UsuarioRel;

@Controller("UsuarioControleRel")
@Scope("session")
@Lazy
public class UsuarioControleRel extends SuperControleRelatorio {

	private UsuarioRel usuarioRel;
	private String nomeEntidade = null;

	public UsuarioControleRel() throws Exception {
		setUsuarioRel(new UsuarioRel());
		inicializarEmpresaLogado();
		inicializarListasSelectItemTodosComboBox();
		setOpcaoOrdenacao(new Integer(0));
		setMensagemID("msg_entre_prmrelatorio");
	}

	public void inicializarEmpresaLogado() {
		try {
			setNomeEntidade(getEmpresaLogado().getNomeFantasia());
		} catch (Exception ex) {
		}
	}

	public void inicializarListasSelectItemTodosComboBox() {
		montarListaSelectItemOrdenacao();
	}

	/**
	 * Método responsável por gerar uma lista de objetos do tipo <code> SelectItem </code> para preencher o comboBox
	 * relativo a possíveis formas de ordenação do relatório
	 */
	public void montarListaSelectItemOrdenacao() {
		Vector opcoes = getUsuarioRel().getOrdenacoesRelatorio();
		Enumeration i = opcoes.elements();
		List objs = new ArrayList(0);
		int contador = 0;
		while (i.hasMoreElements()) {
			String opcao = (String) i.nextElement();
			objs.add(new SelectItem(new Integer(contador), opcao));
		}
		setListaSelectItemOrdenacoesRelatorio(objs);
	}


	public void exportarListaConsultaParaPdf(List lista) {
		try {
			apresentarRelatorioObjetos(getUsuarioRel().getIdEntidade(), "Relatório de Usuários", getEmpresaLogado().getRazaoSocial(), getCodigoEmpresaLogado(), "", "PDF", "", getUsuarioRel().getDesignIReportRelatorio(), getUsuarioLogado().getNome(),
					"", lista, getUsuarioRel().getCaminhoBaseRelatorio());
			setMensagemID("msg_relatorio_ok");
		} catch (Exception e) {
			setMensagemDetalhada("msg_erro", e.getMessage());
		}
	}

	/**
	 * Método responsável por gerar o relatório de <code>AniversariantesRel</code> no formato HTML
	 */
	public void imprimirHTML() {
		try {
			getUsuarioRel().setOrdenarPor(getOpcaoOrdenacao().intValue());
			String xml = getUsuarioRel().emitirRelatorio();
			apresentarRelatorio(getUsuarioRel().getIdEntidade(), xml, "Relatório de Usuário", this.getNomeEntidade(), "", "HTML", "/" + getUsuarioRel().getIdEntidade() + "/registros",
					getUsuarioRel().getDesignIReportRelatorio(), this.getUsuarioLogado().getNome(), getUsuarioRel().getDescricaoFiltros());
			setMensagemID("msg_relatorio_ok");
		} catch (Exception e) {
			setMensagemDetalhada("msg_erro", e.getMessage());
		}
	}

	public String getNomeEntidade() {
		return nomeEntidade;
	}

	public void setNomeEntidade(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}

	public UsuarioRel getUsuarioRel() {
		return usuarioRel;
	}

	public void setUsuarioRel(UsuarioRel usuarioRel) {
		this.usuarioRel = usuarioRel;
	}

}
