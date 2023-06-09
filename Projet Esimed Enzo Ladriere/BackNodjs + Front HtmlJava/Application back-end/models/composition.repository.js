const {tableListeComposition, tableListeJoueur} = require("./user.model");
const {DataTypes} = require("sequelize");

exports.getCompositions = async function getCompositions() {
    return await tableListeComposition.findAll();
}

exports.getCompositionsOfOneUser = async function getCompositionsOfOneUser(idUtilisateur) {
    return await tableListeComposition.findAll({where: {idUtilisateur: idUtilisateur }})
}

exports.getOneCompositionOfOneUser = async function getOneCompositionOfOneUser(idUtilisateur, nom) {
    return await tableListeComposition.findAll({where: {idUtilisateur: idUtilisateur , nom : nom}})
}

exports.creerComposition = async  function creerComposition(body) {
    await tableListeComposition.create( {
        idUtilisateur : body.idUtilisateur,
        nom : body.nom,
        nbrCarte : body.nbrCarte,
        nbrVillageois : body.nbrVillageois,
        nbrLoupGarou : body.nbrLoupGarou,
        petiteFille : body.petiteFille,
        sorciere : body.sorciere,
        chasseur : body.chasseur,
        cupidon : body.cupidon,
        voleur : body.voleur,
        voyante : body.voyante
    })
}

exports.modifierComposition = async function modifierComposition(body) {
    await tableListeComposition.update( {
        idUtilisateur : body.idUtilisateur,
        nom : body.nom,
        nbrCarte : body.nbrCarte,
        nbrVillageois : body.nbrVillageois,
        nbrLoupGarou : body.nbrLoupGarou,
        petiteFille : body.petiteFille,
        sorciere : body.sorciere,
        chasseur : body.chasseur,
        cupidon : body.cupidon,
        voleur : body.voleur,
        voyante : body.voyante
    }, {where : {idUtilisateur:body.idUtilisateur, nom:body.nom}})
}

exports.deleteJoueur = async function deleteJoueur(idUtilisateur, nom) {
    if (idUtilisateur != undefined && nom != undefined) {
        const user = await tableListeComposition.findOne({where: {idUtilisateur: idUtilisateur, nom: nom}});
        if (user) {
            await tableListeComposition.destroy({
                where: {
                    idUtilisateur: idUtilisateur,
                    nom: nom
                }
            })
        }
    }
}