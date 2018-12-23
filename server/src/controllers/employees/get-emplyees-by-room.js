module.exports = async (ctx) => {
    const room = ctx.params.room;
    ctx.body = await ctx.employeeModel.getBy({room});
};