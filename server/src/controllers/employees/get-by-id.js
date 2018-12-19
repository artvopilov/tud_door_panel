module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    ctx.body = await ctx.employeeModel.getById(employeeId);
};