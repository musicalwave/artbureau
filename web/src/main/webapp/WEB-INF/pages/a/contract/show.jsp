<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Contract | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/contract/${ccontract.id}" title="">Контракт</a>
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
                    <h3>Контракт ${contract.teacherS} [${contract.typeS}]</h3>
                    <span>${contract.clientLS} ${contract.clientFS}</span>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <div class="col-md-12">


                    <div class="tabbable tabbable-custom tabbable-full-width">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#tab_overview" data-toggle="tab">Overview</a></li>
                            <li><a href="#tab_edit" data-toggle="tab">Edit Contract</a></li>
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
                                        <div class="row">
                                            <div class="col-md-4">
                                                <p>Клиент: <b><a href="${pageContext.request.contextPath}/client/${contract.clientId}">${contract.clientLS} ${contract.clientFS}</a></b></p>

                                                <p>Педагог: <b><a href="${pageContext.request.contextPath}/teacher/${contract.teacherId}">${contract.teacherS}</a></b></p>

                                                <p>Направление: <b>${contract.typeS}</b></p>

                                                <p>Дата начала: <b>${contract.dateS}</b></p>

                                                <p>Число занятий: <b>${contract.countLessons}</b></p>
                                            </div>
                                            <div class="col-md-4">
                                                <p>Стоимость: <b>${contract.price}</b></p>

                                                <p>Запланировано: <b>${contract.moneyV}</b></p>

                                                <p>Необходимо: <b>${contract.moneyNeed}</b></p>

                                                <p>Переведено: <b>${contract.moneyR}</b></p>
                                            </div>
                                            <div class="col-md-4">
                                                <p>Переносы: <b>${contract.countShifts}</b>
                                                </p>
                                                <p>Оплата: <b>
                                                    <c:if test="${contract.cash == 0}">
                                                        <h5 style="color:red">Не установлено</h5>
                                                    </c:if>
                                                    <c:if test="${contract.cash == 1}">
                                                        Наличные
                                                    </c:if>
                                                    <c:if test="${contract.cash == 2}">
                                                        Чек
                                                    </c:if>
                                                           </b>
                                                </p>
                                                <p>Тип: <b><c:if test="${contract.special == 1}">
                                                        Специальный
                                                    </c:if>
                                                    <c:if test="${contract.special == 2}">
                                                        Стандартный
                                                    </c:if></b>
                                                </p>
                                                <p><b><c:if test="${contract.freezed == 1}">
                                                    Заморожен до ${contract.freezeDateS}
                                                </c:if></b>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="widget box">
                                    <div class="widget-header">
                                        <h4>Занятия [<a href="${pageContext.request.contextPath}/contract/${contract.id}/lesson">+</a>]</h4>

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
                                                <th>Дата</th>
                                                <th></th>
                                                <th>Статус</th>
                                                <th>Класс</th>
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="lesson" items="${lessons}">
                                                <tr>
                                                    <td>
                                                        ${lesson.dateS} ${lesson.startTime} - ${lesson.finishTime}
                                                    </td>
                                                    <td></td>
                                                    <td>${lesson.statusName}</td>
                                                    <td>${lesson.roomName}</td>
                                                    <td>
                                                        <c:if test="${lesson.statusId == 1}">
                                                            <form:form class="form-horizontal" action="${pageContext.request.contextPath}/lesson/do" commandName="l" method="POST">
                                                                <div class="hidden">
                                                                    <form:input class="form-control" path="id" type="text" value="${lesson.id}"/>
                                                                </div>
                                                                <input type="submit" value="Провести" class="btn btn-xs">
                                                            </form:form>
                                                            <form:form class="form-horizontal" action="${pageContext.request.contextPath}/lesson/burn" commandName="l" method="POST">
                                                                <div class="hidden">
                                                                    <form:input class="form-control" path="id" type="text" value="${lesson.id}"/>
                                                                </div>
                                                                <input type="submit" value="Сжечь" class="btn btn-xs">
                                                            </form:form>
                                                            <%--<form:form class="form-horizontal" action="${pageContext.request.contextPath}/lesson/burn" commandName="l" method="POST">--%>
                                                                <%--<div class="hidden">--%>
                                                                    <%--<form:input class="form-control" path="id" type="text" value="${lesson.id}"/>--%>
                                                                <%--</div>--%>
                                                                <%--<input type="submit" value="Перенести" class="btn btn-xs">--%>
                                                            <%--</form:form>--%>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="widget box">
                                    <div class="widget-header">
                                        <h4>Платежи [<a href="${pageContext.request.contextPath}/client/${client.id}/payment?c=${contract.id}">+</a>]</h4>

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
                                                <th></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="payment" items="${payments}">
                                                <tr>
                                                    <td>
                                                        <c:if test="${payment.planned == 1}">
                                                            <span class="label label-info">Запланирован</span>
                                                        </c:if>
                                                        <c:if test="${payment.done == 1}">
                                                            <span class="label label-warning">Проведен</span>
                                                        </c:if>
                                                        <c:if test="${payment.approved == 1}">
                                                            <span class="label label-danger">Подтвержден</span>
                                                        </c:if>
                                                    </td>
                                                    <td>${payment.dateS}</td>
                                                    <td>${payment.value}</td>
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
                                        <h4>Действия</h4>
                                    </div>
                                    <div class="widget-content">
                                        <!-- Tabs-->
                                        <div class="tabbable tabbable-custom tabs-left">
                                            <!-- Only required for left/right tabs -->
                                            <ul class="nav nav-tabs tabs-left">
                                                <c:if test="${contract.cash == 0}">
                                                <li class="active"><a href="#tab_3_1" data-toggle="tab">Установить тип</a></li>
                                                <li><a href="#tab_3_2" data-toggle="tab">Заморозить</a></li>
                                                </c:if>
                                                <c:if test="${contract.cash != 0}">
                                                <li class="active"><a href="#tab_3_2" data-toggle="tab">Заморозить</a></li>
                                                </c:if>
                                                <li><a href="#tab_3_3" data-toggle="tab">Продлить</a></li>
                                                <li><a href="#tab_3_4" data-toggle="tab">Удалить</a></li>
                                            </ul>
                                            <div class="tab-content">
                                                <c:if test="${contract.cash == 0}">
                                                <div class="tab-pane active" id="tab_3_1">
                                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/contract/cash" commandName="contract" method="POST">
                                                        <div class="hidden">
                                                            <form:input class="form-control" path="id" type="text" value="${contract.id}"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <form:select id="input17" path="cash" class="select2-select-00 col-md-4 full-width-fix">
                                                                <form:option value="1">Наличные</form:option>
                                                                <form:option value="2">Чек</form:option>
                                                            </form:select>
                                                        </div>
                                                        <div class="form-actions">
                                                            <input type="submit" value="Применить" class="btn btn-primary pull-right">
                                                        </div>
                                                    </form:form>
                                                </div>
                                                <div class="tab-pane" id="tab_3_2">
                                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/contract/freeze" commandName="contract" method="POST">
                                                        <div class="hidden">
                                                            <form:input class="form-control" path="id" type="text" value="${contract.id}"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-2 control-label">Дата начала заморозки<span class="required">*</span></label>
                                                            <div class="col-md-10"><form:input type="text" path="freezeDateS" class="form-control required datepicker"/></div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-2 control-label">Дата окончания заморозки<span class="required">*</span></label>
                                                            <div class="col-md-10"><form:input type="text" path="freezeFinishDateS" class="form-control required datepicker"/></div>
                                                        </div>
                                                        <div class="form-actions">
                                                            <input type="submit" value="Заморозить" class="btn btn-primary pull-right">
                                                        </div>
                                                    </form:form>
                                                </div>
                                                </c:if>
                                                <c:if test="${contract.cash != 0}">
                                                <div class="tab-pane active" id="tab_3_2">
                                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/contract/freeze" commandName="contract" method="POST">
                                                        <div class="hidden">
                                                            <form:input class="form-control" path="id" type="text" value="${contract.id}"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-2 control-label">Дата начала заморозки<span class="required">*</span></label>
                                                            <div class="col-md-10"><form:input type="text" path="freezeDateS" class="form-control required datepicker"/></div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-2 control-label">Дата окончания заморозки<span class="required">*</span></label>
                                                            <div class="col-md-10"><form:input type="text" path="freezeFinishDateS" class="form-control required datepicker"/></div>
                                                        </div>
                                                        <div class="form-actions">
                                                            <input type="submit" value="Заморозить" class="btn btn-primary pull-right">
                                                        </div>
                                                    </form:form>
                                                </div>
                                                </c:if>
                                                <div class="tab-pane" id="tab_3_3">
                                                    <div class="form-actions">
                                                        <a href="${pageContext.request.contextPath}/contract?client=${contract.clientId}&prev=${contract.id}"
                                                           class="btn btn-primary pull-right">Продление</a>
                                                    </div>
                                                </div>
                                                <div class="tab-pane" id="tab_3_4">
                                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/contract/${contract.id}/delete" commandName="contract" method="POST">
                                                        <div class="hidden">
                                                            <form:input class="form-control" path="id" type="text" value="${task.fromId}"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-2 control-label">Комментарий<span class="required">*</span></label>
                                                            <div class="col-md-10"><form:textarea rows="3" cols="5" path="comment" class="limited form-control required wysiwyg" data-limit="100"/></div>
                                                        </div>
                                                        <div class="form-actions">
                                                            <input type="submit" value="Удалить" class="btn btn-primary pull-right">
                                                        </div>
                                                    </form:form>
                                                </div>
                                            </div>
                                        </div>
                                        <!--END TABS-->
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane" id="tab_edit">
                                <%--<form:form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/contract"
                                           commandName="contract">
                                    <div class="col-md-12">
                                        <div class="widget">
                                            <div class="widget-header">
                                                <h4>Основная информация</h4>
                                            </div>
                                            <div class="widget-content">
                                                <div class="row">
                                                        &lt;%&ndash;<div class="col-md-6">&ndash;%&gt;
                                                        &lt;%&ndash;<div class="hidden">&ndash;%&gt;
                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-4 control-label">id:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-8"><form:input type="text" path="id" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="256"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-4 control-label">Фамилия<span class="required">*</span>:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-8"><form:input type="text" path="lname" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="Константинопольский"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-4 control-label">Имя<span class="required">*</span>:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-8"><form:input type="text" path="fname" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="Константин"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-4 control-label">Отчество:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-8"><form:input type="text" path="pname" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="Константинович"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-4 control-label">Дата рождения:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-8"><form:input type="text" path="bdate" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;data-mask="99/99/9999"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="15/10/1992"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-4 control-label">Комментарий:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-8"><form:textarea rows="3" cols="5" path="comment"&ndash;%&gt;
                                                        &lt;%&ndash;class="auto form-control"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="Client comment"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;


                                                        &lt;%&ndash;<div class="col-md-4">&ndash;%&gt;
                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-6 control-label">Телефон 1<span class="required">*</span>:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-6"><form:input type="text" path="phone1" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;data-mask="+7 (999) 999-9999"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="+7 (915) 123-4567"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-6 control-label">Телефон 2:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-6"><form:input type="text" path="phone2" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;data-mask="+7 (999) 999-9999"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="+7 (915) 123-4567"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<label class="col-md-6 control-label">E-mail:</label>&ndash;%&gt;

                                                        &lt;%&ndash;<div class="col-md-6"><form:input type="text" path="email" class="form-control"&ndash;%&gt;
                                                        &lt;%&ndash;placeholder="example@mail.ru"/></div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;<div class="col-md-2">&ndash;%&gt;
                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<div class="col-md-12">&ndash;%&gt;
                                                        &lt;%&ndash;<form:input type="text" path="phone1name" class="form-control" placeholder="Имя"/>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;<div class="form-group">&ndash;%&gt;
                                                        &lt;%&ndash;<div class="col-md-12">&ndash;%&gt;
                                                        &lt;%&ndash;<form:input type="text" path="phone2name" class="form-control" placeholder="Имя"/>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
                                                        &lt;%&ndash;</div>&ndash;%&gt;
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
                                </form:form>--%>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:general>