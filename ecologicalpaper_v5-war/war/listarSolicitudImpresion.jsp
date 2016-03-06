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
	<h:form>

		<t:jscookMenu layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<!-- 
            CUERPO DEL MENSAJE 
            -->
	<h:form>
		<a4j:region renderRegionOnly="true" id="stat3">

			<%
				/* <!--	<h1><h:outputText styleClass="form" value="#{txt.search_buscar}" />-->*/
			%>


			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
					action="#{listarSolicitudImpresion.regresar}" />
			 

			</h:panelGrid>

			<h:panelGroup id="body">


				<t:dataTable id="data" styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="car" value="#{listarSolicitudImpresion.solicitudImpPartLst}"
					preserveDataModel="false" rows="90000">

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees"
								value="#{txt.toSolicitudImpresion}" />
						</f:facet>
						<h:outputText styleClass="node" value="#{car.usuario} " />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.doc_documento}" />
						</f:facet>
						<h:outputText styleClass="node"
							value="#{car.solicitudimpresion.doc_detalle.doc_maestro.nombre}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.estado}" />
						</f:facet>
						<h:outputText styleClass="node" value="#{txt[car.estado.nombre]}" />
					</h:column>




					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees"
								value="#{txt.fecha} #{txt.toSolicitudImpresion}" />
						</f:facet>
						<h:outputText styleClass="node"
							title="#{car.solicitudimpresion.fechaSolicitudtxt}"
							value="#{car.solicitudimpresion.fechaSolicitudtxt}" />
					</h:column>




					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees"
								value="#{txt.fecha} #{txt.desde}" />
						</f:facet>
						<h:outputText styleClass="node"
							title="#{car.solicitudimpresion.fechadesdeimprimirtxt}"
							value="#{car.solicitudimpresion.fechadesdeimprimirtxt}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees"
								value="#{txt.fecha} #{txt.hasta}" />
						</f:facet>
						<h:outputText styleClass="node"
							title="#{car.solicitudimpresion.fechahastaimprimirtxt}"
							value="#{car.solicitudimpresion.fechahastaimprimirtxt}" />
					</h:column>




					<h:column>
						<f:facet name="header">
						</f:facet>
						<h:outputText styleClass="node"
							value="#{txt[car.solicitudimpresion.estado.nombre]}" />


					</h:column>



					<h:column
						rendered="#{listarSolicitudImpresion.seguridadMenu.toImprimirAdministrar}">
						<%
							/*<!--<h:panelGroup rendered="#{car.historico}">-->*/
						%>
						<f:facet name="header">
							<h:outputText value="#{txt.flow_firmar}" />
						</f:facet>


						<h:commandLink
							rendered="#{car.cancelar && listarSolicitudImpresion.seguridadMenu.toImprimirAdministrar}"
							action="#{listarSolicitudImpresion.firmarFlow}"
							actionListener="#{listarSolicitudImpresion.firmarsolicitudImpParts}">
							<h:outputText value="#{txt.flow_firmar}" />
							<f:param id="usuarioImpresionId" name="noseusa_id"
								value="#{car.codigo}" />
						</h:commandLink>

					</h:column>

 

					<h:column
						rendered="#{listarSolicitudImpresion.seguridadMenu.toImprimirAdministrar}">
						 
						<f:facet name="header">
							<h:outputText value="#{txt.cancelar}" />
						</f:facet>


						<h:commandLink
							rendered="#{car.cancelar && listarSolicitudImpresion.seguridadMenu.toImprimirAdministrar}"
							action="#{listarSolicitudImpresion.aceptar}"
							actionListener="#{listarSolicitudImpresion.cancelarsolicitudImpParts}">
							<h:outputText value="#{txt.cancelar}" />
							<f:param id="cancelarImpresionId" name="noseusa_idcancelar"
								value="#{car.codigo}" />
						</h:commandLink>

					</h:column>

 





				</t:dataTable>

			</h:panelGroup>
		</a4j:region>
	</h:form>


</f:view>



</body>

</html>
