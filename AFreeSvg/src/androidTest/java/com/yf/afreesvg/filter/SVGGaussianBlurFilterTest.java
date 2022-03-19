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

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yf.afreesvg.TestConstant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(AndroidJUnit4.class)
public class SVGGaussianBlurFilterTest extends SVGFilterBaseTest {


    @Test
    public void convertToSVGElement() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);
        Element element = filter.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("filter", element.getTagName());
        assertNotNull(element.getFirstChild());
        assertEquals(0, Float.valueOf(element.getAttribute("width")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("height")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("x")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("y")), TestConstant.DELTA_F);

    }

    @Test
    public void effectConvertToSVGElement() {
        SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect effect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(0.5f, 0.5f);

        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);

        assertEquals("feGaussianBlur", element.getTagName());

        assertEquals("" + canvas.getGeomDoubleConverter().apply(0.5f) +
                        "," + canvas.getGeomDoubleConverter().apply(0.5f)
                , element.getAttribute("stdDeviation"));
        effectBaseTest(effect);

    }


    @Test
    public void getStdDeviationX() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationX(5f);
        assertEquals(5, filter.getStdDeviationX(), TestConstant.DELTA_F);
    }

    @Test
    public void setStdDeviationX() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationX(10f);
        assertEquals(10, filter.getStdDeviationX(), TestConstant.DELTA_F);
    }

    @Test
    public void getStdDeviationY() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationY(5f);
        assertEquals(5, filter.getStdDeviationY(), TestConstant.DELTA_F);
    }

    @Test
    public void setStdDeviationY() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationY(10f);
        assertEquals(10, filter.getStdDeviationY(), TestConstant.DELTA_F);
    }
}