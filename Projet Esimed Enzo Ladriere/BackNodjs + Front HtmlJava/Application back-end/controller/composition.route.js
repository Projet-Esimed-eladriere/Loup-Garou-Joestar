const express = require('express');
const compositionRepository = require("../models/composition.repository");
const userRepository = require("../models/user.repository")
const router = express.Router();
router.get('/', async (req, res) => {
    res.send(await compositionRepository.getCompositions());
});

router.get('/:idUtilisateur', async (req, res) => {
    res.send(await compositionRepository.getCompositionsOfOneUser(req.params.idUtilisateur));
});

router.get('/pseudonyme/:pseudonyme', async (req, res) => {
    let user = await userRepository.getUserByName(req.params.pseudonyme)
    let compositions = await compositionRepository.getCompositionsOfOneUser(user.id)
    res.send(compositions);
});

router.get('/:idUtilisateur/:nom', async (req, res) => {
    res.send(await compositionRepository.getOneCompositionOfOneUser(req.params.idUtilisateur, req.params.nom));
});

router.post('/', async (req, res) => {
    let compositionTrouve = await compositionRepository.
    getOneCompositionOfOneUser(req.body.idUtilisateur, req.body.nom);
    console.log(compositionTrouve)
    if(compositionTrouve != "") {
        console.log(compositionTrouve)
        res.sendStatus(401)
    } else {
        await compositionRepository.creerComposition(req.body)
        compositionTrouve = await compositionRepository.
        getOneCompositionOfOneUser(req.body.idUtilisateur, req.body.nom);
            res.status(201)
        res.send(compositionTrouve)
    }
});

router.put('/', async (req, res) => {
    let compositionTrouve = await compositionRepository.
    getOneCompositionOfOneUser(req.body.idUtilisateur, req.body.nom);
    console.log(compositionTrouve)
    if(compositionTrouve == null) {
        res.sendStatus(401)
    } else {
        await compositionRepository.modifierComposition(req.body)
        res.send([]).status(200)
    }
});

router.delete('/:idUtilisateur/:nom', async(req, res) => {
    await compositionRepository.deleteJoueur(req.params.idUtilisateur, req.params.nom)
    res.send({}).status(200)
})

exports.initializeRoutes = () => router;