<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
<jsp:attribute name="title">
    <title>Teacher | ARTBUREAU</title>
</jsp:attribute>
<jsp:attribute name="addimports">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/tables.css"/>
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
            <a href="${pageContext.request.contextPath}/teacher/${teacher.id}" title="">Преподаватель</a>
        </li>
    </ul>

    <!-- <ul class="crumb-buttons">
        <li><a href="charts.html" title=""><i class="icon-signal"></i><span>Statistics</span></a></li>
    </ul> -->
</div>
<!-- /Breadcrumbs line -->

<!--=== Page Header ===-->
<div class="page-header">
    <div class="page-title">
        <h3>${teacher.name}</h3>
        <span>Страница преподавателя</span>
    </div>
</div>
<!-- /Page Header -->

<!--=== Page Content ===-->
<div class="row">
<div class="col-md-12">
<div class="tabbable tabbable-custom tabbable-full-width">
<ul class="nav nav-tabs">
    <li class="active"><a href="#tab_overview" data-toggle="tab">Overview</a></li>
    <li><a href="#tab_edit_account" data-toggle="tab">Edit Account</a></li>
</ul>
<div class="tab-content row">
<div class="tab-pane active" id="tab_overview">
    <div class="widget box">
        <div class="widget-header">
            <h4>Информация</h4>

            <div class="toolbar no-padding">
                <div class="btn-group">
                    <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                </div>
            </div>
        </div>
        <div class="widget-content">
            <!-- Tabs-->
            <div class="tabbable tabbable-custom tabs-left">
                <!-- Only required for left/right tabs -->
                <ul class="nav nav-tabs tabs-left">
                    <li class="active"><a href="#tab_1" data-toggle="tab">Персональная</a></li>
                    <li><a href="#tab_2" data-toggle="tab">Финансовая</a></li>
                    <li><a href="#tab_3" data-toggle="tab">Юридическая</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="tab_1">
                        <div class="col-md-4">
                            <p>ФИО: <b>${teacher.name}</b></p>

                            <p>Дата рождения: <b>${teacher.bdate}</b></p>

                            <p>Направления:
                                <b>
                                    <c:forEach var="type" items="${types}">
                                        <br>${type.name}
                                    </c:forEach>
                                </b></p>
                        </div>
                        <div class="col-md-4">
                            <p>Телефон: <b>${teacher.phone}</b></p>

                            <p>Концерты: <b>0</b></p>
                        </div>
                        <div class="col-md-4">
                            <p>Комментарий: <b>${teacher.comment}</b>
                            </p>
                        </div>
                    </div>
                    <div class="tab-pane" id="tab_2">
                        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/finance/t/${teacher.id}" commandName="dates" method="POST">
                        <div class="col-md-7">
                            <table id="hor-minimalist-a" summary="Prices Sheet">
                                <thead>
                                <tr>
                                    <th scope="col">Направление</th>
                                    <th scope="col">Индивидуальное</th>
                                    <th scope="col">Парное</th>
                                    <th scope="col">Групповое</th>
                                    <th scope="col">Действия</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="type" items="${types}">
                                <tr><td>${type.name}</td><td>${type.pPrice}</td><td>${type.dPrice}</td><td>${type.gPrice}</td><td><a href="${pageContext.request.contextPath}/tm/teacher/cp?te=${teacher.id}&ty=${type.id}">Изменить</a></td></tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="col-md-5">
                            <div class="form-group">
                                <form:input type="text" path="startdate" class="form-control required datepicker"/>
                                <form:input type="text" path="finishdate" class="form-control required datepicker"/>
                            </div>
                            <div class="form-actions">
                                <input type="submit" value="Рассчитать" class="btn btn-primary pull-right">
                            </div>
                        </div>
                        </form:form>
                    </div>
                    <div class="tab-pane" id="tab_3">
                        <c:if test="${teacher.hasCred == 1}">
                            <p><a href="${pageContext.request.contextPath}/tm/cred/${teacher.id}" class="btn btn-xs btn-success">Изменить</a></p>
                        </c:if>
                        <c:if test="${teacher.hasCred == 0}">
                            <p><a href="${pageContext.request.contextPath}/tm/cred/${teacher.id}" class="btn btn-xs btn-success">Добавить</a></p>
                        </c:if>
                    </div>
                </div>
            </div>
            <!--END TABS-->
        </div>
    </div>
    <div class="widget box">
        <div class="widget-header">
            <h4>Действующие контракты [<a href="${pageContext.request.contextPath}/teacher/${teacher.id}/contracts">Все</a>]</h4>

            <div class="toolbar no-padding">
                <div class="btn-group">
                    <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                </div>
            </div>
        </div>
        <div class="widget-content">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>Статус</th>
                    <th>Ученик</th>
                    <th>Переносы</th>
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
                        <td>${contract.countShifts}</td>
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
    <div class="widget box">
        <div class="widget-header">
            <h4>Расписание [<a href="${pageContext.request.contextPath}/teacher/${teacher.id}/events">+</a>]</h4>

            <div class="toolbar no-padding">
                <div class="btn-group">
                    <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                </div>
            </div>
        </div>
        <div class="widget-content">
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

</div>

<div class="tab-pane" id="tab_edit_account">
    <form:form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/teacher"
               commandName="teacher">
        <div class="col-md-12">
            <div class="widget">
                <div class="widget-header">
                    <h4>Основная информация</h4>
                </div>
                <div class="widget-content">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="hidden">
                                <div class="form-group">
                                    <label class="col-md-4 control-label">id:</label>

                                    <div class="col-md-8"><form:input type="text" path="id" class="form-control"
                                                                      placeholder="256"/></div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">ФИО<span class="required">*</span>:</label>

                                <div class="col-md-8"><form:input type="text" path="name" class="form-control"
                                                                  placeholder="Петров Иван"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">Дата рождения:</label>

                                <div class="col-md-8"><form:input type="text" path="bdate" class="form-control"
                                                                  data-mask="99/99/9999"
                                                                  placeholder="15/10/1992"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">Комментарий:</label>

                                <div class="col-md-8"><form:textarea rows="3" cols="5" path="comment"
                                                                     class="auto form-control"
                                                                     placeholder="Client comment"/></div>
                            </div>
                        </div>


                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-6 control-label">Телефон<span class="required">*</span>:</label>

                                <div class="col-md-6"><form:input type="text" path="phone" class="form-control"
                                                                  data-mask="+7 (999) 999-9999"
                                                                  placeholder="+7 (915) 123-4567"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-6 control-label">Направления:</label>
                                <div class="col-md-6">
                                    <form:select multiple="multiple" class="multiselect" path="types">
                                        <c:forEach var="type" items="${allTypes}">
                                            <option value="${type.id}" <c:if test="${type.active == 1}">selected</c:if>>${type.name}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.widget-content -->
            </div>
            <!-- /.widget -->
            <div class="form-actions">
                <input type="submit" value="Update Account" class="btn btn-primary pull-right">
            </div>
        </div>
        <!-- /.col-md-12 -->
    </form:form>
</div>
</div>
</div>
</div>
<!-- /.col-md-12 -->
<!-- /Example Box -->
</div>
<!-- /.row -->
<!-- /Page Content -->
</div>
<!-- /.container -->
</jsp:body>
</t:general>
