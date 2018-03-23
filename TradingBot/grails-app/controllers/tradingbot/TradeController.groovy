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
    
    /**
     *  Buys BTC
     **/
    def buy() {
        config = paramsService.get(1)   // Get the config
        
        println " :: running: " + config.running
        if (!config.running) {
            respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'  // do nothing
            return
        }
        
        BigDecimal usd = bitfinexService.getBtcUsd()    // Get the exchange rate
        println " :: USD excahnge rate: "  + usd
        
        Trade lastTrade = tradeService.list(max: 1, sort: "date", order: "desc")[0]     // Get the last trade
        
        if (!lastTrade || lastTrade.getTradeType().equals(TradeType.SHORT)) {
            Trade trade = new Trade()
            trade.setAmountUSD(config.amountTrade)
            trade.setExchangeRateBTC(usd)
            trade.setTradeType(TradeType.LONG)

            tradeService.save(trade)

            flash.message = message(code: 'default.created.message', args: [message(code: 'trade.label', default: 'Trade'), trade.id])
        }
        
        params.max = Math.min(10, 100)
        params.sort = "date"
        params.order = "desc"
        respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'
    }
    
    /**
     * Sells BTC
     **/
    def sell() {
        config = paramsService.get(1)   // Get the config
        
        println " :: running: " + config.running
        if (!config.running) {
            respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'  // do nothing
            return
        }
        
        BigDecimal usd = bitfinexService.getBtcUsd()    // Get the exchange rate
        println " :: USD exchange rate: "  + usd
        
        Trade lastTrade = tradeService.list(max: 1, sort: "date", order: "desc")[0]     // Get the last trade
        
        if (lastTrade && lastTrade.getTradeType().equals(TradeType.LONG)) {
            Trade trade = new Trade()
            trade.setAmountUSD(config.amountTrade)
            trade.setExchangeRateBTC(usd)
            trade.setTradeType(TradeType.SHORT)
        
            tradeService.save(trade)

            flash.message = message(code: 'default.created.message', args: [message(code: 'trade.label', default: 'Trade'), trade.id])
        }
        
        params.max = Math.min(10, 100)
        params.sort = "date"
        params.order = "desc"
        respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'
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
    
    /**
     * Controles the bot status
     **/
    def toggleBotStatus(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "date"
        params.order = "desc"
        
        // Get the config
        this.config = paramsService.get(1)
        this.config.running = !this.config.running;
        paramsService.save(config)
        
        respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'
    }
    
    /**
     * Updates the trading amount
     **/
    def updateTradingAmount(BigDecimal tradingAmount, Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "date"
        params.order = "desc"
        
        // Get the config
        this.config = paramsService.get(1)
        this.config.amountTrade = tradingAmount
        paramsService.save(config)
        
        respond tradeService.list(params), model:[tradeCount: tradeService.count(), config: config], view: 'index'
    }
    
}
