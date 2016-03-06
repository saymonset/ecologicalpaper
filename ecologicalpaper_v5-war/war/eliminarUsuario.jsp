<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>
  
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT> 
function refrescar() 
{ 
    top.frames['leftFrame'].location.href = './arbol.jsf'; 
} 
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

	<!-- 
            CUERPO DEL MENSAJE 
            -->

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<h:panelGroup rendered="#{!Historico.swSoloVerHistorico}">
		<title><h:outputText styleClass="form"
			value="#{txt.btn_erase}" /></title>
		<h1></h1>
		<center>
		<h2><h:outputText styleClass="form"
			value="#{txt.doc_razodelcambio} #{txt.btn_erase}
                                                  #{Historico.usuarioEliminar.nombre}
                                          #{Historico.usuarioEliminar.apellido}[#{Historico.usuarioEliminar.cargo.nombre}]" /></h2>
		</center>
		<p></p>

		<h:form id="forma">
			<h:panelGrid columns="1" id="panelGrid1">

				<h:outputText styleClass="form" value="#{txt.remplaza_usuario}" />
				<h:selectOneMenu immediate="true" converter="ConverUsuarios"
					value="#{Historico.usuarioRemplazo}">
					<f:selectItems value="#{DatosCombo.usuarios}" />
				</h:selectOneMenu>


				<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">
				
				<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{Historico.cualquierComentario}"></h:inputTextarea>
					
				<% 
				/* <t:inputHtml value="#{Historico.cualquierComentario}"
						style="width: 700px;" allowEditSource="false"
						showPropertiesToolBox="false" showLinksToolBox="false"
						showImagesToolBox="false" showTablesToolBox="false"
						showDebugToolBox="false" /> */
						%>


				</h:panelGrid>

				<h:panelGroup id="btones">
					<h:panelGrid columns="2" id="panelGrid2">
						<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
							action="#{Historico.cancelar}" />
						<h:commandButton styleClass="boton" id="btnDelete" value="#{txt.btn_aceptar}"
							action="#{Historico.delete2}" />
					</h:panelGrid>
				</h:panelGroup>
				<f:verbatim></f:verbatim>
			</h:panelGrid>
		</h:form>
	</h:panelGroup>

	<%
		/*
			 SOLO HISTORICO
			 */
	%>
	<h:panelGroup rendered="#{Historico.swSoloVerHistorico}">
		<h:form>
			<h1><h:outputText styleClass="form" value="#{txt.historico}" /></h1>

			<h:panelGroup id="body">

				<t:dataTable id="data" styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="car" value="#{Historico.historicos}" preserveDataModel="false"
					rows="#{Utilidades.verNumeroDeRegistros}">
					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_nombre}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="#{car.usuario_anterior.nombre}
                                              #{car.usuario_anterior.apellido}[#{car.usuario_anterior.cargo.nombre}]"
							title="#{car.usuario_anterior.nombre}
                                              #{car.usuario_anterior.apellido}[#{car.usuario_anterior.cargo.nombre}]" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_elimino}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="#{car.usuario_accion.nombre}
                                              #{car.usuario_accion.apellido}[#{car.usuario_accion.cargo.nombre}]"
							title="#{car.usuario_accion.nombre}
                                              #{car.usuario_accion.apellido}[#{car.usuario_accion.cargo.nombre}]" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.pc_accion}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.maquina}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.fecha_elimino}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.fechaAux}"
							title="#{car.fechaAux}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.remplaza_usuario}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.usuario_new}"
							title="#{car.usuario_new}" />
					</h:column>
				</t:dataTable>



				<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">
					<h:outputText styleClass="form" value="#{txt.doc_descripciontab}"></h:outputText>
					
					<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{Historico.cualquierComentario}" disabled="true"></h:inputTextarea>
					<% 
					/*
					
					<t:inputHtml displayValueOnly="true"
						value="#{Historico.cualquierComentario}" style="width: 700px;"
						allowEditSource="false" showPropertiesToolBox="false"
						showLinksToolBox="false" showImagesToolBox="false"
						showTablesToolBox="false" showDebugToolBox="false" />
						*/
						%>


				</h:panelGrid>


			</h:panelGroup>
			<h:panelGrid columns="1" id="panelGrid2">
				<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
					action="#{Historico.cancelarIrUsuarios}" />
			</h:panelGrid>
		</h:form>


	</h:panelGroup>


</f:view>
</body>
</html>
