<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>


<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 
  
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
      <LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
      <SCRIPT> 
       function delete(TodoElObjeto){
           if (confirm('<h:outputText value="#{txt.seguroeliminar}"/>')) {
              return true;
           }else{
              return false;
           }
         }

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
                <h:panelGrid columns="1" binding="#{backing_login.panelGrid1}"
                             id="panelGrid1">
                    
                    <h:commandButton styleClass="boton"  value="#{txt.btn_menu}" 
                                       action="#{listarControlFlowByTime.cancelarListar}"/>
                    
                    <h:outputText styleClass="form" value="#{listarControlFlowByTime.fechaActualServidor}"/>
                    <h:outputText styleClass="form" value="#{listarControlFlowByTime.diaDeLaSemanaServidor}"/>
                    <h:outputText styleClass="form" value="#{listarControlFlowByTime.horaServidior}"/>
                </h:panelGrid>
                <h1><h:outputText styleClass="form"   value="#{txt.asignacio_horas}"/></h1>
                <a4j:region renderRegionOnly="true" id="stat3"> 
                    <h:panelGroup id="body">
                        
                        <t:dataTable id="data"
                                     styleClass="scrollerTable"
                                     headerClass="standardTable_Header"
                                     footerClass="standardTable_Header"
                                     rowClasses="standardTable_Row1,standardTable_Row2"
                                     columnClasses="standardTable_Column,standardTable_ColumnCentered,standardTable_Column"
                                     var="car"
                                     value="#{listarControlFlowByTime.flowControlByUsuarioBeans}"
                                     preserveDataModel="false"
                                     rows="#{Utilidades.verNumeroDeRegistros}"
                        >
                            <h:column>
                                <f:facet name="header">
                                  <h:graphicImage styleClass="grees" title="" url="#{conftxt.img_editar}" />
                                </f:facet>
                                <h:panelGroup rendered="#{!car.swEsRole}">
                            <%/*        <h:panelGroup rendered="#{listarControlFlowByTime.swMod}">*/%>
                                        <h:commandLink  id="Edit" 
                                                        action="#{listarControlFlowByTime.edit}" 
                                                        actionListener="#{listarControlFlowByTime.select}">
                                            <h:outputText  styleClass="node"
                                                value="#{car.flow_Participantes.participante.apellido}
                                                #{car.flow_Participantes.participante.nombre}
                                                [#{car.flow_Participantes.participante.cargo.nombre}]" />
                                            <f:param id="editId" 
                                                     name="id" 
                                                     value="#{car.codigo}" />
                                        </h:commandLink>
                                    <%/*</h:panelGroup>
                                    <h:panelGroup rendered="#{!listarControlFlowByTime.swMod}">
                                        <h:outputText 
                                            value="#{car.flow_Participantes.participante.apellido}
                                            #{car.flow_Participantes.participante.nombre}
                                            [#{car.flow_Participantes.participante.cargo.nombre}]" />
                                    </h:panelGroup>                       */%>
                                </h:panelGroup>
                                
                                <h:panelGroup rendered="#{car.swEsRole}">
                                <%/*    <h:panelGroup rendered="#{listarControlFlowByTime.swMod}">*/%>
                                        <h:commandLink   
                                            action="#{listarControlFlowByTime.edit}" 
                                            actionListener="#{listarControlFlowByTime.select}">
                                            <h:outputText 
                                                value="#{car.role.nombre}" />
                                            <f:param id="editId2" 
                                                     name="id2" 
                                                     value="#{car.codigo}" />
                                        </h:commandLink>
                               <%/*     </h:panelGroup>
                                    <h:panelGroup rendered="#{!listarControlFlowByTime.swMod}">
                                        <h:outputText 
                                            value="#{car.role.nombre}" />
                                    </h:panelGroup>        */%>               
                                </h:panelGroup>
                                
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText   styleClass="grees" value="#{txt.asignadas_horas}" />
                                </f:facet>
                                <h:outputText   styleClass="node"
                                    value="#{car.horasAsignadas}" />
                            </h:column>
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText   styleClass="grees" value="#{txt.asignadas_minutos}" />
                                </f:facet>
                                <h:outputText   styleClass="node"
                                    value="#{car.minutosAsignados}" />
                            </h:column>
                            
                        <!--<%/*    <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.enviar_mails}" />
                                </f:facet>
                                <h:outputText  rendered="#{car.envMailRemember}"
                                               value="#{txt.si}" />
                                <h:outputText  rendered="#{!car.envMailRemember}"
                                               value="#{txt.no}" />
                            </h:column>
                            
                            
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText  value="#{txt.hora_antes_envia_mail}" />
                                </f:facet>
                                <h:outputText 
                                    value="#{car.hrsAntesToEnvMailRemember}" />
                            </h:column>*/%>-->
                            
                            
                            
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
