<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>
    <a4j:loadScript src="script/script.js" />    <a4j:loadStyle src="css/azul.css"/>
    <style type="text/css">body {margin: 0px;padding: 0px;}</style>    <link rel="shortcut icon" href="./favicon.ico" >
    <rich:modalPanel id="panelSair" autosized="true" shadowOpacity="true" width="350" height="160" >
        <f:facet name="header"><h:outputText value="CONFIRMAÇÃO"/></f:facet>
        <a4j:form id="formSair" ajaxSubmit="true">
            <h:panelGrid columns="1" width="350px" styleClass="colunaCentralizada">
                <h:outputText  styleClass="tituloCampos" value="Deseja realmente sair?" /><rich:spacer height="20px"/>
                <h:panelGrid columns="2" width="100%" columnClasses="colunaCentralizada" >
                    <a4j:commandButton ignoreDupResponses="true" action="#{LoginControle.logout}" oncomplete="Richfaces.hideModalPanel('panelSair');" reRender="form" value=" - #{msg_bt.btn_sim} - " styleClass="botoes"/>
                    <a4j:commandButton id="hidelinkSair" ignoreDupResponses="true" onclick="Richfaces.hideModalPanel('panelSair');" reRender="form" value=" - #{msg_bt.btn_nao} - " styleClass="botoes"/>
                </h:panelGrid>
            </h:panelGrid>
        </a4j:form>
    </rich:modalPanel>

    <rich:modalPanel id="panelMudarSenha" autosized="true" shadowOpacity="true" width="350" height="160" onshow="document.getElementById('formMudarSenha:senhaAtual').focus();">
        <f:facet name="header"><h:outputText value="#{msg_aplic.prt_mudarSenha}"/></f:facet>        
        <a4j:form id="formMudarSenha" ajaxSubmit="true">
            <h:panelGrid columns="1" width="350px" style="background-image:url('./imagens/chaves.png'); background-repeat: no-repeat" styleClass="colunaCentralizada">
                <h:panelGrid columns="2" width="100%" styleClass="colunaCentralizada" >
                    <h:outputText  styleClass="tituloCampos" value="Senha Atual" />
                    <h:inputSecret maxlength="50" id="senhaAtual" styleClass="campos" value="#{LoginControle.senha}"/>
                    <h:outputText  styleClass="tituloCampos" value="Nova Senha" />
                    <h:inputSecret maxlength="50" id="novaSenha" styleClass="campos" value="#{LoginControle.novaSenha}">
                        <a4j:support event="onkeyup" reRender="panelValidacoes"/>
                    </h:inputSecret>
                    <h:outputText  styleClass="tituloCampos" value="Confirmar Senha" />
                    <h:inputSecret maxlength="50"  styleClass="campos" value="#{LoginControle.novaSenha2}">
                        <a4j:support event="onkeyup" reRender="panelValidacoes"/>
                    </h:inputSecret>
                </h:panelGrid>
                <h:panelGrid columns="2" id="panelValidacoes" width="100%" styleClass="colunaCentralizada" >
                    <rich:spacer width="120px"/>
                    <h:outputText escape="false" value="#{LoginControle.labelTamanhoSenha}"/>
                    <rich:spacer width="10px"/>
                    <h:outputText escape="false" value="#{LoginControle.labelSenhaLetraMaiuscula}"/>
                    <rich:spacer width="10px"/>
                    <h:outputText escape="false" value="#{LoginControle.labelSenhaNumero}"/>
                    <rich:spacer width="10px"/>
                    <h:outputText escape="false" value="#{LoginControle.labelSenhaCaractereEspecial}"/>
                    <rich:spacer width="10px"/>
                    <h:outputText escape="false" value="#{LoginControle.labelSenhaIdenticos}"/>
                    <rich:spacer width="60px"/>
                    <h:outputText value="#{LoginControle.mensagemDetalhada}" style="#{LoginControle.cssMensagemMudarSenha}"/>
                    <rich:spacer width="60px"/>
                    <h:panelGroup>
                        <a4j:commandButton ignoreDupResponses="true" action="#{LoginControle.mudarSenha}" reRender="formMudarSenha" value="#{msg_bt.btn_gravar}" styleClass="#{LoginControle.cssBotaoMudarSenha}" disabled="#{!LoginControle.apresentarBotaoMudarSenha}"/>
                        <rich:spacer width="10px"/>
                        <a4j:commandButton ignoreDupResponses="true" reRender="form, formMudarSenha" oncomplete="Richfaces.hideModalPanel('panelMudarSenha')" value="#{msg_bt.btn_fechar}" styleClass="botoes"/>
                    </h:panelGroup>
                </h:panelGrid>
            </h:panelGrid>
        </a4j:form>
    </rich:modalPanel>

    <title><h:outputText value="DISCOVERY - #{LoginControle.versaoSistema}"/></title>
    <h:panelGrid columns="1" columnClasses="colunaCentralizada" rendered="false">
        <h:form id="form">
            <h:commandLink action="#{LoginControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" width="100%"  columnClasses="colunaCentralizada"  cellpadding="0" cellspacing="0" >
                <h:panelGrid columns="1" width="100%"  columnClasses="colunaCentralizada" style="position:relative; left:2px;" cellpadding="0" cellspacing="0" >
                    <f:facet name="header"><jsp:include page="topo.jsp"/></f:facet>
                </h:panelGrid>
                <h:panelGrid columns="1" style="background-image:url('./imagens/azul/fundoHome.png');background-repeat: no-repeat;background-position: center; height: 500px;" columnClasses="colunaCentralizada" width="100%" >
                    <a4j:region><a4j:poll interval="10000" id="poolInformarTelaAberta" action="#{LoginControle.atualizarTelaAberta}"/></a4j:region>
                    <h:panelGrid columns="3" width="130px">
                        <h:graphicImage value="/imagens/#{ConfiguracaoSistemaControle.nomeImagemBanner}" id="imagemBanner" style="width:520px;height:320px; vertical-align: center;"/>
                        <h:panelGrid width="150px" style="position:relative; top: -20px; left:100px;" id="panelBotaoCorreio"></h:panelGrid>
                    </h:panelGrid>
                    <f:facet name="footer">
                        <h:panelGrid columns="3" width="95%" columnClasses="colunaEsquerda, colunaCentralizada, colunaDireita" style="position:relative; top: -20px;">
                            <h:outputText id="empresaLogada" value="Empresa : #{LoginControle.empresaLogado.codigo} - #{LoginControle.empresaLogado.nomeFantasia}"/>
                            <h:outputText id="ultimoAcesso" value="Último Acesso : #{LoginControle.usuarioLogado.dataUltimoAcesso_Apresentar}"/>
                            <h:outputText id="nomeUsuarioLogado" value="Usuário : #{LoginControle.usuarioLogado.codigo} - #{LoginControle.usuarioLogado.nome}"/>
                        </h:panelGrid>
                    </f:facet>
                </h:panelGrid>
            </h:panelGrid>
            <rich:contextMenu event="oncontextmenu" attachTo="cidade" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="cidade" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
            <rich:contextMenu event="oncontextmenu" attachTo="pais" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="pais" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
            <rich:contextMenu event="oncontextmenu" attachTo="empresa" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="empresa" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
            <rich:contextMenu event="oncontextmenu" attachTo="configuracaoSistema" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="configuracaoSistema" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
            <rich:contextMenu event="oncontextmenu" attachTo="loginUsuario" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="loginUsuario" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
            <rich:contextMenu event="oncontextmenu" attachTo="perfilAcesso" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="perfilAcesso" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
            <rich:contextMenu event="oncontextmenu" attachTo="usuario" showDelay="250" submitMode="ajax">
                <rich:menuItem value="Adicionar aos Favoritos" submitMode="none">
                    <a4j:support event="onclick" action="#{LoginControle.prepararFavoritoParaApresentarModal}" oncomplete="Richfaces.showModalPanel('panelAdicionarFavorito');" reRender="formAdicionarFavorito">
                        <f:setPropertyActionListener target="#{LoginControle.favorito.entidade}" value="usuario" />
                    </a4j:support>
                </rich:menuItem>
            </rich:contextMenu>
        </h:form>
    </h:panelGrid>
</f:view>