<%--
  Created by IntelliJ IDEA.
  Created: 2011-11-22 15:28 
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

<portlet:renderURL var="pendingDelegation" portletMode="VIEW">
    <portlet:param name="view" value="editDelegation"/>
    <portlet:param name="vcVgrId" value="${vcVgrId}"/>
</portlet:renderURL>


<div>
    <h1>Delegeringar</h1>
    <hr/>
    <span>Aktuella delegeringar</span> <span><a href="${pendingDelegation}">Skapa ny delegering</a></span>
    <hr/>
    <div>Vårdenhet: ${vardEnhetInfo.vardEnhet.ou} [${vardEnhetInfo.verksamhetsChef.fullName}]</div>

    <c:if test="${not activeDelegation eq null}">
        <liferay-ui:search-container id="delegation" delta="10" iteratorURL="${iteratorURL}">
            <liferay-ui:search-container-results results="${activeDelegation.delegationsTo}" total=""/>
            <liferay-ui:search-container-row className="se.vgregion.delegation.domain.DelegationTo"
                                             keyProperty="delegationTo">
                <portlet:actionURL var="userInfo" name="userInfo" portletMode="VIEW">
                    <portlet:param name="vgrId" value="${delegationTo.id}"/>
                </portlet:actionURL>

                <liferay-ui:search-container-column-text name="VGRID" property="delegateTo"/>
                <liferay-ui:search-container-column-text name="Gäller till" property="validTo"/>

                <liferay-ui:search-container-column-text>
                    <a href="${userInfo}" class="info-img">i</a>
                </liferay-ui:search-container-column-text>


            </liferay-ui:search-container-row>


        </liferay-ui:search-container>
    </c:if>


</div>