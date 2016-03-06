<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>

<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<t:messages id="messageList" styleClass="form" />
<

<h:outputText styleClass="form" value="#{txt.doc_descripciontab}" />
<h:panelGrid rendered="#{FlowsResponse.verTabs}" columns="1"
	cellspacing="0" border="0" width="75%">
	<h:panelGrid columns="1">
		<h:inputTextarea cols="100" styleClass="form" rows="10"
			value="#{FlowsResponse.firmarComentario}"></h:inputTextarea>
		<%
			/*
																	       <t:inputHtml value="#{FlowsResponse.firmarComentario}"
																	                    style="width: 700px;"
																	                    allowEditSource="false"
																	                    showPropertiesToolBox="false"
																	                    showLinksToolBox="false"
																	                    showImagesToolBox="false"
																	                    showTablesToolBox="false"
																	                    showDebugToolBox="false"/> */
		%>
	</h:panelGrid>
</h:panelGrid>


<h:panelGrid columns="2">

	<h:panelGroup rendered="#{!FlowsResponse.swAttachmentSVN}">
		<h:outputText styleClass="form"
			value="#{txt.doc_datosCargaDocumentotabp} " />
		<t:inputFileUpload id="fileupload" accept="image/*"
			value="#{FlowsResponse.upFile}" storage="file"
			styleClass="fileUploadInput" maxlength="20000000" />
	</h:panelGroup>
	<f:verbatim></f:verbatim>

	<h:panelGroup>

		<h:commandLink rendered="#{detalle.swCanRealizarFlow}"
			id="attachmentSvn" action="#{FlowsResponse.agregarDocumentosvn}">
			<h:graphicImage  id="imageSvn" width="16" height="16"
				title="#{txt.subversion} " url="#{conftxt.img_svn}" />
			<h:outputText rendered="#{FlowsResponse.swAttachmentSVN}"
				styleClass="form"
				value="#{txt.subversion}  #{txt.operacion_exitosa}" />
		</h:commandLink>
	</h:panelGroup>


</h:panelGrid>
<%
	/*FIN COMENTARIOS ATACHMENT*/
%>
<h:panelGrid columns="4">
	<h:outputText styleClass="form" value="#{txt.flow_Firma}" />
	<h:selectOneMenu id="estado" value="#{FlowsResponse.firma}"
		converter="ConverDoc_Estado">
		<f:selectItems value="#{FlowsResponse.tipoFirma}" />
	</h:selectOneMenu>

	<h:commandButton styleClass="boton" value="#{txt.flow_realizar}"
		action="#{FlowsResponse.saveDatosBasicos_action}"
		onclick="this.style.display='none';" />
	<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
		action="#{FlowsResponse.cancelar}" />
</h:panelGrid>



