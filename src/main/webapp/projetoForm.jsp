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
        <h:form id="form">
            <jsp:include page="topoReduzido.jsp"/>
            <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid id="panelgrid1" columns="2" styleClass="tabConsulta" width="100%">
                        <f:facet name="header">
                            <h:outputText styleClass="tituloFormulario" value="#{msg_aplic.prt_Projeto_tituloForm}"/>
                        </f:facet>
                    </h:panelGrid>
                    <rich:tabPanel switchType="ajax" width="100%" id="abas">
                    <rich:tab label="#{msg_aplic.prt_Projeto_tituloForm}">
                    <h:panelGrid columns="2" styleClass="tabForm" width="100%">
                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Projeto_codigo}" rendered="#{!ProjetoControle.projetoVO.novoObj}"/>
                        <h:outputText  styleClass="tituloCampos" value="#{ProjetoControle.projetoVO.codigo}" rendered="#{!ProjetoControle.projetoVO.novoObj}"/>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Projeto_nome}"/>
                        <h:inputText  id="nome" size="40"   styleClass="camposObrigatorios" value="#{ProjetoControle.projetoVO.nome}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Projeto_nomeApresentar}"/>
                        <h:inputText  id="nomeApresentar" size="40"   styleClass="camposObrigatorios" value="#{ProjetoControle.projetoVO.nomeApresentar}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Projeto_url}"/>
                        <h:inputText  id="url" size="40"   styleClass="camposObrigatorios" value="#{ProjetoControle.projetoVO.url}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Projeto_backend}"/>
                        <h:selectOneMenu id="backend" styleClass="camposObrigatorios" value="#{ProjetoControle.projetoVO.backend}" >
                            <f:selectItems  value="#{ProjetoControle.projetoVO.listaSelectItemBackend}" />
                            <a4j:support event="onchange" reRender="form"/>
                        </h:selectOneMenu>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Projeto_frontend}"/>
                        <h:outputText  styleClass="tituloCamposNegrito" value="#{ProjetoControle.projetoVO.frontend_Apresentar}" rendered="#{ProjetoControle.projetoVO.backendJava6}"/>
                        <h:selectOneMenu id="frontend" styleClass="camposObrigatorios" value="#{ProjetoControle.projetoVO.frontend}" rendered="#{!ProjetoControle.projetoVO.backendJava6}">
                            <f:selectItems  value="#{ProjetoControle.projetoVO.listaSelectItemFrontend}" />
                        </h:selectOneMenu>


                    </h:panelGrid>
                    </rich:tab>
                    <rich:tab label="#{msg_aplic.prt_Modulo_tituloForm}" id="abaModulo">
                        <h:panelGrid columns="1" id="panelgridModulo" width="100%" styleClass="colunaCentralizada">
                            <f:facet name="header">
                                <h:outputText styleClass="tituloFormulario" value="#{msg_aplic.prt_Modulo_tituloForm}"/>
                            </f:facet>
                            <h:panelGrid columns="2" width="100%" columnClasses="colunaDireita, colunaEsquerda">
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Modulo_nome}"/>
                                <h:inputText  id="modulo_nome" size="40"   styleClass="camposObrigatorios" value="#{ProjetoControle.moduloVO.nome}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Modulo_nomeApresentar}"/>
                                <h:inputText  id="modulo_nomeApresentar" size="40"   styleClass="camposObrigatorios" value="#{ProjetoControle.moduloVO.nomeApresentar}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Modulo_data}"/>
                                <rich:calendar firstWeekDay="0" showWeeksBar="false" id="modulo_data" value="#{ProjetoControle.moduloVO.data}" inputSize="10" inputClass="camposRichCalendar" datePattern="dd/MM/yyyy HH:mm"
                                    showFooter="true" enableManualInput="true"
                                    showApplyButton="false" cellWidth="24px" cellHeight="22px" style="width:200px"/>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Modulo_usuario}"/>

                                <rich:spacer width="5px" height="40px"/>
                                <a4j:commandLink styleClass="btn" ignoreDupResponses="true" style="position: relative; top: 0px; width: 120px !important; background: #004400 !important;" id="modulo_adicionar" action="#{ProjetoControle.adicionarModulo}" focus="modulo_nomeApresentar" reRender="panelgridModulo">
                                    <i class="fas fa-plus-circle fa-2x" style="position: relative; top: 6px"></i>
                                    <h:outputText value="Adicionar" escape="false" style="font-size: 10pt;"/>
                                </a4j:commandLink>
                            </h:panelGrid>
                            <rich:dataTable id="listaModulo" width="100%" headerClass="consulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                                             value="#{ProjetoControle.projetoVO.listaModulo}" rows="10" var="modulo">
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Modulo_nome}"/>
                                    </f:facet>
                                    <h:commandLink action="#{ProjetoControle.editarModulo}" id="nome" value="#{modulo.nome}"/>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Modulo_nomeApresentar}"/>
                                    </f:facet>
                                    <h:commandLink action="#{ProjetoControle.editarModulo}" id="nomeApresentar" value="#{modulo.nomeApresentar}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_bt.btn_opcoes}"/>
                                    </f:facet>
                                    <a4j:commandLink styleClass="btnFit" style="position: relative; top: 0px; width: 120px !important; background: #007700 !important;" action="#{ProjetoControle.editarModulo}" reRender="form">
                                        <i class="fas fa-edit fa-lg"></i>
                                        <h:outputText value="Editar" escape="false" style="font-size: 10pt;"/>
                                    </a4j:commandLink>
                                    <rich:spacer width="10px"/>
                                    <a4j:commandLink styleClass="btnFit" style="position: relative; top: 0px; width: 120px !important; background: #660000 !important;" action="#{ProjetoControle.removerModulo}" reRender="form">
                                       <i class="far fa-window-close fa-lg"></i>
                                       <h:outputText value="Remover" escape="false" style="font-size: 10pt;"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="center" for="form:listaModulo"  id="scResultadoListaModulo" />

                        </h:panelGrid>
                    </rich:tab>
                    </rich:tabPanel>
                    <h:panelGrid columns="1" width="100%" styleClass="tabMensagens">
                        <h:outputText styleClass="mensagem"  value="#{ProjetoControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{ProjetoControle.mensagemDetalhada}"/>
                        <h:panelGrid columns="4" width="100%" columnClasses="colunaCentralizada">
                            <h:commandLink styleClass="btn" style="position: relative; top: 0px; width: 120px !important; background: #004400 !important;" id="novo" action="#{ProjetoControle.novo}">
                                <i class="far fa-plus-square fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Novo" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                            <a4j:commandLink onclick="desabilitarEnquantoProcessa(this)" ignoreDupResponses="true" styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #004444 !important;" id="salvar" action="#{ProjetoControle.gravar}" reRender="form">
                                <i class="far fa-save fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Gravar" escape="false" style="font-size: 10pt;"/>
                            </a4j:commandLink>
                            <h:commandLink styleClass="btn" onclick="return confirm('Confirma exclusão dos dados?');" style="position: relative; top: -3px; width: 120px !important; background: #660000 !important;" id="excluir" action="#{ProjetoControle.excluir}">
                                <i class="far fa-trash-alt fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Excluir" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                            <h:commandLink styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #000044 !important;" id="consultar" action="#{ProjetoControle.inicializarConsultar}">
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
    document.getElementById("form:nomeApresentar").focus();
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
