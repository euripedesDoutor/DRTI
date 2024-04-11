package controle.cadastro;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import negocio.comuns.cadastro.CidadeVO;
import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.facade.jdbc.cadastro.Empresa;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import controle.arquitetura.SuperControle;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.cadastro.Cidade;

/**
 * Classe responsável por implementar a interação entre os componentes JSF das páginas empresaForm.jsp empresaCons.jsp)
 * com as funcionalidades da classe <code>Empresa</code>. Implemtação da camada controle (Backing Bean).
 * 
 * @see SuperControle
 * @see Empresa
 * @see EmpresaVO
 */
@Controller("EmpresaControle")
@Scope("session")
@Lazy
public class EmpresaControle extends SuperControle {

    protected List listaSelectItemCidade;
    private EmpresaVO empresaVO;
    private String campoConsultaCidade;
    private String valorConsultaCidade;
    private List listaConsultaCidade;

    public EmpresaControle() throws Exception {
        obterUsuarioLogado();
        setControleConsulta(new ControleConsulta());
        setMensagemID("msg_entre_prmconsulta");
    }

    /**
     * Rotina responsável por disponibilizar um novo objeto da classe <code>Empresa</code> para edição pelo USUÁRIO da
     * aplicação.
     */
    public String novo() {
        setEmpresaVO(new EmpresaVO());
        inicializarListasSelectItemTodosComboBox();
        setMensagemID("msg_entre_dados");
        return "editar";
    }

