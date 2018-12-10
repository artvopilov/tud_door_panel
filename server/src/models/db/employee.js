const mongoose = require('mongoose');


const Employee = mongoose.model('Employee', new mongoose.Schema({
    id: Number,
    name: String,
    age: Number,
    email: String,
    room: {
        type: String,
        required: false
    },
    phoneNumber: {
        type: String,
        required: false
    }
}));

module.exports = Employee;
