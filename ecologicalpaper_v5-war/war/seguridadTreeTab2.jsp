<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

  

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<h:messages styleClass="form" />
<h:form>
	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">


		<h:outputText styleClass="form" value="#{txt.usuario_varios}" />
		<h:outputText styleClass="form" rendered="#{Seguridad_tab2.swIniciar}"
			value="#{Seguridad_tab2.usuario.nombre} 
                              #{Seguridad_tab2.usuario.apellido}  [#{Seguridad_tab2.usuario.cargo.nombre}]" />
		<f:verbatim>&nbsp;</f:verbatim>
		<h:selectOneMenu rendered="#{!Seguridad_tab2.swIniciar}"
			binding="#{Seguridad_tab2.selectItemUsuario}" id="selectOneMenu1"
			converter="ConverUsuarios" value="#{Seguridad_tab2.usuario}">
			<f:selectItems value="#{DatosCombo.usuarios}" />
		</h:selectOneMenu>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>


	</h:panelGrid>

	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">
		<f:verbatim></f:verbatim>
		<h:commandButton styleClass="boton" rendered="#{Seguridad_tab2.swIniciar}"
			value="#{txt.Tab_desbloquear}"
			action="#{Seguridad_tab2.editSeguridad_User}">/>
        </h:commandButton>
	
		<h:commandButton styleClass="boton" rendered="#{Seguridad_tab2.swIniciar}"
			value="#{txt.usuario_guardar}"
			action="#{Seguridad_tab2.saveUsuario_Operaciones}" />
 
	</h:panelGrid>

	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%"
		rendered="#{Seguridad_tab2.swIniciar}">
		<h:panelGroup>
			<h:selectBooleanCheckbox value="#{Seguridad_tab2.heredaSeguridad}"
				immediate="true" />
			<h:outputText styleClass="form" value="#{txt.heredarPermisos}" />
			<h:outputText styleClass="grees"
				value=".#{Seguridad_tab2.heredaDependeDeTipoNodo}" />
		</h:panelGroup>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>

		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>

		<h:outputText styleClass="form" value="#{txt.Operaciones_noSelec}" />
		<f:verbatim>&nbsp;</f:verbatim>
		<h:outputText styleClass="form" value="#{txt.Operaciones_Selec}" />



		<h:selectManyListbox converter="ConverOperaciones"
			value="#{Seguridad_tab2.selectedInvisibleItems}" size="30"
			style="width: 300px;">
			<f:selectItems value="#{Seguridad_tab2.invisibleItems}" />
		</h:selectManyListbox>

		<h:panelGrid columns="1">
			<h:commandButton image="/images/arrow-next.gif"
				actionListener="#{Seguridad_tab2.moveSelectedToVisible}"
				style="width:20px;" />
			<h:commandButton image="/images/arrow-ff.gif"
				actionListener="#{Seguridad_tab2.moveAllToVisible}"
				style="width: 20px;" />
			<h:commandButton image="/images/arrow-fr.gif"
				actionListener="#{Seguridad_tab2.moveAllToInvisible}"
				style="width: 20px;" />
			<h:commandButton image="/images/arrow-previous.gif"
				actionListener="#{Seguridad_tab2.moveSelectedToInvisible}"
				style="width: 20px;" />
		</h:panelGrid>

		<h:selectManyListbox converter="ConverOperaciones"
			value="#{Seguridad_tab2.selectedVisibleItems}" size="30"
			style="width: 300px;">
			<f:selectItems id="visibleItems"
				value="#{Seguridad_tab2.visibleItems}" />
		</h:selectManyListbox>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>
		<f:verbatim>&nbsp;</f:verbatim>
	</h:panelGrid>

	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">
		<h:commandButton styleClass="boton" rendered="#{!Seguridad_tab2.swIniciar}" value="+"
			action="#{Seguridad_tab2.editSeguridad_User}">
			<f:param id="editId" name="id" value="#{Seguridad.usuario.id}" />
		</h:commandButton>

		<h:commandButton styleClass="boton" rendered="#{Seguridad_tab2.swIniciar}"
			value="#{txt.Tab_desbloquear}"
			action="#{Seguridad_tab2.editSeguridad_User}">/>
        </h:commandButton>
		<%
			/* GRABAR DATA */
		%>
		<h:commandButton styleClass="boton" rendered="#{Seguridad_tab2.swIniciar}"
			value="#{txt.usuario_guardar}"
			action="#{Seguridad_tab2.saveUsuario_Operaciones}" />

		<h:commandButton styleClass="boton" rendered="#{!Seguridad_tab2.swIniciar}"
			value="#{txt.btn_cancelar}" action="menu" />
	</h:panelGrid>


</h:form>
