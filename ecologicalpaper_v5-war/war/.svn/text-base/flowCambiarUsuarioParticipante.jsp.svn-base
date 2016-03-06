<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT>
	function refrescar() {
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

	<title><h:outputText styleClass="form" value="#{txt.btn_erase}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form"
		value="#{txt.doc_razodelcambio} #{txt.toModFlow}
                                                  #{FlowsResponse.participanteOLd.nombre}
                                          #{FlowsResponse.participanteOLd.apellido}[#{FlowsResponse.participanteOLd.cargo.nombre}]" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="1" >
			<h:outputText styleClass="form" value="#{txt.toModFlow}" />
			<h:selectOneRadio title=""
				  styleClass="form" immediate="true"
				value="#{FlowsResponse.tipoDeCambio}">
				<f:selectItem itemValue="0" itemLabel="#{txt.cambioParticipanteActual}" />
				<f:selectItem itemValue="1" itemLabel="#{txt.cambioParticipanteInFlow}" />
				<f:selectItem itemValue="2" itemLabel="#{txt.cambioParticipanteAllFlow}" />
			</h:selectOneRadio>
			<h:selectOneMenu immediate="true" converter="ConverUsuarios"
				value="#{FlowsResponse.usuarioRemplazo}">
				<f:selectItems value="#{DatosCombo.usuarios}" />
			</h:selectOneMenu>




			<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">

				<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{FlowsResponse.cualquierComentario}"></h:inputTextarea>

				<%
					/* <t:inputHtml value="#{FlowsResponse.cualquierComentario}"
																			style="width: 700px;" allowEditSource="false"
																			showPropertiesToolBox="false" showLinksToolBox="false"
																			showImagesToolBox="false" showTablesToolBox="false"
																			showDebugToolBox="false" /> */
				%>


			</h:panelGrid>

			<h:panelGroup id="btones">
				<h:panelGrid columns="2" id="panelGrid2">
					<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
						action="#{FlowsResponse.cancelar}" />
					<h:commandButton styleClass="boton" id="btnDelete" value="#{txt.btn_aceptar}"
						action="#{FlowsResponse.remplazandoParticipante}" />
				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
	</h:form>




</f:view>
</body>
</html>
