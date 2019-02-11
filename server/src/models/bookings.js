const DbModel = require('./common/dbModel');


class Bookings extends DbModel {
    constructor() {
        super('booking')
    }

    async create (booking) {
        const isBooking = booking
            && Object.prototype.hasOwnProperty.call(booking, 'date')
            && Object.prototype.hasOwnProperty.call(booking, 'time')
            && Object.prototype.hasOwnProperty.call(booking, 'message')
            && Object.prototype.hasOwnProperty.call(booking, 'email')
            && Object.prototype.hasOwnProperty.call(booking, 'name')
            && Object.prototype.hasOwnProperty.call(booking, 'eventId')
            && Object.prototype.hasOwnProperty.call(booking, 'phone')
            && Object.prototype.hasOwnProperty.call(booking, 'workerId');
        if (isBooking) {
            booking.id = await this._generate_id();

            return await this._insert(booking);
        }

        throw new Error('Booking is invalid');
    }
}

module.exports = Bookings;