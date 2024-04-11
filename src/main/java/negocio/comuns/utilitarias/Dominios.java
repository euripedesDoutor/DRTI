package negocio.comuns.utilitarias;

import java.util.Hashtable;

/**
 * Classe estática responsável por manter os domínios de valores utilizados pela aplicação.
 * Um domínio define um conjunto de valores que um determinado atributo pode assumir.
 * Exemplo de domínios são: Estados Federativos; Tipos de Sexo; Estado Civil.
 * Ao alterar um domínio neste classe, o valor modificado estará automaticamente disponível em toda a aplicação.
 */
public abstract class Dominios {

    public static Hashtable getPeriodo_relatorio() {
        return contabil_Periodo_relatorio;
    }

    public static void setPeriodo_relatorio(Hashtable aPeriodo_Relatorio) {
        contabil_Periodo_relatorio = aPeriodo_Relatorio;
    }

    public static Hashtable getPeriodo_TipoRelatorio_relatorio() {
        return contabil_Periodo_TipoRelatorio_relatorio;
    }

    public static void setPeriodo_TipoRelatorio_relatorio(Hashtable aPeriodo_TipoRelatorio_Relatorio) {
        contabil_Periodo_TipoRelatorio_relatorio = aPeriodo_TipoRelatorio_Relatorio;
    }

    public static Hashtable getLoteProducao_situacao() {
        return loteProducao_situacao;
    }

    public static void setLoteProducao_situacao(Hashtable aLoteProducao_situacao) {
        loteProducao_situacao = aLoteProducao_situacao;
    }

    public static Hashtable getCompra_PedidoCompra_Situacao() {
        return compra_PedidoCompra_Situacao;
    }

    public static void setCompra_PedidoCompra_Situacao(Hashtable aCompra_PedidoCompra_Situacao) {
        compra_PedidoCompra_Situacao = aCompra_PedidoCompra_Situacao;
    }

    public static Hashtable getRequisicaoPagamento_situacao() {
        return requisicaoPagamento_situacao;
    }

    public static void setRequisicaoPagamento_situacao(Hashtable aRequisicaoPagamento_Situacao) {
        requisicaoPagamento_situacao = aRequisicaoPagamento_Situacao;
    }

    protected static Hashtable situacaoOrdemServico = new Hashtable();
    protected static Hashtable entradaSaida = new Hashtable();
    protected static Hashtable situacaoCotacao = new Hashtable();
    protected static Hashtable situacaoRequisicaoCompra = new Hashtable();
    protected static Hashtable situacaoRequisicaoEntregaCompra = new Hashtable();
    protected static Hashtable comprasSituacaoCompraConsumo = new Hashtable();
    protected static Hashtable comprasSituacaoCompra = new Hashtable();
    protected static Hashtable contabil_Periodo_TipoRelatorio_relatorio = new Hashtable();
    protected static Hashtable contabil_Periodo_relatorio = new Hashtable();
    protected static Hashtable contabil_ControleCobranca_situacao = new Hashtable();
    protected static Hashtable contabil_ChequePreDatado_situacao = new Hashtable();
    protected static Hashtable compra_PedidoCompra_Situacao = new Hashtable();
    protected static Hashtable compra_Taxa_tipoTaxa = new Hashtable();
    protected static Hashtable compra_Produto_tipoProduto = new Hashtable();
    protected static Hashtable compra_NotaFiscalCompra_frete = new Hashtable();
    protected static Hashtable compra_NotaFiscalCompra_tipoNotaFiscal = new Hashtable();
    protected static Hashtable compra_LocalEstoque_tipoMovimentacao = new Hashtable();
    protected static Hashtable compra_DetalhamentoRomaneioAbate_tipoPesagem = new Hashtable();
    protected static Hashtable compra_CompraAnimais_tipoEntrada = new Hashtable();
    protected static Hashtable compra_Compra_tipoEntrada = new Hashtable();
    protected static Hashtable compra_ClassificacaoItemCompraAnimais_tipoComissao = new Hashtable();
    protected static Hashtable cadastro_TabelaCFOP_entradaSaida = new Hashtable();
    protected static Hashtable cadastro_PessoaEmpresa_tipoPessoa = new Hashtable();
    protected static Hashtable contabil_contaReceber_ContaPagar_situacao = new Hashtable();
    protected static Hashtable cadastro_FormaPagamento_tipoEntrada = new Hashtable();
    protected static Hashtable cadastro_Comprador_tipoComissao = new Hashtable();
    protected static Hashtable nivelAcesso = new Hashtable();
    protected static Hashtable estadoCivil = new Hashtable();
    protected static Hashtable venda_TabelaDesconto_calculo = new Hashtable();
    protected static Hashtable escolaridade = new Hashtable();
    protected static Hashtable venda_TabelaDesconto_tipoDesconto = new Hashtable();
    protected static Hashtable simNao = new Hashtable();
    protected static Hashtable baseadoEm = new Hashtable();
    protected static Hashtable loteProducao_situacao = new Hashtable();
    protected static Hashtable venda_PedidoVenda_situacaoLiberacao = new Hashtable();
    protected static Hashtable estado = new Hashtable();
    protected static Hashtable venda_PedidoVenda_atendimento = new Hashtable();
    protected static Hashtable sexo = new Hashtable();
    protected static Hashtable venda_NotaFiscalSaida_tipoFrete = new Hashtable();
    protected static Hashtable venda_NotaFiscalSaida_situacaoNotaFiscal = new Hashtable();
    protected static Hashtable requisicaoPagamento_situacao = new Hashtable();
    protected static Hashtable filtroRelatorioChequePreDatado = new Hashtable();
    protected static Hashtable ordenacaoRelatorioPedidoCompras = new Hashtable();
    protected static Hashtable tipoRelatorioPedidoCompras = new Hashtable();


