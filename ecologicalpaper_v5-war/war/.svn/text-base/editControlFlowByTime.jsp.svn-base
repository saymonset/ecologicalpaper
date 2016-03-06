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
		value="#{txt.asignacio_horas}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.asignacio_horas}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:panelGrid columns="3" id="panelGrid1">

			<h:outputLabel styleClass="form" value="#{txt.profesion_nombre}" />
			<h:outputText styleClass="form"
				rendered="#{!listarControlFlowByTime.flowControlByUsuarioBean.swEsRole}"
				value="#{listarControlFlowByTime.flowControlByUsuarioBean.flow_Participantes.participante.apellido}
                                  #{listarControlFlowByTime.flowControlByUsuarioBean.flow_Participantes.participante.nombre}
                                  [#{listarControlFlowByTime.flowControlByUsuarioBean.flow_Participantes.participante.cargo.nombre}]" />

			<h:outputText styleClass="form"
				rendered="#{listarControlFlowByTime.flowControlByUsuarioBean.swEsRole}"
				value="#{listarControlFlowByTime.flowControlByUsuarioBean.role.nombre}" />
			<h:message styleClass="form" for="inputTextNombre" />

			<%
				/*    * Error de validación
							 * Error de validación
							 Se corrige en el metodo llenar horas colocar
							 hora es string, se cambia por integer
							 item=new SelectItem(new Integer(hora),hora);
							 */
			%>
			<h:outputText styleClass="form" value="#{txt.num_horas}" />
			<h:selectOneMenu id="horas31"
				value="#{listarControlFlowByTime.flowControlByUsuarioBean.horasAsignadas}">
				<f:selectItems value="#{listarControlFlowByTime.llenarHoras}" />
			</h:selectOneMenu>
			<f:verbatim></f:verbatim>

			<h:outputText styleClass="form" value="#{txt.minutos}" />
			<h:selectOneMenu id="horas32"
				value="#{listarControlFlowByTime.flowControlByUsuarioBean.minutosAsignados}">
				<f:selectItems value="#{listarControlFlowByTime.llenarMinutos}" />
			</h:selectOneMenu>
			<f:verbatim></f:verbatim>


			<h:message styleClass="form" for="btncancel" />
			<h:panelGroup>
				<h:panelGrid columns="2">
					<h:commandButton styleClass="boton" id="btncancel"
						value="#{txt.btn_cancelar}"
						action="#{listarControlFlowByTime.cancelarListar}" />

					<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
						action="#{listarControlFlowByTime.saveObjeto}" />
				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
