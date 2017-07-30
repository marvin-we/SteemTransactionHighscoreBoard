package eu.bittrade.steem.steemstats.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@Entity
@Table(name = "TRANSACTIONS_PER_BLOCK")
public class TransactionsPerBlock {
    @Id
    @Column(name = "BLOCK_NUMBER")
    private int blockNumber;
    @Column(name = "BLOCK_CREATION_DATE")
    private Date blockCreationDate;
    @Column(name = "NUMBER_OF_OPERATIONS")
    private long numberOfOperations;
    @Column(name = "NUMBER_OF_TRANSACTIONS")
    private long numberOfTransactions;

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    /**
     * @return the blockCreationDate
     */
    public Date getBlockCreationDate() {
        return blockCreationDate;
    }

    /**
     * @param blockCreationDate
     *            the blockCreationDate to set
     */
    public void setBlockCreationDate(Date blockCreationDate) {
        this.blockCreationDate = blockCreationDate;
    }

    /**
     * @return the numberOfOperations
     */
    public long getNumberOfOperations() {
        return numberOfOperations;
    }

    /**
     * @param numberOfOperations
     *            the numberOfOperations to set
     */
    public void setNumberOfOperations(long numberOfOperations) {
        this.numberOfOperations = numberOfOperations;
    }

    /**
     * @return the numberOfTransactions
     */
    public long getNumberOfTransactions() {
        return numberOfTransactions;
    }

    /**
     * @param numberOfTransactions
     *            the numberOfTransactions to set
     */
    public void setNumberOfTransactions(long numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }
}
