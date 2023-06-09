const express = require('express');
const authRepository = require("../models/auth.repository");
const router = express.Router();
const jwt = require('jsonwebtoken');

router.post('/login', async(req, res) => {
    for (let attribut in req.body) {
        console.log(attribut + ': ' + req.body[attribut]);
    }

    if(req.body.pseudonyme == undefined || req.body.motDePasse == undefined) {
        return
    }

    if((await authRepository.authUser(req.body.pseudonyme, req.body.motDePasse))) {
        const token = jwt.sign({ payload: `${req.body.motDePasse}${req.body.id}` }, 'sopKEY',
        {
            expiresIn : "30m"
        });
        console.log("Token = " + token)
        res.status(200).json({ token: token })
    }
    else {
        res.sendStatus(401).end();
    }
})

router.get('/refreshToken', async (req, res) => {
})

exports.initializeRoutes = () => router;