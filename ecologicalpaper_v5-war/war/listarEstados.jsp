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
                
                <h:panelGrid columns="4" binding="#{backing_login.panelGrid1}"
                             id="panelGrid1">
                    <f:verbatim>&nbsp;</f:verbatim>
                    <h:commandButton  styleClass="boton" value="#{txt.btn_menu}" 
                                       action="#{CrearEstado.cancelar}"/>
                    <h:commandButton styleClass="boton" rendered="#{CrearEstado.swAdd}"
                                     value="#{txt.btn_crear}" 
                                     action="#{CrearEstado.inic_crear}"/>
                </h:panelGrid>
                
                <h:panelGroup>
                    <a4j:region renderRegionOnly="true" id="stat3">    
                        <h:outputText id="selectOneMenuPais"  styleClass="form" value="#{txt.crearestadoen}  " title="#{txt.crearestadoen}  "/>
                        <h:outputText id="selectOneMenuPais2"  styleClass="form" value="#{txt.pais}" title="#{txt.pais}"/>
                        <h:selectOneMenu 
                            id="selectPais"
                            immediate="true"
                            value="#{CrearEstado.pais}" converter="ConverPais">
                            <a4j:support event="onchange"  
                                         reRender="data"
                                         action="#{CrearEstado.colocarPaisSession}"
                            />
                            <f:selectItems value="#{DatosCombo.allPaises}" />
                        </h:selectOneMenu>
                        <h:message for="selectOneMenuPais" styleClass="form"/> 
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
                                     value="#{CrearEstado.estados}"
                                     preserveDataModel="false"
                                     rows="#{Utilidades.verNumeroDeRegistros}"
                        >
                            <h:column>
                                <f:facet name="header">
                                   <h:graphicImage styleClass="grees" title="" url="#{conftxt.img_editar}" />
                                </f:facet>
                                <h:panelGroup rendered="#{CrearEstado.swMod}">
                                    <h:commandLink  id="Edit" 
                                                    action="#{CrearEstado.edit}" 
                                                    actionListener="#{CrearEstado.selection}">
                                        <h:outputText  styleClass="node" value="#{car.nombre}" />
                                        <f:param id="editId" 
                                                 name="id" 
                                                 value="#{car.codigo}" />
                                    </h:commandLink>
                                </h:panelGroup>
                                <h:panelGroup rendered="#{!CrearEstado.swMod}">
                                    <h:outputText styleClass="node" value="#{car.nombre}"
                                                   title="#{car.nombre}"/>
                                </h:panelGroup>                                     
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText styleClass="grees"  value="#{txt.pais}" />
                                </f:facet>
                                <h:outputText styleClass="node" value="#{car.pais.nombre}" />
                            </h:column>
                            
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:graphicImage  styleClass="grees" 
                                        title="#{txt.btn_erase}"
                                        url="#{conftxt.img_erase}" /> 
                                    
                                </f:facet>
                                <h:panelGroup rendered="#{car.delete}">
                                    <h:commandLink  id="del" 
                                                    action="#{CrearEstado.delete}" 
                                                    actionListener="#{CrearEstado.selection}"
                                                    onclick="if (!confirm('Are you sure you want to delete this event?')) return false">
                                        >
                                        <h:graphicImage   styleClass="node"
                                            title="#{txt.btn_erase}"
                                            url="#{conftxt.img_erase}" /> 
                                        
                                        <f:param id="editId" 
                                                 name="id" 
                                                 value="#{car.codigo}" />
                                    </h:commandLink>
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
                        
                        
                        
                    </a4j:region>                 
                </h:panelGroup>
                
            </h:form>
            
            
        </f:view>
        
        
        
    </body>
    
</html>
