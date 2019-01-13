module.exports = async (ctx) => {
    const mobile = ctx.request.body;

    try {
        const newMobile = await ctx.mobileModel.create(mobile);
        ctx.status = 201;
        ctx.body = newMobile;
    } catch (e) {
        console.log(`Error caught in create controller: ${e.message}: `);
        ctx.status = 400;
    }
};