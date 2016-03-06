<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 
  

<t:messages id="messageList" styleClass="form"
showSummary="true" showDetail="true" summaryFormat="{0}:" />            
<h:form>
    <h:inputHidden id="id" value="#{Seguridad.tree.nodo}"/>
    <h:panelGrid columns="2" border="0">
        <h:outputText styleClass="form" value="#{txt.struct_nombre}" />
        <h:inputText  binding="#{Seguridad.inputTextNombre}" id="inputTextNombre"
                      styleClass="form" value="#{Seguridad.tree.nombre}">
        </h:inputText>
        
        <h:outputText styleClass="form" value="#{txt.struct_descripcion}" />
        <h:inputText   
            styleClass="form" value="#{Seguridad.tree.descripcion}"
            binding="#{Seguridad.inputTextDescripcion}" id="inputTextDescripcion">
        </h:inputText>
        
        <h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" 
                         action="menu"/>
        <h:commandButton styleClass="boton" value="#{txt.usuario_guardar}" 
                         action="#{Seguridad.saveDatosBasicos_action}"/>
    </h:panelGrid>
</h:form>
