<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%> 


<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
      <LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
      <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
          <%@include file="/inc/head.inc" %>    
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

            
            <title><h:outputText styleClass="form" value="#{txt.doc_relacionados}" /></title>
        </head>
        
        <body>
        <h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
        
            
            <h:messages id="errors" 
                        style="color:red;font-weight:bold" 
                        layout="table" />
            <h:form>
                <h:panelGrid columns="3" border="0">
                    <h:outputText styleClass="form" value="#{txt.doc_relacionados}" />
                    <f:verbatim></f:verbatim>
                    <h:outputText styleClass="form" value="#{Doc_Vinculados.doc_maestro.nombre}" />
                </h:panelGrid>
                       
                    <h:panelGrid columns="3" cellspacing="0" border="0" width="75%">        
                        <h:outputText styleClass="form" value="#{txt.doc_norelacionados}"/>
                        <f:verbatim>&nbsp;</f:verbatim>
                        <h:outputText styleClass="form" value="#{txt.doc_relacionados}"/>
                        <h:panelGroup id="slct111">
                            <h:selectManyListbox  converter="ConverDoc_Maestro"  value="#{Doc_Vinculados.selectedInvisibleItems}" size="#{Utilidades.verNumeroDeRegistros}" style="width: 300px;">
                                <f:selectItems  value="#{Doc_Vinculados.invisibleItems}" />
                            </h:selectManyListbox>
                        </h:panelGroup>
                        <h:panelGrid columns="1">
                            <h:commandButton image="/images/arrow-next.gif"   actionListener="#{Doc_Vinculados.moveSelectedToVisible}" style="width:20px;" />
                            <h:commandButton image="/images/arrow-ff.gif" actionListener="#{Doc_Vinculados.moveAllToVisible}" style="width: 20px;" />
                            <h:commandButton image="/images/arrow-fr.gif"  actionListener="#{Doc_Vinculados.moveAllToInvisible}" style="width: 20px;" />
                            <h:commandButton image="/images/arrow-previous.gif" actionListener="#{Doc_Vinculados.moveSelectedToInvisible}" style="width: 20px;" />
                        </h:panelGrid>
                        
                        <h:selectManyListbox id="slct2" converter="ConverDoc_Maestro"  value="#{Doc_Vinculados.selectedVisibleItems}" size="#{Utilidades.verNumeroDeRegistros}"  style="width: 300px;">
                            <f:selectItems  id="visibleItems" value="#{Doc_Vinculados.visibleItems}" />
                        </h:selectManyListbox>
                        
                        <f:verbatim>&nbsp;</f:verbatim>
                        <h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" 
                                          action="#{Doc_Vinculados.cancelarEditar}"/>
                        <h:commandButton styleClass="boton" value="#{txt.usuario_guardar}" 
                                          action="#{Doc_Vinculados.saveRelacionados}" 
                        />
                    </h:panelGrid>
               
            </h:form>
        </body>
    </html>
</f:view>
