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
            
            <!-- 
            CUERPO DEL MENSAJE 
            -->

            <f:loadBundle
                basename="com.ecological.resource.ecologicalpaper"
                var="txt" />
                <h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
                
            <title><h:outputText styleClass="form" value="#{txt.creardiasferiado}" /></title>
            <h1></h1>
            <center><h2><h:outputText styleClass="form" value="#{txt.creardiasferiado}"/></h2></center>
            <p></p>
            
            <h:form id="forma">
                <h:panelGrid columns="3" 
                             id="panelGrid1">
                    <h:outputText styleClass="form"  value="#{txt.doc_creado}"/>
                    <t:inputCalendar id="fecha_creado" monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader" popupButtonStyleClass="standard_bold"
                                     currentDayCellClass="currentDayCell" value="#{DiasFeriadosCliente.diasFeriadosBean.fechaonly}" renderAsPopup="true"
                                     popupTodayString="#{txt.calendar_fecha}"
                                     popupDateFormat="dd.MM.yyyy" popupWeekString="#{txt.popup_week_string}"
                                     helpText="dd.MM.yyyy"
                                     forceId="true"
                    />
                    <h:message for="fecha_creado"  styleClass="form"/>
                    
                    <h:outputLabel styleClass="form" value="#{txt.doc_descripcion}" 
                    />
                    <h:inputText   id="inputTextDescripcion" size="50"
                                   value="#{DiasFeriadosCliente.diasFeriadosBean.descripcion}" />
                    <h:message styleClass="form" for="inputTextDescripcion" />
                    
                    <h:message styleClass="form" for="btncancel" />
                    <h:commandButton styleClass="boton" id="btncancel"  value="#{txt.btn_cancelar}"
                                     action="#{DiasFeriadosCliente.cancelar}"
                                     
                    />
                    
                    <h:commandButton styleClass="boton" value="#{txt.btn_guardar}"
                                      action="#{DiasFeriadosCliente.create}"
                                      
                    />
                    
                </h:panelGrid>
            </h:form>
        </f:view>
    </body>
</html>
