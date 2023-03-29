import BaseController from './basecontroller.js'
import Mymodel from "../model/mymodel.js"

class IndexController extends BaseController {
    constructor() {
        super()
        this.model = new Mymodel()
    }

    async afficherUtilisateurLog(){
        console.log(await this.model.getUtilisateurs())
    }

    async afficherUtilisateurLogParPseudonyme(pseudonyme){
        console.log(await this.model.getUtilisateursParPseudonyme(pseudonyme.value))
    }

    async getUtilisateur(pseudonyme)
    {
        return await this.model.getUtilisateursParPseudonyme(pseudonyme.value)
    }

    async creerUtilisateur()
    {
        const pseudonyme = document.getElementById("pseudonyme")
        const nom = document.getElementById("nom")
        const prenom = document.getElementById("prenom")
        const motDePasse = document.getElementById("motDePasse")
        const confirmationMotDePasse = document.getElementById("confirmationMotDePasse")
        const messageErreur = document.getElementById("MessageErreur")
        let textMessage = ""

        let analyseDesErreur = this.verifierFormulaireInscription(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse)
        console.log(`Erreur = ${analyseDesErreur}`)

        messageErreur.style.display = "none"

        if(analyseDesErreur == 0)
        {
            await this.model.creerUtilisateur(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse)
        }
        else
        {
            switch (analyseDesErreur)
            {
                case 1 :
                {
                    textMessage = "Veuillez saisir tout les informations"
                    break
                }

                case 2 :
                {
                    textMessage = "Veuillez mettre au minimum 8 charactere pour le mot de passe"
                    break
                }

                case 3 :
                {
                    textMessage = "Le mot de passe et sa confirmation ne corresponde pas"
                    break
                }

                case 4 :
                {
                    textMessage = "Utilisateur déja existant (Cherchez un autre nom)"
                    break
                }

                default :
                {
                    break
                }
            }

            messageErreur.innerHTML = textMessage
            messageErreur.style.display = ""
            messageErreur.style.fontSize = "10px"
            messageErreur.style.padding = "20px"
            messageErreur.className = "alert alert-danger"
        }
    }


    verifierFormulaireInscription(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse)
    {
        if(this.verifierSiInformationsSaisie(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse))
        {
            if(this.verifierCaracteristiqueMotDePasse(motDePasse))
            {
                if(this.verifierCorrespondanceMotDePasseConfirmation(motDePasse, confirmationMotDePasse))
                {
                    if(this.verifierSiUtilisateurDejaExistant(pseudonyme))
                    {
                        return 0 //Tout c'es bien passé
                    }
                    else
                    {
                        return 4 // Utilisateur déja existant
                    }
                }
                else
                {
                    return 3 // Le mot de passe ne corerespond pas a sa confirmation
                }
            }
            else
            {
                return 2 // Le mot de passe a moin de 8 charactere
            }
        }
        else
        {
            return 1 // Une ou des informations n'ont pas était saisie
        }
    }

    verifierCaracteristiqueMotDePasse(motDePasse)
    {
        console.log("verifierCaracteristiqueMotDePasse()")
        if(motDePasse.value.length >= 8)
        {
            return true
        }
        else
        {
            return false
        }
    }

    verifierCorrespondanceMotDePasseConfirmation(motDePasse, confirmationMotDePasse)
    {
        console.log("verifierCorrespondanceMotDePasseConfirmation()")
        if(motDePasse.value == confirmationMotDePasse.value)
        {
            return true
        }
        else
        {
            return false
        }
    }

    verifierSiInformationsSaisie(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse)
    {
        console.log("verifierSiInformationsSaisie()")
        let informations = [pseudonyme.value, nom.value, prenom.value, motDePasse.value, confirmationMotDePasse.value]
        for(let information of informations)
        {
            if(information == "")
            {
                return false
            }
        }

        return true
    }

    async verifierSiUtilisateurDejaExistant(pseudonyme)
    {
        console.log("verifierSiUtilisateurDejaExistant()")
        if(await this.getUtilisateur(pseudonyme) === undefined)
        {
            return false
        }
        else
        {
            return true
        }
    }
}


export default () => window.indexController = new IndexController()
