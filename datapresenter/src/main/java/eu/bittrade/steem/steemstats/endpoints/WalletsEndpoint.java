package eu.bittrade.steem.steemstats.endpoints;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eu.bittrade.steem.steemstats.dao.RecordDaoImpl;
import eu.bittrade.steem.steemstats.dao.TransactionsDaoImpl;
import eu.bittrade.steem.steemstats.entities.TransactionsPerBlock;
import eu.bittrade.steem.userstats.dto.SteemStatsDTO;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@Path("/wallets")
public class WalletsEndpoint {
    private final static Integer BLOCKS_PER_HOUR = 1200;

    @Inject
    private RecordDaoImpl recordDaoImpl;

    @Inject
    private TransactionsDaoImpl transactionsDaoImpl;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response numberOfWalletsInDatabase() {
        TransactionsPerBlock latestTransaction = transactionsDaoImpl.findNewest();

        SteemStatsDTO steemStatsDTO = new SteemStatsDTO();
        steemStatsDTO.setRecords(recordDaoImpl.findAll());
        steemStatsDTO.setOperationsLastDay(transactionsDaoImpl.countOperations());
        steemStatsDTO.setTransactionsLastDay(transactionsDaoImpl.countTransactions());
        steemStatsDTO.setOperationsLastHour(transactionsDaoImpl.countOperationsBetween(
                latestTransaction.getBlockNumber() - BLOCKS_PER_HOUR, latestTransaction.getBlockNumber()));
        steemStatsDTO.setTransactionsLastHour(transactionsDaoImpl.countTransactionsBetween(
                latestTransaction.getBlockNumber() - BLOCKS_PER_HOUR, latestTransaction.getBlockNumber()));
        return Response.status(Status.OK).entity(steemStatsDTO).build();
    }
}
