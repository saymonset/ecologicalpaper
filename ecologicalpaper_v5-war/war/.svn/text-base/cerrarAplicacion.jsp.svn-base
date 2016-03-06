
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
    <body onload="cerrar()" >
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
                
            <h:form>
                <h:inputHidden immediate="true" value="#{backing_login.goSalir}"></h:inputHidden>                
                  <h:outputText styleClass="form" value="#{txt.aplicacion_cerrada}"/>
                  
                   
                     
            </h:form>
        </f:view>
        <!-- 
        <% /*
 
        <jsp:forward page="./index.jsp" />
         *
        <jsp:forward page="./bienvenido.jsf" />
 
         */ %> 
            CUERPO DEL MENSAJE 
            -->
    </body>
</html>


<script type="text/javascript">
function cerrar(){
   
    top.frames['leftFrame'].location.href = './cerrarAplicacionArbol.jsf'; 
   //  window.opener=''; window.close();
   window.open("./bienvenidoUsuarioPagPrincipal.jsf");

   	 //top.frames['mainFrame'].location.href = './bienvenidousuario.jsf';
  //  
   // window.opener=''; window.close();
    //top.frames['mainFrame'].close();
}
</script>
