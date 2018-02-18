package com.example.dori;

import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * Created by dori on 2/14/2018.
 */

public class PrintableTransactionReceipt {
    private TransactionReceipt tx;

    public PrintableTransactionReceipt() {}

    public PrintableTransactionReceipt(TransactionReceipt _tx) {
        tx = _tx;
    }

    public String toString() {
        int min_width = 25;
        int min_log_width = 15;
        String ret = "======================== START TX " + tx.getTransactionHash() + "========================\n";
        ret += String.format("%1$-"+min_width+"s", "Index: ") + tx.getTransactionIndex() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Block Hash: ") + tx.getBlockHash() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Block Number: ") + tx.getBlockNumber() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Cumulative Gas used: ") + tx.getCumulativeGasUsed() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Gas Used: ") + tx.getGasUsed() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Contract Address: ") + tx.getContractAddress() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Root: ") + tx.getRoot() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Status: ") + tx.getStatus() + "\n";
        ret += String.format("%1$-"+min_width+"s", "From: ") + tx.getFrom() + "\n";
        ret += String.format("%1$-"+min_width+"s", "To: ") + tx.getTo() + "\n";
        ret += String.format("%1$-"+min_width+"s", "Logs Bloom: ") + tx.getLogsBloom() + "\n";
        ret += "Logs:\n";
        for (Log log: tx.getLogs()) {
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Removed: ") + (log.isRemoved() ? "TRUE" : "FALSE") + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Log Index: ") + log.getLogIndex() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "TX Index: ") + log.getTransactionIndex() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "TX Hash: ") + log.getTransactionHash() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Block Hash: ") + log.getBlockHash() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Block Number: ") + log.getBlockNumber() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Address: ") + log.getAddress() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Data: ") + log.getData() + "\n";
            ret += "\t" + String.format("%1$-"+min_log_width+"s", "Type: ") + log.getType() + "\n";
            ret += "\tTopics:\n";
            for (String topic: log.getTopics()) {
                ret += "\t\t" + topic + "\n";
            }
        }
        ret += "========================= END TX " + tx.getTransactionHash() + "=========================";
        return ret;
    }
}
