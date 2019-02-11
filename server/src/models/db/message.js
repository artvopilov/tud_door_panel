const mongoose = require('mongoose');


const Message = mongoose.model('Message', new mongoose.Schema({
    id: Number,
    date: String,
    time: String,
    message: String,
    workerId: Number,
    email: String,
    name: String,
}));

module.exports = Message;
