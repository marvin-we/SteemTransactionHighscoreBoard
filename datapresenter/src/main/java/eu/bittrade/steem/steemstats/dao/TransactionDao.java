package eu.bittrade.steem.steemstats.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface TransactionDao<T, I extends Serializable> extends GenericDao<T, I> {
    public List<T> findOlderThan(I id);

    public List<T> findBetween(I from, I to);

    public long count();

    public long countTransactions();

    public long countOperations();

    public long countTransactionsBetween(I from, I to);

    public long countOperationsBetween(I from, I to);
}
