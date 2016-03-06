<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@page import="javax.servlet.http.HttpSession"%>


<script language="javascript">
	
</script>
<html>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
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


<SCRIPT>
	var formId; // reference to the main form
	var winId; // reference to the popup window
	// This function calls the popup window.
	//

	function showPlaceList(action, form, target) {
		features = "height=800,width=800,status=yes,toolbar=no,menubar=no,location=yes,scrollbars=yes";
		winId = window.open('ClienteDocumentoGenerar', 'list', features); // open an empty window

	}
</SCRIPT>

</head>



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

		<t:jscookMenu layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<!-- 
            CUERPO DEL MENSAJE 
            -->
	<h:form>
		<a4j:region renderRegionOnly="true" id="stat3">
			<h:panelGrid columns="3" binding="#{backing_login.panelGrid1}"
				id="panelGrid1">

				<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
					action="#{ClienteUsuario.cancelarListtUser}" />
				<h:panelGroup rendered="#{ClienteUsuario.swAddUsuario}">
					<h:panelGrid columns="2">

						<h:selectOneMenu disabled="#{!ClienteUsuario.swSuperUsuario}"
							id="selectOneMenuEmpresa" immediate="true"
							value="#{ClienteUsuario.empresaEscojer}" converter="ConverTree">
							<a4j:support event="onchange" reRender="data" />
							<f:selectItems value="#{backing_login.lstEmpresas}" />
						</h:selectOneMenu>
						<h:commandButton styleClass="boton"
							value="#{txt.menu_crearUsuario}"
							action="#{ClienteUsuario.inic_crear}" />
					</h:panelGrid>
				</h:panelGroup>

				<h:commandButton styleClass="boton" immediate="true"
					rendered="#{ClienteUsuario.swHistorico}"
					value="#{txt.usuarios_activos}"
					action="#{ClienteUsuario.historico}" />

				<h:commandButton styleClass="boton" immediate="true"
					rendered="#{!ClienteUsuario.swHistorico}" value="#{txt.historico} "
					action="#{ClienteUsuario.historico}" />
			</h:panelGrid>
			<h1><h:outputText styleClass="form" value="#{txt.search_buscar}" />
			</h1>
			<f:verbatim>&nbsp;</f:verbatim>
			<h:panelGroup>
				<h:selectOneRadio id="r1" value="#{ClienteUsuario.buscarPor}"
					immediate="true">
					<a4j:support event="onchange" reRender="searchtext" />
					<f:selectItem itemValue="#{ClienteUsuario.searchLogin}"
						itemLabel="#{txt.usuario_login}" />
					<f:selectItem itemValue="#{ClienteUsuario.searchCargo}"
						itemLabel="#{txt.usuario_cargo}" />
					<f:selectItem itemValue="#{ClienteUsuario.searchNombre}"
						itemLabel="#{txt.usuario_nombre}" />
					<f:selectItem itemValue="#{ClienteUsuario.searchApellido}"
						itemLabel="#{txt.usuario_apellido}" />
				</h:selectOneRadio>
			</h:panelGroup>


			<h:panelGroup id="buscar">
				<h:inputText id="searchtext" value="#{ClienteUsuario.strBuscar}"
					immediate="true">
					<a4j:support event="onkeyup" reRender="repeater,data" />
				</h:inputText>
				<h:graphicImage value="#{conftxt.img_search}" />
				<h:outputText value="#{txt.search_buscar}" />

			</h:panelGroup>



			<h:panelGroup id="body">
				<t:dataTable id="data" styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="car" value="#{ClienteUsuario.usuarios}"
					preserveDataModel="false"
					rows="#{Utilidades.maxRegisterMostrarForTable}">

					<h:column>
						<f:facet name="header">
							<h:graphicImage styleClass="grees" title=""
								url="#{conftxt.img_editar}" />
						</f:facet>
						<h:panelGroup rendered="#{ClienteUsuario.swModUsuario}">
							<h:commandLink id="Edit" action="#{ClienteUsuario.editUsuario}"
								actionListener="#{ClienteUsuario.selectUsuario}">
								<h:outputText styleClass="node"
									binding="#{ClienteUsuario.id_str}" id="id_editar"
									value="#{car.login}" title="#{car.login}" />
								<f:param id="editId" name="id" value="#{car.id}" />
							</h:commandLink>
						</h:panelGroup>
						<h:panelGroup rendered="#{!ClienteUsuario.swModUsuario}">
							<h:outputText styleClass="node" value="#{car.login}"
								title="#{car.login}" />
						</h:panelGroup>
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.usuario_nombre}" />
						</f:facet>
						<h:outputText styleClass="node" title="#{car.nombre}"
							value="#{car.nombre}" />
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.usuario_apellido}" />
						</f:facet>
						<h:outputText styleClass="node" value="#{car.apellido} " />
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.usuario_cargo}" />
						</f:facet>
						<h:outputText styleClass="node" value="[#{car.cargo.nombre}] " />
					</h:column>






					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.usuario_empresa}" />
						</f:facet>
						<h:outputText styleClass="node" value="#{car.empresa.nombre}" />
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.usuario_fechac}" />
						</f:facet>
						<h:outputText styleClass="node" value="#{car.fecha_creadotxt}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.mail_cliente}" />
						</f:facet>
						<h:outputText styleClass="node" value="#{car.mail_principal}" />
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.flows_trab_doc}" />
						</f:facet>
						<h:commandLink id="viewflujoswork"
							action="#{ClienteUsuario.viewHistorico}"
							actionListener="#{ClienteUsuario.selectUsuarioFlow}">
							<h:outputText styleClass="node" id="id_editarflows"
								value="#{txt.flows_trab_doc}" title="#{txt.flows_trab_doc}" />
							<f:param id="editId" name="idFlow" value="#{car.id}" />
						</h:commandLink>
					</h:column>
					<%
						/*
																
																 <!--					<h:column>-->
																 <!--						<f:facet name="header">-->
																 <!--							<h:outputText styleClass="grees" value="" />-->
																 <!--						</f:facet>-->
																 <!--						<h:commandButton styleClass="boton" id="find"-->
																 <!--							action="#{ClienteUsuario.usuarioFlowStadisticasGraficar}"-->
																 <!--							image="#{conftxt.img_grafico}"-->
																 <!--							actionListener="#{ClienteUsuario.selectUsuarioStadisticaGraficar}"-->
																 <!--							value="..." immediate="true"-->
																 <!--							onmousedown="showPlaceList('UsuarioFlowStadisticasGraficar.jsf?codigo=#{car.id}','')"-->
																 <!--							onclick="return false">-->
																 <!---->
																 <!--						</h:commandButton>-->
																 <!--					</h:column>-->

											 */
					%>
					<!-- -->


					<h:column>
						<h:panelGroup rendered="#{car.historico}">
							<f:facet name="header">
								<h:graphicImage styleClass="grees" title="#{txt.historico}"
									url="#{conftxt.img_historico}" />
							</f:facet>


							<h:commandLink action="#{ClienteUsuario.verHistoricotUsuario}"
								actionListener="#{ClienteUsuario.detalleHistorico}">
								<h:graphicImage styleClass="node" title="#{txt.historico}"
									url="#{conftxt.img_historico}" />
								<f:param id="historicoId" name="noseusa_id" value="#{car.id}" />
							</h:commandLink>
						</h:panelGroup>
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.doc_hist}" />
						</f:facet>
						<h:commandLink id="hist"
							action="#{ClienteUsuario.historicoActivo}"
							actionListener="#{ClienteUsuario.selectUsuario}">
                                    >
                                    <h:outputText styleClass="node"
								value="#{txt.doc_hist}" />
							<f:param id="editId" name="idHist" value="#{car.id}" />
						</h:commandLink>
					</h:column>



					<h:column>
						<f:facet name="header">
							<h:graphicImage styleClass="grees" title="#{txt.btn_erase}"
								url="#{conftxt.img_erase}" />
						</f:facet>
						<h:panelGroup rendered="#{car.status}">
							<h:commandLink rendered="#{car.status}" id="Desconectar"
								action="#{ClienteUsuario.listtUsuario}"
								actionListener="#{ClienteUsuario.sacardeApplicationUsuario}"
								onclick="if (!confirm('#{txt.desconectar_usuario}')) return false">
								<h:outputText styleClass="node"
									value="#{car.apellido} [#{car.cargo.nombre}]" />
								<f:param id="desconectarId" name="desconectar_id"
									value="#{car.id}" />
							</h:commandLink>
						</h:panelGroup>

						<h:panelGroup rendered="#{!car.status}">
							<h:commandLink rendered="#{car.delete}" id="del"
								action="#{ClienteUsuario.delete}"
								actionListener="#{ClienteUsuario.selectUsuario}"
								onclick="if (!confirm('#{txt.seguroeliminar}')) return false">
                                        >
                                        <h:graphicImage
									title="#{txt.btn_erase}" url="#{conftxt.img_erase}" />

								<f:param id="editId" name="id" value="#{car.id}" />
							</h:commandLink>
						</h:panelGroup>
					</h:column>










				</t:dataTable>
				<h:panelGrid columns="3" styleClass="scrollerTable2"
					columnClasses="standardTable_ColumnCentered">
					<h:commandButton styleClass="boton"
						value="<" 
                                              
						
						action=" #{ClienteUsuario.izquierdaGoBD}" />
					<h:outputText styleClass="form"
						value="#{ClienteUsuario.izquierdaBD}
                                          --#{ClienteUsuario.derechaBD}" />
					<h:commandButton styleClass="boton" value=">"
						action="#{ClienteUsuario.derechaGoBD}" />
				</h:panelGrid>


			</h:panelGroup>
		</a4j:region>
	</h:form>


</f:view>



</body>

</html>
