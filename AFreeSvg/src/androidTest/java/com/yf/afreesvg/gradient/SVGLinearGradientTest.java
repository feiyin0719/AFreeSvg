package com.yf.afreesvg.gradient;

import android.graphics.PointF;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yf.afreesvg.TestConstant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SVGLinearGradientTest extends SVGGradientBaseTest {

    @Test
    public void convertToSVGElement() {
        SVGLinearGradient linearGradient = new SVGLinearGradient();
        assertEquals(0f, linearGradient.getStartPoint().x, TestConstant.DELTA_F);
        assertEquals(0f, linearGradient.getStartPoint().y, TestConstant.DELTA_F);
        assertEquals(0f, linearGradient.getEndPoint().x, TestConstant.DELTA_F);
        assertEquals(0f, linearGradient.getEndPoint().y, TestConstant.DELTA_F);
        linearGradient.setStartPoint(new PointF(1f, 2f));
        linearGradient.setEndPoint(new PointF(3f, 4f));
        assertEquals(1f, linearGradient.getStartPoint().x, TestConstant.DELTA_F);
        assertEquals(2f, linearGradient.getStartPoint().y, TestConstant.DELTA_F);
        assertEquals(3f, linearGradient.getEndPoint().x, TestConstant.DELTA_F);
        assertEquals(4f, linearGradient.getEndPoint().y, TestConstant.DELTA_F);
        Element element = linearGradient.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("linearGradient", element.getTagName());
        assertEquals(1f, Float.valueOf(element.getAttribute("x1")), TestConstant.DELTA_F);
        assertEquals(2f, Float.valueOf(element.getAttribute("y1")), TestConstant.DELTA_F);
        assertEquals(3f, Float.valueOf(element.getAttribute("x2")), TestConstant.DELTA_F);
        assertEquals(4f, Float.valueOf(element.getAttribute("y2")), TestConstant.DELTA_F);
        baseGradientTest(linearGradient);
    }
}