const express = require('express');
const middleswares = require('./middleswares')
const userRoutes = require('../controller/user.routes');
const authRoutes = require('../controller/auth.route');
const joueurRoutes = require('../controller/joueur.route');
const compositionRoutes = require('../controller/composition.route')
class WebServer
{
    app = undefined;
    port = 3000;
    server = undefined;
    constructor() {
        console.log('WebServer()')
        this.app = express();
        middleswares.initializeConfigMiddlewares(this.app);
        this._initializeRoutes();
        middleswares.initializeErrorMiddlwares(this.app);
    }

    start() {
        this.server = this.app.listen(this.port, () => {
            console.log(`Example app listening on port ${this.port}`);
        });
    }
    _initializeRoutes() {
        this.app.use('/users', userRoutes.initializeRoutes());
        this.app.use('/auth', authRoutes.initializeRoutes());
        this.app.use('/joueur', joueurRoutes.initializeRoutes());
        this.app.use('/composition', compositionRoutes.initializeRoutes())
    }
    stop() {
        this.server.close();
    }
}

module.exports = WebServer;