package eu.bittrade.steem.steemstats;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.Produces;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@WebListener
public class PersistenceListener implements ServletContextListener {
    private static EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        entityManagerFactory = Persistence.createEntityManagerFactory("eu.bittrade.steem.steemstats.entities.local");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        entityManagerFactory.close();
    }

    @Produces
    public static EntityManager createEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        return entityManagerFactory.createEntityManager();
    }
}
