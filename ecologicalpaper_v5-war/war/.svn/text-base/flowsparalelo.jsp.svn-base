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

		<h:panelGrid columns="1">

			<h:panelGroup rendered="#{Flows.flowParalelo.flow.plantilla}">
				<h:outputText styleClass="form"
					value="#{txt.struct_nombre} #{txt.plantilla}: " />
				<h:inputText 
				  disabled="#{Flows.swDeshabilitarEditaSolicitudimpresion}"
				id="txt1" size="40" maxlength="40" required="false"
					value="#{Flows.flowParalelo.nombre}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
			</h:panelGroup>
			<!--			Flows.flowParalelo.flow.plantilla= este sw viene del flujo
                Flows.swTipoFormatoPlantilla= este sw viene del constructor clienteflows->inicializa
                Flows.toPlantillaInDocFlowParalelo= este sw viene de la permisologia del usuario

-->

 
<%/* <!--<h:outputText styleClass="form"-->
<!--					value="plantilla: #{Flows.flowParalelo.flow.plantilla} -->
<!--					swTipoFormatoPlantilla: #{Flows.swTipoFormatoPlantilla}-->
<!--					Flows.toPlantillaInDocFlowParalelo: #{Flows.toPlantillaInDocFlowParalelo}-->
<!--					!Flows.swFlowParaleloSession: #{!Flows.swFlowParaleloSession}-->
<!--					" />-->*/%>
			<h:panelGrid columns="3"
				rendered="#{Flows.flowParalelo.flow.plantilla && Flows.swTipoFormatoPlantilla
			&& Flows.toPlantillaInDocFlowParalelo
			&& !Flows.swFlowParaleloSession}">
			
				<h:outputText id="idtxtFormato" styleClass="form"
					value="#{txt.plantillaInDocFlowParalelo} (#{Flows.areaDocumentos}-#{Flows.tipoDocumento})" />
				<h:selectBooleanCheckbox disabled="#{Flows.swFlowParaleloSession}"
					value="#{Flows.flowParalelo.swTipoPlantilladocumento}" />
				<h:message styleClass="form" for="idtxtFormato" />
			</h:panelGrid>

		</h:panelGrid>


		<h:panelGroup>
			<t:dataTable id="data" styleClass="scrollerTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="car" value="#{clienteFlowsParalelo.listaFlujosParalelos}"
				preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="form"
							value="#{txt.doc_nombre} #{txt.flowsub} " />
					</f:facet>
					<t:popup id="a" styleClass="popup"
						closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
						displayAtDistanceX="10" displayAtDistanceY="10">
						<h:outputText styleClass="form"
							value=" #{car.nombredelflujo}->(#{car.codigo})" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<h:outputText value="#{car.nombredelflujo}->(#{car.codigo})" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>
				</h:column>

				<h:column rendered="#{clienteFlowsParalelo.swMostrarListaPreceder}">
					<f:facet name="header">
						<h:outputText styleClass="form" value="#{txt.precede}" />
					</f:facet>
					<h:inputText id="txt1" size="20"
						rendered="#{clienteFlowsParalelo.swMostrarListaPreceder}"
						maxlength="300" value="#{car.precedencia}" disabled="true">
					</h:inputText>

				</h:column>


				<h:column rendered="#{clienteFlowsParalelo.swMostrarListaPreceder}">
					<f:facet name="header">
						<h:graphicImage title="" url="#{conftxt.img_editar}" />
					</f:facet>

					<h:commandLink id="saveflowparaleloprecede"
						rendered="#{clienteFlowsParalelo.swMostrarListaPreceder}"
						action="#{clienteFlowsParalelo.actionEdit}"
						actionListener="#{clienteFlowsParalelo.editPreceder}">
						<h:outputText value="#{txt.precede}" />
						<f:param id="editflowid" name="editflow_id" value="#{car.codigo}" />
					</h:commandLink>



				</h:column>


				<h:column>
					<f:facet name="header">
						<h:graphicImage title="" url="#{conftxt.img_editar}" />
					</f:facet>

					<h:commandLink id="saveflowparalelo"
						action="#{clienteFlowsParalelo.flows_actionNew}"
						actionListener="#{clienteFlowsParalelo.editFlow}">
						<h:outputText value="#{txt.edit}" />
						<f:param id="editfloweditarid" name="editflow_id"
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
			<h:panelGroup>
				<h:outputText styleClass="form" value="#{txt.flow_paralelodecision}" />
				<h:panelGrid columns="2">
					<h:selectOneRadio id="r1" styleClass="form"
					  disabled="#{Flows.swDeshabilitarEditaSolicitudimpresion}"
						value="#{Flows.swCrearHijoDeFlowParalelo}">

						<f:selectItem itemValue="#{Flows.swSi}" itemLabel="#{txt.si}" />
						<f:selectItem itemValue="#{!Flows.swSi}" itemLabel="#{txt.no}" />

					</h:selectOneRadio>
					<h:panelGrid columns="2">
						<h:commandButton styleClass="boton" value="#{txt.btn_aceptar}"
						    immediate="#{Flows.swDeshabilitarEditaSolicitudimpresion}"
							onclick="this.style.display='none';" action="#{Flows.aceptar}" />
						

					</h:panelGrid>

				</h:panelGrid>
			</h:panelGroup>
		</h:panelGrid>

		<h:panelGrid columns="2">
			<f:verbatim></f:verbatim>
			<h2><h:outputText styleClass="form" rendered="#{Flows.flowParalelo.flow.plantilla}"
				value="#{txt.plantilla} #{txt.operacion_exitosa}" /></h2>

		</h:panelGrid>
	</h:form>




	</body>

	</html>
</f:view>


