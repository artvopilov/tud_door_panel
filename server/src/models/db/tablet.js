const mongoose = require('mongoose');


const Tablet = mongoose.model('Tablet', new mongoose.Schema({
    id: Number,
    room: String,
    token: {
        type: String,
        default: ''
    }
}));

module.exports = Tablet;
