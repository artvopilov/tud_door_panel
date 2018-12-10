module.exports = async ctx => {
    const employees = await ctx.employeeModel.getAll();
    ctx.status = 201;
    ctx.body = employees;
};