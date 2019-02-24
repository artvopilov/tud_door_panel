const mongoose = require('mongoose');


const Message = mongoose.model('Message', new mongoose.Schema({
    id: Number,
    date: String,
    time: String,
    message: String,
    workerId: Number,
    roomNumber: String,
    previousMessageId: {
        type: Number,
        default: -1
    },
    email: String,
    name: String,
    fromWorker: {
        type: Boolean,
        default: false
    },
}));

module.exports = Message;
