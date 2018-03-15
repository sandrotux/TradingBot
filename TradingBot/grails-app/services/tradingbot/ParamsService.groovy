package tradingbot

import grails.gorm.services.Service

@Service(Params)
interface ParamsService {

    Params get(Serializable id)

    List<Params> list(Map args)

    Long count()

    void delete(Serializable id)

    Params save(Params params)

}