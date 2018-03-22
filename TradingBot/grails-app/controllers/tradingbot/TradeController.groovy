package tradingbot

import grails.validation.ValidationException
import tradingbot.bitfinex.BitfinexService

import static org.springframework.http.HttpStatus.*

class TradeController {

    TradeService tradeService
    BitfinexService bitfinexService
    ParamsService paramsService
    Params config

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "date"
        params.order = "desc"
        
        // Get the config
        config = paramsService.get(1)
        
        respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config]
    }

    def show(Long id) {
        respond tradeService.get(id)
    }

    def create() {
        respond new Trade(params)
    }
    
    def buy() {
        BigDecimal usd = bitfinexService.getBtcUsd()
        println " :: usd: "  + usd
        
        // Get the last trade
        Trade lastTrade = tradeService.list(max: 1, sort: "date", order: "desc")[0]
        
        if (!lastTrade || lastTrade.getTradeType().equals(TradeType.SHORT)) {
            Trade trade = new Trade()
            trade.setAmountUSD(4000)
            trade.setExchangeRateBTC(usd)
            trade.setTradeType(TradeType.LONG)

            tradeService.save(trade)

            flash.message = message(code: 'default.created.message', args: [message(code: 'trade.label', default: 'Trade'), trade.id])
        }
        
        params.max = Math.min(10, 100)
        params.sort = "date"
        params.order = "desc"
        respond tradeService.list(params), model:[tradeCount: tradeService.count()]
    }
    
    def sell() {
        BigDecimal usd = bitfinexService.getBtcUsd()
        println " :: usd: "  + usd
        
        // Get the last trade
        Trade lastTrade = tradeService.list(max: 1, sort: "date", order: "desc")[0]
        
        if (lastTrade && lastTrade.getTradeType().equals(TradeType.LONG)) {
            Trade trade = new Trade()
            trade.setAmountUSD(4000)
            trade.setExchangeRateBTC(usd)
            trade.setTradeType(TradeType.SHORT)
        
            tradeService.save(trade)

            flash.message = message(code: 'default.created.message', args: [message(code: 'trade.label', default: 'Trade'), trade.id])
        }
        
        params.max = Math.min(10, 100)
        params.sort = "date"
        params.order = "desc"
        respond tradeService.list(params), model:[tradeCount: tradeService.count()]
    }

    def save(Trade trade) {
        if (trade == null) {
            notFound()
            return
        }

        try {
            tradeService.save(trade)
        } catch (ValidationException e) {
            respond trade.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'trade.label', default: 'Trade'), trade.id])
                redirect trade
            }
            '*' { respond trade, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond tradeService.get(id)
    }

    def update(Trade trade) {
        if (trade == null) {
            notFound()
            return
        }

        try {
            tradeService.save(trade)
        } catch (ValidationException e) {
            respond trade.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'trade.label', default: 'Trade'), trade.id])
                redirect trade
            }
            '*'{ respond trade, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        tradeService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'trade.label', default: 'Trade'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'trade.label', default: 'Trade'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
