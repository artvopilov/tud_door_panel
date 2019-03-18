module.exports = async (ctx) => {
    console.log('Change image request received');
    const worker = await ctx.req.user;
    const {image} = ctx.request.body;
    console.log(`New room: ${image}, workerID: ${worker.id}`);
    const messageToTabletIn = {
        data: {subject: 'workerPhoto', workerId: worker.id.toString()},
        topic: '80b'
    };
    
    await ctx.workerModel.changePhoto(image, worker.id);
    ctx.admin.messaging().send(messageToTabletIn)
        .then(() => {
            console.log('Successfully sent message:', messageToTabletIn);
        })
        .catch((error) => {
            console.log('Error sending message:', messageToTabletIn, '\nError: ' + error);
        });
   
    ctx.body = image;
};