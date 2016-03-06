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

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
	
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.configuracion}" /></h2>
	</center>
	<h:form>
		<h:panelGrid columns="3" id="panelGrid1">

			<h:outputText styleClass="form" value="#{txt.server}" />
			<h:inputText id="server"
				value="#{ConfiguracionCliente.configuracion.server}"
				title="#{txt.server}" />
			<h:message for="server" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.ip}" />
			<h:inputText required="false" id="ip"
				value="#{ConfiguracionCliente.configuracion.serverIp}"
				title="#{txt.server} #{txt.ip}" />
			<h:message for="ip" styleClass="form" />
			
			<h:outputText styleClass="form" value="#{txt.serverPuerto}" />
			<h:inputText required="false" id="serverPuerto"
				value="#{ConfiguracionCliente.configuracion.serverPuerto}"
				title="#{txt.serverPuerto}" />
			<h:message for="serverPuerto" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.smtpHost}" />
			<h:inputText required="false" id="smtpHost"
				value="#{ConfiguracionCliente.configuracion.smtpHost}"
				title="#{txt.smtpHost}" />
			<h:message for="smtpHost" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.puerto}" />
			<h:inputText required="false" id="puerto"
				value="#{ConfiguracionCliente.configuracion.puerto}"
				title="#{txt.puerto}" />
			<h:message for="puerto" styleClass="form" />
			
			<h:outputText styleClass="form" value="#{txt.endPoint}" />
			<h:inputText required="false" id="endPoint"
			size="80" maxlength="79"
				value="#{ConfiguracionCliente.configuracion.endPoint}"
				title="#{txt.endPoint}" />
			<h:message for="endPoint" styleClass="form" />
			
			

			<h:outputText styleClass="form"
				value="#{txt.ldapactivedirectoryhost}" />
			<h:panelGroup>
				<h:panelGrid columns="3">
					<h:inputText required="false" id="ldapactivedirectoryhost"
						value="#{ConfiguracionCliente.configuracion.ldapactivedirectoryhost}"
						title="#{txt.ldapactivedirectoryhost}" />
					<h:selectBooleanCheckbox immediate="true"
					
						value="#{ConfiguracionCliente.configuracion.swWindows}" />
					<h:outputText value="#{txt.windows}" styleClass="form" />
				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.ldapbaseDN} " />
			<h:inputText required="false" id="ldapdominiodc"
				value="#{ConfiguracionCliente.configuracion.ldapdominiodc}"
				title="#{txt.ldapdominiodc}" />
			<h:message for="ldapdominiodc" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.ldapalternateBaseDN}" />
			<h:inputText required="false" id="ldaporganizacion"
				value="#{ConfiguracionCliente.configuracion.ldaporganizacion}"
				title="#{txt.ldaporganizacion}" />
			<h:message for="ldaporganizacion" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.ldapUsuarioAdmin}" />
			<h:inputText required="false" id="ldapUsuarioAdmin"
				value="#{ConfiguracionCliente.configuracion.ldapUsuarioAdmin}"
				title="#{txt.ldapUsuarioAdmin}" />
			<h:message for="ldapUsuarioAdmin" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.ldapPasswordAdmin}" />
			<h:inputSecret required="false" id="ldapPasswordAdmin"
				value="#{ConfiguracionCliente.configuracion.ldapPasswordAdmin}"
				title="#{txt.ldapPasswordAdmin}" />
			<h:message for="ldapPasswordAdmin" styleClass="form" />

			<h:outputText styleClass="form"
				value="#{txt.usuario_passwordconfirm}" />
			<h:inputSecret id="passwordconf"
				value="#{ConfiguracionCliente.configuracion.ldapPasswordAdminHidden}"
				title="#{txt.usuario_passwordconfirm}">
			</h:inputSecret>
			<h:message for="ldapPasswordAdminHidden" styleClass="form" />



			 
			<h:outputText styleClass="form" value="#{txt.carpetaCompartida}" />
			<h:inputText required="false" id="carpetaCompartida"
				value="#{ConfiguracionCliente.configuracion.carpetaCompartida}"
				title="#{txt.carpetaCompartida}" />
			<h:message for="carpetaCompartida" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.mail_cliente}" />
			<h:inputText required="false" id="mailCliente"
				value="#{ConfiguracionCliente.configuracion.mailCliente}"
				title="#{txt.mail_cliente}" />
			<h:message for="mailCliente" styleClass="form" />

			<h:outputText id="selectOneMenuAppl_idioma" styleClass="form"
				value="#{txt.appl_idioma}" title="#{txt.appl_idioma}" />
			<h:selectOneMenu
				value="#{ConfiguracionCliente.configuracion.paisBundle}">
				<f:selectItems value="#{ConfiguracionCliente.lenguajes}" />
			</h:selectOneMenu>
			<h:message for="selectOneMenuProfesion" styleClass="form" />


			<h:message styleClass="form" for="btncancel" />
			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
					action="#{ConfiguracionCliente.cancelarListar}" />
				<h:commandButton styleClass="boton" action="#{ConfiguracionCliente.create}"
					value="#{txt.usuario_guardar}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>


		</h:panelGrid>
	</h:form>




</f:view>



</body>

</html>


