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
    },
    position: {
        type: String,
        default: "Research Assistant"
    },
    summary: {
        type: String,
        default: "Director of brand marketing, with experience managing global teams and multi-million-dollar " +
            "campaigns. Her background in brand strategy, visual design, and account management inform her " +
            "mindful but competitive approach."
    }

}));

module.exports = Worker;
