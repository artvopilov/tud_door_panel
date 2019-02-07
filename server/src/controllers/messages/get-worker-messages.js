module.exports = async ctx => {
    console.log('Got request for messages');
    const worker = await ctx.req.user;
    ctx.body = await ctx.messageModel.getBy({workerId: worker.id});
};