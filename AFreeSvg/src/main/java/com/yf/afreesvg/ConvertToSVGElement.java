package com.yf.afreesvg;

import com.yf.afreesvg.util.DoubleFunction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The interface to convert dom element
 *
 * @author iffly
 * @since 0.0.1
 */
public interface ConvertToSVGElement {
    /**
     * Convert class to dom element
     *
     * @param canvas   The svg canvas,{@link SVGCanvas}
     * @param document The dom document,use it to create dom element {@link Document}
     * @param convert  The double convert,convert double to string
     * @return The dom element
     * @since 0.0.1
     */
    public Element convertToSVGElement(SVGCanvas canvas, Document document, DoubleFunction<String> convert);
}
