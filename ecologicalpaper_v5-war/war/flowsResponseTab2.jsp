<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 

  
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>

<h:messages styleClass="form"
/>
<h:panelGrid columns="2" border="0">
    <h:form id="viewForm">
    <f:verbatim><p></f:verbatim>
    <%/*<!--<h:outputText styleClass="form" value="#{FlowsResponse.nomCompletoFlowDoc} "/>-->*/%>
    <h:selectBooleanCheckbox value="#{FlowsResponse.flow.condicional}" disabled="true"/>
    <h:outputText styleClass="form" value="#{txt.flow_Condicional}"/>
    <h:selectBooleanCheckbox value="#{FlowsResponse.flow.secuencial}" disabled="true"/>
    <h:outputText styleClass="form" value="#{txt.flow_Secuencial}"/>
    <h:selectBooleanCheckbox  value="#{FlowsResponse.flow.notificacionMail}" disabled="true"/>
    <h:outputText  styleClass="form" value="#{txt.flow_envio_mail}"/>
    <t:dataTable id="data" styleClass="standardTable" headerClass="standardTable_Header" 
                 footerClass="standardTable_Header" rowClasses="standardTable_Row1,standardTable_Row2"
                 columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column" 
                 var="flowWithParticipantes" value="#{FlowsResponse.flowWithParticipantes}"
                 preserveDataModel="true" varDetailToggler="detailToggler">
        <h:column>
            <f:facet name="header">
                <h:outputText  value="#{txt.doc_nombretab}" />
            </f:facet>
            <h:commandLink rendered="#{detailToggler.currentDetailExpanded}" 
                           action="#{detailToggler.toggleDetail}">
                <h:outputText styleClass="form" value="#{flowWithParticipantes.estado.nombre}" />
            </h:commandLink>
            <h:commandLink rendered="#{!detailToggler.currentDetailExpanded}"
                           action="#{detailToggler.toggleDetail}">
                <h:outputText value="#{flowWithParticipantes.estado.nombre}" />    
            </h:commandLink>
        </h:column>
        
        <f:facet name="detailStamp">
            <t:dataTable  id="cities" styleClass="standardTable_Column"
                          var="detalle" value="#{flowWithParticipantes.participantesFlows }">
                <h:column>
                    <h:commandLink  id="Edit" 
                                    action="">
                        <h:outputText  value="#{detalle.participante.nombre}" style="font-size: 11px" />                                    
                        <f:param id="editId" 
                                 name="id" 
                                 value="#{detalle.codigo}" />
                    </h:commandLink>
                </h:column>
            </t:dataTable>
        </f:facet>
        
        <f:verbatim>
            <br>
        </f:verbatim>
    </t:dataTable>
      </h:form> 
    
    
</h:panelGrid>









