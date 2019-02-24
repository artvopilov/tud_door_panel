module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    let message = ctx.request.body;
    message.workerId = workerId;
    message.roomNumber = '80b'; // Later we need to get rid of this hard-coded value

    try {
        message = await ctx.messageModel.create(message);
    } catch (e) {
        console.log(`Error caught in send-message controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
    }

    const messageToWorker = {
        data: {workerId, type: 'message', email: message.email, name: message.name, message: message.message,
            time: message.time, date: message.date, message_id: message.id.toString()},
        topic: workerId.toString()
    };
    ctx.admin.messaging().send(messageToWorker)
        .then(() => {
            console.log('Successfully sent message:', messageToWorker);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToWorker, '\nError: ' + error);
        });
    ctx.status = 200;
    ctx.body = { status: 'ok' };
};