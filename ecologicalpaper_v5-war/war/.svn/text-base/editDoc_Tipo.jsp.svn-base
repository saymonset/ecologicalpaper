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
	<title><h:outputText styleClass="form"
		value="#{txt.toModTipoDocumentos}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form"
		value="#{txt.toModTipoDocumentos}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">
			<h:outputLabel styleClass="form" value="#{txt.doc_nombre}" />
			<h:inputText id="inputTextNombre" maxlength="30"
				value="#{Doc_tipo.doc_tipo.nombre}" immediate="false">
				<f:validateLength maximum="29" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />
			
			<h:outputLabel styleClass="form" value="#{txt.abreviatura}" />
			<h:inputText id="abreviatura" size="10" maxlength="6"
				value="#{Doc_tipo.doc_tipo.abreviatura}" />
			<h:message styleClass="form" for="abreviatura" />
			

			<h:outputLabel styleClass="form" value="#{txt.doc_descripcion}" />
			<h:inputTextarea id="textArea" cols="100" styleClass="form" rows="10"
				value="#{Doc_tipo.doc_tipo.descripcion}"></h:inputTextarea>
			<h:message styleClass="form" for="textArea" />

			<h:outputText id="idtxtFormato" styleClass="form" value="#{txt.formato}" />
			<h:selectBooleanCheckbox value="#{Doc_tipo.doc_tipo.swTipoFormato}" />
			<h:message styleClass="form" for="idtxtFormato" />

			<%
				/*
															<t:inputHtml value="#{Doc_tipo.doc_tipo.descripcion}"
																style="width: 700px;" allowEditSource="false"
																showPropertiesToolBox="false" showLinksToolBox="false"
																showImagesToolBox="false" showTablesToolBox="false"
																showDebugToolBox="false" />
							 */
			%>


			
			<h:message styleClass="form" for="btncancel" />
			<h:panelGroup>
				<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
					immediate="true" action="#{Doc_tipo.cancelar}" />

				<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
					action="#{Doc_tipo.save}" />
			</h:panelGroup>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
