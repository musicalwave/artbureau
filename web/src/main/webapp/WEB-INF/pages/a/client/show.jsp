
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Страница клиента | ARTBUREAU</title>
    </jsp:attribute>
    <jsp:attribute name="addimports">
        <script src="${pageContext.request.contextPath}/resources/js/client.min.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
        <!-- Breadcrumbs line -->
        <div class="crumbs">
            <ul id="breadcrumbs" class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="${pageContext.request.contextPath}/">Главная</a>
                </li>
                <li class="current">
                    <a href="${pageContext.request.contextPath}/client/${client.id}" title="">Страница клиента</a>
                </li>
            </ul>
        </div>
        <!-- /Breadcrumbs line -->

        <div id="client-box" />

    </jsp:body>
</t:general>