    /**
     * Rotina responsável por disponibilizar os dados de um objeto da classe <code>Empresa</code> para alteração. O
     * objeto desta classe é disponibilizado na session da página (request) para que o JSP correspondente possa
     * disponibilizá-lo para edição.
     */
    public String editar() {
        try {
            EmpresaVO obj = (EmpresaVO) context().getExternalContext().getRequestMap().get("empresa");
            obj.setNovoObj(false);
            obj = getFacadeFactory().getEmpresaFacade().consultarPorChavePrimaria(obj.getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS);
            obj.setCidade(getFacadeFactory().getCidadeFacade().consultarPorChavePrimaria(obj.getCidade().getCodigo(), Uteis.NIVELMONTARDADOS_DADOSBASICOS));
            setEmpresaVO(obj);
            inicializarListasSelectItemTodosComboBox();
            setMensagemID("msg_dados_editar");
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
        return "editar";
    }

    /**
     * Rotina responsável por gravar no BD os dados editados de um novo objeto da classe <code>Empresa</code>. Caso o
     * objeto seja novo (ainda não gravado no BD) é acionado a operação <code>incluir()</code>. Caso contrário é
     * acionado o <code>alterar()</code>. Se houver alguma inconsistência o objeto não é gravado, sendo re-apresentado
     * para o USUÁRIO juntamente com uma mensagem de erro.
     */
    public String gravar() {
        try {
            if (empresaVO.isNovoObj().booleanValue()) {
                getFacadeFactory().getEmpresaFacade().incluir(empresaVO);
            } else {
                getFacadeFactory().getEmpresaFacade().alterar(empresaVO);
                setarAlteracaoEmpresa(empresaVO);
            }
            setMensagemID("msg_dados_gravados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    /**
     * Rotina responsavel por executar as consultas disponiveis no JSP EmpresaCons.jsp. Define o tipo de consulta a ser
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
                objs = getFacadeFactory().getEmpresaFacade().consultarPorCodigo(new Integer(valorInt), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("nomeFantasia")) {
                objs = getFacadeFactory().getEmpresaFacade().consultarPorNomeFantasia(getControleConsulta().getValorConsulta(), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("endereco")) {
                objs = getFacadeFactory().getEmpresaFacade().consultarPorEndereco(getControleConsulta().getValorConsulta(), true);
            }
            if (getControleConsulta().getCampoConsulta().equals("nomeCidade")) {
                objs = getFacadeFactory().getEmpresaFacade().consultarPorNomeCidade(getControleConsulta().getValorConsulta());
            }
            if (getControleConsulta().getCampoConsulta().equals("CNPJ")) {
                objs = getFacadeFactory().getEmpresaFacade().consultarPorCNPJ(getControleConsulta().getValorConsulta(), true);
            }
            // objs = ControleConsulta.obterSubListPaginaApresentar(objs, controleConsulta);
            // definirVisibilidadeLinksNavegacao(controleConsulta.getPaginaAtual(),
            // controleConsulta.getNrTotalPaginas());
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
     * Operação responsável por processar a exclusão um objeto da classe <code>EmpresaVO</code> Após a exclusão ela
     * automaticamente aciona a rotina para uma nova inclusão.
     */
    public String excluir() {
        try {
            getFacadeFactory().getEmpresaFacade().excluir(empresaVO);
            setEmpresaVO(new EmpresaVO());
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }


    public void consultarCidade() {
        try {
            List objs = new ArrayList(0);

            if (getCampoConsultaCidade().equals("codigo")) {
                if (getValorConsultaCidade().equals("")) {
                    setValorConsultaCidade("0");
                }
                int valorInt = Integer.parseInt(getValorConsultaCidade());
                objs = getFacadeFactory().getCidadeFacade().consultarPorCodigo(new Integer(valorInt), true);
            }
            if (getCampoConsultaCidade().equals("nome")) {
                objs = getFacadeFactory().getCidadeFacade().consultarPorNome(getValorConsultaCidade(), true);
            }
            if (getCampoConsultaCidade().equals("estado")) {
                objs = getFacadeFactory().getCidadeFacade().consultarPorEstado(getValorConsultaCidade(), true);
            }
            if (getCampoConsultaCidade().equals("codigoIBJE")) {
                objs = getFacadeFactory().getCidadeFacade().consultarPorCodigoIBGE(getValorConsultaCidade(), true);
            }

            setListaConsultaCidade(objs);
            setMensagemID("msg_dados_consultados");
        } catch (Exception e) {
            setListaConsultaCidade(new ArrayList(0));
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void selecionarCidade() throws Exception {
        CidadeVO obj = (CidadeVO) context().getExternalContext().getRequestMap().get("cidade");
        if (getMensagemDetalhada().equals("")) {
            this.getEmpresaVO().setCidade(obj);
        }
        // copiarValoresEndereco();
        listaConsultaCidade = new ArrayList(0);
        this.valorConsultaCidade = null;
        this.campoConsultaCidade = null;
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

    /**
     * Método responsável por gerar uma lista de objetos do tipo <code>SelectItem</code> para preencher o comboBox
     * relativo ao atributo <code>Cidade</code>.
     */
    public void montarListaSelectItemCidade(String prm) throws Exception {
        List resultadoConsulta = consultarCidadePorNome(prm);
        Iterator i = resultadoConsulta.iterator();
        List objs = new ArrayList(0);
        objs.add(new SelectItem(0, ""));
        while (i.hasNext()) {
            CidadeVO obj = (CidadeVO) i.next();
            objs.add(new SelectItem(obj.getCodigo(), obj.getNome().toString()));
        }
        setListaSelectItemCidade(objs);
    }

    /**
     * Método responsável por atualizar o ComboBox relativo ao atributo <code>Cidade</code>. Buscando todos os objetos
     * correspondentes a entidade <code>Cidade</code>. Esta rotina não recebe parâmetros para filtragem de dados, isto é
     * importante para a inicialização dos dados da tela para o acionamento por meio requisições Ajax.
     */
    public void montarListaSelectItemCidade() {
        try {
            montarListaSelectItemCidade("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método responsável por consultar dados da entidade <code><code> e montar o atributo <code>nome</code> Este
     * atributo é uma lista (<code>List</code>) utilizada para definir os valores a serem apresentados no ComboBox
     * correspondente
     */
    public List consultarCidadePorNome(String nomePrm) throws Exception {
        List lista = getFacadeFactory().getCidadeFacade().consultarPorNome(nomePrm, false, Uteis.NIVELMONTARDADOS_DADOSBASICOS);
        return lista;
    }

    /**
     * Método responsável por inicializar a lista de valores (<code>SelectItem</code>) para todos os ComboBox's.
     */
    public void inicializarListasSelectItemTodosComboBox() {
        //montarListaSelectItemCidade();
    }

    /**
     * Rotina responsável por preencher a combo de consulta da telas.
     */
    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("nomeFantasia", "Nome Fantasia"));
        itens.add(new SelectItem("endereco", "Endereço"));
        itens.add(new SelectItem("nomeCidade", "Cidade"));
        itens.add(new SelectItem("CNPJ", "CNPJ"));
        return itens;
    }

    public List getTipoConsultaComboCidade() {
        List itens = new ArrayList(0);// codigo, nome, cpf, cnpj, tipoPessoa
        itens.add(new SelectItem("codigo", "Código"));
        itens.add(new SelectItem("nome", "Nome"));
        itens.add(new SelectItem("estado", "Estado"));
        itens.add(new SelectItem("codigoIBJE", "Código do IBGE"));
        return itens;
    }

    /**
     * Rotina responsável por organizar a paginação entre as páginas resultantes de uma consulta.
     */
    public String inicializarConsultar() {
        // setPaginaAtualDeTodas("0/0");
        setListaConsulta(new ArrayList(0));
        // definirVisibilidadeLinksNavegacao(0, 0);
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public void limparDataBloqueio() {
        getEmpresaVO().setDataBloqueio(null);
    }

    public List getListaSelectItemCidade() {
        return (listaSelectItemCidade);
    }

    public void setListaSelectItemCidade(List listaSelectItemCidade) {
        this.listaSelectItemCidade = listaSelectItemCidade;
    }

    public EmpresaVO getEmpresaVO() {
        return empresaVO;
    }

    public void setEmpresaVO(EmpresaVO empresaVO) {
        this.empresaVO = empresaVO;
    }

    public String getCampoConsultaCidade() {
        return campoConsultaCidade;
    }

    public void setCampoConsultaCidade(String campoConsultaCidade) {
        this.campoConsultaCidade = campoConsultaCidade;
    }

    public List getListaConsultaCidade() {
        return listaConsultaCidade;
    }

    public void setListaConsultaCidade(List listaConsultaCidade) {
        this.listaConsultaCidade = listaConsultaCidade;
    }

    public String getValorConsultaCidade() {
        return valorConsultaCidade;
    }

    public void setValorConsultaCidade(String valorConsultaCidade) {
        this.valorConsultaCidade = valorConsultaCidade;
    }

    public List getPerfilApresentacaoSelectItem() {
        List itens = new ArrayList(0);
        try {
            itens.add(new SelectItem("", ""));
            itens.add(new SelectItem("A", "A"));
            itens.add(new SelectItem("B", "B"));
            itens.add(new SelectItem("C", "C"));
        } catch (Exception e) {
        }
        return itens;
    }

    public List getIndicadorTipoAtividadeSelectItem() {
        List itens = new ArrayList(0);
        try {
            itens.add(new SelectItem("", ""));
            itens.add(new SelectItem("0", "0 - Industrial ou equiparado a industrial"));
            itens.add(new SelectItem("1", "1 - Outros"));
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
        return itens;
    }

    public List getIndicadorTipoLayoutSelectItem() {
        List itens = new ArrayList(0);
        try {
            itens.add(new SelectItem("", ""));
            itens.add(new SelectItem("0", "0 - Layout Simplificado"));
            itens.add(new SelectItem("1", "1 - Layout Completo"));
            itens.add(new SelectItem("2", "2 - Layout Restrito aos Saldos de Estoque"));
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
        return itens;
    }

    public List getIndicadorTipoAtividadePreponderanteSelectItem() {
        List itens = new ArrayList(0);
        try {
            itens.add(new SelectItem("", ""));
            itens.add(new SelectItem("0", "0 - Industrial ou equiparado a industrial"));
            itens.add(new SelectItem("1", "1 - Prestador de serviços"));
            itens.add(new SelectItem("2", "2 - Atividade de comércio"));
            itens.add(new SelectItem("3", "3 - Pessoas jurídicas referidas nos §§ 6º, 8º e 9º do art. 3º da Lei nº 9.718, de 1998"));
            itens.add(new SelectItem("4", "4 - Atividade imobiliária"));
            itens.add(new SelectItem("9", "9 - Outros"));
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
        return itens;
    }

    public List getListaIndicadorNaturezaPessoaJuridica() {
        List opcoes = new ArrayList(0);
        opcoes.add(new SelectItem("", ""));
        opcoes.add(new SelectItem("00", "00 - Pessoa jurídica em geral (não participantede SCP como sócia ostensiva)"));
        opcoes.add(new SelectItem("01", "01 - Sociedade cooperativa (não participante de SCP como sócia ostensiva)"));
        opcoes.add(new SelectItem("02", "02 - Sociedade sujeita ao PIS/PASEP"));
        opcoes.add(new SelectItem("03", "03 - Pessoa jurídica em geral participante de SCP como sócia ostensiva"));
        opcoes.add(new SelectItem("04", "04 - Sociedade cooperativa participante de SCP como sócia ostensiva"));
        opcoes.add(new SelectItem("05", "05 - Sociedade em Conta de Participação - SCP"));
        return opcoes;
    }

}
