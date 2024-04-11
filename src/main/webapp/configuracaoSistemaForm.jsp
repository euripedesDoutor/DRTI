<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>

    <jsp:include page="loads.jsp"/>
    <style>
        .btnMenu {
            background: #3498db;
            background-image: -webkit-linear-gradient(top, #3498db, #2980b9) !important;
            background-image: -moz-linear-gradient(top, #3498db, #2980b9) !important;
            background-image: -ms-linear-gradient(top, #3498db, #2980b9) !important;
            background-image: -o-linear-gradient(top, #3498db, #2980b9) !important;
            background-image: linear-gradient(to bottom, #3498db, #2980b9) !important;
            -webkit-border-radius: 13 !important;
            -moz-border-radius: 13 !important;
            border-radius: 30px !important;
            font-family: Arial !important;
            color: #ffffff !important;
            font-size: 50px !important;
            padding: 20px 50px 20px 50px !important;
            width: 200px !important;
            text-decoration: none !important;
        }
    </style>
    <title>
        <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_tituloForm}"/>
    </title>


    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <f:facet name="header">
            <f:verbatim>
                <jsp:include page="topoReduzido.jsp"/>
            </f:verbatim>
        </f:facet>

        <h:form id="form">
            <h:commandLink action="#{ConfiguracaoSistemaControle.liberarBackingBeanMemoria}" id="idLiberarBackingBeanMemoria" style="display: none" />
            <h:panelGrid columns="1" styleClass="tabconteudo" width="100%">
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid columns="2" styleClass="tabConsulta" width="100%">
                        <f:facet name="header">
                            <h:outputText value="#{msg_aplic.prt_ConfiguracaoSistema_tituloForm}"/>
                        </f:facet>
                        <rich:tabPanel switchType="ajax" >
                            <rich:tab label="Geral">
                                <h:panelGrid columns="2" id="panelImagens" styleClass="tabConsulta" rowClasses="linhaImpar, linhaPar" width="100%">
                                    <h:outputText  styleClass="tituloCampos" value="Imagem Banner" />
                                    <h:panelGrid columns="2"  width="100%" >

                                        <rich:fileUpload listHeight="40"
                                                         listWidth="150"
                                                         noDuplicate="true"
                                                         fileUploadListener="#{ConfiguracaoSistemaControle.upLoadImagemBanner}"
                                                         maxFilesQuantity="1"
                                                         addControlLabel="Adicionar"
                                                         cancelEntryControlLabel="Cancelar"
                                                         doneLabel="Pronto"
                                                         sizeErrorLabel="Arquivo não Enviado. Tamanho Máximo Permitido 100 MB"
                                                         progressLabel="Enviando"
                                                         stopControlLabel="Parar"
                                                         uploadControlLabel="Enviar"
                                                         transferErrorLabel="Falha de Transmissão"
                                                         stopEntryControlLabel="Parar"
                                                         id="uploadImagemBanner"
                                                         immediateUpload="true"
                                                         autoclear="true"
                                                         acceptedTypes="jpg,png,PNG">
                                            <a4j:support event="onuploadcomplete" reRender="form" />
                                        </rich:fileUpload>
                                        <a4j:mediaOutput element="img" id="imagem1" style="width:100px;height:65px "  rendered="#{ConfiguracaoSistemaControle.existeImagemBanner}" cacheable="false" session="true"
                                                         createContent="#{ConfiguracaoSistemaControle.paintImagemBanner}"  value="#{ImagemData}" mimeType="image/jpeg" >
                                            <f:param value="#{ConfiguracaoSistemaControle.timeStamp}" />
                                        </a4j:mediaOutput>
                                        <%--h:panelGrid style="width:100px;height:65px "/--%>


                                    </h:panelGrid>



                                    <h:outputText  styleClass="tituloCampos" value="Imagem Relatório" />
                                    <h:panelGrid columns="2"  width="100%" >

                                        <rich:fileUpload listHeight="40"
                                                         listWidth="150"
                                                         noDuplicate="true"
                                                         fileUploadListener="#{ConfiguracaoSistemaControle.upLoadImagemRelatorio}"
                                                         maxFilesQuantity="1"
                                                         addControlLabel="Adicionar"
                                                         cancelEntryControlLabel="Cancelar"
                                                         doneLabel="Pronto"
                                                         sizeErrorLabel="Arquivo não Enviado. Tamanho Máximo Permitido 100 MB"
                                                         progressLabel="Enviando"
                                                         stopControlLabel="Parar"
                                                         uploadControlLabel="Enviar"
                                                         transferErrorLabel="Falha de Transmissão"
                                                         stopEntryControlLabel="Parar"
                                                         id="uploadImagemRelatorio"
                                                         immediateUpload="true"
                                                         autoclear="true"
                                                         acceptedTypes="jpg,jpeg,JPG,JPEG,PNG,png">
                                            <a4j:support event="onuploadcomplete" reRender="form" />
                                        </rich:fileUpload>
                                        <a4j:mediaOutput element="img" id="imagem2" style="width:100px;height:65px "  rendered="#{ConfiguracaoSistemaControle.existeImagemRelatorio}" cacheable="false" session="true"
                                                         createContent="#{ConfiguracaoSistemaControle.paintImagemRelatorio}"  value="#{ImagemData}" mimeType="image/jpeg" >
                                            <f:param value="#{ConfiguracaoSistemaControle.timeStamp}" />
                                        </a4j:mediaOutput>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </rich:tab>

                            <rich:tab label="Segurança" rendered="#{ConfiguracaoSistemaControle.userNameIsAdmin}">
                                <h:panelGrid id="panelSeguranca" columns="2" width="100%" columnClasses="colunaDireita, colunaEsquerda" styleClass="tabConsulta" rowClasses="linhaImpar, linhaPar">
                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaPrazoExpiracaoSenha}"/>
                                    <h:panelGroup>
                                        <h:inputText id="segurancaPrazoExpiracaoSenha" size="4" styleClass="campos" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaPrazoExpiracaoSenha}">
                                            <a4j:support event="onchange" reRender="panelSeguranca" focus="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaPrazoExpiracaoSenhaFoco}"/>
                                        </h:inputText>
                                        <h:outputText styleClass="tituloCampos" value="dias"/>
                                    </h:panelGroup>
                                    <rich:spacer width="5px"/>
                                    <h:outputText styleClass="tituloCamposVermelho" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaPrazoExpiracaoSenhaValidacao}"/>

                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaTentativasAcessoAntesBloqueio}"/>
                                    <h:panelGroup>
                                        <h:inputText id="segurancaTentativasAcessoAntesBloqueio" size="4" styleClass="campos" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaTentativasAcessoAntesBloqueio}">
                                            <a4j:support event="onchange" reRender="panelSeguranca" focus="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaTentativasAcessoAntesBloqueioFoco}"/>
                                        </h:inputText>
                                        <h:outputText styleClass="tituloCampos" value="tentativas"/>
                                    </h:panelGroup>
                                    <rich:spacer width="5px"/>
                                    <h:outputText styleClass="tituloCamposVermelho" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaTentativasAcessoAntesBloqueioValidacao}"/>

                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaHorasParaZerarTentativasAcesso}"/>
                                    <h:panelGroup>
                                        <h:inputText id="segurancaHorasParaZerarTentativasAcesso" size="4" styleClass="campos" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaHorasParaZerarTentativasAcesso}">
                                            <a4j:support event="onchange" reRender="panelSeguranca" focus="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaHorasParaZerarTentativasAcessoFoco}"/>
                                        </h:inputText>
                                        <h:outputText styleClass="tituloCampos" value="horas"/>
                                    </h:panelGroup>
                                    <rich:spacer width="5px"/>
                                    <h:outputText styleClass="tituloCamposVermelho" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaHorasParaZerarTentativasAcessoValidacao}"/>

                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaTamanhoSenhaUsuario}"/>
                                    <h:inputText id="segurancaTamanhoSenhaUsuario" size="4" styleClass="campos" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaTamanhoSenhaUsuario}">
                                        <a4j:support event="onchange" reRender="panelSeguranca" focus="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaTamanhoSenhaUsuarioFoco}"/>
                                    </h:inputText>
                                    <rich:spacer width="5px"/>
                                    <h:outputText styleClass="tituloCamposVermelho" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaTamanhoSenhaUsuarioValidacao}"/>

                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaSenhaExigirLetraMaiuscula}"/>
                                    <h:selectBooleanCheckbox id="segurancaSenhaExigirLetraMaiuscula" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaSenhaExigirLetraMaiuscula}"/>

                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaSenhaExigirNumero}"/>
                                    <h:selectBooleanCheckbox id="segurancaSenhaExigirNumero" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaSenhaExigirNumero}"/>

                                    <h:outputText styleClass="tituloCampos" value="#{msg_aplic.prt_ConfiguracaoSistema_segurancaSenhaExigirCaractereEspecial}"/>
                                    <h:selectBooleanCheckbox id="segurancaSenhaExigirCaractereEspecial" value="#{ConfiguracaoSistemaControle.configuracaoSistemaVO.segurancaSenhaExigirCaractereEspecial}"/>

                                </h:panelGrid>
                            </rich:tab>
                        </rich:tabPanel>
                    </h:panelGrid>

                    <h:panelGrid columns="1" width="100%" styleClass="tabMensagens" columnClasses="colunaEsquerda">
                        <h:outputText styleClass="linhaMensagem"  value="#{ConfiguracaoSistemaControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{ConfiguracaoSistemaControle.mensagemDetalhada}"/>
                    </h:panelGrid>

                    <h:panelGrid columns="1" width="100%" styleClass="tabBotoes" columnClasses="colunaCentralizada">
                        <h:commandButton id="salvar" action="#{ConfiguracaoSistemaControle.gravar}" value="#{msg_bt.btn_gravar}" accesskey="2" styleClass="botoes"/>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:tela").focus();
</script>