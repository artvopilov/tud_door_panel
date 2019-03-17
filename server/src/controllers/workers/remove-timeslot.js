module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    const {timeslot} = ctx.request.body;

    console.log("remove timeslot: "+timeslot)

    await ctx.workerModel.removeTimeslot(timeslot, workerId);
    ctx.body = "ok";
};