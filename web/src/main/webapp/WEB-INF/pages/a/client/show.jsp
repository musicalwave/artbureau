<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
<jsp:attribute name="title">
        <title>Страница клиента | ARTBUREAU</title>
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
            <a href="${pageContext.request.contextPath}/client/${client.id}" title="">Страница клиента</a>
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
        <h3>${client.fname} ${client.lname} <c:if test="${client.moneyV < 0}"><b>!Необходимо добавить платеж!</b></c:if></h3>
        <span>Страница клиента</span>
    </div>
</div>
<!-- /Page Header -->

<!--=== Page Content ===-->
<div class="row">
<div class="col-md-12">
<div class="tabbable tabbable-custom tabbable-full-width">
<ul class="nav nav-tabs">
    <li class="active"><a href="#tab_overview" data-toggle="tab">Обзор</a></li>
    <li><a href="#tab_edit_account" data-toggle="tab">Редактирование</a></li>
    <li><a href="#tab_actions" data-toggle="tab">Действия</a></li>
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
                        <p>Фамилия: <b>${client.lname}</b></p>

                        <p>Имя: <b>${client.fname}</b></p>

                        <p>Отчество: <b>${client.pname}</b></p>

                        <p>Дата рождения: <b>${client.bdate}</b></p>
                    </div>
                    <div class="col-md-4">
                        <p>Телефон 1: <b>${client.phone1} ${client.phone1name}</b></p>

                        <p>Телефон 2: <b>${client.phone2} ${client.phone2name}</b></p>

                        <p>E-mail: <b>${client.email}</b></p>

                        <p>Концерты: <b>${client.concerts}</b></p>
                    </div>
                    <div class="col-md-4">
                        <p>Комментарий: <b>${client.comment}</b>
                        </p>
                        <p>Референт:
                        </p>
                    </div>
                </div>
                <div class="tab-pane" id="tab_2">
                    <div class="col-md-3">
                        <p>На счету:</p>

                        <p><b>${client.moneyR}</b></p>

                        <p>Свободные:</p>

                        <p><b>${client.moneyV}</b></p>

                        <p>

                        <form action="#" method="GET">
                            <button type="submit" class="btn btn-success">Полная статистика</button>
                        </form>
                        </p>
                    </div>
                    <div class="col-md-9">
                        <div id="chart_bars_vertical" class="chart"></div>
                    </div>
                </div>
                <div class="tab-pane" id="tab_3">
                    <c:if test="${client.hasJdata == 1}">
                        <p><a href="${pageContext.request.contextPath}/tm/jdata/${client.id}" class="btn btn-xs btn-success">Изменить</a></p>
                    </c:if>
                    <c:if test="${client.hasJdata == 0}">
                        <p><a href="${pageContext.request.contextPath}/tm/jdata/${client.id}" class="btn btn-xs btn-success">Добавить</a></p>
                    </c:if>
                </div>
            </div>
        </div>
        <!--END TABS-->
    </div>
</div>
<div class="widget box">
    <div class="widget-header">
        <h4>Действующие контракты [<a href="${pageContext.request.contextPath}/contract?client=${client.id}&prev=0">+</a>] [<a href="${pageContext.request.contextPath}/client/${client.id}/contracts">Все</a>]</h4>

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
                <th>Преподаватель</th>
                <th>Направление</th>
                <th>Дата начала</th>
                <th>Переносы</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="contract" items="${contracts}">
                <tr>
                    <td>
                        <c:if test="${contract.freezed == 0}">
                            <span class="label label-success">Активный</span>
                        </c:if>
                        <c:if test="${contract.freezed == 1}">
                            <span class="label label-info">Заморозка</span>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/teacher/${contract.teacherId}">${contract.teacherS}</a></td>
                    <td>${contract.typeS}</td>
                    <td>${contract.dateS}</td>
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
        <h4>Запланированные платежи [<a href="${pageContext.request.contextPath}/client/${client.id}/payment?c=all">+</a>] [<a href="${pageContext.request.contextPath}/client/${client.id}/payments">Все</a>]</h4>

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
                <th>Дата</th>
                <th>Сумма</th>
                <th>Контракт</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="payment" items="${payments}">
                <tr>
                    <td><span class="label label-info">Planned</span></td>
                    <td>${payment.dateS}</td>
                    <td>${payment.value}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/contract/${payment.contractId}">[${payment.typeS}] ${payment.teacherS}</a>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/payment/${payment.id}"
                           class="btn btn-xs btn-success">Подробнее</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="widget box">
    <div class="widget-header">
        <h4>Звонки [<a href="${pageContext.request.contextPath}/call?client=${client.id}">+</a>]</h4>

        <div class="toolbar no-padding">
            <div class="btn-group">
                <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
            </div>
        </div>
    </div>
    <div class="widget-content no-padding">
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th>Дата</th>
                <th>Статус</th>
                <th>Преподаватель</th>
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
                    <td>${call.teacherName}</td>
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
<div class="tab-pane" id="tab_edit_account">
    <form:form class="form-horizontal" method="POST" id="validate-2" action="${pageContext.request.contextPath}/client"
               commandName="client">
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
                                <label class="col-md-4 control-label">Фамилия<span class="required">*</span>:</label>

                                <div class="col-md-8"><form:input type="text" path="lname" class="form-control required"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">Имя<span class="required">*</span>:</label>

                                <div class="col-md-8"><form:input type="text" path="fname" class="form-control required"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label">Отчество:</label>

                                <div class="col-md-8"><form:input type="text" path="pname" class="form-control"/></div>
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
                                                                     class="auto form-control"/></div>
                            </div>
                        </div>


                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-6 control-label">Телефон 1<span class="required">*</span>:</label>

                                <div class="col-md-6"><form:input type="text" path="phone1" class="form-control required"
                                                                  data-mask="+7 (999) 999-9999"
                                                                  placeholder="+7 (900) 000-0000" maxlength="40"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-6 control-label">Телефон 2:</label>

                                <div class="col-md-6"><form:input type="text" path="phone2" class="form-control"
                                                                  data-mask="+7 (999) 999-9999"
                                                                  placeholder="+7 (900) 000-0000" maxlength="40"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-6 control-label">E-mail:</label>

                                <div class="col-md-6"><form:input type="text" path="email" class="form-control email"
                                                                  placeholder="example@mail.ru"/></div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <div class="col-md-12">
                                    <form:input type="text" path="phone1name" class="form-control" placeholder="Комментарий" maxlength="40"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <form:input type="text" path="phone2name" class="form-control" placeholder="Комментарий" maxlength="40"/>
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
<div class="tab-pane" id="tab_actions">
    <form action="${pageContext.request.contextPath}/tm/client/${client.id}/delete" method="post">
        <button class="btn btn-danger" type="submit"><i class="icon-lock"></i> Удалить клиента</button>
    </form>
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
