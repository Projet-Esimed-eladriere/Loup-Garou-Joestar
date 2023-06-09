const express = require('express');
const userRepository = require('../models/user.repository');
const {tableUtilisateur, tableListeJoueur} = require("../models/user.model");
const {body} = require("express-validator");
const router = express.Router();

router.get('/', async (req, res) => {
    res.send(await userRepository.getUsers());
});

router.get('/:pseudonyme', async (req, res) => {
    const foundUser = await userRepository.getUserByName(req.params.pseudonyme);
    res.send(foundUser);
});
router.get('/syncBDD', async (req ,res) => {
    await userRepository.syncBDD();
    res.status(200)
});

router.post('/', async (req, res) => {
    let foundUser =  await tableUtilisateur.findOne({where: {pseudonyme: req.body.pseudonyme }});
    if (foundUser) {
        res.status(400)
        res.send(foundUser.dataValues)
    } else {
        await userRepository.createUser(req.body);
        let newUser = await tableUtilisateur.findOne({where: {pseudonyme: req.body.pseudonyme}});
        if(newUser) {
            res.status(200)
            res.send(newUser.dataValues)
        } else {
            res.status(403)
        }

    }
});

router.put('/:id', async (req, res) => {
    await userRepository.updateUser(req.params.id, req.body)
        .catch((err) => res.status(500).send(err.message));
    res.status(204).end();
});

router.delete('/:id', async (req, res) => {
    await userRepository.deleteUser(req.params.id);
    res.status(204).end();
});

router.get('/sql/test-sqlite', async(req, res) => {
    console.log('route : /sql/test-sqlite')
    await userRepository.testSQLite();
    res.sendStatus(201).end();
})

exports.initializeRoutes = () => router;