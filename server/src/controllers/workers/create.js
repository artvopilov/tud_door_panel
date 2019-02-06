module.exports = async ctx => {
    const worker = ctx.request.body;
    try {
        const newWorker = await ctx.workerModel.create(worker);
        const messageToTablet = {
            data: {subject: 'newWorker', workerId: worker.id.toString()},
            topic: "80b" // Later it will be real room
        };
        ctx.admin.messaging().send(messageToTablet)
            .then((response) => {
                console.log('Successfully sent message:', messageToTablet, '\nResponse: ' + response);
            })
            .catch((error) => {
                console.log('Error sending message:', messageToTablet, '\nError: ' + error);
            });
        ctx.status = 201;
        ctx.body = newWorker;
    } catch (e) {
        console.log(`Error caught in create controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
    }
};