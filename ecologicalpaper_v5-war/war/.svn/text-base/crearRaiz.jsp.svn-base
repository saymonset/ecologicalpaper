<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>
  
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>


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
<script type="text/javascript" src="./validacione.js"></script>
<body onLoad="javascript:refrescar();">
<f:view>
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<h:messages />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt_ecolo" />
		<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
		
	<title><h:outputText value="#{txt_ecolo.struct_crearArbol}" /></title>

	<h:form id="forma1">
		<h:panelGrid columns="3" id="panelGrid1">


			<h:panelGroup>
				<h:outputLabel value="#{txt_ecolo.struct_nombre}" styleClass="form" />
				<h:inputText id="inputTextNombre" value="#{tree.tree.nombre}"
					maxlength="40" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
			</h:panelGroup>
			<h:message styleClass="form" for="inputTextNombre" />
			<f:verbatim></f:verbatim>

		</h:panelGrid>
		<h:panelGrid columns="1">
			<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">
				<h:outputLabel value="#{txt_ecolo.struct_descripcion}"
					styleClass="form" />
				<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{tree.tree.descripcion}"></h:inputTextarea>

				<%
					/*	<t:inputHtml value="#{tree.tree.descripcion}" style="width: 700px;"
									 allowEditSource="false" showPropertiesToolBox="false"
									 showLinksToolBox="false" showImagesToolBox="false"
									 showTablesToolBox="false" showDebugToolBox="false" />*/
				%>

				<h:message styleClass="form" for="inputTextDescripcion" />

			</h:panelGrid>
			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" action="menu" />

				<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
					action="#{tree.ingresarnodoRaiz_action}">
				</h:commandButton>
			</h:panelGrid>
		</h:panelGrid>

	</h:form>
</f:view>
</body>
</html>
