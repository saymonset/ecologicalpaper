<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@page import="javax.servlet.http.HttpSession"%>


<script language="javascript">
	
</script>
<html>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
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

<SCRIPT>
	var formId; // reference to the main form
	var winId; // reference to the popup window
	// This function calls the popup window.
	//
	function showFlowUser(action, title) {
		features = "height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
</SCRIPT>
<script>
	function ChequearTodos(chkbox) {

		palabras = new Array(document.forms[0].elements.length);
		var j = -1;
		for ( var i = 0; i < (document.forms[0].elements.length); i++) {
			var nombre = document.forms[0].elements[i];
			if (nombre.name.charAt(nombre.name.lastIndexOf('I')) == 'I') {
				palabras[++j] = nombre.name
						.substring(0, nombre.name.length - 1);
			}
		}
		arreglo = new Array(j);
		for ( var k = 0; k <= j; k++) {
			arreglo[k] = palabras[k];
		}
		for ( var l = 0; l <= j; l++) {
			var nom0 = arreglo[l];
			for ( var i = 0; i < (document.forms[0].elements.length); i++) {
				var nom1 = document.forms[0].elements[i];
				if (nom0 == nom1.name) {
					if ((nom1.type == "hidden")) {
						if (chkbox.checked == false) {
							nom1.value = 0;
						} else {
							nom1.value = 1;
						}
					}
				}
			}
			for ( var i = 0; i < (document.forms[0].elements.length); i++) {
				var elemento = document.forms[0].elements[i];
				if (elemento.type == "checkbox") {
					elemento.checked = chkbox.checked
				}
			}
		}
	}
</script>
</head>



<body>
<f:view>

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />


	<h:messages />

	<!-- 
            CUERPO DEL MENSAJE 
            -->
	<h:form>
		<a4j:region renderRegionOnly="true" id="stat3">

			<%
				/* <!--	<h1><h:outputText styleClass="form" value="#{txt.search_buscar}" />-->*/
			%>


			<h:panelGrid columns="2">
				<h:commandButton value="#{txt.btn_menu}"
					action="#{ClienteUsuario.listarSolicitudImpresion}" />




			</h:panelGrid>

			<h:panelGroup id="body">

				<h:panelGrid columns="3">
					<f:verbatim></f:verbatim>
					<h:outputText styleClass="form" value="#{txt.solicitudImpresion}" />
					<f:verbatim></f:verbatim>
					<h:panelGroup></h:panelGroup>
					<h:panelGroup></h:panelGroup>
					<h:panelGroup></h:panelGroup>
					<h:outputText styleClass="form" value="#{txt.fecha} #{txt.desde}" />
					<t:inputCalendar id="fechadesdeimprimir"
						disabled="#{ClienteUsuario.swSolicitudimpresion }"
						monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
						popupButtonStyleClass="standard_bold"
						currentDayCellClass="currentDayCell"
						value="#{ClienteUsuario.solicitudimpresion.fechadesdeimprimir}"
						renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
						popupDateFormat="dd.MM.yyyy"
						popupWeekString="#{txt.popup_week_string}" helpText="dd.MM.yyyy"
						forceId="true" />
					<h:message for="fechadesdeimprimir" styleClass="form" />

					<h:outputText styleClass="form" value="#{txt.fecha} #{txt.hasta}" />
					<t:inputCalendar id="fechahastaimprimir"
						disabled="#{ClienteUsuario.swSolicitudimpresion }"
						monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
						popupButtonStyleClass="standard_bold"
						currentDayCellClass="currentDayCell"
						value="#{ClienteUsuario.solicitudimpresion.fechahastaimprimir}"
						renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
						popupDateFormat="dd.MM.yyyy"
						popupWeekString="#{txt.popup_week_string}" helpText="dd.MM.yyyy"
						forceId="true" />
					<h:message for="fechahastaimprimir" styleClass="form" />
<%/*
<!--					<h:outputText styleClass="form" value="#{txt.numcopias}" />-->
<!--					<h:inputText disabled="#{ClienteUsuario.swSolicitudimpresion }"-->
<!--						id="numcopias" maxlength="20"-->
<!--						value="#{ClienteUsuario.solicitudimpresion.numcopias}"-->
<!--						immediate="false">-->
<!--						<f:validator validatorId="caracteresinvalidos" />-->
<!--					</h:inputText>-->
<!--					<h:message for="numcopias" showDetail="true" />-->*/%>

					<h:outputText styleClass="form" value="#{txt.doc_descripciontab}" />
					<h:inputTextarea id="comentarios" cols="40" rows="3"
						disabled="#{ClienteUsuario.swSolicitudimpresion }"
						value="#{ClienteUsuario.solicitudimpresion.comentarios}"
						immediate="false">
						<f:validator validatorId="caracteresinvalidos" />
					</h:inputTextarea>
					<h:message for="comentarios" showDetail="true" />

					<h:outputText rendered="#{!ClienteUsuario.swSolicitudimpresion }"
						id="seleccionados" styleClass="form"
						value="#{txt.seleccionartodos}" />
					<h:selectBooleanCheckbox
						rendered="#{!ClienteUsuario.swSolicitudimpresion }" id="selectchk"
						value="" onclick="ChequearTodos(this);" />
					<h:message for="seleccionados" showDetail="true" />
				</h:panelGrid>
				<h:panelGrid columns="1" binding="#{backing_login.panelGrid1}"
					id="panelGrid1">

					<h:outputText rendered="#{ClienteUsuario.swSolicitudimpresion }"
						styleClass="form"
						value="#{txt.solicitudImpresion} #{txt.pendiente}" />

					<h:commandButton
						rendered="#{ClienteUsuario.swSolicitudimpresion 
						&& ClienteUsuario.toImprimirAdministrar && !ClienteUsuario.swSolicitudimpresionObsoleto}"
						value="#{txt.cancelar} #{txt.solicitudImpresion}"
						action="#{ClienteUsuario.cancelarListSolicitudImpresion}" />

					<h:commandButton
						rendered="#{!ClienteUsuario.swSolicitudimpresion  
						&& ClienteUsuario.toSolicitudImpresion}"
						value="#{txt.enviar} #{txt.solicitudImpresion}"
						action="#{ClienteUsuario.solicitudImpresion}" />
				</h:panelGrid>
				<t:dataTable id="data" styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="car" value="#{ClienteUsuario.usuariosImpresion}"
					preserveDataModel="false" rows="90000">

					<h:column>
						<f:facet name="header">

						</f:facet>
						<!-- si  swDiferenciaEntreVersiones = true , es porque es un archivo binario
							y no c puede comparar-->
						<h:selectBooleanCheckbox
							rendered="#{!ClienteUsuario.swSolicitudimpresion}"
							value="#{ClienteUsuario.selectedIds[car.id]}" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_apellido}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.apellido} " />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_nombre}" />
						</f:facet>
						<h:outputText styleClass="form" title="#{car.nombre}"
							value="#{car.nombre}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_cargo}" />
						</f:facet>
						<h:outputText styleClass="form" value="[#{car.cargo.nombre}] " />
					</h:column>






					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_empresa}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.empresa.nombre}" />
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.mail_cliente}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.mail_principal}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.estado}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="#{txt[car.solicitudImpParts.estado.nombre]}" />
					</h:column>

					<h:column rendered="#{ClienteUsuario.swSolicitudimpresion}">
						<f:facet name="header">
							<h:graphicImage title="#{txt.imprimir}"
								url="#{conftxt.img_impresora}" />
						</f:facet>

						<h:commandButton id="EditApplet"
							rendered="#{car.swMostrar && car.cancelar && 
							ClienteUsuario.swSolicitudimpresion}"
							image="#{conftxt.img_impresora}" immediate="true"
							action="#{listarSolicitudImpresion.viewAppletImp}"
							actionListener="#{listarSolicitudImpresion.versionId}">
							>
							<f:param id="numcopias" name="numcopiasN"
								value="#{car.solicitudImpParts.solicitudimpresion.numcopias}" />
							<f:param id="solicitudimpresion" name="solicitudimpresionN"
								value="#{car.solicitudImpParts.solicitudimpresion.codigo}" />

						</h:commandButton>


					</h:column>


					<h:column
						rendered="#{ClienteUsuario.swSolicitudimpresion && ClienteUsuario.toImprimirAdministrar}">
						<%
							/*<!--<h:panelGroup rendered="#{car.historico}">-->*/
						%>
						<f:facet name="header">
							<h:outputText value="#{txt.cancelar}" />
						</f:facet>


						<h:commandLink
							rendered="#{car.cancelar && ClienteUsuario.toImprimirAdministrar}"
							action="#{ClienteUsuario.aceptar}"
							actionListener="#{ClienteUsuario.cancelarsolicitudImpParts}">
							<h:outputText value="#{txt.cancelar}" />
							<f:param id="usuarioImpresionId" name="noseusa_id"
								value="#{car.id}" />
						</h:commandLink>
						<%
							/*<!--</h:panelGroup>-->*/
						%>
					</h:column>


				</t:dataTable>



			</h:panelGroup>
		</a4j:region>
	</h:form>


</f:view>



</body>

</html>
