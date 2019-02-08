const passport = require('koa-passport');
const jwt = require('jsonwebtoken');


module.exports = async (ctx) => {
    return passport.authenticate('local', {session: false}, (err, user, info) => {
        console.log("Login request got");
        if (user) {
            ctx.login(user, {session: false}, err => {
                if (err) {
                    console.log(err);
                    ctx.status = 200;
                    ctx.body = { status: 'error' };
                }
            });
            const token = jwt.sign(user, 'very_secret');
            ctx.status = 200;
            ctx.body = {worker: user, token, status: 'ok'};
        } else {
            console.log(err);
            ctx.status = 200;
            ctx.body = { status: 'error' };
        }
    })(ctx);
};
