<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New contract | ARTBUREAU</title>
    </jsp:attribute>

    <jsp:attribute name="addimports">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/contract.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/contract.js"></script>
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
                        <a href="" title="">Новый контракт</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3><a href="${pageContext.request.contextPath}/client/${contract.clientId}">${contract.clientFS} ${contract.clientLS}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->


            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Новый контракт</h4>
                        </div>
                        <div class="widget-content">
                            <form:form class="form-horizontal row-border" id="new-contract-form" method="POST" action="${pageContext.request.contextPath}/contract/save" commandName="contract">

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Направление<span class="required">*</span></label>

                                    <div class="col-md-2">
                                        <form:select id="lesson-type" path="typeId" class="select2-select-00 full-width-fix">
                                            <c:forEach var="type" items="${types}">
                                                <form:option value="${type.id}">${type.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Тип<span class="required">*</span></label>

                                    <div class="col-md-2">
                                        <form:select id="contract-type" path="contractType" class="select2-select-00 full-width-fix">
                                            <c:forEach var="contractType" items="${contractTypes}">
                                            <form:option value="${contractType.id}">${contractType.name}</form:option>
                                            </c:forEach>
                                        </form:select>

                                        <form:select id="trial" path="trial" class="select2-select-00 full-width-fix">
                                            <form:option value="0">Абонемент</form:option>
                                            <form:option value="1">Пробный</form:option>
                                        </form:select>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Количество занятий<span class="required">*</span></label>

                                    <div class="col-md-2">
                                        <form:input id="lesson-count" type="number" path="countLessons" class="form-control required digits" min="1" max="20"/>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Педагог<span class="required">*</span></label>

                                    <div class="col-md-2">
                                        <form:select id="teacher-type" path="teacherTypeId" class="select2-select-00 full-width-fix">
                                            <c:forEach var="teacherType" items="${teacherTypes}">
                                                <form:option value="${teacherType.id}">${teacherType.teacherName}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Дата начала<span class="required">*</span></label>

                                    <div class="col-md-2">
                                        <form:input id="first-lesson-date" type="text" path="dateS" class="form-control required full-width-fix"/>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Расписание<span class="required">*</span></label>

                                    <div class="col-md-6">
                                        <form:select id="schedule" path="days" class="select2-select-00 required full-width-fix" multiple="true" size="5">
                                            <c:forEach var="event" items="${emptyEvents}">
                                                <form:option value="${event.id}">
                                                    ${event.weekdayS} : ${event.startTime} - ${event.finishTime}
                                                </form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Стоимость одного занятия</label>

                                    <div class="col-md-2">
                                        <input id="single-lesson-price" type="text" class="form-control" value="${singleLessonPrice}" readonly/>
                                    </div>

                                    <label class="col-md-2 control-label currency-label"/>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Cкидка (в процентах)<span class="required">*</span></label>

                                    <div class="col-md-2">
                                        <form:select id="discount" path="discount" class="select2-select-00 full-width-fix">
                                            <c:forEach var="discount" items="${discounts}">
                                                <form:option value="${discount.id}">${discount.name} [${discount.id} %]</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>

                                </div>

                                <div class="form-group">

                                    <label class="col-md-2 control-label">Общая сумма</label>

                                    <div class="col-md-2">
                                        <form:input id="total" type="text" path="price" class="form-control" readonly="true"/>
                                    </div>

                                    <label class="col-md-2 control-label currency-label"/>

                                </div>

                                <div class="form-actions">
                                    <input type="submit" value="Создать контракт" class="btn btn-primary pull-right">
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
