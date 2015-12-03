<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Similar clients | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/calls" title="">Звонки</a>
                    </li>
                </ul>

            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Новый звонок (Шаг 2)</h3>
                    <span>Клиенты со схожими данными присутствуют в системе.</span>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Выберите клиента из списка или
                                <form action="${pageContext.request.contextPath}/call/use?client=new" method="post">
                                    <button class="btn btn-success" type="submit">создайте нового</button>
                                </form>
                            </h4>
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
                                    <th>E-mail</th>
                                    <th>Комментарий</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="client" items="${clients}">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/client/${client.id}">${client.lname} ${client.fname} ${client.pname}</a>
                                        </td>
                                        <td>${client.phone1} ${client.phone1name}<br>${client.phone2} ${client.phone2name}</td>
                                        <td>${client.email}</td>
                                        <td>${client.comment}</td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/call/use?call=${callId}&client=${client.id}" method="post">
                                                <button class="btn btn-success" type="submit">Сохранить</button>
                                            </form>
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

