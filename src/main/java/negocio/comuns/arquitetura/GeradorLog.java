/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.comuns.arquitetura;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import negocio.comuns.utilitarias.Ordenacao;
import annotations.arquitetura.ChaveEstrangeira;
import annotations.arquitetura.ChavePrimaria;
import annotations.arquitetura.DescricaoObjeto;
import annotations.arquitetura.Lista;
import annotations.arquitetura.NaoControlarLogAlteracao;
import controle.arquitetura.LoginControle;

import java.security.Timestamp;

import negocio.comuns.utilitarias.Uteis;

/**
 * @author Eurioedes
 */
public class GeradorLog {

    protected List logDeAlteracoes;
    protected LogVO log;
    protected String nomeEntidade;
    protected String nomeEntidadeDescricao;
    protected String chavePrimaria;
    protected String responsavel;
    protected String chavePrimariaDoObjetoAnteriorChaveEstrangeira;
    protected String chavePrimariaDoObjetoAlteradoChaveEstrangeira;
    protected String valorCampoAlterado;
    protected String valorCampoAnterior;
    protected LoginControle loginControle;

    public GeradorLog() {
        loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        inicializarDados();
    }

    public void inicializarDados() {
        setNomeEntidade("");
        setNomeEntidadeDescricao("");
        setChavePrimaria("");
        setResponsavel("");
        setChavePrimariaDoObjetoAlteradoChaveEstrangeira("");
        setChavePrimariaDoObjetoAnteriorChaveEstrangeira("");
    }

    /**
     * Método responsável por fazer a comparação do objeto nas duas situações: Objeto Alterado e Objeto antes da
     * alteração. Apartir do valor do Field será gerado o LOG e nele em qual situação se encontra o Objeto. Por exempro:
     * Se o field no objetoAlterado estiver com valor diferente do field no objetoAnterior. Apartir dessa comparação
     * será gerado um log com a operação ALTERAÇÃO. Fields que utilizam annotation NaoControlarLogAlteracao e
     * ChavePrimaria não são comparados. Field com annotation NaoControlarLogAlteracao significa que para fields com
     * essa annotation não seráo gerados o LOG. Field com annotation ChavePrimaria significa que para fields com essa
     * annotation não seráo gerados o LOG e tem a função de informar qual Field é a ChavePrimaria do objeto. A
     * comparação dos Fields são feitos em 3 tipos, são eles: Field com annotation ChaveEstrangeira Field com annotation
     * Lista Field sem annotation
     *
     * @param objetoAlterado  Objeto após ser alterado.
     * @param objetoAnterior  Objeto antes da alteração
     * @param nomeEntidadeMae Nome da entidade mãe. Esse variavel só é utilizada pro caso de Fields com annotation do tipo Lista.
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.IllegalAccessException
     */
    public void validarCamposDeclaradosComAnnotations(Object objetoAlterado, Object objetoAnterior, String nomeEntidadeMae, String nomeEntidadeMaeDescricao, String chavePrimariaEntidadeSubordinada) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, Exception {
        loginControle = (LoginControle) context().getExternalContext().getSessionMap().get("LoginControle");
        adquirirNomeEntidadeChavePrimariaResponsavelAlteracao(objetoAlterado, chavePrimariaEntidadeSubordinada);

        Class superClasse = objetoAnterior.getClass().getSuperclass();
        if (!superClasse.getSimpleName().equals("SuperVO")) {
            Field[] fieldsSuperClasse = superClasse.getDeclaredFields();
            gerarLogAlteracoes(fieldsSuperClasse, objetoAlterado, objetoAnterior, nomeEntidadeMae, nomeEntidadeMaeDescricao, chavePrimariaEntidadeSubordinada);
            Field[] fieldsPrincipal = objetoAnterior.getClass().getDeclaredFields();
            gerarLogAlteracoes(fieldsPrincipal, objetoAlterado, objetoAnterior, nomeEntidadeMae, nomeEntidadeMaeDescricao, chavePrimariaEntidadeSubordinada);
        } else {
            Field[] fieldsPrincipal = objetoAnterior.getClass().getDeclaredFields();
            gerarLogAlteracoes(fieldsPrincipal, objetoAlterado, objetoAnterior, nomeEntidadeMae, nomeEntidadeMaeDescricao, chavePrimariaEntidadeSubordinada);
        }
    }

