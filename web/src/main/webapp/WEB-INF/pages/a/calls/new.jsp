<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New Call | ARTBUREAU</title>
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
                        <a href="#" title="">New call</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Новый звонок</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <form:form class="form-horizontal row-border" method="POST" id="validate-1" action="${pageContext.request.contextPath}/call?action=save" commandName="call">
                        <div class="widget box">
                            <div class="widget-header">
                                <h4><i class="icon-reorder"></i>Клиент</h4>
                            </div>
                            <div class="widget-content">
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-6">
                                                 <div class="form-group">
                                                    <label class="col-md-4 control-label">ФИО <span class="required">*</span></label>

                                                    <div class="col-md-8">
                                                        <form:input type="text" path="clientLname" class="form-control" placeholder="Фамилия"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label class="col-md-4 control-label">Контакты <span class="required">*</span></label>
                                                    <div class="col-md-8">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <form:input type="text" class="form-control required" path="clientPhone1" data-mask="+7 (999) 999-9999" placeholder="+7 (900) 000-0000"/>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <form:input type="text" class="form-control" path="clientPhone1name" placeholder="Комментарий"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-2"></div>
                                            <div class="col-md-4">
                                                <form:input type="text" path="clientFname" class="form-control required" placeholder="Имя"/>
                                            </div>
                                            <div class="col-md-2"></div>
                                            <div class="col-md-4">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <form:input type="text" class="form-control" path="clientPhone2" data-mask="+7 (999) 999-9999" placeholder="+7 (900) 000-0000"/>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <form:input type="text" class="form-control" path="clientPhone2name" placeholder="Комментарий"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-2"></div>
                                            <div class="col-md-4">
                                                <form:input type="text" path="clientPname" class="form-control" placeholder="Отчество"/>
                                            </div>
                                            <div class="col-md-2"></div>
                                            <div class="col-md-4">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <form:input type="text" path="clientMail" class="form-control" placeholder="example@mail.ru"/>
                                                    </div>
                                                    <div class="col-md-6"></div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="widget box">
                            <div class="widget-header">
                                <h4><i class="icon-reorder"></i>Звонок</h4>
                            </div>
                            <div class="widget-content">
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Комментарий к звонку <span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:textarea path="callComment" rows="3" cols="5" class="form-control required wysiwyg" placeholder="О чем был разговор..."/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label">Рекламный источник <span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="input17" path="callAdId" class="select2-select-00 col-md-10 full-width-fix">
                                            <c:forEach var="ad" items="${ads}">
                                                <form:option value="${ad.id}">${ad.name}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-2 control-label">Направления <span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="call-type-select" path="callTypeIds" class="select2-select-00 col-md-10 full-width-fix required" multiple="true">
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
                                    <label class="col-md-2 control-label">Статус <span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="input17" path="callStatusId" class="select2-select-00 col-md-10 full-width-fix required">
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
