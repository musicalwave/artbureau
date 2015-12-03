<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>Login | ARTBUREAU</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/i/favicon.ico" type="image/x-icon">

    <!--=== CSS ===-->

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/js/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

    <!-- Theme -->
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/plugins.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/responsive.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/icons.css" rel="stylesheet" type="text/css" />

    <!-- Login -->
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/login.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome.min.css">
    <!--[if IE 7]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome-ie7.min.css">
    <![endif]-->

    <!--[if IE 8]>
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/ie8.css" rel="stylesheet" type="text/css" />
    <![endif]-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

    <!--=== JavaScript ===-->

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/jquery-1.11.1.min.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/lodash.compat.min.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/html5shiv.js"></script>
    <![endif]-->

    <!-- Beautiful Checkboxes -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/uniform/jquery.uniform.min.js"></script>

    <!-- Form Validation -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/validation/jquery.validate.min.js"></script>

    <!-- Slim Progress Bars -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/nprogress/nprogress.js"></script>

    <!-- App -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/login.js"></script>
    <script>
        $(document).ready(function(){
            "use strict";

            Login.init(); // Init login JavaScript
        });
    </script>
</head>

<body class="login">
<!-- Logo -->
<div class="logo">
    <img src="${pageContext.request.contextPath}/resources/js/lib/assets/img/logo.png" alt="logo" />
    <strong>ART</strong>BUREAU
</div>
<!-- /Logo -->

<!-- Login Box -->
<div class="box">
    <div class="content">
        <!-- Login Formular -->
        <form class="form-vertical" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
            <!-- Title -->
            <h3 class="form-title">Sign In to your Account</h3>

            <!-- Error Message -->
            <div class="alert fade in alert-danger" style="display: none;">
                <i class="icon-remove close" data-dismiss="alert"></i>
                Enter any username and password.
            </div>

            <!-- Input Fields -->
            <div class="form-group">
                <!--<label for="username">Username:</label>-->
                <div class="input-icon">
                    <i class="icon-user"></i>
                    <input type="text" name="j_username" class="form-control" placeholder="Username" autofocus="autofocus" data-rule-required="true" data-msg-required="Please enter your username." />
                </div>
            </div>
            <div class="form-group">
                <!--<label for="password">Password:</label>-->
                <div class="input-icon">
                    <i class="icon-lock"></i>
                    <input type="password" name="j_password" class="form-control" placeholder="Password" data-rule-required="true" data-msg-required="Please enter your password." />
                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                </div>
            </div>
            <!-- /Input Fields -->

            <!-- Form Actions -->
            <div class="form-actions">
                <label class="checkbox pull-left"><input type="checkbox" class="uniform" name="_spring_security_remember_me"/> Remember me</label>
                <button type="submit" class="submit btn btn-primary pull-right">
                    Sign In <i class="icon-angle-right"></i>
                </button>
            </div>
        </form>
        <!-- /Login Formular -->

    </div> <!-- /.content -->

    <!-- Forgot Password Form -->
    <div class="inner-box">
        <div class="content">
            <!-- Close Button -->
            <i class="icon-remove close hide-default"></i>

            <!-- Link as Toggle Button -->
            <a href="#" class="forgot-password-link">Forgot Password?</a>

            <!-- Forgot Password Formular -->
            <form class="form-vertical forgot-password-form hide-default" action="${pageContext.request.contextPath}/login" method="POST" >
                <!-- Input Fields -->
                <div class="form-group">
                    <!--<label for="email">Email:</label>-->
                    <div class="input-icon">
                        <i class="icon-envelope"></i>
                        <input type="text" path="login" class="form-control" placeholder="Enter your login" data-rule-required="true" data-msg-required="Please enter your login." />
                    </div>
                </div>
                <!-- /Input Fields -->

                <button type="submit" class="submit btn btn-default btn-block">
                    Help me!
                </button>
            </form>
            <!-- /Forgot Password Formular -->

            <!-- Shows up if reset-button was clicked -->
            <div class="forgot-password-done hide-default">
                <i class="icon-ok success-icon"></i> <!-- Error-Alternative: <i class="icon-remove danger-icon"></i> -->
                <span>Great. Your system administrator will contact you.</span>
            </div>
        </div> <!-- /.content -->
    </div>
    <!-- /Forgot Password Form -->
</div>
<!-- /Login Box -->
</body>
</html>