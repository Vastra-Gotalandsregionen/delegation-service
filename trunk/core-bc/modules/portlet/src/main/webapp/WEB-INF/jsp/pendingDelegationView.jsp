
  Created by IntelliJ IDEA.
  Created: 2011-11-22 15:28 
  @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>

<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<style type="text/css">
    <%@ include file="/css/delegation.css" %>
</style>

<portlet:renderURL var="activeDelegation" portletMode="VIEW">
    <portlet:param name="view" value="activeDelegation"/>
</portlet:renderURL>

<portlet:renderURL var="pendingDelegationView" portletMode="VIEW">
    <portlet:param name="view" value="pendingDelegation"/>
</portlet:renderURL>

<portlet:renderURL var="searchPersonView">
    <portlet:param name="view" value="searchPersonView"/>
</portlet:renderURL>


<div class="delegation">
    <h1>Delegeringar</h1>
    <hr/>
    <liferay-ui:tabs names="activeDelegation,
    pendingDelegationView" url="${activeDelegation}"/>
    <hr/>
    <div>Vårdenhet: ${vardEnhetInfo.vardEnhet.ou} [${vardEnhetInfo.verksamhetsChef.fullName}]</div>
    <hr/>
    <div>
        <div>Delegera till</div>
        <div>
            <img src="<c:url value="/image/person.png"/>"/>
            <aui:button onClick="${searchPersonView}" value="Sök person"/>
        </div>
        <div>- Gäller för behörighetsbeställning och attest</div>
    </div>
    <hr/>
    <div>
        <div>Gäller från</div>
        <div><span>Today</span><span>Period: 1 år framåt</span></div>
    </div>
    <c:if test="${pendingDelegation != null}">
        <c:if test="${delegationsToSize gt 0}">
            <hr/>
            <div>Delegeringar</div>
            <table>
                <c:forEach items="${pendingDelegation.delegationsTo}" var="to">
                    <tr>
                        <td>${to.delegateTo}</td>
                        <td>${to.validTo}</td>
                    </tr>
                </c:forEach>
            </table>
            <liferay-ui:search-container id="delegation" delta="10" iteratorURL="${pendingDelegationView}">
                <liferay-ui:search-container-results results="${pendingDelegation.delegationsTo}"
                                                     <total="${delegationsToSize}"/>
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
    </c:if>
    <hr/>
    <div>
        <span>Signera</span>
        <span>Avbryt</span>
    </div>
</div>