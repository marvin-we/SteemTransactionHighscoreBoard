package eu.bittrade.steem.steemstats;

import javax.persistence.EntityManager;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import eu.bittrade.steem.steemstats.dao.RecordDaoImpl;
import eu.bittrade.steem.steemstats.dao.TransactionsDaoImpl;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ResourceLoader extends ResourceConfig {

    public ResourceLoader() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(EntityManagerFactoryForHK2.class).to(EntityManager.class).proxy(false);
                bind(TransactionsDaoImpl.class).to(TransactionsDaoImpl.class).proxy(false);
                bind(RecordDaoImpl.class).to(RecordDaoImpl.class).proxy(false);
            }
        });
        packages(true, "eu.bittrade.steem.steemstats.endpoints");
    }
}   
