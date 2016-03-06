<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<%@page import="javax.servlet.http.HttpSession"%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<f:view>
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt_ecolo" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
	<html>
	<head>
	<title>  <h:outputText value="#{tree.user_logueado}" /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<script type="text/javascript" src="dtree.js"></script>
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

	<script type="text/javascript" src="./tree/dtree.js"></script>
	<script type="text/javascript" src="./validacione.js">
         
            </script>
	</head>

	<body>





	<div class=forms><script type="text/javascript">
	             <h:outputText value="#{tree.docTaskPendiente}" />	 
                </script></div>






	<div class="forms"><script type="text/javascript">
      <h:outputText value="#{tree.obtenArbolSeguridad}" /> 
            </script> 
            
            <h:panelGroup rendered="#{tree.swVerArbol}">
		<h:outputText styleClass="grees" value="#{txt_ecolo.user_connected}" />
		<h:form id="frmPrinc">
			<t:dataTable id="data" styleClass="scrollerTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="car" value="#{tree.usuariosConectados}"
				preserveDataModel="false" rows="#{Utilidades.verNumeroDeRegistros}">

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="form" value="#{txt.usuario_login}" />

					</f:facet>
					<h:outputText styleClass="grees" value="#{car.login}"
						title="#{car.login}" />
				</h:column>





				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="form" value="#{txt.usuario_nombre}" />
					</f:facet>

					<h:outputText styleClass="grees" title="#{car.nombre}"
						value="#{car.nombre}" />

				</h:column>



				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="form" value="#{txt.usuario_apellido}" />
					</f:facet>
					<h:outputText styleClass="form" value="#{car.apellido} " />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="form" value="#{txt.usuario_cargo}" />
					</f:facet>
					<h:outputText styleClass="form" value="[#{car.cargo.nombre}]" />
				</h:column>
			</t:dataTable>
			<h:panelGroup>
				<f:verbatim></f:verbatim>
				<f:verbatim></f:verbatim>
				<f:verbatim></f:verbatim>
				<f:verbatim></f:verbatim>
				<f:verbatim></f:verbatim>
				<f:verbatim></f:verbatim>
			</h:panelGroup>
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
					<h:outputFormat value="#{example_messages['dataScroller_pages']}"
						styleClass="grees">
						<f:param value="#{rowsCount}" />
						<f:param value="#{displayedRowsCountVar}" />
						<f:param value="#{firstRowIndex}" />
						<f:param value="#{lastRowIndex}" />
						<f:param value="#{pageIndex}" />
						<f:param value="#{pageCount}" />
					</h:outputFormat>

				</t:dataScroller>
			</h:panelGrid>
			<h:inputHidden binding="#{tree.varOculta}" immediate="true"
				id="cantidadCapturada" value="#{tree.cantidadCapturada}"></h:inputHidden>
		</h:form>
	</h:panelGroup> <!--<h:graphicImage width="276" height="90" title="#{txt.multiplataforma}"
			url="#{conftxt.img_logo2}" />-->
	</body>
	</html>
</f:view>