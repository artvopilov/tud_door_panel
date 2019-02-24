const DbModel = require('./common/dbModel');


class Messages extends DbModel {
    constructor() {
        super('message')
    }

    async create (message) {
        const isMessage = message
            && Object.prototype.hasOwnProperty.call(message, 'date')
            && Object.prototype.hasOwnProperty.call(message, 'time')
            && Object.prototype.hasOwnProperty.call(message, 'message')
            && Object.prototype.hasOwnProperty.call(message, 'email')
            && Object.prototype.hasOwnProperty.call(message, 'name')
            && Object.prototype.hasOwnProperty.call(message, 'roomNumber')
            && Object.prototype.hasOwnProperty.call(message, 'workerId');
        if (isMessage) {
            message.id = await this._generate_id();

            return await this._insert(message);
        }

        throw new Error('Message is invalid');
    }
}

module.exports = Messages;
