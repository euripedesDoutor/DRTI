<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_Projeto_tituloForm}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <h:form id="formConsulta">
            <h:commandLink action="#{ProjetoControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabForm" width="100%" >
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="4" headerClass="tabTituloForm" width="100%">
                        <f:facet name="header">
                            <h:outputText styleClass="tituloFormulario" value="#{msg_aplic.prt_Projeto_tituloForm}"/>
                        </f:facet>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="colunaCentralizada" width="100%">
                        <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/>
                        <h:selectOneMenu styleClass="campos" id="consulta" required="true" value="#{ProjetoControle.controleConsulta.campoConsulta}">
                            <f:selectItems value="#{ProjetoControle.tipoConsultaCombo}" />
                            <a4j:support event="onchange" reRender="formConsulta"/>
                        </h:selectOneMenu>
                        <h:inputText id="valorConsulta" styleClass="campos" value="#{ProjetoControle.controleConsulta.valorConsulta}" rendered="#{ProjetoControle.tipoConsultaOutros}"/>
                        <h:commandLink styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #000044 !important;" id="consultar" action="#{ProjetoControle.consultar}">
                            <i class="fas fa-search fa-2x" style="position: relative; top: 6px"></i>
                            <h:outputText value="Consultar" escape="false" style="font-size: 10pt;"/>
                        </h:commandLink>

                    </h:panelGrid>
                </h:panelGrid>

                <rich:dataTable id="items" width="100%" headerClass="consulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                             value="#{ProjetoControle.listaConsulta}" rows="10" var="projeto">
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Projeto_codigo}"/>
                        </f:facet>
                        <h:commandLink action="#{ProjetoControle.editar}" id="codigo" value="#{projeto.codigo}"/>
                    </rich:column>

                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Projeto_nomeApresentar}"/>
                        </f:facet>
                        <h:commandLink action="#{ProjetoControle.editar}" id="nomeApresentar" value="#{projeto.nomeApresentar}"/>
                    </rich:column>

                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="#{msg_bt.btn_opcoes}"/>
                        </f:facet>
                        <h:commandLink styleClass="btnFit" style="position: relative; top: 0px; width: 120px !important; background: #007700 !important;" action="#{ProjetoControle.editar}">
                            <i class="fas fa-edit fa-lg"></i>
                            <h:outputText value="Editar" escape="false" style="font-size: 10pt;"/>
                        </h:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="center" for="formConsulta:items"  id="scResultadoConsulta" />

                <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" id="mensagens">
                    <h:panelGrid columns="1" width="100%">
                        <h:outputText styleClass="mensagem"  value="#{ProjetoControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{ProjetoControle.mensagemDetalhada}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" width="100%" columnClasses="colunaCentralizada">
                        <h:commandLink styleClass="btn" style="position: relative; top: 0px; width: 120px !important; background: #004400 !important;" id="novo" action="#{ProjetoControle.novo}">
                            <i class="far fa-plus-square fa-2x" style="position: relative; top: 6px"></i>
                            <h:outputText value="Novo" escape="false" style="font-size: 10pt;"/>
                        </h:commandLink>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("formConsulta:valorConsulta").focus();
</script>
