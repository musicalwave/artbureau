<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New lesson | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/contract/${contract.id}/lesson" title="">Новое занятие</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3><a href="${pageContext.request.contextPath}/client/${client.id}">${client.fname} ${client.lname}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->


            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Основные параметры</h4>
                        </div>
                        <div class="widget-content">
                            <c:if test="${step == 1}">
                            <form:form class="form-horizontal row-border" id="validate-1" method="POST" action="${pageContext.request.contextPath}/contract/${contract.id}/lesson?s=1" commandName="lesson">
                                <div class="form-group">
                                    <div class="col-md-4">
                                        <div class="row">
                                            <label class="col-md-6 control-label">Преподаватель:</label>
                                            <div class="col-md-6">${contract.teacherS}</div>
                                        </div>
                                        <div class="row">
                                            <label class="col-md-6 control-label">Направление:</label>
                                            <div class="col-md-6">${contract.typeS}</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <c:if test="${error == 1}">
                                        <div class="alert alert-danger fade in">
                                            <i class="icon-remove close" data-dismiss="alert"></i>
                                            <strong>Error!</strong>
                                            Выберите другую дату.
                                        </div>
                                    </c:if>
                                    <label class="col-md-2 control-label">Дата занятия<span class="required">*</span></label>
                                    <div class="col-md-2"><form:input type="text" path="dateS" class="form-control required datepicker"/></div>
                                </div>

                                <div class="form-actions">
                                    <input type="submit" value="Далее" class="btn btn-primary pull-right">
                                </div>
                            </form:form>
                            </c:if>
                            <c:if test="${step == 2}">
                                <form:form class="form-horizontal row-border" id="validate-1" method="POST" action="${pageContext.request.contextPath}/contract/${contract.id}/lesson/save" commandName="lesson">
                                    <div class="form-group">
                                        <div class="col-md-4">
                                            <div class="row">
                                                <label class="col-md-6 control-label">Преподаватель:</label>
                                                <div class="col-md-6">${contract.teacherS}</div>
                                            </div>
                                            <div class="row">
                                                <label class="col-md-6 control-label">Направление:</label>
                                                <div class="col-md-6">${contract.typeS}</div>
                                            </div>
                                            <div class="row">
                                                <label class="col-md-6 control-label">Дата:</label>
                                                <div class="col-md-6">${lesson.dateS}</div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="hidden">
                                            <form:input class="form-control" path="dateS" type="text" value="${lesson.dateS}"/>
                                        </div>
                                        <div class="col-md-4">
                                        <label class="col-md-2 control-label">Занятие<span class="required">*</span></label>
                                        <form:select id="input17" path="eventId" class="select2-select-00 col-md-4 full-width-fix">
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
                            </c:if>
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