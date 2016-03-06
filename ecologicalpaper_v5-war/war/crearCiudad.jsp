<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT> 
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
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.listarEstados}" /></h2>
	</center>
	<h:form>
		<h:panelGrid columns="3" id="panelGrid1">


			<h:outputText styleClass="form" value="#{txt.pais}" />
			<h:outputText styleClass="form" id="pais"
				value="#{CrearCiudad.pais.nombre}" />
			<h:outputText styleClass="form" id="estado"
				value="#{CrearCiudad.estado.nombre}" />
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.usuario_nombre}" />
			<h:inputText id="nombre" value="#{CrearCiudad.ciudad.nombre}"
				title="#{txt.usuario_nombre}" maxlength="40" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="nombre" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.profesion_descripcion}" />
			<h:inputText required="false" id="descripcion"
				value="#{CrearCiudad.ciudad.descripcion}"
				title="#{txt.profesion_descripcion}" maxlength="40"
				immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="descripcion" styleClass="form" />


			<h:message styleClass="form" for="btncancel" />
			<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
				action="#{CrearCiudad.cancelarListar}" />

			<h:commandButton styleClass="boton" action="#{CrearCiudad.create}"
				value="#{txt.usuario_guardar}" />

		</h:panelGrid>
	</h:form>




</f:view>



</body>

</html>


