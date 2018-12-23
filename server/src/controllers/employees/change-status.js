module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    const {status} = ctx.request.body;

    const employee = await ctx.employeeModel.getById(employeeId);
    const room = employee.room;
    const tablet = await ctx.tabletModel.getBy({room});
    const token = tablet[0].token;

    await ctx.employeeModel.changeStatus(status, employeeId);

    const messageToTablet = {
        data: {employeeId, status},
        token: token
    };
    ctx.body = status;
    ctx.admin.messaging().send(messageToTablet)
        .then((response) => {
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });
};