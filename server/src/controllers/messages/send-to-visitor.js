module.exports = async (ctx) => {
    console.log('Sending message to visitor request got');
    const worker = await ctx.req.user;
    const message = ctx.request.body;
    console.log(message);
    const previousMessage = await ctx.messageModel.getById(parseInt(message.previousMessageId));
    console.log(previousMessage);
    message.workerId = worker.id;
    message.workerName = worker.name;
    message.roomNumber = previousMessage.roomNumber;
    message.fromWorker = true;

    try {
        await ctx.messageModel.create(message);
    } catch (e) {
        console.log(`Error caught in send-to-visitor controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
        return
    }

    const messageToTablet = {
        data: {subject: 'messageFromWorker', from_worker: worker.name, to_visitor: message.name, date: message.date,
            time: message.time, text: message.message},
        topic: message.roomNumber.toString()
    };

    ctx.admin.messaging().send(messageToTablet)
        .then((response) => {
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });

    ctx.body = "Ok";
};