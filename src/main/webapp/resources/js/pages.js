var selectedPage = /*$('a.buttons_header_selected');*/null;
/**
 *
 */
function initDatePickers() {
    var yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    var now = new Date();
    $('#first_date').datetimepicker({
        value:yesterday,
        maxDate:now
    });
    $('#second_date').datetimepicker({
        value:new Date(),
        maxDate:now
    });
}

function initGetPointsButton() {
    $('#submit_get_points').click(function () {
        var firstDate = $('#first_date').datetimepicker('getValue');
        var secondDate = $('#second_date').datetimepicker('getValue');
        //TODO: Add validation
        getAndBuildByDate(firstDate, secondDate);
    });
}

$('#logout_link').click(function () {
    $('#logout_form').submit();
});

$('#find_by_date').click(function () {
    console.log('find_by_date');
    if(this != selectedPage) {
        $('.sidebar').empty().append('' +
            '<div class="input_div">' +
            '<h2>First date</h2>' +
            '<input type="datetime" id="first_date">' +
            '</div>' +
            '<div class="input_div">' +
            '<h2>Second date</h2>' +
            '<input type="datetime" id="second_date">' +
            '</div>' +
            '<a class="tracker_button" id="submit_get_points">Get points</a>');
        initDatePickers();
        initGetPointsButton();
    }
});

//
$('#gadgets').click(function () {
    console.log('gadgets');
    if(this != selectedPage) {
        $('.sidebar').empty().append('' +
            '<div id="gadgets_div">' +
            '<h1>Gadgets</h1>' +
            '<table>' +
            '<tr>' +
            '<td><img src="resources/img/lamp_active.png" alt=""></td>' +
            '<td>My phone</td>' +
            '<td>Onore de balzaka..</td>' +
            '</tr>' +
            '<tr>' +
            '<td>' +
            '<img src="resources/img/lamp_inactive.png" alt="">' +
            '</td>' +
            '<td>My phone</td>' +
            '<td>Onore de balzaka..</td>' +
            '</tr>' +
            '</table>' +
            '</div>');
    }
});