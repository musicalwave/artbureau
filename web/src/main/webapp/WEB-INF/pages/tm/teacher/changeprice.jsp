<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Edit prices | ARTBUREAU</title>
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
                        <a href="#" title="">Цены</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Редактирование ставок преподавателя ${type.teacherName} (Цена/Ставка)</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-content no-padding">
                            <div class="row">
                                <form:form class="form-horizontal row-border" method="POST" action="${pageContext.request.contextPath}/tm/teacher/cp" commandName="type">
                                    <div class="hidden">
                                        <form:input path="id" type="number" value="${type.id}"/>
                                        <form:input path="typeId" type="number" value="${type.typeId}"/>
                                        <form:input path="teacherId" type="number" value="${type.teacherId}"/>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-4">
                                            <h5>Название направления</h5>
                                            <form:input path="typeName" type="text" class="form-control" placeholder="Ошибка..." value="${type.teacherName} [${type.typeName}]" readonly="true"/>

                                        </div>
                                        <div class="col-md-8">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <h5>Индивидуальный</h5>
                                                    <form:input path="pPrice" type="number" class="form-control" placeholder="Цена"/>
                                                    <form:input path="pPay" type="number" class="form-control" placeholder="Ставка"/>

                                                </div>
                                                <div class="col-md-3">
                                                    <h5>Парный</h5>

                                                    <form:input path="dPrice" type="number" class="form-control" placeholder="Цена"/>
                                                    <form:input path="dPay" type="number" class="form-control" placeholder="Ставка"/>
                                                </div>
                                                <div class="col-md-3">
                                                    <h5>Групповой</h5>

                                                    <form:input path="gPrice" type="number" class="form-control" placeholder="Цена"/>
                                                    <form:input path="gPay" type="number" class="form-control" placeholder="Ставка"/>
                                                </div>
                                                <div class="col-md-3">
                                                    <h5>Ансамбль</h5>

                                                    <form:input path="aPrice" type="number" class="form-control" placeholder="Цена"/>
                                                    <form:input path="aPay" type="number" class="form-control" placeholder="Ставка"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-actions">
                                        <input type="submit" value="Сохранить" class="btn btn-primary pull-right">
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /no-padding and table-colvis -->
            <!-- /Page Content -->

        </div>
    </jsp:body>
</t:general>

