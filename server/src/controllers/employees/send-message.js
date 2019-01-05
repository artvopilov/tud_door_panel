module.exports = async (ctx) => {
    const employeeId = ctx.params.id;
    const {message} = ctx.request.body;

    const mobiles = await ctx.mobileModel.getBy({employeeId});
    const token = mobiles[0].token;


    const messageToTablet = {
        data: {employeeId, message},
        token: token
    };
    ctx.body = message;
    ctx.mobileApp.messaging().send(messageToTablet)
        .then((response) => {
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });
};