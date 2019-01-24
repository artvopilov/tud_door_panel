module.exports = async (ctx) => {
    const workerId = ctx.params.id;
    const {room} = ctx.request.body;

    await ctx.workerModel.changeRoom(room, workerId);
    ctx.body = room;
};