package controle.cadastro;

import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import negocio.comuns.cadastro.EmpresaVO;
import negocio.comuns.utilitarias.ControleConsulta;
import negocio.comuns.utilitarias.OpcaoPerfilAcesso;
import negocio.comuns.utilitarias.OpcoesPerfilAcesso;
import negocio.comuns.utilitarias.Uteis;
import negocio.facade.jdbc.cadastro.ConfiguracaoSistema;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import controle.arquitetura.LoginControle;
import controle.arquitetura.SuperControle;

@Controller("ConfiguracaoSistemaControle")
@Scope("session")
@Lazy
public class ConfiguracaoSistemaControle extends SuperControle {

    private ConfiguracaoSistemaVO configuracaoSistemaVO;
    private String entidade;
    private byte imagemBanner[];
    private byte imagemRelatorio[];
    private String emailTeste;

    public ConfiguracaoSistemaControle() throws Exception {
        obterUsuarioLogado();
        novo();
        setControleConsulta(new ControleConsulta());
        setMensagemID("msg_entre_prmconsulta");
    }

    public String novo() {
        try {
            setConfiguracaoSistemaVO(new ConfiguracaoSistemaVO());
            inicializarListasSelectItemTodosComboBox();
            setConfiguracaoSistemaVO(getFacadeFactory().getConfiguracaoSistemaFacade().consultarSeExisteConfiguracaoSistema(getCodigoEmpresaLogado()));
            inicializarEmpresaLogadoSistema();
            getConfiguracaoSistemaVO().registrarObjetoVOAntesDaAlteracao();
            substituirInstanciaConfiguracaoSistemaLoginControle();
            setMensagemID("msg_entre_dados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void inicializarEmpresaLogadoSistema() {
        try {
            configuracaoSistemaVO.setEmpresa(getEmpresaLogado());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String editar() {
        ConfiguracaoSistemaVO obj = (ConfiguracaoSistemaVO) context().getExternalContext().getRequestMap().get("configuracaoSistema");
        obj.setNovoObj(false);
        setConfiguracaoSistemaVO(obj);
        getConfiguracaoSistemaVO().registrarObjetoVOAntesDaAlteracao();
        inicializarListasSelectItemTodosComboBox();
        setMensagemID("msg_dados_editar");
        return "editar";
    }

    public String gravar() {
        try {
            if (configuracaoSistemaVO.isNovoObj().booleanValue()) {
                getFacadeFactory().getConfiguracaoSistemaFacade().incluir(configuracaoSistemaVO);
            } else {
                getFacadeFactory().getConfiguracaoSistemaFacade().alterar(configuracaoSistemaVO);
            }
            substituirInstanciaConfiguracaoSistemaLoginControle();
            getConfiguracaoSistemaVO().registrarObjetoVOAntesDaAlteracao();
            setMensagemID("msg_dados_gravados");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void substituirInstanciaConfiguracaoSistemaLoginControle() {
        LoginControle loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        loginControle.setConfiguracaoSistema(getConfiguracaoSistemaVO());
        context().getExternalContext().getSessionMap().put("LoginControle", loginControle);
    }

    public String consultar() {
        try {
            super.consultar();
            Integer empresa = getCodigoEmpresaLogado();
            List objs = new ArrayList(0);
            if (getControleConsulta().getCampoConsulta().equals("codigo")) {
                if (getControleConsulta().getValorConsulta().equals("")) {
                    getControleConsulta().setValorConsulta("0");
                }
                int valorInt = Integer.parseInt(getControleConsulta().getValorConsulta());
                objs = getFacadeFactory().getConfiguracaoSistemaFacade().consultarPorCodigo(new Integer(valorInt), empresa, true);
            }
            if (getControleConsulta().getCampoConsulta().equals("nomeFantasiaEmpresa")) {
                objs = getFacadeFactory().getConfiguracaoSistemaFacade().consultarPorNomeFantasiaEmpresa(getControleConsulta().getValorConsulta(), empresa);
            }

            setListaConsulta(objs);
            setMensagemID("msg_dados_consultados");
            return "consultar";
        } catch (Exception e) {
            setListaConsulta(new ArrayList(0));
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "consultar";
        }
    }

    public String excluir() {
        try {
            getFacadeFactory().getConfiguracaoSistemaFacade().excluir(configuracaoSistemaVO);
            setConfiguracaoSistemaVO(new ConfiguracaoSistemaVO());
            setMensagemID("msg_dados_excluidos");
            return "editar";
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
            return "editar";
        }
    }

    public void irPaginaInicial() throws Exception {
        controleConsulta.setPaginaAtual(1);
        this.consultar();
    }

    public void irPaginaAnterior() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getPaginaAtual() - 1);
        this.consultar();
    }

    public void irPaginaPosterior() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getPaginaAtual() + 1);
        this.consultar();
    }

    public void irPaginaFinal() throws Exception {
        controleConsulta.setPaginaAtual(controleConsulta.getNrTotalPaginas());
        this.consultar();
    }

    public void inicializarListasSelectItemTodosComboBox() {
        //montarListaSelectItemEmpresa();

    }

    public String inicializarConsultar() {
        setListaConsulta(new ArrayList(0));
        setMensagemID("msg_entre_prmconsulta");
        return "consultar";
    }

    public void upLoadImagemBanner(UploadEvent upload) {

        UploadItem item = upload.getUploadItem();
        File item1 = item.getFile();

        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte buffer[] = new byte[4096];
            int bytesRead = 0;
            FileInputStream fi = new FileInputStream(item1.getAbsolutePath());
            while ((bytesRead = fi.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, bytesRead);
            }
            getConfiguracaoSistemaVO().setImagemBanner(arrayOutputStream.toByteArray());

            String caminho = obterCaminhoDiretorioWeb() + File.separator + "imagens" + File.separator + "imagemBanner" + Uteis.removerMascara(getEmpresaLogado().getCNPJ()) + ".jpg";

            File verificarArquivo = new File(caminho);
            if (!verificarArquivo.exists()) {
                FileOutputStream outPut = new FileOutputStream(caminho);
                outPut.write(getConfiguracaoEmpresaLogado().getImagemBanner());
                outPut.flush();
                outPut.close();
            }



            arrayOutputStream.close();
            fi.close();
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void paintImagemBanner(OutputStream out, Object data) throws IOException, Exception {
        if (getConfiguracaoSistemaVO().getImagemBanner() == null) {
            String caminho = obterCaminhoDiretorioImagens() + File.separator + "homePadrao.jpg";
            File imagem = new File(caminho);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte buffer[] = new byte[4096];
            int bytesRead = 0;
            FileInputStream fi = new FileInputStream(imagem.getAbsolutePath());
            while ((bytesRead = fi.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, bytesRead);
            }
            getConfiguracaoSistemaVO().setImagemBanner(arrayOutputStream.toByteArray());
        }
        BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(new ByteArrayInputStream((getConfiguracaoSistemaVO().getImagemBanner()))));
        ImageIO.write(bufferedImage, "jpeg", out);
    }

    public void upLoadImagemRelatorio(UploadEvent upload) {
        UploadItem item = upload.getUploadItem();
        File item1 = item.getFile();
        try {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte buffer[] = new byte[4096];
            int bytesRead = 0;
            FileInputStream fi = new FileInputStream(item1.getAbsolutePath());
            while ((bytesRead = fi.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, bytesRead);
            }
            getConfiguracaoSistemaVO().setImagemRelatorio(arrayOutputStream.toByteArray());
            arrayOutputStream.close();
            fi.close();
        } catch (Exception e) {
            setMensagemDetalhada("msg_erro", e.getMessage());
        }
    }

    public void paintImagemRelatorio(OutputStream out, Object data) throws IOException, Exception {
        if (getConfiguracaoSistemaVO().getImagemRelatorio() == null) {
            String caminhoApp = obterCaminhoWebAplicacao();
            caminhoApp = caminhoApp.substring(0, caminhoApp.length() - 10);
            String caminho = caminhoApp + File.separator + "imagens" + File.separator + "logoPadraoRelatorio.jpg";
            File imagem = new File(caminho);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte buffer[] = new byte[4096];
            int bytesRead = 0;
            FileInputStream fi = new FileInputStream(imagem.getAbsolutePath());
            while ((bytesRead = fi.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, bytesRead);
            }
            getConfiguracaoSistemaVO().setImagemRelatorio(arrayOutputStream.toByteArray());
        }
        BufferedImage bufferedImage = ImageIO.read(new BufferedInputStream(new ByteArrayInputStream((getConfiguracaoSistemaVO().getImagemRelatorio()))));
        ImageIO.write(bufferedImage, "jpeg", out);
    }

    public Boolean getExisteImagemBanner() {
        if (getConfiguracaoSistemaVO().getImagemBanner() == null) {
            return false;
        }
        return true;
    }

    public Boolean getExisteImagemRelatorio() {
        if (getConfiguracaoSistemaVO().getImagemRelatorio() == null) {
            return false;
        }
        return true;
    }

    public ConfiguracaoSistemaVO getConfiguracaoSistemaVO() {
        return configuracaoSistemaVO;
    }

    public void setConfiguracaoSistemaVO(ConfiguracaoSistemaVO configuracaoSistemaVO) {
        this.configuracaoSistemaVO = configuracaoSistemaVO;
    }

    public byte[] getImagemBanner() {
        return imagemBanner;
    }

    public void setImagemBanner(byte[] imagemBanner) {
        this.imagemBanner = imagemBanner;
    }

    public byte[] getImagemRelatorio() {
        return imagemRelatorio;
    }

    public void setImagemRelatorio(byte[] imagemRelatorio) {
        this.imagemRelatorio = imagemRelatorio;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }


    public String getCaminhoPastaImagemBanner() {
        String caminho = "";
        try {
            caminho = obterCaminhoDiretorioWeb() + File.separator + "imagens" + File.separator + "imagemBanner" + Uteis.removerMascara(getEmpresaLogado().getCNPJ()) + ".jpg";
        } catch (Exception e) {
        }
        return caminho;
    }

    public String getNomeImagemBanner() {
        String nome = "";
        try {
            //nome = "imagemBanner" + Uteis.removerMascara(getEmpresaLogado().getCNPJ()) + ".jpg";
            if (getEmpresaLogado().getCNPJ().equals("00.000.000/0000-00")) {
                return nome = "homePadrao.jpg";
            }
            nome = "imagemBanner" + Uteis.removerMascara(getEmpresaLogado().getCNPJ()) + ".jpg";
        } catch (Exception e) {
        }
        return nome;
    }


    public String getEmailTeste() {
        if (emailTeste == null) {
            emailTeste = "contato@msisistemas.com.br";
        }
        return emailTeste;
    }

    public void setEmailTeste(String emailTeste) {
        this.emailTeste = emailTeste;
    }

}
