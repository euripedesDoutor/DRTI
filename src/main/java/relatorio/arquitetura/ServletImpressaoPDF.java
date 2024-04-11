package relatorio.arquitetura;

import controle.arquitetura.LoginControle;
import it.businesslogic.ireport.connection.JRXMLDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Flayson Potenciano
 */
public class ServletImpressaoPDF extends HttpServlet {

    private HashMap map = new HashMap();
    private String nomeEmpresa;
    private String nomeRelatorio;
    private String tipoRelatorio;
    private JasperPrint print;
    private String filePath;
    private HashMap parametrosRelatorio;
    private Integer codigoEmpresa;
    private String nomeDesignIReport;
    private String caminhoParserXML;
    private String xmlDados;
    private String mensagemRel;
    private List listaObjetos;
    private String tipoImplementacao;
    private File filePDF;
    private List listaFile;

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ip = (String) request.getAttribute("ip");
            if (request.getAttribute("nomeDesignIReport") != null) { //impressao NFe

                inicializarParametrosRelatorio(request, response);

                if (tipoImplementacao == null) {
                    tipoImplementacao = "";
                }

                if (tipoImplementacao.equals("")) {
                    print = gerarRelatorioJasperPrint(request, response, getXmlDados(), getCaminhoParserXML(), getNomeDesignIReport());
                } else {
                    print = gerarRelatorioJasperPrintObjeto(request, response, getNomeDesignIReport());
                }

                impressaoLocalPDF(request, response, print);
                byte[] fileBytes = fileToBytes(getFilePDF());

                if (getMap().containsKey(ip)) { //caso contenha um map com o ip: será adicionado o array bytes
                    if (request.getAttribute("viasImpressaoAutomatica") != null) {
                        int quantidade = (Integer) request.getAttribute("viasImpressaoAutomatica");
                        for (int i = 0; i < quantidade; i++) {
                            ((ArrayList<byte[]>) getMap().get(ip)).add(fileBytes);
                        }
                    } else {
                        ((ArrayList<byte[]>) getMap().get(ip)).add(fileBytes);
                    }
                } else {
                    List lista = new ArrayList();
                    if (request.getAttribute("viasImpressaoAutomatica") != null) {
                        int quantidade = (Integer) request.getAttribute("viasImpressaoAutomatica");
                        for (int i = 0; i < quantidade; i++) {
                            lista.add((byte[]) fileBytes);
                        }
                    } else {
                        lista.add((byte[]) fileBytes);
                    }
                    getMap().put(ip, lista);
                }
                request.setAttribute("nomeDesignIReport", null);
            } else {
//                if (request.getAttribute("localePDF") != null) {
//
//                    File pdfFile = new File(request.getAttribute("localePDF").toString());
//                    byte[] fileBytes = fileToBytes(pdfFile);
//                    if (getMap().containsKey(ip)) {
//                        ((ArrayList<byte[]>) getMap().get(ip)).add(fileBytes);
//                    } else {
////                        getListaFile().add((byte[]) fileBytes);
//                        List lista = new ArrayList();
//                        if (request.getAttribute("viasImpressaoAutomatica") != null) {
//                            int quantidade = (Integer) request.getAttribute("viasImpressaoAutomatica");
//                            for (int i = 0; i < quantidade; i++) {
//                                lista.add((byte[]) fileBytes);
//                            }
//                        } else {
//                            lista.add((byte[]) fileBytes);
//                        }
//
//                        getMap().put(ip, lista);
//                    }
//                    request.setAttribute("localePDF", null);
//                }

                if (request.getAttribute("bytes") != null) {//impressao de boletos
                    byte[] fileBytes = (byte[]) request.getAttribute("bytes");
                    if (getMap().containsKey(ip)) {//caso contenha um map com o ip: será adicionado o array bytes
                        if (request.getAttribute("viasImpressaoAutomatica") != null) {
                            int quantidade = (Integer) request.getAttribute("viasImpressaoAutomatica");
                            for (int i = 0; i < quantidade; i++) {
                                ((ArrayList<byte[]>) getMap().get(ip)).add(fileBytes);
                            }
                        } else {
                            ((ArrayList<byte[]>) getMap().get(ip)).add(fileBytes);
                        }
                    } else {
                        List lista = new ArrayList();
                        if (request.getAttribute("viasImpressaoAutomatica") != null) {
                            int quantidade = (Integer) request.getAttribute("viasImpressaoAutomatica");
                            for (int i = 0; i < quantidade; i++) {
                                lista.add((byte[]) fileBytes);
                            }
                        } else {
                            lista.add((byte[]) fileBytes);
                        }
                        getMap().put(ip, lista);
                    }
                    request.setAttribute("bytes", null);
                }
            }
//            request.setAttribute("viasImpressaoAutomatica", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String ip = request.getParameter("ip");

