<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<%@taglib prefix="rich" uri="http://richfaces.org/rich" %>
<%@taglib prefix="jsfChart" uri="http://sourceforge.net/projects/jsf-comp" %>

<f:view>
    <style>
        .vermelho {
            background-color: #550000;
        }

        .azul {
            background-color: #000077;
        }

        .verde {
            background-color: #007700;
        }

        .you {
            background-color: #007700;
            color: #FFFFFF;
            padding: 2px 7px 2px 7px !important;
            border-radius: 5px;
        }

        .box {
            padding: 10px !important;
            height: 100px;
            color: #FFFFFF;
            font-size: 16pt;
        }

        .labelNumero {
            font-size: 42pt;
            color: #FFFFFF;
        }

        .labelTipoPerfil {
            font-size: 8pt;
            color: #FFFFFF;
        }

        .iconeBox {
            opacity: 0.6;
        }
    </style>

    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="Usuários Logados no Sistema"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <a4j:form id="form">
            <h:panelGrid columns="1" id="painel">
                <a4j:poll interval="5000" id="poll" reRender="form"
                          action="#{LoginControle.removerUsuariosTelaFechada}"/>
                <h:panelGrid columns="3">
                    <h:panelGrid columns="1" width="320px" styleClass="box azul" columnClasses="colunaCentralizada">
                        <h:panelGroup>
                            <i class="fas fa-sitemap fa-2x iconeBox"></i>
                            <rich:spacer width="20px"/>
                            <h:outputText value="Conectados"/>
                        </h:panelGroup>
                        <h:panelGrid columns="3" width="320px" columnClasses="colunaCentralizada, colunaCentralizada, colunaCentralizada">
                            <h:outputText styleClass="labelNumero" value="#{LoginControle.quantidadeUsuariosLoginEmpresaGerencial}"/>
                            <h:outputText styleClass="labelNumero" value="|"/>
                            <h:outputText styleClass="labelNumero" value="#{LoginControle.quantidadeUsuariosLoginEmpresaVendedorExterno}"/>
                            <h:outputText styleClass="labelTipoPerfil" value="Gerencial"/>
                            <rich:spacer width="20px"/>
                            <h:outputText styleClass="labelTipoPerfil" value="Vend. Externo"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" width="320px" styleClass="box verde" columnClasses="colunaCentralizada">
                        <h:panelGroup>
                            <i class="far fa-thumbs-up fa-2x iconeBox"></i>
                            <rich:spacer width="20px"/>
                            <h:outputText value="Disponíveis"/>
                        </h:panelGroup>
                        <h:panelGrid columns="3" width="320px" columnClasses="colunaCentralizada, colunaCentralizada, colunaCentralizada">
                            <h:outputText styleClass="labelNumero" value="#{LoginControle.empresaLogado.limiteUsuariosSimultaneos - LoginControle.quantidadeUsuariosLoginEmpresaGerencial}"/>
                            <h:outputText styleClass="labelNumero" value="|"/>
                            <h:outputText styleClass="labelNumero" value="#{LoginControle.empresaLogado.limiteUsuariosSimultaneosVendedorExterno - LoginControle.quantidadeUsuariosLoginEmpresaVendedorExterno}"/>
                            <h:outputText styleClass="labelTipoPerfil" value="Gerencial"/>
                            <rich:spacer width="20px"/>
                            <h:outputText styleClass="labelTipoPerfil" value="Vend. Externo"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" width="320px" styleClass="box vermelho" columnClasses="colunaCentralizada">
                        <h:panelGroup>
                            <i class="far fa-handshake fa-2x iconeBox"></i>
                            <rich:spacer width="20px"/>
                            <h:outputText value="Contratados"/>
                        </h:panelGroup>
                        <h:panelGrid columns="3" width="320px" columnClasses="colunaCentralizada, colunaCentralizada, colunaCentralizada">
                            <h:outputText styleClass="labelNumero" value="#{LoginControle.empresaLogado.limiteUsuariosSimultaneos}"/>
                            <h:outputText styleClass="labelNumero" value="|"/>
                            <h:outputText styleClass="labelNumero" value="#{LoginControle.empresaLogado.limiteUsuariosSimultaneosVendedorExterno}"/>
                            <h:outputText styleClass="labelTipoPerfil" value="Gerencial"/>
                            <rich:spacer width="20px"/>
                            <h:outputText styleClass="labelTipoPerfil" value="Vend. Externo"/>
                        </h:panelGrid>
                    </h:panelGrid>

                </h:panelGrid>
                <rich:dataTable id="usuariosLogados" width="1000px" headerClass="consulta" styleClass="tabConsulta"
                                rowClasses="linhaImpar, linhaPar"
                                columnClasses="colunaCentralizada, colunaCentralizada, colunaEsquerda, colunaEsquerda, colunaEsquerda, colunaEsquerda, colunaCentralizada"
                                value="#{LoginControle.listaUsuariosLoginEmpresa}" rows="10" var="loginUsuario">
                    <rich:column width="100px" sortBy="#{loginUsuario.dataLogin_Apresentar}" sortOrder="DESCENDING">
                        <f:facet name="header">
                            <h:outputText value="Data Login"/>
                        </f:facet>
                        <h:outputText value="#{loginUsuario.dataLogin_Apresentar}"/>
                    </rich:column>
                    <rich:column width="70px" sortBy="#{loginUsuario.tempoLogado}">
                        <f:facet name="header">
                            <h:outputText value="Tempo"/>
                        </f:facet>
                        <h:outputText value="#{loginUsuario.tempoLogado}"/>
                    </rich:column>
                    <rich:column width="150px" sortBy="#{loginUsuario.username}">
                        <f:facet name="header">
                            <h:outputText value="Username"/>
                        </f:facet>
                        <h:panelGroup>
                            <h:outputText value="#{loginUsuario.username}"/>
                            <rich:spacer width="5px"/>
                            <h:outputText value="Você" styleClass="you"
                                          rendered="#{loginUsuario.username == LoginControle.username}"/>
                        </h:panelGroup>
                    </rich:column>
                    <rich:column width="220px" sortBy="#{loginUsuario.nome}">
                        <f:facet name="header">
                            <h:outputText value="Nome"/>
                        </f:facet>
                        <h:outputText value="#{loginUsuario.nome}"/>
                    </rich:column>
                    <rich:column width="200px" sortBy="#{loginUsuario.perfilAcesso}">
                        <f:facet name="header">
                            <h:outputText value="Perfil de Acesso"/>
                        </f:facet>
                        <h:outputText value="#{loginUsuario.perfilAcesso}"/>
                    </rich:column>
                    <rich:column width="150px" sortBy="#{loginUsuario.tipoPerfilAcesso_Apresentar}">
                        <f:facet name="header">
                            <h:outputText value="Tipo Perfil"/>
                        </f:facet>
                        <h:outputText value="#{loginUsuario.tipoPerfilAcesso_Apresentar}"/>
                    </rich:column>
