<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<h:messages styleClass="form" />
<h:form>
	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">


		<h:outputText rendered="#{!Flows_User.swIniciar}" styleClass="form"
			value="#{txt.usuario_varios}" />


		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>


	</h:panelGrid>

	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">
		<f:verbatim></f:verbatim>
		<h:commandButton rendered="#{Flows_User.swIniciar}"
		styleClass="boton"
			value="#{txt.Tab_desbloquear}" action="#{Flows_User.editFlows_User}">/>
        </h:commandButton>

		<h:commandButton styleClass="boton" rendered="#{Flows_User.swIniciar}"
			value="#{txt.usuario_guardar}"
			action="#{Flows_User.saveUsuario_Operaciones}" />

	</h:panelGrid>

	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%"
		rendered="#{Flows_User.swIniciar}">

		<h:outputText styleClass="form" value="#{txt.usuario_varios}" />
		<f:verbatim>&nbsp;</f:verbatim>
		<h:outputText styleClass="form" value="#{txt.flow_users}" />



		<h:selectManyListbox converter="ConverUsuarios"
			value="#{Flows_User.selectedInvisibleItems}" size="25"
			style="width: 300px;">
			<f:selectItems value="#{Flows_User.invisibleItems}" />
		</h:selectManyListbox>

		<h:panelGrid columns="1">
			<h:commandButton image="/images/arrow-next.gif"
				actionListener="#{Flows_User.moveSelectedToVisible}"
				style="width:20px;" />
			<h:commandButton image="/images/arrow-ff.gif"
				actionListener="#{Flows_User.moveAllToVisible}" style="width: 20px;" />
			<h:commandButton image="/images/arrow-fr.gif"
				actionListener="#{Flows_User.moveAllToInvisible}"
				style="width: 20px;" />
			<h:commandButton image="/images/arrow-previous.gif"
				actionListener="#{Flows_User.moveSelectedToInvisible}"
				style="width: 20px;" />
		</h:panelGrid>

		<h:selectManyListbox converter="ConverUsuarios"
			value="#{Flows_User.selectedVisibleItems}" size="25"
			style="width: 300px;">
			<f:selectItems id="visibleItems" value="#{Flows_User.visibleItems}" />
		</h:selectManyListbox>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>
	</h:panelGrid>

	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">
		<h:commandButton styleClass="boton" rendered="#{!Flows_User.swIniciar}" value="+"
			action="#{Flows_User.editFlows_User}">
			<f:param id="editId" name="id" value="#{Seguridad.usuario.id}" />
		</h:commandButton>
		<h:commandButton styleClass="boton" rendered="#{Flows_User.swIniciar}"
			value="#{txt.Tab_desbloquear}" action="#{Flows_User.editFlows_User}">/>
        </h:commandButton>

		<h:commandButton styleClass="boton" rendered="#{Flows_User.swIniciar}"
			value="#{txt.usuario_guardar}"
			action="#{Flows_User.saveUsuario_Operaciones}" />

	</h:panelGrid>


</h:form>
