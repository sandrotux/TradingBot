package tradingbot.bitfinex

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

@Transactional
class BitfinexService {

    def serviceMethod() {
        
    }
    
    def getBtcUsd() {
        def usd = 0
        def connection = new URL( "https://api.bitfinex.com/v1/pubticker/btcusd")
        .openConnection() as HttpURLConnection
        
        connection.setRequestProperty( 'User-Agent', 'groovy-2.4.4' )
        connection.setRequestProperty( 'Accept', 'application/json' )
        
        //println connection.responseCode + ": " + connection.inputStream.text
        def resultText = connection.inputStream.text
        
        if (connection.responseCode == 200) {
            def slurper = new JsonSlurper()
            def resultMap = slurper.parseText(resultText)
            
            usd = resultMap["last_price"]
        }
        return new BigDecimal(usd)
    }
}
