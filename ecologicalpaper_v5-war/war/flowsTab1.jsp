<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<h:form>
	<t:messages id="messageList" styleClass="form" />
	<h:inputHidden id="id" value="#{Flows.tree.nodo}" />

</h:form>

<h:panelGrid rendered="#{Flows.swMostrarCatalogo}" columns="3"
	cellspacing="0" border="0" width="75%">

	<h:outputText styleClass="form" value="#{txt.flow_participantes}" />
	<f:verbatim>&nbsp;</f:verbatim>
	<h:outputText styleClass="form" value="#{txt.flows_role_Participantes}" />

	<h:selectManyListbox converter="ConverUsuarios" rendered="true"
		id="users" value="#{Flows.selectedUsuarios}" size="5"
		style="width: 300px;">

		<f:selectItems id="visibleUsersFlows"
			value="#{Flows.visibleUsersFlows}" />
	</h:selectManyListbox>

	<h:panelGrid columns="1">
		<f:verbatim></f:verbatim>
	</h:panelGrid>

	<h:selectManyListbox converter="ConverRoles" rendered="true" id="roles"
		value="#{Flows.selectedRoles}" size="5" style="width: 300px;">
		<f:selectItems id="visibleRolsFlows" value="#{Flows.visibleRoleFlows}" />
	</h:selectManyListbox>
</h:panelGrid>
<h:panelGrid rendered="#{Flows.swMostrarCatalogo}" columns="3"
	cellspacing="0" border="0" width="75%">
	<h:form>

		<h:outputText styleClass="form" value="#{txt.flow_tipos}" />
		<h:selectOneRadio id="r1" value="#{Flows.tipoFlow}"
			disabled="#{Flows.swDeshabilitarflowAprobacionParalelo}">
			<f:selectItem
				itemDisabled="#{Flows.swDeshabilitarflowAprobacion
			or !Flows.seguridadTree.toDoFlow}"
				itemValue="#{!Flows.flowAprobacion}"
				itemLabel="#{txt.flow_enAprobacion}" />
			<f:selectItem itemValue="#{Flows.flowAprobacion}"
				itemDisabled="#{!Flows.seguridadTree.toDoFlowRevision}"
				itemLabel="#{txt.flow_enRevision}" />
		</h:selectOneRadio>
		<f:verbatim></f:verbatim>


		<h:panelGrid columns="2">
			<%
				/*<!-- aqui esta si es plantilla o no checkbox -->*/
			%>
			<h:selectBooleanCheckbox
				disabled="#{Flows.swDeshabilitarflowAprobacionParalelo
				 or (!Flows.seguridadTree.toDoFlow)}"
				value="#{Flows.flow.plantilla}" immediate="true" />
			<h:outputText styleClass="form"
				value="#{txt.plantilla} #{txt.solo_flow_aprobacion}
				" />

			<h:selectBooleanCheckbox
				disabled="#{Flows.swDeshabilitarflowAprobacion
				 or (!Flows.seguridadTree.toDoFlow)}"
				value="#{Flows.flow.condicional}" immediate="true" />
			<h:outputText styleClass="form"
				value="#{txt.flow_Condicional} #{txt.solo_flow_aprobacion}" />


			 


			<h:selectBooleanCheckbox value="#{Flows.flow.secuencial}"
				immediate="true" />
			<h:outputText styleClass="form" value="#{txt.flow_Secuencial}" />

			<h:selectBooleanCheckbox
				disabled="#{Flows.swDeshabilitarEditaSolicitudimpresion}"
				value="#{Flows.flow.lectores}" immediate="true" />
			<h:outputText styleClass="form" value="#{txt.flow_lectores}" />

			<h:selectBooleanCheckbox rendered="#{Flows.swCanSolicitudimpresion}"
				value="#{Flows.swSolicitudimpresion}" immediate="true" />
			<h:outputText rendered="#{Flows.swCanSolicitudimpresion}"
				styleClass="form" value="#{txt.solicitudImpresion}" />


			<h:selectBooleanCheckbox value="#{Flows.flow.notificacionMail}"
				immediate="true" />
			<h:outputText styleClass="form" value="#{txt.flow_envio_mail}" />



		</h:panelGrid>
		<h:panelGrid columns="2" rendered="#{!Flows.flow.plantilla}">
			<h:commandButton image="img/admin_console_large.gif"
				styleClass="boton" styleClass="form"
				action="#{Flows.listarControlFlowByUsuario}" />
			<h:outputLabel value="#{txt.control_time_flow}" styleClass="form" />
		</h:panelGrid>


		<h:panelGrid columns="1">
			<h:panelGroup>
				<h:panelGrid columns="2">
					<h:commandButton value="#{txt.btn_cancelar}" styleClass="boton"
						action="#{Flows.cancelar}" />
					<h:commandButton styleClass="boton"
						onclick="this.style.display='none';" value="#{txt.siguiente}"
						action="#{Flows.saveDatosBasicos_action}" />
				</h:panelGrid>
			</h:panelGroup>
			<h:panelGroup>
				<h:panelGrid columns="1" border="0">
					<h:outputText styleClass="form"
						value="#{txt.struct_nombre} #{txt.flowsub}" />
					<h:inputText id="txt1" size="80" maxlength="79"
						disabled="#{Flows.swDeshabilitarEditaSolicitudimpresion}"
						value="#{Flows.flow.nombredelflujo}" immediate="false">
						<f:validator validatorId="caracteresinvalidos" />
					</h:inputText>
				</h:panelGrid>
			</h:panelGroup>
			<h:outputText styleClass="form" value="#{txt.doc_descripciontab}" />
			<h:inputTextarea cols="80" styleClass="form" rows="10"
				value="#{Flows.flow.comentarios}"></h:inputTextarea>

		</h:panelGrid>

		<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
			action="#{Flows.cancelar}" />

		<f:verbatim></f:verbatim>
		<h:commandButton styleClass="boton"
			onclick="this.style.display='none';" value="#{txt.siguiente}"
			action="#{Flows.saveDatosBasicos_action}" />


	</h:form>
</h:panelGrid>

