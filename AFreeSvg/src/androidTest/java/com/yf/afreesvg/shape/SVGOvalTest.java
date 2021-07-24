package com.yf.afreesvg.shape;

import com.yf.afreesvg.TestConstant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SVGOvalTest extends SVGShapeBaseTest {
    private float cx;
    private float cy;
    private float width;
    private float height;

    public SVGOvalTest(float cx, float cy, float width, float height) {
        this.cx = cx;
        this.cy = cy;
        this.width = width;
        this.height = height;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {1f, 2f, 3f, 4f},
                        {0f, 0f, 0f, 0f}
                }
        );
    }

    @Test
    public void convertToSVGElement() {
        SVGOval oval = new SVGOval(cx, cy, width, height);
        Assert.assertEquals(cx, oval.getCx(), TestConstant.DELTA_F);
        Assert.assertEquals(cy, oval.getCy(), TestConstant.DELTA_F);
        Assert.assertEquals(width, oval.getRx(), TestConstant.DELTA_F);
        Assert.assertEquals(height, oval.getRy(), TestConstant.DELTA_F);

        Element element = oval.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        Assert.assertEquals(cx, Float.valueOf(element.getAttribute("cx")), TestConstant.DELTA_F);
        Assert.assertEquals(cy, Float.valueOf(element.getAttribute("cy")), TestConstant.DELTA_F);
        Assert.assertEquals(width, Float.valueOf(element.getAttribute("rx")), TestConstant.DELTA_F);
        Assert.assertEquals(height, Float.valueOf(element.getAttribute("ry")), TestConstant.DELTA_F);
    }
}