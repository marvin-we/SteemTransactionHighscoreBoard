package eu.bittrade.steem.steemstats;

import javax.persistence.EntityManager;

import org.glassfish.hk2.api.Factory;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class EntityManagerFactoryForHK2 implements Factory<EntityManager> {
	@Override
	public void dispose(EntityManager entityManager) {
		entityManager.close();
	}

	@Override
	public EntityManager provide() {
		return PersistenceListener.createEntityManager();
	}
}
