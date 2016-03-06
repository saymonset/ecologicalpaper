<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

  
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="./tree/dtree.js"></script>
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
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
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



	<a4j:region renderRegionOnly="true" id="stat3">
		<h:form id="forma">


			<h:panelGrid columns="1" id="panelGrid1">
				<!-- Expand/Collapse Handled By Server -->
				<t:tree2 id="serverTree" value="#{Historico.treeData}" var="node"
					varNodeToggler="t" clientSideToggle="false">
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
								action="#{Historico.viewDocumentoPDF}"
								actionListener="#{Historico.verDocId}">
								<t:graphicImage value="#{conftxt.img_doc}" border="0" />
								<h:outputText value="#{node.description}" />
								<f:param name="docNum" value="#{node.identifier}" />
							</h:commandLink>
						</h:panelGroup>
					</f:facet>
				</t:tree2>
			</h:panelGrid>

		</h:form>

		<h1><h:outputText styleClass="form" value="#{txt.historico}" /></h1>
		<%
			/*ELIMINADOS TREE*/
		%>
		<h:form>
			<h:panelGroup rendered="#{Historico.swhayData}" id="data0">

				<t:dataTable id="data" styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="car" value="#{Historico.historicosTreeEliminado}"
					preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.struct_nombre}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.tree_origen.nombre}"
							title="#{car.tree_origen.nombre}" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.usuario_elimino}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="#{car.usuario_accion.nombre}
                                                  #{car.usuario_accion.apellido}[#{car.usuario_accion.cargo.nombre}]"
							title="#{car.usuario_accion.nombre}
                                                  #{car.usuario_accion.apellido}[#{car.usuario_accion.cargo.nombre}]" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.pc_accion}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.maquina}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.fecha_elimino}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.fechaAux}"
							title="#{car.fechaAux}" />
					</h:column>

				</t:dataTable>

				<%
					/*
																																																																																																																																						 SOLO PARA HISTORICO, SE HIZO ASI PORQUE DABA ERROR ANTERIORMENTE
																																																																																																																																						
									 */
				%>

				<h:panelGrid columns="1" cellspacing="0" border="0" width="75%">
					<h:outputText styleClass="form" value="#{txt.doc_descripciontab}"></h:outputText>
					 <h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{Historico.cualquierComentario}" disabled="true"></h:inputTextarea>
					<% /*
					<t:inputHtml displayValueOnly="true"
						value="#{Historico.cualquierComentario}" style="width: 700px;"
						allowEditSource="false" showPropertiesToolBox="false"
						showLinksToolBox="false" showImagesToolBox="false"
						showTablesToolBox="false" showDebugToolBox="false" />
						*/ 
						%>


				</h:panelGrid>


			</h:panelGroup>



			<%
				/*MODIFICADOS TREE*/
			%>

			<h:panelGroup rendered="#{Historico.swhayData}">

				<t:dataTable styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="car" value="#{Historico.historicosTreeCambio}"
					preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.struct_nombre}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.tree_origen.nombre}"
							title="#{car.tree_origen.nombre}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.menu_Usuario}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="#{car.usuario_accion.nombre}
                                                  #{car.usuario_accion.apellido}[#{car.usuario_accion.cargo.nombre}]"
							title="#{car.usuario_accion.nombre}
                                                  #{car.usuario_accion.apellido}[#{car.usuario_accion.cargo.nombre}]" />
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.nodo_origen}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="#{car.tree_anterior.nombre}"
							title="#{car.tree_anterior.nombre}" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.nodo_destino}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.tree_new.nombre}"
							title="#{car.tree_new.nombre}" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.pc_accion}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.maquina}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.doc_cambio}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.fechaAux}"
							title="#{car.fechaAux}" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText value="#{txt.doc_descripciontab}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.comentarios}"
							title="#{car.comentarios}" />
					</h:column>
				</t:dataTable>



			</h:panelGroup>




			<h:panelGroup id="btones">
				<h:panelGrid columns="1" id="panelGrid2">
					<h:commandButton styleClass="boton" id="btncancel" value="#{txt.btn_cancelar}"
						action="#{Historico.cancelar}" />

				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>


		</h:form>

	</a4j:region>


</f:view>
</body>
</html>
