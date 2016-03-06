<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<HTML>
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
<BODY>
	<f:view>
		<h:form>

			<h:panelGrid columns="3" cellspacing="0" border="1" width="75%">

				<h:outputText value="Invisible:"/>
				<f:verbatim>&nbsp;</f:verbatim>
				<h:outputText value="Visible:"/>

				<h:selectManyListbox value="#{customizePageBean.selectedInvisibleItems}" size="5" style="width: 300px;">
					<f:selectItems value="#{customizePageBean.invisibleItems}" />
				</h:selectManyListbox>

				<h:panelGrid columns="1">
					<h:commandButton value=">"  actionListener="#{customizePageBean.moveSelectedToVisible}" style="width: 50px;" />
					<h:commandButton value=">>" actionListener="#{customizePageBean.moveAllToVisible}" style="width: 50px;" />
					<h:commandButton value="<"  actionListener="#{customizePageBean.moveSelectedToInvisible}" style="width: 50px;" />
					<h:commandButton value="<<" actionListener="#{customizePageBean.moveAllToInvisible}" style="width: 50px;" />
				</h:panelGrid>

				<h:selectManyListbox value="#{customizePageBean.selectedVisibleItems}" size="5"  style="width: 300px;">
					<f:selectItems id="visibleItems" value="#{customizePageBean.visibleItems}" />
				</h:selectManyListbox>

			</h:panelGrid>

		</h:form>

		<h:outputLink value="index.jsp">
			<h:outputText value="Start page"/>
		</h:outputLink>

	</f:view>
</BODY>
</HTML>