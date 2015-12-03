<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Administration | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/tm/admin" title="">Управление</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Журнал переносов</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Фильтры</h4>
                        </div>
                        <div class="widget-content">
                            <div class="row">
                                <form:form class="form-horizontal" method="POST"
                                           action="${pageContext.request.contextPath}/tm/transfer"
                                           commandName="date">
                                    <div class="col-md-8">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <label class="pull-right">Дата</label>
                                            </div>
                                            <div class="col-md-6">
                                                <form:input type="text" path="name" class="form-control required datepicker"/>
                                            </div>
                                            <div class="col-md-3">
                                                <input type="submit" value="Применить" class="btn btn-primary pull-left">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4"></div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Переносы</h4>
                        </div>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                                <thead>
                                <tr>
                                    <th>Даты</th>
                                    <th>Причина</th>
                                    <th>Преподаватель</th>
                                    <th>Клиент</th>
                                    <th>Комментарий</th>
                                    <th>Ответственный</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="lesson" items="${lessons}">
                                    <tr>
                                        <td>${lesson.fromDateS} &rarr; ${lesson.toDateS}</td>
                                        <td>
                                            <c:if test="${lesson.reason == 1}">
                                                Клиент
                                            </c:if>
                                            <c:if test="${lesson.reason == 2}">
                                                Педагог
                                            </c:if>
                                            <c:if test="${lesson.reason == 3}">
                                                Школа
                                            </c:if>
                                        </td>
                                        <td><a href="${pageContext.request.contextPath}/teacher/${lesson.teacherId}">${lesson.teacherName}</a></td>
                                        <td><a href="${pageContext.request.contextPath}/client/${lesson.clientId}">${lesson.clientName}</a></td>
                                        <td>${lesson.comment}</td>
                                        <td>${lesson.userS}<br>${lesson.actDateS}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.row -->
            <!-- /Page Content -->

        </div>

    </jsp:body>
</t:general>