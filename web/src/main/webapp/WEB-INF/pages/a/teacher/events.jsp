<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Events | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/teacher/${teacher.id}/events" title="">Занятия</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Расписание преподавателя: <a href="${pageContext.request.contextPath}/teacher/${teacher.id}">${teacher.name}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">

                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Занятия</h4>
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
                                    <th>День недели</th>
                                    <th>Время</th>
                                    <th>Класс</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="event" items="${events}">
                                    <tr>
                                        <td><c:if test="${event.weekday == 1}">
                                            Понедельник
                                            </c:if>
                                            <c:if test="${event.weekday == 2}">
                                            Вторник
                                            </c:if>
                                            <c:if test="${event.weekday == 3}">
                                            Среда
                                            </c:if>
                                            <c:if test="${event.weekday == 4}">
                                            Четверг
                                            </c:if>
                                            <c:if test="${event.weekday == 5}">
                                            Пятница
                                            </c:if>
                                            <c:if test="${event.weekday == 6}">
                                            Суббота
                                            </c:if>
                                            <c:if test="${event.weekday == 7}">
                                            Воскресенье
                                            </c:if>
                                        </td>
                                        <td>${event.startTime} - ${event.finishTime}</td>
                                        <td>${event.roomS}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/teacher/${teacher.id}/events/${event.id}"
                                               class="btn btn-xs btn-success">Редактировать</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <c:if test="${event.id != 0}">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Действия</h4>
                            <div class="toolbar no-padding">
                                <div class="btn-group">
                                    <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="widget-content no-padding">
                            <div class="tabbable tabbable-custom tabs-left">
                                <!-- Only required for left/right tabs -->
                                <ul class="nav nav-tabs tabs-left">
                                    <li class="active"><a href="#tab_1" data-toggle="tab">Редактировать</a></li>
                                    <li><a href="#tab_2" data-toggle="tab">Удалить</a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tab_1">
                                    <form:form class="form-horizontal row-border" method="POST"
                                               action="${pageContext.request.contextPath}/event/edit" commandName="event">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <div class="hidden">
                                                        <form:input type="text" path="id" class="form-control"/>
                                                        <form:input type="text" path="teacherId" class="form-control"/>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">Время начала<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="hidden">
                                                                        <form:input type="text" path="teacherId" class="form-control"/>
                                                                    </div>

                                                                    <form:input type="text" path="startTimeS" class="form-control"
                                                                                data-mask="99:99"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">Время окончания<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <form:input type="text" path="finishTimeS" class="form-control"
                                                                                data-mask="99:99"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">День недели<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <form:select id="input17" path="weekday" class="select2-select-00 col-md-4 full-width-fix">
                                                                        <c:forEach var="day" items="${days}">
                                                                            <form:option value="${day.id}">${day.name}</form:option>
                                                                        </c:forEach>
                                                                    </form:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">Класс<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <form:select id="input17" path="roomId" class="select2-select-00 col-md-4 full-width-fix">
                                                                        <c:forEach var="room" items="${rooms}">
                                                                            <form:option value="${room.id}">${room.name}</form:option>
                                                                        </c:forEach>
                                                                    </form:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-actions">
                                                    <input type="submit" value="Сохранить" class="btn btn-primary pull-left">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    </form:form>
                                    </div>
                                    <div class="tab-pane active" id="tab_2">
                                        <form action="${pageContext.request.contextPath}/tm/event/${event.id}/delete" method="post">
                                            <button class="btn btn-danger" type="submit"><i class="icon-lock"></i> Удалить занятие</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:if>
                    <c:if test="${event.id == 0}">
                        <div class="widget box">
                            <div class="widget-header">
                                <h4><i class="icon-reorder"></i>Новое занятие</h4>
                                <div class="toolbar no-padding">
                                    <div class="btn-group">
                                        <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-content no-padding">

                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <form:form class="form-horizontal row-border" method="POST"
                                                           action="${pageContext.request.contextPath}/event/new" commandName="event">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">Время начала<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="hidden">
                                                                        <form:input type="text" path="teacherId" class="form-control"/>
                                                                    </div>

                                                                    <form:input type="text" path="startTimeS" class="form-control"
                                                                                data-mask="99:99"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">Время окончания<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <form:input type="text" path="finishTimeS" class="form-control"
                                                                                data-mask="99:99"/>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">День недели<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <form:select id="input17" path="weekday" class="select2-select-00 col-md-4 full-width-fix">
                                                                        <c:forEach var="day" items="${days}">
                                                                            <form:option value="${day.id}">${day.name}</form:option>
                                                                        </c:forEach>
                                                                    </form:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label class="control-label">Класс<span class="required">*</span></label>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <form:select id="input17" path="roomId" class="select2-select-00 col-md-4 full-width-fix">
                                                                        <c:forEach var="room" items="${rooms}">
                                                                            <form:option value="${room.id}">${room.name}</form:option>
                                                                        </c:forEach>
                                                                    </form:select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-actions">
                                                    <input type="submit" value="Сохранить" class="btn btn-primary pull-left">
                                                </div>
                                                </form:form>
                                            </div>
                                        </div>
                                    </div>

                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
            <!-- /no-padding and table-colvis -->
            <!-- /Page Content -->

        </div>
    </jsp:body>
</t:general>
