/**
 * Created by kasyan on 10/2/16.
 */
var Chat = function (socket, vehicleId) {
    console.log('vehicleId = ' + vehicleId);
    this.socket = socket;
    this.vehicleId = vehicleId;
    socket.emit('subscribe', {vehicleId: vehicleId});
    console.log(socket);
}

var socket = io.connect('http://127.0.0.1:3003');

var chat;

socket.on('connect', function() {
    console.log('Connected...');
    chat = new Chat(socket, 1001);
});

socket.on('message', function(msg) {
    console.log(msg);
});