    static {
        inicializarDominioEntradaSaida();
        inicializarDominioComprasSituacaoCompraConsumo();
        inicializarDominioComprasSituacaoCompra();
        inicializarPeriodo_TipoRelatorio_relatorio();
        inicializarPeriodo_relatorio();
        inicializarDominioContabil_ControleCobranca_situacao();
        inicializarDominioCompra_PedidoCompra_Situacao();
        inicializarDominioCompra_Taxa_tipoTaxa();
        inicializarDominioCompra_Produto_tipoProduto();
        inicializarDominioCompra_NotaFiscalCompra_frete();
        inicializarDominioCompra_NotaFiscalCompra_tipoNotaFiscal();
        inicializarDominioCompra_LocalEstoque_tipoMovimentacao();
        inicializarDominioCompra_DetalhamentoRomaneioAbate_tipoPesagem();
        inicializarDominioCompra_CompraAnimais_tipoEntrada();
        inicializarDominioCompra_Compra_tipoEntrada();
        inicializarDominioCompra_ClassificacaoItemCompraAnimais_tipoComissao();
        inicializarDominioCadastro_TabelaCFOP_entradaSaida();
        inicializarDominioCadastro_PessoaEmpresa_tipoPessoa();
        inicializarDominioCadastro_FormaPagamento_tipoEntrada();
        inicializarDominioCadastro_Comprador_tipoComissao();
        inicializarDominioNivelAcesso();
        inicializarDominioEstadoCivil();
        inicializarDominioContabil_contaReceber_ContaPagar_situacao();
        inicializarDominioContabil_ChequePreDatado_situacao();
        inicializarDominioVenda_TabelaDesconto_calculo();
        inicializarDominioEscolaridade();
        inicializarDominioVenda_TabelaDesconto_tipoDesconto();
        inicializarDominioSimNao();
        inicializarDominioBaseadoEm();
        inicializarDominioLoteProducao_Situacao();
        inicializarDominioVenda_PedidoVenda_situacaoLiberacao();
        inicializarDominioEstado();
        inicializarDominioVenda_PedidoVenda_atendimento();
        inicializarDominioSexo();
        inicializarDominioVenda_NotaFiscalSaida_tipoFrete();
        inicializarDominioVenda_NotaFiscalSaida_situacaoNotaFiscal();
        inicializarRequisicaoPagamento_situacao();
        inicializarDominiofiltroRelatorioChequePreDatado();
        inicializarDominioSituacaoRequisicaoCompra();
        inicializarDominioSituacaoRequisicaoEntregaCompra();
        inicializarDominioSituacaoCotacao();
        inicializarDominioOrdenacaoRelatorioPedidoCompras();
        inicializarDominioTipoRelatorioPedidoCompras();
        inicializarDominioSituacaoOrdemServico();

    }

    private static void inicializarDominioSituacaoOrdemServico() {
        getSituacaoOrdemServico().put("FI","Finalizado");
        getSituacaoOrdemServico().put("CA","Cancelado");
        getSituacaoOrdemServico().put("EX","Executado");
        getSituacaoOrdemServico().put("AB","Aberto");
    }
    private static void inicializarDominioEntradaSaida() {
        getEntradaSaida().put("SA", "Saída");
        getEntradaSaida().put("EN", "Entrada");
    }

    public static void inicializarPeriodo_relatorio() {

        getPeriodo_relatorio().put("DE", "Data Emissão");
        getPeriodo_relatorio().put("DV", "Data Vencimento");
    }

