<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT>
	
</SCRIPT>
<html>
<head>


<script>
	function ChequearTodos(chkbox) {

		palabras = new Array(document.forms[0].elements.length);
		var j = -1;
		for ( var i = 0; i < (document.forms[0].elements.length); i++) {
			var nombre = document.forms[0].elements[i];
			if (nombre.name.charAt(nombre.name.lastIndexOf('I')) == 'I') {
				palabras[++j] = nombre.name
						.substring(0, nombre.name.length - 1);
			}
		}
		arreglo = new Array(j);
		for ( var k = 0; k <= j; k++) {
			arreglo[k] = palabras[k];
		}
		for ( var l = 0; l <= j; l++) {
			var nom0 = arreglo[l];
			for ( var i = 0; i < (document.forms[0].elements.length); i++) {
				var nom1 = document.forms[0].elements[i];
				if (nom0 == nom1.name) {
					if ((nom1.type == "hidden")) {
						if (chkbox.checked == false) {
							nom1.value = 0;
						} else {
							nom1.value = 1;
						}
					}
				}
			}
			for ( var i = 0; i < (document.forms[0].elements.length); i++) {
				var elemento = document.forms[0].elements[i];
				if (elemento.type == "checkbox") {
					elemento.checked = chkbox.checked
				}
			}
		}
	}
</script>
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
	<h:messages />


	<!-- 
            CUERPO DEL MENSAJE 
            -->
	<h:form>

		<h:panelGrid columns="3">
			<h:panelGrid columns="3">
				<h:outputText styleClass="form" value="#{txt.doc_publicar}" />
				<h:graphicImage title="#{txt.doc_publicar}"
					url="#{conftxt.img_publico}" />
				<h:selectBooleanCheckbox
					value="#{Documento.doc_detalle.doc_maestro.publico}"
					immediate="true">
					<a4j:support event="onchange" reRender="putPublic" />
				</h:selectBooleanCheckbox>
			</h:panelGrid>
			<f:verbatim></f:verbatim>
			<f:verbatim></f:verbatim>
			<h:panelGrid columns="2">
				<h:outputText styleClass="form" value="#{txt.usuario_fechab}" />
				<t:inputCalendar id="fecha_caduca" onkeyup="borraCaracter(this)"
					monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
					popupButtonStyleClass="standard_bold"
					currentDayCellClass="currentDayCell"
					value="#{Documento.doc_detalle.fechaexpirapubli}"
					renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
					popupDateFormat="MM/dd/yyyy"
					popupWeekString="#{txt.popup_week_string}" helpText="MM/DD/YYYY"
					forceId="true" />
			</h:panelGrid>
			<f:verbatim></f:verbatim>
			<f:verbatim></f:verbatim>

			<h:panelGrid columns="2">
				<h:outputText styleClass="form" value="#{txt.publicador}" />
				<h:outputText styleClass="form" value="#{doc_detalle.publicador}" />
				<h:outputText styleClass="form" value="#{txt.fecha_publicado}" />
				<h:outputText styleClass="form" value="#{doc_detalle.fechapubliStr}" />
				<h:outputText styleClass="form" value="#{txt.usuario_fechab}" />
				<h:outputText styleClass="form"
					value="#{doc_detalle.fechaexpirapubliStr}" />

				 

			</h:panelGrid>
			<f:verbatim></f:verbatim>
			<f:verbatim></f:verbatim>



		</h:panelGrid>

		<h:panelGrid columns="2">
			<h:outputText styleClass="form" value="#{txt.seleccionartodos}" />
			<h:selectBooleanCheckbox id="selectchk" value=""
				onclick="ChequearTodos(this);" />
		</h:panelGrid>

		<h:panelGroup id="body">
			<t:dataTable id="data" styleClass="scrollerTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="car" value="#{Documento.roles}" preserveDataModel="false"
				rows="#{Utilidades.verNumeroDeRegistros}">

				<h:column>
					<f:facet name="header">
						<h:outputText value="#{txt.publicaronlygrupos}" />
					</f:facet>
					<!-- si  swDiferenciaEntreVersiones = true , es porque es un archivo binario
							y no c puede comparar-->
					<h:selectBooleanCheckbox
						value="#{Documento.selectedIds[car.codigo]}" />
				</h:column>




				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.Operaciones_nombre}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{car.nombre} [#{car.empresa.nombre}]" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.role_descripcion}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.descripcion}" />
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


		<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
			immediate="true" action="#{Documento.regresarVerDocumento}" />

		<h:commandButton styleClass="boton" value="#{txt.usuario_guardar}"
			action="#{Documento.putPublico}">
			<f:param name="idvinculo111112"
				value="#{Documento.doc_maestro.codigo}" />
		</h:commandButton>

	</h:form>
</f:view>



</body>

</html>
