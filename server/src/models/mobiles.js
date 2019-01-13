const DbModel = require('./common/dbModel');


class Mobiles extends DbModel {
    constructor() {
        super('mobile')
    }

    async create(mobile) {
        const isMobile = mobile
            && Object.prototype.hasOwnProperty.call(mobile, 'employeeId');
        if (isMobile) {
            mobile.id = await this._generate_id();

            return await this._insert(mobile);
        }

        throw new Error('Mobile is invalid');
    }

    async registerToken(id, token) {
        await this._updateById(id, {token})
    }
}

module.exports = Mobiles;