    public static void inicializarPeriodo_TipoRelatorio_relatorio() {
        getPeriodo_TipoRelatorio_relatorio().put("PE", "Período");
        getPeriodo_TipoRelatorio_relatorio().put("CO", "Conta");
        getPeriodo_TipoRelatorio_relatorio().put("FO", "Fornecedor");
    }

    public static void inicializarRequisicaoPagamento_situacao() {
        getRequisicaoPagamento_situacao().put("LI", "Liberado");
        getRequisicaoPagamento_situacao().put("CA", "Cancelado");
        getRequisicaoPagamento_situacao().put("EA", "Em Aberto");
    }

    public static void inicializarContasReceber_situacao() {
        getRequisicaoPagamento_situacao().put("JU", "Juridico");
        getRequisicaoPagamento_situacao().put("LI", "Liquidado");
        getRequisicaoPagamento_situacao().put("EP", "Em Aberto Parcial ");
        getRequisicaoPagamento_situacao().put("EA", "Em Aberto");
    }

    private static void inicializarDominioVenda_NotaFiscalSaida_situacaoNotaFiscal() {
        getVenda_NotaFiscalSaida_situacaoNotaFiscal().put("CA", "Cancelado");
    }

    private static void inicializarDominioVenda_NotaFiscalSaida_tipoFrete() {
        getVenda_NotaFiscalSaida_tipoFrete().put("2", "Destinatário");
        getVenda_NotaFiscalSaida_tipoFrete().put("1", "Emitente");
    }

    private static void inicializarDominioSexo() {
        getSexo().put("F", "Feminino");
        getSexo().put("M", "Masculino");
    }

    private static void inicializarDominioVenda_PedidoVenda_atendimento() {
        getVenda_PedidoVenda_atendimento().put("AT", "Ativo");
        getVenda_PedidoVenda_atendimento().put("RE", "Receptivo");
    }

    private static void inicializarDominioEstado() {
        getEstado().put("BA", "BA");
        getEstado().put("RS", "RS");
        getEstado().put("RR", "RR");
        getEstado().put("RO", "RO");
        getEstado().put("RN", "RN");
        getEstado().put("RJ", "RJ");
        getEstado().put("CE", "CE");
        getEstado().put("AP", "AP");
        getEstado().put("MT", "MT");
        getEstado().put("MS", "MS");
        getEstado().put("PR", "PR");
        getEstado().put("GO", "GO");
        getEstado().put("AM", "AM");
        getEstado().put("AL", "AL");
        getEstado().put("SP", "SP");
        getEstado().put("DF", "DF");
        getEstado().put("PI", "PI");
        getEstado().put("AC", "AC");
        getEstado().put("MG", "MG");
        getEstado().put("ES", "ES");
        getEstado().put("PE", "PE");
        getEstado().put("SE", "SE");
        getEstado().put("SC", "SC");
        getEstado().put("MA", "MA");
        getEstado().put("PB", "PB");
        getEstado().put("PA", "PA");
        getEstado().put("TO", "TO");
        getEstado().put("EX", "EX");
    }

    private static void inicializarDominioVenda_PedidoVenda_situacaoLiberacao() {
        getVenda_PedidoVenda_situacaoLiberacao().put("BL", "Bloqueado");
        getVenda_PedidoVenda_situacaoLiberacao().put("LI", "Liberado");
        getVenda_PedidoVenda_situacaoLiberacao().put("VE", "Venda Efetuada");
    }

    private static void inicializarDominioContabil_ChequePreDatado_situacao() {
        getContabil_ChequePreDatado_situacao().put("C", "Cheque Cofre");
        getContabil_ChequePreDatado_situacao().put("B", "Cheque Baixado");
    }

    private static void inicializarDominioSimNao() {
        getSimNao().put("S", "Sim");
        getSimNao().put("N", "Não");
    }

    private static void inicializarDominioBaseadoEm() {
        getBaseadoEm().put("VB", "Venda Balcão");
        getBaseadoEm().put("NF", "Nota Fiscal");
        getBaseadoEm().put("CA", "Carregamento");
        getBaseadoEm().put("PV", "Pedido de Venda");
    }

    private static void inicializarDominioLoteProducao_Situacao() {
        getLoteProducao_situacao().put("BP", "Bloqueado para Produção");
        getLoteProducao_situacao().put("LP", "Liberado para Produção");
    }

    private static void inicializarDominioVenda_TabelaDesconto_tipoDesconto() {
        getVenda_TabelaDesconto_tipoDesconto().put("DE", "Débito");
        getVenda_TabelaDesconto_tipoDesconto().put("CR", "Crédito");
    }

