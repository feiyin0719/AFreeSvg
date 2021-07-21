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


import com.yf.afreesvg.util.Args;
import com.yf.afreesvg.util.DoubleConverter;
import com.yf.afreesvg.util.DoubleFunction;
import com.yf.afreesvg.util.RyuDouble;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * Utility methods related to the {@link SVGCanvas} implementation.
 */
public class SVGUtils {

    private SVGUtils() {
        // no need to instantiate this
    }

    /**
     * Writes a file containing the SVG element.
     *
     * @param file       the file ({@code null} not permitted).
     * @param svgElement the SVG element ({@code null} not permitted).
     * @throws IOException if there is an I/O problem.
     * @since 0.0.1
     */
    public static void writeToSVG(File file, String svgElement)
            throws IOException {
        writeToSVG(file, svgElement, false);
    }

    /**
     * Writes a file containing the SVG element.
     *
     * @param file       the file ({@code null} not permitted).
     * @param svgElement the SVG element ({@code null} not permitted).
     * @param zip        compress the output.
     * @throws IOException if there is an I/O problem.
     * @since 0.0.1
     */
    public static void writeToSVG(File file, String svgElement, boolean zip)
            throws IOException {
        BufferedWriter writer = null;
        try {
            OutputStream os = new FileOutputStream(file);
            if (zip) {
                os = new GZIPOutputStream(os);
            }
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            writer = new BufferedWriter(osw);
            writer.write("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
            writer.write(svgElement + "\n");
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Writes an HTML file containing an SVG element.
     *
     * @param file       the file.
     * @param title      the title.
     * @param svgElement the SVG element.
     * @throws IOException if there is an I/O problem.
     */
    public static void writeToHTML(File file, String title, String svgElement)
            throws IOException {
        BufferedWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            writer = new BufferedWriter(osw);
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>" + title + "</title>\n");
            writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write(svgElement + "\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SVGUtils.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
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
