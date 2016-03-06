<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
  
<f:view>
    <f:loadBundle
        basename="com.ecological.resource.ecologicalpaper"
        var="txt_ecolo" />
    
    <SCRIPT> 
function refrescar() 
{ 
    top.frames['leftFrame'].location.href = './arbol.jsf'; 
} 
    </SCRIPT>
    
    <HTML><HEAD><TITLE> <h:outputText value="#{txt_ecolo.multiplataforma} #{tree.user_logueado}"/></TITLE>
            <META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
            <STYLE type=text/css>BODY {
                   BACKGROUND-IMAGE: url(img/background.gif)
                   }
                   </STYLE>
            <LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
                  
              <META content="MSHTML 6.00.2900.3059" name=GENERATOR></HEAD>
        <BODY onLoad="javascript:refrescar();">
            <h:form>
                <f:loadBundle
                    basename="com.ecological.resource.ecologicalpaper"
                    var="txt" />
                <TABLE cellSpacing=1 cellPadding=0 width=750 align=center bgColor=#666666 
                       border=0>
                    <TBODY>
                        <TR>
                            <TD vAlign=top bgColor=#ffffff height=473>
                                <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                                    <TBODY>
                                        <TR>
                                            <TD vAlign=top><IMG height=317 src="bienvenido_archivos/tope.jpg" 
                                                            width=748></TD></TR>
                                        <TR>
                                            <TD height=19>
                                                <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                                                    <TBODY>
                                                        <TR>
                                                            <TD width="58%">
                                                            <DIV align=right></DIV></TD>
                                                            <TD width="20%">
                                                                <DIV align=right><BR><IMG height=95 
                                                                                              src="bienvenido_archivos/cerrojo.jpg" 
                                                                                      width=97><BR><BR><BR></DIV></TD>
                                                            <TD width="20%">
                                                                <DIV align=right><BR>
                                                                    <h:outputText styleClass="form" value="#{requestScope.usuario_nologueado}" />
                                                                    <h:messages styleClass="form"/>                  
                                                            </DIV></TD>
                                                            
                                                            
                                                            <TD width="22%">
                                                                <h:panelGrid columns="3" binding="#{backing_login.panelGrid1}"
                                                                             id="panelGrid1">
                                                                    <h:outputText styleClass="grees"  value="#{txt.usuario_login}"
                                                                                  binding="#{backing_login.outputText1}"
                                                                                  id="outputText1"/>
                                                                    <h:inputText binding="#{backing_login.inputText1}" id="inputText1"
                                                                                 required="true"
                                                                                 value="#{backing_login.usuario.login}"/>
                                                                    <h:message  styleClass="form" for="inputText1"/>
                                                                    
                                                                    <h:outputText styleClass="grees" value="#{txt.usuario_password}"
                                                                                  binding="#{backing_login.outputText2}"
                                                                                  id="outputText2"
                                                                    />
                                                                    <h:inputSecret binding="#{backing_login.inputSecret1}"
                                                                                   id="inputSecret1" required="true"
                                                                                   value="#{backing_login.usuario.password}">
                                                                        <f:validateLength maximum="8" minimum="4"/>
                                                                    </h:inputSecret>
                                                                    <h:message styleClass="form" for="inputSecret1"/>
                                                                    
                                                                    <h:outputText styleClass="grees" value="Licencia"/>
                                                                    <h:outputText styleClass="grees" value="#{numero_usuarios} #{txt.usuario_varios}"/>
                                                                    <f:verbatim></f:verbatim>
                                                                    
                                                                    <h:outputText styleClass="grees" value="#{txt.expiraen}:"/>
                                                                    <h:outputText styleClass="grees" value="#{backing_login.diasSeVence} #{txt.dias}"/>
                                                                    <f:verbatim></f:verbatim>
                                                                    
                                                                    
                                                                    
                                                                    <h:commandButton styleClass="boton" value="#{txt.btn_aceptar}"
                                                                                     binding="#{backing_login.commandButton1}"
                                                                                     id="commandButton1"
                                                                                     action="#{backing_login.login_action}"/>
                                                                    <f:verbatim></f:verbatim>
                                                                    <f:verbatim></f:verbatim>
                                                                </h:panelGrid>
                                                            </TD>
                                    </TR></TBODY></TABLE></TD></TR></TBODY>
                </TABLE></TD></TR></TBODY></TABLE>
            </h:form>
        </BODY>
    </HTML>
</f:view>

