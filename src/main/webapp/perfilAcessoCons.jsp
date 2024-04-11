<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<%@taglib prefix="rich" uri="http://richfaces.org/rich" %>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>Perfil Acesso</title>

    <title>
        <h:outputText value="#{PerfilAcessoControle.versaoSistema}"/>
    </title>
    <rich:modalPanel id="panelListaUsuarios" autosized="true" shadowOpacity="true" width="400" height="300">
        <f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Lista de usuários"></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
                <h:graphicImage value="/imagens/close.png" style="cursor:pointer" id="hidelinkUsuarios"/>
                <rich:componentControl for="panelListaUsuarios" attachTo="hidelinkUsuarios" operation="hide"
                                       event="onclick"/>
            </h:panelGroup>
        </f:facet>

        <a4j:form id="formUsuario" ajaxSubmit="true">
            <h:panelGrid columns="2" width="100%" columnClasses="colunaDireita, colunaEsquerda">
                <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_PerfilAcesso_tituloForm}:"/>
                <h:outputText styleClass="tituloCamposNegrito12"
                              value="#{PerfilAcessoControle.perfilAcessoConsulta.nome}"
                              style="position: relative; top: -3px;"/>
            </h:panelGrid>
            <h:panelGrid columns="1" width="100%">
                <rich:dataTable id="resultadoConsultaUsuario" width="100%" headerClass="consulta"
                                rowClasses="linhaImpar, linhaPar"
                                columnClasses="colunaDireita, colunaEsquerda, colunaCentralizada"
                                value="#{PerfilAcessoControle.listaConsultaUsuario}" rows="10" var="usuario">
                    <rich:column sortBy="#{usuario.codigo}">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Usuario_codigo}"/>
                        </f:facet>
                        <h:outputText value="#{usuario.codigo}"/>
                    </rich:column>
                    <rich:column sortBy="#{usuario.nome}">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Usuario_nome}"/>
                        </f:facet>
                        <h:outputText value="#{usuario.nome}"/>
                    </rich:column>
                    <rich:column sortBy="#{usuario.dataUltimoAcesso_ApresentarDataNumero}">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Usuario_ultimoAcesso}"/>
                        </f:facet>
                        <h:outputText value="#{usuario.dataUltimoAcesso_ApresentarDataNumero}"/>
                    </rich:column>
                </rich:dataTable>

                <rich:datascroller align="center" maxPages="10"
                                   for="resultadoConsultaUsuario"
                                   id="dscResultadoUsuario"/>
                <h:panelGrid id="mensagemConsultaUsuario" columns="1" width="100%" styleClass="tabMensagens">
                    <h:panelGrid columns="1" width="100%">
                        <h:outputText styleClass="mensagem" value="#{PerfilAcessoControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{PerfilAcessoControle.mensagemDetalhada}"/>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </a4j:form>
    </rich:modalPanel>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <h:form id="formCadastro">
            <h:commandLink action="#{PerfilAcessoControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria"
                           style="display: none"/>
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%">
                <h:panelGrid columns="1" width="100%">
                    <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                        <h:panelGrid columns="5" headerClass="tabTituloForm" footerClass="colunaCentralizada"
                                     width="100%">
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_PerfilAcesso_tituloForm}"/>
                            </f:facet>
                            <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/>
                            <h:selectOneMenu styleClass="campos" id="consulta" required="true"
                                             value="#{PerfilAcessoControle.controleConsulta.campoConsulta}">
                                <f:selectItems value="#{PerfilAcessoControle.tipoConsultaCombo}"/>
                                <a4j:support event="onchange" reRender="formCadastro"/>
                            </h:selectOneMenu>

                            <h:selectOneMenu styleClass="campos" id="tipoPerfilSelectItem"
                                             value="#{PerfilAcessoControle.controleConsulta.valorConsulta}"
                                             rendered="#{PerfilAcessoControle.tipoConsultaTipoPerfil}">
                                <f:selectItems value="#{PerfilAcessoControle.listaTipoPerfil}"/>
                            </h:selectOneMenu>

                            <h:inputText id="valorConsulta" styleClass="campos"
                                         value="#{PerfilAcessoControle.controleConsulta.valorConsulta}"
                                         rendered="#{!PerfilAcessoControle.tipoConsultaTipoPerfil}"/>
                            <a4j:commandButton id="consultar" styleClass="botoes" value="#{msg_bt.btn_consultar}"
                                               action="#{PerfilAcessoControle.consultar}" accesskey="2"/>

                        </h:panelGrid>

                        <h:dataTable id="items" width="100%" headerClass="consulta" styleClass="tabConsulta"
                                     rowClasses="linhaImpar, linhaPar"
                                     columnClasses="colunaDireita, colunaEsquerda, colunaCentralizada"
                                     value="#{PerfilAcessoControle.listaConsulta}" rows="10" var="perfilAcesso">

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_PerfilAcesso_codigo}"/>
                                </f:facet>
                                <h:commandLink action="#{PerfilAcessoControle.editar}" id="codigo"
                                               value="#{perfilAcesso.codigo}"/>
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_aplic.prt_PerfilAcesso_nome}"/>
                                </f:facet>
                                <h:commandLink action="#{PerfilAcessoControle.editar}" id="nome"
                                               value="#{perfilAcesso.nome}"/>
                            </h:column>

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="#{msg_bt.btn_opcoes}"/>
                                </f:facet>
                                <h:commandButton action="#{PerfilAcessoControle.editar}" value="#{msg_bt.btn_editar}"
                                                 styleClass="botoes"/>
                                <a4j:commandButton id="listarUsusarios" ignoreDupResponses="true" reRender="formUsuario"
                                                   image="./imagens/informacao.gif"
                                                   action="#{PerfilAcessoControle.consultarUsuario}"
                                                   oncomplete="Richfaces.showModalPanel('panelListaUsuarios')"/>

                            </h:column>

                        </h:dataTable>
                        <rich:datascroller align="center" for="formCadastro:items" id="scResultadoConsulta"/>

                        <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                            <h:panelGrid columns="1" width="100%" styleClass="tabMensagens"
                                         columnClasses="colunaEsquerda">
                                <h:outputText styleClass="linhaMensagem" value="#{PerfilAcessoControle.mensagem}"/>
                                <h:outputText styleClass="mensagemDetalhada"
                                              value="#{PerfilAcessoControle.mensagemDetalhada}"/>
                            </h:panelGrid>

                        </h:panelGrid>

                        <h:panelGrid columns="1" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                            <h:commandButton id="novo" action="#{PerfilAcessoControle.novo}" value="#{msg_bt.btn_novo}"
                                             styleClass="botoes" accesskey="1"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>

<script>
    document.getElementById("formCadastro:valorConsulta").focus();
</script>