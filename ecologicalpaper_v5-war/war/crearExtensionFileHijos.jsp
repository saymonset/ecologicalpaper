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
	
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.tipodearchivo}" /></h2>
	</center>
	<h:form>
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputText styleClass="form" value="#{txt.tipodeextensionuevo}" />
			<h:inputText id="nombre"
				value="#{extensionFileHijosCliente.extensionFileHijos.extension}"
				title="#{txt.usuario_nombre}" maxlength="20" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="nombre" styleClass="form" />

			<h:outputLabel styleClass="form" value="#{txt.tipodearchivo}" />
			<h:outputLabel id="inputTextDescripcion"
				value="#{extensionFileHijosCliente.extensionFileHijos.extensionFile.mimeType}" />
			<h:message styleClass="form" for="inputTextDescripcion" />


			<h:message styleClass="form" for="btncancel" />
			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
					action="#{extensionFileHijosCliente.cancelarListar}" />

				<h:commandButton styleClass="boton" action="#{extensionFileHijosCliente.create}"
					value="#{txt.usuario_guardar}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
	</h:form>




</f:view>



</body>

</html>


