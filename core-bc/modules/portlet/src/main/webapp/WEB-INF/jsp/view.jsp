<%--
  Created by IntelliJ IDEA.
  Created: 2011-10-18 11:36 
  @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
--%>

<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style type="text/css">
    <%@ include file="/css/delegation.css" %>
</style>

<portlet:actionURL var="selectVardEnhet" name="selectVardEnhet" portletMode="VIEW"/>

<div>
    <h1>Hej Delegering</h1>

    <form:form method="post" action="${selectVardEnhet}" commandName="delegationInfo">
        <h2>Mina VårdEnheter</h2>
        <form:select path="vardEnhet">
            <form:option value="">-- Ingen VårdEnhet vald --</form:option>
            <form:options items="${verksamhetsChefInfoList}" itemLabel="vardEnhet.ou" />
        </form:select>
        <h2>Alla VårdEnheter</h2>
        <form:select path="vardEnhet">
            <form:option value="">-- Ingen VårdEnhet vald --</form:option>
            <form:options items="${allVardEnhets}" itemLabel="ou" />
        </form:select>
    </form:form>

    <br/>
    <br/>
    <br/>
    <br/>

    <form method="post" action="${sign_requestUrl}">
        <p>${sign_requestUrl}</p>
        <input name="tbs" value="Sätt in 1000 kronor på konto 123456-7" type="text"/>
        <input name="submitUri" value="${sign_submitUri}" type="text"/>
        <input name="clientType" value="${sign_clientType}" type="text"/>
        <input type="submit" value="Signera"/>
    </form>
</div>