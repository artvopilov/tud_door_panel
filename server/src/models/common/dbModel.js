class DbModel {
    constructor(dbModelName) {
        const MongooseModel = require(`../db/${dbModelName}`);
        this.MongooseModel = MongooseModel;
    }

    async getAll() {
        const data = await this.MongooseModel
            .find({})
            .lean()
            .exec();
        return data;
    }

    async getById(id) {
        const data = await this.MongooseModel
            .findOne({id})
            .lean()
            .exec();
        return data;
    }

    async getBy(cond) {
        const data = await this.MongooseModel
            .find(cond)
            .lean()
            .exec();
        return data;
    }

    async _generate_id() {
        const data = await this.MongooseModel
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
}

module.exports = DbModel;
