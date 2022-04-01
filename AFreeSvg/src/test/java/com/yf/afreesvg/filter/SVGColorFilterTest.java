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


import com.yf.afreesvg.TestConstant;

import com.yf.afreesvg.util.DoubleFunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.w3c.dom.Element;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(RobolectricTestRunner.class)
public class SVGColorFilterTest extends SVGFilterBaseTest {


    @Test
    public void effectConvertToSVGElement() {
        float f1[] = new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        float f2[] = new float[]{1, 2, 3};
        SVGColorFilter.SVGColorFilterEffect effect = new SVGColorFilter.SVGColorFilterEffect(f1);
        effect.setType(SVGColorFilter.SVGColorFilterEffect.ColorFilterType.TYPE_HUEROTATE);
        assertEquals(SVGColorFilter.SVGColorFilterEffect.ColorFilterType.TYPE_HUEROTATE, effect.getType());

        assertArrayEquals(f1, effect.getColorMatrix(), TestConstant.DELTA_F);

        effect.setColorMatrix(f2);
        assertArrayEquals(f2, effect.getColorMatrix(), TestConstant.DELTA_F);

        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("feColorMatrix", element.getTagName());
        assertEquals(SVGColorFilter.SVGColorFilterEffect.ColorFilterType.TYPE_HUEROTATE, element.getAttribute("type"));
        assertNotNull(element.getAttribute("value"));
        DoubleFunction function = canvas.getGeomDoubleConverter();
        StringBuilder sb = new StringBuilder();
        for (float f : f2) {
            sb.append(function.apply(f)).append(" ");
        }
        assertEquals(sb.toString(), element.getAttribute("value"));
        effectBaseTest(effect);


    }

    @Test
    public void convertToSVGElement() {
        SVGColorFilter filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        Element element = filter.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        filterBaseTest(element);
    }

    @Test
    public void getColorMatrix() {
        SVGColorFilter filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        assertArrayEquals(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, filter.getColorMatrix(), TestConstant.DELTA_F);
    }

    @Test
    public void setColorMatrix() {
        SVGColorFilter filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        filter.setColorMatrix(new float[]{1, 2, 3});
        assertArrayEquals(new float[]{1, 2, 3}, filter.getColorMatrix(), TestConstant.DELTA_F);

    }

    @Test
    public void getType() {
        SVGColorFilter filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        assertEquals(SVGColorFilter.SVGColorFilterEffect.ColorFilterType.TYPE_MATRIX, filter.getType());
    }

    @Test
    public void setType() {
        SVGColorFilter filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        filter.setType(SVGColorFilter.SVGColorFilterEffect.ColorFilterType.TYPE_HUEROTATE);
        assertEquals(SVGColorFilter.SVGColorFilterEffect.ColorFilterType.TYPE_HUEROTATE, filter.getType());
    }
}