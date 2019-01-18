module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    const timeslot = ctx.request.body;

    console.log("add timeslot: "+timeslot)

    await ctx.employeeModel.addTimeslot(timeslot, employeeId);
    ctx.body = "ok";
};