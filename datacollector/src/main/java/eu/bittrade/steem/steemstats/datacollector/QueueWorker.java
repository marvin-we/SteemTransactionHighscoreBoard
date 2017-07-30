package eu.bittrade.steem.steemstats.datacollector;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steemj.SteemApiWrapper;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.steem.steemstats.datacollector.dao.TransactionsDaoImpl;
import eu.bittrade.steem.steemstats.datacollector.entities.TransactionsPerBlock;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class QueueWorker implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(QueueWorker.class);
    private int uuid = new Random().nextInt();

    private TransactionsDaoImpl transactionsPerBlockDAO;
    private SteemApiWrapper apiWrapper;

    public QueueWorker(TransactionsDaoImpl transactionsPerBlockDAO) throws InterruptedException {
        this.transactionsPerBlockDAO = transactionsPerBlockDAO;
        this.apiWrapper = BlockchainSynchronization.resourceQueue.take();
    }

    @Override
    public void run() {
        int nextBlockNumber = 0;
        try {
            nextBlockNumber = BlockchainSynchronization.queue.take();

            LOGGER.debug("Thread {} starts to sync block {}.", uuid, nextBlockNumber);
            int newAccountsInThisBlock = 0;
            List<AppliedOperation> transactionsInBlock = apiWrapper.getOpsInBlock(nextBlockNumber, false);
            SignedBlockWithInfo currentBlock = apiWrapper.getBlock(nextBlockNumber);
            
            TransactionsPerBlock transactionsPerBlock = new TransactionsPerBlock();
            transactionsPerBlock.setBlockNumber(nextBlockNumber);
            transactionsPerBlock.setBlockCreationDate(currentBlock.getTimestamp().getDateTimeAsDate());
            transactionsPerBlock.setNumberOfTransactions(currentBlock.getTransactions().size());

            
            transactionsPerBlock.setNumberOfOperations(transactionsInBlock.size());

            transactionsPerBlockDAO.persist(transactionsPerBlock);

            // Remove blocks older than this - 28800

            for (TransactionsPerBlock oldEntries : transactionsPerBlockDAO.findOlderThan(nextBlockNumber - 28800)) {
                transactionsPerBlockDAO.delete(oldEntries);
            }

            LOGGER.debug("Thread {} has finished succesful and found {} new accounts in his block.", uuid,
                    newAccountsInThisBlock);
        } catch (InterruptedException |

                SteemCommunicationException e) {
            LOGGER.warn("Thread {} has not finished succesful for block {}.", uuid, nextBlockNumber, e);
        } catch (Exception e) {
            LOGGER.error("Thread {} died for an unexpected reason when processing block {}.", uuid, nextBlockNumber, e);
        } finally {
            try {
                BlockchainSynchronization.resourceQueue.put(apiWrapper);
            } catch (InterruptedException e) {
                LOGGER.error("Thread {} could not pass back SteemJ instance when processing block {}.", uuid,
                        nextBlockNumber, e);
            }
        }
    }

}
