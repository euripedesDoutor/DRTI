<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<jsp:include page="loads.jsp"/>



<h:panelGrid columns="1" columnClasses="colunaCentralizada" width="100%">
    <h:panelGroup>
        <h:panelGroup>
            <h:outputText styleClass="tituloCamposReduzidos" value="Usuário: "/>
            <h:outputText styleClass="tituloCamposReduzidos" value="#{LoginControle.nome}"/>
            <rich:spacer width="5"/>
            <h:commandLink styleClass="tituloCamposReduzidos" action="#{LoginControle.logout}">
                <h:outputText value="(Logout)"/>
            </h:commandLink>
        </h:panelGroup>
        <rich:spacer width="30"/>
        <h:panelGroup>
            <h:outputText styleClass="tituloCamposReduzidos" value="Versão: #{LoginControle.versaoSistema}"/>
        </h:panelGroup>
        <h:panelGroup>
            <div>
                <form name="clock" onSubmit="0">
                    <input style="border: 0px; font: 8pt 'Trebuchet MS', verdana, arial, helvetica, sans-serif;color: #000000;text-align: right;" type="text" name="label" size=12 value="Hora: ">
                    <input style="border: 0px; font: 8pt 'Trebuchet MS', verdana, arial, helvetica, sans-serif;color: #000000;text-align: right;" type="text" name="face" size=12 value="">
                </form>
            </div>
        </h:panelGroup>
    </h:panelGroup>
</h:panelGrid>

<h:panelGrid columns="1" columnClasses="colunaCentralizada" width="100%">
    <h:panelGroup>

        <rich:spacer width="50"/>

    </h:panelGroup>
</h:panelGrid>    
