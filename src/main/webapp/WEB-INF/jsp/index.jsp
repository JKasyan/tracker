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
</head>
<body>
<div id="map"></div>
<script>
    var poly;
    jQuery(document).ready(function($) {

    });
    //
    function initMap(){
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 50.5151189, lng: 30.6098523},
            zoom: 14
        });
        poly = new google.maps.Polyline({
            strokeColor: '#000000',
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
    }
    //
    function buildPolyline(points) {
        var path = poly.getPath();
        if(points){
            points.forEach(function (x) {
                path.push({
                    lat:function(){return x.lat},
                    lng:function(){return x.lng},
                });
            })
        }
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
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ZoCNEDPN29SW8f2D8jCmQBAx0nBgB-c&callback=initMap"
        async defer></script>
</body>
</html>
