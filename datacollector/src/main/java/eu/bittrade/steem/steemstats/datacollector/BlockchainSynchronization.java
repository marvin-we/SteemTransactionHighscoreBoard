package eu.bittrade.steem.steemstats.datacollector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import eu.bittrade.libs.steemj.SteemApiWrapper;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.steem.steemstats.datacollector.dao.RecordDaoImpl;
import eu.bittrade.steem.steemstats.datacollector.dao.TransactionsDaoImpl;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockchainSynchronization {
    private static final Logger LOGGER = LogManager.getLogger(BlockchainSynchronization.class);
    private static final int NUMBER_OF_THREADS = Integer.parseInt(System.getProperty("NUMBER_OF_THREADS", "5"));
    private static final String STEEM_WS_ENDPOINT = System.getProperty("STEEM_WS_ENDPOINT", "wss://this.piston.rocks");

    private static boolean shutdownSignalReceived = false;

    private SteemJConfig wrapperConfig = SteemJConfig.getInstance();

    private static ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    protected static BlockingQueue<SteemApiWrapper> resourceQueue = new LinkedBlockingQueue<>(NUMBER_OF_THREADS);

    protected static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(28900);
    public static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public BlockchainSynchronization() throws SteemCommunicationException {
        // Disable Timeout
        wrapperConfig.setTimeout(0);
        try {
            wrapperConfig.setWebsocketEndpointURI(new URI(STEEM_WS_ENDPOINT));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("The given URI is not valid.", e);
        }
        wrapperConfig.setSslVerificationDisabled(true);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            resourceQueue.add(new SteemApiWrapper());
        }
    }

    public void synchronizeWithDatabase() throws SteemCommunicationException {

        Timer recordTimer = new Timer("CollectRecords", true);
        recordTimer.schedule(new RecordCollector(new RecordDaoImpl(), new TransactionsDaoImpl()), 1000L, 1000L);

        Timer timer = new Timer("SynchronizeBlockchainWithDatabase", true);
        timer.schedule(new QueueCrammer(new SteemApiWrapper(), new TransactionsDaoImpl()), 2000L, 2000L);

        while (!shutdownSignalReceived) {
            if (!queue.isEmpty()) {
                try {
                    executorService.execute(new QueueWorker(new TransactionsDaoImpl()));
                } catch (InterruptedException e) {
                    LOGGER.warn("An executor thread has been interrupted.", e);
                    // Restore interrupted state...
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                BlockchainSynchronization.shutdownSignalReceived = true;
            }
        });

        try {
            BlockchainSynchronization blockchainSynchronization = new BlockchainSynchronization();
            blockchainSynchronization.synchronizeWithDatabase();
        } catch (SteemCommunicationException e) {
            LOGGER.error("An error has occured.", e);
        }
    }
}
