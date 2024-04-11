<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_tituloForm}"/>
    </title>
    
    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>
        
        <h:form id="formCadastro">
            <h:commandLink action="#{ConfiguracaoSistemaControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%" >
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="4" headerClass="tabTituloForm" footerClass="colunaCentralizada" width="100%">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_tituloForm}"/>
                        </f:facet>
                        <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/> 
                        <h:selectOneMenu styleClass="campos" id="consulta" required="true" value="#{ConfiguracaoSistemaControle.controleConsulta.campoConsulta}">
                            <f:selectItems value="#{ConfiguracaoSistemaControle.tipoConsultaCombo}" />
                        </h:selectOneMenu>
                        <h:inputText id="valorConsulta" styleClass="campos" value="#{ConfiguracaoSistemaControle.controleConsulta.valorConsulta}"/>
                        <h:commandButton id="consultar" type="submit" styleClass="botoes" value="#{msg_bt.btn_consultar}" action="#{ConfiguracaoSistemaControle.irPaginaInicial}" accesskey="2"/>
                        <f:facet name="footer">
                            <h:panelGroup binding="#{ConfiguracaoSistemaControle.apresentarLinha}">
                                <h:commandLink styleClass="tituloCampos" value="  <<  " rendered="false" binding="#{ConfiguracaoSistemaControle.apresentarPrimeiro}" action="#{ConfiguracaoSistemaControle.irPaginaInicial}"/> 
                                <h:commandLink styleClass="tituloCampos" value="  <  " rendered="false" binding="#{ConfiguracaoSistemaControle.apresentarAnterior}" action="#{ConfiguracaoSistemaControle.irPaginaAnterior}"/> 
                                <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_msg_pagina} #{ConfiguracaoSistemaControle.paginaAtualDeTodas}" rendered="true"/>
                                <h:commandLink styleClass="tituloCampos" value="  >  " rendered="false" binding="#{ConfiguracaoSistemaControle.apresentarPosterior}" action="#{ConfiguracaoSistemaControle.irPaginaPosterior}"/> 
                                <h:commandLink styleClass="tituloCampos" value="  >>  " rendered="false" binding="#{ConfiguracaoSistemaControle.apresentarUltimo}" action="#{ConfiguracaoSistemaControle.irPaginaFinal}"/>
                            </h:panelGroup>
                        </f:facet>
                    </h:panelGrid>

                    <h:dataTable id="items" width="100%" headerClass="consulta" styleClass="tabConsulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                                 value="#{ConfiguracaoSistemaControle.listaConsulta}" rows="10" var="configuracaoSistema">

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_codigo}"/>
                                </f:facet>
                                <h:outputText id="codigo" value="#{configuracaoSistema.codigo}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_empresa}"/>
                                </f:facet>
                                <h:outputText id="empresa" value="#{configuracaoSistema.empresa.nomeFantasia}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_planoContaDesconto}"/>
                                </f:facet>
                                <h:outputText id="planoContaDesconto" value="#{configuracaoSistema.planoContaDesconto.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_planoContaJuros}"/>
                                </f:facet>
                                <h:outputText id="planoContaJuros" value="#{configuracaoSistema.planoContaJuros.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_planoContaCaixa}"/>
                                </f:facet>
                                <h:outputText id="planoContaCaixa" value="#{configuracaoSistema.planoContaCaixa.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_planoContaVendaCancelada}"/>
                                </f:facet>
                                <h:outputText id="planoContaVendaCancelada" value="#{configuracaoSistema.planoContaVendaCancelada.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_planoContaVendaDebito}"/>
                                </f:facet>
                                <h:outputText id="planoContaVendaDebito" value="#{configuracaoSistema.planoContaVendaDebito.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_planoContaVendaCredito}"/>
                                </f:facet>
                                <h:outputText id="planoContaVendaCredito" value="#{configuracaoSistema.planoContaVendaCredito.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_historicoEmissaoCheque}"/>
                                </f:facet>
                                <h:outputText id="historicoEmissaoCheque" value="#{configuracaoSistema.historicoEmissaoCheque.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_CFOPdentroEstado}"/>
                                </f:facet>
                                <h:outputText id="CFOPdentroEstado" value="#{configuracaoSistema.CFOPdentroEstado.descricao}"/> 
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_CFOPforaEstado}"/>
                                </f:facet>
                                <h:outputText id="CFOPforaEstado" value="#{configuracaoSistema.CFOPforaEstado.descricao}"/> 
                            </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_bt.btn_opcoes}"/>
                            </f:facet>
                            <h:commandButton action="#{ConfiguracaoSistemaControle.editar}" value="#{msg_bt.btn_editar}" styleClass="botoes"/>
                        </h:column>
                    </h:dataTable>

                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{ConfiguracaoSistemaControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{ConfiguracaoSistemaControle.mensagemDetalhada}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" width="10%" styleClass="tabMensagenReduzida" columnClasses="colunaDireita">
                            <h:outputText styleClass="tabMensagenReduzida" value="(ConfiguracaoSistema)"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columns="1" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="novo" action="#{ConfiguracaoSistemaControle.novo}" value="#{msg_bt.btn_novo}" styleClass="botoes" accesskey="1"/>
                    </h:panelGrid>
                </h:panelGrid>        
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>

<script>
    document.getElementById("formCadastro:valorConsulta").focus();
</script>