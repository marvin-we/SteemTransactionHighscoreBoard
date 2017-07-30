package eu.bittrade.steem.steemstats.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface GenericDao<T, I extends Serializable> {
    public void delete(T entity);

    public void deleteAll();

    public List<T> findAll();

    public T findNewest();

    public T findOldest();

    public T findById(I id);

    public void persist(T entity);
}
