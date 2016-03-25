<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <title>Schedule | ARTBUREAU</title>
  <meta charset="utf-8">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/jquery-ui/jquery-ui.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/fullcalendar/fullcalendar.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-context-menu/jquery.contextMenu.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/schedule.min.css">

  <script src="${pageContext.request.contextPath}/resources/js/lib/fullcalendar/lib/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/jquery-ui/jquery-ui.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/fullcalendar/lib/moment.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/fullcalendar/fullcalendar.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-context-menu/jquery.contextMenu.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-context-menu/jquery.ui.position.js"></script>
  <script src='${pageContext.request.contextPath}/resources/js/schedule.min.js'></script>
  <script src='${pageContext.request.contextPath}/resources/js/lib/fullcalendar/lang-all.js'></script>

</head>

<body>
    <div id="calendar"></div>
</body>

</html>
