module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    const message = ctx.request.body;
    message.workerId = workerId;

    try {
        await ctx.messageModel.create(message);
    } catch (e) {
        console.log(`Error caught in send-message controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
    }

    const messageToTablet = {
        data: {workerId, type: 'message', email: message.email, name: message.name, text: message.text,
            time: message.time, date: message.date},
        topic: workerId.toString()
    };
    ctx.admin.messaging().send(messageToTablet)
        .then(() => {
            console.log('Successfully sent message:', messageToTablet);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToTablet, '\nError: ' + error);
        });
    ctx.status = 200;
    ctx.body = { status: 'ok' };
};