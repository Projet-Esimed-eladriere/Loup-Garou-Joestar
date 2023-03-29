/*const {Sequelize, DataTypes} = require("sequelize");
const bcrypt = require('bcryptjs');
const {tableUtilisateur} = require("./user.model");

exports.authUser = async function authUser(firstName, password)
{
    console.log('authUser')
    const userAuth = await tableUtilisateur.findOne({
        where: {
            firstName: firstName
        }
    });

    if(userAuth === undefined)
    {
        return false
    }
    else
    {
        return bcrypt.compareSync(password, userAuth.password)
    }
}*/