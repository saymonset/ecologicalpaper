<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@include file="/inc/head.inc"%>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<f:view>
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
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

	<script type="text/javascript" src="./validacione.js"></script>

	<title></title>
	</head>

	<body onLoad="javascript:refrescar();">
	<h:form>

		<t:jscookMenu layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<h:form>
		<h:panelGrid columns="2">

			<h2><h:outputText styleClass="form"
				value="#{txt.operacion_exitosa}" /></h2>
			<h2><h:outputText rendered="#{Exitoso.swArchNoCargados}"
				styleClass="form" value="#{Exitoso.archNoCargados}" /></h2>

			<h2><h:outputText styleClass="form"
				rendered="#{Exitoso.swMensaje}" value="#{Exitoso.mensaje}" /></h2>
			<h:commandButton styleClass="boton" value="#{txt.btn_aceptar}"
				action="#{Exitoso.regresarExitoso}" />
		</h:panelGrid>
	</h:form>




	</body>

	</html>
</f:view>


