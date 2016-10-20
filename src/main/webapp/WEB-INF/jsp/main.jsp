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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="resources/js/jquery.datetimepicker.full.js"></script>
    <script src="resources/js/maps.js"></script>
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
<div class="sidebar">
    <div id="gadgets_div">
        <h1>Gadgets</h1>
        <table>
            <tr>
                <td><img src="resources/img/lamp_active.png" style="color: yellow" alt=""></td>
                <td>My phone</td>
                <td>Onore de balzaka..</td>
            </tr>
            <tr>
                <td><img src="resources/img/lamp_inactive.png" alt=""></td>
                <td>My phone</td>
                <td>Onore de balzaka..</td>
            </tr>
        </table>
    </div>
</div>

<div class="overlay">
    <img id="loading_svg_img" src="resources/img/gears.svg" alt="loading32" />
</div>
<script>
    var map;
    var currentPoint;
    var poly;
    var p;
    var markers = [];
    var localWindows = [];
    var polylineHolder = [];
    jQuery(document).ready(function($) {
        //lastPoint(initMap);
        //
        //$('#find_by_date').trigger('click');
        //initDatePickers();
        //initGetPointsButton();
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
    function initMap(lastPoint){
        console.log(lastPoint.lat,', ', lastPoint.lng);
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: lastPoint.lat, lng: lastPoint.lng},
            zoom: 14
        });
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
    function buildPolyline(points) {
        var lineSymbol = {
            path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW
        };
        poly.setMap(null);
        poly = new google.maps.Polyline({
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 3,
            icons: [{
                icon: lineSymbol,
                offset: '100%'
            }]
        });
        poly.setMap(map);
        var path = poly.getPath();
        if(points){
            points.forEach(function (x) {
                path.push({
                    lat:function(){return x.lat},
                    lng:function(){return x.lng}
                });
            })
        }
        //
        var latMax = _highestPoint(points);
        var latMin = _lowestPoint(points);
        var lngMin  =_leftPoint(points);
        var lngMax  =_rightPoint(points);
        //
        var centerLat = (latMax + latMin)*0.5;
        var centerLng = (lngMin + lngMax)*0.5;
        var center = new google.maps.LatLng(centerLat, centerLng);
        map.setCenter(center);
        map.fitBounds(new google.maps.LatLngBounds(
                new google.maps.LatLng(latMin, lngMin),
                new google.maps.LatLng(latMax, lngMax)
        ));
        //animateCircle(poly);
    }
    //
    function erasePath(){
        poly.setMap(null);
    }

    function initGetPointsButton() {
        $('#submit_get_points').click(function () {
            var firstDate = $('#first_date').datetimepicker('getValue');
            var secondDate = $('#second_date').datetimepicker('getValue');
            //TODO: Add validation
            getAndBuildByDate(firstDate, secondDate);
        });
    }

    $(document).ajaxStart(function () {
        $('div.overlay').show();
    }).ajaxStop(function () {
        $('div.overlay').hide();
    });

    $('#logout_link').click(function () {
        $('#logout_form').submit();
    })

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
            initDatePickers();
            initGetPointsButton();
        }
    });
    //
    $('#gadgets').click(function () {
        console.log('gadgets');
        if(this != selectedPage) {
            $('.sidebar').empty().append('<h1>Not implemented yet</h1>');
        }
    });
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

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ZoCNEDPN29SW8f2D8jCmQBAx0nBgB-c&"></script>
<%--<script src="https://cdn.socket.io/socket.io-1.4.5.js"></script>--%>
<script src="https://cdn.socket.io/socket.io-1.0.0.js"></script>
<script>
    //https://obscure-thicket-55734.herokuapp.com
    /*var socket = io.connect('obscure-thicket-55734.herokuapp.com');
    //
    socket.on('connect', function() {
        console.log('connect');
        socket.emit('subscribeOnVehicle', 916584);
        //
        socket.on('gpsData', function(data) {
            if(currentPoint) currentPoint.setMap(null);
            console.log(data);
            var point = {lat:parseFloat(data.lat), lng: parseFloat(data.lng)};
            createMarker(point);
        });
    });

    function createMarker(point) {
        console.log('point = ', point);
        currentPoint = new google.maps.Marker({
            position: point,
            map: map,
            title: 'Hello World!'
        });
        map.setCenter(point);
    }

    function subscribeOnVehicle(idVehicle) {
        console.log('subscribeOnVehicle = ' + idVehicle);
        socket.emit('subscribeOnVehicle', idVehicle);
    }

    function unSubscribeOnVehicle(idVehicle) {
        console.log('unSubscribeOnVehicle = ' + idVehicle);
        socket.emit('unSubscribeOnVehicle', idVehicle);
    }*/
</script>
</body>
</html>
