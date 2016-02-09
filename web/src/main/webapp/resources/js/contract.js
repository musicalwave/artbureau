var getCurrentOption = function() {
    return null;
}

function setCurrentOption(option) {
    getCurrentOption = function() {
        return option;
    }
}

function getSelectedTeacherTypeId() {
    return $('#teacher-type').children(':selected').first().val();
}

function getSelectedTypeId() {
    return $('#lesson-type').children(':selected').first().val();
}

function getSelectedContractType() {
    return $('#contract-type').children(':selected').first().val();
}

function getSelectedContractOption() {
    return $('#contract-option').children(':selected').first().val();
}

function getTotal() {
    return parseInt($('#total').val());
}

function setTotal(total) {
    $('#total').val(total);
    recreatePayments();
}

function getSingleLessonPrice() {
    return parseInt($('#single-lesson-price').val());
}

function setSingleLessonPrice(price) {
    $('#single-lesson-price').val(price);
    updateTotal();
}

function getDiscount() {
    return parseInt($('select#discount').children(':selected').first().val());
}

function getLessonCount() {
    return parseInt($('#lesson-count').val());
}

function setLessonCount(count) {
    $('#lesson-count').val(count);
    updateTotal();
}

function getPaymentCount() {
    return $('.payment-count').val();
}

function setPaymentCount(count) {
    $('.payment-count').val(count);
}

function setPaymentCountRange(min, max) {
    $('.payment-count').attr('min', min);
    $('.payment-count').attr('max', max);
}

function setFirstLessonDate(date) {
    $('#first-lesson-date').datepicker(
              'setDate',
              date);
}

function getFirstLessonDate() {
    return $('#first-lesson-date').val();
}

function setFirstLessonDateChecker(checker) {
    $('#first-lesson-date').datepicker(
        'option',
        'beforeShowDay',
        checker);
}

function refillSelect($select, options) {
    $select.empty();
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
        url: '/do/teachers/type/' + type_id,
        success: updateTeacherTypesSuccess
    });
}

function updateTeacherTypesSuccess(response) {
    var $teachers = $(response);

    var options = '';
    for (var i = 0; i < $teachers.size(); i++) {
        options = options.concat(
        '<option value="' + $teachers[i].id + '">' +
            $teachers[i].teacherName +
        '</option>');
    }

    refillSelect($('#teacher-type'), options);

    teacherTypeChanged();
}

function updateSchedule() {

    // get schedule of selected teacher
    $.ajax({
        url: '/do/teachers/schedule',
        data: {
            teacherTypeId: getSelectedTeacherTypeId()
        },
        success: updateScheduleSuccess
    });
}

function updateScheduleSuccess(response) {
    var $events = $(response);

    var options = '';
    for (var i = 0; i < $events.size(); i++) {
        $event = $events[i];
        options = options.concat(
        '<option value="' + $event.id + '">' +
            $event.weekdayS   + ':' +
            $event.startTime  + '-' + 
            $event.finishTime +
        '</option>');
    }

    refillSelect($('#schedule'), options);
}

function updatePrice() {

    $.ajax({
        url: '/do/teachers/price',
        data: {
            teacherTypeId: getSelectedTeacherTypeId(),
            typeId: getSelectedTypeId(),
            contractType: getSelectedContractType
        },
        success: setSingleLessonPrice
    });
}


function updateWorkingDays() {

    $.ajax({
        url: '/do/teachers/working-days',
        data: {
            teacherTypeId: getSelectedTeacherTypeId()
        },
        success: updateWorkingDaysSuccess
    });
}

function updateWorkingDaysSuccess(response) {

    setFirstLessonDateChecker(function(date) {
        // only working days are selectable
        return [$.inArray(date.getDay(), response) !== -1, '', ''];
    });

    if(response.length > 0) {
        var firstLessonDate = moment();
        while($.inArray(firstLessonDate.weekday(), response) === -1)
          firstLessonDate.add(1, 'd');
        setFirstLessonDate(firstLessonDate.format('DD-MM-YYYY'));
        updateContractOption();
    }

}

