package negocio.comuns.utilitarias;

import com.google.zxing.WriterException;
import controle.arquitetura.LoginControle;
import controle.arquitetura.SuperControle;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilderFactory;
import negocio.comuns.arquitetura.Extenso;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.w3c.dom.Document;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;

public class Uteis {

    public static final int CONSULTAR = 0;
    public static final int INCLUIR = 1;
    public static final int ALTERAR = 2;
    public static final int EXCLUIR = 3;
    public static final int PROXIMO = 5;
    public static final int ANTERIOR = 6;
    public static final int INICIALIZAR = 7;
    public static final int EXISTENTE = 8;
    public static final int NOVO = 9;
    public static final int ULTIMO = 10;
    public static final int PRIMEIRO = 11;
    public static final int EMITIRRELATORIO = 12;
    public static final int GRAVAR = 13;
    public static final String PERMISSAOTOTAL = "(" + NOVO + ")" + "(" + INCLUIR + ")" + "(" + ALTERAR + ")" + "(" + EXCLUIR + ")" + "(" + CONSULTAR + ")" + "(" + EMITIRRELATORIO + ")";
    public static final String PERMISSAOINCLUIR = "(" + NOVO + ")" + "(" + INCLUIR + ")";
    public static final String PERMISSAOCONSULTAR = "(" + CONSULTAR + ")" + "(" + EMITIRRELATORIO + ")" + "(" + PROXIMO + ")" + "(" + ANTERIOR + ")";
    public static final String PERMISSAOALTERAR = "(" + ALTERAR + ")";
    public static final String PERMISSAOEXCLUIR = "(" + EXCLUIR + ")";
    public static final int TAMANHOLISTA = 10;
    public static final int NIVELMONTARDADOS_TODOS = 0;
    public static final int NIVELMONTARDADOS_DADOSBASICOS = 1;
    public static final int NIVELMONTARDADOS_DADOSENTIDADESUBORDINADAS = 2;
    public static final int NIVELMONTARDADOS_DADOSENVIONFE = 3;
    public static final int NIVELMONTARDADOS_DADOSENTIDADESRELACIONADAS = 4;
    public static final int NIVELMONTARDADOS_DADOSCONSULTAR = 5;
    public static final int NIVELMONTARDADOS_DADOSIMPRESSAOMATRICIAL = 6;
    public static final int NIVELMONTARDADOS_DADOSRICHMODAL = 7;
    public static final int NIVELMONTARDADOS_DADOSRELATORIO = 8;
    public static final int NIVELMONTARDADOS_DADOSBOLETO = 9;
    public static final String TIPOCALCULO_ROMANEIOABATE_DEBITO = "DE";
    public static final String TIPOCALCULO_ROMANEIOABATE_CREDITO = "CR";
    public static final String CALCULO_TABELADESCONTO_INFORMADO = "IN";
    public static final String CALCULO_TABELADESCONTO_AUTOMATICO = "AU";
    public static final String CALCULO_TABELADESCONTO_FUNRURAL = "FU";
    public static final String DETALHAMENTOROMANEIOABATE_TIPOPESAGEM_PESOTOTAL = "PT";
    public static final String DETALHAMENTOROMANEIOABATE_TIPOPESAGEM_PESOPORBANDA = "BA";
    public static final int NIVELMONTARDADOS_DADOSTABELACONS = 14;
    public static final int CODIGO_RETORNO_HTTP_SUCESSO = 200;
    public static final int CODIGO_RETORNO_HTTP_NAO_AUTORIZADO = 401;
    public static final int CODIGO_RETORNO_HTTP_NAO_ENCONTRADO = 404;
    public static final int LIMITE_DIAS_CONSULTA_RELATORIOS = 180;

    /**
     * Creates a new instance of CfgPadrao
     */
    public Uteis() {
    }

    public static Integer getVersaoJava() {
        if (System.getProperty("java.version").contains("1.6.")) {
            return 6;
        }
        if (System.getProperty("java.version").contains("1.7.")) {
            return 7;
        }
        return 8;
    }

    public static Date incrementarDataDiasUteis(Date data, Integer qtdeDiasAcrescentados) {
        Calendar dataInicial = Calendar.getInstance();
        dataInicial.setTime(data);
        while (qtdeDiasAcrescentados > 0) {
            dataInicial.add(Calendar.DAY_OF_MONTH, 1);
            int diaDaSemana = dataInicial.get(Calendar.DAY_OF_WEEK);
            if (diaDaSemana != Calendar.SATURDAY && diaDaSemana != Calendar.SUNDAY) {
                --qtdeDiasAcrescentados;
            }
        }
        return dataInicial.getTime();
    }

    public static Date incrementarData(Date data, Integer dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();
    }

