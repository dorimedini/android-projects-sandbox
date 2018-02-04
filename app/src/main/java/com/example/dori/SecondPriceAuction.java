package com.example.dori;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class SecondPriceAuction extends Contract {
    private static final String BINARY = "606060405267016345785d8a0000600155341561001b57600080fd5b6040516020806103a88339810160405280805160008054600160a060020a03191633600160a060020a0316178155909250821115905061005b5760018190555b5061033d8061006b6000396000f3006060604052600436106100775763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166309ad1063811461007c5780633a2bcc701461009157806341c0e1b5146100b65780634423c5f1146100c957806393386034146100df578063c028df06146100f2575b600080fd5b341561008757600080fd5b61008f6100fa565b005b341561009c57600080fd5b6100a46101a5565b60405190815260200160405180910390f35b34156100c157600080fd5b61008f6101f4565b34156100d457600080fd5b6100a460043561021d565b34156100ea57600080fd5b6100a4610231565b61008f610237565b60008054819033600160a060020a0390811691161461011857600080fd5b600254600160a060020a0316151561012f57600080fd5b50506005805460048054600280546000938490559290945573ffffffffffffffffffffffffffffffffffffffff1980831690945560038054909416909355910190600160a060020a0316806108fc83150283604051600060405180830381858888f1935050505015156101a157600080fd5b5050565b60025460009033600160a060020a03908116911614156101cd57600460005b015490506101f1565b60035433600160a060020a03908116911614156101ed57600460016101c4565b5060005b90565b60005433600160a060020a0390811691161461020f57600080fd5b600054600160a060020a0316ff5b6004816002811061022a57fe5b0154905081565b60015481565b60025460009033600160a060020a039081169116141561025657600080fd5b600254600160a060020a03161561027a57600454341161027557600080fd5b610289565b60015434101561028957600080fd5b50600580546002805460038054600160a060020a0380841673ffffffffffffffffffffffffffffffffffffffff19928316179283905592163383161790925560048054909455349093559091161561030e57600354600160a060020a031681156108fc0282604051600060405180830381858888f19350505050151561030e57600080fd5b505600a165627a7a7230582037a93e3eacc7ddf07d332689c73ca9ee3d7916d75a8b98445c7dcceca2dc7c450029";

    protected SecondPriceAuction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SecondPriceAuction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> end_game() {
        Function function = new Function(
                "end_game", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> my_bid() {
        Function function = new Function("my_bid", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> kill() {
        Function function = new Function(
                "kill", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> bids(BigInteger param0) {
        Function function = new Function("bids", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> minimum_start_bid() {
        Function function = new Function("minimum_start_bid", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> offer(BigInteger weiValue) {
        Function function = new Function(
                "offer", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static RemoteCall<SecondPriceAuction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger min_bid) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(min_bid)));
        return deployRemoteCall(SecondPriceAuction.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<SecondPriceAuction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger min_bid) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(min_bid)));
        return deployRemoteCall(SecondPriceAuction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static SecondPriceAuction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SecondPriceAuction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SecondPriceAuction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SecondPriceAuction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
