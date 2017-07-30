package eu.bittrade.steem.steemstats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.steem.steemstats.entities.Record;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RecordDaoImpl implements RecordDao<Record, Long> {
    private static final Logger LOGGER = LogManager.getLogger(TransactionsDaoImpl.class);

    @Inject
    private EntityManager entityManager;

    @Override
    public void delete(Record entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAll() {
        entityManager.getTransaction().begin();
        for (Record record : findAll()) {
            entityManager.remove(record);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Record> findAll() {
        return entityManager.createQuery("SELECT r FROM Record r", Record.class).getResultList();
    }

    @Override
    public Record findNewest() {
        return entityManager.createQuery("SELECT r FROM Record r ORDER BY id DESC", Record.class).setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public Record findOldest() {
        return entityManager.createQuery("SELECT r FROM Record r ORDER BY id ASC", Record.class).setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public Record findById(Long id) {
        try {
            return entityManager.createQuery("SELECT r FROM Record r  WHERE id=:id", Record.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.trace("No result found.", e);
        }
        return null;
    }

    @Override
    public void persist(Record entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }
}
