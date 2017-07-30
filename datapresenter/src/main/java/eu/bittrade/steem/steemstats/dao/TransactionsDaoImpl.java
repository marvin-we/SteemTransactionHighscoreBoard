package eu.bittrade.steem.steemstats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.steem.steemstats.entities.TransactionsPerBlock;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransactionsDaoImpl implements TransactionDao<TransactionsPerBlock, Integer> {
    private static final Logger LOGGER = LogManager.getLogger(TransactionsDaoImpl.class);

    @Inject
    private EntityManager entityManager;

    @Override
    public void delete(TransactionsPerBlock entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAll() {
        entityManager.getTransaction().begin();
        for (TransactionsPerBlock transactionsPerBlock : findAll()) {
            entityManager.remove(transactionsPerBlock);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public List<TransactionsPerBlock> findAll() {
        return entityManager.createQuery("SELECT t FROM TransactionsPerBlock t", TransactionsPerBlock.class)
                .getResultList();
    }

    @Override
    public TransactionsPerBlock findById(Integer id) {
        TransactionsPerBlock result = null;
        try {
            result = entityManager
                    .createQuery("SELECT t FROM TransactionsPerBlock t WHERE id=:id", TransactionsPerBlock.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            LOGGER.trace("No result found.", e);
        }

        return result;
    }

    @Override
    public void persist(TransactionsPerBlock entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT count(*) FROM TransactionsPerBlock t", Long.class).getSingleResult();
    }

    @Override
    public long countTransactions() {
        return entityManager.createQuery("SELECT sum(t.numberOfTransactions) FROM TransactionsPerBlock t", Long.class)
                .getSingleResult();
    }

    @Override
    public long countOperations() {
        return entityManager.createQuery("SELECT sum(t.numberOfOperations) FROM TransactionsPerBlock t", Long.class)
                .getSingleResult();
    }

    @Override
    public List<TransactionsPerBlock> findOlderThan(Integer id) {
        return entityManager
                .createQuery("SELECT t FROM TransactionsPerBlock t  WHERE id <=:id", TransactionsPerBlock.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public TransactionsPerBlock findNewest() {
        return entityManager
                .createQuery("SELECT t FROM TransactionsPerBlock t ORDER BY id DESC", TransactionsPerBlock.class)
                .setMaxResults(1).getSingleResult();
    }

    @Override
    public TransactionsPerBlock findOldest() {
        return entityManager
                .createQuery("SELECT t FROM TransactionsPerBlock t ORDER BY id ASC", TransactionsPerBlock.class)
                .setMaxResults(1).getSingleResult();
    }

    @Override
    public List<TransactionsPerBlock> findBetween(Integer from, Integer to) {
        return entityManager.createQuery("SELECT t FROM TransactionsPerBlock t WHERE id <= :to AND id >= :from",
                TransactionsPerBlock.class).setParameter("to", to).setParameter("from", from).getResultList();
    }

    @Override
    public long countTransactionsBetween(Integer from, Integer to) {
        return entityManager.createQuery(
                "SELECT sum(t.numberOfTransactions) FROM TransactionsPerBlock t WHERE id <= :to AND id >= :from",
                Integer.class).setParameter("to", to).setParameter("from", from).getSingleResult();
    }

    @Override
    public long countOperationsBetween(Integer from, Integer to) {
        return entityManager.createQuery(
                "SELECT sum(t.numberOfOperations) FROM TransactionsPerBlock t WHERE id <= :to AND id >= :from",
                Integer.class).setParameter("to", to).setParameter("from", from).getSingleResult();
    }

}
