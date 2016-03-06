<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<br/>
<br/>

<t:dataList value="#{accessedBeans.beanList}" var="accessedBean" layout="unorderedList">
    <h:outputLink value="#{accessedBean.clazz}.java.source">
        <h:outputText value="Utilizar"/>
        <h:outputText value="#{accessedBean.name}"/>
        <h:outputText value=" Utilizar "/>
        <h:outputText value="#{accessedBean.clazz}"/>
    </h:outputLink>
</t:dataList>
