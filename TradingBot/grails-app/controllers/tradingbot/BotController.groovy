package tradingbot

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class BotController {

    BotService botService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond botService.list(params), model:[botCount: botService.count()]
    }

    def show(Long id) {
        respond botService.get(id)
    }

    def create() {
        respond new Bot(params)
    }

    def save(Bot bot) {
        if (bot == null) {
            notFound()
            return
        }

        try {
            botService.save(bot)
        } catch (ValidationException e) {
            respond bot.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bot.label', default: 'Bot'), bot.id])
                redirect bot
            }
            '*' { respond bot, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond botService.get(id)
    }

    def update(Bot bot) {
        if (bot == null) {
            notFound()
            return
        }

        try {
            botService.save(bot)
        } catch (ValidationException e) {
            respond bot.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bot.label', default: 'Bot'), bot.id])
                redirect bot
            }
            '*'{ respond bot, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        botService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bot.label', default: 'Bot'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bot.label', default: 'Bot'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
