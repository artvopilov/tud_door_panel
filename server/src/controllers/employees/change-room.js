module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    const {room} = ctx.request.body;

    await ctx.employeeModel.changeRoom(room, employeeId);
    ctx.body = room;
};