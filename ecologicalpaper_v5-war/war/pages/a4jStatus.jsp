
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<html>
<head>
<title>Input Name Page</title>
</head>
<body bgcolor="white">
<f:view>
<f:verbatim>
This example shows how a4j status component works. It has two possible output format:<br>
facet with some content to show or simple text which can be setted through attribute.<br>
<br>
A4j Status can attached to Ajax Container through 'for' attribute. If the 'for' attribute is ommited
it works for any ajax requests on this page.<br>

</f:verbatim>
	<h:form id="helloForm">
		<h:messages style="color: red" />
		<a4j:region id="stat1">
			<h:panelGroup style="display:block">
				<h:outputText
					value="Type a few words into the text field. (There is a small delay on server to show you ajax status component's work.) " />
				<h:outputText value="There you can see simple text status." />
			</h:panelGroup>
			<h:inputText>
				<a4j:support action="#{nameBean.DelayAct}" event="onkeyup"></a4j:support>
			</h:inputText>
			<a4j:status startText=" Performing Request" stopText=" Request Done"
				for="stat1">
			</a4j:status>
		</a4j:region>
		<hr>
		<a4j:region id="stat2">
			<h:panelGroup style="display:block">
				<h:outputText
					value="Type a few words into the text field. There you can see icon-like status." />
			</h:panelGroup>
			<h:inputText>
				<a4j:support action="#{nameBean.DelayAct}" event="onkeyup"></a4j:support>
			</h:inputText>
			<a4j:status for="stat2">
				<f:facet name="start">
					<h:graphicImage value="/images/ajax_process.gif" />
				</f:facet>
				<f:facet name="stop">
					<h:graphicImage value="/images/ajax_stoped.gif" />
				</f:facet>
			</a4j:status>
		</a4j:region>
		<HR>
		<a4j:region id="stat3">
			<h:panelGroup style="display:block">
				<h:outputText value="Type a few words into the text field. " />
				<h:outputText value="There you can hidden while inactive status(with empty 'stop' facet/text attribute)." />
			</h:panelGroup>
			<h:inputText>
				<a4j:support action="#{nameBean.DelayAct}" event="onkeyup"></a4j:support>
			</h:inputText>
			<a4j:status for="stat3" stopText=" ">
				<f:facet name="start">
					<h:graphicImage value="/images/ajax_process.gif" />
				</f:facet>
			</a4j:status>
		</a4j:region>
	</h:form>
	<hr>
	Code snippets of this page:
	<jsp:include page="/pages/source.html"/>
</f:view>
</body>
</html>
