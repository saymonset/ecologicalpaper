<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<SCRIPT>
	
</SCRIPT>
<html>
<head>

<script language="javascript">
	var formId; // reference to the main form
	var winId; // reference to the popup window

	function showFlowUser(action, title) {
		//features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		features = "height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
	// This function calls the popup window.
	//
	function showPlaceList(action, title) {
		features = "height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";
		winId = window.open(action, title, features); // open an empty window
	}
</script>

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

		<h:panelGroup id="body">
			<t:dataTable id="data" styleClass="scrollerTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="car" value="#{Documento.publicadosUsuComentLst}"
				preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.Operaciones_nombre}" />
					</f:facet>

					<h:outputText styleClass="node"
						value="#{car.doc_detalle.doc_maestro.nombre}" />
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_estadotab}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{car.doc_detalle.doc_estado.nombre}" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_version}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{car.doc_detalle.mayorVer}.#{car.doc_detalle.minorVer}" />
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.menu_Usuario}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.usuario}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.fecha}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.fechaStr}" />
				</h:column>

			 

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.comentario}" />
					</f:facet>
					<h:outputText styleClass="node" value="#{car.comentario}" />
				</h:column>
				<%
					/*
																	 <!--				<h:column>-->
																	 <!--					<f:facet name="header">-->
																	 <!--						<h:outputText styleClass="grees" value="#{txt.comentario}" />-->
																	 <!--					</f:facet>-->
																	 <!--					<h:commandLink-->
																	 <!--						onmousedown="showFlowUser('editarDocumento.jsf?publicadosUsuComent=1&usuario_id=#{car.usuario.id}&detalle_id=#{car.doc_detalle.codigo}','')"-->
																	 <!--						immediate="true" onclick="return false" immediate="true">-->
																	 <!--						<h:graphicImage title="#{txt.doc_detalle}"-->
																	 <!--							url="#{car.doc_detalle.icono}" />-->
																	 <!---->
																	 <!--						<f:param id="usuarioid" name="idDetallepublic1"-->
																	 <!--							value="#{car.usuario.id}" />-->
																	 <!---->
																	 <!--						<f:param id="docdetalleid" name="idDetallepublic2"-->
																	 <!--							value="#{car.doc_detalle.codigo}" />-->
																	 <!--					</h:commandLink>-->
																	 <!--				</h:column>-->


									 */
				%>



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
			immediate="true"
			action="#{Documento.regresarListarDocumentosPublicar}" />


	</h:form>
</f:view>



</body>

</html>
