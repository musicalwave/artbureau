<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New payment | ARTBUREAU</title>
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
                        <a href="#" title="">New payment</a>
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
                    <h3>Новый платеж</h3>
                    <span><a href="${pageContext.request.contextPath}/client/${client.id}">${client.fname} ${client.lname}</a></span>
                </div>
            </div>
            <!-- /Page Header -->
            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-content">
                            <form:form class="form-horizontal row-border" method="POST" action="${pageContext.request.contextPath}/client/payment" commandName="payment">
                                <div class="form-group">
                                    <div class="col-md-4">
                                        <label class="control-label">Договор</label>
                                        <form:select id="input17" path="contractId" class="select2-select-00 col-md-4 full-width-fix">
                                            <c:forEach var="contract" items="${contracts}">
                                                <form:option value="${contract.id}">${contract.teacherS} [${contract.typeS}] [${contract.price}]</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                    <div class="col-md-4">
                                        <label class="control-label">Сумма</label>
                                        <form:input class="form-control input-width-large" path="value" type="text" placeholder="100" value="${total}"/>

                                        <label class="radio-inline">
                                            <form:radiobutton path="direction" value="1" checked="true"/>
                                            Пополнение
                                        </label>

                                        <label class="radio-inline">
                                            <form:radiobutton path="direction" value="2" disabled="true"/>
                                            Возврат
                                        </label>
                                    </div>

                                    <div class="col-md-4">
                                        <label class="control-label">Планирование</label>
                                        <label class="checkbox">
                                            <form:checkbox class="uniform" path="plannedS" value="true"/>Запланировать
                                        </label>

                                        <label class="control-label">Дата платежа</label>
                                        <form:input type="text" path="dateS" class="form-control datepicker"/>

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