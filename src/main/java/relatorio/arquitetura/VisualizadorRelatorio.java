//relatorio
package relatorio.arquitetura;

import it.businesslogic.ireport.connection.JRXMLDataSource;
import it.businesslogic.ireport.export.JRTxtExporter;
import it.businesslogic.ireport.export.JRTxtExporterParameter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import controle.arquitetura.LoginControle;

/**
 * Servlet responsável por gerar a visualização do relatório e apresentá-lo ao USUÁRIO.
 * é utilizado por todos os relatórios do sistema com esta finalidade. é capaz de receber os
 * dados do relatório e compilar o relatório final utilizando o JasperReport.
 */
public class VisualizadorRelatorio extends HttpServlet {

    private HashMap parametrosRelatorio;
    private String nomeRelatorio;
    private String nomeEmpresa;
    private Integer codigoEmpresa;
    private String nomeDesignIReport;
    private String caminhoParserXML;
    private String xmlDados;
    private String tipoRelatorio;
    private String mensagemRel;
    private List listaObjetos;
    private String tipoImplementacao;
    private String caminhoWebAplicacao;

    /**
     * Rotina responsável por obter o diretório real da aplicação Web em execução.
     * é importante acessar este diretório para que seja possível utilizar recursos
     * existentes nos pacotes da aplicação.
     */
    public String obterCaminhoBaseAplicacao() throws Exception {
        return getServletContext().getRealPath("WEB-INF" + File.separator + "classes");
    }

    /**
     * Rotina responsável por obter o diretório real da aplicação Web em execução.
     * é importante acessar este diretório para que seja possível utilizar recursos
     * existentes nos pacotes da aplicação.
     */
    public String obterCaminhoWebAplicacao() throws Exception {
        try {
            return getServletContext().getRealPath("");
        } catch (Exception ex) {
            return getCaminhoWebAplicacao();
        }
    }

