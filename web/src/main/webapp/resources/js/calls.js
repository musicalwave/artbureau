function getSelectedStatusId() {
    return parseInt($('#call-status-select').
                        children(':selected').first().val());
}

function getTeachersSuccess(response) {

    var $select = $('#call-teacher-select');

    // delete select's options
    $select.empty();

    // build new options
    var $teachers = $(response);
    for (var i = 0; i < $teachers.size(); i++) {
        $select.append('<option value="' + $teachers[i].id + '">'
                       + $teachers[i].name
                       + '</option>');
    }

    // recreate select
    $select.select2();
}

function callStatusChanged() {
    $.ajax({
        url: '/do/new-contract-status',
        data: {
            id: getSelectedStatusId()
        },
        success: callStatusChangedSuccess
    })
}

function callStatusChangedSuccess(newContractStatus) {
    if(newContractStatus)
        $('#contract-option-group').show();
    else
        $('#contract-option-group').hide();
}


$(document).ready(function() {

    $('#call-type-select').change(function() {
        
        // construct array of selected types
        var types =  $(this).children(':selected');
        var typeIds = [];
        for(var i = 0; i < types.size(); i++) {
            typeIds[i] = types[i].value;
        }
        
        // get teachers of selected types
        $.ajax({
               url: '/do/teachers/',
               data: {
                   types: typeIds.join()
               },
               success: getTeachersSuccess
           });
    })

    $('#call-status-select').change(callStatusChanged);
});
