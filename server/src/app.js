const Koa = require('koa');
const router = require('koa-router')();
const bodyParser = require('koa-bodyparser');
const mongoose = require('mongoose');
const config = require('config');
const admin = require('firebase-admin');
const serviceAccount = require('./../foxtrotdoorpanel-firebase-adminsdk.json');

const getEmployeesController = require('./controllers/employees/get-emplyees');
const getEmployeesByRoomController = require('./controllers/employees/get-emplyees-by-room');
const createEmployeesController = require('./controllers/employees/create');
const changeEmployeeStatusController = require('./controllers/employees/change-status');
const sendEmployeeMessageController = require('./controllers/employees/send-message');
const getEmployeeByIdController = require('./controllers/employees/get-by-id');
const changeEmployeeRoomController = require('./controllers/employees/change-room');

const getTabletsController = require('./controllers/tablets/get-tablets');
const registerTabletTokenController = require('./controllers/tablets/register-token');
const createTabletController = require('./controllers/tablets/create');

const getMobilesController = require('./controllers/mobiles/get-mobiles');
const registerMobileTokenController = require('./controllers/mobiles/register-token');
const createMobileController = require('./controllers/mobiles/create');

const EmployeeModel = require('./models/employees');
const TabletModel = require('./models/tablets');
const MobileModel = require('./models/mobiles');

mongoose.connect(config.get('mongo.uri'), { useNewUrlParser: true })
    .then(() => console.log("Successfully connected to db"))
    .catch(err => console.log(`Error while connecting to db: ${err}`));
mongoose.Promise = global.Promise;

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://foxtrotdoorpanel.firebaseio.com'
});


const app = new Koa();

router.param('id', (id, ctx, next) => next());
router.param('room', (room, ctx, next) => next());

router.get('/employees/', getEmployeesController);
router.get('/employees/room/:room', getEmployeesByRoomController);
router.get('/employees/:id', getEmployeeByIdController);
router.post('/employees/', createEmployeesController);
router.post('/employees/:id/status', changeEmployeeStatusController);
router.post('/employees/:id/message', sendEmployeeMessageController);
router.post('/employees/:id/room', changeEmployeeRoomController);

router.get('/tablets/', getTabletsController);
router.post('/tablets/', createTabletController);
router.post('/tablets/:id/token', registerTabletTokenController);

router.get('/mobiles/', getMobilesController);
router.post('/mobiles/', createMobileController);
router.post('/mobiles/:id/token', registerMobileTokenController);

app.use(async (ctx, next) => {
    ctx.employeeModel = new EmployeeModel();
    ctx.tabletModel = new TabletModel();
    ctx.mobileModel = new MobileModel();
    ctx.admin = admin;
    await next();
});

app.use(bodyParser());
app.use(router.routes());

app.listen(5000, () => console.log('Server is starting...'));
