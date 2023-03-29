import Myapi from "../services/myapi.js"

export default class Mymodel {
    constructor() {
        this.api = new Myapi()
    }

    async getUtilisateurs() {
       try {
            return await this.api.getUtilisateurs()
        } catch {
            return undefined
        }
    }
    async getUtilisateursParPseudonyme(pseudonyme){
            try {
                return await this.api.getUtilisateursParPseudonyme(pseudonyme)
            } catch {
                return undefined
            }
        }

    async creerUtilisateur(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse){
        try {
            return await this.api.creerUtilisateur(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse)
        } catch {
            return undefined
        }
    }
}