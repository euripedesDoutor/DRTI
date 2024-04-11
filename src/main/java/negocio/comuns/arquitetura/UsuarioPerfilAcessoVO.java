package negocio.comuns.arquitetura;
import java.io.Serializable;
import negocio.comuns.cadastro.*;
import negocio.comuns.utilitarias.*;

public class UsuarioPerfilAcessoVO extends SuperVO implements Serializable {
	
    protected Integer codigo;
    private Integer usuario;
    private EmpresaVO empresa;
    protected PerfilAcessoVO perfilAcesso;
	
    public UsuarioPerfilAcessoVO() {
        super();
        inicializarDados();
    }
	
    public static void validarDados(UsuarioPerfilAcessoVO obj) throws ConsistirException {
//        if ((obj.getUsuario() == null) ||
//            (obj.getUsuario().intValue() == 0)) { 
//            throw new ConsistirException("O campo Usuario (Perfil Acesso USUÁRIO) deve ser informado.");
//        }
        if ((obj.getEmpresa() == null) ||
            (obj.getEmpresa().getCodigo().intValue() == 0)) { 
            throw new ConsistirException("O campo EMPRESA (Perfil Acesso Usuário) deve ser informado.");
        }
        if ((obj.getPerfilAcesso() == null) ||
            (obj.getPerfilAcesso().getCodigo().intValue() == 0)) { 
            throw new ConsistirException("O campo PERFIL ACESSO (Perfil Acesso Usuário) deve ser informado.");
        }
    }
     
    /**
     * Operação reponsável por inicializar os atributos da classe.
    */
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

    public PerfilAcessoVO getPerfilAcesso() {
        if (perfilAcesso == null){
            perfilAcesso = new PerfilAcessoVO();
        }
        return perfilAcesso;
    }

    public void setPerfilAcesso(PerfilAcessoVO perfilAcesso) {
        this.perfilAcesso = perfilAcesso;
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
}