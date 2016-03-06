<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 




<h:messages styleClass="form"
/>
<h:form>
    <h:panelGrid  columns="3" 
                  cellspacing="0" border="0" width="75%"
    >
        <h:outputText styleClass="form"  
                      value="#{SeguridadRole.tree.nombre}  [#{SeguridadRole.tree.descripcion}]"/>
        <f:verbatim>&nbsp;</f:verbatim>
        
        
    </h:panelGrid>    
    
    
    
    <h:panelGrid columns="3" cellspacing="0" border="0" width="75%" rendered="#{SeguridadRole.swIniciar}">
        
        <h:outputText styleClass="form" value="#{txt.Operaciones_noSelec}"/>
        <f:verbatim>&nbsp;</f:verbatim>
        <h:outputText styleClass="form" value="#{txt.Operaciones_Selec}"/>
        
        
        
        <h:selectManyListbox  converter="ConverRoles" styleClass="form" value="#{SeguridadRole.selectedInvisibleItems}" size="5" style="width: 300px;">
            <f:selectItems   value="#{SeguridadRole.invisibleItems}" />
        </h:selectManyListbox>
        
        <h:panelGrid columns="1">
            <h:commandButton image="/images/arrow-next.gif"   actionListener="#{SeguridadRole.moveSelectedToVisible}" style="width:20px;" />
            <h:commandButton image="/images/arrow-ff.gif" actionListener="#{SeguridadRole.moveAllToVisible}" style="width: 20px;" />
            <h:commandButton image="/images/arrow-fr.gif"  actionListener="#{SeguridadRole.moveAllToInvisible}" style="width: 20px;" />
            <h:commandButton image="/images/arrow-previous.gif" actionListener="#{SeguridadRole.moveSelectedToInvisible}" style="width: 20px;" />
        </h:panelGrid>
        
        <h:selectManyListbox  converter="ConverRoles" styleClass="form" value="#{SeguridadRole.selectedVisibleItems}" size="5"  style="width: 300px;">
            <f:selectItems  id="visibleItems" value="#{SeguridadRole.visibleItems}" />
        </h:selectManyListbox>
        <f:verbatim>&nbsp;</f:verbatim>
        <f:verbatim>&nbsp;</f:verbatim>
        <f:verbatim>&nbsp;</f:verbatim>
    </h:panelGrid>
    
    <h:panelGrid columns="3" cellspacing="0" border="0" width="75%" >        
        <h:commandButton styleClass="boton" rendered="#{!SeguridadRole.swIniciar}" styleClass="form"
                         value="#{txt.role_permiso}" 
                         action="#{SeguridadRole.editSeguridad_User}">
        </h:commandButton>
        <h:commandButton styleClass="boton" rendered="#{SeguridadRole.swIniciar}" 
                         styleClass="form" 
                         value="#{txt.role_listar}" 
                         action="#{SeguridadRole.editSeguridad_User}">/>
        </h:commandButton>
        
        <h:commandButton styleClass="boton" rendered="#{SeguridadRole.swIniciar}" styleClass="form" value="#{txt.usuario_guardar}" 
                         action="#{SeguridadRole.saveRole}"/>
        <h:commandButton styleClass="boton" rendered="#{!SeguridadRole.swIniciar}" styleClass="form" value="#{txt.btn_cancelar}" 
                         action="menu"/>
    </h:panelGrid>        
    
    
</h:form>

