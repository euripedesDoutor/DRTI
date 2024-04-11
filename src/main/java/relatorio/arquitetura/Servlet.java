package relatorio.arquitetura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Euripedes Doutor
 */
public class Servlet extends HttpServlet {

    private List lista;
    private HashMap map;

    @SuppressWarnings("SizeReplaceableByIsEmpty")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        removerPorTempoExpirado();
        try {
            String idApplet = request.getParameter("idApplet");
            List listaID = (List) getMap().get(idApplet);
            if (listaID != null && !listaID.isEmpty()) {
                ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
                output.writeUnshared(listaID);
                output.flush();
                getMap().remove(idApplet);
            } else {
                ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
                output.writeUnshared(new ArrayList(0));
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removerPorTempoExpirado() {
        List<String> listaExclusao = new ArrayList<String>();
        Set set = getMap().entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry entrada = (Map.Entry) i.next();
            if ((obterIDImpressaoMatricial() - new Long(entrada.getKey().toString()) > (1000 * 60 * 10))) {
                listaExclusao.add(entrada.getKey().toString());
            }
        }
        for (String itemLista : listaExclusao) {
            getMap().remove(itemLista);
        }
    }

    private Long obterIDImpressaoMatricial() {
        Date data = new Date();
        return data.getTime();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            //ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
            String id = request.getParameter("id");
            String ip = request.getParameter("ip");
            String leitura = request.getParameter("leitura");
            String acao = request.getParameter("acao");

//            if (acao.equals("leitura")) {
//                response.sendRedirect(ip + "?acao=ok&p1=" + leitura + "&p2=" + getId());
//            }
//            if (acao.equals("teste")) {
//                response.sendRedirect(ip + "?acao=teste&p1=" + Uteis.getDataAtualAplicandoFormatacao("yyyy-MMM-dd HH:mm:ss") + "&p2=" + getId());
//            }
//            output.writeChars("");
//            output.flush();

//            String HTTP_VERSION = "HTTP/1.1";
//
//            String host = ip;
//            int port = 80;
//            Socket socket = null;
//            try {
//                // Abre a conexão
//                socket = new Socket(host, port);
//                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        socket.getInputStream()));
//
//                // Envia a requisição
//                out.println("GET /" + "?acao=ok&p1=" + leitura + "&p2=" + getId() + " " + HTTP_VERSION);
//                out.println("Host: " + host);
//                out.println("Connection: Close");
//                out.println();
//
//            } finally {
//                if (socket != null) {
//                    socket.close();
//                }
//            }

            Socket s = new Socket(InetAddress.getByAddress(id, ip.getBytes()), 80);
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.print("GET /" + "?acao=ok&p1=" + leitura + "&p2=" + getId() + " HTTP/1.1");
            pw.print("Host: " + ip);
            pw.println("Connection: Close");
            pw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String t;
            while ((t = br.readLine()) != null) {
                System.out.println(t);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getId() {
        StringBuilder sb = new StringBuilder(10);
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public List getLista() {
        if (lista == null) {
            lista = new ArrayList(0);
        }
        return lista;
    }

    public void setLista(List lista) {
        this.lista = lista;
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
}
