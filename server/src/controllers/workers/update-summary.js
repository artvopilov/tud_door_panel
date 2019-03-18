module.exports = async (ctx) => {
    console.log('Personal summary update request received');
    const worker = ctx.req.user;
    const {summary} = ctx.request.body;

    await ctx.workerModel.changeSummary(summary, worker.id);
    const messageToTabletIn = {
        data: {subject: 'workerInRoom', workerId: worker.id.toString()},
        topic: worker.room
    };
    ctx.admin.messaging().send(messageToTabletIn)
        .then(() => {
            console.log('Successfully sent message (Summary):', messageToTabletIn);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToTabletIn, '\nError: ' + error);
        });

    ctx.body = "ok"
};
