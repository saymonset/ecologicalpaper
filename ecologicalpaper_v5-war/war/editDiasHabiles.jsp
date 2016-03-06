<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

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
	<title><h:outputText styleClass="form"
		value="#{txt.Profesion_p}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.dias}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">


			<h:outputLabel styleClass="form" value="#{txt.dia_semana}" />

			<h:inputText id="inputTextNombre" disabled="true"
				value="#{DiasHabilesByHoras.diaHabilNombre}" immediate="false">
				<f:validateLength maximum="29" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />


			<h:outputText styleClass="form" value="#{txt.HoraInicialAm}" />
			<h:selectOneMenu id="horas31"
				value="#{DiasHabilesByHoras.diasHabiles.h_InicialAM}">
				<f:selectItems value="#{DiasHabilesByHoras.llenarHoras}" />
			</h:selectOneMenu>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.HoraFinalAm}" />
			<h:selectOneMenu id="horas32"
				value="#{DiasHabilesByHoras.diasHabiles.h_FinalAM}">
				<f:selectItems value="#{DiasHabilesByHoras.llenarHoras}" />
			</h:selectOneMenu>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.HoraInicialPm}" />
			<h:selectOneMenu id="horas33"
				value="#{DiasHabilesByHoras.diasHabiles.h_InicialPM}">
				<f:selectItems value="#{DiasHabilesByHoras.llenarHoras}" />
			</h:selectOneMenu>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.HoraFinalPm}" />
			<h:selectOneMenu id="horas44"
				value="#{DiasHabilesByHoras.diasHabiles.h_FinalPM}">
				<f:selectItems value="#{DiasHabilesByHoras.llenarHoras}" />
			</h:selectOneMenu>
			<f:verbatim></f:verbatim>




			<h:message styleClass="form" for="btncancel" />
			<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}" immediate="true"
				action="#{DiasHabilesByHoras.cancelarListar}" />

			<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
				action="#{DiasHabilesByHoras.saveObjeto}" />

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
