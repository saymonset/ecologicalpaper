<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<script type="text/javascript" src="./validacione.js"></script>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<html>
<%@include file="/inc/head.inc"%>
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

a.no-decor>img {
	border: none;
}
-->
</style>



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
</head>
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


		<t:jscookMenu id="menu2" layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.navigationItemsDocuments}" />
		</t:jscookMenu>

	</h:form>



	<h:form id="formalistar">
		<h:messages />

		<h:panelGroup id="body">
			<t:dataTable id="data" var="currentMaestro"
				styleClass="standardTable" headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				value="#{Documento.clienteDocumentoMaestros}"
				preserveDataModel="true" varDetailToggler="detailToggler">
				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{txt.doc_detalle}"
							url="#{currentMaestro.primerClienteDocumentoDetalle.icono}" />
					</f:facet>

					<h:commandLink
						onmousedown="showFlowUser('mostrarDocProtegido.jsf?detalle_id=#{currentMaestro.primerClienteDocumentoDetalle.codigo}','')"
						immediate="true" onclick="return false">
						<h:graphicImage title="#{txt.doc_detalle}"
							url="#{currentMaestro.primerClienteDocumentoDetalle.icono}" />
					</h:commandLink>
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{txt.doc_versiones}"
							url="#{conftxt.img_versiones}" />
					</f:facet>
					<h:commandLink rendered="#{detailToggler.currentDetailExpanded}"
						action="#{detailToggler.toggleDetail}">
						<h:graphicImage title="#{txt.doc_versiones}"
							url="#{conftxt.img_versiones}" />

					</h:commandLink>
					<h:commandLink rendered="#{!detailToggler.currentDetailExpanded}"
						action="#{detailToggler.toggleDetail}">
						<h:graphicImage title="#{txt.doc_versiones}"
							url="#{conftxt.img_versiones}" />
					</h:commandLink>
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.Operaciones_nombre}" />
					</f:facet>

					<h:commandLink rendered="#{detailToggler.currentDetailExpanded}"
						action="#{detailToggler.toggleDetail}">
						<h:outputText styleClass="node" value="#{currentMaestro.nombre}" />
					</h:commandLink>
					<h:commandLink rendered="#{!detailToggler.currentDetailExpanded}"
						action="#{detailToggler.toggleDetail}">

						<h:outputText styleClass="node" value="#{currentMaestro.nombre}" />
					</h:commandLink>

				</h:column>

				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" id="zzimgprincipal"
							rendered="#{currentMaestro.swLocked}"
							title="#{txt.doc_bloqueado}" url="#{conftxt.img_locked}" />
					</f:facet>
					<h:outputText rendered="#{currentMaestro.swLocked}"
						styleClass="node"
						value="#{currentMaestro.primerClienteDocumentoDetalle.modificadoPor} " />
				</h:column>


				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_estadotab}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.primerClienteDocumentoDetalle.doc_estado.nombre}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_version}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.primerClienteDocumentoDetalle.mayorVer}.#{currentMaestro.primerClienteDocumentoDetalle.minorVer}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.areadocumento}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{
					currentMaestro.primerClienteDocumentoDetalle.areaDocumentos!=null?
					currentMaestro.primerClienteDocumentoDetalle.areaDocumentos.nombre:''
					}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_tipotab}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.doc_tipo.nombre}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_consecutivotab}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.consecutivo}" />
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{txt.doc_publico}"
							url="#{conftxt.img_publico}" />

					</f:facet>
					<h:panelGrid columns="2">
						<h:graphicImage rendered="#{currentMaestro.publico}"
							title="#{txt.doc_publico}" url="#{conftxt.img_publico}" />

						<h:graphicImage
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swFechaPublicoExpiro}"
							title="#{txt.documentoexpirado}"
							url="#{conftxt.img_publicoexpiro}" />

					</h:panelGrid>
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_creado}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.fecha_mostrar}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_cambio}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.datecambio} " />
				</h:column>







				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.pesomgb}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{currentMaestro.primerClienteDocumentoDetalle.size_doc}" />
				</h:column>



				<%
					/*   <!--Me muestra qu el documento esta sin proteger o no bloqueado -->*/
				%>



				<%
					/*___DETALLES______DE LOS DOCUMENTOS_________________________________________________________
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									 DETALLES DEL DOCUMENTO______________________________________________________________*/
				%>
				<f:facet name="detailStamp">
					<t:dataTable id="cities" styleClass="standardTable"
						headerClass="standardTable_Header"
						footerClass="standardTable_Header"
						rowClasses="standardTable_Row1,standardTable_Row2"
						columnClasses="standardTable_Column,standardTable_ColumnCentered,
						standardTable_Column"
						binding="#{Documento.myDataTable}" var="detalle"
						value="#{currentMaestro.doc_detallesCliente}">







						<h:column>
							<f:facet name="header">

							</f:facet>
							<h:commandLink id="detalle"
								onmousedown="showFlowUser('mostrarDocProtegido.jsf?detalle_id=#{detalle.codigo}','')"
								immediate="true" onclick="return false">
								<h:graphicImage
									title="#{txt.doc_detalle}  #{currentMaestro.codigo}"
									url="#{detalle.icono}" />
							</h:commandLink>
						</h:column>

						<h:column>
							<f:facet name="header">

							</f:facet>

							<h:commandLink rendered="#{Documento.seguridadTree.toDownload}"
								onmousedown="showFlowUser('clienteDocumentoDownloadFile.jsf?detalle_id=#{detalle.codigo}','')"
								immediate="true" onclick="return false">
								<h:graphicImage title="#{txt.doc_detalle}"
									url="#{conftxt.img_attachment}" />
							</h:commandLink>


						</h:column>
						<h:column>
							<f:facet name="header">

							</f:facet>

							<h:commandLink
								rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHabilitadoImprimir}"
								action="#{Documento.hacerSendSolicitudImpresion}"
								actionListener="#{Documento.listarSolicitudImpPartsToSendToImprimir}"
								onclick="this.style.display='none';" immediate="true">
								<h:graphicImage title="#{txt.solicitudImpresion}"
									url="#{conftxt.img_impresora}" />
								<h:graphicImage
									rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHabilitadoImprimir}"
									title="#{txt.solicitudImpresion}" url="#{conftxt.img_cheked}" />
								<f:param id="editId2Imprimirversion"
									name="idDetalleImpresionversion" value="#{detalle.codigo}" />

							</h:commandLink>
						</h:column>



						<h:column>
							<f:facet name="header">
								<h:outputText styleClass="grees" value="#{txt.doc_estadotab}" />
							</f:facet>
							<h:outputText styleClass="node"
								value="#{detalle.doc_estado.nombre}" />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText styleClass="grees" value="#{txt.doc_version}" />
							</f:facet>
							<h:outputText styleClass="node"
								value="#{detalle.mayorVer}.#{detalle.minorVer}" />
						</h:column>


						<h:column>
							<f:facet name="header">
								<h:outputText styleClass="grees" value="#{txt.doc_dueniotab}" />
							</f:facet>
							<h:column>
								<h:outputText styleClass="node" value="#{detalle.duenio} " />
							</h:column>
						</h:column>






						<h:column>
							<f:facet name="header">
								<h:outputText styleClass="grees"
									value="#{txt.doc_modificadopor}" />
							</f:facet>
							<h:outputText styleClass="node" value="#{detalle.modificadoPor} " />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText styleClass="grees" value="#{txt.doc_cambio}" />
							</f:facet>
							<h:outputText styleClass="node" value="#{detalle.datecambioStr} " />
						</h:column>
						<h:column>
							<f:facet name="header">
								<h:outputText styleClass="grees" value="#{txt.razondecambio}" />
							</f:facet>
							<h:outputText styleClass="node"
								rendered="#{not empty detalle.descripcion}"
								value="#{detalle.descripcion} " />
						</h:column>
					</t:dataTable>
				</f:facet>

				<f:verbatim>
					<br>
				</f:verbatim>
			</t:dataTable>
		</h:panelGroup>

		<t:dataTable id="dataleyenda" styleClass="standardTable"
			headerClass="standardTable_Header" footerClass="standardTable_Header"
			rowClasses="standardTable_Row1,standardTable_Row2"
			columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
			var="currentMaestro" value="#{Documento.panelControl}"
			preserveDataModel="true" varDetailToggler="detailToggler">

			<h:column>
				<f:facet name="header">
					<h:panelGrid columns="2">
						<h:graphicImage id="imagetxtaneldecontrol"
							title="#{txt.paneldecontrol}" url="#{conftxt.img_control_panel}" />
						<h:outputLabel styleClass="grees" value="#{txt.paneldecontrol}" />
					</h:panelGrid>


				</f:facet>
				<h:panelGrid columns="2">

					<%
						/*PUEDE REALIZAR  FLUJO EL DOCUMENTO*/
					%>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swPuedeRealizarFlujo}"
						action="#{Documento.flows_action}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageflow" title="#{txt.flow_someter}"
							url="#{conftxt.img_flow}" />
						<f:param id="editId2_flow" name="idflow"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swPuedeRealizarFlujo}"
						action="#{Documento.flows_action}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swPuedeRealizarFlujo}"
							styleClass="grees" value="#{txt.flow_someter}" />
					</h:commandLink>

					<%
						/*  FLUJO ACTIVO YA EXTA EN FLUJO DOCUMENTO*/
					%>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swFlujoActivo}"
						action="#{Documento.flowsView_action}"
						actionListener="#{Documento.viewFlow}"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageflowActivo"
							title="#{txt.flow_activo} #{currentMaestro.primerClienteDocumentoDetalle.usuariosInFlowStr}"
							url="#{conftxt.img_flowactivo}" />
						<f:param id="editId22" name="idflowActivo"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swFlujoActivo}"
						action="#{Documento.flowsView_action}"
						actionListener="#{Documento.viewFlow}"
						onclick="this.style.display='none';">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swFlujoActivo}"
							styleClass="grees" value="#{txt.flow_activo}" />
					</h:commandLink>
					<%
						/*  PLANTILLA  DOCUMENTO FLOW */
					%>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swPuedeRealizarFlujo
						 && Documento.swHayPlantillasFlujosparalelos}"
						action="#{Documento.listaPlantillaFlowParalelo}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageflowplantilla"
							title="#{txt.flow} #{txt.plantilla}"
							url="#{conftxt.img_flowplantilla}" />
						<f:param id="editId2_flowplantilla" name="idflowplantilla"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swPuedeRealizarFlujo
						 && Documento.swHayPlantillasFlujosparalelos}"
						action="#{Documento.listaPlantillaFlowParalelo}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swPuedeRealizarFlujo
						 && Documento.swHayPlantillasFlujosparalelos}"
							styleClass="grees" value="#{txt.flow} #{txt.plantilla}" />
					</h:commandLink>


					<%
						/*  NUEVA VERSiON*/
					%>

					<h:commandLink action="#{Documento.crear_nuevaversion}"
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swCheckOut}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageCheckOut"
							title="#{txt.doc_version_nueva}" url="#{conftxt.img_checkout}" />
						<f:param id="editId2_checkout" name="id2"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink action="#{Documento.crear_nuevaversion}"
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swCheckOut}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swCheckOut}"
							styleClass="grees" value="#{txt.doc_version_nueva}" />
					</h:commandLink>

					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swLockedwithkey}"
						action="#{Documento.viewListarDocumento}"
						onclick="if (!confirm('¿#{txt.doc_desbloquear}?')) return false"
						actionListener="#{Documento.liberarBloqueoId}">
						<h:graphicImage id="imageDesbloqueo"
							title="#{txt.doc_desbloquear}" url="#{conftxt.img_lockedwithkey}" />
						<f:param id="editId2_Desbloqueo" name="idDetalleDesbloqueo"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swLockedwithkey}"
						action="#{Documento.viewListarDocumento}"
						onclick="if (!confirm('¿#{txt.doc_desbloquear}?')) return false"
						actionListener="#{Documento.liberarBloqueoId}">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swLockedwithkey}"
							styleClass="grees" value="#{txt.doc_desbloquear}" />
					</h:commandLink>




					<%
						/*  EDITAR DOCUMENTOS y MODIFICARLO COMO TAL WORD EXCEL POWERPOINT ETC*/
					%>
					<h:commandLink
						rendered="#{(currentMaestro.primerClienteDocumentoDetalle.swLockedwithkey
						 or currentMaestro.primerClienteDocumentoDetalle.swFirstBorrador)
						  }"
						action="#{Documento.viewDocumentoEditar}"
						actionListener="#{Documento.editBloqueoId}" immediate="true"
						onmousedown="showFlowUser('editarDocumento.jsf?detalle_id=#{currentMaestro.primerClienteDocumentoDetalle.codigo}','')"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageDesbloqueoaaplet"
							title="#{txt.doc_editar}" url="#{conftxt.img_applet}" />

						<f:param id="editId2_applet" name="idDetalleDesbloqueoapplet"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{(currentMaestro.primerClienteDocumentoDetalle.swLockedwithkey
						 or currentMaestro.primerClienteDocumentoDetalle.swFirstBorrador) }"
						action="#{Documento.viewDocumentoEditar}"
						actionListener="#{Documento.editBloqueoId}" immediate="true"
						onmousedown="showFlowUser('editarDocumento.jsf?detalle_id=#{currentMaestro.primerClienteDocumentoDetalle.codigo}','')"
						onclick="this.style.display='none';">
						<h:outputLabel styleClass="grees" value="#{txt.doc_abrir}" />

					</h:commandLink>

					<%
						/* VER DCUMENTO SOLO LECTURA*/
					%>
					<h:commandLink action="#{Documento.viewOnly}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage title="#{txt.doc_detalle}"
							url="#{conftxt.img_detalledoc}" />
						<f:param id="editIdx2_2" name="idDetall"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink action="#{Documento.viewOnly}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel styleClass="grees"
							value="#{txt.doc_detalle} 
                                  " />
					</h:commandLink>

					<%
						/* EDITAR META CARACTERES DOCUMENTOS */
					%>
					<h:commandLink rendered="#{Documento.swMod}"
						action="#{Documento.editamosMetaCaracteresdoc}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage title="#{txt.edit} #{txt.doc_detalle}"
							url="#{conftxt.img_editmetacaracteresdoc}" />
						<f:param id="editIdx2_2_2" name="idDetall2"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink rendered="#{Documento.swMod}"
						action="#{Documento.editamosMetaCaracteresdoc}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel styleClass="grees"
							value="#{txt.edit} #{txt.doc_detalle} 
                                  " />
					</h:commandLink>






				</h:panelGrid>
			</h:column>



			<h:column>
				<f:facet name="header">
				</f:facet>
				<h:panelGrid columns="2">
					<%
						/*  DOCUMENTO PUBLICO */
					%>


					<h:commandLink rendered="#{Documento.seguridadTree.toDoPublico}"
						action="#{Documento.docPublicar}"
						actionListener="#{Documento.listarVersionIdPublicar}"
						immediate="true" onclick="this.style.display='none';">
						<h:graphicImage title="#{txt.doc_publico}"
							url="#{conftxt.img_publico}" />

						<f:param id="editid2publico" name="id2publicl1"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink rendered="#{Documento.seguridadTree.toDoPublico}"
						action="#{Documento.docPublicar}"
						actionListener="#{Documento.listarVersionIdPublicar}"
						immediate="true" onclick="this.style.display='none';">

						<h:outputLabel styleClass="grees" value="#{txt.doc_publico}" />
					</h:commandLink>

					<%
						/*  VINCULAR DOCUMENTO*/
					%>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swVincularDocumento}"
						action="#{Documento.docVinculados}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage id="idvinculoimage" title="#{txt.doc_vinculado}"
							url="#{conftxt.img_docvinculado}" />
						<f:param id="editId2" name="idvinculo1112"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swVincularDocumento}"
						action="#{Documento.docVinculados}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swVincularDocumento}"
							styleClass="grees" value="#{txt.doc_vinculado}" />
					</h:commandLink>



	<%
						/*  HACER REGISTROS DE UN  DOCUMENTO*/
					%>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swRegistro}"
						action="#{Documento.generarRegistro}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swRegistro}"
							id="imageCopiar" title="#{txt.registro}"
							url="#{conftxt.img_copy}" />
						<f:param id="editId2_Copiar" name="id2Copiar"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swRegistro}"
						action="#{Documento.generarRegistro}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swRegistro}"
							styleClass="grees" value="#{txt.registro}" />
					</h:commandLink>

					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHistFlow}"
						action="#{Documento.viewHistorico}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageHist" title="#{txt.flows_trab_doc}"
							url="#{conftxt.img_historicoflow}" />
						<f:param id="editId2_historicoflow" name="id2Hist"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHistFlow}"
						action="#{Documento.viewHistorico}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel styleClass="grees"
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHistFlow}"
							value="#{txt.doc_hist} #{txt.flows_trab_doc}" />
					</h:commandLink>


					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHistDoc}"
						action="#{Documento.viewHistoricoDocActivo}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:graphicImage id="imageHist2"
							title="#{txt.doc_hist} #{txt.doc_documento}"
							url="#{conftxt.img_historico2}" />
						<f:param id="editId2_HistoricoDoc" name="id2Hist1"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHistDoc}"
						action="#{Documento.viewHistoricoDocActivo}"
						actionListener="#{Documento.versionId}"
						onclick="this.style.display='none';">
						<h:outputLabel styleClass="grees"
							rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHistDoc}"
							value="#{txt.doc_hist} #{txt.doc_documento}" />
					</h:commandLink>

					<%
						/*  SOLICITUD DE IMPRESION */
					%>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swImprimir
						and !currentMaestro.primerClienteDocumentoDetalle.swFlujoActivo}"
						action="#{Documento.hacerSendSolicitudImpresion}"
						actionListener="#{Documento.listarSolicitudImpPartsToSendToImprimir}"
						immediate="true" onclick="this.style.display='none';">


						<h:panelGrid columns="2">
							<h:graphicImage title="#{txt.solicitudImpresion}"
								url="#{conftxt.img_impresora}" />
							<h:graphicImage
								rendered="#{currentMaestro.primerClienteDocumentoDetalle.swHabilitadoImprimir}"
								title="#{txt.solicitudImpresion}" url="#{conftxt.img_cheked}" />

							<h:graphicImage
								rendered="#{currentMaestro.primerClienteDocumentoDetalle.swEstaInFlowToImprimir
								&& !currentMaestro.primerClienteDocumentoDetalle.swHabilitadoImprimir}"
								title="#{txt.solicitudImpresion}" url="#{conftxt.img_inflow}" />
						</h:panelGrid>

						<f:param id="editId2Imprimir" name="idDetalleImpresion"
							value="#{currentMaestro.primerClienteDocumentoDetalle.codigo}" />
					</h:commandLink>
					<h:commandLink
						rendered="#{currentMaestro.primerClienteDocumentoDetalle.swImprimir
						and !currentMaestro.primerClienteDocumentoDetalle.swFlujoActivo}"
						action="#{Documento.hacerSendSolicitudImpresion}"
						actionListener="#{Documento.listarSolicitudImpPartsToSendToImprimir}"
						onclick="this.style.display='none';" immediate="true">
						<h:outputLabel styleClass="grees"
							value="#{txt.solicitudImpresion}" />
					</h:commandLink>

					<!--

					<%/*  VERSIONES DEL DOCUMENTO
								 <h:panelGrid columns="2">
								 <h:graphicImage title="#{txt.doc_versiones}"
								 url="#{conftxt.img_versiones}" />
								 <h:outputLabel styleClass="grees" value="#{txt.doc_versiones}" />
								 </h:panelGrid>
								 <f:verbatim></f:verbatim>

								 <h:panelGrid columns="2">
								 <h:graphicImage
								 rendered="#{!currentMaestro.primerClienteDocumentoDetalle.swUnlocked}"
								 id="zzimagedetalle" title="#{txt.doc_bloqueado}"
								 url="#{conftxt.img_locked}" />
								 <h:outputLabel
								 rendered="#{!currentMaestro.primerClienteDocumentoDetalle.swUnlocked}"
								 styleClass="grees" value="#{txt.doc_bloqueado}" />
								 </h:panelGrid>
								 <f:verbatim></f:verbatim>

								 <h:panelGrid columns="2">
								 <h:graphicImage
								 rendered="#{currentMaestro.primerClienteDocumentoDetalle.swUnlocked}"
								 id="ccimagedetalle" title="#{txt.doc_liberado}"
								 url="#{conftxt.img_unlocked}" />
								 <h:outputLabel
								 rendered="#{currentMaestro.primerClienteDocumentoDetalle.swUnlocked}"
								 styleClass="grees" value="#{txt.doc_liberado}" />
								 </h:panelGrid>
								 <f:verbatim></f:verbatim>


								 <h:panelGrid columns="2">
								 <h:graphicImage
								 rendered="#{currentMaestro.primerClienteDocumentoDetalle.swFechaPublicoExpiro}"
								 title="#{txt.documentoexpirado}"
								 url="#{conftxt.img_publicoexpiro}" />
								 <h:outputLabel
								 rendered="#{currentMaestro.primerClienteDocumentoDetalle.swFechaPublicoExpiro}"
								 styleClass="grees" value="#{txt.documentoexpirado}" />
								 </h:panelGrid>
								 <f:verbatim></f:verbatim>
								 */%> -->
				</h:panelGrid>
			</h:column>
		</t:dataTable>



	</h:form>



</f:view>



</body>

</html>
