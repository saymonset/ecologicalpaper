<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<HTML>
<BODY>
	<f:view>
		<h:form>
			<h:panelGrid columns="1">

				<h:selectManyListbox value="#{dynamicPageBean.selectedItems}" size="5">
					<f:selectItems value="#{dynamicPageBean.items}" />
				</h:selectManyListbox>

				<h:commandButton value="Show Selected"  actionListener="#{dynamicPageBean.showSelected}" />

				<h:outputText binding="#{dynamicPageBean.selItemsOutput}" />

			</h:panelGrid>
		</h:form>
		<h:outputLink value="index.jsp">
			<h:outputText value="Start page"/>
		</h:outputLink>
	</f:view>
</BODY>
</HTML>