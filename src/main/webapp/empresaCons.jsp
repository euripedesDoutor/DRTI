<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_Empresa_tituloForm}"/>
    </title>
    
    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>
        
        <h:form id="formCadastro">
            <h:commandLink action="#{EmpresaControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%" >
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="4" headerClass="tabTituloForm" footerClass="colunaCentralizada" width="100%">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Empresa_tituloForm}"/>
                        </f:facet>
                        <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/> 
                        <h:selectOneMenu styleClass="campos" id="consulta" required="true" value="#{EmpresaControle.controleConsulta.campoConsulta}">
                            <f:selectItems value="#{EmpresaControle.tipoConsultaCombo}" />
                        </h:selectOneMenu>
                        <h:inputText id="valorConsulta" styleClass="campos" value="#{EmpresaControle.controleConsulta.valorConsulta}"/>
                        <h:commandButton id="consultar" type="submit" styleClass="botoes" value="#{msg_bt.btn_consultar}" action="#{EmpresaControle.consultar}" accesskey="2"/>
                       
                    </h:panelGrid>

                    <h:dataTable id="items" width="100%" headerClass="consulta" styleClass="tabConsulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaCentralizada, colunaEsquerda, colunaEsquerda, colunaEsquerda, colunaCentralizada"
                                 value="#{EmpresaControle.listaConsulta}" rows="10" var="empresa">

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_Empresa_codigo}"/>
                                </f:facet>
                                <h:commandLink action="#{EmpresaControle.editar}" id="codigo" value="#{empresa.codigo}" style="#{empresa.situacaoInativaCss}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_Empresa_nomeFantasia}"/>
                                </f:facet>
                               <h:commandLink action="#{EmpresaControle.editar}" id="nomeFantasia" value="#{empresa.nomeFantasia}" style="#{empresa.situacaoInativaCss}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_Empresa_endereco}"/>
                                </f:facet>
                               <h:commandLink action="#{EmpresaControle.editar}" id="endereco" value="#{empresa.endereco}" style="#{empresa.situacaoInativaCss}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_Empresa_cidade}"/>
                                </f:facet>
                               <h:commandLink action="#{EmpresaControle.editar}" id="cidade" value="#{empresa.cidade.nome}" style="#{empresa.situacaoInativaCss}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_Empresa_CNPJ}"/>
                                </f:facet>
                               <h:commandLink action="#{EmpresaControle.editar}" id="CNPJ" value="#{empresa.CNPJ}" style="#{empresa.situacaoInativaCss}"/>
                            </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_bt.btn_opcoes}"/>
                            </f:facet>
                            <h:commandButton action="#{EmpresaControle.editar}" value="#{msg_bt.btn_editar}" styleClass="botoes"/>
                        </h:column>
                    </h:dataTable>
                    <rich:datascroller align="center" for="formCadastro:items"  id="scResultadoConsulta" />

                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{EmpresaControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{EmpresaControle.mensagemDetalhada}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columns="1" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="novo" action="#{EmpresaControle.novo}" value="#{msg_bt.btn_novo}" styleClass="botoes" accesskey="1"/>
                    </h:panelGrid>
                </h:panelGrid>        
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>

<script>
    document.getElementById("formCadastro:valorConsulta").focus();
</script>