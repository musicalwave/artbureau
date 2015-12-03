<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Статистика | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/stat" title="">Статистика</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Последнее занятие</h3>
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
                                <form:form class="form-horizontal" method="POST" id="validate-1"
                                           action="${pageContext.request.contextPath}/stat/last"
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
                            <h4><i class="icon-reorder"></i>Занятия</h4>
                        </div>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                                <thead>
                                <tr>
                                    <th>Время</th>
                                    <th>Клиент</th>
                                    <th>Контракт</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="lesson" items="${lessons}">
                                    <tr>
                                        <td><h5 style="color:red">${lesson.startTime}</h5></td>
                                        <td><a href="${pageContext.request.contextPath}/client/${lesson.clientId}">${lesson.clientName}</a></td>
                                        <td><a href="${pageContext.request.contextPath}/contract/${lesson.contractId}">${lesson.teacherName} [${lesson.typeName}]</a></td>
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