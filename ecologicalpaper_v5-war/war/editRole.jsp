<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<f:view>
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<%@include file="/inc/head.inc"%>
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

	<title><h:outputText styleClass="form"
		value="#{txt.role_editar}" /></title>
	</head>

	<body>

	<h:messages id="errors" style="color:red;font-weight:bold"
		layout="table" />
		<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
		
	<h:form>
		<h:inputHidden id="id" value="#{Rol.role.codigo}" />
		<h:panelGrid columns="2" border="0">

			<h:outputText styleClass="form" value="#{txt.role_nombre}" />
			<h:panelGroup>
				<h:inputText size="30" required="true" value="#{Rol.role.nombre}"
					immediate="false">
					<f:validateLength maximum="40" minimum="1" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:outputText styleClass="form" value="[#{Rol.role.empresa.nombre}]" />
			</h:panelGroup>


			<h:outputText styleClass="form" value="#{txt.role_descripcion}" />

			<h:inputTextarea cols="100" styleClass="form" rows="10"
				value="#{Rol.role.descripcion}"></h:inputTextarea>
			<%
				/*
																		<t:inputHtml value="#{Rol.role.descripcion}" style="width: 700px;"
																			allowEditSource="false" showPropertiesToolBox="false"
																			showLinksToolBox="false" showImagesToolBox="false"
																			showTablesToolBox="false" showDebugToolBox="false" />
							 */
			%>



		</h:panelGrid>



		<h:panelGrid rendered="#{!Rol.usadoParaCrearFlujo}" columns="3"
			cellspacing="0" border="0" width="75%">
			<h:outputText styleClass="form" value="#{txt.Operaciones_noSelec}" />
			<f:verbatim>&nbsp;</f:verbatim>
			<h:outputText styleClass="form" value="#{txt.Operaciones_Selec}" />

			<h:selectManyListbox converter="ConverOperaciones"
				value="#{Rol.selectedInvisibleItems}"
				size="#{Utilidades.verNumeroDeRegistros}" style="width: 500px;">
				<f:selectItems value="#{Rol.invisibleItems}" />
			</h:selectManyListbox>

			<h:panelGrid columns="1">
				<h:commandButton image="/images/arrow-next.gif"
					actionListener="#{Rol.moveSelectedToVisible}" style="width:20px;" />
				<h:commandButton image="/images/arrow-ff.gif"
					actionListener="#{Rol.moveAllToVisible}" style="width: 20px;" />
				<h:commandButton image="/images/arrow-fr.gif"
					actionListener="#{Rol.moveAllToInvisible}" style="width: 20px;" />
				<h:commandButton image="/images/arrow-previous.gif"
					actionListener="#{Rol.moveSelectedToInvisible}"
					style="width: 20px;" />
			</h:panelGrid>

			<h:selectManyListbox converter="ConverOperaciones"
				value="#{Rol.selectedVisibleItems}"
				size="#{Utilidades.verNumeroDeRegistros}" style="width: 500px;">
				<f:selectItems id="visibleItems" value="#{Rol.visibleItems}" />
			</h:selectManyListbox>
		</h:panelGrid>

		<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">

			<h:outputText styleClass="form"
				value="#{txt.Operaciones_noSelec} #{txt.menu_Usuario}" />
			<f:verbatim>&nbsp;</f:verbatim>
			<h:outputText styleClass="form"
				value="#{txt.Operaciones_Selec} #{txt.menu_Usuario}" />

			<h:selectManyListbox converter="ConverUsuarios"
				value="#{Rol.selectedInvisibleItems2}"
				size="#{Utilidades.verNumeroDeRegistros}" style="width: 500px;">
				<f:selectItems value="#{Rol.invisibleItems2}" />
			</h:selectManyListbox>

			<h:panelGrid columns="1">
				<h:commandButton image="/images/arrow-next.gif"
					actionListener="#{Rol.moveSelectedToVisible2}" style="width:20px;" />
				<h:commandButton image="/images/arrow-ff.gif"
					actionListener="#{Rol.moveAllToVisible2}" style="width: 20px;" />
				<h:commandButton image="/images/arrow-fr.gif"
					actionListener="#{Rol.moveAllToInvisible2}" style="width: 20px;" />
				<h:commandButton image="/images/arrow-previous.gif"
					actionListener="#{Rol.moveSelectedToInvisible2}"
					style="width: 20px;" />
			</h:panelGrid>

			<h:selectManyListbox converter="ConverUsuarios"
				value="#{Rol.selectedVisibleItems2}"
				size="#{Utilidades.verNumeroDeRegistros}" style="width: 500px;">
				<f:selectItems id="visibleItems2" value="#{Rol.visibleItems2}" />
			</h:selectManyListbox>

			<f:verbatim>&nbsp;</f:verbatim>
			<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" immediate="true"
				action="#{Rol.cancelarEditar}" />
			<h:commandButton styleClass="boton" value="#{txt.usuario_guardar}"
				action="#{Rol.saveRole}" />
		</h:panelGrid>

	</h:form>
	</body>
	</html>
</f:view>
