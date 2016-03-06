<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@page import="javax.servlet.http.HttpSession,com.ecological.paper.usuario.Usuario,
com.util.Utilidades,java.util.Date"%>

<%
Usuario usu= session.getAttribute("user_logueado")!=null?(Usuario)session.getAttribute("user_logueado"):null;
Date fecha = new Date();
String cadenaFecha = Utilidades.sdfShowWithoutHour.format(fecha);
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/frameset.dtd">
<!-- saved from url=(0051)http://www.entornocreativo.com/saymon/principal.htm -->
<HTML><HEAD><TITLE>:: Ecological Paper ::<%=usu%>:<%=cadenaFecha%> </TITLE>
        <META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
        <STYLE type=text/css>BODY {
               BACKGROUND-IMAGE: none
               }
               </STYLE>
        
        
        <meta http-equiv=Content-Type content="text/html; charset=windows-1252">
        <meta content="MSHTML 6.00.2800.1106" name=GENERATOR>
    </HEAD>
    
<frameset rows="90,*" cols="*"  >
  <frame src="tope.jsf" name="topFrame" framespacing="0" noresize  border="0" frameBorder=0 >
   <frameset border="10" frameSpacing="2" rows="*" frameBorder="2" cols="18%,*" bordeColor="#FF0000">
        <frameset border="0" frameSpacing="0" rows="0,*" frameBorder="0" cols="0" bordeColor="#FF0000">
            <frame name="menu" marginWidth=0 marginHeight=0 src="" frameBorder=0 scrolling=true>
            <frame name="leftFrame" marginWidth=0 marginHeight=5 src="./arbol.jsf" frameBorder=0>
        </frameset>
        <FRAME name=mainFrame src="./aplicacion.jsf"> 
        <%/*<!--
        <FRAME name=mainFrame src="./PuentedeTask.jsf?idroot=4&parametro=publico">
        -->*/%>
    </FRAMESET>
</frameset>
</HTML>


