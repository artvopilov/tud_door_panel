module.exports = async ctx => {
    var employees;
    if ('room' in ctx.request.body){
        employees = await ctx.employeeModel.getBy({room: ctx.request.body.room}, function (err, docs) {});
    }else{
        employees = await ctx.employeeModel.getAll();
    }
    ctx.status = 201;
    ctx.body = employees;
};
