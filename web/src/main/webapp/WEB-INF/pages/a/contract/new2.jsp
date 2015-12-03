<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<t:general username = "${username}" ntasks = "${ntasks}" userid = "${userid}">
    <jsp:attribute name="title">
        <title>New contract | ARTBUREAU</title>
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <!-- Breadcrumbs line -->
            <div class="crumbs">
                <ul id="breadcrumbs" class="breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="${pageContext.request.contextPath}/">Главная</a>
                    </li>
                    <li class="current">
                        <a href="#" title="">Новый контракт</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3><a href="${pageContext.request.contextPath}/client/${contractObj.clientId}">${contractObj.clientFS} ${contractObj.clientLS}</a></h3>
                </div>
            </div>
            <!-- /Page Header -->


            <!--=== Page Content ===-->
            <div class="row">
                <!--=== Example Box ===-->
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Новый контракт [Шаг 2]</h4>
                        </div>
                        <div class="widget-content">
                            <form:form class="form-horizontal row-border" id="validate-1" method="POST" action="${pageContext.request.contextPath}/contract?s=3" commandName="contractObj">
                                <div class="form-group">
                                    <label class="col-md-2 control-label">Преподаватель<span class="required">*</span></label>
                                    <div class="col-md-10">
                                        <form:select id="input17" path="teacherTypeId" class="select2-select-00 col-md-4 full-width-fix">
                                            <c:forEach var="t" items="${ts}">
                                                <form:option value="${t.id}">${t.teacherName}</form:option>
                                            </c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <input type="submit" value="Далее" class="btn btn-primary pull-right">
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div> <!-- /.col-md-12 -->
                <!-- /Example Box -->
            </div> <!-- /.row -->
            <!-- /Page Content -->

        </div>
        <!-- /.container -->
    </jsp:body>
</t:general>