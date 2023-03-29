import BaseController from "./basecontroller.js"

class DetailsController extends BaseController {
    constructor()
    {
        super()
    }
}

export default () => window.detailsController = new DetailsController()