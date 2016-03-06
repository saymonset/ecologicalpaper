<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

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
<%@include file="inc/head.inc"%>
<script type="text/javascript" src="./validacione.js"></script>
<body>

<f:view>


	<t:saveState id="ss1" value="#{tabbedPaneBean}" />

	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>


	<f:subview id="panelTabbedPane2">


		<t:panelTabbedPane bgcolor="#CCFFFF" serverSideTabSwitch="true">
            <%/*<!-- ESTOS SON USUARIOS -->*/%>
			<f:subview id="tab2">
			<%/*	<!-- si estopy con roles, deshabilitom  usuarios--> */ %>
				<t:panelTab disabled="#{Flows.swRoleVisible}"
					label="#{txt.flow_participantes}"
					rendered="#{tabbedPaneBean.tab2Visible}">
					<jsp:include page="flowsTab2.jsp" />
				</t:panelTab>
			</f:subview>

           <%/*<!-- ESTOS SON ROLES -->*/%>
			<f:subview id="tab3">
				<!-- si estopy con usuarios, deshabilitom roles -->
				<t:panelTab disabled="#{Flows.swUserVisible}" id="tab3"
					label="#{txt.flows_role_Participantes}"
					rendered="#{tabbedPaneBean.tab3Visible}">
					<jsp:include page="flowsTab3.jsp" />
				</t:panelTab>
			</f:subview>
           <%/* <!-- ESTOS SON PRINCIPAL -->*/%>
			<f:subview id="tab1">
				<t:panelTab disabled="#{!Flows.swPrincipalVisible}" id="tab1"
					label="#{txt.flows_trab_doc}"
					onclick="#{tabbedPaneBean.inicializarSessiones}"
					rendered="#{tabbedPaneBean.tab1Visible}">
					<jsp:include page="flowsTab1.jsp" />
				</t:panelTab>
			</f:subview>





		</t:panelTabbedPane>
	</f:subview>

</f:view>


</body>

</html>