    private static void inicializarDominioEscolaridade() {
        getEscolaridade().put("PR", "Primário");
        getEscolaridade().put("BA", "Básico");
        getEscolaridade().put("DO", "Doutorado");
        getEscolaridade().put("PD", "Pós-Doutorado");
        getEscolaridade().put("SU", "Superior");
        getEscolaridade().put("PL", "Pós-Graduação Lato-Senso (MBA)");
        getEscolaridade().put("ME", "Mestrado");
    }

    private static void inicializarDominioVenda_TabelaDesconto_calculo() {
        getVenda_TabelaDesconto_calculo().put("AU", "Automático");
        getVenda_TabelaDesconto_calculo().put("IN", "Informado");
        getVenda_TabelaDesconto_calculo().put("FU", "Funrural");
        getVenda_TabelaDesconto_calculo().put("SE", "Senar");
    }

    private static void inicializarDominioEstadoCivil() {
        getEstadoCivil().put("S", "Solteiro");
        getEstadoCivil().put("C", "Casado");
    }

    private static void inicializarDominioNivelAcesso() {
        getNivelAcesso().put("(0)(9)(1)", "Incluir e Consultar");
        getNivelAcesso().put("(0)", "Consultar");
        getNivelAcesso().put("(12)", "Relatorio");
        getNivelAcesso().put("(2)", "Alterar");
        getNivelAcesso().put("(0)(1)(2)(9)(12)", "Total (Sem Excluir)");
        getNivelAcesso().put("(0)(1)(2)(3)(9)(12)", "Total");
        getNivelAcesso().put("(3)", "Excluir");
        getNivelAcesso().put("(1)(9)", "Incluir");
    }

    private static void inicializarDominioCadastro_Comprador_tipoComissao() {
        getCadastro_Comprador_tipoComissao().put("CA", "Por Cabeça");
        getCadastro_Comprador_tipoComissao().put("PO", "Porcentagem");
    }

    private static void inicializarDominioCadastro_FormaPagamento_tipoEntrada() {
        getCadastro_FormaPagamento_tipoEntrada().put("PO", "Porcentagem (%)");
        getCadastro_FormaPagamento_tipoEntrada().put("VE", "Valor Específico");
    }

    private static void inicializarDominioCadastro_PessoaEmpresa_tipoPessoa() {
        getCadastro_PessoaEmpresa_tipoPessoa().put("FI", "Física");
        getCadastro_PessoaEmpresa_tipoPessoa().put("JU", "Jurídica");
    }

    private static void inicializarDominioCadastro_TabelaCFOP_entradaSaida() {
        getCadastro_TabelaCFOP_entradaSaida().put("SA", "Saída");
        getCadastro_TabelaCFOP_entradaSaida().put("EN", "Entrada");
    }

    private static void inicializarDominioCompra_ClassificacaoItemCompraAnimais_tipoComissao() {
        getCompra_ClassificacaoItemCompraAnimais_tipoComissao().put("CA", "Por Cabeça");
        getCompra_ClassificacaoItemCompraAnimais_tipoComissao().put("VL", "Por Valor");
    }

    private static void inicializarDominioCompra_Compra_tipoEntrada() {
        getCompra_Compra_tipoEntrada().put("PO", "Porcentagem (%)");
        getCompra_Compra_tipoEntrada().put("VE", "Valor Específico");
    }

    private static void inicializarDominioCompra_CompraAnimais_tipoEntrada() {
        getCompra_CompraAnimais_tipoEntrada().put("PO", "Porcentagem (%)");
        getCompra_CompraAnimais_tipoEntrada().put("VE", "Valor Específico");
    }

    private static void inicializarDominioCompra_DetalhamentoRomaneioAbate_tipoPesagem() {
        getCompra_DetalhamentoRomaneioAbate_tipoPesagem().put("BA", "Por Banda");
        getCompra_DetalhamentoRomaneioAbate_tipoPesagem().put("PT", "Por Peso Total");
    }

    private static void inicializarDominioCompra_LocalEstoque_tipoMovimentacao() {
        getCompra_LocalEstoque_tipoMovimentacao().put("SA", "Saída");
        getCompra_LocalEstoque_tipoMovimentacao().put("EN", "Entrada");
    }

    private static void inicializarDominioCompra_NotaFiscalCompra_frete() {
        getCompra_NotaFiscalCompra_frete().put("RE", "0-Contratação do Frete por conta do Remetente (CIF)");
        getCompra_NotaFiscalCompra_frete().put("DE", "1-Contratação do Frete por conta do Destinatário (FOB)");
        getCompra_NotaFiscalCompra_frete().put("TE", "2-Contratação do Frete por conta de Terceiros");
        getCompra_NotaFiscalCompra_frete().put("PR", "3-Transporte Próprio por conta do Remetente");
        getCompra_NotaFiscalCompra_frete().put("PD", "4-Transporte Próprio por conta do Destinatário");
        getCompra_NotaFiscalCompra_frete().put("SF", "9-Sem Ocorrência de Transporte");
    }

