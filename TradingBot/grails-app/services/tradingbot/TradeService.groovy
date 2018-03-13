package tradingbot

import grails.gorm.services.Service

@Service(Trade)
interface TradeService {

    Trade get(Serializable id)

    List<Trade> list(Map args)

    Long count()

    void delete(Serializable id)

    Trade save(Trade trade)

}