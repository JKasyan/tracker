/**
 * Created by kasyan on 10/2/16.
 */
var GadgetSubscribe = function (socket) {
    this.socket = socket;
}

GadgetSubscribe.prototype.subscribe = function (gadgetId) {
    if(this.gadgetId) throw new Error('You already subscribed on = ' + this.gadgetId);
    this.gadgetId = gadgetId;
    this.socket.emit('subscribeOnVehicle', gadgetId);
    return this;
}

GadgetSubscribe.prototype.unSubscribe = function () {
    console.log('un subscribe on vehicle = ', this.gadgetId);
    if(this.gadgetId) {
        this.socket.emit('unSubscribeOnVehicle', this.gadgetId);
    }
    this.gadgetId = null;
    return this;
}