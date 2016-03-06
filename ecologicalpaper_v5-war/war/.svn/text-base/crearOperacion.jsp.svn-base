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


	<h:messages />
<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt_ecolo" />
	<title><h:outputText value="#{txt_ecolo.Operaciones_crear}" /></title>
	<h1></h1>
	<p></p>
	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputLabel value="#{txt_ecolo.Operaciones_nombre}"
				styleClass="form" />
			<h:inputText id="inputTextNombre" required="true"
				value="#{Operaciones.operaciones.operacion}" maxlength="40"
				immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />

			<h:outputLabel value="#{txt_ecolo.Operaciones_descripcion}"
				styleClass="form" />
			<h:inputText id="inputTextDescripcion" required="true"
				value="#{Operaciones.operaciones.descripcion}" maxlength="40"
				immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextDescripcion" />
			<h:commandButton styleClass="boton" value="#{txt_ecolo.btn_aceptar}"
				action="#{Operaciones.create}"
				actionListener="#{Operaciones.actionListener}" />
		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
