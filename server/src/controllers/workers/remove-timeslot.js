module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    const {timeslot} = ctx.request.body;

    console.log("remove timeslot: "+timeslot)

    await ctx.workerModel.removeTimeslot(timeslot, workerId);

   const worker = await ctx.req.user;

    const room = worker.room;


    const messageToTablet = {
            data: {subject: 'removeTimeslot', workerId: workerId, timeslotId: timeslot},
            topic: room
        };

    ctx.admin.messaging().send(messageToTablet)
            .then(() => {
                console.log('Successfully sent message:', messageToTablet);
            })
            .catch((error) => {
                console.log('Error sending message:', messageToTablet, '\nError: ' + error);
            });

    ctx.body = "ok";
};