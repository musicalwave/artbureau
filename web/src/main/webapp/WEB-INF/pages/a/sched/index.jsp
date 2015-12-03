<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Finance | ARTBUREAU</title>
    </jsp:attribute>

    <jsp:attribute name="addimports">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/fullcalendar2.css"/>
        <link rel="stylesheet" media='print' href="${pageContext.request.contextPath}/resources/js/lib/fullcalendar.print.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/jquery.arcticmodal-0.3.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/themes/simple.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
        <!-- Theme -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/fullcalendar.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery.arcticmodal-0.3.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/generator.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datasources/default.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/main.js"></script>
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
                        <a href="${pageContext.request.contextPath}/sched" title="">Расписание</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Расписание</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div id="calendar_wrapper">
                </div>
                <!-- /.col-md-12 -->
                <!-- /Example Box -->
            </div>
            <!-- /.row -->
            <!-- /Page Content -->

        </div>

    </jsp:body>
</t:general>