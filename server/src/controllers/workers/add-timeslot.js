module.exports = async (ctx) => {
    const timeslot = ctx.request.body;

    console.log("add timeslot: "+timeslot);

    await ctx.workerModel.addTimeslot(timeslot, worker.id);

    const worker = await ctx.req.user;

    const room = worker.room;


    const messageToTablet = {
            data: {subject: 'addTimeslot', workerId: worker.id},
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