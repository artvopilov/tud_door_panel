module.exports = async (ctx) => {
    const mobileId = ctx.params.id;
    const {token} = ctx.request.body;

    await ctx.mobileModel.registerToken(mobileId, token);
    ctx.body = 'Ok'
};