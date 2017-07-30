package eu.bittrade.steem.steemstats.datacollector;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steemj.SteemApiWrapper;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.steem.steemstats.datacollector.dao.TransactionsDaoImpl;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class QueueCrammer extends TimerTask {
    private static final Logger LOGGER = LogManager.getLogger(QueueCrammer.class);

    private TransactionsDaoImpl transactionsPerBlockDAO;
    private SteemApiWrapper apiWrapper;

    public QueueCrammer(SteemApiWrapper apiWrapper, TransactionsDaoImpl transactionsPerBlockDAO) {
        this.apiWrapper = apiWrapper;
        this.transactionsPerBlockDAO = transactionsPerBlockDAO;
    }

    @Override
    public void run() {
        LOGGER.info("Synchronization has been started.");
        int numberOfBlocks = 0;
        try {
            numberOfBlocks = apiWrapper.getDynamicGlobalProperties().getHeadBlockNumber();
        } catch (SteemCommunicationException e) {

            LOGGER.error("Fata error on Steem api call.", e);
        }
        LOGGER.info("Current head block number is {}.", numberOfBlocks);

        int startBlock = 0;
        if (transactionsPerBlockDAO.findNewest() != null) {
            startBlock = transactionsPerBlockDAO.findNewest().getBlockNumber() + 1;
        } else {
            startBlock = 1;
        }

        for (int i = startBlock; i <= numberOfBlocks; i++) {
            if (!BlockchainSynchronization.queue.contains(i)) {
                BlockchainSynchronization.queue.offer(i);
            }
        }
    }
}