    private static void inicializarDominioCompra_NotaFiscalCompra_tipoNotaFiscal() {
        getCompra_NotaFiscalCompra_tipoNotaFiscal().put("1", "Saída - 1");
        getCompra_NotaFiscalCompra_tipoNotaFiscal().put("0", "Entrada - 0");
    }

    private static void inicializarDominioCompra_Produto_tipoProduto() {
        getCompra_Produto_tipoProduto().put("00", "00-Mercadoria para Revenda");
        getCompra_Produto_tipoProduto().put("01", "01-Matéria-prima");
        getCompra_Produto_tipoProduto().put("02", "02-Embalagem");
        getCompra_Produto_tipoProduto().put("03", "03-Produto em Processo");
        getCompra_Produto_tipoProduto().put("04", "04-Produto Acabado");
        getCompra_Produto_tipoProduto().put("05", "05-Subproduto");
        getCompra_Produto_tipoProduto().put("06", "06-Produto Intermediário");
        getCompra_Produto_tipoProduto().put("07", "07-Material de Uso e Consumo");
        getCompra_Produto_tipoProduto().put("08", "08-Ativo Imobilizado");
        getCompra_Produto_tipoProduto().put("09", "09-Serviços");
        getCompra_Produto_tipoProduto().put("10", "10-Outros Insumos");
        getCompra_Produto_tipoProduto().put("99", "99-Outras");
    }

    private static void inicializarDominioCompra_PedidoCompra_Situacao() {
        getCompra_PedidoCompra_Situacao().put("BL", "Bloqueado");
        getCompra_PedidoCompra_Situacao().put("LI", "Liberado");
        getCompra_PedidoCompra_Situacao().put("CO", "Comprado");
        getCompra_PedidoCompra_Situacao().put("CA", "Cancelado");
    }

    private static void inicializarDominioCompra_Taxa_tipoTaxa() {
        getCompra_Taxa_tipoTaxa().put("DE", "Débito");
        getCompra_Taxa_tipoTaxa().put("CR", "Crédito");
    }

    private static void inicializarDominioContabil_ControleCobranca_situacao() {
        getContabil_ControleCobranca_situacao().put("ER", "Efetuar o Recebimento");
        getContabil_ControleCobranca_situacao().put("NC", "Realizar Novo Contato");
        getContabil_ControleCobranca_situacao().put("NL", "Cliente Não Localizado");
        getContabil_ControleCobranca_situacao().put("EJ", "Enviar para Jurídico");
    }

    private static void inicializarDominioContabil_contaReceber_ContaPagar_situacao() {
        getContabil_contaReceber_ContaPagar_situacao().put("PG", "EM ABERTO");
//        getContabil_contaReceber_ContaPagar_situacao().put("PG", "PAGO");
//        getContabil_contaReceber_ContaPagar_situacao().put("AP", "A PAGAR");
//        getContabil_contaReceber_ContaPagar_situacao().put("NE", "NEGOCIADO");
    }

    private static void inicializarDominiofiltroRelatorioChequePreDatado() {
        getFiltroRelatorioChequePreDatado().put("DD", "Data de Deposito");
        getFiltroRelatorioChequePreDatado().put("DV", "Data de Vencimento");
        getFiltroRelatorioChequePreDatado().put("DE", "Data de Emissao");
        getFiltroRelatorioChequePreDatado().put("CL", "Cliente");
        getFiltroRelatorioChequePreDatado().put("FO", "Fornecedor");
        getFiltroRelatorioChequePreDatado().put("SI", "Situação");

    }

    private static void inicializarDominioComprasSituacaoCompraConsumo() {
        getComprasSituacaoCompraConsumo().put("EP", "Entrega Parcial");
        getComprasSituacaoCompraConsumo().put("AE", "Aguardando Entrega");
        getComprasSituacaoCompraConsumo().put("CO", "Concluída");
    }

    private static void inicializarDominioComprasSituacaoCompra() {
        getComprasSituacaoCompra().put("EP", "Entrega Parcial");
        getComprasSituacaoCompra().put("AE", "Aguardando Entrega");
        getComprasSituacaoCompra().put("CO", "Concluída");
        getComprasSituacaoCompra().put("CA", "Cancelada");
    }

    private static void inicializarDominioSituacaoRequisicaoCompra() {
        getSituacaoRequisicaoCompra().put("DE", "Deferido");
        getSituacaoRequisicaoCompra().put("IN", "Indeferido");
        getSituacaoRequisicaoCompra().put("AD", "Aguardando Deferimento");
    }

