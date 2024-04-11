package controle.arquitetura;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Euripedes Doutor
 */
public class DeletarArquivoPDFDanfe implements Runnable {

    private String pastaArquivosASeremDeletados;
    private String extensao;
    private Integer minutosParaProximaExecucao;

    public void run() {
        try {
            deletarArquivosDanfe();
        } catch (Exception e) {
            System.out.println("THREAD EMAIL ERRO: " + e.getMessage());
        }
    }

    public synchronized void deletarArquivosDanfe() throws InterruptedException {
        while (true) {
            String arq = ".+\\." + extensao;
            File pasta = new File(pastaArquivosASeremDeletados);
            File[] arquivos = pasta.listFiles();

            for (int i = 0; i < arquivos.length; i++) {
                if (arquivos[i].getName().matches(arq)) {
                    if ((new Date().getTime() - arquivos[i].lastModified()) > 1000 * 60 * minutosParaProximaExecucao) { // minutos após a criação, o arquivo PDF será deletado
                        System.out.println("Deletando arquivo " + arquivos[i].getName());
                        arquivos[i].delete();
                    }
                }
            }
            wait(1000 * 60 * minutosParaProximaExecucao);

        }
    }

    public String getExtensao() {
        if (extensao == null) {
            extensao = "";
        }
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getPastaArquivosASeremDeletados() {
        if (pastaArquivosASeremDeletados == null) {
            pastaArquivosASeremDeletados = "";
        }
        return pastaArquivosASeremDeletados;
    }

    public void setPastaArquivosASeremDeletados(String pastaArquivosASeremDeletados) {
        this.pastaArquivosASeremDeletados = pastaArquivosASeremDeletados;
    }

    public Integer getMinutosParaProximaExecucao() {
        if(minutosParaProximaExecucao == null){
            minutosParaProximaExecucao = 0;
        }
        return minutosParaProximaExecucao;
    }

    public void setMinutosParaProximaExecucao(Integer minutosParaProximaExecucao) {
        this.minutosParaProximaExecucao = minutosParaProximaExecucao;
    }

}
