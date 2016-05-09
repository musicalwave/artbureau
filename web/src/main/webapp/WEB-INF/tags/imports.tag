<%@tag description="Imports template" pageEncoding="UTF-8"%>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" />

<!--=== CSS ===-->

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/js/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<!-- jQuery UI -->
<!--<link href="${pageContext.request.contextPath}/resources/plugins/jquery-ui/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />-->
<!--[if lt IE 9]>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-ui/jquery.ui.1.10.2.ie.css"/>
<![endif]-->

<!-- Theme -->
<link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/main.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/responsive.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/js/lib/assets/css/icons.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome.min.css">
<!--[if IE 7]>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/lib/assets/css/fontawesome/font-awesome-ie7.min.css">
<![endif]-->

<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,600,700' rel='stylesheet' type='text/css'>

<link href="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-timepicker/jquery.timepicker.css" rel="stylesheet" type="text/css" />

<!--=== JavaScript ===-->

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-ui/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-ui/datepicker-ru.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/jquery-timepicker/jquery.timepicker.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/lodash.compat.min.js"></script>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/html5shiv.js"></script>
<![endif]-->

<!-- Smartphone Touch Events -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/touchpunch/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/event.swipe/jquery.event.move.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/event.swipe/jquery.event.swipe.js"></script>

<!-- General -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/libs/breakpoints.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/respond/respond.min.js"></script> <!-- Polyfill for min/max-width CSS3 Media Queries (only for IE8) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/cookie/jquery.cookie.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/slimscroll/jquery.slimscroll.horizontal.min.js"></script>

<%--<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/fullcalendar/fullcalendar.min.js"></script>--%>

<!-- Pickers -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/pickadate/picker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/pickadate/picker.date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/pickadate/picker.time.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/bootstrap-colorpicker/bootstrap-colorpicker.min.js"></script>

<!-- DataTables -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/datatables/tabletools/TableTools.min.js"></script> <!-- optional -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/datatables/colvis/ColVis.min.js"></script> <!-- optional -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/datatables/DT_bootstrap.js"></script>

<!-- Circle Dials -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/blockui/jquery.blockUI.min.js"></script>

<!-- Slim Progress Bars -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/nprogress/nprogress.js"></script>

<!-- Bootbox -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/bootbox/bootbox.js"></script>

<!-- Charts -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/sparkline/jquery.sparkline.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/blockui/jquery.blockUI.min.js"></script>


<!-- Forms -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/typeahead/typeahead.min.js"></script> <!-- AutoComplete -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/autosize/jquery.autosize.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/inputlimiter/jquery.inputlimiter.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/uniform/jquery.uniform.min.js"></script> <!-- Styled radio and checkboxes -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/tagsinput/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/select2/select2.min.js"></script> <!-- Styled select boxes -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/fileinput/fileinput.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/duallistbox/jquery.duallistbox.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/bootstrap-inputmask/jquery.inputmask.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/bootstrap-wysihtml5/wysihtml5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/bootstrap-multiselect/bootstrap-multiselect.min.js"></script>

<!-- Form Validation -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/validation/additional-methods.min.js"></script>

<!-- Noty -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/noty/jquery.noty.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/noty/layouts/top.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/plugins/noty/themes/default.js"></script>

<!-- App -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/app.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/plugins.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/plugins.form-components.js"></script>


<script>
    $(document).ready(function(){
        "use strict";

        App.init(); // Init layout and core plugins
        Plugins.init(); // Init all plugins
        FormComponents.init(); // Init all form-specific plugins
    });
</script>

<!-- Demo JS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/demo/ui_general.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/demo/form_components.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/lib/assets/js/demo/form_validation.js"></script>


