<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Staff | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/tm/staff" title="">Управление сотрудниками</a>
                    </li>
                </ul>

                <ul class="crumb-buttons">
                    <li><a href="${pageContext.request.contextPath}/tm/staff/new" title=""><i class="icon-user"></i><span>Новый сотрудник</span></a></li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Управление сотрудниками</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Сотрудники</h4>
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
                                    <th>Роль</th>
                                    <th>Группы</th>
                                    <th>Телефон</th>
                                    <th>E-mail</th>
                                    <th>День рождения</th>
                                    <th>Логин</th>
                                    <th>Пароль</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.name}</td>
                                        <td>${user.roleName}</td>
                                        <td>${user.groups}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.mail}</td>
                                        <td>${user.bdate}</td>
                                        <td>${user.login}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/tm/staff/cpw?id=${user.id}" class="btn btn-xs btn-warning">Изменить</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /no-padding and table-colvis -->
            <!-- /Page Content -->

        </div>
    </jsp:body>
</t:general>

