package eu.bittrade.steem.steemstats.datacollector;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.steem.steemstats.datacollector.dao.RecordDaoImpl;
import eu.bittrade.steem.steemstats.datacollector.dao.TransactionsDaoImpl;
import eu.bittrade.steem.steemstats.datacollector.entities.Record;
import eu.bittrade.steem.steemstats.datacollector.entities.TransactionsPerBlock;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RecordCollector extends TimerTask {
    private static final Logger LOGGER = LogManager.getLogger(RecordCollector.class);
    private static final long RECORD_DIFFERENCE = 50000;

    private RecordDaoImpl recordDaoImpl;
    private TransactionsDaoImpl transactionsPerBlockDAO;

    public RecordCollector(RecordDaoImpl recordDaoImpl, TransactionsDaoImpl transactionsPerBlockDAO) {
        this.recordDaoImpl = recordDaoImpl;
        this.transactionsPerBlockDAO = transactionsPerBlockDAO;
    }

    @Override
    public void run() {
        try {
            Record latestRecord = recordDaoImpl.findNewest();
            Long currentSumOfOperations = transactionsPerBlockDAO.countOperations();

            if (latestRecord == null) {
                LOGGER.info("Created initial record.");
                Record newRecord = new Record();

                TransactionsPerBlock transactionsPerBlock = transactionsPerBlockDAO.findOldest();
                newRecord.setStartBlockNumber(transactionsPerBlock.getBlockNumber());
                newRecord.setStartBlockCreationDate(transactionsPerBlock.getBlockCreationDate());

                transactionsPerBlock = transactionsPerBlockDAO.findNewest();
                newRecord.setEndBlockNumber(transactionsPerBlock.getBlockNumber());
                newRecord.setEndBlockCreationDate(transactionsPerBlock.getBlockCreationDate());

                newRecord.setNumberOfOperations(transactionsPerBlockDAO.countOperations());
                newRecord.setNumberOfTransactions(transactionsPerBlockDAO.countTransactions());

                recordDaoImpl.persist(newRecord);
            }

            if (latestRecord != null && currentSumOfOperations > latestRecord.getNumberOfOperations()) {
                LOGGER.info("New record reached ({} Operations).", currentSumOfOperations);

                // Delete the current record as we have a new one.
                recordDaoImpl.delete(latestRecord);

                // Add an additional record object as we passed another
                // RECORD_DIFFERENCE mark.
                if (((int) (currentSumOfOperations / RECORD_DIFFERENCE)) > ((int) (latestRecord.getNumberOfOperations())
                        / RECORD_DIFFERENCE)) {
                    LOGGER.info("Creating additional record object.", currentSumOfOperations);

                    Record additionalRecord = new Record();

                    TransactionsPerBlock transactionsPerBlock = transactionsPerBlockDAO.findOldest();
                    additionalRecord.setStartBlockNumber(transactionsPerBlock.getBlockNumber());
                    additionalRecord.setStartBlockCreationDate(transactionsPerBlock.getBlockCreationDate());

                    transactionsPerBlock = transactionsPerBlockDAO.findNewest();
                    additionalRecord.setEndBlockNumber(transactionsPerBlock.getBlockNumber());
                    additionalRecord.setEndBlockCreationDate(transactionsPerBlock.getBlockCreationDate());

                    additionalRecord.setNumberOfOperations(transactionsPerBlockDAO.countOperations());
                    additionalRecord.setNumberOfTransactions(transactionsPerBlockDAO.countTransactions());

                    recordDaoImpl.persist(additionalRecord);
                }

                // Create the new record.
                Record newRecord = new Record();

                TransactionsPerBlock transactionsPerBlock = transactionsPerBlockDAO.findOldest();
                newRecord.setStartBlockNumber(transactionsPerBlock.getBlockNumber());
                newRecord.setStartBlockCreationDate(transactionsPerBlock.getBlockCreationDate());

                transactionsPerBlock = transactionsPerBlockDAO.findNewest();
                newRecord.setEndBlockNumber(transactionsPerBlock.getBlockNumber());
                newRecord.setEndBlockCreationDate(transactionsPerBlock.getBlockCreationDate());

                newRecord.setNumberOfOperations(transactionsPerBlockDAO.countOperations());
                newRecord.setNumberOfTransactions(transactionsPerBlockDAO.countTransactions());

                recordDaoImpl.persist(newRecord);
            }
        } catch (Exception e) {
            LOGGER.error("An unexpected Exception occured.", e);
        }
    }
}
