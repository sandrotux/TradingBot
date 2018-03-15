package tradingbot

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ParamsServiceSpec extends Specification {

    ParamsService paramsService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Params(...).save(flush: true, failOnError: true)
        //new Params(...).save(flush: true, failOnError: true)
        //Params params = new Params(...).save(flush: true, failOnError: true)
        //new Params(...).save(flush: true, failOnError: true)
        //new Params(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //params.id
    }

    void "test get"() {
        setupData()

        expect:
        paramsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Params> paramsList = paramsService.list(max: 2, offset: 2)

        then:
        paramsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        paramsService.count() == 5
    }

    void "test delete"() {
        Long paramsId = setupData()

        expect:
        paramsService.count() == 5

        when:
        paramsService.delete(paramsId)
        sessionFactory.currentSession.flush()

        then:
        paramsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Params params = new Params()
        paramsService.save(params)

        then:
        params.id != null
    }
}
