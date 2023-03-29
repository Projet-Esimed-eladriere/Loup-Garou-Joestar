export default class Myapi {
    constructor() {
        this.baseurl = "http://localhost:3000"
    }

    myFetch(url,init) {
        return new Promise(((resolve, reject) => {
            fetch(`${this.baseurl}/${url}`, init)
                .then(response => {
                    if (response.status === 200) {
                        resolve(response.json())
                    } else {
                        reject(response.status)
                    }
                })
                .catch(err => reject(err))
        }))
    }

    getUtilisateurs()
    {
        return this.myFetch(`users`,{method : "GET"})
    }

    getUtilisateursParPseudonyme(pseudonyme)
    {
        return this.myFetch(`users/${pseudonyme}`,{method : "GET"})
    }

    creerUtilisateur(pseudonyme, nom, prenom, motDePasse, confirmationMotDePasse)
    {
            console.log(`fetch => `)
            return this.myFetch('users', {
                method: "POST",
                body: JSON.stringify({
                    pseudonyme: pseudonyme.value,
                    nom: nom.value,
                    prenom: prenom.value,
                    motDePasse: motDePasse.value
                }),
                headers: {"Content-type": "application/json; charset=UTF-8"}
            })
    }


}
