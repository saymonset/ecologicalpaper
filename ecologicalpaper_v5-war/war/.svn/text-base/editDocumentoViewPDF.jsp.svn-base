<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<html>
<head>




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
			<f:verbatim />
			<h:outputText styleClass="form" value="#{txt.doc_editar}" />
			<f:verbatim />

			<h:outputText styleClass="form" value="#{txt.doc_nombretab}" />
			<h:inputText id="name1" binding="#{Documento.name1}" size="90"
				value="#{Documento.doc_maestroModifica.nombre}" immediate="false">
				<f:validateLength maximum="89" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="name1" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_creado}" />
			<h:outputText styleClass="form"
				value="#{Documento.doc_maestro.fecha_mostrar}" />
			<f:verbatim></f:verbatim>



			<h:outputText styleClass="form" value="#{txt.doc_tipotab}" />
			<h:selectOneMenu immediate="true" id="selectOneMenu1"
				value="#{Documento.doc_maestro.doc_tipo}"
				binding="#{Documento.selectOneMenu1}" converter="ConverDoc_tipo">
				<f:selectItems value="#{DatosCombo.doc_tipo}" />
			</h:selectOneMenu>
			<h:message for="maestro1" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.areadocumento}" />
			<h:selectOneMenu id="areadocumento1"
				value="#{Documento.doc_detalleModifica.areaDocumentos}"
				converter="converAreadocumento">
				<f:selectItems value="#{DatosCombo.areaDocumentos}" />
			</h:selectOneMenu>
			<h:message for="areadocumento1" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_mayorVertab}" />
			<h:panelGrid columns="2" id="panelGrid1">
				<h:inputText id="mayor_ver" size="4" maxlength="10"
					binding="#{Documento.mayor_ver}"
					value="#{Documento.doc_detalleModifica.mayorVer}" immediate="false">
					<f:validateLength maximum="9" minimum="1" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>.
                        <h:inputText id="minor_ver" size="4"
					maxlength="10" binding="#{Documento.minor_ver}"
					value="#{Documento.doc_detalleModifica.minorVer}" immediate="false">
					<f:validateLength maximum="9" minimum="1" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
			</h:panelGrid>
			<h:message for="mayor_ver" showDetail="true" />


			<h:outputText styleClass="form" value="#{txt.doc_consecutivotab}" />
			<h:inputText id="numconsecutivo"
				binding="#{Documento.numconsecutivo}"
				value="#{Documento.doc_maestroModifica.consecutivo}"
				immediate="false">
				<f:validateLength maximum="40" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="numconsecutivo" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_dueniotab}" />
			<h:selectOneMenu immediate="true" binding="#{Documento.duenio}"
				id="duenio" value="#{Documento.doc_detalleModifica.duenio}"
				converter="ConverUsuarios">
				<f:selectItems value="#{DatosCombo.usuarios}" />
			</h:selectOneMenu>
			<h:message for="duenio" showDetail="true" />

			<%
				/*GRUPOS DE ROLES PARA DAR LOS PERMISOS AL GRUPO ...*/
			%>
			<h:outputText styleClass="form" value="#{txt.loadDocPermGrupo}" />
			<h:panelGroup>
				<h:selectOneMenu id="rolesid" value="#{Documento.roleParaPermisos}"
					converter="ConverRoles">
					<f:selectItems value="#{DatosCombo.todosLosRoles}" />
				</h:selectOneMenu>
				<h:selectBooleanCheckbox value="#{Documento.swPermGrupo}" />
			</h:panelGroup>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.doc_estadotab}" />
			<h:selectOneMenu id="estado" disabled="true"
				value="#{Documento.doc_detalle.doc_estado}"
				converter="ConverDoc_Estado">
				<f:selectItems value="#{DatosCombo.doc_Estados}" />
			</h:selectOneMenu>
			<h:message for="estado" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_keystab}" />
			<h:inputTextarea id="desc" cols="40" rows="3"
				binding="#{Documento.desc}"
				value="#{Documento.doc_maestroModifica.busquedakeys}"
				immediate="false">
				<f:validateLength maximum="1000" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputTextarea>
			<h:message for="desc" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_descripcion}" />
			<h:inputTextarea id="desc2" cols="40" rows="3"
				binding="#{Documento.desc2}"
				value="#{Documento.doc_detalleModifica.descripcion}"
				immediate="false">
				<f:validateLength maximum="1000" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputTextarea>
			<h:message for="desc2" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.size_doc}" />
			<h:outputText styleClass="form"
				value="#{Documento.doc_detalle.size_doc} " />
			<f:verbatim></f:verbatim>

			<f:verbatim></f:verbatim>
			<h:outputText styleClass="form"
				value="#{Documento.doc_detalle.contextType}" />
			<f:verbatim></f:verbatim>

		 
			<h:outputText styleClass="form" value="#{txt.doc_documento}" />
			<h:commandLink
				onmousedown="showFlowUser('mostrarDocProtegido.jsf?detalle_id=#{Documento.doc_detalle.codigo}','')"
				immediate="true" onclick="return false">
				<h:graphicImage title="#{txt.doc_verdocumento}"
					url="#{Documento.icono}" />
			</h:commandLink>
			<f:verbatim></f:verbatim>




			<h:outputText styleClass="form" value="#{Documento.publicoStr}" />
			<h:panelGroup>
				<a4j:region renderRegionOnly="true" id="stat1">
					<h:panelGrid columns="2">
						<h:selectBooleanCheckbox disabled="true"
							rendered="#{Documento.siEsVigentepuedeSerPublico}"
							value="#{Documento.doc_maestro.publico}" immediate="true">
							<a4j:support event="onchange" reRender="putPublic" />
						</h:selectBooleanCheckbox>
						<h:graphicImage rendered="#{Documento.doc_maestro.publico}"
							title="#{txt.doc_publico}" url="#{conftxt.img_publico}" />
					</h:panelGrid>
				</a4j:region>
			</h:panelGroup>
			<f:verbatim></f:verbatim>

			<f:verbatim></f:verbatim>
			<h:panelGroup>
				<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
					immediate="true" action="#{Documento.regresarVerDocumento}" />

				<h:commandButton styleClass="boton" rendered="#{Documento.swMod}"
					value="#{txt.usuario_guardar}"
					action="#{Documento.modificarDocumento}">
					<f:param name="idvinculo111112"
						value="#{Documento.doc_maestro.codigo}" />
				</h:commandButton>
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
