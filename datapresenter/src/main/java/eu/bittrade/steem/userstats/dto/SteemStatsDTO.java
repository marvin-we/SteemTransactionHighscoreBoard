package eu.bittrade.steem.userstats.dto;

import java.util.List;

import eu.bittrade.steem.steemstats.entities.Record;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemStatsDTO {
    private List<Record> records;
    private long transactionsLastDay;
    private long operationsLastDay;
    private long transactionsLastHour;
    private long operationsLastHour;

    /**
     * @return the records
     */
    public List<Record> getRecords() {
        return records;
    }

    /**
     * @param records
     *            the records to set
     */
    public void setRecords(List<Record> records) {
        this.records = records;
    }

    /**
     * @return the transactionsLastDay
     */
    public long getTransactionsLastDay() {
        return transactionsLastDay;
    }

    /**
     * @param transactionsLastDay
     *            the transactionsLastDay to set
     */
    public void setTransactionsLastDay(long transactionsLastDay) {
        this.transactionsLastDay = transactionsLastDay;
    }

    /**
     * @return the operationsLastDay
     */
    public long getOperationsLastDay() {
        return operationsLastDay;
    }

    /**
     * @param operationsLastDay
     *            the operationsLastDay to set
     */
    public void setOperationsLastDay(long operationsLastDay) {
        this.operationsLastDay = operationsLastDay;
    }

    /**
     * @return the transactionsLastHour
     */
    public long getTransactionsLastHour() {
        return transactionsLastHour;
    }

    /**
     * @param transactionsLastHour
     *            the transactionsLastHour to set
     */
    public void setTransactionsLastHour(long transactionsLastHour) {
        this.transactionsLastHour = transactionsLastHour;
    }

    /**
     * @return the operationsLastHour
     */
    public long getOperationsLastHour() {
        return operationsLastHour;
    }

    /**
     * @param operationsLastHour
     *            the operationsLastHour to set
     */
    public void setOperationsLastHour(long operationsLastHour) {
        this.operationsLastHour = operationsLastHour;
    }

}
