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
            function borraCaracter(TodoElObjeto){
               //obtenemos el objeto de la forma
                forma = TodoElObjeto.form;
                //obtenemos el objetdo de la forma nombrado por el id y su campo
                valor=forma[forma.id+":fecha_caduca"]
                value=valor.value;
                forma[forma.id+":fecha_caduca"].value='';
            
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
<h:form>

		<t:jscookMenu   layout="hbr" theme="ThemeOffice"
			styleLocation="css/jscookmenu">
			<t:navigationMenuItems id="navitems"
				value="#{tree.panelNavigationItems}" />
		</t:jscookMenu>

	</h:form>



	<f:verbatim>

	</f:verbatim>



	<h:messages />


	<h:panelGrid columns="1" id="panelGrid1">

	</h:panelGrid>

	<h:form id="frmCrear">

		<h:panelGroup>

			<h:panelGrid columns="3" id="panelGrid1">
				<f:verbatim></f:verbatim>
				<h:outputText styleClass="form" value="#{txt.usuario_nuevo}" />
				<f:verbatim></f:verbatim>
				
				<h:outputText styleClass="form" value="#{txt.usuario_nombre} * " />
				<h:inputText id="nombre" value="#{ClienteUsuario.usuario.nombre}"
					title="#{txt.usuario_nombre}" immediate="false">
					<f:validateLength maximum="40" minimum="4" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>

				<h:message for="nombre" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_apellido} * " />
				<h:inputText id="apellido"
					value="#{ClienteUsuario.usuario.apellido}"
					title="#{txt.usuario_Apellido}" immediate="false">
					<f:validateLength maximum="40" minimum="4" />
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="apellido" styleClass="form" />


				<h:outputText styleClass="form" value="#{txt.usuario_fechab}" />
				<t:inputCalendar id="fecha_caduca" onkeyup="borraCaracter(this)"
					monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
					popupButtonStyleClass="standard_bold"
					currentDayCellClass="currentDayCell"
					value="#{ClienteUsuario.usuario.fecha_caduca}" renderAsPopup="true"
					popupTodayString="#{txt.calendar_fecha}"
					popupDateFormat="MM/dd/yyyy"
					popupWeekString="#{txt.popup_week_string}" helpText="MM/DD/YYYY"
					forceId="true" />
				<h:message for="fecha_caduca" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_mail} * " />
				<h:inputText id="mail"
					value="#{ClienteUsuario.usuario.mail_principal}"
					title="#{txt.usuario_mail}" maxlength="40" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="mail" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_telefono_ofic}" />
				<h:inputText id="telefono_ofic"
					value="#{ClienteUsuario.usuario.telefono_ofic}"
					title="#{txt.usuario_telefono_ofic}" maxlength="40"
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="telefono_ofic" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_telefono_casa}" />
				<h:inputText id="telefono_casa"
					value="#{ClienteUsuario.usuario.telefono_casa}"
					title="#{txt.usuario_telefono_casa}" maxlength="40"
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="telefono_casa" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_telefono_cel}" />
				<h:inputText id="telefono_cel"
					value="#{ClienteUsuario.usuario.telefono_cel}"
					title="#{txt.usuario_telefono_cel}" maxlength="40"
					immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
				</h:inputText>
				<h:message for="telefono_cel" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_direccion}" />
				<h:inputTextarea id="desc2" cols="40" rows="3"
					value="#{ClienteUsuario.usuario.direccion}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
					<f:validateLength maximum="3500" minimum="0" />
				</h:inputTextarea>
				<h:message for="desc2" styleClass="form" />


				<h:outputText id="selectOneMenuProfesion" styleClass="form"
					value="#{txt.usuario_profesion}" title="#{txt.usuario_profesion}" />
				<h:selectOneMenu value="#{ClienteUsuario.usuario.profesion}"
					converter="ConverProfesion">
					<f:selectItems value="#{ClienteUsuario.allProfesion}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuProfesion" styleClass="form" />


				<h:outputText styleClass="form" value="#{txt.usuario_empresa} * "
					title="#{txt.usuario_empresa}" />
				<h:panelGroup>


					<h:selectOneMenu disabled="true" id="selectOneMenuEmpresa"
						immediate="true" binding="#{ClienteUsuario.selectEmpresa}"
						value="#{ClienteUsuario.usuario.empresa}" converter="ConverTree">

						<a4j:support event="onchange" ajaxSingle="true"
							reRender="selectOneMenuPrincipal,selectOneMenuArea,selectOneMenuCargo"
							action="#{ClienteUsuario.changePrincipalP}" />

						<f:selectItems value="#{ClienteUsuario.allEmpresas}" />
					</h:selectOneMenu>

				</h:panelGroup>
				<h:message for="selectOneMenuEmpresa" styleClass="form" />

				<h:outputText styleClass="form"
					value="#{txt.flow_commentPrincipal} * "
					title="#{txt.flow_commentPrincipal}" />

				<h:panelGroup>

					<h:selectOneMenu id="selectOneMenuPrincipal" immediate="true"
						binding="#{ClienteUsuario.selectPrincipal}"
						value="#{ClienteUsuario.usuario.principal}" converter="ConverTree">
						<a4j:support event="onchange" ajaxSingle="true"
							reRender="selectOneMenuArea,selectOneMenuCargo"
							action="#{ClienteUsuario.changeAreaP}" />

						<f:selectItems value="#{ClienteUsuario.allPrincipal}" />
					</h:selectOneMenu>

				</h:panelGroup>
				<h:message for="selectOneMenuPrincipal" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_area} * "
					title="#{txt.usuario_area}" />

				<h:selectOneMenu id="selectOneMenuArea" immediate="true"
					binding="#{ClienteUsuario.selectArea}"
					value="#{ClienteUsuario.usuario.area}" converter="ConverTree">
					<a4j:support event="onchange" ajaxSingle="true"
						reRender="selectOneMenuCargo"
						action="#{ClienteUsuario.changeCargos}" />
					<f:selectItems value="#{ClienteUsuario.allAreas}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuArea" styleClass="form" />



				<h:outputText styleClass="form" value="#{txt.usuario_cargo} * "
					title="#{txt.usuario_cargo}" />

				<h:selectOneMenu id="selectOneMenuCargo" immediate="true"
					binding="#{ClienteUsuario.selectCargo}"
					value="#{ClienteUsuario.usuario.cargo}" converter="ConverTree">
					<f:selectItems value="#{ClienteUsuario.allCargos}" />
				</h:selectOneMenu>
				<h:message for="selectOneMenuCargo" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_login} *" />
				<h:inputText id="login" value="#{ClienteUsuario.usuario.login}"
					title="#{txt.usuario_login}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
					<f:validateLength maximum="15" minimum="4" />
				</h:inputText>
				<h:message for="login" styleClass="form" />

				<h:outputText styleClass="form" value="#{txt.usuario_password} *" />
				<h:inputSecret id="password"
					value="#{ClienteUsuario.usuario.password}"
					title="#{txt.usuario_password}" immediate="false">
					<f:validator validatorId="caracteresinvalidos" />
					<f:validateLength maximum="100" minimum="4" />
				</h:inputSecret>
				<h:message for="password" styleClass="form" />

				<h:outputText styleClass="form"
					value="#{txt.usuario_passwordconfirm}" />
				<h:inputSecret id="passwordconf"
					value="#{ClienteUsuario.passwordConfirm}"
					title="#{txt.usuario_passwordconfirm}">
				</h:inputSecret>
				<h:message for="passwordconf" styleClass="form" />
			</h:panelGrid>


			<h:panelGrid columns="3" cellspacing="0" border="0" width="75%">

				<h:outputText styleClass="form" value="#{txt.Operaciones_noSelec}" />
				<f:verbatim>&nbsp;</f:verbatim>
				<h:outputText styleClass="form" value="#{txt.Operaciones_Selec}" />

				<h:selectManyListbox converter="ConverRoles" styleClass="form"
					value="#{ClienteUsuario.selectedInvisibleItems}" size="5"
					style="width: 300px;">
					<f:selectItems value="#{ClienteUsuario.invisibleItems}" />
				</h:selectManyListbox>

				<h:panelGrid columns="1">
					<h:commandButton  image="/images/arrow-next.gif"
						actionListener="#{ClienteUsuario.moveSelectedToVisible}"
						style="width:20px;" />
					<h:commandButton image="/images/arrow-ff.gif"
						actionListener="#{ClienteUsuario.moveAllToVisible}"
						style="width: 20px;" />
					<h:commandButton image="/images/arrow-fr.gif"
						actionListener="#{ClienteUsuario.moveAllToInvisible}"
						style="width: 20px;" />
					<h:commandButton image="/images/arrow-previous.gif"
						actionListener="#{ClienteUsuario.moveSelectedToInvisible}"
						style="width: 20px;" />
				</h:panelGrid>

				<h:selectManyListbox converter="ConverRoles"
					value="#{ClienteUsuario.selectedVisibleItems}" size="5"
					style="width: 300px;">
					<f:selectItems id="visibleItems"
						value="#{ClienteUsuario.visibleItems}" />
				</h:selectManyListbox>

				<f:verbatim></f:verbatim>
				<h:commandButton styleClass="boton" value="#{txt.btn_cancelar}" immediate="true"
					action="#{ClienteUsuario.cancelarEditUser}" />

				<h:commandButton styleClass="boton" onclick="validarCampos(this)"
					value="#{txt.usuario_guardar}" action="#{ClienteUsuario.create}" />





			</h:panelGrid>


		</h:panelGroup>

	</h:form>




	</body>

	</html>
</f:view>


