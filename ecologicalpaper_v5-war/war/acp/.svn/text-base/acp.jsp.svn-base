<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 

<LINK href="../bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
      <LINK href="../tree/dtree.css" type=text/css rel=stylesheet>
      <script type="text/javascript" src="../tree/dtree.js"></script>

<html>
    <%@include file="../inc/head.inc" %>
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
            
            
            
            
            <h:form id="forma">
                
                
                <h:panelGroup id="btones">
                    <h:panelGrid columns="2"  
                                 id="panelGrid2">
                        <h:panelGroup>
                            <h:panelGrid columns="1">
                                <!-- Expand/Collapse Handled By Server -->
                                <t:tree2 id="serverTree" value="#{treeBacker.treeData}" var="node" varNodeToggler="t" clientSideToggle="false">
                                    <f:facet name="person">
                                        <h:panelGroup>
                                            <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="foo-folder">
                                        <h:panelGroup>
                                            <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="bar-folder">
                                        <h:panelGroup>
                                            <t:graphicImage value="/images/blue-folder-open.gif" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="/images/blue-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="document">
                                        <h:panelGroup>
                                            <h:commandLink immediate="true" styleClass="#{t.nodeSelected ? 'documentSelected':'document'}" actionListener="#{t.setNodeSelected}">
                                                <t:graphicImage value="/images/document.png" border="0"/>
                                                <h:outputText value="#{node.description}"/>
                                                <f:param name="docNum" value="#{node.identifier}"/>
                                            </h:commandLink>
                                        </h:panelGroup>
                                    </f:facet>
                                </t:tree2>
                                
                            </h:panelGrid>
                            <h:panelGrid columns="1">
                                <!-- Expand/Collapse Handled By Server -->
                                <t:tree2 id="serverTree2" value="#{treeBacker.treeData}" var="node" varNodeToggler="t" clientSideToggle="false">
                                    <f:facet name="person">
                                        <h:panelGroup>
                                            <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="foo-folder">
                                        <h:panelGroup>
                                            <t:graphicImage value="/images/yellow-folder-open.png" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="/images/yellow-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="bar-folder">
                                        <h:panelGroup>
                                            <t:graphicImage value="/images/blue-folder-open.gif" rendered="#{t.nodeExpanded}" border="0"/>
                                            <t:graphicImage value="/images/blue-folder-closed.png" rendered="#{!t.nodeExpanded}" border="0"/>
                                            <h:outputText value="#{node.description}" styleClass="nodeFolder"/>
                                            <h:outputText value=" (#{node.childCount})" styleClass="childCount" rendered="#{!empty node.children}"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <f:facet name="document">
                                        <h:panelGroup>
                                            <h:commandLink immediate="true" styleClass="#{t.nodeSelected ? 'documentSelected':'document'}" actionListener="#{t.setNodeSelected}">
                                                <t:graphicImage value="/images/document.png" border="0"/>
                                                <h:outputText value="#{node.description}"/>
                                                <f:param name="docNum" value="#{node.identifier}"/>
                                            </h:commandLink>
                                        </h:panelGroup>
                                    </f:facet>
                                </t:tree2>
                            </h:panelGrid>
                        </h:panelGroup>
                        
                        <h:panelGroup>
                            <h:panelGrid  columns="3">
                              
                            </h:panelGrid>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGroup>
                
            </h:form>
        </f:view>
    </body>
</html>
