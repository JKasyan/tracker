<%--
  Created by IntelliJ IDEA.
  User: Kasyan Evgen
  Date: 17.06.2016
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index</title>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        #map {
            height: 100%;
        }
    </style>
    <link rel="stylesheet" href="resources/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="resources/css/sidebar-wrapper.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="resources/js/bootstrap/bootstrap.js" type="text/javascript"></script>
    <script src="resources/js/maps.js"></script>
    <script href="resources/js/socket.js"></script>
</head>
<body>
<div id="map"></div>
<script>
    var map;
    var currentPoint;
    var poly;
    var p;
    var markers = [];
    var localWindows = [];
    var polylineHolder = [];
    jQuery(document).ready(function($) {
        lastPoint(initMap);
    });
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
        var unixTimeFrom = from.getTime() / 1000;
        var unixTimeTo = to.getTime() / 1000;
        var url = "api/points/from=" + unixTimeFrom + "/to=" + unixTimeTo;
        console.log("url: ", url);
        $.ajax({
            type: "GET",
            url : url,
            timeout : 100000,
            success: function(data){
                console.log(data);
                buildMultiplePolyline(data);
            }
        });
    };
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
                    lng:function(){return x.lng},
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
        animateCircle(poly);
    }
    //
    function erasePath(){
        poly.setMap(null);
    }

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ZoCNEDPN29SW8f2D8jCmQBAx0nBgB-c&"></script>
<%--<script src="https://cdn.socket.io/socket.io-1.4.5.js"></script>--%>
<script src="https://cdn.socket.io/socket.io-1.0.0.js"></script>
<script>
    //https://obscure-thicket-55734.herokuapp.com
    var socket = io.connect('obscure-thicket-55734.herokuapp.com');
    //
    socket.on('connect', function() {
        console.log('connect');
        socket.emit('subscribeOnVehicle', 100);
        //
        socket.on('gpsData', function(data) {
            if(currentPoint) currentPoint.setMap(null);
            console.log(data);
            var point = {lat:parseFloat(data.lat), lng: parseFloat(data.lng)};
            currentPoint = new google.maps.Marker({
                position: point,
                map: map,
                title: 'Hello World!'
            });
            map.setCenter(point);
        });
    });

    function subscribeOnVehicle(idVehicle) {
        console.log('subscribeOnVehicle = ' + idVehicle);
        socket.emit('subscribeOnVehicle', idVehicle);
    }

    function unSubscribeOnVehicle(idVehicle) {
        console.log('unSubscribeOnVehicle = ' + idVehicle);
        socket.emit('unSubscribeOnVehicle', idVehicle);
    }
</script>
</body>
</html>
