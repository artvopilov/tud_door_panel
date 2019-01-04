const passport = require('koa-passport');
const jwt = require('jsonwebtoken');


module.exports = async (ctx) => {
    return passport.authenticate('local', {session: false}, (err, user, info) => {
        if (user) {
            console.log(user);
            ctx.login(user, {session: false}, err => {
                if (err) {
                    console.log(err);
                    ctx.status = 400;
                    ctx.body = { status: 'error' };
                }
            });
            const token = jwt.sign(user, 'secret');
            console.log(token);
            ctx.status = 200;
            ctx.body = {user, token};
        } else {
            console.log(err);
            ctx.status = 400;
            ctx.body = { status: 'error' };
        }
    })(ctx);
};
