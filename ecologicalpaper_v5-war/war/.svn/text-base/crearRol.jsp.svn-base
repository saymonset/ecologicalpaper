<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT> 
function refrescar() 
{ 
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
		value="#{txt.role_crear}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.role_crear}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">


                     //----------------------------------------empresa-------------------------
                     <h:outputText rendered="#{Rol.swSuperUsuario}"
				styleClass="form" value="#{txt.usuario_empresa}"
				title="#{txt.usuario_empresa}" />
			<h:selectOneMenu rendered="#{Rol.swSuperUsuario}"
				id="selectOneMenuEmpresa" immediate="true" value="#{Rol.empresa}"
				converter="ConverTree">
				<f:selectItems value="#{backing_login.lstEmpresas}" />
			</h:selectOneMenu>
			<h:message rendered="#{Rol.swSuperUsuario}"
				for="selectOneMenuEmpresa" styleClass="form" />
                //----------------------------------------empresa-------------------------fin




                    <h:outputLabel styleClass="form"
				value="#{txt.role_nombre}" />
			<h:inputText id="inputTextNombre" size="30" maxlength="30"
				value="#{Rol.role.nombre}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message styleClass="form" for="inputTextNombre" />

			<h:outputLabel styleClass="form" value="#{txt.role_descripcion}" />
	<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{Rol.role.descripcion}"></h:inputTextarea>
		<%/*	<t:inputHtml value="#{Rol.role.descripcion}" style="width: 700px;"
				allowEditSource="false" showPropertiesToolBox="false"
				showLinksToolBox="false" showImagesToolBox="false"
				showTablesToolBox="false" showDebugToolBox="false" />*/%>


			<h:message styleClass="form" for="inputTextDescripcion" />


			<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
				immediate="true" action="#{Rol.cancelarListRol}" />

			<h:commandButton styleClass="boton" id="btnCrear" value="#{txt.btn_aceptar}"
				action="#{Rol.create}" />
			<h:message styleClass="form" for="btnCrear" />

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
