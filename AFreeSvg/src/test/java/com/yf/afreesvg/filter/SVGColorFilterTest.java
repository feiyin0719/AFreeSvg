package com.yf.afreesvg.filter;

import com.yf.afreesvg.TestConstant;
import com.yf.afreesvg.util.DoubleFunction;

import org.junit.Test;
import org.w3c.dom.Element;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SVGColorFilterTest extends SVGFilterBaseTest {


    @Test
    public void effectConvertToSVGElement() {
        float f1[] = new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        float f2[] = new float[]{1, 2, 3};
        SVGColorFilter.SVGColorFilterEffect effect = new SVGColorFilter.SVGColorFilterEffect(f1);
        effect.setType(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE);
        assertEquals(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE, effect.getType());

        assertArrayEquals(f1, effect.getColorMatrix(), TestConstant.DELTA_F);

        effect.setColorMatrix(f2);
        assertArrayEquals(f2, effect.getColorMatrix(), TestConstant.DELTA_F);

        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("feColorMatrix", element.getTagName());
        assertEquals(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE, element.getAttribute("type"));
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
        assertEquals(SVGColorFilter.SVGColorFilterEffect.TYPE_MATRIX, filter.getType());
    }

    @Test
    public void setType() {
        SVGColorFilter filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        filter.setType(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE);
        assertEquals(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE, filter.getType());
    }
}