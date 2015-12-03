<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="addimports" fragment="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="userid" required="true"%>
<%@ attribute name="username" required="true"%>
<%@ attribute name="ntasks" required="true"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:invoke fragment="title"/>
    <t:imports/>
    <jsp:invoke fragment="addimports"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/i/favicon.ico" type="image/x-icon">
</head>
<body>
    <t:header username = "${username}"/>
    <div id="container">
        <t:sidebar  ntasks = "${ntasks}" userid = "${userid}"/>
        <div id="content">
            <jsp:doBody/>
        </div>
    </div>
</body>
</html>