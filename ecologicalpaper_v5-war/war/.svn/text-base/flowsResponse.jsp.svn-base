<%@ page import="java.math.BigDecimal,java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

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
<script type="text/javascript" src="./validacione.js"></script>

<script language="javascript">
var formId; // reference to the main form
var winId;	// reference to the popup window
// This function calls the popup window.
//
function showFlowUser(action,title) {
    features="height=330,width=450,status=yes,resizable = yes,toolbar=no,menubar=no,location=no,scrollbars=yes";			
    winId=window.open(action,title,features); // open an empty window
}
function showPlaceList(action, form, target) {
    features="height=800,width=800,status=yes,toolbar=no,menubar=no,location=yes,scrollbars=yes";			
    winId=window.open('ClienteDocumentoGenerar','list',features); // open an empty window

} 
</script>
<body>

<f:view>
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />





	<h:form>

		<t:jscookMenu layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>

	<h:form id="viewForm" enctype="multipart/form-data">

		<t:messages id="messageList" styleClass="form" />


		<h:panelGrid columns="1" border="0">
			<t:saveState id="ss1" value="#{tabbedPaneBean}" />


			<h:outputText styleClass="form"
				value="#{FlowsResponse.nomCompletoFlowDoc}" />

			<t:panelTabbedPane rendered="#{FlowsResponse.verTabs}"
				bgcolor="#CCFFFF" serverSideTabSwitch="true">

				<f:subview id="tab1">
					<t:panelTab id="tab1" label="#{txt.flows_trab_doc}"
						onclick="#{tabbedPaneBean.inicializarSessiones}"
						rendered="#{tabbedPaneBean.tab1Visible}">
						<jsp:include page="flowsResponseTab1.jsp" />
					</t:panelTab>
				</f:subview>
			</t:panelTabbedPane>
		</h:panelGrid>

		<h:panelGrid rendered="#{!FlowsResponse.verTabs}" columns="2"
			border="0">
			<h:panelGroup>
				<h:panelGrid columns="2" border="0">

				

					<h:outputText styleClass="form" value="#{txt.flow_Condicional} " />
					<h:selectBooleanCheckbox value="#{FlowsResponse.flow.condicional}"
						disabled="true" />
					<h:outputText styleClass="form" value="#{txt.flow_Secuencial}" />
					<h:selectBooleanCheckbox value="#{FlowsResponse.flow.secuencial}"
						disabled="true" />

					<h:outputText styleClass="form" value="#{txt.flow_envio_mail}" />
					<h:selectBooleanCheckbox
						value="#{FlowsResponse.flow.notificacionMail}" disabled="true" />
					<h:outputText styleClass="form"
						value="#{listarControlFlowByTime.fechaActualServidor}" />
					<f:verbatim></f:verbatim>
					<h:outputText styleClass="form"
						value="#{listarControlFlowByTime.diaDeLaSemanaServidor}" />
					<f:verbatim></f:verbatim>
					<h:outputText styleClass="form"
						value="#{listarControlFlowByTime.horaServidior}" />
					<f:verbatim></f:verbatim>
					<h:outputText styleClass="form" value="#{txt.flows_trab_doc}" />
					<h:commandButton styleClass="boton" id="findflow"
						image="#{conftxt.img_grafico}" immediate="true"
						onmousedown="showFlowUser('UsuarioFlowStadisticasGraficar.jsf?codigo=#{FlowsResponse.user_logueado.id}','_blank')"
						onclick="return false">
					</h:commandButton>






				</h:panelGrid>
			</h:panelGroup>
			<f:verbatim></f:verbatim>


			<%
				/*flowWithParticipantes ES COLLECION DE LA CLASE DEL FLOW*/
			%>
			<t:dataTable id="data" styleClass="standardTable"
				headerClass="standardTable_Header"
				footerClass="standardTable_Header"
				rowClasses="standardTable_Row1,standardTable_Row2"
				columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
				var="flowWithParticipantes"
				value="#{FlowsResponse.flowWithParticipantes}"
				preserveDataModel="true" varDetailToggler="detailToggler">
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.flow_Detalles}" />
					</f:facet>

					<h:commandLink rendered="#{detailToggler.currentDetailExpanded}"
						action="#{detailToggler.toggleDetail}">
						<h:outputText styleClass="node" value="#{txt.flow_Detalles}" />
					</h:commandLink>
					<h:commandLink rendered="#{!detailToggler.currentDetailExpanded}"
						action="#{detailToggler.toggleDetail}">
						<h:outputText styleClass="node" value="#{txt.flow_Detalles}" />
					</h:commandLink>
				</h:column>


				<h:column rendered="#{FlowsResponse.flow.secuencial}">
					<f:facet name="header">
						<h:outputText styleClass="grees"
							value="#{txt.flow_Secuencial} #{txt.flow_Firma} #{txt.flow_firmar}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{FlowsResponse.usuarioFirmaSecuencial.nombre} 
                          #{FlowsResponse.usuarioFirmaSecuencial.apellido} #{FlowsResponse.usuarioFirmaSecuencial.cargo.nombre}" />
				</h:column>

				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{txt.cancelar}"
							url="#{conftxt.img_cancelflow}" />

					</f:facet>
					<h:panelGroup rendered="#{FlowsResponse.swIsDuenio}">
						<h:commandLink action="#{FlowsResponse.canceladoByDuenio}"
							onclick="if (!confirm('¿#{txt.cancelar}?')) return false"
							actionListener="#{FlowsResponse.cancelaCompletoByDuenioFlow}">
							<h:graphicImage title="#{txt.cancelar}"
								url="#{conftxt.img_cancelflow}" />

							<f:param id="editId2" name="idvinculo1112"
								value="#{FlowsResponse.flow.codigo}" />
						</h:commandLink>
					</h:panelGroup>

				</h:column>

				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees"
							value="#{txt.autor_flujo} #{txt.flowsub}" />
					</f:facet>
					<h:outputText styleClass="node"
						value="#{FlowsResponse.duenioFlow.apellido} #{FlowsResponse.duenioFlow.nombre} [#{FlowsResponse.duenioFlow.cargo.nombre}]" />
				</h:column>
				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_estadotab}" />
					</f:facet>
					<%
						/*ES EL DUE;O DEL FLOW*/
					%>
					<h:outputText styleClass="node"
						value="#{flowWithParticipantes.firmaPrincipalStr}" />
				</h:column>



				<h:column>
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{pdf}"
							url="#{conftxt.img_pdf}" />
					</f:facet>
					<h:commandLink styleClass="node" id="Edit" type="submit"
						action="#{FlowsResponse.reporte}">
						<h:graphicImage title="#{pdf}" url="#{conftxt.img_pdf}" />
					</h:commandLink>
				</h:column>





				<h:column>
					<f:facet name="header">
						<h:outputText styleClass="grees" value="#{txt.doc_creado}" />
					</f:facet>
					<%
						/* me trae la fecha en el campo comentario, cuando pesiono comentario arriba, estevse refresca con el verdadero coimentarioo  */
					%>
					<h:outputText styleClass="node"
						value="#{flowWithParticipantes.comentarios}">
					</h:outputText>
				</h:column>


				<h:column rendered="#{FlowsResponse.swPuedeBorrarHistTree}">
					<f:facet name="header">
						<h:graphicImage styleClass="grees" title="#{txt.btn_erase}"
							url="#{conftxt.img_erase}" />

					</f:facet>
					<h:commandLink id="id_erasea"
						action="#{FlowsResponse.erasedDetalleTreeHistoricoPersonalInFlow}"
						actionListener="#{FlowsResponse.erasedDetalleTreeHistoricoPersonalInFlow}">
						<h:graphicImage id="idimage_erase" title="#{txt.btn_erase}"
							url="#{conftxt.img_erase}" />

						<f:param id="editId2" name="iderase"
							value="#{FlowsResponse.flow.codigo}" />
					</h:commandLink>

				</h:column>
				<%
					/* LOS DETALLES
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	 ___________________________________________________________________________________________
									 *___________________________________________________________________________________________
									 *___________________________________________________________________________________________
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	
									 */
				%>
				<f:facet name="detailStamp">
					<t:dataTable id="cities" styleClass="standardTable_Column"
						var="detalle" value="#{flowWithParticipantes.participantesFlows}">
						<h:column>
							<h:panelGrid columns="1">
								<h:commandLink rendered="#{detalle.toModFlow}"
									action="#{FlowsResponse.action_CambiarUsuario}"
									actionListener="#{FlowsResponse.selectCambiarParticipante}">
									<h:outputText styleClass="node" value="#{txt.toModFlow}"
										style="font-size: 11px" />
									<f:param id="editIdCambiar" name="id2cambio"
										value="#{detalle.codigo}" />
								</h:commandLink>
								<h:outputText styleClass="node" rendered="#{detalle.swRole}"
									value="#{detalle.participante.nombre} 
                                   #{detalle.participante.apellido} [#{detalle.participante.cargo.nombre}] #{detalle.role.nombre}->(#{detalle.flow.nombredelflujo})"
									style="font-size: 11px" />

								<h:outputText styleClass="node" rendered="#{!detalle.swRole}"
									value="#{detalle.participante.nombre} #{detalle.participante.apellido}[#{detalle.participante.cargo.nombre}]
									->(#{detalle.flow.nombredelflujo})"
									style="font-size: 11px" />
							</h:panelGrid>
						</h:column>






						<h:column>
							<h:commandLink rendered="#{detalle.auxdeFirma}" id="EditFirma"
								action="#{FlowsResponse.action_verTabs}"
								actionListener="#{FlowsResponse.selectFirmar}">
								<h:outputText styleClass="node" value="#{txt.flow_firmar}"
									style="font-size: 11px" />
								<f:param id="editIdFirmar" name="id2" value="#{detalle.codigo}" />
							</h:commandLink>

							<h:panelGrid columns="2">
								<h:outputText styleClass="node"
									rendered="#{!detalle.auxdeFirma}" value="#{detalle.firmaStr}"
									style="font-size: 11px" />

								<%
									/*<!--	   PUEDE EDITAR DOCUMENTO --> */
								%>

								<h:commandButton styleClass="boton" id="EditApplet"
									rendered="#{detalle.auxdeFirma 
							  && !FlowsResponse.flow.lectores}"
									image="#{conftxt.img_applet}" immediate="true"
									onmousedown="showFlowUser('editarDocumento.jsf','')"
									onclick="return false">
								</h:commandButton>

							</h:panelGrid>
						</h:column>




						<%
							/*<!--	   PUEDE REALIZAR  FLUJO EL DOCUMENTO --> */
						%>


						<h:column>
							<%
								/* <!-- swCanRealizarFlow= sw puede realizar flujo -->*/
							%>
							<h:commandLink rendered="#{detalle.swCanRealizarFlow}"
								id="EditSubFlow" action="#{FlowsResponse.flows_action}"
								actionListener="#{FlowsResponse.versionId}">
								<h:graphicImage id="imageflow" title="#{txt.flow_someter}"
									url="#{conftxt.img_flow}" />
								<f:param id="editIdSubFlow" name="idsubFlow2"
									value="#{detalle.codigo}" />
							</h:commandLink>
						</h:column>

						<h:column>
							<h:commandLink id="Editflowplantilla"
								rendered="#{detalle.swCanRealizarFlow && 
								detalle.swPuedeRealizarFlowPlantilla}"
								action="#{FlowsResponse.listaPlantillaFlowParalelo}"
								actionListener="#{FlowsResponse.versionIdPlantilla}">
								<h:graphicImage id="imageflowplantilla"
									title="#{txt.flow} #{txt.plantilla} "
									url="#{conftxt.img_flowplantilla}" />
								<f:param id="editIdSubFlowPlantilla" name="idsubFlowPlantilla"
									value="#{detalle.codigo}" />
							</h:commandLink>
						</h:column>









						<h:column>
							<h:commandLink
								rendered="#{detalle.swFlow_ParticipantesAttachment}"
								action="#{FlowsResponse.viewDocumentoAttachment}"
								actionListener="#{FlowsResponse.selectCommunAttachment}">
								<h:graphicImage style="form" id="imageAttach"
									title="#{txt.doc_verdocumento}" url="#{conftxt.img_attachment}" />
								<f:param id="editIdAttach" name="idAttach"
									value="#{detalle.codigo}" />
							</h:commandLink>
						</h:column>




						<h:column>
							<h:outputText styleClass="node" value="#{detalle.comentario}"
								style="font-size: 11px">
							</h:outputText>
						</h:column>


						<h:column>
							<h:panelGroup
								rendered="#{detalle.swCanRealizarFlow 
								&& detalle.swAgregarDocumentosvnUpload}">
								<h:panelGrid columns="1">
									<h:outputLabel styleClass="node"
										value="#{txt.cargarautomatico} #{txt.svn}"></h:outputLabel>
									<h:panelGrid columns="2">
										<h:selectOneRadio title="#{txt.cargarautomatico} #{txt.svn}"
											id="subscriptions" styleClass="form"
											value="#{FlowsResponse.automaticoCargaSvn}">
											<f:selectItem itemValue="1" itemLabel="#{txt.si}" />
											<%
												/*<!--<f:selectItem itemValue="0" itemLabel="#{txt.no}" />-->*/
											%>
										</h:selectOneRadio>
										<h:commandLink id="EditSvn"
											action="#{FlowsResponse.agregardocumentosvnupload}"
											actionListener="#{FlowsResponse.versionIdPlantilla}">
											<h:graphicImage id="imageSvn" width="16" height="16"
												title="#{txt.subversion} " url="#{conftxt.img_svn}" />
											<f:param id="editIdSvn" name="idsubSvn"
												value="#{detalle.codigo}" />
										</h:commandLink>
									</h:panelGrid>
								</h:panelGrid>
							</h:panelGroup>
						</h:column>




						<h:column>
							<h:panelGrid columns="1">
								<h:panelGroup>
									<h:outputText styleClass="node"
										rendered="#{detalle.horasAsignadas!=0 && not empty detalle.horasAsignadas}"
										value="#{txt.asignadas_horas} #{detalle.horasAsignadas}"
										style="font-size: 11px" />
									<h:outputText styleClass="node"
										rendered="#{detalle.minutosAsignados!=0 && not empty detalle.minutosAsignados}"
										value=", #{txt.asignadas_minutos} #{detalle.minutosAsignados}"
										style="font-size: 11px" />
								</h:panelGroup>
							</h:panelGrid>
						</h:column>



						<h:column>
							<h:panelGrid columns="1">
								<h:panelGroup>
									<h:outputText styleClass="node"
										rendered="#{detalle.horasAcumuladas!=0 && not empty detalle.horasAcumuladas}"
										value="#{txt.horas_acumuladas}  #{detalle.horasAcumuladas}"
										style="font-size: 11px" />
									<h:outputText styleClass="node"
										rendered="#{detalle.minutosAcumulados!=0 && not empty detalle.minutosAcumulados}"
										value=", #{txt.minutos_acumulados} #{detalle.minutosAcumulados}"
										style="font-size: 11px" />
								</h:panelGroup>
							</h:panelGrid>
						</h:column>

						<h:column>
							<h:panelGrid columns="1">
								<h:panelGroup>
									<h:outputText styleClass="node"
										rendered="#{detalle.horasRestantes!=0 && not empty detalle.horasRestantes}"
										value="#{txt.horas_restantes}   #{detalle.horasRestantes}"
										style="font-size: 11px" />
									<h:outputText styleClass="node"
										rendered="#{detalle.minutosRestantes!=0  && not empty detalle.minutosRestantes}"
										value=", #{txt.minutos_restantes} #{detalle.minutosRestantes}"
										style="font-size: 11px" />
								</h:panelGroup>
							</h:panelGrid>
						</h:column>
					</t:dataTable>
				</f:facet>
			</t:dataTable>
			<f:verbatim></f:verbatim>


			<h:panelGrid rendered="#{FlowsResponse.swViewComentario}" columns="1"
				cellspacing="0" border="0" width="75%">
				<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{FlowsResponse.cualquierComentario}"></h:inputTextarea>
				<%
					/*  <t:inputHtml value="#{FlowsResponse.cualquierComentario}"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               style="width: 700px;"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               allowEditSource="false"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               showPropertiesToolBox="false"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               showLinksToolBox="false"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               showImagesToolBox="false"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               showTablesToolBox="false"
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	               showDebugToolBox="false"/>
									 */
				%>
				<h:panelGroup>


					<h:commandButton styleClass="boton"
						action="#{FlowsResponse.viewComentarioFlow}"
						actionListener="#{FlowsResponse.selectCommun}"
						value="#{txt.flow_cerrarComentario}" action="" />
				</h:panelGroup>
			</h:panelGrid>

		</h:panelGrid>

	</h:form>

	<h:form id="form2">
		<h:panelGrid columns="3" border="0">

			<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
				action="#{FlowsResponse.salir}" />





			<%
				/* <!--  esta en session el documento por doc_detalle o objeto -->*/
			%>
			<h:commandLink
				onmousedown="showFlowUser('mostrarDocProtegido.jsf','')"
				immediate="true" onclick="return false">
				<h:graphicImage style="form" title="#{txt.doc_verdocumento}"
					url="#{Documento.icono}" />
			</h:commandLink>

			<h:commandLink styleClass="form" id="Edit" type="submit"
				action="#{tree.desdeWorkFlowDocumento}"
				onclick="this.style.display='none';">

				<h:graphicImage title="#{txt.mas}" url="#{conftxt.img_mas}" />
			</h:commandLink>





			<!--<%/*
						

						 <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
						 codebase="http://java.sun.com/update/1.5.0/jinstall-1_5-windows-i586.cab#Version=1,5,0,0"
						 height="60" width="80">
						 <param name="code" value="Applet1.class" />
						 <param name="type" value="application/x-java-applet;version=1.5" />
						 <PARAM name="codebase" value=".">

						 <comment> <embed code="Applet1.class" codebase="."
						 height="60"
						 pluginspage="http://java.sun.com/products/plugin/index.html#download"
						 scriptable="false" type="application/x-java-applet;version=1.5"
						 width="80"></embed> <noembed>alt="Your browser
						 understands the &lt;APPLET&gt; tag but isn't running the applet, for
						 some reason." Your browser is completely ignoring the &lt;APPLET&gt;
						 tag!</noembed> </comment> 

						

						 <h:commandLink styleClass="form" id="Edit" type="submit"
						 action="#{tree.desdeWorkFlowDocumento}"
						 onclick="this.style.display='none';"
						 >

						 <h:graphicImage 
						 title="#{txt.mas}"
						 url="#{conftxt.img_mas}" /> 
						 </h:commandLink>

						 <h:panelGroup id="body">
						 <h:panelGrid columns="2">
						 <!-- Expand/Collapse Handled By Server -->
						 <t:tree2 id="serverTree" value="#{FlowsResponse.treeData}"
						 var="node" varNodeToggler="t" clientSideToggle="false">
						 <f:facet name="identificaRaizTree">
						 <h:panelGroup>
						 <t:graphicImage value="/images/yellow-folder-open.png"
						 rendered="#{t.nodeExpanded}" border="0" />
						 <t:graphicImage value="/images/yellow-folder-closed.png"
						 rendered="#{!t.nodeExpanded}" border="0" />
						 <h:outputText value="#{node.description}"
						 styleClass="nodeFolder" />
						 </h:panelGroup>
						 </f:facet>
						 <f:facet name="identifica1Tree">
						 <h:panelGroup>
						 <h:commandLink immediate="true"
						 styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
						 <t:graphicImage value="/images/yellow-folder-open.png"
						 rendered="#{t.nodeExpanded}" border="0" />
						 <t:graphicImage value="/images/yellow-folder-closed.png"
						 rendered="#{!t.nodeExpanded}" border="0" />
						 <h:outputText value="#{node.description}"
						 styleClass="nodeFolder" />
						 <h:outputText value=" (#{node.childCount})"
						 styleClass="childCount" rendered="#{!empty node.children}" />
						 <f:param name="docNum" value="#{node.identifier}" />
						 </h:commandLink>
						 </h:panelGroup>
						 </f:facet>
						
						 <f:facet name="identifica2Tree">
						 <h:panelGroup>
						 <h:commandLink immediate="true" 
						 styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
						 <t:graphicImage value="/images/spacer.gif" rendered="#{t.nodeExpanded}" border="0"/>
						 <t:graphicImage value="/images/spacer.gif" rendered="#{!t.nodeExpanded}" border="0"/>
						 <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
						 <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
						 <f:param name="docNum" value="#{node.identifier}"/>
						 </h:commandLink>
						 </h:panelGroup>
						 </f:facet>

						 <f:facet name="identifica3Tree">
						 <h:panelGroup>
						 <h:commandLink immediate="true"
						 styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
						 action="#{FlowsResponse.viewDocumentoAttachment}"
						 actionListener="#{FlowsResponse.selectCommunAttachmentByTree}">
						 <t:graphicImage value="#{conftxt.img_attachment}" border="0" />
						 <h:outputText value="#{node.description}" />
						 <f:param name="docNum" value="#{node.identifier}" />
						 </h:commandLink>
						 </h:panelGroup>
						 </f:facet>

						 </t:tree2>

						 </h:panelGrid>


						 </h:panelGroup>
						 */%>-->
		</h:panelGrid>
	</h:form>


</f:view>

</body>

</html>