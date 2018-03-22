package tradingbot.config

import grails.gorm.transactions.Transactional

@Transactional
class ConfigService {
    
    Boolean enabled
    BigDecimal amountTrading

    synchronized Boolean isEnabled() {
        return this.enabled
    }
    
    synchronized void setEnabled(Boolean enabled) {
        this.enabled = enabled
    }
    
    synchronized BigDecimal getAmountTrading() {
        return this.amountTrading
    }
    
    synchronized void setAmountTrading(BigDecimal amountTrading) {
        this.amountTrading = amountTrading
    }
    
}
