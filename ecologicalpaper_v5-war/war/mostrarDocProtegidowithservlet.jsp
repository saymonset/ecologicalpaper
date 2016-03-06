<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.ecological.util.ContextSessionRequest"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%
 
    String parameters = ContextSessionRequest.parseParameters(request);
    String file = "ClienteDocumentoGenerarFlowParalelo.jsf" + parameters;
    String pie = "" + parameters;

%>
<script>
    function sendForm() {
        document.documento.submit();
    }
</script>
</head>
<body onLoad="javascript:sendForm();">
    <form name="documento" action="<%=file%>" method="Post" >
        

    </form>
</body>
</html>

 