<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New contract | ARTBUREAU</title>
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
                        <a href="#" title="">Новый контракт</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3><a href="${pageContext.request.contextPath}/client/${contractObj.clientId}">${contractObj.clientFS} ${contractObj.clientLS}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->


            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Новый контракт [Шаг 3]</h4>
                        </div>
                        <div class="widget-content">
                            <form:form class="form-horizontal row-border" id="validate-1" method="POST" action="${pageContext.request.contextPath}/contract?s=4" commandName="contractObj">
                                <div class="form-group">
                                    <p><b>Преподаватель</b>: ${t.teacherName}</p>
                                    <p><b>Направление</b>: ${t.typeName}</p>
                                    <p><b>Тип</b>:
                                        <c:if test="${contractObj.contractType == 1}">
                                            Индивидуальный
                                        </c:if>
                                        <c:if test="${contractObj.contractType == 2}">
                                            Групповой
                                        </c:if>
                                        <c:if test="${contractObj.contractType == 3}">
                                            Парный
                                        </c:if>
                                        <c:if test="${contractObj.contractType == 4}">
                                            Ансамбль
                                        </c:if>
                                        /
                                        <c:if test="${contractObj.trial == 1}">
                                            Пробный
                                        </c:if>
                                        <c:if test="${contractObj.trial == 0}">
                                            Абонемент
                                        </c:if>
                                    </p>
                                    <p><b>Стоимость</b>: ${price1} руб.</p>
                                    <p><b>Количество</b>: ${contractObj.countLessons}</p>
                                    <p><b>Скидка</b>: ${contractObj.discount}%</p>
                                    <p><b>Общая стоимость</b>: ${contractObj.price} руб.</p>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Расписание занятий<span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="input18" path="days" class="select2-select-00 col-md-12 full-width-fix" multiple="true" size="5">
                                            <c:forEach var="event" items="${events}">
                                                <form:option value="${event.id}">
                                                    <c:if test="${event.weekday == 1}">
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
                                                    </c:if>: ${event.startTime} - ${event.finishTime}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <input type="submit" value="Далее" class="btn btn-primary pull-right">
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div> <!-- /.col-md-12 -->
                <!-- /Example Box -->
            </div> <!-- /.row -->
            <!-- /Page Content -->

        </div>
        <!-- /.container -->
    </jsp:body>
</t:general>