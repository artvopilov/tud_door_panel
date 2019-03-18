const DbModel = require('./common/dbModel');


class Workers extends DbModel {
    constructor() {
        super('worker');
    }

    async create(worker) {
        const isWorkerValid = worker
            && Object.prototype.hasOwnProperty.call(worker, 'name')
            && Object.prototype.hasOwnProperty.call(worker, 'password')
            && Object.prototype.hasOwnProperty.call(worker, 'email');
        if (isWorkerValid) {
            worker.id = await this._generate_id();

            return await this._insert(worker);
        }

        throw new Error('Worker is invalid');
    }

    async changeStatus(status, id) {
        await this._updateById(id, {status})
    }

    async changeRoom(room, id) {
        await this._updateById(id, {room})
    }
    
	async changePhoto(image, id) {
        await this._updateById(id, {image})
    }

    async addTimeslot(event, id) {
    console.log(id)
        await this._MongooseModel.updateOne({id}, {$addToSet:{timeslots:event}});
    }

}

module.exports = Workers;
