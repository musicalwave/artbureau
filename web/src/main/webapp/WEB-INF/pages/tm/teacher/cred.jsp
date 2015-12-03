<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>Data | ARTBUREAU</title>
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
                        <a href="#" title="">Юр. информация</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Данные преподавателя: ${teacher.name}</h3>
                </div>
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Паспортные данные</h4>
                        </div>
                        <div class="widget-content">
                            <form:form class="form-horizontal row-border" method="POST" action="${pageContext.request.contextPath}/tm/cred" commandName="teacher">
                                <div class="form-group">
                                    <div class="hidden">
                                        <form:input class="form-control" path="id" type="text" value="${teacher.id}"/>
                                    </div>
                                    <form:textarea path="credentials" rows="3" cols="5" class="form-control wysiwyg required" placeholder="Паспортные данные"/>
                                </div>
                                <div class="form-actions">
                                    <input type="reset" value="Отмена" class="btn btn-primary pull-left" onclick="history.back();">
                                    <input type="submit" value="Сохранить" class="btn btn-primary pull-right">
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:general>