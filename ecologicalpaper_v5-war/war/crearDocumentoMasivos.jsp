<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="./validacione.js"></script>
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
<%@include file="inc/head.inc"%>

<body>

<f:view>




	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<h:messages id="messageList" showSummary="true" />
<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<h:form id="form1" enctype="multipart/form-data">
		<h:panelGrid columns="3" border="0">

			<h:outputText styleClass="form" value="#{txt.doc_rutadirectorio} " />
			<h:inputText id="rutadir" value="#{Documento.directorio}" />
			<h:message for="rutadir" showDetail="true" />


			<h:outputText styleClass="form" value="#{txt.doc_creado}" />
			<t:inputCalendar id="fecha_creado"
				monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
				popupButtonStyleClass="standard_bold"
				currentDayCellClass="currentDayCell"
				value="#{Documento.doc_maestro.fecha_creado}" renderAsPopup="true"
				popupTodayString="#{txt.calendar_fecha}"
				popupDateFormat="MM/dd/yyyy"
				popupWeekString="#{txt.popup_week_string}" helpText="MM/DD/YYYY"
				forceId="true" />
			<h:message for="fecha_creado" styleClass="form" />



			<h:outputText styleClass="form" value="#{txt.doc_tipotab}" />
			<h:selectOneMenu id="maestro1"
				value="#{Documento.doc_maestro.doc_tipo}" converter="ConverDoc_tipo">
				<f:selectItems value="#{DatosCombo.doc_tipo}" />
			</h:selectOneMenu>
			<h:message for="maestro1" showDetail="true" />



			<h:outputText styleClass="form" value="#{txt.doc_mayorVertab}" />
			<h:panelGrid columns="2" id="panelGrid1">
				<h:inputText id="mayor_ver" size="4" maxlength="4" required="true"
					value="#{Documento.docVersionMayorMasivo}" 
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>.
                        <h:inputText id="minor_ver" size="4"
					maxlength="4" required="true"
					value="#{Documento.docVersionMenorMasivo}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
			</h:panelGrid>
			<h:message for="mayor_ver" showDetail="true" />


			<h:outputText styleClass="form" value="#{txt.doc_consecutivotab}" />
			<h:inputText id="numconsecutivo" maxlength="20"
				value="#{Documento.doc_maestro.consecutivo}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
			<h:message for="numconsecutivo" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_dueniotab}" />
			<h:selectOneMenu id="duenio" value="#{Documento.doc_detalle.duenio}"
				converter="ConverUsuarios">
				<f:selectItems value="#{DatosCombo.usuarios}" />
			</h:selectOneMenu>
			<h:message for="duenio" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_estadotab}" />
			<h:selectOneMenu id="estado"
				value="#{Documento.doc_detalle.doc_estado}"
				converter="ConverDoc_Estado">
				<f:selectItems value="#{DatosCombo.doc_Estados}" />
			</h:selectOneMenu>
			<h:message for="estado" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_keystab}" />
			<h:inputTextarea id="desc" cols="40" rows="3"
				value="#{Documento.doc_maestro.busquedakeys}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
			</h:inputTextarea>
			<h:message for="desc" showDetail="true" />
			<%
				/* tiene longitud de 4000 caracteres.. puede escribir lo que quiera*/
			%>
			<h:outputText styleClass="form" value="#{txt.doc_descripcion}" />
			<h:inputTextarea id="desc2" cols="40" rows="3"
				value="#{Documento.doc_detalle.descripcion}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
			</h:inputTextarea>
			<h:message for="desc2" showDetail="true" />

			<h:outputText styleClass="form" value="#{txt.doc_publicarDocumento}" />
			<h:selectBooleanCheckbox value="#{Documento.doc_maestro.publico}">
			</h:selectBooleanCheckbox>
			<f:verbatim></f:verbatim>



			<f:verbatim></f:verbatim>
			<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
				action="#{Documento.uploadMasivos}"
				onclick="this.style.display='none';"
				>
			</h:commandButton>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
	</h:form>
	<h:form id="form2">
		<h:panelGrid columns="3" border="0">
			<f:verbatim></f:verbatim>
			<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
				action="#{Documento.cancelarCrear}" 
				/>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
	</h:form>
</f:view>


</body>

</html>
