/**
 * Created by 1 on 11/14/2016.
 */
socket.on('connect', function () {
    console.log('connect');
    //
    socket.on('gpsData', function (data) {
        if (currentPoint) currentPoint.setMap(null);
        console.log(data);
        var point = {lat: parseFloat(data.lat), lng: parseFloat(data.lng)};
        createMarker(point);
    });
});

function createMarker(point) {
    console.log('point = ', point);
    currentPoint = new google.maps.Marker({
        position: point,
        map: map,
        title: gadgetSubscribing.gadgetId
    });
    map.setCenter(point);
}