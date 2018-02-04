package com.example.dori.sandbox;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dori.SecondPriceAuction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
//import org.web3j.sample.contracts.generated.Greeter;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    public static boolean got_credentials = false;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);
    private static final String contractAddress = "0x356a73cd1e3511511f87159a4e072f28ae0524be";
    private static Web3j web3j;
    private static Credentials credentials;
    private static SecondPriceAuction contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log.info("In onCreate()! Current working directory is " + getApplicationInfo().dataDir);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log.info("Connecting smart contract at address " + contractAddress);
        web3j = Web3jFactory.build(new HttpService(
                "https://ropsten.infura.io/ku5IkS4NTM4PDhwmc5iI"));
        log.info("Fetching wallet...");
        String path_to_wallet = getPathToWallet();
        if (path_to_wallet == null || path_to_wallet.isEmpty()) {
            log.error("Got empty path to the wallet, can't construct activity...");
            return;
        }
        log.info("Got path to wallet: " + path_to_wallet + "\nLoading credentials...");
        try {
            credentials = WalletUtils.loadCredentials("Xgnebvkho4", path_to_wallet);
        }
        catch (Exception e) {
            log.error(e.toString());
            return;
        }
        log.info("Got the following credentials:\n" + credentials.toString() + "\n" +
                "Calling SecondPriceAuction.load() with the following parameters:\n" +
                "Contract address:\t" + contractAddress + "\n" +
                "Gas price:\t\t" + ManagedTransaction.GAS_PRICE.toString() + "\n" +
                "Gas limit:\t\t" + Contract.GAS_LIMIT.toString());
        got_credentials = true;
/*        contract = SecondPriceAuction.load(
                contractAddress,
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);*/
    }

    private String getPathToWallet() {
        String target = getCacheDir()+"/SecondPriceWallet.json";
        File f = new File(target);
        if (!f.exists()) {
            try {
                InputStream is = getAssets().open("wallets/UTC--2018-02-04T16-57-03.590000000Z--945707bf413d65ffa8ae0856936fa7986303e5d3.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();
            } catch (Exception e) {
                log.error(e.toString());
                return "";
            }
        }
        return target;
    }

    /** FIXME Called when user makes an offer */
    public void offer(View view) {
        if (!got_credentials) {
            log.error("Credentials failed to load, can't complete call to offer()");
            return;
        }
        // Start by running an async call to showBids(), so that after the string parsing we can
        // check if the user bid meets the minimum bid.
        //
        // Next, Parse the string and check if it's a valid Ether value - starts with a decimal
        // number and an optional unit (eth/ether, finney, gwei, wei).
        // If no unit is given, assume wei (cheapest option if user makes a mistake).
        // Parse the string to get a value in wei, without parsing the number as a decimal
        // number (to avoid floating point errors).
        //
        // Wait for showBids() to return. If the minimum isn't met - return false. If it is, send
        // the transaction (async, then call the callback). Return true if the transaction was
        // sent successfully.
    }

    /** FIXME When offer() transaction returns, this should be the callback.
     *  See Web3j documentation for implementation requirements... */
    private void offerCB() {

    }

    /** Called when user requests to see the current bids */
    public void showBids(View view) {
        if (!got_credentials) {
            log.error("Credentials failed to load, can't complete call to showBids()");
            return;
        }

        BigInteger winner_bid;
        BigInteger runner_up_bid;
        log.info("Fetching bids...");
        try {
            winner_bid = contract.bids(new BigInteger("0")).send();
            runner_up_bid = contract.bids(new BigInteger("1")).send();
        }
        catch(Exception e) {
            log.error("Caught exception when calling bids(): " + e.toString());
            return;
        }
        log.info("Done, got winner="+winner_bid.toString()+" and runner-up=" + runner_up_bid.toString());
        TextView winner_textview = findViewById(R.id.winner_bid_textview);
        TextView runner_up_textview = findViewById(R.id.runner_up_bid_textview);
        winner_textview.setText(winner_bid.toString());
        runner_up_textview.setText(runner_up_bid.toString());
    }
}
