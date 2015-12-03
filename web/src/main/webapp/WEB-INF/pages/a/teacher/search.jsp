<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Search | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/teacher/search" title="">Search</a>
                    </li>
                </ul>
                <ul class="crumb-buttons">
                    <li><a href="${pageContext.request.contextPath}/teacher" title=""><i class="icon-music"></i><span>Новый преподаватель</span></a></li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Поиск преподавателя</h3>
                    <span>Для неточного поиска используй %</span>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4>Фильтры</h4>
                        </div>
                        <div class="widget-content">
                            <form:form class="form-horizontal" method="POST"
                                       action="${pageContext.request.contextPath}/teacher/search"
                                       commandName="t">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-4 control-label">ФИО:</label>

                                            <div class="col-md-8"><form:input type="text" path="name"
                                                                              class="form-control"/></div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Дата рождения:</label>

                                            <div class="col-md-8"><form:input type="text" path="bdate"
                                                                              class="form-control"
                                                                              data-mask="99/99/9999"/></div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Направление:</label>

                                            <div class="col-md-8">
                                                <form:select id="input18" path="types" class="select2-select-00 col-md-8 full-width-fix" multiple="true" size="5">
                                                    <c:forEach var="type" items="${types}">
                                                        <form:option value="${type.id}">${type.name}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-4 control-label">Телефон:</label>

                                            <div class="col-md-8"><form:input type="text" path="phone"
                                                                              class="form-control"
                                                                              data-mask="+7 (999) 999-9999"/></div>
                                        </div>
                                    </div>

                                    <!-- /.widget -->
                                </div>
                                <div class="form-actions">
                                    <input type="submit" value="Поиск" class="btn btn-primary pull-right">
                                </div>

                            </form:form>
                        </div>
                        <!-- /.col-md-12 -->

                    </div>

                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Преподаватели</h4>
                            <div class="toolbar no-padding">
                                <div class="btn-group">
                                    <span class="btn btn-xs widget-collapse"><i class="icon-angle-down"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="widget-content no-padding">
                            <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                                <thead>
                                <tr>
                                    <th>ФИО</th>
                                    <th>Телефон</th>
                                    <th>Дата рождения</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="teacher" items="${teachers}">
                                    <tr>
                                        <td>${teacher.name}
                                        </td>
                                        <td>${teacher.phone}
                                        </td>
                                        <td>${teacher.bdate}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/teacher/${teacher.id}" class="btn btn-xs btn-success">Подробнее</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col-md-12 -->
            <!-- /Example Box -->
        </div>
    </jsp:body>
</t:general>
