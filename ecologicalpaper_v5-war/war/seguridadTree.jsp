<%@ page import="java.math.BigDecimal,
java.util.Date"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>


  
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
    <%@include file="inc/head.inc" %>
    
    <script type="text/javascript"   src="./validacione.js"></script>
    <body onLoad="javascript:refrescar();">  
        <f:view>
            <h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
            
            
            <t:saveState id="ss1" value="#{tabbedPaneBean}" />
            
            <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>
            <f:loadBundle
                basename="com.ecological.resource.ecologicalpaper"
                var="txt" />
            
            
            <f:subview id="panelTabbedPane2">
                
                
                <t:panelTabbedPane bgcolor="#CCFFFF" serverSideTabSwitch="true">
                    
                    <f:verbatim><p></f:verbatim>
                    <h:outputText  value="#{txt.menu_conf} #{Seguridad_tab1.tree.nombre}"/>
                    <f:verbatim></f:verbatim>
                    
                    <f:subview id="tab1" >
                     <%/*   <t:panelTab  disabled="#{!Seguridad_tab1.swPrincipalVisible}"
                                     
                                     label="#{txt.menu_conf1}"
                                     rendered="#{Seguridad_tab1.tab1Visible}">
                            <jsp:include page="seguridadTreeTab1.jsp"/>
                        </t:panelTab>*/%>
                         <t:panelTab  disabled="#{!Seguridad_tab1.swPrincipalVisible}"
                                     
                                     label="#{txt.menu_conf1}"
                                     rendered="true">
                            <jsp:include page="seguridadTreeTab1.jsp"/>
                        </t:panelTab>
                    </f:subview>
                    <f:subview id="tab2" >
                        <t:panelTab disabled="#{Seguridad_tab1.swRoleVisible}"   label="#{txt.menu_conf2}" 
                                    
                                    rendered="#{Seguridad_tab1.tab2Visible}">
                            <jsp:include page="seguridadTreeTab2.jsp"/>
                        </t:panelTab>
                    </f:subview>
                    
                    <f:subview id="tab3" >
                        <t:panelTab  disabled="#{Seguridad_tab1.swUserVisible}"  id="tab3" label="#{txt.menu_conf3}"
                                   
                                     rendered="#{Seguridad_tab1.tab3Visible}">
                            <jsp:include page="seguridadTreeTab3.jsp"/>        
                        </t:panelTab>
                    </f:subview>
                    
                    
                    
                  
                    
                </t:panelTabbedPane>
            </f:subview>
            
        </f:view>
        
        
    </body>
    
</html>
