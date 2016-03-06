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
                
                	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
                
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
                <h1><h:outputText styleClass="form"   value="#{txt.toHistFlow}"/></h1>
                
                <h:panelGroup id="body">
                    
                    <t:dataTable id="data"
                                 styleClass="scrollerTable"
                                 headerClass="standardTable_Header"
                                 footerClass="standardTable_Header"
                                 rowClasses="standardTable_Row1,standardTable_Row2"
                                 columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                                 var="car"
                                 value="#{FlowsHistorico.flowsHistorico}"
                                 preserveDataModel="false"
                                 rows="#{Utilidades.verNumeroDeRegistros}"
                    >
                        <h:column>
                            <f:facet name="header">
                               <h:graphicImage styleClass="grees" title="" url="#{conftxt.img_editar}" />
                            </f:facet>
                            
                            <h:commandLink  id="Edit" 
                                            action="#{FlowsHistorico.viewDocDetalleFlowHistorico}" 
                                            actionListener="#{FlowsHistorico.versionId}">
                                <h:outputText styleClass="node" value="#{car.doc_detalle.doc_maestro.nombre}" />
                                <f:param id="editId2" 
                                         name="id" 
                                         value="#{car.flow.codigo}" />
                                <f:param id="editId3" 
                                         name="id2" 
                                         value="#{car.doc_detalle.codigo}" />
                            </h:commandLink>
                            
                        </h:column>
                          <h:column>
                            <f:facet name="header">
                                <h:outputText styleClass="grees" value="#{txt.flow_tipos}" />
                            </f:facet>
                            <h:outputText styleClass="node" value="#{car.tipoFlujo}" />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText styleClass="grees" value="#{txt.flow_fechacreado}" />
                            </f:facet>
                            <h:outputText styleClass="node" value="#{car.fechaCreado}" />
                        </h:column>
                       
                        <h:column>
                            <f:facet name="header">
                                <h:outputText  styleClass="grees" value="#{txt.autor_flujo}" />
                            </f:facet>
                            <h:outputText styleClass="node" value="#{car.flow.duenio.nombre} #{car.flow.duenio.apellido} [#{car.flow.duenio.cargo.nombre}]" />
                        </h:column>
                        
                          <h:column>
                            <f:facet name="header">
                                <h:outputText styleClass="grees" value="" />
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
                                         action="#{Documento.regresarVerDocumento}"/>
                        
                    </h:panelGrid>
                    
                    
                </h:panelGroup>
                
            </h:form>
            
            
        </f:view>
        
        
        
    </body>
    
</html>
