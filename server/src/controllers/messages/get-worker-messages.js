module.exports = async ctx => {
    // magic
    console.log('Got request for messages');
    const worker = await ctx.req.user;
    ctx.body = await ctx.messageModel.getBy({workerId: worker.id, fromWorker: false});
};