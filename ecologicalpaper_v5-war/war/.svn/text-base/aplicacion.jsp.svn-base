
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<f:view>
	<html>
	<%@include file="/inc/head.inc"%>

	<script type="text/javascript" src="./validacione.js">
	
</script>



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
	function showFlowUser(action, title) {
		//	features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		features = "height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
</SCRIPT>

	</head>
	<body>


	<h:form>
		<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
			var="txt" />
		<f:loadBundle
			basename="org.apache.myfaces.examples.resource.example_messages"
			var="example_messages" />
		<f:loadBundle basename="com.util.resource.ecological_conf"
			var="conftxt" />

		<h:messages />

		<t:jscookMenu id="menu2" layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>


	<h:form id="ff">





		<h:panelGroup rendered="#{tree.swMostrarListDocumentos}">

			<t:dataTable id="data" styleClass="scrollerTable"
			width="50%"
			newspaperOrientation="horizontal"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_Column,standardTable_Column
				,standardTable_Column,standardTable_Column,standardTable_Column,standardTable_Column,standardTable_Column,standardTable_Column"
				var="car" value="#{tree.mostrarListDocumentos}"
				preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_verdocumento}" />
					</f:facet>
					<t:popup id="a00" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">

						<h:commandLink id="detalle"
							onmousedown="showFlowUser('mostrarDocProtegido.jsf?detalle_id=#{car.doc_detalle.codigo}','')"
							immediate="true" onclick="return false">
							<h:graphicImage title="#{txt.doc_verdocumento}"
								url="#{car.doc_detalle.icono}" />
						</h:commandLink>

						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText value="#{txt.doc_verdocumento}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>


				</h:column>


				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.mas}" />
					</f:facet>

					<t:popup id="a0" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:commandLink styleClass="node" id="Edit" type="submit"
							action="#{tree.actionDocumento}"
							onclick="this.style.display='none';"
							actionListener="#{tree.selectId}">

							<h:graphicImage title="#{txt.mas}" url="#{conftxt.img_mas}" />
							<f:param id="editId" name="id" value="#{car.tree.nodo}" />
						</h:commandLink>

						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">  
									<h:outputText value="#{txt.doc_detalle}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>
				</h:column>
				<h:column >
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_nombre}" />
					</f:facet>

					<t:popup id="a" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:outputText styleClass="node" value=" #{car.nombre}" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText
										value="[#{car.doc_detalle.doc_estado.nombre}]
									#{txt.doc_version}:#{car.doc_detalle.mayorVer}.#{car.doc_detalle.minorVer}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>
				</h:column>


				<h:column>
					<f:facet name="header">
						<h:outputText value="#{txt.doc_estadotab}" />
					</f:facet>
					<t:popup id="a1" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:outputText styleClass="node"
							value="#{car.doc_detalle.doc_estado.nombre}" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText value="#{car.doc_detalle.doc_estado.nombre}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>

				</h:column>


				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.areadocumento}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.doc_detalle.areaDocumentos!=null?
					car.doc_detalle.areaDocumentos.nombre:''}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_tipotab}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.doc_tipo.nombre}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_consecutivotab}" />
					</f:facet>
					<t:popup id="a2" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:outputText styleClass="node" value="#{car.consecutivo}" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText value="#{car.consecutivo}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>


				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.usuario_fechac}" />
					</f:facet>
					<t:popup id="a3" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:outputText styleClass="node" value="#{car.fecha_mostrar}" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText value="#{car.fecha_mostrar}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>


				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.pesomgb}" />
					</f:facet>
					<t:popup id="a4" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:outputText styleClass="node"
							value="#{car.doc_detalle.size_doc}" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText value="#{car.doc_detalle.size_doc}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>


				</h:column>
			</t:dataTable>

			<h:panelGrid columns="1" styleClass="scrollerTable2"
				columnClasses="standardTable_ColumnCentered">
				<t:dataScroller id="scroll_1" for="data"
					fastStep="#{Utilidades.verNumeroDeRegistros}"
					pageCountVar="pageCount" pageIndexVar="pageIndex"
					styleClass="scroller" paginator="true"
					paginatorMaxPages="#{Utilidades.verpaginatorMaxPages}"
					paginatorTableClass="paginator"
					paginatorActiveColumnStyle="font-weight:bold;" immediate="true"
					actionListener="#{scrollerList.scrollerAction}">
					<f:facet name="first">
						<t:graphicImage url="/images/arrow-first.gif" border="1" />
					</f:facet>
					<f:facet name="last">
						<t:graphicImage url="/images/arrow-last.gif" border="1" />
					</f:facet>
					<f:facet name="previous">
						<t:graphicImage url="/images/arrow-previous.gif" border="1" />
					</f:facet>
					<f:facet name="next">
						<t:graphicImage url="/images/arrow-next.gif" border="1" />
					</f:facet>
					<f:facet name="fastforward">
						<t:graphicImage url="/images/arrow-ff.gif" border="1" />
					</f:facet>
					<f:facet name="fastrewind">
						<t:graphicImage url="/images/arrow-fr.gif" border="1" />
					</f:facet>


				</t:dataScroller>
				<t:dataScroller id="scroll_2" for="data" rowsCountVar="rowsCount"
					displayedRowsCountVar="displayedRowsCountVar"
					firstRowIndexVar="firstRowIndex" lastRowIndexVar="lastRowIndex"
					pageCountVar="pageCount" immediate="true" pageIndexVar="pageIndex">
					<h:outputFormat styleClass="node"
						value="#{example_messages['dataScroller_pages']}"
						styleClass="standard">
						<f:param value="#{rowsCount}" />
						<f:param value="#{displayedRowsCountVar}" />
						<f:param value="#{firstRowIndex}" />
						<f:param value="#{lastRowIndex}" />
						<f:param value="#{pageIndex}" />
						<f:param value="#{pageCount}" />
					</h:outputFormat>
				</t:dataScroller>
			</h:panelGrid>
		</h:panelGroup>
	</h:form>


	</body>
	</html>

</f:view>