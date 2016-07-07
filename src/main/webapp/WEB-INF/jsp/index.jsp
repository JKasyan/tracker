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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="resources/js/maps.js"></script>
</head>
<body>
<div id="map"></div>
<script>
    var poly;
    var p;
    jQuery(document).ready(function($) {

    });
    //
    function initMap(){
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 50.5151189, lng: 30.6098523},
            zoom: 14
        });
        poly = new google.maps.Polyline({
            geodesic: true,
            strokeColor: '#00FF00',
            strokeOpacity: 1.0,
            strokeWeight: 3
        });
        poly.setMap(map);
        map.addListener('click', addLatLng);
    }
    //
    function addLatLng(event) {
        var path = poly.getPath();
        path.push(event.latLng);
        var marker = new google.maps.Marker({
            position: event.latLng,
            title: '#' + path.getLength(),
            map: map
        });
    }
    //
    var getPoints = function () {
        $.ajax({
            type: "GET",
            url : "api/points",
            timeout : 100000,
            success: function(data){
                console.log(data);
                buildPolyline(data);
            }
        });
    };
    //
    function getAndBuildByDate(from, to) {
        var unixTimeFrom = from.getTime() / 1000;
        var unixTImeTo = to.getTime() / 1000;
        var url = "api/points/from=" + unixTimeFrom + "/to=" + unixTImeTo;
        console.log("url: ", url);
        $.ajax({
            type: "GET",
            url : url,
            timeout : 100000,
            success: function(data){
                console.log(data);
                buildPolyline(data);
            }
        });
    };
    //
    function lastPoints(quantity) {
        var url = "api/points/quantity=" + quantity;
        $.ajax({
            type: "GET",
            url : url,
            timeout : 100000,
            success: function(data){
                console.log(data);
                buildPolyline(data);
            }
        });
    }
    //
    function buildPolyline(points) {
        var lineSymbol = {
            path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW
        };
        p = points;//Testing
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
    function sendPoints() {
        var points = [];
        poly.getPath().j.forEach(function(x){
            points.push({lat:x.lat(), lng:x.lng()});
        });
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType : "application/json",
            url : "api/points",
            data: JSON.stringify(points)
        })
    };
    //
    function erasePath(){
        poly.setMap(null);
    }
    //
    function _highestPoint(points) {
        var max = points[0].lat;
        points.forEach(function(x){
            if(x.lat > max) max = x.lat;
        });
        return max;
    }
    //
    function _lowestPoint(points) {
        var min = points[0].lat;
        points.forEach(function(x){
            if(x.lat < min) min = x.lat;
        });
        return min;
    }
    //
    function _leftPoint(points) {
        var l = points[0].lng;
        points.forEach(function(x){
            if(x.lng < l) l = x.lng;
        });
        return l;
    }
    //
    function _rightPoint(points) {
        var r = points[0].lng;
        points.forEach(function(x) {
            if(x.lng > r) r = x.lng;
        });
        return r;
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ZoCNEDPN29SW8f2D8jCmQBAx0nBgB-c&callback=initMap"
        async defer></script>
</body>
</html>
