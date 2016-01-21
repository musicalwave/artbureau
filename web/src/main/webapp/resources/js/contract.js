function getSelectedTeacherTypeId() {
    return $("select#teacher-type").children(':selected').first().val();
}

function getSelectedTypeId() {
    return $("select#lesson-type").children(':selected').first().val();
}

function getSelectedContractType() {
    return $("select#contract-type").children(':selected').first().val();
}

function refillSelect($select, options) {
    $select.html('');
    $select.append(options);
    $select.select2();
}

function teacherTypeChanged() {
    updateWorkingDays();
    updateSchedule();
    updatePrice();
}


function updateTeacherTypes() {
    var type_id = getSelectedTypeId();

    // get teacher types of selected lesson type
    $.ajax({
        url: "/do/teachers/type/" + type_id,
        success: updateTeacherTypesSuccess
    });
}

function updateTeacherTypesSuccess(response) {
    var $teachers = $(response);

    var options = "";
    for (var i = 0; i < $teachers.size(); i++) {
        options = options.concat("<option value=\"" + $teachers[i].id + "\">"
            + $teachers[i].teacherName
            + "</option>");
    }


    refillSelect($('select#teacher-type'), options);

    teacherTypeChanged();
}

function updateSchedule() {
    var teacher_type_id = getSelectedTeacherTypeId();

    // get schedule of selected teacher
    $.ajax({
        url: "/do/teachers/schedule",
        data: "teacherTypeId=" + teacher_type_id,
        success: updateScheduleSuccess
    });
}

function updateScheduleSuccess(response) {
    var $events = $(response);

    var options = "";
    for (var i = 0; i < $events.size(); i++) {
        $event = $events[i];
        options = options.concat("<option value=\"" + $event.id + "\">"
            + $event.weekdayS + ":"
            + $event.startTime + "-" + $event.finishTime
            + "</option>");
    }

    refillSelect($('select#schedule'), options);
}

function updatePrice() {
    var teacher_type_id = getSelectedTeacherTypeId();
    var type_id = getSelectedTypeId();
    var contract_type = getSelectedContractType();

    $.ajax({
        url: "/do/teachers/price",
        data: "teacherTypeId=" + teacher_type_id + "&" +
        "typeId=" + type_id + "&" +
        "contractType=" + contract_type,
        success: updatePriceSuccess
    });
}

function updatePriceSuccess(response) {
    $("input#single-lesson-price").val(response);
    updateTotal();
}

function updateWorkingDays() {
    var teacher_type_id = getSelectedTeacherTypeId();

    $.ajax({
        url: "/do/teachers/working-days",
        data: "teacherTypeId=" + teacher_type_id,
        success: updateWorkingDaysSuccess
    });
}

function updateWorkingDaysSuccess(response) {
    var checkDate = function (date) {
        // only working days are selectable
        return [$.inArray(date.getDay(), response) !== -1, "", ""];
    }

    $( "input#first-lesson-date" ).datepicker( "option",
        "beforeShowDay",
        checkDate );
}

function updateTotal() {
    var discount = $("select#discount").children(':selected').first().val();
    var lessonPrice = $("input#single-lesson-price").val();
    var lessonCount = $("input#lesson-count").val();
    var total = lessonCount * lessonPrice * (1 - discount / 100);
    $("input#total").val(total);
}

$(document).ready(function() {
    $("select#lesson-type").change(updateTeacherTypes);
    $("select#contract-type").change(updatePrice);
    $("input#lesson-count").change(updateTotal);
    $("select#teacher-type").change(teacherTypeChanged);
    $("select#discount").change(updateTotal);

    $("input#first-lesson-date").datepicker({
        dateFormat: "dd-mm-yy",
        firstDay: 1,
        minDate: 0
    });

    updateWorkingDays();
})