    public InputStream getImagem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String caminhoApp = obterCaminhoWebAplicacao();
        String caminho = caminhoApp + File.separator + "imagens" + File.separator + "logoPadraoRelatorio.jpg";
        File imagem = new File(caminho);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte buffer[] = new byte[4096];
        int bytesRead = 0;
        FileInputStream fi = new FileInputStream(imagem.getAbsolutePath());
        while ((bytesRead = fi.read(buffer)) != -1) {
            arrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] a = (arrayOutputStream.toByteArray());
        InputStream fs = new ByteArrayInputStream(a);
        arrayOutputStream.close();
        fi.close();
        return fs;

    }

    public InputStream getImagemGuiaTransito(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String caminhoApp = obterCaminhoWebAplicacao();
        String caminho = caminhoApp + File.separator + "imagens" + File.separator + "AGRODEFESA.jpg";
        File imagem = new File(caminho);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte buffer[] = new byte[4096];
        int bytesRead = 0;
        FileInputStream fi = new FileInputStream(imagem.getAbsolutePath());
        while ((bytesRead = fi.read(buffer)) != -1) {
            arrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] a = (arrayOutputStream.toByteArray());
        InputStream fs = new ByteArrayInputStream(a);
        arrayOutputStream.close();
        fi.close();
        return fs;
    }

    public InputStream getImagemBrasaoGoias(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String caminhoApp = obterCaminhoWebAplicacao();
        String caminho = caminhoApp + File.separator + "imagens" + File.separator + "BRASAOGOIAS.jpg";
        File imagem = new File(caminho);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte buffer[] = new byte[4096];
        int bytesRead = 0;
        FileInputStream fi = new FileInputStream(imagem.getAbsolutePath());
        while ((bytesRead = fi.read(buffer)) != -1) {
            arrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] a = (arrayOutputStream.toByteArray());
        InputStream fs = new ByteArrayInputStream(a);
        arrayOutputStream.close();
        fi.close();
        return fs;
    }

    public JasperPrint gerarRelatorioJasperPrint(HttpServletRequest request, HttpServletResponse response, String xmlDados, String caminhoParserXML, String nomeDesignIReport) throws Exception {
        JRXMLDataSource jrxmlds = new JRXMLDataSource(new ByteArrayInputStream(xmlDados.getBytes()), caminhoParserXML);
        String nomeJasperReportDesignIReport = nomeDesignIReport.substring(0, nomeDesignIReport.lastIndexOf(".")) + ".jasper";
        File arquivoIReport = new File(obterCaminhoBaseAplicacao() + File.separator + nomeJasperReportDesignIReport);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);
        jasperReport.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
        jasperReport.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
        JasperPrint print = JasperFillManager.fillReport(jasperReport, getParametrosRelatorio(), jrxmlds);

        return print;
    }

    public JasperPrint gerarRelatorioJasperPrintObjeto(HttpServletRequest request, HttpServletResponse response, String nomeDesignIReport) throws Exception {

        JRDataSource jr = new JRBeanArrayDataSource(getListaObjetos().toArray());
        String nomeJasperReportDesignIReport = nomeDesignIReport.substring(0, nomeDesignIReport.lastIndexOf(".")) + ".jasper";
        File arquivoIReport = new File(obterCaminhoBaseAplicacao() + File.separator + nomeJasperReportDesignIReport);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivoIReport);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, getParametrosRelatorio(), jr);

        return print;
    }

    protected void visualizarRelatorioHTML(HttpServletRequest request,
            HttpServletResponse response,
            JasperPrint print) throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        // Gerar relatório no padrão HTML
        JRHtmlExporter jrhtmlexporter = new JRHtmlExporter();

        Map imagesMap = new HashMap();
        request.getSession().setAttribute("IMAGES_MAP", imagesMap);
        jrhtmlexporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
        jrhtmlexporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
        jrhtmlexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        jrhtmlexporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
        jrhtmlexporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
        jrhtmlexporter.exportReport();
    }

    protected void visualizarRelatorioPDF(HttpServletRequest request, HttpServletResponse response, JasperPrint print) throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Visualizador de Relatório</title>");
        out.println("</head>");

        String nomePDF = new Date().getTime() + ".pdf";
        String nomeRelPDF = "relatorio" + File.separator + nomePDF;
        File pdfFile = new File(obterCaminhoWebAplicacao() + File.separator + nomeRelPDF);

        if (pdfFile.exists()) {
            try {
                pdfFile.delete();
            } catch (Exception e) {
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
                String dataStr = formatador.format(new Date());
                nomePDF = getNomeRelatorio() + dataStr + ".pdf";
                nomeRelPDF = "relatorios" + File.separator + nomePDF;
                pdfFile = new File(obterCaminhoWebAplicacao() + File.separator + nomeRelPDF);
            }
        }

        JRPdfExporter jrpdfexporter = new JRPdfExporter();
        jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);

        jrpdfexporter.exportReport();

        out.println("<frameset cols=\"*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">");
        String urlAplicacao = request.getRequestURI().toString();
        urlAplicacao = urlAplicacao.substring(0, urlAplicacao.lastIndexOf("/"));
        out.println("<frame src=\"" + urlAplicacao + "/relatorio/" + nomePDF + "\" name=\"mainFrame\">");
        out.println("</frameset>");
        out.println("<noframes><body>");
        out.println("</body></noframes>");
        out.println("</html>");
        out.close();
    }
    protected void impressaoLocalPDFFile(HttpServletRequest request, HttpServletResponse response, JasperPrint print) throws ServletException, IOException, Exception {
        this.impressaoLocalPDF(request, response, print);
    }

    protected File impressaoLocalPDF(HttpServletRequest request, HttpServletResponse response, JasperPrint print) throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");


        String nomePDF = new Date().getTime() + ".pdf";
        String nomeRelPDF = "relatorio" + File.separator + nomePDF;
        File pdfFile = new File(obterCaminhoWebAplicacao() + File.separator + nomeRelPDF);

        if (pdfFile.exists()) {
            try {
                pdfFile.delete();
            } catch (Exception e) {
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
                String dataStr = formatador.format(new Date());
                nomePDF = getNomeRelatorio() + dataStr + ".pdf";
                nomeRelPDF = "relatorios" + File.separator + nomePDF;
                pdfFile = new File(obterCaminhoWebAplicacao() + File.separator + nomeRelPDF);
            }
        }

        JRPdfExporter jrpdfexporter = new JRPdfExporter();
        jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);

        jrpdfexporter.exportReport();

        String urlAplicacao = request.getRequestURI().toString();
        urlAplicacao = urlAplicacao.substring(0, urlAplicacao.lastIndexOf("/"));
        return pdfFile;
    }

    protected void visualizarRelatorioEmail(HttpServletRequest request, HttpServletResponse response, JasperPrint print) throws ServletException, IOException, Exception {

        String nomePDF = getNomeRelatorio() + ".pdf";
        String nomeRelPDF = "relatorio" + File.separator + nomePDF;
        File pdfFile = new File(obterCaminhoWebAplicacao() + File.separator + nomeRelPDF);

        if (pdfFile.exists()) {
            pdfFile.delete();
        }
        JRPdfExporter jrpdfexporter = new JRPdfExporter();
        jrpdfexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        jrpdfexporter.setParameter(JRExporterParameter.OUTPUT_FILE, pdfFile);
        jrpdfexporter.exportReport();

    }

    protected void inicializarParametrosRelatorio(HttpServletRequest request, HttpServletResponse response) throws Exception {
        setNomeRelatorio((String) request.getAttribute("nomeRelatorio"));
        setNomeEmpresa((String) request.getAttribute("nomeEmpresa"));
        setCodigoEmpresa((Integer) request.getAttribute("codigoEmpresa"));
        setXmlDados((String) request.getAttribute("xmlRelatorio"));
        setNomeDesignIReport((String) request.getAttribute("nomeDesignIReport"));
        setCaminhoParserXML((String) request.getAttribute("caminhoParserXML"));
        setTipoImplementacao((String) request.getAttribute("tipoImplementacao"));
        setTipoRelatorio((String) request.getAttribute("tipoRelatorio"));
        setListaObjetos((List) request.getAttribute("listaObjetos"));
        setMensagemRel((String) request.getAttribute("mensagemRel"));
        if ((getTipoRelatorio() == null) || getTipoRelatorio().equals("")) {
            setTipoRelatorio("PDF");
        }
        HashMap parameters = new HashMap();
        //String caminhoImagemLogo = "imagens" + File.separator + "logoPadraoRelatorio.png";
        String caminhoImagemLogoNFE = "imagens" + File.separator + "logoPadraoRelatorioNFE.png";
        Boolean nfe = false;
        if (getTipoRelatorio().equals("DANFE")) {
            nfe = true;
        }
        if (getConfiguracaoSistemaLogado().getImagemPadraoRelatorio(nfe) == null) {
            parameters.put("logoPadraoRelatorio", getImagem(request, response));
        } else {
            parameters.put("logoPadraoRelatorio", getConfiguracaoSistemaLogado().getImagemPadraoRelatorio(nfe));

        }
        //parameters.put("logoPadraoRelatorio", obterCaminhoWebAplicacao(request, response) + File.separator + caminhoImagemLogo);
        parameters.put("logoPadraoRelatorioNFE", obterCaminhoWebAplicacao() + File.separator + caminhoImagemLogoNFE);
        parameters.put("pastaPadraoImagens", obterCaminhoWebAplicacao() + File.separator + "imagens" + File.separator);
        parameters.put("tituloRelatorio", (String) request.getAttribute("tituloRelatorio"));
        parameters.put("usuario", (String) request.getAttribute("nomeUsuario"));
        parameters.put("filtros", "Filtros: " + (String) request.getAttribute("filtros"));
        parameters.put("nomeEmpresa", getNomeEmpresa());
        parameters.put("mensagemRel", getMensagemRel());
        parameters.put("versaoSoftware", "NEVERA SISTEMAS - www.nevera.io");
        parameters.put("caminhoBaseRelatorio", request.getAttribute("caminhoBaseRelatorio"));
        parameters.put("SUBREPORT_DIR", request.getAttribute("caminhoBaseRelatorio"));
        parameters.put("imagemAgrodefesa", getImagemGuiaTransito(request, response));
        parameters.put("imagemBrasaoGoias", getImagemBrasaoGoias(request, response));
        String parametro1 = (String) request.getAttribute("parametro1");
        if (parametro1 == null) {
            parametro1 = "";
        }
        parameters.put("parametro1", parametro1);

        String parametro2 = (String) request.getAttribute("parametro2");
        if (parametro2 == null) {
            parametro2 = "";
        }
        parameters.put("parametro2", parametro2);

        String parametro3 = (String) request.getAttribute("parametro3");
        if (parametro3 == null) {
            parametro3 = "";
        }
        parameters.put("parametro3", parametro3);
        setParametrosRelatorio(parameters);
    }

    public static ConfiguracaoSistemaVO getConfiguracaoSistemaLogado() throws Exception {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        return loginControle.getConfiguracaoEmpresaLogado();
    }

    protected static FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        inicializarParametrosRelatorio(request, response);
        JasperPrint print = null;
        if (tipoImplementacao == null) {
            tipoImplementacao = "";
        }
        if (tipoImplementacao.equals("")) {
            print = gerarRelatorioJasperPrint(request, response, getXmlDados(), getCaminhoParserXML(), getNomeDesignIReport());
        } else {
            print = gerarRelatorioJasperPrintObjeto(request, response, getNomeDesignIReport());
        }
        if (getTipoRelatorio().equals("impressaoLocal")) {
            impressaoLocalPDF(request, response, print);
        }
        if (getTipoRelatorio().equals("PDF")) {
            visualizarRelatorioPDF(request, response, print);
        } else if (getTipoRelatorio().equals("DANFE")) {
            visualizarRelatorioPDF(request, response, print);
        } else if (getTipoRelatorio().equals("EMAIL")) {
            visualizarRelatorioEmail(request, response, print);
        } else {
            visualizarRelatorioHTML(request, response, print);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO RELATÓRIO: " + e.getMessage());
        }
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Visualizador de Relatório</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>ERRO:</h1>");
            out.println("<h2>" + e.getMessage() + "</h2>");
            out.println("</body>");
            out.println("</html>");
            out.close();
            System.out.println("ERRO RELATÓRIO: " + e.getMessage());
        }
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet responsável por imprimir os relatórios em PDF do IReport.";
    }
    // </editor-fold>

    public HashMap getParametrosRelatorio() {
        return parametrosRelatorio;
    }

    public void setParametrosRelatorio(HashMap parametrosRelatorio) {
        this.parametrosRelatorio = parametrosRelatorio;
    }

    public String getNomeDesignIReport() {
        return nomeDesignIReport;
    }

    public void setNomeDesignIReport(String nomeDesignIReport) {
        this.nomeDesignIReport = nomeDesignIReport;
    }

    public String getCaminhoParserXML() {
        return caminhoParserXML;
    }

    public void setCaminhoParserXML(String caminhoParserXML) {
        this.caminhoParserXML = caminhoParserXML;
    }

    public String getXmlDados() {
        return xmlDados;
    }

    public void setXmlDados(String xmlDados) {
        this.xmlDados = xmlDados;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public String getNomeRelatorio() {
        return nomeRelatorio;
    }

    public void setNomeRelatorio(String nomeRelatorio) {
        this.nomeRelatorio = nomeRelatorio;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getMensagemRel() {
        return mensagemRel;
    }

    public void setMensagemRel(String mensagemRel) {
        this.mensagemRel = mensagemRel;
    }

    public List getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(List listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public String getTipoImplementacao() {
        if (tipoImplementacao == null) {
            return "";
        }
        return tipoImplementacao;
    }

    public void setTipoImplementacao(String tipoImplementacao) {
        this.tipoImplementacao = tipoImplementacao;
    }

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getCaminhoWebAplicacao() {
        return caminhoWebAplicacao;
    }

    public void setCaminhoWebAplicacao(String caminhoWebAplicacao) {
        this.caminhoWebAplicacao = caminhoWebAplicacao;
    }
}
