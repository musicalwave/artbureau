<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Calculation | ARTBUREAU</title>
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
                    <a href="${pageContext.request.contextPath}/client/site" title="">Site</a>
                </li>
            </ul>
        </div>
        <!-- /Breadcrumbs line -->

        <!--=== Page Header ===-->
        <div class="page-header">
            <div class="page-title">
                <h3>Расчет преподавателя: <a href="${pageContext.request.contextPath}/teacher/${teacher.id}">${teacher.name}</a></h3>
                    <%--<span>Найди своего клиента!</span>--%>
            </div>
        </div>
        <!-- /Page Header -->

        <!--=== Page Content ===-->
        <div class="row">
            <!--=== Example Box ===-->
            <div class="col-md-12">
                <div class="widget box">
                    <div class="widget-header">
                        <h4>Параметры</h4>
                    </div>
                    <div class="widget-content">
                        <form:form class="form-horizontal row-border" id="validate-1" method="POST" action="${pageContext.request.contextPath}/finance/t/get" commandName="data">
                            <div class="form-group">
                                <div class="hidden">
                                    <form:input class="form-control" path="teacherId" type="number" value="${data.teacherId}"/>
                                </div>

                                <label class="col-md-2 control-label">Номер акта:<span class="required">*</span></label>
                                <div class="col-md-2"><form:input type="text" path="actnumber" class="form-control required"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Дата начала:<span class="required">*</span></label>
                                <div class="col-md-2"><form:input type="text" path="startdate" class="form-control required datepicker"/></div>

                                <label class="col-md-2 control-label">Дата окончания:<span class="required">*</span></label>
                                <div class="col-md-2"><form:input type="text" path="finishdate" class="form-control required datepicker"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Фамилия:<span class="required">*</span></label>
                                <div class="col-md-2"><form:input type="text" path="lname" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Имя:<span class="required">*</span></label>
                                <div class="col-md-2"><form:input type="text" path="fname" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Отчество:<span class="required">*</span></label>
                                <div class="col-md-2"><form:input type="text" path="pname" class="form-control required"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Кол-во инд. занятий:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="soloquantity" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Кол-во парн. занятий:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="pairquantity" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Кол-во групп. занятий:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="groupquantity" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Кол-во анс. занятий:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="ensemblequantity" class="form-control required"/></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Ставка инд.:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="solorate" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Ставка парн.:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="pairrate" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Ставка групп:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="grouprate" class="form-control required"/></div>

                                <label class="col-md-2 control-label">Ставка анс.:<span class="required">*</span></label>
                                <div class="col-md-1"><form:input type="number" path="ensemblerate" class="form-control required"/></div>
                            </div>

                            <div class="form-actions">
                                <input type="submit" value="Рассчитать" class="btn btn-primary pull-right">
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:general>
