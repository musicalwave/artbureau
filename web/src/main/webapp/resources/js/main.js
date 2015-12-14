

$(document).ready(function() {

    $('#calendar_wrapper').append(
        '<div class="content">' +
            '<div id="rooms"></div>' +
        '    <div id="calendar">' +
        '    </div>' +
        '</div>' +
        '<div style="display: none;">' +
        '    <div class="box-modal" id="exampleModal">' +
        '        <div class="box-modal_close arcticmodal-close">закрыть</div>' +
        '        <span class="caption">Имя преподавателя: </span><span class="value" id="teacherName"></span><br/>' +
        '        <span class="time">Время события: </span><span class="value" id="time"></span><br/>' +
        '        <div class="detail">' +
        '            <span class="caption">Имя клиента: </span><span class="value" id="clientName"></span><br/>' +
        '            <span class="caption">Телефон клиента: </span><span class="value" id="clientPhone"></span><br/>' +
        '            <span class="caption">Предмет: </span><span class="value" id="typeName"></span><br/>' +
        '            <span class="caption">Комната: </span><span class="value" id="roomName"></span><br/>' +
        '            <span class="caption">Тип занятия: </span><span class="value" id="clientStatus"></span><br/>' +
        '            <span class="caption">Статус занятия: </span><span class="value" id="doneStatus"></span><br/>' +
        '            <span class="caption">Статус контракта: </span><span class="value" id="contractStatus"></span><br/>' +
        '            <span class="caption">Есть задание: </span><span class="value" id="hasTask"></span><br/>' +
        '            <span class="caption">Есть платеж: </span><span class="value" id="hasPayment"></span><br/>' +
        '        </div>' +
        '        <div id="actions"></div>' +
        '    </div>' +
        '    <div class="box-modal" id="createModal">' +
        '        <div class="box-modal_close arcticmodal-close">закрыть</div>' +
        '        <div id="create_contents"></div>' +
        '    </div>' +
        '    <div class="box-modal" id="commentModal">' +
        '        <div class="box-modal_close arcticmodal-close">закрыть</div>' +
        '        <span class="value">Введите комментарий</span><br/> ' +
        '        <textarea id="comment"></textarea><br/>' +
        '        <a class="btn" id="comment_submit">Перенести</a> ' +
        '    </div>' +
        '</div>'

    );

    var minTime = 8, maxTime = 23;
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    var selectedRoom;
    var filterRooms = true;

    function setTimeline(view) {
        var parentDiv = jQuery(".fc-agenda-slots:visible").parent();
        var timeline = parentDiv.children(".timeline");
        if (timeline.length == 0) { //if timeline isn't there, add it
            timeline = jQuery("<hr>").addClass("timeline");
            parentDiv.prepend(timeline);
        }

        var curTime = new Date();

        var curCalView = jQuery("#calendar").fullCalendar('getView');
        if (curCalView.visStart < curTime && curCalView.visEnd > curTime) {
            timeline.show();
        } else {
            timeline.hide();
            return;
        }

        var curSeconds = ((curTime.getHours() - minTime) * 60 * 60) + (curTime.getMinutes() * 60) + curTime.getSeconds();
        var percentOfDay = curSeconds / ((maxTime - minTime) * 3600);
        var topLoc = Math.floor(parentDiv.height() * percentOfDay);

        timeline.css("top", topLoc + "px");

        if (curCalView.name == "agendaWeek") { //week view, don't want the timeline to go the whole way across
            var dayCol = jQuery(".fc-today:visible");
            var left = dayCol.position().left + 1;
            var width = dayCol.width()-2;
            timeline.css({
                left: left + "px",
                width: width + "px"
            });
        }

    }



    var cache = {
        from: null,
        to: null,
        events: [],
        resources: [],
        add: function(event, resource) {
//            if (this.events.filter(function(ev){return ev.eventId == event.eventId}).length == 0) {
                this.events.push(event);
//            }
//            if (this.resources.filter(function(res){return res.id == resource.id}).length == 0) {
                this.resources.push(resource);
//            }
        },
        clear: function() {
            this.from = null;
            this.to = null;
            this.events = [];
            this.resources = [];
        }
    };

    function getDate(date) {
        return $.fullCalendar.formatDate(date, 'dd-MM-yyyy');
    }

    function getTime(date) {
        return $.fullCalendar.formatDate(date, 'HH:mm');
    }

    function fetchData(from, to, callback){
//        console.log('FETCH DATA', from, to);
//        console.log('CACHE', cache.from, cache.to);
        if (!cache.from || !cache.to || to > cache.to || from < cache.from) {
            cache.to = !cache.to || to > cache.to ? to : cache.to;
            cache.from = !cache.from || from < cache.from ? from : cache.from;

            function convertDate(dateString) { //23-03-14
                return dateString;
            }

            $.get(
                DATASOURCES.LESSONS + '?sd=' + getDate(from) + '&fd=' + getDate(to),
                function(data) {
                    var events = [];
                    for (var p in data) {
                        if (data.hasOwnProperty(p)) {
                            for (var i in data[p]) {
                                events.push(data[p][i]);
                            }
                        }
                    }
                    //Трансформируем данные с сервера в формат FullCalendar.
                    for (var i = 0; i < events.length; i++) {
                        var event = events[i];

                        event.allDay = false;
                        event.start = convertDate(event.date) + ' ' + event.startTime;
                        event.end = convertDate(event.date) + ' ' + event.finishTime;
                        if (event.hasLesson == 1) {
                            event.title = event.clientName + ' (' + (event.clientPhone || 'нет телефона') + ')';
                        } else {
                            event.title = '';
                        }

                        event.color = generator.getStatusColor(event);
                        event.textColor = COLORS.TEXT;
                        event.borderColor = COLORS.BORDER;
                        event.resources = '' + event.roomId;
                        cache.add(event, {id: event.roomId, name: event.roomName || 'Room ' + event.roomId});
                    }
//                    console.log('FETCHED:');
                    var evs = filterRooms? events.filter(function(ev){return ev.roomId == selectedRoom}) : events;
//                    console.log(evs);
//                    console.log('-------');
//                    console.log(cache);
//                    console.log('-------');
                    callback(evs);
                }
            );
        } else {
//            console.log('CACHE:');
            var evs = filterRooms? cache.events.filter(function(ev){return ev.roomId == selectedRoom}) : cache.events;
//            console.log(evs);
//            console.log('-------');
//            console.log(cache);
//            console.log('-------');
            callback(evs);

        }

    }


    function fetchRooms(callback) {
        $.get(DATASOURCES.ROOMS, callback);

//        return callback(
//            [ {
//                "id" : 1,
//                "filialId" : 1,
//                "name" : "Green"
//            }, {
//                "id" : 2,
//                "filialId" : 1,
//                "name" : "Blue"
//            } ]
//        );
    }
    fetchRooms(function(rooms){
        selectedRoom = rooms[0].id;
        rooms = rooms.map(function(r) {
            return {
                id: '' + r.id,
                filialId: r.filialId,
                name: r.name
            }
        });

        var roomsSelector = $('#rooms');
        roomsSelector.append('Комната: | ');
        rooms.map(function(r){
            roomsSelector.append('<a class="room" data-room="' + r.id + '">' + r.name + '</a> | ');
        });

        $('.room').click(function(){
            $('.room').removeClass('active');
            $(this).addClass('active');
            selectedRoom = $(this).data('room');
            $('#calendar').fullCalendar('refetchEvents');
        });

        $('[data-room="'+ selectedRoom + '"]').addClass('active');

        $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'agendaWeek,agendaDay,resourceDay'
            },
            defaultView: 'agendaWeek',
            editable: false,
            eventClick: function(calEvent, jsEvent, view) {

                $('#teacherName').html(calEvent.teacherName);
                $('#time').html(calEvent.startTime.split(':').slice(0,2).join(':') + '-' + calEvent.finishTime.split(':').slice(0,2).join(':'));

                if (calEvent.hasLesson) {
                    $('.detail').show();

                    $('#clientName').html(calEvent.clientName);
                    $('#clientPhone').html(calEvent.phoneName || 'нет');
                    $('#typeName').html(calEvent.typeName);
                    $('#roomName').html(calEvent.roomName);
                    $('#clientStatus').html(generator.clientStatusIconHTML(calEvent.clientStatus, 'black') + generator.clientStatusText(calEvent.clientStatus));
                    if (calEvent.needSetStatus) {
                        $('#doneStatus').html('<span style="color: red;">необходимо проставить статус занятия!!!</span>');
                    } else {
                        $('#doneStatus').html(generator.doneStatusHTML(calEvent.doneStatus));
                    }

                    $('#contractStatus').html(generator.contractStatusText(calEvent.contractStatus));
                    $('#hasTask').html(generator.answer(calEvent.hasTask));
                    $('#hasPayment').html(generator.answer(calEvent.hasPayment));
                } else {
                    $('.detail').hide();
                }

                $('#actions').html(generator.actionsHtml(calEvent));

                var processEvent = function(action, opts, done, needSet, hasLesson) {
                    calEvent.doneStatus = done;
                    calEvent.needSetStatus = needSet;
                    calEvent.hasLesson = hasLesson;
    //                $.post('/do/lesson/' + calEvent.lessonId + '/' + action, opts);
                    $.post(
                        DATASOURCES.LESSON
                            .replace('{id}', calEvent.lessonId)
                            .replace('{action}', action),
                        opts
                    );
                    calEvent.color = generator.getStatusColor(calEvent);
                    $('#calendar').fullCalendar('rerenderEvents');
                    $.arcticmodal('close');
                };

                $('#do').click(function(){
                    processEvent('do', {}, 2, 0, 1);
                });
                $('#burn').click(function(){
                    processEvent('burn', {}, 3, 0, 1);
                });
                $('.transfer').click(function(){
                    $('#comment_submit').data('reason', $(this).data('reason'));
                    $('#comment').val('');
                    $('#commentModal').arcticmodal();
//                    processEvent('transfer', {reason: $(this).data('reason')}, 0, 0, 0);
                });

                $('#comment_submit').click(function(){
                    processEvent('transfer', {
                        reason: $(this).data('reason'),
                        comment: $('#comment').val()
                    }, 0, 0, 0);
                });


                $('#exampleModal').arcticmodal();
            },
            minTime: minTime,
            maxTime: maxTime,
            slotMinutes:30,
            timeFormat: 'H:mm',
            dayNames: ['Воскресение', 'Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота'],
            dayNamesShort: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            titleFormat: {
                month: 'MMMM yyyy',
                week: "MMMM d[ yyyy]{ '&#8212;'[ MMM] d yyyy}",
                day: 'dddd, dd.MM, yyyy'
            },
            allDaySlot: false,
            eventRender: function(event, element, view) {
                var title = $(element).find('.fc-event-title');
                var time = $(element).find('.fc-event-time');

                time.append(' ' + event.teacherName);

                title.append(generator.clientStatusIconHTML(event.clientStatus, COLORS.ICONS));
                if (event.hasLesson && event.hasPayment == 0) {
                    title.append(generator.billIconHTML(COLORS.ICONS));
                }
                if (event.hasLesson && event.needSetStatus) {
//                    $(element).addClass('warning');
                    title.append('<img class="warning-icon" width="14" src="resources/i/warning.png">')
                }


                var popover_content = '<span class="caption">Имя преподавателя: </span><span class="value">' + event.teacherName + '</span><br/>' +
                    '        <span class="time">Время события: </span><span class="value">' + (event.startTime.split(':').slice(0,2).join(':') + '-' + event.finishTime.split(':').slice(0,2).join(':')) + '</span><br/>' +
                    (event.hasLesson ?
                    '        <div class="detail">' +
                    '            <span class="caption">Имя клиента: </span><span class="value">' + event.clientName + '</span><br/>' +
                    '            <span class="caption">Телефон клиента: </span><span class="value">' + (event.phoneName || 'нет') +'</span><br/>' +
                    '            <span class="caption">Предмет: </span><span class="value">' + event.typeName + '</span><br/>' +
                    '            <span class="caption">Комната: </span><span class="value">' + event.roomName +'</span><br/>' +
                    '            <span class="caption">Тип занятия: </span><span class="value">' + (generator.clientStatusIconHTML(event.clientStatus, 'black') + generator.clientStatusText(event.clientStatus)) + '</span><br/>' +
                    '            <span class="caption">Статус занятия: </span><span class="value">' +
                        (event.needSetStatus ? '<span style="color: red;">необходимо проставить статус занятия!!!</span>' : generator.doneStatusHTML(event.doneStatus))
                        + '</span><br/>' +
                    '            <span class="caption">Статус контракта: </span><span class="value">' + generator.contractStatusText(event.contractStatus) + '</span><br/>' +
                    '            <span class="caption">Есть задание: </span><span class="value">' + generator.answer(event.hasTask)+ '</span><br/>' +
                    '            <span class="caption">Есть платеж: </span><span class="value">' + generator.answer(event.hasPayment) + '</span><br/>' +
                    '        </div>'
                    : '');

                $(element).popover({
                    content: popover_content,
                    html: true,
                    trigger: 'hover',
                    placement: 'auto left'
                });


            },
            resources: rooms,
            events: fetchData,
            viewDisplay: function(view) {
                if (view.name == 'resourceDay') {
                    $('#rooms').hide();
                    filterRooms = false;
                }  else {
                    $('#rooms').show();
                    filterRooms = true;
                }
                $('#calendar').fullCalendar('refetchEvents');
            },
            viewRender: function(view){
                try {
                    setTimeline(view);
                } catch(err) {}
            },
            dayClick: function(date, allDay, jsEvent, view) {

                var dateStr = getDate(date),
                    timeStr = getTime(date),
                    html = '<strong>' + dateStr + ' ' + timeStr + '</strong>';
                html += '<br>';
                html += '<a class="btn btn-primary" href="' + DATASOURCES.CREATE.replace('{date}', dateStr).replace('{time}', timeStr).replace('{room}', selectedRoom) + '">Создать событие</a>';

                $('#create_contents').html(html);
                $('#createModal').arcticmodal();

                console.log(view);

            }
        });

        $('.fc-button-agendaWeek').html('Неделя');
        $('.fc-button-agendaDay').html('День');
        $('.fc-button-resourceDay').html('Комнаты');

//        $('.fc-header-left').append(
//            '<span class="fc-header-space"></span>' +
//            '<span>Комната: </span>' +
//            '<span class="fc-header-space"></span>' +
//            '<select id="roomSelect">' +
//                rooms.map(function(x) {
//                    return '<option value="'+ x.id + '">' + x.name + '</option>'
//                }).join(' ') +
//            '</select>'
//        );

//        $('#roomSelect').change(function(ev){
//            selectedRoom = $(this).find(':selected').val();
//            $('#calendar').fullCalendar('refetchEvents');
//        });


        setInterval(function(){
            cache.clear();
            $('#calendar').fullCalendar('refetchEvents');
            setTimeline();
        }, window.CONFIG.REFRESH_TIMEOUT || 60000 * 5);

    });

});

