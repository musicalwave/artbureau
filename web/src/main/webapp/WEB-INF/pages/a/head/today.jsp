<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<t:general username="${username}" ntasks="${ntasks}" userid="${userid}">
    <jsp:attribute name="title">
        <title>Статистика | ARTBUREAU</title>
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
                        <a href="${pageContext.request.contextPath}/stat" title="">Статистика</a>
                    </li>
                </ul>
            </div>
            <!-- /Breadcrumbs line -->

            <!--=== Page Header ===-->
            <div class="page-header">
                <div class="page-title">
                    <h3>Статистика на сегодня</h3>
                    <span>Все что нужно</span>
                </div>

                <!-- Page Stats -->
                <ul class="page-stats">
                    <li>
                        <div class="summary">
                            <span>Проведено</span>
                            <h3>${valueDo}&#8381;</h3>
                        </div>
                        <div id="sparkline-bar" class="graph sparkline hidden-xs">20,15,8,50,20,40,20,30,20,15,30,20,25,20</div>
                        <!-- Use instead of sparkline e.g. this:
                        <div class="graph circular-chart" data-percent="73">73%</div>
                        -->
                    </li>
                    <li>
                        <div class="summary">
                            <span>Запланировано</span>
                            <h3>${valuePlan}&#8381;</h3>
                        </div>
                        <div id="sparkline-bar2" class="graph sparkline hidden-xs">20,15,8,50,20,40,20,30,20,15,30,20,25,20</div>
                    </li>
                </ul>
                <!-- /Page Stats -->
            </div>
            <!-- /Page Header -->

            <!--=== Page Content ===-->
            <!--=== Statboxes ===-->
            <div class="row row-bg"> <!-- .row-bg -->
                <div class="col-sm-6 col-md-3">
                    <div class="statbox widget box box-shadow">
                        <div class="widget-content">
                            <div class="visual cyan">
                                <i class="icon-user"></i>
                            </div>
                            <div class="title">Клиенты</div>
                            <div class="value">${nclients}</div>
                            <a class="more" href="javascript:void(0);">Подробнее <i class="pull-right icon-angle-right"></i></a>
                        </div>
                    </div> <!-- /.smallstat -->
                </div> <!-- /.col-md-3 -->

                <div class="col-sm-6 col-md-3">
                    <div class="statbox widget box box-shadow">
                        <div class="widget-content">
                            <div class="visual green">
                                <i class="icon-phone"></i>
                            </div>
                            <div class="title">Звонки</div>
                            <div class="value">${ncalls}</div>
                            <a class="more" href="javascript:void(0);">Подробнее <i class="pull-right icon-angle-right"></i></a>
                        </div>
                    </div> <!-- /.smallstat -->
                </div> <!-- /.col-md-3 -->

                <div class="col-sm-6 col-md-3 hidden-xs">
                    <div class="statbox widget box box-shadow">
                        <div class="widget-content">
                            <div class="visual red">
                                <div class="statbox-sparkline">30,20,15,30,22,25,26,30,27</div>
                            </div>
                            <div class="title">Важный показатель</div>
                            <div class="value">${weekValueDo}&#8381;</div>
                            <a class="more" href="javascript:void(0);">Подробнее <i class="pull-right icon-angle-right"></i></a>
                        </div>
                    </div> <!-- /.smallstat -->
                </div> <!-- /.col-md-3 -->

                <div class="col-sm-6 col-md-3 hidden-xs">
                    <div class="statbox widget box box-shadow">
                        <div class="widget-content">
                            <div class="visual green">
                                <div class="statbox-sparkline">20,30,30,29,22,15,20,30,32</div>
                            </div>
                            <div class="title">Важный показатель</div>
                            <div class="value">${weekValuePlan}&#8381;</div>
                            <a class="more" href="javascript:void(0);">Подробнее <i class="pull-right icon-angle-right"></i></a>
                        </div>
                    </div> <!-- /.smallstat -->
                </div> <!-- /.col-md-3 -->
            </div> <!-- /.row -->
            <!-- /Statboxes -->

            <div class="row">
                <div class="col-md-12">
                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Запланированные платежи</h4>
                        </div>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                                <thead>
                                <tr>
                                    <th>Дата</th>
                                    <th>Клиент</th>
                                    <th>Контракт</th>
                                    <th>Сумма</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="payment" items="${paymentsPlan}">
                                    <tr>
                                        <td><a href="${pageContext.request.contextPath}/payment/${payment.id}">${payment.dateS}</a></td>
                                        <td><a href="${pageContext.request.contextPath}/client/${payment.clientId}">${payment.clientS}</a></td>
                                        <td><a href="${pageContext.request.contextPath}/contract/${payment.contractId}">${payment.teacherS} [${payment.typeS}][${payment.cash}]</a></td>
                                        <td>${payment.value}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="widget box">
                        <div class="widget-header">
                            <h4><i class="icon-reorder"></i>Проведенные платежи</h4>
                        </div>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover table-checkable table-colvis datatable">
                                <thead>
                                <tr>
                                    <th>Дата</th>
                                    <th>Клиент</th>
                                    <th>Контракт</th>
                                    <th>Сумма</th>
                                    <th>Комментарий</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="payment" items="${paymentsDo}">
                                    <tr>
                                        <td><a href="${pageContext.request.contextPath}/payment/${payment.id}">${payment.dateS}</a></td>
                                        <td><a href="${pageContext.request.contextPath}/client/${payment.clientId}">${payment.clientS}</a></td>
                                        <td><a href="${pageContext.request.contextPath}/contract/${payment.contractId}">${payment.teacherS} [${payment.typeS}][${payment.cash}]</a></td>
                                        <td>${payment.value}</td>
                                        <td>${payment.comment}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.row -->
            <!-- /Page Content -->

        </div>

    </jsp:body>
</t:general>