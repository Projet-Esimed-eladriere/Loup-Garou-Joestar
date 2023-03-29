const uuid = require("uuid")
const bcrypt = require('bcryptjs');
const {tableUtilisateur} = require("./user.model");

exports.getUsers = async function getUsers()
{
    //SELECT * FROM tableUtilisateur
    return await tableUtilisateur.findAll();
}

exports.getUserByName =  async function getUserByName(pseudonyme)
{
    //SELECT * FROM tableUtilisateur WHERE tableUtilisateur.pseudonyme = pseudonyme
    // où pseudonyme est le paramétre de fonction
    return await tableUtilisateur.findOne({where: {pseudonyme: `${pseudonyme}` }});
}

exports.createUser =  async function createUser(body)
{
    console.log("body : ")
    console.log(body)
    let foundUser =  await tableUtilisateur.findOne({where: {pseudonyme: body.pseudonyme }});
    if(!foundUser)
    {
        if(!verifierMotdePasse(body.motDePasse))
        {
            console.log('Mot de passe invalide');
        }
        else
        {
            console.log(`
            Utilisateur créer avec : pseudonyme = ${body.pseudonyme},
             nom = ${body.nom},
             prenom = ${body.prenom},
             motDePasse = ${body.motDePasse}.`)
            const salt = bcrypt.genSaltSync(12);
            const hash = bcrypt.hashSync(body.motDePasse, salt);
            await tableUtilisateur.create({
                id : uuid.v4(),
                pseudonyme : body.pseudonyme,
                prenom : body.prenom,
                nom : body.nom,
                motDePasse : hash
            })
        }
    }
    else
    {
        console.log("Utilisateur déja existant");
    }
}

exports.updateUser =  async function updateUser(id, data)
{
    const foundUser = await tableUtilisateur.findOne({ where: { id } });
    let hash;
    if (!foundUser) {
        console.log("Pas d'utilisateur");
    }

    if(data.motDePasse)
    {
        if(!verifierMotdePasse(data.motDePasse))
        {
            console.log('Mot de passe invalide');
        }
        else
        {
            console.log(data.motDePasse)
            const salt = bcrypt.genSaltSync(12);
            hash = bcrypt.hashSync(data.motDePasse, salt);
        }
    }
    else
    {
        hash = foundUser.motDePasse
    }

    await tableUtilisateur.update(
        {
            pseudonyme: data.pseudonyme || foundUser.pseudonyme,
            prenom: data.prenom || foundUser.prenom,
            nom: data.nom || foundUser.nom,
            motDePasse: hash,
        }, { where: { id } });
}

exports.deleteUser =  async function deleteUser(id)
{
    await tableUtilisateur.destroy({
        where: {
            id: id
        }
    });
}

exports.testSQLite = async function testSQLite(){
    console.log('testSQLite()')

    const allusers = await tableUtilisateur.findAll();
    console.log(allusers)
}

function verifierMotdePasse(password)
{
    if(password.length >= 8)
    {
        return true
    }
    else
    {
        return false
    }
}