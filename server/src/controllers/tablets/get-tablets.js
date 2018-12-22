module.exports = async ctx => {
    ctx.body = await ctx.tabletModel.getAll();
};