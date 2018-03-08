package tradingbot

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class BotServiceSpec extends Specification {

    BotService botService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Bot(...).save(flush: true, failOnError: true)
        //new Bot(...).save(flush: true, failOnError: true)
        //Bot bot = new Bot(...).save(flush: true, failOnError: true)
        //new Bot(...).save(flush: true, failOnError: true)
        //new Bot(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //bot.id
    }

    void "test get"() {
        setupData()

        expect:
        botService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Bot> botList = botService.list(max: 2, offset: 2)

        then:
        botList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        botService.count() == 5
    }

    void "test delete"() {
        Long botId = setupData()

        expect:
        botService.count() == 5

        when:
        botService.delete(botId)
        sessionFactory.currentSession.flush()

        then:
        botService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Bot bot = new Bot()
        botService.save(bot)

        then:
        bot.id != null
    }
}
