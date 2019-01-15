module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    const {timeslot, name, email, pone, message} = ctx.request.body;

    //const mobiles = await ctx.mobileModel.getBy({employeeId});
    //const token = mobiles[0].token;


    const messageToTablet = {
        data: {employeeId, timeslot},
        topic: employeeId.toString()
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