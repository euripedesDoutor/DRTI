<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <rich:modalPanel id="panelEstado" autosized="true" shadowOpacity="true" width="350" height="150">
        <f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Estado"></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
                <h:graphicImage value="/imagens/close.png" style="cursor:pointer" id="hidelink10"/>
                <rich:componentControl for="panelEstado"  attachTo="hidelink10" operation="hide" event="onclick"/>
            </h:panelGroup>
        </f:facet>
        <h:form id="formEstado">
            <h:panelGrid columns="1" width="100%" columnClasses="colunaCentralizada">
                <h:panelGrid columns="2" footerClass="colunaCentralizada" width="100%">
                    <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Cidade_estado}" />
                    <h:panelGroup>
                        <h:selectOneMenu  id="estado" styleClass="camposObrigatorios" value="#{CidadeControle.cidadeVO.estado}" >
                            <f:selectItems  value="#{CidadeControle.listaSelectItemEstadoCidade}" />
                        </h:selectOneMenu>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columns="1" width="100%" columnClasses="colunaCentralizada" >
                    <h:commandLink id="imprimirPDF" target="_blank" action="#{CidadeControle.imprimirPDF}">
                        <a4j:commandButton ignoreDupResponses="true" styleClass="botoes" value="Imprimir PDF" reRender="formCadastro:mensagens" oncomplete="Richfaces.hideModalPanel('panelEstado')" />
                    </h:commandLink>
                </h:panelGrid>

                <h:panelGrid id="mensagemConsultaEstado" columns="1" width="100%" styleClass="tabMensagens">
                    <h:panelGrid columns="1" width="100%">
                        <h:outputText styleClass="mensagem"  value="#{CidadeControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{CidadeControle.mensagemDetalhada}"/>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>
    </rich:modalPanel>

    <title>
        <h:outputText value="#{msg_aplic.prt_Cidade_tituloForm}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>


        <h:form id="formCadastro">
            <h:commandLink action="#{CidadeControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%" >
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="4" headerClass="tabTituloForm" footerClass="colunaCentralizada" width="100%">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Cidade_tituloForm}"/>
                        </f:facet>
                        <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/> 
                        <h:selectOneMenu styleClass="campos" id="consulta" required="true" value="#{CidadeControle.controleConsulta.campoConsulta}">
                            <f:selectItems value="#{CidadeControle.tipoConsultaCombo}" />
                        </h:selectOneMenu>
                        <h:inputText id="valorConsulta" styleClass="campos" value="#{CidadeControle.controleConsulta.valorConsulta}"/>
                        <h:commandButton id="consultar" type="submit" styleClass="botoes" value="#{msg_bt.btn_consultar}" action="#{CidadeControle.consultar}" accesskey="2"/>

                    </h:panelGrid>

                    <h:dataTable id="items" width="100%" headerClass="consulta" styleClass="tabConsulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                                 value="#{CidadeControle.listaConsulta}" rows="10" var="cidade">

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_codigo}"/>
                            </f:facet>
                            <h:commandLink action="#{CidadeControle.editar}" id="codigo" value="#{cidade.codigo}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_nome}"/>
                            </f:facet>
                            <h:commandLink action="#{CidadeControle.editar}" id="nome" value="#{cidade.nome}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_estado}"/>
                            </f:facet>
                            <h:commandLink action="#{CidadeControle.editar}" id="estado" value="#{cidade.estado_Apresentar}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Cidade_codigoIBGE}"/>
                            </f:facet>
                            <h:commandLink action="#{CidadeControle.editar}" id="codigoIBGE" value="#{cidade.codigoIBGE}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_bt.btn_opcoes}"/>
                            </f:facet>
                            <h:commandButton action="#{CidadeControle.editar}" value="#{msg_bt.btn_editar}" styleClass="botoes"/>
                        </h:column>
                    </h:dataTable>
                    <rich:datascroller align="center" for="formCadastro:items"  id="scResultadoConsulta" />

                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens" id="mensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{CidadeControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{CidadeControle.mensagemDetalhada}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columns="2" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="novo" action="#{CidadeControle.novo}" value="#{msg_bt.btn_novo}" styleClass="botoes" accesskey="1"/>
                        <a4j:commandButton ignoreDupResponses="true" id="imprimirPDF" value="Gerar Relatório (PDF)" oncomplete="Richfaces.showModalPanel('panelEstado')" reRender="formCadastro:mensagens" styleClass="botoes" accesskey="1"/>
                    </h:panelGrid>
                </h:panelGrid>        
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>

<script>
    document.getElementById("formCadastro:valorConsulta").focus();
</script>