const express = require('express');
const userRepository = require('../models/user.repository');
const router = express.Router();

router.get('/', async (req, res) => {
    res.send(await userRepository.getUsers());
});

router.get('/:pseudonyme', async (req, res) => {
    const foundUser = userRepository.getUserByName(req.params.pseudonyme);
    res.send(await foundUser);
});

router.post('/', async (req, res) => {
    await userRepository.createUser(req.body);
    res.status(201).end();
});

router.post('/authenticate', async (req, res) => {
    await userRepository.authenticateUser(req.body);
    res.status(201).end();
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