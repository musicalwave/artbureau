<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
      <title>Tasks | ARTBUREAU</title>
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

                <ul class="crumb-buttons">
                    <li><a href="${pageContext.request.contextPath}/task" title=""><i class="icon-rocket"></i><span>Новое задание</span></a></li>
                        <%--<li class="range">--%>
                        <%--<a href="#">--%>
                        <%--<i class="icon-calendar"></i>--%>
                        <%--<span>October 19, 2013 - November 17, 2013</span>--%>
                        <%--<i class="icon-angle-down"></i>--%>
                        <%--</a>--%>
                        <%--</li>--%>

                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Исходящие задания</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== no-padding and table-colvis ===-->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Задания [<a href="${pageContext.request.contextPath}/tasks/my">В работе</a>] [<a href="${pageContext.request.contextPath}/tasks/my/done">Выполненные</a>]</h4>
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
                                    <td></td>
                                    <th>Дата</th>
                                    <th>Ответственный</th>
                                    <th>Статус</th>
                                    <th>Задание</th>
                                    <td></td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="task" items="${tasks}">
                                    <tr>
                                        <td>
                                            <c:if test="${task.important > 0}">
                                                <i class="icon-exclamation-sign "></i>
                                            </c:if>
                                            <c:if test="${task.info > 0}">
                                                <i class="icon-info"></i>
                                            </c:if>
                                            <c:if test="${task.toId > 0}">
                                                <i class="icon-user"></i>
                                            </c:if>
                                        </td>
                                        <td>${task.dateS} <b>${task.timeS}</b></td>
                                        <td>${task.toName}${task.groupName}</td>
                                        <td>
                                            <c:if test="${task.done == 0}">
                                                <span class="label label-warning">Назначено</span></td>
                                            </c:if>
                                            <c:if test="${task.done == 1}">
                                                <span class="label label-success">Выполнено</span></td>
                                            </c:if>
                                        <td>${task.message}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/task/${task.id}" class="btn btn-xs btn-success">Подробнее</a>
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