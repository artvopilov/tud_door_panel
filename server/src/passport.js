const passport = require('koa-passport');
const bcrypt  = require('bcryptjs');

const WorkerModel = require('./models/workers');

const LocalStrategy = require('passport-local').Strategy;
const JWTStrategy = require('passport-jwt').Strategy;
const ExtractJWT = require('passport-jwt').ExtractJwt;


const localStrategyOptions = {
    usernameField: 'email',
    passwordField: 'password'
};

passport.use('local', new LocalStrategy(localStrategyOptions, (email, password, done) => {
    const workerModel = new WorkerModel();

    workerModel.getBy({email})
        .then(users => {
            if (users.length === 0) {
                return done(null, false, {message: 'bad username or password'})
            }
            const user = users[0];
            console.log(user);
            if (bcrypt.compareSync(password, user.password)) {
                return done(null, user);
            } else {
                return done("Wrong password");
            }

        })
        .catch(err => {
            return done(err);
        })
}));

const jwtStrategyOptions = {
    jwtFromRequest: ExtractJWT.fromAuthHeaderAsBearerToken(),
    secretOrKey   : 'very_secret'
};

passport.use('jwt', new JWTStrategy(jwtStrategyOptions, (jwtPayload, done) => {
    const workerModel = new WorkerModel();
    workerModel.getById(jwtPayload.id)
        .then(user => {
            return done(null, user);
        })
        .catch(err => {
            return done(err);
        })
}));
