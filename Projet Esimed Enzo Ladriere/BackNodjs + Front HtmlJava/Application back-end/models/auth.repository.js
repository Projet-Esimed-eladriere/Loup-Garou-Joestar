const bcrypt = require('bcryptjs');
const {tableUtilisateur} = require("./user.model");

exports.authUser = async function authUser(pseudonyme, password) {
    console.log('authUser');
    const userAuth = await tableUtilisateur.findOne({ where: { pseudonyme: pseudonyme } });
    if (!userAuth) {
        return false;
    } else {
        const passwordMatch = bcrypt.compareSync(password, userAuth.motDePasse);
        return passwordMatch;
    }
};