<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Payments | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/client/${client.id}/payments" title="">Платежи</a>
                    </li>
                </ul>

                <ul class="crumb-buttons">
                    <li><a href="${pageContext.request.contextPath}/client/${client.id}/payment" title=""><i class="icon-phone"></i><span>Новый платеж</span></a></li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Все платежи клиента: <a href="${pageContext.request.contextPath}/client/${client.id}">${client.lname} ${client.fname}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Платежи</h4>
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
                                    <th>Дата</th>
                                    <th>Сумма</th>
                                    <th>Контракт</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="payment" items="${payments}">
                                    <tr>
                                        <td>
                                            <c:if test="${payment.planned > 0}">
                                                <span class="label label-info">Запланирован</span>
                                            </c:if>
                                            <c:if test="${payment.done > 0}">
                                                <span class="label label-warning">Проведен</span>
                                            </c:if>
                                            <c:if test="${payment.approved > 0}">
                                                <span class="label label-danger">Подтвержден</span>
                                            </c:if>
                                        </td>
                                        <td>${payment.dateS}</td>
                                        <td>${payment.value}</td>
                                        <td><a href="${pageContext.request.contextPath}/contract/${payment.contractId}">[${payment.typeS}] ${payment.teacherS}</a></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/payment/${payment.id}" class="btn btn-xs btn-success">Подробнее</a>
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
