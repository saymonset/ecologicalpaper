<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@include file="/inc/head.inc"%>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<f:view>
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />

	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
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

	<script type="text/javascript" src="./validacione.js"></script>

	<title></title>
	</head>

	<body>
	<h:form>

		<t:jscookMenu layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<h:form>

		<h:messages />

		<h:panelGroup>
			<t:dataTable id="data" styleClass="scrollerTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="car" value="#{clienteFlowsParalelo.listarParalelos}"
				preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">


				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title=""
							url="#{conftxt.img_editar}" />
					</f:facet>

					<h:commandLink id="flow_someterflowparalelo"
						action="#{clienteFlowsParalelo.cambiarNombreComentarioDePlantilla}"
						actionListener="#{clienteFlowsParalelo.editFlowParalelo}">
						<h:outputText styleClass="node" styleClass="form"
							value=" #{car.nombre}" />
						<f:param id="editflowid" name="editflow_id" value="#{car.codigo}" />
					</h:commandLink>
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" id="idtxtFormato"
							value="#{txt.areadocumento}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{car.areaDocumentos.nombre}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" id="idtxtFormato"
							value="#{txt.plantillaInDocFlowParalelo}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.doc_tipo.nombre}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.comentario}" />
					</f:facet>


					<h:commandLink rendered="#{car.solicituImpresion}"
						action="#{clienteFlowsParalelo.listarSolicitudImpresion}"
						actionListener="#{clienteFlowsParalelo.editSolicitudImpresion}">
						<h:graphicImage rendered="#{car.solicituImpresion}"
							title="#{txt.solicitudImpresion}" url="#{conftxt.img_impresora}" />
						<f:param id="editimprimirid" name="editimp_id"
							value="#{car.codigo}" />
					</h:commandLink>

					<h:outputText styleClass="node"
						rendered="#{!car.solicituImpresion}"
						value="#{car.flow.comentarios}" />


				</h:column>




				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{txt.btn_erase}"
							url="#{conftxt.img_erase}" />
					</f:facet>
					<h:commandLink
						rendered="#{!car.swTipoPlantilladocumento
					&& !car.solicituImpresion}"
						id="del" action="#{clienteFlowsParalelo.delete}"
						actionListener="#{clienteFlowsParalelo.editFlowParalelo}"
						onclick="if (!confirm('#{txt.seguroeliminar}')) return false">
                                        >
                                        <h:graphicImage
							title="#{txt.btn_erase}" url="#{conftxt.img_erase}" />

						<f:param id="editflowiddel" name="editflow_iddel"
							value="#{car.codigo}" />
					</h:commandLink>

					<!--                 <%/*   SI SON PLANTILLAS GLOBALES Y TIENE PREMISO PUEDE BORRAR*/%>-->
					<h:commandLink
						rendered="#{car.swTipoPlantilladocumento && car.swTipoPlantilladocumentoDelete
						&& !car.solicituImpresion}"
						id="delPlantilla" action="#{clienteFlowsParalelo.delete}"
						actionListener="#{clienteFlowsParalelo.editFlowParalelo}"
						onclick="if (!confirm('#{txt.seguroeliminar}')) return false">
                                        >
                                        <h:graphicImage
							title="#{txt.btn_erase}" url="#{conftxt.img_erase}" />

						<f:param id="editflowiddelplantillaflowdocumento"
							name="editflow_idelplantillaflowdocumento" value="#{car.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{car.solicituImpresion
						&& clienteFlowsParalelo.seguridadMenu.toImprimirAdministrar
						}"
						action="#{clienteFlowsParalelo.delete}"
						actionListener="#{clienteFlowsParalelo.editFlowParalelo}"
						onclick="if (!confirm('#{txt.seguroeliminar}')) return false">
                                        >
                                        <h:graphicImage
							title="#{txt.btn_erase}" url="#{conftxt.img_erase}" />

						<f:param id="editflowiddelimp" name="editflow_iddelimp"
							value="#{car.codigo}" />
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

		<h:panelGrid columns="2">
			<h2><h:outputText styleClass="form"
				value="#{txt.operacion_exitosa}" /></h2>
			<f:verbatim></f:verbatim>
		</h:panelGrid>
		<h:panelGrid columns="1">
			<h:panelGroup>
				<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
					onclick="this.style.display='none';"
					action="#{clienteFlowsParalelo.listar}" />
			</h:panelGroup>
		</h:panelGrid>
	</h:form>




	</body>

	</html>
</f:view>


