<%@ page import="java.math.BigDecimal,
java.util.Date"%>
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
    <script type="text/javascript"   src="./validacione.js"></script>
    <%@include file="inc/head.inc" %>
    
    <body>
        
        <f:view>
            
            
            
            
            <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>
            <f:loadBundle
                basename="com.util.resource.ecological_conf"
                var="conftxt" />
            <f:loadBundle
                basename="com.ecological.resource.ecologicalpaper"
                var="txt" />
            <h:messages id="messageList" showSummary="true" />
            
            <h:form id="form1"  enctype="multipart/form-data" > 
                
                <h:panelGrid columns="3" border="0">
                    
                    <f:verbatim/>
                    <h:outputText styleClass="form" value="#{txt.doc_editar} "/>
                    <f:verbatim/>                    
                    
                    <h:outputText styleClass="form" value="#{txt.doc_datosCargaDocumentotabp} "/>
                    <t:inputFileUpload   id="fileupload"
                                         accept="image/*"
                                         
                                         value="#{Documento.upFile}"
                                         storage="file"
                                         styleClass="fileUploadInput"
                                         required="true"
                                         maxlength="20000000"/>
                    <h:message for="fileupload" showDetail="true" />
                    
                    
                    <h:outputText styleClass="form" value="#{txt.doc_razodelcambio}" />
                    <h:inputTextarea id="desc2" cols="40" rows="3" value="#{Documento.doc_detalle.descripcion}">
                    </h:inputTextarea>
                    <h:message  for="desc2" showDetail="true" />
                    
                    <f:verbatim></f:verbatim>
                    <% /*<!--
                    AQUI SE CREA UN NUEVO DOCUMENTO-->*/%>
                    <h:commandLink id="newVersion" rendered="#{!Documento.swExecuteActualizar}"    
                                   action="#{Documento.nuevaVersionDoc}"
                    >
                        <h:graphicImage   
                            id="imageNewVersion" title="#{txt.doc_version_nueva}"
                            url="#{conftxt.img_newversion}" /> 
                    </h:commandLink>
                    
                    <%/*   <!--ACTUALIZAR, ES CON EL MISMO DOCUMENTO Y NO CREAR UN NUEVO DOCUMENTO -->*/%>
                    <h:commandLink id="update" rendered="#{Documento.swExecuteActualizar}"    
                                   action="#{Documento.actualizarDoc}"
                    >
                        <h:graphicImage   
                            id="imageUpdate" title="#{txt.doc_actualiza}"
                            url="#{conftxt.img_update}" /> 
                    </h:commandLink>
                    <f:verbatim></f:verbatim>
                    
                    
                    
                    
                </h:panelGrid>
            </h:form>
            <h:form id="form2">
                <h:panelGrid columns="3" border="0">
                    
                    
                    <h:commandButton styleClass="boton"  value="#{txt.btn_menu}"
                                      action="#{Documento.cancelar}"/>
                    <%/*  <!--Esro solo lo qu hace es actualizar el mismo documento -->*/%>
                    <h:commandButton styleClass="boton"
                        value="#{txt.download}"
                        onmousedown="showPlaceList(this,'placeList','find')"
                        action="" 
                    />
                    
                    
                    <h:commandLink      
                        action="#{Documento.viewDocumentoPDF}" 
                        actionListener="#{Documento.versionId}"
                    >
                        <h:graphicImage   
                            title="#{txt.doc_detalle}"
                            url="#{Documento.doc_detalle.icono}" /> 
                        
                        <f:param 
                            name="idDetall" 
                            value="#{Documento.doc_detalle.codigo}" />
                    </h:commandLink>
                    
                </h:panelGrid>
            </h:form>
        </f:view>
        
        
    </body>
    
</html>
