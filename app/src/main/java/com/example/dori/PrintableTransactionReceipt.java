package com.example.dori;

import org.web3j.abi.EventValues;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

/**
 * Created by dori on 2/14/2018.
 */

// FIXME Commit 235297f to web3j offers built-in toString method for TransactionReceipt, making this
// FIXME class obsolete
public class PrintableTransactionReceipt {
    private TransactionReceipt tx;
    private SecondPriceAuction contract;

    public PrintableTransactionReceipt(SecondPriceAuction c) { contract = c; }

    public PrintableTransactionReceipt(SecondPriceAuction c, TransactionReceipt _tx) {
        contract = c; tx = _tx;
    }

    public String toString() {
        int min_width = 25;
        int min_log_width = 15;
        StringBuilder ret = new StringBuilder();
        ret.append("======================== START TX ").append(tx.getTransactionHash()).append("========================\n");
        ret.append(String.format("%1$-"+min_width+"s", "Index: ")).append(tx.getTransactionIndex()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Block Hash: ")).append(tx.getBlockHash()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Block Number: ")).append(tx.getBlockNumber()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Cumulative Gas used: ")).append(tx.getCumulativeGasUsed()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Gas Used: ")).append(tx.getGasUsed()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Contract Address: ")).append(tx.getContractAddress()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Root: ")).append(tx.getRoot()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Status: ")).append(tx.getStatus()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "From: ")).append(tx.getFrom()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "To: ")).append(tx.getTo()).append("\n");
        ret.append(String.format("%1$-"+min_width+"s", "Logs Bloom: ")).append(tx.getLogsBloom()).append("\n");

        ret.append("Events:\n");
        for (SecondPriceAuction.OutOfTimeEventEventResponse e: contract.getOutOfTimeEventEvents(tx)) {
            ret.append("\t\tOutOfTime: now=").append(e.now_seconds.toString()).append(", deadline=").append(e.deadline_seconds.toString()).append("\n");
        }
        for (SecondPriceAuction.NoPermittionEventResponse e: contract.getNoPermittionEvents(tx)) {
            ret.append("\t\tNoPermittion: attacker=").append(e.psudo_user).append("\n");
        }
        for (SecondPriceAuction.DoubleBidEventEventResponse e: contract.getDoubleBidEventEvents(tx)) {
            ret.append("\t\tDoubleBidEvent: cheater=").append(e.cheater).append("\n");
        }
        for (SecondPriceAuction.TooLowEventResponse e: contract.getTooLowEvents(tx)) {
            ret.append("\t\tTooLow: bid=").append(e.bid.toString()).append(", need_to_beat=").append(e.need_to_beat.toString()).append("\n");
        }
        for (SecondPriceAuction.DoesntReachMinimumEventResponse e : contract.getDoesntReachMinimumEvents(tx)) {
            ret.append("\t\tDoesntReachMinimum: bid=").append(e.bid.toString()).append(", min=").append(e.min.toString()).append("\n");
        }
        for (SecondPriceAuction.NoPlayersEventResponse e: contract.getNoPlayersEvents(tx)) {
            ret.append("\t\tNoPlayers").append("\n");
        }

        ret.append("Logs:\n");
        for (Log log: tx.getLogs()) {
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Removed: ")).append(log.isRemoved() ? "TRUE" : "FALSE").append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Log Index: ")).append(log.getLogIndex()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "TX Index: ")).append(log.getTransactionIndex()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "TX Hash: ")).append(log.getTransactionHash()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Block Hash: ")).append(log.getBlockHash()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Block Number: ")).append(log.getBlockNumber()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Address: ")).append(log.getAddress()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Data: ")).append(log.getData()).append("\n");
            ret.append("\t").append(String.format("%1$-"+min_log_width+"s", "Type: ")).append(log.getType()).append("\n");
            ret.append("\tTopics:\n");
            for (String topic: log.getTopics()) {
                ret.append("\t\t").append(topic).append("\n");
            }
        }
        ret.append("========================= END TX ").append(tx.getTransactionHash()).append("=========================");
        return ret.toString();
    }
}
