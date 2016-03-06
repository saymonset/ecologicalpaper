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
    <%@include file="/inc/head.inc" %>
    <body>
        <f:view>
            <f:loadBundle
                basename="com.ecological.resource.ecologicalpaper"
                var="txt" />
            <f:loadBundle
                basename="org.apache.myfaces.examples.resource.example_messages"
                var="example_messages" />
            
            <h:messages/>
            <center><h2><h:outputText styleClass="form" value="#{txt.cliente}"/></h2></center>
            <h:form>
                <h:panelGrid columns="3" 
                             id="panelGrid1">
                    
                    
                    <h:panelGroup>
                        <h:outputText styleClass="form" id="pais" value="#{txt.pais} #{CrearCliente_EUJ.pais.nombre}, " />
                        <h:outputText styleClass="form" id="estado" value="#{txt.estado} #{CrearCliente_EUJ.estado.nombre}, " />
                        <h:outputText styleClass="form" id="ciudad" value="#{txt.ciudad} #{CrearCliente_EUJ.ciudad.nombre} " />
                    </h:panelGroup>
                    <f:verbatim></f:verbatim>
                    <f:verbatim></f:verbatim>
                    
                    <h:panelGroup>
                        <h:panelGrid columns="3" 
                                     id="panelGrid11">
                            <h:outputText styleClass="form" value="#{txt.usuario_nombre}"/>
                            <h:inputText  id="nombre" value="#{CrearCliente_EUJ.cliente_EUJ.nombre}" title="#{txt.usuario_nombre}" />
                            <h:message for="nombre"  styleClass="form"/>    
                            <h:outputText styleClass="form" value="#{txt.usuario_telefono_ofic}"/>
                            <h:inputText  id="telefono" value="#{CrearCliente_EUJ.cliente_EUJ.telefono}" title="#{txt.usuario_telefono_ofic}" />
                            <h:message for="telefono"  styleClass="form"/>
                            <h:outputText styleClass="form" value="#{txt.cliente}"/>
                            <h:selectBooleanCheckbox  id="selctCliente" immediate="true"
                                                      value="#{CrearCliente_EUJ.cliente_EUJ.eujovans}"
                            >
                            </h:selectBooleanCheckbox>
                            <h:message for="selctCliente"  styleClass="form"/>
                            
                        </h:panelGrid>
                        
                    </h:panelGroup>
                    <f:verbatim></f:verbatim>
                    <f:verbatim></f:verbatim>
                    
                    
                    
                    
                    <h:outputText styleClass="form" value="#{txt.direccion}"/>
                    <f:verbatim></f:verbatim>
                    <f:verbatim></f:verbatim>
                    
                    <h:panelGrid  columns="1" cellspacing="0" border="0" width="75%" >  
                        	<h:inputTextarea cols="100" styleClass="form" rows="10"
					value="#{CrearCliente_EUJ.cliente_EUJ.direccion}"></h:inputTextarea>
                       <%/*
                       <t:inputHtml id="inputTextDescripcion"
                                     value="#{CrearCliente_EUJ.cliente_EUJ.direccion}"
                                     style="width: 700px;"
                                     allowEditSource="false"
                                     showPropertiesToolBox="false"
                                     showLinksToolBox="false"
                                     showImagesToolBox="false"
                                     showTablesToolBox="false"
                                     showDebugToolBox="false"/>*/%>
                        
                        <h:message styleClass="form" for="inputTextDescripcion" />
                    </h:panelGrid>
                    <f:verbatim></f:verbatim>
                    <f:verbatim></f:verbatim>
                    
                    <h:panelGroup>
                        <h:commandButton styleClass="boton" id="btncancel"  value="#{txt.btn_cancelar}"
                                         action="#{CrearCliente_EUJ.cancelarListar}"
                        />
                        
                        <h:commandButton styleClass="boton" disabled="#{CrearCliente_EUJ.swNoModificar}"
                                         action="#{CrearCliente_EUJ.saveObjeto}" value="#{txt.usuario_guardar}"/>
                        <h:message styleClass="form" for="btncancel" />
                    </h:panelGroup> 
                    <f:verbatim></f:verbatim>
                    <f:verbatim></f:verbatim>
                    
                </h:panelGrid>
            </h:form>
            
            
            
            
        </f:view>
        
        
        
    </body>
    
</html>


