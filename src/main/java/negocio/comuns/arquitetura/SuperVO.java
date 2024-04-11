package negocio.comuns.arquitetura;

import annotations.arquitetura.*;
import negocio.comuns.utilitarias.Uteis;
import controle.arquitetura.LoginControle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import negocio.comuns.cadastro.ConfiguracaoSistemaVO;
import negocio.facade.jdbc.cadastro.ConfiguracaoSistema;

/**
 * SuperClasse padrão para classes do tipo VO - Value Object (Design Pattern: Transfer Object).
 * Classe somente com atributos representando dados da entidade de negócio correspondente.
 * Implementa um controle simples para indicar se um objeto é novo (ainda não gravado no banco de dados)
 * ou, caso contrário, o objeto em questão já existe no banco de dados.
 * Permite mapear se um objeto deve ser incluído ou alterado no BD.
 */
public class SuperVO {

    protected Boolean novoObj;
    protected Object objetoVOAntesAlteracao;

    /** Creates a new instance of SuperDAO */
    public SuperVO() {
        setNovoObj(true);
        setObjetoVOAntesAlteracao(null);
    }

    /**
     * Responsável por registrar o objeto que está sendo alterado antes de sua alteração ou exclusão.
     * é necessário esse regitro para que possa ser possivel comparar o objeto no seu estado antes da edição e depois da edição
     * para ver em qual situação o mesmo se encontra. Essa ação é necessaria para geração do CONTROLE DE LOG.
     * @author Kelvin Cantarelli dos Santos
     *
     */
    public void registrarObjetoVOAntesDaAlteracao() {
        try {
            objetoVOAntesAlteracao = getClone();
        } catch (Exception e) {
            objetoVOAntesAlteracao = null;

        }
    }

    /**
     * Método responsável pela geração do Log (alteração) do Objeto referente.
     * Na classe GeradorLog será feito a validação para geração do Log.
     * @return List Retorna uma lista com os objetos do tipo LogVO.
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.IllegalAccessException
     */
    public List gerarLogAlteracaoObjetoVO() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, Exception {
        GeradorLog geradorLog = new GeradorLog();
        geradorLog.validarCamposDeclaradosComAnnotations(this, objetoVOAntesAlteracao, "", "", "");
        return geradorLog.getLogDeAlteracoes();
    }

    /**
     * Método responsável pela geração do Log (alteração) do Objeto referente.
     * Na classe GeradorLog será feito a validação para geração do Log.
     * @return List Retorna uma lista com os objetos do tipo LogVO.
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.IllegalArgumentException
     * @throws java.lang.IllegalAccessException
     */
    public List gerarLogExclusaoObjetoVO() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, Exception {
        GeradorLog geradorLog = new GeradorLog();
        geradorLog.validarCadastrosExclusao(this);
        return geradorLog.getLogDeAlteracoes();
    }

    public Object getObjetoVOAntesAlteracao() {
        return objetoVOAntesAlteracao;
    }

    public void setObjetoVOAntesAlteracao(Object objetoVOAntesAlteracao) {
        this.objetoVOAntesAlteracao = objetoVOAntesAlteracao;
    }

    /**
     * Método responsável por realizar o Clone do objeto antes que o mesmo seja alterado.
     * O clone de um objeto é realizado em três (3) etapas, são elas:
     * Os Fields que possuem annotation do tipo ChaveEstrangeira.
     * Os Fields que possuem annotation do tipo Lista.
     * Os demais Fields que não contém os annotations citados acima.
     * @author Kelvin Cantarelli dos Santos
     */
    public Object getClone() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Object objetoClonado = this;
        Object objeto = new Object();
        objeto = this.getClass().newInstance();
        if (!this.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
            Field[] fieldsPrincipalSuperClasse = this.getClass().getSuperclass().getDeclaredFields();
            objeto = gerarCloneObjeto(fieldsPrincipalSuperClasse, objeto, objetoClonado);
        }
        Field[] fieldsPrincipal = this.getClass().getDeclaredFields();
        objeto = gerarCloneObjeto(fieldsPrincipal, objeto, objetoClonado);

