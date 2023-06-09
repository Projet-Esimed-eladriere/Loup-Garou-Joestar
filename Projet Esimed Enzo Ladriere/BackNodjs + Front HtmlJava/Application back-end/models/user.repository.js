const uuid = require("uuid")
const bcrypt = require('bcryptjs');
const {tableUtilisateur} = require("./user.model");
const {sequelize} = require("./sqlite.db");

exports.getUsers = async function getUsers() {
    return await tableUtilisateur.findAll();
}

exports.getUserByName =  async function getUserByName(pseudonyme) {
    return await tableUtilisateur.findOne({where: {pseudonyme: `${pseudonyme}` }});
}

exports.createUser =  async function createUser(body) {
    const salt = bcrypt.genSaltSync(12);
    if(body.motDePasse != undefined){
        const hash = bcrypt.hashSync(body.motDePasse, salt);
        console.log("body" + body)
        await tableUtilisateur.create({
            id: uuid.v4(),
            pseudonyme: body.pseudonyme,
            prenom: body.prenom,
            nom: body.nom,
            motDePasse: hash
        })
    }
}

exports.updateUser =  async function updateUser(id, data) {
    const foundUser = await tableUtilisateur.findOne({ where: { id } });
    let hash;
    if (!foundUser) {
        console.log("Pas d'utilisateur");
    }
    else {
        console.log(data.motDePasse)
        const salt = bcrypt.genSaltSync(12);
        hash = bcrypt.hashSync(data.motDePasse, salt);
        hash = foundUser.motDePasse
    }

    await tableUtilisateur.update({
            pseudonyme: data.pseudonyme || foundUser.pseudonyme,
            prenom: data.prenom || foundUser.prenom,
            nom: data.nom || foundUser.nom,
            motDePasse: hash,
        }, { where: { id } });
}

exports.deleteUser =  async function deleteUser(id) {
    await tableUtilisateur.destroy({
        where: {
            id: id
        }
    });
}

exports.syncBDD = async function syncBDD() {
    sequelize.sync({ force: true }).then(() => {
        console.log('La table a été créée avec succès.');
    }).catch(err => {
        console.error('Une erreur s\'est produite lors de la synchronisation du modèle :', err);
    });
}

exports.testSQLite = async function testSQLite() {
    const allusers = await tableUtilisateur.findAll();
    console.log(allusers)
}