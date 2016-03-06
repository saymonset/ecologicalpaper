<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<html>
<%@include file="/inc/head.inc"%>
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
	<title><h:outputText styleClass="form"
		value="#{txt.tipodearchivo}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.tipodearchivo}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputLabel styleClass="form" value="#{txt.tipodeextensionuevo}" />
			<h:inputText id="inputTextNombre"
				value="#{extensionFileHijosCliente.extensionFileHijos.extension}"
				immediate="false">
				<f:validateLength maximum="40" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />

			<h:outputLabel styleClass="form" value="#{txt.tipodearchivo}" />
			<h:outputLabel id="inputTextDescripcion"
				value="#{extensionFileHijosCliente.extensionFileHijos.extensionFile.mimeType}" />
			<h:message styleClass="form" for="inputTextDescripcion" />

			<h:message styleClass="form" for="btncancel" />
			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
					immediate="true"
					action="#{extensionFileHijosCliente.cancelarListar}" />
				<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
					action="#{extensionFileHijosCliente.saveObjeto}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
