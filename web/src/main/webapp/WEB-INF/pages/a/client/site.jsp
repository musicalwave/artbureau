<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Site | ARTBUREAU</title>
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
                    <a href="${pageContext.request.contextPath}/client/site" title="">Заявки с сайта</a>
                </li>
            </ul>
        </div>
        <!-- /Breadcrumbs line -->

        <!--=== Page Header ===-->
        <div class="page-header">
            <div class="page-title">
                <h3>Заявки с сайта</h3>
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
                        <h4>Необработанные заявки</h4>
                    </div>
                    <div class="widget-content">
                        <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                            <thead>
                            <tr>
                                <th>ФИО</th>
                                <th>Телефон</th>
                                <th>E-mail</th>
                                <th>Комментарий</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="client" items="${clients}">
                                <tr>
                                    <td>${client.lname} ${client.fname}
                                    </td>
                                    <td>${client.phone1}
                                    </td>
                                    <td>${client.email}</td>
                                    <td>${client.comment}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/client/${client.id}" class="btn btn-xs btn-success">Подробнее</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
            </div>
        </div>
        </div>
    </jsp:body>
</t:general>
