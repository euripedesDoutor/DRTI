<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_Cidade_tituloForm}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <h:form id="form">
            <h:commandLink action="#{CidadeControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%">
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="2" styleClass="tabConsulta" width="100%">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Cidade_tituloForm}"/>
                        </f:facet>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Cidade_codigo}" />
                        <h:panelGroup>
                            <h:inputText  id="codigo" size="10" maxlength="10" readonly="true" styleClass="camposSomenteLeitura" value="#{CidadeControle.cidadeVO.codigo}" />
                        </h:panelGroup>
                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Cidade_nome}" />
                        <h:panelGroup>
                            <h:inputText  id="nome" size="60" maxlength="60" styleClass="camposObrigatorios" value="#{CidadeControle.cidadeVO.nome}" />
                        </h:panelGroup>
                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Cidade_estado}" />
                        <h:panelGroup>
                            <h:selectOneMenu  id="estado" styleClass="camposObrigatorios" value="#{CidadeControle.cidadeVO.estado}" >
                                <f:selectItems  value="#{CidadeControle.listaSelectItemEstadoCidade}" />
                                <a4j:support event="onchange" reRender="form" focus="estado"/>
                            </h:selectOneMenu>
                        </h:panelGroup>
                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Cidade_codigoIBGE}" rendered="#{!CidadeControle.cidadeVO.estadoEX}"/>
                        <h:inputText  id="codigoIBGE" onkeypress="return Tecla('form:codigoIBGE');"  size="20" maxlength="20" styleClass="camposObrigatorios" value="#{CidadeControle.cidadeVO.codigoIBGE}" rendered="#{!CidadeControle.cidadeVO.estadoEX}"/>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Cidade_pais}" />
                        <h:panelGroup>
                            <h:inputText  id="pais"  size="60" maxlength="80" styleClass="camposObrigatorios" value="#{CidadeControle.cidadeVO.pais.nome}" />
                            <rich:suggestionbox
                                height="100"
                                width="800"
                                for="pais"
                                fetchValue="#{result.nome}"
                                suggestionAction="#{CidadeControle.autocompletePais}"
                                minChars="#{LoginControle.configuracaoEmpresaLogado.quantidadeDigitosAutoComplete}"
                                rowClasses="20"
                                nothingLabel="Nenhum país encontrado!"
                                var="result"
                                id="paisSugggestionBoxTrocaMercadoria">
                                <a4j:support event="onselect" focus=""
                                             reRender="form"
                                             action="#{CidadeControle.consultarPaisPorChavePrimaria}">
                                    <f:setPropertyActionListener target="#{CidadeControle.cidadeVO.pais}"
                                                                 value="#{result}" />
                                </a4j:support>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Pais_nome}" />
                                    </f:facet>
                                    <h:outputText value="#{result.nome}" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Pais_codigoSped}" />
                                    </f:facet>
                                    <h:outputText value="#{result.codigoSped}" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Pais_codigoSiscomex}" />
                                    </f:facet>
                                    <h:outputText value="#{result.codigoSiscomex}" />
                                </h:column>

                            </rich:suggestionbox>
                            <h:outputText escape="false" style="font: 7pt verdana, arial, helvetica, sans-serif;" value="<br>&nbsp;&nbsp;&nbsp;Mínimo #{LoginControle.configuracaoEmpresaLogado.quantidadeDigitosAutoComplete} caracteres" />
                        </h:panelGroup>


                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Pais_codigoSped} (País)" />
                        <h:inputText  id="codigoPais"  size="10" maxlength="10" styleClass="camposSomenteLeitura" readonly="true" value="#{CidadeControle.cidadeVO.pais.codigoSped}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Pais_codigoSiscomex} (País)" />
                        <h:inputText styleClass="camposSomenteLeitura" readonly="true" size="10" maxlength="10" value="#{CidadeControle.cidadeVO.pais.codigoSiscomex}" />
                    </h:panelGrid>

                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{CidadeControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{CidadeControle.mensagemDetalhada}"/>
                        </h:panelGrid>

                    </h:panelGrid>

                    <h:panelGrid columns="4" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="novo" immediate="true" action="#{CidadeControle.novo}" value="#{msg_bt.btn_novo}" accesskey="1" styleClass="botoes"/>
                        <h:commandButton id="salvar" action="#{CidadeControle.gravar}" value="#{msg_bt.btn_gravar}" accesskey="2" styleClass="botoes"/>
                        <h:commandButton id="excluir" onclick="return confirm('Confirma exclusão dos dados?');" action="#{CidadeControle.excluir}" value="#{msg_bt.btn_excluir}" accesskey="3" styleClass="botaoExcluir"/>
                        <h:commandButton id="consultar" immediate="true" action="#{CidadeControle.inicializarConsultar}" value="#{msg_bt.btn_consultar}" accesskey="4" styleClass="botoes"/>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>        
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:nome").focus();
</script>