<%@ page import="javax.portlet.PortletURL" %>
<%--
  Created by IntelliJ IDEA.
  Created: 2011-11-23 11:03 
  @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
--%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>


<style type="text/css">
    <%@ include file="/css/delegation.css" %>
</style>

<%--<portlet:renderURL var="searchPersonView">--%>
<%--<portlet:param name="view" value="searchPersonView"/>--%>
<%--</portlet:renderURL>--%>

<portlet:actionURL name="searchPerson" var="searchPerson" portletMode="VIEW">
    <portlet:param name="action" value="searchPerson"/>
</portlet:actionURL>

<portlet:defineObjects/>

<%
    PortletURL searchPersonalView = renderResponse.createRenderURL();
    searchPersonalView.setParameter("view", "searchPersonalView");

%>

<div class="delegation">
    <aui:column columnWidth="50" first="true">
        <aui:form action="<%= searchPerson %>" method="post">
            <aui:input name="firstName" value="${search.firstName}" type="text" label="Förnamn"/>
            <aui:input name="lastName" value="${search.lastName}" type="text" label="Efternamn"/>
            <aui:input name="vgrId" value="${search.vgrId}" type="text" label="VgrId"/>
            <aui:button-row>
                <aui:button type="submit" value="search"/>
            </aui:button-row>
        </aui:form>
    </aui:column>
    <aui:column columnWidth="50" last="true">
        <div>Sökresultat</div>
        <div>Klicka på den person du vill lägga till.</div>
        <hr/>
        <c:forEach items="${vardEnhetInfoPersonalMap}" var="enhet">
            <div>${enhet.key.ou}</div>
            <c:forEach items="${enhet.value}" var="personal">
                <portlet:actionURL name="addPersonal" var="addPersonal">
                    <portlet:param name="vgrId" value="${personal.uid}"/>
                </portlet:actionURL>
                <div>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${addPersonal}">${personal.fullName} [${personal.uid}]</a>
                </div>
            </c:forEach>
        </c:forEach>
        <%--<liferay-ui:search-container id="personResult" delta="30"--%>
        <%--emptyResultsMessage="Tomten kommer inte"--%>
        <%--curParam="currentPage"--%>
        <%--iteratorURL="${searchPersonalView}">--%>
        <%--<liferay-ui:search-container-results results="${vardEnhetInfoPersonalList}"--%>
        <%--total="${vardEnhetInfoPersonalListSize}"/>--%>
        <%--<liferay-ui:search-container-row className="se.vgregion.delegation.domain.PersonalInfo"--%>
        <%--modelVar="personalInfo"--%>
        <%--keyProperty="uid">--%>
        <%--${personalInfo.uid}     <br/>--%>

        <%--<portlet:actionURL name="addPerson" var="addPerson">--%>
        <%--<portlet:param name="vgrId" value="${personalInfo.uid}"/>--%>
        <%--</portlet:actionURL>--%>

        <%--&lt;%&ndash;<a href="${addPerson}">${persolalInfo.fullName} [${persolalInfo.uid}]&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:forEach items="${personalInfo.vgrStrukturPersonDN}" var="orgDn">&ndash;%&gt;--%>
        <%--&lt;%&ndash;- ${orgDN}&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</liferay-ui:search-container-column-text>&ndash;%&gt;--%>

        <%--</liferay-ui:search-container-row>--%>
        <%--</liferay-ui:search-container>--%>
    </aui:column>
</div>