    private static void inicializarDominioSituacaoRequisicaoEntregaCompra() {
        getSituacaoRequisicaoEntregaCompra().put("PE", "Pendente");
        getSituacaoRequisicaoEntregaCompra().put("EN", "Entrege");
    }

    private static void inicializarDominioSituacaoCotacao() {
        getSituacaoCotacao().put("AU", "Autorizada");
        getSituacaoCotacao().put("EA", "Em Aberto");
        getSituacaoCotacao().put("CR", "Compra Realizada");
    }

    private static void inicializarDominioOrdenacaoRelatorioPedidoCompras() {
        getOrdenacaoRelatorioPedidoCompras().put("DA", "Data");
        getOrdenacaoRelatorioPedidoCompras().put("NO", "Nome");
    }

    private static void inicializarDominioTipoRelatorioPedidoCompras() {
        getTipoRelatorioPedidoCompras().put("RE", "Resumo");
        getTipoRelatorioPedidoCompras().put("CO", "Completo");
    }

    public static void setVenda_NotaFiscalSaida_situacaoNotaFiscal(Hashtable venda_NotaFiscalSaida_situacaoNotaFiscalPrm) {
        venda_NotaFiscalSaida_situacaoNotaFiscal = venda_NotaFiscalSaida_situacaoNotaFiscalPrm;
    }

    public static Hashtable getVenda_NotaFiscalSaida_situacaoNotaFiscal() {
        return venda_NotaFiscalSaida_situacaoNotaFiscal;
    }

    public static void setVenda_NotaFiscalSaida_tipoFrete(Hashtable venda_NotaFiscalSaida_tipoFretePrm) {
        venda_NotaFiscalSaida_tipoFrete = venda_NotaFiscalSaida_tipoFretePrm;
    }

    public static Hashtable getVenda_NotaFiscalSaida_tipoFrete() {
        return venda_NotaFiscalSaida_tipoFrete;
    }

    public static void setSexo(Hashtable sexoPrm) {
        sexo = sexoPrm;
    }

    public static Hashtable getSexo() {
        return sexo;
    }

    public static void setVenda_PedidoVenda_atendimento(Hashtable venda_PedidoVenda_atendimentoPrm) {
        venda_PedidoVenda_atendimento = venda_PedidoVenda_atendimentoPrm;
    }

    public static Hashtable getVenda_PedidoVenda_atendimento() {
        return venda_PedidoVenda_atendimento;
    }

    public static void setEstado(Hashtable estadoPrm) {
        estado = estadoPrm;
    }

    public static Hashtable getEstado() {
        return estado;
    }

    public static void setVenda_PedidoVenda_situacaoLiberacao(Hashtable venda_PedidoVenda_situacaoLiberacaoPrm) {
        venda_PedidoVenda_situacaoLiberacao = venda_PedidoVenda_situacaoLiberacaoPrm;
    }

    public static Hashtable getVenda_PedidoVenda_situacaoLiberacao() {
        return venda_PedidoVenda_situacaoLiberacao;
    }

    public static void setSimNao(Hashtable simNaoPrm) {
        simNao = simNaoPrm;
    }

    public static Hashtable getSimNao() {
        return simNao;
    }

    public static void setVenda_TabelaDesconto_tipoDesconto(Hashtable venda_TabelaDesconto_tipoDescontoPrm) {
        venda_TabelaDesconto_tipoDesconto = venda_TabelaDesconto_tipoDescontoPrm;
    }

    public static Hashtable getVenda_TabelaDesconto_tipoDesconto() {
        return venda_TabelaDesconto_tipoDesconto;
    }

    public static void setEscolaridade(Hashtable escolaridadePrm) {
        escolaridade = escolaridadePrm;
    }

    public static Hashtable getEscolaridade() {
        return escolaridade;
    }

    public static void setVenda_TabelaDesconto_calculo(Hashtable venda_TabelaDesconto_calculoPrm) {
        venda_TabelaDesconto_calculo = venda_TabelaDesconto_calculoPrm;
    }

    public static Hashtable getVenda_TabelaDesconto_calculo() {
        return venda_TabelaDesconto_calculo;
    }

    public static void setEstadoCivil(Hashtable estadoCivilPrm) {
        estadoCivil = estadoCivilPrm;
    }

    public static Hashtable getEstadoCivil() {
        return estadoCivil;
    }

    public static void setNivelAcesso(Hashtable nivelAcessoPrm) {
        nivelAcesso = nivelAcessoPrm;
    }

    public static Hashtable getNivelAcesso() {
        return nivelAcesso;
    }

    public static void setCadastro_Comprador_tipoComissao(Hashtable cadastro_Comprador_tipoComissaoPrm) {
        cadastro_Comprador_tipoComissao = cadastro_Comprador_tipoComissaoPrm;
    }

