<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Finance | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/tm/finance" title="">Финансы</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Финансы</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Доступные модули</h4>
                        </div>
                        <div class="widget-content">
                            <b><h5><a href="${pageContext.request.contextPath}/types" title="">Общие ставки</a></h5></b>
                            <%--<b><h5><a href="${pageContext.request.contextPath}/tm/finance/in" title="">Внесение денег</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/out" title="">Вывод денег</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/cassa" title="">Касса</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/refund" title="">Возвраты</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/teachers" title="">Выплата преподавателям</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/employers" title="">Выплата сотрудникам</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/accounting" title="">Учет</a></h5></b>
                            <b><h5><a href="${pageContext.request.contextPath}/tm/finance/stat" title="">Статистика</a></h5></b>--%>
                        </div>
                    </div>
                </div>
                <!-- /.col-md-12 -->
                <!-- /Example Box -->
            </div>
            <!-- /.row -->
            <!-- /Page Content -->

        </div>

    </jsp:body>
</t:general>