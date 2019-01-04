const passport = require('koa-passport');

const EmployeeModel = require('./models/employees');

const LocalStrategy = require('passport-local').Strategy;
const JWTStrategy = require('passport-jwt').Strategy;
const ExtractJWT = require('passport-jwt').ExtractJwt;


const localStrategyOptions = {
    usernameField: 'email',
    passwordField: 'password'
};

passport.use('local', new LocalStrategy(localStrategyOptions, (email, password, done) => {
    const employeeModel = new EmployeeModel();
    employeeModel.getBy({email, password})
        .then(users => {
            if (users.length === 0) {
                return done(null, false, {message: 'bad username or password'})
            }
            const user = users[0];
            console.log('User found');
            return done(null, user);
        })
        .catch(err => {
            return done(err);
        })
}));

const jwtStrategyOptions = {
    jwtFromRequest: ExtractJWT.fromAuthHeaderAsBearerToken(),
    secretOrKey   : 'secret'
};

passport.use('jwt', new JWTStrategy(jwtStrategyOptions, (jwtPayload, done) => {
    const employeeModel = new EmployeeModel();
    employeeModel.getById(jwtPayload.id)
        .then(user => {
            console.log(user);
            return done(null, user);
        })
        .catch(err => {
            return done(err);
        })
}));
