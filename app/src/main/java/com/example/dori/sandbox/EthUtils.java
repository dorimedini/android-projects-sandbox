package com.example.dori.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;

/**
 * Created by dori on 2/23/2018.
 */

/**
 * FIXME Hopefully we'll be able to get rid of this class as web3j develops
 */
class EthUtils {

    private static final Logger log = LoggerFactory.getLogger(EthUtils.class);
    static final BigInteger INVALID_WEI_VALUE = BigInteger.valueOf(-1);

    /**
     * Converts a string into an Ether value.
     *
     * <p>Syntax is "XXX[ YYY]" where XXX is an integer and YYY is an optional
     * string describing the units used.
     *
     * @param  str The string to be parsed as an Ether value.
     * @return BigInteger The input value, in wei; or {@see INVALID_WEI_VALUE} on error.
     */
    static BigInteger strToWei(String str) {

        /** Mapping of text units to multipliers.
         *  If X is a value in unit Y, then X*weiPerUnit.get(Y) is the value of X in wei. */
        Map<String, BigInteger> weiPerUnit = new HashMap<>();
        BigInteger ten = BigInteger.valueOf(10);
        BigInteger e18 = ten.pow(18);
        BigInteger e15 = ten.pow(15);
        BigInteger e12 = ten.pow(12);
        BigInteger e9 = ten.pow(9);
        BigInteger e6 = ten.pow(6);
        BigInteger e3 = ten.pow(3);
        weiPerUnit.put("eth", e18);
        weiPerUnit.put("ether", e18);
        weiPerUnit.put("finney", e15);
        weiPerUnit.put("szabo", e12);
        weiPerUnit.put("microether", e12);
        weiPerUnit.put("micro", e12);
        weiPerUnit.put("gwei", e9);
        weiPerUnit.put("shannon", e9);
        weiPerUnit.put("nanoether", e9);
        weiPerUnit.put("nano", e9);
        weiPerUnit.put("mwei", e6);
        weiPerUnit.put("babbage", e6);
        weiPerUnit.put("picoether", e6);
        weiPerUnit.put("kwei", e3);
        weiPerUnit.put("ada", e3);
        weiPerUnit.put("femtoether", e3);
        weiPerUnit.put("wei", BigInteger.valueOf(1));
        String valid_units = String.join("|", new ArrayList<>(weiPerUnit.keySet()));


        String[] text_parts = str.toLowerCase().trim().split(" ");
        if (text_parts.length > 2) {
            log.error("Invalid input string '" + str + "', must be 'XXX[ YYY]' " +
                    "where XXX is an integer and YYY is an (optional) unit name.");
            return INVALID_WEI_VALUE;
        }
        if (text_parts[0].equals("")) {
            log.error("No text entered!");
            return INVALID_WEI_VALUE;
        }

        // Get value, if there's no units assume wei (for cheapest human error)
        BigInteger offer_value;
        String regex_integer = "[1-9]\\d*";
        String regex_decimal = regex_integer + "\\.?\\d*";
        if (!text_parts[0].matches(regex_integer)) {
            if (text_parts[0].matches(regex_decimal)) {
                log.error("No decimal values allowed; use smaller units (one of <" + valid_units + ">)");
                return INVALID_WEI_VALUE;
            }
            log.error("Invalid integer value '" + text_parts[0] + "'");
            return INVALID_WEI_VALUE;
        }
        try {
            offer_value = new BigInteger(text_parts[0]);
        } catch (Exception e) {
            log.error("Caught exception: " + e.toString());
            return INVALID_WEI_VALUE;
        }
        if (offer_value.signum() < 0) {
            log.error("Input value cannot be negative! Got '" + offer_value.toString() + "'");
            return INVALID_WEI_VALUE;
        }
        if (text_parts.length == 1) { // No units
            return offer_value;
        }

        // Convert by unit
        String units = text_parts[1];
        boolean valid_unit = false;
        for(String key: weiPerUnit.keySet()) {
            if (key.equals(units)) {
                offer_value = offer_value.multiply(weiPerUnit.get(units));
                valid_unit = true;
                break;
            }
        }
        if (!valid_unit) {
            log.error("Unknown Ethereum unit '" + units + "', must be one of <" + valid_units + ">");
            return INVALID_WEI_VALUE;
        }
        return offer_value;
    }
}
