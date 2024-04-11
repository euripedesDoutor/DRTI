<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<jsp:include page="loads.jsp"/>
<h:panelGrid width="100%" border="0"  columns="5" cellpadding="0" bgcolor="#FFFFFF" cellspacing="0" style="height:100px; background-image:url('./imagens/azul/topoDemoComEspacoLogoCliente.png');background-repeat: no-repeat;background-align: center;" columnClasses="colunaEsquerda">
    <rich:spacer width="90px"/>
    <h:graphicImage url="./imagens/logoNevera.jpg" style="position: relative; top: 15px;"/>
    <rich:spacer width="350px"/>
    <a4j:mediaOutput id="imagemBanner" element="img" style="width:150px;height:90px; vertical-align: center;position:relative; top: 3px;"  cacheable="false" session="true"
                     createContent="#{ConfiguracaoSistemaControle.paintImagemBanner}"  value="#{ImagemData}" mimeType="image/jpeg" >
        <f:param value="#{ConfiguracaoSistemaControle.timeStamp}" />
    </a4j:mediaOutput>
    <rich:spacer width="280px"/>
    <%--h:panelGrid width="936px" columns="1" cellpadding="0" cellspacing="0"  style="background-image:url('./imagens/azul/fundoMenu.png');background-repeat: repeat-x;" columnClasses="colunaEsquerda">

    </h:panelGrid--%>
</h:panelGrid>



