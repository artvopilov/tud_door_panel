const Koa = require('koa');
const router = require('koa-router')();
const bodyParser = require('koa-bodyparser');
const mongoose = require('mongoose');
//const config =require('config');

const getEmployeesController = require('./controllers/employees/get-emplyees');
const createEmployeesController = require('./controllers/employees/create');

const EmployeeModel = require('./models/employees');

mongoose.connect("mongodb://Foxtrot_Artem:12345a@ds163103.mlab.com:63103/foxtrot_db", { useNewUrlParser: true })
    .then(() => console.log("Successfully connected to db"))
    .catch(err => console.log(`Error while connecting to db: ${err}`));
mongoose.Promise = global.Promise;

const app = new Koa();

router.get('/employees/', getEmployeesController);
router.post('/employees/', createEmployeesController);

app.use(async (ctx, next) => {
    ctx.employeeModel = new EmployeeModel();
    await next();
});

app.use(bodyParser());
app.use(router.routes());

app.listen(5000, () => console.log('Server is starting...'));
