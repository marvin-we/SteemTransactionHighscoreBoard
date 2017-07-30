package eu.bittrade.steem.steemstats.datacollector.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import eu.bittrade.steem.steemstats.datacollector.BlockchainSynchronization;
import eu.bittrade.steem.steemstats.datacollector.entities.Record;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RecordDaoImpl implements RecordDao<Record, Long> {
    private static final Logger LOGGER = LogManager.getLogger(TransactionsDaoImpl.class);

    private Session session;

    @Override
    public void delete(Record entity) {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (Record record : findAll()) {
            session.remove(record);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List<Record> findAll() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        List<Record> result = session.createQuery("SELECT r FROM Record r", Record.class).getResultList();
        session.close();
        return result;
    }

    @Override
    public Record findNewest() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Record result = session.createQuery("SELECT r FROM Record r ORDER BY id DESC", Record.class).setMaxResults(1)
                .uniqueResult();
        session.close();
        return result;
    }

    @Override
    public Record findOldest() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Record result = session.createQuery("SELECT r FROM Record r ORDER BY id ASC", Record.class).setMaxResults(1)
                .getSingleResult();
        session.close();
        return result;
    }

    @Override
    public Record findById(Long id) {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Record result = null;
        try {
            result = session.createQuery("SELECT r FROM Record r  WHERE id=:id", Record.class).setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            LOGGER.trace("No result found.", e);
        }
        session.close();
        return result;
    }

    @Override
    public void persist(Record entity) {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
    }
}
