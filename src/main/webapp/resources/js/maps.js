/**
 * Created by kasyan on 7/7/16.
 */

Array.prototype.max = function() {
    return Math.max.apply(null, this);
};

Array.prototype.min = function() {
    return Math.min.apply(null, this);
};

var rad = function(x) {
    return x * Math.PI / 180;
};

var getDistance = function(p1, p2) {
    var R = 6378137; // Earth’s mean radius in meter
    var dLat = rad(p2.lat - p1.lat);
    var dLong = rad(p2.lng - p1.lng);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(rad(p1.lat)) * Math.cos(rad(p2.lat)) *
        Math.sin(dLong / 2) * Math.sin(dLong / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // returns the distance in meter
};

function speedChecker(points) {
    var speed = [];
    for(var i = 0;i<points.length - 1;i++) {
        var p1 = points[i];
        var p2 = points[i + 1];
        var dist = getDistance(p1, p2);
        var time = p2.timestamp  - p1.timestamp;
        var speedMs = dist/time;
        speed.push(speedMs * 3.6);
    }
    return speed;
}

function animateCircle(line) {
    var count = 0;
    window.setInterval(function() {
        count = (count + 1) % 200;

        var icons = line.get('icons');
        icons[0].offset = (count / 2) + '%';
        line.set('icons', icons);
    }, 100);
}

function buildMultiplePolyline(points) {
    _clearMap();
    console.log('Initial size=', points.length);
    if(!points) {
        return;
    }
    var testCounter = 0;
    var polylineSize = 0;
    for(var i = 1; i < points.length;i++) {
        polylineSize++;
        //
        var timeDifference = points[i].timestamp - points[i-1].timestamp;
        if(timeDifference > 15 * 60) {
            console.log('Break! Length of polyline: ', polylineSize, ', time difference=', timeDifference);
            addPolylineWithMarkersAndInfoWindows(points.slice(i - polylineSize, i));
            testCounter += polylineSize;
            polylineSize = 0;
        }
    }
    /*if(!polylineSize || polylineHolder.length == 0) {
        console.log('Only one polyline');
        addPolylineWithMarkersAndInfoWindows(points.slice(i - polylineSize, i));
    }*/
    if(polylineSize == (points.length - 1)) {
        console.log('Only one polyline');
        addPolylineWithMarkersAndInfoWindows(points);
    } else {
        console.log('Tail');
        addPolylineWithMarkersAndInfoWindows(points.slice(points.length - polylineSize, points.length - 1));
    }
    var latMax = _highestPoint(points);
    var latMin = _lowestPoint(points);
    var lngMin  =_leftPoint(points);
    var lngMax  =_rightPoint(points);
    //
    var centerLat = (latMax + latMin) * 0.5;
    var centerLng = (lngMin + lngMax) * 0.5;
    var center = new google.maps.LatLng(centerLat, centerLng);
    map.setCenter(center);
    map.fitBounds(new google.maps.LatLngBounds(
        new google.maps.LatLng(latMin, lngMin),
        new google.maps.LatLng(latMax, lngMax)
    ));
}


function addPolylineWithMarkersAndInfoWindows(points) {
    var polyline = new google.maps.Polyline({
        path: points,
        geodesic: true,
        strokeColor: randomColor(),
        strokeOpacity: 1.0,
        strokeWeight: 3
    });
    polylineHolder.push(polyline);
    //
    const firstMarker = new google.maps.Marker({
        position:points[0],
        animation:google.maps.Animation.DROP,
        map: map
    });
    const lastMarker = new google.maps.Marker({
        position:points[points.length - 1],
        animation:google.maps.Animation.DROP,
        map: map
    });
    //
    const infoWindowStart = new google.maps.InfoWindow({
        content: infoWindow(points[0].timestamp, 'Start', points)
    });
    firstMarker.addListener('click', function () {
        infoWindowStart.open(map, firstMarker);
    });
    const infoWindowFinish = new google.maps.InfoWindow({
        content: infoWindow(points[points.length - 1].timestamp, 'Finish', points)
    });
    lastMarker.addListener('click', function () {
        infoWindowFinish.open(map, lastMarker);
    });
    //
    localWindows.push(infoWindowStart);
    localWindows.push(infoWindowFinish);
    //
    markers.push(firstMarker);
    markers.push(lastMarker);
    //
    polyline.setMap(map);
}


function _clearMap() {
    if(polylineHolder.length != 0) {
        polylineHolder.forEach(function (x) {
            x.setMap(null);
        });
        polylineHolder = [];
    }
    if(markers.length != 0) {
        markers.forEach(function (x) {
            x.setMap(null);
        });
        markers = [];
        localWindows = [];
    }
}


function _highestPoint(points) {
    var max = points[0].lat;
    points.forEach(function(x){
        if(x.lat > max) max = x.lat;
    });
    return max;
}


function _lowestPoint(points) {
    var min = points[0].lat;
    points.forEach(function(x){
        if(x.lat < min) min = x.lat;
    });
    return min;
}


function _leftPoint(points) {
    var l = points[0].lng;
    points.forEach(function(x){
        if(x.lng < l) l = x.lng;
    });
    return l;
}


function _rightPoint(points) {
    var r = points[0].lng;
    points.forEach(function(x) {
        if(x.lng > r) r = x.lng;
    });
    return r;
}

function infoWindow(timestamp, title, points) {
    var date = new Date(timestamp * 1000);
    var d = distance(points) | 0;
    return '<div>' +
            '<h3 style="text-align: center">' + title + '</h3>' +
            '<span>Date: ' + date + '</span><br/>' +
            '<span>Distance: ' + d + ' meters</span>' +
        '</div>'
}


function addLatLng(event) {
    var path = poly.getPath();
    path.push(event.latLng);
    var marker = new google.maps.Marker({
        position: event.latLng,
        title: '#' + path.getLength(),
        map: map
    });
}

function randomColor() {
    return '#' + ((1<<24) * Math.random() | 0).toString(16)
}

function distance(points) {
    var dist = 0;
    for(var i = 1;i<points.length;i++) {
        dist += getDistance(points[i-1], points[i]);
    }
    return dist;
}