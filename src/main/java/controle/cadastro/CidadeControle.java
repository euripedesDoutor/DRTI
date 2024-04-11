package controle.cadastro;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.faces.model.SelectItem;

import negocio.comuns.cadastro.CidadeVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.Dominios;
import negocio.facade.jdbc.cadastro.Cidade;
import negocio.facade.jdbc.cadastro.Pais;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import relatorio.controle.arquitetura.SuperControleRelatorio;
import controle.arquitetura.SelectItemOrdemValor;
import controle.arquitetura.SuperControle;
import negocio.comuns.cadastro.PaisVO;

/**
 * Classe responsável por implementar a interação entre os componentes JSF das páginas cidadeForm.jsp cidadeCons.jsp)
 * com as funcionalidades da classe <code>Cidade</code>. Implemtação da camada controle (Backing Bean).
 * 
 * @see SuperControle
 * @see Cidade
 * @see CidadeVO
 */
@Controller("CidadeControle")
@Scope("session")
@Lazy
public class CidadeControle extends SuperControleRelatorio {

    private CidadeVO cidadeVO;
    protected static String idEntidade;

    public CidadeControle() throws Exception {
        obterUsuarioLogado();
        setControleConsulta(new ControleConsulta());
        getControleConsulta().setCampoConsulta("nome");
        setMensagemID("msg_entre_prmconsulta");
        setIdEntidade("CidadeRel");
        setCidadeVO(new CidadeVO());
    }

    /**
     * Rotina responsável por disponibilizar um novo objeto da classe <code>Cidade</code> para edição pelo USUÁRIO da
     * aplicação.
     */
    public String novo() {
        setCidadeVO(new CidadeVO());
        setMensagemID("msg_entre_dados");
        return "editar";
    }

    /**
     * Rotina responsável por disponibilizar os dados de um objeto da classe <code>Cidade</code> para alteração. O
     * objeto desta classe é disponibilizado na session da página (request) para que o JSP correspondente possa
     * disponibilizá-lo para edição.
     */
    public String editar() {
        CidadeVO obj = (CidadeVO) context().getExternalContext().getRequestMap().get("cidade");
        obj.setNovoObj(false);
        setCidadeVO(obj);
        setMensagemID("msg_dados_editar");
        return "editar";
    }

