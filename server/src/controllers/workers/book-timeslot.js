module.exports = async (ctx) => {
    console.log('Booking request got');
    const workerId = ctx.params.id;
    const booking = ctx.request.body;
    booking.workerId = workerId;

    try {
        await ctx.bookingModel.create(booking);
    } catch (e) {
        console.log(`Error caught in book-timeslot controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
        return;
    }

    const messageToTablet = {
        data: {eventId: booking.eventId, name: booking.name, email: booking.email, phone: booking.phone,
            message: booking.message, type: 'booking', time: booking.time, date: booking.date},
        topic: workerId.toString()
    };
    ctx.admin.messaging().send(messageToTablet)
        .then(() => {
            console.log('Successfully booked slot: ', booking);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });
    ctx.status = 200;
    ctx.body = { status: 'ok' };
};