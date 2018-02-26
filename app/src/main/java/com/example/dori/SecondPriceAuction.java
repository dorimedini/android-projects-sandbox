package com.example.dori;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

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
    private static final String BINARY = "0x6060604052610e1060015567016345785d8a0000600355341561002157600080fd5b604051604080610e4783398101604052808051906020019091908051906020019091905050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600081111561009757806001819055505b60008211156100a857816003819055505b42600154016002819055505050610d83806100c46000396000f3006060604052600436106100a4576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806304ddf1e7146100a957806309ad1063146100fe57806329dcb0cf146101135780633a2bcc701461013c57806341c0e1b5146101655780634423c5f11461017a57806393386034146101b1578063c028df06146101da578063dfbf53ae146101e4578063e782f53414610239575b600080fd5b34156100b457600080fd5b6100bc61024e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561010957600080fd5b610111610274565b005b341561011e57600080fd5b6101266104ea565b6040518082815260200191505060405180910390f35b341561014757600080fd5b61014f6104f0565b6040518082815260200191505060405180910390f35b341561017057600080fd5b6101786105d8565b005b341561018557600080fd5b61019b600480803590602001909190505061071d565b6040518082815260200191505060405180910390f35b34156101bc57600080fd5b6101c4610737565b6040518082815260200191505060405180910390f35b6101e261073d565b005b34156101ef57600080fd5b6101f7610c1c565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561024457600080fd5b61024c610c42565b005b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610335577fcc454678efc57c2399c49068b6bc8b388888c98f08f44bc3631557a9aab5b92033604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a16104e6565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156103a7577fdff55bfb838deddf0a1fee4b362405bfd37e793f686b706c3420d4dbfdaccbc160405160405180910390a16104e6565b600660016002811015156103b757fe5b0154600660006002811015156103c957fe5b0154019150600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060006006600060028110151561040557fe5b018190555060006006600160028110151561041c57fe5b01819055506000600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f1935050505015156104e557600080fd5b5b5050565b60025481565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415610561576006600060028110151561055857fe5b015490506105d5565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614156105d057600660016002811015156105c757fe5b015490506105d5565b600090505b90565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610696577fcc454678efc57c2399c49068b6bc8b388888c98f08f44bc3631557a9aab5b92033604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a161071b565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156106e1576106e0610274565b5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16ff5b565b60068160028110151561072c57fe5b016000915090505481565b60035481565b6000806002544211156107da576000341115610794573373ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f19350505050151561079357600080fd5b5b7f5ee740cda97449e9acdb38a38b0a69b167dd0da886f96d9d2f82ba4ff37abd7342600254604051808381526020018281526020019250505060405180910390a1610c18565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561090457600034111561087a573373ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f19350505050151561087957600080fd5b5b7fa0a7d995ada126b12b2dec726efcf365b499deca2f960f1bf172c409c5f03f4c600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a1610c18565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415801561095e57506006600060028110151561095857fe5b01543411155b15610a025760003411156109ad573373ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f1935050505015156109ac57600080fd5b5b7f7e4b4593ece4745a70b83b83be7be572b479361d078db0746e0fdade350a9ce034600660006002811015156109df57fe5b0154604051808381526020018281526020019250505060405180910390a1610c18565b600354341015610a9c576000341115610a56573373ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f193505050501515610a5557600080fd5b5b7ff84871d9e7da9f27bfc2afae621a07f59d72746e543fc04bc4493ad5ff4c427d34600354604051808381526020018281526020019250505060405180910390a1610c18565b60066001600281101515610aac57fe5b01549150600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555033600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060066000600281101515610b8957fe5b015460066001600281101515610b9b57fe5b01819055503460066000600281101515610bb157fe5b018190555060008173ffffffffffffffffffffffffffffffffffffffff16141515610c17578073ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f193505050501515610c1657600080fd5b5b5b5050565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b3373ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610d00577fcc454678efc57c2399c49068b6bc8b388888c98f08f44bc3631557a9aab5b92033604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390a1610d55565b600254421115610d54577f5ee740cda97449e9acdb38a38b0a69b167dd0da886f96d9d2f82ba4ff37abd7342600254604051808381526020018281526020019250505060405180910390a1610d53610274565b5b5b5600a165627a7a723058200da686093a0a7da487920f2e9dc8a05f951fc1e92437d047383813eef87d5b940029";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
    }

    protected SecondPriceAuction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SecondPriceAuction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<OutOfTimeEventEventResponse> getOutOfTimeEventEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("OutOfTimeEvent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<OutOfTimeEventEventResponse> responses = new ArrayList<OutOfTimeEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            OutOfTimeEventEventResponse typedResponse = new OutOfTimeEventEventResponse();
            typedResponse.now_seconds = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.deadline_seconds = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OutOfTimeEventEventResponse> outOfTimeEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("OutOfTimeEvent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, OutOfTimeEventEventResponse>() {
            @Override
            public OutOfTimeEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                OutOfTimeEventEventResponse typedResponse = new OutOfTimeEventEventResponse();
                typedResponse.now_seconds = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.deadline_seconds = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<NoPermittionEventResponse> getNoPermittionEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("NoPermittion", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<NoPermittionEventResponse> responses = new ArrayList<NoPermittionEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            NoPermittionEventResponse typedResponse = new NoPermittionEventResponse();
            typedResponse.psudo_user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NoPermittionEventResponse> noPermittionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("NoPermittion", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, NoPermittionEventResponse>() {
            @Override
            public NoPermittionEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                NoPermittionEventResponse typedResponse = new NoPermittionEventResponse();
                typedResponse.psudo_user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<DoubleBidEventEventResponse> getDoubleBidEventEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("DoubleBidEvent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<DoubleBidEventEventResponse> responses = new ArrayList<DoubleBidEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            DoubleBidEventEventResponse typedResponse = new DoubleBidEventEventResponse();
            typedResponse.cheater = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DoubleBidEventEventResponse> doubleBidEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("DoubleBidEvent", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, DoubleBidEventEventResponse>() {
            @Override
            public DoubleBidEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                DoubleBidEventEventResponse typedResponse = new DoubleBidEventEventResponse();
                typedResponse.cheater = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public List<TooLowEventResponse> getTooLowEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("TooLow", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<TooLowEventResponse> responses = new ArrayList<TooLowEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            TooLowEventResponse typedResponse = new TooLowEventResponse();
            typedResponse.bid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.need_to_beat = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TooLowEventResponse> tooLowEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("TooLow", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TooLowEventResponse>() {
            @Override
            public TooLowEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TooLowEventResponse typedResponse = new TooLowEventResponse();
                typedResponse.bid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.need_to_beat = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<DoesntReachMinimumEventResponse> getDoesntReachMinimumEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("DoesntReachMinimum", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<DoesntReachMinimumEventResponse> responses = new ArrayList<DoesntReachMinimumEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            DoesntReachMinimumEventResponse typedResponse = new DoesntReachMinimumEventResponse();
            typedResponse.bid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.min = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DoesntReachMinimumEventResponse> doesntReachMinimumEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("DoesntReachMinimum", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, DoesntReachMinimumEventResponse>() {
            @Override
            public DoesntReachMinimumEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                DoesntReachMinimumEventResponse typedResponse = new DoesntReachMinimumEventResponse();
                typedResponse.bid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.min = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<NoPlayersEventResponse> getNoPlayersEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("NoPlayers", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<NoPlayersEventResponse> responses = new ArrayList<NoPlayersEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            NoPlayersEventResponse typedResponse = new NoPlayersEventResponse();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NoPlayersEventResponse> noPlayersEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("NoPlayers", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, NoPlayersEventResponse>() {
            @Override
            public NoPlayersEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                NoPlayersEventResponse typedResponse = new NoPlayersEventResponse();
                return typedResponse;
            }
        });
    }

    public RemoteCall<String> runner_up() {
        Function function = new Function("runner_up", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> deadline() {
        Function function = new Function("deadline", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteCall<String> winner() {
        Function function = new Function("winner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<SecondPriceAuction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger min_bid, BigInteger time_limit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(min_bid), 
                new org.web3j.abi.datatypes.generated.Uint256(time_limit)));
        return deployRemoteCall(SecondPriceAuction.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<SecondPriceAuction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger min_bid, BigInteger time_limit) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(min_bid), 
                new org.web3j.abi.datatypes.generated.Uint256(time_limit)));
        return deployRemoteCall(SecondPriceAuction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteCall<TransactionReceipt> enforce_time() {
        Function function = new Function(
                "enforce_time", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> kill() {
        Function function = new Function(
                "kill", 
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

    public RemoteCall<TransactionReceipt> offer(BigInteger weiValue) {
        Function function = new Function(
                "offer", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> end_game() {
        Function function = new Function(
                "end_game", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static SecondPriceAuction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SecondPriceAuction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SecondPriceAuction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SecondPriceAuction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OutOfTimeEventEventResponse {
        public BigInteger now_seconds;

        public BigInteger deadline_seconds;
    }

    public static class NoPermittionEventResponse {
        public String psudo_user;
    }

    public static class DoubleBidEventEventResponse {
        public String cheater;
    }

    public static class TooLowEventResponse {
        public BigInteger bid;

        public BigInteger need_to_beat;
    }

    public static class DoesntReachMinimumEventResponse {
        public BigInteger bid;

        public BigInteger min;
    }

    public static class NoPlayersEventResponse {
    }
}
