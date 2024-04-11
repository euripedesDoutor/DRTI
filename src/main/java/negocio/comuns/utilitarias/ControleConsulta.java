package negocio.comuns.utilitarias;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe responsável por controlar a paginação de um conjunto de objetos armazenados em um lista <code>List</code>.
 * Fornecer atributos que persistem informações sobre: os parâmetros para realização da consulta, página atual da
 * da consulta, posicição inicial e final da lista que está sendo apresentada e, por fim, o NÚMERO de itens total
 * armazenados na lista que está sendo paginada. Adicionalmente, esta classe fornece métodos que permitem navegar
 * para primeira, próxima, anterior e última página. Calculando automaticamente a posição inicial e final da próxima
 * página. Também fornece informações como NÚMERO total de páginas.
 */
public class ControleConsulta {

    private String campoConsulta;
    private String valorConsulta;
    private int posInicialListar;
    private int posFinalListar;
    private int paginaAtual;
    private int tamanhoConsulta;

    public ControleConsulta() {
        campoConsulta = "";
        valorConsulta = "";
        paginaAtual = 1;
        posInicialListar = 0;
        posFinalListar = Uteis.TAMANHOLISTA;
        tamanhoConsulta = 0;
    }

    /**
     * Inicializa todos os atributos necessários para o iíicio de uma nova consulta.
     */
    public static void inicializar(ControleConsulta controleConsulta) {
        controleConsulta.paginaAtual = 1;
        controleConsulta.posInicialListar = 0;
        controleConsulta.posFinalListar = Uteis.TAMANHOLISTA;
        controleConsulta.tamanhoConsulta = 0;
    }

    /**
     * Configura todos os atributos necessários para apresentação da página fornecida pelo parâmetro.
     * Permitindo uma fácil navegação para a página desejada.
     */
    public void definirProximaPaginaApresentar(int pagina) {
        paginaAtual = pagina;
        if (paginaAtual > 1) {
            posInicialListar = (pagina - 1) * Uteis.TAMANHOLISTA;
            posFinalListar = (pagina) * Uteis.TAMANHOLISTA;
        } else {
            posInicialListar = 0;
            posFinalListar = Uteis.TAMANHOLISTA;
        }
    }

    public String getURLPrimeiraPagina() {
        String url = null;
        if ((paginaAtual > 1)
                && (getNrTotalPaginas() > 1)) {
            url = "?consultar=0"
                    + "&pagina=" + 1
                    + "&campoConsulta=" + campoConsulta
                    + "&valorConsulta=" + valorConsulta;
        }
        return url;
    }

    public String getURLPaginaAnterior() {
        String url = null;
        if (paginaAtual > 1) {
            url = "?consultar=0"
                    + "&pagina=" + (paginaAtual - 1)
                    + "&campoConsulta=" + campoConsulta
                    + "&valorConsulta=" + valorConsulta;
        }
        return url;
    }

    public String getURLPaginaPosterior() {
        String url = null;
        if (paginaAtual < getNrTotalPaginas()) {
            url = "?consultar=0"
                    + "&pagina=" + (paginaAtual + 1)
                    + "&campoConsulta=" + campoConsulta
                    + "&valorConsulta=" + valorConsulta;
        }

        return url;
    }

    public String getURLUltimaPagina() {
        String url = null;
        if ((getNrTotalPaginas() > 1)
                && (paginaAtual < getNrTotalPaginas())) {
            url = "?consultar=0"
                    + "&pagina=" + getNrTotalPaginas()
                    + "&campoConsulta=" + campoConsulta
                    + "&valorConsulta=" + valorConsulta;
        }
        return url;
    }

    public static List paginarConsulta(HttpServletRequest request, List resultado, ControleConsulta controleConsulta) throws Exception {
        ControleConsulta.registrarTamanhoConsulta(resultado, controleConsulta);
        resultado = ControleConsulta.obterSubListPaginaApresentar(resultado, controleConsulta);
        ControleConsulta.registrarParametrosConsulta(request, controleConsulta);
        return resultado;
    }

