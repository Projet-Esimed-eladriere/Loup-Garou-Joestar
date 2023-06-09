import BaseController from './basecontroller.js'
import Mymodel from "../model/mymodel.js"

class IndexController extends BaseController {
    constructor() {
        super()
        this.model = new Mymodel()
    }

    async actualiserCompositions() {
        let container = document.getElementById("compositions")
        const compositions = await this.getCompositions()
        console.log(compositions)
        container.innerHTML = ""
        for(let composition of compositions) {
            container.innerHTML += `<div class="row"><div class="col"><p> Nom de la composition : ${composition.nom}</p></div></div>`
            if(composition.petiteFille) {
                container.innerHTML += `<img class="carte" src="./res/imagepetitefille.jpg">`
            }

            if(composition.sorciere) {
                container.innerHTML += `<img class="carte" src="./res/imagesorciere.jpg">`
            }

            if(composition.chasseur) {
                container.innerHTML += `<img class="carte" src="./res/imgchasseur.jpg">`
            }

            if(composition.cupidon) {
                container.innerHTML += ` <img class="carte" src="./res/imgcupidon.jpg">`
            }

            if(composition.voleur) {
                container.innerHTML += `<img class="carte" src="./res/imgvoleur.jpg">`
            }

            if(composition.voyante) {
                container.innerHTML += `<img class="carte" src="./res/imgvoyante.jpg">`
            }

            for(let i = 0; i < composition.nbrVillageois; ++i) {
                container.innerHTML += `<img class="carte" src="./res/imgvillageois.jpg">`
            }

            for(let i = 0; i < composition.nbrLoupGarou; ++i) {
                container.innerHTML += `<img class="carte" src="./res/imgloupgarou.jpg">`
            }
        }
    }

    async getCompositions() {
        const pseudonyme = document.getElementById("pseudonyme")
        return await this.model.getCompositionParPseudonyme(pseudonyme.value)
        console.log(compositions)
    }
}
export default () => window.indexController = new IndexController()
