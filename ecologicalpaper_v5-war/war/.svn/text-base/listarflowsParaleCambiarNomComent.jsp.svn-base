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
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />


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
	<title><h:outputText styleClass="form" value="#{txt.flow}" /></title>
	<h1></h1>
	<center>
	<h2><h:outputText styleClass="form" value="#{txt.flow}" /></h2>
	</center>
	<p></p>

	<h:form id="forma">
		<h:messages />
		<t:dataTable id="data" styleClass="scrollerTable"
			headerClass="standardTable_Header" footerClass="standardTable_Header"
			rowClasses="standardTable_Row1,standardTable_Row2"
			columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
			var="car" value="#{clienteFlowsParalelo.listaFlujosParalelos}"
			preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">






			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value=" #{txt.flow_participantes}" />
				</f:facet>
				<t:popup id="a001" styleClass="popup"
					closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
					displayAtDistanceX="10" displayAtDistanceY="10">
					<h:outputText styleClass="node" value=" #{txt.flow_participantes}" />
					<f:facet name="popup">
						<h:panelGroup>
							<h:panelGrid columns="1">
								<h:outputText styleClass="form"
									value="#{car.usuariosParticipantes}" />
							</h:panelGrid>
						</h:panelGroup>
					</f:facet>
				</t:popup>
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value=" #{txt.flows_role}" />
				</f:facet>
				<t:popup id="a002" styleClass="popup"
					closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
					displayAtDistanceX="10" displayAtDistanceY="10">

					<h:outputText styleClass="node" value=" #{txt.flows_role}" />
					<f:facet name="popup">
						<h:panelGroup>
							<h:panelGrid columns="1">
								<h:outputText styleClass="form"
									value=" #{car.gruposParticipantes}" />
							</h:panelGrid>
						</h:panelGroup>
					</f:facet>
				</t:popup>

			</h:column>


			<h:column>
				<f:facet name="header">
					<h:graphicImage styleClass="grees" title=""
						url="#{conftxt.img_editar}" />
				</f:facet>

				<h:commandLink id="flow_someterflowparalelo"
					rendered="#{car.swEdit}"
					action="#{clienteFlowsParalelo.flows_action}"
					actionListener="#{clienteFlowsParalelo.editFlow}">
					<h:outputText styleClass="node"
						value=" #{car.nombredelflujo}->(#{car.codigo})" />
					<f:param id="editfloweditarid" name="editflow_id"
						value="#{car.codigo}" />
				</h:commandLink>
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.precede}" />
				</f:facet>
				<h:inputText id="txt1" size="20" maxlength="300"
					value="#{car.precedencia}" disabled="true">
				</h:inputText>

			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.flow}" />
				</f:facet>
				<h:outputText styleClass="node" value=" #{car.flowParalelo.nombre}" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.doc_descripciontab}" />
				</f:facet>
				<h:outputText styleClass="node" value=" #{car.comentarios}" />
			</h:column>
			
			 
			
		 
			
			
			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.flow_Condicional}" />
				</f:facet>
				<h:selectBooleanCheckbox styleClass="node"
					value="#{car.condicional}" disabled="true" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.flow_Secuencial}" />
				</f:facet>
				<h:selectBooleanCheckbox styleClass="node" value="#{car.secuencial}"
					disabled="true" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.flow_lectores}" />
				</f:facet>
				<h:selectBooleanCheckbox styleClass="node" value="#{car.lectores}"
					disabled="true" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.flow_envio_mail}" />
				</f:facet>
				<h:selectBooleanCheckbox styleClass="node"
					value="#{car.notificacionMail}" disabled="true" />
			</h:column>

			<h:column>
				<f:facet name="header">
					<h:outputText styleClass="grees" value="#{txt.flow}" />
				</f:facet>
				<h:outputText styleClass="node" value=" #{car.tipoFlujo}" />
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



		<h:panelGrid columns="3" id="panelGrid1">
			<f:verbatim></f:verbatim>
			<h:panelGroup>
				<h:commandButton styleClass="boton" id="btncancel"
					value="#{txt.btn_cancelar}" immediate="true"
					action="#{clienteFlowsParalelo.cancelar}" />
				<h:commandButton styleClass="boton" value="#{txt.flow_realizar}"
					onclick="this.style.display='none';"
					action="#{clienteFlowsParalelo.crearFlujoDesdeFlujoParalelo_action}" />
			</h:panelGroup>
			<f:verbatim></f:verbatim>

		</h:panelGrid>
	</h:form>
</f:view>
</body>
</html>
