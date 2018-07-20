<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="app" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('USER')">
<app:redir-button action="tutorials/new.do" code="misc.actions.new" />
</security:authorize>
<display:table name="tutorials"
	       id="tutorial"
	       pagesize="${displayTagPageSize}"
	       sort="list"
	       requestURI="tutorials/index.do">

    <display:column property="title" titleKey="tutorials.title" escapeXml="true" sortable="true" href="tutorials/show.do" paramId="id" paramProperty="id" />
    <display:column property="lastUpdateTime" titleKey="tutorials.lastUpdateTime" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="true" />
    <display:column property="user.fullName" titleKey="tutorials.user" escapeXml="true" sortable="true" />
    <security:authorize access="isAuthenticated()">
        <display:column titleKey="misc.actions">
            <c:if test="${tutorial.user == principal}">
                <app:redir-button code="misc.actions.edit" action="tutorials/edit.do?id=${tutorial.id}" />
            </c:if>
            <security:authorize access="hasRole('ADMINISTRATOR')">
                <app:delete-button code="misc.actions.delete" action="tutorials/delete.do" id="${tutorial.id}" />
            </security:authorize>
        </display:column>
    </security:authorize>
</display:table>