    public void gerarLogAlteracoes(Field[] fields, Object objetoAlterado, Object objetoAnterior, String nomeEntidadeMae, String nomeEntidadeMaeDescricao, String chavePrimariaEntidadeSubordinada) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, Exception {
        // percorre todos os campos do javabean
        for (Field field : fields) {
            // Verifica se existe marcao. No nosso caso é @NaoComparados, @ChavePrimaria e @Responsavel.
            // Não preciso comparar os campos ChavePrimaria e Responsavel porque os mesmos não seráo inclusos no LOG
            // mais preciso dos campos para saber qual é a ChavePrimaria e o Responsavel pela alteração.

            if ((!field.isAnnotationPresent(NaoControlarLogAlteracao.class)) && (!field.isAnnotationPresent(ChavePrimaria.class))) {

                // permite acesso aos dados do campo
                field.setAccessible(true);
                // verifica se o alterado é nulo, se for ele passa pro proximo field.
                if ((field.get(objetoAlterado)) == null) {
                    continue;
                }
                // comparação se o field do objeto principal é diferente do objeto secundario
                // caso seja será adicionado no LOG as informações do field.
                if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
                    adquirirChavePrimariaDoObjetoDeChaveEstrangeira(objetoAlterado, objetoAnterior, nomeEntidadeMae, nomeEntidadeMaeDescricao, field);
                } else if (field.isAnnotationPresent(Lista.class)) {
                    adquirirAcoesRealizadasNaListaItems(objetoAlterado, objetoAnterior, field);
                } else {
                    if (field.get(objetoAlterado).equals(field.get(objetoAnterior)) == false) {
                        try {
                            String nomeCampo = loginControle.getNomeEntidadeCamposLog("prt_" + getNomeEntidade() + "_" + field.getName());
                            getLog().setNomeEntidade(nomeEntidadeMae + getNomeEntidade());
                            getLog().setNomeEntidadeDescricao(nomeEntidadeMaeDescricao + getNomeEntidadeDescricao());
                            getLog().setChavePrimaria(getChavePrimaria());
                            getLog().setChavePrimariaEntidadeSubordinada(chavePrimariaEntidadeSubordinada);
                            getLog().setNomeCampo(nomeCampo);
                            if (field.get(objetoAnterior) instanceof Date) {
                                getLog().setValorCampoAnterior(Uteis.getData((Date) field.get(objetoAnterior)) + " " + Uteis.gethoraHHMMSS((Date) field.get(objetoAnterior)));
                                getLog().setValorCampoAlterado(Uteis.getData((Date) field.get(objetoAlterado)) + " " + Uteis.gethoraHHMMSS((Date) field.get(objetoAlterado)));
                            } else {
                                getLog().setValorCampoAnterior(field.get(objetoAnterior).toString());
                                getLog().setValorCampoAlterado(field.get(objetoAlterado).toString());
                            }
                            getLog().setDataAlteracao(new Date());
                            getLog().setResponsavelAlteracao(loginControle.getUsuarioLogado().getNome());
                            getLog().setOperacao("ALTERAÇÃO");
                            getLogDeAlteracoes().add(getLog());
                            setLog(new LogVO());
                        } catch (Exception e) {
                            System.out.println("Erro na hora de montar os dados do log");
                        }
                    }
                }
            }
        }
    }

    /**
     * Método responsável por adquirir a Entidade, a Chave Primaria do Objeto e o Responsavel pela Alteração.
     *
     * @param objetoSecundario
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.IllegalAccessException
     */
    public void adquirirNomeEntidadeChavePrimariaResponsavelAlteracao(Object objetoSecundario, String chavePrimariaEntidadeSubordinada) throws IllegalArgumentException, IllegalAccessException, Exception {
        Field[] fieldsSecundario = objetoSecundario.getClass().getDeclaredFields();

        String nomeEntidadeStr = objetoSecundario.getClass().getSimpleName();
        int tamanhoString = nomeEntidadeStr.length() - 2;
        setNomeEntidade(nomeEntidadeStr.substring(0, tamanhoString));
        nomeEntidadeStr = loginControle.getNomeEntidadeCamposLog("prt_" + getNomeEntidade() + "_tituloForm");
        setNomeEntidadeDescricao(nomeEntidadeStr);
        // percorre todos os campos do javabean
        if (chavePrimariaEntidadeSubordinada.equals("")) {

            for (Field field : fieldsSecundario) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ChavePrimaria.class)) {
                    setChavePrimaria(field.get(objetoSecundario).toString());
                    break;
                }
            }
            if (getChavePrimaria().equals("")) {
                Field[] fieldsSecundarioClasseMae = objetoSecundario.getClass().getSuperclass().getDeclaredFields();
                for (Field field : fieldsSecundarioClasseMae) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ChavePrimaria.class)) {
                        setChavePrimaria(field.get(objetoSecundario).toString());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Método reponsável por adquirir a Chave Primaria do objetoAlterado e objetoAnterior. Com o valor da Chave Primaria
     * sera comparado pra saber se o objeto foi ALTERADO.
     *
     * @param objetoAlterado
     * @param objetoAnterior
     * @param field
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.IllegalAccessException
     */
    public void adquirirChavePrimariaDoObjetoDeChaveEstrangeira(Object objetoAlterado, Object objetoAnterior, String nomeEntidadeMae, String nomeEntidadeMaeDescricao, Field field) throws IllegalArgumentException, IllegalAccessException, Exception {

        adquirirNomeEntidadeChavePrimariaResponsavelAlteracao(objetoAnterior, "CHAVEESTRANGEIRA");

        setChavePrimariaDoObjetoAnteriorChaveEstrangeira("");
        setChavePrimariaDoObjetoAlteradoChaveEstrangeira("");
        Object valorObjetoAlterado = new Object();
        if (field.get(objetoAlterado) != null) {
            valorObjetoAlterado = field.get(objetoAlterado);
        }
        Object valorObjetoAnterior = new Object();
        if (field.get(objetoAnterior) != null) {
            valorObjetoAnterior = field.get(objetoAnterior);
        } else {
            valorObjetoAnterior = field.get(objetoAlterado).getClass().newInstance();
        }

        // O valor 1 é referente ao PRIMEIRO OBJETO.
        // O valor 2 é referente ao SEGUNDO OBJETO.
        setValorCampoAlterado("");
        setValorCampoAnterior("");
        obterChavePrimariaObjeto(valorObjetoAnterior, "1");
        obterChavePrimariaObjeto(valorObjetoAlterado, "2");

        if (!getChavePrimariaDoObjetoAnteriorChaveEstrangeira().equals(getChavePrimariaDoObjetoAlteradoChaveEstrangeira())) {
            String nomeCampo = loginControle.getNomeEntidadeCamposLog("prt_" + getNomeEntidade() + "_" + field.getName());

            getLog().setNomeEntidade(nomeEntidadeMae + getNomeEntidade());
            getLog().setNomeEntidadeDescricao(nomeEntidadeMaeDescricao + getNomeEntidadeDescricao());
            getLog().setChavePrimaria(getChavePrimaria());
            getLog().setChavePrimariaEntidadeSubordinada("");
            getLog().setNomeCampo(nomeCampo);
            getLog().setValorCampoAnterior(getValorCampoAnterior());
            getLog().setValorCampoAlterado(getValorCampoAlterado());
            getLog().setDataAlteracao(new Date());
            getLog().setResponsavelAlteracao(loginControle.getUsuarioLogado().getNome());
            getLog().setOperacao("ALTERAÇÃO");
            getLogDeAlteracoes().add(getLog());
            setLog(new LogVO());
        }

    }

    /**
     * Método responsável por controlar e gerar LOGs das ações que foram realizadas na lista subordinada. Ações:
     * Inclusão, Alteração e Exclusão.
     *
     * @param objetoAlterado
     * @param objetoAnterior
     * @param field
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     */
    public void adquirirAcoesRealizadasNaListaItems(Object objetoAlterado, Object objetoAnterior, Field field) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, Exception {
        String nomeEntidadeMae = objetoAnterior.getClass().getSimpleName();
        int tamanhoString = nomeEntidadeMae.length() - 2;
        nomeEntidadeMae = nomeEntidadeMae.substring(0, tamanhoString) + " - ";
        String nomeEntidadeMaeDescricao = loginControle.getNomeEntidadeCamposLog("prt_" + nomeEntidadeMae.substring(0, tamanhoString) + "_tituloForm") + " - ";
        List listaAnterior = (ArrayList) field.get(objetoAnterior);
        List listaAlterado = (ArrayList) field.get(objetoAlterado);
        listaAnterior = Ordenacao.ordenarLista(listaAnterior, "codigo");
        listaAlterado = Ordenacao.ordenarLista(listaAlterado, "codigo");

        validarCamposListas(listaAlterado, listaAnterior, "EXCLUSÃO", nomeEntidadeMae, nomeEntidadeMaeDescricao);
        validarCamposListas(listaAlterado, listaAnterior, "ALTERAÇÃO", nomeEntidadeMae, nomeEntidadeMaeDescricao);

    }

    public void validarCamposListas(List listaAlterado, List listaAnterior, String tipoOperacao, String nomeEntidadeMae, String nomeEntidadeMaeDescricao) throws IllegalArgumentException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, Exception {
        String nomeEntidadeLista = "";
        int numeroObjetosIguais = 0;

        Iterator i = null;
        if (tipoOperacao.equals("EXCLUSÃO")) {
            i = listaAnterior.iterator();
        } else {
            i = listaAlterado.iterator();
        }
        while (i.hasNext()) {
            numeroObjetosIguais = 0;
            SuperVO primeiroObjeto = (SuperVO) i.next();
            Object valorPrimeiroObjeto = primeiroObjeto;

            nomeEntidadeLista = valorPrimeiroObjeto.getClass().getSimpleName();
            int tamanhoString = nomeEntidadeLista.length() - 2;
            nomeEntidadeLista = nomeEntidadeLista.substring(0, tamanhoString);
            String nomeEntidadeListaDescricao = loginControle.getNomeEntidadeCamposLog("prt_" + nomeEntidadeLista + "_tituloForm");

            nomeEntidadeLista = nomeEntidadeMae + nomeEntidadeLista;

            if (tipoOperacao.equals("ALTERAÇÃO")) {
                setChavePrimariaDoObjetoAlteradoChaveEstrangeira("");
            } else {
                setChavePrimariaDoObjetoAnteriorChaveEstrangeira("");
            }
            setValorCampoAlterado("");
            setValorCampoAnterior("");

            Iterator j = null;
            if (tipoOperacao.equals("EXCLUSÃO")) {
                j = listaAlterado.iterator();
            } else {
                j = listaAnterior.iterator();
            }
            while (j.hasNext()) {
                setValorCampoAlterado("");
                setValorCampoAnterior("");
                SuperVO segundoObjeto = (SuperVO) j.next();
                Object valorSegundoObjeto = tipoOperacao.equals("ALTERAÇÃO") ? primeiroObjeto : segundoObjeto;
                valorPrimeiroObjeto = tipoOperacao.equals("ALTERAÇÃO") ? segundoObjeto : primeiroObjeto;
                obterChavePrimariaObjeto(valorPrimeiroObjeto, tipoOperacao.equals("ALTERAÇÃO") ? "1" : "2");
                obterChavePrimariaObjeto(valorSegundoObjeto, tipoOperacao.equals("ALTERAÇÃO") ? "2" : "1");
                if ((getChavePrimariaDoObjetoAnteriorChaveEstrangeira().equals(getChavePrimariaDoObjetoAlteradoChaveEstrangeira()))) {
                    if (tipoOperacao.equals("ALTERAÇÃO")) {
                        validarCamposDeclaradosComAnnotations(valorSegundoObjeto, valorPrimeiroObjeto, nomeEntidadeMae, nomeEntidadeMaeDescricao, getChavePrimariaDoObjetoAlteradoChaveEstrangeira());
                        numeroObjetosIguais++;
                    } else {
                        numeroObjetosIguais++;
                    }
                    break;
                }
            }
            if (numeroObjetosIguais == 0) {
                if (tipoOperacao.equals("ALTERAÇÃO")) {
                    adicionarListaLogDeAcordoComOperacao(valorPrimeiroObjeto, "INCLUSÃO", nomeEntidadeLista, nomeEntidadeListaDescricao);
                } else if (tipoOperacao.equals("EXCLUSÃO")) {
                    adicionarListaLogDeAcordoComOperacao(valorPrimeiroObjeto, "EXCLUSÃO", nomeEntidadeLista, nomeEntidadeListaDescricao);
                }

            }
        }
    }

    public void adicionarListaLogDeAcordoComOperacao(Object valorPrimeiroObjeto, String tipoOperacao, String nomeEntidadeLista, String nomeEntidadeListaDescricao) throws Exception {
        getLog().setNomeEntidade(nomeEntidadeLista);
        getLog().setNomeEntidadeDescricao(nomeEntidadeListaDescricao);
        getLog().setChavePrimaria(getChavePrimaria());
        getLog().setChavePrimariaEntidadeSubordinada(getChavePrimariaDoObjetoAlteradoChaveEstrangeira());
        getLog().setNomeCampo("");
        if (getValorCampoAlterado().equals("")) {
            Field[] fields = valorPrimeiroObjeto.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                int tamanhoStrig = nomeEntidadeLista.indexOf("-") + 2;
                String nome = nomeEntidadeLista.substring(tamanhoStrig, nomeEntidadeLista.length());
                String nomeCampo = loginControle.getNomeEntidadeCamposLog("prt_" + nome + "_" + field.getName());
                if (field.isAnnotationPresent(NaoControlarLogAlteracao.class)) {
                    continue;
                }
                if (field.isAnnotationPresent(ChavePrimaria.class)) {
                    continue;
                }
                if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
                    setChavePrimariaDoObjetoAlteradoChaveEstrangeira("");
                    setValorCampoAlterado("");
                    setValorCampoAnterior("");
                    obterChavePrimariaObjeto(field.get(valorPrimeiroObjeto), tipoOperacao.equals("EXCLUSÃO") ? "1" : "2");
                    getLog().setValorCampoAlterado(getLog().getValorCampoAlterado() + nomeCampo + ": " + obterDescricaoObjetoChaveEstrangeira(valorPrimeiroObjeto));
                    // Field[] fieldsChaveEstrangeira = field.get(valorPrimeiroObjeto).getClass().getDeclaredFields();
                    // Object objetoChaveEstrangeira = field.get(valorPrimeiroObjeto);
                    // obterNomesCamposChaveEstrangeira(fieldsChaveEstrangeira, objetoChaveEstrangeira);
                } else {
                    getLog().setValorCampoAlterado(getLog().getValorCampoAlterado() + nomeCampo + ": " + field.get(valorPrimeiroObjeto) + "-" + obterDescricaoObjeto(valorPrimeiroObjeto));
                }
            }
        } else {
            getLog().setValorCampoAlterado(getLog().getValorCampoAlterado() + getValorCampoAlterado());
        }
        setValorCampoAlterado("");
        setValorCampoAnterior("");
        getLog().setValorCampoAnterior("");
        getLog().setDataAlteracao(new Date());
        getLog().setResponsavelAlteracao(loginControle.getUsuarioLogado().getNome());
        getLog().setOperacao(tipoOperacao);
        getLogDeAlteracoes().add(getLog());

        setLog(new LogVO());
    }

    // public void obterNomesCamposChaveEstrangeira(Field[] fields, Object valorPrimeiroObjeto) throws
    // IllegalArgumentException, IllegalAccessException {
    // for (Field fieldChaveEstrangeira : fields) {
    // fieldChaveEstrangeira.setAccessible(true);
    // if (fieldChaveEstrangeira.isAnnotationPresent(ChavePrimaria.class)) {
    // int tamanhoString = valorPrimeiroObjeto.getClass().getSimpleName().length() - 2;
    // String nomeCampo = valorPrimeiroObjeto.getClass().getSimpleName().substring(0, tamanhoString);
    // getLog().setValorCampoAnterior(getLog().getValorCampoAnterior() + "Campo - " + nomeCampo + " : " +
    // fieldChaveEstrangeira.get(valorPrimeiroObjeto) + "\n");
    // continue;
    // }
    // if (fieldChaveEstrangeira.isAnnotationPresent(ChaveEstrangeira.class)) {
    // Field[] fieldss = fieldChaveEstrangeira.get(valorPrimeiroObjeto).getClass().getDeclaredFields();
    // Object objetoChaveEstrangeira = fieldChaveEstrangeira.get(valorPrimeiroObjeto);
    // obterNomesCamposChaveEstrangeira(fieldss, objetoChaveEstrangeira);
    // continue;
    // }
    // if (fieldChaveEstrangeira.isAnnotationPresent(Lista.class)) {
    //
    // continue;
    // }
    // }
    // }
    public void obterChavePrimariaObjeto(Object valorObjeto, String tipoObjeto) throws IllegalArgumentException, IllegalArgumentException, IllegalArgumentException, IllegalAccessException {
        Field[] fieldsBeanPrincipal = valorObjeto.getClass().getDeclaredFields();
        if (valorObjeto.getClass().getSuperclass() != null) {
            if (!valorObjeto.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
                fieldsBeanPrincipal = valorObjeto.getClass().getSuperclass().getDeclaredFields();
            }
        }

        for (Field fieldPrincipal : fieldsBeanPrincipal) {
            fieldPrincipal.setAccessible(true);
            if (fieldPrincipal.isAnnotationPresent(ChavePrimaria.class)) {
                if ((fieldPrincipal.get(valorObjeto)) == null) {
                    break;
                }
                if (tipoObjeto.equals("1")) {
                    setChavePrimariaDoObjetoAnteriorChaveEstrangeira(fieldPrincipal.get(valorObjeto).toString());
                } else {
                    setChavePrimariaDoObjetoAlteradoChaveEstrangeira(fieldPrincipal.get(valorObjeto).toString());
                }
                break;
            }
        }
        for (Field fieldPrincipal : fieldsBeanPrincipal) {
            fieldPrincipal.setAccessible(true);
            if (fieldPrincipal.isAnnotationPresent(DescricaoObjeto.class)) {
                if ((fieldPrincipal.get(valorObjeto)) == null) {
                    break;
                }
                String nomeEntidadeLista = valorObjeto.getClass().getSimpleName();
                String nome = nomeEntidadeLista.substring(0, nomeEntidadeLista.length() - 2);
                String nomeCampo = loginControle.getNomeEntidadeCamposLog("prt_" + nome + "_" + fieldPrincipal.getName());
                if (tipoObjeto.equals("1")) {
                    if (!getValorCampoAnterior().isEmpty() && !getValorCampoAnterior().endsWith("<br>")) {
                        setValorCampoAnterior(getValorCampoAnterior() + "<br>");
                    }
                } else {
                    if (!getValorCampoAlterado().isEmpty() && !getValorCampoAlterado().endsWith("<br>")) {
                        setValorCampoAlterado(getValorCampoAlterado() + "<br>");
                    }
                }
                if (fieldPrincipal.isAnnotationPresent(ChaveEstrangeira.class)) {
                    if (tipoObjeto.equals("1")) {
                        setValorCampoAnterior(getValorCampoAnterior() + nomeCampo + ": " + obterChaveEstrangeiraObjeto(fieldPrincipal.get(valorObjeto)) + "-" + obterDescricaoObjeto(fieldPrincipal.get(valorObjeto)));
                    } else {
                        setValorCampoAlterado(getValorCampoAlterado() + nomeCampo + ": " + obterChaveEstrangeiraObjeto(fieldPrincipal.get(valorObjeto)) + "-" + obterDescricaoObjeto(fieldPrincipal.get(valorObjeto)));
                    }
                } else {
                    if (tipoObjeto.equals("1")) {
                        setValorCampoAnterior(getValorCampoAnterior() + nomeCampo + ": " + fieldPrincipal.get(valorObjeto).toString());
                    } else {
                        setValorCampoAlterado(getValorCampoAlterado() + nomeCampo + ": " + fieldPrincipal.get(valorObjeto).toString());
                    }
                }
                break;
            }
        }
    }

    private String obterDescricaoObjetoChaveEstrangeira(Object valorObjeto) throws IllegalAccessException {
        Field[] fieldsBeanPrincipal = valorObjeto.getClass().getDeclaredFields();
        for (Field fieldPrincipal : fieldsBeanPrincipal) {
            fieldPrincipal.setAccessible(true);
            if (fieldPrincipal.isAnnotationPresent(DescricaoObjeto.class)) {
                if ((fieldPrincipal.get(valorObjeto)) == null) {
                    break;
                }
                if (fieldPrincipal.isAnnotationPresent(ChaveEstrangeira.class)) {
                    return obterChaveEstrangeiraObjeto(fieldPrincipal.get(valorObjeto)) + "-" + obterDescricaoObjeto(fieldPrincipal.get(valorObjeto));
                }
                break;
            }
        }
        return "";
    }

    private String obterDescricaoObjeto(Object valorObjeto) throws IllegalAccessException {
        Field[] fieldsBeanPrincipal = valorObjeto.getClass().getDeclaredFields();
        for (Field fieldPrincipal : fieldsBeanPrincipal) {
            fieldPrincipal.setAccessible(true);
            if (fieldPrincipal.isAnnotationPresent(DescricaoObjeto.class)) {
                if ((fieldPrincipal.get(valorObjeto)) == null) {
                    break;
                }
                return fieldPrincipal.get(valorObjeto).toString();
            }
        }
        return "";
    }

    private Integer obterChaveEstrangeiraObjeto(Object valorObjeto) throws IllegalAccessException {
        Field[] fieldsBeanPrincipal = valorObjeto.getClass().getDeclaredFields();
        for (Field fieldPrincipal : fieldsBeanPrincipal) {
            fieldPrincipal.setAccessible(true);
            if (fieldPrincipal.isAnnotationPresent(ChavePrimaria.class)) {
                if ((fieldPrincipal.get(valorObjeto)) == null) {
                    break;
                }
                try {
                    return Integer.parseInt(fieldPrincipal.get(valorObjeto).toString());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    public void validarCadastrosExclusao(Object objetoExcluido) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, Exception {

        adquirirNomeEntidadeChavePrimariaResponsavelAlteracao(objetoExcluido, "");

        getLog().setNomeEntidade(getNomeEntidade());
        getLog().setChavePrimaria(getChavePrimaria());
        getLog().setChavePrimariaEntidadeSubordinada("");
        getLog().setNomeCampo("");
        getLog().setValorCampoAnterior("");
        getLog().setValorCampoAlterado("");
        getLog().setDataAlteracao(new Date());
        getLog().setResponsavelAlteracao(loginControle.getUsuarioLogado().getNome());
        getLog().setOperacao("EXCLUSÃO");
        getLogDeAlteracoes().add(getLog());
        setLog(new LogVO());
    }

    protected FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }

    /**
     * @return the logDeAlteracoes
     */
    public List getLogDeAlteracoes() {
        if (logDeAlteracoes == null) {
            logDeAlteracoes = new ArrayList(0);
        }
        return logDeAlteracoes;
    }

    /**
     * @param logDeAlteracoes the logDeAlteracoes to set
     */
    public void setLogDeAlteracoes(List logDeAlteracoes) {
        this.logDeAlteracoes = logDeAlteracoes;
    }

    /**
     * @return the log
     */
    public LogVO getLog() {
        if (log == null) {
            log = new LogVO();
        }
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(LogVO log) {
        this.log = log;
    }

    /**
     * @return the nomeEntidade
     */
    public String getNomeEntidade() {
        if (nomeEntidade == null) {
            nomeEntidade = "";
        }
        return nomeEntidade;
    }

    /**
     * @param nomeEntidade the nomeEntidade to set
     */
    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    /**
     * @return the chavePrimaria
     */
    public String getChavePrimaria() {
        if (chavePrimaria == null) {
            chavePrimaria = "";
        }
        return chavePrimaria;
    }

    /**
     * @param chavePrimaria the chavePrimaria to set
     */
    public void setChavePrimaria(String chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }

    /**
     * @return the responsavel
     */
    public String getResponsavel() {
        if (responsavel == null) {
            responsavel = "";
        }
        return responsavel;
    }

    /**
     * @param responsavel the responsavel to set
     */
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    /**
     * @return the chavePrimariaDoObjetoAnteriorChaveEstrangeira
     */
    public String getChavePrimariaDoObjetoAnteriorChaveEstrangeira() {
        if (chavePrimariaDoObjetoAnteriorChaveEstrangeira == null) {
            chavePrimariaDoObjetoAnteriorChaveEstrangeira = "";
        }
        return chavePrimariaDoObjetoAnteriorChaveEstrangeira;
    }

    /**
     * @param chavePrimariaDoObjetoAnteriorChaveEstrangeira the chavePrimariaDoObjetoAnteriorChaveEstrangeira to set
     */
    public void setChavePrimariaDoObjetoAnteriorChaveEstrangeira(String chavePrimariaDoObjetoAnteriorChaveEstrangeira) {
        this.chavePrimariaDoObjetoAnteriorChaveEstrangeira = chavePrimariaDoObjetoAnteriorChaveEstrangeira;
    }

    /**
     * @return the chavePrimariaDoObjetoAlteradoChaveEstrangeira
     */
    public String getChavePrimariaDoObjetoAlteradoChaveEstrangeira() {
        if (chavePrimariaDoObjetoAlteradoChaveEstrangeira == null) {
            chavePrimariaDoObjetoAlteradoChaveEstrangeira = "";
        }
        return chavePrimariaDoObjetoAlteradoChaveEstrangeira;
    }

    /**
     * @param chavePrimariaDoObjetoAlteradoChaveEstrangeira the chavePrimariaDoObjetoAlteradoChaveEstrangeira to set
     */
    public void setChavePrimariaDoObjetoAlteradoChaveEstrangeira(String chavePrimariaDoObjetoAlteradoChaveEstrangeira) {
        this.chavePrimariaDoObjetoAlteradoChaveEstrangeira = chavePrimariaDoObjetoAlteradoChaveEstrangeira;
    }

    /**
     * @return the nomeEntidadeAuxiliar
     */
    public String getNomeEntidadeDescricao() {
        return nomeEntidadeDescricao;
    }

    /**
     * @param nomeEntidadeAuxiliar the nomeEntidadeAuxiliar to set
     */
    public void setNomeEntidadeDescricao(String nomeEntidadeDescricao) {
        this.nomeEntidadeDescricao = nomeEntidadeDescricao;
    }

    public String getValorCampoAlterado() {
        if (valorCampoAlterado == null) {
            valorCampoAlterado = "";
        }
        return valorCampoAlterado;
    }

    public void setValorCampoAlterado(String valorCampoAlterado) {
        this.valorCampoAlterado = valorCampoAlterado;

    }

    public String getValorCampoAnterior() {
        if (valorCampoAnterior == null) {
            valorCampoAnterior = "";
        }
        return valorCampoAnterior;
    }

    public void setValorCampoAnterior(String valorCampoAnterior) {
        this.valorCampoAnterior = valorCampoAnterior;
    }
}
