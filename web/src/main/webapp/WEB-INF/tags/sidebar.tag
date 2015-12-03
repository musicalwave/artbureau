<%@tag description="Sidebar template" pageEncoding="UTF-8"%>
<%@ attribute name="ntasks" required="true"%>
<%@ attribute name="userid" required="true"%>

<div id="sidebar" class="sidebar-fixed">
    <div id="sidebar-content">

        <!-- Search Input -->
        <form class="sidebar-search">
            <div class="input-box">
                <button type="submit" class="submit">
                    <i class="icon-search"></i>
                </button>
						<span>
							<input type="text" placeholder="Search...">
						</span>
            </div>
        </form>

        <!-- Search Results -->
        <div class="sidebar-search-results">

            <i class="icon-remove close"></i>
            <!-- Persons -->
            <div class="title">
                Persons
            </div>
            <ul class="notifications">
                <li>
                    <%--<a href="javascript:void(0);">--%>
                        <%--<div class="col-left">--%>
                            <%--<span class="label label-danger"><i class="icon-female"></i></span>--%>
                        <%--</div>--%>
                        <%--<div class="col-right with-margin">--%>
                            <%--<span class="message">Jane <strong>Doe</strong></span>--%>
                            <%--<span class="time">21 years old</span>--%>
                        <%--</div>--%>
                    <%--</a>--%>
                </li>
            </ul>
        </div> <!-- /.sidebar-search-results -->

        <!--=== Navigation ===-->
        <ul id="nav">
            <li>
                <a href="${pageContext.request.contextPath}/tasks">
                    <i class="icon-ok"></i>
                    Задания
                    <span class="label label-info pull-right">${ntasks}</span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/task">
                            <i class="icon-plus"></i>
                            Новое задание
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/tasks">
                            <i class="icon-angle-right"></i>
                            Входящие
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/tasks/my">
                            <i class="icon-angle-left"></i>
                            Исходящие
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/calls">
                    <i class="icon-phone"></i>
                    Звонки
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/call?client=new">
                            <i class="icon-plus"></i>
                            Новый звонок
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/clients">
                    <i class="icon-male"></i>
                    Клиенты
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/client">
                            <i class="icon-plus"></i>
                            Новый клиент
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/client/search">
                            <i class="icon-search"></i>
                            Поиск
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/client/site">
                            <i class="icon-download-alt"></i>
                            Заявки с сайта
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/teacher/all">
                    <i class="icon-music"></i>
                    Преподаватели
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/teacher">
                            <i class="icon-plus"></i>
                            Новый преподаватель
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/teacher/search">
                            <i class="icon-search"></i>
                            Поиск
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/teacher/all">
                            <i class="icon-th-list"></i>
                            Все
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/stat">
                    <i class="icon-group"></i>
                    Статистика
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/stat/planned">
                            <i class="icon-usd"></i>
                            Запланированние платежи
                        </a>
                        <a href="${pageContext.request.contextPath}/stat/done">
                            <i class="icon-usd"></i>
                            Проведенные платежи
                        </a>
                        <a href="${pageContext.request.contextPath}/stat/last">
                            <i class="icon-bell"></i>
                            Последнее занятие
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/sched">
                    <i class="icon-calendar"></i>
                    Расписание
                </a>
            </li>
        </ul>
        <!-- /Navigation -->
    </div>
    <div id="divider" class="resizeable"></div>
</div>
<!-- /Sidebar -->
