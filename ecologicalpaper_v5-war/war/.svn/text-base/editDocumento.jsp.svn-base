<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>


<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

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

<SCRIPT>
	var formId; // reference to the main form
	var winId; // reference to the popup window
	// This function calls the popup window.
	//
	function showFlowUser(action, title) {
		features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
</SCRIPT>

</head>
<script type="text/javascript" src="./validacione.js"></script>
<%@include file="inc/head.inc"%>

<body>

<f:view>




	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<h:messages id="messageList" showSummary="true" />
	<h:form>

		<t:jscookMenu layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>


	<h:form id="form1" enctype="multipart/form-data">

		<h:panelGrid columns="3" border="0">

			<f:verbatim />
			<h:outputText styleClass="form" value="#{txt.doc_editar} " />
			<f:verbatim />

			<h:outputText styleClass="form"
				rendered="#{Documento.swExecuteActualizar}"
				value="#{txt.doc_datosCargaDocumentotabp} " />
			<t:inputFileUpload id="fileupload"
				rendered="#{Documento.swExecuteActualizar}" accept="image/*"
				value="#{Documento.upFile}" storage="file"
				styleClass="fileUploadInput" required="true" maxlength="20000000" />
			<h:message for="fileupload"
				rendered="#{Documento.swExecuteActualizar}" showDetail="true" />


			<h:outputText styleClass="form" value="#{txt.doc_razodelcambio}" />
			<h:inputTextarea id="desc2" cols="40" rows="3"
				value="#{Documento.doc_detalle.descripcion}" immediate="false">
				<f:validateLength maximum="500" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputTextarea>
			<h:message for="desc2" showDetail="true" />

			<f:verbatim></f:verbatim>
			<%
				/*<!--
																																																										                   AQUI SE CREA UN NUEVO DOCUMENTO-->*/
			%>
			<h:commandLink id="newVersion"
				rendered="#{!Documento.swExecuteActualizar}"
				action="#{Documento.nuevaVersionDoc}"
				onclick="this.style.display='none';">
				<h:panelGrid columns="2">
					<h:graphicImage id="imageNewVersion"
						title="#{txt.doc_version_nueva}" url="#{conftxt.img_newversion}" />
					<h:outputText styleClass="form" value="#{txt.doc_version_nueva} " />
				</h:panelGrid>
			</h:commandLink>

			<%
				/*   <!--ACTUALIZAR, ES CON EL MISMO DOCUMENTO Y NO CREAR UN NUEVO DOCUMENTO -->*/
			%>
			<h:commandLink id="update"
				rendered="#{Documento.swExecuteActualizar}"
				action="#{Documento.actualizarDoc}"
				onclick="this.style.display='none';">
				<h:panelGrid columns="2">
					<h:graphicImage id="imageUpdate" title="#{txt.doc_actualiza}"
						url="#{conftxt.img_update}" />
					<h:outputText styleClass="form" value="#{txt.doc_version_nueva} " />
				</h:panelGrid>

			</h:commandLink>
			<f:verbatim></f:verbatim>




		</h:panelGrid>
	</h:form>
	<h:form id="form2">
		<h:panelGrid columns="2" border="0">


			<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
				action="#{Documento.cancelar}" onclick="this.style.display='none';" />
			<%
				/*  <!--Esro solo lo qu hace es actualizar el mismo documento -->*/
			%>












		</h:panelGrid>
	</h:form>
</f:view>


</body>

</html>
