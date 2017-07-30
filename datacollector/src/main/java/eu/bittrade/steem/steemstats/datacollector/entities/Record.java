package eu.bittrade.steem.steemstats.datacollector.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@Entity
@Table(name = "RECORDS")
public class Record {
    @Id
    @Column(name = "RECORD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "START_BLOCK_NUMBER")
    private long startBlockNumber;
    @Column(name = "START_BLOCK_CREATION_DATE")
    private Date startBlockCreationDate;
    @Column(name = "END_BLOCK_NUMBER")
    private long endBlockNumber;
    @Column(name = "END_BLOCK_CREATION_DATE")
    private Date endBlockCreationDate;
    @Column(name = "NUMBER_OF_OPERATIONS")
    private long numberOfOperations;
    @Column(name = "NUMBER_OF_TRANSACTIONS")
    private long numberOfTransactions;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the startBlockNumber
     */
    public long getStartBlockNumber() {
        return startBlockNumber;
    }

    /**
     * @param startBlockNumber
     *            the startBlockNumber to set
     */
    public void setStartBlockNumber(long startBlockNumber) {
        this.startBlockNumber = startBlockNumber;
    }

    /**
     * @return the startBlockCreationDate
     */
    public Date getStartBlockCreationDate() {
        return startBlockCreationDate;
    }

    /**
     * @param startBlockCreationDate
     *            the startBlockCreationDate to set
     */
    public void setStartBlockCreationDate(Date startBlockCreationDate) {
        this.startBlockCreationDate = startBlockCreationDate;
    }

    /**
     * @return the endBlockNumber
     */
    public long getEndBlockNumber() {
        return endBlockNumber;
    }

    /**
     * @param endBlockNumber
     *            the endBlockNumber to set
     */
    public void setEndBlockNumber(long endBlockNumber) {
        this.endBlockNumber = endBlockNumber;
    }

    /**
     * @return the endBlockCreationDate
     */
    public Date getEndBlockCreationDate() {
        return endBlockCreationDate;
    }

    /**
     * @param endBlockCreationDate
     *            the endBlockCreationDate to set
     */
    public void setEndBlockCreationDate(Date endBlockCreationDate) {
        this.endBlockCreationDate = endBlockCreationDate;
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
