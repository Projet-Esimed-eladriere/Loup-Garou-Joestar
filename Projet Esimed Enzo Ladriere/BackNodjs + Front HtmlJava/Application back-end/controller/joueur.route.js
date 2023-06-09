const express = require('express');
const joueurRepository = require("../models/joueur.repository");
const {tableUtilisateur, tableListeJoueur} = require("../models/user.model");
const {body} = require("express-validator");
const {getJoueurs} = require("../models/joueur.repository");
const router = express.Router();

router.get('/', async(req, res) => {
    res.send(await joueurRepository.getJoueurs());
})

router.get('/:idUtilisateur', async(req, res) => {
    const joueur = await joueurRepository.getJoueursParID(req.params.idUtilisateur)
    console.log(joueur)
    res.send(joueur);
})

router.get('/:idUtilisateur/:nom', async(req, res) => {
    return await joueurRepository.getJoueursParIDEtNom(req.params.idUtilisateur, req.params.nom)
})

router.post('/', async(req, res) => {
    if(req.body.idUtilisateur != undefined && req.body.nom != undefined) {
        let foundJoueur =  await joueurRepository.getJoueursParIDEtNom(req.body.idUtilisateur, req.body.nom)
        if (foundJoueur) {
            res.sendStatus(401)
        } else {
            await joueurRepository.createJoueur(req.body)
            res.send(await joueurRepository.getJoueursParID(req.body.idUtilisateur)).status(200)
        }
    }
})


router.delete('/:idUtilisateur/:nom', async(req, res) => {
    await joueurRepository.deleteJoueur(req.params.idUtilisateur, req.params.nom)
    res.send({}).status(200)
})


exports.initializeRoutes = () => router;