        return objeto;
    }

    public Object gerarCloneObjeto(Field[] fields, Object objeto, Object objetoClonado) throws IllegalArgumentException, IllegalArgumentException, IllegalArgumentException, IllegalAccessException, InstantiationException {

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(objetoClonado) == null) {
                continue;
            }
            //System.out.println(field.getType() + ": " + field.getName());
            if (field.isAnnotationPresent(ChaveEstrangeira.class)) {
                try {
//                    if (field.getName().equalsIgnoreCase("veiculo")) {
//                        System.out.println(field.getType() + ": " + field.getName());
//                    }

                    Object objetoChaveEstrangeira = field.get(objetoClonado).getClass().newInstance();
                    Field[] fieldsChaveEstrangeira = field.get(objetoClonado).getClass().getDeclaredFields();
                    Object objetoChaveEstrangeiraClonado = field.get(objetoClonado);

                    if (!field.get(objetoClonado).getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
                        fieldsChaveEstrangeira = field.get(objetoClonado).getClass().getSuperclass().getDeclaredFields();
                    }
                    objetoChaveEstrangeira = gerarCloneObjeto(fieldsChaveEstrangeira, objetoChaveEstrangeira, objetoChaveEstrangeiraClonado);
//                for (Field fieldChaveEstrangeira : fieldsChaveEstrangeira) {
//                    fieldChaveEstrangeira.setAccessible(true);
//                    fieldChaveEstrangeira.set(objetoChaveEstrangeira, fieldChaveEstrangeira.get(field.get(this)));
//                }
                    field.set(objeto, objetoChaveEstrangeira);
                } catch (Exception e) {
                }
            } else if (field.isAnnotationPresent(Lista.class)) {
                List listaClone = new ArrayList(0);
                List listaOriginal = (ArrayList) field.get(objetoClonado);
                Iterator i = listaOriginal.iterator();
                while (i.hasNext()) {
                    SuperVO obj = (SuperVO) i.next();
                    Object objetoLista = obj.getClass().newInstance();
                    Field[] fieldsLista = obj.getClass().getDeclaredFields();
                    if (!obj.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
                        fieldsLista = obj.getClass().getSuperclass().getDeclaredFields();
                    }
//                    for (Field fieldLista : fieldsLista) {
//                        fieldLista.setAccessible(true);
//                        fieldLista.set(objetoLista, fieldLista.get(obj));
//                    }

                    gerarCloneObjeto(fieldsLista, objetoLista, obj);
                    listaClone.add(objetoLista);
                }
                field.set(objeto, listaClone);
            } else {
                    field.set(objeto, field.get(objetoClonado));                
            }
        }

        return objeto;
    }

    public void realizarUpperCase(Object obj) throws IllegalAccessException, Exception {
//        if (!getConsultarRealizarUpperCase()) {
//            return;
//        }
        if (!obj.getClass().getSuperclass().getName().equals("java.lang.Object") && !obj.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
            Object objField = obj.getClass().getSuperclass().newInstance();
            realizarUpperCaseSuperClass(objField, obj);
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) != null && fieldString(field) && !field.isAnnotationPresent(NaoRealizarUpperCase.class)) {
                if(field.isAnnotationPresent(NaoRealizarTrimAutomatico.class)) {
                    field.set(obj, field.get(obj).toString().toUpperCase());
                }else {
                    field.set(obj, field.get(obj).toString().toUpperCase().trim());
                }
            }
        }
        //realizarRemocaoAcentos(obj);
    }

    public void realizarRemocaoAcentos(Object obj) throws IllegalAccessException, Exception {
        if (!obj.getClass().getSuperclass().getName().equals("java.lang.Object") && !obj.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
            Object objField = obj.getClass().getSuperclass().newInstance();
            realizarRemocaoAcentosSuperClass(objField, obj);
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) != null && fieldString(field)) {
                field.set(obj, Uteis.substituirCaracteresAcentuados(field.get(obj).toString()));
            }
        }
    }

    public void realizarRemocaoAcentosSuperClass(Object objField, Object objetoValor) throws IllegalAccessException, InstantiationException {
        if (!objField.getClass().getSuperclass().getName().equals("java.lang.Object") && !objField.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
            Object objField1 = objField.getClass().getSuperclass().newInstance();
            realizarUpperCaseSuperClass(objField1, objetoValor);
        }
        Field[] fields = objField.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(objetoValor) != null && fieldString(field)) {
                field.set(objetoValor, Uteis.substituirCaracteresAcentuados(field.get(objetoValor).toString()));
            }
        }

    }

    public void realizarUpperCaseSuperClass(Object objField, Object objetoValor) throws IllegalAccessException, InstantiationException {
        if (!objField.getClass().getSuperclass().getName().equals("java.lang.Object") && !objField.getClass().getSuperclass().getSimpleName().equals("SuperVO")) {
            Object objField1 = objField.getClass().getSuperclass().newInstance();
            realizarUpperCaseSuperClass(objField1, objetoValor);
        }
        Field[] fields = objField.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(objetoValor) != null && fieldString(field) && !field.isAnnotationPresent(NaoRealizarUpperCase.class)) {
                field.set(objetoValor, field.get(objetoValor).toString().toUpperCase());
            }
        }

    }

    public Boolean fieldString(Field field) {
        if (field.getType().getName().equals("java.lang.String")) {
            return true;
        }
        return false;
    }


    protected static FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }

    /**
     * Retorna um status indicando se o objeto é novo (ainda não foi gravado no BD) ou não.
     * @return boolean True indica que o objeto é novo e portanto ainda não foi gravado no BD.
     *                 False indica que o objeto já existe no BD e deve ser alterado.
     */
    public Boolean isNovoObj() {
        return novoObj;
    }

    public Boolean getNovoObj() {
        return novoObj;
    }

    /**
     * Define um status indicando se o objeto é novo (ainda não foi gravado no BD) ou não.
     * @param novoObj True indica que o objeto é novo e portanto ainda não foi gravado no BD.
     *                False indica que o objeto já existe no BD e deve ser alterado.
     */
    public void setNovoObj(Boolean novoObj) {
        this.novoObj = novoObj;
    }

    /*
     * @deprecated em 31/01/2008 - Utializar rotina em Uteis
     */
    public static String removerMascara(String campo) {
        if (campo == null) {
            return "";
        }
        return Uteis.removerMascaraCpf(campo);
    }

    /*
     * @deprecated em 31/01/2008 - Utializar rotina em Uteis
     */
    public static String aplicarMascara(String dado, String mascara) {
        if (dado == null) {
            return "";
        }
        return Uteis.aplicarMascara(dado, mascara);
    }

    public static boolean verificaCPF(String cpf) {
        cpf = removerMascara(cpf);

        if ((cpf.equals("00000000000"))
                || (cpf.equals("11111111111"))
                || (cpf.equals("22222222222"))
                || (cpf.equals("33333333333"))
                || (cpf.equals("44444444444"))
                || (cpf.equals("55555555555"))
                || (cpf.equals("66666666666"))
                || (cpf.equals("77777777777"))
                || (cpf.equals("88888888888"))
                || (cpf.equals("99999999999"))) {
            return false;
        }

        int count, Soma, x, y, CPF[] = new int[11];

        if (cpf.length() != 11) {
            return false;
        }

        Soma = 0;
        for (count = 0; count < 11; count++) {
            CPF[count] = 0;
        }

        char vetorChar[] = new char[11];
        String temp, CPFvalido = "";

        for (count = 0; count < 11; count++) {
            // Transformar String em vetor de caracteres
            vetorChar = cpf.toCharArray();
            // Transformar cada caractere em String
            temp = String.valueOf(vetorChar[count]);
            // Transformar String em inteiro e jogar no vetor
            CPF[count] = Integer.parseInt(temp);
        }
        // Método da árvore para obter o x
        for (count = 0; count < 9; count++) {
            // Pegar soma da permutação dos dígitos do CPF
            Soma += CPF[count] * (10 - count);
        }

        // se o resto da divisão der 0 ou 1, x terá dois dígitos
        if (Soma % 11 < 2) {
            x = 0;
        } // x não pode ter dois dígitos
        else {
            x = 11 - (Soma % 11);
        } // obtendo o penúltimo dígito do CPF

        CPF[9] = x;

        // Método da árvore para obter o y
        Soma = 0;
        for (count = 0; count < 10; count++) {
            // Pegar soma da permutação dos dígitos do CPF
            Soma += CPF[count] * (11 - count);
        }

        // se o resto da divisão der 0 ou 1, y terão dois dígitos
        if (Soma % 11 < 2) {
            y = 0;
        } // y não pode ter dois dígitos
        else {
            y = 11 - (Soma % 11);
        } // obtendo o último dígito do CPF

        CPF[10] = y;
        Soma = 0;

        // Verificando se o cpf informado é válido para retornar resultado ao programa
        for (count = 0; count < 11; count++) {
            CPFvalido += String.valueOf(CPF[count]);
        }
        if (cpf.compareTo(CPFvalido) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validaCNPJ(String cnpj) {
        boolean ret = false;
        cnpj = removerMascara(cnpj);
        String base = "00000000000000";
        if (cnpj != null && !cnpj.equals("")) {
            if (cnpj.length() <= 14) {
                if (cnpj.length() < 14) {
                    cnpj = base.substring(0, 14 - cnpj.length()) + cnpj;
                }
                int soma = 0;
                int dig = 0;
                String cnpj_calc = cnpj.substring(0, 12);
                char[] chr_cnpj = cnpj.toCharArray();
                // Primeira parte
                for (int i = 0; i < 4; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
                // Segunda parte
                soma = 0;
                for (int i = 0; i < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
                ret = cnpj.equals(cnpj_calc);
            }
        }
        return ret;
    }

    public Integer contarQuantidadeDePontos(String frase, String sub) {
        int cont = 0;
        for (int i = 0; i < (frase.length() - sub.length() + 1); i++) {
            String res = frase.substring(i, (i + sub.length()));
            if (res.equals(sub)) {
                cont++;
            }
        }
        return cont;
    }
}
