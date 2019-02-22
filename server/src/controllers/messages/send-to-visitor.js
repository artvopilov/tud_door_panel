module.exports = async (ctx) => {
    console.log('Sending message to visitor request got');
    const worker = await ctx.req.user;
    const {message} = ctx.request.body;
    const previousMessage = ctx.messageModel.getById(parseInt(message.previousMessageId));
    const tablet = ctx.tabletModel.getById(parseInt(previousMessage.tabletId));
    message.workerId = worker.id;
    message.tabletId = tablet.id;
    message.fromWorker = true;

    try {
        await ctx.messageModel.create(message);
    } catch (e) {
        console.log(`Error caught in send-to-visitor controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
    }

    // TODO: edit messageToTablet
    const messageToTablet = {
        data: {subject: 'messageFromWorker', workerId: worker.id.toString(), status},
        topic: "80b" // Later it will be real room
    };

    // TODO: nothing is tested
    ctx.admin.messaging().send(messageToTablet)
        .then((response) => {
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });

    ctx.body = "Ok";
};