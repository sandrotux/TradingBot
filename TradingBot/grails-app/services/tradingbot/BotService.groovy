package tradingbot

import grails.gorm.services.Service

@Service(Bot)
interface BotService {

    Bot get(Serializable id)

    List<Bot> list(Map args)

    Long count()

    void delete(Serializable id)

    Bot save(Bot bot)

}