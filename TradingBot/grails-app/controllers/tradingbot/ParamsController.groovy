package tradingbot

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ParamsController {

    ParamsService paramsService

    static allowedMethods = [updateAmount: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond paramsService.list(params), model:[paramsCount: paramsService.count()]
    }

    def show(Long id) {
        respond paramsService.get(id)
    }

    def create() {
        respond new Params(params)
    }

    def save(Params params) {
        if (params == null) {
            notFound()
            return
        }

        try {
            paramsService.save(params)
        } catch (ValidationException e) {
            respond params.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'params.label', default: 'Params'), params.id])
                redirect params
            }
            '*' { respond params, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond paramsService.get(id)
    }

    def update(Params params) {
        if (params == null) {
            notFound()
            return
        }

        try {
            paramsService.save(params)
        } catch (ValidationException e) {
            respond params.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'params.label', default: 'Params'), params.id])
                redirect params
            }
            '*'{ respond params, [status: OK] }
        }
    }
    
    /**
     * Updates only the amount. And returns to the Trade views
     **/
    def updateAmount() {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "date"
        params.order = "desc"
        
        println "AMOUNT: " + params.inAmount
        // Get the config
        this.config = paramsService.get(1)
        this.config.amountTrade = params.inAmount
        paramsService.save(config)
        
        respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        paramsService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'params.label', default: 'Params'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'params.label', default: 'Params'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
