<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<f:view>
    <jsp:include page="loads.jsp"/>
    <title>
        <h:outputText value="#{msg_aplic.prt_Entidade_tituloForm}"/>
    </title>

    <h:panelGrid columns="1" width="100%" cellpadding="0" cellspacing="0">
        <h:form id="form">
            <jsp:include page="topoReduzido.jsp"/>
            <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                <h:panelGrid columns="1" styleClass="tabForm" width="100%">
                    <h:panelGrid id="panelgrid1" columns="2" styleClass="tabConsulta" width="100%">
                        <f:facet name="header">
                            <h:outputText styleClass="tituloFormulario" value="#{msg_aplic.prt_Entidade_tituloForm}"/>
                        </f:facet>
                    </h:panelGrid>
                    <rich:tabPanel switchType="ajax" width="100%" id="abas">
                    <rich:tab label="#{msg_aplic.prt_Entidade_tituloForm}">
                    <h:panelGrid columns="2" styleClass="tabForm" width="100%">
                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Entidade_codigo}" rendered="#{!EntidadeControle.entidadeVO.novoObj}"/>
                        <h:outputText  styleClass="tituloCampos" value="#{EntidadeControle.entidadeVO.codigo}" rendered="#{!EntidadeControle.entidadeVO.novoObj}"/>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Entidade_projeto}"/>
                        <h:panelGroup id="painelProjeto">
                             <h:inputText onkeypress ="autoTab(this, event);" id="projeto" size="40"  styleClass="camposObrigatorios" value="#{EntidadeControle.entidadeVO.projeto.nomeApresentar}" />
                             <rich:suggestionbox
                                 height="100"
                                 width="450"
                                 for="projeto"
                                 fetchValue="#{result.nomeApresentar}"
                                 suggestionAction="#{EntidadeControle.autocompleteProjeto}"
                                 minChars="#{LoginControle.configuracaoEmpresaLogado.quantidadeDigitosAutoComplete}"
                                 nothingLabel="Nenhum registro de projeto encontrado"
                                 var="result"
                                 id="projetoSugggestionBox">
                                 <a4j:support event="onselect" focus="projeto"
                                              reRender="painelProjeto"
                                              action="#{EntidadeControle.consultarProjetoPorChavePrimaria}">
                                     <f:setPropertyActionListener
                                         target="#{EntidadeControle.entidadeVO.projeto}"
                                         value="#{result}" />
                                 </a4j:support>

                                 <h:column>
                                     <f:facet name="header">
                                         <h:outputText value="#{msg_aplic.prt_Projeto_nomeApresentar}" />
                                     </f:facet>
                                     <h:outputText value="#{result.nomeApresentar}" />
                                 </h:column>
                             </rich:suggestionbox>
                             <h:outputText escape="false" style="font: 7pt verdana, arial, helvetica, sans-serif;" value="<br>&nbsp;&nbsp;&nbsp;Mínimo #{LoginControle.configuracaoEmpresaLogado.quantidadeDigitosAutoComplete} caracteres" />
                         </h:panelGroup>

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Entidade_modulo}"/>
                        <h:panelGroup>
                            <h:selectOneMenu onkeypress ="autoTab(this, event);" id="modulo" styleClass="camposObrigatorios" value="#{EntidadeControle.entidadeVO.modulo.codigo}" >
                                <f:selectItems  value="#{EntidadeControle.listaSelectItemModulo}" />
                            </h:selectOneMenu>
                            <a4j:commandButton ignoreDupResponses="true" id="atualizar_Modulo" action="#{EntidadeControle.montarListaSelectItemModulo}" image="imagens/atualizar.png" immediate="true" ajaxSingle="true" reRender="form:modulo"/>
                        </h:panelGroup>


                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Entidade_nome}"/>
                        <h:inputText  id="nome" size="40"   styleClass="camposObrigatorios" value="#{EntidadeControle.entidadeVO.nome}" />

                        <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Entidade_nomeApresentar}"/>
                        <h:inputText  id="nomeApresentar" size="40"   styleClass="camposObrigatorios" value="#{EntidadeControle.entidadeVO.nomeApresentar}" />

                    </h:panelGrid>
                    </rich:tab>
                    <rich:tab label="#{msg_aplic.prt_Atributos_tituloForm}" id="abaAtributos">
                        <h:panelGrid columns="1" id="panelgridAtributos" width="100%" styleClass="colunaCentralizada">
                            <f:facet name="header">
                                <h:outputText styleClass="tituloFormulario" value="#{msg_aplic.prt_Atributos_tituloForm}"/>
                            </f:facet>
                            <h:panelGrid columns="2" width="100%" columnClasses="colunaDireita, colunaEsquerda">
                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_nome}"/>
                                <h:inputText  id="atributos_nome" size="40"   styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.nome}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_nomeApresentar}"/>
                                <h:inputText  id="atributos_nomeApresentar" size="40"   styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.nomeApresentar}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_tipoCampo}"/>
                                <h:selectOneMenu id="atributos_tipoCampo" styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.tipoCampo}" >
                                    <f:selectItems  value="#{EntidadeControle.atributosVO.listaSelectItemTipoCampo}" />
                                </h:selectOneMenu>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_tipoComponente}"/>
                                <h:selectOneMenu id="atributos_tipoComponente" styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.tipoComponente}" >
                                    <f:selectItems  value="#{EntidadeControle.atributosVO.listaSelectItemTipoComponente}" />
                                </h:selectOneMenu>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_campoObrigatorio}"/>
                                <h:selectBooleanCheckbox id="atributos_campoObrigatorio" value="#{EntidadeControle.atributosVO.campoObrigatorio}"/>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_campoConsulta}"/>
                                <h:selectBooleanCheckbox id="atributos_campoConsulta" value="#{EntidadeControle.atributosVO.campoConsulta}"/>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_campoDescricaoObjeto}"/>
                                <h:selectBooleanCheckbox id="atributos_campoDescricaoObjeto" value="#{EntidadeControle.atributosVO.campoDescricaoObjeto}"/>

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_mascara}"/>
                                <h:inputText  id="atributos_mascara" size="40"   styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.mascara}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_tamanho}"/>
                                <h:inputText  id="atributos_tamanho" size="10" styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.tamanho}" />

                                <h:outputText  styleClass="tituloCampos" value="#{msg_aplic.prt_Atributos_casasDecimais}"/>
                                <h:inputText  id="atributos_casasDecimais" size="10" styleClass="camposObrigatorios" value="#{EntidadeControle.atributosVO.casasDecimais}" />

                                <rich:spacer width="5px" height="40px"/>
                                <a4j:commandLink styleClass="btn" ignoreDupResponses="true" style="position: relative; top: 0px; width: 120px !important; background: #004400 !important;" id="atributos_adicionar" action="#{EntidadeControle.adicionarAtributos}" focus="atributos_nomeApresentar" reRender="panelgridAtributos">
                                    <i class="fas fa-plus-circle fa-2x" style="position: relative; top: 6px"></i>
                                    <h:outputText value="Adicionar" escape="false" style="font-size: 10pt;"/>
                                </a4j:commandLink>
                            </h:panelGrid>
                            <rich:dataTable id="listaAtributos" width="100%" headerClass="consulta" rowClasses="linhaImpar, linhaPar" columnClasses="colunaAlinhamento"
                                             value="#{EntidadeControle.entidadeVO.listaAtributos}" rows="10" var="atributos">
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Atributos_nomeApresentar}"/>
                                    </f:facet>
                                    <h:commandLink action="#{EntidadeControle.editarAtributos}" id="nomeApresentar" value="#{atributos.nomeApresentar}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Atributos_tipoCampo}"/>
                                    </f:facet>
                                    <h:commandLink action="#{EntidadeControle.editarAtributos}" id="tipoCampo" value="#{atributos.tipoCampo_Apresentar}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_aplic.prt_Atributos_tipoComponente}"/>
                                    </f:facet>
                                    <h:commandLink action="#{EntidadeControle.editarAtributos}" id="tipoComponente" value="#{atributos.tipoComponente_Apresentar}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="#{msg_bt.btn_opcoes}"/>
                                    </f:facet>
                                    <a4j:commandLink styleClass="btnFit" style="position: relative; top: 0px; width: 120px !important; background: #007700 !important;" action="#{EntidadeControle.editarAtributos}" reRender="form">
                                        <i class="fas fa-edit fa-lg"></i>
                                        <h:outputText value="Editar" escape="false" style="font-size: 10pt;"/>
                                    </a4j:commandLink>
                                    <rich:spacer width="10px"/>
                                    <a4j:commandLink styleClass="btnFit" style="position: relative; top: 0px; width: 120px !important; background: #660000 !important;" action="#{EntidadeControle.removerAtributos}" reRender="form">
                                       <i class="far fa-window-close fa-lg"></i>
                                       <h:outputText value="Remover" escape="false" style="font-size: 10pt;"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="center" for="form:listaAtributos"  id="scResultadoListaAtributos" />

                        </h:panelGrid>
                    </rich:tab>
                    </rich:tabPanel>
                    <h:panelGrid columns="1" width="100%" styleClass="tabMensagens">
                        <h:outputText styleClass="mensagem"  value="#{EntidadeControle.mensagem}"/>
                        <h:outputText styleClass="mensagemDetalhada" value="#{EntidadeControle.mensagemDetalhada}"/>
                        <h:panelGrid columns="4" width="100%" columnClasses="colunaCentralizada">
                            <h:commandLink styleClass="btn" style="position: relative; top: 0px; width: 120px !important; background: #004400 !important;" id="novo" action="#{EntidadeControle.novo}">
                                <i class="far fa-plus-square fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Novo" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                            <a4j:commandLink onclick="desabilitarEnquantoProcessa(this)" ignoreDupResponses="true" styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #004444 !important;" id="salvar" action="#{EntidadeControle.gravar}" reRender="form">
                                <i class="far fa-save fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Gravar" escape="false" style="font-size: 10pt;"/>
                            </a4j:commandLink>
                            <h:commandLink styleClass="btn" onclick="return confirm('Confirma exclusão dos dados?');" style="position: relative; top: -3px; width: 120px !important; background: #660000 !important;" id="excluir" action="#{EntidadeControle.excluir}">
                                <i class="far fa-trash-alt fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Excluir" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                            <h:commandLink styleClass="btn" style="position: relative; top: -3px; width: 120px !important; background: #000044 !important;" id="consultar" action="#{EntidadeControle.inicializarConsultar}">
                                <i class="fas fa-search fa-2x" style="position: relative; top: 6px"></i>
                                <h:outputText value="Consultar" escape="false" style="font-size: 10pt;"/>
                            </h:commandLink>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </h:panelGrid>
        </h:form>
    </h:panelGrid>
</f:view>
<script>
    document.getElementById("form:nomeApresentar").focus();
    var enviado = false;
    function desabilitarEnquantoProcessa(button){
        if(!enviado){
            enviado = true;
            button.value='Aguarde...';
            button.disabled=true;
        }
        return true;
    }
</script>
