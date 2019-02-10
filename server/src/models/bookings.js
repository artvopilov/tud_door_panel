const DbModel = require('./common/dbModel');


class Bookings extends DbModel {
    constructor() {
        super('booking')
    }

    async create (booking) {
        const isBooking = booking
            && Object.prototype.hasOwnProperty.call(message, 'date')
            && Object.prototype.hasOwnProperty.call(message, 'time')
            && Object.prototype.hasOwnProperty.call(message, 'text')
            && Object.prototype.hasOwnProperty.call(message, 'email')
            && Object.prototype.hasOwnProperty.call(message, 'name')
            && Object.prototype.hasOwnProperty.call(message, 'eventId')
            && Object.prototype.hasOwnProperty.call(message, 'phone')
            && Object.prototype.hasOwnProperty.call(message, 'workerId');
        if (isBooking) {
            booking.id = await this._generate_id();

            return await this._insert(booking);
        }

        throw new Error('Booking is invalid');
    }
}

module.exports = Bookings;