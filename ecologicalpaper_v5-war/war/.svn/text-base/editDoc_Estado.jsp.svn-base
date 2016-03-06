<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT> 
 
</SCRIPT>
<html>
<%@include file="/inc/head.inc"%>
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
<body>
<f:view>

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<h:messages />

	<!-- 
            CUERPO DEL MENSAJE 
            -->

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<title><h:outputText styleClass="form"
		value="#{txt.doc_estado_crear}" /></title>
		<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
		
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form"
		value="#{txt.doc_estado_crear}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputLabel styleClass="form" value="#{txt.doc_nombre}"
				styleClass="form" />
			<h:inputText id="inputTextNombre"
				value="#{Doc_edo.doc_estado.nombre}" immediate="false">
				<f:validateLength maximum="29" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />

			<h:outputLabel styleClass="form" value="#{txt.doc_descripcion}"
				styleClass="form" />
			<h:inputText id="inputTextDescripcion"
				value="#{Doc_edo.doc_estado.descripcion}" immediate="false">
				<f:validateLength maximum="290" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextDescripcion" />

			<h:message styleClass="form" for="btncancel" />
			<h:commandButton styleClass="boton" id="btncancel"   immediate="true"
				value="#{txt.btn_cancelar}" action="#{Doc_edo.cancelarEdit}" />

			<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
				action="#{Doc_edo.save}" />

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
