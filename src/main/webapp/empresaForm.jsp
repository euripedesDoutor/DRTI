<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <a4j:loadScript src="script/jquery.maskedinput-1.3.1.js" />
    <title>
        <h:outputText value="#{msg_aplic.prt_Empresa_tituloForm}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <rich:modalPanel id="panelCidade" autosized="true" shadowOpacity="true" width="550" height="300" onshow="document.getElementById('formCidade:consultaCidade').focus();">
            <f:facet name="header">
                <h:panelGroup>
                    <h:outputText value="#{msg_aplic.prt_Cliente_cidadeConsulta}"></h:outputText>
                </h:panelGroup>
            </f:facet>
            <f:facet name="controls">
                <h:panelGroup>
                    <h:graphicImage value="/imagens/close.png" style="cursor:pointer" id="hidelink1"/>
                    <rich:componentControl for="panelCidade" attachTo="hidelink1" operation="hide" event="onclick"/>
                </h:panelGroup>
            </f:facet>
            <a4j:form id="formCidade" ajaxSubmit="true">
                <h:panelGrid columns="1" width="100%" >
                    <h:panelGrid columns="4" width="100%">
                        <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/>
                        <h:selectOneMenu styleClass="campos" id="consultaCidade" value="#{EmpresaControle.campoConsultaCidade}">
                            <f:selectItems value="#{EmpresaControle.tipoConsultaComboCidade}" />
                        </h:selectOneMenu>
                        <h:inputText id="valorConsultaCidade" styleClass="campos" value="#{EmpresaControle.valorConsultaCidade}"/>
                        <a4j:commandButton ignoreDupResponses="true" id="btnConsultarCidade" reRender="formCidade:mensagemConsultaCidade, formCidade:resultadoConsultaCidade, formCidade:scResultadoCidade" action="#{EmpresaControle.consultarCidade}" styleClass="botoes" value="#{msg_bt.btn_consultar}" />
                    </h:panelGrid>

                    <rich:dataTable id="resultadoConsultaCidade" width="100%" headerClass="consulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                                    value="#{EmpresaControle.listaConsultaCidade}" rows="10" var="cidade">

                        <rich:column filterBy="#{cidade.nome}" filterEvent="onkeyup">
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_nome}"/>
                            </f:facet>
                            <a4j:commandLink ignoreDupResponses="true" action="#{EmpresaControle.selecionarCidade}" reRender="panelCidade, form" id="nome" value="#{cidade.nome}"/>
                        </rich:column>
                        <rich:column filterBy="#{cidade.estado}" filterEvent="onkeyup">
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_estado}"/>
                            </f:facet>
                            <a4j:commandLink ignoreDupResponses="true" action="#{EmpresaControle.selecionarCidade}" reRender="panelCidade, form" id="estado" value="#{cidade.estado}"/>
                        </rich:column>
                        <rich:column filterBy="#{cidade.codigoIBGE}" filterEvent="onkeyup">
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_ibge}"/>
                            </f:facet>
                            <a4j:commandLink ignoreDupResponses="true" action="#{EmpresaControle.selecionarCidade}" reRender="panelCidade, form" id="ibge" value="#{cidade.codigoIBGE}"/>
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_bt.btn_opcoes}"/>
                            </f:facet>
                            <a4j:commandButton ignoreDupResponses="true" action="#{EmpresaControle.selecionarCidade}" focus="cidade" reRender="resultadoConsultaCidade, form" oncomplete="Richfaces.hideModalPanel('panelCidade')" value="#{msg_bt.btn_selecionar}" styleClass="botoes"/>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller align="center" for="formCidade:resultadoConsultaCidade" maxPages="10"
                                       id="scResultadoCidade" />
                    <h:panelGrid id="mensagemConsultaCidade" columns="1" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%">
                            <h:outputText styleClass="mensagem"  value="#{EmpresaControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{EmpresaControle.mensagemDetalhada}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </rich:modalPanel>

        <h:form id="form">
            <h:commandLink action="#{EmpresaControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%">
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <rich:tabPanel switchType="ajax">
                        <rich:tab label="#{msg_aplic.prt_geral}" >

                            <h:panelGrid columns="2" styleClass="tabConsulta" width="100%">
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_Empresa_tituloForm}"/>
                                </f:facet>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_codigo}" />
                                <h:panelGroup>
                                    <h:inputText  id="codigo" required="true" size="10" maxlength="10" readonly="true" styleClass="camposSomenteLeitura" value="#{EmpresaControle.empresaVO.codigo}" />
                                    <h:message for="codigo" styleClass="mensagemDetalhada"/>
                                </h:panelGroup>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_razaoSocial}" />
                                <h:panelGroup>
                                    <h:inputText  id="razaoSocial"  size="40" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.razaoSocial}" />
                                </h:panelGroup>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_nomeFantasia}" />
                                <h:panelGroup>
                                    <h:inputText  id="nomeFantasia"  size="40" maxlength="40" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.nomeFantasia}" />
                                </h:panelGroup>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_endereco}" />
                                <h:panelGroup>
                                    <h:inputText  id="endereco"  size="60" maxlength="60" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.endereco}" />
                                </h:panelGroup>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_numero}" />
                                <h:inputText  id="numero" size="20" maxlength="20" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.numero}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_bairro}" />
                                <h:panelGroup>
                                    <h:inputText  id="bairro"  size="20" maxlength="20" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.bairro}" />
                                </h:panelGroup>
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_cidade}" />
                                <h:panelGroup>
                                    <h:inputText  id="cidade" size="20" maxlength="10" readonly="true" styleClass="camposSomenteLeitura" value="#{EmpresaControle.empresaVO.cidade.nome}" />
                                    <a4j:commandButton ignoreDupResponses="true" reRender="form" oncomplete="Richfaces.showModalPanel('panelCidade')" image="imagens/informacao.gif" title="Localizar Cidade" />
                                </h:panelGroup>


                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_cep}" />
                                <h:panelGroup>
                                    <h:inputText  id="cep" onkeypress="return mascara(this.form, 'form:cep', '99.999-999', event);"  size="10" maxlength="10" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.cep}" />

                                </h:panelGroup>
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_telefone}" />
                                <h:panelGroup>
                                    <h:inputText  id="telefone" onkeypress="return mascara(this.form, 'form:telefone', '(99)99999-9999', event);"  size="14" maxlength="14" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.telefone}" />

                                </h:panelGroup>
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_celular}" />
                                <h:inputText  id="celular" onkeypress="return mascara(this.form, 'form:celular', '(99)99999-9999', event);" size="14" maxlength="14" styleClass="campos" value="#{EmpresaControle.empresaVO.celular}" />
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_CNPJ}" />
                                <h:panelGroup>
                                    <h:inputText  id="CNPJ" onkeypress="return mascara(this.form, 'form:CNPJ', '99.999.999/9999-99', event);"  size="18" maxlength="18" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.CNPJ}" />
                                </h:panelGroup>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_inscricao}" />
                                <h:panelGroup>
                                    <h:inputText  id="inscricao" size="25" maxlength="25" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.inscricao}" />
                                </h:panelGroup>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_inscricaoMunicipal}" />
                                <h:inputText  id="inscricaoMunicipal" size="25" maxlength="25" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.inscricaoMunicipal}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_cnae}" />
                                <h:inputText  id="cnae" size="20" maxlength="20" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.cnae}"/>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_situacao}" />
                                <h:selectOneMenu id="situacaoEmpresa" value="#{EmpresaControle.empresaVO.situacao}">
                                    <f:selectItem itemLabel="ATIVA" itemValue="ATIVA"/>
                                    <f:selectItem itemLabel="INATIVA" itemValue="INATIVA"/>
                                </h:selectOneMenu>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_bloquearEm}" rendered="#{EmpresaControle.userNameIsAdmin}" />
                                <h:panelGroup  rendered="#{EmpresaControle.userNameIsAdmin}">
                                    <rich:calendar showWeeksBar="false" id="dataBloqueio" value="#{EmpresaControle.empresaVO.dataBloqueio}" inputClass="camposRichCalendar"
                                                   enableManualInput="true"
                                                   showApplyButton="false"
                                                   datePattern="dd/MM/yyyy HH:mm"/>

                                    <a4j:commandButton id="limparDataBloqueio" reRender="dataBloqueio" action="#{EmpresaControle.limparDataBloqueio}" accesskey="" alt="Limpar data Bloqueio" image="imagens/limpar.gif"/>
                                </h:panelGroup>
                                
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_limiteUsuariosSimultaneos}" rendered="#{EmpresaControle.userNameIsAdmin}" />
                                <h:inputText id="limiteUsuariosSimultaneos" styleClass="campos" value="#{EmpresaControle.empresaVO.limiteUsuariosSimultaneos}" size="11" maxlength="11" rendered="#{EmpresaControle.userNameIsAdmin}"/>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_limiteUsuariosSimultaneosVendedorExterno}" rendered="#{EmpresaControle.userNameIsAdmin}" />
                                <h:inputText id="limiteUsuariosSimultaneosVendedorExterno" styleClass="campos" value="#{EmpresaControle.empresaVO.limiteUsuariosSimultaneosVendedorExterno}" size="11" maxlength="11" rendered="#{EmpresaControle.userNameIsAdmin}"/>
                            </h:panelGrid>
                        </rich:tab>


                        <rich:tab label="#{msg_aplic.prt_bloqueioSistema}" rendered="false">
                            <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                                <h:panelGroup>
                                    <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_bloquearEm}" />
                                    <rich:spacer width="10px"/>
                                    <rich:calendar showWeeksBar="false" id="dataBloqueio1" value="#{EmpresaControle.empresaVO.dataBloqueio}" inputSize="10" inputClass="camposRichCalendar" datePattern="dd/MM/yyyy"
                                                   enableManualInput="true"
                                                   showApplyButton="false"/>
                                </h:panelGroup>
                                <h:panelGroup>

                                    <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_alertarQuandoRestar}" />
                                    <rich:spacer width="10px"/>
                                    <h:inputText  id="alertarQuandoRestarDias" size="2" styleClass="camposObrigatorios" value="#{EmpresaControle.empresaVO.alertarQuandoRestarDias}" />
                                    <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Empresa_dias}" />
                                </h:panelGroup>
                                <rich:spacer width="10px" height="110px"/>
                            </h:panelGrid>

                        </rich:tab>
                    </rich:tabPanel>

                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{EmpresaControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{EmpresaControle.mensagemDetalhada}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columns="4" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="novo" immediate="true" action="#{EmpresaControle.novo}" value="#{msg_bt.btn_novo}" accesskey="1" styleClass="botoes"/>
                        <h:commandButton id="salvar" action="#{EmpresaControle.gravar}" value="#{msg_bt.btn_gravar}" accesskey="2" styleClass="botoes"/>
                        <h:commandButton id="excluir" onclick="return confirm('Confirma exclusão dos dados?');" action="#{EmpresaControle.excluir}" value="#{msg_bt.btn_excluir}" accesskey="3" styleClass="botaoExcluir"/>
                        <h:commandButton id="consultar" immediate="true" action="#{EmpresaControle.inicializarConsultar}" value="#{msg_bt.btn_consultar}" accesskey="4" styleClass="botoes"/>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>        
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:razaoSocial").focus();
</script>