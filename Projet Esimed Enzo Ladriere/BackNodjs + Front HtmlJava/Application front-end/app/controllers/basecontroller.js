export default class BaseController {
    constructor() {
        this.setBackButtonView('index')
        this.loader = document.createElement("img")
        this.loader.src = "res/loader.gif"
        this.loader.style.width = "100px"
        this.loader.style.margin = "auto"
    }
    toast(elemId) {
        const toast = new bootstrap.Toast(document.getElementById(elemId))
        toast.show()
    }
    setBackButtonView(view) {
        window.onpopstate = function() {
            navigate(view)
        }; history.pushState({}, '');
    }
}
