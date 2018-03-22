package tradingbot

class BootStrap {
    
    def init = { servletContext ->
        new Params(running: true, amountTrade: 10).save()
    }
    def destroy = {
    }
}