    public static void registrarParametrosConsulta(HttpServletRequest request, ControleConsulta controleConsulta) throws Exception {
        request.setAttribute("campoConsulta", controleConsulta.getCampoConsulta());
        request.setAttribute("valorConsulta", controleConsulta.getValorConsulta());
        request.setAttribute("prmPrimeiraPg", controleConsulta.getURLPrimeiraPagina());
        request.setAttribute("prmPgAnterior", controleConsulta.getURLPaginaAnterior());
        request.setAttribute("prmPgPosterior", controleConsulta.getURLPaginaPosterior());
        request.setAttribute("prmUltimaPg", controleConsulta.getURLUltimaPagina());
        request.setAttribute("paginaAtual", controleConsulta.getPaginaAtualDeTodas());
        request.setAttribute("tamanhoTotalConsulta", String.valueOf(controleConsulta.getTamanhoConsulta()));
        request.setAttribute("controleConsulta", controleConsulta);
    }

    public static void registrarTamanhoConsulta(List resultado, ControleConsulta controleConsulta) {
        if (resultado == null) {
            return;
        }
        controleConsulta.setTamanhoConsulta(resultado.size());
    }

    public static List obterSubListPaginaApresentar(List resultado, ControleConsulta controleConsulta) {
        if (resultado == null) {
            inicializar(controleConsulta);
            return null;
        }
        if (resultado.size() == 0) {
            inicializar(controleConsulta);
            return resultado;
        }

        List subList = new ArrayList(0);
        if (controleConsulta.getPosFinalListar() > resultado.size()) {
            controleConsulta.setPosFinalListar(resultado.size());
        }
        if (controleConsulta.getPosInicialListar() < resultado.size()) {
            subList = resultado.subList(controleConsulta.getPosInicialListar(), controleConsulta.getPosFinalListar());
        }
        controleConsulta.setTamanhoConsulta(resultado.size());
        return subList;
    }

    public int getNrTotalPaginas() {
        double tamanhoPagina = Uteis.TAMANHOLISTA;
        double nrPaginasDouble = Math.ceil(tamanhoConsulta / tamanhoPagina);
        String nrTotalPaginas = String.valueOf(nrPaginasDouble);
        nrTotalPaginas = nrTotalPaginas.substring(0, nrTotalPaginas.indexOf("."));
        return (Integer.parseInt(nrTotalPaginas));
    }

    public void limparCampoConsulta() {
        setValorConsulta("");
    }

    public String getPaginaAtualDeTodas() {
        return paginaAtual + "/" + getNrTotalPaginas();
    }

    public int getPosInicialListar() {
        return posInicialListar;
    }

    public void setPosInicialListar(int posInicialListar) {
        this.posInicialListar = posInicialListar;
    }

    public int getPosFinalListar() {
        return posFinalListar;
    }

    public void setPosFinalListar(int posFinalListar) {
        this.posFinalListar = posFinalListar;
    }

    public int getTamanhoConsulta() {
        return tamanhoConsulta;
    }

    public void setTamanhoConsulta(int tamanhoConsulta) {
        this.tamanhoConsulta = tamanhoConsulta;
    }

    public String getCampoConsulta() {
        if (campoConsulta == null) {
            return "";
        }
        return campoConsulta;
    }

    public void setCampoConsulta(String campoConsulta) {
        if (campoConsulta == null) {
            campoConsulta = "";
        }
        this.campoConsulta = campoConsulta;
    }

    public String getValorConsulta() {
        if (valorConsulta == null) {
            return "";
        }
        return valorConsulta;
    }

    public void setValorConsulta(String valorConsulta) {
        if (valorConsulta == null) {
            valorConsulta = "";
        }
        this.valorConsulta = valorConsulta;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }
}
