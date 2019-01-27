module.exports = async ctx => {
    const worker = ctx.request.body;
    try {
        const newWorker = await ctx.workerModel.create(worker);
        ctx.status = 201;
        ctx.body = newWorker;
    } catch (e) {
        console.log(`Error caught in create controller: ${e.message}: `);
        ctx.status = 400;
        ctx.body = { status: 'error' };
    }
};