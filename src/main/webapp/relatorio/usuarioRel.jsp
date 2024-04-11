<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="./loadsRelatorio.jsp"/>
<title>Relatório de Usuário</title>

    <h:panelGrid columns="2" width="100%">        
        <h:form id="form" target="_blank">
            <h:commandLink action="#{UsuarioControleRel.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />                         
            <h:panelGrid columns="1" columnClasses="colunaCentralizada" styleClass="tabconteudo" width="100%">
                
                <h:panelGrid columns="1" columnClasses="colunaCentralizada"  styleClass="tabForm" width="100%">
                    <f:facet name="header">
                        <h:outputText value="#{msg_aplic.prt_UsuarioRel_tituloRel}"/>
                    </f:facet>                      
                    <h:panelGrid columns="2" columnClasses="colunaCentralizada"  styleClass="tabConsulta" width="100%">
                        <h:commandButton id="imprimirHTML" action="#{UsuarioControleRel.imprimirHTML}" value="Gerar Relatório" accesskey="1" styleClass="botoes"/>
                        <h:commandButton id="imprimirPDF" action="#{UsuarioControleRel.imprimirPDF}" value="Gerar Relatório (PDF)" accesskey="2" styleClass="botoes"/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{UsuarioControleRel.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{UsuarioControleRel.mensagemDetalhada}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" width="10%" styleClass="tabMensagenReduzida" columnClasses="colunaDireita">
                            <h:outputText styleClass="tabMensagenReduzida" value="(RelatorioUsuario)"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
                
            </h:panelGrid>
        </h:form>   
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:mes").focus();
</script>
