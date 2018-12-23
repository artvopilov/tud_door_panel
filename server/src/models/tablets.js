const DbModel = require('./common/dbModel');


class Tablets extends DbModel {
    constructor() {
        super('tablet')
    }

    async create(tablet) {
        const isTablet = tablet
            && Object.prototype.hasOwnProperty.call(tablet, 'room');
        if (isTablet) {
            tablet.id = await this._generate_id();

            return await this._insert(tablet);
        }

        throw new Error('Tablet is invalid');
    }

    async registerToken(id, token) {
        await this._updateById(id, {token})
    }
}

module.exports = Tablets;
