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
                <a4j:region renderRegionOnly="true" id="stat3">    
                    <h:panelGrid columns="2">     
                        <h:panelGrid columns="3">
                            <h:outputText id="sel3"  styleClass="form" value="#{txt.destinatario}"/>
                            <f:verbatim></f:verbatim>
                            <f:verbatim></f:verbatim>
                            
                            <h:outputText id="selectOneMenuDestinatario"  
                                          styleClass="form" value="#{crearFlete.facturaFlete.destinatario.nombre}" />
                            <f:verbatim></f:verbatim>
                            <f:verbatim></f:verbatim>
                        </h:panelGrid>
                        
                        
                        <h:panelGrid columns="3">
                            <h:outputText id="sel123"  styleClass="form" value="#{txt.remite}"/>
                            <f:verbatim></f:verbatim>
                            <f:verbatim></f:verbatim>
                            
                            <h:outputText id="selectOneMenuRemite"  
                                          styleClass="form" value="#{crearFlete.facturaFlete.remitente.nombre}" />
                            <f:verbatim></f:verbatim>
                            <f:verbatim></f:verbatim>
                            
                        </h:panelGrid>
                        
                    </h:panelGrid> 
                    
                    <h:panelGrid columns="3" 
                                 id="panelGrid1">
                        
                        
                        <h:panelGroup>
                            <h:panelGrid columns="3" 
                                         id="panelGrid11">
                                
                                <h:outputText styleClass="form" value="#{txt.credito}"/>
                                <h:selectOneRadio disabled="true" id="r1" value="#{crearFlete.facturaFlete.credito}" >
                                    <f:selectItem itemValue="true"   itemLabel="#{txt.si}"/>
                                    <f:selectItem itemValue="false"  itemLabel="#{txt.no}"/>
                                </h:selectOneRadio>     
                                <f:verbatim></f:verbatim>
                                
                                
                                <h:outputText styleClass="form" value="#{txt.numero_entrega}"/>
                                <h:inputText disabled="true" id="nombre" value="#{crearFlete.facturaFlete.numero_entrega}" title="#{txt.numero_entrega}" />
                                <h:message for="nombre"  styleClass="form"/>   
                                
                                <h:outputText styleClass="form"  value="#{txt.fecha}"/>
                                <t:inputCalendar  id="fecha_caduca"
                                                  disabled="#{crearFlete.swNoPuedeModificar}"
                                                  monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                                  currentDayCellClass="currentDayCell" value="#{crearFlete.facturaFlete.fecha}" renderAsPopup="true"
                                                  popupTodayString="#{txt.calendar_fecha}"
                                                  popupDateFormat="MM/dd/yyyy" popupWeekString="#{txt.popup_week_string}"
                                                  helpText="MM/DD/YYYY"
                                                  forceId="true"
                                />
                                <h:message for="fecha_caduca"  styleClass="form"/>
                                
                                
                                <h:outputText styleClass="form" value="#{txt.factura}"/>
                                <h:inputText  id="factura" value="#{crearFlete.facturaFlete.factura}" title="#{txt.factura}" />
                                <h:message for="factura"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.cant_bultos}"/>
                                <h:inputText  id="cant_bultos" value="#{crearFlete.facturaFlete.cant_bultos}" title="#{txt.cant_bultos}" />
                                <h:message for="cant_bultos"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.valor_decl}"/>
                                <h:inputText  id="valor_decl" value="#{crearFlete.facturaFlete.valor_decl}" title="#{txt.valor_decl}" />
                                <h:message for="valor_decl"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.flete_kg_vol}"/>
                                <h:inputText   id="flete_kg_vol" value="#{crearFlete.facturaFlete.flete_kg_vol}" title="#{txt.flete_kg_vol}" />
                                <h:message for="flete_kg_vol"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.seguro}"/>
                                <h:inputText  id="seguro" value="#{crearFlete.facturaFlete.seguro}" title="#{txt.seguro}" />
                                <h:message for="seguro"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.iva}"/>
                                <h:inputText   id="iva" value="#{crearFlete.facturaFlete.iva}" title="#{txt.iva}" />
                                <h:message for="iva"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.total_fletes}"/>
                                <h:inputText  id="total_fletes" value="#{crearFlete.facturaFlete.total_fletes}" title="#{txt.total_fletes}" />
                                <h:message for="total_fletes"  styleClass="form"/> 
                                
                                
                                <h:panelGroup>
                                    <h:outputText styleClass="form" value="#{txt.chofer}" />
                                    <h:selectBooleanCheckbox 
                                        disabled="#{crearFlete.swNoPuedeModificar}"
                                        value="#{crearFlete.swAsignaChofer}"/>
                                </h:panelGroup>
                                <h:selectOneMenu 
                                    disabled="#{crearFlete.swNoPuedeModificar}"
                                    id="duenio" value="#{crearFlete.facturaFlete.chofer}"
                                    converter="ConverUsuarios">
                                    <f:selectItems value="#{DatosCombo.usuarios}" />
                                </h:selectOneMenu>
                                <h:message for="duenio" showDetail="true" />
                                
                                <f:verbatim></f:verbatim>
                                <h:panelGroup>
                                    <h:commandButton styleClass="boton" id="btncancel"  value="#{txt.btn_cancelar}"
                                                     action="#{crearFlete.cancelarListar}"
                                    />
                                    <h:commandButton styleClass="boton" action="#{crearFlete.saveObjeto}"
                                            onclick="if (!confirm('Desea ejecutar la accion?')) return false"
                                                      disabled="#{crearFlete.deshabilitado}"
                                                      value="#{crearFlete.txtSaveButton}"/>
                                    <h:commandButton styleClass="boton"   value="#{txt.download}" 
                                                     action="#{crearFlete.getDownload}"/>
                                </h:panelGroup>
                                <f:verbatim></f:verbatim>
                            </h:panelGrid>
                            
                            
                            
                        </h:panelGroup> 
                        
                    </h:panelGrid>
                </a4j:region>
            </h:form>
            
            
            
            
        </f:view>
        
        
        
    </body>
    
</html>


