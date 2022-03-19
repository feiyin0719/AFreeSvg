/*
 * Copyright (c) 2022.  by iffly Limited.  All rights reserved.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 */

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
