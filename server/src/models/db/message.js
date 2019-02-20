const mongoose = require('mongoose');


const Message = mongoose.model('Message', new mongoose.Schema({
    id: Number,
    date: String,
    time: String,
    message: String,
    workerId: Number,
    email: String,
    name: String,
    fromWorker: {
        type: Boolean,
        default: false
    }
}));

module.exports = Message;
