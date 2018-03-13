package tradingbot

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TradeTypeServiceSpec extends Specification {

    TradeTypeService tradeTypeService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TradeType(...).save(flush: true, failOnError: true)
        //new TradeType(...).save(flush: true, failOnError: true)
        //TradeType tradeType = new TradeType(...).save(flush: true, failOnError: true)
        //new TradeType(...).save(flush: true, failOnError: true)
        //new TradeType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tradeType.id
    }

    void "test get"() {
        setupData()

        expect:
        tradeTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TradeType> tradeTypeList = tradeTypeService.list(max: 2, offset: 2)

        then:
        tradeTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tradeTypeService.count() == 5
    }

    void "test delete"() {
        Long tradeTypeId = setupData()

        expect:
        tradeTypeService.count() == 5

        when:
        tradeTypeService.delete(tradeTypeId)
        sessionFactory.currentSession.flush()

        then:
        tradeTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TradeType tradeType = new TradeType()
        tradeTypeService.save(tradeType)

        then:
        tradeType.id != null
    }
}
