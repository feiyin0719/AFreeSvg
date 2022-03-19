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

package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGBaseTest;
import com.yf.afreesvg.TestConstant;

import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SVGFilterBaseTest extends SVGBaseTest {


    public void filterBaseTest(Element element) {
        assertNotNull(element);
        assertNotNull(element.getFirstChild());
        assertEquals(0, Float.valueOf(element.getAttribute("width")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("height")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("x")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("y")), TestConstant.DELTA_F);
    }

    public void effectBaseTest(SVGBaseFilter.SVGBaseFilterEffect effect) {
        effect.setIn("in");
        effect.setResult("result");
        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("in", element.getAttribute("in"));
        assertEquals("result", element.getAttribute("result"));

    }
}
