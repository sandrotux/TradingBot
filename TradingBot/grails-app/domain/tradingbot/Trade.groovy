package tradingbot

class Trade {
    TradeType tradeType
    BigDecimal amountUSD
    BigDecimal exchangeRateBTC
    Date date
    
    Trade() {
        date = new Date()
    }
    
    static constraints = {
    }
    
    static mapping = {
        date defaultValue: "now()"
    }
}
