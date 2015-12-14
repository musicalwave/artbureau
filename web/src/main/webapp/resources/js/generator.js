window.generator = {
    clientStatusIconHTML: function (clientStatus, color) {
        var image;
        if (clientStatus == 1) {
            image = 'student';
        } else if (clientStatus == 2) {
            image = 'classmate'
        } else if (clientStatus == 3) {
            image = 'demographic'
        } else {
            return '';
        }

        return '<img class="icon" src="resources/i/' + image + '_40_' + color + '.png">';
    },
    clientStatusText: function (clientStatus) {
        if (clientStatus == 1) {
            return 'индивидуальное';
        } else if (clientStatus == 2) {
            return 'групповое';
        } else {
            return 'ансамбль';
        }
    },
    billIconHTML: function (color) {
        return '<img class="icon" src="resources/i/bill_40_' + color + '.png">';
    },
    doneStatusText: function (doneStatus) {
        if (doneStatus == 1) {
            return 'активное';
        }
        if (doneStatus == 2) {
            return 'проведенное';
        }
        if (doneStatus == 3) {
            return 'сгоревшее';
        }
        return 'неизвестно';
    },
    doneStatusHTML: function (doneStatus) {
//        return '<span style="color: ' +
//            generator.getStatusColor({hasLesson: 1, doneStatus: doneStatus}) +
//            ';">' + generator.doneStatusText(doneStatus) + '</span>';
        return '<span>'+ generator.doneStatusText(doneStatus) + '</span>';
    },
    contractStatusText: function (contractStatus) {
        if (contractStatus == 1) {
            return 'абонемент';
        }
        if (contractStatus == 2) {
            return 'пробный';
        }
        return 'неизвестно';
    },
    getStatusColor: function (event) {
        if (!event.hasLesson) {
            return COLORS.EMPTY;
        }

        if (event.needSetStatus == 1) {
            return COLORS.NEED_SET_STATUS;
        }

        if (event.hasLesson) {

            console.log(event)
        }

        switch (event.doneStatus) {
            case 1:
                if (event.contractStatus == 1) {
                    return COLORS.PLANNED;
                } else {
                    return COLORS.TRIAL;
                }
            case 2:
                return COLORS.FINISHED;
            case 3:
                return COLORS.BURNED;
            default:
//                throw 'unexpected state';
                return '#000';
        }
    },
    answer: function (ans) {
        if (ans) {
            return 'да';
        } else {
            return 'нет';
        }
    },
    actionsHtml: function(event) {
        var currentDate = new Date();
        if (!event.hasLesson) {
            return '<a class="btn btn-primary" href="/teacher/' + event.teacherId + '">Подробнее</a>'
        }
        var res = '';
        if (event.needSetStatus || event.doneStatus == 1) {
            res += '<div class="btn-group">';
            res += '<button id="do" class="btn" data-lesson="'+ event.lessonId +'">Провести</button> ';
            res += '<button id="burn" class="btn" data-lesson="'+ event.lessonId +'">Сжечь</button>';
            res += '<div class="btn-group">';
            res += '<button class="btn dropdown-toggle" data-toggle="dropdown">Перенести <span class="caret"></span></button>';
            res += '<ul class="dropdown-menu">';
            res += '<li><a href="#" class="transfer" data-reason="2" data-lesson="'+ event.lessonId +'">по инициативе преподавателя</a></li> ';
            res += '<li><a href="#" class="transfer" data-reason="1" data-lesson="'+ event.lessonId +'">по инициативе ученика</a></li> ';
            res += '<li><a href="#" class="transfer" data-reason="3" data-lesson="'+ event.lessonId +'">по инициативе школы</a></li> ';
            res += '</ul>';
            res += '</div>';
            res += '</div><br/>';
        }

        res += '<a class="btn btn-primary" href="/contract/' + event.contractId + '">Подробнее</a>'

        return res;
    }

};