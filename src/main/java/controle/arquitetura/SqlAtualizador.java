
package controle.arquitetura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Euripedes Doutor
 */
public class SqlAtualizador {

    private static List<SqlVO> listaSql;
    private Integer cont = 1;
    private String caminhoDiretorioWeb;
    private String arquivo;

    public static List<SqlVO> getListaSql() {
        if (listaSql == null) {
            listaSql = new ArrayList<SqlVO>();
        }
        return listaSql;
    }

    public SqlAtualizador() {
    }

    public void carregarArquivoSql() {
        getListaSql().clear();
        String caminho = caminhoDiretorioWeb + File.separator + "arquivos" + File.separator + arquivo;//
        File arquivo = new File(caminho);
        if (arquivo.exists()) {
            try {
                FileReader arquivoSql = new FileReader(caminho);

                BufferedReader leituraArquivo = new BufferedReader(arquivoSql);
                String linha = null;
                while ((linha = leituraArquivo.readLine()) != null) {
                    if (!linha.equals("") && !linha.equals(" ")) {
                        getListaSql().add(new SqlVO(cont++, linha));
                    }
                }

            } catch (Exception e) {
            }

        }

    }

    public void setCaminhoDiretorioWeb(String caminhoDiretorioWeb) {
        this.caminhoDiretorioWeb = caminhoDiretorioWeb;
    }

    public void setCont(Integer cont) {
        this.cont = cont;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
}
