const express = require('express');
const cors = require('cors');
const { DateTime } = require('luxon');
const { expressjwt: jwt } = require('express-jwt');

function initJsonHandlerMiddlware(app) {
    console.log('initJsonHandlerMiddlware')
    app.use(express.json());
}

function initLoggerMiddlware(app) {
    app.use((req, res, next) => {
        const begin = new DateTime(new Date());

        res.on('finish', () => {
            const { DateTime } = require('luxon');
            const requestDate = begin.toFormat('dd/MM/yyyy HH:mm:ss.SSS');
            const remoteIP = `IP: ${req.socket.remoteAddress}`;
            const urlInfo = `${req.baseUrl}${req.path}`
            const method = `${req.method}` ;

            const end = new DateTime(new Date());
            const requestDurationMs = end.diff(begin).toMillis();
            const requestDuration = `Duration: ${requestDurationMs}ms`;
            process.stdout.write(`\x1b[36m[${requestDate}] - \x1b[0m`)
            process.stdout.write(`\x1b[35m[${remoteIP}] - \x1b[0m`)
            switch (method) {
                case "GET": process.stdout.write(`\x1b[92m[${method}] - \x1b[0m`);break
                case "POST": process.stdout.write(`\x1b[93m[${method}] - \x1b[0m`);break
                case "PUT": process.stdout.write(`\x1b[94m[${method}] - \x1b[0m`);break
                case "DELETE": process.stdout.write(`\x1b[91m[${method}] - \x1b[0m`);break
            }
            process.stdout.write(`\x1b[97m[${urlInfo}] - \x1b[0m`)
            console.log(`\x1b[36m[${requestDuration}]\x1b[0m`);
        })
        next();
    });
}

function initCors(app) {
    console.log('initCors()')
    app.use(cors())
}

function initJwtMiddleware(app) {
    app.use(
        jwt({
            secret: 'sopKEY',
            algorithms: ['HS256'],
        }).unless({
            path: [
                '/auth/login',
                '/users',
                '/users/syncBDD',
                //Test a supprimer
                /^\/composition\/pseudonyme\/\w+$/,
            ],
        }),
    );
}

exports.initializeConfigMiddlewares = function initializeConfigMiddlewares(app) {
    console.log('initializeConfigMiddlewares()')
    initJsonHandlerMiddlware(app);
    initLoggerMiddlware(app);
    initCors(app);
    initJwtMiddleware(app);
}

exports.initializeErrorMiddlwares = function initializeErrorMiddlwares(app) {
    console.log('initializeErrorMiddlwares()')
    app.use((err, req, res, next) => {
        res.status(500).send(err.message);
    })
}