    public static Date decrementarData(Date data, Integer dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, -dias);
        return calendar.getTime();
    }

    public static long obterQuantidadeDiasIntervaloDatas(Date dataMaior, Date dataMenor) {
        return (dataMaior.getTime() - dataMenor.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static String getMesAnterior(Date dataPrm) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dataPrm);

        int ano = dataCalendar.get(Calendar.YEAR);
        int mes = dataCalendar.get(Calendar.MONTH);
        if (mes == 0) {
            mes = 12;
            ano = ano - 1;
            return mes + "/" + ano;
        }

        String mesStr = String.valueOf(mes);
        if (mesStr.length() == 1) {
            mesStr = "0" + mesStr;
        }
        String mesRef = mesStr + "/" + ano;
        return mesRef;
    }

    public static String obterDataHoraRecebimentoNFe(String tagXml) {
        String retorno = tagXml.substring(8, 10);
        retorno += "/";
        retorno += tagXml.substring(5, 7);
        retorno += "/";
        retorno += tagXml.substring(0, 4);
        retorno += " ";
        retorno += tagXml.substring(11, tagXml.length() - 6);
        return retorno;
    }

    public static String obterDataRecebimentoNFe(String tagXml) {
        String retorno = tagXml.substring(8, 10);
        retorno += "/";
        retorno += tagXml.substring(5, 7);
        retorno += "/";
        retorno += tagXml.substring(0, 4);
        return retorno;
    }

    public static String getCaminhoWebArquivos() throws Exception {
        return getCaminhoWeb() + File.separator + "arquivos";
    }

    public static String getCaminhoWebImagens() throws Exception {
        return getCaminhoWeb() + File.separator + "imagens";
    }

    public static String getCaminhoWebRelatorio() throws Exception {
        return getCaminhoWeb() + File.separator + "relatorio";
    }

    public static String getCaminhoWebNFe() throws Exception {
        return getCaminhoWeb() + File.separator + "NFe";
    }

    public static String getCaminhoWeb() throws Exception {
        String path = "";
        //if (getVersaoJava().equals(6)) {
        if (getSistemaWindows()) {
            path = obterCaminhoDiretorioWeb();
        } else {
            path = getCaminhoBaseWeb();
        }
        return path;
    }

    private static String getCaminhoBaseWeb() throws Exception {
        CharSequence space = "%20", replace = " ";
        String path = "";
        path = Class.forName(Uteis.class.getName()).getResource("").getPath().replace(space, replace).replace("/WEB-INF/classes/negocio/comuns/utilitarias/", "");
        return path;
    }

    private static String obterCaminhoDiretorioWeb() throws Exception {
        FacesContext contexto = FacesContext.getCurrentInstance();
        if(contexto == null) {
            throw new Exception("O CONTEXT da aplicação não foi encontrado");
        }
        ServletContext servletContext = (ServletContext) contexto.getExternalContext().getContext();
        if(servletContext == null){
            throw new Exception("O CONTEXT do servlet não foi encontrado");
        }
        return servletContext.getRealPath("");
    }

    public static Boolean getSistemaWindows() {
        if (System.getProperty("os.name").contains("indows")) {
            return true;
        }
        return false;
    }

    public static java.sql.Timestamp getDataJDBCTimestamp(java.util.Date dataConverter) {
        if (dataConverter == null) {
            return null;
        }
        java.sql.Timestamp dataSQL = new java.sql.Timestamp(dataConverter.getTime());
        return dataSQL;
    }

    public static String getObterHoraData(Date data) {
        String tipoData = "dd/MM/yyyy";
        String tipoHora = "HH:mm";

        SimpleDateFormat formata = new SimpleDateFormat(tipoData);
        formata = new SimpleDateFormat(tipoHora);
        return formata.format(data);
    }

    public static String getAnoMes(Date data) {
        String dataStr = getData(data);
        dataStr = removerMascara(dataStr);
        String mes = dataStr.substring(2, 4);
        String ano = dataStr.substring(4, dataStr.length());
        return ano + mes;
    }

    public static String getAnoMesDia(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        String mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (mes.length() == 1) {
            mes = "0" + mes;
        }
        String ano = String.valueOf(calendar.get(Calendar.YEAR));
        String dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        return ano + "-" + mes + "-" + dia;
    }

    public static String gethoraHHMMSSAjustado(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        String hora = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minuto = String.valueOf(cal.get(Calendar.MINUTE));
        String segundo = String.valueOf(cal.get(Calendar.SECOND));

        if (hora.length() == 1) {
            hora = "0" + hora;
        }
        if (minuto.length() == 1) {
            minuto = "0" + minuto;
        }
        if (segundo.length() == 1) {
            segundo = "0" + segundo;
        }

        String horaFormata = hora + ":" + minuto + ":" + segundo;

        return horaFormata;
    }

    public static java.sql.Date getSQLData(java.util.Date dataConverter) {
        if (dataConverter == null) {
            return null;
        }
        java.sql.Date dataSQL = new java.sql.Date(dataConverter.getTime());
        return dataSQL;
    }

    public static java.sql.Date getDataJDBC(java.util.Date dataConverter) {
        if (dataConverter == null) {
            return null;
            //dataConverter = new Date();
        }
        java.sql.Date dataSQL = new java.sql.Date(dataConverter.getTime());
        return dataSQL;
    }

    public static String getDataFormatoBD(java.util.Date dataConverter) {
        return getData(dataConverter, "bd");
    }

    public static String getData(java.util.Date dataConverter, String padrao) {
        if (dataConverter == null) {
            return ("");
        }
        String dataStr;
        if (padrao.equals("bd")) {
            Locale aLocale = new Locale("pt", "BR");
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy.MM.dd", aLocale);
            dataStr = formatador.format(dataConverter);
        } else if (padrao.equals("padrao")) {
            Locale aLocale = new Locale("pt", "BR");
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy", aLocale);
            dataStr = formatador.format(dataConverter);
        } else {
            Locale aLocale = new Locale("pt", "BR");
            SimpleDateFormat formatador = new SimpleDateFormat(padrao, aLocale);
            dataStr = formatador.format(dataConverter);
        }
        return (dataStr);
    }

    public static Date gerarDataInicioMes(Integer mes, Integer ano) throws Exception {
        String ini = "01/" + Uteis.getMesReferencia(mes, ano);
        return Uteis.getData(ini, "dd/MM/yyyy");

    }

    public static Date gerarDataFimMes(Integer mes, Integer ano) throws Exception {
        String fim = Uteis.getMesReferencia(mes, ano);
        Integer qtdDiasMes = Uteis.obterNrDiasNoMes(Uteis.getData("01/" + fim, "dd/MM/yyyy"));
        fim = qtdDiasMes + "/" + fim;
        return Uteis.getData(fim, "dd/MM/yyyy");
    }

    public static String getDataAplicandoFormatacao(Date data, String mascara) {
        if (data == null) {
            return "";
        }
        Locale aLocale = new Locale("pt", "BR");
        SimpleDateFormat formatador = new SimpleDateFormat(mascara, aLocale);
        String dataStr = formatador.format(data);
        return dataStr;
    }

    /* Define-se a mascara a ser aplicada a data atual
     * de acordo com o padrão - dd/mm/yyyy ou MM.yy.dd e assim por diante
     */
    public static String getDataAtualAplicandoFormatacao(String mascara) {
        Date hoje = new Date();
        return getDataAplicandoFormatacao(hoje, mascara);
    }

    public static Date getDateAplicandoFormatacao(String mascara, String data) throws Exception {
        try {
            SimpleDateFormat formato = new SimpleDateFormat(mascara);
            return formato.parse(data);
        } catch (ParseException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static int obterNrDiasNoMes(Date dataPrm) {
        Calendar dataCalendario = Calendar.getInstance();
        dataCalendario.setTime(dataPrm);
        int numeroDias = dataCalendario.getActualMaximum(Calendar.DAY_OF_MONTH);
        return numeroDias;
    }

    public static Integer obterNrDiasEntreDatas(Date dataIni, Date dataFim) {
        return Integer.valueOf(((Long) ((dataFim.getTime() - dataIni.getTime()) / (1000 * 60 * 60 * 24))).toString());
    }

    public static Integer obterMinutosEntreDatas(Date dataIni, Date dataFim) {
        if (dataIni != null) {
            return Integer.valueOf(((Long) ((dataFim.getTime() - dataIni.getTime()) / (1000 * 60))).toString());
        }
        return 999999999;
    }

    public static String getData(java.util.Date dataConverter) {
        return (getData(dataConverter, "padrao"));
    }

    public static Date getData(String data, String formato) throws Exception {
        DateFormat formatter = new SimpleDateFormat(formato);
        return (java.util.Date) formatter.parse(data);
    }

    public static java.util.Date getDate(String data) throws Exception {
        if (data.trim().equals("")) {
            return null;
        }
        java.util.Date valorData = null;
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        valorData = formatador.parse(data);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minuto = cal.get(Calendar.MINUTE);
        int segundo = cal.get(Calendar.SECOND);

        cal.setTime(valorData);
        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, segundo);

        return cal.getTime();
    }

    public static String mascaraTEL(String valor) {
        String mascara = "(XX)XXXX-XXXX";
        int i = valor.length();
        if (i == 10) {
            String parte3 = valor.substring(6);
            String parte2 = valor.substring(2, 6);
            String parte1 = valor.substring(0, 2);
            return "(" + parte1 + ")" + parte2 + "-" + parte3;
        } else {
            return valor;
        }
    }

    public static String mascaraCEP(String valor) {
        String mascara = "XXXXX-XXX";
        int i = valor.length();
        if (i == 8) {
            String fim = valor.substring(5);
            String inicial = valor.substring(0, 5);
            return inicial + "-" + fim;
        } else {
            return valor;
        }
    }

    public static String adicionarMascaraCodigoBarraNFE(String valor) {
        String mascara = "XXXX.XXXX.XXXX.XXXX.XXXX.XXXX.XXXX.XXXX.XXXX.XXXX.XXXX";

        if (valor.length() == 44) {
            String parte1 = valor.substring(0, 4);
            String parte2 = valor.substring(4, 8);
            String parte3 = valor.substring(8, 12);
            String parte4 = valor.substring(12, 16);
            String parte5 = valor.substring(16, 20);
            String parte6 = valor.substring(20, 24);
            String parte7 = valor.substring(24, 28);
            String parte8 = valor.substring(28, 32);
            String parte9 = valor.substring(32, 36);
            String parte10 = valor.substring(36, 40);
            String parte11 = valor.substring(40, 44);
            return parte1 + "." + parte2 + "." + parte3 + "." + parte4 + "." + parte5 + "." + parte6 + "." + parte7 + "." + parte8 + "." + parte9 + "." + parte10 + "." + parte11;
        } else {
            return mascara;
        }
    }

    public static String mascaraCNPJ(String valor) {
        valor = Uteis.removerMascaraCpf(valor);
        int i = valor.length();
        if (i == 14) {
            String parte1 = valor.substring(12);
            String parte2 = valor.substring(8, 12);
            String parte3 = valor.substring(5, 8);
            String parte4 = valor.substring(2, 5);
            String parte5 = valor.substring(0, 2);
            return parte5 + "." + parte4 + "." + parte3 + "/" + parte2 + "-" + parte1;
        } else if (i == 11) {
            String parte1 = valor.substring(9);
            String parte2 = valor.substring(6, 9);
            String parte3 = valor.substring(3, 6);
            String parte4 = valor.substring(0, 3);
            return parte4 + "." + parte3 + "." + parte2 + "-" + parte1;
        } else {
            return valor;
        }
    }

    public static int getDate_DiaSemana(String data) throws Exception {
        java.util.Date valorData = null;
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        valorData = formatador.parse(data);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);

        return diaSemana;
    }

    public static int getDiaSemana(Date data) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);

        return diaSemana;
    }

    public static java.util.Date getDate(String data, Locale local) throws Exception {
        java.util.Date valorData = new Date();
        if (local == null) {
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            valorData = formatador.parse(data);
        } else {
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT, local);
            valorData = formatador.parse(data);
        }
        return valorData;
    }

    public static String getAnoDataAtual() {
        SimpleDateFormat ano = new SimpleDateFormat("yy");
        Date data = new Date();
        return ano.format(data);
    }

    public static String obterAnoDataAtual4numeros() {
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        Date data = new Date();
        return ano.format(data);
    }

    public static String getDataAtual() {
        Date hoje = new Date();
        return (Uteis.getData(hoje));
    }

    public static String getHoraAtual() {
        Locale aLocale = new Locale("pt", "BR");
        DateFormat formatador = DateFormat.getTimeInstance(DateFormat.SHORT, aLocale);
        return (formatador.format(new Date()));
    }

    public static int getHoraAtualNumerico() {
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutosAtualNumerico() {
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getHoraNumerico(Date trialTime) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(trialTime);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutosNumerico(Date trialTime) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(trialTime);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSegundosAtualNumerico() {
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        return calendar.get(Calendar.SECOND);
    }

    public static Date getDateTime(Date data, int hora, int minuto, int segundo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, segundo);

        return cal.getTime();
    }

    public static Date getDataCompleta(int dia, int mes, int ano, int hora, int minuto, int segundo) {
        Calendar cal = Calendar.getInstance();
        mes--;
        //cal.setTime(data);
        cal.set(Calendar.DAY_OF_MONTH, dia);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, segundo);

        return cal.getTime();
    }

    public static long getDateTimeReturnLong(Date data, int hora, int minuto, int segundo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, hora);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, segundo);

        return cal.getTime().getTime();
    }

    public static String gethoraHHMMSS(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        String hora = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String minuto = String.valueOf(cal.get(Calendar.MINUTE));
        String segundo = String.valueOf(cal.get(Calendar.SECOND));
        if (hora.length() == 1) {
            hora = "0" + hora;
        }
        if (minuto.length() == 1) {
            minuto = "0" + minuto;
        }
        if (segundo.length() == 1) {
            segundo = "0" + segundo;
        }
        return hora + ":" + minuto + ":" + segundo;
    }

    public static String gethoraHHMM(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        String hora = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        if (hora.length() == 1) {
            hora = "0" + hora;
        }
        String minuto = String.valueOf(cal.get(Calendar.MINUTE));
        if (minuto.length() == 1) {
            minuto = "0" + minuto;
        }

        return hora + ":" + minuto;
    }

    public static String obterNomeFantasiaObservacao(String valor) {
        int i = valor.lastIndexOf("*FANTASIA*");
        if (i == -1) {
            return " ";
        }
        return valor.substring(i);
    }

    public static String obterEntregaObservacao(String valor) {
        int i = valor.lastIndexOf("*ENTREGA*");
        if (i == -1) {
            return " ";
        }
        return valor.substring(i);
    }

    public static String removerFANTASIANomeFantasia(String valor) {
        int i = valor.lastIndexOf("*");
        if (i == -1) {
            return valor;
        }
        return valor.substring(i + 1);
    }

    public static String removerENTREGA(String valor) {
        int i = valor.lastIndexOf("*");
        if (i == -1) {
            return valor;
        }
        return valor.substring(i + 1);
    }

    public static String removerNomeFantasiaObservacao(String valor) {
        int i = valor.indexOf("*FANTASIA*");
        if (i == -1) {
            return valor;
        }
        return valor.substring(0, i);
    }

    public static String removerEntregaObservacao(String valor) {
        int i = valor.indexOf("*ENTREGA*");
        if (i == -1) {
            return valor;
        }
        return valor.substring(0, i);
    }

    public static String formatarValorDoubleNFE(String valor) {
        int i = valor.indexOf('.');
        if (i == -1) {
            return valor;
        }
        String parc = valor.substring(i + 1);
        valor = valor.substring(0, i + 1);
        if (parc.length() > 2) {
            valor += parc.substring(0, 2);
        }
        return valor;
    }

    public static double arrendondarForcando2CadasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 2, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static Double arrendondarForcando2CadasDecimaisDouble(Double valor) {
        valor = Uteis.arredondar(valor, 2, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando3CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 3, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando4CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 4, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando5CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 5, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        if (extensao.length() == 4) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando6CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 6, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        if (extensao.length() == 4) {
            extensao += "0";
        }
        if (extensao.length() == 5) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando7CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 7, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        if (extensao.length() == 4) {
            extensao += "0";
        }
        if (extensao.length() == 5) {
            extensao += "0";
        }
        if (extensao.length() == 6) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando8CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 8, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        if (extensao.length() == 4) {
            extensao += "0";
        }
        if (extensao.length() == 5) {
            extensao += "0";
        }
        if (extensao.length() == 6) {
            extensao += "0";
        }
        if (extensao.length() == 7) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando9CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 9, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        if (extensao.length() == 4) {
            extensao += "0";
        }
        if (extensao.length() == 5) {
            extensao += "0";
        }
        if (extensao.length() == 6) {
            extensao += "0";
        }
        if (extensao.length() == 7) {
            extensao += "0";
        }
        if (extensao.length() == 8) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static double arrendondarForcando10CasasDecimais(double valor) {
        valor = Uteis.arredondar(valor, 10, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        if (extensao.length() == 3) {
            extensao += "0";
        }
        if (extensao.length() == 4) {
            extensao += "0";
        }
        if (extensao.length() == 5) {
            extensao += "0";
        }
        if (extensao.length() == 6) {
            extensao += "0";
        }
        if (extensao.length() == 7) {
            extensao += "0";
        }
        if (extensao.length() == 8) {
            extensao += "0";
        }
        if (extensao.length() == 9) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira) + "." + extensao;
        return Double.parseDouble(valorStr);
    }

    public static String formatarDoubleParaBoleto(Double valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String numeroFormatado = df.format(arrendondarForcando2CadasDecimais(valor));
        if (numeroFormatado.substring(numeroFormatado.indexOf(",") + 1, numeroFormatado.length()).length() == 1) {
            numeroFormatado = numeroFormatado + "0";
        }
        numeroFormatado = numeroFormatado.replace(",", "");
        numeroFormatado = numeroFormatado.replace(".", "");
        return numeroFormatado;
    }

    public static String arrendondarForcando1CasaDecimalComVirgula(double valor) {
        valor = Uteis.arredondar(valor, 1, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 0) {
            extensao += "0";
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        inteira = nf.format(Double.parseDouble(inteira));
        inteira = inteira.substring(3, inteira.length() - 3);
        valorStr = inteira + "," + extensao;
        return valorStr;
    }

    public static String arrendondarForcando2CadasDecimaisComVirgula(double valor) {
        valor = Uteis.arredondar(valor, 2, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        inteira = nf.format(Double.parseDouble(inteira));
        inteira = inteira.substring(3, inteira.length() - 3);
        valorStr = inteira + "," + extensao;
        return valorStr;
    }

    public static String arredondarForcando3CasasDecimaisComVirgula(double valor) {
        valor = Uteis.arredondar(valor, 3, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        if (extensao.length() == 2) {
            extensao += "0";
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        inteira = nf.format(Double.parseDouble(inteira));
        inteira = inteira.substring(3, inteira.length() - 3);
        valorStr = inteira + "," + extensao;
        return valorStr;
    }

    public static String arrendondarForcando4CadasDecimaisComVirgula(double valor) {
        valor = Uteis.arredondar(valor, 4, 1);
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        while (extensao.length() < 4) {
            extensao += "0";
        }
        valorStr = inteira + "," + extensao;
        return valorStr;
    }

    //Exemplo de formatação com 2 casas decimais: 24.970,00
    public static String arrendondarForcando2CadasDecimaisAlinhadoDireita(double valor, int casasDireita) {
        String valorStr = arrendondarForcando2CadasDecimaisComVirgula(valor);
        return alinharDireita(valorStr, casasDireita);
    }

    public static String arrendondarForcando4CadasDecimaisAlinhadoDireita(double valor, int casasDireita) {
        String valorStr = arrendondarForcando4CadasDecimaisComVirgula(valor);
        return alinharDireita(valorStr, casasDireita);
    }

    public static String alinharDireita(String campo, int casasDireita) {
        while (campo.length() < (casasDireita + 3)) { // mais 3 devido a virgula e as 2 casas decimais (,00)
            campo = " " + campo;
        }
        return campo;
    }

    public static String alinharDireitaValorInteiro(String campo, int casasDireita) {
        while (campo.length() < (casasDireita)) {
            campo = " " + campo;
        }
        return campo;
    }

    public static String centralizar(String campo, int largura) {
        while (campo.length() < (largura / 2)) {
            campo = " " + campo;
        }
        return campo;
    }

    public static String inserir(String conteudo, Integer tamanhoLinha) {
        String novoConteudo = conteudo;
        Integer contador = tamanhoLinha - conteudo.length();
        while (contador > 0) {
            novoConteudo += " ";
            contador--;
        }
        return novoConteudo;
    }

    public static String alinharValorStringDireita(String conteudo, Integer tamanhoLinha) {
        char[] linha = conteudo.toCharArray();
        String comecoLinha = "";
        String conteudoValido = "";
        String retorno = "";
        for (int i = 0; i < linha.length; i++) {
            if (linha[i] != ' ') {
                conteudoValido += linha[i];
            } else if (linha[i] == ' ') {
                comecoLinha += linha[i];
            }
        }

        return comecoLinha + conteudoValido;
    }

    public static String alinharColunaEsquerda(String campo, int larguraLinha) {
        while (campo.length() < (larguraLinha / 2)) {
            campo = campo + " ";
        }
        return campo;
    }

    public static double arredondar(double valor, int casas, int abaixo) {
        valor = (new BigDecimal(valor).setScale(casas, BigDecimal.ROUND_HALF_UP)).doubleValue();
        return valor;
        /*double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (abaixo == 0) {
        arredondado = Math.ceil(arredondado);
        } else {
        arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;*/
    }

    public static double arredondar2(double valor, int casas, int abaixo) {
//        valor = (new BigDecimal(valor).setScale(casas, BigDecimal.ROUND_HALF_UP)).doubleValue();
//        return valor;
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        if (abaixo == 0) {
            arredondado = Math.ceil(arredondado);
        } else {
            arredondado = Math.floor(arredondado);
        }
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }

    public static long nrDiasEntreDatas(Date dataInicial, Date dataFinal) {
        long dias = (dataInicial.getTime() - dataFinal.getTime()) / (1000 * 60 * 60 * 24);
        return dias;
    }

    public static long nrMinutosEntreDatas(Date dataInicial, Date dataFinal) {
        long dias = (dataInicial.getTime() - dataFinal.getTime()) / (1000 * 60);
        return dias;
    }

    public static Date obterDataFutura2(Date dataInicial, int nrDiasProgredir) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dataInicial);

        dataCalendar.add(Calendar.DAY_OF_MONTH, nrDiasProgredir);
        return dataCalendar.getTime();
    }

    public static Date obterDataFuturaParcela(Date dataInicial) throws Exception {
        if (dataInicial == null) {
            return null;
        }
        int nrMesesProgredir = 1;

        int dia = Uteis.getDiaMesData(dataInicial);
        int mes = Uteis.getMesData(dataInicial);
        int ano = Uteis.getAnoData(dataInicial);

        // PROGREDINDO OS ANOS
        if (nrMesesProgredir > 12) {
            while (nrMesesProgredir > 12) {
                ano++;
                nrMesesProgredir += -12;
            }
        }

        // PROGREDINDO OS MESES
        mes += nrMesesProgredir;
        if (mes > 12) {
            mes -= 12;
            ano++;
        }

        if (dia > 30) {
            dia = 1;
        } else {
            // CASO MES SEJA FEVEREIRO
            if ((dia == 29) && (mes == 2)) {
                dia = 1;
            }
        }

        Date dataFutura = Uteis.getDate(dia + "/" + mes + "/" + ano);
        return dataFutura;
    }

    public static Date obterDataFuturaMesContabil(Date dataInicial, int nrTotalDiasProgredir) throws Exception {
        if (dataInicial == null) {
            return null;
        }
        int nrMesesProgredir = nrTotalDiasProgredir / 30;
        int nrDiasProgredir = nrTotalDiasProgredir - (30 * nrMesesProgredir);

        int dia = Uteis.getDiaMesData(dataInicial);
        int mes = Uteis.getMesData(dataInicial);
        int ano = Uteis.getAnoData(dataInicial);

        // PROGREDINDO OS ANOS
        if (nrMesesProgredir > 12) {
            while (nrMesesProgredir > 12) {
                ano++;
                nrMesesProgredir += -12;
            }
        }

        // PROGREDINDO OS MESES
        mes += nrMesesProgredir;
        if (mes > 12) {
            mes -= 12;
            ano++;
        }

        // PROGREDINDO OS DIAS;
        boolean incrementarMes = false;
        if ((dia + nrDiasProgredir) > 30) {
            dia = (dia + nrDiasProgredir) - 30;
            incrementarMes = true;
        } else {
            dia = dia + nrDiasProgredir;
        }

        if (incrementarMes) {
            mes++;
            if (mes == 13) {
                mes = 1;
                ano++;
            }
        }

        Date dataFutura = Uteis.getDate(dia + "/" + mes + "/" + ano);
        return dataFutura;
    }

    public static String getMesReferenciaData(Date dataPrm) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dataPrm);

        int ano = dataCalendar.get(Calendar.YEAR);
        int mes = dataCalendar.get(Calendar.MONTH) + 1;

        String mesStr = String.valueOf(mes);
        if (mesStr.length() == 1) {
            mesStr = "0" + mesStr;
        }
        String mesRef = mesStr + "/" + ano;
        return mesRef;
    }

    public static String getMesReferencia(int mes, int ano) {
        String mesStr = String.valueOf(mes);
        if (mesStr.length() == 1) {
            mesStr = "0" + mesStr;
        }
        mesStr = mesStr + "/" + String.valueOf(ano);
        return mesStr;
    }

    /* Retorna o mês no formato String com 2 caracteres */
    public static String getNumeroString2Caracteres(int mes) {
        String mesStr = String.valueOf(mes);
        if (mesStr.length() == 1) {
            mesStr = "0" + mesStr;
        }
        return mesStr;
    }

    public static int compareMesReferencia(String mesInicial, String mesFinal) {
        String mesInicialOrdenado = mesInicial.substring(mesInicial.indexOf("/") + 1)
                + mesInicial.substring(0, mesInicial.indexOf("/"));
        String mesFinalOrdenado = mesFinal.substring(mesFinal.indexOf("/") + 1)
                + mesFinal.substring(0, mesFinal.indexOf("/"));
        return mesInicialOrdenado.compareTo(mesFinalOrdenado);
    }

    public static String getDataDiaMesAnoConcatenado() {
        String dataAtual = Uteis.getDataAtual();
        String ano = "";
        String mes = "";
        String dia = "";
        int cont = 1;
        while (cont != 3) {
            int posicao = dataAtual.lastIndexOf("/");
            if (posicao != -1) {
                cont++;
                if (cont == 2) {
                    ano = dataAtual.substring(posicao + 1);
                    dataAtual = dataAtual.substring(0, posicao);
                } else if (cont == 3) {
                    mes = dataAtual.substring(posicao + 1);
                    dia = dataAtual.substring(0, posicao);
                }
            }
        }
        return dia + mes + ano;
    }

    public static String getDataAnoMesDiaConcatenado(Date data) {
        String dataAtual = Uteis.getData(data);
        String ano = "";
        String mes = "";
        String dia = "";
        int cont = 1;
        while (cont != 3) {
            int posicao = dataAtual.lastIndexOf("/");
            if (posicao != -1) {
                cont++;
                if (cont == 2) {
                    ano = dataAtual.substring(posicao + 1);
                    dataAtual = dataAtual.substring(0, posicao);
                } else if (cont == 3) {
                    mes = dataAtual.substring(posicao + 1);
                    dia = dataAtual.substring(0, posicao);
                }
            }
        }
        return ano + mes + dia;
    }

    public static String getDataDiaMesAnoConcatenadoPorExtenso() {
        String dataAtual = Uteis.getDataAtual();
        String ano = "";
        String mes = "";
        String dia = "";
        int cont = 1;
        while (cont != 3) {
            int posicao = dataAtual.lastIndexOf("/");
            if (posicao != -1) {
                cont++;
                if (cont == 2) {
                    ano = dataAtual.substring(posicao + 1);
                    dataAtual = dataAtual.substring(0, posicao);
                } else if (cont == 3) {
                    mes = dataAtual.substring(posicao + 1);
                    dia = dataAtual.substring(0, posicao);
                }
            }
        }
        return dia + " de " + getMesExtenso(Integer.parseInt(mes)) + " de " + ano;
    }

    public static String getDataDiaMesAnoConcatenadoPorExtenso(Date dataPrm) {
        String data = Uteis.getData(dataPrm);
        String ano = "";
        String mes = "";
        String dia = "";
        int cont = 1;
        while (cont != 3) {
            int posicao = data.lastIndexOf("/");
            if (posicao != -1) {
                cont++;
                if (cont == 2) {
                    ano = data.substring(posicao + 1);
                    data = data.substring(0, posicao);
                } else if (cont == 3) {
                    mes = data.substring(posicao + 1);
                    dia = data.substring(0, posicao);
                }
            }
        }
        return dia + " de " + getMesExtenso(Integer.parseInt(mes)) + " de " + ano;
    }

    public static String getMesExtenso(Integer mes) {
        if (mes.equals(1)) {
            return "Janeiro";
        } else if (mes.equals(2)) {
            return "Fevereiro";
        } else if (mes.equals(3)) {
            return "Março";
        } else if (mes.equals(4)) {
            return "Abril";
        } else if (mes.equals(5)) {
            return "Maio";
        } else if (mes.equals(6)) {
            return "Junho";
        } else if (mes.equals(7)) {
            return "Julho";
        } else if (mes.equals(8)) {
            return "Agosto";
        } else if (mes.equals(9)) {
            return "Setembro";
        } else if (mes.equals(10)) {
            return "Outubro";
        } else if (mes.equals(11)) {
            return "Novembro";
        } else if (mes.equals(12)) {
            return "Dezembro";
        }
        return "erro";
    }

    public static String getDataDiaMesAnoConcatenado(String dataAtual) {
        String ano = "";
        String mes = "";
        String dia = "";
        int cont = 1;
        while (cont != 3) {
            int posicao = dataAtual.lastIndexOf("/");
            if (posicao == -1) {
                posicao = dataAtual.lastIndexOf("-");
            }
            if (posicao != -1) {
                cont++;
                if (cont == 2) {
                    dia = dataAtual.substring(posicao + 1);
                    dataAtual = dataAtual.substring(0, posicao);
                } else if (cont == 3) {
                    mes = dataAtual.substring(posicao + 1);
                    ano = dataAtual.substring(0, posicao);
                }
            }
        }
        return dia + "/" + mes + "/" + ano;
    }

    public static String getDataMesAnoConcatenado() {
        // return MM/AAAA
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        String mesAtualStr = String.valueOf(mesAtual);
        if (mesAtualStr.length() == 1) {
            mesAtualStr = "0" + mesAtualStr;
        }
        return mesAtualStr + "/" + anoAtual;
    }

    public static String removerMascara(String campo) {
        String campoSemMascara = "";
        for (int i = 0; i < campo.length(); i++) {
            if ((campo.charAt(i) != ',')
                    && (campo.charAt(i) != '.')
                    //&& (campo.charAt(i) != '-')
                    && (campo.charAt(i) != ':')
                    && (campo.charAt(i) != '<')
                    && (campo.charAt(i) != '>')
                    && (campo.charAt(i) != '/')) {
                campoSemMascara = campoSemMascara + campo.substring(i, i + 1);
            }
        }
        return campoSemMascara;
    }

    public static String removerMascaraCpf(String campo) {
        String campoSemMascara = "";
        for (int i = 0; i < campo.length(); i++) {
            if ((campo.charAt(i) != ',')
                    && (campo.charAt(i) != '.')
                    && (campo.charAt(i) != '-')
                    && (campo.charAt(i) != ':')
                    && (campo.charAt(i) != '<')
                    && (campo.charAt(i) != '(')
                    && (campo.charAt(i) != ')')
                    && (campo.charAt(i) != '>')
                    && (campo.charAt(i) != '/')) {
                campoSemMascara = campoSemMascara + campo.substring(i, i + 1);
            }
        }
        return campoSemMascara;
    }

    public static String removerMascaraFinanceiro(String campo) {
        String campoSemMascara = "";
        for (int i = 0; i < campo.length(); i++) {
            if ((campo.charAt(i) != ',')
                    && (campo.charAt(i) != '.')) {
                campoSemMascara = campoSemMascara + campo.substring(i, i + 1);
            }
        }
        return campoSemMascara;
    }

    public static String aplicarMascara(String dado, String mascara) {
        if (dado == null) {
            return dado;
        }
        if (dado.equals("")) {
            return dado;
        }
        if (dado.length() == mascara.length()) {
            return dado;
        }
        dado = removerMascara(dado);
        int posDado = 0;
        String dadoComMascara = "";
        for (int i = 0; i < mascara.length(); i++) {
            if (posDado >= dado.length()) {
                break;
            }
            String caracter = mascara.substring(i, i + 1);
            if (caracter.equals("9")) {
                dadoComMascara = dadoComMascara + dado.substring(posDado, posDado + 1);
                posDado++;
            } else {
                dadoComMascara = dadoComMascara + caracter;
            }
        }
        return dadoComMascara;
    }

    public static String getDoubleFormatado(double valor) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        return nf.format(valor);
    }

    public static int getDiaMesData(Date dataPrm) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dataPrm);

        int dia = dataCalendar.get(Calendar.DAY_OF_MONTH);
        return dia;
    }

    public static int getMesData(Date dataPrm) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dataPrm);

        int mes = dataCalendar.get(Calendar.MONTH) + 1;
        return mes;
    }

    public static int getAnoData(Date dataPrm) {
        Calendar dataCalendar = Calendar.getInstance();
        dataCalendar.setTime(dataPrm);

        int ano = dataCalendar.get(Calendar.YEAR);
        return ano;
    }

    public static String getValorMonetarioParaIntegracao_SemPontoNemVirgula(double valor) {
        String valorStr = String.valueOf(valor);

        String inteira = valorStr.substring(0, valorStr.indexOf("."));
        String extensao = valorStr.substring(valorStr.indexOf(".") + 1, valorStr.length());
        if (extensao.length() == 1) {
            extensao += "0";
        }
        valorStr = Uteis.removerMascara(inteira + extensao);
        return valorStr;
    }

    public static String valorPorExtenso(Double valor) throws Exception {
        Extenso valorExtenso = new Extenso(new BigDecimal(valor));
        return valorExtenso.toString();
    }

    public static String adicionarCampoLinhaVersao2(String linha, String valorCampo, int posIni, int posFim, String caracterPreenchimento, boolean alinharEsquerda, boolean novaLinha) throws Exception {
        try {
            if (valorCampo == null) {
                valorCampo = "";
            }
            posFim++;
            String linhaDados = "";
            if (alinharEsquerda) {
                if (valorCampo.length() <= posFim - posIni) {
                    if (valorCampo.length() != posFim - posIni) {
                        int cont = valorCampo.length();
                        while (cont < posFim - posIni) {
                            valorCampo += caracterPreenchimento;
                            cont++;
                        }
                    }
                } else {
                    valorCampo = valorCampo.substring(0, posFim - posIni);
                }
                linhaDados += valorCampo;
            } else {
                String valor = "";
                if (valorCampo.length() <= posFim - posIni) {
                    if (valorCampo.length() != posFim - posIni) {
                        int contPreencheresquerda = posFim - posIni - valorCampo.length();
                        int i = 1;
                        while (i <= contPreencheresquerda) {
                            valor += caracterPreenchimento;
                            i++;
                        }
                    }
                    valor += valorCampo;
                } else {
                    valor = valorCampo.substring(0, posFim - posIni);
                }
                linhaDados += valor;
            }
            linha += linhaDados;
            return linha;
        } catch (Exception e) {
            throw new Exception("Erro na inclusão da linha.");
        }
    }

    public static String encriptar(String senha) throws UnsupportedEncodingException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // "MD5"

            md.update(senha.getBytes());
            return converterParaHexa(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String criptografar(String texto, String algoritmo) throws UnsupportedEncodingException {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo); // "MD5", "SHA-256", "SHA-1"

            md.update(texto.getBytes());
            return converterParaHexa(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String converterParaHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    public static Hashtable efetuarLeituraCheque(String leitura) {
//        String leitura = "<39905464<0869278325>805463316785:";
//        N° 927832
//        AG. 0546
//        C/C. 33167 8
//        BANCO HSBC 399

//        <10422629<0869000245>400100242601:
//        N° 900024
//        AG.2262
//        C/C 01002426 0
//        CAIXA ECONOMICA FEDERAL 104
        Hashtable lista = new Hashtable();
        String numeroBanco = leitura.substring(1, 4);
        lista.put("numeroBanco", numeroBanco);
        String agencia = leitura.substring(4, 8);
        lista.put("agencia", agencia);
        String conta = leitura.substring(13, 19);
        lista.put("numeroCheque", conta);
        String contaCorrente = leitura.substring(26, 32);
        lista.put("contaCorrente", contaCorrente);
        return lista;
    }

    public static List consultarImpressorasNoSO() {
        try {
            List lista = new ArrayList(0);
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            for (int i = 0; i < services.length; i++) {
                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                lista.add(((PrinterName) attr).getValue().toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            return null;
        }
    }


    public static String imprimirEtiqueta(String impressoraSelecionada, Integer qtdeEtiquetas, String texto) {
        try {
            PrintService ps = procurarImpressora(impressoraSelecionada);
            if (ps != null) {
                DocPrintJob job = ps.createPrintJob();
                byte[] by = texto.getBytes();
                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                Doc doc = new SimpleDoc(by, flavor, null);
                job.print(doc, null);
                return "ok";
            } else {
                return "Impressora " + impressoraSelecionada + " não encontrada.";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static PrintService procurarImpressora(String impressoraSelecionada) {
        try {
            PrintService ps = null;
            String nomeImpressoraSelecionada = impressoraSelecionada.toUpperCase();
            String nomeImpressoraEncontrada = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            for (int i = 0; i
                    < services.length; i++) {
                ps = services[i];
                PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                nomeImpressoraEncontrada = ((PrinterName) attr).getValue().toUpperCase();
                if (nomeImpressoraEncontrada.equals(nomeImpressoraSelecionada)) {
                    break;
                }
                ps = null;
            }
            return ps;
        } catch (Exception e) {
            return null;
        }
    }

    public static Document carregarArquivo(String caminho) throws Exception {
        File arquivo = new File(caminho);
        if (arquivo.exists()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(new FileInputStream(arquivo));
            return doc;
        } else {
            throw new Exception("Caminho do arquivo XML não encontrado. " + caminho);
        }
    }

    public static Document transformarStringDocument(String texto) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder().parse(new ByteArrayInputStream(texto.getBytes()));
    }

    public static String obterDiaSemana(Date data) {
        Locale local = new Locale("pt", "BR");
        DateFormat dataFormat = DateFormat.getDateInstance(DateFormat.ERA_FIELD, local);
        String dataExtenso = dataFormat.format(data);
        return dataExtenso.substring(0, dataExtenso.indexOf(","));
    }

    public static Integer obterDiaSemanaInteger(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        return cal.get(Calendar.DAY_OF_WEEK);
        //1=Domingo
        //2=Segunda
        //3=Terça
        //4=Quarta
        //5=Quinta
        //6=Sexta
        //7=Sábado
    }

    public static String substituirCaracteresAcentuados(String texto) {
        texto = substituirCaracterAcentuado(texto, "á", "a");
        texto = substituirCaracterAcentuado(texto, "à", "a");
        texto = substituirCaracterAcentuado(texto, "ã", "a");
        texto = substituirCaracterAcentuado(texto, "â", "a");
        texto = substituirCaracterAcentuado(texto, "Á", "A");
        texto = substituirCaracterAcentuado(texto, "À", "A");
        texto = substituirCaracterAcentuado(texto, "Ã", "A");
        texto = substituirCaracterAcentuado(texto, "Â", "A");
        texto = substituirCaracterAcentuado(texto, "é", "e");
        texto = substituirCaracterAcentuado(texto, "ê", "e");
        texto = substituirCaracterAcentuado(texto, "É", "E");
        texto = substituirCaracterAcentuado(texto, "Ê", "E");
        texto = substituirCaracterAcentuado(texto, "í", "i");
        texto = substituirCaracterAcentuado(texto, "Í", "I");
        texto = substituirCaracterAcentuado(texto, "ó", "o");
        texto = substituirCaracterAcentuado(texto, "õ", "o");
        texto = substituirCaracterAcentuado(texto, "ô", "o");
        texto = substituirCaracterAcentuado(texto, "Ó", "O");
        texto = substituirCaracterAcentuado(texto, "Õ", "O");
        texto = substituirCaracterAcentuado(texto, "Ô", "O");
        texto = substituirCaracterAcentuado(texto, "ú", "u");
        texto = substituirCaracterAcentuado(texto, "Ú", "U");
        texto = substituirCaracterAcentuado(texto, "ñ", "n");
        texto = substituirCaracterAcentuado(texto, "Ñ", "N");
        texto = substituirCaracterAcentuado(texto, "°", ".");
        texto = substituirCaracterAcentuado(texto, "º", ".");
        texto = substituirCaracterAcentuado(texto, "ª", ".");
        texto = substituirCaracterAcentuado(texto, "ç", "c");
        texto = substituirCaracterAcentuado(texto, "Ç", "C");
        texto = substituirCaracterAcentuado(texto, "~", "");
        texto = substituirCaracterAcentuado(texto, "´", "");
        texto = substituirCaracterAcentuado(texto, "`", "");
        texto = substituirCaracterAcentuado(texto, "^", "");

        return texto;
    }


    private static String substituirCaracterAcentuado(String texto, String caracterAcentuado, String caractereSemAcentuacao) {
        if (texto.contains(caracterAcentuado)) {
            texto = texto.replace(caracterAcentuado, caractereSemAcentuacao);
        }
        return texto;
    }

    public static String getPreencherComZeroEsquerda(String codigo, int tamanhoCodigoBarra) {
        int tamanhoCodigo = codigo.length();
        String codigoBarra = "";

        int diferenca = tamanhoCodigoBarra - tamanhoCodigo;
        while (diferenca > 0) {
            codigoBarra = codigoBarra + "0";
            diferenca--;
        }

        return codigoBarra + codigo;
    }

    public static String getPreencherComZeroDireita(String codigo, int tamanhoCodigoBarra) {
        int tamanhoCodigo = codigo.length();
        String codigoBarra = "";

        int diferenca = tamanhoCodigoBarra - tamanhoCodigo;
        while (diferenca > 0) {
            codigoBarra = codigoBarra + "0";
            diferenca--;
        }

        return codigo + codigoBarra;
    }

    public static String getPreencherComEspacoEsquerda(String codigo, int tamanho) {
        int tamanhoCodigo = codigo.length();
        String txt = "";

        int diferenca = tamanho - tamanhoCodigo;
        while (diferenca > 0) {
            txt = txt + " ";
            diferenca--;
        }

        return codigo + txt;
    }

    public static void zipar(String endEntrada, String endSaida) throws FileNotFoundException, IOException {
        String dirInterno = "";
        File file = new File(endEntrada);
        //Verifica se o arquivo ou diretório existe
        if (!file.exists()) {
            throw new FileNotFoundException("Arquivo ou Diretório não existe");
        }
        ZipOutputStream zipDestino = new ZipOutputStream(new FileOutputStream(endSaida));
        //se é um arquivo a ser zipado
        //zipa e retorna
        if (file.isFile()) {
            ziparFile(file, dirInterno, zipDestino);
        } //senão lista o que tem no diretório e zipa
        else {
            dirInterno = file.getName();
            //Verifica se é diretório ou
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                ziparFile(files[i], dirInterno, zipDestino);
            }
        }
        zipDestino.close();
    }

    private static void ziparFile(File file, String dirInterno, ZipOutputStream zipDestino) throws IOException {
        final int TAM_BUFFER = 4096;
        byte data[] = new byte[TAM_BUFFER];
        //Verifica se a file é um diretório, então faz a recursão
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                ziparFile(files[i], dirInterno + File.separator + file.getName(), zipDestino);
            }
            return;
        }
        FileInputStream fi = new FileInputStream(file.getAbsolutePath());
        ZipEntry entry = new ZipEntry(dirInterno + File.separator + file.getName());
        zipDestino.putNextEntry(entry);
        int count;
        while ((count = fi.read(data)) > 0) {
            zipDestino.write(data, 0, count);
        }
        zipDestino.closeEntry();
        fi.close();
    }

    public static void deletarDiretorio(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                deletarDiretorio(new File(dir, children[i]));
            }
        }
        dir.delete();
    }

    public static JRDataSource getJrDataSource(List lista) {
        JRDataSource jr = new JRBeanArrayDataSource(lista.toArray());
        return jr;
    }

    public static String removerZerosAEsquerda(String numero) {
        numero = removerMascara(numero);
        numero = numero.replace(" ", "");
        Integer novo = Integer.parseInt(numero);
        return novo.toString();
    }

    public static String removerAcentuacao(String prm) {
        String nova = "";
        for (int i = 0; i < prm.length(); i++) {
            if (prm.charAt(i) == CaracteresEspeciais.A_AGUDO || prm.charAt(i) == CaracteresEspeciais.A_CIRCUNFLEXO
                    || prm.charAt(i) == CaracteresEspeciais.A_CRASE || prm.charAt(i) == CaracteresEspeciais.A_TIO) {
                nova += "a";
            } else if (prm.charAt(i) == CaracteresEspeciais.A_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.A_CIRCUNFLEXOMAIUSCULO
                    || prm.charAt(i) == CaracteresEspeciais.A_CRASEMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.A_TIOMAIUSCULO) {
                nova += "A";
            } else if (prm.charAt(i) == CaracteresEspeciais.E_AGUDO || prm.charAt(i) == CaracteresEspeciais.E_CIRCUNFLEXO) {
                nova += "e";
            } else if (prm.charAt(i) == CaracteresEspeciais.E_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.E_CIRCUNFLEXOMAIUSCULO) {
                nova += "E";
            } else if (prm.charAt(i) == CaracteresEspeciais.I_AGUDO) {
                nova += "i";
            } else if (prm.charAt(i) == CaracteresEspeciais.I_AGUDOMAIUSCULO) {
                nova += "I";
            } else if (prm.charAt(i) == CaracteresEspeciais.O_AGUDO || prm.charAt(i) == CaracteresEspeciais.O_TIO) {
                nova += "o";
            } else if (prm.charAt(i) == CaracteresEspeciais.O_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.O_TIOMAISCULO) {
                nova += "O";
            } else if (prm.charAt(i) == CaracteresEspeciais.O_CIRCUNFLEXO || prm.charAt(i) == CaracteresEspeciais.O_CIRCUNFLEXOMAISCULO) {
                nova += "O";
            } else if (prm.charAt(i) == CaracteresEspeciais.U_AGUDO || prm.charAt(i) == CaracteresEspeciais.U_TREMA) {
                nova += "u";
            } else if (prm.charAt(i) == CaracteresEspeciais.U_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.U_TREMAMAISCULO) {
                nova += "U";
            } else if (prm.charAt(i) == CaracteresEspeciais.C_CEDILHA) {
                nova += "c";
                //} else if (Character.isSpaceChar(prm.charAt(i))){
                //    nova += "_";
            } else if (prm.charAt(i) == CaracteresEspeciais.C_CEDILHAMAISCULO) {
                nova += "C";
            } else if (prm.charAt(i) == CaracteresEspeciais._OS) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais._AS) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.E_COMERCIAL) {
                nova += "E";
            } else if (prm.charAt(i) == CaracteresEspeciais.PORCENTAGEM) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.ASTERISCO) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.CERQUILHA) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.ARROBA) {
                nova += " ";
            } else {
                nova += prm.charAt(i);
            }
        }
        return (nova);
    }

    public static String somenteNumeros(String prm) {
        String num = "0123456789";
        String nova = "";
        for (int i = 0; i < prm.length(); i++) {
            String str = "" + prm.charAt(i);
            if (num.contains(str)) {
                nova += str;
            }
        }
        return (nova);
    }

    public static int getModulo11(String num, int base, int r) {
        /**
         * Entrada: $num: string numérica para a qual se deseja calcularo digito
         * verificador; $base: valor maximo de multiplicacao [2-$base] $r:
         * quando especificado um (1) devolve somente o resto. Zero (0) devolve
         * o DV
         *
         * Saída: Retorna o Digito verificador.
         *
         * Exemplo de uso: getMod11(nossoNumero, 9,1)
         */
        int soma = 0;
        int fator = 2;
        String[] numeros, parcial;
        numeros = new String[num.length() + 1];
        parcial = new String[num.length() + 1];

        /* Separacao dos numeros */
        for (int i = num.length(); i > 0; i--) {
            // pega cada numero isoladamente
            numeros[i] = num.substring(i - 1, i);
            // Efetua multiplicacao do numero pelo falor
            parcial[i] = String.valueOf(Integer.parseInt(numeros[i]) * fator);
            // Soma dos digitos
            soma += Integer.parseInt(parcial[i]);
            if (fator == base) {
                // restaura fator de multiplicacao para 2
                fator = 1;
            }
            fator++;

        }

        /* Calculo do modulo 11 */
        if (r == 0) {
            soma *= 10;
            int digito = soma % 11;
            if (digito == 10) {
                digito = 0;
            }
            return digito;
        } else {
            int resto = soma % 11;
            return resto;
        }
    }

    public static int getModulo10(String num) {
        int soma = 0;
        int fator = 2;
        String digitosMultiplicacao = "";
        for (int i = num.length(); i > 0; i--) {
            digitosMultiplicacao = (Integer.parseInt(num.substring(i - 1, i)) * fator) + digitosMultiplicacao;
            if (fator == 2) {
                fator = 1;
            } else {
                fator = 2;
            }

        }
        for (int i = digitosMultiplicacao.length(); i > 0; i--) {
            soma += Integer.parseInt(digitosMultiplicacao.substring(i - 1, i));
        }
        int dac = 10 - (soma % 10);
        if (dac >= 10) {
            return 0;
        } else {
            return dac;
        }
    }

    public static String obterIdRandomico(Integer tamanho) {
        String letras = "0123456789ABCDEFGHIJKLMNOPQRSTUVXZKWY";
        Random random = new Random();
        String armazenaChaves = "";
        int index = -1;
        for (int i = 0; i < tamanho; i++) {
            index = random.nextInt(letras.length());
            armazenaChaves += letras.substring(index, index + 1);
        }
        return armazenaChaves;
    }

    public static String obterNumeroRastreabilidade(Date data, Integer numeroLote, String sieSif) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        SimpleDateFormat formatNowDay = new SimpleDateFormat("dd");
        SimpleDateFormat formatNowMonth = new SimpleDateFormat("MM");

        //return (cal.get(Calendar.YEAR) + "" + formatNowMonth.format(data) + "" + formatNowDay.format(data) + "." + numeroLote + "." + sieSif);
        return (sieSif + "." + formatNowDay.format(data) + "" + formatNowMonth.format(data) + "" + cal.get(Calendar.YEAR) + "." + numeroLote);

    }

    public static String obterNomeEstado(String uf) {
        if (uf.equals("AC")) {
            return "Acre";
        }
        if (uf.equals("AL")) {
            return "Alagoas";
        }
        if (uf.equals("AP")) {
            return "Amapá";
        }
        if (uf.equals("AM")) {
            return "Amazonas";
        }
        if (uf.equals("BA")) {
            return "Bahia";
        }
        if (uf.equals("CE")) {
            return "Ceará";
        }
        if (uf.equals("DF")) {
            return "Distrito Federal";
        }
        if (uf.equals("ES")) {
            return "Espírito Santo";
        }
        if (uf.equals("GO")) {
            return "Goiás";
        }
        if (uf.equals("MA")) {
            return "Maranhão";
        }
        if (uf.equals("MT")) {
            return "Mato Grosso";
        }
        if (uf.equals("MS")) {
            return "Mato Grosso do Sul";
        }
        if (uf.equals("MG")) {
            return "Minas Gerais";
        }
        if (uf.equals("PA")) {
            return "Pará";
        }
        if (uf.equals("PB")) {
            return "Paraíba";
        }
        if (uf.equals("PR")) {
            return "Paraná";
        }
        if (uf.equals("PE")) {
            return "Pernambuco";
        }
        if (uf.equals("PI")) {
            return "Piauí";
        }
        if (uf.equals("RJ")) {
            return "Rio de Janeiro";
        }
        if (uf.equals("RN")) {
            return "Rio Grande do Norte";
        }
        if (uf.equals("RS")) {
            return "Rio Grande do Sul";
        }
        if (uf.equals("RO")) {
            return "Rondônia";
        }
        if (uf.equals("RR")) {
            return "Roraima";
        }
        if (uf.equals("SC")) {
            return "Santa Catarina";
        }
        if (uf.equals("SP")) {
            return "São Paulo";
        }
        if (uf.equals("SE")) {
            return "Sergipe";
        }
        if (uf.equals("TO")) {
            return "Tocantins";
        }
        return "";
    }

    public static Date zerarHoras(Date data) throws Exception {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(format.format(data));
    }

    public static byte[] lerArquivo(File file) throws Exception {
        int len = (int) file.length();
        byte[] sendBuf = new byte[len];
        FileInputStream inFile = null;
        inFile = new FileInputStream(file);
        inFile.read(sendBuf, 0, len);
        return sendBuf;
    }

    public static void gravarArquivo(String caminho, byte[] bytesArray) throws Exception {
        File sourceFile = new File(caminho);
        FileOutputStream file = new FileOutputStream(sourceFile);
        BufferedOutputStream output = new BufferedOutputStream(file);
        output.flush();
        output.write(bytesArray, 0, bytesArray.length);
        output.flush();
        output.close();

    }

    public static String qrCodeText(String chave, Double valor, String mensagemPix, String recebedor, String cidade, String nrDocumento) {
        Boolean erro = false;
        if (chave.isEmpty()) {
            erro = true;
        }
        if (recebedor.isEmpty()) {
            erro = true;
        }
        if (cidade.isEmpty()) {
            erro = true;
        }
        if (nrDocumento.isEmpty()) {
            erro = true;
        }
        if (!erro) {
            try {
                StringBuilder qrCode = new StringBuilder("");
                qrCode.append("000201");
                StringBuilder qrCode2 = new StringBuilder("");
                qrCode2.append("0014br.gov.bcb.pix");
                qrCode2.append("01").append(Uteis.getPreencherComZeroEsquerda("" + chave.length(), 2)).append(chave);
                if (!mensagemPix.isEmpty()) {
                    qrCode2.append("02").append(Uteis.getPreencherComZeroEsquerda("" + mensagemPix.length(), 2)).append(mensagemPix);
                }
                qrCode.append("26").append(Uteis.getPreencherComZeroEsquerda("" + qrCode2.toString().length(), 2));
                qrCode.append(qrCode2.toString());
                qrCode.append("52040000");
                qrCode.append("5303986");
                qrCode.append("54").append(Uteis.getPreencherComZeroEsquerda("" + valor.toString().length(), 2)).append(valor.toString());
                qrCode.append("5802BR");
                qrCode.append("59").append(Uteis.getPreencherComZeroEsquerda("" + recebedor.length(), 2)).append(recebedor);
                qrCode.append("60").append(Uteis.getPreencherComZeroEsquerda("" + cidade.length(), 2)).append(cidade);
                StringBuilder qrCode3 = new StringBuilder("");
                if (!nrDocumento.isEmpty()) {
                    qrCode3.append("05").append(Uteis.getPreencherComZeroEsquerda("" + nrDocumento.length(), 2)).append(nrDocumento);
                    qrCode.append("62").append(Uteis.getPreencherComZeroEsquerda("" + qrCode3.toString().length(), 2));
                    qrCode.append(qrCode3.toString());
                }
                qrCode.append("6304");
                qrCode.append(Uteis.calcularCRC16CCITT(qrCode.toString()));
                return qrCode.toString();
            } catch (Exception ex) {
                erro = true;
            }
        }
        return "";
    }

    public static String qrCode(OutputStream out, String chave, Double valor, String mensagemPix, String recebedor, String cidade, String nrDocumento, Integer width, Integer height) {
        Boolean erro = false;
        if (chave.isEmpty()) {
            erro = true;
        }
        if (recebedor.isEmpty()) {
            erro = true;
        }
        if (cidade.isEmpty()) {
            erro = true;
        }
        if (nrDocumento.isEmpty()) {
            erro = true;
        }
        if (!erro) {
            try {
                StringBuilder qrCode = new StringBuilder(qrCodeText(chave, valor, mensagemPix, recebedor, cidade, nrDocumento));

                Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix byteMatrix = qrCodeWriter.encode(qrCode.toString(), BarcodeFormat.QR_CODE, width, height, hintMap);
                int CrunchifyWidth = byteMatrix.getWidth();
                BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
                image.createGraphics();
                Graphics2D graphics = (Graphics2D) image.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
                if (!qrCode.toString().isEmpty()) {
                    graphics.setColor(Color.BLACK);
                    for (int i = 0; i < CrunchifyWidth; i++) {
                        for (int j = 0; j < CrunchifyWidth; j++) {
                            if (byteMatrix.get(i, j)) {
                                graphics.fillRect(i, j, 1, 1);
                            }
                        }
                    }
                }
                ImageIO.write(image, "jpeg", out);
                return qrCode.toString();
            } catch (Exception ex) {
                erro = true;
            }
        }
        if (erro) {
            try {
                String caminho = Uteis.getCaminhoWebImagens() + File.separator + "imagens" + File.separator + "erroQrCode.png";
                File imagem = new File(caminho);
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                byte buffer[] = new byte[4096];
                int bytesRead = 0;
                FileInputStream fi = new FileInputStream(imagem.getAbsolutePath());
                while ((bytesRead = fi.read(buffer)) != -1) {
                    arrayOutputStream.write(buffer, 0, bytesRead);
                }
                byte[] a = (arrayOutputStream.toByteArray());
                arrayOutputStream.close();
                fi.close();
                BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(new ByteArrayInputStream(a)));
                ImageIO.write(bufferedImage, "png", out);
            } catch (Exception ex1) {
            }
        }
        return "";
    }

    public static String calcularCRC16CCITT(String qrCode) {
        try {
            int crc = 0xFFFF; // initial value
            int polynomial = 0x1021; // 0001 0000 0010 0001  (0, 5, 12)
            byte[] bytes = qrCode.getBytes("ASCII");
            for (byte b : bytes) {
                for (int i = 0; i < 8; i++) {
                    boolean bit = (b >> (7 - i) & 1) == 1;
                    boolean c15 = (crc >> 15 & 1) == 1;
                    crc <<= 1;
                    if (c15 ^ bit) {
                        crc ^= polynomial;
                    }
                }
            }
            crc &= 0xffff;
            return Integer.toHexString(crc).toUpperCase();
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
    }

    public static Boolean getDataCompararSeMesmoDia(Date data1, Date data2) {
        Integer dia1 = Uteis.getDiaMesData(data1);
        Integer dia2 = Uteis.getDiaMesData(data2);
        Integer mes1 = Uteis.getMesData(data1);
        Integer mes2 = Uteis.getMesData(data2);
        Integer ano1 = Uteis.getAnoData(data1);
        Integer ano2 = Uteis.getAnoData(data2);

        if (ano1.equals(ano2)) {
            if (mes1.equals(mes2)) {
                if (dia1.equals(dia2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String completaComZeros(String str, int tamanho) {
        if (str.length() < tamanho) {
            String zeros = "";
            for (int i = 0; i < tamanho - str.length(); i++) {
                zeros += "0";
            }
            return zeros + str;
        }

        return str;
    }

    public static String getDiferencaEntreDatasFormatado(Date dataIni, Date dataFim) {
        if (dataIni == null) {
            return "ERRO DI=NULL";
        }
        if (dataFim == null) {
            return "ERRO DF=NULL";
        }
        if (dataIni.getTime() > dataFim.getTime()) {
            return "ERRO DI>DF";
        }

        Long segundos = (dataFim.getTime() - (dataIni.getTime())) / 1000;
        if (segundos.intValue() < 60) {
            return "00:00:" + Uteis.completaComZeros("" + segundos, 2);
        }
        Long minutos = segundos / 60;
        if (minutos.intValue() < 60) {
            return "00:" + Uteis.completaComZeros("" + minutos, 2) + ":" + Uteis.completaComZeros("" + segundos % 60, 2);
        }
        Long horas = minutos / 60;
        if (horas.intValue() < 24) {
            return Uteis.completaComZeros("" + horas, 2) + ":" + Uteis.completaComZeros("" + minutos % 60, 2) + ":" + Uteis.completaComZeros("" + segundos % 60, 2);
        }
        Long dias = horas / 24;
        horas = horas - dias * 24;
        return dias + (dias.equals(1L) ? " dia " : " dias ") + " " + Uteis.completaComZeros("" + horas, 2) + ":" + Uteis.completaComZeros("" + minutos % 60, 2) + ":" + Uteis.completaComZeros("" + segundos % 60, 2);
    }

    public static Boolean validarDiaUtil(Date data) {
        Integer dia = 0;
        Integer mes = 0;
        Boolean diaUtil = true;

        dia = Uteis.getDiaMesData(data);
        mes = Uteis.getMesData(data);
        Date pascoa = calcularPascoa(data);
        diaUtil = true;

        if (mes.equals(1) && dia.equals(1)) {
            //ANO NOVO
            diaUtil = false;
        } else if (mes.equals(4) && dia.equals(21)) {
            //TIRADENTES
            diaUtil = false;
        } else if (mes.equals(Uteis.getMesData(Uteis.decrementarData(pascoa, 48))) && dia.equals(Uteis.getDiaMesData(Uteis.decrementarData(pascoa, 48)))) {
            //CARNAVAL - SEGUNDA-FEIRA
            diaUtil = false;
        } else if (mes.equals(Uteis.getMesData(Uteis.decrementarData(pascoa, 47))) && dia.equals(Uteis.getDiaMesData(Uteis.decrementarData(pascoa, 47)))) {
            //CARNAVAL - TERÇA-FEIRA
            diaUtil = false;
        } else if (mes.equals(Uteis.getMesData(Uteis.decrementarData(pascoa, 2))) && dia.equals(Uteis.getDiaMesData(Uteis.decrementarData(pascoa, 2)))) {
            //SEXTA-FEIRA SANTA
            diaUtil = false;
        } else if (mes.equals(Uteis.getMesData(pascoa)) && dia.equals(Uteis.getDiaMesData(pascoa))) {
            //PASCOA
            diaUtil = false;
        } else if (mes.equals(5) && dia.equals(1)) {
            //DIA DO TRABALHADOR
            diaUtil = false;
        } else if (mes.equals(Uteis.getMesData(Uteis.incrementarData(pascoa, 60))) && dia.equals(Uteis.getDiaMesData(Uteis.incrementarData(pascoa, 60)))) {
            //CORPUS CHRISTI
            diaUtil = false;
        } else if (mes.equals(9) && dia.equals(7)) {
            //INDEPENDENCIA
            diaUtil = false;
        } else if (mes.equals(10) && dia.equals(12)) {
            //NOSSA SENHORA APARECIDA/DIA DAS CRIANÇAS
            diaUtil = false;
        } else if (mes.equals(11) && dia.equals(2)) {
            //FINADOS
            diaUtil = false;
        } else if (mes.equals(11) && dia.equals(15)) {
            //FINADOS
            diaUtil = false;
        } else if (mes.equals(12) && dia.equals(25)) {
            //NATAL
            diaUtil = false;
        } else if (Uteis.obterDiaSemanaInteger(data).equals(7)) {
            //SÁBADO
            diaUtil = false;
        } else if (Uteis.obterDiaSemanaInteger(data).equals(1)) {
            //DOMINGO
            diaUtil = false;
        }
        return diaUtil;
    }

    private static Date calcularPascoa(Date data){
        Integer anoV = Uteis.getAnoData(data);
        int a = anoV % 19;
        int b = anoV / 100;
        int c = anoV % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int mes = (h + l - 7 * m + 114) / 31;
        int dia = ((h + l - 7 * m + 114) % 31) + 1;
        return Uteis.getDataCompleta(dia, mes, anoV, 0, 0, 0);
    }


    public static String removerEspaco(String texto) {
        if (texto.contains(" ")) {
            texto = texto.replace(" ", "");
        }
        return texto;
    }

    public static String retirarAcentuacao(String prm) {
        String nova = "";
        for (int i = 0; i < prm.length(); i++) {
            if (prm.charAt(i) == CaracteresEspeciais.A_AGUDO || prm.charAt(i) == CaracteresEspeciais.A_CIRCUNFLEXO
                    || prm.charAt(i) == CaracteresEspeciais.A_CRASE || prm.charAt(i) == CaracteresEspeciais.A_TIO) {
                nova += "a";
            } else if (prm.charAt(i) == CaracteresEspeciais.A_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.A_CIRCUNFLEXOMAIUSCULO
                    || prm.charAt(i) == CaracteresEspeciais.A_CRASEMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.A_TIOMAIUSCULO) {
                nova += "A";
            } else if (prm.charAt(i) == CaracteresEspeciais.E_AGUDO || prm.charAt(i) == CaracteresEspeciais.E_CIRCUNFLEXO) {
                nova += "e";
            } else if (prm.charAt(i) == CaracteresEspeciais.E_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.E_CIRCUNFLEXOMAIUSCULO) {
                nova += "E";
            } else if (prm.charAt(i) == CaracteresEspeciais.I_AGUDO) {
                nova += "i";
            } else if (prm.charAt(i) == CaracteresEspeciais.I_AGUDOMAIUSCULO) {
                nova += "I";
            } else if (prm.charAt(i) == CaracteresEspeciais.O_AGUDO || prm.charAt(i) == CaracteresEspeciais.O_TIO) {
                nova += "o";
            } else if (prm.charAt(i) == CaracteresEspeciais.O_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.O_TIOMAISCULO) {
                nova += "O";
            } else if (prm.charAt(i) == CaracteresEspeciais.O_CIRCUNFLEXO || prm.charAt(i) == CaracteresEspeciais.O_CIRCUNFLEXOMAISCULO) {
                nova += "O";
            } else if (prm.charAt(i) == CaracteresEspeciais.U_AGUDO || prm.charAt(i) == CaracteresEspeciais.U_TREMA) {
                nova += "u";
            } else if (prm.charAt(i) == CaracteresEspeciais.U_AGUDOMAIUSCULO || prm.charAt(i) == CaracteresEspeciais.U_TREMAMAISCULO) {
                nova += "U";
            } else if (prm.charAt(i) == CaracteresEspeciais.C_CEDILHA) {
                nova += "c";
                //} else if (Character.isSpaceChar(prm.charAt(i))){
                //    nova += "_";
            } else if (prm.charAt(i) == CaracteresEspeciais.C_CEDILHAMAISCULO) {
                nova += "C";
            } else if (prm.charAt(i) == CaracteresEspeciais._OS) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais._AS) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.E_COMERCIAL) {
                nova += "E";
//            } else if (prm.charAt(i) == CaracteresEspeciais.PORCENTAGEM) {
//                nova += " ";
//            } else if (prm.charAt(i) == CaracteresEspeciais.ASTERISCO) {
//                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.CERQUILHA) {
                nova += " ";
            } else if (prm.charAt(i) == CaracteresEspeciais.ARROBA) {
                nova += " ";
            } else {
                nova += prm.charAt(i);
            }
        }
        return (nova);
    }


    public static String retirarCaracteresEspeciais(String texto) {
        String nova = "";
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) != '.' && texto.charAt(i) != '-' && texto.charAt(i) != '&' && texto.charAt(i) != '°') {
                nova += texto.charAt(i);
            } else {
                nova += " ";
            }
        }
        texto = nova;
        nova = "";
        int sp = 0;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == ' ') {
                sp++;
            } else {
                sp = 0;
            }
            if (sp <= 1) {
                nova += texto.charAt(i);
            }
        }
        nova = nova.replaceAll("\n", "");
        return nova;
    }
}
