<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<jsp:include page="loads.jsp"/>
<h:panelGrid width="100%" border="0"  columns="1" cellpadding="0" bgcolor="#FFFFFF" cellspacing="0" style="height:100px; background-image:url('./imagens/azul/topoDemoBack.png');background-repeat: repeat-x;" columnClasses="colunaCentralizada">
    <h:graphicImage url="./imagens/azul/topoDemo.png"/>
    <h:panelGrid width="936px" columns="1" cellpadding="0" cellspacing="0" columnClasses="colunaEsquerda"  style="background-image:url('./imagens/azul/fundoMenu.png');background-repeat: repeat-x;">
        <jsp:include page="menuBasico_1.jsp"/>
    </h:panelGrid>
</h:panelGrid>



