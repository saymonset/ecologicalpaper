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
<script type="text/javascript" src="./validacione.js"></script>
<body onLoad="javascript:refrescar();">
<f:view>
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<h:messages />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt_ecolo" />
		<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
		
	<title><h:outputText value="#{txt_ecolo.struct_crearArbol}" /></title>
	<h1></h1>
	<p></p>
	<h:form id="forma">
		<a4j:region renderRegionOnly="true" id="stat3">
			<h:panelGrid columns="1" id="panelGrid1">
				<!-- Expand/Collapse Handled By Server -->
				<t:tree2 id="serverTree" value="#{Historico.treeProiedades}"
					var="node" varNodeToggler="t" clientSideToggle="true">
					<f:facet name="-1">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">
								<t:graphicImage value="#{conftxt.img_raiz}"
									rendered="#{t.nodeExpanded}" border="0" />
								<t:graphicImage value="#{conftxt.img_raiz}"
									rendered="#{!t.nodeExpanded}" border="0" />
								<h:outputText value="#{node.description}"
									styleClass="nodeFolder" />
								<f:param name="docNum" value="#{node.identifier}" />
								<a4j:support event="onchange" reRender="data0,data" />
							</h:commandLink>

						</h:panelGroup>
					</f:facet>
					<f:facet name="0">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">
								<t:graphicImage value="#{conftxt.img_principal}"
									rendered="#{t.nodeExpanded}" border="0" />
								<t:graphicImage value="#{conftxt.img_principal}"
									rendered="#{!t.nodeExpanded}" border="0" />
								<h:outputText value="#{node.description}"
									styleClass="nodeFolder" />
								<h:outputText value=" (#{node.childCount})"
									styleClass="childCount" rendered="#{!empty node.children}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>

						</h:panelGroup>
					</f:facet>
					<f:facet name="1">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">
								<t:graphicImage value="#{conftxt.img_area}"
									rendered="#{t.nodeExpanded}" border="0" />
								<t:graphicImage value="#{conftxt.img_area}"
									rendered="#{!t.nodeExpanded}" border="0" />
								<h:outputText value="#{node.description}"
									styleClass="nodeFolder" />
								<h:outputText value=" (#{node.childCount})"
									styleClass="childCount" rendered="#{!empty node.children}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>
						</h:panelGroup>
					</f:facet>
					<f:facet name="2">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">
								<t:graphicImage value="#{conftxt.img_cargo}"
									rendered="#{t.nodeExpanded}" border="0" />
								<t:graphicImage value="#{conftxt.img_cargo}"
									rendered="#{!t.nodeExpanded}" border="0" />
								<h:outputText value="#{node.description}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>
						</h:panelGroup>
					</f:facet>

					<f:facet name="3">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">

								<t:graphicImage value="#{conftxt.img_proceso}"
									rendered="#{t.nodeExpanded}" border="0" />
								<t:graphicImage value="#{conftxt.img_proceso}"
									rendered="#{!t.nodeExpanded}" border="0" />
								<h:outputText value="#{node.description}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>
						</h:panelGroup>
					</f:facet>

					<f:facet name="4">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">
								<t:graphicImage value="#{conftxt.img_carpeta}"
									rendered="#{t.nodeExpanded}" border="0" />
								<t:graphicImage value="#{conftxt.img_carpetaopen}"
									rendered="#{!t.nodeExpanded}" border="0" />

								<h:outputText value="#{node.description}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>
						</h:panelGroup>
					</f:facet>

					<f:facet name="5">
						<h:panelGroup>
							<h:commandLink immediate="true"
								styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
								action="#{Historico.nodeClicked}"
								actionListener="#{Historico.processAction}">
								<t:graphicImage value="#{conftxt.img_doc}" border="0" />
								<h:outputText value="#{node.description}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>
						</h:panelGroup>
					</f:facet>
				</t:tree2>
			</h:panelGrid>
		</a4j:region>
	</h:form>

	<h:form id="forma1">
		<h:panelGrid columns="3" id="panelGrid1">

			<h:outputLabel value="#{tree.prefijo}" styleClass="form" />
			<f:verbatim></f:verbatim>
			<f:verbatim></f:verbatim>

			<h:panelGroup>
				<h:panelGrid columns="2" id="panelGrid2">
					<h:outputLabel value="#{txt_ecolo.struct_nombre}" styleClass="form" />
					<h:inputText id="inputTextNombre" size="70"
						value="#{tree.tree.nombre}" maxlength="40" immediate="false">
						<f:validator validatorId="caracteresinvalidos" />
					</h:inputText>
					<h:outputLabel value="#{txt_ecolo.abreviatura}" styleClass="form" />
					<h:inputText id="abreviatura" size="10"
						value="#{tree.tree.abreviatura}" maxlength="6" immediate="false">
						<f:validator validatorId="caracteresinvalidos" />
					</h:inputText>
				</h:panelGrid>
			</h:panelGroup>
			<h:message styleClass="form" for="inputTextNombre" />
			<f:verbatim></f:verbatim>

			 

			<h:panelGroup>
				<h:selectBooleanCheckbox value="#{tree.swHeredarPermisos}">
				</h:selectBooleanCheckbox>
				<h:outputText styleClass="form"
					value="#{txt.heredarPermisos}  #{txt.nodo_origen}" />
			</h:panelGroup>
			<f:verbatim></f:verbatim>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
		<h:panelGrid columns="1">
			<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">
				<h:outputLabel value="#{txt_ecolo.struct_descripcion}"
					styleClass="form" />
				<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{tree.descripcionTree}"></h:inputTextarea>
				<%
					/*	
																	 <t:inputHtml value="#{tree.descripcionTree}" style="width: 700px;"
																	 allowEditSource="false" showPropertiesToolBox="false"
																	 showLinksToolBox="false" showImagesToolBox="false"
																	 showTablesToolBox="false" showDebugToolBox="false" />*/
				%>

				<h:message styleClass="form" for="inputTextDescripcion" />

			</h:panelGrid>
			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" action="menu" />

				<h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
					action="#{tree.ingresarnodoRaiz_action}">
				</h:commandButton>
			</h:panelGrid>
		</h:panelGrid>

	</h:form>
</f:view>
</body>
</html>
