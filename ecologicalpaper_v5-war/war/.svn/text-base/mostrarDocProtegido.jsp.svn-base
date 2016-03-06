<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page import="com.ecological.util.ContextSessionRequest"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<html>
<head>
<title>
<%
	String parameters = ContextSessionRequest.parseParameters(request);
	String file = "mostrarDocProtegidowithservlet.jsf" + parameters;
	String pie = "" + parameters;
%>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript">
	function descargar() {
		window.opener.setNull();
	}
</script>

<frameset rows="*,20" cols="*" frameborder="NO" border="0"
	framespacing="0" onUnload="javascript:descargar();">
	<frame src="<%=file%>" name="doc" scrolling="YES" noresize />
	<!--<frame src= name="info">-->
</frameset>
<body>
</body>

<noframes>
</noframes>
</html>
