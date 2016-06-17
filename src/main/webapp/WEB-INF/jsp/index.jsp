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
    var points;
    var poly;
    jQuery(document).ready(function($) {

    });
    //
    function initMap(){
        map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 50.5151189, lng: 30.6098523},
            zoom: 14
        });
        map.addListener('click', addLatLng);
    }
    //
    var getPoints = function () {
        var map;

        //
        $.ajax({
            type: "GET",
            //contentType : "application/json",
            url : "api/points",
            //dataType : 'json',
            timeout : 100000,
            success: function(data){
                console.log(data);
                points = data;
            }
        })
    }

    var lineSymbol = {
        path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW
    };

    //
    function createPath() {

        points.forEach(function(x){
            new google.maps.Marker({
                position: x,
                map: map
            });
        });
        poly = new google.maps.Polyline({
            path: points,
            strokeColor: '#000000',
            strokeOpacity: 1.0,
            strokeWeight: 3,
            icons: [{
                icon: lineSymbol,
                offset: '100%'
            }]
        });
        poly.setMap(map);
    }
    //
    function addLatLng(event) {
        if(poly) {
            points = [];
            points.push(event.latLng)
            poly = new google.maps.Polyline({
                path: points,
                strokeColor: '#000000',
                strokeOpacity: 1.0,
                strokeWeight: 3,
                icons: [{
                    icon: lineSymbol,
                    offset: '100%'
                }]
            });
        } else {
            var path = poly.getPath();
            path.push(event.latLng);
        }
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC0ZoCNEDPN29SW8f2D8jCmQBAx0nBgB-c&callback=initMap"
        async defer></script>
</body>
</html>