    /**
     * Rotina responsável por gravar no BD os dados editados de um novo objeto da classe <code>Cidade</code>. Caso o
     * objeto seja novo (ainda não gravado no BD) é acionado a operação <code>incluir()</code>. Caso contrário é
     * acionado o <code>alterar()</code>. Se houver alguma inconsistência o objeto não é gravado, sendo re-apresentado
     * para o USUÁRIO juntamente com uma mensagem de erro.
     */
    public String gravar() {
        try {
            if (cidadeVO.isNovoObj().booleanValue()) {
                getFacadeFactory().getCidadeFacade().incluir(cidadeVO);
            } else {
                getFacadeFactory().getCidadeFacade().alterar(cidadeVO);
            }
            setMensagemID("msg_dados_gravados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    /**
     * Rotina responsavel por executar as consultas disponiveis no JSP CidadeCons.jsp. Define o tipo de consulta a ser
     * executada, por meio de ComboBox denominado campoConsulta, disponivel neste mesmo JSP. Como resultado,
     * disponibiliza um List com os objetos selecionados na sessao da pagina.
     */
    public String consultar() {
        try {

            super.consultar();
            List objs = new ArrayList(0);
            if (getControleConsulta().getCampoConsulta().equals("codigo")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    getControleConsulta().setValorConsulta("0");
                }
                int valorInt = Integer.parseInt(getControleConsulta().getValorConsulta());
                objs = getFacadeFactory().getCidadeFacade().consultarPorCodigo(new Integer(valorInt), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("nome")) {
                objs = getFacadeFactory().getCidadeFacade().consultarPorNome(getControleConsulta().getValorConsulta(), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("estado")) {
                objs = getFacadeFactory().getCidadeFacade().consultarPorEstado(getControleConsulta().getValorConsulta(), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("codigoIBGE")) {
                objs = getFacadeFactory().getCidadeFacade().consultarPorCodigoIBGE(getControleConsulta().getValorConsulta(), true);
            }

            setListaConsulta(objs);
            setMensagemID("msg_dados_consultados");

            return "consultar";
        } catch (Exception e) {
            setListaConsulta(new ArrayList(0));
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "consultar";
        }
    }

    /**
     * Operação responsável por processar a exclusão um objeto da classe <code>CidadeVO</code> Após a exclusão ela
     * automaticamente aciona a rotina para uma nova inclusão.
     */
    public String excluir() {
        try {
            getFacadeFactory().getCidadeFacade().excluir(cidadeVO);
            setCidadeVO(new CidadeVO());
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void irPaginaInicial() throws Exception {
        // controleConsulta.setPaginaAtual(1);
        this.consultar();
    }

    public void irPaginaAnterior() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getPaginaAtual() - 1);
        this.consultar();
    }

    public void irPaginaPosterior() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getPaginaAtual() + 1);
        this.consultar();
    }

    public void irPaginaFinal() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getNrTotalPaginas());
        this.consultar();
    }

    /*
     * Método responsável por inicializar List<SelectItem> de valores do ComboBox correspondente ao atributo
     * <code>estado</code>
     */
    public List getListaSelectItemEstadoCidade() throws Exception {
        List objs = new ArrayList(0);
        objs.add(new SelectItem("", ""));
        objs.add(new SelectItem("AC", "AC"));
        objs.add(new SelectItem("AL", "AL"));
        objs.add(new SelectItem("AM", "AM"));
        objs.add(new SelectItem("AP", "AP"));
        objs.add(new SelectItem("BA", "BA"));
        objs.add(new SelectItem("CE", "CE"));
        objs.add(new SelectItem("DF", "DF"));
        objs.add(new SelectItem("ES", "ES"));
        objs.add(new SelectItem("GO", "GO"));
        objs.add(new SelectItem("MT", "MT"));
        objs.add(new SelectItem("MA", "MA"));
        objs.add(new SelectItem("MG", "MG"));
        objs.add(new SelectItem("MS", "MS"));
        objs.add(new SelectItem("PA", "PA"));
        objs.add(new SelectItem("PB", "PB"));
        objs.add(new SelectItem("PE", "PE"));
        objs.add(new SelectItem("PI", "PI"));
        objs.add(new SelectItem("PR", "PR"));
        objs.add(new SelectItem("RJ", "RJ"));
        objs.add(new SelectItem("RN", "RN"));
        objs.add(new SelectItem("RO", "RO"));
        objs.add(new SelectItem("RR", "RR"));
        objs.add(new SelectItem("RS", "RS"));
        objs.add(new SelectItem("SC", "SC"));
        objs.add(new SelectItem("SE", "SE"));
        objs.add(new SelectItem("SP", "SP"));
        objs.add(new SelectItem("TO", "TO"));
        objs.add(new SelectItem("EX", "EX"));
        return objs;
    }

    public void imprimirPDF() throws Exception {
        try {
            if (getCidadeVO().getEstado().equals("")) {
                throw new Exception("Selecione um estado.");
            }
            List lista = (getFacadeFactory().getCidadeFacade().consultarParaRelatorio(getCidadeVO().getEstado(), true));
            String tituloRelatorio = "Relatório de Cidade";
            String design = getDesignIReportRelatorio();
            String filtros = "";
            if (!getCidadeVO().getEstado().equals("")) {
                filtros = "Estado: " + getCidadeVO().getEstado_Apresentar();
            }
            apresentarRelatorioObjetos(getIdEntidade(), tituloRelatorio, getEmpresaLogado().getRazaoSocial(), getCodigoEmpresaLogado(), "", "PDF", "/CidadeRel/registros", design,
                    getUsuarioLogado().getNome(), filtros, lista, getCaminhoBaseRelatorio());
            setCidadeVO(new CidadeVO());
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public List<PaisVO> autocompletePais(Object suggest) {
        String prefixo = (String) suggest;
        try {
            return Pais.consultarParaAutoComplete(prefixo);
        } catch (Exception ex) {
            setMensagemDetalhada("msg_erro", ex.getMessage());
        }
        return new ArrayList<PaisVO>();
    }

    public void consultarPaisPorChavePrimaria() {
        try {
            getCidadeVO().setPais(getFacadeFactory().getPaisFacade().consultarPorChavePrimaria(getCidadeVO().getPais().getCodigo(), negocio.comuns.utilitarias.Uteis.NIVELMONTARDADOS_DADOSBASICOS));
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public static String getDesignIReportRelatorio() {
        return ("relatorio" + File.separator + "designRelatorio" + File.separator + "cadastro" + File.separator + getIdEntidade() + ".jrxml");
    }

    public static String getCaminhoBaseRelatorio() {
        return ("relatorio" + File.separator + "designRelatorio" + File.separator + "cadastro" + File.separator);
    }

    public static String getIdEntidade() {
        return idEntidade;
    }

    public static void setIdEntidade(String idEntidade) {
        CidadeControle.idEntidade = idEntidade;
    }

    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("nome", "Nome"));
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("estado", "Estado"));
        itens.add(new SelectItem("codigoIBGE", "Código IBGE"));
        return itens;
    }

    /**
     * Rotina responsável por organizar a paginação entre as páginas resultantes de uma consulta.
     */
    public String inicializarConsultar() {
        setListaConsulta(new ArrayList(0));
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public CidadeVO getCidadeVO() {
        return cidadeVO;
    }

    public void setCidadeVO(CidadeVO cidadeVO) {
        this.cidadeVO = cidadeVO;
    }
}
