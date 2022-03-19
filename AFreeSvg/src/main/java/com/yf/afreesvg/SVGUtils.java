/* ===================================================
 * JFreeSVG : an SVG library for the Java(tm) platform
 * ===================================================
 *
 * (C)opyright 2013-2021, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.jfree.org/jfreesvg/index.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.]
 *
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * JFreeSVG home page:
 *
 * http://www.jfree.org/jfreesvg
 *
 */

package com.yf.afreesvg;


import com.yf.afreesvg.util.DoubleConverter;
import com.yf.afreesvg.util.DoubleFunction;
import com.yf.afreesvg.util.RyuDouble;

/**
 * Utility methods related to the {@link SVGCanvas} implementation.
 */
public class SVGUtils {

    private SVGUtils() {
        // no need to instantiate this
    }

    /**
     * Returns a string representing the specified double value.  Internally
     * this method is using the code from: https://github.com/ulfjack/ryu which
     * is optimised for speed.
     *
     * @param d the value.
     * @return A string representation of the double.
     * @since 0.0.1
     */
    public static String doubleToString(double d) {
        return RyuDouble.doubleToString(d);
    }

    /**
     * Returns a double-to-string function that limits the output to a
     * specific number of decimal places (in the range 1 to 10).
     *
     * @param dp the decimal places (required in the range 1 to 10).
     * @return The converter.
     * @since 0.0.1
     */
    public static DoubleFunction<String> createDoubleConverter(int dp) {
        return new DoubleConverter(dp);
    }

    /**
     * Returns the SVG RGB color string for the specified color.
     *
     * @param color the color ({@code null} not permitted).
     * @return The SVG RGB color string.
     */

    public static String rgbColorStr(long color) {
        StringBuilder b = new StringBuilder("rgb(");
        b.append(((color >> 16) & 0xff)).append(",").append(((color >> 8) & 0xff)).append(",")
                .append((color) & 0xff).append(")");
        return b.toString();
    }

    public static int colorAlpha(long color) {
        return (int) (color >> 24 & 0xff);
    }

    public static float getColorAlpha(int alpha) {
        return alpha / 255.0f;
    }

    public static boolean isTextEmpty(String str) {
        return str == null || str.equals("");
    }

}
