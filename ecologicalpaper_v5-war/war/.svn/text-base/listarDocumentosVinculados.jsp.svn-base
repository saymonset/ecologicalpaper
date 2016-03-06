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
	

	<!-- 
            CUERPO DEL MENSAJE 
            -->
	<h:form>

		<h:panelGroup id="body">
			<t:dataTable id="data" styleClass="scrollerTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="car" value="#{Doc_Vinculados.docs_relacionados}"
				preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_relacionadosDocumentostabp}" />
					</f:facet>

					<h:outputText styleClass="node" value="#{car.doc_rel1.nombre}" />


				</h:column>
				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="" url="#{conftxt.img_editar}" />
					</f:facet>
					<h:commandLink id="Edit1" action="#{Doc_Vinculados.goDocument}"
						actionListener="#{Doc_Vinculados.versionId}">
						<h:outputText styleClass="node" value="#{car.doc_rel2.nombre}" />
						<f:param id="editId2" name="id1" value="#{car.doc_rel2.codigo}" />
					</h:commandLink>
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
					<h:outputFormat styleClass="form"
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




		<a4j:region renderRegionOnly="true" id="stat1">
			<f:verbatim>&nbsp;</f:verbatim>
			<h:panelGrid columns="1">
				<h:outputText styleClass="form" value="#{txt.vincularDocObser}" />
			</h:panelGrid>
			<h:panelGrid columns="2">
				<h:outputText styleClass="form" value="#{txt.filtrar_por} #{txt.doc_dueniotab}" />
				<h:selectOneMenu immediate="true"
					value="#{Doc_Vinculados.usuarioId}">
					<f:selectItems value="#{ClienteDocumentoSearchPublicados.usuarios}" />
					<a4j:support event="onchange" reRender="slct1" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
				</h:selectOneMenu>


				<h:outputText styleClass="form" value="#{txt.filtrar_por} #{txt.doc_tipotab}" />
				<h:selectOneMenu immediate="true"
					value="#{Doc_Vinculados.doc_tipoId}">
					<f:selectItems
						value="#{ClienteDocumentoSearchPublicados.doc_tipos}" />
					<a4j:support event="onchange" reRender="slct1" />
					<a4j:status for="stat1" stopText=" ">
						<f:facet name="start">
							<h:graphicImage value="#{conftxt.img_reloj}" />
						</f:facet>
					</a4j:status>
				</h:selectOneMenu>
			</h:panelGrid>

			<h:panelGrid columns="1" binding="#{backing_login.panelGrid1}">
				<h:panelGroup>
					<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
						action="#{Doc_Vinculados.goDocument}" />
					<h:commandButton styleClass="boton" value="#{txt.vincular_documentos}"
						action="#{Doc_Vinculados.editVinculado}" />
				</h:panelGroup>
			</h:panelGrid>
		</a4j:region>
	</h:form>
</f:view>



</body>

</html>
