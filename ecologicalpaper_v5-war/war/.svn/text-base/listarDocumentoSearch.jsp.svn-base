<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@page import="javax.servlet.http.HttpSession"%>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>


<script language="javascript">
	var formId; // reference to the main form
	var winId; // reference to the popup window

	function showFlowUser(action, title) {
		//features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		features="height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
	// This function calls the popup window.
	//
	function showPlaceList(action, title) {
		features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
	 
</script>
 
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

	<center>
	<h2></h2>
	</center>
	<h:form>
		<h:panelGrid columns="2" binding="#{backing_login.panelGrid1}"
			id="panelGrid1">


			<!--  <%/* <h:commandButton  value="#{txt.download}" 
						 action="#{ClienteDocumentoSearchPublicados.goDownload}"/>*/%>-->


			<f:verbatim>&nbsp;</f:verbatim>
			<f:verbatim>&nbsp;</f:verbatim>
			<f:verbatim>&nbsp;</f:verbatim>
			<f:verbatim>&nbsp;</f:verbatim>
		</h:panelGrid>
		<a4j:region renderRegionOnly="true" id="stat1">
			<h1></h1>
			<f:verbatim>&nbsp;</f:verbatim>
			<h:panelGrid columns="2">
				<f:verbatim></f:verbatim>
				<h:panelGroup>
					<h:outputText styleClass="form" value="#{txt.desde}" />
					<t:inputCalendar id="fecha_creado"
						monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
						popupButtonStyleClass="standard_bold"
						currentDayCellClass="currentDayCell"
						value="#{ClienteDocumentoSearchPublicados.fecha_creado}"
						renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
						popupDateFormat="dd.MM.yyyy"
						popupWeekString="#{txt.popup_week_string}" helpText="dd.MM.yyyy"
						forceId="true">
						<a4j:support event="onchange" reRender="findstadistica,data" />
						<a4j:status for="stat1" stopText=" ">
							<f:facet name="start">
								<h:graphicImage value="#{conftxt.img_reloj}" />
							</f:facet>
						</a4j:status>
					</t:inputCalendar>
					<h:outputText styleClass="form" value="#{txt.hasta}" />
					<t:inputCalendar id="fechaFin" monthYearRowClass="yearMonthHeader"
						weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
						currentDayCellClass="currentDayCell"
						value="#{ClienteDocumentoSearchPublicados.fecha_creadoFin}"
						renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
						popupDateFormat="dd.MM.yyyy"
						popupWeekString="#{txt.popup_week_string}" helpText="dd.MM.yyyy"
						forceId="true">
						<a4j:support event="onchange" reRender="findstadistica,data" />
						<a4j:status for="stat1" stopText=" ">
							<f:facet name="start">
								<h:graphicImage value="#{conftxt.img_reloj}" />
							</f:facet>
						</a4j:status>
					</t:inputCalendar>
				</h:panelGroup>

				<h:outputText styleClass="form" value="#{txt.doc_dueniotab}" />
				<h:selectOneMenu immediate="true"
					value="#{ClienteDocumentoSearchPublicados.usuarioId}">
					<f:selectItems value="#{ClienteDocumentoSearchPublicados.usuarios}" />
					<a4j:support event="onchange" reRender="findstadistica,data"
						ajaxSingle="true" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
				</h:selectOneMenu>

				<h:outputText styleClass="form" value="#{txt.doc_estadotab}" />
				<h:selectOneMenu immediate="true"
					value="#{ClienteDocumentoSearchPublicados.doc_estadoId}">
					<f:selectItems
						value="#{ClienteDocumentoSearchPublicados.doc_estados}" />
					<a4j:support event="onchange" reRender="findstadistica,data" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
				</h:selectOneMenu>

				<h:outputText styleClass="form" value="#{txt.doc_tipotab}" />
				<h:selectOneMenu immediate="true"
					value="#{ClienteDocumentoSearchPublicados.doc_tipoId}">
					<f:selectItems
						value="#{ClienteDocumentoSearchPublicados.doc_tipos}" />
					<a4j:support event="onchange" reRender="findstadistica,data" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
				</h:selectOneMenu>


				<h:outputText value="#{txt.estadistica}" styleClass="form" />
				<h:commandButton styleClass="boton" id="findstadistica"
					action="#{ClienteDocumentoSearchPublicados.goStadistica}"
					actionListener="#{ClienteDocumentoSearchPublicados.versionIdGrafico}"
					image="#{conftxt.img_grafico}" immediate="true"
					onmousedown="showPlaceList('StadisticasGraficarByauthorfechastadoetc.jsf?empresa=#{user_logueado.empresa.nodo}&fechaHasta=#{ClienteDocumentoSearchPublicados.fechaHasta}&fechaDesde=#{ClienteDocumentoSearchPublicados.fechaDesde}&stadtipodedocumento=#{ClienteDocumentoSearchPublicados.stadtipodedocumento}&stadestadodeldocumento=#{ClienteDocumentoSearchPublicados.stadestadodeldocumento}&stadautordeldocumento=#{ClienteDocumentoSearchPublicados.stadautordeldocumento}','')"
					onclick="return false">

				</h:commandButton>

			</h:panelGrid>

			<%
				/*<h:panelGroup >
																																											
																																											 <h:selectOneRadio id="r1"  value="#{ClienteDocumentoSearchPublicados.buscarPor}" 
																																											 immediate="true"    >
																																											 <a4j:support event="onchange" reRender="searchtext,data" />
																																											 <f:selectItem  itemValue="#{ClienteDocumentoSearchPublicados.none}"    itemLabel="#{txt.doc_keystab0}" />
																																											 <f:selectItem  itemValue="#{ClienteDocumentoSearchPublicados.searchNombreDoc}"    itemLabel="#{txt.doc_nombre}" />
																																											 <f:selectItem  itemValue="#{ClienteDocumentoSearchPublicados.searchConsecutivoDoc}"    itemLabel="#{txt.doc_consecutivotab}" />
																																											 />
																																											 <a4j:status for="stat1" stopText=" ">
																																											 <f:facet name="start">
																																											 <h:graphicImage value="#{conftxt.img_reloj}" />
																																											 </f:facet>
																																											 </a4j:status>
																																											 </h:selectOneRadio>
																																											
																																											 </h:panelGroup>*/
			%>

			<h:panelGroup id="buscar">

				<h:inputText id="searchtext" styleClass="none" size="60"
					value="#{ClienteDocumentoSearchPublicados.strBuscar}"
					immediate="true">
				</h:inputText>

				<h:selectOneMenu immediate="true"
					value="#{ClienteDocumentoSearchPublicados.doc_tiposSearchSTR}">
					<f:selectItems
						value="#{ClienteDocumentoSearchPublicados.doc_tiposSearch}" />
					<a4j:support event="onchange" reRender="data" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
				</h:selectOneMenu>
				<h:commandLink>
					<a4j:support event="onclick" reRender="data" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
					<h:graphicImage value="#{conftxt.img_search}" />
				</h:commandLink>
				<h:outputText value="#{txt.search_buscar}" />

			</h:panelGroup>

			<%
				/*
																																											 //EMPEZAMOS AQUI-----------------------------------------------------------------------------------------
							 */
			%>
			<h:panelGroup id="body">

				<t:dataTable id="data" rowIndexVar="rowIndexVar"
					rowCountVar="rowCountVar" previousRowDataVar="previousRowDataVar"
					styleClass="scrollerTable" headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2"
					columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
					var="currentMaestro"
					value="#{ClienteDocumentoSearchPublicados.doc_detalles}"
					preserveDataModel="false"
					rows="#{Utilidades.maxRegisterMostrarForTable}">

					<h:column>
						<f:facet name="header">
								<h:outputText styleClass="grees" value="#{txt.doc_verdocumento}" />
						</f:facet>
						<t:popup   styleClass="popup"
							closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
							displayAtDistanceX="10" displayAtDistanceY="10">
							
								<h:commandLink id="detallx1"
								onmousedown="showFlowUser('mostrarDocProtegido.jsf?detalle_id=#{currentMaestro.codigo}','')"
								immediate="true" onclick="return false">
								<h:graphicImage title="#{txt.doc_verdocumento}"
									url="#{currentMaestro.icono}" />
							</h:commandLink>
							
							<f:facet name="popup">
								<h:panelGroup>
									<h:panelGrid columns="1">
										<h:outputText styleClass="node" value="#{txt.doc_verdocumento}" />
									</h:panelGrid>
								</h:panelGroup>
							</f:facet>
						</t:popup>
					</h:column>


					<h:column>
						<f:facet name="header">
							<h:graphicImage styleClass="grees" title="" url="#{conftxt.img_editar}" />
						</f:facet>

						<h:commandLink id="Edit1"
							action="#{ClienteDocumentoSearchPublicados.goDocument}"
							actionListener="#{ClienteDocumentoSearchPublicados.versionId}">
							<h:outputText styleClass="node"
								value="
                                                  #{currentMaestro.doc_maestro.nombre}" />

							<f:param id="editId2" name="id"
								value="#{currentMaestro.doc_maestro.codigo}" />
						</h:commandLink>

					</h:column>



					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.estadistica}" />
						</f:facet>

						<%
							/*<h:commandLink  id="Edit1g" 
																																																																																					 action="#{ClienteDocumentoSearchPublicados.goStadistica}" 
																																																																																					 actionListener="#{ClienteDocumentoSearchPublicados.versionIdGrafico}">
																																																																																					 <h:graphicImage   
																																																																																					 title="Estadistica"
																																																																																					 url="#{conftxt.img_grafico}" /> 
																																																																																					
																																																																																					
																																																																																					 <f:param id="editIdg" 
																																																																																					 name="id" 
																																																																																					 value="#{currentMaestro.doc_maestro.codigo}" />
																																																																																					 </h:commandLink> */
						%>


						<h:commandButton styleClass="boton" id="find"
							action="#{ClienteDocumentoSearchPublicados.goStadistica}"
							actionListener="#{ClienteDocumentoSearchPublicados.versionIdGrafico}"
							image="#{conftxt.img_grafico}" immediate="true"
							onmousedown="showPlaceList('DocumentoStadisticasGraficar.jsf?codigo=#{currentMaestro.doc_maestro.codigo}','')"
							onclick="return false">

						</h:commandButton>

					</h:column>





					<h:column>
						<f:facet name="header">
							<h:graphicImage styleClass="grees" title="#{txt.doc_publico}"
								url="#{conftxt.img_publico}" />

						</f:facet>

						<h:graphicImage rendered="#{currentMaestro.doc_maestro.publico}"
							title="#{txt.doc_publico}" url="#{conftxt.img_publico}" />
					</h:column>

				 <% /*  <!--	<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.doc_estadotab}" />
						</f:facet>
						<h:outputText styleClass="form"
							value="
                                              #{currentMaestro.doc_estado.nombre}" />

					</h:column> -->  */ %>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.doc_consecutivotab}" />
						</f:facet>
						<h:outputText styleClass="node"
							value="
                                              #{currentMaestro.doc_maestro.consecutivo}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.doc_creado}" />
						</f:facet>
						<h:outputText styleClass="node"
							value="
                                              #{currentMaestro.doc_maestro.fecha_mostrar}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.doc_tipotab}" />
						</f:facet>
						<h:outputText styleClass="node"
							value="
                                              #{currentMaestro.doc_maestro.doc_tipo.nombre}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="grees" value="#{txt.doc_dueniotab}" />
						</f:facet>
						<h:outputText styleClass="node"
							value="
                                              #{currentMaestro.duenio.nombre}
                                              #{currentMaestro.duenio.apellido}
                                              #{currentMaestro.duenio.cargo.nombre}" />
					</h:column>


				 

				</t:dataTable>

				<h:panelGrid
					rendered="#{ClienteDocumentoSearchPublicados.swMostrarDesplazamiento}"
					columns="3" styleClass="scrollerTable2"
					columnClasses="standardTable_ColumnCentered">
					<h:commandButton styleClass="boton" 
						value="<" 
                                              
						
						
						action="#{ClienteDocumentoSearchPublicados.izquierdaGoBD}" />
					<h:outputText styleClass="form"
						value="#{ClienteDocumentoSearchPublicados.izquierdaBD}
                                          --#{ClienteDocumentoSearchPublicados.derechaBD}" />
					<h:commandButton value=">" styleClass="boton"
						action="#{ClienteDocumentoSearchPublicados.derechaGoBD}" />
				</h:panelGrid>










			</h:panelGroup>
		</a4j:region>
	</h:form>


</f:view>



</body>

</html>
