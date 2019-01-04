const mongoose = require('mongoose');


const Employee = mongoose.model('Employee', new mongoose.Schema({
    id: Number,
    name: String,
    age: Number,
    email: String,
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

module.exports = Employee;
