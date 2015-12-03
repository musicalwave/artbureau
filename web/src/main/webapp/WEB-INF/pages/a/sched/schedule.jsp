<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Schedule | ARTBUREAU</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/fullcalendar2.css"/>
    <link rel="stylesheet" media='print' href="${pageContext.request.contextPath}/resources/js/lib/fullcalendar.print.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/jquery.arcticmodal-0.3.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/themes/simple.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <!-- Theme -->
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/main.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/plugins.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/responsive.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/icons.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/fullcalendar.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/jquery.arcticmodal-0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/generator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/colors.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datasources/default.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/main.js"></script>

</head>
<body>
<div id="calendar_wrapper">

</div>
</body>
</html>