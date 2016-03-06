<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>


<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>
   
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
    <%@include file="/inc/head.inc" %>
    <body>
        <f:view>
            
            <f:loadBundle
                basename="com.ecological.resource.ecologicalpaper"
                var="txt" />
            <f:loadBundle
                basename="org.apache.myfaces.examples.resource.example_messages"
                var="example_messages" />
            
            <f:loadBundle
                basename="com.util.resource.ecological_conf"
                var="conftxt" />
            
            <h:messages/>
            
            <!-- 
            CUERPO DEL MENSAJE 
            -->
            <h:form>
                <a4j:region renderRegionOnly="true" id="stat3">    
                    <h:panelGrid columns="2" binding="#{backing_login.panelGrid1}"
                                 id="panelGrid1">
                        <h:panelGroup>
                            <h:commandButton styleClass="boton"  value="#{txt.btn_menu}" 
                                               action="#{crearFlete.cancelar}"/>
                            <%/*  <h:commandButton immediate="true"  
                            value="#{crearFlete.historicoStr}" 
                            action="#{crearFlete.historico}">
                            <a4j:support event="onclick" reRender="data" />
                            </h:commandButton>*/%>
                        </h:panelGroup>
                        
                        <h:commandButton styleClass="boton" rendered="#{crearFlete.swAdd}"
                                         value="#{txt.btn_crear}" 
                                         action="#{crearFlete.inic_crear}"/>
                        <%/**REMESA*/%>
                        <h:commandButton  styleClass="boton"  value="#{txt.download} #{txt.remesa}" 
                                            action="#{crearFlete.getDownloadRemesaCobranza2}"/>
                        <%/**COBRANZA*/%>
                        <h:commandButton   styleClass="boton" value="#{txt.download} #{txt.cobranza}" 
                                            action="#{crearFlete.getDownloadRemesaCobranza}"/>
                        
                        
                        
                    </h:panelGrid>
                    
                    <h:panelGroup>
                        
                        
                        
                        <h:panelGrid columns="2">
                            <h:panelGroup>
                                <h:outputText styleClass="form" value="#{txt.numero_entrega}" />
                                <h:inputText  id="searchtext" styleClass="none"   
                                              value="#{crearFlete.strBuscar}" 
                                              immediate="true">
                                </h:inputText>
                            </h:panelGroup>
                            <h:panelGroup>
                                <h:commandLink>
                                    <a4j:support event="onclick" reRender="data" />
                                    <a4j:status for="stat3" stopText=" ">
                                        <f:facet name="start">
                                            <h:graphicImage value="#{conftxt.img_reloj}" />
                                        </f:facet>
                                    </a4j:status>
                                    <h:graphicImage value="#{conftxt.img_search}" />
                                </h:commandLink>
                                <h:outputText  styleClass="form"  value="#{txt.search_buscar}" />
                            </h:panelGroup>
                            
                            
                            <h:outputText styleClass="form" value="#{txt.chofer}" />                            
                            <h:panelGroup>
                                <h:selectBooleanCheckbox immediate="true"
                                                         value="#{crearFlete.swAsignaChofer}"
                                >
                                    <a4j:support event="onchange" reRender="data" />
                                </h:selectBooleanCheckbox>
                                
                                <h:selectOneMenu id="bscporchofer" value="#{crearFlete.buscarPorChofer}"
                                                 immediate="true"
                                                 converter="ConverUsuarios">
                                    <a4j:support event="onchange" reRender="data"   />
                                    <f:selectItems value="#{DatosCombo.usuarios}" />
                                </h:selectOneMenu>
                            </h:panelGroup>
                            
                            
                            <h:outputText styleClass="form"  value="#{txt.usuario_fechac}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox immediate="true"
                                                         value="#{crearFlete.swFechaCreado}"
                                >
                                    <a4j:support event="onchange" reRender="data" />
                                </h:selectBooleanCheckbox>
                                <h:outputText styleClass="form"  value="#{txt.desde}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.buscarFechaCreado}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                                <h:outputText styleClass="form"  value="#{txt.hasta}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.buscarFechaCreadoHasta}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                            </h:panelGroup>
                            
                            <h:outputText styleClass="form"  value="#{txt.fechaemitido}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox immediate="true"
                                                         value="#{crearFlete.swFechaemitido}"
                                >
                                    <a4j:support event="onchange" reRender="data" />
                                </h:selectBooleanCheckbox>
                                <h:outputText styleClass="form"  value="#{txt.desde}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.buscarFechaemitido}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                                <h:outputText styleClass="form"  value="#{txt.hasta}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.fechaemitidoHasta}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                            </h:panelGroup>
                            
                            <h:outputText styleClass="form"  value="#{txt.fechaconfirmaentrega}"/>
                            <h:panelGroup>
                                
                                <h:selectBooleanCheckbox immediate="true"
                                                         value="#{crearFlete.swFechaconfirmaentrega}"
                                >
                                    <a4j:support event="onchange" reRender="data" />
                                </h:selectBooleanCheckbox>
                                <h:outputText styleClass="form"  value="#{txt.desde}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.buscarFechaconfirmaentrega}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                                <h:outputText styleClass="form" value="#{txt.hasta}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.buscarFechaconfirmaentregaHasta}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                                
                                
                            </h:panelGroup>
                            
                            <h:outputText styleClass="form"  value="#{txt.fechapagado}"/>
                            <h:panelGroup>
                                <h:selectBooleanCheckbox immediate="true"
                                                         value="#{crearFlete.swFechapagado}"
                                >
                                    <a4j:support event="onchange" reRender="data" />
                                </h:selectBooleanCheckbox>
                                <h:outputText styleClass="form"  value="#{txt.desde}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.buscarFechapagado}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                                <h:outputText styleClass="form" value="#{txt.hasta}" />
                                <t:inputCalendar 
                                    monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                    currentDayCellClass="currentDayCell" value="#{crearFlete.fechapagadoHasta}" renderAsPopup="true"
                                    popupTodayString="#{txt.calendar_fecha}"
                                    popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                    helpText="dd.MM.yyyy"
                                    forceId="true"
                                >
                                    <a4j:support reRender="data"  event="onchange"/>
                                </t:inputCalendar>
                            </h:panelGroup>
                            
                            
                            
                            
                            
                            
                            <h:outputText id="selectOdestinatario"  
                                          styleClass="form" value="#{txt.destinatario}" />
                            <h:selectOneMenu 
                                immediate="true"
                                value="#{crearFlete.buscarDestinatario}" converter="ConverCliente_EUJ">
                                <a4j:support event="onchange" reRender="data" />
                                <f:selectItems value="#{crearFlete.cliente_EUJ_TODOS}" />
                            </h:selectOneMenu>
                            
                            
                            <h:outputText  
                                styleClass="form" value="#{txt.remite}" />
                            <h:selectOneMenu 
                                immediate="true"
                                value="#{crearFlete.buscarRemitente}" 
                                converter="ConverCliente_EUJ">
                                <a4j:support reRender="data"  event="onchange"/>
                                <f:selectItems value="#{crearFlete.cliente_EUJ_SOLO}" />
                            </h:selectOneMenu>
                        </h:panelGrid>
                        <t:dataTable id="data"
                                     rowIndexVar="rowIndexVar"
                                     rowCountVar="rowCountVar"
                                     previousRowDataVar="previousRowDataVar"
                                     styleClass="scrollerTable"
                                     headerClass="standardTable_Header"
                                     footerClass="standardTable_Header"
                                     rowClasses="standardTable_Row1,standardTable_Row2"
                                     columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                                     var="car"
                                     value="#{crearFlete.factura_S}"
                                     preserveDataModel="false"
                                     rows="#{Utilidades.verNumeroDeRegistros}"
                        >
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.numero_entrega}" />
                                </f:facet>
                                <h:panelGroup rendered="#{crearFlete.swMod}">
                                    <%/*
                                     Si se puede borrar, es poreque no esta anulada
                                     */%>
                                    <h:panelGroup rendered="#{car.delete}">
                                        <h:commandLink  id="Edit" 
                                                        action="#{crearFlete.edit}" 
                                                        actionListener="#{crearFlete.selection}">
                                            <h:outputText   value="#{car.numero_entrega}" />
                                            <f:param id="editId" 
                                                     name="id" 
                                                     value="#{car.codigo}" />
                                        </h:commandLink>
                                        
                                    </h:panelGroup>
                                    <%/*
                                     Si no se puede borrar, es porque esta anulada
                                     *y no se podra editar...
                                     */%>
                                    <h:panelGroup rendered="#{!car.delete}">
                                        <h:outputText   value="#{car.numero_entrega}" />
                                    </h:panelGroup>
                                    
                                </h:panelGroup>
                                <h:panelGroup rendered="#{!crearFlete.swMod}">
                                    <h:outputText  value="#{car.numero_entrega}"
                                                   title="#{car.numero_entrega}"/>
                                </h:panelGroup>                                     
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.destinatario}"/>
                                </f:facet>
                                
                                <h:commandLink  id="Edit1D" 
                                                action="#{CrearCliente_EUJ.editDestRemit}" 
                                                actionListener="#{CrearCliente_EUJ.selectionFacturaDestinatario}">
                                    <h:outputText   value="#{car.destinatario.nombre}" />
                                    <f:param id="editIdF" 
                                             name="id" 
                                             value="#{car.destinatario.codigo}" />
                                </h:commandLink>
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.remite}"/>
                                </f:facet>
                                <h:commandLink  id="Edit2R" 
                                                action="#{CrearCliente_EUJ.editDestRemit}" 
                                                actionListener="#{CrearCliente_EUJ.selectionFacturaRemite}">
                                    <h:outputText  value="#{car.remitente.nombre}" />
                                    <f:param id="editIdR" 
                                             name="id" 
                                             value="#{car.remitente.codigo}" />
                                </h:commandLink>
                                
                                
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.factura}"/>
                                </f:facet>
                                <h:outputText  value="#{car.factura}" />
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.cant_bultos}"/>
                                </f:facet>
                                <h:outputText  value="#{car.cant_bultos}" />
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.valor_decl}"/>
                                </f:facet>
                                <h:outputText  value="#{car.valor_decl}" />
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.flete_kg_vol}"/>
                                </f:facet>
                                <h:outputText  value="#{car.flete_kg_vol}" />
                            </h:column>  
                            
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.seguro}"/>
                                </f:facet>
                                <h:outputText  value="#{car.seguro}" />
                            </h:column>  
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.iva}"/>
                                </f:facet>
                                <h:outputText  value="#{car.iva}" />
                            </h:column>  
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.total_fletes}"/>
                                </f:facet>
                                <h:outputText  value="#{car.total_fletes}" />
                            </h:column>  
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.credito}"/>
                                </f:facet>
                                <h:outputText  value="#{car.creditoStr}" />
                            </h:column>  
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.estado}"/>
                                </f:facet>
                                <h:outputText  value="#{car.estadoStr}" />
                            </h:column>  
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.chofer}"/>
                                </f:facet>
                                <%/*<h:commandLink  id="Edit" 
                                action="#{CrearCliente_EUJ.edit}" 
                                actionListener="#{CrearCliente_EUJ.selection}">
                                <h:outputText  value="#{car.chofer.nombre} 
                                #{car.chofer.apellido}[#{car.chofer.cargo.nombre}]" />
                                
                                <f:param id="editId" 
                                name="id" 
                                value="#{car.codigo}" />
                                </h:commandLink>*/%>
                                <h:outputText  value="#{car.chofer.nombre} 
                                               #{car.chofer.apellido}[#{car.chofer.cargo.nombre}]" />
                                
                            </h:column>  
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.usuario_fechac}"/>
                                </f:facet>
                                <h:outputText  value="#{car.fechaStr}" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.fechaemitido}"/>
                                </f:facet>
                                <h:outputText  value="#{car.fechaemitidoStr}" />
                            </h:column>  
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.fechaconfirmaentrega}"/>
                                </f:facet>
                                <h:outputText  value="#{car.fechaconfirmaentregaStr}" />
                            </h:column>  
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.fechapagado}"/>
                                </f:facet>
                                <h:outputText  value="#{car.fechapagadoStr}" />
                            </h:column>  
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:graphicImage   
                                        title="#{txt.btn_erase}"
                                        url="#{conftxt.img_erase}" /> 
                                    
                                </f:facet>
                                <h:panelGroup rendered="#{car.delete}">
                                    <h:commandLink  id="del" 
                                                    action="#{crearFlete.delete}" 
                                                    actionListener="#{crearFlete.selection}"
                                                    onclick="if (!confirm('Are you sure you want to delete this event?')) return false">
                                        >
                                        <h:graphicImage   
                                            title="#{txt.btn_erase}"
                                            url="#{conftxt.img_erase}" /> 
                                        
                                        <f:param id="editId" 
                                                 name="id" 
                                                 value="#{car.codigo}" />
                                    </h:commandLink>
                                </h:panelGroup>
                                
                                <h:panelGroup rendered="#{!car.delete}">
                                    <h:outputText value="#{txt.anulada}" /> 
                                    
                                </h:panelGroup>
                            </h:column>
                        </t:dataTable>
                        
                        <h:panelGrid columns="1" styleClass="scrollerTable2" columnClasses="standardTable_ColumnCentered" >
                            <t:dataScroller id="scroll_1"
                                            for="data"
                                            fastStep="#{Utilidades.verNumeroDeRegistros}"
                                            pageCountVar="pageCount"
                                            pageIndexVar="pageIndex"
                                            styleClass="scroller"
                                            paginator="true"
                                            paginatorMaxPages="#{Utilidades.verpaginatorMaxPages}"
                                            paginatorTableClass="paginator"
                                            paginatorActiveColumnStyle="font-weight:bold;"
                                            immediate="true"
                                            actionListener="#{scrollerList.scrollerAction}"
                            >
                                <f:facet name="first" >
                                    <t:graphicImage url="/images/arrow-first.gif" border="1" />
                                </f:facet>
                                <f:facet name="last">
                                    <t:graphicImage url="/images/arrow-last.gif" border="1" />
                                </f:facet>
                                <f:facet name="previous">
                                    <t:graphicImage url="/images/arrow-previous.gif" border="1" />
                                </f:facet>
                                <f:facet name="next">
                                    <t:graphicImage url="/images/arrow-next.gif" border="1" />
                                </f:facet>
                                <f:facet name="fastforward">
                                    <t:graphicImage url="/images/arrow-ff.gif" border="1" />
                                </f:facet>
                                <f:facet name="fastrewind">
                                    <t:graphicImage url="/images/arrow-fr.gif" border="1" />
                                </f:facet>
                                
                                
                            </t:dataScroller>
                            <t:dataScroller id="scroll_2"
                                            for="data"
                                            rowsCountVar="rowsCount"
                                            displayedRowsCountVar="displayedRowsCountVar"
                                            firstRowIndexVar="firstRowIndex"
                                            lastRowIndexVar="lastRowIndex"
                                            pageCountVar="pageCount"
                                            immediate="true"
                                            pageIndexVar="pageIndex"
                            >
                                <h:outputFormat  value="#{example_messages['dataScroller_pages']}" styleClass="standard" >
                                    <f:param value="#{rowsCount}" />
                                    <f:param value="#{displayedRowsCountVar}" />
                                    <f:param value="#{firstRowIndex}" />
                                    <f:param value="#{lastRowIndex}" />
                                    <f:param value="#{pageIndex}" />
                                    <f:param value="#{pageCount}" />
                                </h:outputFormat>
                            </t:dataScroller>
                        </h:panelGrid>
                        
                        
                        
                        
                    </h:panelGroup>
                </a4j:region>         
            </h:form>
            
            
        </f:view>
        
        
        
    </body>
    
</html>
