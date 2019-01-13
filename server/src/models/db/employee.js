const mongoose = require('mongoose');

var EventsSchema = new mongoose.Schema({
                       id: Number,
                       name: String,
                       start: Date,
                       end: Date
                   });


const Employee = mongoose.model('Employee', new mongoose.Schema({
    id: Number,
    name: String,
    age: Number,
    email: String,
    timeslots: [EventsSchema],
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

module.exports = Employee;
