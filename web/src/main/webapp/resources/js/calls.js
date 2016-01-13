function getTeachersSuccess(response) {

    var $select = $('select#call-teacher-select');

    // delete select's options
    $select.html('');

    // build new options
    var $teachers = $(response);
    for (var i = 0; i < $teachers.size(); i++) {
        $select.append("<option value=\"" + $teachers[i].id + "\">"
                       + $teachers[i].name
                       + "</option>");
    }

    // recreate select
    $select.select2();
}

function getTeachersError(xhr, status, errorThrown) {
    alert( "Sorry, there was a problem!" );
    console.log( "Error: " + errorThrown );
    console.log( "Status: " + status );
    console.dir( xhr );
}

$(document).ready(function() {

    $('select#call-type-select').change(function() {
        
        // construct array of selected types
        var types =  $(this).children(':selected');
        var typeIds = [];
        for(var i = 0; i < types.size(); i++) {
            typeIds[i] = types[i].value;
        }
        
        // get teachers of selected types
        $.ajax({
               url: "/do/teachers/",
               data: "types=" + typeIds.join(),
               type: "GET",
               dataType : "json",
               success: getTeachersSuccess,
               error: getTeachersError
           });
    })
});
