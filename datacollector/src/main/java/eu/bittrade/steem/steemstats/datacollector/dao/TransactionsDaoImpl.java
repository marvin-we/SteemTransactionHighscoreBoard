package eu.bittrade.steem.steemstats.datacollector.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import eu.bittrade.steem.steemstats.datacollector.BlockchainSynchronization;
import eu.bittrade.steem.steemstats.datacollector.entities.TransactionsPerBlock;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransactionsDaoImpl implements TransactionDao<TransactionsPerBlock, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(TransactionsDaoImpl.class);

    private Session session;

    @Override
    public void delete(TransactionsPerBlock entity) {
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
        for (TransactionsPerBlock userPerBlock : findAll()) {
            session.remove(userPerBlock);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List<TransactionsPerBlock> findAll() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        List<TransactionsPerBlock> result = session
                .createQuery("SELECT t FROM TransactionsPerBlock t", TransactionsPerBlock.class).getResultList();
        session.close();
        return result;
    }

    @Override
    public TransactionsPerBlock findById(Integer id) {
        session = BlockchainSynchronization.sessionFactory.openSession();
        TransactionsPerBlock result = null;
        try {
            result = session
                    .createQuery("SELECT t FROM TransactionsPerBlock t WHERE id=:id", TransactionsPerBlock.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.trace("No result found.", e);
        }
        session.close();
        return result;
    }

    @Override
    public void persist(TransactionsPerBlock entity) {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public long count() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        long result = session.createQuery("SELECT count(*) FROM TransactionsPerBlock t", Long.class).getSingleResult();
        session.close();
        return result;
    }

    @Override
    public long countTransactions() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        long result = session.createQuery("SELECT sum(t.numberOfTransactions) FROM TransactionsPerBlock t", Long.class)
                .getSingleResult();
        session.close();
        return result;
    }

    @Override
    public long countOperations() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        Query<Long> queryResult =  session.createQuery("SELECT sum(t.numberOfOperations) FROM TransactionsPerBlock t", Long.class);
        
        if( queryResult == null) {
            return 0;
        }
        
        return queryResult.getSingleResult();
    }

    @Override
    public List<TransactionsPerBlock> findOlderThan(Integer id) {
        session = BlockchainSynchronization.sessionFactory.openSession();
        List<TransactionsPerBlock> result = session
                .createQuery("SELECT t FROM TransactionsPerBlock t  WHERE id <=:id", TransactionsPerBlock.class)
                .setParameter("id", id).getResultList();
        session.close();
        return result;
    }

    @Override
    public TransactionsPerBlock findNewest() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        TransactionsPerBlock result = session
                .createQuery("SELECT t FROM TransactionsPerBlock t ORDER BY id DESC", TransactionsPerBlock.class)
                .setMaxResults(1).uniqueResult();
        session.close();
        return result;
    }

    @Override
    public TransactionsPerBlock findOldest() {
        session = BlockchainSynchronization.sessionFactory.openSession();
        TransactionsPerBlock result = session
                .createQuery("SELECT t FROM TransactionsPerBlock t ORDER BY id ASC", TransactionsPerBlock.class)
                .setMaxResults(1).getSingleResult();
        session.close();
        return result;
    }

}
