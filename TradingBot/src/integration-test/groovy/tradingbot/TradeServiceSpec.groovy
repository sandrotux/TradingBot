package tradingbot

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class TradeServiceSpec extends Specification {

    TradeService tradeService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Trade(...).save(flush: true, failOnError: true)
        //new Trade(...).save(flush: true, failOnError: true)
        //Trade trade = new Trade(...).save(flush: true, failOnError: true)
        //new Trade(...).save(flush: true, failOnError: true)
        //new Trade(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //trade.id
    }

    void "test get"() {
        setupData()

        expect:
        tradeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Trade> tradeList = tradeService.list(max: 2, offset: 2)

        then:
        tradeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tradeService.count() == 5
    }

    void "test delete"() {
        Long tradeId = setupData()

        expect:
        tradeService.count() == 5

        when:
        tradeService.delete(tradeId)
        sessionFactory.currentSession.flush()

        then:
        tradeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Trade trade = new Trade()
        tradeService.save(trade)

        then:
        trade.id != null
    }
}
