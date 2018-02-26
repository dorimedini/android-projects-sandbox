package com.example.dori.sandbox;

import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dori.SecondPriceAuction;
import com.example.dori.PrintableTransactionReceipt;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.utils.Convert;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        try {
            // FIXME At time of writing, loadCredentials() causes OOM exceptions and StackOverflow
            // FIXME has my back on this I swear to god.
            // FIXME Current workararound is to load the private key directly - this is a sandbox
            // FIXME app so fuck it...
            //log.info("Fetching wallet...");
            //String path_to_wallet = getPathToWallet();
            //if (path_to_wallet == null || path_to_wallet.isEmpty()) {
            //    log.error("Got empty path to the wallet, can't construct activity...");
            //    return;
            //}
            //log.info("Got path to wallet: " + path_to_wallet + "\nLoading credentials...");
            // FIXME credentials = WalletUtils.loadCredentials("Xgnebvkho4", path_to_wallet);
            credentials = Credentials.create("88a774a7dbd5591191164ef441c4c71267261a69bf92f17a530cb068bd5b1fd0");
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
        contract = SecondPriceAuction.load(
                contractAddress,
                web3j, credentials,
                ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

        /* Init values */
        showBids(findViewById(R.id.refresh_bids_button));
        updateBalance(findViewById(R.id.my_balance_button));
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

    /** TODO Place hint in red under edittext view */
    private void showOfferHint(String hint) {

        log.warn(hint);
    }
    /** TODO Remove hint text */
    private void hideOfferHint() {

    }

    /** Called when user makes an offer */
    // FIXME Take the unit conversion method to a separate module, it's useful for now
    // FIXME Also, make the callback lambdas actual handlers, so we can gray out the offer button
    // FIXME while transaction is in the air
    public void offer(View view) {
        hideOfferHint();
        if (!got_credentials) {
            showOfferHint("Credentials failed to load, can't complete call to offer()");
            return;
        }
        // Parse the string and check if it's a valid Ether value - starts with a decimal
        // number and an optional unit (eth/ether, finney, gwei, wei).
        // If no unit is given, assume wei (cheapest option if user makes a mistake).
        // Parse the string to get a value in wei, without parsing the number as a decimal
        // number (to avoid floating point errors).
        String offer_text = ((EditText)findViewById(R.id.offer_edittext)).getText().toString();
        BigInteger offer_value = EthUtils.strToWei(offer_text);
        if (offer_value.equals(EthUtils.INVALID_WEI_VALUE)) {
            log.error("strToWei() returned bad int value!");
            return;
        }

        // Check if the current top bidder bid less than the new offer
        Supplier<PrintableTransactionReceipt> bid_supplier = () -> {
            try {
                return new PrintableTransactionReceipt(contract.offer(offer_value).send());
            } catch(Exception e) {
                showOfferHint(e.toString());
            }
            return new PrintableTransactionReceipt();
        };
        Consumer<PrintableTransactionReceipt> bid_consumer = (tx) -> {
            try {
                log.info("Result of offer transaction:\n" + tx.toString());
                new Handler(Looper.getMainLooper()).post(() -> {
                    showBidsAux();
                    enableOffer();
                });
            } catch(Exception e) {
                log.error(e.toString());
            }
        };
        showOfferHint("Sending transaction...");
        try {
            CompletableFuture.supplyAsync(bid_supplier).thenAccept(bid_consumer);
        } catch(Exception e) {
            log.error(e.toString());
        }
    }

    /** Call to see current user balance */
    public void updateBalance(View view) {
        try {
            EthGetBalance ethGetBalance = web3j
                    .ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                    .sendAsync()
                    .get();
            BigInteger wei = ethGetBalance.getBalance();
            log.info("Address '" + credentials.getAddress() + "' has got a balance of " + wei.toString() + " wei");
            ((TextView)findViewById(R.id.my_balance_textview)).setText(wei.toString() + " wei");
        } catch(Exception e) {
            log.error(e.toString());
        }
    }

    /** Use these to fetch the bids */
    private Supplier<BigInteger> getBidSupplier(int index) {
        assert(index == 0 || index == 1);
        return () -> {
            try {
                return contract.bids(BigInteger.valueOf(index)).send();
            } catch(Exception e) {
                showOfferHint(e.toString());
            }
            return BigInteger.valueOf(-1);
        };
    }

    /** Called when user requests to see the current bids */
    public void showBids(View view) { showBidsAux(); }
    private void showBidsAux() {
        if (!got_credentials) {
            log.error("Credentials failed to load, can't complete call to showBids()");
            return;
        }
        // After we get the result we need to perform UI changes. The callback will be in thread
        // context, so pass the Consumer logic a handler (see usage in setBidText)
        Handler handler = new Handler();
        CompletableFuture.supplyAsync(getBidSupplier(0))
                .thenAccept((x) -> { setBidText(x, true, handler); });
        CompletableFuture.supplyAsync(getBidSupplier(1))
                .thenAccept((x) -> { setBidText(x, false, handler); });
    }
    private void setBidText(BigInteger wei, boolean target_winner, Handler handler) {
        log.info("Got " + (target_winner ? "winner" : "runner-up") + " bid: " + wei.toString());
        TextView tv;
        if (target_winner) {
            tv = findViewById(R.id.winner_bid_textview);
        }
        else {
            tv = findViewById(R.id.runner_up_bid_textview);
        }
        // Setting text requires a UI change. Use the handler (we're in thread context)
        handler.post(() -> {
            String text = Convert.fromWei(wei.toString(), Convert.Unit.FINNEY).toString() + " finney";
            tv.setText(text);
        });
    }
}
