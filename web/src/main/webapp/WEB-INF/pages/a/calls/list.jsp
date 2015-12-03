<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Calls | ARTBUREAU</title>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
                    <!-- Breadcrumbs line -->
                    <div class="crumbs">
                        <ul id="breadcrumbs" class="breadcrumb">
                            <li>
                                <i class="icon-home"></i>
                                <a href="${pageContext.request.contextPath}/">Dashboard</a>
                            </li>
                            <li class="current">
                                <a href="${pageContext.request.contextPath}/calls" title="">Звонки</a>
                            </li>
                        </ul>

                        <ul class="crumb-buttons">
                            <li><a href="${pageContext.request.contextPath}/call" title=""><i class="icon-phone"></i><span>Новый звонок</span></a></li>
                            <%--<li class="range">--%>
                                <%--<a href="#">--%>
                                    <%--<i class="icon-calendar"></i>--%>
                                    <%--<span>October 19, 2013 - November 17, 2013</span>--%>
                                    <%--<i class="icon-angle-down"></i>--%>
                                <%--</a>--%>
                            <%--</li>--%>

                        </ul>
                    </div>
                    <!-- /Breadcrumbs line -->

                    <!--=== Page Header ===-->
                    <div class="page-header">
                        <div class="page-title">
                            <h3>Звонки</h3>
                        </div>
                    </div>
                    <!-- /Page Header -->

                    <!--=== Page Content ===-->
                    <!--=== no-padding and table-colvis ===-->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="widget box">
                                <div class="widget-header">
                                    <h4><i class="icon-reorder"></i>Звонки</h4>
                                    <div class="toolbar no-padding">
                                        <div class="btn-group">
                                            <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="widget-content no-padding">
                                    <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                                        <thead>
                                        <tr>
                                            <th>Дата</th>
                                            <th>Статус</th>
                                            <th>Клиент</th>
                                            <th>Телефон</th>
                                            <th>Комментарий</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="call" items="${calls}">
                                            <tr>
                                                <td>${call.dateS}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${call.statusId == 1}">
                                                            <span class="label label-success">${call.statusName}</span>
                                                        </c:when>
                                                        <c:when test="${call.statusId == 2}">
                                                            <span class="label label-info">${call.statusName}</span>
                                                        </c:when>
                                                        <c:when test="${call.statusId == 3}">
                                                            <span class="label label-danger">${call.statusName}</span>
                                                        </c:when>
                                                        <c:when test="${call.statusId == 4}">
                                                            <span class="label label-primary">${call.statusName}</span>
                                                        </c:when>

                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/client/${call.clientId}">${call.clientLName} ${call.clientFName}</a>
                                                </td>
                                                <td>${call.phone1}</td>
                                                <td>${call.comment}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/call/${call.id}" class="btn btn-xs btn-success">Подробнее</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /no-padding and table-colvis -->
                    <!-- /Page Content -->

                </div>
    </jsp:body>
</t:general>

