<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 

  
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<t:messages id="messageList" styleClass="form"
            showSummary="true" showDetail="true" summaryFormat="{0}:" />            
<h:form>
    <h:inputHidden id="id" value="#{Seguridad_tab1.tree.nodo}"/>
    <h:panelGrid columns="2" border="0">
        <h:outputText styleClass="form" value="#{txt.struct_nombre}" />
        
        <h:inputText disabled="true" binding="#{Seguridad_tab1.inputTextNombre}" id="inputTextNombre"
                       value="#{Seguridad_tab1.tree.nombre}">
        </h:inputText>
        
        <h:outputText styleClass="form" value="#{txt.struct_descripcion}" />
       <h:inputTextarea cols="100" styleClass="form" rows="10"
       disabled="true"
					 value="#{Seguridad_tab1.tree.descripcion}"></h:inputTextarea>
             <% /*
          <t:inputHtml    displayValueOnly="true"
                   value="#{Seguridad_tab1.tree.descripcion}"
                                     style="width: 700px;"
                                     allowEditSource="false"
                                     showPropertiesToolBox="false"
                                     showLinksToolBox="false"
                                     showImagesToolBox="false"
                                     showTablesToolBox="false"
                                     showDebugToolBox="false"/>
        */ %>
        <h:commandButton styleClass="boton"  value="#{txt.btn_menu}" 
                         action="menu"/>
    </h:panelGrid>
  <h:panelGroup rendered="#{Seguridad_tab1.swMostrarCuidadoVariable}">
    <h:panelGrid 
                 columns="2" cellspacing="0" border="0" width="75%" >
        <h:panelGroup>
            <h:panelGrid columns="2">   
                <% /* LISTA DE USUARIOS*/ %>
                <h:selectManyListbox 
                immediate="true"
                converter="ConverUsuarios" 
                                     rendered="true"
                                      id="usersUnico1"
                                     value="#{Seguridad_tab1.selectedUsuarios}" size="5" style="width: 300px;">
                    <a4j:support event="onclick" action="#{Seguridad_tab1.changeCurrentUsuario}"  reRender="userOper"/>
                    <f:selectItems  id="visibleUsersFlows" value="#{Seguridad_tab1.visibleUsers}" />
                </h:selectManyListbox>
                <h:panelGroup id="userOper">
                    <h:panelGrid  columns="2">
                        <% /* LISTA DE OPERACIONES DE DICHO USUARIOS*/ %>
                        <h:outputText styleClass="form" value="#{txt.role_operaciones} #{Seguridad_tab1.userSeleccionado}" />
                        <h:selectManyListbox   converter="ConverOperaciones" 
                                               rendered="true"
                                                id="users2"
                                               value="#{Seguridad_tab1.selectedOperacionesUsers}" size="5" style="width: 300px;">
                            <f:selectItems  id="visibleUsersFlows2" value="#{Seguridad_tab1.operacionesInUsuario}" />
                        </h:selectManyListbox>
                    </h:panelGrid>
                </h:panelGroup> 
            </h:panelGrid> 
        </h:panelGroup> 
        
        <h:panelGroup></h:panelGroup>
        
        <h:panelGroup>
            <h:panelGrid columns="2">   
                <% /* LISTA DE ROLES*/ %>
                <h:selectManyListbox  converter="ConverRoles" 
                                      rendered="true" id="roles"
                                      immediate="true"
                                      value="#{Seguridad_tab1.selectedRoles}" size="5" style="width: 300px;">
                    <a4j:support event="onclick" action="#{Seguridad_tab1.changeCurrent}"  reRender="info"/>
                    <f:selectItems  id="visibleRolsFlows" value="#{Seguridad_tab1.visibleRole}" />    
                </h:selectManyListbox>
                <h:panelGroup id="info">
                    <h:panelGrid  columns="2">
                        <% /* LISTA DE OPERACIONES DE DICHO ROLE*/ %>
                        <h:outputText styleClass="form" value="#{txt.role_operaciones} #{Seguridad_tab1.rolSeleccionado}" />
                        <h:selectManyListbox converter="ConverOperaciones" 
                                             rendered="true"
                                              id="opeea"
                                             value="#{Seguridad_tab1.selectedOperaciones}" size="5" style="width: 300px;">
                            <f:selectItems   value="#{Seguridad_tab1.operacionesInRol}" />
                        </h:selectManyListbox>
                        <% /* LISTA DE USUARIOS DE DICHO ROLE*/ %>
                        <h:outputText styleClass="form" value="#{txt.role_usuariospertenece} #{Seguridad_tab1.rolSeleccionado}" />
                        <h:selectManyListbox converter="ConverUsuarios" 
                                             rendered="true"
                                              id="opeea2"
                                             value="#{Seguridad_tab1.selectedUsuariosInRoles}" size="5" style="width: 300px;">
                            <f:selectItems   value="#{Seguridad_tab1.usuariosInRol}" />
                        </h:selectManyListbox>
                    </h:panelGrid>
                </h:panelGroup> 
            </h:panelGrid> 
        </h:panelGroup>   
        <h:panelGroup></h:panelGroup>    
        
    </h:panelGrid>
  </h:panelGroup>
</h:form>
