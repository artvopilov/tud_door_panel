module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    ctx.body = await ctx.workerModel.getById(workerId);
};