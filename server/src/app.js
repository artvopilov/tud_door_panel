const Koa = require('koa');
const router = require('koa-router')();
const bodyParser = require('koa-bodyparser');
const mongoose = require('mongoose');
const config = require('config');
const passport = require('passport');
const admin = require('firebase-admin');
const serviceAccount = require('./../foxtrottabletproject-firebase-adminsdk.json');

const getEmployeesController = require('./controllers/employees/get-emplyees');
const getEmployeesByRoomController = require('./controllers/employees/get-emplyees-by-room');
const createEmployeesController = require('./controllers/employees/create');
const changeEmployeeStatusController = require('./controllers/employees/change-status');
const getEmployeeByIdController = require('./controllers/employees/get-by-id');
const changeEmployeeRoomController = require('./controllers/employees/change-room');
const authenticateEmployeeController = require('./controllers/employees/auth');

const getTabletsController = require('./controllers/tablets/get-tablets');
const registerTabletTokenController = require('./controllers/tablets/register-token');
const createTabletController = require('./controllers/tablets/create');

const EmployeeModel = require('./models/employees');
const TabletModel = require('./models/tablets');

mongoose.connect(config.get('mongo.uri'), { useNewUrlParser: true })
    .then(() => console.log("Successfully connected to db"))
    .catch(err => console.log(`Error while connecting to db: ${err}`));
mongoose.Promise = global.Promise;

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://foxtrottabletproject.firebaseio.com'
});

require('./passport');
const app = new Koa();
app.use(passport.initialize());

router.param('id', (id, ctx, next) => next());
router.param('room', (room, ctx, next) => next());

router.get('/employees/', getEmployeesController);
router.get('/employees/room/:room', getEmployeesByRoomController);
router.get('/employees/:id', getEmployeeByIdController);
router.post('/employees/', createEmployeesController);
router.post('/employees/:id/status', changeEmployeeStatusController);
router.post('/employees/:id/room', changeEmployeeRoomController);
router.post('/employees/login/', authenticateEmployeeController);

router.get('/test-employee-token', passport.authenticate('jwt', {session: false}), async ctx => {
    ctx.body = "authenticated route successful";
});

router.get('/tablets/', getTabletsController);
router.post('/tablets/', createTabletController);
router.post('/tablets/:id/token', registerTabletTokenController);

app.use(async (ctx, next) => {
    ctx.employeeModel = new EmployeeModel();
    ctx.tabletModel = new TabletModel();
    ctx.admin = admin;
    await next();
});

app.use(bodyParser());
app.use(router.routes());

app.listen(5000, () => console.log('Server is starting...'));
