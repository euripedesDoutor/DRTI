<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>
    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_Pais_tituloForm}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <h:form id="form">
            <jsp:include page="topoReduzido.jsp"/>
            <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid id="panelgrid1" columns="2" styleClass="tabConsulta" width="100%">
                        <f:facet name="header">
                            <h:outputText styleClass="tituloFormulario" value="#{msg_aplic.prt_Pais_tituloForm}"/>
                        </f:facet>
                    </h:panelGrid>
                    <h:panelGrid columns="2" styleClass="tabForm" width="100%">
                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Pais_codigo}" rendered="#{!PaisControle.paisVO.novoObj}"/>
                        <h:outputText  styleClass="tituloCampos" value="#{PaisControle.paisVO.codigo}" rendered="#{!PaisControle.paisVO.novoObj}"/>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Pais_nome}"/>
                        <h:inputText  id="nome" size="40"  maxlength="80" styleClass="camposObrigatorios" value="#{PaisControle.paisVO.nome}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Pais_codigoSped}"/>
                        <h:inputText  id="codigoSped" size="40"  maxlength="80" styleClass="camposObrigatorios" value="#{PaisControle.paisVO.codigoSped}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Pais_codigoSiscomex}"/>
                        <h:inputText  id="codigoSiscomex" size="40"  maxlength="80" styleClass="camposObrigatorios" value="#{PaisControle.paisVO.codigoSiscomex}" />

                    </h:panelGrid>
                    <h:panelGrid columns="1" width="100%" styleClass="tabMensagens">
                        <h:outputText styleClass="mensagem"  value="#{PaisControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{PaisControle.mensagemDetalhada}"/>
                        <h:panelGrid columns="4" width="100%" columnClasses="colunaCentralizada">
                            <h:commandLink styleClass="btn" style="position: relative; top: 0px; width: 120px !important; background: #004400 !important;" id="novo" action="#{PaisControle.novo}">
                                <i class="far fa-plus-square fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Novo" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                            <a4j:commandLink onclick="desabilitarEnquantoProcessa(this)" ignoreDupResponses="true" styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #004444 !important;" id="salvar" action="#{PaisControle.gravar}" reRender="form">
                                <i class="far fa-save fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Gravar" escape="false" style="font-size: 10pt;"/>
                            </a4j:commandLink>
                            <h:commandLink styleClass="btn" onclick="return confirm('Confirma exclusão dos dados?');" style="position: relative; top: -3px; width: 120px !important; background: #660000 !important;" id="excluir" action="#{PaisControle.excluir}">
                                <i class="far fa-trash-alt fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Excluir" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                            <h:commandLink styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #000044 !important;" id="consultar" action="#{PaisControle.inicializarConsultar}">
                                <i class="fas fa-search fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Consultar" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:nome").focus();
    var enviado = false;
    function desabilitarEnquantoProcessa(button){
        if(!enviado){
            enviado = true;
            button.value='Aguarde...';
            button.disabled=true;
        }
        return true;
    }
</script>
