<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tracker</title>
    <link rel="stylesheet" href="resources/css/reset.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <link rel="stylesheet" href="resources/css/gadgets.css">
    <link rel="stylesheet" href="resources/css/jquery.datetimepicker.min.css">
    <!-- -->
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
    <!-- -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="resources/js/jquery.datetimepicker.full.js"></script>
    <script src="https://cdn.socket.io/socket.io-1.0.0.js"></script>
    <script src="resources/js/maps.js"></script>
    <script src="resources/js/gadgets.js"></script>
    <script src="resources/js/socket.js"></script>
    <!-- -->
    <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>
</head>
<body>

<div class="header">
    <div class="links_header left_links_header">
        <ul>
            <li><a id="find_by_date" class="buttons_header buttons_header_selected" href="#">Find by date</a></li>
            <li><a id="gadgets" class="buttons_header" href="#">Gadgets</a></li>
            <li><a id="tracks" class="buttons_header" href="#">Tracks</a></li>
        </ul>
    </div>
    <div class="links_header right_links_header">
        <ul>
            <li><a class="buttons_header" href="#">Log in</a></li>
            <li><a class="buttons_header" id="logout_link" href="#">Log out</a></li>
        </ul>
    </div>
</div>

<form action="/logout" method="post" id="logout_form">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />
</form>

<div id="map"></div>
<div class="sidebar"></div>

<div id="ajax_overlay" class="overlay">
    <img id="loading_svg_img" src="resources/img/gears.svg" alt="loading32" />
</div>

<div class="overlay" id="chart_overlay" >
    <div id="chart_wrapper_id" class="chart_wrapper">
        <div id="chart" style="height: 250px;"></div>
    </div>
