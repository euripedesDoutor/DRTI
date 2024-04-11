/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle.arquitetura;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Euripedes Doutor
 */
public class DownloadArquivo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String arquivo = (String) request.getAttribute("caminhoArquivoDownload");
        String nomeArquivo = (String) request.getAttribute("nomeArquivoDownload");
        File file = new File(arquivo);
        if (file.exists()) {
            int BUFSIZE = 1024;
            int length = 0;
            ServletOutputStream op = response.getOutputStream();
            ServletContext context = getServletConfig().getServletContext();
            String mimetype = context.getMimeType(nomeArquivo);
            response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");
            byte[] bbuf = new byte[BUFSIZE];
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            while ((in != null) && ((length = in.read(bbuf)) != -1)) {
                op.write(bbuf, 0, length);
            }
            in.close();
            op.flush();
            op.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
