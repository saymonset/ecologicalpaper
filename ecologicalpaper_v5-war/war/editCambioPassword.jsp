<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<%@taglib uri="http://java.sun.com/jsf/core" prefix="c"%>


<%@include file="/inc/head.inc"%>
<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>
<LINK href="./tree/dtree.css" type=text/css rel=stylesheet>
<f:view>
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt" />
	<f:loadBundle
		basename="org.apache.myfaces.examples.resource.example_messages"
		var="example_messages" />
	<f:loadBundle basename="com.util.resource.ecological_conf"
		var="conftxt" />
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

	<script type="text/javascript" src="./validacione.js"></script>

	<title></title>
	</head>
	<script>
            var formId; // reference to the main form
		var winId;	// reference to the popup window


		// This function calls the popup window.
		//
		function showPlaceList(action,title) {
		 	features="height=330,width=450,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes";			
			winId=window.open(action,title,features); // open an empty window
		}
		
            
            function validarCampos(TodoElObjeto){
               //obtenemos el objeto de la forma
                forma = TodoElObjeto.form;
                //obtenemos el objetdo de la forma nombrado por el id y su campo
                valor=forma[forma.id+":nombre"]
                value=valor.value;
                if (stringVacio(value)){
                alert('<h:outputText value="#{txt.usuario_nombre}"/>');
                return false;
                
                }
                
                valor=forma[forma.id+":apellido"]
                value=valor.value;
                if (stringVacio(value)){
                alert('<h:outputText value="#{txt.usuario_apellido}"/>');
                return false;
                }

                valor=forma[forma.id+":mail"]
                value=valor.value;
                if (isbadMail(value)){
                     alert('<h:outputText value="#{txt.usuario_mail}"/>');
                     return false;
                }
                
                valor=forma[forma.id+":login"]
                value=valor.value;
                if (stringVacio(value)){
                alert('<h:outputText value="#{txt.usuario_login}"/>');
                return false;
                }

                valor=forma[forma.id+":password"]
                value=valor.value;
                if (stringVacio(value)){
                    alert('<h:outputText value="#{txt.usuario_password}"/>');
                    return false;
                }
          
            return true;
            }
            
        </script>

	<body>



	<f:verbatim>

	</f:verbatim>



	<h:messages />
	<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>
	

	<center>
	<h2><h:outputText styleClass="form" value="#{txt.usuario_edit}" /></h2>
	</center>
	<h:form id="frmCrear">
		<h:panelGroup>

			<h:panelGrid columns="3" id="panelGrid1">
				<h:outputText styleClass="form" value="#{txt.usuario_nombre} * " />
				<h:inputText id="nombre"
					value="#{ClienteUsuarioPassword.usuario.nombre}"
					title="#{txt.usuario_nombre}" immediate="false">
					<f:validateLength maximum="40" minimum="4" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="nombre" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_apellido} * " />
				<h:inputText id="apellido"
					value="#{ClienteUsuarioPassword.usuario.apellido}"
					title="#{txt.usuario_Apellido}" immediate="false">
					<f:validateLength maximum="40" minimum="4" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="apellido" styleClass="form" />


				<h:outputText styleClass="form" value="#{txt.usuario_fechab}" />
				<t:inputCalendar disabled="#{ClienteUsuarioPassword.swActivo}"
					id="fecha_caduca" onkeyup="borraCaracter(this)"
					monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
					popupButtonStyleClass="standard_bold"
					currentDayCellClass="currentDayCell"
					value="#{ClienteUsuarioPassword.usuario.fecha_caduca}"
					renderAsPopup="true" popupTodayString="#{txt.calendar_fecha}"
					popupDateFormat="MM/dd/yyyy"
					popupWeekString="#{txt.popup_week_string}" helpText="MM/DD/YYYY"
					forceId="true" />
				<h:message for="fecha_caduca" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_mail} * " />
				<h:inputText id="mail"
					value="#{ClienteUsuarioPassword.usuario.mail_principal}"
					title="#{txt.usuario_mail}">
				</h:inputText>
				<h:message for="mail" styleClass="form" />


				<h:outputText styleClass="form" value="#{txt.usuario_telefono_ofic}" />
				<h:inputText id="telefono_ofic"
					value="#{ClienteUsuarioPassword.usuario.telefono_ofic}"
					title="#{txt.usuario_telefono_ofic}" immediate="false">
					<f:validateLength maximum="40" minimum="0" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="telefono_ofic" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_telefono_casa}" />
				<h:inputText id="telefono_casa"
					value="#{ClienteUsuarioPassword.usuario.telefono_casa}"
					title="#{txt.usuario_telefono_casa}" immediate="false">
					<f:validateLength maximum="40" minimum="0" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="telefono_casa" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_telefono_cel}" />
				<h:inputText id="telefono_cel"
					value="#{ClienteUsuarioPassword.usuario.telefono_cel}"
					title="#{txt.usuario_telefono_cel}" immediate="false">
					<f:validateLength maximum="40" minimum="0" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="telefono_cel" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_direccion}" />
				<h:inputTextarea id="desc2" cols="40" rows="3"
					value="#{ClienteUsuarioPassword.usuario.direccion}">
					<f:validateLength maximum="3500" minimum="0" />
				</h:inputTextarea>
				<h:message for="desc2" styleClass="form" />


				<h:outputText id="selectOneMenuProfesion" styleClass="form"
					value="#{txt.usuario_profesion}" title="#{txt.usuario_profesion}" />
				<h:selectOneMenu value="#{ClienteUsuarioPassword.usuario.profesion}"
					converter="ConverProfesion">
					<f:selectItems value="#{ClienteUsuarioPassword.allProfesion}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuProfesion" styleClass="form" />


				<h:outputText styleClass="form"
					value="#{txt.usuario_empresa} [#{ClienteUsuarioPassword.usuario.empresa.nombre}] "
					title="#{txt.usuario_empresa}" />
				<h:selectOneMenu id="selectOneMenuEmpresa"
					disabled="#{ClienteUsuarioPassword.swActivo}" immediate="true"
					binding="#{ClienteUsuarioPassword.selectEmpresa}"
					value="#{ClienteUsuarioPassword.usuario.empresa}"
					converter="ConverTree">

					<a4j:support event="onchange" ajaxSingle="true"
						reRender="selectOneMenuPrincipal,selectOneMenuArea,selectOneMenuCargo"
						action="#{ClienteUsuarioPassword.changePrincipalP}" />

					<f:selectItems value="#{ClienteUsuarioPassword.allEmpresas}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuEmpresa" styleClass="form" />

				<h:outputText styleClass="form"
					value="#{txt.flow_commentPrincipal} [#{ClienteUsuarioPassword.usuario.principal.nombre}] "
					title="#{txt.flow_commentPrincipal}" />
				<h:selectOneMenu id="selectOneMenuPrincipal"
					disabled="#{ClienteUsuarioPassword.swActivo}" immediate="true"
					binding="#{ClienteUsuarioPassword.selectPrincipal}"
					value="#{ClienteUsuarioPassword.usuario.principal}"
					converter="ConverTree">
					<a4j:support event="onchange" ajaxSingle="true"
						reRender="selectOneMenuArea,selectOneMenuCargo"
						action="#{ClienteUsuarioPassword.changeAreaP}" />
					<f:selectItems value="#{ClienteUsuarioPassword.allPrincipal}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuPrincipal" styleClass="form" />

				<h:outputText styleClass="form"
					value="#{txt.usuario_area} [#{ClienteUsuarioPassword.usuario.area.nombre}] "
					title="#{txt.usuario_area}" />

				<h:selectOneMenu id="selectOneMenuArea"
					disabled="#{ClienteUsuarioPassword.swEditarUsuario}"
					immediate="true" disabled="#{ClienteUsuarioPassword.swActivo}"
					value="#{ClienteUsuarioPassword.usuario.area}"
					converter="ConverTree">
					<a4j:support event="onchange" ajaxSingle="true"
						reRender="selectOneMenuCargo"
						action="#{ClienteUsuarioPassword.changeCargos}" />
					<f:selectItems value="#{ClienteUsuarioPassword.allAreas}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuArea" styleClass="form" />




				<h:outputText styleClass="form"
					value="#{txt.usuario_cargo} [ #{ClienteUsuarioPassword.usuario.cargo.nombre} ]"
					title="#{txt.usuario_cargo}" />

				<h:selectOneMenu disabled="#{ClienteUsuarioPassword.swActivo}"
					id="selectOneMenuCargo"
					value="#{ClienteUsuarioPassword.usuario.cargo}"
					converter="ConverTree">

					<f:selectItems value="#{ClienteUsuarioPassword.allCargos}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuCargo" styleClass="form" />


				<h:outputText styleClass="form" value="#{txt.usuario_login} *" />
				<h:inputText id="login"
					disabled="#{ClienteUsuarioPassword.swEditarUsuario}"
					value="#{ClienteUsuarioPassword.usuario.login}"
					title="#{txt.usuario_login}" immediate="false">
	    			<f:validator validatorId="caracteresinvalidos" />
					<f:validateLength maximum="15" minimum="4" />
				</h:inputText>
				<h:message for="login" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_passwordnew}" />
				<h:inputSecret id="password"
					value="#{ClienteUsuarioPassword.usuario.password}"
					title="#{txt.usuario_passwordnew}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
					<f:validateLength minimum="4" />
				</h:inputSecret>
				<h:message for="password" styleClass="form" />

				<h:outputText styleClass="form"
					value="#{txt.usuario_passwordconfirm}" />
				<h:inputSecret id="passwordnew"
					value="#{ClienteUsuarioPassword.passwordConfirm}"
					title="#{txt.usuario_passwordconfirm}">
					<f:validateLength minimum="4" />
				</h:inputSecret>
				<h:inputHidden id="passwordOculta"
					value="#{ClienteUsuarioPassword.passwordOculta}" />

				<f:verbatim />
				<f:verbatim />
				<f:verbatim />

				<h:outputText styleClass="form" value="#{txt.flows_trab_doc}" />
				<h:commandButton id="find" image="#{conftxt.img_grafico}"
				styleClass="boton"
					immediate="true"
					onmousedown="showPlaceList('UsuarioFlowStadisticasGraficar.jsf?codigo=#{ClienteUsuarioPassword.usuario.id}','')"
					onclick="return false">

				</h:commandButton>
				<h:message for="find" styleClass="form" />

				<f:verbatim />
				<h:panelGroup>

					<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" immediate="true"
						action="#{ClienteUsuarioPassword.cancelarEditUser}" /> 

					<h:commandButton styleClass="boton" rendered="#{!ClienteUsuarioPassword.swHistorico}"
						value="#{txt.usuario_guardar}"
						action="#{ClienteUsuarioPassword.saveEditUser}" />
				</h:panelGroup>
				<f:verbatim />
			</h:panelGrid>


			<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">
				<%
					/*      <h:outputText styleClass="form" value="#{txt.Operaciones_noSelec}"/>
																								 <f:verbatim>&nbsp;</f:verbatim>
																								 <h:outputText styleClass="form" value="#{txt.Operaciones_Selec}"/>
																								
																								 <h:selectManyListbox converter="ConverRoles"  value="#{ClienteUsuarioPassword.selectedInvisibleItems}" size="5" style="width: 300px;">
																								 <f:selectItems  value="#{ClienteUsuarioPassword.invisibleItems}" />
																								 </h:selectManyListbox>
																								
																								 <h:panelGrid columns="1">
																								 <h:commandButton disabled="#{ClienteUsuarioPassword.swActivo}" image="/images/arrow-next.gif"   actionListener="#{ClienteUsuarioPassword.moveSelectedToVisible}" style="width:20px;" />
																								 <h:commandButton disabled="#{ClienteUsuarioPassword.swActivo}" image="/images/arrow-ff.gif" actionListener="#{ClienteUsuarioPassword.moveAllToVisible}" style="width: 20px;" />
																								 <h:commandButton disabled="#{ClienteUsuarioPassword.swActivo}" image="/images/arrow-fr.gif"  actionListener="#{ClienteUsuarioPassword.moveAllToInvisible}" style="width: 20px;" />
																								 <h:commandButton disabled="#{ClienteUsuarioPassword.swActivo}" image="/images/arrow-previous.gif" actionListener="#{ClienteUsuarioPassword.moveSelectedToInvisible}" style="width: 20px;" />
																								 </h:panelGrid>
																								
																								 <h:selectManyListbox converter="ConverRoles"
																								 value="#{ClienteUsuarioPassword.selectedVisibleItems}" size="5"  style="width: 300px;">
																								 <f:selectItems  id="visibleItems" value="#{ClienteUsuarioPassword.visibleItems}" />
																								 </h:selectManyListbox>*/
				%>






			</h:panelGrid>


		</h:panelGroup>

	</h:form>

	</body>

	</html>
</f:view>


