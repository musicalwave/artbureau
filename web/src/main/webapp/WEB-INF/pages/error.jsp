<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>Error | ARTBUREAU</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/i/favicon.ico" type="image/x-icon">
    <!--=== CSS ===-->

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/js/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

    <!-- jQuery UI -->
    <!--<link href="plugins/jquery-ui/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />-->
    <!--[if lt IE 9]>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-ui/jquery.ui.1.10.2.ie.css"/>
    <![endif]-->

    <!-- Theme -->
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/plugins.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/responsive.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/icons.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome.min.css">
    <!--[if IE 7]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome-ie7.min.css">
    <![endif]-->

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

    <!--=== JavaScript ===-->

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-ui/jquery-ui-1.10.2.custom.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/lodash.compat.min.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/html5shiv.js"></script>
    <![endif]-->

    <!-- Smartphone Touch Events -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/touchpunch/jquery.ui.touch-punch.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/event.swipe/jquery.event.move.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/event.swipe/jquery.event.swipe.js"></script>

    <!-- General -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/breakpoints.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/respond/respond.min.js"></script> <!-- Polyfill for min/max-width CSS3 Media Queries (only for IE8) -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/cookie/jquery.cookie.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/slimscroll/jquery.slimscroll.horizontal.min.js"></script>

    <!-- App -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/app.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/plugins.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/plugins.form-components.js"></script>

    <script>
        $(document).ready(function(){
            "use strict";

            App.init(); // Init layout and core plugins
            Plugins.init(); // Init all plugins
            FormComponents.init(); // Init all form-specific plugins
        });
    </script>
</head>

<body>

<div id="container">

    <div id="content">
        <div class="container">

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h1>Error ${errorId}!</h1>
                    <span>Current time: <%= new java.util.Date() %></span>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>General information</h4>
                        </div>
                        <div class="widget-content">
                            <div class="alert alert-warning"><strong>About error:</strong> ${errorDesc}</div>
                            <p>
                                ${errorTodo}
                            </p>
                            <button class="btn btn-info" onclick="history.back();">Back</button>
                        </div> <!-- /.widget-content -->
                    </div>
                </div> <!-- /.col-md-12 -->
                <!-- /Example Box -->
            </div> <!-- /.row -->
            <!-- /Page Content -->

        </div>
        <!-- /.container -->

    </div>
</div>

</body>
</html>
