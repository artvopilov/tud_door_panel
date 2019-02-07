const mongoose = require('mongoose');


const Message = mongoose.model('Message', new mongoose.Schema({
    id: Number,
    date: String,
    time: String,
    text: String,
    workerId: Number,
    email: String,
    name: String,
}));

module.exports = Message;