    public static Hashtable getCadastro_Comprador_tipoComissao() {
        return cadastro_Comprador_tipoComissao;
    }

    public static void setCadastro_FormaPagamento_tipoEntrada(Hashtable cadastro_FormaPagamento_tipoEntradaPrm) {
        cadastro_FormaPagamento_tipoEntrada = cadastro_FormaPagamento_tipoEntradaPrm;
    }

    public static Hashtable getCadastro_FormaPagamento_tipoEntrada() {
        return cadastro_FormaPagamento_tipoEntrada;
    }

    public static void setCadastro_PessoaEmpresa_tipoPessoa(Hashtable cadastro_PessoaEmpresa_tipoPessoaPrm) {
        cadastro_PessoaEmpresa_tipoPessoa = cadastro_PessoaEmpresa_tipoPessoaPrm;
    }

    public static Hashtable getCadastro_PessoaEmpresa_tipoPessoa() {
        return cadastro_PessoaEmpresa_tipoPessoa;
    }

    public static void setCadastro_TabelaCFOP_entradaSaida(Hashtable cadastro_TabelaCFOP_entradaSaidaPrm) {
        cadastro_TabelaCFOP_entradaSaida = cadastro_TabelaCFOP_entradaSaidaPrm;
    }

    public static Hashtable getCadastro_TabelaCFOP_entradaSaida() {
        return cadastro_TabelaCFOP_entradaSaida;
    }

    public static void setCompra_ClassificacaoItemCompraAnimais_tipoComissao(Hashtable compra_ClassificacaoItemCompraAnimais_tipoComissaoPrm) {
        compra_ClassificacaoItemCompraAnimais_tipoComissao = compra_ClassificacaoItemCompraAnimais_tipoComissaoPrm;
    }

    public static Hashtable getCompra_ClassificacaoItemCompraAnimais_tipoComissao() {
        return compra_ClassificacaoItemCompraAnimais_tipoComissao;
    }

    public static void setCompra_Compra_tipoEntrada(Hashtable compra_Compra_tipoEntradaPrm) {
        compra_Compra_tipoEntrada = compra_Compra_tipoEntradaPrm;
    }

    public static Hashtable getCompra_Compra_tipoEntrada() {
        return compra_Compra_tipoEntrada;
    }

    public static void setCompra_CompraAnimais_tipoEntrada(Hashtable compra_CompraAnimais_tipoEntradaPrm) {
        compra_CompraAnimais_tipoEntrada = compra_CompraAnimais_tipoEntradaPrm;
    }

    public static Hashtable getCompra_CompraAnimais_tipoEntrada() {
        return compra_CompraAnimais_tipoEntrada;
    }

    public static void setCompra_DetalhamentoRomaneioAbate_tipoPesagem(Hashtable compra_DetalhamentoRomaneioAbate_tipoPesagemPrm) {
        compra_DetalhamentoRomaneioAbate_tipoPesagem = compra_DetalhamentoRomaneioAbate_tipoPesagemPrm;
    }

    public static Hashtable getCompra_DetalhamentoRomaneioAbate_tipoPesagem() {
        return compra_DetalhamentoRomaneioAbate_tipoPesagem;
    }

    public static void setCompra_LocalEstoque_tipoMovimentacao(Hashtable compra_LocalEstoque_tipoMovimentacaoPrm) {
        compra_LocalEstoque_tipoMovimentacao = compra_LocalEstoque_tipoMovimentacaoPrm;
    }

    public static Hashtable getCompra_LocalEstoque_tipoMovimentacao() {
        return compra_LocalEstoque_tipoMovimentacao;
    }

    public static void setCompra_NotaFiscalCompra_frete(Hashtable compra_NotaFiscalCompra_fretePrm) {
        compra_NotaFiscalCompra_frete = compra_NotaFiscalCompra_fretePrm;
    }

    public static Hashtable getCompra_NotaFiscalCompra_frete() {
        return compra_NotaFiscalCompra_frete;
    }

    public static void setCompra_Produto_tipoProduto(Hashtable compra_Produto_tipoProdutoPrm) {
        compra_Produto_tipoProduto = compra_Produto_tipoProdutoPrm;
    }

    public static Hashtable getCompra_Produto_tipoProduto() {
        return compra_Produto_tipoProduto;
    }

    public static void setCompra_Taxa_tipoTaxa(Hashtable compra_Taxa_tipoTaxaPrm) {
        compra_Taxa_tipoTaxa = compra_Taxa_tipoTaxaPrm;
    }

    public static Hashtable getCompra_Taxa_tipoTaxa() {
        return compra_Taxa_tipoTaxa;
    }

