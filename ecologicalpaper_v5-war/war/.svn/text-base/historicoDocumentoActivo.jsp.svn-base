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
    <body>
        <f:view>
            <%@include file="/inc/head.inc" %>
            <f:loadBundle
                basename="com.ecological.resource.ecologicalpaper"
                var="txt" />
            <f:loadBundle
                basename="com.util.resource.ecological_conf"
                var="conftxt" />
            <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" 
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
                <h:panelGrid columns="1"  
                             id="panelGrid1">
                    
                    <h:commandButton styleClass="boton"  value="#{txt.btn_menu}" 
                                       action="#{Documento.listarMenu}"/>
                    
                    
                    
                </h:panelGrid>
                <h1><h:outputText styleClass="form"   value="#{txt.doc_hist}"/></h1>
                
                
                
                <h:panelGroup id="body">
                    <h:panelGrid columns="2">  
                        <!-- Expand/Collapse Handled By Server -->
                        <t:tree2 id="serverTree" value="#{Documento.treeData}" var="node" varNodeToggler="t" clientSideToggle="false">
                            <f:facet name="identificaRaizTree">
                                <h:panelGroup>
                                    <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                    <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                    <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica1Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica2Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            
                            <f:facet name="identifica3Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica4Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica5Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica6Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica7Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica8Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <f:facet name="identifica9Tree">
                                <h:panelGroup>
                                    <h:commandLink immediate="true" 
                                                   styleClass="#{t.nodeSelected ? 'documentSelected':'document'}">
                                        <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                        <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                        <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        <f:param name="docNum" value="#{node.identifier}"/>
                                    </h:commandLink>
                                </h:panelGroup>
                            </f:facet>
                            <!-- <%/* <f:facet name="identificaCargoTree">
                            <h:panelGroup>
                            <h:commandLink immediate="true" styleClass="#{t.nodeSelected ? 'documentSelected':'document'}" 
                            action="#{CrearCargos.nodeClicked}"
                            actionListener="#{CrearCargos.processAction}">
                            <t:graphicImage value="/images/document.png" border="0"/>
                            <h:outputText value="#{node.description}"/>
                            <f:param name="docNum" value="#{node.identifier}"/>
                            </h:commandLink>
                            </h:panelGroup>
                            </f:facet>*/%>-->
                        </t:tree2>
                        
                    </h:panelGrid>           
                    
                    
                </h:panelGroup>
                
            </h:form>
            
            
            
            
        </f:view>
        
        
        
    </body>
    
</html>