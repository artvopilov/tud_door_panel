const mongoose = require('mongoose');


const Mobile = mongoose.model('Mobile', new mongoose.Schema({
    id: Number,
    employeeId: Number,
    token: {
        type: String,
        default: ''
    }
}));

module.exports = Mobile;
