<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>


<h:messages styleClass="form" />
<h:form>
	<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">
		<h:outputText styleClass="form"
			value="#{SeguridadRole.tree.nombre}  [#{Flows_Role.tree.descripcion}]" />
		<f:verbatim>&nbsp;</f:verbatim>


	</h:panelGrid>

	<h:panelGrid  columns="3" cellspacing="0" border="0"
		width="75%">

		<f:verbatim></f:verbatim>
		<h:commandButton styleClass="boton"  rendered="#{Flows_Role.swIniciar}"
			value="#{txt.Tab_desbloquear}" action="#{Flows_Role.editFlows_Role}" />

		<h:commandButton styleClass="boton" rendered="#{Flows_Role.swIniciar}"
			value="#{txt.usuario_guardar}" action="#{Flows_Role.saveRole}" />

	</h:panelGrid>


	<h:panelGrid columns="3" id="panelCombo" cellspacing="0" border="0"
		width="75%" rendered="#{Flows_Role.swIniciar}">

		<h:outputText styleClass="form" value="#{txt.menu_listarRole}" />
		<f:verbatim>&nbsp;</f:verbatim>
		<h:outputText styleClass="form" value="#{txt.flows_role}" />



		<h:selectManyListbox converter="ConverRoles"
			value="#{Flows_Role.selectedInvisibleItems}" size="25"
			style="width: 300px;">

			<f:selectItems value="#{Flows_Role.invisibleItems}" />
		</h:selectManyListbox>

		<h:panelGrid columns="1">
			<h:commandButton image="/images/arrow-next.gif"
				actionListener="#{Flows_Role.moveSelectedToVisible}"
				style="width:20px;" />
			<h:commandButton image="/images/arrow-ff.gif"
				actionListener="#{Flows_Role.moveAllToVisible}" style="width: 20px;" />
			<h:commandButton image="/images/arrow-fr.gif"
				actionListener="#{Flows_Role.moveAllToInvisible}"
				style="width: 20px;" />
			<h:commandButton image="/images/arrow-previous.gif"
				actionListener="#{Flows_Role.moveSelectedToInvisible}"
				style="width: 20px;" />
		</h:panelGrid>

		<h:selectManyListbox converter="ConverRoles"
			value="#{Flows_Role.selectedVisibleItems}" size="25"
			style="width: 300px;">
			<f:selectItems id="visibleItems" value="#{Flows_Role.visibleItems}" />
		</h:selectManyListbox>

		<%
			/*  POPUP MENU LEYENDA */
		%>
		<h:outputText styleClass="form" value="#{txt.role_leyenda}" />
		<f:verbatim></f:verbatim>
		<f:verbatim></f:verbatim>

		<h:panelGroup id="popup">
			<t:dataTable id="dataPopup" headerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="role" value="#{Rol.rolesFlow_popup}" preserveDataModel="true">
				<h:column>
					<h:panelGrid columns="3">
						<t:popup id="a" closePopupOnExitingElement="true"
							closePopupOnExitingPopup="true" displayAtDistanceX="10"
							displayAtDistanceY="10">
							<h:outputText id="oa" styleClass="form" value="#{role.nombre}" />
							<f:facet name="popup">
								<h:panelGroup>
									<h:panelGrid columns="1">
										<h:outputText value="#{role.lista}" />
									</h:panelGrid>
								</h:panelGroup>
							</f:facet>
						</t:popup>

						<t:popup id="b" styleClass="popup"
							closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
							displayAtDistanceX="10" displayAtDistanceY="10">
							<h:outputText id="ob" styleClass="form"
								value="#{txt.usuario_varios}" />
							<f:facet name="popup">
								<h:panelGroup>
									<h:panelGrid columns="1">
										<h:outputText value="#{role.usuarios}" />
									</h:panelGrid>
								</h:panelGroup>
							</f:facet>
						</t:popup>
					</h:panelGrid>
				</h:column>
			</t:dataTable>
		</h:panelGroup>
		<f:verbatim></f:verbatim>
		<f:verbatim></f:verbatim>
		<%
			/* fin POPUP MENU LEYENDA */
		%>
	</h:panelGrid>

	<h:panelGrid id="panelButon" columns="3" cellspacing="0" border="0"
		width="75%">

		<h:commandButton styleClass="boton" id="idmas" rendered="#{!Flows_Role.swIniciar}"
			value="+" action="#{Flows_Role.editFlows_Role}" />
		<h:commandButton styleClass="boton" id="idmenos" rendered="#{Flows_Role.swIniciar}"
			value="#{txt.Tab_desbloquear}" action="#{Flows_Role.editFlows_Role}" />

		<h:commandButton styleClass="boton" rendered="#{Flows_Role.swIniciar}"
			value="#{txt.usuario_guardar}" action="#{Flows_Role.saveRole}" />

	</h:panelGrid>


</h:form>

