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

package com.yf.afreesvg.shape;

import com.yf.afreesvg.TestConstant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class SVGCircleTest extends SVGShapeBaseTest {
    private float cx;
    private float cy;
    private float r;

    public SVGCircleTest(float cx, float cy, float r) {
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    @Parameterized.Parameters
    public static List<Object> getData() {
        return Arrays.asList(new Object[][]{
                {5f, 10f, 13f},
                {0f, 0f, 0f}
        });
    }

    @Test
    public void convertToSVGElement() {
        SVGCircle svgCircle = new SVGCircle(cx, cy, r);
        Assert.assertEquals(cx, svgCircle.getCx(), TestConstant.DELTA_F);
        Assert.assertEquals(cy, svgCircle.getCy(), TestConstant.DELTA_F);
        Assert.assertEquals(r, svgCircle.getR(), TestConstant.DELTA_F);

        Element element = svgCircle.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertNotNull(element);
        Assert.assertEquals("circle", element.getTagName());
        Assert.assertEquals(cx, Float.valueOf(element.getAttribute("cx")), TestConstant.DELTA_F);
        Assert.assertEquals(cy, Float.valueOf(element.getAttribute("cy")), TestConstant.DELTA_F);
        Assert.assertEquals(r, Float.valueOf(element.getAttribute("r")), TestConstant.DELTA_F);

    }

    @Test
    public void cloneTest() {
        SVGCircle svgCircle = new SVGCircle(cx, cy, r);
        Assert.assertNotEquals(svgCircle, svgCircle.clone());
    }

}