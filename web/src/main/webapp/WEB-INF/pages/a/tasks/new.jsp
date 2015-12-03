<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New Task | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/tasks" title="">Задания</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Новое задание</h4>
                        </div>
                        <div class="widget-content">
                            <form:form class="form-horizontal row-border" id="validate-1" method="POST" action="${pageContext.request.contextPath}/task" commandName="task">
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="hidden">
                                            <form:input class="form-control" path="fromName" type="text" value="${username}"/>
                                            <form:input class="form-control" path="fromId" type="text" value="${userid}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Задание<span class="required">*</span></label>
                                    <div class="col-md-10"><form:textarea rows="3" cols="5" path="message" class="limited form-control wysiwyg required" data-limit="500"/></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Дата начала<span class="required">*</span></label>
                                    <div class="col-md-2"><form:input type="text" path="startDate" class="form-control required datepicker"/></div>

                                    <label class="col-md-2 control-label">Дата выполнения<span class="required">*</span></label>
                                    <div class="col-md-2"><form:input type="text" path="date" class="form-control required datepicker"/></div>

                                    <label class="col-md-2 control-label">Время выполнения</label>
                                    <div class="col-md-2"><form:input type="text" path="time" class="form-control" data-mask="99:99"/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label">Клиент</label>
                                    <div class="col-md-4"><form:input type="text" path="clientName" class="form-control"/></div>

                                    <label class="col-md-2 control-label">Преподаватель</label>
                                    <div class="col-md-4"><form:input type="text" path="teacherName" class="form-control"/></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Исполнители<span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="input18" path="toIds" class="select2-select-00 col-md-12 full-width-fix" multiple="true" size="5">
                                            <c:forEach var="user" items="${users}">
                                                <form:option value="${user.id}">${user.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Группа исполнителей<span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="input18" path="toGroups" class="select2-select-00 col-md-12 full-width-fix" multiple="true" size="5">
                                            <c:forEach var="group" items="${groups}">
                                                <form:option value="${group.id}">${group.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label">Атрибуты</label>
                                    <div class="col-md-10">
                                        <label class="checkbox-inline">
                                            <span class="label label-danger">Важно</span><form:checkbox path="important" value="true"/>
                                        </label>
                                        <label class="checkbox-inline">
                                            <span class="label label-info">Инфо</span><form:checkbox path="info" value="true"/>
                                        </label>
                                        <label class="checkbox-inline">
                                            <span class="label label-warning">Рассылка</span><form:checkbox path="mail" value="true" checked="true"/>
                                        </label>
                                    </div>
                                </div>


                                <div class="form-actions">
                                    <input type="reset" value="Отмена" class="btn btn-primary pull-left" onclick="history.back();">
                                    <input type="submit" value="Сохранить" class="btn btn-primary pull-right">
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