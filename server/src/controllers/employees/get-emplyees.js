module.exports = async ctx => {
    ctx.body = await ctx.employeeModel.getAll();
};