function updateTotal() {
    var total = Math.floor(getLessonCount() * 
                           getSingleLessonPrice() * 
                           (1 - getDiscount() / 100));
    setTotal(total);
}

function updateContractOption() {
    $.ajax({
        url: '/do/contract/option',
        data: {
            id: getSelectedContractOption()
        },
        success: updateContractOptionSuccess
    })
}

function updateContractOptionSuccess(option) {
    setCurrentOption(option);
    setLessonCount(getCurrentOption().lessonCount);    
    updatePayments();
}

function updatePayments() {
    setPaymentCount(1);
    setPaymentCountRange(1, getCurrentOption().maxPaymentsCount);    
    recreatePayments();
}

function getWarningText(delta) {
    if(delta > 0)
        return 'Перебор на ' + delta + ' руб.';
    else
        return 'Не хватает ' + -delta + ' руб.';
}

function recreatePayments() {    
    
    var $paymentsTableBody = $('#payments-table').children('tbody');
    $paymentsTableBody.empty();

    if(getTotal() !== 0) {
        var option = getCurrentOption();
        var nextPaymentDate = moment(getFirstLessonDate(), 'DD-MM-YYYY');
        var singlePaymentAmount = Math.floor(getTotal() /  getPaymentCount());
        var shortage = getTotal() - getPaymentCount() * singlePaymentAmount;
        for(var i = 0; i < getPaymentCount(); i++) {

            var amount = singlePaymentAmount + (i === getPaymentCount() - 1 
                                                ? shortage 
                                                : 0);

            var row = '<tr>' +
                        '<td>' +
                          '<input name="payments[' + i + '].dateS" ' + 
                                 'value="' + nextPaymentDate.format("DD-MM-YYYY") + '" ' +
                                 'class="payment-date form-control" ' +
                                 'type="text" />' +
                        '</td>' +
                        '<td>' +
                          '<input name="payments[' + i + '].value" ' + 
                                 'value="' + amount + '" ' +
                                 'class="payment-amount form-control" ' +
                                 'type="number" />' +
                        '</td>' +
                        '<td>' +
                          '<input name="payments[' + i + '].plannedS" ' + 
                                 'value="true" ' +
                                 'checked ' +
                                 'class="payment-planned form-control" ' +
                                 'type="checkbox" />' +
                        '</td>' +
                      '</tr>'
            $paymentsTableBody.append(row);
            nextPaymentDate.add(option.paymentInterval, 'd');
        }

        $paymentsTableBody.find('.payment-date').datepicker({
            dateFormat: 'dd-mm-yy',
            firstDay: 1,
            minDate: 0
        });

        $paymentsTableBody.find('.payment-planned').change(function() {
            $(this).val($(this).val() === 'true' ? 'false' : 'true');
        })

        $paymentsTableBody.find('.payment-amount').change(function() {
            var currentTotal = 0;
            $paymentsTableBody.find('.payment-amount').each(function() {
                currentTotal += parseInt($(this).val());
            });

            $('.payments-section').find('.warning').remove();
            $paymentsTableBody.find('.payment-amount').removeClass('bad-amount');
            if(getTotal() !== currentTotal) {
                $paymentsTableBody.find('.payment-amount').addClass('bad-amount');
                $('.payments-section').append(
                    '<label class="form-control warning">' + 
                        getWarningText(currentTotal - getTotal()) + 
                    '</label>');
            }
        })
    }
}

function firstLessonDateChanged() {
    recreatePayments();
}

$(document).ready(function() {
    $('#lesson-type').change(updateTeacherTypes);
    $('#contract-type').change(updatePrice);
    $('#contract-option').change(updateContractOption);
    $('#lesson-count').change(updateTotal);
    $('#teacher-type').change(teacherTypeChanged);
    $('#discount').change(updateTotal);
    $('.payment-count').change(recreatePayments);
    $('#first-lesson-date').change(firstLessonDateChanged);

    $('#first-lesson-date').datepicker({
        dateFormat: 'dd-mm-yy',
        firstDay: 1,
        minDate: 0
    });

    updateWorkingDays();
})