package eu.bittrade.steem.steemstats.datacollector.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface TransactionDao<T, I extends Serializable> extends GenericDao<T, I> {
    public List<T> findOlderThan(I id);

    public long count();
    
    public long countTransactions();
    
    public long countOperations();
}
