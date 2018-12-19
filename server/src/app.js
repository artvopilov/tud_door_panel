const Koa = require('koa');
const router = require('koa-router')();
const bodyParser = require('koa-bodyparser');
const mongoose = require('mongoose');
const config =require('config');

const getEmployeesController = require('./controllers/employees/get-emplyees');
const getEmployeesByRoomController = require('./controllers/employees/get-emplyees-by-room');
const createEmployeesController = require('./controllers/employees/create');
const changeEmployeeStatusController = require('./controllers/employees/change-status');
const getEmployeeByIdController = require('./controllers/employees/get-by-id');

const EmployeeModel = require('./models/employees');

mongoose.connect(config.get('mongo.uri'), { useNewUrlParser: true })
    .then(() => console.log("Successfully connected to db"))
    .catch(err => console.log(`Error while connecting to db: ${err}`));
mongoose.Promise = global.Promise;

const app = new Koa();

router.param('id', (id, ctx, next) => next());
router.param('room', (room, ctx, next) => next());

router.get('/employees/', getEmployeesController);
router.get('/employees/room/:room', getEmployeesByRoomController);
router.get('/employees/:id', getEmployeeByIdController);
router.post('/employees/', createEmployeesController);
router.post('/employees/:id/status', changeEmployeeStatusController);

app.use(async (ctx, next) => {
    ctx.employeeModel = new EmployeeModel();
    await next();
});

app.use(bodyParser());
app.use(router.routes());

app.listen(5000, () => console.log('Server is starting...'));
