<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>New teacher | ARTBUREAU</title>
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
                        <a href="#" title="">Новый преподаватель</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Новый преподаватель</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <form:form class="form-horizontal row-border" method="POST"
                               action="${pageContext.request.contextPath}/teacher/new" commandName="teacher">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Персональная информация</h4>
                        </div>
                        <div class="widget-content">
                            <div class="row">
                                <div class="col-md-4">
                                    <label class="col-md-6 control-label">ФИО:</label>
                                    <div class="form-group">
                                        <form:input type="text" path="name" class="form-control"
                                                    placeholder="Константинопольский Константин Константинович"/>
                                    </div>

                                    <label class="col-md-6 control-label">Дата рождения:</label>
                                    <div class="form-group">
                                        <form:input type="text" path="bdate" class="form-control"
                                                    data-mask="99/99/9999"
                                                    placeholder="30/12/1992"/>
                                    </div>

                                    <label class="col-md-6 control-label">Контакты:</label>
                                    <div class="form-group">
                                        <form:input type="text" path="phone" class="form-control"
                                                    data-mask="+7 (999) 999-9999"
                                                    placeholder="+7 (915) 123-4567"/>
                                    </div>

                                    <label class="col-md-6 control-label">Дата начала работы:</label>
                                    <div class="form-group">
                                        <form:input type="text" path="startDate" class="form-control datepicker"/>
                                    </div>

                                    <label class="col-md-6 control-label">Направления</label>
                                    <div class="form-group">
                                        <form:select id="input18" path="types" class="select2-select-00 col-md-12 full-width-fix" multiple="true" size="5">
                                            <c:forEach var="type" items="${types}">
                                                <form:option value="${type.id}">${type.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>

                                    <label class="col-md-6 control-label">Дни работы</label>
                                    <div class="form-group">
                                        <form:select id="input18" path="weekDays" class="select2-select-00 col-md-12 full-width-fix" multiple="true" size="5">
                                            <c:forEach var="day" items="${days}">
                                                <form:option value="${day.id}">${day.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="widget">
                    <div class="widget-header">
                        <h4><i class="icon-reorder"></i>Юридическая инфомация</h4>
                    </div>
                    <div class="widget-content">
                        <form:textarea rows="3" cols="5" path="credentials"
                                       class="auto form-control"/>
                    </div>
                </div>

                <div class="widget">
                    <div class="widget-header">
                        <h4><i class="icon-reorder"></i>Комментарий</h4>
                    </div>
                    <div class="widget-content">
                        <form:textarea rows="3" cols="5" path="comment"
                                       class="auto form-control"/>
                    </div>
                </div>

                <div class="form-actions">
                    <input type="submit" value="Сохранить" class="btn btn-primary pull-right">
                </div>
                </form:form>
            </div>
        </div>

        </div>
    </jsp:body>
</t:general>
