/**
 * Created by kasyan on 10/2/16.
 */
var GadgetSubscribe = function (socket) {
    this.socket = socket;
}

GadgetSubscribe.prototype.subscribe = function (gadgetId) {
    if(this.gadgetId) throw new Error('You subscribed on = ' + his.gadgetId);
    this.gadgetId = gadgetId;
    this.socket.emit('subscribeOnVehicle', gadgetId);
}

GadgetSubscribe.prototype.unsubscribe = function (gadgetId) {
    this.gadgetId = null;
    this.socket.emit('unSubscribeOnVehicle', gadgetId);
}