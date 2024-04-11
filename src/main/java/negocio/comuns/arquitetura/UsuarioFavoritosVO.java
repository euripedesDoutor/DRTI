package negocio.comuns.arquitetura;
import java.io.Serializable;
import negocio.comuns.cadastro.*;
import negocio.comuns.utilitarias.*;

public class UsuarioFavoritosVO extends SuperVO implements Serializable {
	
    private Integer codigo;
    private Integer usuario;
    private EmpresaVO empresa;
    private String titulo;
    private String endereco;
    private String icone;
    private String entidade;
	
    public UsuarioFavoritosVO() {
        super();
        inicializarDados();
    }
	
    public static void validarDados(UsuarioFavoritosVO obj) throws ConsistirException {
//        if ((obj.getUsuario() == null) ||
//            (obj.getUsuario().intValue() == 0)) { 
//            throw new ConsistirException("O campo Usuario (Perfil Acesso USUÁRIO) deve ser informado.");
//        }
        if ((obj.getEmpresa() == null) ||
            (obj.getEmpresa().getCodigo().intValue() == 0)) { 
            throw new ConsistirException("O campo EMPRESA (Perfil Acesso Usuário) deve ser informado.");
        }
        
    }

    public void inicializarDados() {
        setCodigo( 0 );
        //setEmpresa( new EmpresaVO() );
        setUsuario(0);
        //setPerfilAcesso( new PerfilAcessoVO() );
    }
	
    public Integer getCodigo() {
        return (codigo);
    }
     
    public void setCodigo( Integer codigo ) {
        this.codigo = codigo;
    }

    public EmpresaVO getEmpresa() {
        if (empresa == null){
            empresa = new EmpresaVO();
        }
        return empresa;
    }

    public void setEmpresa(EmpresaVO empresa) {
        this.empresa = empresa;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getUrlIcone() {
        return "./imagens/favoritos/" + getIcone();
    }
    public String getEndereco() {
        if (endereco == null){
            endereco = "";
        }
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTituloSemCaracteresEspeciais() {
        return Uteis.removerEspaco(Uteis.retirarAcentuacao(Uteis.retirarCaracteresEspeciais(getTitulo())));
    }
    public String getTitulo() {
        if (titulo == null){
            titulo = "";
        }
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIcone() {
        if (icone == null){
            icone = "favoritos.png";
        }
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getEntidade() {
        if (entidade == null){
            entidade = "";
        }
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }


}