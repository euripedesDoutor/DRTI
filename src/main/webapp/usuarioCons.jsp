<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_Usuario_tituloForm}"/>
    </title>

    <rich:modalPanel id="panelListaPerfilAcesso" autosized="true" shadowOpacity="true" width="400" height="300">
        <f:facet name="header">
            <h:panelGroup>
                <h:outputText value="Perfis de Acesso"></h:outputText>
            </h:panelGroup>
        </f:facet>
        <f:facet name="controls">
            <h:panelGroup>
                <h:graphicImage value="/imagens/close.png" style="cursor:pointer" id="hidelinkListaPerfilAcesso"/>
                <rich:componentControl for="panelListaPerfilAcesso" attachTo="hidelinkListaPerfilAcesso" operation="hide"
                                       event="onclick"/>
            </h:panelGroup>
        </f:facet>

        <a4j:form id="formListaPerfilAcesso" ajaxSubmit="true">
            <h:panelGrid columns="2" width="100%" columnClasses="colunaDireita, colunaEsquerda">
                <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_Usuario_tituloForm}:"/>
                <h:outputText styleClass="tituloCamposNegrito12"
                              value="#{UsuarioControle.usuarioConsulta.nome}"
                              style="position: relative; top: -3px;"/>
            </h:panelGrid>
            <h:panelGrid columns="1" width="100%">
                <rich:dataTable id="resultadoConsultaPerfilAcesso" width="100%" headerClass="consulta"
                                rowClasses="linhaImpar, linhaPar"
                                columnClasses="colunaEsquerda, colunaEsquerda"
                                value="#{UsuarioControle.usuarioConsulta.usuarioPerfilAcessoVOs}" rows="10" var="perfilAcesso">

                    <rich:column sortBy="#{perfilAcesso.empresa.nomeFantasia}">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_UsuarioPerfilAcesso_empresa}"/>
                        </f:facet>
                        <h:outputText value="#{perfilAcesso.empresa.nomeFantasia}"/>
                    </rich:column>
                    <rich:column sortBy="#{perfilAcesso.perfilAcesso.nome}">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_PerfilAcesso_nome}"/>
                        </f:facet>
                        <h:outputText value="#{perfilAcesso.perfilAcesso.nome}"/>
                    </rich:column>
                </rich:dataTable>

                <rich:datascroller align="center" maxPages="10"
                                   for="resultadoConsultaPerfilAcesso"
                                   id="dscResultadoPerfilAcesso"/>
                <h:panelGrid id="mensagemConsultaPerfilAcesso" columns="1" width="100%" styleClass="tabMensagens">
                    <h:panelGrid columns="1" width="100%">
                        <h:outputText styleClass="mensagem" value="#{UsuarioControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{UsuarioControle.mensagemDetalhada}"/>
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
            <h:commandLink action="#{UsuarioControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%" >
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="4" headerClass="tabTituloForm" footerClass="colunaCentralizada" width="100%">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_Usuario_tituloForm}"/>
                        </f:facet>
                        <h:outputText styleClass="tituloCampos" value="#{msg.msg_consultar_por}"/> 
                        <h:selectOneMenu styleClass="campos" id="consulta" required="true" value="#{UsuarioControle.controleConsulta.campoConsulta}">
                            <a4j:support event="onchange" reRender="formCadastro"/>
                            <f:selectItems value="#{UsuarioControle.tipoConsultaCombo}" />
                        </h:selectOneMenu>

                        <h:panelGroup rendered="#{!UsuarioControle.tipoConsultaPerfilAcesso && !UsuarioControle.tipoConsultaSituacaoAcesso}">
                            <h:inputText id="valorConsulta" styleClass="campos" value="#{UsuarioControle.controleConsulta.valorConsulta}"/>

                            <rich:suggestionbox
                                height="100"
                                width="400"
                                for="valorConsulta"
                                fetchValue="#{result.nome}"
                                suggestionAction="#{UsuarioControle.autocompleteUsuario}"
                                minChars="#{LoginControle.configuracaoEmpresaLogado.quantidadeDigitosAutoComplete}"
                                rowClasses="20"
                                nothingLabel="Nenhum Usuário encontrado"
                                var="result"
                                id="usuarioSuggestionBox">
                                <a4j:support event="onselect" focus="valorConsulta"
                                             reRender="form"
                                             action="#{UsuarioControle.consultar}">
                                    <f:setPropertyActionListener target="#{UsuarioControle.usuarioVO}"
                                                                 value="#{result}" />
                                </a4j:support>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Usuario_codigo}" />
                                    </f:facet>
                                    <h:outputText value="#{result.codigo}" />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Usuario_nome}" />
                                    </f:facet>
                                    <h:outputText value="#{result.nome}" />
                                </h:column>

                            </rich:suggestionbox>

                        </h:panelGroup>
                        <h:panelGroup rendered="#{UsuarioControle.tipoConsultaPerfilAcesso}">
                            <h:selectOneMenu  id="codPerfilAcesso" styleClass="camposObrigatorios" value="#{UsuarioControle.controleConsulta.valorConsulta}" >
                                <f:selectItems  value="#{UsuarioControle.listaSelectItemCodPerfilAcesso}" />
                            </h:selectOneMenu>
                        </h:panelGroup>
                        <h:panelGroup rendered="#{UsuarioControle.tipoConsultaSituacaoAcesso}">
                            <h:selectOneMenu styleClass="camposObrigatorios" value="#{UsuarioControle.controleConsulta.valorConsulta}" >
                                <f:selectItems  value="#{UsuarioControle.listaOpcoesConsultaSituacaoAcesso}" />
                            </h:selectOneMenu>
                        </h:panelGroup>
                        <h:commandButton id="consultar" type="submit" styleClass="botoes" value="#{msg_bt.btn_consultar}" action="#{UsuarioControle.consultar}" accesskey="2"/>
                    </h:panelGrid>

                    <h:dataTable id="items" width="100%" headerClass="consulta" styleClass="tabConsulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                                 value="#{UsuarioControle.listaConsulta}" rows="10" var="usuario2">

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Usuario_codigo}"/>
                            </f:facet>
                            <h:commandLink action="#{UsuarioControle.editar}" id="codigo" value="#{usuario2.codigo}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Usuario_nome}"/>
                            </f:facet>
                            <h:commandLink action="#{UsuarioControle.editar}" id="nome" value="#{usuario2.nome}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Usuario_username}"/>
                            </f:facet>
                            <h:commandLink action="#{UsuarioControle.editar}" id="username" value="#{usuario2.username}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Usuario_acesso}"/>
                            </f:facet>
                            <h:commandLink action="#{UsuarioControle.editar}" id="bloquearAcesso">
                                <h:outputText escape="false" value="<i class=\"fa-solid fa-user-lock\"></i>" rendered="#{usuario2.bloquearAcesso}" style="color: #990000;"/>
                                <h:outputText escape="false" value="<i class=\"fa-solid fa-circle-check\"></i>" rendered="#{!usuario2.bloquearAcesso}" style="color: #007700;"/>
                                <rich:spacer width="5px"/>
                                <h:outputText value="#{usuario2.bloquearAcesso_Apresentar}"/>
                            </h:commandLink>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Usuario_ultimoAcesso}"/>
                            </f:facet>
                            <h:commandLink action="#{UsuarioControle.editar}" id="dataUltimoAcesso" value="#{usuario2.dataUltimoAcesso_ApresentarDataNumero}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="#{msg_bt.btn_opcoes}"/>
                            </f:facet>
                            <h:commandButton action="#{UsuarioControle.editar}" value="#{msg_bt.btn_editar}" styleClass="botoes"/>
                            <a4j:commandButton id="listarPerfilAcesso" ignoreDupResponses="true" reRender="formListaPerfilAcesso"
                                               image="./imagens/informacao.gif"
                                               action="#{UsuarioControle.consultarPerfilAcesso}"
                                               oncomplete="Richfaces.showModalPanel('panelListaPerfilAcesso')"/>

                        </h:column>
                    </h:dataTable>
                    <rich:datascroller align="center" for="formCadastro:items"  id="scResultadoConsulta" />

                    <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                        <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                            <h:outputText styleClass="linhaMensagem"  value="#{UsuarioControle.mensagem}"/>
                            <h:outputText styleClass="mensagemDetalhada" value="#{UsuarioControle.mensagemDetalhada}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columns="2" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="novo" action="#{UsuarioControle.novo}" value="#{msg_bt.btn_novo}" styleClass="botoes"/>
                        <h:commandButton id="imprimirPDF" action="#{UsuarioControle.exportarListaConsultaParaPdf}" value="Exportar para PDF" styleClass="botoes"/>
                    </h:panelGrid>
                </h:panelGrid>        
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>

<script>
    document.getElementById("formCadastro:valorConsulta").focus();
</script>