    public static void setContabil_ControleCobranca_situacao(Hashtable contabil_ControleCobranca_situacaoPrm) {
        contabil_ControleCobranca_situacao = contabil_ControleCobranca_situacaoPrm;
    }

    public static Hashtable getContabil_ControleCobranca_situacao() {
        return contabil_ControleCobranca_situacao;
    }

    public static void setContabil_contaReceber_ContaPagar_situacao(Hashtable contabil_contaReceber_ContaPagar_situacaoPrm) {
        contabil_contaReceber_ContaPagar_situacao = contabil_contaReceber_ContaPagar_situacaoPrm;
    }

    public static Hashtable getContabil_contaReceber_ContaPagar_situacao() {
        return contabil_contaReceber_ContaPagar_situacao;
    }

    public static Hashtable getCompra_NotaFiscalCompra_tipoNotaFiscal() {
        return compra_NotaFiscalCompra_tipoNotaFiscal;
    }

    public static void setCompra_NotaFiscalCompra_tipoNotaFiscal(Hashtable aCompra_NotaFiscalCompra_tipoNotaFiscal) {
        compra_NotaFiscalCompra_tipoNotaFiscal = aCompra_NotaFiscalCompra_tipoNotaFiscal;
    }

    public static Hashtable getBaseadoEm() {
        return baseadoEm;
    }

    public static void setBaseadoEm(Hashtable aBaseadoEm) {
        baseadoEm = aBaseadoEm;
    }

    public static Hashtable getContabil_ChequePreDatado_situacao() {
        return contabil_ChequePreDatado_situacao;
    }

    public static void setContabil_ChequePreDatado_situacao(Hashtable aContabil_ChequePreDatado_situacao) {
        contabil_ChequePreDatado_situacao = aContabil_ChequePreDatado_situacao;
    }

    public static Hashtable getFiltroRelatorioChequePreDatado() {
        return filtroRelatorioChequePreDatado;
    }

    public static void setFiltroRelatorioChequePreDatado(Hashtable filtroRelatorioChequePreDatado) {
        Dominios.filtroRelatorioChequePreDatado = filtroRelatorioChequePreDatado;
    }

    public static Hashtable getComprasSituacaoCompraConsumo() {
        return comprasSituacaoCompraConsumo;
    }

    public static void setComprasSituacaoCompraConsumo(Hashtable comprasSituacaoCompraConsumo) {
        Dominios.comprasSituacaoCompraConsumo = comprasSituacaoCompraConsumo;
    }

    public static Hashtable getComprasSituacaoCompra() {
        return comprasSituacaoCompra;
    }

    public static void setComprasSituacaoCompra(Hashtable comprasSituacaoCompra) {
        Dominios.comprasSituacaoCompra = comprasSituacaoCompra;
    }

    public static Hashtable getSituacaoRequisicaoCompra() {
        return situacaoRequisicaoCompra;
    }

    public static void setSituacaoRequisicaoCompra(Hashtable situacaoRequisicaoCompra) {
        Dominios.situacaoRequisicaoCompra = situacaoRequisicaoCompra;
    }

    public static Hashtable getSituacaoCotacao() {
        return situacaoCotacao;
    }

    public static void setSituacaoCotacao(Hashtable situacaoCotacao) {
        Dominios.situacaoCotacao = situacaoCotacao;
    }

    public static Hashtable getSituacaoRequisicaoEntregaCompra() {
        return situacaoRequisicaoEntregaCompra;
    }

    public static void setSituacaoRequisicaoEntregaCompra(Hashtable situacaoRequisicaoEntregaCompra) {
        Dominios.situacaoRequisicaoEntregaCompra = situacaoRequisicaoEntregaCompra;
    }

    public static Hashtable getOrdenacaoRelatorioPedidoCompras() {
        return ordenacaoRelatorioPedidoCompras;
    }

    public static void setOrdenacaoRelatorioPedidoCompras(Hashtable ordenacaoRelatorioPedidoCompras) {
        Dominios.ordenacaoRelatorioPedidoCompras = ordenacaoRelatorioPedidoCompras;
    }

    public static Hashtable getTipoRelatorioPedidoCompras() {
        return tipoRelatorioPedidoCompras;
    }

    public static void setTipoRelatorioPedidoCompras(Hashtable tipoRelatorioPedidoCompras) {
        Dominios.tipoRelatorioPedidoCompras = tipoRelatorioPedidoCompras;
    }

    public static Hashtable getEntradaSaida() {
        return entradaSaida;
    }
    public static void setSituacaoOrdemServico( Hashtable situacaoOrdemServicoPrm ) {
        situacaoOrdemServico = situacaoOrdemServicoPrm;
    }

    public static Hashtable getSituacaoOrdemServico() {
        return situacaoOrdemServico;
    }

}
