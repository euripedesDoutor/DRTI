<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <title>Perfil Acesso</title>

    <title>
        <h:outputText value="#{PerfilAcessoControle.versaoSistema}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <h:form id="form">
            <h:commandLink action="#{PerfilAcessoControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%">
                <h:panelGrid columns="1" width="100%">
                    <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                        <h:panelGrid columns="2" styleClass="tabConsulta" width="100%">
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_PerfilAcesso_tituloForm}"/>
                            </f:facet>
                            <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_PerfilAcesso_nome}" />
                            <h:panelGroup>
                                <h:inputText  id="nome" size="70" maxlength="100" styleClass="camposObrigatorios" value="#{PerfilAcessoControle.perfilAcessoVO.nome}" rendered="#{PerfilAcessoControle.perfilAcessoVO.novoObj || (!PerfilAcessoControle.perfilAcessoVO.novoObj && PerfilAcessoControle.userNameIsAdmin)}" />
                                <h:outputText  styleClass="tituloCamposNegrito" value="#{PerfilAcessoControle.perfilAcessoVO.nome}"  rendered="#{!PerfilAcessoControle.perfilAcessoVO.novoObj && !PerfilAcessoControle.userNameIsAdmin}"/>
                                <rich:spacer width="100px"/>
                                <h:commandButton id="clonarPerfil" value="#{msg_bt.btn_clonarPerfilAcesso}" styleClass="botoes" rendered="#{!PerfilAcessoControle.perfilAcessoVO.novoObj}" action="#{PerfilAcessoControle.clonarPerfilAcesso}"/>
                                <h:message for="nome" styleClass="mensagemDetalhada"/>
                            </h:panelGroup>
                            <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_PerfilAcesso_administrador}" />
                            <h:selectBooleanCheckbox style="campos" value="#{PerfilAcessoControle.perfilAcessoVO.administrador}"  rendered="#{PerfilAcessoControle.perfilAcessoVO.novoObj || (!PerfilAcessoControle.perfilAcessoVO.novoObj && PerfilAcessoControle.userNameIsAdmin)}"/>
                            <h:outputText  styleClass="tituloCamposNegrito" value="#{PerfilAcessoControle.perfilAcessoVO.administrador_Apresentar}" rendered="#{!PerfilAcessoControle.perfilAcessoVO.novoObj && !PerfilAcessoControle.userNameIsAdmin}"/>

                            <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Permissao_tipoPerfil}" />
                            <h:selectOneMenu  id="tipo" styleClass="camposObrigatorios" value="#{PerfilAcessoControle.perfilAcessoVO.tipoPerfil}" rendered="#{PerfilAcessoControle.perfilAcessoVO.novoObj || (!PerfilAcessoControle.perfilAcessoVO.novoObj && PerfilAcessoControle.userNameIsAdmin)}">
                                <f:selectItems  value="#{PerfilAcessoControle.listaTipoPerfil}" />
                            </h:selectOneMenu>
                            <h:outputText  styleClass="tituloCamposNegrito" value="#{PerfilAcessoControle.perfilAcessoVO.tipoPerfil}"  rendered="#{!PerfilAcessoControle.perfilAcessoVO.novoObj && !PerfilAcessoControle.userNameIsAdmin}"/>

                        </h:panelGrid>




                        <h:panelGrid columns="1" width="100%" headerClass="subordinado" columnClasses="colunaCentralizada">
                            <f:facet name="header">
                                <h:outputText value="#{msg_aplic.prt_Permissao_Funcionalidades}"/>
                            </f:facet>
                            <h:panelGrid columns="2" width="100%" styleClass="tabFormSubordinada" footerClass="colunaCentralizada">
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Permissao_nomeModulos}" />
                                <h:selectOneMenu  id="Permissao_modulos" styleClass="camposObrigatorios" value="#{PerfilAcessoControle.modulo}" >
                                    <f:selectItems  value="#{PerfilAcessoControle.listaSelectItemModulos}" />
                                    <a4j:support event="onchange" action="#{PerfilAcessoControle.inicializarListaSelectItemTodasFuncionalidades}" reRender="Permissao_funcionalidades"/>
                                </h:selectOneMenu>
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Permissao_nomeEntidade}" />
                                <h:selectOneMenu  id="Permissao_funcionalidades" styleClass="camposObrigatorios" value="#{PerfilAcessoControle.permissaoVO.nomeEntidade}" >
                                    <f:selectItems  value="#{PerfilAcessoControle.listaSelectItemTodasFuncionalidades}" />
                                </h:selectOneMenu>                                
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Permissao_permissoes}" />
                                <h:selectOneMenu  id="Permissao_permissoes" styleClass="camposObrigatorios" value="#{PerfilAcessoControle.permissaoVO.permissoes}" >
                                    <f:selectItems  value="#{PerfilAcessoControle.listaSelectItemPermissoesPermissao}" />
                                </h:selectOneMenu>
                            </h:panelGrid>
                            <h:commandButton action="#{PerfilAcessoControle.adicionarPermissao}" value="#{msg_bt.btn_adicionar}" accesskey="5" styleClass="botoes"/>
                        </h:panelGrid>
                        <h:panelGrid columns="1" width="100%" styleClass="tabFormSubordinada">
                            <rich:dataTable id="dataTablePermissao" width="100%" headerClass="subordinado" styleClass="tabFormSubordinada"
                                            rowClasses="linhaImparSubordinado, linhaParSubordinado" columnClasses="colunaAlinhamento"
                                            value="#{PerfilAcessoControle.perfilAcessoVO.permissaoVOs}" var="permissao" rows="10">
                                <rich:column filterBy="#{permissao.tituloApresentacao}" filterEvent="onkeyup">
                                    <f:facet name="header" >
                                        <h:outputText  value="#{msg_aplic.prt_Permissao_nomeEntidade}" />
                                    </f:facet>
                                    <div align="left">
                                        <h:outputText  value="#{permissao.tituloApresentacao}" />
                                    </div>
                                </rich:column>
                                <rich:column filterBy="#{permissao.permissoes_Apresentar}" filterEvent="onkeyup">
                                    <f:facet name="header">
                                        <h:outputText  value="#{msg_aplic.prt_Permissao_permissoes}" />
                                    </f:facet>
                                    <%--<h:outputText  value="#{permissao.permissoes_Apresentar}" />--%>
                                    <h:selectOneMenu  id="permissao" styleClass="camposObrigatorios" value="#{permissao.temporaria}" >
                                        <a4j:support event="onchange" action="#{permissao.alterarPermissao}"/>
                                        <f:selectItems  value="#{PerfilAcessoControle.listaSelectItemPermissoesPermissao}" />
                                    </h:selectOneMenu>
                                </rich:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:commandButton id="removerPermissaoTodosMarcados" action="#{PerfilAcessoControle.removerPermissaoTodosMarcados}" style="height:20px;width:15px;" alt="Remover Marcados" image="./imagens/iconeLixeira.png"/>
                                    </f:facet>
                                    <h:panelGroup>
                                        <h:selectBooleanCheckbox id="remover" value="#{permissao.remover}">
                                            <a4j:support event="onclick" reRender="remover"/>
                                        </h:selectBooleanCheckbox>
                                    </h:panelGroup>
                                </h:column>
                            </rich:dataTable>

                        </h:panelGrid>
                        <rich:datascroller align="center" for="form:dataTablePermissao" reRender="form"/>
                        <h:panelGrid columns="2" width="100%" styleClass="tabMensagens">
                            <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                                <h:outputText styleClass="linhaMensagem"  value="#{PerfilAcessoControle.mensagem}"/>
                                <h:outputText styleClass="mensagemDetalhada" value="#{PerfilAcessoControle.mensagemDetalhada}"/>
                            </h:panelGrid>
                        </h:panelGrid>

                        <h:panelGrid columns="4" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                            <h:commandButton id="novo" immediate="true" action="#{PerfilAcessoControle.novo}" value="#{msg_bt.btn_novo}" accesskey="1" styleClass="botoes"/>
                            <h:commandButton id="salvar" action="#{PerfilAcessoControle.gravar}" value="#{msg_bt.btn_gravar}" accesskey="2" styleClass="botoes"/>
                            <h:commandButton id="excluir" onclick="return confirm('Confirma exclusão dos dados?');" action="#{PerfilAcessoControle.excluir}" value="#{msg_bt.btn_excluir}" accesskey="3" styleClass="botaoExcluir"/>
                            <h:commandButton id="consultar" immediate="true" action="#{PerfilAcessoControle.inicializarConsultar}" value="#{msg_bt.btn_consultar}" accesskey="4" styleClass="botoes"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>        
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:nome").focus();
</script>