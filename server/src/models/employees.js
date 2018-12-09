const DbModel = require('./common/dbModel');


class Employee extends DbModel {
    constructor() {
        super('employee');
    }

    async create(employee) {
        const isEmployeeValid = employee
            && Object.prototype.hasOwnProperty.call(employee, 'name')
            && Object.prototype.hasOwnProperty.call(employee, 'age')
            && Object.prototype.hasOwnProperty.call(employee, 'email');
        if (isEmployeeValid) {
            employee.id = await this._generate_id();

            await this.MongooseModel.create(employee);
            return employee
        }

        throw new Error('Employee is invalid');
    }

    async remode(id) {
        const employee = this.getById(id);
        if (!employee) {
            throw Error(`Employee with id ${id} not found`);
        }
        await this.MongooseModel.remove({id});
    }
}

module.exports = Employee;
