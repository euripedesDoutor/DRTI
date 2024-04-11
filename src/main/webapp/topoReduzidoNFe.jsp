<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<%@taglib prefix="a4j" uri="https://ajax4jsf.dev.java.net/ajax"%>
<%@taglib prefix="rich" uri="http://richfaces.ajax4jsf.org/rich"%>


<link rel="shortcut icon" href="./favicon.ico" >


    <a4j:status style="position:absolute; top:5px; left:10px" >
        <f:facet  name="start">
            <h:panelGroup style="position:absolute; top:5px; left:10px">
                <h:graphicImage width="30px"  value="./imagens/ajax.gif">
                </h:graphicImage>                
            </h:panelGroup>
        </f:facet>
    </a4j:status>
    <h:panelGrid width="100%" bgcolor="#000000" border="0"  columns="1" cellpadding="0" cellspacing="0" style="height:45px; background-image:url('../imagens/azul/topoDemoBackReduzido.png');" columnClasses="colunaDireita">
        <h:graphicImage height="45px" url="../imagens/azul/topoDemoReduzidoNFe.png"/>
    </h:panelGrid>

