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
    //
    //validatesSpeed(points);
    //
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
        content: infoWindow(points[0].timestamp, 'Start', points),
        maxWidth: 250
    });
    firstMarker.addListener('click', function () {
        infoWindowStart.open(map, firstMarker);
    });
    const infoWindowFinish = new google.maps.InfoWindow({
        content: infoWindow(points[points.length - 1].timestamp, 'Finish', points),
        maxWidth: 250
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
    //
    // google.maps.event.addListener(polyline, 'mouseover', function (event) {
    //     this.setOptions({
    //         strokeOpacity: 1
    //     });
    // });
    //
    // google.maps.event.addListener(polyline, 'mouseout', function (event) {
    //     this.setOptions({
    //         strokeOpacity: 0.5
    //     });
    // });

    // var chartData = points.map(function (point) {
    //     if(!point.speed) {
    //         console.log('speed null')
    //     }
    //     return {timestamp: point.timestamp * 1000, speed: point.speed};
    // });
    //
    // console.log('chart data = ', chartData);

    /*google.maps.event.addListener(polyline, 'click', function (event) {
        var chart = new Morris.Line({
            element:'chart',
            data: chartData,
            xkey: 'timestamp',
            ykeys:['speed'],
            labels: ['speed']
        });
        $('#chart_overlay').show();
        chart.redraw();
    });*/
}

/**
 * Set speed if it is null
 */
function validatesSpeed(points) {
    if(!points) return;
    for(var i = 0; i < points.length;i++) {
        if(!points[i].speed) {
            if(i == 0) {
                console.log('First point with invalid speed');
                points[i].speed = 0;
            } else if(!points[i].lat || !points[i].lng || !points[i].timestamp || !points[i-1].timestamp) {
                console.log('Invalid lat or lng = ', points[i].lng, points[i].lat);
                points[i].speed = 0;
            } else {
                var dist = distance2Points(points[i], points[i - 1]);
                var speed = dist / (points[i].timestamp - points[i-1].timestamp);
                console.log('Speed = ', speed);
                if(!speed || isNaN(speed)) {
                    points[i].speed = 0;
                }
                points[i].speed = Math.round(speed);
            }
        }
    }
}
/**
 *
 */


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
    var d = distance(points) | 0;
    var middleSpeed = Math.round(d / (points[points.length - 1].timestamp - points[0].timestamp));
    return '<div class="info_window">' +
                '<h1>' + title + '</h1>' +
                '<div class="info_window_row">' +
                    '<h3>Date:</h3>' +
                    '<h3>' + timestampToDateFormat(timestamp) + '</h3>' +
                '</div>' +
                '<div class="info_window_row">' +
                    '<h3>Distance:</h3>' +
                    '<h3>' + d + ' meters</h3>' +
                '</div>' +
                '<div class="info_window_row">' +
                    '<h3>Middle speed:</h3>' +
                    '<h3>' + middleSpeed + ' m/s</h3>' +
                '</div>' +
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

function distance2Points(p1, p2) {
    return distance([p1, p2]);
}