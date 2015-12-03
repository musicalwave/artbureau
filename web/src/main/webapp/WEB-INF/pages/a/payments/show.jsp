<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Payment | ARTBUREAU</title>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="crumbs">
                <ul id="breadcrumbs" class="breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="${pageContext.request.contextPath}/">Dashboard</a>
                    </li>
                    <li class="current">
                        <a href="#" title="">Payment</a>
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
                    <h3>Платеж</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4>Информация</h4>
                        </div>
                        <div class="widget-content">
                            <div class="row">
                                <div class="col-md-4">
                                    <p>
                                        <label class="control-label">Клиент:</label>
                                        <a href="${pageContext.request.contextPath}/client/${client.id}">${client.fname} ${client.lname}</a>
                                    </p>
                                    <p>
                                        <label class="control-label">Дата:</label>
                                        ${payment.dateS}
                                    </p>
                                    <p>
                                        <label class="control-label">Комментарий:</label>
                                    </p>
                                    <p>
                                        ${payment.comment}
                                    </p>
                                </div>
                                <div class="col-md-4">
                                    <p>
                                        <label class="control-label">Договор:</label>
                                        <a href="${pageContext.request.contextPath}/contract/${contract.id}">${contract.teacherS} [${contract.typeS}]</a>
                                    </p>
                                    <p>
                                        <c:if test="${contract.special == 1}">
                                            <span class="label label-warning">Специальный</span>
                                        </c:if>
                                        <c:if test="${contract.special == 0}">
                                            <span class="label label-info">Стандартный</span>
                                            <c:if test="${contract.cash == 0}">
                                                <h5 style="color:red">Не установлено</h5>
                                            </c:if>
                                            <c:if test="${contract.cash == 1}">
                                                Наличные
                                            </c:if>
                                            <c:if test="${contract.cash == 2}">
                                                Чек
                                            </c:if>
                                        </c:if>
                                    </p>
                                </div>
                                <div class="col-md-4">
                                <p>
                                    <label class="control-label">Статус:</label>
                                    <c:if test="${payment.planned == 1}">
                                        <span class="label label-info">Запланирован</span>
                                    </c:if>
                                    <c:if test="${payment.done == 1}">
                                        <span class="label label-warning">Проведен</span>
                                    </c:if>
                                    <c:if test="${payment.approved == 1}">
                                        <span class="label label-danger">Подтвержден</span>
                                        ${payment.approvedDateS}
                                    </c:if>
                                </p>
                            </div>
                            </div>
                        </div>
                    </div>

                    <div class="widget">
                        <div class="widget-header">
                            <h4>Действия</h4>
                        </div>
                        <div class="widget-content">
                            <div class="tabbable tabbable-custom tabs-left">
                                <!-- Only required for left/right tabs -->
                                <ul class="nav nav-tabs tabs-left">
                                    <li class="active"><a href="#tab_3_4" data-toggle="tab">Добавить комментарий</a></li>
                                    <c:if test="${payment.done == 0}">
                                        <li><a href="#tab_3_5" data-toggle="tab">Провести</a></li>
                                        <li><a href="#tab_3_1" data-toggle="tab">Перенести</a></li>
                                    </c:if>
                                    <c:if test="${payment.done == 1}">
                                        <c:if test="${payment.approved == 0}">
                                        <li><a href="#tab_3_2" data-toggle="tab">Подтвердить <i class="icon-lock"></i></a></li>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${payment.done == 0}">
                                        <li><a href="#tab_3_3" data-toggle="tab">Удалить <i class="icon-lock"></i></a></li>
                                    </c:if>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tab_3_4">
                                        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/payment/comment" commandName="payment" method="POST">
                                            <div class="hidden">
                                                <form:input class="form-control" path="id" type="text" value="${payment.id}"/>
                                                <form:input class="form-control" path="comment" type="text" value="${payment.comment}"/>
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

                                    <c:if test="${payment.done == 0}">
                                    <div class="tab-pane" id="tab_3_5">
                                        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/payment/do" commandName="payment" method="POST" >
                                            <div class="hidden">
                                                <form:input class="form-control" path="id" type="text" value="${payment.id}"/>
                                                <form:input class="form-control" path="comment" type="text" value="${payment.comment}"/>
                                            </div>

                                            <div class="form-actions">
                                                <input type="submit" value="Провести" class="btn btn-primary pull-right">
                                            </div>
                                        </form:form>
                                    </div>

                                    <div class="tab-pane" id="tab_3_1">
                                        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/payment/transfer" commandName="payment" method="POST" >
                                            <div class="hidden">
                                                <form:input class="form-control" path="id" type="text" value="${payment.id}"/>
                                                <form:input class="form-control" path="comment" type="text" value="${payment.comment}"/>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Дата<span class="required">*</span></label>
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
                                    </c:if>

                                    <c:if test="${payment.done == 1}">
                                    <c:if test="${payment.approved == 0}">
                                    <div class="tab-pane" id="tab_3_2">
                                        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/tm/payment/approve" commandName="payment" method="POST">
                                            <div class="hidden">
                                                <form:input class="form-control" path="id" type="text" value="${payment.id}"/>
                                                <form:input class="form-control" path="comment" type="text" value="${payment.comment}"/>
                                            </div>
                                            <div class="form-actions">
                                                <input type="submit" value="Подтвердить" class="btn btn-primary pull-right">
                                            </div>
                                        </form:form>
                                    </div>
                                    </c:if>
                                    </c:if>

                                    <c:if test="${payment.done == 0}">
                                    <div class="tab-pane" id="tab_3_3">
                                        <form:form class="form-horizontal" action="${pageContext.request.contextPath}/tm/payment/delete" commandName="payment" method="POST">
                                            <div class="hidden">
                                                <form:input class="form-control" path="id" type="text" value="${payment.id}"/>
                                                <form:input class="form-control" path="comment" type="text" value="${payment.comment}"/>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Комментарий<span class="required">*</span></label>
                                                <div class="col-md-10"><form:textarea rows="3" cols="5" path="commentNew" class="limited form-control required wysiwyg" data-limit="100"/></div>
                                            </div>
                                            <div class="form-actions">
                                                <input type="submit" value="Удалить" class="btn btn-primary pull-right">
                                            </div>
                                        </form:form>
                                    </div>
                                    </c:if>
                                </div>
                            </div>
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