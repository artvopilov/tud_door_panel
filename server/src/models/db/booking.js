const mongoose = require('mongoose');


const Booking = mongoose.model('Booking', new mongoose.Schema({
    id: Number,
    eventId: Number,
    workerId: Number,
    time: String,
    date: String,
    text: String,
    email: String,
    name: String,
    phone: String,
    confirmed: {
        type: Boolean,
        default: false
    }
}));

module.exports = Booking;
