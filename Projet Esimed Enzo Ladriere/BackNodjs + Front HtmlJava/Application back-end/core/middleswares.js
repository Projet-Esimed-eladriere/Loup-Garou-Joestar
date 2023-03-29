const express = require('express')
const cors = require('cors')
const { DateTime } = require('luxon')
const { expressjwt: jwt } = require('express-jwt');

function initJsonHandlerMiddlware(app) {
    console.log('initJsonHandlerMiddlware')
    app.use(express.json());
}

function initLoggerMiddlware(app) {
    console.log('initLoggerMiddlware')
    app.use((req, res, next) => {
        const begin = new DateTime(new Date());
        res.on('finish', () => {
            const requestDate = begin.toString();
            const remoteIP = `IP: ${req.connection.remoteAddress}`;
            const httpInfo = `${req.method} ${req.baseUrl || req.path}`;

            const end = new DateTime(new Date());
            const requestDurationMs = end.diff(begin).toMillis();
            const requestDuration = `Duration: ${requestDurationMs}ms`;

            console.log(`[${requestDate}] - [${remoteIP}] - [${httpInfo}] - [${requestDuration}]`);
        })
        next()
    })
}

function initCors(app) {
    console.log('initCors()')
    app.use(cors())
}

exports.initializeConfigMiddlewares = function initializeConfigMiddlewares(app) {
    console.log('initializeConfigMiddlewares()')
    initJsonHandlerMiddlware(app);
    initLoggerMiddlware(app);
    initCors(app);
}

exports.initializeErrorMiddlwares = function initializeErrorMiddlwares(app) {
    console.log('initializeErrorMiddlwares()')
    app.use((err, req, res, next) => {
        res.status(500).send(err.message);
    })
}

