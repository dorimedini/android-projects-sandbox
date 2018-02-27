package com.example.dori.sandbox;

import android.graphics.Bitmap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * @param   str The string to be parsed as an Ether value.
     * @param   sb  On error, the message will be returned here.
     * @return BigInteger The input value, in wei; or {@see INVALID_WEI_VALUE} on error.
     */
    static BigInteger strToWei(String str, StringBuilder sb) {

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
            sb.append("Invalid input string '")
                    .append(str)
                    .append("', must be 'XXX[ YYY]' ")
                    .append("where XXX is an integer and YYY is an (optional) unit name.");
            return INVALID_WEI_VALUE;
        }
        if (text_parts[0].equals("")) {
            sb.append("No text entered!");
            return INVALID_WEI_VALUE;
        }

        // Get value, if there's no units assume wei (for cheapest human error)
        BigInteger offer_value;
        String regex_integer = "[1-9]\\d*";
        String regex_decimal = regex_integer + "\\.?\\d*";
        if (!text_parts[0].matches(regex_integer)) {
            if (text_parts[0].matches(regex_decimal)) {
                sb.append("No decimal values allowed; use smaller units (one of <")
                        .append(valid_units)
                        .append(">)");
                return INVALID_WEI_VALUE;
            }
            sb.append("Invalid integer value '")
                    .append(text_parts[0])
                    .append("'");
            return INVALID_WEI_VALUE;
        }
        try {
            offer_value = new BigInteger(text_parts[0]);
        } catch (Exception e) {
            sb.append("Caught exception: ")
                    .append(e.toString());
            return INVALID_WEI_VALUE;
        }
        if (offer_value.signum() < 0) {
            sb.append("Input value cannot be negative! Got '")
                    .append(offer_value.toString())
                    .append("'");
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
            sb.append("Unknown Ethereum unit '")
                    .append(units)
                    .append("', must be one of <")
                    .append(valid_units)
                    .append(">");
            return INVALID_WEI_VALUE;
        }
        return offer_value;
    }

    /**
     * Converts an address in the Ethereum space into an icon (the output of Bitmap.createBitmap()).
     *
     * <p>Assigns pixels to colors based on the value of the address.
     *
     * @param   str The address to parse. After removing the possible preceding '0x', there must be
     *              at least 40 characters in the input strrng.
     * @return  Bitmap
     */
    static Bitmap addrToBitmap(String addr) {
        // Setup.
        // Output dimensions are as follows: 5 pixels hieght, 25 pixels across divided into 5
        // squares.
        int height = 15;
        int total_squares = 5;
        int width = total_squares * height;
        int total_pixels = height * width;
        Bitmap default_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        // Sanity
        addr = addr.replaceFirst("^0x", "");
        if (addr.length() < 40) {
            log.error("Input string '" + addr + "' too short!");
            return default_bitmap;
        }
        addr = addr.substring(addr.length()-40, addr.length());

        // Parse first 40 characters and make sure they're hex values!
        BigInteger addr_bigint;
        try {
            addr_bigint = new BigInteger(addr, 16);
        } catch(NumberFormatException e) {
            log.error(e.toString());
            return default_bitmap;
        }

        // Convert to colors.
        BigInteger mod_value = BigInteger.valueOf(2);
        mod_value = mod_value.pow(32);
        int[] colors = new int[total_squares];
        for (int col=0; col < total_squares; ++col) {
            colors[col] = addr_bigint.mod(mod_value).intValue();
            addr_bigint = addr_bigint.divide(mod_value);
        }
        log.info("Got colors: " + colors.toString());

        // Convert to int array
        int[] pixels = new int[total_pixels];
        for (int row=0; row < height; ++row) {
            for (int col=0; col < width; ++col) {
                pixels[width * row + col] = colors[col / height];
            }
        }

        // That's it! create the bitmap
        try {
            return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.RGB_565);
        } catch (IllegalArgumentException e) {
            log.error(e.toString());
            return default_bitmap;
        }
    }
}
