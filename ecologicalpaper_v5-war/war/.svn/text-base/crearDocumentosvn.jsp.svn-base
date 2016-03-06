<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="./validacione.js"></script>
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
<%@include file="inc/head.inc"%>

<body>

<f:view>



	<f:loadBundle basename="com.util.resource.ecological_conf" var="conf" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<h:messages id="messageList" showSummary="true" />
	<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
	
	<center>
	<h2><h:outputText styleClass="form"
		value="#{txt.menu_agregarDocumento}" /></h2>
	</center>
	<a4j:region renderRegionOnly="true" id="stat3">
		<h:form id="form1" enctype="multipart/form-data">

			<h:panelGrid columns="3" border="0">

				<h:outputText styleClass="form" value="#{conf.usuariosubversion}" />
				<h:inputText id="desc2" size="90" maxlength="100"
					value="#{DocumentoSvn.subVersionUsuario.usuariosubversion}"
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="desc2" showDetail="true" />

				<h:outputText styleClass="form" value="#{conf.passwordsubversion}" />
				<h:inputSecret id="desc20" size="90" maxlength="100"
					value="#{DocumentoSvn.subVersionUsuario.passwordsubversion}"
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputSecret>
				<h:message for="desc20" showDetail="true" />


				<h:outputText styleClass="form" value="#{conf.urlsubversion}" />
				<h:inputText id="desc21" size="90" maxlength="1100"
					value="#{DocumentoSvn.subVersionUsuario.urlsubversion}"
					immediate="false"
					disabled="#{not empty DocumentoSvn.filesllenadosdeListaVista}">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="desc21" showDetail="true" />

				<h:outputText styleClass="form" value="#{txt.startRevision}" />
				<h:inputText id="startRevision" size="10" maxlength="5"
					value="#{DocumentoSvn.subVersionUsuario.startRevision}"
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="startRevision" showDetail="true" />

				<h:outputText styleClass="form" value="#{txt.revision}" />
				<h:inputText id="version" size="10" maxlength="5"
					value="#{DocumentoSvn.subVersionUsuario.version}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="version" showDetail="true" />
 
				<h:panelGroup>
 					<h:panelGrid columns="3">
						<%
							/*<!--No Viene del flujo -->*/
						%>
						<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
							rendered="#{!DocumentoSvn.swUploadSvnSinFlowJsp}"
							action="#{DocumentoSvn.cancelarLista}" />
						<%
							/*<!-- Viene del flujo -->*/
						%>
						<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
							rendered="#{DocumentoSvn.swUploadSvnSinFlowJsp}"
							action="#{DocumentoSvn.cancelarListaUploadSvnIndividual}" />

						<a4j:commandButton id="btncancelarajax" styleClass="boton"
							rendered="#{not empty DocumentoSvn.listaRepositorio && !DocumentoSvn.swUploadSvnSinFlowJsp}"
							value="#{txt.limpiar}"
							action="#{DocumentoSvn.cancelarllenadosdeLista}">
							<a4j:support event="onclick" reRender="outputName,btn" />
						</a4j:commandButton>
						<%
							/*<!-- boton buscar.. -->*/
						%>
						<h:commandButton styleClass="boton" id="btnrefrescar" value="#{txt.refrescar0}"
							action="#{DocumentoSvn.refrescar}"
							onclick="this.style.display='none';">
							<a4j:support event="onclick" reRender="data" />
						</h:commandButton>

					</h:panelGrid>
				</h:panelGroup>
				<f:verbatim></f:verbatim>
				<h:panelGroup>
				</h:panelGroup>
				<f:verbatim></f:verbatim>
			</h:panelGrid>

			<h:panelGrid columns="2">
				<h:commandButton styleClass="boton" id="btnllenarLista"
					rendered="#{not empty DocumentoSvn.listaRepositorio && !DocumentoSvn.swUploadSvnSinFlowJsp}"
					value="#{txt.agregar}" action="#{DocumentoSvn.llenarLista}">
				</h:commandButton>

				<h:outputText styleClass="form" id="outputName"
					value="#{DocumentoSvn.filesllenadosdeListaVista}" />
			</h:panelGrid>

			<h:panelGrid columns="1"
				rendered="#{not empty DocumentoSvn.listaRepositorio && !DocumentoSvn.swUploadSvnSinFlowJsp
								 or not empty DocumentoSvn.filesllenadosdeListaVista}">

				<h:panelGrid columns="3" border="0">
					<h:outputText styleClass="form" value="#{txt.doc_nombretab} *" />
					<h:inputText maxlength="50" id="name1"
						value="#{DocumentoSvn.subVersionUsuario.nombreZip}"
						immediate="false">
						<f:validator validatorId="caracteresinvalidos" />
					</h:inputText>
					<f:verbatim></f:verbatim>

					<h:outputText styleClass="form" value="#{txt.doc_keystab} *" />
					<h:panelGrid columns="2">
						<h:inputTextarea id="desc" cols="40" rows="3"
							value="#{DocumentoSvn.subVersionUsuario.busquedakeys}"
							immediate="false">
							<f:validator validatorId="caracteresinvalidos" />
						</h:inputTextarea>
						<h:commandLink id="btn"
							rendered="#{not empty DocumentoSvn.listaRepositorio && !DocumentoSvn.swUploadSvnSinFlowJsp
								 or not empty DocumentoSvn.filesllenadosdeListaVista}"
							action="#{DocumentoSvn.create}"
							onclick="this.style.display='none';">
							<h:graphicImage id="imageNewVersion"
								title="#{txt.attachment} #{txt.to} #{txt.ecological}"
								url="#{conf.img_newversion}" />
						</h:commandLink>
					</h:panelGrid>
					<f:verbatim></f:verbatim>

					<h:outputText id="selectOne1" styleClass="form"
						value="#{txt.svnurlbase}" title="#{txt.svnurlbase}" />
					<h:selectOneMenu value="#{DocumentoSvn.svnUrlBase}"
						immediate="true" binding="#{DocumentoSvn.selectOneMenu1}"
						converter="ConverSvnUrlBase">
						<f:selectItems value="#{DocumentoSvn.allSvnUrlBase}" />
						<a4j:support event="onchange" ajaxSingle="true"
							reRender="selectOne21,selectOne213,data"
							action="#{DocumentoSvn.change}" />
					</h:selectOneMenu>
					<h:message for="selectOne1" styleClass="form" />

					<h:outputText styleClass="form" value="#{txt.svnnombreaplicacion}"
						title="#{txt.svnnombreaplicacion}" />
					<h:selectOneMenu id="selectOne21" immediate="true"
						binding="#{DocumentoSvn.selectOneMenu3}"
						value="#{DocumentoSvn.svnNombreAplicacion}"
						converter="ConverSvnNombreAplicacion">
						<f:selectItems value="#{DocumentoSvn.allSvnNombreAplicacion}" />
						<a4j:support event="onchange" ajaxSingle="true"
							action="#{DocumentoSvn.change2}" reRender="selectOne213,data" />
					</h:selectOneMenu>
					<h:message for="selectOne21" styleClass="form" />

					<h:outputText styleClass="form" value="#{txt.svntipoambiente}"
						title="#{txt.svntipoambiente}" />
					<h:selectOneMenu id="selectOne213" immediate="true"
						binding="#{DocumentoSvn.selectOneMenu2}"
						value="#{DocumentoSvn.svnTipoAmbiente}"
						converter="converSvnTipoAmbiente">
						<f:selectItems value="#{DocumentoSvn.allSvnTipoAmbientes}" />
						<a4j:support event="onchange" ajaxSingle="true"
							action="#{DocumentoSvn.change3}" reRender="selectOne3,data" />
				/>
			</h:selectOneMenu>
					<h:message for="selectOne213" styleClass="form" />


					<h:outputText styleClass="form" value="#{txt.svnmodulo}"
						title="#{txt.svnmodulo}" />
					<h:selectOneMenu id="selectOne3" immediate="true"
						binding="#{DocumentoSvn.selectOneMenu4}"
						value="#{DocumentoSvn.svnModulo}" converter="ConverSvnModulo">
						<f:selectItems value="#{DocumentoSvn.allSvnModulo}" />
						<a4j:support event="onchange" action="#{DocumentoSvn.change4}"
							ajaxSingle="true" reRender="urlUpload" />
					</h:selectOneMenu>
					<h:message for="selectOne3" styleClass="form" />

					<h:outputText styleClass="form"
						value="#{conf.urlsubversion} [#{txt.liberar} #{txt.workflow}]" />
					<h:outputText id="urlUpload"
						value="#{DocumentoSvn.subVersionUsuario.urlsubversionUploadAutomatico}">
					</h:outputText>
					<h:message for="urlUpload" showDetail="true" />
				</h:panelGrid>


			</h:panelGrid>

			<h:panelGroup id="pdata12"
				rendered="#{not empty DocumentoSvn.listaRepositorio}">

				<h:panelGrid columns="2">
					<h:outputText styleClass="form" value="#{txt.seleccionartodos}" />
					<h:selectBooleanCheckbox id="selectchk" value=""
						onclick="ChequearTodos(this);" />
				</h:panelGrid>

				<t:dataTable id="data" styleClass="scrollerTable"
					headerClass="standardTable_Header"
					footerClass="standardTable_Header"
					rowClasses="standardTable_Row1,standardTable_Row2" var="car"
					value="#{DocumentoSvn.listaRepositorio}" preserveDataModel="false">


					<h:column>
						<f:facet name="header">

						</f:facet>
						<!-- si  swDiferenciaEntreVersiones = true , es porque es un archivo binario
							y no c puede comparar-->
						<h:selectBooleanCheckbox
							value="#{DocumentoSvn.selectedIds[car.codigo]}" />
					</h:column>

					<h:column>
						<f:facet name="header">

						</f:facet>
						<h:panelGrid columns="1">
							<h:outputText styleClass="form" value="#{car.archivo}" />
						</h:panelGrid>
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.author}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.author}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.log}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.log}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.tipo}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.tipo}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.revision}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.revision}" />
					</h:column>

					<h:column>
						<f:facet name="header">
							<h:outputText styleClass="form" value="#{txt.fecha}" />
						</f:facet>
						<h:outputText styleClass="form" value="#{car.date}" />
					</h:column>
				</t:dataTable>


			</h:panelGroup>
		</h:form>
	</a4j:region>






</f:view>


</body>

</html>
