<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<form:form action="antennas/update.do" modelAttribute="antenna">
    <app:model-editor />
    <form:hidden path="user" />
    <app:textbox path="serialNumber" code="antennas.serialNumber" />
    <app:textbox path="model" code="antennas.model" />
    <app:textbox path="positionLongitude" code="antennas.positionLongitude" />
    <app:textbox path="positionLatitude" code="antennas.positionLatitude" />
    <app:textbox path="rotationAzimuth" code="antennas.rotationAzimuth" />
    <app:textbox path="rotationElevation" code="antennas.rotationElevation" />
    <app:textbox path="signalQuality" code="antennas.signalQuality" />
    <app:select path="satellite" items="${satellites}" itemLabel="name" code="antennas.satellite" />

    <div>
        <app:submit name="submit" code="misc.actions.update" />
        <app:delete-button id="${antenna.id}" action="antennas/delete.do" code="misc.actions.delete" />
        <app:cancel-button code="misc.actions.cancel" />
    </div>

    <c:if test="${message == ''}">
        <span class="error"><spring:message code="${message}" /></span>
    </c:if>
</form:form>

