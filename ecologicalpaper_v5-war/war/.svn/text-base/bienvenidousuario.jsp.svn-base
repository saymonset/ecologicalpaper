<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>



<f:view>
	<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
		var="txt_ecolo" />


	<HTML>
	<HEAD>
	<TITLE><h:outputText
		value="#{txt_ecolo.multiplataforma} #{tree.user_logueado}" /></TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
	<LINK href="bienvenido_archivos/comun.css" type=text/css rel=stylesheet>

	<META content="MSHTML 6.00.2900.3059" name=GENERATOR>
	</HEAD>
	<BODY>


	<h:form>

		<f:loadBundle basename="com.ecological.resource.ecologicalpaper"
			var="txt" />
		<f:loadBundle
			basename="org.apache.myfaces.examples.resource.example_messages"
			var="example_messages" />
		<f:loadBundle basename="com.util.resource.ecological_conf"
			var="conftxt" />

		<TABLE cellSpacing=1 cellPadding=0 width=750 align=center
			bgColor=#666666 border=0>
			<TBODY>
				<TR>
					<TD vAlign=top bgColor=#ffffff height=473>
					<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
						<TBODY>
							<TR>
								<TD vAlign=top><IMG height=317
									src="<h:outputText value="#{backing_login.imgLogoEmpresa}"/>"
									width=748></TD>
							</TR>
							<TR>
								<TD height=19>
								<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
									<TBODY>
										<TR>
											<TD width="58%">
											<DIV align="right"></DIV>
											</TD>
											<TD width="20%">
											<DIV align="right"><BR>
											<h:graphicImage width="150" height="75"
												title="#{txt.multiplataforma}" url="#{conftxt.img_logo1}" />
											<!--<IMG height=95
            src="bienvenido_archivos/cerrojo.jpg"
            width=97>--><BR>
											<BR>
											<BR>
											</DIV>
											</TD>
											<TD width="20%">
											<DIV align="right"><BR>
											<!--  <a href="http://www.ecologicalpaper.com/" style="forms">http://www.ecologicalpaper.com/</a>-->

											<h:outputText styleClass="form"
												value="#{requestScope.usuario_nologueado} " /> <h:messages
												styleClass="form" /></DIV>
											</TD>

											<TD ALIGN="left" width="28%">
											<%
												/*<IMG height=90
																																																																																																																												 src="./tree/tope_archivos/logo2.jpg" width=276>
														 */
											%>
											</TD>
											<TD width="22%"><h:panelGroup
												rendered="#{!backing_login.swEscojerempresa}">
												<h:panelGrid columns="3"
													binding="#{backing_login.panelGrid1}" id="panelGrid1">
													<f:verbatim></f:verbatim>
													<h:panelGrid columns="2">
														<h:selectBooleanCheckbox
															rendered="#{backing_login.swLdapdominiodc}"
															value="#{backing_login.servidorLdap}" immediate="true" />
														<h:outputText styleClass="form"
															rendered="#{backing_login.swLdapdominiodc}"
															value="#{txt.ldapactivedirectoryhost}" />
													</h:panelGrid>
													<f:verbatim></f:verbatim>

													<h:outputText styleClass="grees"
														value="#{txt.usuario_login}"
														binding="#{backing_login.outputText1}" id="outputText1" />
													<h:inputText binding="#{backing_login.inputText1}"
														id="inputText1" required="true"
														value="#{backing_login.usuario.login}" />
													<h:message styleClass="form" for="inputText1" />

													<h:outputText styleClass="grees"
														value="#{txt.usuario_password}"
														binding="#{backing_login.outputText2}" id="outputText2" />
													<h:inputSecret binding="#{backing_login.inputSecret1}"
														id="inputSecret1" required="true"
														value="#{backing_login.usuario.password}">
														<f:validateLength maximum="18" minimum="4" />
													</h:inputSecret>
													<h:message styleClass="form" for="inputSecret1" />



												</h:panelGrid>

												<h:panelGrid columns="1">

													<h:commandButton value="#{txt.btn_aceptar}"
														styleClass="boton"
														binding="#{backing_login.commandButton1}"
														id="commandButton1sss"
														action="#{backing_login.login_actionbienvenido}" />
												</h:panelGrid>
											</h:panelGroup> <h:panelGroup rendered="#{backing_login.swEscojerempresa}">
												<h:panelGrid columns="3" id="panelGrid2xxx">


													<h:outputText styleClass="grees"
														value="#{txt.usuario_empresa}"
														title="#{txt.usuario_empresa}" />
													<h:selectOneMenu id="selectOneMenuEmpresaxxx"
														immediate="true" value="#{backing_login.empresa}"
														converter="ConverTree">
														<f:selectItems value="#{backing_login.lstEmpresas}" />
													</h:selectOneMenu>
													<f:verbatim></f:verbatim>



												</h:panelGrid>

												<h:panelGrid columns="1">

													<h:panelGrid columns="2">

														<h:commandButton styleClass="boton"
															value="#{txt.cancelar}" id="commandButton1cancxelar"
															action="#{backing_login.inicio}" />

														<h:commandButton styleClass="boton"
															value="#{txt.btn_aceptar}" id="commandButton1xxxa"
															action="#{backing_login.login_action}" />
													</h:panelGrid>

												</h:panelGrid>
											</h:panelGroup> <h:panelGrid columns="1">




												<h:outputLink target="_blank"
													value="http://www.ecologicalpaper.com">

													<t:popup id="a2" styleClass="popup"
														closePopupOnExitingElement="true"
														closePopupOnExitingPopup="true" displayAtDistanceX="10"
														displayAtDistanceY="10">
														<h:outputText styleClass="grees"
															value="#{conftxt.txt_direccion}" />
														<f:facet name="popup">
															<h:panelGroup>
																<h:panelGrid columns="2">
																	<h:outputText styleClass="grees"
																		value="#{conftxt.licencia}" />
																	<h:outputText styleClass="grees"
																		value="#{backing_login.numero_usuarios} #{txt.usuario_varios}" />
																	<f:verbatim></f:verbatim>
																</h:panelGrid>

																<h:panelGrid columns="2">
																	<h:outputText styleClass="grees"
																		value="#{txt.expiraen}:"
																		rendered="#{backing_login.swNoComprado}" />
																	<h:outputText styleClass="grees"
																		value="#{backing_login.diasSeVence} #{txt.dias}"
																		rendered="#{backing_login.swNoComprado}" />
																	<f:verbatim></f:verbatim>
																</h:panelGrid>
															</h:panelGroup>
														</f:facet>
													</t:popup>





												</h:outputLink>
											</h:panelGrid></TD>
										</TR>
									</TBODY>
								</TABLE>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</h:form>
	</BODY>
	</HTML>
</f:view>

