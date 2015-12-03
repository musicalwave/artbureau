<%@tag description="Header template" pageEncoding="UTF-8"%>
<%@ attribute name="username" required="true"%>

<!-- Header -->
<header class="header navbar navbar-fixed-top" role="banner">
    <!-- Top Navigation Bar -->
    <div class="container">

        <!-- Only visible on smartphones, menu toggle -->
        <ul class="nav navbar-nav">
            <li class="nav-toggle"><a href="javascript:void(0);" title=""><i class="icon-reorder"></i></a></li>
        </ul>

        <!-- Logo -->
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/resources/js/lib/assets/img/logo.png" alt="logo" />
            <strong>АРТ</strong>БЮРО
        </a>
        <!-- /logo -->

        <!-- Sidebar Toggler -->
        <a href="#" class="toggle-sidebar bs-tooltip" data-placement="bottom" data-original-title="Toggle navigation">
            <i class="icon-reorder"></i>
        </a>
        <!-- /Sidebar Toggler -->

        <!-- Top Left Menu -->
        <ul class="nav navbar-nav navbar-left hidden-xs hidden-sm">
            <li>
                <a href="${pageContext.request.contextPath}/">
                    Главная
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/today">
                    Сегодня
                </a>
            </li>
        </ul>
        <!-- /Top Left Menu -->

        <!-- Top Right Menu -->
        <ul class="nav navbar-nav navbar-right">

            <!-- Messages -->
            <li class="dropdown hidden-xs hidden-sm">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="icon-envelope"></i>
                    <span class="badge">0</span>
                </a>
                <%--<ul class="dropdown-menu extended notification">--%>
                    <%--<li class="title">--%>
                        <%--<p>You have 1 new site requests</p>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="javascript:void(0);">--%>
                            <%--<span class="photo"><img src="${pageContext.request.contextPath}/resources/assets/img/demo/avatar-1.jpg" alt="" /></span>--%>
								<%--<span class="subject">--%>
									<%--<span class="from">Иван Иванов</span>--%>
									<%--<span class="time">Just Now</span>--%>
								<%--</span>--%>
								<%--<span class="text">--%>
									<%--+7(925)779-38-06--%>
									<%--Вокал, Тюленева--%>
								<%--</span>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li class="footer">--%>
                        <%--<a href="javascript:void(0);">View all messages</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            </li>

            <!-- User Login Dropdown -->
            <li class="dropdown user">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="icon-male"></i>
                    <span class="username">${username}</span>
                    <i class="icon-caret-down small"></i>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="${pageContext.request.contextPath}/tm/admin"><i class="icon-lock"></i>Управление</a></li>
                    <li><a href="${pageContext.request.contextPath}/tm/finance"><i class="icon-lock"></i>Финансы</a></li>
                    <li><a href="${pageContext.request.contextPath}/settings"><i class="icon-cog"></i>Настройки</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout"><i class="icon-key"></i>Выход</a></li>
                </ul>
            </li>
            <!-- /user login dropdown -->
        </ul>
        <!-- /Top Right Menu -->
    </div>
    <!-- /top navigation bar -->
</header> <!-- /.header -->