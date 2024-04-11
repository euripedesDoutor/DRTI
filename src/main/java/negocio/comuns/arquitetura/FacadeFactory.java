package negocio.comuns.arquitetura;

import negocio.interfaces.arquitetura.LogInterfaceFacade;
import negocio.interfaces.arquitetura.PerfilAcessoInterfaceFacade;
import negocio.interfaces.arquitetura.PermissaoInterfaceFacade;
import negocio.interfaces.arquitetura.UsuarioInterfaceFacade;
import negocio.interfaces.arquitetura.UsuarioPerfilAcessoInterfaceFacade;
import negocio.interfaces.arquitetura.UsuarioFavoritosInterfaceFacade;
import negocio.interfaces.cadastro.CidadeInterfaceFacade;
import negocio.interfaces.cadastro.ConfiguracaoSistemaInterfaceFacade;
import negocio.interfaces.cadastro.EmpresaInterfaceFacade;
import negocio.interfaces.cadastro.PaisInterfaceFacade;
import negocio.interfaces.projeto.AtributosInterfaceFacade;
import negocio.interfaces.projeto.EntidadeInterfaceFacade;
import negocio.interfaces.projeto.ModuloInterfaceFacade;
import negocio.interfaces.projeto.ProjetoInterfaceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class FacadeFactory {

    @Autowired
    private CidadeInterfaceFacade cidadeFacade = null;
    @Autowired
    private ConfiguracaoSistemaInterfaceFacade configuracaoSistemaFacade = null;
    @Autowired
    private EmpresaInterfaceFacade empresaFacade = null;
    @Autowired
    private PerfilAcessoInterfaceFacade perfilAcessoFacade = null;
    @Autowired
    private UsuarioInterfaceFacade usuarioFacade = null;
    @Autowired
    private LogInterfaceFacade logFacade = null;
    @Autowired
    private UsuarioPerfilAcessoInterfaceFacade usuarioPerfilAcessoFacade = null;
    @Autowired
    private UsuarioFavoritosInterfaceFacade usuarioFavoritosFacade = null;
    @Autowired
    private PermissaoInterfaceFacade permissaoFacade = null;
    @Autowired
    private PaisInterfaceFacade paisFacade;
    @Autowired
    private ProjetoInterfaceFacade projetoFacade;
    @Autowired
    private ModuloInterfaceFacade moduloFacade;
    @Autowired
    private EntidadeInterfaceFacade entidadeFacade;
    @Autowired
    private AtributosInterfaceFacade atributosFacade;

    public ConfiguracaoSistemaInterfaceFacade getConfiguracaoSistemaFacade() throws Exception {
        return configuracaoSistemaFacade;
    }

    public void setConfiguracaoSistemaFacade(ConfiguracaoSistemaInterfaceFacade configuracaoSistemaFacade) {
        this.configuracaoSistemaFacade = configuracaoSistemaFacade;
    }

    public CidadeInterfaceFacade getCidadeFacade() throws Exception {
        return cidadeFacade;
    }

    public void setCidadeFacade(CidadeInterfaceFacade cidadeFacade) {
        this.cidadeFacade = cidadeFacade;
    }

    public EmpresaInterfaceFacade getEmpresaFacade() throws Exception {
        return empresaFacade;
    }

    public void setEmpresaFacade(EmpresaInterfaceFacade empresaFacade) {
        this.empresaFacade = empresaFacade;
    }

    public PerfilAcessoInterfaceFacade getPerfilAcessoFacade() throws Exception {
        return perfilAcessoFacade;
    }

    public void setPerfilAcessoFacade(PerfilAcessoInterfaceFacade perfilAcessoFacade) {
        this.perfilAcessoFacade = perfilAcessoFacade;
    }

    public UsuarioInterfaceFacade getUsuarioFacade() throws Exception {
        return usuarioFacade;
    }

    public void setUsuarioFacade(UsuarioInterfaceFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    public LogInterfaceFacade getLogFacade() throws Exception {
        return logFacade;
    }

    public void setLogFacade(LogInterfaceFacade logFacade) {
        this.logFacade = logFacade;
    }

    public UsuarioPerfilAcessoInterfaceFacade getUsuarioPerfilAcessoFacade() throws Exception {
        return usuarioPerfilAcessoFacade;
    }

    public void setUsuarioPerfilAcessoFacade(UsuarioPerfilAcessoInterfaceFacade usuarioPerfilAcessoFacade) {
        this.usuarioPerfilAcessoFacade = usuarioPerfilAcessoFacade;
    }

    public PermissaoInterfaceFacade getPermissaoFacade() throws Exception {
        return permissaoFacade;
    }

    public void setPermissaoFacade(PermissaoInterfaceFacade permissaoFacade) {
        this.permissaoFacade = permissaoFacade;
    }

    public UsuarioFavoritosInterfaceFacade getUsuarioFavoritosFacade() {
        return usuarioFavoritosFacade;
    }

    public void setUsuarioFavoritosFacade(UsuarioFavoritosInterfaceFacade usuarioFavoritosFacade) {
        this.usuarioFavoritosFacade = usuarioFavoritosFacade;
    }
    public PaisInterfaceFacade getPaisFacade() {
        return paisFacade;
    }

    public void setPaisFacade(PaisInterfaceFacade paisFacade) {
        this.paisFacade = paisFacade;
    }

    public ProjetoInterfaceFacade getProjetoFacade() {
        return projetoFacade;
    }

    public void setProjetoFacade(ProjetoInterfaceFacade projetoFacade) {
        this.projetoFacade = projetoFacade;
    }

    public ModuloInterfaceFacade getModuloFacade() {
        return moduloFacade;
    }

    public void setModuloFacade(ModuloInterfaceFacade moduloFacade) {
        this.moduloFacade = moduloFacade;
    }

    public EntidadeInterfaceFacade getEntidadeFacade() {
        return entidadeFacade;
    }

    public void setEntidadeFacade(EntidadeInterfaceFacade entidadeFacade) {
        this.entidadeFacade = entidadeFacade;
    }

    public AtributosInterfaceFacade getAtributosFacade() {
        return atributosFacade;
    }

    public void setAtributosFacade(AtributosInterfaceFacade atributosFacade) {
        this.atributosFacade = atributosFacade;
    }
}
