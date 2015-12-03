<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>New client | ARTBUREAU</title>
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
                        <a href="#" title="">Новый клиент</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Новый клиент</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <form:form class="form-horizontal row-border" id="validate-1" method="POST"
                               action="${pageContext.request.contextPath}/client/new" commandName="client">
                        <div class="widget">
                            <div class="widget-header">
                                <h4><i class="icon-reorder"></i>Персональная информация</h4>
                            </div>
                            <div class="widget-content">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label class="col-md-6 control-label">ФИО:</label>
                                        <div class="form-group">
                                            <form:input type="text" path="lname" class="form-control"
                                                        placeholder="Фамилия"/>
                                            <form:input type="text" path="fname" class="form-control required"
                                                        placeholder="Имя"/>
                                            <form:input type="text" path="pname" class="form-control"
                                                        placeholder="Отчество"/>
                                        </div>

                                        <label class="col-md-6 control-label">Дата рождения:</label>

                                        <div class="form-group">
                                            <form:input type="text" path="bdate" class="form-control"
                                                        data-mask="99/99/9999"
                                                        placeholder="30/12/1992"/>
                                        </div>

                                        <label class="col-md-6 control-label">Контакты:</label>
                                        <div class="form-group">
                                            <form:input type="text" path="phone1" class="form-control required"
                                                        data-mask="+7 (999) 999-9999"
                                                        placeholder="+7 (915) 123-4567"/>

                                            <form:input type="text" path="phone1name" class="form-control" placeholder="Комментарий"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input type="text" path="phone2" class="form-control"
                                                        data-mask="+7 (999) 999-9999"
                                                        placeholder="+7 (915) 123-4567"/>
                                            <form:input type="text" path="phone2name" class="form-control" placeholder="Комментарий"/>
                                        </div>
                                        <div class="form-group">
                                            <form:input type="text" path="email" class="form-control"
                                                        placeholder="example@mail.ru"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="widget">
                            <div class="widget-header">
                                <h4><i class="icon-reorder"></i>Юридическая информация</h4>
                            </div>
                            <div class="widget-content">
                                <form:textarea rows="3" cols="5" path="jdata"
                                               class="auto form-control"/>
                            </div>
                        </div>

                        <div class="widget">
                            <div class="widget-header">
                                <h4><i class="icon-reorder"></i>Комментарий</h4>
                            </div>
                            <div class="widget-content">
                                <form:textarea rows="3" cols="5" path="comment"
                                               class="auto form-control"/>
                            </div>
                        </div>

                        <div class="form-actions">
                            <input type="submit" value="Сохранить" class="btn btn-primary pull-right">
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </jsp:body>
</t:general>
