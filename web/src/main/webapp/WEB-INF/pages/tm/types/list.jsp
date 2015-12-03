<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Prices | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/types" title="">Направления</a>
                    </li>
                </ul>

                <ul class="crumb-buttons">
                    <li><a href="${pageContext.request.contextPath}/tm/type" title=""><i class="icon-user"></i><span>Новое направление</span></a></li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Направления</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Ставки по-умолчанию</h4>
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
                                    <th>Направление</th>
                                    <th>Индивидуальные</th>
                                    <th>Парные</th>
                                    <th>Групповые</th>
                                    <th>Ансамбль</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="p" items="${prices}">
                                    <tr>
                                        <td>${p.name}</td>
                                        <td>${p.pPrice} (${p.pPay})</td>
                                        <td>${p.dPrice} (${p.dPay})</td>
                                        <td>${p.gPrice} (${p.gPay})</td>
                                        <td>${p.aPrice} (${p.aPay})</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tm/type/${p.id}" class="btn btn-xs btn-warning">Изменить</a>
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

