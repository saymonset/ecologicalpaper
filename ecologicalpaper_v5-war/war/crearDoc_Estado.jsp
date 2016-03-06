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

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<title><h:outputText styleClass="form"
		value="#{txt.doc_estado_crear}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form"
		value="#{txt.doc_estado_crear}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputLabel styleClass="form" value="#{txt.doc_nombre}" />
			<h:inputText id="inputTextNombre"
				value="#{Doc_edo.doc_estado.nombre}" />
			<h:message styleClass="form" for="inputTextNombre" />

			<h:outputLabel styleClass="form" value="#{txt.doc_descripcion}" />
			<h:inputText id="inputTextDescripcion"
				value="#{Doc_edo.doc_estado.descripcion}" />
			<h:message styleClass="form" for="inputTextDescripcion" />

			<h:message styleClass="form" for="btncancel" />
			<h:commandButton styleClass="boton" id="btncancel"
				value="#{txt.btn_cancelar}" action="#{Doc_edo.cancelarCrear}" />

			<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
				action="#{Doc_edo.create}" />

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>