        String comando = request.getParameter("comando");

        if (comando != null) {
            String get = request.getParameter("get");
            if (comando.equals("get")) {
                try {
                    Integer id = Integer.parseInt(get);
                    if (getListaFile().size() > id) {
                        this.downloadArquivoPDF(request, response, id);
                    }
                } catch (Exception exception) {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>LISTA DE PDFs</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>ID não existe</h1></br>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                }
            } else {
                if (comando.equals("listarPDF")) {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>LISTA DE PDFs</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>LISTA DE PDFs</h1></br>");
//                    for (Object pdfFile : getListaFile()) {
//                        out.println("<h2>" + ((File) pdfFile).getName() + "</h2></br>");
//                    }
                    if (map.isEmpty()) {
                        out.println("<h2>VAZIA</h2></br>");

                    }
                    Set keysID = map.keySet();
                    for (Object key : keysID) {
                        out.println("<ol>" + key + "</ol>");
                    }
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                }
            }
        } else {
            ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
            if (((List) getMap().get(ip)) != null) {
                output.writeUnshared(((List) getMap().get(ip)));
            } else {
                output.writeUnshared(null);
            }

            if (((List) getMap().get(ip)) != null) {
                getMap().remove(ip);
            }
//            setListaFile(new ArrayList());
            output.flush();
        }
    }

    public byte[] fileToBytes(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null) {
                    ous.close();
                }
            } catch (IOException e) {
            }

            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    protected void downloadArquivoPDF(HttpServletRequest request,
            HttpServletResponse response, Integer id) throws ServletException, IOException {
        String ip = request.getParameter("ip");
//        File file = (File) getMap().get(ip);
        List<File> listFile = (ArrayList) getMap().get(ip);
//        if (!file.isEmpty()) {
//            getMap().remove(ip);
//        }
        File file = listFile.get(id);
        if (file != null) {
            FileInputStream inStream = new FileInputStream(file);
            String relativePath = getServletContext().getRealPath("");
            System.out.println("relativePath = " + relativePath);
            ServletContext context = getServletContext();
            String mimeType = context.getMimeType(getFilePath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            response.setContentType(mimeType);
            response.setContentLength((int) file.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
            listFile.remove(id);
            if (listFile.isEmpty()) {
                getMap().remove(ip);
            }
        }
//        setFilePDF(null);
    }

    protected void impressaoLocalPDF(HttpServletRequest request, HttpServletResponse response, JasperPrint print) throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        String nomePDF = new Date().getTime() + ".pdf";
        String nomeRelPDF = "relatorio" + File.separator + nomePDF;
        File pdfFile = new File(obterCaminhoWebAplicacao() + File.separator + nomeRelPDF);
        obterCaminhoBaseAplicacao();
        obterCaminhoWebAplicacao();
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
        setFilePDF(pdfFile);
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

        return JasperFillManager.fillReport(jasperReport, getParametrosRelatorio(), jr);
    }

    public String obterCaminhoBaseAplicacao() throws Exception {
        return getServletContext().getRealPath("WEB-INF" + File.separator + "classes");
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

    public String obterCaminhoWebAplicacao() throws Exception {
        return getServletContext().getRealPath("");
    }

    public static ConfiguracaoSistemaVO getConfiguracaoSistemaLogado() throws Exception {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        return loginControle.getConfiguracaoEmpresaLogado();
    }

    protected static FacesContext context() {
        return (FacesContext.getCurrentInstance());
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

    public HashMap getMap() {
        if (map == null) {
            map = new HashMap();
        }
        return map;
    }

    public void setMap(HashMap map) {
        this.map = map;
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

    public JasperPrint getPrint() {
        return print;
    }

    public void setPrint(JasperPrint print) {
        this.print = print;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getServletInfo() {
        return "Servlet para impressão automática.";
    }

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

    public File getFilePDF() {
        return filePDF;
    }

    public void setFilePDF(File filePDF) {
        this.filePDF = filePDF;
    }

    public List getListaFile() {
        if (listaFile == null) {
            listaFile = new ArrayList<File>();
        }
        return listaFile;
    }

    public void setListaFile(List listaFile) {
        this.listaFile = listaFile;
    }
}
