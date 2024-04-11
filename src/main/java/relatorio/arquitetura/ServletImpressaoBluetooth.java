package relatorio.arquitetura;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Euripedes Doutor
 */
public class ServletImpressaoBluetooth extends HttpServlet {

    private List lista;
    private HashMap map;

    @SuppressWarnings("SizeReplaceableByIsEmpty")
    protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //removerPorTempoExpirado();
        try {
            String id = request.getParameter("id");
            String comando = request.getParameter("comando");
            if (comando != null) {
                if (comando.equals("listarID")) {
                    HashMap mapID = new HashMap();
                    Set keys = getMap().keySet();
                    for (Object key : keys) {
                        Integer keyString = (Integer) mapID.get(key);
                        if (keyString == null) {
                            mapID.put(key, 1);
                        } else {
                            mapID.remove(key);
                            mapID.put(key, keyString + 1);
                        }
                    }

                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>LISTA DE IDs</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>LISTA DE IDs</h1></br>");
                    if (mapID.isEmpty()) {
                        out.println("<h2>VAZIA</h2></br>");

                    }
                    Set keysID = mapID.keySet();
                    for (Object key : keysID) {
                        out.println("<h2>" + key + "</h2></br>");
                    }
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                }
                if (comando.equals("removerID")) {
                    String id2 = request.getParameter("id");
                    getMap().remove(id2);
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>REMOVER ID</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>LISTA DE IMPRESSAO REMOVIDA</h1></br>");
                    out.println("<h2>ID: " + id2 + "</h2></br>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();

                }
            } else {
                List<String> listaID = (List<String>) getMap().get(id);
                List lista2 = new ArrayList(0);
                ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
                if (listaID != null && !listaID.isEmpty()) {
                    for(String linha : listaID){
                        lista2.add(linha);
                    }
                    output.writeUnshared(lista2);
                    getMap().remove(id);
                } else {
                    output.writeUnshared(null);
                }
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

//    private void removerPorTempoExpirado() {
//        List<String> listaExclusao = new ArrayList<String>();
//        Set set = getMap().entrySet();
//        Iterator i = set.iterator();
//        while (i.hasNext()) {
//            Map.Entry entrada = (Map.Entry) i.next();
//            if ((obterIDImpressaoMatricial() - new Long(entrada.getKey().toString()) > (1000 * 60 * 10))) {
//                listaExclusao.add(entrada.getKey().toString());
//            }
//        }
//        for (String itemLista : listaExclusao) {
//            getMap().remove(itemLista);
//        }
//    }
//    private Long obterIDImpressaoMatricial() {
//        Date data = new Date();
//        return data.getTime();
//    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = (String) request.getAttribute("id");
            List listaID = (List) getMap().get(id);
            if (listaID != null && !listaID.isEmpty()) {
                getMap().remove(id);
            }
            getMap().put(id, (List) request.getAttribute("lista"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
