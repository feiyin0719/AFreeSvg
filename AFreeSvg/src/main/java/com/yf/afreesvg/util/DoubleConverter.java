package com.yf.afreesvg.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


/**
 * A function that converts double values to a string representation with 
 * a maximum number of decimal places.
 */
public class DoubleConverter implements DoubleFunction<String> {

    private final DecimalFormat formatter;
    
    /**
     * Creates a new function that converts double values to strings with
     * the maximum number of decimal places as specified.
     * 
     * @param dp  the max decimal places (in the range 1 to 10). 
     */    
    public DoubleConverter(int dp) {
        Args.requireInRange(dp, "dp", 1, 10);
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.US);
        this.formatter = new DecimalFormat("0." + "##########".substring(0, dp), dfs);
    }

    /**
     * Returns a string representation of the specified value.
     * 
     * @param value  the value.
     * 
     * @return A string representation of the specified value. 
     */
    @Override
    public String apply(double value) {
        return this.formatter.format(value);
    }

}
