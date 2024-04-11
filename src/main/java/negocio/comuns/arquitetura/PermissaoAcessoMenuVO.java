package negocio.comuns.arquitetura;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class PermissaoAcessoMenuVO {
    private Boolean configuracaoSistema;
    private Boolean empresa;
    private Boolean cidade;
    private Boolean pais;
    private Boolean perfilAcesso;
    private Boolean usuario;
    private Boolean loginUsuario;
    private Boolean projeto;
    private Boolean entidade;

    public PermissaoAcessoMenuVO() {
    }

    public Boolean getCidade() {
        if(cidade == null){
            cidade = false;
        }
        return cidade;
    }

    public void setCidade(Boolean cidade) {
        this.cidade = cidade;
    }

    public Boolean getEmpresa() {
        if (empresa == null){
            empresa = false;
        }
        return empresa;
    }

    public void setEmpresa(Boolean empresa) {
        this.empresa = empresa;
    }

    public Boolean getConfiguracaoSistema() {
        if (configuracaoSistema == null){
            configuracaoSistema = false;
        }
        return configuracaoSistema;
    }

    public void setConfiguracaoSistema(Boolean configuracaoSistema) {
        this.configuracaoSistema = configuracaoSistema;
    }

    public Boolean getUsuario() {
        if (usuario == null){
            usuario = false;
        }
        return usuario;
    }

    public void setUsuario(Boolean usuario) {
        this.usuario = usuario;
    }

    public Boolean getPerfilAcesso() {
        if (perfilAcesso == null){
            perfilAcesso = false;
        }
        return perfilAcesso;
    }

    public void setPerfilAcesso(Boolean perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
    }

    public PermissaoAcessoMenuVO montarPermissoesMenu(List permissaoVOs) {

        PermissaoAcessoMenuVO permissaoAcessoMenuVO = new PermissaoAcessoMenuVO();
        Iterator j = permissaoVOs.iterator();

        while (j.hasNext()) {
            try {
                PermissaoVO obj = (PermissaoVO) j.next();

                Class classeGenerica = Class.forName(obj.getClass().getName());
                Method metodoGet = classeGenerica.getMethod("getNomeEntidade");
                metodoGet.invoke(obj).toString();

                Class permissaoMenu = Class.forName(permissaoAcessoMenuVO.getClass().getName());
                Method metodoSet = permissaoMenu.getMethod("set" + metodoGet.invoke(obj).toString(), Boolean.class);
                metodoSet.invoke(permissaoAcessoMenuVO, true);

            } catch (Exception e) {
            }
        }
        return permissaoAcessoMenuVO;
    }


    public Boolean getGroupCadastrosBasico() {
        if (getCidade()
                || getEmpresa()) {
            return false;
        }
        return true;
    }

    public Boolean getGroupCadastros() {
        if (!getGroupCadastrosBasico()) {
            return false;
        }
        return true;
    }

    public Boolean getPais() {
        if (pais == null) {
            pais = false;
        }
        return pais;
    }

    public void setPais(Boolean pais) {
        this.pais = pais;
    }

    public Boolean getLoginUsuario() {
        if (loginUsuario == null) {
            loginUsuario = false;
        }
        return loginUsuario;
    }

    public void setLoginUsuario(Boolean loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public Boolean getProjeto() {
        if (projeto == null) {
            projeto = false;
        }
        return projeto;
    }

    public void setProjeto(Boolean projeto) {
        this.projeto = projeto;
    }

    public Boolean getEntidade() {
        if (entidade == null) {
            entidade = false;
        }
        return entidade;
    }

    public void setEntidade(Boolean entidade) {
        this.entidade = entidade;
    }

}
