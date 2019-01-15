module.exports = async (ctx) => {
    const {status} = ctx.request.body;

    const employee = await ctx.req.user;
    await ctx.employeeModel.changeStatus(status, employee.id);

    const room = employee.room;
    const tablet = await ctx.tabletModel.getBy({room});
    if (!tablet) {
        console.log(`There is no active tablet at room ${room}`)
    }

    const messageToTablet = {
        data: {employeeId: employee.id.toString(), status},
        topic: "80b" // Later it will be real room
    };
    ctx.admin.messaging().send(messageToTablet)
        .then((response) => {
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });
    ctx.body = status;
};