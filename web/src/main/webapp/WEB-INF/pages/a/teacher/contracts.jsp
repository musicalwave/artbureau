<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Contracts | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/teacher/${teacher.id}/contracts" title="">Контракты</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Все контракты преподавателя: <a href="${pageContext.request.contextPath}/teacher/${teacher.id}">${teacher.name}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Контракты</h4>
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
                                    <th></th>
                                    <th>Ученик</th>
                                    <th>Направление</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="contract" items="${contracts}">
                                    <tr>
                                        <td>
                                            <c:if test="${contract.statusId == 1}">
                                                <span class="label label-warning">Действующий</span>
                                            </c:if>
                                            <c:if test="${contract.statusId == 2}">
                                                <span class="label label-success">Завершенный</span>
                                            </c:if>
                                            <c:if test="${contract.statusId == 3}">
                                                <span class="label label-default">Удаленный</span>
                                            </c:if>
                                            <c:if test="${contract.freezed == 1}">
                                                <span class="label label-info">Заморозка</span>
                                            </c:if>
                                        </td>
                                        <td><a href="${pageContext.request.contextPath}/client/${contract.clientId}">${contract.clientLS} ${contract.clientFS}</a></td>
                                        <td>${contract.typeS}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/contract/${contract.id}"
                                               class="btn btn-xs btn-success">Подробнее</a>
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
