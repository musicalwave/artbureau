<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Edit Call | ARTBUREAU</title>
    </jsp:attribute>
    <jsp:attribute name="addimports">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/calls.js"></script>
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
                        <a href="#" title="">Edit call</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Звонок ${call.callDateS}</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <form:form class="form-horizontal row-border" method="POST" action="${pageContext.request.contextPath}/call?action=update" commandName="call">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Клиент</h4>
                        </div>
                        <div class="widget-content">
                                <div class="form-group">

                                        <label class="col-md-2 control-label">Имя:</label>
                                        <div class="col-md-4">
                                            <div class="hidden">
                                                <form:input class="form-control" path="clientId" type="text" value="${call.clientId}"/>
                                                <form:input class="form-control" path="callId" type="text" value="${call.callId}"/>
                                            </div>
                                            <form:input class="form-control input-width-large" path="clientFname" type="text" placeholder="Имя" value="${call.clientFname}" style="display: block; margin-top: 6px;" readonly="true"/>
                                            <form:input class="form-control input-width-large" path="clientLname" type="text" placeholder="Фамилия" value="${call.clientLname}" style="display: block; margin-top: 6px;" readonly="true"/>
                                        </div>
                                        <label class="col-md-2 control-label">Телефоны:</label>
                                        <div class="col-md-4">
                                            <form:input type="text" class="form-control" path="clientPhone1" data-mask="+7 (999) 999-9999" placeholder="+7 (915) 123-4567" value="${call.clientPhone1}" style="display: block; margin-top: 6px;" readonly="true"/>
                                            <form:input type="text" class="form-control" path="clientPhone2" data-mask="+7 (999) 999-9999" placeholder="+7 (495) 765-4321" value="${call.clientPhone2}" style="display: block; margin-top: 6px;" readonly="true"/>
                                        </div>
                                </div>
                                <div class="form-group">
                                        <label class="col-md-2 control-label">Комментарий клиента:</label>
                                        <div class="col-md-10">
                                            <form:textarea path="clientComment" rows="5" cols="5" class="form-control wysiwyg" placeholder="Client comment"/>
                                        </div>
                                </div>
                        </div>
                    </div>
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Звонок</h4>
                        </div>
                        <div class="widget-content">

                            <div class="form-group">
                                <label class="col-md-2 control-label">Комментарий</label>
                                <div class="col-md-10">
                                    <form:textarea path="callComment" rows="5" cols="5" class="form-control wysiwyg" readonly="true"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Добавить комментарий</label>
                                <div class="col-md-10">
                                    <form:textarea path="callCommentNew" rows="3" cols="5" class="form-control wysiwyg" placeholder="Call comment"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Рекламный источник</label>
                                <div class="col-md-10">
                                    <form:select id="input17" path="callAdId" class="select2-select-00 col-md-10 full-width-fix">
                                        <c:forEach var="ad" items="${ads}">
                                            <form:option value="${ad.id}">${ad.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Направления</label>
                                <div class="col-md-10">
                                    <form:select id="call-type-select" path="callTypeIds" class="select2-select-00 col-md-10 full-width-fix" multiple="true">
                                        <c:forEach var="type" items="${types}">
                                            <form:option value="${type.id}">${type.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Преподаватель</label>
                                <div class="col-md-10">
                                    <form:select id="call-teacher-select" path="callTeacherId" class="select2-select-00 col-md-10 full-width-fix">
                                        <c:forEach var="teacher" items="${teachers}">
                                            <form:option value="${teacher.id}">${teacher.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Статус</label>
                                <div class="col-md-10">
                                    <form:select id="input17" path="callStatusId" class="select2-select-00 col-md-10 full-width-fix">
                                        <c:forEach var="status" items="${callStatus}">
                                            <form:option value="${status.id}">${status.name}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>

                            <div class="form-actions">
                                <input type="reset" value="Отмена" class="btn btn-primary pull-left" onclick="history.back();">
                                <input type="submit" value="Сохранить" class="btn btn-primary pull-right">
                            </div>
                        </div>
                    </div>
                    </form:form>
                </div> <!-- /.col-md-12 -->
                <!-- /Example Box -->
            </div> <!-- /.row -->
            <!-- /Page Content -->

        </div>
    </jsp:body>
</t:general>