</div>
<script>
    var map;
    var currentPoint;
    var poly;
    var markers = [];
    var localWindows = [];
    var polylineHolder = [];
    var gadgetSubscribing = new GadgetSubscribe(socket);
    jQuery(document).ready(function($) {
        $('#find_by_date').trigger('click');
    });
    //
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
    //
    function initMap(lastPoint) {
        console.log(lastPoint.lat,', ', lastPoint.lng);
        if(!map) {
            map = new google.maps.Map(document.getElementById('map'), {
                center: {lat: lastPoint.lat, lng: lastPoint.lng},
                zoom: 14
            });
        } else {
            map.setCenter(new google.maps.LatLng({lat: lastPoint.lat, lng: lastPoint.lng}));
        }
        poly = new google.maps.Polyline({
            geodesic: true,
            strokeColor: '#00FF00',
            strokeOpacity: 1.0,
            strokeWeight: 3
        });
        poly.setMap(map);
    }
    //
    function getAndBuildByDate(from, to) {
        var unixTimeFrom = Math.round(from.getTime() / 1000);
        var unixTimeTo = Math.round(to.getTime() / 1000);
        var url = "api/points/from=" + unixTimeFrom + "/to=" + unixTimeTo;
        console.log("url: ", url);
        $.ajax({
            type: "GET",
            url : url,
            timeout : 100000,
            success: function(data){
                console.log(data);
                if(data.length != 0) buildMultiplePolyline(data);
            }
        });
    }
    //
    function lastPoint(callback) {
        var url = "api/points/quantity=1";
        $.ajax({
            type: "GET",
            url : url,
            timeout : 100000,
            success: function(data){
                callback.call(null, data[0]);
            }
        });
    }
    //
    function initGetPointsButton() {
        $('#submit_get_points').click(function () {
            var firstDate = $('#first_date').datetimepicker('getValue');
            var secondDate = $('#second_date').datetimepicker('getValue');
            //TODO: Add validation
            getAndBuildByDate(firstDate, secondDate);
        });
    }
    //
    $(document).ajaxStart(function () {
        $('#ajax_overlay').show();
    }).ajaxStop(function () {
        $('#ajax_overlay').hide();
    }).ajaxSuccess(function(event, xhr, settings){
        console.log(settings.url);
    });
    //
    $('#logout_link').click(function () {
        $('#logout_form').submit();
    });

    /**
     *
     */
    var selectedPage = $('a.buttons_header_selected');
    //
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
            _clearMap();
            initDatePickers();
            initGetPointsButton();
            lastPoint(initMap);
        }
    });
    //
    $('#gadgets').click(function () {
        console.log('gadgets');
        if(this != selectedPage) {
            $.ajax({
                type: "GET",
                url : 'api/gadgets',
                timeout : 100000,
                success: function(data, status, request) {
                    _clearMap();
                    if(request.responseText.charCodeAt(2) == 60) {
                        window.location = '/login';
                    }
                    console.log('My gadgets = ', data);
                    if(data.length != 0) {
                        $('.sidebar').empty().append(fillGadgetsTable(data));
                        _addListenersForCheckboxes();
                        addGadgetsOnMap(data);
                    }
                },
                error: function (data, status, request) {
                    console.log('Error = ',request);
                }
            });
        }
    });
    //
    function _addListenersForCheckboxes() {
        $('#gadgets_div input').click(function () {
            var isSubscribes = $(this).prop('checked');
            var gadgetId = $(this).attr('data_id');
            console.log('isSubscribes = ', isSubscribes, ', gadgetId = ', gadgetId);
            var self = this;
            $('#gadgets_div input').each(function (index, el) {
                if(self != el && $(this).prop('checked')) {
                    $(this).prop('checked', false);
                }
            });
            if(isSubscribes) {
                console.log('Subscribe on ', gadgetId);
                gadgetSubscribing.unSubscribe().subscribe(gadgetId);
                //TODO: Show only one gadget on map
            } else {
                console.log('Un subscribe');
                gadgetSubscribing.unSubscribe();
                //TODO: Show all gadgets
            }
        });
    }
    //
    function fillGadgetsTable(data) {
        var currentDate = new Date();
        var html =
                '<div id="gadgets_div">' +
                    '<div>' +
                        '<h1>Gadgets</h1>' +
                        '<a id="new_gadget" class="tracker_button" href="#">New</a>' +
                    '</div>' +
                    '<table>';
        data.forEach(function(gadget) {
            var time;
            var isActive;
            if(gadget.lastActivity) {
                time = timestampToDateFormat(gadget.lastActivity);
                isActive = currentDate.getTime() - gadget.lastActivity * 1000 > 5 * 60 * 1000;
            }
            var title = gadget.gadget.title;
            var imageActivity = isActive ? "lamp_inactive.png" : "lamp_active.png";
            console.log('title = ', title, ', imageActivity = ', imageActivity, ', time = ', time)
            html += '<tr>' +
                        '<td><input data_id="' + gadget.id + '" type="checkbox" ></td>' +
                        '<td><img src="resources/img/' + imageActivity + '" alt=""></td>' +
                        '<td>' + title + '</td>' +
                        '<td>' + time + '</td>' +
                    '</tr>'
        });
        html += '</table>' +
                        '</div>';
        return html;
    }
    //
    function addGadgetsOnMap(data) {
        data.forEach(function (gadget) {
            var marker = new google.maps.Marker({
                position:gadget,
                animation:google.maps.Animation.DROP,
                map: map
            });
            var info = new google.maps.InfoWindow({
                content: '<h3>' + gadget.gadget.title + '</h3>',
                maxWidth: 100
            });
            marker.addListener('click', function () {
                info.open(map, marker);
            });
            //
            markers.push(marker);
            localWindows.push(info);
            //
            map.setCenter(gadget);
        });
        if(data.length == 1) {
            map.setZoom(18);
        } else {
            calculateAndSetBoundsMap(data);
            console.log('More then 1 gadget');
        }
    }
    //
    function timestampToDateFormat(seconds) {
        var monthNames = [
            "January", "February", "March",
            "April", "May", "June", "July",
            "August", "September", "October",
            "November", "December"
        ];
        var date = new Date(seconds * 1000);
        var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
        var month = monthNames[date.getMonth()];
        return date.getFullYear() + ' ' + month + ' ' + date.getUTCDate() + ' ' +
                date.getHours() + ':' + minutes;
    }
    //
    $('#tracks').click(function () {
        console.log('gadgets');
        if(this != selectedPage) {
            $('.sidebar').empty().append('<h1>Tracks not implemented yet</h1>');
        }
    });
    //Init listener
    $('a.buttons_header').each(function () {
        $(this).click(function () {
            if(this != selectedPage) {
                $(selectedPage).removeClass('buttons_header_selected');
                selectedPage = this;
                $(this).addClass('buttons_header_selected');
            }
        })
    });

    $('#chart_overlay').click(function () {
        $(this).hide();
        $('#chart_wrapper_id').empty();
    });

    

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ZoCNEDPN29SW8f2D8jCmQBAx0nBgB-c&"></script>
<%--<script src="https://cdn.socket.io/socket.io-1.4.5.js"></script>--%>
</body>
</html>
