/**
 * Created by kasyan on 10/29/16.
 */

function subscriveOnGadget(idGadget) {
    if(!socket) {
        socket = io.connect('https://obscure-thicket-55734.herokuapp.com');
    }
    socket.on('connect', function() {
        console.log('Socket connected!');
    })
}