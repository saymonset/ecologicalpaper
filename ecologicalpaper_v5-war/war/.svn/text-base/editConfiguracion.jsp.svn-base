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
		value="#{txt.listarPaises}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.configuracion}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">


			<h:outputText id="selectOneMenuProfesion" styleClass="form"
				value="#{txt.nombreCliente}" title="#{txt.nombreCliente}" />
			<h:selectOneMenu
				value="#{ConfiguracionCliente.configuracion.nombreCliente}">
				<f:selectItems value="#{ConfiguracionCliente.clientes}" />
			</h:selectOneMenu>
			<h:message for="selectOneMenuProfesion" styleClass="form" />

			<h:outputText id="bd" styleClass="form" value="#{txt.bdpostgres}" />
			<h:panelGrid columns="2">
				<h:outputText styleClass="form"
					value="----------------------------------->" />
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.configuracion.bdpostgres}" />
			</h:panelGrid>
			<h:message for="bd" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.smtpHost}" />
			<h:inputText required="false" id="smtpHost" maxlength="29"
				value="#{ConfiguracionCliente.configuracion.smtpHost}"
				title="#{txt.smtpHost}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="smtpHost" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.puerto}" />
			<h:inputText required="false" id="puerto" maxlength="4"
				value="#{ConfiguracionCliente.configuracion.puerto}"
				title="#{txt.puerto}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="puerto" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.endPoint}" />
			<h:inputText required="false" id="endPoint" size="80" maxlength="79"
				value="#{ConfiguracionCliente.configuracion.endPoint}"
				title="#{txt.endPoint}" />
			<h:message for="endPoint" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.carpetaCompartida}" />
			<h:inputText required="false" id="carpetaCompartida" maxlength="29"
				value="#{ConfiguracionCliente.configuracion.carpetaCompartida}"
				title="#{txt.carpetaCompartida}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="carpetaCompartida" styleClass="form" />

			<h:outputText styleClass="form" value="#{txt.mail_cliente}" />
			<h:inputText required="false" id="mailCliente" maxlength="29"
				value="#{ConfiguracionCliente.configuracion.mailCliente}"
				title="#{txt.mail_cliente}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="mailCliente" styleClass="form" />

			<h:outputText id="selectOneMenuAppl_idioma" styleClass="form"
				value="#{txt.appl_idioma}" title="#{txt.appl_idioma}" />
			<h:selectOneMenu
				value="#{ConfiguracionCliente.configuracion.paisBundle}">
				<f:selectItems value="#{ConfiguracionCliente.lenguajes}" />
			</h:selectOneMenu>
			<h:message for="selectOneMenuProfesion" styleClass="form" />


			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.ldapactivedirectoryhost}" />
			<h:panelGrid columns="3"
				rendered="#{ConfiguracionCliente.swSuperUsuario}">
				<h:inputText required="false" id="ldapactivedirectoryhost"
					maxlength="29"
					value="#{ConfiguracionCliente.configuracion.ldapactivedirectoryhost}"
					title="#{txt.ldapactivedirectoryhost}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.configuracion.swWindows}" />
				<h:outputText value="#{txt.windows}" styleClass="form" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.ldapbaseDN}[dc=example,dc=com]" />
			<h:inputText required="false" id="ldapdominiodc" maxlength="29"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.configuracion.ldapdominiodc}"
				title="#{txt.ldapdominiodc}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="ldapdominiodc" styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />

			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.ldapalternateBaseDN}[ou=people,dc=example,dc=com]" />
			<h:inputText required="false" id="ldaporganizacion" maxlength="29"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.configuracion.ldaporganizacion}"
				title="#{txt.ldaporganizacion}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="ldaporganizacion" styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />

			<h:outputText styleClass="form" value="#{txt.ldapUsuarioAdmin}"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />
			<h:inputText required="false" id="ldapUsuarioAdmin" maxlength="29"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.configuracion.ldapUsuarioAdmin}"
				title="#{txt.ldapUsuarioAdmin}" immediate="false">
				<f:validator validatorId="caracteresinvalidos" />
			</h:inputText>
			<h:message for="ldapUsuarioAdmin" styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />


			<h:outputText styleClass="form" value="#{txt.ldapPasswordAdmin}"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />
			<h:panelGrid columns="3"
				rendered="#{ConfiguracionCliente.swSuperUsuario}">
				<h:inputSecret required="false" id="ldapPasswordAdmin"
					value="#{ConfiguracionCliente.configuracion.ldapPasswordAdmin}"
					title="#{txt.ldapPasswordAdmin}" />
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.swLdapPasswordAdmin}" />
				<h:outputText styleClass="form" value="#{txt.encriptado}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>


			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.usuario_passwordconfirm}" />
			<h:inputSecret id="ldapPasswordAdminHidden" maxlength="14"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.configuracion.ldapPasswordAdminHidden}"
				title="#{txt.usuario_passwordconfirm}">
			</h:inputSecret>
			<h:inputHidden id="passwordOculta"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.configuracion.passwordOcultaSincambio}" />




			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.server}[#{txt.seguridad}]" />
			<h:panelGrid columns="3"
				rendered="#{ConfiguracionCliente.swSuperUsuario}">
				<h:inputText id="server" maxlength="29"
					value="#{ConfiguracionCliente.configuracion.server}"
					title="#{txt.server}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.swServerEncryp}" />
				<h:outputText styleClass="form" value="#{txt.encriptado}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.ip}[#{txt.seguridad}]"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />
			<h:panelGrid columns="3"
				rendered="#{ConfiguracionCliente.swSuperUsuario}">
				<h:inputText required="false" id="ip"
					value="#{ConfiguracionCliente.configuracion.serverIp}"
					title="#{txt.server} #{txt.ip}" immediate="false" maxlength="29">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.swServerIpEncryp}" />
				<h:outputText styleClass="form" value="#{txt.encriptado}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.serverPuerto}"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />
			<h:inputText required="false" id="serverPuerto"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.configuracion.serverPuerto}"
				title="#{txt.serverPuerto}" />
			<h:message for="serverPuerto" styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />

			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.numUsuarios}[#{txt.seguridad}]" />
			<h:panelGrid columns="3"
				rendered="#{ConfiguracionCliente.swSuperUsuario}">
				<h:inputText required="false" id="numUsuarios" maxlength="29"
					value="#{ConfiguracionCliente.configuracion.numeroUsuarios}"
					title="#{txt.numUsuarios}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.swNumeroUsuariosEncryp}" />
				<h:outputText styleClass="form" value="#{txt.encriptado}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.comprado}[#{txt.seguridad}]" />
			<h:panelGrid columns="3"
				rendered="#{ConfiguracionCliente.swSuperUsuario}">
				<h:inputText required="false" id="comprado" maxlength="1"
					value="#{ConfiguracionCliente.configuracion.comprado}"
					title="#{txt.comprado}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:selectBooleanCheckbox immediate="true"
					value="#{ConfiguracionCliente.swCompradoEncryp}" />
				<h:outputText styleClass="form" value="#{txt.encriptado}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>


			<h:outputText styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{txt.usuario_fechab}[#{txt.seguridad}]" />
			<t:inputCalendar id="usuario_fechab"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
				popupButtonStyleClass="standard_bold"
				currentDayCellClass="currentDayCell"
				value="#{ConfiguracionCliente.configuracion.fechaCaduca}"
				renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
				popupDateFormat="dd.MM.yyyy"
				popupWeekString="#{txt.popup_week_string}" helpText="dd.MM.yyyy"
				forceId="true" />
			<h:message for="usuario_fechab" styleClass="form"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />




			<h:outputText rendered="#{ConfiguracionCliente.swSuperUsuario}"
				styleClass="form" value="#{txt.usuario_password}" id="outputText2" />
			<h:inputSecret id="inputSecret1"
				rendered="#{ConfiguracionCliente.swSuperUsuario}"
				value="#{ConfiguracionCliente.passwordConf}">
				<f:validateLength maximum="8" minimum="4" />
			</h:inputSecret>
			<h:message styleClass="form" for="inputSecret1"
				rendered="#{ConfiguracionCliente.swSuperUsuario}" />



			<h:message styleClass="form" for="btncancel" />
			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" id="btncancel"
					value="#{txt.btn_cancelar}" immediate="true"
					action="#{ConfiguracionCliente.cancelarListar}" />

				<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
					action="#{ConfiguracionCliente.saveObjeto}" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>
		</h:panelGrid>

	</h:form>
	<h:form>

		<h:panelGrid columns="3"
			rendered="#{ConfiguracionCliente.swSuperUsuario}">
			<h:panelGroup></h:panelGroup>
			<h:panelGroup></h:panelGroup>
			<h:panelGroup>
				<h:outputText styleClass="form" value="#{txt.menu_Usuario}" />
				<h:inputText styleClass="form"
					value="#{ConfiguracionCliente.usuarioProbarLdap}" />
				<h:outputText styleClass="form" value="#{txt.usuario_password}" />
				<h:inputSecret styleClass="form"
					value="#{ConfiguracionCliente.passwordProbarLdap}" />
				<h:commandButton styleClass="boton"
					action="#{ConfiguracionCliente.probarLdap}"
					value="#{txt.probarldap}" />
			</h:panelGroup>
		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
