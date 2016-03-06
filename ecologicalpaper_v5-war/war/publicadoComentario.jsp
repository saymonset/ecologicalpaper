<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


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



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>


<script language="javascript">
	var formId; // reference to the main form
	var winId; // reference to the popup window
	// This function calls the popup window.
	//
	function showFlowUser(action, title) {
		features = "height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
	function showPlaceList(action, form, target) {
		features = "height=800,width=800,status=yes,toolbar=no,menubar=no,location=yes,scrollbars=yes";
		winId = window.open('ClienteDocumentoGenerar', 'list', features); // open an empty window

	}
</script>

</head>
<%@include file="/inc/head.inc"%>
<body>
<f:view>

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

	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<h:messages id="messageList" showSummary="true" />


	<h:form id="forma">
		<h:panelGrid columns="1" id="panelGrid1">
			<h:panelGrid columns="3">
				<f:verbatim></f:verbatim>
				<h:outputText styleClass="form"
					value="#{
                            Documento.publicadosUsuComent.doc_detalle.doc_maestro.nombre}" />
				<f:verbatim></f:verbatim>
			</h:panelGrid>

			<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">

				<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{Documento.publicadosUsuComent.comentario}"></h:inputTextarea>
				<%
					/*
																					                           
																					                           <t:inputHtml value="#{Historico.cualquierComentario}"
																					                                        style="width: 700px;"
																					                                        allowEditSource="false"
																					                                        showPropertiesToolBox="false"
																					                                        showLinksToolBox="false"
																					                                        showImagesToolBox="false"
																					                                        showTablesToolBox="false"
																					                                        showDebugToolBox="false"/> */
				%>



			</h:panelGrid>

			<h:panelGroup id="btones">

				<%
					/*
													 <!---->
													 <!--				<h:panelGrid columns="2" id="panelGrid2">-->
													 <!---->
													 <!--					<h:outputText styleClass="form" value=" #{txt.doc_editar}" />-->
													 <!--					<h:commandButton styleClass="boton" id="EditComentPublico"-->
													 <!--					action="#{Documento.inicalizaPublicaComentario}"-->
													 <!--						image="#{conftxt.img_applet}" immediate="true"-->
													 <!--						onmousedown="showFlowUser('editarDocumento.jsf','')"-->
													 <!--						onclick="return false">-->
													 <!--					</h:commandButton>-->
													 <!--				</h:panelGrid>-->
									 */
				%>
				<h:panelGrid columns="3">
					<f:verbatim></f:verbatim>
					<h:panelGrid columns="2">
						<h:commandButton styleClass="boton" id="btncancel"
							value="#{txt.btn_cancelar}"
							action="#{Documento.cancelarPublicadoComentario}" />
						<h:commandButton styleClass="boton" id="btnDelete"
							value="#{txt.btn_aceptar}"
							action="#{Documento.savePublicadoComentario}" />
					</h:panelGrid>
					<f:verbatim></f:verbatim>
				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
	</h:form>


</f:view>
</body>
</html>
