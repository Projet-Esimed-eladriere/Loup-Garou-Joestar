const bcrypt = require('bcryptjs');
const {tableListeJoueur} = require("./user.model");
const {where} = require("sequelize");


exports.getJoueurs = async function getJoueurs() {
    return await tableListeJoueur.findAll()
}

exports.createJoueur = async function createJoueur(body) {
    await tableListeJoueur.create({
        idUtilisateur: body.idUtilisateur,
        nom: body.nom
    })
}

exports.getJoueursParIDEtNom = async function getJoueursParIDEtNom(idUtilisateur, nom) {
    if(idUtilisateur != undefined && nom != undefined) {
        return await tableListeJoueur.findOne({where: {idUtilisateur: idUtilisateur, nom: nom}});
    }
}

exports.getJoueursParID = async function getJoueursParID(idUtilisateur) {
    if (idUtilisateur != undefined) {
        return await tableListeJoueur.findAll({
            attributes: ['idUtilisateur', 'nom'],
            where: {idUtilisateur: `${idUtilisateur}`}
        })
    }
}

exports.getJoueur = async function getJoueur(idUtilisateur, nom) {
    if (idUtilisateur != undefined && nom != undefined) {
        return await tableListeJoueur.findOne({
            where: {
                idUtilisateur: idUtilisateur,
                nom: nom
            }
        })
    }
}

exports.deleteJoueur = async function deleteJoueur(idUtilisateur, nom) {
    if (idUtilisateur != undefined && nom != undefined) {
        const user = await tableListeJoueur.findOne({where: {idUtilisateur: idUtilisateur, nom: nom}});
        if (user) {
            await tableListeJoueur.destroy({
                where: {
                    idUtilisateur: idUtilisateur,
                    nom: nom
                }
            })
        }
    }
}