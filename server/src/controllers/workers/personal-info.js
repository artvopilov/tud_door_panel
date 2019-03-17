module.exports = async (ctx) => {
    console.log('Personal info request received');
    const worker = await ctx.req.user;
    const formerRoom = worker.room;
    const {phone, email, room} = ctx.request.body;
    console.log(ctx.request.body);

    if (phone !== "") {
        await ctx.workerModel.changePhone(phone, worker.id)
    }
    if (email !== "") {
        await ctx.workerModel.changeEmail(email, worker.id)
    }
    if (room !== "") {
        await ctx.workerModel.changeRoom(room, worker.id)
    }

    let messageToTabletIn = {}
    if (formerRoom !== room && room  !== "") {
        const messageToTabletOut = {
            data: {subject: 'workerOutRoom', workerId: worker.id.toString()},
            topic: formerRoom
        };
        ctx.admin.messaging().send(messageToTabletOut)
            .then(() => {
                console.log('Successfully sent message (TabletOutWorker):', messageToTabletOut);
            })
            .catch((error) => {
                console.log('Error sending message:', messageToTabletOut, '\nError: ' + error);
            });
        messageToTabletIn = {
            data: {subject: 'workerInRoom', workerId: worker.id.toString()},
            topic: room
        };
    } else {
        messageToTabletIn = {
            data: {subject: 'workerInRoom', workerId: worker.id.toString()},
            topic: formerRoom
        };
    }

    ctx.admin.messaging().send(messageToTabletIn)
        .then(() => {
            console.log('Successfully sent message (TabletWorkerIn):', messageToTabletIn);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToTabletIn, '\nError: ' + error);
        });

    ctx.body = "ok"
};
