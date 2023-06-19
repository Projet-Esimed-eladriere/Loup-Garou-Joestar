const {DataTypes, STRING} = require("sequelize");
const { sequelize } = require('./sqlite.db');

const tableUtilisateur = sequelize.define('tableUtilisateur', {
    id : {primaryKey: true, type: DataTypes.UUIDV4, notNull: true},
    pseudonyme : {type: DataTypes.STRING, notNull: true, unique: true},
    prenom : {type: DataTypes.STRING, notNull: true},
    nom : {type: DataTypes.STRING, notNull: true},
    motDePasse : {type: DataTypes.STRING, notNull: true}
})

const tableListeJoueur = sequelize.define('listeJoueur', {
    idUtilisateur : {type: DataTypes.UUIDV4, notNull: true},
    nom : {type: DataTypes.STRING, notNull: true, unique: true}
})

const tableListeComposition = sequelize.define('composition', {
    idUtilisateur : {type: DataTypes.UUIDV4, notNull: true},
    nom : {type: DataTypes.STRING, notNull: true},
    nbrCarte : {type: DataTypes.INTEGER, notNull: true},
    nbrVillageois : {type: DataTypes.INTEGER, notNull: true},
    nbrLoupGarou : {type: DataTypes.INTEGER, notNull: true},
    petiteFille : {type: DataTypes.BOOLEAN, notNull: true},
    sorciere : {type: DataTypes.BOOLEAN, notNull: true},
    chasseur : {type: DataTypes.BOOLEAN, notNull: true},
    cupidon : {type: DataTypes.BOOLEAN, notNull: true},
    voleur : {type: DataTypes.BOOLEAN, notNull: true},
    voyante : {type: DataTypes.BOOLEAN, notNull: true},
})




/*sequelize.sync({ force: true }).then(() => {
    console.log('La table a été créée avec succès.');
}).catch(err => {
    console.error('Une erreur s\'est produite lors de la synchronisation du modèle :', err);
});*/


exports.tableUtilisateur = tableUtilisateur;
exports.tableListeJoueur = tableListeJoueur;
exports.tableListeComposition = tableListeComposition;