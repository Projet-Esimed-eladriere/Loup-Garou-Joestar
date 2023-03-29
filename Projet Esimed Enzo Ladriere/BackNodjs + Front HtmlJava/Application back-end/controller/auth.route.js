/*const express = require('express');
const authRepository = require("../models/auth.repository");
const router = express.Router();
const jwt = require('jsonwebtoken');

router.post('/auth/login', async(req, res) => {
    if((await authRepository.authUser(req.body.firstName, req.body.password)))
    {
        console.log('même mot de passe')
        const token = jwt.sign({ payload: `${req.body.firstName}${req.body.id}` }, 'sopKEY');
        res.send(token)
    }
    else
    {
        console.log('erreur de  mot de passe')
        //res.sendStatus(201).end();
    }
})

exports.initializeRoutes = () => router;*/