const Koa = require('koa');
const router = require('koa-router')();
const bodyParser = require('koa-bodyparser');
const mongoose = require('mongoose');
const config = require('config');
const passport = require('passport');
const admin = require('firebase-admin');
const serviceAccount = require('./../foxtrotdoorpanel-firebase-adminsdk.json');

const getWorkersController = require('./controllers/workers/get-workers');
const getWorkersByRoomController = require('./controllers/workers/get-workers-by-room');
const getWorkerTimeslots = require('./controllers/workers/get-timeslots');
const createWorkersController = require('./controllers/workers/create');
const changeWorkerStatusController = require('./controllers/workers/change-status');
const sendWorkerMessageController = require('./controllers/workers/send-message');
const getWorkerByIdController = require('./controllers/workers/get-by-id');
const changeWorkerRoomController = require('./controllers/workers/change-room');
const changeWorkerPhotoController = require('./controllers/workers/change-photo');
const addWorkerTimeslotController = require('./controllers/workers/add-timeslot');
const removeWorkerTimeslotController = require('./controllers/workers/remove-timeslot');
const bookWorkerTimeslotController = require('./controllers/workers/book-timeslot');
const authenticateWorkerController = require('./controllers/workers/auth');

const getTabletsController = require('./controllers/tablets/get-tablets');
const createTabletController = require('./controllers/tablets/create');

const getWorkerMessagesController = require('./controllers/messages/get-worker-messages');
const getRoomMessagesController = require('./controllers/messages/get-room-messages');
const sendMessageToVisitorController = require('./controllers/messages/send-to-visitor');
const getBookingsController = require('./controllers/bookings/get-worker-bookings');

const WorkerModel = require('./models/workers');
const TabletModel = require('./models/tablets');
const MessageModel = require('./models/messages');
const BookingModel = require('./models/bookings');

mongoose.connect(("mongodb://Foxtrot_Artem:12345a@ds163103.mlab.com:63103/foxtrot_db"), { useNewUrlParser: true })
    .then(() => console.log("Successfully connected to db"))
    .catch(err => console.log(`Error while connecting to db: ${err}`));
mongoose.Promise = global.Promise;

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://foxtrotdoorpanel.firebaseio.com'
});


const app = new Koa();
require('./passport');
app.use(passport.initialize());
console.log("console print working");
router.param('id', (id, ctx, next) => next());
router.param('room', (room, ctx, next) => next());

router.get('/workers/', getWorkersController);
router.get('/workers/room/:room', getWorkersByRoomController);
router.get('/workers/timeslots/', passport.authenticate('jwt', {session: false}),
    getWorkerTimeslots);
router.get('/workers/:id/', getWorkerByIdController);
router.post('/workers/', createWorkersController);
router.post('/workers/status', passport.authenticate('jwt', {session: false}),
    changeWorkerStatusController);
router.post('/workers/:id/message', sendWorkerMessageController);
router.post('/workers/room', passport.authenticate('jwt', {session: false}),
    changeWorkerRoomController);
router.post('/workers/:id/timeslot', passport.authenticate('jwt', {session: false}),
    addWorkerTimeslotController);
router.delete('/workers/:id/timeslot', removeWorkerTimeslotController);
router.post('/workers/photo', passport.authenticate('jwt', {session: false}),
    changeWorkerPhotoController);
router.post('/workers/:id/timeslot', addWorkerTimeslotController);
router.post('/workers/:id/book', bookWorkerTimeslotController);
router.post('/workers/login/', authenticateWorkerController);
router.get('/test-worker-token/', passport.authenticate('jwt', {session: false}),
    async ctx => {
        ctx.body = "Authenticated route reached";
});

router.get('/tablets/', getTabletsController);
router.post('/tablets/', createTabletController);

router.get('/messages', passport.authenticate('jwt', {session: false}),
    getWorkerMessagesController);
router.post('/messages/send-to-visitor', passport.authenticate('jwt', {session: false}),
    sendMessageToVisitorController);
router.get('/messages/:room/get', getRoomMessagesController);

router.get('/bookings', passport.authenticate('jwt', {session: false}),
    getBookingsController);

app.use(async (ctx, next) => {
    ctx.workerModel = new WorkerModel();
    ctx.tabletModel = new TabletModel();
    ctx.messageModel = new MessageModel();
    ctx.bookingModel = new BookingModel();
    ctx.admin = admin;
    await next();
});

app.use(bodyParser());
app.use(router.routes());

app.listen(5000, () => console.log('Server is starting...'));
