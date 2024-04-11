<%@taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@taglib prefix="rich" uri="http://richfaces.ajax4jsf.org/rich" %>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<f:loadBundle var="msg_menu" basename="propriedades.menu"/>
<a4j:loadStyle src="css/azul.css"/>


<rich:toolBar itemSeparator="line" width="100%" style="border: 1px;">
    <rich:dropDownMenu rendered="#{!LoginControle.permissaoAcessoMenuVO.groupCadastros}">
        <f:facet name="label">
            <h:panelGroup>
                <h:outputText rendered="#{!LoginControle.permissaoAcessoMenuVO.groupCadastros}"
                              styleClass="tituloCamposMenu" value="#{msg_menu.Menu_cadastros}"/>
                <h:outputText rendered="#{LoginControle.permissaoAcessoMenuVO.groupCadastros}"
                              styleClass="tituloCamposMenuDesabilitado" value="#{msg_menu.Menu_cadastros}"/>
            </h:panelGroup>
        </f:facet>
        <rich:menuGroup value="#{msg_menu.Menu_basico}"
                        disabled="#{LoginControle.permissaoAcessoMenuVO.groupCadastrosBasico}">
            <rich:menuItem id="cidade" value="#{msg_menu.Menu_cidade}"
                           onclick="abrirPopup('cidadeCons.jsp', 'cidade', 780, 595);"
                           rendered="#{LoginControle.permissaoAcessoMenuVO.cidade}" submitMode="ajax"/>
            <rich:menuItem id="pais" value="#{msg_menu.Menu_pais}"
                           onclick="abrirPopup('paisCons.jsp', 'pais', 780, 595);"
                           rendered="#{LoginControle.permissaoAcessoMenuVO.pais}" submitMode="ajax"/>
            <rich:menuItem id="empresa" value="#{msg_menu.Menu_empresa}"
                           onclick="abrirPopup('empresaCons.jsp', 'Empresa', 780, 595);"
                           rendered="#{LoginControle.permissaoAcessoMenuVO.empresa && LoginControle.userNameIsAdmin}" submitMode="ajax"/>
        </rich:menuGroup>
    </rich:dropDownMenu>

    <rich:dropDownMenu>
        <f:facet name="label">
            <h:panelGroup>
                <h:outputText styleClass="tituloCamposMenu" value="#{msg_menu.Menu_Administrador}"/>
            </h:panelGroup>
        </f:facet>
        <rich:menuItem id="configuracaoSistema" value="#{msg_menu.Menu_configuracaoSistema}"
                       onclick="abrirPopupMaximizada('configuracaoSistemaForm.jsp', 'ConfiguracaoSistema');"
                       rendered="#{LoginControle.permissaoAcessoMenuVO.configuracaoSistema}" submitMode="ajax"/>
        <rich:menuItem id="loginUsuario" value="#{msg_menu.Menu_loginUsuario}"
                       onclick="abrirPopup('loginUsuario.jsp', 'LoginUsuario', 1050, 900);"
                       rendered="#{LoginControle.permissaoAcessoMenuVO.loginUsuario}" submitMode="ajax"/>
        <rich:menuItem id="perfilAcesso" value="#{msg_menu.Menu_perfilAcesso}"
                       onclick="abrirPopup('perfilAcessoCons.jsp', 'PerfilAcesso', 780, 595);"
                       rendered="#{LoginControle.permissaoAcessoMenuVO.perfilAcesso}" submitMode="ajax"/>
        <rich:menuItem id="usuario" value="#{msg_menu.Menu_usuario}"
                       onclick="abrirPopup('usuarioCons.jsp', 'Usuario', 780, 595);"
                       rendered="#{LoginControle.permissaoAcessoMenuVO.usuario}" submitMode="ajax"/>
        <rich:menuItem value="#{msg_menu.Menu_mudarSenha}" oncomplete="Richfaces.showModalPanel('panelMudarSenha')" action="#{LoginControle.limparMensagemAlteracaoSenha}" reRender="formMudarSenha:panelValidacoes"
                       submitMode="ajax"/>
    </rich:dropDownMenu>
    <rich:toolBarGroup location="right">
        <rich:dropDownMenu>
            <f:facet name="label">
                <a4j:commandLink oncomplete="Richfaces.showModalPanel('panelSair')">
                    <h:outputText styleClass="tituloCamposMenu" value="#{msg_menu.prt_Logout_titulo}"/>
                </a4j:commandLink>
            </f:facet>
        </rich:dropDownMenu>
    </rich:toolBarGroup>
</rich:toolBar>
