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
                            <f:verbatim></f:verbatim>
                            <h:outputText id="sel3"  styleClass="form" value="#{txt.destinatario}"/>
                            <f:verbatim></f:verbatim>
                            
                            <h:outputText id="selectOneMenuPais2"  styleClass="form" value="#{txt.pais}"/>
                            <h:selectOneMenu 
                                id="selectPais"
                                immediate="true"
                                value="#{crearFlete.paisDestinatario}" converter="ConverPais">
                                <a4j:support event="onchange"  
                                             reRender="selectEstadoRemite,selectCiudadRemite,selectDestinatario"
                                             action="#{crearFlete.colocarPaisSessionDestinatario}"
                                             
                                />
                                <f:selectItems value="#{DatosCombo.allPaises}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuPais2" styleClass="form"/>     
                            
                            <h:outputText id="selectOneMenuEstado2"  
                                          styleClass="form" value="#{txt.estado}" />
                            <h:selectOneMenu 
                                id="selectEstadoRemite"
                                immediate="true"
                                value="#{crearFlete.estadoDestinatario}" converter="ConverEstado">
                                <a4j:support event="onchange"  
                                             reRender="selectCiudadRemite,selectDestinatario"
                                             action="#{crearFlete.colocarEstadoSessionDestinatario}"
                                />
                                <f:selectItems value="#{crearFlete.listEstadosDestinatario}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuEstado2" styleClass="form"/> 
                            
                            
                            <h:outputText id="selectOneMenuCiudad2"  
                                          styleClass="form" value="#{txt.ciudad}" />
                            <h:selectOneMenu 
                                id="selectCiudadRemite"
                                immediate="true"
                                value="#{crearFlete.ciudadDestinatario}" converter="ConverCiudad">
                                <a4j:support event="onchange"  
                                             reRender="selectDestinatario"
                                             action="#{crearFlete.colocarCiudadSessionDestinatario}"
                                             
                                />
                                <f:selectItems value="#{crearFlete.listCiudadDestinatario}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuCiudad2" styleClass="form"/> 
                            
                            <h:outputText id="selectOneMenuDestinatario"  
                                          styleClass="form" value="#{txt.cliente}" />
                            <h:selectOneMenu 
                                id="selectDestinatario"
                                immediate="true"
                                value="#{crearFlete.facturaFlete.destinatario}" converter="ConverCliente_EUJ">
                                <f:selectItems value="#{crearFlete.cliente_EUJ_SDestinatario}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuDestinatario" styleClass="form"/> 
                        </h:panelGrid>
                        
                        
                        <h:panelGrid columns="3">
                            <f:verbatim></f:verbatim>
                            <h:outputText id="sel123"  styleClass="form" value="#{txt.remite}"/>
                            <f:verbatim></f:verbatim>
                            <h:outputText id="selectOneMenuPais22"  styleClass="form" value="#{txt.pais}"/>
                            <h:selectOneMenu 
                                id="selectPais2"
                                immediate="true"
                                value="#{crearFlete.paisRemite}" converter="ConverPais">
                                <a4j:support event="onchange"  
                                             reRender="selectEstado2Remite,selectCiudad2Remite,selectRemite"
                                             action="#{crearFlete.colocarPaisSessionRemite}"
                                />
                                <f:selectItems value="#{DatosCombo.allPaises}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuPais22" styleClass="form"/>     
                            
                            
                            <h:outputText id="selectOneMenuEstado22"  
                                          styleClass="form" value="#{txt.estado}" />
                            <h:selectOneMenu 
                                id="selectEstado2Remite"
                                immediate="true"
                                value="#{crearFlete.estadoRemite}" converter="ConverEstado">
                                <a4j:support event="onchange"  
                                             reRender="selectCiudad2Remite,selectRemite"
                                             action="#{crearFlete.colocarEstadoSessionRemite}"
                                />
                                <f:selectItems value="#{crearFlete.listEstadosRemite}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuEstado22" styleClass="form"/> 
                            
                            
                            <h:outputText id="selectOneMenuCiudad22"  
                                          styleClass="form" value="#{txt.ciudad}" />
                            <h:selectOneMenu 
                                id="selectCiudad2Remite"
                                immediate="true"
                                value="#{crearFlete.ciudadRemite}" converter="ConverCiudad">
                                <a4j:support event="onchange"  
                                             reRender="selectRemite"
                                             action="#{crearFlete.colocarCiudadSessionRemite}"
                                />
                                <f:selectItems value="#{crearFlete.listCiudadRemite}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuCiudad22" styleClass="form"/> 
                            
                            
                            <h:outputText id="selectOneMenuRemite"  
                                          styleClass="form" value="#{txt.cliente}" />
                            <h:selectOneMenu 
                                id="selectRemite"
                                immediate="true"
                                value="#{crearFlete.facturaFlete.remitente}" converter="ConverCliente_EUJ">
                                <f:selectItems value="#{crearFlete.cliente_EUJ_SRemite}" />
                            </h:selectOneMenu>
                            <h:message for="selectOneMenuRemite" styleClass="form"/> 
                        </h:panelGrid>
                        
                    </h:panelGrid> 
                    
                    <h:panelGrid columns="3" 
                                 id="panelGrid1">
                        
                        
                        <h:panelGroup>
                            <h:panelGrid columns="3" 
                                         id="panelGrid11">
                                
                                <h:outputText styleClass="form" value="#{txt.credito}"/>
                                <h:selectOneRadio id="r1" value="#{crearFlete.facturaFlete.credito}" >
                                    <f:selectItem itemValue="#{!crearFlete.facturaFlete.credito}" itemLabel="#{txt.si}"/>
                                    <f:selectItem itemValue="#{crearFlete.facturaFlete.credito}" itemLabel="#{txt.no}"/>
                                </h:selectOneRadio>     
                                <f:verbatim></f:verbatim>
                                
                                
                                <h:outputText styleClass="form" value="#{txt.numero_entrega}"/>
                                <h:inputText  id="nombre" value="#{crearFlete.facturaFlete.numero_entrega}" title="#{txt.numero_entrega}" />
                                <h:message for="nombre"  styleClass="form"/>   
                                <%/*
                                <h:outputText styleClass="form"  value="#{txt.fecha}"/>
                                <t:inputCalendar id="fecha_caduca"
                                                 monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                                 currentDayCellClass="currentDayCell" value="#{crearFlete.facturaFlete.fecha}" renderAsPopup="true"
                                                 popupTodayString="#{txt.calendar_fecha}"
                                                 popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                                 helpText="dd.MM.yyyy"
                                                 forceId="true"
                                />
                                <h:message for="fecha_caduca"  styleClass="form"/>
                                
                                */%>
                                <h:outputText styleClass="form" value="#{txt.factura}"/>
                                <h:inputText  id="factura" value="#{crearFlete.facturaFlete.factura}" title="#{txt.factura}" />
                                <h:message for="factura"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.cant_bultos}"/>
                                <h:inputText  id="cant_bultos" value="#{crearFlete.facturaFlete.cant_bultos}" title="#{txt.cant_bultos}" />
                                <h:message for="cant_bultos"  styleClass="form"/> 
                                
                                <h:outputText styleClass="form" value="#{txt.valor_decl}"/>
                                <h:inputText  id="valor_decl" value="#{crearFlete.valorDeclarado}" 
                                              title="#{txt.valor_decl}" >
                                    <a4j:support event="onchange"  />
                                </h:inputText>
                                <h:message for="valor_decl"  styleClass="form"/> 
                                
                                
                                
                                
                                <h:outputText styleClass="form" value="#{txt.flete_kg_vol}"/>
                                <h:panelGroup>
                                    <h:inputText  id="flete_kg_vol" value="#{crearFlete.facturaFlete.flete_kg_vol}"
                                                  title="#{txt.flete_kg_vol}" >
                                        <a4j:support event="onchange" reRender="total_fletes" />
                                    </h:inputText>
                                    <h:outputText styleClass="form" value="%"/>
                                    <h:selectBooleanCheckbox immediate="true"
                                                             value="#{crearFlete.swCalcularFlete_kg_vol}"
                                    >
                                        <a4j:support event="onchange" reRender="porctfletekg,flete_seguro1,total_fletes" />
                                    </h:selectBooleanCheckbox>
                                    <h:inputText  immediate="true" id="porctfletekg"
                                                  value="#{crearFlete.facturaFlete.porct_flete_kg_vol}" title="" />
                                </h:panelGroup>
                                <h:message for="flete_kg_vol"  styleClass="form"/> 
                                
                                
                                <h:outputText styleClass="form" value="#{txt.seguro}"/>
                                <h:panelGroup>
                                    <h:inputText  id="flete_seguro1" value="#{crearFlete.facturaFlete.seguro}"
                                                  title="#{txt.seguro}" >
                                        <a4j:support event="onchange" reRender="total_fletes" />
                                    </h:inputText>
                                    <h:outputText styleClass="form" value="%"/>
                                    <h:selectBooleanCheckbox immediate="true"
                                                             value="#{crearFlete.swSeguro}"
                                    >
                                        <a4j:support event="onchange" reRender="porctseguro,flete_seguro1,total_fletes" />
                                    </h:selectBooleanCheckbox>
                                    <h:inputText  immediate="true" id="porctseguro"
                                                  value="#{crearFlete.facturaFlete.porct_seguro}" title="" />
                                </h:panelGroup>
                                <h:message for="flete_seguro1"  styleClass="form"/> 
                                
                                
                                <h:outputText styleClass="form" value="#{txt.iva}"/>
                                <h:panelGroup>
                                    <h:inputText  id="iva2" value="#{crearFlete.facturaFlete.iva}" title="#{txt.iva}" >
                                        <a4j:support event="onchange" reRender="total_fletes" />
                                    </h:inputText>
                                    <h:outputText styleClass="form" value="%"/>
                                    <h:selectBooleanCheckbox immediate="true"
                                                             value="#{crearFlete.swIva}"
                                    >
                                        <a4j:support event="onchange" reRender="porctiva2,iva2,total_fletes" />
                                    </h:selectBooleanCheckbox>
                                    <h:inputText  immediate="true" id="porctiva2"
                                                  value="#{crearFlete.facturaFlete.porct_iva}" title="" />
                                </h:panelGroup>
                                <h:message for="iva2"  styleClass="form"/> 
                                
                                <%/*        <h:outputText styleClass="form" value="#{txt.total_fletes}"/>
                                <h:panelGroup>
                                <h:inputText  id="total_fletes"
                                immediate="true"
                                value="#{crearFlete.facturaFlete.total_fletes}"
                                title="#{txt.total_fletes}" />
                                
                                
                                </h:panelGroup>
                                <h:message for="total_fletes"  styleClass="form"/>   */%>
                                
                                
                                <h:panelGroup>
                                    <h:outputText styleClass="form" value="#{txt.chofer}" />
                                    <h:selectBooleanCheckbox value="#{crearFlete.swAsignaChofer}"/>
                                </h:panelGroup>
                                <h:selectOneMenu id="duenio" value="#{crearFlete.facturaFlete.chofer}"
                                                 converter="ConverUsuarios">
                                    <f:selectItems value="#{DatosCombo.usuarios}" />
                                </h:selectOneMenu>
                                <h:message for="duenio" showDetail="true" />
                                
                                <f:verbatim></f:verbatim>
                                <h:panelGroup>
                                    <h:commandButton styleClass="boton"  action="#{crearFlete.create}" 
                                                      onclick="if (!confirm('Desea ejecutar la accion?')) return false"
                                                      value="#{txt.usuario_guardar}"/>
                                                      <f:verbatim></f:verbatim>
                                    <h:commandButton styleClass="boton" id="btncancel"  value="#{txt.btn_cancelar}"
                                                     action="#{crearFlete.cancelarListar}"
                                    />
                                    
                                    
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


