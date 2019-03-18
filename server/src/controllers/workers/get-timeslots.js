module.exports = async (ctx) => {
    const worker = await ctx.req.user;
    ctx.body = worker.timeslots;
};