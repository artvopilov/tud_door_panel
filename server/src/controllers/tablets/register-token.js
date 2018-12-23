module.exports = async (ctx) => {
    const tabletId = ctx.params.id;
    const {token} = ctx.request.body;

    await ctx.tabletModel.registerToken(tabletId, token);
    ctx.body = 'Ok'
};