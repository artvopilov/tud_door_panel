module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    const timeslot = ctx.request.body;

    console.log("add timeslot: "+timeslot)

    await ctx.workerModel.addTimeslot(timeslot, workerId);
    ctx.body = "ok";
};