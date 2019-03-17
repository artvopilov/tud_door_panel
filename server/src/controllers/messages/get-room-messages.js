module.exports = async (ctx) => {
    console.log('Got request for room messages');
    const roomNumber = ctx.params.room;
    const curDate = getCurrentDate();
    console.log(`Today: ${curDate}`);
    let messages = await ctx.messageModel.getBy({roomNumber: roomNumber, fromWorker: true, date: curDate});
    messages = messages.slice(0, 30);

    ctx.body = messages.map(item => {
        return {from: item.workerName, to: item.name, date: item.date, time: item.time, text: item.message};
    });
    ctx.status = 200;
};


function getCurrentDate() {
    const curDate = new Date();
    const year = curDate.getFullYear();
    let month = curDate.getMonth() + 1;
    let day = curDate.getDate();
    month = month < 10 ? '0' + month : month;
    day = day < 10 ? '0' + day : day;
    return `${year}-${month}-${day}`;
}
