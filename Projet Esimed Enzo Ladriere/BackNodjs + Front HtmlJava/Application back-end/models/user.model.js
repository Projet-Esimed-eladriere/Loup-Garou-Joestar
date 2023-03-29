const {DataTypes, STRING} = require("sequelize");
const { sequelize } = require('./sqlite.db');

const tableUtilisateur = sequelize.define('tableUtilisateur', {
    id : {primaryKey: true, type: DataTypes.UUIDV4, notNull: true},
    pseudonyme : {type: DataTypes.STRING, notNull: true, unique: true},
    prenom : {type: DataTypes.STRING, notNull: true},
    nom : {type: DataTypes.STRING, notNull: true},
    motDePasse : {type: DataTypes.STRING, notNull: true}
})

exports.tableUtilisateur = tableUtilisateur;