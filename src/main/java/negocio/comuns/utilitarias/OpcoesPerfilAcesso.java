/*
 * OpcoesPerfilAcesso.java
 *
 * Created on 6 de Setembro de 2007, 11:37
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package negocio.comuns.utilitarias;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class OpcoesPerfilAcesso {

    private static List<OpcaoPerfilAcesso> modulos;
    private static List<OpcaoPerfilAcesso> entidadesCadastro;
    private static List<OpcaoPerfilAcesso> entidadesArquitetura;
    private static List<OpcaoPerfilAcesso> entidadesProjeto;

    public OpcoesPerfilAcesso() {
        inicializarCadastro();
        inicializarArquitetura();
        inicializarProjeto();
    }

    public static void inicializarModulos() {
        modulos.add(new OpcaoPerfilAcesso("Cadastro", "Cadastro", OpcaoPerfilAcesso.TP_MODULO));
        modulos.add(new OpcaoPerfilAcesso("Arquitetura", "Arquitetura", OpcaoPerfilAcesso.TP_MODULO));
        modulos.add(new OpcaoPerfilAcesso("Projeto", "Projeto", OpcaoPerfilAcesso.TP_MODULO));
    }

    public static void inicializarArquitetura() {
        entidadesArquitetura.add(new OpcaoPerfilAcesso("ConfiguracaoSistema", "Administrador > Configuração do Sistema", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("LoginUsuario", "Administrador > Usuários Conectados", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("PainelGestor", "Administrador > Painel Gestor", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("PerfilAcesso", "Administrador > Perfil de Acesso", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("Usuario", "Administrador > Usuário", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("UsuarioRel", "Adminstrador > Usuário > Relatório de Usuário", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("ContasUsuario", "Administrador > Contas Usuário", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("GerenciamentoSistema", "Administrador > Gerenciamento Sistema", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesArquitetura.add(new OpcaoPerfilAcesso("AcompanhamentoPendencias", "Administrador > Acompanhamento de Pendências", OpcaoPerfilAcesso.TP_ENTIDADE));
    }

    public static void inicializarProjeto() {
        entidadesProjeto.add(new OpcaoPerfilAcesso("Projeto", "Projeto > Projeto", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesProjeto.add(new OpcaoPerfilAcesso("Entidade", "Projeto > Entidade", OpcaoPerfilAcesso.TP_ENTIDADE));

    }

    public static void inicializarCadastro() {
        entidadesCadastro.add(new OpcaoPerfilAcesso("Cidade", "Cadastros > Básico > Cidade", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesCadastro.add(new OpcaoPerfilAcesso("Pais", "Cadastros > Básico > País", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesCadastro.add(new OpcaoPerfilAcesso("Cargo", "Cadastros > Básico > Cargo", OpcaoPerfilAcesso.TP_ENTIDADE));
        entidadesCadastro.add(new OpcaoPerfilAcesso("Empresa", "Cadastros > Básico > Empresa", OpcaoPerfilAcesso.TP_ENTIDADE));
    }


    public static String obterTituloReferenteOpcaoPerfilAcesso(String nomeOpcao) {
        for (OpcaoPerfilAcesso opcao : OpcoesPerfilAcesso.getTodasEntidades()) {
            if (opcao == null) {
                continue;
            }
            if (opcao.getNome().equals(nomeOpcao)) {
                return opcao.getTitulo();
            }
        }
        return "Entidade não encontrada";
    }

    public static List<OpcaoPerfilAcesso> getTodasEntidades() {
        List listaEntidades = new ArrayList<OpcaoPerfilAcesso>();
        listaEntidades.addAll(getEntidadesCadastro());
        listaEntidades.addAll(getEntidadesProjeto());
        listaEntidades.addAll(getEntidadesArquitetura());
        return listaEntidades;
    }

    public static List<OpcaoPerfilAcesso> getEntidadesArquitetura() {
        if (entidadesArquitetura == null || entidadesArquitetura.isEmpty()) {
            entidadesArquitetura = new ArrayList<OpcaoPerfilAcesso>(0);
            inicializarArquitetura();
        }
        return entidadesArquitetura;
    }

    public static void setEntidadesArquitetura(List<OpcaoPerfilAcesso> entidadesArquitetura) {
        OpcoesPerfilAcesso.entidadesArquitetura = entidadesArquitetura;
    }

    public static List<OpcaoPerfilAcesso> getEntidadesCadastro() {
        if (entidadesCadastro == null || entidadesCadastro.isEmpty()) {
            entidadesCadastro = new ArrayList<OpcaoPerfilAcesso>(0);
            inicializarCadastro();
        }
        return entidadesCadastro;
    }

    public static void setEntidadesCadastro(List<OpcaoPerfilAcesso> entidadesCadastro) {
        OpcoesPerfilAcesso.entidadesCadastro = entidadesCadastro;
    }

    public static List<OpcaoPerfilAcesso> getModulos() {
        if (modulos == null || modulos.isEmpty()) {
            modulos = new ArrayList<OpcaoPerfilAcesso>(0);
            inicializarModulos();
        }
        return modulos;
    }

    public static void setModulos(List<OpcaoPerfilAcesso> modulos) {
        OpcoesPerfilAcesso.modulos = modulos;
    }

    public static List<OpcaoPerfilAcesso> getEntidadesProjeto() {
        if (entidadesProjeto == null || entidadesProjeto.isEmpty()) {
            entidadesProjeto = new ArrayList<OpcaoPerfilAcesso>(0);
            inicializarProjeto();
        }
        return entidadesProjeto;
    }

    public static void setEntidadesPorjeto(List<OpcaoPerfilAcesso> entidadesProjeto) {
        OpcoesPerfilAcesso.entidadesProjeto = entidadesProjeto;
    }
}
