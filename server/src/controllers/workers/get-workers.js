module.exports = async ctx => {
    ctx.body = await ctx.workerModel.getAll();
};
