<%@page pageEncoding="UTF-8" %>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>


<f:view>

    <head>
        <link rel="shortcut icon" href="./favicon.ico">
        <title><h:outputText value="DRTI - #{LoginControle.versaoSistema}"/></title>
        <link rel="stylesheet" href="./home/css/style.css">
        <link rel="stylesheet" href="./home/css/boxicons.min.css">
        <link rel="stylesheet" href="./home/css/font-awesome.css">
        <link rel="stylesheet" href="./fonts/css/icons.css">
        <a4j:loadScript src="script/script.js" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    <h:form id="form">
        <rich:modalPanel id="panelSair" autosized="true" shadowOpacity="true" width="350" height="160" >
            <f:facet name="header"><h:outputText value="CONFIRMAÇÃO"/></f:facet>
                <h:panelGrid columns="1" width="350px" styleClass="colunaCentralizada">
                    <h:outputText  styleClass="tituloCampos" value="Deseja realmente sair?" /><rich:spacer height="20px"/>
                    <h:panelGrid columns="2" width="100%" columnClasses="colunaCentralizada" >
                        <a4j:commandButton ignoreDupResponses="true" action="#{LoginControle.logout}" oncomplete="Richfaces.hideModalPanel('panelSair');" reRender="form" value=" - SIM - " styleClass="botoes"/>
                        <a4j:commandButton id="hidelinkSair" ignoreDupResponses="true" onclick="Richfaces.hideModalPanel('panelSair');" reRender="form" value=" - NÃO - " styleClass="botoes"/>
                    </h:panelGrid>
                </h:panelGrid>
        </rich:modalPanel>
        <div class="sidebar">
            <div class="logo-details">
                <div class="logo_name">
                    <img src="./home/images/logo.png" alt="profileImg" width="150px;">
                </div>
                <i class='bx bx-menu' id="btn"></i>
            </div>
            <ul class="nav-list">
                <h:panelGroup>
                    <li>
                        <h:commandLink id="dashboard" action="#{LoginControle.selecionarFrameDashboard}">
                            <i class='bx bx-grid-alt'></i>
                            <span class="links_name">Dashboard</span>
                        </h:commandLink>
                        <span class="tooltip">Dashboard</span>
                    </li>
                </h:panelGroup>

                <h:panelGroup rendered="#{LoginControle.permissaoAcessoMenuVO.projeto}">
                    <li>
                        <h:commandLink id="projeto" action="#{LoginControle.selecionarFrameProjeto}">
                            <i class='bx bx-building'></i>
                            <span class="links_name">Projeto</span>
                        </h:commandLink>
                        <span class="tooltip">Projeto</span>
                    </li>
                </h:panelGroup>

                <h:panelGroup rendered="#{LoginControle.permissaoAcessoMenuVO.entidade}">
                    <li>
                        <h:commandLink id="entidade" action="#{LoginControle.selecionarFrameEntidade}">
                            <i class='bx bx-building'></i>
                            <span class="links_name">Entidade</span>
                        </h:commandLink>
                        <span class="tooltip">Entidade</span>
                    </li>
                </h:panelGroup>

                <h:panelGroup rendered="#{LoginControle.permissaoAcessoMenuVO.empresa}">
                    <li>
                        <h:commandLink id="empresa" action="#{LoginControle.selecionarFrameEmpresa}">
                            <i class='bx bx-building'></i>
                            <span class="links_name">Empresa</span>
                        </h:commandLink>
                        <span class="tooltip">Empresa</span>
                    </li>
                </h:panelGroup>
                <h:panelGroup rendered="#{LoginControle.permissaoAcessoMenuVO.perfilAcesso}">
                    <li>
                        <h:commandLink id="perfilAcesso" action="#{LoginControle.selecionarFramePerfilAcesso}">
                            <i class='bx bxs-user-detail'></i>
                            <span class="links_name">Perfil de Acesso</span>
                        </h:commandLink>
                        <span class="tooltip">Perfil de Acesso</span>
                    </li>
                </h:panelGroup>
                <h:panelGroup rendered="#{LoginControle.permissaoAcessoMenuVO.usuario}">
                    <li>
                        <h:commandLink id="usuario" action="#{LoginControle.selecionarFrameUsuario}">
                            <i class='bx bx-user'></i>
                            <span class="links_name">Usuário</span>
                        </h:commandLink>
                        <span class="tooltip">Usuário</span>
                    </li>
                </h:panelGroup>

                <h:panelGroup rendered="#{LoginControle.permissaoAcessoMenuVO.configuracaoSistema}">
                    <li>
                        <h:commandLink id="configuracaoSistema" action="#{LoginControle.selecionarFrameConfiguracao}">
                            <i class='fas fa-cog'></i>
                            <span class="links_name">Configurações</span>
                        </h:commandLink>
                        <span class="tooltip">Configurações</span>
                    </li>
                </h:panelGroup>
                <li>
                    <a4j:commandLink oncomplete="Richfaces.showModalPanel('panelSair')">
                        <i class='bx bx-log-out'></i>
                        <span class="links_name">Sair</span>
                    </a4j:commandLink>
                    <span class="tooltip">Sair</span>
                </li>
                <li class="profile">
                    <div class="profile-details">
                        <div class="name_job">
                            <div class="name"><h:outputText id="nomeUsuarioLogado" value="User: #{LoginControle.usuarioLogado.codigo} - #{LoginControle.usuarioLogado.nome}"/></div>
                            <div class="job"><h:outputText id="ultimoAcesso" value="Último Acesso: #{LoginControle.usuarioLogado.dataUltimoAcesso_Apresentar}"/></div>
                        </div>
                    </div>
                    <i class='bx' id="log_out" ></i>
                </li>
            </ul>
        </div>
        <section class="home-section">
            <div id="frames" class="frame">
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFrameDashboard}">
                    <iframe src="imagens/dashboard.jpg" frameborder="0"  width="100%" height="100%" ></iframe>
                </h:panelGrid>
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFrameProjeto}">
                    <iframe src="projetoCons.jsp" frameborder="0" width="100%" height="100%"></iframe>
                </h:panelGrid>
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFrameEntidade}">
                    <iframe src="entidadeCons.jsp" frameborder="0" width="100%" height="100%"></iframe>
                </h:panelGrid>
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFrameEmpresa}">
                    <iframe src="empresaCons.jsp" frameborder="0" width="100%" height="100%"></iframe>
                </h:panelGrid>
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFramePerfilAcesso}">
                    <iframe src="perfilAcessoCons.jsp" frameborder="0" width="100%" height="100%"></iframe>
                </h:panelGrid>
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFrameUsuario}">
                    <iframe src="usuarioCons.jsp" frameborder="0" width="100%" height="100%"></iframe>
                </h:panelGrid>
                <h:panelGrid width="100%" style="height:95%" rendered="#{LoginControle.apresentarFrameConfiguracao}">
                    <iframe src="configuracaoSistemaForm.jsp" frameborder="0" width="100%" height="100%"></iframe>
                </h:panelGrid>
            </div>

        </section>
    </h:form>
    </body>
</f:view>
<script src="./home/script/script.js"></script>