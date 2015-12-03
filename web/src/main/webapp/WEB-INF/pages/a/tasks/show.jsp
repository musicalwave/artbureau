<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Task | ARTBUREAU</title>
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
            <div class="col-md-12 form-vertical no-margin">
                <div class="widget">
                    <div class="widget-header">
                        <h4>Основная информация</h4>
                    </div>
                    <div class="widget-content">
                       <div class="row">
                            <div class="col-md-6">
                                <div class="row">
                                    <label class="col-md-4 control-label">Назначил:</label>
                                    <div class="col-md-8">${task.fromName}</div>
                                </div>
                                <div class="row">
                                    <label class="col-md-4 control-label">Дата назначения:</label>
                                    <div class="col-md-8">${task.hashS}</div>
                                </div>
                                <div class="row">
                                    <label class="col-md-4 control-label">Атрибуты:</label>
                                    <div class="col-md-8">
                                        <c:if test="${task.important > 0}">
                                            <i class="icon-exclamation-sign "></i>
                                        </c:if>
                                        <c:if test="${task.info > 0}">
                                            <i class="icon-info"></i>
                                        </c:if>
                                        <c:if test="${task.toId > 0}">
                                            <i class="icon-user"></i>
                                        </c:if>
                                        <c:if test="${task.done == 0}">
                                            <span class="label label-warning">Назначено</span></td>
                                        </c:if>
                                        <c:if test="${task.done == 1}">
                                            <span class="label label-success">Выполнено</span></td>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="row">
                                    <label class="col-md-4 control-label">Исполнитель:</label>
                                    <div class="col-md-8">${task.toName}${task.groupName}</div>
                                </div>
                                <div class="row">
                                    <label class="col-md-4 control-label">Дата начала:</label>
                                    <div class="col-md-8">${task.startDateS}</div>
                                </div>
                                <div class="row">
                                    <label class="col-md-4 control-label">Дата выполнения:</label>
                                    <div class="col-md-8">${task.dateS} <b>${task.timeS}</b></div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12 form-vertical no-margin">
                <div class="widget">
                    <div class="widget-header">
                        <h4>Задание</h4>
                    </div>
                    <div class="widget-content">
                        <div class="form-group">
                            <div class="col-md-12">${task.message}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12 form-vertical no-margin">
                <div class="widget">
                    <div class="widget-header">
                        <h4>Комментарий</h4>
                    </div>
                    <div class="widget-content">
                        <div class="form-group">
                            <div class="col-md-12">${task.comment}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12 form-vertical no-margin">
                <div class="widget">
                    <div class="widget-header">
                        <h4>Действия</h4>
                    </div>
                    <div class="widget-content">
                        <!-- Tabs-->
                        <div class="tabbable tabbable-custom tabs-left">
                            <!-- Only required for left/right tabs -->
                            <ul class="nav nav-tabs tabs-left">
                                <li class="active"><a href="#tab_3_4" data-toggle="tab">Добавить комментарий</a></li>
                                <li><a href="#tab_3_1" data-toggle="tab">Выполнить</a></li>
                                <li><a href="#tab_3_2" data-toggle="tab">Перенести</a></li>
                                <li><a href="#tab_3_3" data-toggle="tab">Удалить</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab_3_4">
                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/task/${task.id}/comment" commandName="task" method="POST">
                                        <div class="hidden">
                                            <form:input class="form-control" path="toId" type="text" value="${task.toId}"/>
                                            <form:input class="form-control" path="groupId" type="text" value="${task.groupId}"/>
                                            <form:input class="form-control" path="comment" type="text" value="${task.comment}"/>
                                            <form:input class="form-control" path="date" type="text" value="${task.date}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Комментарий<span class="required">*</span></label>
                                            <div class="col-md-10"><form:textarea rows="3" cols="5" path="commentNew" class="limited form-control wysiwyg required" data-limit="100"/></div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="submit" value="Добавить комментарий" class="btn btn-primary pull-right">
                                        </div>
                                    </form:form>
                                </div>
                                <div class="tab-pane" id="tab_3_1">
                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/task/${task.id}/done" commandName="task" method="POST">
                                        <div class="hidden">
                                            <form:input class="form-control" path="toId" type="text" value="${task.toId}"/>
                                            <form:input class="form-control" path="groupId" type="text" value="${task.groupId}"/>
                                            <form:input class="form-control" path="comment" type="text" value="${task.comment}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Комментарий<span class="required">*</span></label>
                                            <div class="col-md-10"><form:textarea rows="3" cols="5" path="commentNew" class="limited form-control required wysiwyg" data-limit="100"/></div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="submit" value="Выполнить" class="btn btn-primary pull-right">
                                        </div>
                                    </form:form>
                                </div>
                                <div class="tab-pane" id="tab_3_2">
                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/task/${task.id}/transfer" commandName="task" method="POST" >
                                        <div class="hidden">
                                            <form:input class="form-control" path="toId" type="text" value="${task.toId}"/>
                                            <form:input class="form-control" path="groupId" type="text" value="${task.groupId}"/>
                                            <form:input class="form-control" path="comment" type="text" value="${task.comment}"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Дата выполнения<span class="required">*</span></label>
                                            <div class="col-md-10"><form:input type="text" path="dateS" class="form-control required datepicker"/></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">Комментарий<span class="required">*</span></label>
                                            <div class="col-md-10"><form:textarea rows="3" cols="5" path="commentNew" class="limited form-control required wysiwyg" data-limit="100"/></div>
                                        </div>
                                        <div class="form-actions">
                                            <input type="submit" value="Перенести" class="btn btn-primary pull-right">
                                        </div>
                                    </form:form>
                                </div>
                                <div class="tab-pane" id="tab_3_3">
                                    <form:form class="form-horizontal" action="${pageContext.request.contextPath}/task/${task.id}/delete" commandName="task" method="POST">
                                        <div class="hidden">
                                            <form:input class="form-control" path="fromId" type="text" value="${task.fromId}"/>
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
        </div>
    </jsp:body>
</t:general>