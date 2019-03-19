const DbModel = require('./common/dbModel');
const bcrypt  = require('bcryptjs');


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
            const email = worker.email;
            const users = await this.getBy({email});
            if (users.length !== 0) {
                throw new Error('Worker with this email already exists');
            }

            worker.password = bcrypt.hashSync(worker.password, bcrypt.genSaltSync(8));
            worker.id = await this._generate_id();

            return await this._insert(worker);
        }

        throw new Error('Worker is invalid');
    }

    async changeStatus(status, id) {
        await this._updateById(id, {status})
    }

    async changeEmail(email, id) {
        await this._updateById(id, {email})
    }

    async changeSummary(summary, id) {
        await this._updateById(id, {summary})
    }

    async changePhone(phoneNumber, id) {
        await this._updateById(id, {phoneNumber})
    }

    async changeRoom(room, id) {
        await this._updateById(id, {room})
    }
    
	async changePhoto(image, id) {
        await this._updateById(id, {image})
    }

    async addTimeslot(event, id) {
    console.log(id);
        await this._MongooseModel.updateOne({id}, {$addToSet:{timeslots:event}});
    }

    async removeTimeslot(eventId, id) {
        console.log(eventId);
        await this._MongooseModel.updateOne({id}, {$pull:{timeslots:{id:eventId}}});
    }

}

module.exports = Workers;
