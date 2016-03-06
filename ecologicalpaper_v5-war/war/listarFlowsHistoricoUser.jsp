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
            <h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
            
            
            <!-- 
            CUERPO DEL MENSAJE 
            -->
            <h:form>
                <a4j:region renderRegionOnly="true" id="stat3"> 
               
                    <h:panelGrid columns="2" 
                                 id="panelGrid121">
                        
                        <h:panelGroup>
                            <t:tree2 id="serverTree" value="#{Historico.treeFlowData}" var="node" 
                                     varNodeToggler="t" clientSideToggle="false">
                                <f:facet name="-1">
                                    <h:panelGroup>
                                        <h:commandLink immediate="true" 
                                                       styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
                                                       action="#{Historico.nodeClicked}"
                                                       actionListener="#{Historico.processFlowTipoAction}">
                                            <t:graphicImage value="#{conftxt.img_raiz}" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="#{conftxt.img_raiz}" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <f:param name="docNum" value="#{node.identifier}"/>
                                            <a4j:support  event="onchange" reRender="data" />
                                        </h:commandLink>
                                        
                                    </h:panelGroup>
                                </f:facet>
                                <f:facet name="1">
                                    <h:panelGroup>
                                        <h:commandLink immediate="true" 
                                                       styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
                                                       action="#{Historico.nodeClicked}"
                                                       actionListener="#{Historico.processFlowTipoAction}">
                                            <t:graphicImage value="#{conftxt.img_raiz}" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="#{conftxt.img_raiz}" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <f:param name="docNum" value="#{node.identifier}"/>
                                            <a4j:support  event="onchange" reRender="data" />
                                        </h:commandLink>
                                        
                                    </h:panelGroup>
                                </f:facet>
                                <f:facet name="2">
                                    <h:panelGroup>
                                        <h:commandLink immediate="true" 
                                                       styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
                                                       action="#{Historico.nodeClicked}"
                                                       actionListener="#{Historico.processFlowTipoAction}">
                                            <t:graphicImage value="#{conftxt.img_raiz}" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="#{conftxt.img_raiz}" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <f:param name="docNum" value="#{node.identifier}"/>
                                            <a4j:support  event="onchange" reRender="data" />
                                        </h:commandLink>
                                        
                                    </h:panelGroup>
                                </f:facet>
                            </t:tree2>
                        </h:panelGroup>
                        
                        <h:panelGroup>
                            
                            <h1><h:outputText styleClass="form"   value="#{txt.toHistFlow}"/></h1>
                            <h:panelGrid columns="2">
                                <h:panelGroup>
                                     <h:outputText styleClass="form"  value="#{txt.desde}"/>
                                    <t:inputCalendar  id="fecha_creado" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                                     immediate="true" 
                                                     currentDayCellClass="currentDayCell" value="#{Historico.fecha_creado}" renderAsPopup="true"
                                                     popupTodayString="#{txt.calendar_fecha}"
                                                     popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                                     helpText="dd.MM.yyyy"
                                                     forceId="true"
                                    >
                                        <a4j:support event="onchange" reRender="data" />
                                        <a4j:status for="stat3" stopText=" ">
                                            <f:facet name="start">
                                                <h:graphicImage value="#{conftxt.img_reloj}" />
                                            </f:facet>
                                        </a4j:status>
                                    </t:inputCalendar>
                                    <h:outputText styleClass="form"  value="#{txt.hasta}"/>
                                    <t:inputCalendar 
                                                    immediate="true" 
                                                     id="fechaFin" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                                     currentDayCellClass="currentDayCell" value="#{Historico.fecha_creadoFin}" renderAsPopup="true"
                                                     popupTodayString="#{txt.calendar_fecha}"
                                                     popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                                     helpText="dd.MM.yyyy"
                                                     forceId="true"
                                    >
                                        <a4j:support event="onchange" reRender="data" />
                                        <a4j:status for="stat3" stopText=" ">
                                            <f:facet name="start">
                                                <h:graphicImage value="#{conftxt.img_reloj}" />
                                            </f:facet>
                                        </a4j:status>
                                    </t:inputCalendar>
                                </h:panelGroup>
                                
                            </h:panelGrid>
                            <h:panelGroup id="body">
                                <t:dataTable id="data"
                                             styleClass="scrollerTable"
                                             headerClass="standardTable_Header"
                                             footerClass="standardTable_Header"
                                             rowClasses="standardTable_Row1,standardTable_Row2"
                                             var="car"
                                             value="#{Historico.flowParaleloUser}"
                                             preserveDataModel="false"
                                             rows="#{Utilidades.verNumeroDeRegistros}"
                                >
                                  
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText styleClass="grees" value="#{txt.nombredelflujo}" />
                                        </f:facet>
                                        <h:outputText styleClass="node" value="#{car.nombre}" />
                                    </h:column>
                                  
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText styleClass="grees"  value="#{txt.flow}" />
                                        </f:facet>
                                        <h:commandLink  id="Edit" 
                                                        action="#{FlowsHistorico.viewDocDetalleFlowHistorico}" 
                                                        actionListener="#{FlowsHistorico.versionId}">
                                            <h:outputText  styleClass="node"  value="[ #{car.nombre}(#{car.flow.nombredelflujo}) ]  " />
                                            <f:param id="editId2" 
                                                     name="id" 
                                                     value="#{car.flow.codigo}" />
                                            <f:param id="editId3" 
                                                     name="id2" 
                                                     value="#{car.flow.doc_detalle.codigo}" />
                                        </h:commandLink>
                                    </h:column>
                                    
                                  
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText styleClass="grees"  value="#{txt.flow_fechacreado}" />
                                        </f:facet>
                                        <h:outputText styleClass="node" value="#{car.fechaCreado}" />
                                    </h:column>
                                    
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText styleClass="grees" value="#{txt.autor_flujo}" />
                                        </f:facet>
                                        <h:outputText styleClass="node" value="#{car.flow.duenio.nombre} #{car.flow.duenio.apellido} [#{car.flow.duenio.cargo.nombre}]" />
                                    </h:column>
                                    
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText styleClass="grees"  value="" />
                                        </f:facet>
                                        <h:outputText styleClass="node" value="#{car.statusEnQedo}" />
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
                                        <h:outputFormat styleClass="form" value="#{example_messages['dataScroller_pages']}" styleClass="standard" >
                                            <f:param value="#{rowsCount}" />
                                            <f:param value="#{displayedRowsCountVar}" />
                                            <f:param value="#{firstRowIndex}" />
                                            <f:param value="#{lastRowIndex}" />
                                            <f:param value="#{pageIndex}" />
                                            <f:param value="#{pageCount}" />
                                        </h:outputFormat>
                                    </t:dataScroller>
                                </h:panelGrid>
                                
                                <h:panelGrid columns="3"  
                                             id="panelGrid1">
                                    <f:verbatim>&nbsp;</f:verbatim>
                                    <h:commandButton styleClass="boton" value="#{txt.btn_cancelar}"
                                                      action="#{ClienteUsuario.cancelarEditUser}"/>
                                    
                                </h:panelGrid>
                                
                                
                            </h:panelGroup>
                            
                            
                        </h:panelGroup>
                        
                    </h:panelGrid>    
                </a4j:region>
            </h:form>  
        </f:view>
        
        
        
    </body>
    
</html>
