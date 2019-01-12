module.exports = async ctx => {
    const employee = ctx.request.body;
    try {
        const newEmployee = await ctx.employeeModel.create(employee);
        ctx.status = 201;
        ctx.body = newEmployee;
    } catch (e) {
        console.log(`Error caught in create controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
    }
};