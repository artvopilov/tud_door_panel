module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    const {timeslot, name, email, phone, message} = ctx.request.body;

    //const mobiles = await ctx.mobileModel.getBy({workerId});
    //const token = mobiles[0].token;


    const messageToTablet = {
        data: {workerId, timeslot, name, email, phone, message, type: 'booking'},
        topic: workerId.toString()
    };
    ctx.body = timeslot;
    ctx.admin.messaging().send(messageToTablet)
        .then((response) => {
            console.log('Successfully booked slot:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });
};