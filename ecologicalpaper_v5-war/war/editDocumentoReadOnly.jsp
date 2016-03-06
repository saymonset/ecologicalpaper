<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<html>
<head>


<SCRIPT>
	
</SCRIPT>
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




<script language="javascript">
	var formId; // reference to the main form
	var winId; // reference to the popup window

	function showFlowUser(action, title) {
		//features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		features = "height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
	// This function calls the popup window.
	//
	function showPlaceList(action, title) {
		features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
</script>
</head>
<script type="text/javascript" src="./validacione.js"></script>
<%@include file="inc/head.inc"%>

<body>

<f:view>



	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
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

			<h:outputText styleClass="form" value="#{txt.doc_nombretab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_maestroModifica.nombre}" />
			<h:message for="name1" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_creado}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_maestro.fecha_mostrar}" />
			<f:verbatim></f:verbatim>



			<h:outputText styleClass="form" value="#{txt.doc_tipotab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_maestro.doc_tipo.nombre}" />
			<h:message for="maestro1" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_mayorVertab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_detalleModifica.mayorVer}.#{Documento.doc_detalleModifica.minorVer}" />
			<h:message for="mayor_ver" showDetail="true" />


			<h:outputText styleClass="form" value="#{txt.doc_consecutivotab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_maestroModifica.consecutivo}" />
			<h:message for="numconsecutivo" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_dueniotab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_detalleModifica.duenio}" />
			<h:message for="duenioxx" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_estadotab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_detalle.doc_estado.nombre}" />
			<h:message for="estado" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_keystab}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_maestroModifica.busquedakeys}" />
			<h:message for="desc" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_descripcion}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_detalleModifica.descripcion}" />
			<h:message for="desc2" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.size_doc}" />
			<h:outputText styleClass="forms"
				value="#{Documento.doc_detalle.size_doc} " />
			<f:verbatim></f:verbatim>

			<f:verbatim></f:verbatim>
			<h:outputText styleClass="forms"
				value="#{Documento.doc_detalle.contextType}" />
			<f:verbatim></f:verbatim>

		 
 
			<h:outputText styleClass="form" value="#{txt.doc_documento}" />
			<h:panelGrid columns="2">
				<h:commandLink
					onmousedown="showFlowUser('mostrarDocProtegido.jsf?detalle_id=#{Documento.doc_detalle.codigo}','')"
					immediate="true" onclick="return false">
					<h:graphicImage title="#{txt.doc_verdocumento}"
						url="#{Documento.icono}" />
				</h:commandLink>
				<h:commandLink styleClass="form" id="Edit" type="submit"
					rendered="#{Documento.swIsPublicador}"
					action="#{Documento.verDocumentoDesdePublico}"
					onclick="this.style.display='none';">
					<h:graphicImage title="#{txt.mas}" url="#{conftxt.img_mas}" />
				</h:commandLink>
			</h:panelGrid>
			<f:verbatim></f:verbatim>






			<!--<%/*  Si es publicador y esrta publicado, puede ir a ver el docuymento en listar documentos*/%>-->




			<f:verbatim></f:verbatim>
			<h:panelGroup>
				<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
					immediate="true" action="#{Documento.regresarVerDocumento}" />

			</h:panelGroup>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
		<h:panelGrid columns="3">

		</h:panelGrid>
	</h:form>
	<h:form id="form2">
		<h:panelGrid columns="3" border="0">
		</h:panelGrid>
	</h:form>
</f:view>


</body>

</html>
