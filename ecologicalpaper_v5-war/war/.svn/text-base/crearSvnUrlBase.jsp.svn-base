<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT>
	
</SCRIPT>
<html>
<head>
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(img/bg_left.jpg);
}
-->
</style>
</head>
<%@include file="/inc/head.inc"%>
<body>
<f:view>

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<h:messages />
	<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
	

	<!-- 
            CUERPO DEL MENSAJE 
            -->

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<title><h:outputText styleClass="form" value="#{txt.svn}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.svn}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputLabel styleClass="form" value="#{txt.doc_nombre}" />
		<h:inputText id="inputTextNombre" maxlength="119" size="119"
				value="#{svnUrlBaseCliente.svnUrlBase.nombre}" immediate="false">
				<f:validateLength maximum="120" minimum="4" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />

		
 

		</h:panelGrid>
		<h:panelGrid columns="3">
			<f:verbatim></f:verbatim>
			<h:panelGroup>
				<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
					action="#{svnUrlBaseCliente.cancelar}" />

				<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
					action="#{svnUrlBaseCliente.create}" />
			</h:panelGroup>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
