package com.yf.afreesvg.filter;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yf.afreesvg.TestConstant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(AndroidJUnit4.class)
public class SVGOffsetFilterTest extends SVGFilterBaseTest {

    @Test
    public void convertToSVGElement() {
        SVGOffsetFilter filter = new SVGOffsetFilter(5f, 10f);
        assertEquals(5f, filter.getDx(), TestConstant.DELTA_F);
        assertEquals(10f, filter.getDy(), TestConstant.DELTA_F);
        filter.setDx(10f);
        filter.setDy(5f);
        assertEquals(10f, filter.getDx(), TestConstant.DELTA_F);
        assertEquals(5f, filter.getDy(), TestConstant.DELTA_F);
        Element element = filter.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        filterBaseTest(element);

    }

    @Test
    public void effectConvertToSVGElement() {
        SVGOffsetFilter.SVGOffsetFilterEffect offsetFilterEffect = new SVGOffsetFilter.SVGOffsetFilterEffect(5f, 10f);
        assertEquals(5f, offsetFilterEffect.getDx(), TestConstant.DELTA_F);
        assertEquals(10f, offsetFilterEffect.getDy(), TestConstant.DELTA_F);
        offsetFilterEffect.setDx(10f);
        offsetFilterEffect.setDy(5f);
        assertEquals(10f, offsetFilterEffect.getDx(), TestConstant.DELTA_F);
        assertEquals(5f, offsetFilterEffect.getDy(), TestConstant.DELTA_F);
        Element element = offsetFilterEffect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("feOffset", element.getTagName());
        assertEquals(canvas.getGeomDoubleConverter().apply(10f),element.getAttribute("dx"));
        assertEquals(canvas.getGeomDoubleConverter().apply(5f),element.getAttribute("dy"));
        effectBaseTest(offsetFilterEffect);
    }
}