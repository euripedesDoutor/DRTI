package relatorio.controle.arquitetura;

import controle.arquitetura.SuperControle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import appletImpressaoMatricial.ObjetoVO;
import java.io.FileInputStream;
import negocio.comuns.utilitarias.Uteis;

public class SuperControleRelatorio extends SuperControle {

    public static final String linhaDupla = "========================================================================================================================================";
    public static final String linhaSimples = "----------------------------------------------------------------------------------------------------------------------------------------";
    public static final String linhaPontilhada = "........................................................................................................................................";
    protected Integer opcaoOrdenacao;
    protected Integer linhaImpressaoMatricial;
    protected List listaSelectItemOrdenacoesRelatorio;
    protected List listaImpressaoMatricial;
    protected String tagAppletImpressaoMatricial;
    protected int contadorLinhaRelatorioMatricial = 0;
    private String tituloRelatorioMatricial = "";
    private String filtroRelatorioMatricial = "";
    protected String empresaRelatorioMatricial = "";
    protected Integer numeroPagina = 1;
    protected Byte[] arquivoPDF = new Byte[0];

    public SuperControleRelatorio() throws Exception {
    }


    /* Rotina responsável por acionar o Servlet de Apresentação de Relatério <code>VisualizadorRelatorio</code>,
     * fornecendo todos os parâmetros e dados necessários para a geração e visualização
     * do mesmo.
     * @param xml Dados a serem visualizados no relatório
     * @param tituloRelatorio Título do relatório
     * @param tipoRelatorio Pode assumir dois valores: HTML ou PDF
     * @param parserBuscaTag Padrão a ser utilizado pelo JasperReport
     *                       para filtrar quais dados do XML deveráo ser processados
     * @param designIReport Nome do arquivo do IReport contendo o design gráfico do relatório
     * @param nomeUsuario Nome do USUÁRIO logado para apresentação no relatório
     */
    public void apresentarRelatorio(String nomeRelatorio,
            String xml,
            String tituloRelatorio,
            String nomeEmpresa,
            String mensagemRel,
            String tipoRelatorio,
            String parserBuscaTags,
            String designIReport,
            String nomeUsuario,
            String filtros/*,
            String ordenacao*/) throws Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        request.setAttribute("xmlRelatorio", xml);
        request.setAttribute("tipoRelatorio", tipoRelatorio);
        request.setAttribute("nomeRelatorio", nomeRelatorio);
        request.setAttribute("nomeEmpresa", nomeEmpresa);
        request.setAttribute("mensagemRel", mensagemRel);
        request.setAttribute("tituloRelatorio", tituloRelatorio);
        request.setAttribute("caminhoParserXML", parserBuscaTags);
        request.setAttribute("nomeDesignIReport", designIReport);
        request.setAttribute("nomeUsuario", nomeUsuario);
        //request.setAttribute("ordenacao", ordenacao);
        if (filtros.equals("")) {
            filtros = "nenhum";
        }
        request.setAttribute("filtros", filtros);
        context().getExternalContext().dispatch("/VisualizadorRelatorio");
        context().getCurrentInstance().responseComplete();
    }

    public void apresentarRelatorio(String nomeRelatorio,
            String xml,
            String tituloRelatorio,
            String nomeEmpresa,
            Integer codigoEmpresa,
            String mensagemRel,
            String tipoRelatorio,
            String parserBuscaTags,
            String designIReport,
            String nomeUsuario,
            String filtros,
            String parametro1,
            String parametro2,
            String parametro3) throws Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        request.setAttribute("xmlRelatorio", xml);
        request.setAttribute("tipoRelatorio", tipoRelatorio);
        request.setAttribute("nomeRelatorio", nomeRelatorio);
        request.setAttribute("nomeEmpresa", nomeEmpresa);
        request.setAttribute("codigoEmpresa", codigoEmpresa);
        request.setAttribute("mensagemRel", mensagemRel);
        request.setAttribute("tituloRelatorio", tituloRelatorio);
        request.setAttribute("caminhoParserXML", parserBuscaTags);
        request.setAttribute("nomeDesignIReport", designIReport);
        request.setAttribute("nomeUsuario", nomeUsuario);
        request.setAttribute("parametro1", parametro1);
        request.setAttribute("parametro2", parametro2);
        request.setAttribute("parametro3", parametro3);

        if (filtros.equals("")) {
            filtros = "nenhum";
        }
        request.setAttribute("filtros", filtros);
        context().getExternalContext().dispatch("/VisualizadorRelatorio");
        context().getCurrentInstance().responseComplete();
    }

    public void apresentarRelatorio(String nomeRelatorio,
            String xml,
            String tituloRelatorio,
            String nomeEmpresa,
            Integer codigoEmpresa,
            String mensagemRel,
            String tipoRelatorio,
            String parserBuscaTags,
            String designIReport,
            String nomeUsuario,
            String filtros,
            String parametro1,
            String parametro2,
            String parametro3,
            String caminhoBaseRelatorio) throws Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        request.setAttribute("xmlRelatorio", xml);
        request.setAttribute("tipoRelatorio", tipoRelatorio);
        request.setAttribute("nomeRelatorio", nomeRelatorio);
        request.setAttribute("nomeEmpresa", nomeEmpresa);
        request.setAttribute("codigoEmpresa", codigoEmpresa);
        request.setAttribute("mensagemRel", mensagemRel);
        request.setAttribute("tituloRelatorio", tituloRelatorio);
        request.setAttribute("caminhoParserXML", parserBuscaTags);
        request.setAttribute("nomeDesignIReport", designIReport);
        request.setAttribute("nomeUsuario", nomeUsuario);
        request.setAttribute("parametro1", parametro1);
        request.setAttribute("parametro2", parametro2);
        request.setAttribute("parametro3", parametro3);
        request.setAttribute("caminhoBaseRelatorio", caminhoBaseRelatorio);

        if (filtros.equals("")) {
            filtros = "nenhum";
        }
        request.setAttribute("filtros", filtros);
        context().getExternalContext().dispatch("/VisualizadorRelatorio");
        context().getCurrentInstance().responseComplete();
    }

    public void apresentarRelatorioObjetos(String nomeRelatorio,
            String tituloRelatorio,
            String nomeEmpresa,
            Integer codigoEmpresa,
            String mensagemRel,
            String tipoRelatorio,
            String parserBuscaTags,
            String designIReport,
            String nomeUsuario,
            String filtros,
            List listaObjetos,
            String caminhoBaseRelatorio) throws Exception {
        apresentarRelatorioObjetos(nomeRelatorio, tituloRelatorio, nomeEmpresa, codigoEmpresa, mensagemRel, tipoRelatorio, parserBuscaTags, designIReport, nomeUsuario, filtros, listaObjetos, caminhoBaseRelatorio, false);
    }

    public void apresentarRelatorioObjetos(String nomeRelatorio,
            String tituloRelatorio,
            String nomeEmpresa,
            Integer codigoEmpresa,
            String mensagemRel,
            String tipoRelatorio,
            String parserBuscaTags,
            String designIReport,
            String nomeUsuario,
            String filtros,
            List listaObjetos,
            String caminhoBaseRelatorio, Boolean impressaoLocal) throws Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        request.setAttribute("tipoRelatorio", tipoRelatorio);
        request.setAttribute("nomeRelatorio", nomeRelatorio);
        request.setAttribute("nomeEmpresa", nomeEmpresa);
        request.setAttribute("codigoEmpresa", codigoEmpresa);
        request.setAttribute("mensagemRel", mensagemRel);
        request.setAttribute("tituloRelatorio", tituloRelatorio);
        request.setAttribute("caminhoParserXML", parserBuscaTags);
        request.setAttribute("nomeDesignIReport", designIReport);
        request.setAttribute("nomeUsuario", nomeUsuario);
        request.setAttribute("listaObjetos", listaObjetos);
        request.setAttribute("tipoImplementacao", "OBJETO");
        request.setAttribute("caminhoBaseRelatorio", caminhoBaseRelatorio);
        if (filtros.equals("")) {
            filtros = "nenhum";
        }
        request.setAttribute("filtros", filtros);
        if (impressaoLocal) {
            String ip = getIpUsuarioRemoto();
            request.setAttribute("ip", ip);
            context().getExternalContext().dispatch("/ServletImpressaoPDF");
        } else {
            context().getExternalContext().dispatch("/VisualizadorRelatorio");
        }
        if (!impressaoLocal) {
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    public void adicionarCabecalho(String titulo, String empresa, String filtros) {
        tituloRelatorioMatricial = titulo;
        empresaRelatorioMatricial = empresa;
        adicionarCabecalho();

    }

    private void adicionarCabecalho() {
        adicionarLinha(linhaDupla, 0, true);
        adicionarLinha(empresaRelatorioMatricial, 0, false);
        adicionarLinha(tituloRelatorioMatricial, 68 - (tituloRelatorioMatricial.length() / 2), false);
        String dataHora = Uteis.getDataAtual() + " " + Uteis.getHoraAtual();
        String paginacao = "Pagina " + numeroPagina + "/{[]}  ";
        adicionarLinha(dataHora, 136 - (dataHora + "  " + paginacao).length(), false);
        adicionarLinha(paginacao, 136 - paginacao.length(), true);
        adicionarLinha(linhaDupla, 0, true);
        numeroPagina++;
    }

    public void adicionarRodape() {
        adicionarLinha(linhaSimples, 0, true);
        adicionarLinha(getUsuarioLogado().getNome(), 0, false);
        String mensagem = "NEVERA Sistemas";
        adicionarLinha(mensagem, 136 - mensagem.length(), true);
        adicionarLinhaEmBranco(3);
        contadorLinhaRelatorioMatricial = 0;
    }

    public void adicionarRodapeFinal() {
        adicionarLinhaEmBranco(61 - contadorLinhaRelatorioMatricial);
        contadorLinhaRelatorioMatricial = 0;
        adicionarRodape();
    }

    public void adicionarLinhaEmBranco(int numero) {
        for (int i = 0; i < numero; i++) {
            adicionarLinha("", 0, true);
        }
    }

    public void adicionarLinha(String linha, Integer posicao, Boolean finalizarLinha) {
        ObjetoVO obj = new ObjetoVO();
        if (contadorLinhaRelatorioMatricial == 61) {
            contadorLinhaRelatorioMatricial = 0;
            adicionarRodape();
            adicionarCabecalho();
        }
        obj.setLinha(linha);
        obj.setPosicao(posicao);
        obj.setFinalizarLinha(finalizarLinha);
        getListaImpressaoMatricial().add(obj);
        if (finalizarLinha) {
            contadorLinhaRelatorioMatricial++;
        }

    }

    public void adicionarLinhaZebra(String linha) {
        ObjetoVO obj = new ObjetoVO();
        obj.setLinha(linha);
        obj.setPosicao(0);
        obj.setFinalizarLinha(true);
        getListaImpressaoMatricial().add(obj);

    }

    public void imprimirZebra(String caminhoAplicacao, String porta) throws IOException, Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        Long id = obterIDImpressaoMatricial();
        request.setAttribute("lista", getListaImpressaoMatricial());
        request.setAttribute("id", id.toString());
        context().getExternalContext().dispatch("/Servlet");
        setTagAppletImpressaoMatricial("<applet archive=\"AppletImpressaoMatricial.jar\" code=\"Main.class\" width=\"1\" height=\"1\"> <param name=url value=\"" + caminhoAplicacao + "/Servlet\"> <param name=porta value=\"" + porta + "\"> <param name=id value=" + id + "><PARAM name=\"classloader_cache\" value=\"false\"></applet>");
        setListaImpressaoMatricial(new ArrayList(0));
    }

    public void imprimirZebraSemApplet(String ip) throws IOException, Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        request.setAttribute("lista", getListaImpressaoMatricial());
        request.setAttribute("ip", ip);
        context().getExternalContext().dispatch("/ServletImpressaoMatricial");
        setTagAppletImpressaoMatricial("");
        setListaImpressaoMatricial(new ArrayList(0));
    }

    public void imprimirModoMatricial(String caminhoAplicacao) throws IOException, Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        getListaImpressaoMatricial().remove(getListaImpressaoMatricial().size() - 2);
        adicionarTotalPaginasPaginador();
        Long id = obterIDImpressaoMatricial();
        request.setAttribute("lista", getListaImpressaoMatricial());
        request.setAttribute("id", id.toString());
        context().getExternalContext().dispatch("/Servlet");
        setTagAppletImpressaoMatricial("<applet archive=\"AppletImpressaoMatricial.jar\" code=\"Main.class\" width=\"1\" height=\"1\"> <param name=url value=\"" + caminhoAplicacao + "/Servlet\"> <param name=porta value=\"lpt1\"> <param name=id value=" + id + "><PARAM name=\"classloader_cache\" value=\"false\"></applet>");
        setListaImpressaoMatricial(new ArrayList(0));
        contadorLinhaRelatorioMatricial = 0;
        numeroPagina = 1;
    }

    public void imprimirModoMatricialSemApplet(String ip) throws IOException, Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        getListaImpressaoMatricial().remove(getListaImpressaoMatricial().size() - 2);
        adicionarTotalPaginasPaginador();
        request.setAttribute("lista", getListaImpressaoMatricial());
        request.setAttribute("ip", ip);
        context().getExternalContext().dispatch("/ServletImpressaoMatricial");
        setTagAppletImpressaoMatricial("");
        setListaImpressaoMatricial(new ArrayList(0));
        contadorLinhaRelatorioMatricial = 0;
        numeroPagina = 1;
    }

    public void imprimirModoBluetooth() throws IOException, Exception {
        HttpServletRequest request = (HttpServletRequest) context().getExternalContext().getRequest();
        List<String> lista = new ArrayList<String>(0);
        for (String linha : (List<String>) getListaImpressaoMatricial()) {
            lista.add(formatarTextoImpressoraBluetooth(linha));
        }
        request.setAttribute("lista", lista);
        //request.setAttribute("id", getUsuarioLogado().getIdImpressaoMatricialInternet());
        context().getExternalContext().dispatch("/ServletImpressaoBluetooth");
        setListaImpressaoMatricial(new ArrayList(0));
    }

    private String formatarTextoPOSBluetooth(String texto) {
        String ESC = "\u001B";
        String abreNegrito = ESC + "E" + "\u0001";
        String fechaNegrito = ESC + "E" + "\0";
        String fonte0 = ESC + "\u0021" + "\u0001";
        String fonte1 = ESC + "\u0021" + "\u0000";
        String fonte2 = ESC + "\u0021" + "\u0031";
        String fonte3 = ESC + "\u0021" + "\u0030";
        texto = texto.replace("<negrito>", abreNegrito);
        texto = texto.replace("</negrito>", fechaNegrito);
        texto = texto.replace("<fonte0>", fonte0);
        texto = texto.replace("<fonte1>", fonte1);
        texto = texto.replace("<fonte2>", fonte2);
        texto = texto.replace("<fonte3>", fonte3);
        texto = texto.replace("<linha>", abreNegrito + fonte2 + "----------------" + fechaNegrito);
        texto = texto.replace("<linhaDupla>", abreNegrito + fonte2 + "================" + fechaNegrito);
        texto = texto.replace("<linhaPontilhada>", abreNegrito + fonte2 + "................" + fechaNegrito);
        //texto = texto.replace("<fonte2>", abreFonte2x);
        //texto = texto.replace("</fonte2>", fechaFonte2x);
        return texto;
    }

    private String formatarTextoBluetoothCarregamento(String texto) {
        String ESC = "\u001B";
        String abreNegrito = ESC + "E" + "\u0001";
        String fechaNegrito = ESC + "E" + "\0";
        String fonte0 = ESC + "\u0021" + "\u0001";
        String fonte1 = ESC + "\u0021" + "\u0000";
        String fonte2 = ESC + "\u0021" + "\u0031";
        String fonte3 = ESC + "\u0021" + "\u0030";
        texto = texto.replace("<negrito>", abreNegrito);
        texto = texto.replace("</negrito>", fechaNegrito);
        texto = texto.replace("<fonte0>", fonte0);
        texto = texto.replace("<fonte1>", fonte1);
        texto = texto.replace("<fonte2>", fonte2);
        texto = texto.replace("<fonte3>", fonte3);
        texto = texto.replace("<linha>", abreNegrito + fonte2 + "--------------------------------" + fechaNegrito);
        texto = texto.replace("<linhaDupla>", abreNegrito + fonte2 + "================================" + fechaNegrito);
        texto = texto.replace("<linhaPontilhada>", abreNegrito + fonte2 + "................................" + fechaNegrito);
        //texto = texto.replace("<fonte2>", abreFonte2x);
        //texto = texto.replace("</fonte2>", fechaFonte2x);
        return texto;
    }

    private String formatarTextoImpressoraBluetooth(String texto) {
        String ESC = "\u001B";
        String abreNegrito = ESC + "E" + "\u0001";
        String fechaNegrito = ESC + "E" + "\0";
        String fonte0 = ESC + "\u0021" + "\u0001";
        String fonte1 = ESC + "\u0021" + "\u0000";
        String fonte2 = ESC + "\u0021" + "\u0031";
        String fonte3 = ESC + "\u0021" + "\u0030";
        texto = texto.replace("<negrito>", abreNegrito);
        texto = texto.replace("</negrito>", fechaNegrito);
        texto = texto.replace("<fonte0>", fonte0);
        texto = texto.replace("<fonte1>", fonte1);
        texto = texto.replace("<fonte2>", fonte2);
        texto = texto.replace("<fonte3>", fonte3);
        texto = texto.replace("<linha>", abreNegrito + fonte2 + "---------------------" + fechaNegrito);
        texto = texto.replace("<linhaDupla>", abreNegrito + fonte2 + "=====================" + fechaNegrito);
        texto = texto.replace("<linhaPontilhada>", abreNegrito + fonte2 + "....................." + fechaNegrito);
        //texto = texto.replace("<fonte2>", abreFonte2x);
        //texto = texto.replace("</fonte2>", fechaFonte2x);
        return texto;
    }

    private Long obterIDImpressaoMatricial() {
        //return   ( int ) ( 1 + ( Math.random() * 1000000 ) )  ;
        Date data = new Date();
        return data.getTime();
    }

    private void adicionarTotalPaginasPaginador() {
        for (ObjetoVO obj : (List<ObjetoVO>) getListaImpressaoMatricial()) {
            if (obj.getLinha().contains("{[]}")) {
                obj.setLinha(obj.getLinha().replace("{[]}", "" + (numeroPagina - 1)));
            }
        }
    }

    public List getListaSelectItemOrdenacoesRelatorio() {
        return listaSelectItemOrdenacoesRelatorio;
    }

    public void setListaSelectItemOrdenacoesRelatorio(List listaSelectItemOrdenacoesRelatorio) {
        this.listaSelectItemOrdenacoesRelatorio = listaSelectItemOrdenacoesRelatorio;
    }

    public Integer getOpcaoOrdenacao() {
        return opcaoOrdenacao;
    }

    public void setOpcaoOrdenacao(Integer opcaoOrdenacao) {
        this.opcaoOrdenacao = opcaoOrdenacao;
    }

    public List getListaImpressaoMatricial() {
        if (listaImpressaoMatricial == null) {
            listaImpressaoMatricial = new ArrayList(0);
        }
        return listaImpressaoMatricial;
    }

    public void setListaImpressaoMatricial(List listaImpressaoMatricial) {
        this.listaImpressaoMatricial = listaImpressaoMatricial;
    }

    public Boolean getApresentarAppletImpressaoMatricial() {
        if (tagAppletImpressaoMatricial == null) {
            return false;
        }
        if (tagAppletImpressaoMatricial.equals("")) {
            return false;
        }
        return true;
    }

    public void limparTagApplet() {
        setTagAppletImpressaoMatricial("");
        setListaImpressaoMatricial(new ArrayList(0));
    }

    public void limparListaImpressao() {
        setListaImpressaoMatricial(new ArrayList(0));
    }

    public String getTagAppletImpressaoMatricial() {
        if (tagAppletImpressaoMatricial == null) {
            tagAppletImpressaoMatricial = "";
        }
        return tagAppletImpressaoMatricial;
    }

    public void setTagAppletImpressaoMatricial(String tagAppletImpressaoMatricial) {
        this.tagAppletImpressaoMatricial = tagAppletImpressaoMatricial;
    }

    public void liberarBackingBeanMemoria() {
        try {
            super.liberarBackingBeanMemoria();
            limparTagApplet();
        } catch (Exception e) {
            System.out.println("Nao conseguimos remover o Backing da Memória" + this.getClass().getSimpleName());
        }
    }
}
