class DbModel {
    constructor(dbModelName) {
        const MongooseModel = require(`../db/${dbModelName}`);
        this._MongooseModel = MongooseModel;
    }

    async getAll() {
        const data = await this._MongooseModel
            .find({})
            .lean()
            .exec();
        return data;
    }

    async getById(id) {
        const data = await this._MongooseModel
            .findOne({id})
            .lean()
            .exec();
        return data;
    }

    async getBy(cond) {
        const data = await this._MongooseModel
            .find(cond)
            .lean()
            .exec();
        return data;
    }

    async _generate_id() {
        const data = await this._MongooseModel
            .find({})
            .sort({id: -1})
            .limit(1)
            .lean()
            .exec();
        if (data.length === 0) {
            return 1;
        }
        return data[0].id + 1;
    }

    async _insert(item) {
        return await this._MongooseModel
            .create(item)
            .then(item => { return item; });
    }

    async _remove(id) {
        await this._MongooseModel
            .remove({id});
    }

    async _updateById(id, set) {
        await this._MongooseModel
            .updateOne({id}, {$set: set});
    }

    async _updateBy(cond, set) {
        await this._MongooseModel
            .updateMany(cond, {$set: set});
    }
}

module.exports = DbModel;
