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
	<title><h:outputText styleClass="form" value="#{txt.svn}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.svn}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">

			<h:outputText id="selectOne1" styleClass="form"
				value="#{txt.svnurlbase}" title="#{txt.svnurlbase}" />
			<h:selectOneMenu value="#{svnTipoAmbienteCliente.svnUrlBase}"
				immediate="true" binding="#{svnTipoAmbienteCliente.selectOneMenu1}"
				converter="ConverSvnUrlBase">
				<f:selectItems value="#{DocumentoSvn.allSvnUrlBase}" />
				<a4j:support event="onchange" ajaxSingle="true"
					reRender="data,selectOne21"
					action="#{svnTipoAmbienteCliente.change}" />
			</h:selectOneMenu>
			<h:message for="selectOne1" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.svnnombreaplicacion}"
				title="#{txt.svnnombreaplicacion}" />
			<h:selectOneMenu id="selectOne21" immediate="true"
				binding="#{svnTipoAmbienteCliente.selectOneMenu3}"
				value="#{svnTipoAmbienteCliente.svnTipoAmbiente.svnnombreaplicacion}"
				converter="ConverSvnNombreAplicacion">
				<f:selectItems value="#{svnTipoAmbienteCliente.allSvnNombreAplicacion}" />
				<a4j:support event="onchange" ajaxSingle="true" reRender="data" />
			</h:selectOneMenu>
			<h:message for="selectOne21" styleClass="form" />


			<h:outputLabel styleClass="form" value="#{txt.doc_nombre}" />
			<h:inputText id="inputTextNombre" maxlength="30"
				value="#{svnTipoAmbienteCliente.svnTipoAmbiente.nombre}"
				immediate="false">
				<f:validateLength maximum="29" minimum="4" />
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />


			<h:outputLabel styleClass="form" value="#{txt.doc_descripcion}" />
			<h:inputTextarea cols="100" styleClass="form" rows="10"
				value="#{svnTipoAmbienteCliente.svnTipoAmbiente.descripcion}"></h:inputTextarea>
 


			<h:message styleClass="form" for="inputTextDescripcion" />

			<h:message styleClass="form" for="btncancel" />
			<h:panelGroup>
				<h:panelGrid columns="2">
					<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
						immediate="true" action="#{svnTipoAmbienteCliente.cancelar}" />

					<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
						action="#{svnTipoAmbienteCliente.save}" />
				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
