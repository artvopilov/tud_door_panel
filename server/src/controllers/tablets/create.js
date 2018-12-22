module.exports = async (ctx) => {
    const tablet = ctx.request.body;

    try {
        const newTablet = await ctx.tabletModel.create(tablet);
        ctx.status = 201;
        ctx.body = newTablet;
    } catch (e) {
        console.log(`Error caught in create controller: ${e.message}: `);
        ctx.status = 400;
    }
};