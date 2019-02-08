module.exports = async (ctx) => {
    console.log('Change room request received');
    const worker = await ctx.req.user;
    const {room} = ctx.request.body;
    console.log(`New room: ${room}, workerID: ${worker.id}`);
    const messageToTabletIn = {
        data: {subject: 'workerInRoom', workerId: worker.id.toString()},
        topic: room
    };
    const formerRoom = worker.room;
    const messageToTabletOut = {
        data: {subject: 'workerOutRoom', workerId: worker.id.toString()},
        topic: formerRoom
    };
    await ctx.workerModel.changeRoom(room, worker.id);

    ctx.admin.messaging().send(messageToTabletIn)
        .then(() => {
            console.log('Successfully sent message:', messageToTabletIn);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToTabletIn, '\nError: ' + error);
        });
    ctx.admin.messaging().send(messageToTabletOut)
        .then(() => {
            console.log('Successfully sent message:', messageToTabletOut);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToTabletOut, '\nError: ' + error);
        });
    ctx.body = room;
};