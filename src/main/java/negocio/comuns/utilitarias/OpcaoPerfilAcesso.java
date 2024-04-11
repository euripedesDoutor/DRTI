/*
 * OpcaoPerfilAcesso.java
 *
 * Created on 6 de Setembro de 2007, 16:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package negocio.comuns.utilitarias;

/**
 *
 * @author Administrador
 */
public class OpcaoPerfilAcesso {
    public static int TP_ENTIDADE = 1;
    public static int TP_FUNCIONALIDADE = 2;
    public static int TP_MODULO = 3;
    
    private String nome;
    private String titulo;
    private int tipo;
    
    /** Creates a new instance of OpcaoPerfilAcesso */
    public OpcaoPerfilAcesso() {
        
    }

    public OpcaoPerfilAcesso(String nomePrm, String tituloPrm, int tipoPrm) {
        nome = nomePrm;
        titulo = tituloPrm;
        tipo = tipoPrm;
    }    
    
    private void inicializar() {
        setNome("");
        setTitulo("");
        setTipo(TP_ENTIDADE);
    }

    public String getNome() {
        if(nome == null){
            return "";
        }
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        if(titulo == null){
            return "";
        }
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
}
