package controle.arquitetura;

import negocio.comuns.utilitarias.*;
import negocio.comuns.arquitetura.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.model.SelectItem;
import negocio.facade.jdbc.arquitetura.Log;
import negocio.facade.jdbc.arquitetura.Usuario;
import negocio.interfaces.arquitetura.LogInterfaceFacade;

/**
 * Classe responsável por implementar a interação entre os componentes JSF das páginas
 * usuarioForm.jsp usuarioCons.jsp) com as funcionalidades da classe <code>Usuario</code>.
 * Implementação da camada controle (Backing Bean).
 * @see SuperControle
 * @see Usuario
 * @see UsuarioVO
 */
public class LogControle extends SuperControle {

    protected LogVO logVO;
    protected String nomeEntidade;
    protected Date dataInicio;
    protected Date dataFim;
    /**
     * Interface <code>UsuarioInterfaceFacade</code> responsável pela interconexão da camada de controle com a camada de negócio.
     * Criando uma independência da camada de controle com relação a tenologia de persistência dos dados (DesignPatter: Façade).
     */
    private LogInterfaceFacade logFacade = null;

    public LogControle() throws Exception {
        obterUsuarioLogado();
        inicializarFacades();
        setControleConsulta(new ControleConsulta());
        setMensagemID("msg_entre_prmconsulta");
        setNomeEntidade("");
        getControleConsulta().setCampoConsulta("nomeEntidade");
    }

    /**
     * Rotina responsavel por executar as consultas disponiveis no JSP UsuarioCons.jsp.
     * Define o tipo de consulta a ser executada, por meio de ComboBox denominado campoConsulta, disponivel neste mesmo JSP.
     * Como resultado, disponibiliza um List com os objetos selecionados na sessao da pagina.
     */
    public String consultar() {
        try {
            super.consultar();
            List objs = new ArrayList(0);           
            if (getControleConsulta().getCampoConsulta().equals("nomeEntidade")) {
                objs = logFacade.consultarPorNomeEntidade(getControleConsulta().getValorConsulta(), false, Uteis.NIVELMONTARDADOS_TODOS);
            }           
            if (getControleConsulta().getCampoConsulta().equals("responsavel")) {
                objs = logFacade.consultarPorResponsavel(getControleConsulta().getValorConsulta(), false, Uteis.NIVELMONTARDADOS_TODOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("operacao")) {
                objs = logFacade.consultarPorOpercao(getControleConsulta().getValorConsulta(), false, Uteis.NIVELMONTARDADOS_TODOS);
            }
            if (getControleConsulta().getCampoConsulta().equals("conteudoLog")) {
                objs = logFacade.consultarPorConteudoLog(getControleConsulta().getValorConsulta(), false, Uteis.NIVELMONTARDADOS_TODOS);
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

    public void visualizarLog(){
       LogVO obj = (LogVO) context().getExternalContext().getRequestMap().get("controleLog");
       setLogVO(obj);       
    }

    /**
     * Operação responsável por processar a exclusão um objeto da classe <code>UsuarioVO</code>
     * Após a exclusão ela automaticamente aciona a rotina para uma nova inclusão.
     */
    public String excluir() {
        try {
            logFacade.excluir(getLogVO());
            setLogVO(new LogVO());
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void irPaginaInicial() throws Exception {
        controleConsulta.setPaginaAtual(1);
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
     * Rotina responsável por preencher a combo de consulta da telas.
     */
    public List getTipoConsultaCombo() {
        List itens = new ArrayList(0);
        itens.add(new SelectItem("nomeEntidade", "Nome Entidade"));
        itens.add(new SelectItem("responsavel", "Responsável"));
        itens.add(new SelectItem("operacao", "Operação"));
        itens.add(new SelectItem("conteudoLog", "Conteúdo do Log"));
        return itens;
    }

    /**

    /**
     * Rotina responsável por organizar a paginação entre as páginas resultantes de uma consulta.
     */
    public String inicializarConsultar() {
        setListaConsulta(new ArrayList(0));
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    /**
     * Operação que inicializa as Interfaces Façades com os respectivos objetos de
     * persistência dos dados no banco de dados.
     */
    protected boolean inicializarFacades() {
        try {
            
            logFacade =  new Log();

            return true;
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro_conectarBD", e.getMessage());
            return false;
        }
    }

    /**
     * @return the logVO
     */
    public LogVO getLogVO() {
        return logVO;
    }

    /**
     * @param logVO the logVO to set
     */
    public void setLogVO(LogVO logVO) {
        this.logVO = logVO;
    }

    /**
     * @return the nomeEntidade
     */
    public String getNomeEntidade() {
        return nomeEntidade;
    }

    /**
     * @param nomeEntidade the nomeEntidade to set
     */
    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    /**
     * @return the dataInicio
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the dataFim
     */
    public Date getDataFim() {
        return dataFim;
    }

    /**
     * @param dataFim the dataFim to set
     */
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}