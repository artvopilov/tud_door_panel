const mongoose = require('mongoose');

var EventsSchema = new mongoose.Schema({
                       id: Number,
                       name: String,
                       start: Date,
                       end: Date
                   });


const Worker = mongoose.model('Worker', new mongoose.Schema({
    id: Number,
    name: String,
    age: Number,
    email: String,
    timeslots: [EventsSchema],
    password: String,
    status: {
        type: String,
        default: 'Available'
    },
    room: {
        type: String,
        default: 0
    },
    phoneNumber: {
        type: String,
        default: ""
    }
}));

module.exports = Worker;
