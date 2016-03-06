<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="./validacione.js"></script>
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
<%@include file="inc/head.inc"%>

<body>

<f:view>



	<f:loadBundle basename="com.util.resource.ecological_conf" var="conf" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />

	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
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

	<h:form id="form1" enctype="multipart/form-data">
		<a4j:region renderRegionOnly="true" id="stat3">
			<h:messages id="messageList" showSummary="true" />
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


				<h:outputText styleClass="form" id="outtxt"
					rendered="#{!DocumentoSvn.automaticoCargaSvn}"
					value="#{conf.urlsubversion}" />
				<h:inputText id="urlUpload2" size="90" maxlength="1100"
					value="#{DocumentoSvn.subVersionUsuario.urlsubversionUpload}"
					rendered="#{!DocumentoSvn.automaticoCargaSvn}" immediate="false">
					<a4j:support event="onkeyup"
						action="#{DocumentoSvn.inicilizaChange}"
						reRender="selectOne1,selectOne21,selectOne3,urlUpload" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="urlUpload" showDetail="true"
					rendered="#{!DocumentoSvn.automaticoCargaSvn}" />



			</h:panelGrid>
			<h:panelGrid columns="3" border="0"
				rendered="#{DocumentoSvn.automaticoCargaSvn}">
 
				<h:outputText id="selectOne1" styleClass="form"
					value="#{txt.svnurlbase}" title="#{txt.svnurlbase}" />
				<h:selectOneMenu value="#{DocumentoSvn.svnUrlBase}" immediate="true"
					binding="#{DocumentoSvn.selectOneMenu1}"
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


			<h:panelGroup>
				<h:panelGrid columns="3">

					<h:commandButton styleClass="boton" value="#{txt.btn_menu}"
						action="#{DocumentoSvn.cancelarLista}" />

					<h:commandButton styleClass="boton" id="btnupload" value="#{txt.upload}"
						rendered="#{not empty DocumentoSvn.svnUploadLista}"
						action="#{DocumentoSvn.uploadSeleccionSvn}"
						onclick="this.style.display='none';">
						<a4j:support event="onclick" reRender="btnupload,dataupload" />
					</h:commandButton>

					<%
						/*<!--<h:commandLink styleClass="form" id="Edit" type="submit"
																																																																																																										 action="#{DocumentoSvn.uploadSvnSinFlow}"
																																																																																																										 onclick="this.style.display='none';">
																																																																																																										 <h:graphicImage title="#{txt.mas}" url="#{conftxt.img_mas}" />
																																																																																																										 </h:commandLink>-->*/
					%>
				</h:panelGrid>
			</h:panelGroup>


			<h:panelGroup rendered="#{not empty DocumentoSvn.svnUploadLista}">
				<h:panelGrid columns="1">
					<t:dataTable id="dataupload" styleClass="scrollerTable"
						headerClass="standardTable_Header"
						footerClass="standardTable_Header"
						rowClasses="standardTable_Row1,standardTable_Row2"
						columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
						var="car" value="#{DocumentoSvn.svnUploadLista}"
						preserveDataModel="false"
						rows="#{Utilidades.verNumeroDeRegistrosSVN}">


						<h:column>
							<f:facet name="header">
								<h:outputText value="#{txt.menu_agregarDocumentosvn}" />
							</f:facet>
							<!-- si  swDiferenciaEntreVersiones = true , es porque es un archivo binario
							y no c puede comparar-->
							<h:selectBooleanCheckbox
								value="#{DocumentoSvn.selectedIdsUploads[car.codigoParticipante]}" />
						</h:column>
						
					
						

						<h:column>
							<h:commandLink action="#{DocumentoSvn.viewDocumentoAttachment}"
								actionListener="#{DocumentoSvn.selectCommunAttachment}">
								<h:graphicImage style="form" id="imageAttach"
									title="#{txt.doc_verdocumento}" url="#{car.icono}" />
								<f:param id="editIdAttach" name="idAttach"
									value="#{car.codigoParticipante}" />
							</h:commandLink>
						</h:column>
						
							<h:column>
							<f:facet name="header">
								<h:outputText value="" />
							</f:facet>
							<t:popup id="a2" styleClass="popup"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true" displayAtDistanceX="10"
								displayAtDistanceY="10">
								<h:outputText styleClass="form" value="#{car.nameFile}" />
								<f:facet name="popup">
									<h:panelGroup>
										<h:panelGrid columns="1">
											<h:outputText value="#{car.nameFile}" />
										</h:panelGrid>
									</h:panelGroup>
								</f:facet>
							</t:popup>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:outputText value="#{txt.author}" />
							</f:facet>
							<t:popup id="a2" styleClass="popup"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true" displayAtDistanceX="10"
								displayAtDistanceY="10">
								<h:outputText styleClass="form" value="#{car.autor}" />
								<f:facet name="popup">
									<h:panelGroup>
										<h:panelGrid columns="1">
											<h:outputText value="#{car.autor}" />
										</h:panelGrid>
									</h:panelGroup>
								</f:facet>
							</t:popup>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:outputText value="#{txt.comentario}" />
							</f:facet>
							<t:popup id="a2" styleClass="popup"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true" displayAtDistanceX="10"
								displayAtDistanceY="10">
								<h:outputText styleClass="form" value="#{car.comentarios}" />
								<f:facet name="popup">
									<h:panelGroup>
										<h:panelGrid columns="1">
											<h:outputText value="#{car.comentarios}" />
										</h:panelGrid>
									</h:panelGroup>
								</f:facet>
							</t:popup>
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:outputText value="#{txt.svn}" />
							</f:facet>
							<!-- si  swDiferenciaEntreVersiones = true , es porque es un archivo binario
							y no c puede comparar-->
							<h:selectBooleanCheckbox
								rendered="#{car.codigoParticipante != -1}" disabled="true"
								value="#{car.swSVN}" />
							<h:outputText rendered="#{car.codigoParticipante == -1}"
								styleClass="form" value="#{txt.flow_commentPrincipal}" />
						</h:column>

						<h:column>
							<f:facet name="header">
								<h:outputText value="#{txt.upload}" />
							</f:facet>
							<h:panelGrid columns="2">
								<!-- si  swDiferenciaEntreVersiones = true , es porque es un archivo binario
							y no c puede comparar-->
								<h:selectBooleanCheckbox
									rendered="#{car.codigoParticipante != -1}" disabled="true"
									value="#{car.swSVNUpload}" />

								<%
									/*<!-- ruta donde se a subido el archivo -->*/
								%>
								<h:outputText
									rendered="#{car.codigoParticipante != -1
	                        && !empty car.urlsubversionUpload}"
									styleClass="form" value="#{car.urlsubversionUpload}" />
							</h:panelGrid>
							<h:outputText rendered="#{car.codigoParticipante == -1}"
								styleClass="form" value="#{txt.flow_commentPrincipal}" />

						</h:column>


					</t:dataTable>

					<h:panelGrid columns="1" styleClass="scrollerTable2"
						columnClasses="standardTable_ColumnCentered">
						<t:dataScroller id="scroll_1upload" for="dataupload"
							fastStep="#{Utilidades.verNumeroDeRegistrosSVN}"
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
						<t:dataScroller id="scroll_2upload" for="dataupload"
							rowsCountVar="rowsCount"
							displayedRowsCountVar="displayedRowsCountVar"
							firstRowIndexVar="firstRowIndex" lastRowIndexVar="lastRowIndex"
							pageCountVar="pageCount" immediate="true"
							pageIndexVar="pageIndex">
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
					<h:panelGrid columns="1">

						<h:outputLabel styleClass="form" value="#{txt.comentario}" />
						<h:inputTextarea cols="100" styleClass="form" rows="10"
							value="#{DocumentoSvn.subVersionUsuario.comentario}"></h:inputTextarea>
					</h:panelGrid>
				</h:panelGrid>
			</h:panelGroup>



		</a4j:region>
	</h:form>







</f:view>


</body>

</html>
