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

<portlet:renderURL var="searchPersonView">
    <portlet:param name="view" value="searchPersonView"/>
</portlet:renderURL>

<portlet:actionURL name="searchPerson" var="searchPerson" portletMode="VIEW">
    <portlet:param name="action" value="searchPerson"/>
</portlet:actionURL>

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
        ${vardEnhetInfoPersonalListSize}
        <%--<liferay-ui:search-container id="personResult" delta="30">--%>
            <%--<liferay-ui:search-container-results results="${vardEnhetInfoPersonalList}"--%>
                                                 <%--total="${vardEnhetInfoPersonalListSize}">--%>
                <%--<liferay-ui:search-container-row className="se.vgregion.delegation.domain.PersonalInfo"--%>
                                                 <%--modelVar="personalInfo"--%>
                                                 <%--keyProperty="uid">--%>
                    <%--<liferay-ui:search-container-column-text>--%>
                        <%--<portlet:actionURL name="addPerson" var="addPerson">--%>
                            <%--<portlet:param name="vgrId" value="${personalInfo.uid}"/>--%>
                        <%--</portlet:actionURL>--%>
                        <%--<a href="${addPerson}">${persolalInfo.fullName} [${persolalInfo.uid}]--%>
                            <%--<c:forEach items="${personalInfo.vgrStrukturPersonDN}" var="orgDn">--%>
                                <%--- ${orgDN}--%>
                            <%--</c:forEach>--%>
                        <%--</a>--%>
                    <%--</liferay-ui:search-container-column-text>--%>
                <%--</liferay-ui:search-container-row>--%>
            <%--</liferay-ui:search-container-results>--%>
        <%--</liferay-ui:search-container>--%>
    </aui:column>
</div>