module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    const newStatus = ctx.request.body.status;
    
    await ctx.employeeModel.changeStatus(newStatus, employeeId);
    ctx.body = newStatus;
};