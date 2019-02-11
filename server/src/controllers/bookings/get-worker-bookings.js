module.exports = async ctx => {
    // magic
    console.log('Got request for bookings');
    const worker = await ctx.req.user;
    ctx.body = await ctx.bookingModel.getBy({workerId: worker.id});
};