<%--                    <rich:column width="100px" sortBy="#{loginUsuario.ip}">--%>
<%--                        <f:facet name="header">--%>
<%--                            <h:outputText value="Ip"/>--%>
<%--                        </f:facet>--%>
<%--                        <h:outputText value="#{loginUsuario.ip}"/>--%>
<%--                    </rich:column>--%>
                    <rich:column width="150px">
                        <f:facet name="header">
                            <h:outputText value="Ações"/>
                        </f:facet>
                        <a4j:commandLink ignoreDupResponses="true" styleClass="btnFit"
                                         style="position: relative; top: -3px; width: 120px !important; background: #770000 !important;"
                                         id="abrirModalParcelaRP" action="#{LoginControle.removerAcessoUsuario}"
                                         reRender="form" rendered="#{loginUsuario.username != LoginControle.username}">
                            <i class="fas fa-user-times fa-1x" style="position: relative; top: 0px"></i>
                            <h:outputText value="Derrubar Acesso" escape="false" style="font-size: 10pt;"/>
                        </a4j:commandLink>
                    </rich:column>

                </rich:dataTable>
                <rich:datascroller align="center" for="form:usuariosLogados" id="scUsuariosLogados"/>
            </h:panelGrid>
        </a4j:form>
    </h:panelGrid>
</f:view>
